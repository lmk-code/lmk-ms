package ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>service;

import com.lmk.ms.common.mvc.service.BaseService;
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>entity.${entityName};

/**
 * ${entity.entityComment}服务层
 * @author ${author}
 * @email ${email}
 */
public interface ${entityName}Service extends BaseService<${entityName}, Long> {
	
}