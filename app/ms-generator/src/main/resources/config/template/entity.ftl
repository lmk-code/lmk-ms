package ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>entity;

<#if ((entity.mtms?size>0)||(entity.children?size>0))>
import java.util.List;
</#if>
<#if ((entity.mtms?size>0)||(entity.children?size>0))>
import java.util.ArrayList;
</#if>
<#if entity.hasDateType>
import java.util.Date;
</#if>
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import com.lmk.ms.common.mvc.entity.PkEntity;

/**
 * ${entity.entityComment}实体
 * @author ${author}
 * @email ${email}
 */
@Data
@Accessors(chain = true)
@ApiModel("${entity.entityComment}")
@TableName("${entity.tableName}")
public class ${entity.entityName} extends PkEntity<Long> {
	
	private static final long serialVersionUID = ${entity.serialVersionUID}L;

	@ApiModelProperty(value = "序号")
	@TableId(type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long ${entity.primaryKey.fieldName};

<#list entity.manyToOnes as manyToOne>
	/** ${manyToOne.entityComment} */
	@ApiModelProperty(value = "${manyToOne.entityComment}")
	private ${manyToOne.entityName} ${manyToOne.entityNameLower};

</#list>
<#list entity.fields as field>
<#if (field.keyType != "ForeignKey")>
	/** ${field.comment} */
	@ApiModelProperty(value = "${field.comment}")
	private ${field.fieldDataType} ${field.fieldName};

</#if>
</#list>
<#list entity.children as child>
	@ApiModelProperty(value = "${child.entityComment}", dataType = "List")
	private List<${child.entityName}> ${child.entityNameLower}s = new ArrayList<${child.entityName}>(0);

</#list>
<#list entity.mtms as manyToMany>
	@ApiModelProperty(value = "${manyToMany.entityComment}", dataType = "List")
	private List<${manyToMany.entityName}> ${manyToMany.entityNameLower}s = new ArrayList<${manyToMany.entityName}>(0);
	
</#list>
	public ${entity.entityName}() {
	}

	public ${entity.entityName}(Long id) {
		this.id = id;
	}
}