package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLqualityCheck质量评审内容表
 */
@Entity
@Table(name = "PRPLQUALITYCHECK")
public class PrpLqualityCheck implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLqualityCheckId id;

	/** 属性单证类型代码 */
	private String typeCode;

	/** 属性单证类型名称 */
	private String typeName;

	/** 属性回访结果 */
	private String checkResult;

	/** 属性问题备注 */
	private String checkRemark;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLqualityCheck的默认构造方法
	 */
	public PrpLqualityCheck() {
		this.id = new PrpLqualityCheckId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "qualityCheckType", column = @Column(name = "QUALITYCHECKTYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLqualityCheckId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLqualityCheckId id) {
		this.id = id;
	}

	/**
	 * 属性单证类型代码的getter方法
	 */

	@Column(name = "TYPECODE")
	public String getTypeCode() {
		return this.typeCode;
	}

	/**
	 * 属性单证类型代码的setter方法
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 属性单证类型名称的getter方法
	 */

	@Column(name = "TYPENAME")
	public String getTypeName() {
		return this.typeName;
	}

	/**
	 * 属性单证类型名称的setter方法
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 属性回访结果的getter方法
	 */

	@Column(name = "CHECKRESULT")
	public String getCheckResult() {
		return this.checkResult;
	}

	/**
	 * 属性回访结果的setter方法
	 */
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	/**
	 * 属性问题备注的getter方法
	 */

	@Column(name = "CHECKREMARK")
	public String getCheckRemark() {
		return this.checkRemark;
	}

	/**
	 * 属性问题备注的setter方法
	 */
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
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

}
