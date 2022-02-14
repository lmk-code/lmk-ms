package com.lmk.ms.common.auth.vo;

import lombok.Data;
import com.lmk.ms.common.api.StatusEnum;

/**
 * 登录结果
 * @author zdf
 * @email laomake@hotmail.com
 */
@Data
public class LoginResult {

    /** 状态码 */
    protected StatusEnum status;

    /** 登录用户信息 */
    protected LoginUser user;

    /** 用户对象 */
    protected JwtToken token;

    public LoginResult() {
    }

    public LoginResult(StatusEnum status) {
        this.status = status;
    }

    public LoginResult(StatusEnum status, LoginUser user, JwtToken token) {
        this.status = status;
        this.user = user;
        this.token = token;
    }
}
