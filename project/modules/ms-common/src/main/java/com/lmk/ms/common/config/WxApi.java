package com.lmk.ms.common.config;

/**
 * 微信API接口
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/20
 */
public class WxApi {

    /** 微信基础服务器 */
    public static final String API_SERVER = "https://api.weixin.qq.com";

    /** 获取AccessToken */
    public static final String ACCESS_TOKEN = API_SERVER + "/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /** 获取jsApiTicket */
    public static final String JS_TICKET = API_SERVER + "/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    /** 获取微信服务器IP */
    public static final String SERVER_IP = API_SERVER + "/cgi-bin/getcallbackip";

    /** 获取短连接 */
    public static final String SHORT_URL = API_SERVER + "/cgi-bin/shorturl";

    /** 回复消息 */
    public static final String SEND_MESSAGE = API_SERVER + "/cgi-bin/message/custom/send";

    /** 自定义菜单 */
    public static final String CREATE_MENU = API_SERVER + "/cgi-bin/menu/create";

    /** 删除菜单 */
    public static final String DELETE_MENU = API_SERVER + "/cgi-bin/menu/delete";

    /** 获取用户信息 */
    public static final String USER_INFO = API_SERVER + "/cgi-bin/user/info";

    /** 发送模板消息 */
    public static final String SEND_TEMPLATE_MESSAGE = API_SERVER + "/cgi-bin/message/template/send";

    /** 创建带参数二维码ticket */
    public static final String CREATE_QR_CODE = API_SERVER + "/cgi-bin/qrcode/create?access_token=";

    /** 通过ticket获取二维码 */
    public static final String SHOW_QR_CODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    /** OAuth授权界面 */
    public static final String OAUTH_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    /** OAuth获取access_token */
    public static final String OAUTH_TOKEN = API_SERVER + "/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /** OAuth获取用户信息 */
    public static final String OAUTH_USER_INFO = API_SERVER + "/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
}
