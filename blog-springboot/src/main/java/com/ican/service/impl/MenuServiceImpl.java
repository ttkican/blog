package com.ican.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Menu;
import com.ican.entity.RoleMenu;
import com.ican.mapper.MenuMapper;
import com.ican.mapper.RoleMenuMapper;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MenuDTO;
import com.ican.model.vo.MenuOption;
import com.ican.model.vo.MenuTree;
import com.ican.model.vo.MenuVO;
import com.ican.service.MenuService;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ican.constant.CommonConstant.PARENT_ID;

/**
 * 菜单业务接口实现类
 *
 * @author ican
 * @date 2022/12/05 22:13
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuVO> listMenuVO(ConditionDTO condition) {
        // 查询当前菜单列表
        List<MenuVO> menuVOList = menuMapper.selectMenuVOList(condition);
        // 当前菜单id列表
        Set<Integer> menuIdList = menuVOList.stream()
                .map(MenuVO::getId)
                .collect(Collectors.toSet());
        return menuVOList.stream().map(menuVO -> {
            Integer parentId = menuVO.getParentId();
            // parentId不在当前菜单id列表，说明为父级菜单id，根据此id作为递归的开始条件节点
            if (!menuIdList.contains(parentId)) {
                menuIdList.add(parentId);
                return recurMenuList(parentId, menuVOList);
            }
            return new ArrayList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    @Override
    public void addMenu(MenuDTO menu) {
        // 名称是否存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menu.getMenuName()));
        Assert.isNull(existMenu, menu.getMenuName() + "菜单已存在");
        Menu newMenu = BeanCopyUtils.copyBean(menu, Menu.class);
        baseMapper.insert(newMenu);
    }

    @Override
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

    @Override
    public void updateMenu(MenuDTO menu) {
        // 名称是否存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menu.getMenuName()));
        Assert.isFalse(Objects.nonNull(existMenu) && !existMenu.getId().equals(menu.getId()),
                menu.getMenuName() + "菜单已存在");
        Menu newMenu = BeanCopyUtils.copyBean(menu, Menu.class);
        baseMapper.updateById(newMenu);
    }

    @Override
    public List<MenuTree> listMenuTree() {
        List<MenuTree> menuTreeList = menuMapper.selectMenuTree();
        return recurMenuTreeList(PARENT_ID, menuTreeList);
    }

    @Override
    public List<MenuOption> listMenuOption() {
        List<MenuOption> menuOptionList = menuMapper.selectMenuOptions();
        return recurMenuOptionList(PARENT_ID, menuOptionList);
    }

    @Override
    public MenuDTO editMenu(Integer menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父菜单id
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuVO> recurMenuList(Integer parentId, List<MenuVO> menuList) {
        return menuList.stream()
                .filter(menuVO -> menuVO.getParentId().equals(parentId))
                .peek(menuVO -> menuVO.setChildren(recurMenuList(menuVO.getId(), menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单下拉树
     *
     * @param parentId     父菜单id
     * @param menuTreeList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuTree> recurMenuTreeList(Integer parentId, List<MenuTree> menuTreeList) {
        return menuTreeList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuTreeList(menu.getId(), menuTreeList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单选项树
     *
     * @param parentId       父菜单id
     * @param menuOptionList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuOption> recurMenuOptionList(Integer parentId, List<MenuOption> menuOptionList) {
        return menuOptionList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuOptionList(menu.getValue(), menuOptionList)))
                .collect(Collectors.toList());
    }

}