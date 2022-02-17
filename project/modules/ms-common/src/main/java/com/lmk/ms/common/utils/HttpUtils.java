package com.lmk.ms.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import eu.bitwalker.useragentutils.UserAgent;
import com.lmk.ms.common.api.ResponseResult;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.auth.config.CookieProperties;
import com.lmk.ms.common.auth.config.JwtProperties;
import com.lmk.ms.common.utils.html.EscapeUtil;

/**
 * Http工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/06
 */
@Slf4j
public class HttpUtils {

    /**
     * 获取访问令牌
     * @return
     */
    public static String getToken() {
        return getHttpServletRequest().getHeader("Authorization");
    }

    /**
     * 获取访问令牌
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    /**
     * 获取请求头信息
     * @param name
     * @return
     */
    public static String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    /**
     * 获取请求头信息
     * @param request
     * @param name
     * @return
     */
    public static String getHeader(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    /**
     * 从当前线程中获取 HttpServletRequest 对象
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            log.warn("获取HttpServletRequest失败：", e);
            return null;
        }
    }

    /**
     * 从当前线程中获取 HttpServletResponse 对象
     * @return
     */
    public static HttpServletResponse getHttpServletResponse() {
        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        } catch (Exception e) {
            log.warn("获取HttpServletResponse失败：", e);
            return null;
        }
    }

    /**
     * 向客户端写入纯文本
     * @param text
     */
    public static void writeText(String text){
        HttpServletResponse response = getHttpServletResponse();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.warn("向客户端写入信息出错：", e);
        }
    }

    /**
     * 向客户端返回异常信息
     * @param status
     */
    public static void writeError(StatusEnum status){
        HttpServletResponse response = getHttpServletResponse();
        response.setStatus(status.httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ResponseResult<?> result = ResponseResult.error(status);
        try {
            response.getWriter().write(JsonUtils.toJSON(result));
        } catch (IOException e) {
            log.warn("向客户端写入信息出错：", e);
        }
    }

    /**
     * 向客户端返回异常信息
     * @param response
     * @param status
     */
    public static void writeError(HttpServletResponse response, StatusEnum status){
        response.setStatus(status.httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ResponseResult<?> result = ResponseResult.error(status);
        try {
            response.getWriter().write(JsonUtils.toJSON(result));
        } catch (IOException e) {
            log.warn("向客户端写入信息出错：", e);
        }
    }

    /**
     * 向客户端返回异常信息
     * @param response
     * @param httpStatusCode
     * @param status
     */
    public static void writeError(HttpServletResponse response, int httpStatusCode, StatusEnum status){
        response.setStatus(httpStatusCode);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ResponseResult<?> result = ResponseResult.error(status);
        try {
            response.getWriter().write(JsonUtils.toJSON(result));
        } catch (IOException e) {
            log.warn("向客户端写入信息出错：", e);
        }
    }

    /**
     * 获取客户端IP地址
     * @return
     */
    public static String getClientIp() {
        return getClientIp(getHttpServletRequest());
    }

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : EscapeUtil.clean(ip);
    }

    /**
     * 获取UA信息
     * @return
     */
    public static UserAgent getUserAgent(){
        return getUserAgent(getHttpServletRequest());
    }

    /**
     * 获取UA信息
     * @param request
     * @return
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent;
    }

    /**
     * 获取Post参数
     * @return
     */
    public static String getPostData() {
        return getPostData(getHttpServletRequest());
    }

    /**
     * 获取Post参数
     * @param request
     * @return
     */
    public static String getPostData(HttpServletRequest request) {
        if(request == null){
            return null;
        }

        StringBuilder data = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())){
                data.append(line);
            }
        } catch (IOException e) {
            log.warn("读取参数异常：", e);
        }
        return data.toString();
    }

    /**
     * 获取客户端传递的JWT访问令牌
     * @param jwtProperties
     * @return
     */
    public static String getAccessToken(JwtProperties jwtProperties) {
        HttpServletRequest request = getHttpServletRequest();

        // 通过 header 获取
        String headerName = jwtProperties.getHeaderName();
        String headerPrefix = jwtProperties.getHeaderPrefix();
        String header = request.getHeader(headerName);
        if(StringUtils.isNotBlank(header) && StringUtils.startsWithIgnoreCase(header, headerPrefix)){
            return header.substring(headerPrefix.length());
        }
        CookieProperties cookieProperties = jwtProperties.getCookie();
        String cookieName = cookieProperties.getName();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        log.warn("解析JwtToken失败");
        return null;
    }

    /**
     * 构建URL查询参数
     * @param params
     * @return
     */
    public static String buildQueryParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if(params != null && params.size() > 0){
            sb.append("?");
            params.forEach( (k, v) -> sb.append(k).append("=").append(v).append("&"));
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
