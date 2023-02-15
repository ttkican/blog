package com.ican.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色菜单
 *
 * @author ican
 * @date 2022/11/29 22:13
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

}