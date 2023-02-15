import { CheckDTO, PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Comment, CommentQuery } from "./types";

/**
 * 查看评论列表
 * @param params 查询条件
 * @returns 评论列表
 */
export function getCommentList(params: CommentQuery): AxiosPromise<Result<PageResult<Comment[]>>> {
  return request({
    url: "/admin/comment/list",
    method: "get",
    params,
  });
}

/**
 * 删除评论
 * @param data 评论id集合
 */
export function deleteComment(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/comment/delete",
    method: "delete",
    data,
  });
}

/**
 * 审核评论
 * @param data 审核信息
 */
export function updateCommentCheck(data: CheckDTO): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/comment/pass",
    method: "put",
    data,
  });
}
