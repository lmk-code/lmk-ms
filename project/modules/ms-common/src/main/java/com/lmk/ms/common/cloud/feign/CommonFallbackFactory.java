package com.lmk.ms.common.cloud.feign;

import org.slf4j.Logger;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 通用地快速返回工厂
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
public abstract class CommonFallbackFactory<T> implements FallbackFactory<T> {
    public void before(Throwable cause, Logger log){
        log.error("{} - {}", cause.getClass().getSimpleName(), cause.getMessage());
    }
}
