package com.lmk.ms.autoconfigure.tp;

import com.lmk.ms.notify.config.AlySmsProperties;
import com.lmk.ms.notify.sms.impl.AlySmsService;
import com.lmk.ms.tp.wx.config.WxProperties;
import com.lmk.ms.tp.wx.service.WxApiService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信API自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
@ConditionalOnProperty(value = "ms.config.tp.wx.enabled", havingValue = "true", matchIfMissing = false)
public class WxAutoconfigure {

    /**
     * 微信API服务
     * @return
     */
    @Bean
    public WxApiService wxApiService(){
        return new WxApiService();
    }
}
