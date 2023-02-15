import { PageQuery } from "@/model";

/**
 * 登录用户信息
 */
export interface UserInfo {
  /**
   * 用户id
   */
  id: number;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 角色集合
   */
  roleList: string[];
  /**
   * 权限集合
   */
  permissionList: string[];
}

/**
 * 用户角色
 */
export interface UserRole {
  /**
   * 角色id
   */
  id: string;
  /**
   * 角色名
   */
  roleName: string;
}

/**
 * 用户
 */
export interface User {
  /**
   * 用户id
   */
  id: number;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * 用户头像
   */
  avatar: string;
  /**
   * 登录ip
   */
  ipAddress: string;
  /**
   * 登录地址
   */
  ipSource: string;
  /**
   * 登录方式
   */
  loginType: number;
  /**
   * 用户角色
   */
  roleList: UserRole[];
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 登录时间
   */
  loginTime: string;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 用户查询参数
 */
export interface UserQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 登录方式
   */
  loginType: number;
}

/**
 * 用户表单
 */
export interface UserForm {
  /**
   * 用户id
   */
  id: number;
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 角色id
   */
  roleIdList: string[];
}

/**
 * 用户状态
 */
export interface UserStatus {
  /**
   * 用户id
   */
  id: number;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
}

/**
 * 在线用户查询参数
 */
export interface OnlineQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 在线用户
 */
export interface Online {
  /**
   * 用户id
   */
  id: number;
  /**
   * 在线token
   */
  token: string;
  /**
   * 用户头像
   */
  avatar: string;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * ip地址
   */
  ipAddress: string;
  /**
   * ip来源
   */
  ipSource: string;
  /**
   * 操作系统
   */
  os: string;
  /**
   * 浏览器
   */
  browser: string;
  /**
   * 登录时间
   */
  loginTime: string;
}

/**
 * 修改密码
 */
export interface Password {
  /**
   * 旧密码
   */
  oldPassword: string;
  /**
   * 新密码
   */
  newPassword: string;
  /**
   * 旧密码
   */
  checkPassword: string;
}
