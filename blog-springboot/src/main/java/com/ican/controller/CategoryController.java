package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.annotation.VisitLogger;
import com.ican.model.dto.CategoryDTO;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.*;
import com.ican.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 分类控制器
 *
 * @author ican
 * @date 2022/12/02 17:32
 **/
@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看后台分类列表
     *
     * @param condition 查询条件
     * @return {@link CategoryBackVO} 后台分类
     */
    @ApiOperation(value = "查看后台分类列表")
    @SaCheckPermission("blog:category:list")
    @GetMapping("/admin/category/list")
    public Result<PageResult<CategoryBackVO>> listCategoryBackVO(ConditionDTO condition) {
        return Result.success(categoryService.listCategoryBackVO(condition));
    }

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加分类")
    @SaCheckPermission("blog:category:add")
    @PostMapping("/admin/category/add")
    public Result<?> addCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除分类")
    @SaCheckPermission("blog:category:delete")
    @DeleteMapping("/admin/category/delete")
    public Result<?> deleteCategory(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.success();
    }

    /**
     * 修改分类
     *
     * @param category 分类信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改分类")
    @SaCheckPermission("blog:category:update")
    @PutMapping("/admin/category/update")
    public Result<?> updateCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    /**
     * 查看分类选项
     *
     * @return {@link Result<CategoryOptionVO>} 分类列表
     */
    @ApiOperation(value = "查看分类选项")
    @GetMapping("/admin/category/option")
    public Result<List<CategoryOptionVO>> listCategoryOption() {
        return Result.success(categoryService.listCategoryOption());
    }

    /**
     * 查看分类列表
     *
     * @return {@link Result<CategoryDTO>} 分类列表
     */
    @VisitLogger(value = "文章分类")
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/category/list")
    public Result<List<CategoryVO>> listCategoryVO() {
        return Result.success(categoryService.listCategoryVO());
    }

    /**
     * 查看分类下的文章
     *
     * @param condition 查询条件
     * @return 文章列表
     */
    @VisitLogger(value = "分类文章")
    @ApiOperation(value = "查看分类下的文章")
    @GetMapping("/category/article")
    public Result<ArticleConditionList> listArticleCategory(ConditionDTO condition) {
        return Result.success(categoryService.listArticleCategory(condition));
    }

}