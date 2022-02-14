package com.lmk.ms.common.validate.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import com.google.common.collect.Lists;

/**
 * 枚举字段验证
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/06
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValidate {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String enums(); // 形如 0:无效;1:有效 的枚举字段
}

class EnumValidator implements ConstraintValidator<EnumValidate, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value == null || StringUtils.isBlank(value.toString())) {
			return true;
		}
		ConstraintValidatorContextImpl impl = (ConstraintValidatorContextImpl) context;
		EnumValidate annotation = (EnumValidate) impl.getConstraintDescriptor().getAnnotation();
		
		// 0:无效;1:有效
		String enums = annotation.enums();
		
		List<String> enumList = Lists.newArrayList();
		String[] enumArray = StringUtils.split(enums, ";");
		
		for(String e : enumArray) {
			String[] split = StringUtils.split(e, ":");
			if(split.length > 1) {
				enumList.add(split[0]);
			}
		}
		if(enumList.contains(value.toString())) {
			return true;
		}
		
		return false;
	}
	
}
