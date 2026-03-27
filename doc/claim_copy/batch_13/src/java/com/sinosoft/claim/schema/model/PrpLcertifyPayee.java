package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLcertifyPayee
 */
@Entity
@Table(name = "PRPLCERTIFYPAYEE")
public class PrpLcertifyPayee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcertifyPayeeId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性payeetypecode */
	private String payeeTypeCode;

	/** 属性payeetypename */
	private String payeeTypeName;

	/** 属性relationscode */
	private String relationsCode;

	/** 属性relationsname */
	private String relationsName;

	/** 属性payeename */
	private String payeeName;

	/** 属性licensetypecode */
	private String licenseTypeCode;

	/** 属性licensetypename */
	private String licenseTypeName;

	/** 属性licensecode */
	private String licenseCode;

	/** 属性linker */
	private String linker;

	/** 属性banktype */
	private String bankType;

	/** 属性bankcardno */
	private String bankCardNo;

	/** 属性linkertel */
	private String linkerTel;

	/**
	 * 类PrpLcertifyPayee的默认构造方法
	 */
	public PrpLcertifyPayee() {
		id = new PrpLcertifyPayeeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcertifyPayeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcertifyPayeeId id) {
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
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name = "payeeTypeCode")
	public String getPayeeTypeCode() {
		return payeeTypeCode;
	}

	public void setPayeeTypeCode(String payeeTypeCode) {
		this.payeeTypeCode = payeeTypeCode;
	}
	@Column(name = "payeeTypeName")
	public String getPayeeTypeName() {
		return payeeTypeName;
	}

	public void setPayeeTypeName(String payeeTypeName) {
		this.payeeTypeName = payeeTypeName;
	}
	@Column(name = "relationsCode")
	public String getRelationsCode() {
		return relationsCode;
	}

	public void setRelationsCode(String relationsCode) {
		this.relationsCode = relationsCode;
	}
	@Column(name = "relationsName")
	public String getRelationsName() {
		return relationsName;
	}

	public void setRelationsName(String relationsName) {
		this.relationsName = relationsName;
	}
	@Column(name = "payeeName")
	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	@Column(name = "licenseTypeCode")
	public String getLicenseTypeCode() {
		return licenseTypeCode;
	}

	public void setLicenseTypeCode(String licenseTypeCode) {
		this.licenseTypeCode = licenseTypeCode;
	}
	@Column(name = "licenseTypeName")
	public String getLicenseTypeName() {
		return licenseTypeName;
	}

	public void setLicenseTypeName(String licenseTypeName) {
		this.licenseTypeName = licenseTypeName;
	}
	@Column(name = "licenseCode")
	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}
	@Column(name = "linker")
	public String getLinker() {
		return linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}
	@Column(name = "bankType")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	@Column(name = "bankCardNo")
	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	@Column(name = "linkerTel")
	public String getLinkerTel() {
		return linkerTel;
	}

	public void setLinkerTel(String linkerTel) {
		this.linkerTel = linkerTel;
	}

}
