package com.lmk.ms.auth.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求权限
 * @author LaoMake
 * @email laomake@hotmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermission {
    @AliasFor("permissions")
    String[] value() default {};

    @AliasFor("value")
    String[] permissions() default {};
}
