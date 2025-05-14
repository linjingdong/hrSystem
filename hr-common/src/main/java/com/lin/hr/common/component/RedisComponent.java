package com.lin.hr.common.component;

import com.lin.hr.common.constants.RedisKeyConstant;
import com.lin.hr.common.constants.TimeConstant;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 15:27
 **/
@Component("redisComponent")
public class RedisComponent {
    @Resource
    private RedisUtils<Object> redisUtils;

    /**
     * 获取心跳
     *
     * @param userId 用户Id
     * @return 心跳
     */
    public Long getUserHeartBeat(String userId) {
        return (Long) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_USER_HEART_BEAT + userId);
    }

    /**
     * 保存心跳
     */
    public void saveUserHeartBeat(String userId) {
        redisUtils.setEx(RedisKeyConstant.REDIS_KEY_WS_USER_HEART_BEAT + userId, System.currentTimeMillis(), TimeConstant.REDIS_KEY_EXPIRES_HEART_BEAT);
    }

    /**
     * 删除心跳
     */
    public void removeUserHeartBeat(String userId) {
        redisUtils.delete(RedisKeyConstant.REDIS_KEY_WS_USER_HEART_BEAT + userId);
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

    /**
     * 获取Token
     */
    public TokenUserInfoDto getTokenUserInfo(String token) {
        return (TokenUserInfoDto) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_TOKEN + token);
    }

    /**
     * 通过userId获取Token
     * @param userId 用户id
     * @return token
     */
    public TokenUserInfoDto getTokenUserInfoByUserId(String userId) {
        return (TokenUserInfoDto) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_TOKEN_USERID + userId);
    }

    /**
     * 通过useId清除该用户token
     *
     * @param userId 用户id
     */
    public void cleanUserTokenByUserId(String userId) {
        String token = (String) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_TOKEN_USERID + userId);
        if (StringUtils.isBlank(token)) {
            return;
        }
        redisUtils.delete(RedisKeyConstant.REDIS_KEY_WS_TOKEN + token);
    }

    /**
     * 获取系统设置缓存
     */
    public SysSettingDto getSysSetting() {
        SysSettingDto sysSettingDto = (SysSettingDto) redisUtils.get(RedisKeyConstant.REDIS_KEY_SYS_SETTING);
        return sysSettingDto == null ? new SysSettingDto() : sysSettingDto;
    }

    /**
     * 系统缓存设置
     */
    public void saveSysSetting(SysSettingDto sysSettingDto) {
        redisUtils.set(RedisKeyConstant.REDIS_KEY_SYS_SETTING, sysSettingDto);
    }

    /**
     * 清楚缓存联系人
     */
    public void cleanUserContactIds(String userId) {
        redisUtils.delete(RedisKeyConstant.REDIS_KEY_USER_CONTACT + userId);
    }

    /**
     * 批量缓存联系人
     */
    public void addUserContactBatch(String userId, List<String> userContactIds) {
        List<Object> objectList = new ArrayList<>(userContactIds);
        redisUtils.lPushAll(RedisKeyConstant.REDIS_KEY_USER_CONTACT + userId, objectList, TimeConstant.REDIS_TIME_EXPIRES_DAY);
    }

    /**
     * 单独缓存联系人
     */
    public void addUserContact(String userId, String contactId) {
        List<String> userContactIds = getUserContactIds(userId);
        if (userContactIds.contains(contactId)) {
            return;
        }
        redisUtils.lPush(RedisKeyConstant.REDIS_KEY_USER_CONTACT + userId, contactId, TimeConstant.REDIS_TIME_EXPIRES_DAY);
    }

    /**
     * 获取联系人
     */
    public List<String> getUserContactIds(String userId) {
        List<Object> objectList = redisUtils.getQueueList(RedisKeyConstant.REDIS_KEY_USER_CONTACT + userId);
        return objectList.stream().map(Object::toString).collect(Collectors.toList());
    }
}
