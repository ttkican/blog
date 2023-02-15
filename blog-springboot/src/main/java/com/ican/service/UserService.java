package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.User;
import com.ican.model.dto.*;
import com.ican.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author ican
 * @date 2022/12/05 15:29
 **/
public interface UserService extends IService<User> {

    /**
     * 获取后台登录用户信息
     *
     * @return 后台用户信息
     */
    UserBackInfoVO getUserBackInfo();

    /**
     * 获取登录用户信息
     *
     * @return 用户信息
     */
    UserInfoVO getUserInfo();

    /**
     * 获取登录用户菜单
     *
     * @return 登录用户菜单
     */
    List<RouterVO> getUserMenu();

    /**
     * 查看后台用户列表
     *
     * @param condition 条件
     * @return 用户列表
     */
    PageResult<UserBackVO> listUserBackVO(ConditionDTO condition);

    /**
     * 查看用户角色选项
     *
     * @return 用户角色选项
     */
    List<UserRoleVO> listUserRoleDTO();

    /**
     * 修改用户
     *
     * @param user 用户信息
     */
    void updateUser(UserRoleDTO user);

    /**
     * 修改用户状态
     *
     * @param disable 禁用信息
     */
    void updateUserStatus(DisableDTO disable);

    /**
     * 查看在线用户列表
     *
     * @param condition 条件
     * @return 在线用户列表
     */
    PageResult<OnlineVO> listOnlineUser(ConditionDTO condition);

    /**
     * 下线用户
     *
     * @param token 在线token
     */
    void kickOutUser(String token);

    /**
     * 修改管理员密码
     *
     * @param password 密码
     */
    void updateAdminPassword(PasswordDTO password);

    /**
     * 修改用户邮箱
     *
     * @param email 邮箱信息
     */
    void updateUserEmail(EmailDTO email);

    /**
     * 修改用户头像
     *
     * @param file 头像
     * @return 头像链接
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     */
    void updateUserInfo(UserInfoDTO userInfo);

    /**
     * 修改用户密码
     *
     * @param user 用户密码
     */
    void updatePassword(UserDTO user);
}
