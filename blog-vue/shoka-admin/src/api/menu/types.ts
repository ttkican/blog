/**
 * 用户菜单
 */
export interface UserMenu {
  /**
   * 菜单id
   */
  id: number;
  /**
   * 菜单名
   */
  menuName: string;
  /**
   * 菜单路径
   */
  path: string;
  /**
   * 菜单图标
   */
  icon: string;
  /**
   * 菜单组件
   */
  component: string;
  /**
   * 是否隐藏 (0否 1是)
   */
  isHidden: number;
}

/**
 * 菜单下拉树
 */
export interface MenuTree {
  /**
   * 菜单id
   */
  id: number;
  /**
   * 菜单名
   */
  label: string;
  /**
   * 子菜单树
   */
  children?: MenuTree[];
}

/**
 * 菜单选项树
 */
export interface MenuOption {
  /**
   * 菜单id
   */
  value: number;
  /**
   * 菜单名
   */
  label: string;
  /**
   * 子菜单树
   */
  children?: MenuOption[];
}

/**
 * 菜单查询参数
 */
export interface MenuQuery {
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
 * 菜单
 */
export interface Menu {
  /**
   * 菜单id
   */
  id: number;
  /**
   * 父菜单id
   */
  parentId: number;
  /**
   * 权限类型 (M菜单 C菜单 B按钮)
   */
  menuType: string;
  /**
   * 菜单名称
   */
  menuName: string;
  /**
   * 路由地址
   */
  path: string;
  /**
   * 菜单图标
   */
  icon: string;
  /**
   * 菜单组件
   */
  component: string;
  /**
   * 权限标识
   */
  perms: string;
  /**
   * 是否隐藏 (0否 1是)
   */
  isHidden: number;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 菜单排序
   */
  orderNum: number;
  /**
   * 创建时间
   */
  createTime: string;
  /**
   * 子菜单列表
   */
  children: Menu[];
}

/**
 * 菜单表单
 */
export interface MenuForm {
  /**
   * 菜单id
   */
  id?: number;
  /**
   * 父菜单id
   */
  parentId?: number;
  /**
   * 菜单名称
   */
  menuName: string;
  /**
   * 类型（M目录 C菜单 B按钮）
   */
  menuType: string;
  /**
   * 路由路径
   */
  path?: string;
  /**
   * 菜单图标
   */
  icon?: string;
  /**
   * 组件路径
   */
  component?: string;
  /**
   * 是否隐藏 (0否 1是)
   */
  isHidden: number;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 菜单排序
   */
  orderNum: number;
  /**
   * 权限标识
   */
  perms?: string;
}
