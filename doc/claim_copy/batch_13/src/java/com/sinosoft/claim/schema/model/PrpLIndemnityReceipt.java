package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpLIndemnityReceipt
 */
@Entity
@Table(name = "PRPLINDEMNITYRECEIPT")
public class PrpLIndemnityReceipt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号码 */
	private String businessNo;

	/** 属性单证类型 */
	private String certiType;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性收款单位银行 */
	private String unitBank;

	/** 属性收款单位帐号 */
	private String unitAccount;

	/** 属性收款人银行帐号 */
	private String account;

	/** 属性收款人身份证号 */
	private String identifyNumber;

	/** 属性打印单证代码 */
	private String visaCode;

	/** 属性打印单证名称 */
	private String visaName;

	/** 属性打印单证流水号 */
	private String printNo;

	/**
	 * 类PrpLIndemnityReceipt的默认构造方法
	 */
	public PrpLIndemnityReceipt() {
	}

	/**
	 * 属性业务号码的getter方法
	 */
	@Id
	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号码的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性单证类型的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性单证类型的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性收款单位银行的getter方法
	 */

	@Column(name = "UNITBANK")
	public String getUnitBank() {
		return this.unitBank;
	}

	/**
	 * 属性收款单位银行的setter方法
	 */
	public void setUnitBank(String unitBank) {
		this.unitBank = unitBank;
	}

	/**
	 * 属性收款单位帐号的getter方法
	 */

	@Column(name = "UNITACCOUNT")
	public String getUnitAccount() {
		return this.unitAccount;
	}

	/**
	 * 属性收款单位帐号的setter方法
	 */
	public void setUnitAccount(String unitAccount) {
		this.unitAccount = unitAccount;
	}

	/**
	 * 属性收款人银行帐号的getter方法
	 */

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	/**
	 * 属性收款人银行帐号的setter方法
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 属性收款人身份证号的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性收款人身份证号的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性打印单证代码的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性打印单证代码的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性打印单证名称的getter方法
	 */

	@Column(name = "VISANAME")
	public String getVisaName() {
		return this.visaName;
	}

	/**
	 * 属性打印单证名称的setter方法
	 */
	public void setVisaName(String visaName) {
		this.visaName = visaName;
	}

	/**
	 * 属性打印单证流水号的getter方法
	 */

	@Column(name = "PRINTNO")
	public String getPrintNo() {
		return this.printNo;
	}

	/**
	 * 属性打印单证流水号的setter方法
	 */
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

}
