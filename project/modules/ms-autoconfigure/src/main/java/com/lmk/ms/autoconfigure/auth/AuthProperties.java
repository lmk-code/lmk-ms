package com.lmk.ms.autoconfigure.auth;

import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.lmk.ms.common.auth.config.CookieProperties;
import com.lmk.ms.common.auth.config.JwtProperties;

/**
 * 用户授权自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Component
@ConfigurationProperties(prefix = "ms.config.auth")
public class AuthProperties {

    /** 是否启用 */
    private boolean enabled = true;

    /** JWT配置 */
    private JwtProperties jwt;

    public AuthProperties() {
    }

    @PostConstruct
    public void initJwtProperties(){
        if(this.jwt == null){
            this.jwt = new JwtProperties();
        }
        if(this.jwt.getCookie() == null){
            this.jwt.setCookie(new CookieProperties());
        }
        this.jwt.initKeyPair();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }
}
