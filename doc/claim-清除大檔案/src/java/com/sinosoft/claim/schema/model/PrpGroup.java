package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpGroup
 */
@Entity
@Table(name = "PRPGROUP")
public class PrpGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpGroupId id;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpGroup的默认构造方法
	 */
	public PrpGroup() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "groupNo", column = @Column(name = "GROUPNO")), @AttributeOverride(name = "subGroupNo", column = @Column(name = "SUBGROUPNO")) })
	public PrpGroupId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpGroupId id) {
		this.id = id;
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
