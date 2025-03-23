package com.lin.hr.common.component;

import com.lin.hr.common.constants.RedisKeyConstant;
import com.lin.hr.common.constants.TimeConstant;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 15:27
 **/
@Component("redisComponent")
public class RedisComponent {
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取心跳
     *
     * @param userId 用户Id
     * @return 心跳
     */
    public Long getUserHeartBeat(String userId) {
        return Long.parseLong(redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_USER_HEART_BEAT + userId).toString());
    }

    /**
     * 保存Token
     */
    public void saveTokenUserInfo(TokenUserInfoDto tokenUserInfo) {
        // 缓存Token
        redisUtils.setEx(RedisKeyConstant.REDIS_KEY_WS_TOKEN + tokenUserInfo.getToken(), tokenUserInfo, TimeConstant.REDIS_TIME_EXPIRES_DAY * 2);

        // 缓存userId - token
        redisUtils.setEx(RedisKeyConstant.REDIS_KEY_WS_TOKEN_USERID + tokenUserInfo.getUserId(), tokenUserInfo.getToken(), TimeConstant.REDIS_TIME_EXPIRES_DAY * 2);
    }
}
