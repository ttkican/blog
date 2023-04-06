package com.ican.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.ican.config.properties.QqProperties;
import com.ican.enums.LoginTypeEnum;
import com.ican.exception.ServiceException;
import com.ican.model.dto.QqLoginDTO;
import com.ican.model.vo.QqTokenVO;
import com.ican.model.vo.QqUserInfoVO;
import com.ican.model.vo.SocialTokenVO;
import com.ican.model.vo.SocialUserInfoVO;
import com.ican.utils.CommonUtils;
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
    public SocialTokenVO getSocialToken(String data) {
        QqLoginDTO qqLoginVO = JSON.parseObject(data, QqLoginDTO.class);
        // 校验QQ token信息
        checkQqToken(qqLoginVO);
        // 返回token信息
        return SocialTokenVO.builder()
                .openId(qqLoginVO.getOpenId())
                .accessToken(qqLoginVO.getAccessToken())
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
                .nickname(Objects.requireNonNull(qqUserInfo).getNickname())
                .avatar(qqUserInfo.getFigureurl_qq_1())
                .build();
    }

    /**
     * 校验qq token信息
     *
     * @param qqLogin qq登录信息
     */
    private void checkQqToken(QqLoginDTO qqLogin) {
        // 根据token获取qq openId信息
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(ACCESS_TOKEN, qqLogin.getAccessToken());
        try {
            String result = restTemplate.getForObject(qqProperties.getCheckTokenUrl(), String.class, qqData);
            QqTokenVO qqTokenVO = JSON.parseObject(CommonUtils.getBracketsContent(Objects.requireNonNull(result)), QqTokenVO.class);
            // 判断openId是否一致
            if (!qqLogin.getOpenId().equals(qqTokenVO.getOpenid())) {
                throw new ServiceException("qq登录错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("qq登录错误");
        }
    }
}