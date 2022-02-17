package com.lmk.ms.auth.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.lmk.ms.common.auth.config.JwtProperties;
import com.lmk.ms.common.auth.vo.JwtToken;
import com.lmk.ms.common.auth.vo.JwtUser;
import com.lmk.ms.common.auth.vo.LoginUser;
import com.lmk.ms.common.auth.utils.JwtUtils;
import com.lmk.ms.common.cache.GlobalCacheService;
import com.lmk.ms.common.config.RedisKey;
import com.lmk.ms.common.mvc.entity.RecordEntity;
import com.lmk.ms.common.utils.HttpUtils;

/**
 * 统一的鉴权服务
 *
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
public class MsAuthService {

    private JwtProperties jwtProperties;

    private GlobalCacheService globalCacheService;

    public MsAuthService(GlobalCacheService globalCacheService, JwtProperties jwtProperties) {
        this.globalCacheService = globalCacheService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 设置创建信息
     * @param entity
     */
    public void createBy(RecordEntity entity){
        entity.setCreateBy(getCurrentUser().getUsername());
        entity.setCreateTime(new Date());
    }

    /**
     * 设置更新这信息
     * @param entity
     */
    public void updateBy(RecordEntity entity){
        entity.setUpdateBy(getCurrentUser().getUsername());
        entity.setUpdateTime(new Date());
    }

    /**
     * 生成令牌
     * @param loginUser
     * @return
     */
    public JwtToken getToken(LoginUser loginUser) {
        // 添加Token缓存，可用于快速解析
        String key = RedisKey.KEY_JWT_TOKEN + loginUser.getId();

        JwtToken jwtToken = (JwtToken) globalCacheService.get(key);
        if(jwtToken == null){
            JwtUser user = new JwtUser(loginUser);
            jwtToken = JwtUtils.getToken(user, jwtProperties);
            globalCacheService.set(key, jwtToken, jwtProperties.getAccessTokenExpiration(), TimeUnit.SECONDS);

            // 添加用户信息缓存
            key = RedisKey.KEY_JWT_USER + user.getId();
            globalCacheService.set(key, loginUser, jwtProperties.getAccessTokenExpiration(), TimeUnit.SECONDS);
        }

        return jwtToken;
    }

    /**
     * 刷新访问令牌
     * @param refreshToken
     * @return
     */
    public JwtToken refreshToken(String refreshToken) {
        return JwtUtils.refreshToken(refreshToken, jwtProperties);
    }

    /**
     * 根据访问令牌获取登录用户
     * @param accessToken
     * @return
     */
    public JwtUser getUser(String accessToken){
        return JwtUtils.getJwtUserByAccessToken(accessToken, jwtProperties.getKeyPair().getPublic());
    }

    public JwtProperties getJwtProperties() {
        return jwtProperties;
    }

    public void setJwtProperties(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public LoginUser getLoginUser(){
        Long userId = getCurrentUserId();
        if(userId == null){
            return null;
        }
        return (LoginUser) globalCacheService.get(RedisKey.KEY_JWT_USER + userId);
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public JwtUser getCurrentUser() {
        String accessToken = HttpUtils.getAccessToken(jwtProperties);
        return JwtUtils.getJwtUserByAccessToken(accessToken, jwtProperties.getKeyPair().getPublic());
    }

    /**
     * 获取当前登录用户的ID
     * @return
     */
    public Long getCurrentUserId() {
        JwtUser user = getCurrentUser();
        if(user != null){
            return user.getId();
        }else{
            return null;
        }
    }

    /**
     * 获取当前登录用户的用户名
     * @return
     */
    public String getCurrentUsername() {
        JwtUser user = getCurrentUser();
        if(user != null){
            return user.getUsername();
        }else{
            return null;
        }
    }
}
