import { PageQuery, PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Talk } from "./types";

/**
 * 查看说说列表
 * @param params 查询条件
 * @returns 说说列表
 */
export function getTalkList(params: PageQuery): AxiosPromise<Result<PageResult<Talk[]>>> {
  return request({
    url: "/talk/list",
    method: "get",
    params,
  });
}

/**
 * 查看首页说说
 * @returns 首页说说
 */
export function getTalkHomeList(): AxiosPromise<Result<string[]>> {
  return request({
    url: "/home/talk",
    method: "get",
  });
}

/**
 * 查看说说
 * @returns 说说
 */
export function getTalk(talkId: number): AxiosPromise<Result<Talk>> {
  return request({
    url: `/talk/${talkId}`,
    method: "get",
  });
}

/**
 * 点赞说说
 * @param talkId 说说id
 */
export function likeTalk(talkId: number): AxiosPromise<Result<null>> {
  return request({
    url: `/talk/${talkId}/like`,
    method: "post",
  });
}
