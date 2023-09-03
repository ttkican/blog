package com.ican.config.properties;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
public class QiniuProperties {

    private String url;//url 或者 域名

    private String bucketName; //存储桶名字

    private String region;//区域 如huanan hubei

    private String accessKey;//accessKey

    private String secretKey;//secretKey




    /**
     * 配置空间的存储区域
     */
    @Bean
    public com.qiniu.storage.Configuration qiNiuConfig() {
        switch (region) {
            case "huadong":
                return new com.qiniu.storage.Configuration(Zone.huadong());
            case "huabei":
                return new com.qiniu.storage.Configuration(Zone.huabei());
            case "huanan":
                return new com.qiniu.storage.Configuration(Zone.huanan());
            case "beimei":
                return new com.qiniu.storage.Configuration(Zone.beimei());
            default:
                throw new RuntimeException("存储区域配置错误");
        }
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiNiuConfig());
    }

    /**
     * 认证信息实例
     */
    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiNiuConfig());
    }


}
