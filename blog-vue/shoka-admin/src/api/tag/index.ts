import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Tag, TagForm, TagQuery } from "./types";

/**
 * 查看标签列表
 * @param params 查询条件
 * @returns 标签列表
 */
export function getTagList(
  params?: TagQuery
): AxiosPromise<Result<PageResult<Tag[]>>> {
  return request({
    url: "/admin/tag/list",
    method: "get",
    params,
  });
}

/**
 * 删除标签
 * @param data 标签id集合
 */
export function deleteTag(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/tag/delete",
    method: "delete",
    data,
  });
}

/**
 * 添加标签
 * @param data 标签信息
 */
export function addTag(data: TagForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/tag/add",
    method: "post",
    data,
  });
}

/**
 * 修改标签
 * @param data 标签信息
 */
export function updateTag(data: TagForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/tag/update",
    method: "put",
    data,
  });
}