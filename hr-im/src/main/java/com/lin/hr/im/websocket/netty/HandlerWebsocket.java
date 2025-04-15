package com.lin.hr.im.websocket.netty;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/14 23:25
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class HandlerWebsocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Resource
    private RedisComponent redisComponent;

    /**
     * 通道就绪后调用，一般用来用户初始化
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的连接加入...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有连接断开...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext cxt, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = cxt.channel();
        log.info("收到消息：{}", textWebSocketFrame.text());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            HttpHeaders entries = complete.requestHeaders();
            String token = entries.get("token");
            if (null == token) {
                ctx.channel().close();
                return;
            }
            TokenUserInfoDto tokenUserInfo = redisComponent.getTokenUserInfo(token);
            if (null == tokenUserInfo) {
                ctx.channel().close();
                return;
            }
        }
    }
}
