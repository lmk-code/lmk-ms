package com.lmk.ms.common.auth.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Data
@NoArgsConstructor
@ApiModel("登录用户")
public class LoginUser implements Serializable {

    /** 用户ID */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户ID")
    protected Long id;

    /** 登录名 */
    @ApiModelProperty(value = "用户名")
    protected String username;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    protected String nickname;

    /** 头像 */
    @ApiModelProperty(value = "头像")
    protected String avatar;

    /** 性别：0.女，1.男，2.未设置 */
    @ApiModelProperty(value = "性别：0.女，1.男，2.未设置")
    protected Integer gender;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    protected String mobile;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    protected String email;

    public LoginUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public LoginUser(Long id, String username, String nickname, String avatar) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
    }
}

