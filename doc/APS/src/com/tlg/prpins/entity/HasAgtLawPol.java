package com.tlg.prpins.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class HasAgtLawPol extends IBatisBaseEntity<BigDecimal> {

	private BigDecimal oid;
	private String batchNo; // 批次號碼
	private String proposalno; // 要保書編號
	private String policyno; // 保單號碼
	private String agentcode; // 經代/旅行社
	private String handleridentifynumber; // 電傳人
	private String riskcname; // 投保類別
	private String appliname; // 主要保人姓名
	private String birthday; // 主要保人生日
	private String applicode; // 要保人ID/統一編號
	private String startdate; // 保單起日
	private String starthour; // 保單起日時間
	private String enddate; // 保單迄日
	private String endhour; // 保單迄日時間
	private BigDecimal insuredtotaldays; // 投保天數
	private String totalinsuredno; // 被保險人數
	private BigDecimal sumpremium; // 總保險費
	private String travelpalce; // 目的地
	private String roomtelenumber; // 要保人電話
	private String roompostcode; // 要保人郵遞區號
	private String roomaddress; // 要保人地址
	private String underwriteenddate; // 要保日期(簽單日)
	private String riskcode; // 險種代號
	private String carLicenseno; // 車牌號碼
	private String peSpecies; // 寵物種類
	private String peIdentifynumber; // 晶片號碼
	private String paJobtitle; // 職稱類別

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
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}
	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
	}
	public String getRiskcname() {
		return riskcname;
	}
	public void setRiskcname(String riskcname) {
		this.riskcname = riskcname;
	}
	public String getAppliname() {
		return appliname;
	}
	public void setAppliname(String appliname) {
		this.appliname = appliname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getApplicode() {
		return applicode;
	}
	public void setApplicode(String applicode) {
		this.applicode = applicode;
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
	public String getEndhour() {
		return endhour;
	}
	public void setEndhour(String endhour) {
		this.endhour = endhour;
	}
	public BigDecimal getInsuredtotaldays() {
		return insuredtotaldays;
	}
	public void setInsuredtotaldays(BigDecimal insuredtotaldays) {
		this.insuredtotaldays = insuredtotaldays;
	}
	public String getTotalinsuredno() {
		return totalinsuredno;
	}
	public void setTotalinsuredno(String totalinsuredno) {
		this.totalinsuredno = totalinsuredno;
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
	public String getRoomtelenumber() {
		return roomtelenumber;
	}
	public void setRoomtelenumber(String roomtelenumber) {
		this.roomtelenumber = roomtelenumber;
	}
	public String getRoompostcode() {
		return roompostcode;
	}
	public void setRoompostcode(String roompostcode) {
		this.roompostcode = roompostcode;
	}
	public String getRoomaddress() {
		return roomaddress;
	}
	public void setRoomaddress(String roomaddress) {
		this.roomaddress = roomaddress;
	}
	public String getUnderwriteenddate() {
		return underwriteenddate;
	}
	public void setUnderwriteenddate(String underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public String getCarLicenseno() {
		return carLicenseno;
	}
	public void setCarLicenseno(String carLicenseno) {
		this.carLicenseno = carLicenseno;
	}
	public String getPeSpecies() {
		return peSpecies;
	}
	public void setPeSpecies(String peSpecies) {
		this.peSpecies = peSpecies;
	}
	public String getPeIdentifynumber() {
		return peIdentifynumber;
	}
	public void setPeIdentifynumber(String peIdentifynumber) {
		this.peIdentifynumber = peIdentifynumber;
	}
	public String getPaJobtitle() {
		return paJobtitle;
	}
	public void setPaJobtitle(String paJobtitle) {
		this.paJobtitle = paJobtitle;
	}
}
