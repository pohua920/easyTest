package com.sinosoft.app.perf.hr.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类HrCompany
 */
@Entity
@Table(name = "JX_COMPANY")
public class HrCompany implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性compId */
	private String compId;

	/** 属性compCName */
	private String compCName;

	/** 属性adminId */
	private String adminId;

	/** 属性orgGrade */
	private String orgGrade;

	/**
	 * 类HrCompany的默认构造方法
	 */
	public HrCompany() {
	}

	/**
	 * 属性compId的getter方法
	 */
	@Id
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
	 * 属性compCName的getter方法
	 */

	@Column(name = "COMPCNAME")
	public String getCompCName() {
		return this.compCName;
	}

	/**
	 * 属性compCName的setter方法
	 */
	public void setCompCName(String compCName) {
		this.compCName = compCName;
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
	 * 属性orgGrade的getter方法
	 */

	@Column(name = "ORGGRADE")
	public String getOrgGrade() {
		return this.orgGrade;
	}

	/**
	 * 属性orgGrade的setter方法
	 */
	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

}
