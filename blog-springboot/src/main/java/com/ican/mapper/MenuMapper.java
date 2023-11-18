package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Menu;
import com.ican.model.vo.response.MenuOptionResp;
import com.ican.model.vo.query.MenuQuery;
import com.ican.model.vo.request.MenuReq;
import com.ican.model.vo.response.MenuResp;
import com.ican.model.vo.response.MenuTreeResp;
import com.ican.model.vo.response.UserMenuResp;
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
     * @param menuQuery 菜单查询条件
     * @return 菜单列表
     */
    List<MenuResp> selectMenuVOList(@Param("param") MenuQuery menuQuery);

    /**
     * 根据用户id查询用户菜单列表
     *
     * @param userId 用户id
     * @return 用户菜单列表
     */
    List<UserMenuResp> selectMenuByUserId(@Param("userId") Integer userId);

    /**
     * 查询菜单下拉树
     *
     * @return 菜单下拉树
     */
    List<MenuTreeResp> selectMenuTree();

    /**
     * 查询菜单选项树
     *
     * @return 菜单选项树
     */
    List<MenuOptionResp> selectMenuOptions();

    /**
     * 根据id查询菜单信息
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    MenuReq selectMenuById(@Param("menuId") Integer menuId);
}