package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MenuDTO;
import com.ican.model.vo.MenuOption;
import com.ican.model.vo.MenuTree;
import com.ican.model.vo.MenuVO;
import com.ican.model.vo.Result;
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
     * @return {@link MenuVO} 菜单列表
     */
    @ApiOperation(value = "查看菜单列表")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/list")
    public Result<List<MenuVO>> listMenuVO(ConditionDTO condition) {
        return Result.success(menuService.listMenuVO(condition));
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
    public Result<?> addMenu(@Validated @RequestBody MenuDTO menu) {
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
    public Result<?> updateMenu(@Validated @RequestBody MenuDTO menu) {
        menuService.updateMenu(menu);
        return Result.success();
    }

    /**
     * 获取菜单下拉树
     *
     * @return {@link MenuTree} 菜单树
     */
    @ApiOperation(value = "获取菜单下拉树")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/menu/getMenuTree")
    public Result<List<MenuTree>> listMenuTree() {
        return Result.success(menuService.listMenuTree());
    }

    /**
     * 获取菜单选项树
     *
     * @return {@link MenuOption} 菜单下拉树
     */
    @ApiOperation(value = "获取菜单选项树")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/getMenuOptions")
    public Result<List<MenuOption>> listMenuOption() {
        return Result.success(menuService.listMenuOption());
    }

    /**
     * 编辑菜单
     *
     * @param menuId 菜单id
     * @return {@link MenuDTO} 菜单
     */
    @ApiOperation(value = "编辑菜单")
    @SaCheckPermission("system:menu:edit")
    @GetMapping("/admin/menu/edit/{menuId}")
    public Result<MenuDTO> editMenu(@PathVariable("menuId") Integer menuId) {
        return Result.success(menuService.editMenu(menuId));
    }

}