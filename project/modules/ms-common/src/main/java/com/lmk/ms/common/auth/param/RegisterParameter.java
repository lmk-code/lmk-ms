package com.lmk.ms.common.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户注册参数
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/20
 */
@Data
@ApiModel("用户注册参数")
public class RegisterParameter{

    /** 验证码 */
    @NotNull(message = "验证码不可以为空")
    @ApiModelProperty(name = "captcha", value = "验证码", required = true)
    private Captcha captcha;

    /** 用户名 */
    @NotNull(message = "用户名不可以为空")
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    private String username;

    /** 手机号 */
    @NotNull(message = "手机号不可以为空")
    @ApiModelProperty(name = "mobile", value = "手机", required = true)
    private String mobile;

    /** 明文密码 */
    @NotNull(message = "密码不可以为空")
    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password;

    /** 昵称 */
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty(value = "性别：0.女，1.男")
    private Integer gender;

    /** 头像地址 */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
}
