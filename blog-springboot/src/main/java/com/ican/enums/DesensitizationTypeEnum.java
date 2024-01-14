package com.ican.enums;

/**
 * 脱敏策略枚举
 *
 * @author ican
 * @date 2024/01/14 16:04
 **/
public enum DesensitizationTypeEnum {

    /**
     * 自定义
     */
    MY_RULE,

    /**
     * 用户id
     */
    USER_ID,

    /**
     * 中文名
     */
    CHINESE_NAME,

    /**
     * 身份证号
     */
    ID_CARD,

    /**
     * 座机号
     */
    FIXED_PHONE,

    /**
     * 手机号
     */
    MOBILE_PHONE,

    /**
     * IP4
     */
    IP4,

    /**
     * 地址
     */
    ADDRESS,

    /**
     * 电子邮件
     */
    EMAIL,

    /**
     * 密码
     */
    PASSWORD,

    /**
     * 车牌
     */
    CAR_LICENSE,

    /**
     * 银行卡
     */
    BANK_CARD
}