package com.lmk.ms.common.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Jwt令牌
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
@ApiModel("JWT令牌")
public class JwtToken {

    @ApiModelProperty(value = "正常调用使用的token")
    String accessToken;

    @ApiModelProperty(value = "accessToken过期时间,单位:秒")
    Long expiresIn;

    @ApiModelProperty(value = "刷新使用的token")
    String refreshToken;

    @ApiModelProperty(value = "refreshToken过期时间,单位:秒")
    Long refreshExpiresIn;

    @ApiModelProperty(value = "token类型")
    String tokenType = "Bearer";

    public JwtToken() {
    }

    public JwtToken(String accessToken, Long expiresIn, String refreshToken, Long refreshExpiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshExpiresIn=" + refreshExpiresIn +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
