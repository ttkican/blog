import { PageQuery } from "@/model";

export interface Task {
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
  cronExpression: string;
  /**
   * 计划执行错误策略 (1立即执行 2执行一次 3放弃执行)
   */
  misfirePolicy: number;
  /**
   * 是否并发执行 (0否 1是)
   */
  concurrent: number;
  /**
   * 任务状态 (0运行 1暂停)
   */
  status: number;
  /**
   * 任务备注信息
   */
  remark: string;
  /**
   * 下次执行时间
   */
  nextValidTime: Date;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 任务查询参数
 */
export interface TaskQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
  /**
   * 任务状态 (0运行 1暂停)
   */
  status?: number;
  /**
   * 任务组名
   */
  taskGroup: string;
}

/**
 * 定时任务表单
 */
export interface TaskForm {
  /**
   * 任务id
   */
  id?: number;
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
  cronExpression: string;
  /**
   * 计划执行错误策略 (1立即执行 2执行一次 3放弃执行)
   */
  misfirePolicy: number;
  /**
   * 是否并发执行 (0否 1是)
   */
  concurrent: number;
  /**
   * 任务状态 (0运行 1暂停)
   */
  status: number;
  /**
   * 任务备注信息
   */
  remark: string;
  /**
   * 下次执行时间
   */
  nextValidTime: Date;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 任务状态
 */
export interface TaskStatus {
  /**
   * 任务id
   */
  id: number;
  /**
   * 任务状态 (0运行 1暂停)
   */
  status: number;
}

/**
 * 任务运行
 */
export interface TaskRun {
  /**
   * 任务id
   */
  id: number;
  /**
   * 任务组别
   */
  taskGroup: string;
}
