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
}

/**
 * 照片信息
 */
export interface PhotoInfo {
  /**
   * 相册名
   */
  albumName: string;
  /**
   * 照片列表
   */
  photoVOList: Photo[];
}

/**
 * 照片
 */
export interface Photo {
  /**
   * 照片id
   */
  id: number;
  /**
   * 照片链接
   */
  photoUrl: string;
}
