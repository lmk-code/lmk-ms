package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信JSSDK配置
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/24
 */
@Data
@ApiModel(value = "WxJsConfig", description = "微信JSSDK配置")
public class WxJsConfig {
    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("s时间戳")
    private String timestamp;

    @ApiModelProperty("混淆字符串")
    private String nonceStr;

    @ApiModelProperty("签名")
    private String signature;
}
