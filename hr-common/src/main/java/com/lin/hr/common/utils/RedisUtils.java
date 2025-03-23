package com.lin.hr.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @decirption RedisUtils
 * @since 2025/3/21 00:25
 **/
@Slf4j
@Component("redisUtils")
public class RedisUtils<V> {
    @Resource
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 删除缓存
     *
     * @param key 键（可以传一个或多个值）
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取缓存
     *
     * @param key 键
     */
    public V get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public boolean set(String key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.info("设置redisKey:{},value:{}失败", key, value);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     * @return 布尔值
     */
    public boolean setEx(String key, V value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.info("设置redisKey: {}, value: {}失败", key, value);
            return false;
        }
    }

    /**
     * 设置失效时间
     *
     * @param key  键
     * @param time 时间
     * @return 布尔值
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取缓存队列
     *
     * @param key 键
     * @return 队列
     */
    public List<V> getQueueList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 将值插入到列表的头部
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return 布尔值
     */
    public boolean lPush(String key, V value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 移除列表中的一个值
     *
     * @param key   键
     * @param object 值
     * @return 布尔值
     */
    public long remove(String key, Object object) {
        try {
            return redisTemplate.opsForList().remove(key, 1, object);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将多个值插入到列表的头部
     *
     * @param key    键
     * @param values 值
     * @param time   时间
     * @return 布尔值
     */
    public boolean lPushAll(String key, List<V> values, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
