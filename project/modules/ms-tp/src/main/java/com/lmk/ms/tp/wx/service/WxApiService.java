package com.lmk.ms.tp.wx.service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.lmk.ms.common.cache.GlobalCacheService;
import com.lmk.ms.common.config.RedisKey;
import com.lmk.ms.common.config.WxApi;
import com.lmk.ms.common.tp.dto.WxAccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.lmk.ms.common.tp.dto.WxVerifyParameter;
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
    public boolean verifyRequest(WxVerifyParameter parameter) {
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
        String key = RedisKey.WG_AK + wxProperties.getAppId();
        String ak = (String) globalCacheService.get(key);
        if(ak != null){
            return ak;
        }

        String url = String.format(WxApi.ACCESS_TOKEN, wxProperties.getAppId(), wxProperties.getAppSecret());

        WxAccessTokenResponse response = restTemplate.getForObject(url, WxAccessTokenResponse.class);
        if(response.getErrcode() != null){
            log.error("微精灵获取访问令牌失败：{}", response.getErrmsg());
        }else{
            ak = response.getAccess_token();
            globalCacheService.set(RedisKey.WG_AK, ak, response.getExpires_in(), TimeUnit.SECONDS);
        }
        return ak;
    }

    public String getOAuthCodeURL(){

    }

}
