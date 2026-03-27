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
public @interface StartProcess {

	String processId() default "";

    int processIdBeanOffset() default -1;

    String processIdAttributeName() default "";

	int businessBeanOffset();

	String businessIdAttributeName() default "";

}
