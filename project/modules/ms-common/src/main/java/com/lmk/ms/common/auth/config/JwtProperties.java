package com.lmk.ms.common.auth.config;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.springframework.util.Assert;
import com.lmk.ms.common.config.Constants;
import com.lmk.ms.common.utils.encrypt.RSAEncrypt;

/**
 * Jwt配置对象
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
public class JwtProperties {

    /** Cookie配置 */
    private CookieProperties cookie;

    /** 是否使用微精灵的Token校验 */
    private Boolean useWgToken = false;

    /** 是否使用IDP的Token校验 */
    private Boolean useIdpToken = false;

    /** 认证要求的请求头部名称，默认: Authorization */
    private String headerName = "Authorization";

    /** 认证要求的JWT前缀，默认: Bearer */
    private String headerPrefix = "Bearer ";

    /** 登录成功后向HTTP请求头中添加用户ID */
    private String headerUserId = Constants.HEADER_USER_ID;

    /** 登录成功后向HTTP请求头中添加用户手机号 */
    private String headerUserMobile = Constants.HEADER_USER_MOBILE;

    /** token失效时长，单位: 秒，默认: 一小时 */
    private Long expiration = 3600L;

    /** jwt公钥 */
    private String publicKey;

    /** jwt私钥 */
    private String privateKey;

    /** 密钥对，不需要在配置文件中设置 */
    private KeyPair keyPair;

    public JwtProperties() {
    }

    public void initKeyPair(){
        PublicKey pubKey = null;
        PrivateKey priKey = null;

        pubKey = RSAEncrypt.getPublicKey(this.publicKey);
        priKey = RSAEncrypt.getPrivateKey(this.privateKey);

        Assert.notNull(pubKey, "公钥解析失败");
        Assert.notNull(priKey, "私钥解析失败");

        this.keyPair = new KeyPair(pubKey, priKey);
    }

    public CookieProperties getCookie() {
        return cookie;
    }

    public void setCookie(CookieProperties cookie) {
        this.cookie = cookie;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderPrefix() {
        return headerPrefix;
    }

    public void setHeaderPrefix(String headerPrefix) {
        this.headerPrefix = headerPrefix;
    }

    public String getHeaderUserId() {
        return headerUserId;
    }

    public void setHeaderUserId(String headerUserId) {
        this.headerUserId = headerUserId;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
