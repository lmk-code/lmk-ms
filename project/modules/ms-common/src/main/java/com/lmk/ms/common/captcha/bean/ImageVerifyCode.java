package com.lmk.ms.common.captcha.bean;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 图片验证码
 * @author LaoMake
 * @email laomake@hotmail.com
 */
@ApiModel("图片验证码")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ImageVerifyCode {

    /** 验证码ID */
    @ApiModelProperty(value = "验证码ID")
    private String id;

    /** BASE64编码的图片 */
    @ApiModelProperty(value = "BASE64编码的图片")
    private String img;
}
