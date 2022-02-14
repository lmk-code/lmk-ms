package com.lmk.ms.autoconfigure.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Spring Cloud自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Component
@ConfigurationProperties(prefix = "ms.config.cloud")
public class CloudProperties {

    /** 启用Spring Cloud功能 */
    private boolean enabled = true;

    /** 启动Sentinel */
    private boolean enableSentinel = true;


    public CloudProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnableSentinel() {
        return enableSentinel;
    }

    public void setEnableSentinel(boolean enableSentinel) {
        this.enableSentinel = enableSentinel;
    }
}
