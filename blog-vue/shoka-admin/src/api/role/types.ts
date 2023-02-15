import { PageQuery } from "@/model";
import { TreeKey } from "element-plus/es/components/tree/src/tree.type";

/**
 * 角色
 */
export interface Role {
  /**
   * 角色id
   */
  id: string;
  /**
   * 角色名
   */
  roleName: string;
  /**
   * 角色描述
   */
  roleDesc: string;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 角色查询参数
 */
export interface RoleQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable?: number;
}

/**
 * 角色表单
 */
export interface RoleForm {
  /**
   * 角色id
   */
  id?: string;
  /**
   * 角色名
   */
  roleName: string;
  /**
   * 角色描述
   */
  roleDesc: string;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 菜单id集合
   */
  menuIdList: TreeKey[];
}

/**
 * 角色状态
 */
export interface RoleStatus {
  /**
   * 角色id
   */
  id: string;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
}
