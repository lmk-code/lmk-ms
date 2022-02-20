package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信访问令牌返回对象
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/20
 */
@Data
@ApiModel(value = "OAuthAccessTokenResponse", description = "微信访问令牌返回对象")
public class OAuthAccessTokenResponse {

    @ApiModelProperty("错误代码")
    private Integer errcode;

    @ApiModelProperty("错误提示")
    private String errmsg;

    @ApiModelProperty("访问令牌")
    private String access_token;

    @ApiModelProperty("有效时长，单位秒")
    private Long expires_in;

    @ApiModelProperty("刷新令牌")
    private String refresh_token;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("作用域")
    private String scope;
}
