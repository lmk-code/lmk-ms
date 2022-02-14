package com.lmk.ms.common.utils;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import com.lmk.ms.common.auth.config.CookieProperties;
import com.lmk.ms.common.auth.config.JwtProperties;

/**
 * Spring Cloud Gateway 通用工具
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
public class GatewayUtils {

    /**
     * 获取请求头信息
     * @param request
     * @param name
     * @return
     */
    public static String getHeader(ServerHttpRequest request, String name) {
        return request.getHeaders().getFirst(name);
    }

    /**
     * 网关返回错误信息
     * @param response
     * @param status
     * @param data
     * @return
     */
    public static Mono<Void> getErrorResponseMono(ServerHttpResponse response, HttpStatus status, Object data){
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        if(data != null){
            String msg = null;
            if(data instanceof String) {
                msg = (String) data;
            }else{
                msg = JsonUtils.toJSON(data);
            }
            DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }

        return response.setComplete();
    }

    public static String getAccessToken(JwtProperties jwtProperties, ServerHttpRequest request) {
        // 通过 header 获取
        String headerName = jwtProperties.getHeaderName();
        String headerPrefix = jwtProperties.getHeaderPrefix();
        boolean findInHeader = false;
        HttpHeaders headers = request.getHeaders();
        if (headers.containsKey(headerName)) {
            findInHeader = true;
            String header = headers.getFirst(headerName);
            if (StringUtils.startsWithIgnoreCase(header, headerPrefix)) {
                return header.substring(headerPrefix.length());
            }
        }

        if (!findInHeader) {
            CookieProperties cookieProperties = jwtProperties.getCookie();
            String cookieName = cookieProperties.getName();
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            if (cookies != null && cookies.containsKey(cookieName)) {
                return cookies.get(cookieName).get(0).getValue();
            }
        }
        log.info("解析JwtToken失败");
        return null;
    }
}
