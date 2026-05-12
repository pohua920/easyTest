package com.sinosoft.app.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CodeCondition implements Serializable {
	private String codeType;
	private String typeParam;
	private String extraCond;
	private String language;
	private String userCode;
	private String query;
	private String riskCode;
	private String type;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeParam() {
		return typeParam;
	}

	public void setTypeParam(String typeParam) {
		this.typeParam = typeParam;
	}

	public String getExtraCond() {
		return extraCond;
	}

	public void setExtraCond(String extraCond) {
		this.extraCond = extraCond;
	}
}
