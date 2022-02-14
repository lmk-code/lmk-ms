package com.lmk.ms.common.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 验证码登录接口
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/20
 */
@Data
@ApiModel("验证码登录参数")
public class SmsLoginParameter {
    @NotNull(message = "账户不可以为空")
    @ApiModelProperty(name = "account", value = "账户", required = true)
    private String account;

    @NotNull(message = "验证码不可以为空")
    @ApiModelProperty(name = "code", value = "验证码", required = true)
    private String code;
}
