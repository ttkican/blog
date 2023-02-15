import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Album, AlbumForm, AlbumQuery } from "./types";

/**
 * 查看相册列表
 * @param params 查询条件
 * @returns 相册列表
 */
export function getAlbumList(params: AlbumQuery): AxiosPromise<Result<PageResult<Album[]>>> {
  return request({
    url: "/admin/album/list",
    method: "get",
    params,
  });
}

/**
 * 编辑相册
 * @param albumId 相册id
 * @returns 相册
 */
export function editAlbum(albumId: number): AxiosPromise<Result<AlbumForm>> {
  return request({
    url: `/admin/album/edit/${albumId}`,
    method: "get",
  });
}

/**
 * 添加相册
 * @param data 相册信息
 */
export function addAlbum(data: AlbumForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/album/add",
    method: "post",
    data,
  });
}

/**
 * 修改相册
 * @param data 分类信息
 */
export function updateAlbum(data: AlbumForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/album/update",
    method: "put",
    data,
  });
}

/**
 * 删除相册
 * @param data 相册id
 */
export function deleteAlbum(albumId: number | undefined): AxiosPromise<Result<null>> {
  return request({
    url: `/admin/album/delete/${albumId}`,
    method: "delete",
  });
}
