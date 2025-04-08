package com.lin.hr.im.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.im.entity.po.UserContactApply;
import com.lin.hr.im.entity.query.*;
import com.lin.hr.im.entity.vo.account.UserInfoVo;
import com.lin.hr.im.enums.apply.JoinTypeEnum;
import com.lin.hr.im.enums.apply.UserContactApplyStatusEnum;
import com.lin.hr.im.enums.group.GroupStatusEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.im.constant.ApplyConstant;
import com.lin.hr.im.entity.dto.UserContactSearchResultDto;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.im.mappers.GroupInfoMapper;
import com.lin.hr.im.mappers.UserContactApplyMapper;
import com.lin.hr.im.mappers.UserInfoMapper;
import com.lin.hr.im.service.UserContactApplyService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.service.UserContactService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 联系人 业务接口实现
 */
@Service("userContactService")
public class UserContactServiceImpl implements UserContactService {

    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;
    @Autowired
    private UserContactApplyService userContactApplyService;
    @Autowired
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Autowired
    private UserInfoServiceImpl userInfoService;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserContact> findListByParam(UserContactQuery param) {
        return this.userContactMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserContactQuery param) {
        return this.userContactMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserContact> findListByPage(UserContactQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserContact> list = this.findListByParam(param);
        PaginationResultVO<UserContact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserContact bean) {
        return this.userContactMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserContact bean, UserContactQuery param) {
        StringTools.checkParam(param);
        return this.userContactMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserContactQuery param) {
        StringTools.checkParam(param);
        return this.userContactMapper.deleteByParam(param);
    }

    /**
     * 根据UserIdAndContactId获取对象
     */
    @Override
    public UserContact getUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
    }

    /**
     * 根据UserIdAndContactId修改
     */
    @Override
    public Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId) {
        return this.userContactMapper.updateByUserIdAndContactId(bean, userId, contactId);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    @Override
    public Integer deleteUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.deleteByUserIdAndContactId(userId, contactId);
    }

    @Override
    public UserContactSearchResultDto searchContact(String userId, String contactId) {
        UserContactTypeEnum contactType = UserContactTypeEnum.getByPrefix(contactId);
        if (null == contactType) {
            return null;
        }
        UserContactSearchResultDto resultDto = new UserContactSearchResultDto();
        switch (contactType) {
            case USER:
                UserInfo userInfo = userInfoMapper.selectByUserId(contactId);
                if (null == userInfo) {
                    return null;
                }
                BeanUtils.copyProperties(userInfo, resultDto);
                resultDto.setNickName(userInfo.getUsername());
                break;
            case GROUP:
                GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
                if (null == groupInfo) {
                    return null;
                }
                resultDto.setNickName(groupInfo.getGroupName());
                break;
        }
        resultDto.setContactType(contactType.toString());
        resultDto.setContactId(contactId);

        if (userId.equals(contactId)) {
            resultDto.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            return resultDto;
        }
        // 查询是否是好友
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(userId, contactId);
        resultDto.setStatus(userContact == null ? null : userContact.getStatus());
        return resultDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applyAdd(TokenUserInfoDto tokenUserInfo, String contactId, String applyInfo) {
        // 获取会话类型
        UserContactTypeEnum contactType = UserContactTypeEnum.getByPrefix(contactId);
        if (null == contactType) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 申请人
        String applyUserId = tokenUserInfo.getUserId();
        // 申请内容
        applyInfo = StringUtils.isBlank(applyInfo) ? String.format(ApplyConstant.APPLY_INFO_TEMPLATE, applyInfo) : applyInfo;


        Integer joinType = null;
        String receiveUserId = contactId;

        // 如果是联系人，查询对方好友是否已经添加过了，如果拉黑了无法添加
        UserContact contact = userContactMapper.selectByUserIdAndContactId(applyUserId, contactId);
        if (contact != null && ArrayUtils.contains(
                new Integer[]{UserContactStatusEnum.BLACKLIST_BE.getStatus(), UserContactStatusEnum.BLACKLIST_BE_FIRST.getStatus()}, contact.getStatus()
        )) {
            throw new BusinessException("对方已经将你拉黑，无法添加！");
        }

        switch (contactType) {
            case GROUP: // 群
                GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
                if (null == groupInfo || GroupStatusEnum.DISSOLUTION.getStatus().equals(groupInfo.getStatus())) {
                    throw new BusinessException("该群组已经解散！");
                }
                // 申请给群主
                receiveUserId = groupInfo.getGroupOwnerId();
                // 直接加入还是申请加入
                joinType = groupInfo.getJoinType();
                break;
            case USER: // 用户
                UserInfo userInfo = userInfoMapper.selectByUserId(applyUserId);
                if (null == userInfo) {
                    throw new BusinessException("改用户不存在！");
                }
                // 一定要申请
                joinType = 1;
                break;
        }

        // 如果是加入不用申请记录
        if (JoinTypeEnum.JOIN.getType().equals(joinType)) {
            userContactApplyService.addContact(applyUserId, receiveUserId, contactId, contactType.getType(), applyInfo);
            return joinType;
        }

        // 添加申请数据
        UserContactApply dbApply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
        if (null == dbApply) {
            UserContactApply userContactApply = new UserContactApply();
            userContactApply.setApplyUserId(applyUserId);
            userContactApply.setReceiveUserId(receiveUserId);
            userContactApply.setContactType(contactType.getType());
            userContactApply.setContactId(contactId);
            userContactApply.setLastApplyTime(System.currentTimeMillis());
            userContactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
            userContactApply.setApplyInfo(applyInfo);
            userContactApplyMapper.insert(userContactApply);
        } else {
            UserContactApply userContactApply = new UserContactApply();
            userContactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
            userContactApply.setLastApplyTime(System.currentTimeMillis());
            userContactApply.setApplyInfo(applyInfo);
            userContactApplyMapper.updateByApplyId(userContactApply, dbApply.getApplyId());
        }

        if (dbApply == null || !UserContactApplyStatusEnum.INIT.getStatus().equals(dbApply.getStatus())) {
            // TODO 发送ws消息
        }
        return joinType;
    }

    @Override
    public List<UserContact> loadContact(String userId, String contactType) {
        UserContactTypeEnum userContactType = UserContactTypeEnum.getByType(contactType);
        if (userContactType == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setUserId(userId);
        userContactQuery.setContactType(userContactType.getType());
        if (UserContactTypeEnum.USER.getType().equals(userContactType.getType())) {
            // 用户
            userContactQuery.setQueryContactInfo(true);
        } else if (UserContactTypeEnum.GROUP.getType().equals(userContactType.getType())) {
            // 群聊
            userContactQuery.setQueryGroupInfo(true);
            userContactQuery.setExcludeMyGroup(true);
        }
        userContactQuery.setOrderBy("last_update_time desc");
        userContactQuery.setStatusArray(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
                UserContactStatusEnum.BLACKLIST_BE.getStatus()
        });
        return this.findListByParam(userContactQuery);
    }

    @Override
    public UserInfoVo getContactInfo(String userId, String contactId) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(contactId);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        userInfoVo.setContactStatus(UserContactStatusEnum.NOT_FRIEND.getStatus());
        UserContact userContact = getUserContactByUserIdAndContactId(userId, contactId);
        if (null != userContact) {
            userInfoVo.setContactStatus(UserContactStatusEnum.FRIEND.getStatus());
        }
        return userInfoVo;
    }
}