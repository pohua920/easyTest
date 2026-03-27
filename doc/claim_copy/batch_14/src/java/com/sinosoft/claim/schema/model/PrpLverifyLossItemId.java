package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLverifyLossItemId
 */
@Embeddable
public class PrpLverifyLossItemId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性REGISTNO */
	private String registno;

	/** 属性立案号 */
	private String claimNo;

	/** 属性交费计划序号 */
	private Integer serialNo;

	/** 属性赔付类型 */
	private String lossType;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性被保人名称 */
	private String insuredName;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性币别 */
	private String currency;

	/** 属性定损总金额 */
	private double sumPreDefLoss;

	/** 属性核定损金额（中间计算乘以数量） */
	private double sumDefLoss;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性归属机构 */
	private String comCode;

	/** 属性经办人 */
	private String handlerCode;

	/** 属性定损人 */
	private String handlerName;

	/** 属性定损结束日期 */
	private Date defLossDate;

	/** 属性核损人代码 */
	private String underWriteCode;

	/** 属性核损人名称 */
	private String underWriteName;

	/** 属性最终核损完成日期 */
	private Date underWriteEndDate;

	/** 属性审核通过标志 */
	private String underwriteflag;

	/** 属性节点种类 */
	private String nodeType;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性备注(核损) */
	private String verifyRemark;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLverifyLossItemId的默认构造方法
	 */
	public PrpLverifyLossItemId() {
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
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
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
	 * 属性被保人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
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
	 * 属性车牌底色代码的getter方法
	 */

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	/**
	 * 属性车牌底色代码的setter方法
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * 属性车辆种类代码的getter方法
	 */

	@Column(name = "CARKINDCODE")
	public String getCarKindCode() {
		return this.carKindCode;
	}

	/**
	 * 属性车辆种类代码的setter方法
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
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
	 * 属性定损总金额的getter方法
	 */

	@Column(name = "SUMPREDEFLOSS")
	public double getSumPreDefLoss() {
		return this.sumPreDefLoss;
	}

	/**
	 * 属性定损总金额的setter方法
	 */
	public void setSumPreDefLoss(double sumPreDefLoss) {
		this.sumPreDefLoss = sumPreDefLoss;
	}

	/**
	 * 属性核定损金额（中间计算乘以数量）的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public double getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性核定损金额（中间计算乘以数量）的setter方法
	 */
	public void setSumDefLoss(double sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
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
	 * 属性定损人的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性定损人的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性定损结束日期的getter方法
	 */

	@Column(name = "DEFLOSSDATE")
	public Date getDefLossDate() {
		return this.defLossDate;
	}

	/**
	 * 属性定损结束日期的setter方法
	 */
	public void setDefLossDate(Date defLossDate) {
		this.defLossDate = defLossDate;
	}

	/**
	 * 属性核损人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性核损人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 属性核损人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性核损人名称的setter方法
	 */
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
	}

	/**
	 * 属性最终核损完成日期的getter方法
	 */

	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性最终核损完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 属性审核通过标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderwriteflag() {
		return this.underwriteflag;
	}

	/**
	 * 属性审核通过标志的setter方法
	 */
	public void setUnderwriteflag(String underwriteflag) {
		this.underwriteflag = underwriteflag;
	}

	/**
	 * 属性节点种类的getter方法
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点种类的setter方法
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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

	/**
	 * 属性备注(核损)的getter方法
	 */

	@Column(name = "VERIFYREMARK")
	public String getVerifyRemark() {
		return this.verifyRemark;
	}

	/**
	 * 属性备注(核损)的setter方法
	 */
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLverifyLossItemId)) {
			return false;
		}
		PrpLverifyLossItemId castOther = (PrpLverifyLossItemId) other;

		return ((this.getRegistno() == castOther.getRegistno()) || (this.getRegistno() != null && castOther.getRegistno() != null && this.getRegistno().equals(castOther.getRegistno())))
				&& ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getLossType() == castOther.getLossType()) || (this.getLossType() != null && castOther.getLossType() != null && this.getLossType().equals(castOther.getLossType())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getInsuredName() == castOther.getInsuredName()) || (this.getInsuredName() != null && castOther.getInsuredName() != null && this.getInsuredName().equals(castOther.getInsuredName())))
				&& ((this.getLicenseNo() == castOther.getLicenseNo()) || (this.getLicenseNo() != null && castOther.getLicenseNo() != null && this.getLicenseNo().equals(castOther.getLicenseNo())))
				&& ((this.getLicenseColorCode() == castOther.getLicenseColorCode()) || (this.getLicenseColorCode() != null && castOther.getLicenseColorCode() != null && this.getLicenseColorCode().equals(castOther.getLicenseColorCode())))
				&& ((this.getCarKindCode() == castOther.getCarKindCode()) || (this.getCarKindCode() != null && castOther.getCarKindCode() != null && this.getCarKindCode().equals(castOther.getCarKindCode())))
				&& ((this.getCurrency() == castOther.getCurrency()) || (this.getCurrency() != null && castOther.getCurrency() != null && this.getCurrency().equals(castOther.getCurrency())))
				&& ((this.getMakeCom() == castOther.getMakeCom()) || (this.getMakeCom() != null && castOther.getMakeCom() != null && this.getMakeCom().equals(castOther.getMakeCom())))
				&& ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getHandlerCode() == castOther.getHandlerCode()) || (this.getHandlerCode() != null && castOther.getHandlerCode() != null && this.getHandlerCode().equals(castOther.getHandlerCode())))
				&& ((this.getHandlerName() == castOther.getHandlerName()) || (this.getHandlerName() != null && castOther.getHandlerName() != null && this.getHandlerName().equals(castOther.getHandlerName())))
				&& ((this.getDefLossDate() == castOther.getDefLossDate()) || (this.getDefLossDate() != null && castOther.getDefLossDate() != null && this.getDefLossDate().equals(castOther.getDefLossDate())))
				&& ((this.getUnderWriteCode() == castOther.getUnderWriteCode()) || (this.getUnderWriteCode() != null && castOther.getUnderWriteCode() != null && this.getUnderWriteCode().equals(castOther.getUnderWriteCode())))
				&& ((this.getUnderWriteName() == castOther.getUnderWriteName()) || (this.getUnderWriteName() != null && castOther.getUnderWriteName() != null && this.getUnderWriteName().equals(castOther.getUnderWriteName())))
				&& ((this.getUnderWriteEndDate() == castOther.getUnderWriteEndDate()) || (this.getUnderWriteEndDate() != null && castOther.getUnderWriteEndDate() != null && this.getUnderWriteEndDate().equals(castOther.getUnderWriteEndDate())))
				&& ((this.getUnderwriteflag() == castOther.getUnderwriteflag()) || (this.getUnderwriteflag() != null && castOther.getUnderwriteflag() != null && this.getUnderwriteflag().equals(castOther.getUnderwriteflag())))
				&& ((this.getNodeType() == castOther.getNodeType()) || (this.getNodeType() != null && castOther.getNodeType() != null && this.getNodeType().equals(castOther.getNodeType())))
				&& ((this.getRemark() == castOther.getRemark()) || (this.getRemark() != null && castOther.getRemark() != null && this.getRemark().equals(castOther.getRemark())))
				&& ((this.getVerifyRemark() == castOther.getVerifyRemark()) || (this.getVerifyRemark() != null && castOther.getVerifyRemark() != null && this.getVerifyRemark().equals(castOther.getVerifyRemark())))
				&& ((this.getFlag() == castOther.getFlag()) || (this.getFlag() != null && castOther.getFlag() != null && this.getFlag().equals(castOther.getFlag())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistno() == null ? 0 : this.getRegistno().hashCode());
		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getLossType() == null ? 0 : this.getLossType().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getInsuredName() == null ? 0 : this.getInsuredName().hashCode());
		result = 37 * result + (getLicenseNo() == null ? 0 : this.getLicenseNo().hashCode());
		result = 37 * result + (getLicenseColorCode() == null ? 0 : this.getLicenseColorCode().hashCode());
		result = 37 * result + (getCarKindCode() == null ? 0 : this.getCarKindCode().hashCode());
		result = 37 * result + (getCurrency() == null ? 0 : this.getCurrency().hashCode());
		result = 37 * result + (getMakeCom() == null ? 0 : this.getMakeCom().hashCode());
		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getHandlerCode() == null ? 0 : this.getHandlerCode().hashCode());
		result = 37 * result + (getHandlerName() == null ? 0 : this.getHandlerName().hashCode());
		result = 37 * result + (getDefLossDate() == null ? 0 : this.getDefLossDate().hashCode());
		result = 37 * result + (getUnderWriteCode() == null ? 0 : this.getUnderWriteCode().hashCode());
		result = 37 * result + (getUnderWriteName() == null ? 0 : this.getUnderWriteName().hashCode());
		result = 37 * result + (getUnderWriteEndDate() == null ? 0 : this.getUnderWriteEndDate().hashCode());
		result = 37 * result + (getUnderwriteflag() == null ? 0 : this.getUnderwriteflag().hashCode());
		result = 37 * result + (getNodeType() == null ? 0 : this.getNodeType().hashCode());
		result = 37 * result + (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result + (getVerifyRemark() == null ? 0 : this.getVerifyRemark().hashCode());
		result = 37 * result + (getFlag() == null ? 0 : this.getFlag().hashCode());
		return result;
	}

}
