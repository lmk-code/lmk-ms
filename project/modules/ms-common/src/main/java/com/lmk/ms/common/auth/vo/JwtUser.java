package com.lmk.ms.common.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT用户信息
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Data
@NoArgsConstructor
@ApiModel("JWT用户")
public class JwtUser implements Serializable {

    /** 用户ID */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户ID")
    private Long id;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 登录名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    public JwtUser(LoginUser loginUser) {
        this.id = loginUser.getId();
        this.mobile = loginUser.getMobile();
        this.username = loginUser.getUsername();
    }
}

