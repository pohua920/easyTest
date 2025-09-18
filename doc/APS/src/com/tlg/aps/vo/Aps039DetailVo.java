package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 */
public class Aps039DetailVo {
	private BigDecimal oid;
	private String batchNo;
	private String batchSeq;
	private String fkOrderSeq;
	private String policyno;
	private String orderSeqStatus;
	private Date printCtfTime;
	private String spBatchNo;
	private Date bkTime;
	private String bkFilename;
	private String bkType;
	private String prodCode;
	private String planCode;
	private String areaCode;
	private String ownerName;
	private String debitType;
	private String sendType;
	private String sendToBank;
	private String recipient;
	private String recipientZip;
	private String recipientCity;
	private String recipientCounty;
	private String recipientAddr;
	private String remark;
	private String icreate;
	private Date dcreate;
	private String iupdate;
	private Date dupdate;
	//mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔
	private String inscoFeedback;
	
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getFkOrderSeq() {
		return fkOrderSeq;
	}
	public void setFkOrderSeq(String fkOrderSeq) {
		this.fkOrderSeq = fkOrderSeq;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getOrderSeqStatus() {
		return orderSeqStatus;
	}
	public void setOrderSeqStatus(String orderSeqStatus) {
		this.orderSeqStatus = orderSeqStatus;
	}
	public Date getPrintCtfTime() {
		return printCtfTime;
	}
	public void setPrintCtfTime(Date printCtfTime) {
		this.printCtfTime = printCtfTime;
	}
	public String getSpBatchNo() {
		return spBatchNo;
	}
	public void setSpBatchNo(String spBatchNo) {
		this.spBatchNo = spBatchNo;
	}
	public Date getBkTime() {
		return bkTime;
	}
	public void setBkTime(Date bkTime) {
		this.bkTime = bkTime;
	}
	public String getBkFilename() {
		return bkFilename;
	}
	public void setBkFilename(String bkFilename) {
		this.bkFilename = bkFilename;
	}
	public String getBkType() {
		return bkType;
	}
	public void setBkType(String bkType) {
		this.bkType = bkType;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getDebitType() {
		return debitType;
	}
	public void setDebitType(String debitType) {
		this.debitType = debitType;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendToBank() {
		return sendToBank;
	}
	public void setSendToBank(String sendToBank) {
		this.sendToBank = sendToBank;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecipientZip() {
		return recipientZip;
	}
	public void setRecipientZip(String recipientZip) {
		this.recipientZip = recipientZip;
	}
	public String getRecipientCity() {
		return recipientCity;
	}
	public void setRecipientCity(String recipientCity) {
		this.recipientCity = recipientCity;
	}
	public String getRecipientCounty() {
		return recipientCounty;
	}
	public void setRecipientCounty(String recipientCounty) {
		this.recipientCounty = recipientCounty;
	}
	public String getRecipientAddr() {
		return recipientAddr;
	}
	public void setRecipientAddr(String recipientAddr) {
		this.recipientAddr = recipientAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIcreate() {
		return icreate;
	}
	public void setIcreate(String icreate) {
		this.icreate = icreate;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getIupdate() {
		return iupdate;
	}
	public void setIupdate(String iupdate) {
		this.iupdate = iupdate;
	}
	public Date getDupdate() {
		return dupdate;
	}
	public void setDupdate(Date dupdate) {
		this.dupdate = dupdate;
	}
	/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 start*/
	public String getInscoFeedback() {
		return inscoFeedback;
	}
	public void setInscoFeedback(String inscoFeedback) {
		this.inscoFeedback = inscoFeedback;
	}
	/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 end*/
}
