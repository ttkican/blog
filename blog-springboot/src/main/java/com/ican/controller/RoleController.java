package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.ican.annotation.OptLogger;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.RoleQuery;
import com.ican.model.vo.request.RoleReq;
import com.ican.model.vo.request.RoleStatusReq;
import com.ican.model.vo.response.RoleResp;
import com.ican.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 角色控制器
 *
 * @author ican
 * @date 2022/12/05 09:58
 **/
@Api(tags = "角色模块")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查看后台角色列表
     *
     * @param roleQuery 角色查询条件
     * @return {@link RoleResp} 角色列表
     */
    @ApiOperation(value = "查看后台角色列表")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/role/list")
    public Result<PageResult<RoleResp>> listRoleVO(RoleQuery roleQuery) {
        return Result.success(roleService.listRoleVO(roleQuery));
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加角色")
    @SaCheckPermission("system:role:add")
    @PostMapping("/admin/role/add")
    public Result<?> addRole(@Validated @RequestBody RoleReq role) {
        roleService.addRole(role);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleIdList 角色id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除角色")
    @SaCheckPermission("system:role:delete")
    @DeleteMapping("/admin/role/delete")
    public Result<?> deleteRole(@RequestBody List<String> roleIdList) {
        roleService.deleteRole(roleIdList);
        return Result.success();
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改角色")
    @SaCheckPermission("system:role:update")
    @PutMapping("/admin/role/update")
    public Result<?> updateRole(@Validated @RequestBody RoleReq role) {
        roleService.updateRole(role);
        return Result.success();
    }

    /**
     * 修改角色状态
     *
     * @param roleStatus 角色状态
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改角色状态")
    @SaCheckPermission(value = {"system:role:update", "system:role:status"}, mode = SaMode.OR)
    @PutMapping("/admin/role/changeStatus")
    public Result<?> updateRoleStatus(@Validated @RequestBody RoleStatusReq roleStatus) {
        roleService.updateRoleStatus(roleStatus);
        return Result.success();
    }

    /**
     * 查看角色的菜单权限
     *
     * @param roleId 角色id
     * @return {@link List<Integer>} 角色的菜单权限
     */
    @ApiOperation(value = "查看角色的菜单权限")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/role/menu/{roleId}")
    public Result<List<Integer>> listRoleMenuTree(@PathVariable("roleId") String roleId) {
        return Result.success(roleService.listRoleMenuTree(roleId));
    }

}