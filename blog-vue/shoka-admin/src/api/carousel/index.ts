import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Carousel, CarouselForm, CarouselQuery, CarouselStatus } from "./types";

/**
 * 查看轮播图列表
 * @param params 查询条件
 * @returns 轮播图列表
 */
export function getCarouselList(
  params: CarouselQuery
): AxiosPromise<Result<PageResult<Carousel[]>>> {
  return request({
    url: "/admin/carousel/list",
    method: "get",
    params,
  });
}

/**
 * 添加轮播图
 * @param params 轮播图
 */
export function addCarousel(data: CarouselForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/carousel/add",
    method: "post",
    data,
  });
}

/**
 * 修改轮播图
 * @param data 轮播图
 */
export function updateCarousel(data: CarouselForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/carousel/update",
    method: "put",
    data,
  });
}

/**
 * 删除轮播图
 * @param data 轮播图id集合
 */
export function deleteCarousel(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/carousel/delete",
    method: "delete",
    data,
  });
}

/**
 * 修改轮播图状态
 * @param data 置顶信息
 */
export function updateCarouselStatus(data: CarouselStatus): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/carousel/status",
    method: "put",
    data,
  });
}
