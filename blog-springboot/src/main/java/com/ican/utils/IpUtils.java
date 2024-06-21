package com.ican.utils;

import com.ican.model.dto.BiliIpInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * IP地址工具类
 *
 * @author ican
 */
@Slf4j
public class IpUtils {

    /**
     * 根据ip调用B站接口获取地理位置
     *
     * @param ip ip
     * @return 地理位置
     */
    public static String getIpSource(String ip) {
        try {
            Map<String, String> paraMap = new HashMap<>(1);
            paraMap.put("ip", ip);
            String uri = "https://api.live.bilibili.com/client/v1/Ip/getInfoNew?ip={ip}";
            RestTemplate restTemplate = new RestTemplate();
            BiliIpInfoDTO ipInfoDTO = restTemplate.getForObject(uri, BiliIpInfoDTO.class, paraMap);
            if (ipInfoDTO != null && ipInfoDTO.getCode() == 0) {
                BiliIpInfoDTO.IpInfoData ipInfoData = ipInfoDTO.getData();
                if (ipInfoData != null) {
                    return ipInfoData.getCountry() + "|" + ipInfoData.getProvince() + "|" + ipInfoData.getCity() + "|" + ipInfoData.getIsp();
                }
            }
        } catch (Exception e) {
            log.info("getIpSource fail, e", e);
            return null;
        }
        return null;
    }

}
