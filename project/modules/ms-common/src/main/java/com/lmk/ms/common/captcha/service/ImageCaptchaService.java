package com.lmk.ms.common.captcha.service;

import com.lmk.ms.common.captcha.bean.ImageVerifyCode;

/**
 * 图片验证码服务
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/07
 */
public interface ImageCaptchaService {

    /**
     * 获取常规字符形式的图片验证码
     * @return
     */
    ImageVerifyCode textCaptcha();

    /**
     * 获取数学计算形式的图片验证码
     * @return
     */
    ImageVerifyCode mathCaptcha();

    /**
     * 校验图片验证码
     * @param sn
     * @param code
     * @return
     */
    boolean checkImageVerifyCode(String sn, String code);

}
