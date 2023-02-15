import { PageQuery } from "@/model";

/**
 * 评论
 */
export interface Comment {
  /**
   * 评论id
   */
  id: number;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 评论用户昵称
   */
  fromNickname: string;
  /**
   * 被回复用户昵称
   */
  toNickname: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 评论内容
   */
  commentContent: string;
  /**
   * 评论类型
   */
  commentType: number;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
  /**
   * 发表时间
   */
  createTime: string;
}

/**
 * 评论查询参数
 */
export interface CommentQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 评论类型
   */
  commentType: number;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
}
