package com.lmk.ms.gateway.filter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网关授权配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/16
 */
public class AuthorizeConfig {
    // 控制是否开启认证
    private boolean enabled;

    // 是否校验权限
    private boolean auth;

    private String permitAll;

    private List<String> permitAllList;

    public AuthorizeConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(String permitAll) {
        this.permitAll = permitAll;
    }

    public List<String> getPermitAllList() {
        if (permitAllList == null) {
            if (StringUtils.isNotBlank(permitAll)) {
                permitAllList = Arrays.asList(StringUtils.split(permitAll, " "));
            } else {
                permitAllList = new ArrayList<>();
            }
        }
        return permitAllList;
    }

    public void setPermitAllList(List<String> permitAllList) {
        this.permitAllList = permitAllList;
    }
}
