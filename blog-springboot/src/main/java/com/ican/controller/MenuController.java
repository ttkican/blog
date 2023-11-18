package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.model.vo.response.MenuOptionResp;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.MenuQuery;
import com.ican.model.vo.request.MenuReq;
import com.ican.model.vo.response.MenuResp;
import com.ican.model.vo.response.MenuTreeResp;
import com.ican.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 菜单控制器
 *
 * @author ican
 * @date 2022/12/05 09:57
 **/
@Api(tags = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查看菜单列表
     *
     * @param menuQuery 菜单查询条件
     * @return {@link MenuResp} 菜单列表
     */
    @ApiOperation(value = "查看菜单列表")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/list")
    public Result<List<MenuResp>> listMenuVO(MenuQuery menuQuery) {
        return Result.success(menuService.listMenuVO(menuQuery));
    }

    /**
     * 添加菜单
     *
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加菜单")
    @SaCheckPermission("system:menu:add")
    @PostMapping("/admin/menu/add")
    public Result<?> addMenu(@Validated @RequestBody MenuReq menu) {
        menuService.addMenu(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除菜单")
    @SaCheckPermission("system:menu:delete")
    @DeleteMapping("/admin/menu/delete/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 修改菜单
     *
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改菜单")
    @SaCheckPermission("system:menu:update")
    @PutMapping("/admin/menu/update")
    public Result<?> updateMenu(@Validated @RequestBody MenuReq menu) {
        menuService.updateMenu(menu);
        return Result.success();
    }

    /**
     * 获取菜单下拉树
     *
     * @return {@link MenuTreeResp} 菜单树
     */
    @ApiOperation(value = "获取菜单下拉树")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/menu/getMenuTree")
    public Result<List<MenuTreeResp>> listMenuTree() {
        return Result.success(menuService.listMenuTree());
    }

    /**
     * 获取菜单选项树
     *
     * @return {@link MenuOptionResp} 菜单下拉树
     */
    @ApiOperation(value = "获取菜单选项树")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/getMenuOptions")
    public Result<List<MenuOptionResp>> listMenuOption() {
        return Result.success(menuService.listMenuOption());
    }

    /**
     * 编辑菜单
     *
     * @param menuId 菜单id
     * @return {@link MenuReq} 菜单
     */
    @ApiOperation(value = "编辑菜单")
    @SaCheckPermission("system:menu:edit")
    @GetMapping("/admin/menu/edit/{menuId}")
    public Result<MenuReq> editMenu(@PathVariable("menuId") Integer menuId) {
        return Result.success(menuService.editMenu(menuId));
    }

}