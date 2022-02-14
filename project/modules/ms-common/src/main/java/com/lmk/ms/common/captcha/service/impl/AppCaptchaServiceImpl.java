package com.lmk.ms.common.captcha.service.impl;

import com.lmk.ms.common.captcha.bean.AppVerifyCode;
import com.lmk.ms.common.captcha.service.AppCaptchaService;
import com.lmk.ms.common.utils.IdUtils;
import com.lmk.ms.common.utils.encrypt.TextEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 应用类验证码服务实现
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/08
 */
public class AppCaptchaServiceImpl implements AppCaptchaService {

    /**
     * 缓存服务
     */
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 缓存key的前缀
     */
    private String preKey = "MS:AppVerifyCode:";

    @Override
    public AppVerifyCode getVerifyCode(String app, String appId, String mobile) {
        String sn = IdUtils.uuid();
        String code = TextEncrypt.verifyCode();

        AppVerifyCode vc = new AppVerifyCode();
        vc.setApp(app)
          .setSn(sn)
          .setCode(code)
          .setMobile(mobile);

        // 将验证码结果保存到缓存，2分钟内有效
        redisTemplate.opsForValue().set(preKey + sn, code, 2, TimeUnit.MINUTES);

        return vc;
    }

    @Override
    public boolean checkAppVerifyCode(String sn, String code) {
        String cacheCode = (String) redisTemplate.opsForValue().get(preKey + sn);
        redisTemplate.delete(preKey + sn);
        if(cacheCode != null && cacheCode.equals(code)){
            return true;
        }
        return false;
    }
}
