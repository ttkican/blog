import { BlogInfo } from "@/api/blogInfo/types";

/**
 * 应用
 */
export interface AppState {
  /**
   * 侧边栏展开
   */
  isCollapse: boolean;
  /**
   * 搜索框
   */
  searchFlag: boolean;
  /**
   * 登录框
   */
  loginFlag: boolean;
  /**
   * 注册框
   */
  registerFlag: boolean;
  /**
   * 忘记密码框
   */
  forgetFlag: boolean;
  /**
   * 邮箱框
   */
  emailFlag: boolean;
  /**
   * 左侧展开
   */
  sideFlag: boolean;
}

/**
 * 用户
 */
export interface UserState {
  /**
   * 用户id
   */
  id?: number;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 用户名
   */
  username: string;
  /**
   * 邮箱
   */
  email: string;
  /**
   * 个人网站
   */
  webSite: string;
  /**
   * 个人简介
   */
  intro: string;
  /**
   * 登录方式
   */
  loginType?: number;
  /**
   * 第三方登录之前的path
   */
  path: string;
  /**
   * 文章点赞集合
   */
  articleLikeSet: number[];
  /**
   * 评论点赞集合
   */
  commentLikeSet: number[];
  /**
   * 说说点赞集合
   */
  talkLikeSet: number[];
}

/**
 * 博客
 */
export interface BlogState {
  /**
   * 博客信息
   */
  blogInfo: BlogInfo;
}
