package cn.com.sinosoft.dms.web;

import ins.framework.web.Struts2Action;

import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;

public class CheckSameKeyAction extends Struts2Action {
	private CheckSameKeyService  checkSameKeyService;
	private String tableName;
	private String values;
	
	public CheckSameKeyService getCheckSameKeyService() {
		return checkSameKeyService;
	}

	public void setCheckSameKeyService(CheckSameKeyService checkSameKeyService) {
		this.checkSameKeyService = checkSameKeyService;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public void isSameKey() {
		if(checkSameKeyService.isSameKey(tableName,values)){
			 renderText("sameKey");
		}
		
	}
	public void isSameKeys() {
		String s = getRequest().getParameter("values1");
		String tableName = getRequest().getParameter("tableName");
		if(checkSameKeyService.isSameKeys(tableName,s)){
			 renderText("sameKey");
		}
	}
}
