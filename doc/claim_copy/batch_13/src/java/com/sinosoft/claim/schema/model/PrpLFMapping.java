package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLFMapping
 */
@Entity
@Table(name = "PRPLFMAPPING")
public class PrpLFMapping implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLFMappingId id;

	/** 属性费用类型名称 */
	private String chargeName;

	/** 属性收付原因名称 */
	private String payRefReasonName;

	/** 属性标志字段 */
	private String flag;

	/** 属性说明 */
	private String remark;

	/**
	 * 类PrpLFMapping的默认构造方法
	 */
	public PrpLFMapping() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "chargeCode", column = @Column(name = "CHARGECODE")), @AttributeOverride(name = "payRefReason", column = @Column(name = "PAYREFREASON")) })
	public PrpLFMappingId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLFMappingId id) {
		this.id = id;
	}

	/**
	 * 属性费用类型名称的getter方法
	 */

	@Column(name = "CHARGENAME")
	public String getChargeName() {
		return this.chargeName;
	}

	/**
	 * 属性费用类型名称的setter方法
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * 属性收付原因名称的getter方法
	 */

	@Column(name = "PAYREFREASONNAME")
	public String getPayRefReasonName() {
		return this.payRefReasonName;
	}

	/**
	 * 属性收付原因名称的setter方法
	 */
	public void setPayRefReasonName(String payRefReasonName) {
		this.payRefReasonName = payRefReasonName;
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

	/**
	 * 属性说明的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性说明的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
