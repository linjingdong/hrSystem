package com.lin.hr.im.service;

import java.util.List;

import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.im.entity.dto.UserContactSearchResultDto;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.vo.account.UserInfoVo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 联系人 业务接口
 */
public interface UserContactService {

    /**
     * 根据条件查询列表
     */
    List<UserContact> findListByParam(UserContactQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(UserContactQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<UserContact> findListByPage(UserContactQuery param);

    /**
     * 新增
     */
    Integer add(UserContact bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserContact> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<UserContact> listBean);

    /**
     * 多条件更新
     */
    Integer updateByParam(UserContact bean, UserContactQuery param);

    /**
     * 多条件删除
     */
    Integer deleteByParam(UserContactQuery param);

    /**
     * 根据UserIdAndContactId查询对象
     */
    UserContact getUserContactByUserIdAndContactId(String userId, String contactId);


    /**
     * 根据UserIdAndContactId修改
     */
    Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId);


    /**
     * 根据UserIdAndContactId删除
     */
    Integer deleteUserContactByUserIdAndContactId(String userId, String contactId);

    UserContactSearchResultDto searchContact(String userId, @NotBlank String contactId);

    /**
     * 申请好友
     *
     * @return 加入类型：
     */
    Integer applyAdd(TokenUserInfoDto tokenUserInfo, @NotBlank String contactId, String applyInfo);

    /**
     * 添加联系人
     */
    void addContact(String applyUserId, String receiveUserId, String contactId, Integer contactType, String applyInfo);

    /**
     * 获取联系人列表
     */
    List<UserContact> loadContact(String userId, @NotNull String contactType);

    /**
     * 获取联系人信息，不一定是好友，群聊
     */
    UserInfoVo getContactInfo(String userId, @NotNull String contactId);

    /**
     * 获取联系人用户信息，一定是好友
     */
    UserInfoVo getUserContactInfo(String userId, @NotBlank String contactId);

    /**
     * 删除或拉黑好友
     */
    void removeUserContact(String userId, String contactId, UserContactStatusEnum statusEnum);
}