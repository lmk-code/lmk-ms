package com.lmk.ms.tp.wx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 三方平台基础配置
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/25
 */
@Component
@ConfigurationProperties(prefix = "ms.config.tp.basic")
public class TpBasicProperties {

    /** 当前服务端域名 */
    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
