import { TagVO } from "../article/types";
import { Category } from "../category/types";

/**
 * 文章贡献统计
 */
export interface ArticleStatisticsVO {
  /**
   * 日期
   */
  date: Date;
  /**
   * 数量
   */
  count: number;
}

/**
 * 文章浏览量排行
 */
export interface ArticleRankVO {
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 浏览量
   */
  viewCount: number;
}

/**
 * 用户浏览量
 */
export interface UserViewVO {
  /**
   * 日期
   */
  date: string;
  /**
   * uv
   */
  uv: number;
  /**
   * pv
   */
  pv: number;
}

/**
 * 博客后台信息
 */
export interface BlogInfo {
  /**
   * 访问量
   */
  viewCount: number;
  /**
   * 留言量
   */
  messageCount: number;
  /**
   * 用户量
   */
  userCount: number;
  /**
   * 文章量
   */
  articleCount: number;
  /**
   * 文章分类
   */
  categoryVOList: Category[];
  /**
   * 文章标签
   */
  tagVOList: TagVO[];
  /**
   * 文章贡献统计
   */
  articleStatisticsList: ArticleStatisticsVO[];
  /**
   * 文章浏览量排行
   */
  articleRankVOList: ArticleRankVO[];
  /**
   * 用户浏览量
   */
  userViewVOList: UserViewVO[];
}
