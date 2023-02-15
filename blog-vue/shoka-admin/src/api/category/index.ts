import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Category, CategoryForm, CategoryQuery } from "./types";

/**
 * 查看分类列表
 * @param params 查询条件
 * @returns 分类列表
 */
export function getCategoryList(
  params: CategoryQuery
): AxiosPromise<Result<PageResult<Category[]>>> {
  return request({
    url: "/admin/category/list",
    method: "get",
    params,
  });
}

/**
 * 删除分类
 * @param data 分类id集合
 */
export function deleteCategory(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/category/delete",
    method: "delete",
    data,
  });
}

/**
 * 添加分类
 * @param data 分类信息
 */
export function addCategory(data: CategoryForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/category/add",
    method: "post",
    data,
  });
}

/**
 * 修改分类
 * @param data 分类信息
 */
export function updateCategory(data: CategoryForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/category/update",
    method: "put",
    data,
  });
}
