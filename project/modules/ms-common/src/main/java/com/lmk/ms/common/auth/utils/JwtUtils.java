package com.lmk.ms.common.auth.utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.lmk.ms.common.auth.vo.JwtToken;
import com.lmk.ms.common.auth.vo.JwtUser;
import com.lmk.ms.common.utils.JsonUtils;
import com.lmk.ms.common.auth.vo.JwtTokenPayload;
import com.lmk.ms.common.utils.encrypt.TextEncrypt;

/**
 * Jwt工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
@Slf4j
public class JwtUtils {

    /** 场景：访问令牌 */
    public static final String SUB_USER = "USER";

    /** 场景：刷新令牌 */
    public static final String SUB_REFRESHER = "REFRESHER";

    /** 签发人 */
    public static final String ISSUER = "MS";

    /**
     * 从IDP访问令牌中提取用户ID，不进行签名验证
     * @param accessToken
     * @return
     */
    public static String getIdpUserId(String accessToken){
        if(StringUtils.isBlank(accessToken)){
            return null;
        }
        accessToken = accessToken.substring(7);
        String str = TextEncrypt.fromBase64(accessToken.split("\\.")[1]);

        JwtTokenPayload payload = JsonUtils.parseObject(str, JwtTokenPayload.class);
        return payload.getAud();
    }

    /**
     * 从微精灵访问令牌中提取用户ID，不进行签名验证
     * @param accessToken
     * @return
     */
    public static String getWgUserId(String accessToken){
        return getIdpUserId(accessToken);
    }

    /**
     * 生成令牌
     * @param user                      登录用户
     * @param accessTokenExpiration     访问令牌失效时间
     * @param privateKey                RSA私钥，用于签名
     * @return
     */
    public static JwtToken getToken(JwtUser user, long accessTokenExpiration, PrivateKey privateKey) {
        // 刷新令牌的有效时间 默认7天
        long refreshTokenExpiration = 7 * 24 * 60 * 60L;

        Date now = new Date();
        Date accessExp = new Date(now.getTime() + accessTokenExpiration * 1000L);
        Date refreshExp = new Date(now.getTime() + refreshTokenExpiration * 1000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("data", user);

        String userId = user.getId().toString();

        // 生成访问令牌
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setId(userId)
                .setIssuer(ISSUER)
                .setSubject(SUB_USER)
                .setIssuedAt(now)
                .setExpiration(accessExp)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        // 生成刷新令牌
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setId(userId)
                .setIssuer(ISSUER)
                .setSubject(SUB_REFRESHER)
                .setIssuedAt(now)
                .setExpiration(refreshExp)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        return new JwtToken(accessToken, accessTokenExpiration, refreshToken, refreshTokenExpiration);
    }

    /**
     * 刷新访问令牌
     * @param refreshToken              刷新令牌
     * @param accessTokenExpiration     访问令牌失效时间
     * @param keyPair                   RSA私钥对，用于解密、签名
     * @return
     */
    public static JwtToken refreshToken(String refreshToken, long accessTokenExpiration, KeyPair keyPair) {
        JwtUser user = getJwtUserByRefreshToken(refreshToken, keyPair.getPublic());
        if(user != null){
            return getToken(user, accessTokenExpiration, keyPair.getPrivate());
        }
        return null;
    }

    /**
     * 提取数据
     * @param jwt
     * @param publicKey
     * @return
     */
    public static Claims getClaims(String jwt, PublicKey publicKey) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("解析令牌失败->令牌已过期：{}", jwt);
        } catch (MalformedJwtException e){
            log.warn("解析令牌失败->格式错误：{}", jwt);
        } catch (SignatureException e){
            log.warn("解析令牌失败->签名验证失败：{}", jwt);
        } catch (Exception e){
            log.warn("解析令牌失败：", e);
        }
        return claims;
    }

    /**
     * 根据访问令牌获取登录用户
     * @param accessToken
     * @param publicKey
     * @return
     */
    public static JwtUser getJwtUserByAccessToken(String accessToken, PublicKey publicKey){
        if(accessToken == null || publicKey == null)
            return null;
        return getJwtUser(accessToken, publicKey, SUB_USER);
    }

    /**
     * 根据刷新令牌获取登录用户
     * @param refreshToken
     * @param publicKey
     * @return
     */
    public static JwtUser getJwtUserByRefreshToken(String refreshToken, PublicKey publicKey){
        return getJwtUser(refreshToken, publicKey, SUB_REFRESHER);
    }

    /**
     * 获取登录用户信息
     * @param accessToken
     * @param publicKey
     * @param subject
     * @return
     */
    private static JwtUser getJwtUser(String accessToken, PublicKey publicKey, String subject){
        if(StringUtils.isBlank(accessToken)) {
            log.info("鉴权失败：accessToken为空");
            return null;
        }
        JwtUser user = null;
        try {
            Claims claims = getClaims(accessToken, publicKey);
            if(claims != null && claims.getSubject().equals(subject)) {
                user = getJwtUser(claims);
            }
        } catch (Exception e) {
            log.info("鉴权失败：", e);
        }
        if(user == null){
            log.info("鉴权失败：未能获取登录用户信息");
        }
        return user;
    }

    /**
     * 获取登录用户信息
     * @param claims
     * @return
     */
    private static JwtUser getJwtUser(Claims claims){
        if(claims == null)
            return null;

        Object data = claims.get("data");
        return JsonUtils.parseObject(JsonUtils.toJSON(data), JwtUser.class);
    }

    /**
     * 校验令牌是否已过期
     * @param accessToken
     * @param publicKey
     * @return
     */
    public static boolean isAccessTokenExpiration(String accessToken, PublicKey publicKey){
        if(StringUtils.isBlank(accessToken)) {
            return true;
        }
        try {
            Claims claims = getClaims(accessToken, publicKey);
            long time = claims.getExpiration().getTime();
            if(time < System.currentTimeMillis()){
                log.info("令牌已过期：{}", accessToken);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return true;
        }
        return false;
    }

}
