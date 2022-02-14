package com.lmk.ms.common.api;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lmk.ms.common.validate.ValidateError;

/**
 * 响应结果
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@ApiModel("接口响应对象")
public class ResponseResult<T> {

    /** 用于服务端判断的错误代码 */
    @JsonIgnore
    private StatusEnum status;

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("错误信息，请求成功时没有该属性")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @ApiModelProperty("返回数据，请求成功时才有数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @ApiModelProperty("参数校验错误，参数校验失败时才有数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidateError> errors;

    public ResponseResult() {
        super();
        this.status = StatusEnum.SUCCESS;
        this.code = this.status.code;
        this.message = this.status.message;
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回请求成功结果
     * @return
     */
    public static <T> ResponseResult<T> success(T data){
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> status(StatusEnum status){
        ResponseResult<T> result = new ResponseResult<>(status.code, status.message);
        result.setStatus(status);
        return result;
    }

    public static <T> ResponseResult<T> error(StatusEnum status){
        ResponseResult<T> result = new ResponseResult<>(status.code, status.message);
        result.setStatus(status);
        return result;
    }

    public static <T> ResponseResult<T> error(StatusEnum status, String message){
        ResponseResult<T> result = new ResponseResult<>(status.code, message);
        result.setStatus(status);
        return result;
    }

    public static <T> ResponseResult<T> error(StatusEnum status, List<ValidateError> errors){
        ResponseResult<T> result = new ResponseResult<>(status.code, status.message);
        result.setStatus(status);
        result.setErrors(errors);
        return result;
    }

    public static <T> ResponseResult<T> error(StatusEnum status, int code, String message, List<ValidateError> errors){
        ResponseResult<T> result = new ResponseResult<>(status.code, status.message);
        if(code != 0){
            result.setCode(code);
        }
        if(!StringUtils.isBlank(message)){
            result.setMessage(message);
        }
        result.setStatus(status);
        result.setErrors(errors);
        return result;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ValidateError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidateError> errors) {
        this.errors = errors;
    }
}
