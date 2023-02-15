package com.ican.strategy.impl;

import com.ican.config.properties.CosProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * cos上传策略
 *
 * @author ican
 */
@Slf4j
@Service("cosUploadStrategyImpl")
public class CosUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private CosProperties cosProperties;

    @Override
    public Boolean exists(String filePath) {
        return getCosClient().doesObjectExist(cosProperties.getBucketName(), filePath);
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        COSClient cosClient = getCosClient();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
            objectMetadata.setContentLength(inputStream.available());
            // 调用cos方法上传
            cosClient.putObject(cosProperties.getBucketName(), path + fileName, inputStream, objectMetadata);
        } catch (CosServiceException e) {
            log.error("Error Message:" + e.getErrorMessage());
            log.error("Error Code:" + e.getErrorCode());
            log.info("Request ID:" + e.getRequestId());
        } catch (CosClientException e) {
            log.error("Caught an CosClientException, Error Message:" + e.getMessage());
        } catch (IOException e) {
            log.error("Caught an IOException, Error Message:" + e.getMessage());
        } finally {
            cosClient.shutdown();
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return cosProperties.getUrl() + filePath;
    }

    /**
     * 获取cosClient
     *
     * @return {@link COSClient} cosClient
     */
    private COSClient getCosClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region(cosProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }
}
