package com.ican.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.constant.CommonConstant;
import com.ican.entity.Role;
import com.ican.entity.UserRole;
import com.ican.mapper.RoleMapper;
import com.ican.mapper.RoleMenuMapper;
import com.ican.mapper.UserRoleMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.RoleQuery;
import com.ican.model.vo.request.RoleReq;
import com.ican.model.vo.request.RoleStatusReq;
import com.ican.model.vo.response.RoleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 角色服务
 *
 * @author ican
 * @date 2022/12/07 09:38
 **/
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    public PageResult<RoleResp> listRoleVO(RoleQuery roleQuery) {
        // 查询角色数量
        Long count = roleMapper.selectBackRoleCount(roleQuery);
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询角色列表
        List<RoleResp> roleRespList = roleMapper.selectBackRoleList(roleQuery);
        return new PageResult<>(roleRespList, count);
    }

    public void addRole(RoleReq role) {
        // 角色名是否存在
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, role.getRoleName()));
        Assert.isNull(existRole, role.getRoleName() + "角色名已存在");
        // 添加新角色
        Role newRole = Role.builder().roleName(role.getRoleName()).roleDesc(role.getRoleDesc()).isDisable(role.getIsDisable()).build();
        baseMapper.insert(newRole);
        // 添加角色菜单权限
        roleMenuMapper.insertRoleMenu(newRole.getId(), role.getMenuIdList());
    }

    public void deleteRole(List<String> roleIdList) {
        Assert.isFalse(roleIdList.contains(CommonConstant.ADMIN), "不允许删除管理员角色");
        // 角色是否已分配
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, roleIdList));
        Assert.isFalse(count > 0, "角色已分配");
        // 删除角色
        roleMapper.deleteBatchIds(roleIdList);
        // 批量删除角色关联的菜单权限
        roleMenuMapper.deleteRoleMenu(roleIdList);
        // 删除Redis缓存中的菜单权限
        roleIdList.forEach(roleId -> {
            SaSession sessionById = SaSessionCustomUtil.getSessionById("role-" + roleId, false);
            Optional.ofNullable(sessionById).ifPresent(saSession -> saSession.delete("Permission_List"));
        });
    }

    public void updateRole(RoleReq role) {
        Assert.isFalse(role.getId().equals(CommonConstant.ADMIN) && role.getIsDisable().equals(CommonConstant.TRUE), "不允许禁用管理员角色");
        // 角色名是否存在
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>().select(Role::getId).eq(Role::getRoleName, role.getRoleName()));
        Assert.isFalse(Objects.nonNull(existRole) && !existRole.getId().equals(role.getId()),
                role.getRoleName() + "角色名已存在");
        // 更新角色信息
        Role newRole = Role.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .roleDesc(role.getRoleDesc()).
                isDisable(role.getIsDisable())
                .build();
        roleMapper.updateById(newRole);
        // 先删除角色关联的菜单权限
        roleMenuMapper.deleteRoleMenuByRoleId(newRole.getId());
        // 再添加角色菜单权限
        roleMenuMapper.insertRoleMenu(newRole.getId(), role.getMenuIdList());
        // 删除Redis缓存中的菜单权限
        SaSession sessionById = SaSessionCustomUtil.getSessionById("role-" + newRole.getId(), false);
        Optional.ofNullable(sessionById).ifPresent(saSession -> saSession.delete("Permission_List"));
    }

    public void updateRoleStatus(RoleStatusReq roleStatus) {
        Assert.isFalse(roleStatus.getId().equals(CommonConstant.ADMIN), "不允许禁用管理员角色");
        // 更新角色状态
        Role newRole = Role.builder()
                .id(roleStatus.getId())
                .isDisable(roleStatus.getIsDisable())
                .build();
        roleMapper.updateById(newRole);
    }

    public List<Integer> listRoleMenuTree(String roleId) {
        return roleMenuMapper.selectMenuByRoleId(roleId);
    }

}