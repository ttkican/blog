package com.ican.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.ican.config.properties.QqProperties;
import com.ican.enums.LoginTypeEnum;
import com.ican.exception.ServiceException;
import com.ican.model.dto.CodeDTO;
import com.ican.model.dto.QqLoginDTO;
import com.ican.model.vo.QqUserInfoVO;
import com.ican.model.vo.SocialTokenVO;
import com.ican.model.vo.SocialUserInfoVO;
import com.ican.model.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ican.constant.SocialLoginConstant.*;

/**
 * Qq登录策略
 *
 * @author ican
 * @date 2023/04/06 18:43
 **/
@Service("qqLoginStrategyImpl")
public class QqLoginStrategyImpl extends AbstractLoginStrategyImpl {

    @Autowired
    private QqProperties qqProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenVO getSocialToken(CodeDTO codeDTO) {
        // 获取Qq的Token
        TokenVO qqToken = getQqToken(codeDTO.getCode());
        // 获取用户OpenId
        String userOpenId = getUserOpenId(qqToken.getAccess_token());
        // 返回token信息
        return SocialTokenVO.builder()
                .openId(userOpenId)
                .accessToken(qqToken.getAccess_token())
                .loginType(LoginTypeEnum.QQ.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoVO getSocialUserInfo(SocialTokenVO socialToken) {
        // 定义请求参数
        Map<String, String> formData = new HashMap<>(3);
        formData.put(QQ_OPEN_ID, socialToken.getOpenId());
        formData.put(ACCESS_TOKEN, socialToken.getAccessToken());
        formData.put(OAUTH_CONSUMER_KEY, qqProperties.getAppId());
        // 获取QQ返回的用户信息
        QqUserInfoVO qqUserInfo = JSON.parseObject(restTemplate.getForObject(qqProperties.getUserInfoUrl(), String.class, formData), QqUserInfoVO.class);
        // 返回用户信息
        return SocialUserInfoVO.builder()
                .id(socialToken.getOpenId())
                .nickname(Objects.requireNonNull(qqUserInfo).getNickname())
                .avatar(qqUserInfo.getFigureurl_qq_1())
                .build();
    }

    /**
     * 获取QQ的Token
     *
     * @param code 第三方code
     * @return {@link TokenVO} QQ的Token
     */
    private TokenVO getQqToken(String code) {
        // 根据code换取accessToken
        Map<String, String> qqData = new HashMap<>(5);
        // Gitee的Token请求参数
        qqData.put(GRANT_TYPE, qqProperties.getGrantType());
        qqData.put(CLIENT_ID, qqProperties.getAppId());
        qqData.put(CLIENT_SECRET, qqProperties.getAppKey());
        qqData.put(CODE, code);
        qqData.put(REDIRECT_URI, qqProperties.getRedirectUrl());
        try {
            return restTemplate.getForObject(qqProperties.getAccessTokenUrl(), TokenVO.class, qqData);
        } catch (Exception e) {
            throw new ServiceException("QQ登录错误");
        }
    }

    /**
     * 获取用户的OpenId
     *
     * @return 用户的OpenId
     */
    private String getUserOpenId(String accessToken) {
        Map<String, String> dataMap = new HashMap<>(1);
        // 请求参数
        dataMap.put(ACCESS_TOKEN, accessToken);
        // 返回用户OpenId
        QqLoginDTO qqLoginDTO = restTemplate.getForObject(qqProperties.getUserOpenIdUrl(), QqLoginDTO.class, dataMap);
        return qqLoginDTO.getOpenid();
    }
}