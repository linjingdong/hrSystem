package com.lin.hr.im.websocket.utils;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.TimeConstant;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.utils.JsonUtils;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.dto.WsInitData;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.*;

import com.lin.hr.im.entity.query.ChatMessageQuery;
import com.lin.hr.im.entity.query.ChatSessionUserQuery;
import com.lin.hr.im.entity.query.UserContactApplyQuery;
import com.lin.hr.im.entity.query.UserInfoQuery;
import com.lin.hr.im.enums.apply.UserContactApplyStatusEnum;
import com.lin.hr.im.mappers.ChatMessageMapper;
import com.lin.hr.im.mappers.ChatSessionUserMapper;
import com.lin.hr.im.mappers.UserContactApplyMapper;
import com.lin.hr.im.mappers.UserInfoMapper;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/15 22:07
 **/
@Slf4j
@Component
public class ChannelContextUtils {
    @Resource
    private RedisComponent redisComponent;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Autowired
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Autowired
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;

    private static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();

    /**
     * 连接netty
     */
    public void addContext(String userId, Channel channel) {
        String channelId = channel.id().toString();
        AttributeKey<String> attributeKey = null;
        if (!AttributeKey.exists(channelId)) {
            attributeKey = AttributeKey.newInstance(channelId);
        } else {
            attributeKey = AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(userId);

        List<String> contactIds = redisComponent.getUserContactIds(userId);
        for (String contactId : contactIds) {
            if (contactId.startsWith(UserContactTypeEnum.GROUP.getPrefix())) {
                add2Group(contactId, channel);
            }
        }
        USER_CONTEXT_MAP.put(userId, channel);
        redisComponent.saveUserHeartBeat(userId);

        // 更新用户最后连接时间
        UserInfo updateUserInfo = new UserInfo();
        updateUserInfo.setLastLoginTime(System.currentTimeMillis());
        userInfoMapper.updateByUserId(updateUserInfo, userId);

        // 给用户发消息
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        Long sourceLastOffTime = userInfo.getLastOffTime();
        Long lastOffTime = sourceLastOffTime;
        if (sourceLastOffTime != null && System.currentTimeMillis() - TimeConstant.MILLIS_SECONDS_3_DAYS_AGO > sourceLastOffTime) {
            lastOffTime = TimeConstant.MILLIS_SECONDS_3_DAYS_AGO;
        }

        WsInitData wsInitData = new WsInitData();
        // 1. 查询用户所有的会话信息
        ChatSessionUserQuery sessionUserQuery = new ChatSessionUserQuery();
        sessionUserQuery.setUserId(userId);
        sessionUserQuery.setOrderBy("last_receive_time desc");
        List<ChatSessionUser> chatSessionUsers = chatSessionUserMapper.selectList(sessionUserQuery);
        wsInitData.setChatSessionList(chatSessionUsers);

        // 2. 查询聊天记录
        ChatMessageQuery chatMessageQuery = new ChatMessageQuery();
        List<String> groupAndMeContactList = contactIds.stream().filter(item ->
                item.startsWith(UserContactTypeEnum.GROUP.getPrefix())
        ).collect(Collectors.toList());
        groupAndMeContactList.add(userId);
        chatMessageQuery.setContactIdList(groupAndMeContactList);
        chatMessageQuery.setLastReceiveTime(lastOffTime);
        List<ChatMessage> chatMessageList = chatMessageMapper.selectList(chatMessageQuery);
        wsInitData.setChatMessageList(chatMessageList);

        // 3. 查询好友申请
        UserContactApplyQuery userContactApplyQuery = new UserContactApplyQuery();
        userContactApplyQuery.setReceiveUserId(userId);
        userContactApplyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        userContactApplyQuery.setLastApplyTimeStamp(lastOffTime);
        Integer applyCount = userContactApplyMapper.selectCount(userContactApplyQuery);
        wsInitData.setApplyCount(applyCount);

        // 4. 发送消息
        MessageSendDto<WsInitData> messageSendDto = new MessageSendDto<>();
        messageSendDto.setMessageType(MessageTypeEnum.INIT.getType());
        messageSendDto.setContactId(userId);
        messageSendDto.setExtendData(wsInitData);
        sendMsg(messageSendDto, userId);
    }


    /**
     * 连接群组
     */
    private void add2Group(String groupId, Channel channel) {
        ChannelGroup group = GROUP_CONTEXT_MAP.get(groupId);
        if (group == null) {
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            GROUP_CONTEXT_MAP.put(groupId, group);
        }
        if (channel == null) {
            return;
        }
        group.add(channel);
        log.info("Channel added to group: {}", groupId);
    }

    /**
     * 断开netty
     */
    public void removeContext(Channel channel) {
        Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attr.get();
        if (StringUtils.isNotBlank(userId)) {
            USER_CONTEXT_MAP.remove(userId);
        }
        redisComponent.removeUserHeartBeat(userId);
        // 更新用户最后离线时间
        UserInfo updateUserInfo = new UserInfo();
        updateUserInfo.setLastOffTime(System.currentTimeMillis());
        userInfoMapper.updateByUserId(updateUserInfo, userId);
    }

    /**
     * 发送消息
     */
    public void sendMessage(MessageSendDto<?> messageSendDto) {
        UserContactTypeEnum contactType = UserContactTypeEnum.getByPrefix(messageSendDto.getContactId());
        if (contactType == null) {
            return;
        }
        switch (contactType) {
            case USER:
                send2User(messageSendDto);
                break;
            case GROUP:
                send2Group(messageSendDto);
                break;
        }
    }

    /**
     * 发送消息给用户
     */
    public void send2User(MessageSendDto<?> messageSendDto) {
        String receiverId = messageSendDto.getContactId();
        if (StringUtils.isBlank(receiverId)) {
            return;
        }
        sendMsg(messageSendDto, receiverId);
        // 强制下线
        if (MessageTypeEnum.FORCE_OFF_LINE.getType().equals(messageSendDto.getMessageType())) {
            // 关闭通道
            closeContext(receiverId);
        }
    }

    /**
     * 发送用户消息
     *
     * @param messageSendDto 消息参数
     * @param receiverId     消息接收人
     */
    public void sendMsg(MessageSendDto<?> messageSendDto, String receiverId) {
        Channel userChannel = USER_CONTEXT_MAP.get(receiverId);
        if (null == userChannel) {
            return;
        }
        // 现在是发送者发送消息，但是对于接收者而言，接收到的消息的内容中，联系人应该是发送者（原本发送者发送消息，联系人是接收者的），所以这里做了一下转换
        if (MessageTypeEnum.ADD_FRIEND_SELF.getType().equals(messageSendDto.getMessageType())) {
            UserInfo extendData = (UserInfo) messageSendDto.getExtendData();
            messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEND.getType());
            messageSendDto.setContactId(extendData.getUserId());
            messageSendDto.setContactName(extendData.getUsername());
            messageSendDto.setExtendData(null);
        } else {
            messageSendDto.setContactId(messageSendDto.getSendUserId());
            messageSendDto.setContactName(messageSendDto.getSendUserNickName());
        }
        userChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.convertObj2Json(messageSendDto)));
    }

    /**
     * 发送消息给群组
     */
    public void send2Group(MessageSendDto<?> messageSendDto) {
        if (StringUtils.isBlank(messageSendDto.getContactId())) {
            return;
        }
        ChannelGroup groupChannel = GROUP_CONTEXT_MAP.get(messageSendDto.getContactId());
        if (null == groupChannel) {
            return;
        }
        groupChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.convertObj2Json(messageSendDto)));

        // 移除群聊
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(messageSendDto.getMessageType());
        if (MessageTypeEnum.LEAVE_GROUP == messageTypeEnum || MessageTypeEnum.REMOVE_GROUP == messageTypeEnum) {
            String userId = (String) messageSendDto.getExtendData();
            redisComponent.removeUserContact(userId, messageSendDto.getContactId());
            Channel channel = USER_CONTEXT_MAP.get(userId);
            if (null == channel) {
                return;
            }
            groupChannel.remove(channel);
        }
        if (MessageTypeEnum.DISSOLUTION_GROUP == messageTypeEnum) {
            GROUP_CONTEXT_MAP.remove(messageSendDto.getContactId());
            groupChannel.close();
        }
    }

    /**
     * 强制下线
     */
    public void closeContext(String userId) {
        if (StringUtils.isBlank(userId)) {
            return;
        }
        Channel channel = USER_CONTEXT_MAP.get(userId);
        if (null == channel) {
            return;
        }
        redisComponent.cleanUserTokenByUserId(userId);
        channel.close();
    }

    public void addUser2Group(String userId, String groupId) {
        Channel channel = USER_CONTEXT_MAP.get(userId);
        add2Group(groupId, channel);
    }
}
