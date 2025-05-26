package com.myy.common.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myy.common.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 用户表实体 对应表名sys_user
 *
 * @author myy
 * @date 2025-05-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 用户名 */
    @TableField("username")
    private String username;
    /** 加密后的用户密码 */
    @TableField("password")
    private String password;
    /** 密码加密盐值 */
    @TableField("salt")
    private String salt;
    /** 邮箱 */
    @TableField("email")
    private String email;
    /** 手机号 */
    @TableField("mobile")
    private String mobile;
    /** 用户头像URL */
    @TableField("avatar")
    private String avatar;
    /** 用户昵称 */
    @TableField("nickname")
    private String nickname;
    /** 性别(0-未知 1-男 2-女) */
    @TableField("gender")
    private int gender;
    /** 状态(0-禁用 1-正常) */
    @TableField("status")
    private int status;
    /** 标签（JSON格式存储） */
    @TableField("tags")
    private String tags;
    /** 最后登录时间 */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /** 最后登录IP */
    @TableField("last_login_ip")
    private String lastLoginIp;
}
