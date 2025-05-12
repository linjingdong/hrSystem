package com.lin.hr.im.websocket.utils;

import com.lin.hr.common.utils.JsonUtils;
import com.lin.hr.im.entity.dto.MessageSendDto;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/21 00:29
 **/
@Slf4j
@Component("messageHandler")
public class MessageHandler {
    private static final String MESSAGE_TOPIC = "message.topic";

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ChannelContextUtils channelContextUtils;

    @PostConstruct // 服务启动的时候，要去监听
    public void listenMessage() {
        RTopic rTopic = redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.addListener(MessageSendDto.class, (MessageSendDto, sendDto) -> {
            log.info("收到广播消息：{}", JsonUtils.convertObj2Json(sendDto));
            channelContextUtils.sendMessage(sendDto);
        });
    }

    public void sendMessage(MessageSendDto<?> messageSendDto) {
        RTopic rTopic = redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.publish(messageSendDto);
    }
}
