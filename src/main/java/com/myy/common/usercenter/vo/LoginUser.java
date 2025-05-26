package com.myy.common.usercenter.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户
 */
@Data
public class LoginUser implements Serializable {

    /**
     * 用户名
     */
    @NotBlank
    private String username;
    /**
     * 密码
     */
    @NotBlank
    private String password;
}
