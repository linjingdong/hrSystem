package com.lin.hr.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/19 22:27
 **/
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
@MapperScan(basePackages = {"com.lin.hr.*.mappers"})
@SpringBootApplication(scanBasePackages = {"com.lin.hr.common", "com.lin.hr.im", "com.lin.hr.start", "com.lin.hr.manage"})
public class HrApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }
}
