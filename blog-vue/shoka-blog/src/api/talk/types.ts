/**
 * 说说
 */
export interface Talk {
  /**
   * 说说id
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
   * 说说内容
   */
  talkContent: string;
  /**
   * 图片列表
   */
  imgList: string[];
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
  /**
   * 点赞量
   */
  likeCount: number;
  /**
   * 评论量
   */
  commentCount: number;
  /**
   * 创建时间
   */
  createTime: string;
}
