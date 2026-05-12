package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpDrisk
 */
@Entity
@Table(name = "PRPDRISK")
public class PrpDrisk implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性险种中文名称 */
	private String riskCName;

	/** 属性险种英文名称 */
	private String riskEName;

	/** 属性所属险类 */
	private String classCode;

	/** 属性GROUPCODE */
	private String groupCode;

	/** 属性计算保费方式 */
	private Long calculator;

	/** 属性Y/N 有无终保日期标志 */
	private String endDateFlag;

	/** 属性险种标志位 */
	private String riskFlag;

	/** 属性起保小时 */
	private Long startHour;

	/** 属性新的险种代码 */
	private String newRiskCode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性专项代码(对应会计科目) */
	private String articleCode;

	/** 属性收付费处理标志 */
	private String manageFlag;

	/** 属性SETTLETYPE */
	private String settleType;

	/** 属性标志字段 */
	private String flag;

	/** 属性RISKSHORTNAME */
	private String riskShortName;

	/**
	 * 类PrpDrisk的默认构造方法
	 */
	public PrpDrisk() {
	}

	/**
	 * 属性险种代码的getter方法
	 */
	@Id
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
	 * 属性险种中文名称的getter方法
	 */

	@Column(name = "RISKCNAME")
	public String getRiskCName() {
		return this.riskCName;
	}

	/**
	 * 属性险种中文名称的setter方法
	 */
	public void setRiskCName(String riskCName) {
		this.riskCName = riskCName;
	}

	/**
	 * 属性险种英文名称的getter方法
	 */

	@Column(name = "RISKENAME")
	public String getRiskEName() {
		return this.riskEName;
	}

	/**
	 * 属性险种英文名称的setter方法
	 */
	public void setRiskEName(String riskEName) {
		this.riskEName = riskEName;
	}

	/**
	 * 属性所属险类的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性所属险类的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性GROUPCODE的getter方法
	 */

	@Column(name = "GROUPCODE")
	public String getGroupCode() {
		return this.groupCode;
	}

	/**
	 * 属性GROUPCODE的setter方法
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * 属性计算保费方式的getter方法
	 */

	@Column(name = "CALCULATOR")
	public Long getCalculator() {
		return this.calculator;
	}

	/**
	 * 属性计算保费方式的setter方法
	 */
	public void setCalculator(Long calculator) {
		this.calculator = calculator;
	}

	/**
	 * 属性Y/N 有无终保日期标志的getter方法
	 */

	@Column(name = "ENDDATEFLAG")
	public String getEndDateFlag() {
		return this.endDateFlag;
	}

	/**
	 * 属性Y/N 有无终保日期标志的setter方法
	 */
	public void setEndDateFlag(String endDateFlag) {
		this.endDateFlag = endDateFlag;
	}

	/**
	 * 属性险种标志位的getter方法
	 */

	@Column(name = "RISKFLAG")
	public String getRiskFlag() {
		return this.riskFlag;
	}

	/**
	 * 属性险种标志位的setter方法
	 */
	public void setRiskFlag(String riskFlag) {
		this.riskFlag = riskFlag;
	}

	/**
	 * 属性起保小时的getter方法
	 */

	@Column(name = "STARTHOUR")
	public Long getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性起保小时的setter方法
	 */
	public void setStartHour(Long startHour) {
		this.startHour = startHour;
	}

	/**
	 * 属性新的险种代码的getter方法
	 */

	@Column(name = "NEWRISKCODE")
	public String getNewRiskCode() {
		return this.newRiskCode;
	}

	/**
	 * 属性新的险种代码的setter方法
	 */
	public void setNewRiskCode(String newRiskCode) {
		this.newRiskCode = newRiskCode;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性专项代码(对应会计科目)的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性专项代码(对应会计科目)的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**
	 * 属性收付费处理标志的getter方法
	 */

	@Column(name = "MANAGEFLAG")
	public String getManageFlag() {
		return this.manageFlag;
	}

	/**
	 * 属性收付费处理标志的setter方法
	 */
	public void setManageFlag(String manageFlag) {
		this.manageFlag = manageFlag;
	}

	/**
	 * 属性SETTLETYPE的getter方法
	 */

	@Column(name = "SETTLETYPE")
	public String getSettleType() {
		return this.settleType;
	}

	/**
	 * 属性SETTLETYPE的setter方法
	 */
	public void setSettleType(String settleType) {
		this.settleType = settleType;
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

	/**
	 * 属性RISKSHORTNAME的getter方法
	 */

	@Column(name = "RISKSHORTNAME")
	public String getRiskShortName() {
		return this.riskShortName;
	}

	/**
	 * 属性RISKSHORTNAME的setter方法
	 */
	public void setRiskShortName(String riskShortName) {
		this.riskShortName = riskShortName;
	}

}
