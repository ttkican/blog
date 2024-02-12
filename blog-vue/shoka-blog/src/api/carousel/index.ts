import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Carousel } from "./types";

/**
 * 获取轮播图列表
 * @returns 轮播图列表
 */
export function getCarouselList(): AxiosPromise<Result<Carousel[]>> {
  return request({
    url: "/carousel/list",
    method: "get",
  });
}
