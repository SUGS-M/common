package com.myy.common.usercenter.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SessionUser implements Serializable {

    /**
     * 用户id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工号
     */
    private String jobNo;
    /**
     * 是否需要修改密码
     */
    private boolean passwordChange = false;
    /**
     * 密码修改原因
     */
    private String passwordMessage;
    /**
     * 登录凭证
     */
    private String token;
    /**
     * 令牌创建时间
     */
    private LocalDateTime creationTime;
    /**
     * 人力资源状态
     * 0.未审核 1.审核通过 2.驳回
     * 空值为没有人力资源数据
     */
    private String hrStatus;
}
