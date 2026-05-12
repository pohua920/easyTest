package com.sinosoft.claim.schema.model;

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
 * POJO类PrpLassure
 */
@Entity
@Table(name = "PRPLASSURE")
public class PrpLassure implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性担保号 */
	private String assureNo;

	/** 属性关联担保号 */
	private String relativeAssureNo;

	/** 属性担保函类型 */
	private String assureTypeCode;

	/** 属性担保性质 */
	private String assureNatureCode;

	/** 属性担保原因 */
	private String assureReason;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性船舶险：船名 */
	private String shipName;

	/** 属性船舶险：船名（英文） */
	private String shipEName;

	/** 属性船东 */
	private String shipOwner;

	/** 属性航次 */
	private String voyage;

	/** 属性起始地 */
	private String startSiteName;

	/** 属性中转地 */
	private String viaSiteName;

	/** 属性目的地 */
	private String endSiteName;

	/** 属性扣押船法院 */
	private String countName;

	/** 属性法人代表 */
	private String artificialPerson;

	/** 属性保协名称 */
	private String piservice;

	/** 属性被侵权人 */
	private String bytortiousUnit;

	/** 属性被侵权标的 */
	private String bytortiousObject;

	/** 属性币别 */
	private String currency;

	/** 属性担保金额/反担保金额 */
	private BigDecimal reverseAmount;

	/** 属性手续费金额 */
	private BigDecimal commissionCharge;

	/** 属性损失金额 */
	private BigDecimal sumLoss;

	/** 属性诉讼费用 */
	private BigDecimal legalCost;

	/** 属性利息 */
	private BigDecimal accrual;

	/** 属性其它费用 */
	private BigDecimal otherFee;

	/** 属性是否不可撤销担保 */
	private String quashAssure;

	/** 属性担保人 */
	private String assurer;

	/** 属性收到日期 */
	private Date receiveDate;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性经办人 */
	private String handlerCode;

	/** 属性案情简要描述 */
	private String claimDescribe;

	/** 属性退还日期 */
	private Date returnDate;

	/** 属性退还经办人代码 */
	private String returnHandlerCode;

	/** 属性反担保人 */
	private String reverseCautioner;

	/** 属性承办人 */
	private String purveyor;

	/** 属性处（科）长意见 */
	private String chiefText;

	/** 属性申请担保人意见 */
	private String applicationText;

	/** 属性上级意见 */
	private String superText;

	/** 属性担保日期 */
	private Date assureDate;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLassure的默认构造方法
	 */
	public PrpLassure() {
	}

	/**
	 * 属性担保号的getter方法
	 */
	@Id
	@Column(name = "ASSURENO")
	public String getAssureNo() {
		return this.assureNo;
	}

	/**
	 * 属性担保号的setter方法
	 */
	public void setAssureNo(String assureNo) {
		this.assureNo = assureNo;
	}

	/**
	 * 属性关联担保号的getter方法
	 */

	@Column(name = "RELATIVEASSURENO")
	public String getRelativeAssureNo() {
		return this.relativeAssureNo;
	}

	/**
	 * 属性关联担保号的setter方法
	 */
	public void setRelativeAssureNo(String relativeAssureNo) {
		this.relativeAssureNo = relativeAssureNo;
	}

	/**
	 * 属性担保函类型的getter方法
	 */

	@Column(name = "ASSURETYPECODE")
	public String getAssureTypeCode() {
		return this.assureTypeCode;
	}

	/**
	 * 属性担保函类型的setter方法
	 */
	public void setAssureTypeCode(String assureTypeCode) {
		this.assureTypeCode = assureTypeCode;
	}

	/**
	 * 属性担保性质的getter方法
	 */

	@Column(name = "ASSURENATURECODE")
	public String getAssureNatureCode() {
		return this.assureNatureCode;
	}

	/**
	 * 属性担保性质的setter方法
	 */
	public void setAssureNatureCode(String assureNatureCode) {
		this.assureNatureCode = assureNatureCode;
	}

	/**
	 * 属性担保原因的getter方法
	 */

	@Column(name = "ASSUREREASON")
	public String getAssureReason() {
		return this.assureReason;
	}

	/**
	 * 属性担保原因的setter方法
	 */
	public void setAssureReason(String assureReason) {
		this.assureReason = assureReason;
	}

	/**
	 * 属性立案号的getter方法
	 */

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
	 * 属性船舶险：船名的getter方法
	 */

	@Column(name = "SHIPNAME")
	public String getShipName() {
		return this.shipName;
	}

	/**
	 * 属性船舶险：船名的setter方法
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	/**
	 * 属性船舶险：船名（英文）的getter方法
	 */

	@Column(name = "SHIPENAME")
	public String getShipEName() {
		return this.shipEName;
	}

	/**
	 * 属性船舶险：船名（英文）的setter方法
	 */
	public void setShipEName(String shipEName) {
		this.shipEName = shipEName;
	}

	/**
	 * 属性船东的getter方法
	 */

	@Column(name = "SHIPOWNER")
	public String getShipOwner() {
		return this.shipOwner;
	}

	/**
	 * 属性船东的setter方法
	 */
	public void setShipOwner(String shipOwner) {
		this.shipOwner = shipOwner;
	}

	/**
	 * 属性航次的getter方法
	 */

	@Column(name = "VOYAGE")
	public String getVoyage() {
		return this.voyage;
	}

	/**
	 * 属性航次的setter方法
	 */
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	/**
	 * 属性起始地的getter方法
	 */

	@Column(name = "STARTSITENAME")
	public String getStartSiteName() {
		return this.startSiteName;
	}

	/**
	 * 属性起始地的setter方法
	 */
	public void setStartSiteName(String startSiteName) {
		this.startSiteName = startSiteName;
	}

	/**
	 * 属性中转地的getter方法
	 */

	@Column(name = "VIASITENAME")
	public String getViaSiteName() {
		return this.viaSiteName;
	}

	/**
	 * 属性中转地的setter方法
	 */
	public void setViaSiteName(String viaSiteName) {
		this.viaSiteName = viaSiteName;
	}

	/**
	 * 属性目的地的getter方法
	 */

	@Column(name = "ENDSITENAME")
	public String getEndSiteName() {
		return this.endSiteName;
	}

	/**
	 * 属性目的地的setter方法
	 */
	public void setEndSiteName(String endSiteName) {
		this.endSiteName = endSiteName;
	}

	/**
	 * 属性扣押船法院的getter方法
	 */

	@Column(name = "COUNTNAME")
	public String getCountName() {
		return this.countName;
	}

	/**
	 * 属性扣押船法院的setter方法
	 */
	public void setCountName(String countName) {
		this.countName = countName;
	}

	/**
	 * 属性法人代表的getter方法
	 */

	@Column(name = "ARTIFICIALPERSON")
	public String getArtificialPerson() {
		return this.artificialPerson;
	}

	/**
	 * 属性法人代表的setter方法
	 */
	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

	/**
	 * 属性保协名称的getter方法
	 */

	@Column(name = "PISERVICE")
	public String getPiservice() {
		return this.piservice;
	}

	/**
	 * 属性保协名称的setter方法
	 */
	public void setPiservice(String piservice) {
		this.piservice = piservice;
	}

	/**
	 * 属性被侵权人的getter方法
	 */

	@Column(name = "BYTORTIOUSUNIT")
	public String getBytortiousUnit() {
		return this.bytortiousUnit;
	}

	/**
	 * 属性被侵权人的setter方法
	 */
	public void setBytortiousUnit(String bytortiousUnit) {
		this.bytortiousUnit = bytortiousUnit;
	}

	/**
	 * 属性被侵权标的的getter方法
	 */

	@Column(name = "BYTORTIOUSOBJECT")
	public String getBytortiousObject() {
		return this.bytortiousObject;
	}

	/**
	 * 属性被侵权标的的setter方法
	 */
	public void setBytortiousObject(String bytortiousObject) {
		this.bytortiousObject = bytortiousObject;
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
	 * 属性担保金额/反担保金额的getter方法
	 */

	@Column(name = "REVERSEAMOUNT")
	public BigDecimal getReverseAmount() {
		return this.reverseAmount;
	}

	/**
	 * 属性担保金额/反担保金额的setter方法
	 */
	public void setReverseAmount(BigDecimal reverseAmount) {
		this.reverseAmount = reverseAmount;
	}

	/**
	 * 属性手续费金额的getter方法
	 */

	@Column(name = "COMMISSIONCHARGE")
	public BigDecimal getCommissionCharge() {
		return this.commissionCharge;
	}

	/**
	 * 属性手续费金额的setter方法
	 */
	public void setCommissionCharge(BigDecimal commissionCharge) {
		this.commissionCharge = commissionCharge;
	}

	/**
	 * 属性损失金额的getter方法
	 */

	@Column(name = "SUMLOSS")
	public BigDecimal getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性损失金额的setter方法
	 */
	public void setSumLoss(BigDecimal sumLoss) {
		this.sumLoss = sumLoss;
	}

	/**
	 * 属性诉讼费用的getter方法
	 */

	@Column(name = "LEGALCOST")
	public BigDecimal getLegalCost() {
		return this.legalCost;
	}

	/**
	 * 属性诉讼费用的setter方法
	 */
	public void setLegalCost(BigDecimal legalCost) {
		this.legalCost = legalCost;
	}

	/**
	 * 属性利息的getter方法
	 */

	@Column(name = "ACCRUAL")
	public BigDecimal getAccrual() {
		return this.accrual;
	}

	/**
	 * 属性利息的setter方法
	 */
	public void setAccrual(BigDecimal accrual) {
		this.accrual = accrual;
	}

	/**
	 * 属性其它费用的getter方法
	 */

	@Column(name = "OTHERFEE")
	public BigDecimal getOtherFee() {
		return this.otherFee;
	}

	/**
	 * 属性其它费用的setter方法
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * 属性是否不可撤销担保的getter方法
	 */

	@Column(name = "QUASHASSURE")
	public String getQuashAssure() {
		return this.quashAssure;
	}

	/**
	 * 属性是否不可撤销担保的setter方法
	 */
	public void setQuashAssure(String quashAssure) {
		this.quashAssure = quashAssure;
	}

	/**
	 * 属性担保人的getter方法
	 */

	@Column(name = "ASSURER")
	public String getAssurer() {
		return this.assurer;
	}

	/**
	 * 属性担保人的setter方法
	 */
	public void setAssurer(String assurer) {
		this.assurer = assurer;
	}

	/**
	 * 属性收到日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECEIVEDATE")
	public Date getReceiveDate() {
		return this.receiveDate;
	}

	/**
	 * 属性收到日期的setter方法
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * 属性复核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性复核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性经办人的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性案情简要描述的getter方法
	 */

	@Column(name = "CLAIMDESCRIBE")
	public String getClaimDescribe() {
		return this.claimDescribe;
	}

	/**
	 * 属性案情简要描述的setter方法
	 */
	public void setClaimDescribe(String claimDescribe) {
		this.claimDescribe = claimDescribe;
	}

	/**
	 * 属性退还日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RETURNDATE")
	public Date getReturnDate() {
		return this.returnDate;
	}

	/**
	 * 属性退还日期的setter方法
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * 属性退还经办人代码的getter方法
	 */

	@Column(name = "RETURNHANDLERCODE")
	public String getReturnHandlerCode() {
		return this.returnHandlerCode;
	}

	/**
	 * 属性退还经办人代码的setter方法
	 */
	public void setReturnHandlerCode(String returnHandlerCode) {
		this.returnHandlerCode = returnHandlerCode;
	}

	/**
	 * 属性反担保人的getter方法
	 */

	@Column(name = "REVERSECAUTIONER")
	public String getReverseCautioner() {
		return this.reverseCautioner;
	}

	/**
	 * 属性反担保人的setter方法
	 */
	public void setReverseCautioner(String reverseCautioner) {
		this.reverseCautioner = reverseCautioner;
	}

	/**
	 * 属性承办人的getter方法
	 */

	@Column(name = "PURVEYOR")
	public String getPurveyor() {
		return this.purveyor;
	}

	/**
	 * 属性承办人的setter方法
	 */
	public void setPurveyor(String purveyor) {
		this.purveyor = purveyor;
	}

	/**
	 * 属性处（科）长意见的getter方法
	 */

	@Column(name = "CHIEFTEXT")
	public String getChiefText() {
		return this.chiefText;
	}

	/**
	 * 属性处（科）长意见的setter方法
	 */
	public void setChiefText(String chiefText) {
		this.chiefText = chiefText;
	}

	/**
	 * 属性申请担保人意见的getter方法
	 */

	@Column(name = "APPLICATIONTEXT")
	public String getApplicationText() {
		return this.applicationText;
	}

	/**
	 * 属性申请担保人意见的setter方法
	 */
	public void setApplicationText(String applicationText) {
		this.applicationText = applicationText;
	}

	/**
	 * 属性上级意见的getter方法
	 */

	@Column(name = "SUPERTEXT")
	public String getSuperText() {
		return this.superText;
	}

	/**
	 * 属性上级意见的setter方法
	 */
	public void setSuperText(String superText) {
		this.superText = superText;
	}

	/**
	 * 属性担保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ASSUREDATE")
	public Date getAssureDate() {
		return this.assureDate;
	}

	/**
	 * 属性担保日期的setter方法
	 */
	public void setAssureDate(Date assureDate) {
		this.assureDate = assureDate;
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
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
