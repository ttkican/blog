package com.ican.strategy.impl;

import com.ican.config.properties.QiniuProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * qiniu上传策略
 *
 * @author ican
 */
@Slf4j
@Service("qiniuUploadStrategyImpl")
public class QiniuUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private QiniuProperties qiniuProperties;

    @Override
    public Boolean exists(String filePath) {
        return false;
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        try {
            if (path.length() > 1 && path.charAt(0) == '/') {
                path = path.substring(1);
            }
            // 认证信息实例
            Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
            // 构建一个七牛上传工具实例
            UploadManager uploadManager = new UploadManager(qiNiuConfig());
            // 上传图片文件
            Response res = uploadManager.put(inputStream, path + fileName, auth.uploadToken(qiniuProperties.getBucketName()), null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res);
            }
        } catch (Exception e) {
            log.error("上传七牛出错，{}", e.getMessage());
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        // 1./  --> /    2./xxx/ --> xxx/
        if (filePath.length() > 1 && filePath.charAt(0) == '/') {
            filePath = filePath.substring(1);
        }
        return qiniuProperties.getUrl() + filePath;
    }

    /**
     * 配置空间的存储区域
     */
    private com.qiniu.storage.Configuration qiNiuConfig() {
        switch (qiniuProperties.getRegion()) {
            case "huadong":
                return new com.qiniu.storage.Configuration(Region.huadong());
            case "huabei":
                return new com.qiniu.storage.Configuration(Region.huabei());
            case "huanan":
                return new com.qiniu.storage.Configuration(Region.huanan());
            case "beimei":
                return new com.qiniu.storage.Configuration(Region.beimei());
            default:
                throw new RuntimeException("存储区域配置错误");
        }
    }

}
