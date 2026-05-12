package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import java.util.List;

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
 * POJO类PrpLDocArchiveLog
 */
@Entity
@Table(name = "PRPLDOCARCHIVELOG")
public class PrpLDocArchiveLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLDocArchiveLogId id;

	/** 属性报案号 */
	private String registNo;

	/** 属性保单号 */
	private String policyNo;

	/** 属性归属机构 */
	private String comcode;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性结案日期 */
	private Date endCaseDate;

	/** 属性标的赔款金额 */
	private Double sumDutyPaid;

	/** 属性资料状态标志 */
	private String status;

	/** 属性调阅理由 */
	private String applyReason;

	/** 属性预计归档周期 */
	private String estimatePeriod;

	/** 属性申请延期次数 */
	private Integer applyDeferno;

	/** 属性延期周期 */
	private String applyDeferPeriod;

	/** 属性预计归档日期 */
	private Date estimateReturnDate;

	/** 属性实际归档日期 */
	private Date returnDate;

	/** 属性备注 */
	private String remark;

	/** 属性审核结论 */
	private String undwrtFlag;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorName;

	/** 属性操作时间 */
	private Date operatorDate;

	/** 属性模板号 */
	private Integer modelNo;

	/** 属性节点号 */
	private Integer nodeNo;

	/** 属性节点名称 */
	private String nodeName;

	/** 列表 */
	private List<PrpLDocArchiveLog> archiveList;

	/**
	 * 类PrpLDocArchiveLog的默认构造方法
	 */
	public PrpLDocArchiveLog() {
		id = new PrpLDocArchiveLogId();
	}

	public PrpLDocArchiveLog(PrpLDocArchiveLog prpLDocArchiveLog) {
		this.id = new PrpLDocArchiveLogId();
		id.setClaimNo(prpLDocArchiveLog.getId().getClaimNo());
		id.setSerialNo(prpLDocArchiveLog.getId().getSerialNo());
		this.registNo = prpLDocArchiveLog.registNo;
		this.policyNo = prpLDocArchiveLog.policyNo;
		this.comcode = prpLDocArchiveLog.comcode;
		this.insuredCode = prpLDocArchiveLog.insuredCode;
		this.insuredName = prpLDocArchiveLog.insuredName;
		this.endCaseDate = prpLDocArchiveLog.endCaseDate;
		this.sumDutyPaid = prpLDocArchiveLog.sumDutyPaid;
		this.status = prpLDocArchiveLog.status;
		this.applyReason = prpLDocArchiveLog.applyReason;
		this.estimatePeriod = prpLDocArchiveLog.estimatePeriod;
		this.applyDeferno = prpLDocArchiveLog.applyDeferno;
		this.applyDeferPeriod = prpLDocArchiveLog.applyDeferPeriod;
		this.estimateReturnDate = prpLDocArchiveLog.estimateReturnDate;
		this.returnDate = prpLDocArchiveLog.returnDate;
		this.remark = prpLDocArchiveLog.remark;
		this.undwrtFlag = prpLDocArchiveLog.undwrtFlag;
		this.operatorCode = prpLDocArchiveLog.operatorCode;
		this.operatorName = prpLDocArchiveLog.operatorName;
		this.operatorDate = prpLDocArchiveLog.operatorDate;
		this.modelNo = prpLDocArchiveLog.modelNo;
		this.nodeNo = prpLDocArchiveLog.nodeNo;
		this.nodeName = prpLDocArchiveLog.nodeName;
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLDocArchiveLogId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLDocArchiveLogId id) {
		this.id = id;
	}

	/**
	 * 属性报案号的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComcode() {
		return this.comcode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
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
	 * 属性标的赔款金额的getter方法
	 */

	@Column(name = "SUMDUTYPAID")
	public Double getSumDutyPaid() {
		return this.sumDutyPaid;
	}

	/**
	 * 属性标的赔款金额的setter方法
	 */
	public void setSumDutyPaid(Double sumDutyPaid) {
		this.sumDutyPaid = sumDutyPaid;
	}

	/**
	 * 属性资料状态标志的getter方法
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性资料状态标志的setter方法
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性调阅理由的getter方法
	 */

	@Column(name = "APPLYREASON")
	public String getApplyReason() {
		return this.applyReason;
	}

	/**
	 * 属性调阅理由的setter方法
	 */
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * 属性预计归档周期的getter方法
	 */

	@Column(name = "ESTIMATEPERIOD")
	public String getEstimatePeriod() {
		return this.estimatePeriod;
	}

	/**
	 * 属性预计归档周期的setter方法
	 */
	public void setEstimatePeriod(String estimatePeriod) {
		this.estimatePeriod = estimatePeriod;
	}

	/**
	 * 属性申请延期次数的getter方法
	 */

	@Column(name = "APPLYDEFERNO")
	public Integer getApplyDeferno() {
		return this.applyDeferno;
	}

	/**
	 * 属性申请延期次数的setter方法
	 */
	public void setApplyDeferno(Integer applyDeferno) {
		this.applyDeferno = applyDeferno;
	}

	/**
	 * 属性延期周期的getter方法
	 */

	@Column(name = "APPLYDEFERPERIOD")
	public String getApplyDeferPeriod() {
		return this.applyDeferPeriod;
	}

	/**
	 * 属性延期周期的setter方法
	 */
	public void setApplyDeferPeriod(String applyDeferPeriod) {
		this.applyDeferPeriod = applyDeferPeriod;
	}

	/**
	 * 属性预计归档日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ESTIMATERETURNDATE")
	public Date getEstimateReturnDate() {
		return this.estimateReturnDate;
	}

	/**
	 * 属性预计归档日期的setter方法
	 */
	public void setEstimateReturnDate(Date estimateReturnDate) {
		this.estimateReturnDate = estimateReturnDate;
	}

	/**
	 * 属性实际归档日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RETURNDATE")
	public Date getReturnDate() {
		return this.returnDate;
	}

	/**
	 * 属性实际归档日期的setter方法
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	/**
	 * 属性审核结论的getter方法
	 */

	@Column(name = "UNDWRTFLAG")
	public String getUndwrtFlag() {
		return this.undwrtFlag;
	}

	/**
	 * 属性审核结论的setter方法
	 */
	public void setUndwrtFlag(String undwrtFlag) {
		this.undwrtFlag = undwrtFlag;
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
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 属性操作时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATORDATE")
	public Date getOperatorDate() {
		return this.operatorDate;
	}

	/**
	 * 属性操作时间的setter方法
	 */
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}

	/**
	 * 属性模板号的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模板号的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性节点号的getter方法
	 */

	@Column(name = "NODENO")
	public Integer getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性节点号的setter方法
	 */
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性节点名称的getter方法
	 */

	@Column(name = "NODENAME")
	public String getNodeName() {
		return this.nodeName;
	}

	/**
	 * 属性节点名称的setter方法
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Transient
	public List<PrpLDocArchiveLog> getArchiveList() {
		return archiveList;
	}

	public void setArchiveList(List<PrpLDocArchiveLog> archiveList) {
		this.archiveList = archiveList;
	}

}
