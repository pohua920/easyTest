package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public class Aps041MainVo {
	private String orderseq;
	private String riskcode;
	private Date dcreate;
	private String insuredName;
	private String branchName;
	private Date startDate;
	private Date underwriteenddate;
	private String policyNo;
	private String fbStatus;
	private String proposalNo;
	private String extracomName;
	
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
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
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getFbStatus() {
		return fbStatus;
	}
	public void setFbStatus(String fbStatus) {
		this.fbStatus = fbStatus;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	public String getExtracomName() {
		return extracomName;
	}
	public void setExtracomName(String extracomName) {
		this.extracomName = extracomName;
	}
	
}
