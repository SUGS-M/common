package com.myy.common.usercenter.user.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 用户表  VO
 *
 * @author myy
 * @date 2025-05-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class userVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 用户名 */
    @NotBlank
    private String username;
    /** 加密后的用户密码 */
    @NotBlank
    private String password;
    /** 密码加密盐值 */
    private String salt;
    /** 邮箱 */
    private String email;
    /** 手机号 */
    private String mobile;
    /** 用户头像URL */
    private String avatar;
    /** 用户昵称 */
    private String nickname;
    /** 性别(0-未知 1-男 2-女) */
    private int gender;
    /** 状态(0-禁用 1-正常) */
    @NotNull
    private int status;
    /** 标签（JSON格式存储） */
    private String tags;
    /** 最后登录时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastLoginTime;
    /** 最后登录IP */
    private String lastLoginIp;
}
