package com.lmk.ms.common.mvc.dto;

import java.io.Serializable;

/**
 * 菜单功能
 * @author LaoMake
 * @email laomake@hotmail.com
 */
public class Action implements Serializable {
    /** 添加 */
    private Boolean create = true;

    /** 删除 */
    private Boolean delete = true;

    /** 更新 */
    private Boolean update = true;

    /** 查询 */
    private Boolean query = true;

    /** 扩展操作 */
    private Boolean extend = true;

    public Action() {
    }

    public Action(Boolean create, Boolean delete, Boolean update, Boolean query, Boolean extend) {
        this.create = create;
        this.delete = delete;
        this.update = update;
        this.query = query;
        this.extend = extend;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getQuery() {
        return query;
    }

    public void setQuery(Boolean query) {
        this.query = query;
    }

    public Boolean getExtend() {
        return extend;
    }

    public void setExtend(Boolean extend) {
        this.extend = extend;
    }
}
