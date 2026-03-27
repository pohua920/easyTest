package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpDuser
 */
@Entity
@Table(name = "PRPDUSER")
public class PrpDuser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性员工代码 */
	private String userCode;

	/** 属性员工名称 */
	private String userName;

	/** 属性员工名称E */
	private String userEName;

	/** 属性密码 */
	private String password;

	/** 属性印鉴 */
	private String seal;

	/** 属性密码设置日期 */
	private Date passwordSetDate;

	/** 属性密码过期日期 */
	private Date passwordExpireDate;

	/** 属性归属机构代码 */
	private String comCode;

	/** 属性出单机构代码 */
	private String makeCom;

	/** 属性帳号代码 */
	private String accountCode;

	/** 属性电话号码 */
	private String phone;

	/** 属性手机号码 */
	private String mobile;

	/** 属性通信地址 */
	private String address;

	/** 属性邮政编码 */
	private String postCode;

	/** 属性邮箱 */
	private String email;

	/** 属性员工标志 */
	private String userFlag;

	/** 属性允许登录的应用系统 */
	private String loginSystem;

	/** 属性最新员工代码 */
	private String newUserCode;

	/** 属性效力状态 */
	private String validStatus;

	/** 属性专项代码 */
	private String articleCode;

	/** 属性标志字段 */
	private String flag;

	/** 属性maxoverduecount */
	private Integer maxoverduecount;

	/** 属性maxoverduefee */
	private BigDecimal maxoverduefee;

	/** 属性USERNATURE */
	private String userNature;

	/** 属性USERLEVEL */
	private String userLevel;

	/** 属性USERTYPE */
	private String userType;

	/** 属性ISSALES */
	private String isSales;

	/** 属性LOCKED */
	private boolean locked;

	private String comName;

	/**
	 * 类PrpDuser的默认构造方法
	 */
	public PrpDuser() {
	}

	/**
	 * 属性员工代码的getter方法
	 */
	@Id
	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性员工代码的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 属性员工名称的getter方法
	 */

	@Column(name = "USERNAME")
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 属性员工名称的setter方法
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 属性员工名称E的getter方法
	 */

	@Column(name = "USERENAME")
	public String getUserEName() {
		return this.userEName;
	}

	/**
	 * 属性员工名称E的setter方法
	 */
	public void setUserEName(String userEName) {
		this.userEName = userEName;
	}

	/**
	 * 属性密码的getter方法
	 */

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	/**
	 * 属性密码的setter方法
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 属性印鉴的getter方法
	 */

	@Column(name = "SEAL")
	public String getSeal() {
		return this.seal;
	}

	/**
	 * 属性印鉴的setter方法
	 */
	public void setSeal(String seal) {
		this.seal = seal;
	}

	/**
	 * 属性密码设置日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PASSWORDSETDATE")
	public Date getPasswordSetDate() {
		return this.passwordSetDate;
	}

	/**
	 * 属性密码设置日期的setter方法
	 */
	public void setPasswordSetDate(Date passwordSetDate) {
		this.passwordSetDate = passwordSetDate;
	}

	/**
	 * 属性密码过期日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PASSWORDEXPIREDATE")
	public Date getPasswordExpireDate() {
		return this.passwordExpireDate;
	}

	/**
	 * 属性密码过期日期的setter方法
	 */
	public void setPasswordExpireDate(Date passwordExpireDate) {
		this.passwordExpireDate = passwordExpireDate;
	}

	/**
	 * 属性归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性出单机构代码的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构代码的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性帳号代码的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性帳号代码的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 属性电话号码的getter方法
	 */

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 属性电话号码的setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 属性手机号码的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性手机号码的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性通信地址的getter方法
	 */

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	/**
	 * 属性通信地址的setter方法
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 属性邮政编码的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 属性邮政编码的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	 * 属性员工标志的getter方法
	 */

	@Column(name = "USERFLAG")
	public String getUserFlag() {
		return this.userFlag;
	}

	/**
	 * 属性员工标志的setter方法
	 */
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	/**
	 * 属性允许登录的应用系统的getter方法
	 */

	@Column(name = "LOGINSYSTEM")
	public String getLoginSystem() {
		return this.loginSystem;
	}

	/**
	 * 属性允许登录的应用系统的setter方法
	 */
	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}

	/**
	 * 属性最新员工代码的getter方法
	 */

	@Column(name = "NEWUSERCODE")
	public String getNewUserCode() {
		return this.newUserCode;
	}

	/**
	 * 属性最新员工代码的setter方法
	 */
	public void setNewUserCode(String newUserCode) {
		this.newUserCode = newUserCode;
	}

	/**
	 * 属性效力状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性专项代码的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性专项代码的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
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
	 * 属性maxoverduecount的getter方法
	 */

	@Column(name = "MAXOVERDUECOUNT")
	public Integer getMaxoverduecount() {
		return this.maxoverduecount;
	}

	/**
	 * 属性maxoverduecount的setter方法
	 */
	public void setMaxoverduecount(Integer maxoverduecount) {
		this.maxoverduecount = maxoverduecount;
	}

	/**
	 * 属性maxoverduefee的getter方法
	 */

	@Column(name = "MAXOVERDUEFEE")
	public BigDecimal getMaxoverduefee() {
		return this.maxoverduefee;
	}

	/**
	 * 属性maxoverduefee的setter方法
	 */
	public void setMaxoverduefee(BigDecimal maxoverduefee) {
		this.maxoverduefee = maxoverduefee;
	}

	/**
	 * 属性USERNATURE的getter方法
	 */

	@Column(name = "USERNATURE")
	public String getUserNature() {
		return this.userNature;
	}

	/**
	 * 属性USERNATURE的setter方法
	 */
	public void setUserNature(String userNature) {
		this.userNature = userNature;
	}

	/**
	 * 属性USERLEVEL的getter方法
	 */

	@Column(name = "USERLEVEL")
	public String getUserLevel() {
		return this.userLevel;
	}

	/**
	 * 属性USERLEVEL的setter方法
	 */
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * 属性USERTYPE的getter方法
	 */

	@Column(name = "USERTYPE")
	public String getUserType() {
		return this.userType;
	}

	/**
	 * 属性USERTYPE的setter方法
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 属性ISSALES的getter方法
	 */

	@Column(name = "ISSALES")
	public String getIsSales() {
		return this.isSales;
	}

	/**
	 * 属性ISSALES的setter方法
	 */
	public void setIsSales(String isSales) {
		this.isSales = isSales;
	}

	/**
	 * 属性LOCKED的getter方法
	 */

	@Column(name = "LOCKED")
	public boolean isLocked() {
		return this.locked;
	}

	/**
	 * 属性LOCKED的setter方法
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Transient
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

}
