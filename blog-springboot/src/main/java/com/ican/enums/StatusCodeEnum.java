package com.ican.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码枚举
 *
 * @author ican
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 参数错误
     */
    VALID_ERROR(400, "参数错误"),

    /**
     * 未登录
     */
    UNAUTHORIZED(402, "未登录"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(-1, "系统异常"),

    /**
     * 操作失败
     */
    FAIL(500, "操作失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回信息
     */
    private final String msg;

}