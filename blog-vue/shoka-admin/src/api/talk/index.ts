import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Talk, TalkForm, TalkQuery } from "./types";

/**
 * 查看说说列表
 * @param params 查询条件
 * @returns 说说列表
 */
export function getTalkList(params: TalkQuery): AxiosPromise<Result<PageResult<Talk[]>>> {
  return request({
    url: "/admin/talk/list",
    method: "get",
    params,
  });
}

/**
 * 上传说说图片
 * @param data 文件
 * @returns 说说图片链接
 */
export function uploadTalkCover(data: FormData): AxiosPromise<Result<String>> {
  return request({
    url: "/admin/talk/upload",
    method: "post",
    data,
  });
}

/**
 * 编辑说说
 * @param talkId 说说id
 * @returns 说说
 */
export function editTalk(talkId: number): AxiosPromise<Result<TalkForm>> {
  return request({
    url: `/admin/talk/edit/${talkId}`,
    method: "get",
  });
}

/**
 * 添加说说
 * @param data 说说信息
 */
export function addTalk(data: TalkForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/talk/add",
    method: "post",
    data,
  });
}

/**
 * 修改说说
 * @param data 说说信息
 */
export function updateTalk(data: TalkForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/talk/update",
    method: "put",
    data,
  });
}

/**
 * 删除说说
 * @param talkId 说说id
 */
export function deleteTalk(talkId: number | undefined): AxiosPromise<Result<null>> {
  return request({
    url: `/admin/talk/delete/${talkId}`,
    method: "delete",
  });
}
