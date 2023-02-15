/**
 * 标签
 */
export interface Tag {
  /**
   * 标签id
   */
  id: number;
  /**
   * 标签名
   */
  tagName: string;
  /**
   * 文章数量
   */
  articleCount: number;
}

/**
 * 标签VO
 */
export interface TagVO {
  /**
   * 标签id
   */
  id: number;
  /**
   * 标签名
   */
  tagName: string;
}
