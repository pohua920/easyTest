package com.sinosoft.one.bpm.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author zhujinwei
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BackTask {

	int userBeanOffset() default -1;
	
	String userAttributeName() default "";
	
	String businessAttributeName() default "";
	
	
	
}
