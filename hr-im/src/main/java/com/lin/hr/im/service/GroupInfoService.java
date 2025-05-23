package com.lin.hr.im.service;

import java.io.IOException;
import java.util.List;

import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.query.GroupInfoQuery;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.vo.gourp.GroupInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * 业务接口
 */
public interface GroupInfoService {

    /**
     * 根据条件查询列表
     */
    List<GroupInfo> findListByParam(GroupInfoQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(GroupInfoQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<GroupInfo> findListByPage(GroupInfoQuery param);

    /**
     * 新增
     */
    Integer add(GroupInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<GroupInfo> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<GroupInfo> listBean);

    /**
     * 多条件更新
     */
    Integer updateByParam(GroupInfo bean, GroupInfoQuery param);

    /**
     * 多条件删除
     */
    Integer deleteByParam(GroupInfoQuery param);

    /**
     * 根据GroupId查询对象
     */
    GroupInfo getGroupInfoByGroupId(String groupId);


    /**
     * 根据GroupId修改
     */
    Integer updateGroupInfoByGroupId(GroupInfo bean, String groupId);


    /**
     * 根据GroupId删除
     */
    Integer deleteGroupInfoByGroupId(String groupId);

    /**
     * 保存群组
     */
    void saveGroup(GroupInfo groupInfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException;

    GroupInfo getGroupInfo(String userId, String groupId);

    /**
     * 获取会话群组详情信息
     */
    GroupInfoVo getGroupInfo4Chat(String userId, @NotEmpty String groupId);

    /**
     * 解散群组
     */
    void dissolutionGroup(String groupOwenId, String groupId);

    /**
     * 添加或移除群组成员
     */
    void addOrRemoveGroupUser(TokenUserInfoDto tokenUserInfoDto, String groupId, String selectContacts, Integer opType);

    /**
     * 退出群聊
     */
    void leaveGroup(String userId, String groupId, MessageTypeEnum messageTypeEnum);
}