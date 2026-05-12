package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpCengage 特别约定表
 */
@Entity
@Table(name = "PRPCENGAGE")
public class PrpCengage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCengageId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性条款编码 */
	private String clauseCode;

	/** 属性条款文字描述 */
	private String clauses;

	/** 属性TITLEFLAG */
	private String titleFlag;

	/** 投保单号码 */
	private PrpCmain prpCmain;

	/** 属性标志字段 */
	private String flag;
	/** 属性显示列表 */
	private List<PrpCengage> prpCengageList = new ArrayList<PrpCengage>();
	/** 属性特别约定内容 */
	private String context = "";

	/**
	 * 类PrpCengage的默认构造方法
	 */
	public PrpCengage() {
		id = new PrpCengageId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpCengageId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCengageId id) {
		this.id = id;
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
	 * 属性TITLEFLAG的getter方法
	 */

	@Column(name = "TITLEFLAG")
	public String getTitleFlag() {
		return this.titleFlag;
	}

	/**
	 * 属性TITLEFLAG的setter方法
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

	@Transient
	/**
	 * 属性prpcengateList的getter方法
	 */
	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	/**
	 * 属性context的getter方法
	 */
	@Transient
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * 投保单号码
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policyNo", nullable = false, insertable = false, updatable = false)
	public PrpCmain getPrpCmain() {
		return this.prpCmain;
	}

	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}
}
