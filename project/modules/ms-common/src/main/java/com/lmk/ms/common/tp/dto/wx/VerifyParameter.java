package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信接入验证参数
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/17
 */
@Data
@ApiModel(value = "VerifyParameter", description = "微信接入验证参数")
public class VerifyParameter {
    @ApiModelProperty("微信加密签名")
    private String signature;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("随机数")
    private String nonce;

    @ApiModelProperty("随机字符串")
    private String echostr;
}
