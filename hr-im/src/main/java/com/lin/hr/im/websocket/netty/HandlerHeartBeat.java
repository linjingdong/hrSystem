package com.lin.hr.im.websocket.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/14 21:17
 **/
@Slf4j
@Component
public class HandlerHeartBeat extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                Channel channel = ctx.channel();
                Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
                String userId = attr.get();
                log.info("心跳超时，userId --> {}", userId);
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush("heart");
            }
        }
    }
}
