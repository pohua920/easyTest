package com.sinosoft.app.perf.hr.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类JxDepartment
 */
@Entity
@Table(name = "JX_DEPARTMENT")
public class HrDepartment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性depId */
	private String depId;

	/** 属性depCName */
	private String depCName;

	/** 属性depType */
	private String depType;

	/** 属性szdepId */
	private String szdepId;

	/** 属性adminId */
	private String adminId;

	/** 属性compId */
	private String compId;

	/** 属性isDisable */
	private String isDisable;

	/**
	 * 类JxDepartment的默认构造方法
	 */
	public HrDepartment() {
	}

	/**
	 * 属性depId的getter方法
	 */
	@Id
	@Column(name = "DEPID")
	public String getDepId() {
		return this.depId;
	}

	/**
	 * 属性depId的setter方法
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * 属性depCName的getter方法
	 */

	@Column(name = "DEPCNAME")
	public String getDepCName() {
		return this.depCName;
	}

	/**
	 * 属性depCName的setter方法
	 */
	public void setDepCName(String depCName) {
		this.depCName = depCName;
	}

	/**
	 * 属性depType的getter方法
	 */

	@Column(name = "DEPTYPE")
	public String getDepType() {
		return this.depType;
	}

	/**
	 * 属性depType的setter方法
	 */
	public void setDepType(String depType) {
		this.depType = depType;
	}

	/**
	 * 属性szdepId的getter方法
	 */

	@Column(name = "SZDEPID")
	public String getSzdepId() {
		return this.szdepId;
	}

	/**
	 * 属性szdepId的setter方法
	 */
	public void setSzdepId(String szdepId) {
		this.szdepId = szdepId;
	}

	/**
	 * 属性adminId的getter方法
	 */

	@Column(name = "ADMINID")
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 属性adminId的setter方法
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 属性compId的getter方法
	 */

	@Column(name = "COMPID")
	public String getCompId() {
		return this.compId;
	}

	/**
	 * 属性compId的setter方法
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}

	/**
	 * 属性isDisable的getter方法
	 */

	@Column(name = "ISDISABLE")
	public String getIsDisable() {
		return this.isDisable;
	}

	/**
	 * 属性isDisable的setter方法
	 */
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

}
