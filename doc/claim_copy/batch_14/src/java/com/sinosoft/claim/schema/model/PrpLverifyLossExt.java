package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLverifyLossExt
 */
@Entity
@Table(name = "PRPLVERIFYLOSSEXT")
public class PrpLverifyLossExt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLverifyLossExtId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性小时 */
	private String inputHour;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性归属机构 */
	private String comCode;

	/** 属性TITLE */
	private String title;

	/** 属性内容 */
	private String context;

	List<PrpLverifyLossExt> verifyLossExtList;
	/** 属性签发人名称 */
	private String operatorCodeName;

	/**
	 * 类PrpLverifyLossExt的默认构造方法
	 */
	public PrpLverifyLossExt() {
		id = new PrpLverifyLossExtId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLverifyLossExtId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLverifyLossExtId id) {
		this.id = id;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public String getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性小时的setter方法
	 */
	public void setInputHour(String inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性TITLE的getter方法
	 */

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	/**
	 * 属性TITLE的setter方法
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 属性内容的getter方法
	 */

	@Column(name = "CONTEXT")
	public String getContext() {
		return this.context;
	}

	/**
	 * 属性内容的setter方法
	 */
	public void setContext(String context) {
		this.context = context;
	}

	@Transient
	public List<PrpLverifyLossExt> getVerifyLossExtList() {
		return verifyLossExtList;
	}

	public void setVerifyLossExtList(List<PrpLverifyLossExt> verifyLossExtList) {
		this.verifyLossExtList = verifyLossExtList;
	}

	@Transient
	public String getOperatorCodeName() {
		return operatorCodeName;
	}

	public void setOperatorCodeName(String operatorCodeName) {
		this.operatorCodeName = operatorCodeName;
	}

}
