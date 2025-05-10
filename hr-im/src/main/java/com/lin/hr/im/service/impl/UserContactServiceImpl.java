package com.lin.hr.im.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageStatusEnum;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.*;
import com.lin.hr.im.entity.query.*;
import com.lin.hr.im.entity.vo.account.UserInfoVo;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.im.entity.dto.UserContactSearchResultDto;
import com.lin.hr.im.mappers.*;
import com.lin.hr.im.service.UserContactApplyService;
import com.lin.hr.im.websocket.utils.MessageHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lin.hr.common.vo.PaginationResultVO;
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
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Autowired
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Autowired
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Autowired
    private MessageHandler messageHandler;

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
    public void addContact(String applyUserId, String receiveUserId, String contactId, Integer contactType, String applyInfo) {
        // 判断群聊人数与系统群聊人数
        if (UserContactTypeEnum.GROUP.getType().equals(contactType)) {
            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setContactId(contactId);
            userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            Integer count = userContactMapper.selectCount(userContactQuery);
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            if (sysSetting.getMaxGroupCount() > count) {
                throw new BusinessException("成员已满，无法加入");
            }
        }
        Date curDate = new Date();
        // 同意，双方添加好友
        List<UserContact> userContactList = new ArrayList<>();
        // 申请人添加对方
        UserContact applyUserContact = new UserContact();
        applyUserContact.setUserId(applyUserId);
        applyUserContact.setContactId(contactId);
        applyUserContact.setContactType(contactType);
        applyUserContact.setCreateTime(curDate);
        applyUserContact.setLastUpdateTime(curDate);
        applyUserContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContactList.add(applyUserContact);
        // 如果是申请好友，接收人添加申请人，联系类型为群组的话就不用添加好友
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            UserContact userContact = new UserContact();
            userContact.setUserId(receiveUserId);
            userContact.setContactId(applyUserId);
            userContact.setContactType(contactType);
            userContact.setCreateTime(curDate);
            userContact.setLastUpdateTime(curDate);
            userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            userContactList.add(userContact);
        }
        // 批量插入
        userContactMapper.insertBatch(userContactList);
        // 如果是好友，接受人也添加申请人为好友，添加缓存
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            redisComponent.addUserContact(receiveUserId, applyUserId);
        }
        redisComponent.addUserContact(applyUserId, contactId);
        // 创建会话
        String sessionId = null;
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            sessionId = StringTools.getChatSession4User(new String[]{applyUserId, contactId});
        } else {
            sessionId = StringTools.getChatSession4Group(contactId);
        }

        List<ChatSessionUser> chatSessionUsers = new ArrayList<>();
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            // 用户类型
            // 1. 创建会话
            ChatSession chatSession = new ChatSession();
            chatSession.setSessionId(sessionId);
            chatSession.setLastMessage(applyInfo);
            chatSession.setLastReceiveTime(curDate.getTime());
            chatSessionMapper.insertOrUpdate(chatSession); // 这里用更新是因为有可能会有删了重加的可能
            // 2. 申请人session
            ChatSessionUser applySessionUser = new ChatSessionUser();
            applySessionUser.setUserId(applyUserId);
            applySessionUser.setContactId(contactId);
            applySessionUser.setSessionId(sessionId);
            UserInfo contactUserInfo = userInfoMapper.selectByUserId(contactId);
            applySessionUser.setContactName(contactUserInfo.getUsername());
            chatSessionUsers.add(applySessionUser);
            // 3. 接收人session
            ChatSessionUser contactSessionUser = new ChatSessionUser();
            contactSessionUser.setUserId(contactId);
            contactSessionUser.setContactId(applyUserId);
            contactSessionUser.setSessionId(sessionId);
            UserInfo applyUserInfo = userInfoMapper.selectByUserId(applyUserId);
            contactSessionUser.setContactName(applyUserInfo.getUsername());
            chatSessionUsers.add(contactSessionUser);

            chatSessionUserMapper.insertOrUpdateBatch(chatSessionUsers);
            // 4.记录消息表
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSessionId(sessionId);
            chatMessage.setMessageType(MessageTypeEnum.CONTACT_APPLY.getType());
            chatMessage.setMessageContent(applyInfo);
            chatMessage.setSendUserId(applyUserId);
            chatMessage.setSendUserNickName(applyUserInfo.getUsername());
            chatMessage.setSendTime(curDate.getTime());
            chatMessage.setContactId(contactId);
            chatMessage.setContactType(UserContactTypeEnum.USER.getType());
            chatMessageMapper.insert(chatMessage);

            MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
            BeanUtils.copyProperties(chatMessage, messageSendDto);
            messageHandler.sendMessage(messageSendDto);

            //  发送给发送人，发送人变接收人，接收人变发送人
            messageSendDto.setMessageType(MessageTypeEnum.CONTACT_APPLY.getType());
            messageSendDto.setContactId(applyUserId);
            messageSendDto.setExtendData(contactUserInfo);
            messageHandler.sendMessage(messageSendDto);
        } else {
            // 群组类型

        }
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

    @Override
    public UserInfoVo getUserContactInfo(String userId, String contactId) {
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(userId, contactId);
        if (null == userContact || !ArrayUtils.contains(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
                UserContactStatusEnum.BLACKLIST_BE.getStatus()
        }, userContact.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserInfoVo userInfoVo = new UserInfoVo();
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        BeanUtils.copyProperties(userInfo, userInfo);
        return userInfoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeUserContact(String userId, String contactId, UserContactStatusEnum statusEnum) {
        Date curDate = new Date();
        // 拉黑或移除好友
        UserContact userContact = new UserContact();
        userContact.setStatus(statusEnum.getStatus());
        userContact.setLastUpdateTime(curDate);
        userContactMapper.updateByUserIdAndContactId(userContact, userId, contactId);

        UserContact friendContact = new UserContact();
        if (UserContactStatusEnum.DEL.getStatus().equals(statusEnum.getStatus())) {
            friendContact.setStatus(UserContactStatusEnum.DEL_BE.getStatus());
            friendContact.setLastUpdateTime(curDate);

        } else if (UserContactStatusEnum.BLACKLIST.getStatus().equals(statusEnum.getStatus())) {
            friendContact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
            friendContact.setLastUpdateTime(curDate);
        }
        userContactMapper.updateByUserIdAndContactId(friendContact, contactId, userId);
        // TODO 从缓存我的好友列表中删除/拉黑好友
        // TODO 从缓存好友的列表中删除/拉黑我
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContact4Robot(String userId) {
        Date curDate = new Date();
        SysSettingDto sysSetting = redisComponent.getSysSetting();
        String contactId = sysSetting.getRobotUId();
        String contactName = sysSetting.getRobotNickName();
        String sendMessage = StringTools.cleanHtmlTag(sysSetting.getRobotWelcome());
        // 1. 添加机器人为好友
        UserContact userContact = new UserContact();
        userContact.setContactId(contactId);
        userContact.setUserId(userId);
        userContact.setContactType(UserContactTypeEnum.USER.getType());
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContact.setCreateTime(curDate);
        userContact.setLastUpdateTime(curDate);
        userContactMapper.insert(userContact);
        // 2. 添加会话信息
        ChatSession chatSession = new ChatSession();
        String sessionId = StringTools.getChatSession4User(new String[]{contactId, userId});
        chatSession.setSessionId(sessionId);
        chatSession.setLastMessage(sendMessage);
        chatSession.setLastReceiveTime(curDate.getTime());
        chatSessionMapper.insert(chatSession);
        // 3. 添加会话人信息
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        chatSessionUser.setUserId(userId);
        chatSessionUser.setSessionId(sessionId);
        chatSessionUser.setContactId(contactId);
        chatSessionUser.setContactName(contactName);
        chatSessionUserMapper.insert(chatSessionUser);
        // 4.添加聊天消息
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setContactId(userId);
        chatMessage.setContactType(UserContactTypeEnum.USER.getType());
        chatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
        chatMessage.setMessageContent(sendMessage);
        chatMessage.setSendUserId(contactId);
        chatMessage.setSendUserNickName(contactName);
        chatMessage.setSendTime(curDate.getTime());
        chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
        chatMessageMapper.insert(chatMessage);
    }
}