import { PageQuery } from "@/model";

/**
 * 轮播图
 */
export interface Carousel {
  /**
   * 轮播图id
   */
  id: number;
  /**
   * 轮播图地址
   */
  imgUrl: string;
  /**
   * 是否显示（0否 1是）
   */
  status: number;
  /**
   * 备注
   */
  remark: string;
  /**
   * 创建时间
   */
  createTime: string;
  /**
   * 更新时间
   */
  updateTime: string;
}

/**
 * 轮播图查询参数
 */
export interface CarouselQuery extends PageQuery {
  /**
   * 是否显示 (0否 1是)
   */
  status?: number;
}

/**
 * 轮播图表单
 */
export interface CarouselForm {
  /**
   * 轮播图id
   */
  id?: number;
  /**
   * 轮播图地址
   */
  imgUrl: string;
  /**
   * 是否显示（0否 1是）
   */
  status: number;
  /**
   * 备注
   */
  remark: string;
}

/**
 * 轮播图状态
 */
export interface CarouselStatus {
  /**
   * id
   */
  id: number;
  /**
   * 是否显示 (0否 1是)
   */
  status: number;
}
