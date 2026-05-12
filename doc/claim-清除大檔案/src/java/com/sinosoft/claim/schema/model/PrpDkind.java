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
 * POJO类PrpDkind
 */
@Entity
@Table(name = "PRPDKIND")
public class PrpDkind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDkindId id;

	/** 属性险别中文名称 */
	private String kindCName;

	/** 属性险别英文名称 */
	private String kindEName;

	/** 属性计入总保额标志 */
	private String calculateFlag;

	/** 属性新的险别代码 */
	private String newKindCode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDkind的默认构造方法
	 */
	public PrpDkind() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "kindCode", column = @Column(name = "KINDCODE")) })
	public PrpDkindId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDkindId id) {
		this.id = id;
	}

	/**
	 * 属性险别中文名称的getter方法
	 */

	@Column(name = "KINDCNAME")
	public String getKindCName() {
		return this.kindCName;
	}

	/**
	 * 属性险别中文名称的setter方法
	 */
	public void setKindCName(String kindCName) {
		this.kindCName = kindCName;
	}

	/**
	 * 属性险别英文名称的getter方法
	 */

	@Column(name = "KINDENAME")
	public String getKindEName() {
		return this.kindEName;
	}

	/**
	 * 属性险别英文名称的setter方法
	 */
	public void setKindEName(String kindEName) {
		this.kindEName = kindEName;
	}

	/**
	 * 属性计入总保额标志的getter方法
	 */

	@Column(name = "CALCULATEFLAG")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**
	 * 属性计入总保额标志的setter方法
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	/**
	 * 属性新的险别代码的getter方法
	 */

	@Column(name = "NEWKINDCODE")
	public String getNewKindCode() {
		return this.newKindCode;
	}

	/**
	 * 属性新的险别代码的setter方法
	 */
	public void setNewKindCode(String newKindCode) {
		this.newKindCode = newKindCode;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
