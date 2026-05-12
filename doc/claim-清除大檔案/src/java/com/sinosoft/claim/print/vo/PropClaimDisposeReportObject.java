package com.sinosoft.claim.print.vo;

/**
 * 理賠處理報告 數據對象
 * @author 中科軟
 *
 */
public class PropClaimDisposeReportObject {
	/**賠案號碼*/
	private String claimNo;
	/**備案號碼*/
	private String registNo;
	/**險別*/
	private String riskName;
	/**保額*/
	private String sumAmount;
	/**保險期間*/
	private String insurancePeriod;
	/**承保比例*/
	private String coins;
	/**被保險人名稱和地址*/
	private String insuredNameAndAddress;
	/**出險地點*/
	private String damageAddress;
	/**出險日期和時間*/
	private String damageDate;
	/**自 負 額*/
	private String indemnityDuty;
	/**索賠金：*/
	private String sumClaim;
	/**賠付金額*/
	private String sumDefLoss;
	/**結案金額*/
	private String sumPaid;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getInsurancePeriod() {
		return insurancePeriod;
	}
	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	public String getCoins() {
		return coins;
	}
	public void setCoins(String coins) {
		this.coins = coins;
	}
	public String getInsuredNameAndAddress() {
		return insuredNameAndAddress;
	}
	public void setInsuredNameAndAddress(String insuredNameAndAddress) {
		this.insuredNameAndAddress = insuredNameAndAddress;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}
	public String getIndemnityDuty() {
		return indemnityDuty;
	}
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}
	public String getSumClaim() {
		return sumClaim;
	}
	public void setSumClaim(String sumClaim) {
		this.sumClaim = sumClaim;
	}
	public String getSumDefLoss() {
		return sumDefLoss;
	}
	public void setSumDefLoss(String sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
	}
	public String getSumPaid() {
		return sumPaid;
	}
	public void setSumPaid(String sumPaid) {
		this.sumPaid = sumPaid;
	}
	
}
