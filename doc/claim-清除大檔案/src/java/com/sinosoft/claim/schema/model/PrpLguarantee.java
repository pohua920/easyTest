package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLguarantee
 */
@Entity
@Table(name = "PRPLGUARANTEE")
public class PrpLguarantee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private Integer serialNo;

	/** 属性报案号码 */
	private String registNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性担保种类代码 */
	private String guaranteeTypeCode;

	/** 属性担保种类名称 */
	private String guaranteeTypeName;

	/** 属性担保出具时间 */
	private Date offerTime;

	/** 属性担保出具份数 */
	private Integer offerCount;

	/** 属性担保金额 */
	private BigDecimal sumGuarantee;

	/** 属性调查费用币别 */
	private String currency;

	/** 属性担保币别名称 */
	private String currencyName;

	/** 属性担保起始日期 */
	private Date unvalidStartDate;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性操作者名称 */
	private String operatorName;

	/** 属性担保截止日期 */
	private Date unValidendDate;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性担保申请原因 */
	private String applyPerson;

	/** 属性审核人代码 */
	private String undwrtPersonCode;

	/** 属性审核人名 */
	private String undwrtPersonName;

	/** 属性审核日期 */
	private Date undwrtTime;

	/** 属性担保是否回收 */
	private String recycleFlag;

	/** 属性担保状态 */
	private String guaranteeStatus;

	/** 属性担保种类 */
	private String guaranteeType;

	/** 属性备注 */
	private String remark;
	private String handleText = "";
	private ArrayList<?> guaranteeundwrtlogList = new ArrayList<Object>();

	private String offerCountBack = "";

	private String offerCountNot = "";

	/**
	 * 类PrpLguarantee的默认构造方法
	 */
	public PrpLguarantee() {
	}

	/**
	 * 属性立案号的getter方法
	 */
	@Id
	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性担保种类代码的getter方法
	 */

	@Column(name = "GUARANTEETYPECODE")
	public String getGuaranteeTypeCode() {
		return this.guaranteeTypeCode;
	}

	/**
	 * 属性担保种类代码的setter方法
	 */
	public void setGuaranteeTypeCode(String guaranteeTypeCode) {
		this.guaranteeTypeCode = guaranteeTypeCode;
	}

	/**
	 * 属性担保种类名称的getter方法
	 */

	@Column(name = "GUARANTEETYPENAME")
	public String getGuaranteeTypeName() {
		return this.guaranteeTypeName;
	}

	/**
	 * 属性担保种类名称的setter方法
	 */
	public void setGuaranteeTypeName(String guaranteeTypeName) {
		this.guaranteeTypeName = guaranteeTypeName;
	}

	/**
	 * 属性担保出具时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OFFERTIME")
	public Date getOfferTime() {
		return this.offerTime;
	}

	/**
	 * 属性担保出具时间的setter方法
	 */
	public void setOfferTime(Date offerTime) {
		this.offerTime = offerTime;
	}

	/**
	 * 属性担保出具份数的getter方法
	 */

	@Column(name = "OFFERCOUNT")
	public Integer getOfferCount() {
		return this.offerCount;
	}

	/**
	 * 属性担保出具份数的setter方法
	 */
	public void setOfferCount(Integer offerCount) {
		this.offerCount = offerCount;
	}

	/**
	 * 属性担保金额的getter方法
	 */

	@Column(name = "SUMGUARANTEE")
	public BigDecimal getSumGuarantee() {
		return this.sumGuarantee;
	}

	/**
	 * 属性担保金额的setter方法
	 */
	public void setSumGuarantee(BigDecimal sumGuarantee) {
		this.sumGuarantee = sumGuarantee;
	}

	/**
	 * 属性调查费用币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性调查费用币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性担保币别名称的getter方法
	 */

	@Column(name = "CURRENCYNAME")
	public String getCurrencyName() {
		return this.currencyName;
	}

	/**
	 * 属性担保币别名称的setter方法
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 属性担保起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNVALIDSTARTDATE")
	public Date getUnvalidStartDate() {
		return this.unvalidStartDate;
	}

	/**
	 * 属性担保起始日期的setter方法
	 */
	public void setUnvalidStartDate(Date unvalidStartDate) {
		this.unvalidStartDate = unvalidStartDate;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作者名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性操作者名称的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 属性担保截止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNVALIDENDDATE")
	public Date getUnValidendDate() {
		return this.unValidendDate;
	}

	/**
	 * 属性担保截止日期的setter方法
	 */
	public void setUnValidendDate(Date unValidendDate) {
		this.unValidendDate = unValidendDate;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性担保申请原因的getter方法
	 */

	@Column(name = "APPLYPERSON")
	public String getApplyPerson() {
		return this.applyPerson;
	}

	/**
	 * 属性担保申请原因的setter方法
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	/**
	 * 属性审核人代码的getter方法
	 */

	@Column(name = "UNDWRTPERSONCODE")
	public String getUndwrtPersonCode() {
		return this.undwrtPersonCode;
	}

	/**
	 * 属性审核人代码的setter方法
	 */
	public void setUndwrtPersonCode(String undwrtPersonCode) {
		this.undwrtPersonCode = undwrtPersonCode;
	}

	/**
	 * 属性审核人名的getter方法
	 */

	@Column(name = "UNDWRTPERSONNAME")
	public String getUndwrtPersonName() {
		return this.undwrtPersonName;
	}

	/**
	 * 属性审核人名的setter方法
	 */
	public void setUndwrtPersonName(String undwrtPersonName) {
		this.undwrtPersonName = undwrtPersonName;
	}

	/**
	 * 属性审核日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDWRTTIME")
	public Date getUndwrtTime() {
		return this.undwrtTime;
	}

	/**
	 * 属性审核日期的setter方法
	 */
	public void setUndwrtTime(Date undwrtTime) {
		this.undwrtTime = undwrtTime;
	}

	/**
	 * 属性担保是否回收的getter方法
	 */

	@Column(name = "RECYCLEFLAG")
	public String getRecycleFlag() {
		return this.recycleFlag;
	}

	/**
	 * 属性担保是否回收的setter方法
	 */
	public void setRecycleFlag(String recycleFlag) {
		this.recycleFlag = recycleFlag;
	}

	/**
	 * 属性担保状态的getter方法
	 */

	@Column(name = "GUARANTEESTATUS")
	public String getGuaranteeStatus() {
		return this.guaranteeStatus;
	}

	/**
	 * 属性担保状态的setter方法
	 */
	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	/**
	 * 属性担保种类的getter方法
	 */

	@Column(name = "GUARANTEETYPE")
	public String getGuaranteeType() {
		return this.guaranteeType;
	}

	/**
	 * 属性担保种类的setter方法
	 */
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public ArrayList<?> getGuaranteeundwrtlogList() {
		return guaranteeundwrtlogList;
	}

	public void setGuaranteeundwrtlogList(ArrayList<?> guaranteeundwrtlogList) {
		this.guaranteeundwrtlogList = guaranteeundwrtlogList;
	}

	@Transient
	public String getHandleText() {
		return handleText;
	}

	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}

	@Transient
	public String getOfferCountBack() {
		return offerCountBack;
	}

	public void setOfferCountBack(String offerCountBack) {
		this.offerCountBack = offerCountBack;
	}

	@Transient
	public String getOfferCountNot() {
		return offerCountNot;
	}

	public void setOfferCountNot(String offerCountNot) {
		this.offerCountNot = offerCountNot;
	}

}
