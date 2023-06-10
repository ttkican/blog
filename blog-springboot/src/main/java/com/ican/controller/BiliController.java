package com.ican.controller;

import com.ican.model.vo.Result;
import com.ican.service.BiliService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * B站服务控制器
 *
 * @author ican
 * @date 2023/03/24 19:48
 **/
@Api(tags = "B站服务模块")
@RestController
public class BiliController {

    @Autowired
    private BiliService biliService;

    /**
     * B站图片上传
     *
     * @param file 文件
     * @return {@link Result <String>} 图片地址
     */
    @ApiOperation(value = "B站图片上传")
    @PostMapping("/bili/upload")
    public Result<String> biliUpload(@RequestParam("file_up") MultipartFile file,
                                     @RequestParam(value = "csrf") String csrf,
                                     @RequestParam(value = "data") String data) {
        return Result.success(biliService.uploadBiliPicture(file, csrf, data));
    }
}