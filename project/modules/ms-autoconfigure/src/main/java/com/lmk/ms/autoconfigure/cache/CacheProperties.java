package com.lmk.ms.autoconfigure.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局缓存自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Component
@ConfigurationProperties(prefix = "ms.config.cache")
public class CacheProperties {

    /** 启用缓存功能 */
    private boolean enabled = true;

    /** Spring框架缓存的过期时间（分钟） */
    private int springCacheExpire = 60;

    public CacheProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSpringCacheExpire() {
        return springCacheExpire;
    }

    public void setSpringCacheExpire(int springCacheExpire) {
        this.springCacheExpire = springCacheExpire;
    }
}
