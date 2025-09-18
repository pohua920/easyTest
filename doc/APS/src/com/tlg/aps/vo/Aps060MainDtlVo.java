package com.tlg.aps.vo;

import java.math.BigDecimal;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案  **/
public class Aps060MainDtlVo {
	
	private String batchNo;
	
	private String oldpolicyno;
	
	private String checkErrMsg;
	
	private BigDecimal oid;
		
	private BigDecimal batchseq;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOldpolicyno() {
		return oldpolicyno;
	}

	public void setOldpolicyno(String oldpolicyno) {
		this.oldpolicyno = oldpolicyno;
	}

	public String getCheckErrMsg() {
		return checkErrMsg;
	}

	public void setCheckErrMsg(String checkErrMsg) {
		this.checkErrMsg = checkErrMsg;
	}

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public BigDecimal getBatchseq() {
		return batchseq;
	}

	public void setBatchseq(BigDecimal batchseq) {
		this.batchseq = batchseq;
	}

}
