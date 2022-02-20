package com.lmk.ms.common.tp.dto;

import com.lmk.ms.common.tp.support.TpType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 三方平台用户信息
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/20
 */
@Data
@ApiModel(value = "TpUserInfo", description = "三方平台用户信息")
public class TpUserInfo {

    @ApiModelProperty("三方平台类型")
    private TpType type;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("-1.未知, 0.女性，1.男性")
    private Integer gender;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("头像")
    private String avatar;
}
