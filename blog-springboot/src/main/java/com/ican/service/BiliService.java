package com.ican.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * B站服务接口
 *
 * @author ican
 * @date 2023/06/09 17:46
 **/
public interface BiliService {

    /**
     * B站图片上传
     *
     * @param file 图片
     * @param csrf csrf
     * @param data data
     * @return 图片链接
     */
    String uploadBiliPicture(MultipartFile file, String csrf, String data);
}