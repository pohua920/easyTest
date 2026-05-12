package com.sinosoft.claim.schema.model;

// default package
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
 * POJO类PrpPtext
 */
@Entity
@Table(name = "PRPPTEXT")
public class PrpPtext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpPtextId id;

	/** 属性批改信息表 */
	private PrpPhead prpPhead;

	/** 属性批文内容 */
	private String endorseText;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpPtext的默认构造方法
	 */
	public PrpPtext() {
		id = new PrpPtextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpPtextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpPtextId id) {
		this.id = id;
	}

	/**
	 * 属性批改信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDORSENO", nullable = false, insertable = false, updatable = false)
	public PrpPhead getPrpPhead() {
		return this.prpPhead;
	}

	/**
	 * 属性批改信息表的setter方法
	 */
	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
	}

	/**
	 * 属性批文内容的getter方法
	 */

	@Column(name = "ENDORSETEXT")
	public String getEndorseText() {
		return this.endorseText;
	}

	/**
	 * 属性批文内容的setter方法
	 */
	public void setEndorseText(String endorseText) {
		this.endorseText = endorseText;
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
