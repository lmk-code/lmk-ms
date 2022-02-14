package com.lmk.ms.common.captcha.service;

import com.lmk.ms.common.captcha.bean.AppVerifyCode;

/**
 * 应用类消息验证码服务
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/08
 */
public interface AppCaptchaService {

    /** 应用类型：微精灵 */
    String APP_WG = "WG";

    /** 应用类型：微信 */
    String APP_WX = "WX";

    /**
     * 获取验证码
     * @param app
     * @param appId
     * @param mobile
     * @return
     */
    AppVerifyCode getVerifyCode(String app, String appId, String mobile);


    /**
     * 校验验证码
     * @param id
     * @param code
     * @return
     */
    boolean checkAppVerifyCode(String id, String code);

}
