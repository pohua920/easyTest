package com.sinosoft.claim.schema.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PrpLclaimCredit")
public class PrpLclaimCredit implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private PrpLclaimCreditId id;
	/** 保单号码 */
	private String policyNo;
	/** 险种 */
	private String riskCode;
	/** 发卡银行 */
	private String bankName;
	/** 卡类型 */
	private String cardType;
	/** 卡其他类型 */
	private String cardOtherType;
	/** 卡名称 */
	private String cardName;
	/** 卡号码 */
	private String cardNo;
	/** 卡到期年 */
	private String validDateYear;
	/** 卡到期月 */
	private String validDateMonth;
	/** 持卡人姓名 */
	private String holderName;
	/** 持卡人身份证号码 */
	private String holderIdentifyNumber;
	/** 持卡人电话 */
	private String holderTel;
	/** 持卡人手机 */
	private String holderPhone;
	/** 持卡人与被保险人关系 */
	private String holderRelationShip;
	/** 持卡人地址 */
	private String holderAddress;
	/** 信用卡币别*/
	private String currency;
	/** 使用区域 */
	private String useArea;
	/** 备注 */
	private String remark;
	/** 标志位 */
	private String flag;
	/** 卡別代號 */
	private String cardCode ; 
	/** 發卡銀行識別碼 */
	private String bankCode ;
	
	public PrpLclaimCredit() {
		super();
		id = new  PrpLclaimCreditId();
	}
	
	public PrpLclaimCredit(PrpLclaimCreditId id, String policyNo, String riskCode, String bankName, String cardType, String cardOtherType, String cardName, String cardNo, String validDateYear, String validDateMonth, String holderName,
			String holderIdentifyNumber, String holderTel, String holderPhone, String holderRelationShip, String holderAddress, String currency, String useArea, String remark, String flag) {
		super();
		this.id = id;
		this.policyNo = policyNo;
		this.riskCode = riskCode;
		this.bankName = bankName;
		this.cardType = cardType;
		this.cardOtherType = cardOtherType;
		this.cardName = cardName;
		this.cardNo = cardNo;
		this.validDateYear = validDateYear;
		this.validDateMonth = validDateMonth;
		this.holderName = holderName;
		this.holderIdentifyNumber = holderIdentifyNumber;
		this.holderTel = holderTel;
		this.holderPhone = holderPhone;
		this.holderRelationShip = holderRelationShip;
		this.holderAddress = holderAddress;
		this.currency = currency;
		this.useArea = useArea;
		this.remark = remark;
		this.flag = flag;
	}

	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "businessNo", column = @Column(name = "businessNo")), @AttributeOverride(name = "nodeType", column = @Column(name = "nodeType")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialNo")) })
	public PrpLclaimCreditId getId() {
		return id;
	}

	public void setId(PrpLclaimCreditId id) {
		this.id = id;
	}

	@Column(name="policyNo")
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	@Column(name="riskCode")
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name="bankName")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name="cardType")
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(name="CardOtherType")
	public String getCardOtherType() {
		return cardOtherType;
	}
	public void setCardOtherType(String cardOtherType) {
		this.cardOtherType = cardOtherType;
	}
	@Column(name="CardName")
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	@Column(name="CardNo")
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Column(name="validDateYear")
	public String getValidDateYear() {
		return validDateYear;
	}
	public void setValidDateYear(String validDateYear) {
		this.validDateYear = validDateYear;
	}
	@Column(name="validDateMonth")
	public String getValidDateMonth() {
		return validDateMonth;
	}
	public void setValidDateMonth(String validDateMonth) {
		this.validDateMonth = validDateMonth;
	}
	@Column(name="holderName")
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	@Column(name="holderidentifyNumber")
	public String getHolderIdentifyNumber() {
		return holderIdentifyNumber;
	}
	public void setHolderIdentifyNumber(String holderIdentifyNumber) {
		this.holderIdentifyNumber = holderIdentifyNumber;
	}
	@Column(name="holderTel")
	public String getHolderTel() {
		return holderTel;
	}
	public void setHolderTel(String holderTel) {
		this.holderTel = holderTel;
	}
	@Column(name="holderPhone")
	public String getHolderPhone() {
		return holderPhone;
	}
	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}
	@Column(name="holderRelationShip")
	public String getHolderRelationShip() {
		return holderRelationShip;
	}
	public void setHolderRelationShip(String holderRelationShip) {
		this.holderRelationShip = holderRelationShip;
	}
	@Column(name="holderAddress")
	public String getHolderAddress() {
		return holderAddress;
	}
	public void setHolderAddress(String holderAddress) {
		this.holderAddress = holderAddress;
	}
	@Column(name="currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column(name="useArea")
	public String getUseArea() {
		return useArea;
	}
	public void setUseArea(String useArea) {
		this.useArea = useArea;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="cardCode")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	
	@Column(name="bankCode")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
