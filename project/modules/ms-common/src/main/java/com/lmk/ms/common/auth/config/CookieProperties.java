package com.lmk.ms.common.auth.config;

/**
 * Cookie配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
public class CookieProperties {

    /** 是否启用 */
    boolean enabled = false;

    /** 名称 */
    String name = "token-sys";

    /** 路径 */
    String path = "/";

    /** 生命时长 */
    Integer maxAge = -1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
