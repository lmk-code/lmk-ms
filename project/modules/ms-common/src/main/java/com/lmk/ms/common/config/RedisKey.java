package com.lmk.ms.common.config;

/**
 * Redis缓存KEY
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/13
 */
public class RedisKey {

    /** JWT缓存KEY */
    public static final String KEY_JWT_TOKEN = "MS:JWT:Token:";

    /** JWT缓存KEY */
    public static final String KEY_JWT_USER = "MS:JWT:User:";

    /** 微信访问令牌的缓存KEY */
    public static final String WX_AK = "MS:WX:AK:";

    /** 微信用户授权访问令牌的缓存KEY */
    public static final String WX_OAUTH_AK = "MS:WX:OAUTH:AK:";

    /** 微信用户授权刷新令牌的缓存KEY */
    public static final String WX_OAUTH_RK = "MS:WX:OAUTH:RK:";

    /** 微信用户信息的缓存KEY */
    public static final String WX_OAUTH_USER = "MS:WX:OAUTH:USER:";

    /** 微信扫码登录 */
    public static final String WX_QR_LOGIN = "MS:WX:QR:";
}
