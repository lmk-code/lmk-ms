package com.lmk.ms.notify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信服务配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/17
 */
@Component
@ConfigurationProperties(prefix = "ms.config.notify.sms.aly")
public class AlySmsProperties {

    /** 是否启用 */
    private Boolean enabled;

    /** AccessKey ID */
    private String accessKeyId;

    /** AccessKey Secret */
    private String accessKeySecret;

    /** 访问的域名 */
    private String endpoint = "dysmsapi.aliyuncs.com";

    /** 短信签名 */
    private String signName = "双体系";

    /** 验证码有效时长，单位：分钟 */
    private Integer expireIn = 2;

    /** 验证码短信模板 */
    private String verifyTemplateCode = "SMS_130830117";

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public String getVerifyTemplateCode() {
        return verifyTemplateCode;
    }

    public void setVerifyTemplateCode(String verifyTemplateCode) {
        this.verifyTemplateCode = verifyTemplateCode;
    }
}
