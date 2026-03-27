package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLbackVisitText
 */
@Entity
@Table(name = "PRPLBACKVISITTEXT")
public class PrpLbackVisitText implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLbackVisitTextId id;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性反馈/回访内容 */
	private String content;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLbackVisitText的默认构造方法
	 */
	public PrpLbackVisitText() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "backVisitID", column = @Column(name = "BACKVISITID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "backVisitType", column = @Column(name = "BACKVISITTYPE")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLbackVisitTextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLbackVisitTextId id) {
		this.id = id;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性反馈/回访内容的getter方法
	 */

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	/**
	 * 属性反馈/回访内容的setter方法
	 */
	public void setContent(String content) {
		this.content = content;
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
