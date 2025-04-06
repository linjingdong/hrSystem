package com.lin.hr.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/30 16:54
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {
    /**
     * 校验登录
     * @return 默认为true
     */
    boolean checkLogin() default true;

    /**
     * 校验管理员
     * @return 默认为false
     */
    boolean checkAdmin() default false;
}
