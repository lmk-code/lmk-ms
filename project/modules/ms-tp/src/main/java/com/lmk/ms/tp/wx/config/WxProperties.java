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

    /** 作用域：不弹出授权页面，直接跳转，只能获取用户openid */
    public static final String SCOPE_BASE = "snsapi_base";

    /** 作用域：弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 */
    public static final String SCOPE_INFO = "snsapi_userinfo";

    /** 是否启用 */
    private Boolean enabled;

    /** 应用ID */
    private String appId;

    /** 应用密钥 */
    private String appSecret;

    /** 校验Token */
    private String token;

    /** OAuth获取Code的回调地址 */
    private String authCodeRedirectUrl;

    /** OAuth获取Code的回调地址 */
    private String authState;

    /** 用户绑定页面地址 */
    private String userBindUrl;

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

    public String getAuthCodeRedirectUrl() {
        return authCodeRedirectUrl;
    }

    public void setAuthCodeRedirectUrl(String authCodeRedirectUrl) {
        this.authCodeRedirectUrl = authCodeRedirectUrl;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public String getUserBindUrl() {
        return userBindUrl;
    }

    public void setUserBindUrl(String userBindUrl) {
        this.userBindUrl = userBindUrl;
    }
}
