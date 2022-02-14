package com.lmk.ms.common.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    @ApiModelProperty(value = "用户ID")
    private Long id;

    /** 登录名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /** 性别：0.女，1.男，2.未设置 */
    @ApiModelProperty(value = "性别：0.女，1.男，2.未设置")
    private Integer gender;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 是否管理员 */
    @ApiModelProperty(value = "是否管理员")
    private Boolean isAdmin;

    /** 是否超级管理员 */
    @ApiModelProperty(value = "是否超级管理员")
    private Boolean isSuperUser;

    /** 是否罗克佳华人员 */
    @ApiModelProperty(value = "是否佳华科技人员")
    private Boolean isRkUser;

    public JwtUser(LoginUser loginUser) {
        this.id = loginUser.getId();
        this.username = loginUser.getUsername();
        this.nickname = loginUser.getNickname();
        this.avatar = loginUser.getAvatar();
        this.gender = loginUser.getGender();
        this.mobile = loginUser.getMobile();
        this.email = loginUser.getEmail();
        this.isAdmin = loginUser.getIsAdmin();
        this.isSuperUser = loginUser.getIsSuperUser();
        this.isRkUser = loginUser.getIsRkUser();
    }
}

