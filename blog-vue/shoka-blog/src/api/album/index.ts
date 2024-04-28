import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Album, PhotoInfo } from "./types";

/**
 * 查看相册列表
 * @returns 相册列表
 */
export function getAlbumList(): AxiosPromise<Result<Album[]>> {
  return request({
    url: "/album/list",
    method: "get",
  });
}

/**
 * 查看照片列表
 * @param albumId 相册id
 * @returns 照片列表
 */
export function getPhotoList(albumId: number): AxiosPromise<Result<PhotoInfo>> {
  return request({
    url: "/photo/list",
    method: "get",
    params: {
      albumId,
    },
  });
}
