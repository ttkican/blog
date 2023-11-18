package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.model.vo.Result;
import com.ican.model.vo.request.EmailReq;
import com.ican.model.vo.request.UserInfoReq;
import com.ican.model.vo.request.UserReq;
import com.ican.model.vo.response.UserInfoResp;
import com.ican.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息控制器
 *
 * @author ican
 **/
@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserService userService;

    /**
     * 获取登录用户信息
     *
     * @return {@link UserInfoResp} 用户信息
     */
    @SaCheckLogin
    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/user/getUserInfo")
    public Result<UserInfoResp> getUserInfo() {
        return Result.success(userService.getUserInfo());
    }

    /**
     * 修改用户邮箱
     *
     * @param email 邮箱信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户邮箱")
    @SaCheckPermission(value = "user:email:update")
    @PutMapping("/user/email")
    public Result<?> updateUserEmail(@Validated @RequestBody EmailReq email) {
        userService.updateUserEmail(email);
        return Result.success();
    }

    /**
     * 修改用户头像
     *
     * @param file 文件
     * @return {@link Result<String>} 头像地址
     */
    @ApiOperation(value = "修改用户头像")
    @SaCheckPermission(value = "user:avatar:update")
    @PostMapping("/user/avatar")
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        return Result.success(userService.updateUserAvatar(file));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户信息")
    @SaCheckPermission(value = "user:info:update")
    @PutMapping("/user/info")
    public Result<?> updateUserInfo(@Validated @RequestBody UserInfoReq userInfo) {
        userService.updateUserInfo(userInfo);
        return Result.success();
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户密码")
    @PutMapping("/user/password")
    public Result<?> updatePassword(@Validated @RequestBody UserReq user) {
        userService.updatePassword(user);
        return Result.success();
    }

}