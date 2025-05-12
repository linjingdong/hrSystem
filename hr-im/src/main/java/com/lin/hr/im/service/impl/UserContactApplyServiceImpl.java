package com.lin.hr.im.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.im.constant.ApplyConstant;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.im.entity.query.*;
import com.lin.hr.im.enums.apply.JoinTypeEnum;
import com.lin.hr.im.enums.apply.UserContactApplyStatusEnum;
import com.lin.hr.im.enums.group.GroupStatusEnum;
import com.lin.hr.im.mappers.GroupInfoMapper;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.mappers.UserInfoMapper;
import com.lin.hr.im.service.UserContactService;
import com.lin.hr.im.websocket.utils.MessageHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import com.lin.hr.im.entity.po.UserContactApply;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.mappers.UserContactApplyMapper;
import com.lin.hr.im.service.UserContactApplyService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 联系人申请 业务接口实现
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private UserContactService userContactService;
    @Resource
    private MessageHandler messageHandler;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserContactApply> findListByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserContactApply> list = this.findListByParam(param);
        PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserContactApply bean) {
        return this.userContactApplyMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserContactApply bean, UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.deleteByParam(param);
    }

    /**
     * 根据ApplyId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 根据ApplyId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
        return this.userContactApplyMapper.updateByApplyId(bean, applyId);
    }

    /**
     * 根据ApplyId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.deleteByApplyId(applyId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(UserContactApply bean, String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.updateByApplyUserIdAndReceiveUserIdAndContactId(bean, applyUserId, receiveUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.deleteByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
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
        String applyUserName = tokenUserInfo.getUserName();
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
            userContactService.addContact(applyUserId, receiveUserId, contactId, contactType.getType(), applyInfo);
            return joinType;
        }

        // 添加申请数据
        UserContactApply dbApply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
        if (null == dbApply) {
            UserContactApply userContactApply = new UserContactApply();
            userContactApply.setApplyUserId(applyUserId);
            userContactApply.setApplyUserName(applyUserName);
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
            // 发送ws消息
            MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
            messageSendDto.setMessageType(MessageTypeEnum.CONTACT_APPLY.getType());
            messageSendDto.setMessageContent(applyInfo);
            messageSendDto.setContactId(receiveUserId);
            messageSendDto.setSendUserId(applyUserId);
            messageSendDto.setSendNickName(applyUserName);
            messageHandler.sendMessage(messageSendDto);
        }
        return joinType;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealWithApply(String userId, Integer applyId, Integer status) {
        UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
        if (statusEnum == null || UserContactApplyStatusEnum.INIT == statusEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 防止非当前用户的申请处理
        UserContactApply applyInfo = userContactApplyMapper.selectByApplyId(applyId);
        if (null == applyInfo || !userId.equals(applyInfo.getReceiveUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserContactApply userContactApply = new UserContactApply();
        userContactApply.setStatus(statusEnum.getStatus());
        userContactApply.setLastApplyTime(System.currentTimeMillis());

        UserContactApplyQuery userContactApplyQuery = new UserContactApplyQuery();
        userContactApplyQuery.setApplyId(applyId);
        userContactApplyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus()); // 只有初始状态才能修改，防重操作
        Integer count = userContactApplyMapper.updateByParam(userContactApply, userContactApplyQuery);
        // 防重操作
        if (0 == count) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (UserContactApplyStatusEnum.PASS == statusEnum) {
            userContactService.addContact(applyInfo.getApplyUserId(), applyInfo.getReceiveUserId(), applyInfo.getContactId(), applyInfo.getContactType(), applyInfo.getApplyInfo());
            return;
        }

        if (UserContactApplyStatusEnum.BLACKLIST == statusEnum) {
            Date curTime = new Date();
            UserContact userContact = new UserContact();
            userContact.setUserId(applyInfo.getApplyUserId());
            userContact.setContactId(applyInfo.getContactId());
            userContact.setContactType(applyInfo.getContactType());
            userContact.setCreateTime(curTime);
            userContact.setStatus(UserContactStatusEnum.BLACKLIST_BE_FIRST.getStatus());
            userContact.setLastUpdateTime(curTime);
            userContactMapper.insertOrUpdate(userContact);
        }
    }

    @Override
    public PaginationResultVO<UserContactApply> loadApply(String userId, Integer pageNo) {
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setPageNo(pageNo);
        applyQuery.setPageSize(PageSize.SIZE15.getSize());
        applyQuery.setReceiveUserId(userId);
        applyQuery.setOrderBy("last_apply_time desc");
        applyQuery.setQueryContactInfo(true);
        return this.findListByPage(applyQuery);
    }
}