package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 用户信息
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/20
 */
@Data
@ApiModel(value = "UserInfoResponse", description = "用户信息")
public class UserInfoResponse {
    @ApiModelProperty("错误代码")
    private Integer errcode;

    @ApiModelProperty("错误提示")
    private String errmsg;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    private Integer sex;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("头像")
    private String headimgurl;

    @ApiModelProperty("特权信息")
    private String[] privilege;

    @ApiModelProperty("微信开放平台帐号")
    private String unionid;

    @ApiModelProperty("语言")
    private String language;
}
