package com.sinosoft.app.perf.hr.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类HrUser
 */
@Entity
@Table(name = "JX_EMPLOYEE")
public class HrUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性badge */
	private String badge;

	/** 属性name */
	private String name;

	/** 属性szdepid */
	private String szdepid;

	/** 属性depid */
	private String depid;

	/** 属性邮箱 */
	private String email;

	/** 属性extension */
	private String extension;

	/** 属性mobile */
	private String mobile;

	/**
	 * 类HrUser的默认构造方法
	 */
	public HrUser() {
	}

	/**
	 * 属性badge的getter方法
	 */
	@Id
	@Column(name = "BADGE")
	public String getBadge() {
		return this.badge;
	}

	/**
	 * 属性badge的setter方法
	 */
	public void setBadge(String badge) {
		this.badge = badge;
	}

	/**
	 * 属性name的getter方法
	 */

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	/**
	 * 属性name的setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}

	// /**
	// * 属性compid的getter方法
	// */
	//
	// @Column(name = "COMPID")
	// public String getCompId() {
	// return this.compId;
	// }
	//
	// /**
	// * 属性compid的setter方法
	// */
	// public void setCompId(String compId) {
	// this.compId = compId;
	// }
	/**
	 * 属性szdepId的setter方法
	 */
	public void setSzdepid(String szdepid) {
		this.szdepid = szdepid;
	}

	/**
	 * 属性szdepId的getter方法
	 */
	@Column(name = "SZDEPID")
	public String getSzdepid() {
		return this.szdepid;
	}

	/**
	 * 属性邮箱的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性邮箱的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性extension的getter方法
	 */

	@Column(name = "EXTENSION")
	public String getExtension() {
		return this.extension;
	}

	/**
	 * 属性extension的setter方法
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 属性mobile的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性mobile的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性depId的setter方法
	 */
	public void setDepid(String depid) {
		this.depid = depid;
	}

	/**
	 * 属性szdepId的getter方法
	 */
	@Column(name = "DEPID")
	public String getDepid() {
		return depid;
	}

}
