package com.ican.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ican.constant.CommonConstant;
import com.ican.constant.RedisConstant;
import com.ican.entity.SiteConfig;
import com.ican.entity.User;
import com.ican.entity.UserRole;
import com.ican.enums.LoginTypeEnum;
import com.ican.enums.RoleEnum;
import com.ican.mapper.UserMapper;
import com.ican.mapper.UserRoleMapper;
import com.ican.model.dto.MailDTO;
import com.ican.model.vo.request.CodeReq;
import com.ican.model.vo.request.LoginReq;
import com.ican.model.vo.request.RegisterReq;
import com.ican.strategy.context.SocialLoginStrategyContext;
import com.ican.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务
 *
 * @author ican
 * @date 2022/12/04 11:24
 **/
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public String login(LoginReq login) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .eq(User::getUsername, login.getUsername())
                .eq(User::getPassword, SecurityUtils.sha256Encrypt(login.getPassword())));
        Assert.notNull(user, "用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    public void sendCode(String username) {
        Assert.isTrue(Validator.isEmail(username), "请输入正确的邮箱！");
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
        String code = randomGenerator.generate();
        MailDTO mailDTO = MailDTO.builder()
                .toEmail(username)
                .subject(CommonConstant.CAPTCHA)
                .content("您的验证码为 " + code + " 有效期为" + RedisConstant.CODE_EXPIRE_TIME + "分钟")
                .build();
        // 验证码存入消息队列
//        rabbitTemplate.convertAndSend(MqConstant.EMAIL_EXCHANGE, MqConstant.EMAIL_SIMPLE_KEY, mailDTO);
        CompletableFuture.runAsync(() -> emailService.sendSimpleMail(mailDTO), threadPoolTaskExecutor);
        // 验证码存入redis
        redisService.setObject(RedisConstant.CODE_KEY + username, code, RedisConstant.CODE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReq register) {
        verifyCode(register.getUsername(), register.getCode());
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, register.getUsername()));
        Assert.isNull(user, "邮箱已注册！");
        SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING);
        // 添加用户
        User newUser = User.builder()
                .username(register.getUsername())
                .email(register.getUsername())
                .nickname(CommonConstant.USER_NICKNAME + IdWorker.getId())
                .avatar(siteConfig.getUserAvatar())
                .password(SecurityUtils.sha256Encrypt(register.getPassword()))
                .loginType(LoginTypeEnum.EMAIL.getLoginType())
                .isDisable(CommonConstant.FALSE)
                .build();
        userMapper.insert(newUser);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(newUser.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    public String giteeLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.GITEE);
    }

    @Transactional(rollbackFor = Exception.class)
    public String githubLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.GITHUB);
    }

    @Transactional(rollbackFor = Exception.class)
    public String qqLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.QQ);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisService.getObject(RedisConstant.CODE_KEY + username);
        Assert.notBlank(sysCode, "验证码未发送或已过期！");
        Assert.isTrue(sysCode.equals(code), "验证码错误，请重新输入！");
    }

}