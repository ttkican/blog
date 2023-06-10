package com.ican.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * QQ配置属性
 *
 * @author ican
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oauth.qq")
public class QqProperties {

    /**
     * QQ appId
     */
    private String appId;

    /**
     * QQ clientSecret
     */
    private String appKey;

    /**
     * QQ登录类型
     */
    private String grantType;

    /**
     * QQ回调域名
     */
    private String redirectUrl;

    /**
     * QQ访问令牌地址
     */
    private String accessTokenUrl;

    /**
     * 用户OpenID获取地址
     */
    private String userOpenIdUrl;

    /**
     * QQ用户信息地址
     */
    private String userInfoUrl;

}
