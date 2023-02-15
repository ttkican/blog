import { PageQuery } from "@/model";

/**
 * 友链
 */
export interface Friend {
  /**
   * 友链id
   */
  id: number;
  /**
   * 友链颜色
   */
  color: string;
  /**
   * 友链名称
   */
  name: string;
  /**
   * 友链头像
   */
  avatar: string;
  /**
   * 友链地址
   */
  url: string;
  /**
   * 友链介绍
   */
  introduction: string;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 友链查询参数
 */
export interface FriendQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 友链表单
 */
export interface FriendForm {
  /**
   * 友链id
   */
  id?: number;
  /**
   * 友链颜色
   */
  color: string;
  /**
   * 友链名称
   */
  name: string;
  /**
   * 友链头像
   */
  avatar: string;
  /**
   * 友链地址
   */
  url: string;
  /**
   * 友链介绍
   */
  introduction: string;
}