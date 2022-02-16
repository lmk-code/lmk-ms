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
    Long accessTokenExpiresIn;

    @ApiModelProperty(value = "刷新使用的token")
    String refreshToken;

    @ApiModelProperty(value = "refreshToken过期时间,单位:秒")
    Long refreshTokenExpiresIn;

    @ApiModelProperty(value = "token类型")
    String tokenType = "Bearer";

    public JwtToken() {
    }

    public JwtToken(String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public void setAccessTokenExpiresIn(Long accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(Long refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "accessToken='" + accessToken + '\'' +
                ", accessTokenExpiresIn=" + accessTokenExpiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpiresIn=" + refreshTokenExpiresIn +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
