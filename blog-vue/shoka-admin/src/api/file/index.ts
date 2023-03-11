import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { BlogFile, FileQuery, FolderForm } from "./types";

/**
 * 查看文件列表
 * @param params 查询条件
 * @returns 文件列表
 */
export function getFileList(params: FileQuery): AxiosPromise<Result<PageResult<BlogFile[]>>> {
  return request({
    url: "/admin/file/list",
    method: "get",
    params,
  });
}

/**
 * 删除文件
 * @param data 文件id集合
 */
export function deleteFile(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/file/delete",
    method: "delete",
    data,
  });
}

/**
 * 创建文件夹
 * @param data 文件夹信息
 */
export function createFolder(data: FolderForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/file/createFolder",
    method: "post",
    data,
  });
}

/**
 * 上传文件
 * @param data 文件
 */
export function uploadFile(data: FormData): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/file/upload",
    headers: { "content-type": "multipart/form-data" },
    method: "post",
    data,
  });
}

/**
 * 下载文件
 * @param fileId 文件id
 */
export function downloadFile(fileId: number): AxiosPromise<Result<null>> {
  return request({
    url: `/admin/file/download/${fileId}`,
    method: "get",
  });
}
