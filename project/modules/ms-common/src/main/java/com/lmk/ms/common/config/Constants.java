package com.lmk.ms.common.config;

/**
 * 全局常量
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/16
 */
public class Constants {

    /** 请求头中添加IDP TOKEN */
    public static final String HEADER_IDP_TOKEN = "Authorization_IDP";

    /** 登录成功后向HTTP请求头中添加IDP用户ID */
    public static final String HEADER_IDP_ID = "IDP-USER-ID";

    /** 登录成功后向HTTP请求头中添加用户ID */
    public static final String HEADER_USER_ID = "MS-USER-ID";

    /** 登录成功后向HTTP请求头中添加用户手机号 */
    public static final String HEADER_USER_MOBILE = "RK-USER-MOBILE";

}
