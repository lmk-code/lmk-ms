package com.lmk.ms.autoconfigure.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lmk.ms.autoconfigure.auth.AuthProperties;
import com.lmk.ms.gateway.filter.AuthorizeGatewayFilterFactory;

/**
 * 网关自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(name = "ms.config.gateway.enabled")
public class GatewayAutoconfigure {

    @Autowired
    AuthProperties authProperties;

    /**
     * 权限过滤器
     * @return
     */
    @Bean
    public AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory(){
        AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory = null;
        if(authProperties != null){
            authorizeGatewayFilterFactory = new AuthorizeGatewayFilterFactory(authProperties.getJwt());
        }else{
            authorizeGatewayFilterFactory = new AuthorizeGatewayFilterFactory();
        }
        return authorizeGatewayFilterFactory;
    }

    /**
     * 导入K8S心跳检测
     * @return
     */
    /*@Bean
    @ConditionalOnMissingBean(K8SController.class)
    public K8SController k8SController(){
        return new K8SController();
    }*/
}
