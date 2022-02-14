package com.lmk.ms.common.mvc.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 字段信息
 * @author LaoMake
 * @email laomake@hotmail.com
 */
public class Column implements Serializable {

    /** 字段名称 */
    @JsonProperty("dataIndex")
    private String field;

    /** 字段标题 */
    private String title;

    public Column() {
    }

    public Column(String field, String title) {
        this.field = field;
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}