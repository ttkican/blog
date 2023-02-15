package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.annotation.VisitLogger;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.TagDTO;
import com.ican.model.vo.*;
import com.ican.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 标签控制器
 *
 * @author ican
 * @date 2022/12/02 21:58
 **/
@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查看后台标签列表
     *
     * @param condition 查询条件
     * @return {@link TagBackVO} 后台标签
     */
    @ApiOperation(value = "查看后台标签列表")
    @SaCheckPermission("blog:tag:list")
    @GetMapping("/admin/tag/list")
    public Result<PageResult<TagBackVO>> listTagBackVO(ConditionDTO condition) {
        return Result.success(tagService.listTagBackVO(condition));
    }

    /**
     * 添加标签
     *
     * @param tag 标签信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加标签")
    @SaCheckPermission("blog:tag:add")
    @PostMapping("/admin/tag/add")
    public Result<?> addTag(@Validated @RequestBody TagDTO tag) {
        tagService.addTag(tag);
        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param tagIdList 标签id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除分类")
    @SaCheckPermission("blog:tag:delete")
    @DeleteMapping("/admin/tag/delete")
    public Result<?> deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.success();
    }

    /**
     * 修改标签
     *
     * @param tag 标签信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改标签")
    @SaCheckPermission("blog:tag:update")
    @PutMapping("/admin/tag/update")
    public Result<?> updateTag(@Validated @RequestBody TagDTO tag) {
        tagService.updateTag(tag);
        return Result.success();
    }

    /**
     * 查看标签选项
     *
     * @return {@link Result<TagOptionVO>} 标签列表
     */
    @ApiOperation(value = "查看标签选项")
    @GetMapping("/admin/tag/option")
    public Result<List<TagOptionVO>> listTagOption() {
        return Result.success(tagService.listTagOption());
    }

    /**
     * 查看标签列表
     *
     * @return {@link Result<TagVO>} 标签列表
     */
    @VisitLogger(value = "文章标签")
    @ApiOperation(value = "查看标签列表")
    @GetMapping("/tag/list")
    public Result<List<TagVO>> listTagVO() {
        return Result.success(tagService.listTagVO());
    }

    /**
     * 查看标签下的文章
     *
     * @param condition 查询条件
     * @return 文章列表
     */
    @VisitLogger(value = "标签文章")
    @ApiOperation(value = "查看标签下的文章")
    @GetMapping("/tag/article")
    public Result<ArticleConditionList> listArticleTag(ConditionDTO condition) {
        return Result.success(tagService.listArticleTag(condition));
    }
}