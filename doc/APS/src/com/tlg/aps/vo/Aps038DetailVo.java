package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 */
public class Aps038DetailVo {
	private String batchNo;
	private String orderseq;
	private String orderseqStatus;
	private String riskcode;
    private String dataType;
    private String coreNo;
    private Date startDate;
    private String insuredName;
    private String extraComcode;
    private String comcode;
    private String handler1Name;
    
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public String getOrderseqStatus() {
		return orderseqStatus;
	}
	public void setOrderseqStatus(String orderseqStatus) {
		this.orderseqStatus = orderseqStatus;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getCoreNo() {
		return coreNo;
	}
	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getExtraComcode() {
		return extraComcode;
	}
	public void setExtraComcode(String extraComcode) {
		this.extraComcode = extraComcode;
	}
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public String getHandler1Name() {
		return handler1Name;
	}
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}
}
