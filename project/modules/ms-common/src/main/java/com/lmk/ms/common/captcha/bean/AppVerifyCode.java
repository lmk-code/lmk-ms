package com.lmk.ms.common.captcha.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 应用消息验证码
 * @author LaoMake
 * @email laomake@hotmail.com
 */
@Data
@Accessors(chain = true)
public class AppVerifyCode {
    /** 应用代码 */
    private String app;

    /** 验证码序列号 */
    private String sn;

    /** 用户手机号 */
    private String mobile;

    /** 验证码 */
    private String code;
}
