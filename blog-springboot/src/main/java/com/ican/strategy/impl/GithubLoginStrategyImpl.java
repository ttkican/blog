package com.ican.strategy.impl;

import com.ican.config.properties.GithubProperties;
import com.ican.constant.SocialLoginConstant;
import com.ican.enums.LoginTypeEnum;
import com.ican.exception.ServiceException;
import com.ican.model.dto.GitUserInfoDTO;
import com.ican.model.dto.SocialTokenDTO;
import com.ican.model.dto.SocialUserInfoDTO;
import com.ican.model.dto.TokenDTO;
import com.ican.model.vo.request.CodeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Github登录策略
 *
 * @author ican
 **/
@Service("githubLoginStrategyImpl")
public class GithubLoginStrategyImpl extends AbstractLoginStrategyImpl {

    @Autowired
    private GithubProperties githubProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(CodeReq codeReq) {
        // 获取Github的Token
        TokenDTO githubToken = getGithubToken(codeReq.getCode());
        // 返回Github的Token信息
        return SocialTokenDTO.builder()
                .accessToken(githubToken.getAccess_token())
                .loginType(LoginTypeEnum.GITHUB.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialToken) {
        // 请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + socialToken.getAccessToken());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(null, headers);
        // Gitee用户信息
        GitUserInfoDTO gitUserInfoDTO = restTemplate.exchange(githubProperties.getUserInfoUrl(),
                HttpMethod.GET,
                requestEntity,
                GitUserInfoDTO.class).getBody();
        // 返回用户信息
        return SocialUserInfoDTO.builder()
                .avatar(Objects.requireNonNull(gitUserInfoDTO).getAvatar_url())
                .id(gitUserInfoDTO.getId())
                .nickname(gitUserInfoDTO.getLogin()).build();
    }

    /**
     * 获取Github的Token
     *
     * @param code 第三方code
     * @return {@link TokenDTO} Github的Token
     */
    private TokenDTO getGithubToken(String code) {
        // 根据code换取accessToken
        MultiValueMap<String, String> githubData = new LinkedMultiValueMap<>();
        // Github的Token请求参数
        githubData.add(SocialLoginConstant.CLIENT_ID, githubProperties.getClientId());
        githubData.add(SocialLoginConstant.CLIENT_SECRET, githubProperties.getClientSecret());
        githubData.add(SocialLoginConstant.REDIRECT_URI, githubProperties.getRedirectUrl());
        githubData.add(SocialLoginConstant.CODE, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(githubData, headers);
        try {
            return restTemplate.exchange(githubProperties.getAccessTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    TokenDTO.class).getBody();
        } catch (Exception e) {
            throw new ServiceException("Github登录错误");
        }
    }
}