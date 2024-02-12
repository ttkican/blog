package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Carousel;
import com.ican.model.vo.query.CarouselQuery;
import com.ican.model.vo.response.CarouselBackResp;
import com.ican.model.vo.response.CarouselResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图Mapper
 *
 * @author ican
 */
@Repository
public interface CarouselMapper extends BaseMapper<Carousel> {

    /**
     * 查询后台轮播图列表
     *
     * @param carouselQuery 轮播图查询条件
     * @return 后台轮播图列表
     */
    List<CarouselBackResp> selectBackCarouselList(@Param("param") CarouselQuery carouselQuery);

    /**
     * 查看轮播图列表
     *
     * @return 轮播图列表
     */
    List<CarouselResp> selectCarouselList();

}




