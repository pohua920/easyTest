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
@Table(name = "PRPQSCHEME")
public class PrpQscheme implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpQschemeId id;

	/** 属性险类代码 */
	private String riskCode;
	
	/** 属性PRPCMAIN */
	private PrpQmain prpQmain;
	
	/** 属性SCHEMECODE */
	private String schemeCode;
	/** 属性SCHEMENAME */
	private String schemeName;
	/** 属性KINDCODE */
	private String kindCode;
	/** 属性KINDNAME */
	private String kindName;
	/** 属性AMOUNT */
	private String amount;
	/** 属性SUMPREMIUM */
	private String sumPremium;
	/** 属性tcol1 */
	private String tcol1;
	/** 属性flag */
	private String flag;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpQschemeId getId() {
		return this.id;
	}

	public void setId(PrpQschemeId id) {
		this.id = id;
	}
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpQmain getPrpQmain() {
		return this.prpQmain;
	}

	public void setPrpQmain(PrpQmain prpQmain) {
		this.prpQmain = prpQmain;
	}
	@Column(name = "SCHEMECODE")
	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	@Column(name = "SCHEMENAME")
	public String getSchemeName() {
		return schemeName;
	}
	
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	@Column(name = "KINDCODE")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	@Column(name = "KINDNAME")
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	@Column(name = "AMOUNT")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Column(name = "SUMPREMIUM")
	public String getSumPremium() {
		return sumPremium;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}
	@Column(name = "TCOL1")
	public String getTcol1() {
		return tcol1;
	}

	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PrpQscheme() {
		
	}
}
