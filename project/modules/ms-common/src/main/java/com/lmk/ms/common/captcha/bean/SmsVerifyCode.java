package com.lmk.ms.common.captcha.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 短信验证码
 * @author LaoMake
 * @email laomake@hotmail.com
 */
@Data
@Accessors(chain = true)
public class SmsVerifyCode{

    /** 验证码序列号 */
    private String sn;

    /** 目标手机号 */
    private String mobile;

    /** 验证码 */
    private String code;

    /** 短信模板代码 */
    private String templateCode;
}
