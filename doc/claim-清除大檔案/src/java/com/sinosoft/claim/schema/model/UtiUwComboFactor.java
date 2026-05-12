package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类UtiUwComboFactor
 */
@Entity
@Table(name = "UTIUWCOMBOFACTOR")
public class UtiUwComboFactor implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private UtiUwComboFactorId id;

	/** 属性位置序号 */
	private Integer serialNo;

	/** 属性类型名称 */
	private String typeName;

	/** 属性有效标志 */
	private String validStatus;

	/** 属性标志 */
	private String flag;

	/**
	 * 类UtiUwComboFactor的默认构造方法
	 */
	public UtiUwComboFactor() {
		id = new UtiUwComboFactorId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "uwType", column = @Column(name = "UWTYPE")), @AttributeOverride(name = "classCode", column = @Column(name = "CLASSCODE")),
			@AttributeOverride(name = "factorCode", column = @Column(name = "FACTORCODE")), @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE")) })
	public UtiUwComboFactorId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(UtiUwComboFactorId id) {
		this.id = id;
	}

	/**
	 * 属性位置序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性位置序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性类型名称的getter方法
	 */

	@Column(name = "TYPENAME")
	public String getTypeName() {
		return this.typeName;
	}

	/**
	 * 属性类型名称的setter方法
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 属性有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
