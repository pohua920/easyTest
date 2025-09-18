package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public class Aps041DetailVo1 {
	private String orderseq;
	private String riskcode;
	private String isPdf;
	private Date startDate;
	private String insuredName;
	private String branchName;
	private String branchNo;
	private String policyNo;
	private Date underwriteenddate;
	
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
	public String getIsPdf() {
		return isPdf;
	}
	public void setIsPdf(String isPdf) {
		this.isPdf = isPdf;
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public Date getUnderwriteenddate() {
		return underwriteenddate;
	}
	public void setUnderwriteenddate(Date underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}
}
