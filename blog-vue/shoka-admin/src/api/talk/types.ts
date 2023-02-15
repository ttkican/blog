import { PageQuery } from "@/model";

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
   * 说说状态 (1公开 2私密)
   */
  status: number;
  /**
   * 发布时间
   */
  createTime: string;
}

/**
 * 说说查询参数
 */
export interface TalkQuery extends PageQuery {
  /**
   * 状态
   */
  status?: number;
}

/**
 * 说说表单
 */
export interface TalkForm {
  /**
   * 说说id
   */
  id?: number;
  /**
   * 说说内容
   */
  talkContent: string;
  /**
   * 说说图片
   */
  images: string;
  /**
   * 图片列表
   */
  imgList?: string[];
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
  /**
   * 说说状态 (1公开 2私密)
   */
  status: number;
}
