package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Photo;
import com.ican.model.vo.query.PhotoQuery;
import com.ican.model.vo.response.PhotoBackResp;
import com.ican.model.vo.response.PhotoResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 照片 Mapper
 *
 * @author ican
 */
@Repository
public interface PhotoMapper extends BaseMapper<Photo> {

    /**
     * 查询后台照片列表
     *
     * @param photoQuery 照片查询条件
     * @return 后台照片列表
     */
    List<PhotoBackResp> selectBackPhotoList(@Param("param") PhotoQuery photoQuery);

    /**
     * 查询照片列表
     *
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoResp> selectPhotoVOList(@Param("albumId") Integer albumId);
}