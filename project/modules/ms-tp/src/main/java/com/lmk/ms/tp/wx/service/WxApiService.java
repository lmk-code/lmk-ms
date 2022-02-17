package com.lmk.ms.tp.wx.service;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.lmk.ms.common.tp.dto.WxVerifyParameter;
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
}
