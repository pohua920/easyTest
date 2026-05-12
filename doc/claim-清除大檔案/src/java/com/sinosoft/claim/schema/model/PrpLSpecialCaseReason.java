package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLSpecialCaseReason特殊赔案申请原因表
 */
@Entity
@Table(name = "PRPLSPECIALCASEREASON")
public class PrpLSpecialCaseReason implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLSpecialCaseReasonId id;

	/** 属性特殊赔案名称 */
	private String specialName;

	/** 属性申请原因 */
	private String reason;

	/**
	 * 类PrpLSpecialCaseReason的默认构造方法
	 */
	public PrpLSpecialCaseReason() {
		id = new PrpLSpecialCaseReasonId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")) })
	public PrpLSpecialCaseReasonId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLSpecialCaseReasonId id) {
		this.id = id;
	}

	/**
	 * 属性特殊赔案名称的getter方法
	 */

	@Column(name = "SPECIALNAME")
	public String getSpecialName() {
		return this.specialName;
	}

	/**
	 * 属性特殊赔案名称的setter方法
	 */
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	/**
	 * 属性申请原因的getter方法
	 */

	@Column(name = "REASON")
	public String getReason() {
		return this.reason;
	}

	/**
	 * 属性申请原因的setter方法
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
