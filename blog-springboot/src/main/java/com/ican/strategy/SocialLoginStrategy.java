package com.ican.strategy;

import com.ican.model.dto.CodeDTO;

/**
 * 第三方登录策略
 *
 * @author ican
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     *
     * @param data 第三方code
     * @return {@link String} Token
     */
    String login(CodeDTO data);
}
