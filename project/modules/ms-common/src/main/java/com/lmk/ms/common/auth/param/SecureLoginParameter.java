package com.lmk.ms.common.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 安全码登录接口参数
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/20
 */
@Data
@ApiModel("安全登录参数")
public class SecureLoginParameter {
    @NotNull(message = "账户不可以为空")
    @ApiModelProperty(name = "account", value = "账户", required = true)
    private String account;

    @NotNull(message = "密码不可以为空")
    @ApiModelProperty(name = "password", value = "经过加密的密码", required = true)
    private String password;

    @NotNull(message = "密钥ID不可以为空")
    @ApiModelProperty(name = "keyId", value = "密钥ID", required = true)
    private String keyId;
}
