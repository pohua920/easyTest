package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpMaxUse
 */
@Entity
@Table(name = "PRPMAXUSE")
public class PrpMaxUse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpMaxUseId id;

	/** 属性终端号 */
	private String ttyCode;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpMaxUse的默认构造方法
	 */
	public PrpMaxUse() {
		this.id = new PrpMaxUseId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "groupNo", column = @Column(name = "GROUPNO")), @AttributeOverride(name = "tableName", column = @Column(name = "TABLENAME")), @AttributeOverride(name = "maxNo", column = @Column(name = "MAXNO")) })
	public PrpMaxUseId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpMaxUseId id) {
		this.id = id;
	}

	/**
	 * 属性终端号的getter方法
	 */

	@Column(name = "TTYCODE")
	public String getTtyCode() {
		return this.ttyCode;
	}

	/**
	 * 属性终端号的setter方法
	 */
	public void setTtyCode(String ttyCode) {
		this.ttyCode = ttyCode;
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
