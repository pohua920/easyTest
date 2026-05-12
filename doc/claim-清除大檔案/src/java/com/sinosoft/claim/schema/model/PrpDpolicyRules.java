package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 * POJO类PrpDpolicyRules
 */
@Entity
@Table(name = "PRPDPOLICYRULES")
public class PrpDpolicyRules implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDpolicyRulesId id;

	private String codeType;

	private String kindCode;

	private String multiplier;

	private String endDate;

	/**
	 * 类PrpCinsured的默认构造方法
	 */
	public PrpDpolicyRules() {
		this.id = new PrpDpolicyRulesId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE")), @AttributeOverride(name = "startDate", column = @Column(name = "STARTDATE")) })
	public PrpDpolicyRulesId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDpolicyRulesId id) {
		this.id = id;
	}


	@Column(name = "CODETYPE")
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "MULTIPLIER")
	public String getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}

	@Column(name = "ENDDATE")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
