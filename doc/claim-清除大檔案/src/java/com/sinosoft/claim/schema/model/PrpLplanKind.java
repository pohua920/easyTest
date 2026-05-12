package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。


import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLPlanKind赔案险种组合收费计划表
 */
@Entity
@Table(name = "PRPLPLANKIND")
public class PrpLplanKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLplanKindId id;

	/** 属性理赔费用表 */
	private PrpLplan prpLplan;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性险别 */
	private String kindCode;

	/** 属性条款代码 */
	private String clauseType;

	/** 属性险别金额(默认0) */
	private Double kindFee;

	/** 属性按险别缴费所占总缴费金额 */
	private Double kindFeeRate;

	/** 属性归属机构 */
	private String comCode;

	/** 属性赔付类型 */
	private String lossType;

	/** 属性收收/应付确认金额 */
	private Double realPayRefFee;

	/** 属性标志 */
	private String flag;

	/** 属性子险种代码 */
	private String subRiskCode;

	/** 属性应收/应付币种(原币) */
	private String planFeeCurrency;

	/** 属性PLANFEECNY */
	private Double planFeeCNY;

	/** 属性收付汇率 */
	private Double exchangeRate;

	/** 属性ONACCFLAG */
	private String onAccFlag;

	/** 属性REALPAYREFFLAG */
	private String realPayRefFlag;

	/** 属性INPUTDATE */
	private Date inputDate;
	private String chargeCode = "";

	/**
	 * 类PrpLPlanKind的默认构造方法
	 */
	public PrpLplanKind() {
		this.id = new PrpLplanKindId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")), @AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "payRefReason", column = @Column(name = "PAYREFREASON")),
			@AttributeOverride(name = "itemKindNo", column = @Column(name = "ITEMKINDNO")) })
	public PrpLplanKindId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLplanKindId id) {
		this.id = id;
	}

	/**
	 * 属性理赔费用表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CERTITYPE", referencedColumnName = "CERTITYPE", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CERTINO", referencedColumnName = "CERTINO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "SERIALNO", referencedColumnName = "SERIALNO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "PAYREFREASON", referencedColumnName = "PAYREFREASON", nullable = false, insertable = false, updatable = false) })
	public PrpLplan getPrpLplan() {
		return this.prpLplan;
	}

	/**
	 * 属性理赔费用表的setter方法
	 */
	public void setPrpLplan(PrpLplan prpLplan) {
		this.prpLplan = prpLplan;
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
	 * 属性险别的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性条款代码的getter方法
	 */

	@Column(name = "CLAUSETYPE")
	public String getClauseType() {
		return this.clauseType;
	}

	/**
	 * 属性条款代码的setter方法
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	/**
	 * 属性险别金额(默认0)的getter方法
	 */

	@Column(name = "KINDFEE")
	public Double getKindFee() {
		return this.kindFee;
	}

	/**
	 * 属性险别金额(默认0)的setter方法
	 */
	public void setKindFee(Double kindFee) {
		this.kindFee = kindFee;
	}

	/**
	 * 属性按险别缴费所占总缴费金额的getter方法
	 */

	@Column(name = "KINDFEERATE")
	public Double getKindFeeRate() {
		return this.kindFeeRate;
	}

	/**
	 * 属性按险别缴费所占总缴费金额的setter方法
	 */
	public void setKindFeeRate(Double kindFeeRate) {
		this.kindFeeRate = kindFeeRate;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性赔付类型的getter方法
	 */

	@Column(name = "LOSSTYPE")
	public String getLossType() {
		return this.lossType;
	}

	/**
	 * 属性赔付类型的setter方法
	 */
	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	/**
	 * 属性收收/应付确认金额的getter方法
	 */

	@Column(name = "REALPAYREFFEE")
	public Double getRealPayRefFee() {
		return this.realPayRefFee;
	}

	/**
	 * 属性收收/应付确认金额的setter方法
	 */
	public void setRealPayRefFee(Double realPayRefFee) {
		this.realPayRefFee = realPayRefFee;
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

	/**
	 * 属性子险种代码的getter方法
	 */

	@Column(name = "SUBRISKCODE")
	public String getSubRiskCode() {
		return this.subRiskCode;
	}

	/**
	 * 属性子险种代码的setter方法
	 */
	public void setSubRiskCode(String subRiskCode) {
		this.subRiskCode = subRiskCode;
	}

	/**
	 * 属性应收/应付币种(原币)的getter方法
	 */

	@Column(name = "PLANFEECURRENCY")
	public String getPlanFeeCurrency() {
		return this.planFeeCurrency;
	}

	/**
	 * 属性应收/应付币种(原币)的setter方法
	 */
	public void setPlanFeeCurrency(String planFeeCurrency) {
		this.planFeeCurrency = planFeeCurrency;
	}

	/**
	 * 属性PLANFEECNY的getter方法
	 */

	@Column(name = "PLANFEECNY")
	public Double getPlanFeeCNY() {
		return this.planFeeCNY;
	}

	/**
	 * 属性PLANFEECNY的setter方法
	 */
	public void setPlanFeeCNY(Double planFeeCNY) {
		this.planFeeCNY = planFeeCNY;
	}

	/**
	 * 属性收付汇率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性收付汇率的setter方法
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性ONACCFLAG的getter方法
	 */

	@Column(name = "ONACCFLAG")
	public String getOnAccFlag() {
		return this.onAccFlag;
	}

	/**
	 * 属性ONACCFLAG的setter方法
	 */
	public void setOnAccFlag(String onAccFlag) {
		this.onAccFlag = onAccFlag;
	}

	/**
	 * 属性REALPAYREFFLAG的getter方法
	 */

	@Column(name = "REALPAYREFFLAG")
	public String getRealPayRefFlag() {
		return this.realPayRefFlag;
	}

	/**
	 * 属性REALPAYREFFLAG的setter方法
	 */
	public void setRealPayRefFlag(String realPayRefFlag) {
		this.realPayRefFlag = realPayRefFlag;
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
	 * @return the chargeCode
	 */
	@Transient
	public String getChargeCode() {
		return chargeCode;
	}

	/**
	 * @param chargeCode the chargeCode to set
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

}
