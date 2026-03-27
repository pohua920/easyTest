package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLptext预赔文字表
 */
@Entity
@Table(name = "PRPLPTEXT")
public class PrpLptext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLptextId id;

	/** 属性文字说明 */
	private String context;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLptext的默认构造方法
	 */
	public PrpLptext() {
		id = new PrpLptextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "preCompensateNo", column = @Column(name = "PRECOMPENSATENO")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLptextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLptextId id) {
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
