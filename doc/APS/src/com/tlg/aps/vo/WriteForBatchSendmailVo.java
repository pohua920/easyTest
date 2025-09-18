package com.tlg.aps.vo;

import java.math.BigDecimal;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public class WriteForBatchSendmailVo {
	
	private String riskcode;

	private String underwriteenddate;

	private String policyno;

	private BigDecimal count;

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getUnderwriteenddate() {
		return underwriteenddate;
	}

	public void setUnderwriteenddate(String underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}

	public String getPolicyno() {
		return policyno;
	}

	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}
}
