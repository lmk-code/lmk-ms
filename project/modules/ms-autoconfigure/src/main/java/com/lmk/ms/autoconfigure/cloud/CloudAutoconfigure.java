package com.lmk.ms.autoconfigure.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.lmk.ms.autoconfigure.web.WebProperties;

/**
 * Spring Cloud自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(WebProperties.class)
@ConditionalOnProperty(name = "ms.config.cloud.enabled", matchIfMissing = true)
public class CloudAutoconfigure {

    @Bean
    @ConditionalOnClass(SentinelResourceAspect.class)
    @ConditionalOnMissingBean(CloudAutoconfigure.class)
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect ();
    }

}
