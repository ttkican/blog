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
 * 上传图片
 */
export interface Picture {
  /**
   * 链接
   */
  url: string;
}

/**
 * 审核DTO
 */
export interface CheckDTO {
  /**
   * id集合
   */
  idList: number[];
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
}