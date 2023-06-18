/**
 * 分页返回接口
 */
export interface PageResult<T> {
  /**
   * 分页结果
   */
  recordList: T;
  /**
   * 总数
   */
  count: number;
}
/**
 * 结果返回接口
 */
export interface Result<T> {
  /**
   * 返回状态
   */
  flag: boolean;
  /**
   * 状态码
   */
  code: number;
  /**
   * 返回信息
   */
  msg: string;
  /**
   * 返回数据
   */
  data: T;
}

/**
 * 分页参数
 */
export interface PageQuery {
  /**
   * 当前页
   */
  current: number;
  /**
   * 每页大小
   */
  size: number;
}

/**
 * 用户信息
 */
export interface UserForm {
  /**
   * 用户名
   */
  username: string;
  /**
   * 密码
   */
  password: string;
  /**
   * 验证码
   */
  code: string;
}

export interface Record {
  /**
   * 聊天记录id
   */
  id: number;
  /**
   * 用户id
   */
  userId: number;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * 用户头像
   */
  avatar: string;
  /**
   * 聊天内容
   */
  content: string;
  /**
   * 用户登录ip
   */
  ipAddress: string;
  /**
   * ip来源
   */
  ipSource: string;
  /**
   * 创建时间
   */
  createTime: string;
}
