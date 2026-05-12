package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLaccount
 */
@Entity
@Table(name = "PRPLACCOUNT")
public class PrpLaccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLaccountId id;

	/** 属性银行帳号 */
	private String accountCode;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性extendString1 */
	private String extendString1;

	/** 属性extendString2 */
	private String extendString2;

	/**
	 * 类PrpLaccount的默认构造方法
	 */
	public PrpLaccount() {
		id = new PrpLaccountId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLaccountId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLaccountId id) {
		this.id = id;
	}

	/**
	 * 属性银行帳号的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性银行帳号的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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

	/**
	 * 属性extendString1的getter方法
	 */

	@Column(name = "EXTENDSTRING1")
	public String getExtendString1() {
		return this.extendString1;
	}

	/**
	 * 属性extendString1的setter方法
	 */
	public void setExtendString1(String extendString1) {
		this.extendString1 = extendString1;
	}

	/**
	 * 属性extendString2的getter方法
	 */

	@Column(name = "EXTENDSTRING2")
	public String getExtendString2() {
		return this.extendString2;
	}

	/**
	 * 属性extendString2的setter方法
	 */
	public void setExtendString2(String extendString2) {
		this.extendString2 = extendString2;
	}

}
