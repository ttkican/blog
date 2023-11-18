package com.ican.strategy.impl;

import com.ican.config.properties.GiteeProperties;
import com.ican.exception.ServiceException;
import com.ican.model.dto.GitUserInfoDTO;
import com.ican.model.dto.SocialTokenDTO;
import com.ican.model.dto.SocialUserInfoDTO;
import com.ican.model.dto.TokenDTO;
import com.ican.model.vo.request.CodeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ican.constant.SocialLoginConstant.*;
import static com.ican.enums.LoginTypeEnum.GITEE;

/**
 * Gitee登录策略
 *
 * @author ican
 */
@Service("giteeLoginStrategyImpl")
public class GiteeLoginStrategyImpl extends AbstractLoginStrategyImpl {

    @Autowired
    private GiteeProperties giteeProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(CodeReq codeReq) {
        // 获取Gitee的Token
        TokenDTO giteeToken = getGiteeToken(codeReq.getCode());
        // 返回Gitee的Token信息
        return SocialTokenDTO.builder()
                .accessToken(giteeToken.getAccess_token())
                .loginType(GITEE.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialToken) {
        Map<String, String> dataMap = new HashMap<>(1);
        // 请求参数
        dataMap.put(ACCESS_TOKEN, socialToken.getAccessToken());
        // Gitee用户信息
        GitUserInfoDTO gitUserInfoDTO = restTemplate.getForObject(giteeProperties.getUserInfoUrl(), GitUserInfoDTO.class, dataMap);
        // 返回用户信息
        return SocialUserInfoDTO.builder()
                .avatar(Objects.requireNonNull(gitUserInfoDTO).getAvatar_url())
                .id(gitUserInfoDTO.getId())
                .nickname(gitUserInfoDTO.getName()).build();
    }

    /**
     * 获取Gitee的Token
     *
     * @param code 第三方code
     * @return {@link TokenDTO} Gitee的Token
     */
    private TokenDTO getGiteeToken(String code) {
        // 根据code换取accessToken
        MultiValueMap<String, String> giteeData = new LinkedMultiValueMap<>();
        // Gitee的Token请求参数
        giteeData.add(CLIENT_ID, giteeProperties.getClientId());
        giteeData.add(CLIENT_SECRET, giteeProperties.getClientSecret());
        giteeData.add(GRANT_TYPE, giteeProperties.getGrantType());
        giteeData.add(REDIRECT_URI, giteeProperties.getRedirectUrl());
        giteeData.add(CODE, code);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(giteeData, null);
        try {
            return restTemplate.exchange(giteeProperties.getAccessTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    TokenDTO.class).getBody();
        } catch (Exception e) {
            throw new ServiceException("Gitee登录错误");
        }
    }
}
