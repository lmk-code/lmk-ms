package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信用户授权令牌
 * @author laomake@hotmail.com
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OAuthToken", description = "微信用户授权令牌")
public class OAuthToken {
    @ApiModelProperty("有效时长，单位秒")
    private String openid;

    @ApiModelProperty("访问令牌")
    private String accessToken;
}
