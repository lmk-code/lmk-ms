package com.lmk.ms.common.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户登录参数
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/20
 */
@Data
@ApiModel("用户登录参数")
public class LoginParameter {
    /** 用户名 */
    @NotNull(message = "用户名不可以为空")
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    private String username;

    /** 明文密码 */
    @NotNull(message = "密码不可以为空")
    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password;

    /** 验证码 */
    @NotNull(message = "验证码不可以为空")
    @ApiModelProperty(name = "captcha", value = "验证码", required = true)
    private Captcha captcha;
}
