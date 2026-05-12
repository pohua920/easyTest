package com.sinosoft.claim.reins.vo;

import java.io.Serializable;
import java.util.Collection;

import com.sinosoft.sysframework.common.datatype.DateTime;

public class ReinsClaimSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	private String policyNo;
	private DateTime damageDate;
	private String currency;

	private Collection<ReinsClaimDetail> ReinsClaimDetailList;

	public DateTime getDamageDate() {
		return damageDate;
	}

	public void setDamageDate(DateTime damageDate) {
		this.damageDate = damageDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Collection<ReinsClaimDetail> getReinsClaimDetailList() {
		return ReinsClaimDetailList;
	}

	public void setReinsClaimDetailList(Collection<ReinsClaimDetail> reinsClaimDetailList) {
		this.ReinsClaimDetailList = reinsClaimDetailList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
