package com.ican.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Article;
import com.ican.entity.Category;
import com.ican.mapper.ArticleMapper;
import com.ican.mapper.CategoryMapper;
import com.ican.model.vo.response.ArticleConditionList;
import com.ican.model.vo.response.ArticleConditionResp;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.ArticleConditionQuery;
import com.ican.model.vo.query.CategoryQuery;
import com.ican.model.vo.request.CategoryReq;
import com.ican.model.vo.response.CategoryBackResp;
import com.ican.model.vo.response.CategoryOptionResp;
import com.ican.model.vo.response.CategoryResp;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 分类服务
 *
 * @author ican
 * @date 2022/12/02 17:35
 **/
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public PageResult<CategoryBackResp> listCategoryBackVO(CategoryQuery categoryQuery) {
        // 查询分类数量
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.hasText(categoryQuery.getKeyword()), Category::getCategoryName,
                        categoryQuery.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类列表
        List<CategoryBackResp> categoryList = categoryMapper.selectBackCategoryList(categoryQuery);
        return new PageResult<>(categoryList, count);
    }

    public void addCategory(CategoryReq category) {
        // 分类是否存在
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, category.getCategoryName()));
        Assert.isNull(existCategory, category.getCategoryName() + "分类已存在");
        // 添加新分类
        Category newCategory = Category.builder()
                .categoryName(category.getCategoryName())
                .build();
        baseMapper.insert(newCategory);
    }

    public void deleteCategory(List<Integer> categoryIdList) {
        // 分类下是否有文章
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        Assert.isFalse(count > 0, "删除失败，分类下存在文章");
        // 批量删除分类
        categoryMapper.deleteBatchIds(categoryIdList);
    }

    public void updateCategory(CategoryReq category) {
        // 分类是否存在
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, category.getCategoryName()));
        Assert.isFalse(Objects.nonNull(existCategory) && !existCategory.getId().equals(category.getId()),
                category.getCategoryName() + "分类已存在");
        // 修改分类
        Category newCategory = Category.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
        baseMapper.updateById(newCategory);
    }

    public List<CategoryOptionResp> listCategoryOption() {
        // 查询分类
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByDesc(Category::getId));
        return BeanCopyUtils.copyBeanList(categoryList, CategoryOptionResp.class);
    }

    public List<CategoryResp> listCategoryVO() {
        return categoryMapper.selectCategoryVO();
    }

    public ArticleConditionList listArticleCategory(ArticleConditionQuery articleConditionQuery) {
        List<ArticleConditionResp> articleConditionList = articleMapper.selectArticleListByCondition(articleConditionQuery);
        String name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .select(Category::getCategoryName)
                        .eq(Category::getId, articleConditionQuery.getCategoryId()))
                .getCategoryName();
        return ArticleConditionList.builder()
                .articleConditionVOList(articleConditionList)
                .name(name)
                .build();
    }

}