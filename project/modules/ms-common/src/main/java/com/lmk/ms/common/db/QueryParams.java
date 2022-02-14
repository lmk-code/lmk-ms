package com.lmk.ms.common.db;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页查询参数
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@ApiModel("分页查询参数")
public class QueryParams implements Serializable {
    /** 当前页码 */
    @Min(value = 1, message = "当前页码必须大于0")
    @ApiModelProperty(name = "page", value = "当前页码必须大于0")
    private Integer page = 1;

    /** 单页容量 */
    @Max(value = 200, message = "单页容量不可以大于200")
    @ApiModelProperty(name = "size", value = "单页容量不可以大于200")
    private Integer size = 10;

    /** 查询字段 */
    @ApiModelProperty(name = "columns", value = "查询字段")
    private String[] columns;

    /** 查询条件 */
    @ApiModelProperty(name = "search", value = "查询条件")
    private String search;

    /** 排序条件, 如：id_ASC 或 id_DESC__age_ASC等 */
    @ApiModelProperty(name = "sort", value = "排序条件")
    private String sort = "id_DESC";

    public QueryParams() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
