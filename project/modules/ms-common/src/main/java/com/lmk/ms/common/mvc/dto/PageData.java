package com.lmk.ms.common.mvc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lmk.ms.common.db.PageResult;

/**
 * 分页查询列表数据
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询列表数据")
public class PageData<T> {
    @ApiModelProperty("表头信息")
    private Meta meta;

    @ApiModelProperty("查询结果")
    private PageResult<T> result;
}
