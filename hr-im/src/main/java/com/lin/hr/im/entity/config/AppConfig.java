package com.lin.hr.im.entity.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 15:01
 **/
@Getter
@Component("appConfig")
public class AppConfig {
    /**
     * websocket端口
     */
    @Value("${ws.port:}")
    private Integer wsPort;
    /**
     * 文件目录
     */
    @Value("${project.folder:}")
    private String projectFolder;
    /**
     * 超级管理员账号
     */
    @Value("${admin.account:}")
    private String adminAccount;
}
