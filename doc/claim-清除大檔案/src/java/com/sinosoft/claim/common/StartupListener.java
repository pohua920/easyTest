package com.sinosoft.claim.common;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.EntityDaoHibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	public StartupListener() {
	}

	public void contextDestroyed(ServletContextEvent servletcontextevent) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServiceFactory.initServiceFactory(sce.getServletContext());
		EntityDaoHibernate.setOptimizeFind(true);

	}
}
