package com.lmk.ms.common.utils;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * 文本处理工具
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/16
 */
public class TextUtils {
    /** 正则表达式：手机号 */
    public static final String REGEX_MOBILE = "^((1[3-8][0-9]))\\d{8}$";

    /** 正则表达式：邮件地址 */
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /** 正则表达式：URL地址 */
    public static final String REGEX_URL = "[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";

    /** 正则表达式：身份证号18位 */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    /** 正则表达式：身份证号15位 */
    public static final String REGEX_ID_CARD_OLD = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    /** 正则表达式：整数 */
    public static final String REGEX_NUMBER = "\\d*";

    /** 正则表达式：包含中文 */
    public static final String REGEX_CHINESS = "^.*[\\u4e00-\\u9fa5]+.*$";

    /** 正则表达式：全为中文 */
    public static final String REGEX_CHINESS_ALL = "[\\u4e00-\\u9fa5]{2,25}";

    /** 正则表达式：手机浏览器 */
    public static final String REGEX_UA_PHONE = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    /** 正则表达式：平板浏览器 */
    public static final String REGEX_UA_TAB = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    /** 正则表达式：用户名（字母开头的6~16位字符串，可包含：数字、字母、下划线） */
    public static String REGEX_USER_NAME = "^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,15}$";

    /** 正则表达式：密码（6~20位的字符串，可包含字母、数字、特殊符号，不能为纯数字） */
    public static String REGEX_PASSWORD = "(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\\w~!@#$%\\^&*?]{6,20}$";

    private static final Pattern Mobile = Pattern.compile(REGEX_MOBILE);
    private static final Pattern Email = Pattern.compile(REGEX_EMAIL);
    private static final Pattern Url = Pattern.compile(REGEX_URL);
    private static final Pattern IDcard = Pattern.compile(REGEX_ID_CARD);
    private static final Pattern IDcardOld = Pattern.compile(REGEX_ID_CARD_OLD);
    private static final Pattern Number = Pattern.compile(REGEX_NUMBER);
    private static final Pattern Chiness = Pattern.compile(REGEX_CHINESS);
    private static final Pattern ChinessAll = Pattern.compile(REGEX_CHINESS_ALL);
    private static final Pattern FromPhone = Pattern.compile(REGEX_UA_PHONE);
    private static final Pattern FromTab = Pattern.compile(REGEX_UA_TAB);
    private static Pattern UserName = Pattern.compile(REGEX_USER_NAME);
    private static Pattern Password = Pattern.compile(REGEX_PASSWORD);

    /**
     * 首字母变小写
     * @author laomake@hotmail.com
     * @date 2014年10月24日
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(final String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        return Character.toLowerCase(firstChar) + tail;
    }

    /**
     * 首字母变大写
     * @author laomake@hotmail.com
     * @date 2014年10月24日
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(final String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        return Character.toUpperCase(firstChar) + tail;
    }

    /**
     * 转换为数据库风格（全部小写，下划线分割）
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static String toDbStyle(String str) {
        StringBuffer sb = new StringBuffer();
        char c;
        for(int i=0; i< str.length(); i++){
            c = str.charAt(i);
            if(Character.isLowerCase(c))
                sb.append(c);
            else{
                if(i > 0)
                    sb.append("_");
                sb.append(Character.toLowerCase(c));
            }

        }
        return sb.toString();
    }

    /**
     * 转换为Java
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static String toJavaStyle(String str) {
        boolean cahang = false;
        StringBuffer sb = new StringBuffer();
        char c;
        for(int i=0; i< str.length(); i++){
            c = str.charAt(i);
            if(c == '_'){
                cahang = true;
            }else{
                if(cahang){
                    c = Character.toUpperCase(c);
                    cahang = false;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 设定校验用户名及密码的正则表达式，方便扩展
     * @author laomake@hotmail.com
     * @param userName
     * @param password
     */
    public static void setUserNameAndPasswordRegex(String userName, String password) {
        REGEX_USER_NAME = userName;
        UserName = Pattern.compile(REGEX_USER_NAME);

        REGEX_PASSWORD = password;
        Password = Pattern.compile(REGEX_PASSWORD);
    }

    /**
     * 判断字符串是否符合用户名格式
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isLoginName(String str){
        Boolean matcher = false;
        if(StringUtils.isBlank(str))
            matcher = UserName.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否符合密码格式
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isPassword(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Password.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否符合手机号格式
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isMobile(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Mobile.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否符合邮件地址格式
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isEmail(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Email.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否为URL
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isUrl(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Url.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否符合身份证格式
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isIdCard(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str)){
            if(str.length() == 18)
                matcher = IDcard.matcher(str).matches();
            else if(str.length() == 15)
                matcher = IDcardOld.matcher(str).matches();
        }
        return matcher;
    }

    /**
     * 判断字符串是否为纯数字
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isNumber(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Number.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断字符串是否为纯中文
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean isChiness(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = ChinessAll.matcher(str).matches();
        return matcher;
    }

    /**
     * 判断当前请求是否来自移动端
     * @author laomake@hotmail.com
     * @param ua
     * @return
     */
    public static Boolean fromMobile(String ua){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(ua)){
            matcher = FromPhone.matcher(ua).matches();
            if(!matcher)
                matcher = FromTab.matcher(ua).matches();
        }
        return matcher;
    }

    /**
     * 判断字符串是否包含中文
     * @author laomake@hotmail.com
     * @param str
     * @return
     */
    public static Boolean containChiness(String str){
        Boolean matcher = false;
        if(StringUtils.isNotBlank(str))
            matcher = Chiness.matcher(str).matches();
        return matcher;
    }

    /**
     * 去除字符串中的Emoji字符
     * @author laomake@hotmail.com
     * @param content
     * @return
     */
    public static String cleanEmoji(String content) {
        if(containsEmoji(content)){
            StringBuilder sb = new StringBuilder();
            int len = content.length();
            for (int i = 0; i < len; i++) {
                char character = content.charAt(i);
                if(notEmoji(character))
                    sb.append(character);
            }
            content = sb.toString();
        }
        return content;
    }

    /**
     * 判断字符串中数否包含Emoji字符串
     * @author laomake@hotmail.com
     * @param content
     * @return
     */
    public static Boolean containsEmoji(String content) {
        boolean containsEmoji = false;
        if(StringUtils.isNotBlank(content)){
            int len = content.length();
            for (int i = 0; i < len; i++) {
                containsEmoji = !notEmoji(content.charAt(i));
                if(containsEmoji)
                    break;
            }
        }
        return containsEmoji;
    }

    /**
     * 判断字符是否为Emoji
     * @author laomake@hotmail.com
     * @param content
     * @return
     */
    private static Boolean notEmoji(char content){
        Boolean notEmoji = (content == 0x0) ||
                (content == 0x9) ||
                (content == 0xA) ||
                (content == 0xD) ||
                ((content >= 0x20) && (content <= 0xD7FF)) ||
                ((content >= 0xE000) && (content <= 0xFFFD)) ||
                ((content >= 0x10000) && (content <= 0x10FFFF));
        return notEmoji;
    }
}
