package com.sinosoft.claim.print.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

public class CommonAction extends Struts2Action {
	
	private static final long serialVersionUID = 1L;
	private Object resultList = null;
	private Map<String, Object> param = new HashMap<String, Object>();

	public String execute() throws Exception {
		resultList = new JREmptyDataSource();
		return SUCCESS;
	}
	
	
	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}
	
	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

}
