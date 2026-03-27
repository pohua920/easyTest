package com.sinosoft.claim.print.vo;

import java.util.List;
import java.util.Map;

/**
 * 列印對象
 * @author 中科軟
 *
 */
public class JasperPrintObject {
	/** 報表jasper文件位置*/
	private String path;
	/** 參數*/
	private Map<String,Object> parameters;
	/** 結果集對象*/
	private List<?> resultList;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Map<String,Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String,Object> parameters) {
		this.parameters = parameters;
	}
	public List<?> getResultList() {
		return resultList;
	}
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
	
	
}
