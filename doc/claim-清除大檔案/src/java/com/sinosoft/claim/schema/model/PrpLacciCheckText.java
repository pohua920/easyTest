package com.sinosoft.claim.schema.model;

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
 * POJO类PrpLacciCheckText
 */
@Entity
@Table(name = "PRPLACCICHECKTEXT")
public class PrpLacciCheckText implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLacciCheckTextId id;

	/** 属性意健险调查主表 */
	private PrpLacciCheck prpLacciCheck;

	/** 属性文字说明 */
	private String context;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLacciCheckText的默认构造方法
	 */
	public PrpLacciCheckText() {
		this.id = new PrpLacciCheckTextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "checkNo", column = @Column(name = "CHECKNO")), @AttributeOverride(name = "textType", column = @Column(name = "TEXTTYPE")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLacciCheckTextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLacciCheckTextId id) {
		this.id = id;
	}

	/**
	 * 属性意健险调查主表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKNO", nullable = false, insertable = false, updatable = false)
	public PrpLacciCheck getPrpLacciCheck() {
		return this.prpLacciCheck;
	}

	/**
	 * 属性意健险调查主表的setter方法
	 */
	public void setPrpLacciCheck(PrpLacciCheck prpLacciCheck) {
		this.prpLacciCheck = prpLacciCheck;
	}

	/**
	 * 属性文字说明的getter方法
	 */

	@Column(name = "CONTEXT")
	public String getContext() {
		return this.context;
	}

	/**
	 * 属性文字说明的setter方法
	 */
	public void setContext(String context) {
		this.context = context;
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
