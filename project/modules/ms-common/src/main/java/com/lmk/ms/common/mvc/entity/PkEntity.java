package com.lmk.ms.common.mvc.entity;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * 带主键的实体类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@ApiModel("带主键标识的实体类")
public abstract class PkEntity <PK extends Serializable> implements Serializable {

    /** 获取主键ID的值 */
    public abstract PK getId();

}
