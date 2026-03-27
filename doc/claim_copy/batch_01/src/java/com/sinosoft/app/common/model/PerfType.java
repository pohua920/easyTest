package com.sinosoft.app.common.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO类perfType
 */
@Entity
@Table(name = "PERF_TYPE")
public class PerfType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性代码类型 */
	private String codeType;

	/** 属性代码类型描述 */
	private String codeTypeDesc;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性预留标志 */
	private String flag;

	/** 属性perfCodes */
	private List<PerfCode> perfCodes = new ArrayList<PerfCode>(0);

	/**
	 * 类perfType的默认构造方法
	 */
	public PerfType() {
	}

	/**
	 * 属性代码类型的getter方法
	 */
	@Id
	@Column(name = "CODETYPE")
	public String getCodeType() {
		return this.codeType;
	}

	/**
	 * 属性代码类型的setter方法
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 属性代码类型描述的getter方法
	 */

	@Column(name = "CODETYPEDESC")
	public String getCodeTypeDesc() {
		return this.codeTypeDesc;
	}

	/**
	 * 属性代码类型描述的setter方法
	 */
	public void setCodeTypeDesc(String codeTypeDesc) {
		this.codeTypeDesc = codeTypeDesc;
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

	/**
	 * 属性perfCodes的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "perfType")
	public List<PerfCode> getPerfCodes() {
		return this.perfCodes;
	}

	/**
	 * 属性perfCodes的setter方法
	 */
	public void setPerfCodes(List<PerfCode> perfCodes) {
		this.perfCodes = perfCodes;
	}

}
