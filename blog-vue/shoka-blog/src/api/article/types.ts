import { PageQuery } from "@/model";
import { CategoryVO } from "../category/types";
import { TagVO } from "../tag/types";

/**
 * 文章
 */
export interface Article {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章摘要
   */
  articleDesc: string;
  /**
   * 文章分类
   */
  category: CategoryVO;
  /**
   * 文章标签
   */
  tagVOList: TagVO[];
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
  /**
   * 发表时间
   */
  createTime: string;
}

/**
 * 文章上下篇
 */
export interface ArticlePagination {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 文章标题
   */
  articleTitle: string;
}

/**
 * 文章信息
 */
export interface ArticleInfo {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章内容
   */
  articleContent: string;
  /**
   * 文章类型 (1原创 2转载 3翻译)
   */
  articleType: number;
  /**
   * 浏览量
   */
  viewCount: number;
  /**
   * 点赞量
   */
  likeCount: number;
  /**
   * 文章分类
   */
  category: CategoryVO;
  /**
   * 文章标签
   */
  tagVOList: TagVO[];
  /**
   * 发表时间
   */
  createTime: string;
  /**
   * 上一篇文章
   */
  lastArticle: ArticlePagination;
  /**
   * 下一篇文章
   */
  nextArticle: ArticlePagination;
  /**
   * 更新时间
   */
  updateTime: string;
}

/**
 * 推荐文章
 */
export interface ArticleRecommend {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 发布时间
   */
  createTime: string;
}

/**
 * 文章分类、标签
 */
export interface ArticleCondition {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章分类
   */
  category: CategoryVO;
  /**
   * 文章标签
   */
  tagVOList: TagVO[];
  /**
   * 发布时间
   */
  createTime: string;
}

/**
 * 文章条件列表
 */
export interface ArticleConditionList {
  /**
   * 文章列表
   */
  articleConditionVOList: ArticleCondition[];
  /**
   * 条件名
   */
  name: string;
}

/**
 * 文章参数
 */
export interface ArticleQuery extends PageQuery {
  /**
   * 分类id
   */
  categoryId: number;
  /**
   * 标签id
   */
  tagId: number;
}

/**
 * 文章搜索
 */
export interface ArticleSearch {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章内容
   */
  articleContent: string;
}
