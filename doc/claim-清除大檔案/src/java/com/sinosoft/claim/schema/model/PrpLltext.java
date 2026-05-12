package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLltext立案文字表
 */
@Entity
@Table(name = "PRPLLTEXT")
public class PrpLltext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Collection<PrpLltext> ltextList;
	/** 属性id */
	private PrpLltextId id;

	/** 属性立案基本信息表 */
	private PrpLclaim prpLclaim;

	/** 属性文字说明 */
	private String context;

	/** 属性状态字段 */
	private String flag;

	/**
	 * 类PrpLltext的默认构造方法
	 */
	public PrpLltext() {
		id = new PrpLltextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "textType", column = @Column(name = "TEXTTYPE")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLltextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLltextId id) {
		this.id = id;
	}

	/**
	 * 属性立案基本信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMNO", nullable = false, insertable = false, updatable = false)
	public PrpLclaim getPrpLclaim() {
		return this.prpLclaim;
	}

	/**
	 * 属性立案基本信息表的setter方法
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
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

	@Transient
	public Collection<PrpLltext> getLtextList() {
		return ltextList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setLtextList(Collection<PrpLltext> ltextList) {
		this.ltextList = ltextList;
	}

}
