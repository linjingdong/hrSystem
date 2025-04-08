package com.lin.hr.im.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.im.enums.group.GroupStatusEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.entity.vo.gourp.GroupInfoVo;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lin.hr.im.entity.query.GroupInfoQuery;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.query.SimplePage;
import com.lin.hr.im.mappers.GroupInfoMapper;
import com.lin.hr.im.service.GroupInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 业务接口实现
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl implements GroupInfoService {

    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private UserContactService userContactService;
    @Resource
    private AppConfig appConfig;
    @Autowired
    private UserContactApplyServiceImpl userContactApplyService;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<GroupInfo> findListByParam(GroupInfoQuery param) {
        return this.groupInfoMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(GroupInfoQuery param) {
        return this.groupInfoMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<GroupInfo> findListByPage(GroupInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<GroupInfo> list = this.findListByParam(param);
        PaginationResultVO<GroupInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(GroupInfo bean) {
        return this.groupInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<GroupInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.groupInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<GroupInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.groupInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(GroupInfo bean, GroupInfoQuery param) {
        StringTools.checkParam(param);
        return this.groupInfoMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(GroupInfoQuery param) {
        StringTools.checkParam(param);
        return this.groupInfoMapper.deleteByParam(param);
    }

    /**
     * 根据GroupId获取对象
     */
    @Override
    public GroupInfo getGroupInfoByGroupId(String groupId) {
        return this.groupInfoMapper.selectByGroupId(groupId);
    }

    /**
     * 根据GroupId修改
     */
    @Override
    public Integer updateGroupInfoByGroupId(GroupInfo bean, String groupId) {
        return this.groupInfoMapper.updateByGroupId(bean, groupId);
    }

    /**
     * 根据GroupId删除
     */
    @Override
    public Integer deleteGroupInfoByGroupId(String groupId) {
        return this.groupInfoMapper.deleteByGroupId(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGroup(GroupInfo groupInfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException {
        Date curDate = new Date();
        if (StringTools.isEmpty(groupInfo.getGroupId())) { // 新增群组
            GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
            groupInfoQuery.setGroupOwnerId(groupInfo.getGroupOwnerId());
            Integer count = this.groupInfoMapper.selectCount(groupInfoQuery);
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            if (count >= sysSetting.getMaxGroupCount()) {
                // 创建的群组大于系统设置最大值
                throw new BusinessException("最多支持创建" + sysSetting.getMaxGroupCount() + "个群聊");
            }

            if (null == avatarFile) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }

            groupInfo.setCreateTime(curDate);
            groupInfo.setGroupId(StringTools.getGroupId());
            this.groupInfoMapper.insert(groupInfo);

            // 将群组添加为联系人
            UserContact userContact = new UserContact();
            userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            userContact.setContactType(UserContactTypeEnum.GROUP.getType());
            userContact.setContactId(groupInfo.getGroupId());
            userContact.setUserId(groupInfo.getGroupOwnerId());
            userContact.setCreateTime(curDate);
            userContact.setLastUpdateTime(curDate);
            this.userContactMapper.insert(userContact);

            // TODO 创建会话
            // TODO 发送群消息
        } else {
            GroupInfo groupDbInfo = this.groupInfoMapper.selectByGroupId(groupInfo.getGroupId());
            if (null == groupDbInfo || !groupDbInfo.getGroupOwnerId().equals(groupInfo.getGroupOwnerId())) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            this.groupInfoMapper.updateByGroupId(groupInfo, groupInfo.getGroupId());

            // TODO 修改群组冗余信息
            // TODO 发送群昵称修改消息
        }

        // 处理图片
        if (null == avatarFile) {
            return;
        }
        String baseFolder = appConfig.getProjectFolder() + FileConstant.FILE_FOLDER_FILE;
        File targetFileFolder = new File(baseFolder + FileConstant.FILE_FOLDER_AVATAR_NAME);
        if (!targetFileFolder.exists()) {
            targetFileFolder.mkdirs();
        }
        String filePath = targetFileFolder.getPath() + "/" + groupInfo.getGroupId() + FileConstant.IMAGE_SUFFIX;

        avatarFile.transferTo(new File(filePath));
        avatarCover.transferTo(new File(filePath + FileConstant.COVER_IMAGE_SUFFIX));
    }

    @Override
    public GroupInfo getGroupInfo(String userId, String groupId) {
        GroupInfo groupInfo = getGroupInfoCommon(userId, groupId);
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(groupId);
        Integer memberCount = userContactService.findCountByParam(userContactQuery);
        groupInfo.setMemberCount(memberCount);
        return groupInfo;
    }

    @Override
    public GroupInfoVo getGroupInfo4Chat(String userId, String groupId) {
        GroupInfo groupInfoCommon = getGroupInfoCommon(userId, groupId);

        // 获取用户关联群组信息
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(groupId);
        userContactQuery.setQueryUserInfo(true);
        userContactQuery.setOrderBy("create_time asc");
        userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        List<UserContact> userContactList = userContactService.findListByParam(userContactQuery);

        GroupInfoVo groupInfoVo = new GroupInfoVo();
        groupInfoVo.setGroupInfo(groupInfoCommon);
        groupInfoVo.setUserContactList(userContactList);
        return groupInfoVo;
    }

    private GroupInfo getGroupInfoCommon(String userId, String groupId) {
        UserContact userContact = userContactService.getUserContactByUserIdAndContactId(userId, groupId);
        if (null == userContact || !UserContactStatusEnum.FRIEND.getStatus().equals(userContact.getStatus())) {
            throw new BusinessException("您不在群聊或者群聊不存在或已解散");
        }
        GroupInfo groupInfo = getGroupInfoByGroupId(groupId);
        if (null == groupInfo || !GroupStatusEnum.NORMAL.getStatus().equals(groupInfo.getStatus())) {
            throw new BusinessException("群聊不存在或已解散");
        }
        return groupInfo;
    }
}