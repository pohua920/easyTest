package cn.com.sinosoft.cas;

import ins.framework.common.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wubo
 * @version 1.0
 * Date: 2008-6-4
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		ServiceFactory.initServiceFactory(getServletContext());
	}

}