package com.sinosoft.claim.schema.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.claim.common.ConstantCodes;

@Entity
@Table(name = "PRPLPAYOBJECTINFO")
public class PrpLpayObjectInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** CERTITYPE 赔款支付对象类型 */
	public static final String CERTITYPE_PAYOBJECT = "01";
	/** CERTITYPE 费用支付对象类型 */
	public static final String CERTITYPE_CHARGE = "02";
	/** OWNERSHIP 费用支付方式 汇款 */
	public static final String OWNERSHIP_B = "B";
	/** OWNERSHIP 费用支付方式 支票 */
	public static final String OWNERSHIP_Q = "Q";
	/** OWNERSHIP 费用支付方式 现金 */
	public static final String OWNERSHIP_C = "C";
	/** 属性id */
	private PrpLpayObjectInfoId id;

	/** 险种 */
	private String riskCode = "";

	/** 险别代码 */
	private String kindCode = "";

	/** 標的損失賠款支付方式 */
	private String ownerShip = "";

	/** 追償金額 或 理賠金額 */
	private Double payAmount = 0d;

	/** 赔付对象 */
	private String ownerName = "";

	/** 证件类型 */
	private String certificateCode = "";

	/** 統一編號 */
	private String uniformNo = "";

	/** 受款人電話 */
	private String beneficiaryPhone = "";

	/** 總行代號 */
	private String bankCode = "";

	/** 總行名稱 */
	private String bankName = "";

	/** 匯款帳號 */
	private String accountCode ="";

	/** 分行代码 */
	private String customBankCode = "";

	/** 分行名称 */
	private String customBankName = "";

	/** 郵遞地址 */
	private String courierAddress = "";

	/** 郵遞區號 */
	private String areaCode = "";

	/** 帳号归属人联系电话 */
	private String ownerPhoneNo = "";

	/** 帳户类型 */
	private String accountType = "";

	/** 帳户币别 */
	private String accountCurrency = ConstantCodes.LOCAL_CURRENCY;

	/** 业务与帳户关系 */
	private String ownerShipOld = "";

	/** 禁背 0否 1是 */
	private String cutBack = "";

	/** 賠付代號（賠案）1:壹般賠案，2：同業；3：健保局 */
	private String paycodeType = "";

	/** 費用類型 1：修車廠，2：材料商，3：公司行號，4：個人，5：公證公司，6：健保局 ,7:同业*/
	private String paymentKind = "";

	/** 行動電話 */
	private String mobilePhoneNo = "";

	/** 付款日期 */
	private Date payDate;
	
	/** 支付币种*/
	private String currency = ConstantCodes.LOCAL_CURRENCY;
	
	/** 追償負責人 */
	private String repLevyManager;
	
	private Double exchRate = 1d;
	
	/** 洗錢旗標 Y:已命中,N:未命中,M:手動*/
	private String amlFlag ;
	
	/** 洗錢檢測日期*/
	private Date amlDate ;
	
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 START
	private String inputStatus;
	private String inputLv;
	private String reviewLv;
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 END
	
	private List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();

	/**
	 * 类PrpLpayObjectInfo的默认构造方法
	 */
	public PrpLpayObjectInfo() {
		id = new PrpLpayObjectInfoId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")) })
	public PrpLpayObjectInfoId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpayObjectInfoId id) {
		this.id = id;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "OWNERSHIP")
	public String getOwnerShip() {
		return ownerShip;
	}

	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}

	@Column(name = "PAYAMOUNT")
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "CERTIFICATECODE")
	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	@Column(name = "UNIFORMNO")
	public String getUniformNo() {
		return uniformNo;
	}

	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}

	@Column(name = "BENEFICIARYPHONE")
	public String getBeneficiaryPhone() {
		return beneficiaryPhone;
	}

	public void setBeneficiaryPhone(String beneficiaryPhone) {
		this.beneficiaryPhone = beneficiaryPhone;
	}

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANKNAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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

	@Column(name = "OWNERPHONENO")
	public String getOwnerPhoneNo() {
		return ownerPhoneNo;
	}

	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
	}

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		if(accountCurrency==null||"".equals(accountCurrency)){
			accountCurrency = ConstantCodes.LOCAL_CURRENCY;
		}
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	@Column(name = "OWNERSHIPOLD")
	public String getOwnerShipOld() {
		return ownerShipOld;
	}

	public void setOwnerShipOld(String ownerShipOld) {
		this.ownerShipOld = ownerShipOld;
	}

	@Column(name = "CUTBACK")
	public String getCutBack() {
		return cutBack;
	}

	public void setCutBack(String cutBack) {
		this.cutBack = cutBack;
	}

	@Column(name = "PAYCODETYPE")
	public String getPaycodeType() {
		return paycodeType;
	}

	public void setPaycodeType(String paycodeType) {
		this.paycodeType = paycodeType;
	}

	@Column(name = "PAYMENTKIND")
	public String getPaymentKind() {
		return paymentKind;
	}

	public void setPaymentKind(String paymentKind) {
		this.paymentKind = paymentKind;
	}

	@Column(name = "MOBILEPHONENO")
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}

	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}

	@Column(name = "PAYDATE")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Transient
	public List<PrpLpayObjectInfo> getPrpLpayObjectInfoList() {
		return prpLpayObjectInfoList;
	}

	public void setPrpLpayObjectInfoList(List<PrpLpayObjectInfo> prpLpayObjectInfoList) {
		this.prpLpayObjectInfoList = prpLpayObjectInfoList;
	}
	@Column(name="CURRENCY")
	public String getCurrency() {
		if(currency==null||"".equals(currency)){
			currency = ConstantCodes.LOCAL_CURRENCY;
		}
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column(name = "repLevyManager")
	public String getRepLevyManager() {
		return repLevyManager;
	}

	public void setRepLevyManager(String repLevyManager) {
		this.repLevyManager = repLevyManager;
	}
	
	@Column(name = "EXCHRATE")
	public Double getExchRate() {
		if (this.exchRate == null) {
			return 1d;
		}
		return exchRate;
	}

	public void setExchRate(Double exchRate) {
		this.exchRate = exchRate;
	}

	@Column(name = "AMLFLAG")
	public String getAmlFlag() {
		return amlFlag;
	}

	public void setAmlFlag(String amlFlag) {
		this.amlFlag = amlFlag;
	}

	@Column(name = "AMLDATE")
	public Date getAmlDate() {
		return amlDate;
	}

	public void setAmlDate(Date amlDate) {
		this.amlDate = amlDate;
	}

	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 START
	@Transient
	public String getInputStatus() {
		return inputStatus;
	}

	public void setInputStatus(String inputStatus) {
		this.inputStatus = inputStatus;
	}
	
	@Transient
	public String getInputLv() {
		return inputLv;
	}

	public void setInputLv(String inputLv) {
		this.inputLv = inputLv;
	}
	
	@Transient
	public String getReviewLv() {
		return reviewLv;
	}

	public void setReviewLv(String reviewLv) {
		this.reviewLv = reviewLv;
	}
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 END
}
