import { PageQuery } from "@/model";

/**
 * 文件
 */
export interface BlogFile {
  /**
   * 文件id
   */
  id: number;
  /**
   * 文件url
   */
  fileUrl: string;
  /**
   * 文件名
   */
  fileName: string;
  /**
   * 文件大小
   */
  fileSize: number;
  /**
   * 文件扩展名
   */
  extendName: string;
  /**
   * 文件路径
   */
  filePath: string;
  /**
   * 是否为目录 (0否 1是)
   */
  isDir: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 文件查询参数
 */
export interface FileQuery extends PageQuery {
  /**
   * 文件路径
   */
  filePath?: string;
}

/**
 * 文件夹信息
 */
export interface FolderForm {
  /**
   * 文件路径
   */
  filePath: string;
  /**
   * 文件名称
   */
  fileName: string;
}
