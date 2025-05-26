package com.myy.common.usercenter.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtils {

    public static final String PREFIX = "SystemPrefix" + ":"; //ToDo

    private static RedisTemplate<String, Object> getRedisTemplate() {
        return SpringUtil.getBean("redisTemplate");
    }

    private static String getKey(String key) {
        return PREFIX + key;
    }

    /**
     * 通过key获取缓存,默认加入key前缀
     */
    public static <T> T get(String key) {
        return get(key, true);
    }

    /**
     * 通过key获取缓存
     *
     * @param key    指定缓存的key
     * @param prefix 是否自动加入前缀
     */
    public static <T> T get(String key, boolean prefix) {
        if (prefix) {
            return (T) getRedisTemplate().opsForValue().get(getKey(key));
        } else {
            return (T) getRedisTemplate().opsForValue().get(key);
        }
    }

    /**
     * 设置key的值为value
     */
    public static void set(String key, Object value) {
        getRedisTemplate().opsForValue().set(getKey(key), value);
    }

    /**
     * 设置key的值为value
     */
    public static void set(String key, Object value, boolean prefix) {
        if (prefix) {
            getRedisTemplate().opsForValue().set(getKey(key), value);
        } else {
            getRedisTemplate().opsForValue().set(key, value);
        }
    }

    /**
     * 设置key的值为value,并设置超时时间
     */
    public static void set(String key, Object value, int timeout, TimeUnit unit) {
        getRedisTemplate().opsForValue().set(getKey(key), value, timeout, unit);
    }

    /**
     * 重新设置key对应的值，如果存在返回false，否则返回true
     */
    public static boolean setNX(String key, Object value) {
        return Boolean.TRUE.equals(getRedisTemplate().opsForValue().setIfAbsent(getKey(key), value));
    }

    /**
     * 将value加入key指定的Set集合中
     */
    public static void addSet(String key, Object... values) {
        getRedisTemplate().opsForSet().add(key, values);
    }

    /**
     * 判断key对应的集合中是否存在value
     */
    public static boolean inSet(String key, Object value) {
        return Boolean.TRUE.equals(getRedisTemplate().opsForSet().isMember(key, value));
    }

    /**
     * 向Redis中Hash增加值
     */
    public static void putHash(String key, Object hashKey, Object value) {
        if (ObjectUtil.isNotEmpty(hashKey) && ObjectUtil.isNotEmpty(value)) {
            getRedisTemplate().opsForHash().put(key, hashKey, value);
        }
    }

    /**
     * 向Redis中设置Hash值
     */
    public static void setHash(String key, Map map) {
        getRedisTemplate().opsForHash().putAll(key, map);
    }

    /**
     * 获取Redis中hash中对应value
     */
    public static Object getHash(String key, String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }
        return getRedisTemplate().opsForHash().get(key, name);
    }

    /**
     * 向Redis Hash中value+1
     */
    public static void addHash(String key, Object hashKey) {
        if (ObjectUtil.isNotEmpty(hashKey) && ObjectUtil.isNotEmpty(key)) {
            getRedisTemplate().opsForHash().increment(key, hashKey, 1);
        }
    }

    /**
     * 获取Redis中的Hash对象
     */
    public static Map<Object, Object> getHash(String key) {
        if (ObjectUtil.isNotEmpty(key)) {
            return getRedisTemplate().opsForHash().entries(key);
        }
        return new HashMap<>();
    }

    /**
     * 将value加入key指定的List集合中
     */
    public static void addList(String key, Object... values) {
        getRedisTemplate().opsForList().leftPushAll(getKey(key), values);
    }

    /**
     * 通过key获取对应的List
     */
    public static List<Object> getList(String key) {
        return getRedisTemplate().opsForList().range(getKey(key), 0, -1);
    }

    /**
     * 设置key的设置超时时间
     */
    public static void expire(String key, int timeout, TimeUnit unit) {
        getRedisTemplate().expire(key, timeout, unit);
    }

    /**
     * 将旧的key设置为value，并且返回旧的key
     */
    public static <T> T getSet(String key, Object value) {
        Object obj = getRedisTemplate().opsForValue().getAndSet(getKey(key), value);
        return (T) obj;
    }

    /**
     * 获取自增数字，如果key不存在返回空
     */
    public static Long increment(String key) {
        try {
            return getRedisTemplate().opsForValue().increment(getKey(key), 1);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * 获取所有缓存Key
     */
    public static Set<String> keys() {
        return getRedisTemplate().keys(getKey("*"));
    }
     /**
     * 获取所有缓存Key
     */
    public static Set<String> keys(String key) {
        return getRedisTemplate().keys(StrUtil.isNotBlank(key) ? key + ":*" : getKey("*"));
    }

    /**
     * 清理所有缓存
     */
    public static void clear() {
        getRedisTemplate().delete(keys());
    }

    /**
     * 清理以key开头的缓存
     */
    public static void clear(String key) {
        clear(key, true);
    }

    /**
     * 清理以key开头的缓存，prefix用来判断清理的key是否加入前缀
     */
    public static void clear(String key, boolean prefix) {
        Set<String> keys = keys();
        key = prefix ? getKey(key) : key;
        for (String k : keys) {
            if (k.startsWith(key)) {
                if (log.isDebugEnabled()) {
                    log.debug(StrUtil.format("{}清理Redis缓存", k));
                }
                getRedisTemplate().delete(k);
            }
        }
    }

    /**
     * 获取锁成功返回ture，超时返回false
     */
    public static Boolean lock(String key) throws InterruptedException {
        String lockKey = "lock:" + key;
        int timeout = 10 * 1000;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + 600 * 1000 + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            if (setNX(lockKey, expiresStr)) {
                return true;
            }
            // redis里key的时间
            String currentValue = get(lockKey);
            // 判断锁是否已经过期，过期则重新设置并获取
            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                // 设置锁并返回旧值
                String oldValue = getSet(lockKey, expiresStr);
                // 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
                if (oldValue != null && oldValue.equals(currentValue)) {
                    return true;
                }
            }
            timeout -= 100;
            // 延时
            Thread.sleep(100);
        }
        return false;
    }

    /**
     * 释放获取到的锁
     */
    public static void unlock(String key) {
        String lockKey = getKey("lock:" + key);
        getRedisTemplate().delete(lockKey);
    }

}

