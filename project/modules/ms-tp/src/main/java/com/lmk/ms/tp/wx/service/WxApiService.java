package com.lmk.ms.tp.wx.service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.lmk.ms.common.cache.GlobalCacheService;
import com.lmk.ms.common.config.RedisKey;
import com.lmk.ms.common.config.WxApi;
import com.lmk.ms.common.tp.dto.TpUserInfo;
import com.lmk.ms.common.tp.dto.wx.*;
import com.lmk.ms.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.lmk.ms.common.utils.encrypt.TextEncrypt;
import com.lmk.ms.tp.wx.config.WxProperties;
import org.springframework.web.client.RestTemplate;

/**
 * 微信API服务
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/17
 */
@Slf4j
public class WxApiService {

    @Autowired
    WxProperties wxProperties;

    @Autowired
    GlobalCacheService globalCacheService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 微信接入校验
     * @param parameter
     * @return
     */
    public boolean verifyRequest(VerifyParameter parameter) {
        boolean result = false;

        String[] strs =  { wxProperties.getToken(), parameter.getTimestamp(), parameter.getNonce()};
        Arrays.sort(strs);

        String key = "";
        for (String str : strs){
            key += str;
        }
        key = TextEncrypt.sha1(key);

        return key.equals(parameter.getSignature());
    }

    /**
     * 获取访问令牌
     * @return
     */
    public String getAccessToken(){
        String key = RedisKey.WX_AK + wxProperties.getAppId();
        String ak = (String) globalCacheService.get(key);
        if(ak != null){
            return ak;
        }

        String url = String.format(WxApi.ACCESS_TOKEN, wxProperties.getAppId(), wxProperties.getAppSecret());

        AccessTokenResponse response = restTemplate.getForObject(url, AccessTokenResponse.class);
        if(response.getErrcode() != null){
            log.error("微信获取访问令牌失败：{}", response.getErrmsg());
        }else{
            ak = response.getAccess_token();
            globalCacheService.set(key, ak, response.getExpires_in(), TimeUnit.SECONDS);
        }
        return ak;
    }

    /**
     * 微信授权，获取Code的地址
     * @param base    是否只获取OpenId
     * @param state   透传参数
     * @return
     */
    public String getOAuthCodeURL(Boolean base, String state){
        String scope = base ? WxProperties.SCOPE_BASE : WxProperties.SCOPE_INFO;
        return String.format(WxApi.OAUTH_AUTHORIZE, wxProperties.getAppId(), wxProperties.getAuthCodeRedirectUrl(), scope, state);
    }

    /**
     * 通过授权码换取用户AccessToken
     * @param code
     * @return
     */
    public OAuthToken getOAuthAccessToken(String code){
        OAuthToken token = null;

        String url =  String.format(WxApi.OAUTH_TOKEN, wxProperties.getAppId(), wxProperties.getAppSecret(), code);
        String textResponse = restTemplate.getForObject(url, String.class);
        if(textResponse == null){
            log.error("微信获取访问令牌失败");
        }else{
            OAuthAccessTokenResponse response = JsonUtils.parseObject(textResponse, OAuthAccessTokenResponse.class);
            String openId = response.getOpenid();;
            String ak = response.getAccess_token();
            String rk = response.getRefresh_token();
            globalCacheService.set(RedisKey.WX_OAUTH_AK + openId, ak, response.getExpires_in(), TimeUnit.SECONDS);
            globalCacheService.set(RedisKey.WX_OAUTH_RK + openId, rk, 30, TimeUnit.DAYS);

            token = new OAuthToken(openId, ak);
        }
        return token;
    }

    /**
     * 根据访问令牌获取用户信息
     * @param token
     * @return
     */
    public TpUserInfo getUserInfoByAccessToken(OAuthToken token){
        TpUserInfo user = null;
        String url =  String.format(WxApi.OAUTH_USER_INFO, token.getAccessToken(), token.getOpenid());
        String textResponse = restTemplate.getForObject(url, String.class);
        if(textResponse == null){
            log.error("微信获取用户信息失败");
        }else{
            textResponse = new String(textResponse.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            UserInfoResponse response = JsonUtils.parseObject(textResponse, UserInfoResponse.class);
            // 缓存用户信息
            globalCacheService.set(RedisKey.WX_OAUTH_USER + token.getOpenid(), response, 1, TimeUnit.HOURS);
            user = new TpUserInfo();
            user.setOpenId(response.getOpenid());
            user.setNickname(response.getNickname());
            user.setAvatar(response.getHeadimgurl());
            user.setCountry(response.getCountry());
            user.setProvince(response.getProvince());
            user.setCity(response.getCity());

            switch (response.getSex()){
                case 0:
                    user.setGender(-1);
                    break;
                case 1:
                    user.setGender(1);
                    break;
                case 2:
                    user.setGender(0);
                    break;
            }

        }
        return user;
    }

}
