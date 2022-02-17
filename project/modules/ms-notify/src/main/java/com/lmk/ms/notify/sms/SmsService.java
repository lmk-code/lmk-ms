package com.lmk.ms.notify.sms;

/**
 * 短信通知服务
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/17
 */
public interface SmsService {

    /**
     * 发送短息验证码
     * @param mobile
     * @param code
     */
    void sendVerifyCode(String mobile, String code);
}
