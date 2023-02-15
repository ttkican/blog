import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Task, TaskForm, TaskQuery, TaskRun, TaskStatus } from "./types";

/**
 * 查看定时任务列表
 * @param params 查询条件
 * @returns 定时任务列表
 */
export function getTaskList(params: TaskQuery): AxiosPromise<Result<PageResult<Task[]>>> {
  return request({
    url: "/admin/task/list",
    method: "get",
    params,
  });
}

/**
 * 添加定时任务
 * @param data 定时任务信息
 */
export function addTask(data: TaskForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/task/add",
    method: "post",
    data,
  });
}

/**
 * 修改定时任务
 * @param data 定时任务信息
 */
export function updateTask(data: TaskForm): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/task/update",
    method: "put",
    data,
  });
}

/**
 * 删除定时任务
 * @param data 任务id
 */
export function deleteTask(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/task/delete",
    method: "delete",
    data,
  });
}

/**
 * 修改定时任务状态
 * @param data 任务状态
 */
export function updateTaskStatus(data: TaskStatus): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/task/changeStatus",
    method: "put",
    data,
  });
}

/**
 * 执行定时任务
 * @param data 任务状态
 */
export function runTask(data: TaskRun): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/task/run",
    method: "put",
    data,
  });
}
