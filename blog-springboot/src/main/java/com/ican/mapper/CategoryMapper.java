package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Category;
import com.ican.model.vo.query.CategoryQuery;
import com.ican.model.vo.response.CategoryBackResp;
import com.ican.model.vo.response.CategoryResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类 Mapper
 *
 * @author ican
 * @date 2022/12/02 17:34
 **/
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询后台分类列表
     *
     * @param categoryQuery 分类查询条件
     * @return 后台分类列表
     */
    List<CategoryBackResp> selectBackCategoryList(@Param("param") CategoryQuery categoryQuery);

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    List<CategoryResp> selectCategoryVO();
}