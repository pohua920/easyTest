package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpDcurrency
 */
@Entity
@Table(name = "PRPDCURRENCY")
public class PrpDcurrency implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性币别代码 */
	private String currencyCode;

	/** 属性币别中文名称 */
	private String currencyCName;

	/** 属性币别英文名称 */
	private String currencyEName;

	/** 属性最新币别代码 */
	private String newCurrencyCode;

	/** 属性ACCBOOKCODE */
	private String accbookcode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDcurrency的默认构造方法
	 */
	public PrpDcurrency() {
	}

	/**
	 * 属性币别代码的getter方法
	 */
	@Id
	@Column(name = "CURRENCYCODE")
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	/**
	 * 属性币别代码的setter方法
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 属性币别中文名称的getter方法
	 */

	@Column(name = "CURRENCYCNAME")
	public String getCurrencyCName() {
		return this.currencyCName;
	}

	/**
	 * 属性币别中文名称的setter方法
	 */
	public void setCurrencyCName(String currencyCName) {
		this.currencyCName = currencyCName;
	}

	/**
	 * 属性币别英文名称的getter方法
	 */

	@Column(name = "CURRENCYENAME")
	public String getCurrencyEName() {
		return this.currencyEName;
	}

	/**
	 * 属性币别英文名称的setter方法
	 */
	public void setCurrencyEName(String currencyEName) {
		this.currencyEName = currencyEName;
	}

	/**
	 * 属性最新币别代码的getter方法
	 */

	@Column(name = "NEWCURRENCYCODE")
	public String getNewCurrencyCode() {
		return this.newCurrencyCode;
	}

	/**
	 * 属性最新币别代码的setter方法
	 */
	public void setNewCurrencyCode(String newCurrencyCode) {
		this.newCurrencyCode = newCurrencyCode;
	}

	/**
	 * 属性ACCBOOKCODE的getter方法
	 */

	@Column(name = "ACCBOOKCODE")
	public String getAccbookcode() {
		return this.accbookcode;
	}

	/**
	 * 属性ACCBOOKCODE的setter方法
	 */
	public void setAccbookcode(String accbookcode) {
		this.accbookcode = accbookcode;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
