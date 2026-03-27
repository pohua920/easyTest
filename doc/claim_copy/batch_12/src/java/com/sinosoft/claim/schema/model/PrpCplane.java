package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO类prpCplane
 */
@Entity
@Table(name = "PRPCPLANE")
public class PrpCplane implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCplaneId id;

	/**
	 * 属性PrpCmain
	 */
	private PrpCmain prpCmain;

	/** 属性險種代碼 */
	private String riskCode;

	/** 属性標的序號 */
	private Long itemNo;

	/** 属性机型 */
	private String planeType;

	/** 属性机身号码 */
	private String registrationMarks;

	/** 属性执照号码 */
	private String licenseNo;

	/** 属性厂牌编号 */
	private String labelNo;

	/** 属性建造年份 */
	private String buildYear;

	/** 属性引擎号码 */
	private String engineNo;

	/** 属性標誌 */
	private String flag;

	/**
	 * 类prpCplane的默认构造方法
	 */
	public PrpCplane() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCplaneId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCplaneId id) {
		this.id = id;
	}

	/**
	 * 属性PrpCmain的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYNO", nullable = false, insertable = false, updatable = false)
	public PrpCmain getPrpCmain() {
		return this.prpCmain;
	}

	/**
	 * 属性PRPCMAIN的setter方法
	 */
	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	/**
	 * 属性險種代碼的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性險種代碼的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性標的序號的getter方法
	 */

	@Column(name = "ITEMNO")
	public Long getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性標的序號的setter方法
	 */
	public void setItemNo(Long itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性机型的getter方法
	 */

	@Column(name = "PLANETYPE")
	public String getPlaneType() {
		return this.planeType;
	}

	/**
	 * 属性机型的setter方法
	 */
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	/**
	 * 属性机身号码的getter方法
	 */

	@Column(name = "REGISTRATIONMARKS")
	public String getRegistrationMarks() {
		return this.registrationMarks;
	}

	/**
	 * 属性机身号码的setter方法
	 */
	public void setRegistrationMarks(String registrationMarks) {
		this.registrationMarks = registrationMarks;
	}

	/**
	 * 属性执照号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性执照号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性厂牌编号的getter方法
	 */

	@Column(name = "LABELNO")
	public String getLabelNo() {
		return this.labelNo;
	}

	/**
	 * 属性厂牌编号的setter方法
	 */
	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}

	/**
	 * 属性建造年份的getter方法
	 */
	@Column(name = "BUILDYEAR")
	public String getBuildYear() {
		return this.buildYear;
	}

	/**
	 * 属性建造年份的setter方法
	 */
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	/**
	 * 属性引擎号码的getter方法
	 */

	@Column(name = "ENGINENO")
	public String getEngineNo() {
		return this.engineNo;
	}

	/**
	 * 属性引擎号码的setter方法
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * 属性標誌的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性標誌的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
