package com.lmk.ms.autoconfigure.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Ms Web层自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Component
@ConfigurationProperties(prefix = "ms.config.web")
public class WebProperties {

    /** 跨域请求过滤，默认不开启 */
    private boolean enableCorsFilter = false;

    /** HTTP链接读取超时时间，单位为ms */
    private int readTimeout = 60000;

    /** HTTP链接创建连接超时时间，单位为ms */
    private int connectTimeout = 60000;

    public WebProperties() {
    }

    public boolean isEnableCorsFilter() {
        return enableCorsFilter;
    }

    public void setEnableCorsFilter(boolean enableCorsFilter) {
        this.enableCorsFilter = enableCorsFilter;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
