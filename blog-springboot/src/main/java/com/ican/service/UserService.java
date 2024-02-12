package com.ican.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.constant.CommonConstant;
import com.ican.constant.RedisConstant;
import com.ican.entity.User;
import com.ican.entity.UserRole;
import com.ican.enums.FilePathEnum;
import com.ican.mapper.MenuMapper;
import com.ican.mapper.RoleMapper;
import com.ican.mapper.UserMapper;
import com.ican.mapper.UserRoleMapper;
import com.ican.model.vo.response.MetaResp;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.OnlineUserQuery;
import com.ican.model.vo.query.UserQuery;
import com.ican.model.vo.request.*;
import com.ican.model.vo.response.*;
import com.ican.strategy.context.UploadStrategyContext;
import com.ican.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业务服务
 *
 * @author ican
 * @date 2022/12/05 15:32
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    public UserBackInfoResp getUserBackInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        // 查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar).eq(User::getId, userId));
        // 查询用户角色
        List<String> roleIdList = StpUtil.getRoleList();
        // 用户权限列表
        List<String> permissionList = StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return UserBackInfoResp.builder()
                .id(userId)
                .avatar(user.getAvatar())
                .roleList(roleIdList)
                .permissionList(permissionList)
                .build();
    }

    public UserInfoResp getUserInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getNickname, User::getAvatar, User::getUsername, User::getWebSite,
                        User::getIntro, User::getEmail, User::getLoginType)
                .eq(User::getId, userId));
        Set<Object> articleLikeSet = redisService.getSet(RedisConstant.USER_ARTICLE_LIKE + userId);
        Set<Object> commentLikeSet = redisService.getSet(RedisConstant.USER_COMMENT_LIKE + userId);
        Set<Object> talkLikeSet = redisService.getSet(RedisConstant.USER_TALK_LIKE + userId);
        return UserInfoResp
                .builder()
                .id(userId)
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .webSite(user.getWebSite())
                .intro(user.getIntro())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .talkLikeSet(talkLikeSet)
                .loginType(user.getLoginType())
                .build();
    }

    public List<RouterResp> getUserMenu() {
        // 查询用户菜单
        List<UserMenuResp> userMenuRespList = menuMapper.selectMenuByUserId(StpUtil.getLoginIdAsInt());
        // 递归生成路由,parentId为0
        return recurRoutes(CommonConstant.PARENT_ID, userMenuRespList);
    }

    public PageResult<UserBackResp> listUserBackVO(UserQuery userQuery) {
        // 查询后台用户数量
        Long count = userMapper.selectUserCount(userQuery);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台用户列表
        List<UserBackResp> userBackRespList = userMapper.selectUserList(userQuery);
        return new PageResult<>(userBackRespList, count);
    }

    public List<UserRoleResp> listUserRoleDTO() {
        // 查询角色列表
        return roleMapper.selectUserRoleList();
    }

    public void updateUser(UserRoleReq user) {
        // 更新用户信息
        User newUser = User.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
        baseMapper.updateById(newUser);
        // 删除用户角色
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        // 重新添加用户角色
        userRoleMapper.insertUserRole(user.getId(), user.getRoleIdList());
        // 删除Redis缓存中的角色
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(user.getId(), false);
        Optional.ofNullable(sessionByLoginId).ifPresent(saSession -> saSession.delete("Role_List"));
    }

    public void updateUserStatus(DisableReq disable) {
        // 更新用户状态
        User newUser = User.builder()
                .id(disable.getId())
                .isDisable(disable.getIsDisable())
                .build();
        userMapper.updateById(newUser);
        if (disable.getIsDisable().equals(CommonConstant.TRUE)) {
            // 先踢下线
            StpUtil.logout(disable.getId());
            // 再封禁账号
            StpUtil.disable(disable.getId(), 86400);
        } else {
            // 解除封禁
            StpUtil.untieDisable(disable.getId());
        }
    }

    public PageResult<OnlineUserResp> listOnlineUser(OnlineUserQuery onlineUserQuery) {
        // 查询所有会话token
        List<String> tokenList = StpUtil.searchTokenSessionId("", 0, -1, false);
        List<OnlineUserResp> onlineUserRespList = tokenList.stream()
                .map(token -> {
                    // 获取tokenSession
                    SaSession sessionBySessionId = StpUtil.getSessionBySessionId(token);
                    return (OnlineUserResp) sessionBySessionId.get(CommonConstant.ONLINE_USER);
                })
                .filter(onlineUserResp -> StringUtils.isEmpty(onlineUserQuery.getKeyword()) || onlineUserResp.getNickname().contains(onlineUserQuery.getKeyword()))
                .sorted(Comparator.comparing(OnlineUserResp::getLoginTime).reversed())
                .collect(Collectors.toList());
        // 执行分页
        int fromIndex = onlineUserQuery.getCurrent();
        int size = onlineUserQuery.getSize();
        int toIndex = onlineUserRespList.size() - fromIndex > size ? fromIndex + size : onlineUserRespList.size();
        List<OnlineUserResp> userOnlineList = onlineUserRespList.subList(fromIndex, toIndex);
        return new PageResult<>(userOnlineList, (long) onlineUserRespList.size());
    }

    public void kickOutUser(String token) {
        StpUtil.logoutByTokenValue(token);
    }

    public void updateAdminPassword(PasswordReq password) {
        Integer userId = StpUtil.getLoginIdAsInt();
        // 查询旧密码是否正确
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, userId));
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(SecurityUtils.checkPw(user.getPassword(), password.getOldPassword()), "旧密码校验不通过!");
        // 正确则修改密码
        String newPassword = SecurityUtils.sha256Encrypt(password.getNewPassword());
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserEmail(EmailReq email) {
        verifyCode(email.getEmail(), email.getCode());
        User newUser = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .email(email.getEmail())
                .build();
        userMapper.updateById(newUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateUserAvatar(MultipartFile file) {
        // 头像上传
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        // 更新用户头像
        User newUser = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .avatar(avatar)
                .build();
        userMapper.updateById(newUser);
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfoReq userInfo) {
        User newUser = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .nickname(userInfo.getNickname())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .build();
        userMapper.updateById(newUser);
    }

    public void updatePassword(UserReq user) {
        verifyCode(user.getUsername(), user.getCode());
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, user.getUsername()));
        Assert.notNull(existUser, "邮箱尚未注册！");
        // 根据用户名修改密码
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, SecurityUtils.sha256Encrypt(user.getPassword()))
                .eq(User::getUsername, user.getUsername()));
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

    /**
     * 递归生成路由列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 路由列表
     */
    private List<RouterResp> recurRoutes(Integer parentId, List<UserMenuResp> menuList) {
        List<RouterResp> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouterResp routeVO = new RouterResp();
                    routeVO.setName(menu.getMenuName());
                    routeVO.setPath(getRouterPath(menu));
                    routeVO.setComponent(getComponent(menu));
                    routeVO.setMeta(MetaResp.builder()
                            .title(menu.getMenuName())
                            .icon(menu.getIcon())
                            .hidden(menu.getIsHidden().equals(CommonConstant.TRUE))
                            .build());
                    if (menu.getMenuType().equals(CommonConstant.TYPE_DIR)) {
                        List<RouterResp> children = recurRoutes(menu.getId(), menuList);
                        if (CollectionUtil.isNotEmpty(children)) {
                            routeVO.setAlwaysShow(true);
                            routeVO.setRedirect("noRedirect");
                        }
                        routeVO.setChildren(children);
                    } else if (isMenuFrame(menu)) {
                        routeVO.setMeta(null);
                        List<RouterResp> childrenList = new ArrayList<>();
                        RouterResp children = new RouterResp();
                        children.setName(menu.getMenuName());
                        children.setPath(menu.getPath());
                        children.setComponent(menu.getComponent());
                        children.setMeta(MetaResp.builder()
                                .title(menu.getMenuName())
                                .icon(menu.getIcon())
                                .hidden(menu.getIsHidden().equals(CommonConstant.TRUE))
                                .build());
                        childrenList.add(children);
                        routeVO.setChildren(childrenList);
                    }
                    list.add(routeVO);
                }));
        return list;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(UserMenuResp menu) {
        String routerPath = menu.getPath();
        // 一级目录
        if (menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_DIR.equals(menu.getMenuType())) {
            routerPath = "/" + menu.getPath();
        }
        // 一级菜单
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(UserMenuResp menu) {
        String component = CommonConstant.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = CommonConstant.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(UserMenuResp menu) {
        return menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_MENU.equals(menu.getMenuType());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(UserMenuResp menu) {
        return !menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_DIR.equals(menu.getMenuType());
    }

}