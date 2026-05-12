package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类Prplregistrpolicy赔案保单关联表
 */
@Entity
@Table(name = "PRPLREGISTRPOLICY")
public class Prplregistrpolicy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrplregistrpolicyId id;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性保单类型 */
	private String policyType;

	/** 属性流程编号 */
	private String flowID;

	/** 属性备注 */
	private String remark;

	/** 属性保单有效标志 */
	private String validStatus;

	/** 属性状态字段 */
	private String flag;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性报案标记 */
	private String registFlag;

	/**
	 * 商业保单
	 */
	public static final String BUSINESS_POLICY = "1";
	/**
	 * 强制保单
	 */
	public static final String COMPEL_POLICY = "3";

	/**
	 * 类Prplregistrpolicy的默认构造方法
	 */
	public Prplregistrpolicy() {
		id = new PrplregistrpolicyId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")) })
	public PrplregistrpolicyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrplregistrpolicyId id) {
		this.id = id;
	}

	/**
	 * 属性立案号码的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性保单类型的getter方法
	 */

	@Column(name = "POLICYTYPE")
	public String getPolicyType() {
		return this.policyType;
	}

	/**
	 * 属性保单类型的setter方法
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * 属性流程编号的getter方法
	 */

	@Column(name = "FLOWID")
	public String getFlowID() {
		return this.flowID;
	}

	/**
	 * 属性流程编号的setter方法
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
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
	 * 属性保单有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性保单有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
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
	 * 属性报案标记的getter方法
	 */

	@Column(name = "REGISTFLAG")
	public String getRegistFlag() {
		return this.registFlag;
	}

	/**
	 * 属性报案标记的setter方法
	 */
	public void setRegistFlag(String registFlag) {
		this.registFlag = registFlag;
	}

}
