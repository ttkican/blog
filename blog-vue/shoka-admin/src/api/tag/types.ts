import { PageQuery } from "@/model";

/**
 * 标签
 */
export interface Tag {
  /**
   * 标签id
   */
  id: number;
  /**
   * 标签名
   */
  tagName: string;
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
 * 标签查询参数
 */
export interface TagQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 标签表单
 */
export interface TagForm {
  /**
   * 标签id
   */
  id?: number;
  /**
   * 标签名
   */
  tagName: string;
}
