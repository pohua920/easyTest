package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpDclauseKind
 */
@Entity
@Table(name = "PRPDCLAUSEKIND")
public class PrpDclauseKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDclauseKindId id;

	/** 属性标志位 */
	private String flag;

	/**
	 * 类PrpDclauseKind的默认构造方法
	 */
	public PrpDclauseKind() {
		id = new PrpDclauseKindId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "clauseType", column = @Column(name = "CLAUSETYPE")),
			@AttributeOverride(name = "kindCode", column = @Column(name = "KINDCODE")), @AttributeOverride(name = "relateKindCode", column = @Column(name = "RELATEKINDCODE")) })
	public PrpDclauseKindId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDclauseKindId id) {
		this.id = id;
	}

	/**
	 * 属性标志位的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志位的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
