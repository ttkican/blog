/**
 * 留言
 */
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
}

/**
 * 留言表单
 */
export interface MessageForm {
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
}
