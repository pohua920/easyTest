/*
 * @(#)RegistGetCompCode.java	Feb 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.claim.web;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author 中科软
 * @Date <Feb 4, 2013>
 * @description 车辆零件(项目)名称
 */
public class RegistGetCompCodeAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strIndex = "";
	private String partCode = "";
	private String previousFlag = "";

	public String registGetCompCode() throws Exception {
		if (previousFlag == null) {
			previousFlag = "";
		}
		previousFlag = previousFlag.trim();
		return "registGetCompCode";
	}

	public String getStrIndex() {
		return strIndex;
	}

	public void setStrIndex(String strIndex) {
		this.strIndex = strIndex;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPreviousFlag() {
		return previousFlag;
	}

	public void setPreviousFlag(String previousFlag) {
		this.previousFlag = previousFlag;
	}

}
