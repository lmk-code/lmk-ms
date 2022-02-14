package com.lmk.ms.common.exception;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.validate.ValidateError;
import org.springframework.http.HttpStatus;

/**
 * 全局业务异常
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
public class BizException extends RuntimeException {

    /** 请求响应状态 */
    @JsonIgnore
    private StatusEnum status;

    /** 异常类型代码 */
    private HttpStatus httpStatus;

    /** 异常类型代码 */
    private int code;

    /** 异常类型说明 */
    private String message;

    /** 验证错误 */
    private List<ValidateError> errors;

    public BizException(String message) {
        super(message);
        this.status = StatusEnum.INTERNAL_SERVER_ERROR;
        this.httpStatus = StatusEnum.INTERNAL_SERVER_ERROR.httpStatus;
        this.code = this.status.code;
        this.message = this.status.message;
    }

    public BizException(StatusEnum status, String message) {
        super(message);
        this.status = status;
        this.httpStatus = status.httpStatus;
        this.code = status.code;
        this.message = message;
    }

    public BizException(StatusEnum status, int code, String message) {
        super(message);
        this.status = status;
        this.httpStatus = status.httpStatus;
        this.code = code;
        this.message = message;
    }

    public BizException(StatusEnum status, String message, ValidateError error) {
        super(message);
        this.status = status;
        this.httpStatus = status.httpStatus;
        this.code = status.code;
        this.message = message;
        this.errors = Lists.newArrayList(error);
    }

    public BizException(StatusEnum status, int code, String message, ValidateError error) {
        super(message);
        this.status = status;
        this.httpStatus = status.httpStatus;
        this.code = code;
        this.message = message;
        this.errors = Lists.newArrayList(error);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidateError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidateError> errors) {
        this.errors = errors;
    }

    /**
     * 直接抛出一个固定类型的异常
     * @param status
     */
    public static void throwEnum(StatusEnum status) {
        throw new BizException(status, status.message);
    }

    /**
     * 直接抛出一个固定类型的异常，并重新定制错误提示
     * @param status
     * @param message
     */
    public static void throwEnum(StatusEnum status, String message) {
        throw new BizException(status, message);
    }

    public static void throw200(StatusEnum se) {
        BizException exception = new BizException(se.message);
        exception.setStatus(se);
        exception.setHttpStatus(StatusEnum.SUCCESS.httpStatus);
        exception.setCode(se.code);
        exception.setMessage(se.message);
        throw exception;
    }

    public static void throw200(StatusEnum se, String message) {
        BizException exception = new BizException(message);
        exception.setStatus(se);
        exception.setHttpStatus(StatusEnum.SUCCESS.httpStatus);
        exception.setCode(se.code);
        exception.setMessage(message);
        throw exception;
    }

    public static void throw200(int code, String message) {
        BizException exception = new BizException(message);
        exception.setStatus(StatusEnum.SUCCESS);
        exception.setHttpStatus(StatusEnum.SUCCESS.httpStatus);
        exception.setCode(code);
        exception.setMessage(message);
        throw exception;
    }

    /**
     * 直接抛出一个400，通常是请求参数错误，code为 400000000
     * @param message
     */
    public static void throw400(String message) {
        throwEnum(StatusEnum.BAD_REQUEST, message);
    }

    /**
     * 直接抛出一个400，通常是请求参数错误，code为自定义
     * @param message
     */
    public static void throw400(int code, String message) {
        throw new BizException(StatusEnum.BAD_REQUEST, code, message);
    }

    /**
     * 直接抛出一个401
     */
    public static void throw401() {
        throwEnum(StatusEnum.UNAUTHORIZED_NONE);
    }

    /**
     * 抛出一个401，自定义消息
     * @param message
     */
    public static void throw401(String message) {
        throwEnum(StatusEnum.UNAUTHORIZED_NONE, message);
    }

    /**
     * 直接抛出一个403，通常是权限不足，code为 403000000
     * @param message
     */
    public static void throw403(String message) {
        throwEnum(StatusEnum.FORBIDDEN, message);
    }

    /**
     * 直接抛出一个403，通常是权限不足，code为自定义
     *
     * @param code
     * @param message
     */
    public static void throw403(int code, String message) {
        throw new BizException(StatusEnum.FORBIDDEN, code, message);
    }

    /**
     * 直接抛出一个500，通常是无法处理的错误
     *
     * @param message
     */
    public static void throw500(String message) {
        throwEnum(StatusEnum.INTERNAL_SERVER_ERROR, message);
    }

    public static void throw500(int code, String message) {
        throw new BizException(StatusEnum.INTERNAL_SERVER_ERROR, code, message);
    }
}
