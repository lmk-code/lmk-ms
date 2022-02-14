package com.lmk.ms.common.mvc.dto;

import com.lmk.ms.common.api.StatusEnum;

/**
 * 业务层处理结果
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public class ServiceResult {
    /** 处理状态 */
    private StatusEnum status;

    /** 返回结果 */
    private Object result;

    public ServiceResult() {
    }

    /**
     * 快速返回成功结果
     * @param result
     * @return
     */
    public static ServiceResult success(Object result){
        ServiceResult sr = new ServiceResult(StatusEnum.SUCCESS);
        sr.setResult(result);
        return sr;
    }

    /**
     * 快速返回失败结果
     * @param result
     * @return
     */
    public static ServiceResult error(Object result){
        ServiceResult sr = new ServiceResult(StatusEnum.INTERNAL_SERVER_ERROR);
        sr.setResult(result);
        return sr;
    }

    /**
     * 返回失败结果
     * @param status
     * @param result
     * @return
     */
    public static ServiceResult error(StatusEnum status, Object result){
        ServiceResult sr = new ServiceResult(status);
        sr.setResult(result);
        return sr;
    }

    public ServiceResult(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
