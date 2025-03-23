package com.lin.hr.start;

import com.lin.hr.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
    private RedisUtils redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            dataSource.getConnection();
            redisUtil.get("init");
            log.info("服务器启动成功...");
        } catch (SQLException e) {
            log.info("启动数据库失败...");
        }
    }
}
