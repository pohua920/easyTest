package com.sinosoft.claim.print.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * 理算书列印内容
 * @author 中科软
 */
public class ShipCompensateObject implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 計算書號碼 */
	private String compensateNo = "";
	/** 賠案號碼 */
	private String claimNo = "";
	/** 賠付次數 */
	private String times = "";
	/** 保單號碼 */
	private String policyNo = "";
	/** 批单号码 */
	private String endorseNo = "";
	/** 保单年度：承保端保單年度 */
	private String policyYear = "";
	/** 運輸方式 立案運輸 1-海運、2-空運、3-陸運、4-郵寄 */
	private String transportTypeStr = "";
	/** 被保險人 */
	private String insuredName = "";
	/** 保險條件 */
	private String policyCondition = "";
	/** 收費情形 已收，未收 */
	private String payStatus = "";
	/** 收費日期 */
	private String payDateStr = "";
	/** 貨物類別代號 */
	private String cargoType = "";
	/** 開航日期 */
	private String sailStartDateStr = "";
	/** 出險日期 */
	private String damageStartDateStr = "";
	/** 受理日期(備案日期) */
	private String reportDateStr = "";
	/** 結案日期 */
	private String endCaseDateStr = "";
	/** 出險原因 */
	private String damageName = "";
	/** 船名 */
	private String shipCName = "";
	/** 总保险金额 */
	private String sumAmount;
	/** 簽單保費 */
	private String sumPremium;
	/** 賠款金額 */
	private String sumPaid;
	/** 航程 始發 */
	private String startSite = "";
	/** 航程 終達 */
	private String endSite = "";
	/** 理算說明 */
	private String context = "";
	/** 結案號碼 */
	private String caseNo = "";
	/** 險種名稱 */
	private String riskCName = "";
	/** 被保險人 */
	private String appliName = "";
	/** 保險期間 */
	private String duration = "";
	/** 賠付幣別 */
	private String currency = "";
	/** 本次賠付 */
	private String sumThisPaid = "0";
	
	private List<CompensateKindInfoObject> kindInfoList = new ArrayList<CompensateKindInfoObject>();
	
	/** 賠付對象 */
	private List<CompensatePayInfoObject> payInfoList = new ArrayList<CompensatePayInfoObject>();
	/** 理算說明 */
	private List<CompensateContextObject> contextList = new ArrayList<CompensateContextObject>();
	
	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public String getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}

	public String getTransportTypeStr() {
		return transportTypeStr;
	}

	public void setTransportTypeStr(String transportTypeStr) {
		this.transportTypeStr = transportTypeStr;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPolicyCondition() {
		return policyCondition;
	}

	public void setPolicyCondition(String policyCondition) {
		this.policyCondition = policyCondition;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDateStr() {
		return payDateStr;
	}

	public void setPayDateStr(String payDateStr) {
		this.payDateStr = payDateStr;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getSailStartDateStr() {
		return sailStartDateStr;
	}

	public void setSailStartDateStr(String sailStartDateStr) {
		this.sailStartDateStr = sailStartDateStr;
	}

	public String getDamageStartDateStr() {
		return damageStartDateStr;
	}

	public void setDamageStartDateStr(String damageStartDateStr) {
		this.damageStartDateStr = damageStartDateStr;
	}

	public String getReportDateStr() {
		return reportDateStr;
	}

	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}

	public String getEndCaseDateStr() {
		return endCaseDateStr;
	}

	public void setEndCaseDateStr(String endCaseDateStr) {
		this.endCaseDateStr = endCaseDateStr;
	}

	public String getDamageName() {
		return damageName;
	}

	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getSumPremium() {
		return sumPremium;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}

	public String getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(String sumPaid) {
		this.sumPaid = sumPaid;
	}

	public String getStartSite() {
		return startSite;
	}

	public void setStartSite(String startSite) {
		this.startSite = startSite;
	}

	public String getEndSite() {
		return endSite;
	}

	public void setEndSite(String endSite) {
		this.endSite = endSite;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getRiskCName() {
		return riskCName;
	}

	public void setRiskCName(String riskCName) {
		this.riskCName = riskCName;
	}

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	public List<CompensatePayInfoObject> getPayInfoList() {
		return payInfoList;
	}

	public void setPayInfoList(List<CompensatePayInfoObject> payInfoList) {
		this.payInfoList = payInfoList;
	}

	public List<CompensateContextObject> getContextList() {
		return contextList;
	}

	public void setContextList(List<CompensateContextObject> contextList) {
		this.contextList = contextList;
	}

	public List<CompensateKindInfoObject> getKindInfoList() {
		return kindInfoList;
	}

	public void setKindInfoList(List<CompensateKindInfoObject> kindInfoList) {
		this.kindInfoList = kindInfoList;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSumThisPaid() {
		return sumThisPaid;
	}

	public void setSumThisPaid(String sumThisPaid) {
		this.sumThisPaid = sumThisPaid;
	}

}
