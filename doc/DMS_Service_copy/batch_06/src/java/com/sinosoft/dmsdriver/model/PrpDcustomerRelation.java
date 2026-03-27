package com.sinosoft.dmsdriver.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpDcustomerRelation
 */
@Entity
@Table(name = "PRPDCUSTOMER_RELATION")
public class PrpDcustomerRelation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性CUSTOMERCODE */
	private String customerCode;

	/** 属性PASSWORD */
	private String password;

	/** 属性SHORTHANDCODE */
	private String shortHandCode;

	/** 属性CUSTOMERCNAME */
	private String customerCName;

	/** 属性CUSTOMERENAME */
	private String customerEName;

	/** 属性ADDRESSCNAME */
	private String addressCName;

	/** 属性ADDRESSENAME */
	private String addressEName;

	/** 属性POSSESSNATURE */
	private String possessNature;

	/** 属性BUSINESSSOURCE */
	private String businessSource;

	/** 属性BUSINESSSORT */
	private String businessSort;

	/** 属性CUSTOMERKIND */
	private String customerKind;

	/** 属性CUSTOMERFLAG */
	private String customerFlag;

	/** 属性ORGANIZECODE */
	private String organizeCode;

	/** 属性CREDITLEVEL */
	private String creditLevel;

	/** 属性LEADERNAME */
	private String leaderName;

	/** 属性PHONENUMBER */
	private String phoneNumber;

	/** 属性FAXNUMBER */
	private String faxNumber;

	/** 属性MOBILE */
	private String mobile;

	/** 属性NETADDRESS */
	private String netAddress;

	/** 属性EMAILADDRESS */
	private String emailAddress;

	/** 属性POSTADDRESS */
	private String postAddress;

	/** 属性POSTCODE */
	private String postCode;

	/** 属性LINKERNAME */
	private String linkerName;

	/** 属性BANK */
	private String bank;

	/** 属性ACCOUNT */
	private String account;

	/** 属性INDUSTRYCODE */
	private String industryCode;

	/** 属性ECONOMYCODE */
	private String economyCode;

	/** 属性MEASURECODE */
	private String measureCode;

	/** 属性FATHERCODE */
	private String fatherCode;

	/** 属性SPONSORNAME */
	private String sponsorName;

	/** 属性BUSINESSRANGE */
	private String businessRange;

	/** 属性REGISTFUND */
	private BigDecimal registFund;

	/** 属性REGIONCODE */
	private String regionCode;

	/** 属性BLACKSTATE */
	private String blackState;

	/** 属性NEWCUSTOMERCODE */
	private String newCustomerCode;

	/** 属性VALIDSTATUS */
	private String validStatus;

	/** 属性ARTICLECODE */
	private String articleCode;

	/** 属性FLAG */
	private String flag;

	/** 属性CUSTOMERSHORTNAME */
	private String customerShortName;

	/** 属性EMPLOYSUM */
	private String employSum;

	/** 属性SHAREHOLDERFLAG */
	private String shareHolderFlag;

	/** 属性REVENUECODE */
	private String revenueCode;

	/** 属性WORDRISKRANK */
	private String wordRiskRank;

	/** 属性LOWERVIEWFLAG */
	private String lowerViewFlag;

	/** 属性HANDLERCODE */
	private String handlerCode;

	/** 属性OPERATORCODE */
	private String operatorCode;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性UPDATERCODE */
	private String updaterCode;

	/** 属性UPDATEDATE */
	private Date updateDate;

	/** 属性COMCODE */
	private String comCode;

	/** 属性TOPLEVELFLAG */
	private String topLevelFlag;

	/** 属性CAREERRISKGRADE */
	private String careerRiskGrade;

	/**
	 * 类PrpDcustomerRelation的默认构造方法
	 */
	public PrpDcustomerRelation() {
	}

	/**
	 * 属性CUSTOMERCODE的getter方法
	 */
	@Id
	@Column(name = "CUSTOMERCODE")
	public String getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * 属性CUSTOMERCODE的setter方法
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 属性PASSWORD的getter方法
	 */

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	/**
	 * 属性PASSWORD的setter方法
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 属性SHORTHANDCODE的getter方法
	 */

	@Column(name = "SHORTHANDCODE")
	public String getShortHandCode() {
		return this.shortHandCode;
	}

	/**
	 * 属性SHORTHANDCODE的setter方法
	 */
	public void setShortHandCode(String shortHandCode) {
		this.shortHandCode = shortHandCode;
	}

	/**
	 * 属性CUSTOMERCNAME的getter方法
	 */

	@Column(name = "CUSTOMERCNAME")
	public String getCustomerCName() {
		return this.customerCName;
	}

	/**
	 * 属性CUSTOMERCNAME的setter方法
	 */
	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	/**
	 * 属性CUSTOMERENAME的getter方法
	 */

	@Column(name = "CUSTOMERENAME")
	public String getCustomerEName() {
		return this.customerEName;
	}

	/**
	 * 属性CUSTOMERENAME的setter方法
	 */
	public void setCustomerEName(String customerEName) {
		this.customerEName = customerEName;
	}

	/**
	 * 属性ADDRESSCNAME的getter方法
	 */

	@Column(name = "ADDRESSCNAME")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**
	 * 属性ADDRESSCNAME的setter方法
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**
	 * 属性ADDRESSENAME的getter方法
	 */

	@Column(name = "ADDRESSENAME")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**
	 * 属性addressEName的setter方法
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**
	 * 属性POSSESSNATURE的getter方法
	 */

	@Column(name = "POSSESSNATURE")
	public String getPossessNature() {
		return this.possessNature;
	}

	/**
	 * 属性POSSESSNATURE的setter方法
	 */
	public void setPossessNature(String possessNature) {
		this.possessNature = possessNature;
	}

	/**
	 * 属性BUSINESSSOURCE的getter方法
	 */

	@Column(name = "BUSINESSSOURCE")
	public String getBusinessSource() {
		return this.businessSource;
	}

	/**
	 * 属性BUSINESSSOURCE的setter方法
	 */
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	/**
	 * 属性BUSINESSSORT的getter方法
	 */

	@Column(name = "BUSINESSSORT")
	public String getBusinessSort() {
		return this.businessSort;
	}

	/**
	 * 属性BUSINESSSORT的setter方法
	 */
	public void setBusinessSort(String businessSort) {
		this.businessSort = businessSort;
	}

	/**
	 * 属性CUSTOMERKIND的getter方法
	 */

	@Column(name = "CUSTOMERKIND")
	public String getCustomerKind() {
		return this.customerKind;
	}

	/**
	 * 属性CUSTOMERKIND的setter方法
	 */
	public void setCustomerKind(String customerKind) {
		this.customerKind = customerKind;
	}

	/**
	 * 属性CUSTOMERFLAG的getter方法
	 */

	@Column(name = "CUSTOMERFLAG")
	public String getCustomerFlag() {
		return this.customerFlag;
	}

	/**
	 * 属性CUSTOMERFLAG的setter方法
	 */
	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}

	/**
	 * 属性ORGANIZECODE的getter方法
	 */

	@Column(name = "ORGANIZECODE")
	public String getOrganizeCode() {
		return this.organizeCode;
	}

	/**
	 * 属性ORGANIZECODE的setter方法
	 */
	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
	}

	/**
	 * 属性CREDITLEVEL的getter方法
	 */

	@Column(name = "CREDITLEVEL")
	public String getCreditLevel() {
		return this.creditLevel;
	}

	/**
	 * 属性CREDITLEVEL的setter方法
	 */
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	/**
	 * 属性LEADERNAME的getter方法
	 */

	@Column(name = "LEADERNAME")
	public String getLeaderName() {
		return this.leaderName;
	}

	/**
	 * 属性LEADERNAME的setter方法
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * 属性PHONENUMBER的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性PHONENUMBER的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性FAXNUMBER的getter方法
	 */

	@Column(name = "FAXNUMBER")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**
	 * 属性FAXNUMBER的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * 属性MOBILE的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性MOBILE的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性NETADDRESS的getter方法
	 */

	@Column(name = "NETADDRESS")
	public String getNetAddress() {
		return this.netAddress;
	}

	/**
	 * 属性NETADDRESS的setter方法
	 */
	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}

	/**
	 * 属性EMAILADDRESS的getter方法
	 */

	@Column(name = "EMAILADDRESS")
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * 属性EMAILADDRESS的setter方法
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * 属性POSTADDRESS的getter方法
	 */

	@Column(name = "POSTADDRESS")
	public String getPostAddress() {
		return this.postAddress;
	}

	/**
	 * 属性POSTADDRESS的setter方法
	 */
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	/**
	 * 属性POSTCODE的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 属性POSTCODE的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 属性LINKERNAME的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性LINKERNAME的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**
	 * 属性BANK的getter方法
	 */

	@Column(name = "BANK")
	public String getBank() {
		return this.bank;
	}

	/**
	 * 属性BANK的setter方法
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 属性ACCOUNT的getter方法
	 */

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	/**
	 * 属性ACCOUNT的setter方法
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 属性INDUSTRYCODE的getter方法
	 */

	@Column(name = "INDUSTRYCODE")
	public String getIndustryCode() {
		return this.industryCode;
	}

	/**
	 * 属性INDUSTRYCODE的setter方法
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * 属性ECONOMYCODE的getter方法
	 */

	@Column(name = "ECONOMYCODE")
	public String getEconomyCode() {
		return this.economyCode;
	}

	/**
	 * 属性ECONOMYCODE的setter方法
	 */
	public void setEconomyCode(String economyCode) {
		this.economyCode = economyCode;
	}

	/**
	 * 属性MEASURECODE的getter方法
	 */

	@Column(name = "MEASURECODE")
	public String getMeasureCode() {
		return this.measureCode;
	}

	/**
	 * 属性MEASURECODE的setter方法
	 */
	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	/**
	 * 属性FATHERCODE的getter方法
	 */

	@Column(name = "FATHERCODE")
	public String getFatherCode() {
		return this.fatherCode;
	}

	/**
	 * 属性FATHERCODE的setter方法
	 */
	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

	/**
	 * 属性SPONSORNAME的getter方法
	 */

	@Column(name = "SPONSORNAME")
	public String getSponsorName() {
		return this.sponsorName;
	}

	/**
	 * 属性SPONSORNAME的setter方法
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * 属性BUSINESSRANGE的getter方法
	 */

	@Column(name = "BUSINESSRANGE")
	public String getBusinessRange() {
		return this.businessRange;
	}

	/**
	 * 属性BUSINESSRANGE的setter方法
	 */
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	/**
	 * 属性REGISTFUND的getter方法
	 */

	@Column(name = "REGISTFUND")
	public BigDecimal getRegistFund() {
		return this.registFund;
	}

	/**
	 * 属性REGISTFUND的setter方法
	 */
	public void setRegistFund(BigDecimal registFund) {
		this.registFund = registFund;
	}

	/**
	 * 属性REGIONCODE的getter方法
	 */

	@Column(name = "REGIONCODE")
	public String getRegionCode() {
		return this.regionCode;
	}

	/**
	 * 属性REGIONCODE的setter方法
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 属性BLACKSTATE的getter方法
	 */

	@Column(name = "BLACKSTATE")
	public String getBlackState() {
		return this.blackState;
	}

	/**
	 * 属性BLACKSTATE的setter方法
	 */
	public void setBlackState(String blackState) {
		this.blackState = blackState;
	}

	/**
	 * 属性NEWCUSTOMERCODE的getter方法
	 */

	@Column(name = "NEWCUSTOMERCODE")
	public String getNewCustomerCode() {
		return this.newCustomerCode;
	}

	/**
	 * 属性NEWCUSTOMERCODE的setter方法
	 */
	public void setNewCustomerCode(String newCustomerCode) {
		this.newCustomerCode = newCustomerCode;
	}

	/**
	 * 属性VALIDSTATUS的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性VALIDSTATUS的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性ARTICLECODE的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性ARTICLECODE的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性CUSTOMERSHORTNAME的getter方法
	 */

	@Column(name = "CUSTOMERSHORTNAME")
	public String getCustomerShortName() {
		return this.customerShortName;
	}

	/**
	 * 属性CUSTOMERSHORTNAME的setter方法
	 */
	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	/**
	 * 属性EMPLOYSUM的getter方法
	 */

	@Column(name = "EMPLOYSUM")
	public String getEmploySum() {
		return this.employSum;
	}

	/**
	 * 属性EMPLOYSUM的setter方法
	 */
	public void setEmploySum(String employSum) {
		this.employSum = employSum;
	}

	/**
	 * 属性SHAREHOLDERFLAG的getter方法
	 */

	@Column(name = "SHAREHOLDERFLAG")
	public String getShareHolderFlag() {
		return this.shareHolderFlag;
	}

	/**
	 * 属性SHAREHOLDERFLAG的setter方法
	 */
	public void setShareHolderFlag(String shareHolderFlag) {
		this.shareHolderFlag = shareHolderFlag;
	}

	/**
	 * 属性REVENUECODE的getter方法
	 */

	@Column(name = "REVENUECODE")
	public String getRevenueCode() {
		return this.revenueCode;
	}

	/**
	 * 属性REVENUECODE的setter方法
	 */
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}

	/**
	 * 属性WORDRISKRANK的getter方法
	 */

	@Column(name = "WORDRISKRANK")
	public String getWordRiskRank() {
		return this.wordRiskRank;
	}

	/**
	 * 属性WORDRISKRANK的setter方法
	 */
	public void setWordRiskRank(String wordRiskRank) {
		this.wordRiskRank = wordRiskRank;
	}

	/**
	 * 属性LOWERVIEWFLAG的getter方法
	 */

	@Column(name = "LOWERVIEWFLAG")
	public String getLowerViewFlag() {
		return this.lowerViewFlag;
	}

	/**
	 * 属性LOWERVIEWFLAG的setter方法
	 */
	public void setLowerViewFlag(String lowerViewFlag) {
		this.lowerViewFlag = lowerViewFlag;
	}

	/**
	 * 属性HANDLERCODE的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性HANDLERCODE的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性OPERATORCODE的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性OPERATORCODE的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性UPDATERCODE的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性UPDATERCODE的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性UPDATEDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性UPDATEDATE的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性COMCODE的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性COMCODE的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性TOPLEVELFLAG的getter方法
	 */

	@Column(name = "TOPLEVELFLAG")
	public String getTopLevelFlag() {
		return this.topLevelFlag;
	}

	/**
	 * 属性TOPLEVELFLAG的setter方法
	 */
	public void setTopLevelFlag(String topLevelFlag) {
		this.topLevelFlag = topLevelFlag;
	}

	/**
	 * 属性CAREERRISKGRADE的getter方法
	 */

	@Column(name = "CAREERRISKGRADE")
	public String getCareerRiskGrade() {
		return this.careerRiskGrade;
	}

	/**
	 * 属性CAREERRISKGRADE的setter方法
	 */
	public void setCareerRiskGrade(String careerRiskGrade) {
		this.careerRiskGrade = careerRiskGrade;
	}

}
