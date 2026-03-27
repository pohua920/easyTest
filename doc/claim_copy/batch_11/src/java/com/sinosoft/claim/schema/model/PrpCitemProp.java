package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCitemProp
 */
@Entity
@Table(name = "PRPCITEMPROP")
public class PrpCitemProp implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCitemPropId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性风险等级 */
	private String riskGrade;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性赔偿期限：毛利润的月数 */
	private Integer grossProfitMonths;

	/** 属性赔偿期限：工资的月数 */
	private Integer wageMonths;

	/** 属性赔偿期限：工资100％的周数 */
	private Integer wageHeadWeeks;

	/**
	 * 类PrpCitemProp的默认构造方法
	 */
	public PrpCitemProp() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpCitemPropId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCitemPropId id) {
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
	 * 属性风险等级的getter方法
	 */

	@Column(name = "RISKGRADE")
	public String getRiskGrade() {
		return this.riskGrade;
	}

	/**
	 * 属性风险等级的setter方法
	 */
	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 属性赔偿期限：毛利润的月数的getter方法
	 */

	@Column(name = "GROSSPROFITMONTHS")
	public Integer getGrossProfitMonths() {
		return this.grossProfitMonths;
	}

	/**
	 * 属性赔偿期限：毛利润的月数的setter方法
	 */
	public void setGrossProfitMonths(Integer grossProfitMonths) {
		this.grossProfitMonths = grossProfitMonths;
	}

	/**
	 * 属性赔偿期限：工资的月数的getter方法
	 */

	@Column(name = "WAGEMONTHS")
	public Integer getWageMonths() {
		return this.wageMonths;
	}

	/**
	 * 属性赔偿期限：工资的月数的setter方法
	 */
	public void setWageMonths(Integer wageMonths) {
		this.wageMonths = wageMonths;
	}

	/**
	 * 属性赔偿期限：工资100％的周数的getter方法
	 */

	@Column(name = "WAGEHEADWEEKS")
	public Integer getWageHeadWeeks() {
		return this.wageHeadWeeks;
	}

	/**
	 * 属性赔偿期限：工资100％的周数的setter方法
	 */
	public void setWageHeadWeeks(Integer wageHeadWeeks) {
		this.wageHeadWeeks = wageHeadWeeks;
	}

}
