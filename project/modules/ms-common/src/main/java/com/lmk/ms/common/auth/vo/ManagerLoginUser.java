package com.lmk.ms.common.auth.vo;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台登录用户信息
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Data
@ApiModel("后台登录用户信息")
public class ManagerLoginUser implements Serializable {

    @ApiModelProperty(value = "后台用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "基础用户信息")
    private LoginUser user;

    /** 角色ID列表 */
    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIds;

    /** 角色名称列表 */
    @ApiModelProperty(value = "角色名称列表")
    private List<String> roleNames;

    public ManagerLoginUser() {
    }

    public ManagerLoginUser(LoginUser user) {
        this.user = user;
    }
}
