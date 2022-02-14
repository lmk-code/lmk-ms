package com.lmk.ms.auth.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求VIP权益
 * @author LaoMake
 * @email laomake@hotmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasVip {
    @AliasFor("vip")
    String[] value() default {};

    /**
     * VIP 代码列表
     * @return
     */
    @AliasFor("value")
    String[] vip() default {};
}
