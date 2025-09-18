package com.tlg.aps.vo;

/* mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 */
public class FirUbProcessVo {
	private String batchNo;
	private String riskcode;
	private String orderseq;
	private String isPdf;
	private String remark;
	private String orderseqStatus;
	private String coreNo;
	private String dataType;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public String getIsPdf() {
		return isPdf;
	}
	public void setIsPdf(String isPdf) {
		this.isPdf = isPdf;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderseqStatus() {
		return orderseqStatus;
	}
	public void setOrderseqStatus(String orderseqStatus) {
		this.orderseqStatus = orderseqStatus;
	}
	public String getCoreNo() {
		return coreNo;
	}
	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
