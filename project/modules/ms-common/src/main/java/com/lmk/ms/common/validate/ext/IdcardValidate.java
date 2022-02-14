package com.lmk.ms.common.validate.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import com.lmk.ms.common.utils.ValidateUtils;

/**
 * 身份证验证
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/06
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IdcardValidator.class)
public @interface IdcardValidate {

    String message() default "不是一个合法的身份证号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class IdcardValidator implements ConstraintValidator<IdcardValidate, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)) {
			return true;
		}

		return ValidateUtils.isIdcard(value);
	}
	
}
