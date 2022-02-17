package com.lmk.ms.notify.sms.impl;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.lmk.ms.common.utils.JsonUtils;
import com.lmk.ms.notify.config.AlySmsProperties;
import com.lmk.ms.notify.sms.SmsService;

/**
 * 阿里云短信服务实现
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/17
 */
@Slf4j
public class AlySmsService implements SmsService {

    @Autowired
    AlySmsProperties alySmsProperties;

    /**
     * 创建客户端
     * @return
     * @throws Exception
     */
    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(alySmsProperties.getAccessKeyId())
                .setAccessKeySecret(alySmsProperties.getAccessKeySecret());
        config.endpoint = alySmsProperties.getEndpoint();
        return new Client(config);
    }

    @Override
    public void sendVerifyCode(String mobile, String code){
        log.info("通过阿里云发送验证码，手机：{}，验证码：{}", mobile, code);
        try {
            Client client = createClient();

            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(alySmsProperties.getSignName());
            request.setTemplateCode(alySmsProperties.getVerifyTemplateCode());
            request.setPhoneNumbers(mobile);

            Map<String, String> params = new HashMap<>();
            params.put("code", code);
            request.setTemplateParam(JsonUtils.toJSON(params));

            SendSmsResponse response = client.sendSms(request);
            SendSmsResponseBody responseBody = response.getBody();
            log.info("发送结果，Code：{}，RequestId：{}", responseBody.code, responseBody.requestId);
        } catch (Exception e) {
            log.warn("阿里云短信发送失败：", e);
        }
    }
}
