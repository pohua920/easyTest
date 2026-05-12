package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLpersonWound伤情信息表
 */
@Entity
@Table(name = "PRPLPERSONWOUND")
public class PrpLpersonWound implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpersonWoundId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性人员姓名 */
	private String personName;

	/** 属性伤情代码 */
	private String woundCode;

	/** 属性伤情描述 */
	private String woundDesc;

	/** 属性标志字段 */
	private String flag;

	private List<PrpLpersonWound> woundList = new ArrayList<PrpLpersonWound>();

	/**
	 * 类PrpLpersonWound的默认构造方法
	 */
	public PrpLpersonWound() {
		this.id = new PrpLpersonWoundId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "personNo", column = @Column(name = "PERSONNO")) })
	public PrpLpersonWoundId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpersonWoundId id) {
		this.id = id;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性伤情代码的getter方法
	 */

	@Column(name = "WOUNDCODE")
	public String getWoundCode() {
		return this.woundCode;
	}

	/**
	 * 属性伤情代码的setter方法
	 */
	public void setWoundCode(String woundCode) {
		this.woundCode = woundCode;
	}

	/**
	 * 属性伤情描述的getter方法
	 */

	@Column(name = "WOUNDDESC")
	public String getWoundDesc() {
		return this.woundDesc;
	}

	/**
	 * 属性伤情描述的setter方法
	 */
	public void setWoundDesc(String woundDesc) {
		this.woundDesc = woundDesc;
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

	@Transient
	public List<PrpLpersonWound> getWoundList() {
		return woundList;
	}

	public void setWoundList(List<PrpLpersonWound> woundList) {
		this.woundList = woundList;
	}

}
