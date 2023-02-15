package com.ican.strategy;

/**
 * 第三方登录策略
 *
 * @author ican
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     *
     * @param data data
     * @return {@link String} Token
     */
    String login(String data);
}
