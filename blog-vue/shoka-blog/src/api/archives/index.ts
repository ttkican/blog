import { PageQuery, PageResult, Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { Archives } from "./types";

/**
 * 查看文章归档
 * @param params 查询条件
 * @returns 文章归档
 */
export function getArchivesList(params: PageQuery): AxiosPromise<Result<PageResult<Archives[]>>> {
  return request({
    url: "/archives/list",
    method: "get",
    params,
  });
}
