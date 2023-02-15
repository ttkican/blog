import { PageQuery } from "@/model";

/**
 * 日志查询参数
 */
export interface LogQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 操作模块
   */
  optModule?: string;
}

/**
 * 访问日志查询参数
 */
export interface VisitQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 操作日志
 */
export interface OperationLog {
  /**
   * id
   */
  id: number;
  /**
   * 操作模块
   */
  module: string;
  /**
   * 操作uri
   */
  uri: string;
  /**
   * 操作类型
   */
  type: string;
  /**
   * 操作方法
   */
  name: string;
  /**
   * 操作描述
   */
  description: string;
  /**
   * 请求方式
   */
  method: string;
  /**
   * 请求参数
   */
  params: string;
  /**
   * 返回数据
   */
  data: string;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * 操作ip
   */
  ipAddress: string;
  /**
   * 操作地址
   */
  ipSource: string;
  /**
   * 操作耗时 (毫秒)
   */
  times: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 异常日志
 */
export interface ExceptionLog {
  /**
   * 异常id
   */
  id: number;
  /**
   * 异常模块
   */
  module: string;
  /**
   * 异常uri
   */
  uri: string;
  /**
   * 操作类型
   */
  type: string;
  /**
   * 异常名称
   */
  name: string;
  /**
   * 操作描述
   */
  description: string;
  /**
   * 异常方法
   */
  errorMethod: string;
  /**
   * 异常信息
   */
  message: string;
  /**
   * 请求参数
   */
  params: string;
  /**
   * 请求方式
   */
  requestMethod: string;
  /**
   * 操作ip
   */
  ipAddress: string;
  /**
   * 操作地址
   */
  ipSource: string;
  /**
   * 操作系统
   */
  os: string;
  /**
   * 浏览器
   */
  browser: string;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 访问日志
 */
export interface VisitLog {
  /**
   * 访问id
   */
  id: number;
  /**
   * 访问页面
   */
  page: string;
  /**
   * 访问ip
   */
  ipAddress: string;
  /**
   * ip来源
   */
  ipSource: string;
  /**
   * 浏览器
   */
  browser: string;
  /**
   * 操作系统
   */
  os: string;
  /**
   * 访问时间
   */
  createTime: string;
}

export interface TaskLog {
  /**
   * 任务id
   */
  id: number;
  /**
   * 任务名称
   */
  taskName: string;
  /**
   * 任务组名
   */
  taskGroup: string;
  /**
   * 调用目标
   */
  invokeTarget: string;
  /**
   * cron执行表达式
   */
  taskMessage: string;
  /**
   * 任务状态 (0失败 1成功)
   */
  status: number;
  /**
   * 错误信息
   */
  errorInfo: number;
  /**
   * 创建时间
   */
  createTime: string;
}
