package com.lin.hr.im.websocket.netty;

import com.lin.hr.common.config.AppConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/14 00:07
 **/
@Slf4j
@Component
public class NettyWebSocketStarter implements Runnable{
    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Resource
    private HandlerWebsocket handlerWebsocket;
    @Resource
    private AppConfig appConfig;

    @PreDestroy
    private void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void run() {
        try {
            ServerBootstrap serverBootStrap = new ServerBootstrap();
            serverBootStrap.group(bossGroup, workerGroup);
            serverBootStrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // 设置几个重要的处理器
                            // 1.对http协议的支持,使用http的编码器，解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 2.聚合解码：httpRequest/httpContent/lastHttpContent到fullHttpRequest，保证接收的http的完整性
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            // 3.心跳
                            // long readerIdleTime 读超时时间：测试端一定时间内未收到被测试端消息
                            // long writerIdleTime 写超时时间：测试端一定时间内相别测试段发送消息
                            // long allIdleTime    所有类型的超时时间
                            pipeline.addLast(new IdleStateHandler(6, 0, 0, TimeUnit.SECONDS));
                            // 4.心跳处理器
                            pipeline.addLast(new HandlerHeartBeat());
                            // 5.将http协议升级为ws协议，对webSocket支持
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 64 * 1024, true, true, 10000L));
                            // 6.添加消息处理器
                            pipeline.addLast(handlerWebsocket);
                        }
                    });
            ChannelFuture channelFuture = serverBootStrap.bind(appConfig.getWsPort()).sync();
            log.info("Netty服务启动成功，端口：{}", appConfig.getWsPort());
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Netty服务启动失败", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
