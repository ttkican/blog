package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Menu;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MenuDTO;
import com.ican.model.vo.MenuOption;
import com.ican.model.vo.MenuTree;
import com.ican.model.vo.MenuVO;
import com.ican.model.vo.UserMenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author ican
 * @date 2022/12/04 16:24
 **/
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色id查询对应权限
     *
     * @param roleId id
     * @return 权限标识
     */
    List<String> selectPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 查询菜单列表
     *
     * @param condition 查询条件
     * @return 菜单列表
     */
    List<MenuVO> selectMenuVOList(@Param("condition") ConditionDTO condition);

    /**
     * 根据用户id查询用户菜单列表
     *
     * @param userId 用户id
     * @return 用户菜单列表
     */
    List<UserMenuVO> selectMenuByUserId(@Param("userId") Integer userId);

    /**
     * 查询菜单下拉树
     *
     * @return 菜单下拉树
     */
    List<MenuTree> selectMenuTree();

    /**
     * 查询菜单选项树
     *
     * @return 菜单选项树
     */
    List<MenuOption> selectMenuOptions();

    /**
     * 根据id查询菜单信息
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    MenuDTO selectMenuById(@Param("menuId") Integer menuId);
}