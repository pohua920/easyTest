package com.sinosoft.app.common.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PerfcodeTransfer
 */
@Entity
@Table(name = "PERF_CODETRANSFER")
public class PerfCodeTransfer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PerfCodeTransferId id;

	/** 属性代码 */
	private String toCode;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性备注 */
	private String remark;

	/** 属性预留标志 */
	private String flag;

	/**
	 * 类PerfcodeTransfer的默认构造方法
	 */
	public PerfCodeTransfer() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "transferId", column = @Column(name = "TRANSFERID")), @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE")) })
	public PerfCodeTransferId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PerfCodeTransferId id) {
		this.id = id;
	}

	/**
	 * 属性代码的getter方法
	 */

	@Column(name = "TOCODE")
	public String getToCode() {
		return this.toCode;
	}

	/**
	 * 属性代码的setter方法
	 */
	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
	 * 属性预留标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性预留标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
