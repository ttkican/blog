import { PageQuery } from "@/model";

/**
 * 照片
 */
export interface Photo {
  /**
   * 照片id
   */
  id: number;
  /**
   * 照片名
   */
  photoName: string;
  /**
   * 照片描述
   */
  photoDesc: string;
  /**
   * 照片地址
   */
  photoUrl: string;
}

/**
 * 照片查询参数
 */
export interface PhotoQuery extends PageQuery {
  /**
   * 相册id
   */
  albumId: number;
}

/**
 * 相册信息
 */
export interface AlbumInfo {
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
 * 照片表单
 */
export interface PhotoForm {
  /**
   * 照片id
   */
  id: number;
  /**
   * 照片名
   */
  photoName: string;
  /**
   * 照片描述
   */
  photoDesc: string;
}

/**
 * 照片表单
 */
export interface PhotoInfo {
  /**
   * 相册id
   */
  albumId: number;
  /**
   * 照片
   */
  photoUrlList: string[];
}