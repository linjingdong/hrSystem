package com.lin.hr.common.constants;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 19:22
 **/
public class TimeConstant {
    public static final Integer REDIS_KEY_EXPIRES_HEART_BEAT = 6;

    public static final Integer REDIS_TIME_ONE_MIN = 60;

    public static final Integer REDIS_TIME_EXPIRES_DAY = REDIS_TIME_ONE_MIN * 60 * 24;

    public static final Long MILLIS_SECONDS_3_DAYS_AGO = 3 * 24 * 60 * 60 * 1000L;
}
