package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类Prpllawyer
 */
@Entity
@Table(name = "PRPLLAWYER")
public class Prpllawyer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpllawyerId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性姓名 */
	private String name;

	/** 属性归属律师事务所 */
	private String lawoffice;

	/** 属性电子邮件 */
	private String email;

	/** 属性联系电话 */
	private String phone;

	/** 属性通讯地址 */
	private String place;

	/** 属性邮编 */
	private String postcode;

	/** 属性有效状态 */
	private String validStatus;

	/**
	 * 类Prpllawyer的默认构造方法
	 */
	public Prpllawyer() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "itemno", column = @Column(name = "ITEMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpllawyerId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpllawyerId id) {
		this.id = id;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性姓名的getter方法
	 */

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	/**
	 * 属性姓名的setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 属性归属律师事务所的getter方法
	 */

	@Column(name = "LAWOFFICE")
	public String getLawoffice() {
		return this.lawoffice;
	}

	/**
	 * 属性归属律师事务所的setter方法
	 */
	public void setLawoffice(String lawoffice) {
		this.lawoffice = lawoffice;
	}

	/**
	 * 属性电子邮件的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性电子邮件的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性联系电话的getter方法
	 */

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 属性联系电话的setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 属性通讯地址的getter方法
	 */

	@Column(name = "PLACE")
	public String getPlace() {
		return this.place;
	}

	/**
	 * 属性通讯地址的setter方法
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * 属性邮编的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostcode() {
		return this.postcode;
	}

	/**
	 * 属性邮编的setter方法
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

}
