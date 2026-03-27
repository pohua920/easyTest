package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLclaimGrade配件信息接口表
 */
@Entity
@Table(name = "PRPLCLAIMGRADE")
public class PrpLclaimGrade implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLclaimGradeId id;

	/** 属性员工名称 */
	private String userName;

	/** 属性归属机构 */
	private String comCode;

	/** 属性权限级别 */
	private String claimLevel;

	/** 属性金额下限 */
	private double valueLower = 0.00;

	/** 属性金额上限 */
	private double valueUpper = 0;

	/** 属性费率 */
	private double rate = 0.00;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性标志 */
	private String flag;

	/** 属性配置项描述 */
	private String configDesc;

	/**
	 * 类PrpLclaimGrade的默认构造方法
	 */
	public PrpLclaimGrade() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE")), @AttributeOverride(name = "taskCode", column = @Column(name = "TASKCODE")),
			@AttributeOverride(name = "configPara", column = @Column(name = "CONFIGPARA")) })
	public PrpLclaimGradeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLclaimGradeId id) {
		this.id = id;
	}

	/**
	 * 属性员工名称的getter方法
	 */

	@Column(name = "USERNAME")
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 属性员工名称的setter方法
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * 属性权限级别的getter方法
	 */

	@Column(name = "CLAIMLEVEL")
	public String getClaimLevel() {
		return this.claimLevel;
	}

	/**
	 * 属性权限级别的setter方法
	 */
	public void setClaimLevel(String claimLevel) {
		this.claimLevel = claimLevel;
	}

	/**
	 * 属性金额下限的getter方法
	 */

	@Column(name = "VALUELOWER")
	public double getValueLower() {
		return this.valueLower;
	}

	/**
	 * 属性金额下限的setter方法
	 */
	public void setValueLower(double valueLower) {
		this.valueLower = valueLower;
	}

	/**
	 * 属性金额上限的getter方法
	 */

	@Column(name = "VALUEUPPER")
	public double getValueUpper() {
		return this.valueUpper;
	}

	/**
	 * 属性金额上限的setter方法
	 */
	public void setValueUpper(double valueUpper) {
		this.valueUpper = valueUpper;
	}

	/**
	 * 属性费率的getter方法
	 */

	@Column(name = "RATE")
	public double getRate() {
		return this.rate;
	}

	/**
	 * 属性费率的setter方法
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性配置项描述的getter方法
	 */

	@Column(name = "CONFIGDESC")
	public String getConfigDesc() {
		return this.configDesc;
	}

	/**
	 * 属性配置项描述的setter方法
	 */
	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

}
