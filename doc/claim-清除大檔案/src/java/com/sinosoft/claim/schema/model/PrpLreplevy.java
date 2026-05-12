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

import com.sinosoft.claim.dto.custom.TurnPageDto;

/**
 * POJO类PrpLreplevy权益转让及追偿登记表
 */
@Entity
@Table(name = "PRPLREPLEVY")
public class PrpLreplevy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLreplevyId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性追偿类型代码 */
	private String replevyTypeCode;

	/** 属性权益转让日期 */
	private Date rightTransferDate;

	/** 属性被追偿人名称 */
	private String repleviedName;

	/** 属性追偿原因 */
	private String replevyReason;

	/** 属性追偿途径（依据） */
	private String replevyWay;

	/** 属性回收日期 */
	private Date reclaimDate;

	/** 属性追回日期 */
	private Date validDate;

	/** 属性币别 */
	private String currency;

	/** 属性总追偿金额 */
	private BigDecimal sumReplevyFee;

	/** 属性总追回金额 */
	private BigDecimal sumValidFee;

	/** 属性摊销方式 */
	private String amortizeWay;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性公估师代码 */
	private String comCode;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性统计年月 */
	private Date statisticsYM;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性结案日期 */
	private Date endCaseDate;

	/** 属性结案员代码 */
	private String endCaseCode;

	/** 属性注销日期 */
	private Date cancelDate;

	/** 属性注销原因 */
	private String cancelReason;

	/** 属性注销人代码 */
	private String dealerCode;

	/** 属性备注 */
	private String note;

	/** 属性标志字段 */
	private String flag;

	/** 属性REPLEVYCHARGE */
	private BigDecimal replevyCharge;

	/** 属性追偿时效 */
	private Date replevyLimitDate;
	private TurnPageDto turnPageDto = null;

	private ArrayList<PrpLreplevy> prpLreplevyList = null;

	/**
	 * 类PrpLreplevy的默认构造方法
	 */
	public PrpLreplevy() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "times", column = @Column(name = "TIMES")) })
	public PrpLreplevyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLreplevyId id) {
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
	 * 属性追偿类型代码的getter方法
	 */

	@Column(name = "REPLEVYTYPECODE")
	public String getReplevyTypeCode() {
		return this.replevyTypeCode;
	}

	/**
	 * 属性追偿类型代码的setter方法
	 */
	public void setReplevyTypeCode(String replevyTypeCode) {
		this.replevyTypeCode = replevyTypeCode;
	}

	/**
	 * 属性权益转让日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RIGHTTRANSFERDATE")
	public Date getRightTransferDate() {
		return this.rightTransferDate;
	}

	/**
	 * 属性权益转让日期的setter方法
	 */
	public void setRightTransferDate(Date rightTransferDate) {
		this.rightTransferDate = rightTransferDate;
	}

	/**
	 * 属性被追偿人名称的getter方法
	 */

	@Column(name = "REPLEVIEDNAME")
	public String getRepleviedName() {
		return this.repleviedName;
	}

	/**
	 * 属性被追偿人名称的setter方法
	 */
	public void setRepleviedName(String repleviedName) {
		this.repleviedName = repleviedName;
	}

	/**
	 * 属性追偿原因的getter方法
	 */

	@Column(name = "REPLEVYREASON")
	public String getReplevyReason() {
		return this.replevyReason;
	}

	/**
	 * 属性追偿原因的setter方法
	 */
	public void setReplevyReason(String replevyReason) {
		this.replevyReason = replevyReason;
	}

	/**
	 * 属性追偿途径（依据）的getter方法
	 */

	@Column(name = "REPLEVYWAY")
	public String getReplevyWay() {
		return this.replevyWay;
	}

	/**
	 * 属性追偿途径（依据）的setter方法
	 */
	public void setReplevyWay(String replevyWay) {
		this.replevyWay = replevyWay;
	}

	/**
	 * 属性回收日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECLAIMDATE")
	public Date getReclaimDate() {
		return this.reclaimDate;
	}

	/**
	 * 属性回收日期的setter方法
	 */
	public void setReclaimDate(Date reclaimDate) {
		this.reclaimDate = reclaimDate;
	}

	/**
	 * 属性追回日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性追回日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
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
	 * 属性总追偿金额的getter方法
	 */

	@Column(name = "SUMREPLEVYFEE")
	public BigDecimal getSumReplevyFee() {
		return this.sumReplevyFee;
	}

	/**
	 * 属性总追偿金额的setter方法
	 */
	public void setSumReplevyFee(BigDecimal sumReplevyFee) {
		this.sumReplevyFee = sumReplevyFee;
	}

	/**
	 * 属性总追回金额的getter方法
	 */

	@Column(name = "SUMVALIDFEE")
	public BigDecimal getSumValidFee() {
		return this.sumValidFee;
	}

	/**
	 * 属性总追回金额的setter方法
	 */
	public void setSumValidFee(BigDecimal sumValidFee) {
		this.sumValidFee = sumValidFee;
	}

	/**
	 * 属性摊销方式的getter方法
	 */

	@Column(name = "AMORTIZEWAY")
	public String getAmortizeWay() {
		return this.amortizeWay;
	}

	/**
	 * 属性摊销方式的setter方法
	 */
	public void setAmortizeWay(String amortizeWay) {
		this.amortizeWay = amortizeWay;
	}

	/**
	 * 属性出单机构的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性公估师代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性公估师代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
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
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性归属业务员代码的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性归属业务员代码的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性统计年月的setter方法
	 */
	public void setStatisticsYM(Date statisticsYM) {
		this.statisticsYM = statisticsYM;
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
	 * 属性结案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDCASEDATE")
	public Date getEndCaseDate() {
		return this.endCaseDate;
	}

	/**
	 * 属性结案日期的setter方法
	 */
	public void setEndCaseDate(Date endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	/**
	 * 属性结案员代码的getter方法
	 */

	@Column(name = "ENDCASECODE")
	public String getEndCaseCode() {
		return this.endCaseCode;
	}

	/**
	 * 属性结案员代码的setter方法
	 */
	public void setEndCaseCode(String endCaseCode) {
		this.endCaseCode = endCaseCode;
	}

	/**
	 * 属性注销日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CANCELDATE")
	public Date getCancelDate() {
		return this.cancelDate;
	}

	/**
	 * 属性注销日期的setter方法
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * 属性注销原因的getter方法
	 */

	@Column(name = "CANCELREASON")
	public String getCancelReason() {
		return this.cancelReason;
	}

	/**
	 * 属性注销原因的setter方法
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * 属性注销人代码的getter方法
	 */

	@Column(name = "DEALERCODE")
	public String getDealerCode() {
		return this.dealerCode;
	}

	/**
	 * 属性注销人代码的setter方法
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性REPLEVYCHARGE的getter方法
	 */

	@Column(name = "REPLEVYCHARGE")
	public BigDecimal getReplevyCharge() {
		return this.replevyCharge;
	}

	/**
	 * 属性REPLEVYCHARGE的setter方法
	 */
	public void setReplevyCharge(BigDecimal replevyCharge) {
		this.replevyCharge = replevyCharge;
	}

	/**
	 * 属性追偿时效的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPLEVYLIMITDATE")
	public Date getReplevyLimitDate() {
		return this.replevyLimitDate;
	}

	/**
	 * 属性追偿时效的setter方法
	 */
	public void setReplevyLimitDate(Date replevyLimitDate) {
		this.replevyLimitDate = replevyLimitDate;
	}

	@Transient
	public ArrayList<PrpLreplevy> getPrpLreplevyList() {
		return prpLreplevyList;
	}

	public void setPrpLreplevyList(ArrayList<PrpLreplevy> prpLreplevyList) {
		this.prpLreplevyList = prpLreplevyList;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

}
