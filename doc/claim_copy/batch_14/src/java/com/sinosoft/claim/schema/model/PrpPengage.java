package com.sinosoft.claim.schema.model;

// default package
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
 * POJO类PrpPengage
 */
@Entity
@Table(name = "PRPPENGAGE")
public class PrpPengage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpPengageId id;

	/** 属性批改信息表 */
	private PrpPhead prpPhead;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性条款编码 */
	private String clauseCode;

	/** 属性条款文字描述 */
	private String clauses;

	/** 属性条款的名字 */
	private String titleFlag;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpPengage的默认构造方法
	 */
	public PrpPengage() {
		id = new PrpPengageId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpPengageId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpPengageId id) {
		this.id = id;
	}

	/**
	 * 属性批改信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDORSENO", nullable = false, insertable = false, updatable = false)
	public PrpPhead getPrpPhead() {
		return this.prpPhead;
	}

	/**
	 * 属性批改信息表的setter方法
	 */
	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
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

	/**
	 * 属性条款编码的getter方法
	 */

	@Column(name = "CLAUSECODE")
	public String getClauseCode() {
		return this.clauseCode;
	}

	/**
	 * 属性条款编码的setter方法
	 */
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	/**
	 * 属性条款文字描述的getter方法
	 */

	@Column(name = "CLAUSES")
	public String getClauses() {
		return this.clauses;
	}

	/**
	 * 属性条款文字描述的setter方法
	 */
	public void setClauses(String clauses) {
		this.clauses = clauses;
	}

	/**
	 * 属性条款的名字的getter方法
	 */

	@Column(name = "TITLEFLAG")
	public String getTitleFlag() {
		return this.titleFlag;
	}

	/**
	 * 属性条款的名字的setter方法
	 */
	public void setTitleFlag(String titleFlag) {
		this.titleFlag = titleFlag;
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

}
