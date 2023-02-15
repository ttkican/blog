import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Role, RoleForm, RoleQuery, RoleStatus } from "./types";

/**
 * 查看角色列表
 * @param params 查询条件
 * @returns 角色列表
 */
export function getRoleList(params: RoleQuery): AxiosPromise<Result<PageResult<Role[]>>> {
  return request({
    url: "/admin/role/list",
    method: "get",
    params,
  });
}

/**
 * 删除角色
 * @param data 角色id集合
 */
export function deleteRole(data: string[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/role/delete",
    method: "delete",
    data,
  });
}

/**
 * 添加角色
 * @param data 角色信息
 */
export function addRole(data: RoleForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/role/add",
    method: "post",
    data,
  });
}

/**
 * 修改角色
 * @param data 角色信息
 */
export function updateRole(data: RoleForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/role/update",
    method: "put",
    data,
  });
}

/**
 * 修改角色状态
 * @param data 角色状态
 */
export function updateRoleStatus(data: RoleStatus): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/role/changeStatus",
    method: "put",
    data,
  });
}

/**
 * 获取角色的菜单权限
 * @param roleId 角色id
 */
export function getRoleMenuTree(roleId: string): AxiosPromise<Result<number[]>> {
  return request({
    url: `/admin/role/menu/${roleId}`,
    method: "get",
  });
}
