package com.lmk.ms.common.mvc.entity;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 带有修改记录的 主键的实体类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@ApiModel("带有修改记录的 主键的实体类")
public abstract class RecordEntity<PK extends Serializable> extends PkEntity<PK> {

    /** 删除状态：1.删除，0.正常 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "删除状态：1.删除，0.正常")
    protected Integer delFlag;

    /** 创建人 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "创建人")
    protected String createBy;

    /** 创建时间 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "创建时间")
    protected Date createTime;

    /** 修改人 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "修改人")
    protected String updateBy;

    /** 修改时间 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "修改时间")
    protected Date updateTime;

    /** 备注 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "备注")
    protected String remark;

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
