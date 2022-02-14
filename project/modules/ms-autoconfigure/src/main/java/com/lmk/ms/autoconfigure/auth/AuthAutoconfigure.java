package com.lmk.ms.autoconfigure.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lmk.ms.auth.service.MsAuthService;
import com.lmk.ms.common.cache.GlobalCacheService;

/**
 * 用户授权自动配置，默认不自动开启
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(value = "ms.config.auth.enabled", havingValue = "true", matchIfMissing = false)
public class AuthAutoconfigure {

    @Autowired
    AuthProperties authProperties;

    @Autowired
    GlobalCacheService globalCacheService;

    /**
     * 全局的授权服务
     * @return
     */
    @Bean
    public MsAuthService msAuthService(){
        return new MsAuthService(globalCacheService, authProperties.getJwt());
    }
}
