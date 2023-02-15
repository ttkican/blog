import { PageQuery } from "@/model";

export interface Message {
  /**
   * 留言id
   */
  id: number;
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 留言内容
   */
  messageContent: string;
  /**
   * 用户ip
   */
  ipAddress: string;
  /**
   * 用户地址
   */
  ipSource: string;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
  /**
   * 留言时间
   */
  createTime: string;
}

/**
 * 留言查询参数
 */
export interface MessageQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck?: number;
}
