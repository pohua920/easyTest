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
import javax.persistence.Transient;

/**
 * POJO类PrpLrecase重开赔案表
 */
@Entity
@Table(name = "PRPLRECASE")
public class PrpLrecase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLrecaseId id;

	/** 属性重开赔案人代码 */
	private String openCaseUserCode;

	/** 属性重开赔案日期 */
	private Date openCaseDate;

	/** 属性本次结案人代码 */
	private String closeCaseUserCode;

	/** 属性本次结案日期 */
	private Date closeCaseDate;

	/** 属性标志字段 */
	private String flag;

	/** 属性RECASEREASON */
	private String reCaseReason;
	/* 重开赔案操作员归属机构代码 */
	private String openCaseComCode = "";

	/* 重开赔案结案时候操作员归属机构代码 */
	private String closeCaseComCode = "";

	/**
	 * 类PrpLrecase的默认构造方法
	 */
	public PrpLrecase() {
		this.id = new PrpLrecaseId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLrecaseId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLrecaseId id) {
		this.id = id;
	}

	/**
	 * 属性重开赔案人代码的getter方法
	 */

	@Column(name = "OPENCASEUSERCODE")
	public String getOpenCaseUserCode() {
		return this.openCaseUserCode;
	}

	/**
	 * 属性重开赔案人代码的setter方法
	 */
	public void setOpenCaseUserCode(String openCaseUserCode) {
		this.openCaseUserCode = openCaseUserCode;
	}

	/**
	 * 属性重开赔案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPENCASEDATE")
	public Date getOpenCaseDate() {
		return this.openCaseDate;
	}

	/**
	 * 属性重开赔案日期的setter方法
	 */
	public void setOpenCaseDate(Date openCaseDate) {
		this.openCaseDate = openCaseDate;
	}

	/**
	 * 属性本次结案人代码的getter方法
	 */

	@Column(name = "CLOSECASEUSERCODE")
	public String getCloseCaseUserCode() {
		return this.closeCaseUserCode;
	}

	/**
	 * 属性本次结案人代码的setter方法
	 */
	public void setCloseCaseUserCode(String closeCaseUserCode) {
		this.closeCaseUserCode = closeCaseUserCode;
	}

	/**
	 * 属性本次结案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CLOSECASEDATE")
	public Date getCloseCaseDate() {
		return this.closeCaseDate;
	}

	/**
	 * 属性本次结案日期的setter方法
	 */
	public void setCloseCaseDate(Date closeCaseDate) {
		this.closeCaseDate = closeCaseDate;
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
	 * 属性RECASEREASON的getter方法
	 */

	@Column(name = "RECASEREASON")
	public String getReCaseReason() {
		return this.reCaseReason;
	}

	/**
	 * 属性RECASEREASON的setter方法
	 */
	public void setReCaseReason(String reCaseReason) {
		this.reCaseReason = reCaseReason;
	}

	@Transient
	public String getOpenCaseComCode() {
		return openCaseComCode;
	}

	public void setOpenCaseComCode(String openCaseComCode) {
		this.openCaseComCode = openCaseComCode;
	}

	@Transient
	public String getCloseCaseComCode() {
		return closeCaseComCode;
	}

	public void setCloseCaseComCode(String closeCaseComCode) {
		this.closeCaseComCode = closeCaseComCode;
	}
}
