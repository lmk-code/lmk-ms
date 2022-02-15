package ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import com.lmk.ms.common.mvc.dto.Meta;
import com.lmk.ms.common.mvc.service.impl.BaseServiceImpl;
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>entity.${entityName};
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>dao.${entityName}Dao;
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>service.${entityName}Service;

/**
 * ${entity.entityComment}服务层
 * @author ${author}
 * @email ${email}
 */
@CacheConfig(cacheNames = "<#if (entity.moduleName != "")>${entity.moduleName}_</#if>${entityNameLower}")
@Service("${entityNameLower}Service")
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}Dao, ${entityName}, Long> implements ${entityName}Service {

	@Autowired
	${entityName}Dao ${entityNameLower}Dao;

	@Cacheable
	@Override
	public Meta loadMetaInfo(){
		Meta meta = new Meta("${entity.entityComment}", "${entityNameLower}");

		// 增加表头字段信息
		meta.addColumn("id", "序号")
		<#list entity.fields as field>
		<#if (field.keyType != "ForeignKey")>
			.addColumn("${field.fieldName}", "${field.comment}")<#if (!field_has_next)>;</#if>
		</#if>
		</#list>

		return meta;
	}

	
}