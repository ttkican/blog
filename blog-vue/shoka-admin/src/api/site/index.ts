import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { SiteConfig } from "./types";

/**
 * 查看网站配置
 * @returns 网站配置
 */
export function getSiteConfig(): AxiosPromise<Result<SiteConfig>> {
  return request({
    url: "/admin/site/list",
    method: "get",
  });
}

/**
 * 更新网站配置
 * @param data 网站配置
 */
export function updateSiteConfig(data: SiteConfig): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/site/update",
    method: "put",
    data,
  });
}

/**
 * 上传网站配置图片
 * @returns 图片链接
 */
export function uploadSiteImg(data: FormData): AxiosPromise<Result<string>> {
  return request({
    url: "/admin/site/upload",
    headers: { "content-type": "multipart/form-data" },
    method: "post",
    data,
  });
}
