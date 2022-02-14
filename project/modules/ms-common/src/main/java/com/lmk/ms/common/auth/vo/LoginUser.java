package com.lmk.ms.common.auth.vo;

import java.io.Serializable;
import java.util.List;
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

    /** 客户ID */
    @ApiModelProperty(value = "客户ID")
    protected Long cid;

    /** 区域编码 */
    @ApiModelProperty(value = "区域编码")
    protected Long regionId;

    /** 区域类型 */
    @ApiModelProperty(value = "区域类型")
    protected String regionType;

    /** 区域名称 */
    @ApiModelProperty(value = "区域名称")
    protected String regionTitle;

    /** 角色ID列表 */
    @ApiModelProperty(value = "角色ID列表")
    protected List<Long> roleIds;

    /** 角色名称列表 */
    @ApiModelProperty(value = "角色名称列表")
    protected List<String> roleNames;

    /** 权限ID列表 */
    @ApiModelProperty(value = "权限ID列表")
    protected List<Long> permissionIds;

    /** 权限名称列表 */
    @ApiModelProperty(value = "权限名称列表")
    protected List<String> permissionNames;

    /** 系统关联的菜单列表 */
    @ApiModelProperty(value = "系统关联的菜单列表")
    protected List<String> menuList;

    /** 是否管理员 */
    @ApiModelProperty(value = "是否管理员")
    protected Boolean isAdmin;

    /** 是否超级管理员 */
    @ApiModelProperty(value = "是否超级管理员")
    protected Boolean isSuperUser;

    /** 是否罗克佳华人员 */
    @ApiModelProperty(value = "是否罗克佳华人员")
    protected Boolean isRkUser;

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

