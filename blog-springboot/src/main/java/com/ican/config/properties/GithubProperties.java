package com.ican.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Github配置属性
 *
 * @author ican
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oauth.github")
public class GithubProperties {

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;

    /**
     * Github回调域名
     */
    private String redirectUrl;

    /**
     * Github访问令牌地址
     */
    private String accessTokenUrl;

    /**
     * Github用户信息地址
     */
    private String userInfoUrl;
}
