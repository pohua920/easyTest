package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLregistText报案文字表
 */
@Entity
@Table(name = "PRPLREGISTTEXT")
public class PrpLregistText implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLregistTextId id;

	/** 属性文字说明 */
	private String context;

	/** 属性状态字段 */
	private String flag;
	/** 属性显示列表 */
	private Collection<?> registTextList;

	/**
	 * 类PrpLregistText的默认构造方法
	 */
	public PrpLregistText() {
		id = new PrpLregistTextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "textType", column = @Column(name = "TEXTTYPE")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLregistTextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLregistTextId id) {
		this.id = id;
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
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<?> getRegistTextList() {
		return registTextList;
	}

	/**
	 * 设置列表
	 * @param registTextList 待设置的列表
	 */
	public void setRegistTextList(Collection<?> registTextList) {
		this.registTextList = registTextList;
	}

}
