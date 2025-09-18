package com.tlg.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class AppContext {	
	private static ApplicationContext applicationContext;
	
	static {
		applicationContext=ContextLoader.getCurrentWebApplicationContext();
		if (applicationContext==null ) {
			applicationContext=new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static void setApplicationContext(ApplicationContext applicationContext) {
		AppContext.applicationContext = applicationContext;
	}	
	
	public static Object getBean(String beanId) throws BeansException {
		return applicationContext.getBean(beanId);
	}
}
