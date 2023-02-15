/**
 * 邮箱
 */
export interface EmailForm {
  /**
   * 邮箱号
   */
  email: string;
  /**
   * 验证码
   */
  code: string;
}

/**
 * 用户信息
 */
export interface UserInfo {
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 个人网站
   */
  webSite: string;
  /**
   * 个人简介
   */
  intro: string;
}
