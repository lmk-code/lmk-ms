package com.lmk.ms.common.op.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 扫码查询结果
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/24
 */
@Data
@ApiModel("扫码查询结果")
public class QrCheck {

    @ApiModelProperty(value = "会话ID")
    private String sid;

    /** 扫码用户昵称 */
    @ApiModelProperty(value = "扫码用户昵称")
    private String nickname;

    /** 扫码用户头像 */
    @ApiModelProperty(value = "扫码用户头像")
    private String avatar;

    @ApiModelProperty(value = "回跳地址")
    private String url;
}
