package com.myy.common.usercenter.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Set;

public class UserUtil {


    /**
     * 获取当前登录用户ID
     * @return
     */
    public static String getUserId() {
        return "开发ing";
    }

    /**
     * 清理指定前缀的缓存
     */
    public static void clearCache(String prefix, String userId) {
        Set<String> keys = RedisUtils.keys(StrUtil.format("{}:{}", prefix, userId));
        if (CollUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                RedisUtils.clear(key);
            }
        }
    }
}
