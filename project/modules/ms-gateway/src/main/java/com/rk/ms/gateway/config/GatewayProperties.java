package com.lmk.ms.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 网关配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/01/04
 */
@Data
@Component
@ConfigurationProperties(prefix = "ms.config.gateway")
public class GatewayProperties {
}
