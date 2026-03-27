// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;

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
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPTEXCEPTITEM")
public class PrpTexceptItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpTexceptItemId id;

	/** 属性PRPCMAIN */
	private PrpTmain prpTmain;

	/** 属性险类代码 */
	private String riskCode;

	/** 属性除外代号 */
	private String exceptCode;

	/** 属性除外内容 */
	private String exceptName;

	/** 属性被保险人 */
	private String insuredName;
	
	/** 属性险别 */
	private String kindCode;

	/**
	 * 类PrpCengage的默认构造方法
	 */
	public PrpTexceptItem() {
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpTexceptItemId getId() {
		return this.id;
	}

	public void setId(PrpTexceptItemId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpTmain getPrpTmain() {
		return this.prpTmain;
	}

	public void setPrpTmain(PrpTmain prpTmain) {
		this.prpTmain = prpTmain;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	@Column(name = "EXCEPTCODE")
	public String getExceptCode() {
		return exceptCode;
	}

	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}

	@Column(name = "EXCEPTNAME")
	public String getExceptName() {
		return exceptName;
	}

	public void setExceptName(String exceptName) {
		this.exceptName = exceptName;
	}

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	
}
