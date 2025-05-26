package com.myy.common.usercenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "login")
public class LoginConfig {

    /**
     * 登录错误5次锁定10分钟
     */
    private boolean lock;
    /**
     * 同一个用户只能在一个浏览器中登录
     */
    private boolean only;
}
