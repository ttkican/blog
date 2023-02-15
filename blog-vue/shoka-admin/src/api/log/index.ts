import { PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { ExceptionLog, LogQuery, OperationLog, TaskLog, VisitLog, VisitQuery } from "./types";

/**
 * 查看操作日志列表
 * @param params 查询条件
 * @returns 操作日志列表
 */
export function getOperationLogList(
  params?: LogQuery
): AxiosPromise<Result<PageResult<OperationLog[]>>> {
  return request({
    url: "/admin/operation/list",
    method: "get",
    params,
  });
}

/**
 * 删除操作日志
 * @param data 日志id集合
 */
export function deleteOperation(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/operation/delete",
    method: "delete",
    data,
  });
}

/**
 * 查看异常日志列表
 * @param params 查询条件
 * @returns 异常日志列表
 */
export function getExceptionLogList(
  params?: LogQuery
): AxiosPromise<Result<PageResult<ExceptionLog[]>>> {
  return request({
    url: "/admin/exception/list",
    method: "get",
    params,
  });
}

/**
 * 删除异常日志
 * @param data 日志id集合
 */
export function deleteException(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/exception/delete",
    method: "delete",
    data,
  });
}

/**
 * 查看访问日志列表
 * @param params 查询条件
 * @returns 访问日志列表
 */
export function getVisitLogList(params?: VisitQuery): AxiosPromise<Result<PageResult<VisitLog[]>>> {
  return request({
    url: "/admin/visit/list",
    method: "get",
    params,
  });
}

/**
 * 删除访问日志
 * @param data 日志id集合
 */
export function deleteVisit(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/visit/delete",
    method: "delete",
    data,
  });
}

/**
 * 查看任务日志列表
 * @param params 查询条件
 * @returns 任务日志列表
 */
export function getTaskLogList(params?: LogQuery): AxiosPromise<Result<PageResult<TaskLog[]>>> {
  return request({
    url: "/admin/taskLog/list",
    method: "get",
    params,
  });
}

/**
 * 删除任务日志
 * @param data 日志id集合
 */
export function deleteTaskLog(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/taskLog/delete",
    method: "delete",
    data,
  });
}

/**
 * 清空任务日志
 */
export function clearTaskLog(): AxiosPromise<Result<null>> {
  return request({
    url: "/admin/taskLog/clear",
    method: "delete",
  });
}
