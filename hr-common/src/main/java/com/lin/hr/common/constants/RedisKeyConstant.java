package com.lin.hr.common.constants;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 19:19
 **/
public class RedisKeyConstant {
    // 图形验证码
    public static final String REDIS_KEY_CHECK_CODE = "hrsystem:checkcode:";

    // 心跳
    public static final String REDIS_KEY_WS_USER_HEART_BEAT = "hrsystem:ws:user:heartbeat:";

    // token
    public static final String REDIS_KEY_WS_TOKEN = "hrsystem:ws:token:";

    // 用户
    public static final String REDIS_KEY_WS_TOKEN_USERID = "hrsystem:ws:token:userId:";

    // 系统设置
    public static final String REDIS_KEY_SYS_SETTING = "hrsystem:syssetting:";

    // 用户联系人列表
    public static final String REDIS_KEY_USER_CONTACT = "hrsystem:ws:user:contact:";
}
