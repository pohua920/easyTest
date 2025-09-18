package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bj016
 *
 */
@XmlRootElement
public class FirFeedbackVo {

	private String rstType;
	private String fkOrderSeq;
	private String inscoStatus;
	private String inscoFeedback;
	private String policyno;
	private String filenameCtf;
	private String batchNo;
	private String batchSeq;
	
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
	public String getFilenameCtf() {
		return filenameCtf;
	}
	public void setFilenameCtf(String filenameCtf) {
		this.filenameCtf = filenameCtf;
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
}
