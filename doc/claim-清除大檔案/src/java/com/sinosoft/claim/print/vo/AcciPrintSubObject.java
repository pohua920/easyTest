package com.sinosoft.claim.print.vo;

/***
 * 伤害险子报表打印类
 * @author 中科软
 */
public class AcciPrintSubObject {
	/** 賠付對象名稱 */
	private String ownerName;
	/** 賠付對象ID */
	private String uniformNo;
	/** 賠付人聯繫地址 */
	private String courierAddress;
	/** 賠付對象聯繫電話 */
	private String ownerPhoneNo;
	/** 单证清单序号 */
	private int serialNo = 0;
	/** 单证清单名称 */
	private String typeName;
	/** 險種代碼 */
	private String riskCode;
	/** 險種名称 */
	private String riskName;
	/** 骨折程度 */
	private String fractureDegree;
	/** 赔付金額 */
	private String sumRealPay;
	/** 險别代碼 */
	private String kindCode;
	/** 险别名称 */
	private String kindName;
	/** 险别保额 */
	private String amount;
	/** 賠付對象 */
	private String personName;
	/** 身分證字號 */
	private String ID;
	/** 银行名称 */
	private String customBankName;
	/** 总行代码 */
	private String bankCode;
	/** 分行代码 */
	private String customBankCode;
	/** 账号 */
	private String accountCode;
	/** 開票單位 */
	private String billingUnit;
	/** 賠款金額 */
	private String comsumRealPay;
	/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--start */
	private String amlFlagDesc;
	/** 赔付对象的序号 */
	private int no = 0;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getUniformNo() {
		return uniformNo;
	}

	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}

	public String getCourierAddress() {
		return courierAddress;
	}

	public void setCourierAddress(String courierAddress) {
		this.courierAddress = courierAddress;
	}

	public String getOwnerPhoneNo() {
		return ownerPhoneNo;
	}

	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getFractureDegree() {
		return fractureDegree;
	}

	public void setFractureDegree(String fractureDegree) {
		this.fractureDegree = fractureDegree;
	}

	public String getSumRealPay() {
		return sumRealPay;
	}

	public void setSumRealPay(String sumRealPay) {
		this.sumRealPay = sumRealPay;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCustomBankName() {
		return customBankName;
	}

	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCustomBankCode() {
		return customBankCode;
	}

	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getBillingUnit() {
		return billingUnit;
	}

	public void setBillingUnit(String billingUnit) {
		this.billingUnit = billingUnit;
	}

	public String getComsumRealPay() {
		return comsumRealPay;
	}

	public void setComsumRealPay(String comsumRealPay) {
		this.comsumRealPay = comsumRealPay;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--start */
	public String getAmlFlagDesc() {
		return amlFlagDesc;
	}

	public void setAmlFlagDesc(String amlFlagDesc) {
		this.amlFlagDesc = amlFlagDesc;
	}
	/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--end */
}
