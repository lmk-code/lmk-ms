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

    /** 微精灵JWT缓存KEY */
    public static final String KEY_WG_TOKEN = "MS:JWT:WgToken:";

    /** IDP JWT缓存KEY */
    public static final String KEY_IDP_TOKEN = "MS:JWT:IdpToken:";

    /** JWT缓存KEY */
    public static final String KEY_JWT_USER = "MS:JWT:User:";

    /** 微精灵访问令牌的缓存KEY */
    public static final String WG_AK = "MS:WG:AK";

    /** 微精灵用户OpenID的缓存KEY */
    public static final String WG_OPENID = "MS:WG:OpenId:";

}
