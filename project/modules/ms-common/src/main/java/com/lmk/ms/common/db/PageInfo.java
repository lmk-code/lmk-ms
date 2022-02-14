package com.lmk.ms.common.db;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页信息
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/21
 */
@ApiModel("分页信息")
public class PageInfo implements Serializable {
    /** 当前页码 */
    @ApiModelProperty(value = "当前页码")
    private long page = 1;

    /** 当前页容量 */
    @ApiModelProperty(value = "当前页容量")
    private long size = 10;

    /** 总页数 */
    @ApiModelProperty(value = "总页数")
    private long pages = 0;

    /** 起始序号 */
    @ApiModelProperty(value = "当前页起始记录序号")
    private long first = 0;

    /** 截止序号 */
    @ApiModelProperty(value = "当前页截至记录序号")
    private long last = 0;

    /** 当前页记录数 */
    @ApiModelProperty(value = "当前页总条数")
    private long rows = 0;

    /** 总体记录数 */
    @ApiModelProperty(value = "总条数")
    private long total = 0;

    public PageInfo() {
    }

    public PageInfo(long page, long size) {
        this.page = page;
        this.size = size;
    }

    public PageInfo(long page, long size, long total, long rows) {
        this.page = page;
        this.size = size;
        this.rows = rows;
        this.total = total;
        this.pages = total / size;
        if(total % size != 0){
            this.pages++;
        }

        if(this.total > 0 && this.rows > 0) {
            this.first = (this.page - 1) * this.size + 1;
            this.last = this.first + this.rows - 1;
        }
    }

    public PageInfo(com.baomidou.mybatisplus.extension.plugins.pagination.Page<?> dataPage) {
        this.page = dataPage.getCurrent();
        this.size = dataPage.getSize();
        this.pages = dataPage.getPages();
        this.total = dataPage.getTotal();
        this.rows = dataPage.getRecords().size();

        if(this.total > 0 && this.rows > 0) {
            this.first = (this.page - 1) * this.size + 1;
            this.last = this.first + this.rows - 1;
        }
    }

    public PageInfo(org.springframework.data.domain.Page<?> dataPage) {
        this.page = dataPage.getPageable().getPageNumber();
        this.size = dataPage.getPageable().getPageSize();
        this.pages = dataPage.getTotalPages();
        this.total = dataPage.getTotalElements();
        this.rows = dataPage.getNumberOfElements();

        if(this.total > 0 && this.rows > 0) {
            this.first = (this.page - 1) * this.size + 1;
            this.last = this.first + this.rows - 1;
        }
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "page=" + page +
                ", size=" + size +
                ", pages=" + pages +
                ", first=" + first +
                ", last=" + last +
                ", rows=" + rows +
                ", total=" + total +
                '}';
    }
}
