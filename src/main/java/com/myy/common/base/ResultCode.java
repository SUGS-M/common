package com.myy.common.base;

import lombok.Getter;

/**
 * 返回码枚举
 */
@Getter
public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(500, "操作失败"),

    /**
     * 参数校验错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未登录或token已过期
     */
    UNAUTHORIZED(401, "未登录或token已过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(501, "数据库操作失败"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(502, "系统错误"),

    /**
     * 重复操作
     */
    DUPLICATE_OPERATION(503, "重复操作"),

    /**
     * 并发错误
     */
    CONCURRENT_OPERATION(555, "并发错误"),

    /**
     * 随申办配置缺失
     */
    MISSING_CONFIGURATION(504, "随申办配置缺失"),
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
