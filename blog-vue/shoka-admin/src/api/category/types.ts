import { PageQuery } from "@/model";

/**
 * 分类
 */
export interface Category {
  /**
   * 分类id
   */
  id: number;
  /**
   * 分类名
   */
  categoryName: string;
  /**
   * 文章数量
   */
  articleCount: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 分类查询参数
 */
export interface CategoryQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 分类表单
 */
export interface CategoryForm {
  /**
   * 分类id
   */
  id?: number;
  /**
   * 分类名
   */
  categoryName: string;
}
