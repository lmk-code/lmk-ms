package com.lmk.ms.common.op.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/23
 */
@Data
@ApiModel("扫码登录")
public class QrLogin {

    /** 会话ID */
    @ApiModelProperty(value = "会话ID")
    private String sid;

    /** 扫码地址 */
    @ApiModelProperty(value = "扫码地址")
    private String scanUrl;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "平台类型")
    private String type;

    /** OpenId */
    @ApiModelProperty(value = "OpenId")
    private String openId;

    /** 扫码用户昵称 */
    @ApiModelProperty(value = "扫码用户昵称")
    private String nickname;

    /** 扫码用户头像 */
    @ApiModelProperty(value = "扫码用户头像")
    private String avatar;

    /** 回跳地址 */
    @ApiModelProperty(value = "回跳地址")
    private String redirectUrl;

    /** 扫码状态：0.等待扫码，1.等待确认 */
    @ApiModelProperty(value = "扫码状态：0.等待扫码，1.等待确认，2.已确认")
    private Integer status;
}
