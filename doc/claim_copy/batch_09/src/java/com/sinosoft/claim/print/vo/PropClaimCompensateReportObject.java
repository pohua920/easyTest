package com.sinosoft.claim.print.vo;

/**
 * 火險賠款計算書 數據對象
 * @author 中科軟
 *
 */
public class PropClaimCompensateReportObject {
	/** 險種*/
	private String riskName;
	/**保單號碼*/
	private String policyNo;
	/**被保險人*/
	private String insuredName;
	/**賠案號碼*/
	private String claimNo;
	/**標的物地址*/
	private String itemKindAddress;
	/**理算書號*/
	private String compensateNo;
	/**保險標的*/
	private String itemKind;
	/**批單號碼*/
	private String endorseNo;
	/**保險金額*/
	private String sumAmount;
	/**出險地點*/
	private String damageAddress;
	/**出險日期*/
	private String damageDate;
	/**保險期間*/
	private String insurancePeriod;
	/**賠款計算方式*/
	private String context;
	/**賠款金額合計（大寫）*/
	private String upperCaseSumPaid;
	/**標的賠款*/
	private String sumLoss;
	/**公證費*/
	private String assessmentFee;
	/**預付賠款*/
	private String sumPrePaid;
	/**訴訟費*/
	private String legalCost;
	/**處理費用*/
	private String checkFee;
	/**代墊費用*/
	private String reimbursedExpense;
	/**律師費*/
	private String lawyerFee;
	/**其它*/
	private String othersFee;
	/**調查費*/
	private String investigationCharge;
	/**合計*/
	private String sumPaid;
	/**施救費*/
	private String rescueFee;
	/**代付賠款金額*/
	private String myReimbursedExpense;
	/** 币别 */
	private String currency;
	/** 币别名称  */
	private String currencyName;
	
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getItemKindAddress() {
		return itemKindAddress;
	}
	public void setItemKindAddress(String itemKindAddress) {
		this.itemKindAddress = itemKindAddress;
	}
	public String getCompensateNo() {
		return compensateNo;
	}
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}
	public String getItemKind() {
		return itemKind;
	}
	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
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
	public String getInsurancePeriod() {
		return insurancePeriod;
	}
	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUpperCaseSumPaid() {
		return upperCaseSumPaid;
	}
	public void setUpperCaseSumPaid(String upperCaseSumPaid) {
		this.upperCaseSumPaid = upperCaseSumPaid;
	}
	public String getSumLoss() {
		return sumLoss;
	}
	public void setSumLoss(String sumLoss) {
		this.sumLoss = sumLoss;
	}
	public String getAssessmentFee() {
		return assessmentFee;
	}
	public void setAssessmentFee(String assessmentFee) {
		this.assessmentFee = assessmentFee;
	}
	public String getSumPrePaid() {
		return sumPrePaid;
	}
	public void setSumPrePaid(String sumPrePaid) {
		this.sumPrePaid = sumPrePaid;
	}
	public String getLegalCost() {
		return legalCost;
	}
	public void setLegalCost(String legalCost) {
		this.legalCost = legalCost;
	}
	public String getCheckFee() {
		return checkFee;
	}
	public void setCheckFee(String checkFee) {
		this.checkFee = checkFee;
	}
	public String getReimbursedExpense() {
		return reimbursedExpense;
	}
	public void setReimbursedExpense(String reimbursedExpense) {
		this.reimbursedExpense = reimbursedExpense;
	}
	public String getLawyerFee() {
		return lawyerFee;
	}
	public void setLawyerFee(String lawyerFee) {
		this.lawyerFee = lawyerFee;
	}
	public String getOthersFee() {
		return othersFee;
	}
	public void setOthersFee(String othersFee) {
		this.othersFee = othersFee;
	}
	public String getInvestigationCharge() {
		return investigationCharge;
	}
	public void setInvestigationCharge(String investigationCharge) {
		this.investigationCharge = investigationCharge;
	}
	public String getSumPaid() {
		return sumPaid;
	}
	public void setSumPaid(String sumPaid) {
		this.sumPaid = sumPaid;
	}
	public String getRescueFee() {
		return rescueFee;
	}
	public void setRescueFee(String rescueFee) {
		this.rescueFee = rescueFee;
	}
	public String getMyReimbursedExpense() {
		return myReimbursedExpense;
	}
	public void setMyReimbursedExpense(String myReimbursedExpense) {
		this.myReimbursedExpense = myReimbursedExpense;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

}
