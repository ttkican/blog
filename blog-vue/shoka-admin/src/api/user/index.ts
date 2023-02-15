import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import {
  Online,
  OnlineQuery,
  Password,
  User,
  UserForm,
  UserInfo,
  UserQuery,
  UserRole,
  UserStatus,
} from "./types";

/**
 * 获取用户信息
 * @returns 用户信息
 */
export function getUserInfo(): AxiosPromise<Result<UserInfo>> {
  return request({
    url: "/admin/user/getUserInfo",
    method: "get",
  });
}

/**
 * 查看用户列表
 * @param params 查询条件
 * @returns 用户列表
 */
export function getUserList(params: UserQuery): AxiosPromise<Result<PageResult<User[]>>> {
  return request({
    url: "/admin/user/list",
    method: "get",
    params,
  });
}

/**
 * 查看用户角色选项
 * @returns 用户角色选项
 */
export function getUserRoleList(): AxiosPromise<Result<UserRole[]>> {
  return request({
    url: "/admin/user/role",
    method: "get",
  });
}

/**
 * 修改用户
 * @param data 用户信息
 */
export function updateUser(data: UserForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/user/update",
    method: "put",
    data,
  });
}

/**
 * 修改用户状态
 * @param data 用户状态
 */
export function updateUserStatus(data: UserStatus): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/user/changeStatus",
    method: "put",
    data,
  });
}

/**
 * 查看在线用户列表
 * @param params 查询条件
 * @returns 在线用户列表
 */
export function getOnlineList(params: OnlineQuery): AxiosPromise<Result<PageResult<Online[]>>> {
  return request({
    url: "/admin/online/list",
    method: "get",
    params,
  });
}

/**
 * 下线用户
 * @param token 在线token
 */
export function kickOutUser(token: string): AxiosPromise<Result<null>> {
  return request({
    url: `/admin/online/kick/${token}`,
    method: "get",
  });
}

/**
 * 修改管理员密码
 * @param data 密码
 */
export function updateAdminPassword(data: Password) {
  return request({
    url: "/admin/password",
    method: "put",
    data,
  });
}
