package com.lmk.ms.common.exception.biz;

import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.exception.BizException;

/**
 * 用户鉴权异常
 * 包括
 * 无认证
 * 认证错误
 * 认证超期
 * 禁止
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/06
 */
public class AuthenticationException extends BizException {

	StatusEnum status;
	
	public AuthenticationException(String message) {
		super(message);
	}
	
	public AuthenticationException(StatusEnum status) {
		super(status.message);
		this.status = status;
	}
	
	public static AuthenticationException none() {
		return new AuthenticationException(StatusEnum.UNAUTHORIZED_NONE);
	}
	
	public static AuthenticationException error() {
		return new AuthenticationException(StatusEnum.UNAUTHORIZED_ERROR);
	}
	
	public static AuthenticationException expire() {
		return new AuthenticationException(StatusEnum.UNAUTHORIZED_EXPIRE);
	}
	
	public static AuthenticationException forbidden() {
		return new AuthenticationException(StatusEnum.FORBIDDEN);
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
}
