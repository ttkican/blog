import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Friend } from "./types";

/**
 * 查看友链列表
 * @returns 文章分类
 */
export function getFriendList(): AxiosPromise<Result<Friend[]>> {
  return request({
    url: "/friend/list",
    method: "get",
  });
}
