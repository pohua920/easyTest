package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类Prplexcludeclaim
 */
@Entity
@Table(name = "PRPLEXCLUDECLAIM")
public class Prplexcludeclaim implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性REGISTNO */
	private String registNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorname;

	/** 属性归属机构 */
	private String comCode;

	/** 属性归属机构名称 */
	private String comname;

	/** 属性立案除外原因 */
	private String excludereason;

	/** 属性标志 */
	private String flag;

	/**
	 * 类Prplexcludeclaim的默认构造方法
	 */
	public Prplexcludeclaim() {
	}

	/**
	 * 属性REGISTNO的getter方法
	 */
	@Id
	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
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
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorname() {
		return this.operatorname;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
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
	 * 属性归属机构名称的getter方法
	 */

	@Column(name = "COMNAME")
	public String getComname() {
		return this.comname;
	}

	/**
	 * 属性归属机构名称的setter方法
	 */
	public void setComname(String comname) {
		this.comname = comname;
	}

	/**
	 * 属性立案除外原因的getter方法
	 */

	@Column(name = "EXCLUDEREASON")
	public String getExcludereason() {
		return this.excludereason;
	}

	/**
	 * 属性立案除外原因的setter方法
	 */
	public void setExcludereason(String excludereason) {
		this.excludereason = excludereason;
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
