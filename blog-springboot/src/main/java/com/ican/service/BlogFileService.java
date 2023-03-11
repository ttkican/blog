package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.BlogFile;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.FolderDTO;
import com.ican.model.vo.FileVO;
import com.ican.model.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件业务接口
 *
 * @author ican
 */
public interface BlogFileService extends IService<BlogFile> {

    /**
     * 查看文件列表
     *
     * @param condition 查询条件
     * @return 文件列表
     */
    PageResult<FileVO> listFileVOList(ConditionDTO condition);

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 文件路径
     */
    void uploadFile(MultipartFile file, String path);

    /**
     * 创建文件夹
     *
     * @param folder 文件夹信息
     */
    void createFolder(FolderDTO folder);

    /**
     * 删除文件
     *
     * @param fileIdList 文件id列表
     */
    void deleteFile(List<Integer> fileIdList);

    /**
     * 下载文件
     *
     * @param fileId 文件id
     */
    void downloadFile(Integer fileId);
}
