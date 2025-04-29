package com.myy.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.myy.common.base.BaseEntity;
import lombok.Data;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser extends BaseEntity {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密后的用户密码
     */
    private String password;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户头像URL
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别(0-未知 1-男 2-女)
     */
    private Integer gender;

    /**
     * 状态(0-禁用 1-正常)
     */
    private Integer status;

    /**
     * 标签（JSON格式存储）
     */
    private String tags;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;
}