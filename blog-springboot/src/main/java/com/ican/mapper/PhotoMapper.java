package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Photo;
import com.ican.model.vo.PhotoBackVO;
import com.ican.model.vo.PhotoVO;
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
     * @param limit   页码
     * @param size    大小
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoBackVO> selectPhotoBackVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("albumId") Integer albumId);

    /**
     * 查询照片列表
     *
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoVO> selectPhotoVOList(@Param("albumId") Integer albumId);
}