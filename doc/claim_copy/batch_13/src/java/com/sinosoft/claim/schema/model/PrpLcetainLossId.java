package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcetainLossId
 */
@Embeddable
public class PrpLcetainLossId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性报案号码 */
	private String registNo;

	/** 属性标的序号 */
	private BigDecimal itemNo;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性开始时间 */
	private Date startDate;

	/** 属性开始小时 */
	private String startHour;

	/** 属性结束时间 */
	private Date endDate;

	/** 属性结束小时 */
	private String endHour;

	/** 属性调查费用币别 */
	private String currency;

	/** 属性剔除金额/残值/损余 */
	private BigDecimal sumRest;

	/** 属性管理费 */
	private BigDecimal sumManager;

	/** 属性总定损金额 */
	private BigDecimal sumCertainLoss;

	/** 属性总核损金额 */
	private BigDecimal sumVerifyLoss;

	/** 属性损失部位及程度概述 */
	private String lossDesc;

	/** 属性赔偿责任代码 */
	private String indemnityDuty;

	/** 属性责任比例 */
	private BigDecimal indemnityDutyRate;

	/** 属性备注 */
	private String remark;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性审核人代码 */
	private String approverCode;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLcetainLossId的默认构造方法
	 */
	public PrpLcetainLossId() {
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
	 * 属性标的序号的getter方法
	 */

	@Column(name = "ITEMNO")
	public BigDecimal getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setItemNo(BigDecimal itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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
	 * 属性开始时间的getter方法
	 */

	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性开始时间的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性开始小时的getter方法
	 */

	@Column(name = "STARTHOUR")
	public String getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性开始小时的setter方法
	 */
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	/**
	 * 属性结束时间的getter方法
	 */

	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性结束时间的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性结束小时的getter方法
	 */

	@Column(name = "ENDHOUR")
	public String getEndHour() {
		return this.endHour;
	}

	/**
	 * 属性结束小时的setter方法
	 */
	public void setEndHour(String endHour) {
		this.endHour = endHour;
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
	 * 属性剔除金额/残值/损余的getter方法
	 */

	@Column(name = "SUMREST")
	public BigDecimal getSumRest() {
		return this.sumRest;
	}

	/**
	 * 属性剔除金额/残值/损余的setter方法
	 */
	public void setSumRest(BigDecimal sumRest) {
		this.sumRest = sumRest;
	}

	/**
	 * 属性管理费的getter方法
	 */

	@Column(name = "SUMMANAGER")
	public BigDecimal getSumManager() {
		return this.sumManager;
	}

	/**
	 * 属性管理费的setter方法
	 */
	public void setSumManager(BigDecimal sumManager) {
		this.sumManager = sumManager;
	}

	/**
	 * 属性总定损金额的getter方法
	 */

	@Column(name = "SUMCERTAINLOSS")
	public BigDecimal getSumCertainLoss() {
		return this.sumCertainLoss;
	}

	/**
	 * 属性总定损金额的setter方法
	 */
	public void setSumCertainLoss(BigDecimal sumCertainLoss) {
		this.sumCertainLoss = sumCertainLoss;
	}

	/**
	 * 属性总核损金额的getter方法
	 */

	@Column(name = "SUMVERIFYLOSS")
	public BigDecimal getSumVerifyLoss() {
		return this.sumVerifyLoss;
	}

	/**
	 * 属性总核损金额的setter方法
	 */
	public void setSumVerifyLoss(BigDecimal sumVerifyLoss) {
		this.sumVerifyLoss = sumVerifyLoss;
	}

	/**
	 * 属性损失部位及程度概述的getter方法
	 */

	@Column(name = "LOSSDESC")
	public String getLossDesc() {
		return this.lossDesc;
	}

	/**
	 * 属性损失部位及程度概述的setter方法
	 */
	public void setLossDesc(String lossDesc) {
		this.lossDesc = lossDesc;
	}

	/**
	 * 属性赔偿责任代码的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性赔偿责任代码的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性责任比例的getter方法
	 */

	@Column(name = "INDEMNITYDUTYRATE")
	public BigDecimal getIndemnityDutyRate() {
		return this.indemnityDutyRate;
	}

	/**
	 * 属性责任比例的setter方法
	 */
	public void setIndemnityDutyRate(BigDecimal indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
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
	 * 属性审核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性审核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLcetainLossId)) {
			return false;
		}
		PrpLcetainLossId castOther = (PrpLcetainLossId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getItemNo() == castOther.getItemNo()) || (this.getItemNo() != null && castOther.getItemNo() != null && this.getItemNo().equals(castOther.getItemNo())))
				&& ((this.getLicenseNo() == castOther.getLicenseNo()) || (this.getLicenseNo() != null && castOther.getLicenseNo() != null && this.getLicenseNo().equals(castOther.getLicenseNo())))
				&& ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this.getStartDate() != null && castOther.getStartDate() != null && this.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getStartHour() == castOther.getStartHour()) || (this.getStartHour() != null && castOther.getStartHour() != null && this.getStartHour().equals(castOther.getStartHour())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this.getEndDate() != null && castOther.getEndDate() != null && this.getEndDate().equals(castOther.getEndDate())))
				&& ((this.getEndHour() == castOther.getEndHour()) || (this.getEndHour() != null && castOther.getEndHour() != null && this.getEndHour().equals(castOther.getEndHour())))
				&& ((this.getCurrency() == castOther.getCurrency()) || (this.getCurrency() != null && castOther.getCurrency() != null && this.getCurrency().equals(castOther.getCurrency())))
				&& ((this.getSumRest() == castOther.getSumRest()) || (this.getSumRest() != null && castOther.getSumRest() != null && this.getSumRest().equals(castOther.getSumRest())))
				&& ((this.getSumManager() == castOther.getSumManager()) || (this.getSumManager() != null && castOther.getSumManager() != null && this.getSumManager().equals(castOther.getSumManager())))
				&& ((this.getSumCertainLoss() == castOther.getSumCertainLoss()) || (this.getSumCertainLoss() != null && castOther.getSumCertainLoss() != null && this.getSumCertainLoss().equals(castOther.getSumCertainLoss())))
				&& ((this.getSumVerifyLoss() == castOther.getSumVerifyLoss()) || (this.getSumVerifyLoss() != null && castOther.getSumVerifyLoss() != null && this.getSumVerifyLoss().equals(castOther.getSumVerifyLoss())))
				&& ((this.getLossDesc() == castOther.getLossDesc()) || (this.getLossDesc() != null && castOther.getLossDesc() != null && this.getLossDesc().equals(castOther.getLossDesc())))
				&& ((this.getIndemnityDuty() == castOther.getIndemnityDuty()) || (this.getIndemnityDuty() != null && castOther.getIndemnityDuty() != null && this.getIndemnityDuty().equals(castOther.getIndemnityDuty())))
				&& ((this.getIndemnityDutyRate() == castOther.getIndemnityDutyRate()) || (this.getIndemnityDutyRate() != null && castOther.getIndemnityDutyRate() != null && this.getIndemnityDutyRate().equals(castOther.getIndemnityDutyRate())))
				&& ((this.getRemark() == castOther.getRemark()) || (this.getRemark() != null && castOther.getRemark() != null && this.getRemark().equals(castOther.getRemark())))
				&& ((this.getOperatorCode() == castOther.getOperatorCode()) || (this.getOperatorCode() != null && castOther.getOperatorCode() != null && this.getOperatorCode().equals(castOther.getOperatorCode())))
				&& ((this.getApproverCode() == castOther.getApproverCode()) || (this.getApproverCode() != null && castOther.getApproverCode() != null && this.getApproverCode().equals(castOther.getApproverCode())))
				&& ((this.getFlag() == castOther.getFlag()) || (this.getFlag() != null && castOther.getFlag() != null && this.getFlag().equals(castOther.getFlag())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getItemNo() == null ? 0 : this.getItemNo().hashCode());
		result = 37 * result + (getLicenseNo() == null ? 0 : this.getLicenseNo().hashCode());
		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result + (getStartHour() == null ? 0 : this.getStartHour().hashCode());
		result = 37 * result + (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37 * result + (getEndHour() == null ? 0 : this.getEndHour().hashCode());
		result = 37 * result + (getCurrency() == null ? 0 : this.getCurrency().hashCode());
		result = 37 * result + (getSumRest() == null ? 0 : this.getSumRest().hashCode());
		result = 37 * result + (getSumManager() == null ? 0 : this.getSumManager().hashCode());
		result = 37 * result + (getSumCertainLoss() == null ? 0 : this.getSumCertainLoss().hashCode());
		result = 37 * result + (getSumVerifyLoss() == null ? 0 : this.getSumVerifyLoss().hashCode());
		result = 37 * result + (getLossDesc() == null ? 0 : this.getLossDesc().hashCode());
		result = 37 * result + (getIndemnityDuty() == null ? 0 : this.getIndemnityDuty().hashCode());
		result = 37 * result + (getIndemnityDutyRate() == null ? 0 : this.getIndemnityDutyRate().hashCode());
		result = 37 * result + (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result + (getOperatorCode() == null ? 0 : this.getOperatorCode().hashCode());
		result = 37 * result + (getApproverCode() == null ? 0 : this.getApproverCode().hashCode());
		result = 37 * result + (getFlag() == null ? 0 : this.getFlag().hashCode());
		return result;
	}

}
