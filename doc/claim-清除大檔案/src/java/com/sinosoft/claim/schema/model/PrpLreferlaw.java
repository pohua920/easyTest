package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLreferlaw
 */
@Entity
@Table(name = "PRPLREFERLAW")
public class PrpLreferlaw implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLreferlawId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性REGISTNO */
	private String registno;

	/** 属性险种 */
	private String riskCode;

	/** 属性诉讼原因 */
	private String referlawreason;

	/** 属性诉讼金额 */
	private BigDecimal sumreferlaw;

	/** 属性诉讼费用 */
	private BigDecimal sumreferlawfee;

	/** 属性诉讼起始日期 */
	private Date validstartdate;

	/** 属性诉讼截止日期 */
	private Date validenddate;

	/** 属性诉讼费用币别代码 */
	private String currencyfee;

	/** 属性诉讼费用币别名称 */
	private String currencyfeename;

	/** 属性币别 */
	private String currency;

	/** 属性担保币别名称 */
	private String currencyName;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorname;

	/** 属性法院一审判决情况 */
	private String adjudgmentone;

	/** 属性法院二审判决情况 */
	private String adjudgmenttwo;

	/** 属性法院三审判决情况 */
	private String adjudgmentthree;

	/** 属性是否胜诉 */
	private String successflag;

	/** 属性发票/支付单备注 */
	private String remark;
	private ArrayList<?> prpllawyerDtoList = new ArrayList<Object>();
	/** 属性是否已结案 */
	private boolean checkEndCase = false;

	/**
	 * 类PrpLreferlaw的默认构造方法
	 */
	public PrpLreferlaw() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLreferlawId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLreferlawId id) {
		this.id = id;
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
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性诉讼原因的getter方法
	 */

	@Column(name = "REFERLAWREASON")
	public String getReferlawreason() {
		return this.referlawreason;
	}

	/**
	 * 属性诉讼原因的setter方法
	 */
	public void setReferlawreason(String referlawreason) {
		this.referlawreason = referlawreason;
	}

	/**
	 * 属性诉讼金额的getter方法
	 */

	@Column(name = "SUMREFERLAW")
	public BigDecimal getSumreferlaw() {
		return this.sumreferlaw;
	}

	/**
	 * 属性诉讼金额的setter方法
	 */
	public void setSumreferlaw(BigDecimal sumreferlaw) {
		this.sumreferlaw = sumreferlaw;
	}

	/**
	 * 属性诉讼费用的getter方法
	 */

	@Column(name = "SUMREFERLAWFEE")
	public BigDecimal getSumreferlawfee() {
		return this.sumreferlawfee;
	}

	/**
	 * 属性诉讼费用的setter方法
	 */
	public void setSumreferlawfee(BigDecimal sumreferlawfee) {
		this.sumreferlawfee = sumreferlawfee;
	}

	/**
	 * 属性诉讼起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDSTARTDATE")
	public Date getValidstartdate() {
		return this.validstartdate;
	}

	/**
	 * 属性诉讼起始日期的setter方法
	 */
	public void setValidstartdate(Date validstartdate) {
		this.validstartdate = validstartdate;
	}

	/**
	 * 属性诉讼截止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDENDDATE")
	public Date getValidenddate() {
		return this.validenddate;
	}

	/**
	 * 属性诉讼截止日期的setter方法
	 */
	public void setValidenddate(Date validenddate) {
		this.validenddate = validenddate;
	}

	/**
	 * 属性诉讼费用币别代码的getter方法
	 */

	@Column(name = "CURRENCYFEE")
	public String getCurrencyfee() {
		return this.currencyfee;
	}

	/**
	 * 属性诉讼费用币别代码的setter方法
	 */
	public void setCurrencyfee(String currencyfee) {
		this.currencyfee = currencyfee;
	}

	/**
	 * 属性诉讼费用币别名称的getter方法
	 */

	@Column(name = "CURRENCYFEENAME")
	public String getCurrencyfeename() {
		return this.currencyfeename;
	}

	/**
	 * 属性诉讼费用币别名称的setter方法
	 */
	public void setCurrencyfeename(String currencyfeename) {
		this.currencyfeename = currencyfeename;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
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
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorname() {
		return this.operatorname;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	/**
	 * 属性法院一审判决情况的getter方法
	 */

	@Column(name = "ADJUDGMENTONE")
	public String getAdjudgmentone() {
		return this.adjudgmentone;
	}

	/**
	 * 属性法院一审判决情况的setter方法
	 */
	public void setAdjudgmentone(String adjudgmentone) {
		this.adjudgmentone = adjudgmentone;
	}

	/**
	 * 属性法院二审判决情况的getter方法
	 */

	@Column(name = "ADJUDGMENTTWO")
	public String getAdjudgmenttwo() {
		return this.adjudgmenttwo;
	}

	/**
	 * 属性法院二审判决情况的setter方法
	 */
	public void setAdjudgmenttwo(String adjudgmenttwo) {
		this.adjudgmenttwo = adjudgmenttwo;
	}

	/**
	 * 属性法院三审判决情况的getter方法
	 */

	@Column(name = "ADJUDGMENTTHREE")
	public String getAdjudgmentthree() {
		return this.adjudgmentthree;
	}

	/**
	 * 属性法院三审判决情况的setter方法
	 */
	public void setAdjudgmentthree(String adjudgmentthree) {
		this.adjudgmentthree = adjudgmentthree;
	}

	/**
	 * 属性是否胜诉的getter方法
	 */

	@Column(name = "SUCCESSFLAG")
	public String getSuccessflag() {
		return this.successflag;
	}

	/**
	 * 属性是否胜诉的setter方法
	 */
	public void setSuccessflag(String successflag) {
		this.successflag = successflag;
	}

	/**
	 * 属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public boolean isCheckEndCase() {
		return checkEndCase;
	}

	public void setCheckEndCase(boolean checkEndCase) {
		this.checkEndCase = checkEndCase;
	}

	@Transient
	public ArrayList<?> getPrpllawyerDtoList() {
		return prpllawyerDtoList;
	}

	public void setPrpllawyerDtoList(ArrayList<?> prpllawyerDtoList) {
		this.prpllawyerDtoList = prpllawyerDtoList;
	}

}
