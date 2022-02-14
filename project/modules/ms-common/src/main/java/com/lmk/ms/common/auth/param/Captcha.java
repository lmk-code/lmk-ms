package com.lmk.ms.common.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 验证码信息
 * @author zdf
 * @email laomake@hotmail.com
 */
@ApiModel("验证码信息")
@Data
@Accessors(chain = true)
public class Captcha {

    @ApiModelProperty(value = "序列号", required = true)
    private String id;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
