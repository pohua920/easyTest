package com.tlg.aps.vo;

import java.math.BigDecimal;

public class Aps017ResultVo {
	private BigDecimal oid;
	private String businessnature;
	private String branchNo;
	private String branchName;
	private String handler1code;
	private String handler1name;
	private String comcode;
	private String deleteFlag;
	
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
	public String getHandler1name() {
		return handler1name;
	}
	public void setHandler1name(String handler1name) {
		this.handler1name = handler1name;
	}
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

}
