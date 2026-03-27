package com.sinosoft.claim.print.web;

import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.sinosoft.claim.print.vo.PrintObject;

public class JasperReportAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private List<Object> list = new ArrayList<Object>(); // 相当于dataSource,集合属性，查询的结果集[如果想使用list,则connection一定为null，否则没用]
	private Connection connection = null; // 数据源连接
	private String format = "";
	private Map<String, Object> param = new HashMap<String, Object>();; // 传递的参数

	public String execute() throws Exception {
		PrintObject printObject = new PrintObject();
		DataSource dataSource = (DataSource)ServiceFactory.getService("dataSource");
		connection = dataSource.getConnection();
		list.add(printObject);
		param.put("IMGPATH", super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/image/logo.jpg");
		return SUCCESS;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
