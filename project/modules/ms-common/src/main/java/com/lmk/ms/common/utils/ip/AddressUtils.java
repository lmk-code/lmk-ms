package com.lmk.ms.common.utils.ip;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 获取地址类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/11/01
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(RestTemplate restTemplate, String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            AddressResult result = restTemplate.getForObject(IP_URL + "?ip=" + ip + "&json=true", AddressResult.class);
            if (result == null) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            return String.format("%s %s", result.getPro(), result.getCity());
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }

    @Data
    static class AddressResult{
        private String ip;
        private String pro;
        private String proCode;
        private String city;
        private String cityCode;
        private String region;
        private String regionCode;
        private String addr;
        private String regionNames;
        private String err;
    }
}
