package com.lmk.ms.common.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.lmk.ms.common.api.StatusEnum;

/**
 * 登录结果
 * @author zdf
 * @email laomake@hotmail.com
 */
@Data
@ApiModel("登录结果")
public class LoginResult {

    /** 状态码 */
    @ApiModelProperty("状态码")
    protected StatusEnum status;

    /** 登录用户 */
    @ApiModelProperty("登录用户")
    protected LoginUser user;

    /** 访问令牌 */
    @ApiModelProperty("访问令牌")
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
