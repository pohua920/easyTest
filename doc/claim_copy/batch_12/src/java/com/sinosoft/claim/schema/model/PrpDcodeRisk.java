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
 * POJO类PrpDcodeRisk
 */
@Entity
@Table(name = "PRPDCODERISK")
public class PrpDcodeRisk implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDcodeRiskId id;

	/**
	 * 类PrpDcodeRisk的默认构造方法
	 */
	public PrpDcodeRisk() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE")), @AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")) })
	public PrpDcodeRiskId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDcodeRiskId id) {
		this.id = id;
	}

}
