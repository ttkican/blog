import { Result } from "@/model";
import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { ArticleConditionList, ArticleQuery } from "../article/types";
import { Category } from "./types";

/**
 * 查看文章分类
 * @returns 文章分类
 */
export function getCategoryList(): AxiosPromise<Result<Category[]>> {
  return request({
    url: "/category/list",
    method: "get",
  });
}

/**
 * 查看分类文章
 * @returns 文章分类
 */
export function getCategoryArticleList(
  params: ArticleQuery
): AxiosPromise<Result<ArticleConditionList>> {
  return request({
    url: "/category/article",
    method: "get",
    params,
  });
}
