package com.lmk.ms.notify.sms;

import java.util.Map;

/**
 * 短信通知服务
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/17
 */
public interface SmsService {

    /**
     * 发送短信验证码
     * @param template      模板（代码）
     * @param parameters    参数
     * @param mobile        手机号
     * @return              发送状态码
     */
    String sendCode(String template, Map<String, Object> parameters, String mobile);
}
