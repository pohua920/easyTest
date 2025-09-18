package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public class Aps041DetailVo2 {
	private String orderseq;
	private String orderseqStatus;
	private String riskcode;
	private String coreNo;
	private Date startDate;
	private Date underwriteenddate;
	private String insuredName;
	private String branchName;
	private String orderType;
	private String policyNo;
	
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
	public Date getUnderwriteenddate() {
		return underwriteenddate;
	}
	public void setUnderwriteenddate(Date underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
}
