package com.ican.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.constant.CommonConstant;
import com.ican.entity.Menu;
import com.ican.entity.RoleMenu;
import com.ican.mapper.MenuMapper;
import com.ican.mapper.RoleMenuMapper;
import com.ican.model.vo.response.MenuOptionResp;
import com.ican.model.vo.query.MenuQuery;
import com.ican.model.vo.request.MenuReq;
import com.ican.model.vo.response.MenuResp;
import com.ican.model.vo.response.MenuTreeResp;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单业务接口实现类
 *
 * @author ican
 * @date 2022/12/05 22:13
 **/
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    public List<MenuResp> listMenuVO(MenuQuery menuQuery) {
        // 查询当前菜单列表
        List<MenuResp> menuRespList = menuMapper.selectMenuVOList(menuQuery);
        // 当前菜单id列表
        Set<Integer> menuIdList = menuRespList.stream()
                .map(MenuResp::getId)
                .collect(Collectors.toSet());
        return menuRespList.stream().map(menuVO -> {
            Integer parentId = menuVO.getParentId();
            // parentId不在当前菜单id列表，说明为父级菜单id，根据此id作为递归的开始条件节点
            if (!menuIdList.contains(parentId)) {
                menuIdList.add(parentId);
                return recurMenuList(parentId, menuRespList);
            }
            return new ArrayList<MenuResp>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    public void addMenu(MenuReq menu) {
        // 名称是否存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menu.getMenuName()));
        Assert.isNull(existMenu, menu.getMenuName() + "菜单已存在");
        Menu newMenu = BeanCopyUtils.copyBean(menu, Menu.class);
        baseMapper.insert(newMenu);
    }

    public void deleteMenu(Integer menuId) {
        // 菜单下是否存在子菜单
        Long menuCount = menuMapper.selectCount(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getParentId, menuId));
        Assert.isFalse(menuCount > 0, "存在子菜单");
        // 菜单是否已分配
        Long roleCount = roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        Assert.isFalse(roleCount > 0, "菜单已分配");
        // 删除菜单
        menuMapper.deleteById(menuId);
    }

    public void updateMenu(MenuReq menu) {
        // 名称是否存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menu.getMenuName()));
        Assert.isFalse(Objects.nonNull(existMenu) && !existMenu.getId().equals(menu.getId()),
                menu.getMenuName() + "菜单已存在");
        Menu newMenu = BeanCopyUtils.copyBean(menu, Menu.class);
        baseMapper.updateById(newMenu);
    }

    public List<MenuTreeResp> listMenuTree() {
        List<MenuTreeResp> menuTreeRespList = menuMapper.selectMenuTree();
        return recurMenuTreeList(CommonConstant.PARENT_ID, menuTreeRespList);
    }

    public List<MenuOptionResp> listMenuOption() {
        List<MenuOptionResp> menuOptionList = menuMapper.selectMenuOptions();
        return recurMenuOptionList(CommonConstant.PARENT_ID, menuOptionList);
    }

    public MenuReq editMenu(Integer menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父菜单id
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuResp> recurMenuList(Integer parentId, List<MenuResp> menuList) {
        return menuList.stream()
                .filter(menuVO -> menuVO.getParentId().equals(parentId))
                .peek(menuVO -> menuVO.setChildren(recurMenuList(menuVO.getId(), menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单下拉树
     *
     * @param parentId         父菜单id
     * @param menuTreeRespList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuTreeResp> recurMenuTreeList(Integer parentId, List<MenuTreeResp> menuTreeRespList) {
        return menuTreeRespList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuTreeList(menu.getId(), menuTreeRespList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单选项树
     *
     * @param parentId       父菜单id
     * @param menuOptionList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuOptionResp> recurMenuOptionList(Integer parentId, List<MenuOptionResp> menuOptionList) {
        return menuOptionList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuOptionList(menu.getValue(), menuOptionList)))
                .collect(Collectors.toList());
    }

}