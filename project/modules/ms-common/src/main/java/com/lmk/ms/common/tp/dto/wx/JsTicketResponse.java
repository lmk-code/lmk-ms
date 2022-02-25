package com.lmk.ms.common.tp.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信JS Ticket返回对象
 * @author zhudefu@rockontrol.com
 * @version 1.0
 * @date 2022/02/20
 */
@Data
@ApiModel(value = "JsTicketResponse", description = "微信JS Ticket返回对象")
public class JsTicketResponse {

    @ApiModelProperty("错误代码")
    private Integer errcode;

    @ApiModelProperty("错误提示")
    private String errmsg;

    @ApiModelProperty("访问令牌")
    private String ticket;

    @ApiModelProperty("有效时长，单位秒")
    private Long expires_in;
}
