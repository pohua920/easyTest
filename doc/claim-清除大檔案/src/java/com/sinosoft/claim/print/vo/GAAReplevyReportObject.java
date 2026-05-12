package com.sinosoft.claim.print.vo;

import com.ctbcins.util.print.Report;

/**
 * //mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 start
 * 工程險追償計算書
 * 
 * @author BK007
 */
public class GAAReplevyReportObject implements Report {
	/** 保單號碼 */
	private String policyNo = "";
	/** 赔案编号 */
	private String claimNo = "";
	/** 計算書號 */
	private String compensateNo = "";
	/** 批單號 */
	private String endorseNo = "";
	/** 被保險人 */
	private String insuredName = "";
	/** 保險金額 */
	private String sumAmount = "";
	/** 出險日期 */
	private String damageDate = "";
	/** 保險期間 */
	private String insurancePeriod = "";
	/** 追回金額 */
	private String replevyAmount = "";
	/** 其他費用 */
	private String othersFee = "";
	/** 律師费 */
	private String legalFee = "";
	/** 公證費 */
	private String notarialFee = "";
	/** 合计費用 */
	private String sumFee = "";
	/** 應支付費用金额 */
	private String paidFee = "";
	/**	賠案累計理賠金額 */
	private String handlerCode = "";
	private String handlerName = "";
	private String subHandlerCode = "";
	private String subHandlerName = "";
	private String context = "";
	/**	賠案累計理賠金額 */
	private String claimSumPaid;
	/**	賠案累計理賠費用 */
	private String claimSumFee;

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getDamageDate() {
		return damageDate;
	}

	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}

	public String getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}

	public String getReplevyAmount() {
		return replevyAmount;
	}

	public void setReplevyAmount(String replevyAmount) {
		this.replevyAmount = replevyAmount;
	}

	public String getOthersFee() {
		return othersFee;
	}

	public void setOthersFee(String othersFee) {
		this.othersFee = othersFee;
	}

	public String getLegalFee() {
		return legalFee;
	}

	public void setLegalFee(String legalFee) {
		this.legalFee = legalFee;
	}

	public String getNotarialFee() {
		return notarialFee;
	}

	public void setNotarialFee(String notarialFee) {
		this.notarialFee = notarialFee;
	}

	public String getSumFee() {
		return sumFee;
	}

	public void setSumFee(String sumFee) {
		this.sumFee = sumFee;
	}

	public String getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(String paidFee) {
		this.paidFee = paidFee;
	}

	@Override
	public String getHandlerCode() {
		return handlerCode;
	}

	@Override
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	@Override
	public String getHandlerName() {
		return handlerName;
	}

	@Override
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Override
	public String getSubHandlerCode() {
		return subHandlerCode;
	}

	@Override
	public void setSubHandlerCode(String subHandlerCode) {
		this.subHandlerCode = subHandlerCode;
	}

	@Override
	public String getSubHandlerName() {
		return subHandlerName;
	}

	@Override
	public void setSubHandlerName(String subHandlerName) {
		this.subHandlerName = subHandlerName;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public void setContext(String context) {
		this.context = context;
	}

	public String getClaimSumPaid() {
		return claimSumPaid;
	}

	public void setClaimSumPaid(String claimSumPaid) {
		this.claimSumPaid = claimSumPaid;
	}

	public String getClaimSumFee() {
		return claimSumFee;
	}

	public void setClaimSumFee(String claimSumFee) {
		this.claimSumFee = claimSumFee;
	}

}
