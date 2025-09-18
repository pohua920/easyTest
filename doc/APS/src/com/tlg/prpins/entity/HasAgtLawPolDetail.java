package com.tlg.prpins.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class HasAgtLawPolDetail extends IBatisBaseEntity<BigDecimal> {

	private BigDecimal oid;
	private String batchNo; // 批次號碼
	private String proposalno; // 要保書編號
	private String policyno; // 保單號碼
	private String appliname; // 主要保人姓名
	private String insuredname; // 主被保人姓名
	private String identifynumber; // 被要保人ID
	private String birthday; // 主被保人生日
	private String insuredidentity; // 要/被保險人關係
	private String startdate; // 保單起日
	private String starthour; // 保單起日時間
	private String enddate; // 保單迄日
	private BigDecimal ta00ta0d; // 身故及殘廢保險金額
	private BigDecimal ta01ta0e; // 傷害醫療保險金額
	private BigDecimal tr45; // 海外突發疾病健康保險金額
	private BigDecimal insuredtotaldays; // 投保天數
	private BigDecimal sumpremium; // 總保險費
	private String travelpalce; // 目的地
	private String underwriteenddate; // 要保日期
	private String benefitInsuredname; // 受益人姓名
	private String benefitInsuredidentity; // 受益人與被保人關係
	private String totalinsuredno; // 被保險人數
	private String handleridentifynumber; // 電傳人
	private String channelcode; // 通路代號

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
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getAppliname() {
		return appliname;
	}
	public void setAppliname(String appliname) {
		this.appliname = appliname;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
	public String getIdentifynumber() {
		return identifynumber;
	}
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getInsuredidentity() {
		return insuredidentity;
	}
	public void setInsuredidentity(String insuredidentity) {
		this.insuredidentity = insuredidentity;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStarthour() {
		return starthour;
	}
	public void setStarthour(String starthour) {
		this.starthour = starthour;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public BigDecimal getTa00ta0d() {
		return ta00ta0d;
	}
	public void setTa00ta0d(BigDecimal ta00ta0d) {
		this.ta00ta0d = ta00ta0d;
	}
	public BigDecimal getTa01ta0e() {
		return ta01ta0e;
	}
	public void setTa01ta0e(BigDecimal ta01ta0e) {
		this.ta01ta0e = ta01ta0e;
	}
	public BigDecimal getTr45() {
		return tr45;
	}
	public void setTr45(BigDecimal tr45) {
		this.tr45 = tr45;
	}
	public BigDecimal getInsuredtotaldays() {
		return insuredtotaldays;
	}
	public void setInsuredtotaldays(BigDecimal insuredtotaldays) {
		this.insuredtotaldays = insuredtotaldays;
	}
	public BigDecimal getSumpremium() {
		return sumpremium;
	}
	public void setSumpremium(BigDecimal sumpremium) {
		this.sumpremium = sumpremium;
	}
	public String getTravelpalce() {
		return travelpalce;
	}
	public void setTravelpalce(String travelpalce) {
		this.travelpalce = travelpalce;
	}
	public String getUnderwriteenddate() {
		return underwriteenddate;
	}
	public void setUnderwriteenddate(String underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}
	public String getBenefitInsuredname() {
		return benefitInsuredname;
	}
	public void setBenefitInsuredname(String benefitInsuredname) {
		this.benefitInsuredname = benefitInsuredname;
	}
	public String getBenefitInsuredidentity() {
		return benefitInsuredidentity;
	}
	public void setBenefitInsuredidentity(String benefitInsuredidentity) {
		this.benefitInsuredidentity = benefitInsuredidentity;
	}
	public String getTotalinsuredno() {
		return totalinsuredno;
	}
	public void setTotalinsuredno(String totalinsuredno) {
		this.totalinsuredno = totalinsuredno;
	}
	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}
	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
	}
	public String getChannelcode() {
		return channelcode;
	}
	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
	}
}
