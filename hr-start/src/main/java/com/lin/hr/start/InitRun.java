package com.lin.hr.start;

import com.lin.hr.common.utils.RedisUtils;
import com.lin.hr.im.websocket.netty.NettyWebSocketStarter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 16:39
 **/

@Component("initRun")
public class InitRun implements ApplicationRunner {
    private static final Logger log = Logger.getLogger(InitRun.class.getName());
    @Resource
    private DataSource dataSource;
    @Resource
    private RedisUtils<Object> redisUtil;
    @Resource
    private NettyWebSocketStarter nettyWebSocketStarter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            dataSource.getConnection();
            redisUtil.get("init");
            // 启动netty
            new Thread(nettyWebSocketStarter).start();
            log.info("服务器启动成功...");
        } catch (SQLException e) {
            log.info("数据库配置错误，请检查数据库配置...");
        } catch (RedisConnectionFailureException e) {
            log.info("redis配置错误，请检查redis配置...");
        } catch (Exception e) {
            log.info("服务启动失败...");
        }
    }
}
