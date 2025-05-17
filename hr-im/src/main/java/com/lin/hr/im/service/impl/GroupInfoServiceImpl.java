package com.lin.hr.im.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.Constant;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageStatusEnum;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.*;
import com.lin.hr.im.entity.query.*;
import com.lin.hr.im.enums.group.GroupStatusEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.im.entity.vo.gourp.GroupInfoVo;
import com.lin.hr.im.mappers.*;
import com.lin.hr.im.service.ChatSessionUserService;
import com.lin.hr.im.service.UserContactService;
import com.lin.hr.im.websocket.utils.ChannelContextUtils;
import com.lin.hr.im.websocket.utils.MessageHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.service.GroupInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 业务接口实现
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl implements GroupInfoService {
    @Autowired
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;
    @Autowired
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Autowired
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Autowired
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Autowired
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    @Lazy
    private GroupInfoService groupInfoService;
    @Autowired
    private UserContactService userContactService;
    @Autowired
    private ChannelContextUtils channelContextUtils;
    @Autowired
    private ChatSessionUserService chatSessionUserService;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private AppConfig appConfig;

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

            // 创建群组会话
            String sessionId = StringTools.getChatSession4Group(groupInfo.getGroupId());
            ChatSession chatSession = new ChatSession();
            chatSession.setSessionId(sessionId);
            chatSession.setLastMessage(MessageTypeEnum.GROUP_CREATE.getInitMessage());
            chatSession.setLastReceiveTime(curDate.getTime());
            chatSessionMapper.insertOrUpdate(chatSession);

            // 创建会话用户
            ChatSessionUser chatSessionUser = new ChatSessionUser();
            chatSessionUser.setSessionId(sessionId);
            chatSessionUser.setContactId(groupInfo.getGroupId());
            chatSessionUser.setContactName(groupInfo.getGroupName());
            chatSessionUser.setUserId(groupInfo.getGroupOwnerId());
            chatSessionUserMapper.insert(chatSessionUser);

            // 创建消息
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSessionId(sessionId);
            chatMessage.setMessageType(MessageTypeEnum.GROUP_CREATE.getType());
            chatMessage.setMessageContent(MessageTypeEnum.GROUP_CREATE.getInitMessage());
            chatMessage.setSendTime(curDate.getTime());
            chatMessage.setContactId(groupInfo.getGroupId());
            chatMessage.setContactType(UserContactTypeEnum.GROUP.getType());
            chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
            chatMessageMapper.insert(chatMessage);

            // 将群组添加到该用户联系人
            redisComponent.addUserContact(groupInfo.getGroupOwnerId(), groupInfo.getGroupId());
            // 将联系人通道添加到群组通道
            channelContextUtils.addUser2Group(groupInfo.getGroupOwnerId(), groupInfo.getGroupId());

            // 发送ws消息
            chatSessionUser.setLastMessage(MessageTypeEnum.GROUP_CREATE.getInitMessage());
            chatSessionUser.setLastReceiveTime(curDate.getTime());
            chatSessionUser.setMemberCount(1);

            MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
            BeanUtils.copyProperties(chatMessage, messageSendDto);
            messageSendDto.setExtendData(chatSessionUser);
            messageSendDto.setLastMessage(chatSessionUser.getLastMessage());
            messageHandler.sendMessage(messageSendDto);

        } else {
            GroupInfo groupDbInfo = this.groupInfoMapper.selectByGroupId(groupInfo.getGroupId());
            if (null == groupDbInfo || !groupDbInfo.getGroupOwnerId().equals(groupInfo.getGroupOwnerId())) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            this.groupInfoMapper.updateByGroupId(groupInfo, groupInfo.getGroupId());
            // 修改群组冗余信息
            String contactNameUpdate = null;
            if (!groupDbInfo.getGroupName().equals(groupInfo.getGroupName())) {
                contactNameUpdate = groupInfo.getGroupName();
            }
            if (null == contactNameUpdate) {
                return;
            }
            chatSessionUserService.updateRedundancyInfo(contactNameUpdate, groupInfo.getGroupId());
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

    @Override
    public void dissolutionGroup(String groupOwenId, String groupId) {
        GroupInfo dbGroupInfo = this.groupInfoMapper.selectByGroupId(groupId);
        if (null == dbGroupInfo || !dbGroupInfo.getGroupOwnerId().equals(groupOwenId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 删除群组
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setStatus(GroupStatusEnum.DISSOLUTION.getStatus());
        this.groupInfoMapper.updateByGroupId(groupInfo, groupId);

        // 更新联系人信息
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactType(UserContactTypeEnum.GROUP.getType());
        userContactQuery.setContactId(groupId);
        UserContact updateUserContact = new UserContact();
        updateUserContact.setStatus(UserContactStatusEnum.DEL.getStatus());
        this.userContactService.updateByParam(updateUserContact, userContactQuery);

        // 移除相关群员的联系人缓存
        List<UserContact> userContacts = userContactMapper.selectList(userContactQuery);
        for (UserContact userContact : userContacts) {
            redisComponent.removeUserContact(userContact.getUserId(), userContact.getContactId());
        }

        // 发消息 1、更新会话消息 2、记录群消息 3、发送解散通知消息
        String sessionId = StringTools.getChatSession4Group(groupId);
        Date curDate = new Date();
        String messageContent = MessageTypeEnum.DISSOLUTION_GROUP.getInitMessage();

        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(messageContent);
        chatSession.setLastReceiveTime(curDate.getTime());
        chatSessionMapper.updateBySessionId(chatSession, sessionId);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setSendTime(curDate.getTime());
        chatMessage.setContactType(UserContactTypeEnum.GROUP.getType());
        chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
        chatMessage.setMessageType(MessageTypeEnum.DISSOLUTION_GROUP.getType());
        chatMessage.setContactId(groupId);
        chatMessage.setMessageContent(messageContent);
        chatMessageMapper.insert(chatMessage);
        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        BeanUtils.copyProperties(chatMessage, messageSendDto);
        messageHandler.sendMessage(messageSendDto);
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

    @Override
    public void addOrRemoveGroupUser(TokenUserInfoDto tokenUserInfoDto, String groupId, String selectContacts, Integer opType) {
        GroupInfo groupInfo = groupInfoMapper.selectByGroupId(groupId);
        if (null == groupInfo || !groupInfo.getGroupOwnerId().equals(tokenUserInfoDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String[] contactIds = selectContacts.split(",");
        for (String contactId : contactIds) {
            if (Constant.ZERO.equals(opType)) {
                // 1. 移除联系人
                groupInfoService.leaveGroup(contactId, groupId, MessageTypeEnum.REMOVE_GROUP);
            } else {
                // 2. 添加联系人
                userContactService.addContact(contactId, null, groupId, UserContactTypeEnum.GROUP.getType(), null);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void leaveGroup(String userId, String groupId, MessageTypeEnum messageTypeEnum) {
        GroupInfo groupInfo = groupInfoMapper.selectByGroupId(groupId);
        if (null == groupInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (userId.equals(groupInfo.getGroupOwnerId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        Integer count = userContactMapper.deleteByUserIdAndContactId(userId, groupId);
        if (count == 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        String sessionId = StringTools.getChatSession4Group(groupId);
        Date curDate = new Date();
        String messageContent = String.format(messageTypeEnum.getInitMessage(), userInfo.getUsername());

        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(messageContent);
        chatSession.setLastReceiveTime(curDate.getTime());
        chatSessionMapper.updateBySessionId(chatSession, sessionId);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setSendTime(curDate.getTime());
        chatMessage.setContactType(UserContactTypeEnum.GROUP.getType());
        chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
        chatMessage.setMessageType(messageTypeEnum.getType());
        chatMessage.setContactId(groupId);
        chatMessage.setMessageContent(messageContent);
        chatMessageMapper.insert(chatMessage);

        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(groupId);
        userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        Integer memberCount = this.userContactMapper.selectCount(userContactQuery);
        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        BeanUtils.copyProperties(chatMessage, messageSendDto);
        messageSendDto.setExtendData(userId);
        messageSendDto.setMemberCount(memberCount);
        messageHandler.sendMessage(messageSendDto);
    }
}