package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.annotation.VisitLogger;
import com.ican.model.vo.response.ArticleConditionList;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.ArticleConditionQuery;
import com.ican.model.vo.query.TagQuery;
import com.ican.model.vo.request.TagReq;
import com.ican.model.vo.response.TagBackResp;
import com.ican.model.vo.response.TagOptionResp;
import com.ican.model.vo.response.TagResp;
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
     * @param tagQuery 标签查询条件
     * @return {@link TagBackResp} 后台标签
     */
    @ApiOperation(value = "查看后台标签列表")
    @SaCheckPermission("blog:tag:list")
    @GetMapping("/admin/tag/list")
    public Result<PageResult<TagBackResp>> listTagBackVO(TagQuery tagQuery) {
        return Result.success(tagService.listTagBackVO(tagQuery));
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
    public Result<?> addTag(@Validated @RequestBody TagReq tag) {
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
    public Result<?> updateTag(@Validated @RequestBody TagReq tag) {
        tagService.updateTag(tag);
        return Result.success();
    }

    /**
     * 查看标签选项
     *
     * @return {@link Result< TagOptionResp >} 标签列表
     */
    @ApiOperation(value = "查看标签选项")
    @GetMapping("/admin/tag/option")
    public Result<List<TagOptionResp>> listTagOption() {
        return Result.success(tagService.listTagOption());
    }

    /**
     * 查看标签列表
     *
     * @return {@link Result< TagResp >} 标签列表
     */
    @VisitLogger(value = "文章标签")
    @ApiOperation(value = "查看标签列表")
    @GetMapping("/tag/list")
    public Result<List<TagResp>> listTagVO() {
        return Result.success(tagService.listTagVO());
    }

    /**
     * 查看标签下的文章
     *
     * @param articleConditionQuery 查询条件
     * @return 文章列表
     */
    @VisitLogger(value = "标签文章")
    @ApiOperation(value = "查看标签下的文章")
    @GetMapping("/tag/article")
    public Result<ArticleConditionList> listArticleTag(ArticleConditionQuery articleConditionQuery) {
        return Result.success(tagService.listArticleTag(articleConditionQuery));
    }
}