package com.ican.service;

import com.ican.model.dto.GitDTO;
import com.ican.model.dto.LoginDTO;
import com.ican.model.dto.RegisterDTO;

/**
 * 登录业务接口
 *
 * @author ican
 * @date 2022/12/04 11:16
 **/
public interface LoginService {

    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return token
     */
    String login(LoginDTO login);

    /**
     * 发送验证码
     *
     * @param username 用户名
     */
    void sendCode(String username);

    /**
     * 用户注册
     *
     * @param register 注册信息
     */
    void register(RegisterDTO register);

    /**
     * Gitee登录
     *
     * @param data 第三方code
     * @return Token
     */
    String giteeLogin(GitDTO data);

    /**
     * Github登录
     *
     * @param data 第三方code
     * @return Token
     */
    String githubLogin(GitDTO data);
}
