import { CheckDTO, PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import {  Message, MessageQuery } from "./types";

/**
 * 查看留言列表
 * @param params 查询条件
 * @returns 留言列表
 */
export function getMessageList(params: MessageQuery): AxiosPromise<Result<PageResult<Message[]>>> {
  return request({
    url: "/admin/message/list",
    method: "get",
    params,
  });
}

/**
 * 删除留言
 * @param data 留言id集合
 */
export function deleteMessage(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/message/delete",
    method: "delete",
    data,
  });
}

/**
 * 审核留言
 * @param data 审核信息
 */
export function updateMessageCheck(data: CheckDTO): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/message/pass",
    method: "put",
    data,
  });
}
