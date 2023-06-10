package com.ican.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HttpClient工具类
 *
 * @author ican
 */
@Slf4j
public class HttpClientUtils {

    /**
     * 默认超时时间5秒
     */
    private static final int DEFAULT_TIMEOUT = 5 * 1000;

    /**
     * HttpClient上传文件
     *
     * @param url     请求接口
     * @param csrf    校验参数
     * @param headers 请求参数
     * @param file    文件
     * @return 响应结果
     */
    public static String uploadFileByHttpClient(String url, String csrf, Map<String, String> headers, MultipartFile file) {
        String resultString = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建http post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_TIMEOUT)
                    .setConnectionRequestTimeout(DEFAULT_TIMEOUT).setSocketTimeout(DEFAULT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            // 设置参数信息
            // HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            builder.setCharset(StandardCharsets.UTF_8);
            // 设置文件
            builder.addBinaryBody("file_up", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getName());
            // 设置必要参数
            builder.addTextBody("csrf", csrf);
            httpPost.setEntity(builder.build());
            // 设置头信息
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.get(key));
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            }
        } catch (Exception e) {
            log.error("上传文件失败：", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("关闭异常" + e);
            }
        }
        return resultString;
    }

}