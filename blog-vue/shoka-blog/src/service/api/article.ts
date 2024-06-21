import { Article } from "@/api/article/types";
import { PageQuery, PageResult } from "@/model";
import { request } from "../request";

/**
 * 查看文章列表
 * @param params 查询条件
 * @returns 文章列表
 */
export function fetchArticle(params: PageQuery) {
	return request<PageResult<Article[]>>({
		url: "/article/list",
		method: "get",
		params,
	});
}
