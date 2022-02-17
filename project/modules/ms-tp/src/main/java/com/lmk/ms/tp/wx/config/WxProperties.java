package com.lmk.ms.tp.wx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信接入配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/17
 */
@Component
@ConfigurationProperties(prefix = "ms.config.tp.wx")
public class WxProperties {

    /** 是否启用 */
    private Boolean enabled;

    /** 应用ID */
    private String appId;

    /** 应用密钥 */
    private String appSecret;

    /** 校验Token */
    private String token;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
