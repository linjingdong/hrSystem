package com.lin.hr.im.websocket.utils;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.TimeConstant;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.im.entity.dto.WsInitData;
import com.lin.hr.im.entity.po.ChatSessionUser;
import com.lin.hr.im.entity.po.UserInfo;

import com.lin.hr.im.entity.query.ChatSessionUserQuery;
import com.lin.hr.im.entity.query.UserInfoQuery;
import com.lin.hr.im.mappers.UserInfoMapper;
import com.lin.hr.im.service.ChatSessionUserService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
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

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/15 22:07
 **/
@Slf4j
@Component
public class ChannelContextUtils {
    private static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();

    @Resource
    private RedisComponent redisComponent;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    private ChatSessionUserService chatSessionUserService;

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
        updateUserInfo.setLastLoginTime(new Date().toString());
        userInfoMapper.updateByUserId(updateUserInfo, userId);

        // 给用户发消息
        UserInfo userInfo = new UserInfo();
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
        List<ChatSessionUser> chatSessionUsers = chatSessionUserService.findListByParam(sessionUserQuery);
        wsInitData.setChatSessionUserList(chatSessionUsers);
        // TODO 2. 查询聊天记录

        // TODO 3. 查询好友申请
    }

    // TODO 发送消息
    public static void sendMsg() {
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
}
