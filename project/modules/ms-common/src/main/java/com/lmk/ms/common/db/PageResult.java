package com.lmk.ms.common.db;

import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页结果
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/21
 */
@ApiModel("分页查询结果")
public class PageResult<T> implements Serializable {
    /** 分页信息 */
    @ApiModelProperty(value = "分页信息")
    private PageInfo info;

    /** 对象列表 */
    @ApiModelProperty(value = "记录列表")
    private List<T> records;

    public PageResult() {
    }

    public PageResult(long page, long size, long total, List<T> records) {
        this.info = new PageInfo(page, size, total, records.size());
        this.records = records;
    }

    public PageResult(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> dataPage) {
        this.records = dataPage.getRecords();
        this.info =new PageInfo(dataPage);
    }

    public PageResult(org.springframework.data.domain.Page<T> dataPage) {
        this.records = dataPage.getContent();
        this.info =new PageInfo(dataPage);
    }

    public PageInfo getInfo() {
        return info;
    }

    public void setInfo(PageInfo info) {
        this.info = info;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "{" +
                "info=" + info +
                ", records=" + records +
                '}';
    }
}
