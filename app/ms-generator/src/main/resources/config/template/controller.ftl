package ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>web;

import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lmk.ms.common.mvc.controller.BaseController;
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>entity.${entityName};
import ${packageName}.<#if (entity.moduleName != "")>${entity.moduleName}.</#if>service.${entityName}Service;

/**
 * ${entity.entityComment}控制器
 * @author ${author}
 * @email ${email}
 */
@Api(tags = "${entity.entityComment}接口")
@RestController
@RequestMapping(value = "/<#if (entity.moduleName != "")>${entity.moduleName}/</#if>${entityNameLower}")
public class ${entityName}Controller extends BaseController<${entityName}Service, ${entityName}, Long> {

    @Autowired
	${entityName}Service ${entityNameLower}Service;

	

}