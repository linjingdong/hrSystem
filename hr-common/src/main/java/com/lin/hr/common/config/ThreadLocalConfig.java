package com.lin.hr.common.config;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/6 22:31
 **/
public class ThreadLocalConfig {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setToken(String token) {
        threadLocal.set(token);
    }

    public static String getToken() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
