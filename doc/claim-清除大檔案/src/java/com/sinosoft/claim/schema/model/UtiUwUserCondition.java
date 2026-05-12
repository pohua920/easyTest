package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类UtiUwUserCondition双核人员条件设置表
 */
@Entity
@Table(name = "UTIUWUSERCONDITION")
public class UtiUwUserCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private UtiUwUserConditionId id;

	/** 属性险种大类代码 */
	private String riskCategoryCode;

	/** 属性险类代码 */
	private String classCode;

	/** 属性因子取值 */
	private String factorValue;

	/** 属性备注 */
	private String remark;

	/** 属性创建时间 */
	private String createTime;

	/** 属性有效标志 */
	private String validStatus;

	/** 属性CODETYPEVALUE */
	private String codeTypeValue;

	/**
	 * 类UtiUwUserCondition的默认构造方法
	 */
	public UtiUwUserCondition() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "nodeNo", column = @Column(name = "NODENO")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "uwType", column = @Column(name = "UWTYPE")), @AttributeOverride(name = "factorCode", column = @Column(name = "FACTORCODE")),
			@AttributeOverride(name = "factorValueNo", column = @Column(name = "FACTORVALUENO")), @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE")) })
	public UtiUwUserConditionId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(UtiUwUserConditionId id) {
		this.id = id;
	}

	/**
	 * 属性RISKCATEGORYCODE的getter方法
	 */

	@Column(name = "RISKCATEGORYCODE")
	public String getRiskCategoryCode() {
		return this.riskCategoryCode;
	}

	/**
	 * 属性RISKCATEGORYCODE的setter方法
	 */
	public void setRiskCategoryCode(String riskCategoryCode) {
		this.riskCategoryCode = riskCategoryCode;
	}

	/**
	 * 属性CLASSCODE的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性CLASSCODE的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性FACTORVALUE的getter方法
	 */

	@Column(name = "FACTORVALUE")
	public String getFactorValue() {
		return this.factorValue;
	}

	/**
	 * 属性FACTORVALUE的setter方法
	 */
	public void setFactorValue(String factorValue) {
		this.factorValue = factorValue;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性CREATETIME的getter方法
	 */

	@Column(name = "CREATETIME")
	public String getCreateTime() {
		return this.createTime;
	}

	/**
	 * 属性CREATETIME的setter方法
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 属性VALIDSTATUS的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性VALIDSTATUS的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性CODETYPEVALUE的getter方法
	 */

	@Column(name = "CODETYPEVALUE")
	public String getCodeTypeValue() {
		return this.codeTypeValue;
	}

	/**
	 * 属性CODETYPEVALUE的setter方法
	 */
	public void setCodeTypeValue(String codeTypeValue) {
		this.codeTypeValue = codeTypeValue;
	}

}
