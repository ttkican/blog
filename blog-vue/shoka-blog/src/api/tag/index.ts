import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { ArticleConditionList, ArticleQuery } from "../article/types";
import { Tag } from "./types";

/**
 * 查看文章标签
 * @returns 文章标签
 */
export function getTagList(): AxiosPromise<Result<Tag[]>> {
  return request({
    url: "/tag/list",
    method: "get",
  });
}

/**
 * 查看标签文章
 * @returns 文章分类
 */
export function getTagArticleList(
  params: ArticleQuery
): AxiosPromise<Result<ArticleConditionList>> {
  return request({
    url: "/tag/article",
    method: "get",
    params,
  });
}
