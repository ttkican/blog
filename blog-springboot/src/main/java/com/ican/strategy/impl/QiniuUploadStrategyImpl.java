package com.ican.strategy.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.ican.config.properties.OssProperties;
import com.ican.config.properties.QiniuProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;


    @Override
    public Boolean exists(String filePath) {
        return false;
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        try {
            if (path.charAt(0) == '/') {
                path = path.substring(1);
            }
            // 上传图片文件
            Response res = uploadManager.put(inputStream, path + fileName,auth.uploadToken(qiniuProperties.getBucketName()), null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return qiniuProperties.getUrl() + filePath;
    }

}
