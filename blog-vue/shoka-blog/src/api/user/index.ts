import { Result, UserForm } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { EmailForm, UserInfo } from "./types";

/**
 * 修改用户密码
 * @param data 用户密码
 */
export function updateUserPassword(data: UserForm): AxiosPromise<Result<null>> {
  return request({
    url: "/user/password",
    method: "put",
    data,
  });
}

/**
 * 修改用户头像
 * @param data 头像
 */
export function updateUserAvatar(data: FormData): AxiosPromise<Result<string>> {
  return request({
    url: "/user/avatar",
    method: "post",
    headers: { "content-type": "multipart/form-data" },
    data,
  });
}

/**
 * 修改用户邮箱
 * @param data 用户邮箱
 */
export function updateUserEmail(data: EmailForm): AxiosPromise<Result<null>> {
  return request({
    url: "/user/email",
    method: "put",
    data,
  });
}

/**
 * 修改用户信息
 * @param data 用户信息
 */
export function updateUserInfo(data: UserInfo): AxiosPromise<Result<null>> {
  return request({
    url: "/user/info",
    method: "put",
    data,
  });
}

/**
 * B站图片上传
 * @param data 头像
 */
export function biliUpload(data: FormData): AxiosPromise<Result<string>> {
  return request({
    url: "/bili/upload",
    method: "post",
    headers: { "content-type": "multipart/form-data" },
    data,
  });
}