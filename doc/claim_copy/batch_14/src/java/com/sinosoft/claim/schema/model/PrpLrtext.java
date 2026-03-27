package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLrtext追偿损余文字说明
 */
@Entity
@Table(name = "PRPLRTEXT")
public class PrpLrtext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLrtextId id;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性文字说明 */
	private String context;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLrtext的默认构造方法
	 */
	public PrpLrtext() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "textType", column = @Column(name = "TEXTTYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLrtextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLrtextId id) {
		this.id = id;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
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
