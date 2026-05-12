package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * POJO类prpdpaymentaccount
 */
@Entity
@Table(name = "PRPDPAYMENTACCOUNT")
public class PrpDpaymentAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性 银行帳号 */
	private String accountCode = "";

	/** 属性帳户币别 */
	private String accountCurrency = "";

	/** 属性帳户类型 */
	private String accountType = "";

	/** 属性总行代码 */
	private String bankCode = "";

	/** 属性总行名称 */
	private String bankName = "";

	/** 分行代码 */
	private String customBankCode = "";

	/** 分行名称 */
	private String customBankName = "";

	/** 郵遞地址 */
	private String courierAddress = "";

	/** 郵遞區號 */
	private String areaCode = "";

	/** 赔付对象 */
	private String compensateOwnerName = "";

	/** 統一編號 */
	private String uniformNo = "";

	/** 属性accountName */
	private String accountName = "";

	/** 属性customerCode */
	private String customerCode = "";

	/** 属性userCode */
	private String userCode = "";

	/** 属性vehicleComCode */
	private String vehicleComCode = "";

	/** 属性ownerType */
	private String ownerType = "";

	/** 属性帳户归属人姓名 */
	private String ownerName = "";

	/** 属性certificateType */
	private String certificateType = "";;

	/** 属性certificateCode */
	private String certificateCode = "";;

	/** 属性ownerPhoneNo */
	private String ownerPhoneNo = "";

	/** 属性operatorCode */
	private String operatorCode = "";

	/** 属性operatorComCode */
	private String operatorComCode = "";

	/** 属性operatorName */
	private String operatorName = "";

	/** 属性operateDate */
	private Date operateDate = new DateTime();

	/** 属性updateDate */
	private Date updateDate = new DateTime();

	/** 属性operateSys */
	private String operateSys = "";

	/** 属性usedOrNot */
	private String usedOrNot = "";

	/** 属性validStatus */
	private String validStatus = "";

	/** 属性描述 */
	private String remark = "";

	private String serialNo = "";

	private String registNo = "";

	/**
	 * 类prpdpaymentaccount的默认构造方法
	 */
	public PrpDpaymentAccount() {
	}

	/**
	 * 属性accountcode的getter方法
	 */
	@Id
	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性accountcode的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 属性accountcurrency的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性accountcurrency的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * 属性accounttype的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性accounttype的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性银行编码的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性银行编码的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性bankname的getter方法
	 */

	@Column(name = "BANKNAME")
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 属性bankname的setter方法
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 属性accountname的getter方法
	 */

	@Column(name = "ACCOUNTNAME")
	public String getAccountName() {
		return this.accountName;
	}

	/**
	 * 属性accountname的setter方法
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 属性customercode的getter方法
	 */

	@Column(name = "CUSTOMERCODE")
	public String getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * 属性customercode的setter方法
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 属性usercode的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性usercode的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 属性vehiclecomcode的getter方法
	 */

	@Column(name = "VEHICLECOMCODE")
	public String getVehicleComCode() {
		return this.vehicleComCode;
	}

	/**
	 * 属性vehiclecomcode的setter方法
	 */
	public void setVehicleComCode(String vehicleComCode) {
		this.vehicleComCode = vehicleComCode;
	}

	/**
	 * 属性ownertype的getter方法
	 */

	@Column(name = "OWNERTYPE")
	public String getOwnerType() {
		return this.ownerType;
	}

	/**
	 * 属性ownertype的setter方法
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * 属性ownername的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性ownername的setter方法
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 属性certificatetype的getter方法
	 */

	@Column(name = "CERTIFICATETYPE")
	public String getCertificateType() {
		return this.certificateType;
	}

	/**
	 * 属性certificatetype的setter方法
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * 属性certificatecode的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertificateCode() {
		return this.certificateCode;
	}

	/**
	 * 属性certificatecode的setter方法
	 */
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	/**
	 * 属性ownerphoneno的getter方法
	 */

	@Column(name = "OWNERPHONENO")
	public String getOwnerPhoneNo() {
		return this.ownerPhoneNo;
	}

	/**
	 * 属性ownerphoneno的setter方法
	 */
	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
	}

	/**
	 * 属性operatorcode的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性operatorcode的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性operatorcomcode的getter方法
	 */

	@Column(name = "OPERATORCOMCODE")
	public String getOperatorComCode() {
		return this.operatorComCode;
	}

	/**
	 * 属性operatorcomcode的setter方法
	 */
	public void setOperatorComCode(String operatorComCode) {
		this.operatorComCode = operatorComCode;
	}

	/**
	 * 属性operatorname的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性operatorname的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 属性operatedate的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性operatedate的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性updatedate的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性updatedate的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性operatesys的getter方法
	 */

	@Column(name = "OPERATESYS")
	public String getOperateSys() {
		return this.operateSys;
	}

	/**
	 * 属性operatesys的setter方法
	 */
	public void setOperateSys(String operateSys) {
		this.operateSys = operateSys;
	}

	/**
	 * 属性usedornot的getter方法
	 */

	@Column(name = "USEDORNOT")
	public String getUsedOrNot() {
		return this.usedOrNot;
	}

	/**
	 * 属性usedornot的setter方法
	 */
	public void setUsedOrNot(String usedOrNot) {
		this.usedOrNot = usedOrNot;
	}

	/**
	 * 属性validstatus的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性validstatus的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性描述的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性描述的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Transient
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "CUSTOMBANKCODE")
	public String getCustomBankCode() {
		return customBankCode;
	}

	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	@Column(name = "CUSTOMBANKNAME")
	public String getCustomBankName() {
		return customBankName;
	}

	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	@Column(name = "COURIERADDRESS")
	public String getCourierAddress() {
		return courierAddress;
	}

	public void setCourierAddress(String courierAddress) {
		this.courierAddress = courierAddress;
	}

	@Column(name = "AREACODE")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "COMPENSATEOWNERNAME")
	public String getCompensateOwnerName() {
		return compensateOwnerName;
	}

	public void setCompensateOwnerName(String compensateOwnerName) {
		this.compensateOwnerName = compensateOwnerName;
	}

	@Column(name = "UNIFORMNO")
	public String getUniformNo() {
		return uniformNo;
	}

	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}

}
