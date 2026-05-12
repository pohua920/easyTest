package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpDcompany
 */
@Entity
@Table(name = "PRPDCOMPANY")
public class PrpDcompany implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性COMCODE */
	private String comCode;

	/** 属性机构代码表 */
	private PrpDcompany prpDcompany;

	/** 属性机构中文名称 */
	private String comCName;

	/** 属性机构英文名称 */
	private String comEName;

	/** 属性地址中文名称 */
	private String addressCName;

	/** 属性地址英文名称 */
	private String addressEName;

	/** 属性邮编 */
	private String postCode;

	/** 属性PHONENUMBER */
	private String phoneNumber;

	/** 属性传真 */
	private String taxNumber;
	/** 属性--** 上级公司代码 */
	private String uppercomcode;

	/** 属性FAXNUMBER */
	private String faxNumber;

	/** 属性保险人名称 */
	private String insurerName;

	/** 属性机构属性 */
	private String comAttribute;

	/** 属性机构类型 */
	private String comType;

	/** 属性机构级别 */
	private String comLevel;

	/** 属性经理 */
	private String manager;

	/** 属性财务主管 */
	private String accountLeader;

	/** 属性出纳 */
	private String cashier;

	/** 属性会计 */
	private String accountant;

	/** 属性摘要 */
	private String remark;

	/** 属性新的机构代码 */
	private String newComCode;

	/** 属性VALIDSTATUS */
	private String validStatus;

	/** 属性财务机构 */
	private String acntUnit;

	/** 属性ARTICLECODE */
	private String articleCode;

	/** 属性归属帐套 */
	private String accCode;

	/** 属性机构类型标志 */
	private String centerFlag;

	/** 属性对外机构代码 */
	private String outerPayCode;

	/** 属性对内机构代码 */
	private String innerPayCode;

	/** 属性备注 */
	private String flag;

	/** 属性WEBADDRESS */
	private String webAddress;

	/** 属性服务电话 */
	private String servicePhone;

	/** 属性报案电话 */
	private String reportPhone;

	/** 属性AGENTCODE */
	private String agentCode;

	/** 属性AGREEMENTNO */
	private String agreementNo;

	/** 属性SYSAREACODE */
	private String sysAreaCode;

	/** 属性COMBVISITRATE */
	private BigDecimal comBvIsItRate;

	/** 属性打印显示的机构名称 */
	private String printComName;

	/** 属性打印显示的地址 */
	private String printAddress;

	/** 属性打印显示的邮编 */
	private String pringPostCode;

	/** 属性上级理赔机构 */
	private String upperClaimComCode;

	/** 属性sap机构代码 */
	private String sapComCode;

	/** 属性成本中心 */
	private String costCenterCode;

	/** 属性团队渠道属性 */
	private String groupNature;

	/** 属性团队级别 */
	private String groupLevel;

	/** 属性SALESCHANNELCODE */
	private String salesChannelCode;

	/** 属性团队详细 */
	private String groupNatureDetail;

	/** 属性taxidenno */
	private String taxidenno;

	/** 属性prpDcompanies */
	private List<PrpDcompany> prpDcompanies = new ArrayList<PrpDcompany>(0);

	/**
	 * 类PrpDcompany的默认构造方法
	 */
	public PrpDcompany() {
	}

	/**
	 * 属性COMCODE的getter方法
	 */
	@Id
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
	 * 属性机构代码表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPPERCOMCODE", nullable = false)
	public PrpDcompany getPrpDcompany() {
		return this.prpDcompany;
	}

	/**
	 * 属性机构代码表的setter方法
	 */
	public void setPrpDcompany(PrpDcompany prpDcompany) {
		this.prpDcompany = prpDcompany;
	}

	/**
	 * 属性机构中文名称的getter方法
	 */

	@Column(name = "COMCNAME")
	public String getComCName() {
		return this.comCName;
	}

	/**
	 * 属性机构中文名称的setter方法
	 */
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	/**
	 * 属性机构英文名称的getter方法
	 */

	@Column(name = "COMENAME")
	public String getComEName() {
		return this.comEName;
	}

	/**
	 * 属性机构英文名称的setter方法
	 */
	public void setComEName(String comEName) {
		this.comEName = comEName;
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
	 * 属性ADDRESSENAME的setter方法
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
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
	 * 属性传真的getter方法
	 */

	@Column(name = "TAXNUMBER")
	public String getTaxNumber() {
		return this.taxNumber;
	}

	/**
	 * 属性传真的setter方法
	 */
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
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
	 * 属性保险人名称的getter方法
	 */

	/**
	 * 属性--** 上级公司代码的getter方法
	 */

	@Transient
	public String getUppercomcode() {
		return this.uppercomcode;
	}

	/**
	 * 属性--** 上级公司代码的setter方法
	 */
	public void setUppercomcode(String uppercomcode) {
		this.uppercomcode = uppercomcode;
	}

	@Column(name = "INSURERNAME")
	public String getInsurerName() {
		return this.insurerName;
	}

	/**
	 * 属性保险人名称的setter方法
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**
	 * 属性机构属性的getter方法
	 */

	@Column(name = "COMATTRIBUTE")
	public String getComAttribute() {
		return this.comAttribute;
	}

	/**
	 * 属性机构属性的setter方法
	 */
	public void setComAttribute(String comAttribute) {
		this.comAttribute = comAttribute;
	}

	/**
	 * 属性机构类型的getter方法
	 */

	@Column(name = "COMTYPE")
	public String getComType() {
		return this.comType;
	}

	/**
	 * 属性机构类型的setter方法
	 */
	public void setComType(String comType) {
		this.comType = comType;
	}

	/**
	 * 属性机构级别的getter方法
	 */

	@Column(name = "COMLEVEL")
	public String getComLevel() {
		return this.comLevel;
	}

	/**
	 * 属性机构级别的setter方法
	 */
	public void setComLevel(String comLevel) {
		this.comLevel = comLevel;
	}

	/**
	 * 属性经理的getter方法
	 */

	@Column(name = "MANAGER")
	public String getManager() {
		return this.manager;
	}

	/**
	 * 属性经理的setter方法
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 属性财务主管的getter方法
	 */

	@Column(name = "ACCOUNTLEADER")
	public String getAccountLeader() {
		return this.accountLeader;
	}

	/**
	 * 属性财务主管的setter方法
	 */
	public void setAccountLeader(String accountLeader) {
		this.accountLeader = accountLeader;
	}

	/**
	 * 属性出纳的getter方法
	 */

	@Column(name = "CASHIER")
	public String getCashier() {
		return this.cashier;
	}

	/**
	 * 属性出纳的setter方法
	 */
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	/**
	 * 属性会计的getter方法
	 */

	@Column(name = "ACCOUNTANT")
	public String getAccountant() {
		return this.accountant;
	}

	/**
	 * 属性会计的setter方法
	 */
	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	/**
	 * 属性摘要的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性摘要的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性新的机构代码的getter方法
	 */

	@Column(name = "NEWCOMCODE")
	public String getNewComCode() {
		return this.newComCode;
	}

	/**
	 * 属性新的机构代码的setter方法
	 */
	public void setNewComCode(String newComCode) {
		this.newComCode = newComCode;
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
	 * 属性财务机构的getter方法
	 */

	@Column(name = "ACNTUNIT")
	public String getAcntUnit() {
		return this.acntUnit;
	}

	/**
	 * 属性财务机构的setter方法
	 */
	public void setAcntUnit(String acntUnit) {
		this.acntUnit = acntUnit;
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
	 * 属性归属帐套的getter方法
	 */

	@Column(name = "ACCCODE")
	public String getAccCode() {
		return this.accCode;
	}

	/**
	 * 属性归属帐套的setter方法
	 */
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	/**
	 * 属性机构类型标志的getter方法
	 */

	@Column(name = "CENTERFLAG")
	public String getCenterFlag() {
		return this.centerFlag;
	}

	/**
	 * 属性机构类型标志的setter方法
	 */
	public void setCenterFlag(String centerFlag) {
		this.centerFlag = centerFlag;
	}

	/**
	 * 属性对外机构代码的getter方法
	 */

	@Column(name = "OUTERPAYCODE")
	public String getOuterPayCode() {
		return this.outerPayCode;
	}

	/**
	 * 属性对外机构代码的setter方法
	 */
	public void setOuterPayCode(String outerPayCode) {
		this.outerPayCode = outerPayCode;
	}

	/**
	 * 属性对内机构代码的getter方法
	 */

	@Column(name = "INNERPAYCODE")
	public String getInnerPayCode() {
		return this.innerPayCode;
	}

	/**
	 * 属性对内机构代码的setter方法
	 */
	public void setInnerPayCode(String innerPayCode) {
		this.innerPayCode = innerPayCode;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性WEBADDRESS的getter方法
	 */

	@Column(name = "WEBADDRESS")
	public String getWebAddress() {
		return this.webAddress;
	}

	/**
	 * 属性WEBADDRESS的setter方法
	 */
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	/**
	 * 属性服务电话的getter方法
	 */

	@Column(name = "SERVICEPHONE")
	public String getServicePhone() {
		return this.servicePhone;
	}

	/**
	 * 属性服务电话的setter方法
	 */
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	/**
	 * 属性报案电话的getter方法
	 */

	@Column(name = "REPORTPHONE")
	public String getReportPhone() {
		return this.reportPhone;
	}

	/**
	 * 属性报案电话的setter方法
	 */
	public void setReportPhone(String reportPhone) {
		this.reportPhone = reportPhone;
	}

	/**
	 * 属性AGENTCODE的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性AGENTCODE的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性AGREEMENTNO的getter方法
	 */

	@Column(name = "AGREEMENTNO")
	public String getAgreementNo() {
		return this.agreementNo;
	}

	/**
	 * 属性AGREEMENTNO的setter方法
	 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	/**
	 * 属性SYSAREACODE的getter方法
	 */

	@Column(name = "SYSAREACODE")
	public String getSysAreaCode() {
		return this.sysAreaCode;
	}

	/**
	 * 属性SYSAREACODE的setter方法
	 */
	public void setSysAreaCode(String sysAreaCode) {
		this.sysAreaCode = sysAreaCode;
	}

	/**
	 * 属性COMBVISITRATE的getter方法
	 */

	@Column(name = "COMBVISITRATE")
	public BigDecimal getComBvIsItRate() {
		return this.comBvIsItRate;
	}

	/**
	 * 属性COMBVISITRATE的setter方法
	 */
	public void setComBvIsItRate(BigDecimal comBvIsItRate) {
		this.comBvIsItRate = comBvIsItRate;
	}

	/**
	 * 属性打印显示的机构名称的getter方法
	 */

	@Column(name = "PRINTCOMNAME")
	public String getPrintComName() {
		return this.printComName;
	}

	/**
	 * 属性打印显示的机构名称的setter方法
	 */
	public void setPrintComName(String printComName) {
		this.printComName = printComName;
	}

	/**
	 * 属性打印显示的地址的getter方法
	 */

	@Column(name = "PRINTADDRESS")
	public String getPrintAddress() {
		return this.printAddress;
	}

	/**
	 * 属性打印显示的地址的setter方法
	 */
	public void setPrintAddress(String printAddress) {
		this.printAddress = printAddress;
	}

	/**
	 * 属性打印显示的邮编的getter方法
	 */

	@Column(name = "PRINGPOSTCODE")
	public String getPringPostCode() {
		return this.pringPostCode;
	}

	/**
	 * 属性打印显示的邮编的setter方法
	 */
	public void setPringPostCode(String pringPostCode) {
		this.pringPostCode = pringPostCode;
	}

	/**
	 * 属性上级理赔机构的getter方法
	 */

	@Column(name = "UPPERCLAIMCOMCODE")
	public String getUpperClaimComCode() {
		return this.upperClaimComCode;
	}

	/**
	 * 属性上级理赔机构的setter方法
	 */
	public void setUpperClaimComCode(String upperClaimComCode) {
		this.upperClaimComCode = upperClaimComCode;
	}

	/**
	 * 属性sap机构代码的getter方法
	 */

	@Column(name = "SAPCOMCODE")
	public String getSapComCode() {
		return this.sapComCode;
	}

	/**
	 * 属性sap机构代码的setter方法
	 */
	public void setSapComCode(String sapComCode) {
		this.sapComCode = sapComCode;
	}

	/**
	 * 属性成本中心的getter方法
	 */

	@Column(name = "COSTCENTERCODE")
	public String getCostCenterCode() {
		return this.costCenterCode;
	}

	/**
	 * 属性成本中心的setter方法
	 */
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	/**
	 * 属性团队渠道属性的getter方法
	 */

	@Column(name = "GROUPNATURE")
	public String getGroupNature() {
		return this.groupNature;
	}

	/**
	 * 属性团队渠道属性的setter方法
	 */
	public void setGroupNature(String groupNature) {
		this.groupNature = groupNature;
	}

	/**
	 * 属性团队级别的getter方法
	 */

	@Column(name = "GROUPLEVEL")
	public String getGroupLevel() {
		return this.groupLevel;
	}

	/**
	 * 属性团队级别的setter方法
	 */
	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	/**
	 * 属性SALESCHANNELCODE的getter方法
	 */

	@Column(name = "SALESCHANNELCODE")
	public String getSalesChannelCode() {
		return this.salesChannelCode;
	}

	/**
	 * 属性SALESCHANNELCODE的setter方法
	 */
	public void setSalesChannelCode(String salesChannelCode) {
		this.salesChannelCode = salesChannelCode;
	}

	/**
	 * 属性团队详细的getter方法
	 */

	@Column(name = "GROUPNATUREDETAIL")
	public String getGroupNatureDetail() {
		return this.groupNatureDetail;
	}

	/**
	 * 属性团队详细的setter方法
	 */
	public void setGroupNatureDetail(String groupNatureDetail) {
		this.groupNatureDetail = groupNatureDetail;
	}

	/**
	 * 属性taxidenno的getter方法
	 */

	@Column(name = "TAXIDENNO")
	public String getTaxidenno() {
		return this.taxidenno;
	}

	/**
	 * 属性taxidenno的setter方法
	 */
	public void setTaxidenno(String taxidenno) {
		this.taxidenno = taxidenno;
	}

	/**
	 * 属性prpDcompanies的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpDcompany")
	public List<PrpDcompany> getPrpDcompanies() {
		return this.prpDcompanies;
	}

	/**
	 * 属性prpDcompanies的setter方法
	 */
	public void setPrpDcompanies(List<PrpDcompany> prpDcompanies) {
		this.prpDcompanies = prpDcompanies;
	}

}
