package com.sinosoft.undwrt.common.servlet;

import ins.framework.common.ServiceFactory;
import ins.framework.utils.FileUtils;

import java.io.FileNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.sinosoft.sysframework.common.util.PlatformUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBFactory;
import com.sinosoft.sysframework.web.control.ExtendedStrutsActionServlet;

/**
 * 初始化配置文件Servlet.
 */
public class InitAppServlet extends HttpServlet {
	
	/**
	 * 初始化配置文件.
	 * 
	 * @throws ServletException
	 *             servlet異常
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		super.init();
		String configPath = getServletContext().getRealPath("/");
		if ((configPath == null) || (configPath.trim().length() == 0)) {
			String actionPathName = FileUtils
					.getRealPathName(ExtendedStrutsActionServlet.class);

			configPath = actionPathName.substring(0,
					actionPathName.lastIndexOf("/WEB-INF"));
		}

		if (!(configPath.endsWith(PlatformUtils.FILE_SEPARATOR))) {
			configPath = configPath + PlatformUtils.FILE_SEPARATOR;
		}
		configPath = configPath + "WEB-INF" + PlatformUtils.FILE_SEPARATOR
				+ "config" + PlatformUtils.FILE_SEPARATOR;

		initWebApplicationConfig(configPath);
		//System.out.println(".....................ServiceFactoryInitServlet has inited 111111111111111!");
		ServiceFactory.initServiceFactory(super.getServletContext());
		//System.out.println(".....................ServiceFactoryInitServlet has inited 2222222222222222!");
	}

	/**
	 * 初始化給定路徑的配置文件.
	 * 
	 * @param configPath
	 *            配置文件路徑
	 * @throws ServletException
	 *             servlet異常
	 */
	public static synchronized void initWebApplicationConfig(String configPath)
			throws ServletException {
		try {
			try {
				Logger.configure(configPath + "log.properties");
			} catch (FileNotFoundException e) {
				System.out.println("Can't init logger");
			}
			try {
				AppConfig.configure(configPath + PlatformUtils.FILE_SEPARATOR
						+ "appconfig");
			} catch (FileNotFoundException e) {
				System.out.println("Can't init appconfig");
			}
			try {
				UserException.configure(configPath + "UserException.xml");
			} catch (FileNotFoundException e) {
				System.out.println("Can't init user exception");
			}
			try {
				DBFactory.configure(configPath + "dbmanager-config.xml");
			} catch (FileNotFoundException e) {
				System.out.println("Can't init dbmanager config");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}