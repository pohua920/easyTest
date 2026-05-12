package com.sinosoft.app.common;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.EntityDaoHibernate;
import ins.framework.exception.BusinessException;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sinosoft.app.common.util.PerfConstants;
import com.sinosoft.claim.email.util.EmailConstants;
import com.sinosoft.sys.platform.power.util.SaaPowerUtil;

public class StartupListener implements ServletContextListener {

	public StartupListener() {
	}

	public void contextDestroyed(ServletContextEvent servletcontextevent) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServiceFactory.initServiceFactory(sce.getServletContext());
		SaaPowerUtil.setSystemIdentify("TP");
		EntityDaoHibernate.setOptimizeFind(true);

		Properties perfProperties = new Properties();
		Properties mailProperties = new Properties();
		try {
			perfProperties.load(this.getClass().getClassLoader().getResourceAsStream("/config/perfConstants.properties"));
			mailProperties.load(this.getClass().getClassLoader().getResourceAsStream("/config/mail.properties"));
			System.err.println("配置文件加载成功。。。。。。。");
		} catch (IOException e) {
			throw new BusinessException("获取资源文件出错...", false);
		}
		PerfConstants.initPerfConfig(perfProperties);
		EmailConstants.initEmailConfig(mailProperties);
	}
}
