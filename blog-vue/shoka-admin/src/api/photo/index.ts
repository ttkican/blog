import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { AlbumInfo, Photo, PhotoForm, PhotoInfo, PhotoQuery } from "./types";

/**
 * 查看照片列表
 * @param params 查询条件
 * @returns 照片列表
 */
export function getPhotoList(params: PhotoQuery): AxiosPromise<Result<PageResult<Photo[]>>> {
  return request({
    url: "/admin/photo/list",
    method: "get",
    params,
  });
}

/**
 * 获取相册信息
 * @param albumId 相册id
 * @returns 相册信息
 */
export function getAlbumInfo(albumId: number): AxiosPromise<Result<AlbumInfo>> {
  return request({
    url: `/admin/photo/album/${albumId}/info`,
    method: "get",
  });
}

/**
 * 添加照片
 * @param data 照片
 */
export function addPhoto(data: PhotoInfo): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/photo/add",
    method: "post",
    data,
  });
}

/**
 * 修改照片
 * @param data 照片信息
 */
export function updatePhoto(data: PhotoForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/photo/update",
    method: "put",
    data,
  });
}

/**
 * 删除照片
 * @param data 照片id集合
 */
export function deletePhoto(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/photo/delete",
    method: "delete",
    data,
  });
}
