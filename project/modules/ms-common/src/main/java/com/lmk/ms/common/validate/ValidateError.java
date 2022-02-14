package com.lmk.ms.common.validate;

import java.util.List;
import io.swagger.annotations.ApiModel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.lmk.ms.common.utils.MsReflectionUtils;

/**
 * 校验异常结构体
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("参数校验错误")
public class ValidateError {
	
	@ApiModelProperty(value = "错误码")
	String code;
	
	@ApiModelProperty(value = "错误提示")
	String msg;
	
	@ApiModelProperty(value = "错误字段")
	String field;
	
	@ApiModelProperty(value = "错误字段名称")
	String fieldName;
	
	@ApiModelProperty(value = "错误字段值")
	Object fieldValue;

	/**
	 * 将自动校验异常转为 ValidateError
	 * @author lvshengchao
	 * @since 2021年1月7日
	 *
	 * @param result
	 * @return
	 */
	public static List<ValidateError> convert(BindingResult result){
		List<ValidateError> list = Lists.newArrayList();
		if(result == null) {
			return list;
		}
		
		List<ObjectError> allErrors = result.getAllErrors();
    	for(ObjectError error : allErrors) {
    		ValidateError ve = new ValidateError();
    		ve.setCode(error.getCode());
    		ve.setMsg(error.getDefaultMessage());
    		
    		if(error instanceof FieldError) {
    			FieldError fieldError = (FieldError) error;
    			String propertyName = MsReflectionUtils.getApiModelProperty(result.getTarget().getClass(), fieldError.getField());
    			
    			ve.setField(fieldError.getField());
    			ve.setFieldName(propertyName);
    			ve.setFieldValue(fieldError.getRejectedValue());
    		}
    		
    		list.add(ve);
    	}
    	
    	return list;
	}
	
}
