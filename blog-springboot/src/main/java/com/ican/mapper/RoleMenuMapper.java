package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色菜单 Mapper
 *
 * @author ican
 * @date 2022/12/07 11:16
 **/
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 批量删除角色菜单
     *
     * @param roleIdList 需要删除的数据ID
     */
    void deleteRoleMenu(List<String> roleIdList);

    /**
     * 添加角色菜单
     *
     * @param id         角色id
     * @param menuIdList 菜单id集合
     */
    void insertRoleMenu(@Param("roleId") String id, List<Integer> menuIdList);

    /**
     * 根据角色id删除角色菜单
     *
     * @param id 角色id
     */
    void deleteRoleMenuByRoleId(@Param("roleId") String id);

    /**
     * 根据角色id查询菜单权限
     *
     * @param roleId 角色id
     * @return 菜单权限
     */
    List<Integer> selectMenuByRoleId(String roleId);
}