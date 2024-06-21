package com.ican.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ican.model.dto.BiliUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * B站服务
 *
 * @author ican
 * @date 2023/06/09 17:47
 **/
@Slf4j
@Service
public class BiliService {

    @Value("${bili-url}")
    private String biliUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String uploadBiliPicture(MultipartFile file, String csrf, String sess) {
        try {
            HttpHeaders headers = new HttpHeaders();
            // 设置提交方式都是表单提交
            headers.set("Cookie", "SESSDATA=" + sess);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
            multipartBodyBuilder.part("file_up", file.getResource());
            URI uri = UriComponentsBuilder.fromUriString(biliUrl)
                    .queryParam("csrf", csrf)
                    .build()
                    .toUri();
            ResponseEntity<String> response = restTemplate.exchange(uri,
                    HttpMethod.POST,
                    new HttpEntity<>(multipartBodyBuilder.build(), headers),
                    String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            BiliUploadDTO biliUploadDTO = objectMapper.readValue(response.getBody(), BiliUploadDTO.class);
            BiliUploadDTO.UploadData data = biliUploadDTO.getData();
            if (data != null) {
                return data.getImageUrl().replaceFirst("http", "https");
            }
            return null;
        } catch (Exception e) {
            log.error("uploadBiliPicture fail, ", e);
            return null;
        }
    }
}