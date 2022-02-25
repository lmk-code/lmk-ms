package com.lmk.ms.tp.wx.service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.lmk.ms.common.op.dto.QrLogin;
import com.lmk.ms.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.lmk.ms.common.cache.GlobalCacheService;
import com.lmk.ms.common.config.RedisKey;
import com.lmk.ms.common.config.WxApi;
import com.lmk.ms.common.tp.dto.TpUserInfo;
import com.lmk.ms.common.tp.dto.wx.*;
import com.lmk.ms.common.tp.support.TpType;
import com.lmk.ms.common.utils.JsonUtils;
import com.lmk.ms.common.utils.encrypt.TextEncrypt;
import com.lmk.ms.tp.wx.config.WxProperties;


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
     * 获取微信JS Ticket
     * @return
     */
    public String getJsTicket(){
        String key = RedisKey.WX_JS_TICKET + wxProperties.getAppId();
        String jsTicket = (String) globalCacheService.get(key);
        if(jsTicket != null){
            return jsTicket;
        }

        String ak = getAccessToken();

        String url = String.format(WxApi.JS_TICKET, ak);
        JsTicketResponse response = restTemplate.getForObject(url, JsTicketResponse.class);
        if(response.getErrcode() != 0){
            log.error("微信获取JS Ticket失败：{}", response.getErrmsg());
        }else{
            jsTicket = response.getTicket();
            globalCacheService.set(key, jsTicket, response.getExpires_in(), TimeUnit.SECONDS);
        }
        return jsTicket;
    }

    /**
     * 获取微信页面JS 签名信息
     * @return
     */
    public WxJsConfig getJsConfig(String url){
        String jsTicket = getJsTicket();
        String timestamp = System.currentTimeMillis() + "";
        String noncestr = TextEncrypt.nonceString(16);

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("noncestr", noncestr);
        parameters.put("jsapi_ticket", jsTicket);
        parameters.put("timestamp", timestamp);
        parameters.put("url", url);

        StringBuilder sb = new StringBuilder();
        sb.append("jsapi_ticket=").append(jsTicket)
                .append("&noncestr=").append(noncestr)
                .append("&timestamp=").append(timestamp)
                .append("&url=").append(url);

        String signature = TextEncrypt.sha1(sb.toString());
        WxJsConfig jsConfig = new WxJsConfig();
        jsConfig.setAppId(wxProperties.getAppId());
        jsConfig.setTimestamp(timestamp);
        jsConfig.setNonceStr(noncestr);
        jsConfig.setSignature(signature);
        return jsConfig;
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
     * 微信扫码登录，获取Code的地址
     * @param sid
     * @return
     */
    public String getQrScanURL(String sid){
        return String.format(WxApi.OAUTH_AUTHORIZE, wxProperties.getAppId(), wxProperties.getQrScanRedirectUrl(), WxProperties.SCOPE_INFO, sid);
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
            user.setType(TpType.wx);
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

    /**
     * 获取用户绑定表单地址
     * @param qrScan    是否来自扫码登录
     * @param state
     * @return
     */
    public String getUserBindUrl(Boolean qrScan, String state){
        String url = wxProperties.getUserBindUrl();
        url = url + "/" + state;
        if(qrScan){
            url += "?scan=true";
        }
        return url;
    }

    /**
     * 创建微信扫码登录
     * @param redirectUrl
     * @return
     */
    public QrLogin createWxQrLogin(String redirectUrl) {
        String sid = IdUtils.snowflakeIdByText();
        String key = RedisKey.WX_QR_LOGIN + sid;

        QrLogin qrLogin = new QrLogin();
        qrLogin.setSid(sid);
        qrLogin.setType(TpType.wx.toString());
        qrLogin.setStatus(0);

        // 回跳地址
        if(redirectUrl.contains("?")){
            redirectUrl = redirectUrl + "&code=" + sid;
        }else{
            redirectUrl = redirectUrl + "?code=" + sid;
        }
        qrLogin.setRedirectUrl(redirectUrl);

        String scanUrl = wxProperties.getQrScanUrl() + sid;
        qrLogin.setScanUrl(scanUrl);

        globalCacheService.set(key, qrLogin, 20, TimeUnit.MINUTES); // 缓存会话
        return qrLogin;
    }

    /**
     * 获取扫码登录信息
     * @param sid
     * @return
     */
    public QrLogin getWxQrLogin(String sid) {
        String key = RedisKey.WX_QR_LOGIN + sid;
        return (QrLogin) globalCacheService.get(key);
    }

    /**
     * 更新扫码登录信息
     * @param sid
     * @param qrLogin
     */
    public void setWxQrLogin(String sid, QrLogin qrLogin) {
        String key = RedisKey.WX_QR_LOGIN + sid;
        globalCacheService.set(key, qrLogin, 20, TimeUnit.MINUTES); // 缓存会话
    }

    /**
     * 删除扫码登录
     * @param sid
     */
    public void deleteWxQrLogin(String sid) {
        String key = RedisKey.WX_QR_LOGIN + sid;
        globalCacheService.delete(key);
    }
}
