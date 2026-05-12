package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLrelatePerson联系人表
 */
@Entity
@Table(name = "PRPLRELATEPERSON")
public class PrpLrelatePerson implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLrelatePersonId id;

	/** 属性人员代码 */
	private String personCode;

	/** 属性人员姓名 */
	private String personName;

	/** 属性报案人联系电话 */
	private String phoneNumber;

	/** 属性移动电话 */
	private String mobile;

	/** 属性保单号 */
	private String policyNo;

	/** 属性备注 */
	private String remark;

	/** 属性状态字段 */
	private String flag;

	/**
	 * 类PrpLrelatePerson的默认构造方法
	 */
	public PrpLrelatePerson() {
		id = new PrpLrelatePersonId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "personType", column = @Column(name = "PERSONTYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLrelatePersonId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLrelatePersonId id) {
		this.id = id;
	}

	/**
	 * 属性人员代码的getter方法
	 */

	@Column(name = "PERSONCODE")
	public String getPersonCode() {
		return this.personCode;
	}

	/**
	 * 属性人员代码的setter方法
	 */
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	/**
	 * 属性人员姓名的getter方法
	 */

	@Column(name = "PERSONNAME")
	public String getPersonName() {
		return this.personName;
	}

	/**
	 * 属性人员姓名的setter方法
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * 属性报案人联系电话的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性报案人联系电话的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性移动电话的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性移动电话的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
