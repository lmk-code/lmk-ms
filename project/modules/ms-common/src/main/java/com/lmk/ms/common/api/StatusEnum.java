package com.lmk.ms.common.api;

import org.springframework.http.HttpStatus;

/**
 * 全局响应状态码
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/15
 */
public enum StatusEnum {

    SUCCESS(HttpStatus.OK, 0, null),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500000000, "服务器异常"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400000000, "请求参数错误"),
    BAD_REQUEST_ParameterNotValid(HttpStatus.BAD_REQUEST, 400000001, "请求参数错误"),
    BAD_REQUEST_ParameterMissing(HttpStatus.BAD_REQUEST, 400000002, "缺少请求参数"),
    BAD_REQUEST_ParameterTypeMismatch(HttpStatus.BAD_REQUEST, 400000003, "参数类型不匹配"),
    BAD_REQUEST_ContentNotReadable(HttpStatus.BAD_REQUEST, 400000004, "缺少请求内容"),
    BAD_REQUEST_RequestHeaderMissing(HttpStatus.BAD_REQUEST, 400000005, "缺少请求头参数"),

    UNAUTHORIZED_NONE(HttpStatus.UNAUTHORIZED, 401000001, "未认证"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, 401000002, "认证错误"),
    UNAUTHORIZED_EXPIRE(HttpStatus.UNAUTHORIZED, 401000003, "认证过期"),

    FORBIDDEN(HttpStatus.FORBIDDEN, 403000000, "权限不足"),

    NOT_FOUND(HttpStatus.NOT_FOUND, 404000000, "资源未找到"),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405000000, "不支持的请求方法"),

    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415000000, "不支持的请求编码格式"),

    // 业务异常，全局：10
    GLOBAL_CAPTCHA_MISS(HttpStatus.OK, 100010001, "缺少验证码参数"),
    GLOBAL_CAPTCHA_ERROR(HttpStatus.OK, 100010002, "验证码错误"),

    // 业务异常，用户模块：20
    USER_FORBIDDEN(HttpStatus.OK, 200010001, "当前用户没有访问权限"),
    USER_USERNAME_ERROR(HttpStatus.OK, 200010002, "用户名错误"),
    USER_USERNAME_DISABLE(HttpStatus.OK, 200010003, "用户名不可用"),
    USER_MOBILE_ERROR(HttpStatus.OK, 200010004, "手机号错误"),
    USER_MOBILE_DISABLE(HttpStatus.OK, 200010005, "手机号不可用"),
    USER_PASSWORD_ERROR(HttpStatus.OK, 200010006, "密码错误");



    /** HTTP状态码 */
    public HttpStatus httpStatus;

    /** 代码 */
    public int code;

    /** 信息 */
    public String message;

    StatusEnum(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
