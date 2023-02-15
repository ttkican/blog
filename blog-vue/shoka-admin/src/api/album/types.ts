import { PageQuery } from "@/model";

/**
 * 相册
 */
export interface Album {
  /**
   * 相册id
   */
  id: number;
  /**
   * 相册名
   */
  albumName: string;
  /**
   * 相册封面
   */
  albumCover: string;
  /**
   * 相册描述
   */
  albumDesc: string;
  /**
   * 照片数量
   */
  photoCount: number;
  /**
   * 状态 (1公开 2私密)
   */
  status: number;
}

/**
 * 相册查询参数
 */
export interface AlbumQuery extends PageQuery {
  /**
   * 关键词
   */
  keyword?: string;
}

/**
 * 相册
 */
export interface AlbumForm {
  /**
   * 相册id
   */
  id?: number;
  /**
   * 相册名
   */
  albumName: string;
  /**
   * 相册封面
   */
  albumCover: string;
  /**
   * 相册描述
   */
  albumDesc: string;
  /**
   * 状态 (1公开 2私密)
   */
  status: number;
}
