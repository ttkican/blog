package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.Category;
import com.ican.model.dto.CategoryDTO;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.*;

import java.util.List;

/**
 * 分类业务接口
 *
 * @author ican
 * @date 2022/12/02 17:33
 **/
public interface CategoryService extends IService<Category> {

    /**
     * 查看后台分类列表
     *
     * @param condition 查询条件
     * @return 后台分类列表
     */
    PageResult<CategoryBackVO> listCategoryBackVO(ConditionDTO condition);

    /**
     * 添加分类
     *
     * @param category 分类
     */
    void addCategory(CategoryDTO category);

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id集合
     */
    void deleteCategory(List<Integer> categoryIdList);

    /**
     * 修改分类
     *
     * @param category 分类
     */
    void updateCategory(CategoryDTO category);

    /**
     * 查看分类选项
     *
     * @return 分类列表
     */
    List<CategoryOptionVO> listCategoryOption();

    /**
     * 查看分类列表
     *
     * @return 分类列表
     */
    List<CategoryVO> listCategoryVO();

    /**
     * 查看分类下的文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticleConditionList listArticleCategory(ConditionDTO condition);
}