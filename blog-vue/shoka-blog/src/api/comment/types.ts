import { PageQuery } from "@/model";

/**
 * 最近评论
 */
export interface RecentComment {
  /**
   * 评论id
   */
  id: number;
  /**
   * 昵称
   */
  nickname: number;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 评论内容
   */
  commentContent: string;
  /**
   * 评论时间
   */
  createTime: string;
}

/**
 * 评论查询参数
 */
export interface CommentQuery extends PageQuery {
  /**
   * 类型id
   */
  typeId?: number;
  /**
   * 评论类型
   */
  commentType: number;
}

/**
 * 回复
 */
export interface Reply {
  /**
   * 评论id
   */
  id: number;
  /**
   * 父级评论id
   */
  parentId: number;
  /**
   * 评论用户id
   */
  fromUid: number;
  /**
   * 被评论用户id
   */
  toUid: number;
  /**
   * 评论用户昵称
   */
  fromNickname: string;
  /**
   * 被评论用户昵称
   */
  toNickname: string;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 评论内容
   */
  commentContent: string;
  /**
   * 点赞数
   */
  likeCount: number;
  /**
   * 评论时间
   */
  createTime: string;
}

/**
 * 评论
 */
export interface Comment {
  /**
   * 评论id
   */
  id: number;
  /**
   * 评论用户id
   */
  fromUid: number;
  /**
   * 昵称
   */
  fromNickname: string;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 评论内容
   */
  commentContent: string;
  /**
   * 点赞数
   */
  likeCount: number;
  /**
   * 回复量
   */
  replyCount: number;
  /**
   * 回复列表
   */
  replyVOList: Reply[];
  /**
   * 评论时间
   */
  createTime: string;
}

/**
 * 评论表单
 */
export interface CommentForm {
  /**
   * 类型id
   */
  typeId?: number;
  /**
   * 评论类型 (1文章 2友链 3说说)
   */
  commentType: number;
  /**
   * 父评论id
   */
  parentId?: number;
  /**
   * 被回复评论id
   */
  replyId?: number;
  /**
   * 被回复用户id
   */
  toUid?: number;
  /**
   * 评论内容
   */
  commentContent: string;
}
