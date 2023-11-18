package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.BlogFile;
import com.ican.model.vo.query.FileQuery;
import com.ican.model.vo.response.FileResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件Mapper
 *
 * @author ican
 */
@Repository
public interface BlogFileMapper extends BaseMapper<BlogFile> {

    /**
     * 查询后台文件列表
     *
     * @param fileQuery 文件条件
     * @return 后台文件列表
     */
    List<FileResp> selectFileVOList(@Param("param") FileQuery fileQuery);
}




