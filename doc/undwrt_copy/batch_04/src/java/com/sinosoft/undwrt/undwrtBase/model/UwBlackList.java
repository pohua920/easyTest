package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类UwBlackList
 */
@Entity
@Table(name = "UWBLACKLIST")
public class UwBlackList implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private UwBlackListId id;

	/** 属性黑名單級別 */
	private String blackListLevel;

	/** 属性關係人類型 */
	private String insuredType;

	/** 属性關係人代碼 */
	private String insuredCode;

	/** 属性關係人名稱 */
	private String insuredName;
	
	/** 關係人英文名字  */
	private String insuredEName;
	
	/** 關係人電子郵箱*/
	private String email;
	
	/** 關係人電話*/
	private String mobile;
	
	/** 地址英文名稱*/
	private String addressEName;
	
	/** 地址中文名稱*/
	private String addressCName;
	
	/** 關係人性別*/
	private String sex;
	
	/** 通訊地址*/
	private String linkAddress;
	
	/** 郵政編號*/
	private String postCode;
	
	/** 扣款銀行賬號1*/
	private String bankCode1;
	
	/** 扣款銀行賬號2*/
	private String bankCode2;
	
	/** 扣款銀行*/
	private String bank;
	
	/** 扣款賬號*/
	private String account;
	
	/** 扣款賬號*/
	private String insuredIdvNote;
	
	/** 扣款賬號*/
	private String riskCode;
	
	/** 属性證件類型 */
	private String identifyType;

	/** 属性證件號碼 */
	private String identifyNumber;

	/** 属性欺騙手段 */
	private String cheatMeans;

	/** 属性欺騙日期 */
	private Date cheatDate;

	/** 属性makecom */
	private String makecom;

	/** 属性操作人代碼 */
	private String operatorCode;

	/** 属性錄入日期 */
	private Date inputDate;

	/** 属性電話號碼 */
	private String phoneNumber;

	/** 属性備註 */
	private String remark;

	/** 属性標誌 */
	private String flag;

	/**
	 * 类UwBlackList的默认构造方法
	 */
	public UwBlackList() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "blackListType", column = @Column(name = "BLACKLISTTYPE")),
			@AttributeOverride(name = "blackListCode", column = @Column(name = "BLACKLISTCODE")) })
	public UwBlackListId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(UwBlackListId id) {
		this.id = id;
	}

	/**
	 * 属性黑名單級別的getter方法
	 */

	@Column(name = "BLACKLISTLEVEL")
	public String getBlackListLevel() {
		return this.blackListLevel;
	}

	/**
	 * 属性黑名單級別的setter方法
	 */
	public void setBlackListLevel(String blackListLevel) {
		this.blackListLevel = blackListLevel;
	}

	/**
	 * 属性關係人類型的getter方法
	 */

	@Column(name = "INSUREDTYPE")
	public String getInsuredType() {
		return this.insuredType;
	}

	/**
	 * 属性關係人類型的setter方法
	 */
	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	/**
	 * 属性關係人代碼的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性關係人代碼的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性關係人名稱的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性關係人名稱的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性證件類型的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性證件類型的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性證件號碼的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性證件號碼的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性欺騙手段的getter方法
	 */

	@Column(name = "CHEATMEANS")
	public String getCheatMeans() {
		return this.cheatMeans;
	}

	/**
	 * 属性欺騙手段的setter方法
	 */
	public void setCheatMeans(String cheatMeans) {
		this.cheatMeans = cheatMeans;
	}

	/**
	 * 属性欺騙日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHEATDATE")
	public Date getCheatDate() {
		return this.cheatDate;
	}

	/**
	 * 属性欺騙日期的setter方法
	 */
	public void setCheatDate(Date cheatDate) {
		this.cheatDate = cheatDate;
	}

	/**
	 * 属性makecom的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakecom() {
		return this.makecom;
	}

	/**
	 * 属性makecom的setter方法
	 */
	public void setMakecom(String makecom) {
		this.makecom = makecom;
	}

	/**
	 * 属性操作人代碼的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作人代碼的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性錄入日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性錄入日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性電話號碼的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性電話號碼的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性備註的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性備註的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性標誌的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性標誌的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/** 
	 * 属性關係人英文名字getter方法
	 */
	@Column(name = "INSUREDENAME")
	public String getInsuredEName() {
		return insuredEName;
	}
	
	/**
	 * 屬性關係人英文名字setter方法
	 */
	public void setInsuredEName(String insuredEName) {
		this.insuredEName = insuredEName;
	}
	
	/**
	 * 屬性 關係人電子郵箱getter方法
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	
     /**
      * 屬性關係人電子郵箱setter方法
      */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/** 
	 * 屬性關係人電話getter方法
	 */
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/** 
	 * 屬性地址英文名稱getter方法
	 */
	
	@Column(name = "ADDRESSENAME")
	public String getAddressEName() {
		return addressEName;
	}

	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}


	/** 
	 * 屬性地址中文名稱getter方法
	 */
	@Column(name = "ADDRESSCNAME")
	public String getAddressCName() {
		return addressCName;
	}

	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}
	
	/** 
	 * 屬性關係人性別getter方法
	 */
	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/** 
	 * 屬性通訊地址getter方法
	 */
	@Column(name = "LINKADDRESS")
	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	/** 
	 * 屬性郵政編號getter方法
	 */
	@Column(name = "POSTCODE")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	/** 
	 * 屬性扣款銀行賬號1getter方法
	 */
	@Column(name = "BANKCODE1")	
	public String getBankCode1() {
		return bankCode1;
	}

	public void setBankCode1(String bankCode1) {
		this.bankCode1 = bankCode1;
	}
	
	/** 
	 * 屬性扣款銀行賬號2getter方法
	 */
	@Column(name = "BANKCODE2")	
	public String getBankCode2() {
		return bankCode2;
	}

	public void setBankCode2(String bankCode2) {
		this.bankCode2 = bankCode2;
	}
    	
	/** 
	 * 屬性扣款銀行getter方法
	 */
	@Column(name = "BANK")	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	/** 
	 * 屬性扣款賬號getter方法
	 */
	@Column(name = "ACCOUNT")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	/** 
	 * 屬性管制原因getter方法
	 */
	@Column(name = "INSUREDIDVNOTE")
	public String getInsuredIdvNote() {
		return insuredIdvNote;
	}

	public void setInsuredIdvNote(String insuredIdvNote) {
		this.insuredIdvNote = insuredIdvNote;
	}
	
	/** 
	 * 屬性適應險類getter方法
	 */
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	
	
}
