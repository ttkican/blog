package com.ican.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.ican.config.properties.GithubProperties;
import com.ican.exception.ServiceException;
import com.ican.model.dto.GitDTO;
import com.ican.model.vo.GitUserInfoVO;
import com.ican.model.vo.SocialTokenVO;
import com.ican.model.vo.SocialUserInfoVO;
import com.ican.model.vo.TokenVO;
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

import static com.ican.constant.SocialLoginConstant.*;
import static com.ican.enums.LoginTypeEnum.GITHUB;

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
    public SocialTokenVO getSocialToken(String data) {
        GitDTO gitDTO = JSON.parseObject(data, GitDTO.class);
        // 获取Github的Token
        TokenVO githubToken = getGithubToken(gitDTO.getCode());
        // 返回Github的Token信息
        return SocialTokenVO.builder()
                .accessToken(githubToken.getAccess_token())
                .loginType(GITHUB.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoVO getSocialUserInfo(SocialTokenVO socialToken) {
        // 请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + socialToken.getAccessToken());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(null, headers);
        // Gitee用户信息
        GitUserInfoVO gitUserInfoVO = restTemplate.exchange(githubProperties.getUserInfoUrl(),
                HttpMethod.GET,
                requestEntity,
                GitUserInfoVO.class).getBody();
        // 返回用户信息
        return SocialUserInfoVO.builder()
                .avatar(Objects.requireNonNull(gitUserInfoVO).getAvatar_url())
                .id(gitUserInfoVO.getId())
                .nickname(gitUserInfoVO.getLogin()).build();
    }

    /**
     * 获取Github的Token
     *
     * @param code 第三方code
     * @return {@link TokenVO} Github的Token
     */
    private TokenVO getGithubToken(String code) {
        // 根据code换取accessToken
        MultiValueMap<String, String> githubData = new LinkedMultiValueMap<>();
        // Github的Token请求参数
        githubData.add(CLIENT_ID, githubProperties.getClientId());
        githubData.add(CLIENT_SECRET, githubProperties.getClientSecret());
        githubData.add(REDIRECT_URI, githubProperties.getRedirectUrl());
        githubData.add(CODE, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(githubData, headers);
        try {
            return restTemplate.exchange(githubProperties.getAccessTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    TokenVO.class).getBody();
        } catch (Exception e) {
            throw new ServiceException("Github登录错误");
        }
    }
}