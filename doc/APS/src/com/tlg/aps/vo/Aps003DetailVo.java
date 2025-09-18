package com.tlg.aps.vo;

import java.util.Date;

/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)*/
public class Aps003DetailVo {
	private String batchNo;
	private String rstType;
	private String fkOrderSeq;
	private String inscoStatus;
	private String inscoFeedback;
	private String policyno;
	private Date  dcreate;
	private String comcode;
	private String handler1code;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRstType() {
		return rstType;
	}
	public void setRstType(String rstType) {
		this.rstType = rstType;
	}
	public String getFkOrderSeq() {
		return fkOrderSeq;
	}
	public void setFkOrderSeq(String fkOrderSeq) {
		this.fkOrderSeq = fkOrderSeq;
	}
	public String getInscoStatus() {
		return inscoStatus;
	}
	public void setInscoStatus(String inscoStatus) {
		this.inscoStatus = inscoStatus;
	}
	public String getInscoFeedback() {
		return inscoFeedback;
	}
	public void setInscoFeedback(String inscoFeedback) {
		this.inscoFeedback = inscoFeedback;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
}
