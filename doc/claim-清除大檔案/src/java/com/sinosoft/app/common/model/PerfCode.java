package com.sinosoft.app.common.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO类perfCode
 */
@Entity
@Table(name = "PERF_CODE")
public class PerfCode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PerfCodeId id;

	/** 属性代码类型表 */
	private PerfType perfType;

	/** 属性中文名称 */
	private String codeCName;

	/** 属性英文名称 */
	private String codeEName;

	/** 属性显示序号 */
	private Integer displayNo;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性预留标志 */
	private String flag;

	/**
	 * 类perfCode的默认构造方法
	 */
	public PerfCode() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE")), @AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE")) })
	public PerfCodeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PerfCodeId id) {
		this.id = id;
	}

	/**
	 * 属性代码类型表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODETYPE", nullable = false, insertable = false, updatable = false)
	public PerfType getPerfType() {
		return this.perfType;
	}

	/**
	 * 属性代码类型表的setter方法
	 */
	public void setPerfType(PerfType perfType) {
		this.perfType = perfType;
	}

	/**
	 * 属性中文名称的getter方法
	 */

	@Column(name = "CODECNAME")
	public String getCodeCName() {
		return this.codeCName;
	}

	/**
	 * 属性中文名称的setter方法
	 */
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}

	/**
	 * 属性英文名称的getter方法
	 */

	@Column(name = "CODEENAME")
	public String getCodeEName() {
		return this.codeEName;
	}

	/**
	 * 属性英文名称的setter方法
	 */
	public void setCodeEName(String codeEName) {
		this.codeEName = codeEName;
	}

	/**
	 * 属性显示序号的getter方法
	 */

	@Column(name = "DISPLAYNO")
	public Integer getDisplayNo() {
		return this.displayNo;
	}

	/**
	 * 属性显示序号的setter方法
	 */
	public void setDisplayNo(Integer displayNo) {
		this.displayNo = displayNo;
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
