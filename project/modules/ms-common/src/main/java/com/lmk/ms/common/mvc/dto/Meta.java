package com.lmk.ms.common.mvc.dto;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 元信息
 * @author LaoMake
 * @email laomake@hotmail.com
 */
public class Meta implements Serializable {
    /** 名称 */
    private String title;

    /** 代码 */
    private String code;

    /** 字段信息 */
    private List<Column> columns;

    public Meta() {
        columns = new ArrayList<>();
    }

    public Meta(String title, String code) {
        this.title = title;
        this.code = code;
        columns = new ArrayList<>();
    }

    public Meta addColumn(Column column){
        columns.add(column);
        return this;
    }

    public Meta addColumn(String field, String title){
        columns.add(new Column(field, title));
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}