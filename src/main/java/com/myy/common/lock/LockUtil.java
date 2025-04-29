package com.myy.common.lock;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 分布式锁工具类
 */
@Slf4j
public class LockUtil {

    /**
     * 无等待锁住function中的执行
     */
    public static void execute(String key, LockFunction function) {
        execute(key, 0, 0, function);
    }

    /**
     * 锁住function中的执行
     *
     * @param key       唯一key
     * @param waitTime  最多等待时间(单位秒)
     * @param leaseTime 上锁后自动释放锁时间(单位秒)
     * @param function  函数内容
     */
    public static void execute(String key, long waitTime, long leaseTime, LockFunction function) {
        RLock lock = SpringUtil.getBean(RedissonClient.class).getLock(key);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (locked) {
                function.execute();
            } else {
                log.debug("分布式锁:{} 获取失败!", key);
            }
        } catch (InterruptedException e) {
            log.error("分布式锁:{} 获取异常!", key, e);
            Thread.currentThread().interrupt();
        } finally {
            if (locked) {
                try {
                    lock.unlock();
                    if (log.isDebugEnabled()) {
                        log.debug("分布式锁:{} 释放成功!", key);
                    }
                } catch (Exception e) {
                    log.error("分布式锁:{} 释放失败!", key, e);
                }
            }
        }
    }

    /**
     * 锁住function中的执行，带参数和返回值
     *
     * @param key       唯一key
     * @param t         参数
     * @param waitTime  最多等待时间(单位秒)
     * @param leaseTime 上锁后自动释放锁时间(单位秒)
     * @param function  函数内容
     */
    public static <T, R> R execute(String key, T t, long waitTime, long leaseTime, Function<T, R> function) {
        RLock lock = SpringUtil.getBean(RedissonClient.class).getLock(key);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (locked) {
                return function.apply(t);
            } else {
                log.debug("分布式锁:{} 获取失败!", key);
            }
        } catch (InterruptedException e) {
            log.error("分布式锁:{} 获取异常!", key, e);
            Thread.currentThread().interrupt();
        } finally {
            if (locked) {
                try {
                    lock.unlock();
                    if (log.isDebugEnabled()) {
                        log.debug("分布式锁:{} 释放成功!", key);
                    }
                } catch (Exception e) {
                    log.error("分布式锁:{} 释放失败!", key, e);
                }
            }
        }
        return null;
    }

    /**
     * 无等待锁住function中的执行，带参数和返回值
     */
    public static <T, R> R execute(String key, T t, Function<T, R> function) {
        return execute(key, t, 0, 0, function);
    }

}
