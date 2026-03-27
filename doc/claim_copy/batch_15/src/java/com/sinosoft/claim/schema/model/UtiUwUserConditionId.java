package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类UtiUwUserConditionId
 */
@Embeddable
public class UtiUwUserConditionId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性审核部门 */
	private String comCode;

	/** 属性模板编号 */
	private Integer modelNo;

	/** 属性节点编号 */
	private Integer nodeNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性审核类型 */
	private String uwType;

	/** 属性因子代码 */
	private String factorCode;

	/** 属性因子值序号 */
	private Integer factorValueNo;

	/** 属性人员代码 */
	private String userCode;

	/**
	 * 类UtiUwUserConditionId的默认构造方法
	 */
	public UtiUwUserConditionId() {
	}

	/**
	 * 属性COMCODE的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性COMCODE的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性MODELNO的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性MODELNO的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性NODENO的getter方法
	 */

	@Column(name = "NODENO")
	public Integer getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性NODENO的setter方法
	 */
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性RISKCODE的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性RISKCODE的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性UWTYPE的getter方法
	 */

	@Column(name = "UWTYPE")
	public String getUwType() {
		return this.uwType;
	}

	/**
	 * 属性UWTYPE的setter方法
	 */
	public void setUwType(String uwType) {
		this.uwType = uwType;
	}

	/**
	 * 属性FACTORCODE的getter方法
	 */

	@Column(name = "FACTORCODE")
	public String getFactorCode() {
		return this.factorCode;
	}

	/**
	 * 属性FACTORCODE的setter方法
	 */
	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
	}

	/**
	 * 属性FACTORVALUENO的getter方法
	 */

	@Column(name = "FACTORVALUENO")
	public Integer getFactorValueNo() {
		return this.factorValueNo;
	}

	/**
	 * 属性FACTORVALUENO的setter方法
	 */
	public void setFactorValueNo(Integer factorValueNo) {
		this.factorValueNo = factorValueNo;
	}

	/**
	 * 属性USERCODE的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性USERCODE的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof UtiUwUserConditionId)) {
			return false;
		}
		UtiUwUserConditionId castOther = (UtiUwUserConditionId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getModelNo() == castOther.getModelNo()) || (this.getModelNo() != null && castOther.getModelNo() != null && this.getModelNo().equals(castOther.getModelNo())))
				&& ((this.getNodeNo() == castOther.getNodeNo()) || (this.getNodeNo() != null && castOther.getNodeNo() != null && this.getNodeNo().equals(castOther.getNodeNo())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getUwType() == castOther.getUwType()) || (this.getUwType() != null && castOther.getUwType() != null && this.getUwType().equals(castOther.getUwType())))
				&& ((this.getFactorCode() == castOther.getFactorCode()) || (this.getFactorCode() != null && castOther.getFactorCode() != null && this.getFactorCode().equals(castOther.getFactorCode())))
				&& ((this.getFactorValueNo() == castOther.getFactorValueNo()) || (this.getFactorValueNo() != null && castOther.getFactorValueNo() != null && this.getFactorValueNo().equals(castOther.getFactorValueNo())))
				&& ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(castOther.getUserCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		result = 37 * result + (getNodeNo() == null ? 0 : this.getNodeNo().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getUwType() == null ? 0 : this.getUwType().hashCode());
		result = 37 * result + (getFactorCode() == null ? 0 : this.getFactorCode().hashCode());
		result = 37 * result + (getFactorValueNo() == null ? 0 : this.getFactorValueNo().hashCode());
		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		return result;
	}

}
