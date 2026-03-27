package com.sinosoft.app.common.vo;

/**
 * 考核成员各个KPI指标实际值导入明细
 * @author 中科软
 */
public class PerfTypeVo {
	/** 属性代码类型 */
	private String codeType;

	/** 属性代码类型描述 */
	private String codeTypeDesc;

	/** 属性有效状态 */
	private String validStatus;

	// code个数
	private String codeNumber;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeTypeDesc() {
		return codeTypeDesc;
	}

	public void setCodeTypeDesc(String codeTypeDesc) {
		this.codeTypeDesc = codeTypeDesc;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

}
