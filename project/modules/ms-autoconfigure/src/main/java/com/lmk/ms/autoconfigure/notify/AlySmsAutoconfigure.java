package com.lmk.ms.autoconfigure.notify;

import com.lmk.ms.auth.service.MsAuthService;
import com.lmk.ms.autoconfigure.auth.AuthProperties;
import com.lmk.ms.common.cache.GlobalCacheService;
import com.lmk.ms.notify.config.AlySmsProperties;
import com.lmk.ms.notify.sms.impl.AlySmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(AlySmsProperties.class)
@ConditionalOnProperty(value = "ms.config.notify.sms.aly.enabled", havingValue = "true", matchIfMissing = false)
public class AlySmsAutoconfigure {

    /**
     * 阿里云短信服务
     * @return
     */
    @Bean
    public AlySmsService alySmsService(){
        return new AlySmsService();
    }
}
