package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCmainSub
 */
@Entity
@Table(name = "PRPCMAINSUB")
public class PrpCmainSub implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCmainSubId id;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性标志字段 */
	private String flag;

	/** 属性备注 */
	private String remark;

	/** 属性BALANCETIMES */
	private Integer balanceTimes;

	/** 属性GROUPNO */
	private String groupNo;

	/** 属性checkagentdescription */
	private String checkagentdescription;

	/** 属性CONDITIONS */
	private String conditions;

	/** 属性BATCHNO */
	private String batchNo;

	/**
	 * 类PrpCmainSub的默认构造方法
	 */
	public PrpCmainSub() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "mainPolicyNo", column = @Column(name = "MAINPOLICYNO")) })
	public PrpCmainSubId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCmainSubId id) {
		this.id = id;
	}

	/**
	 * 属性批单号码的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
	 * 属性BALANCETIMES的getter方法
	 */

	@Column(name = "BALANCETIMES")
	public Integer getBalanceTimes() {
		return this.balanceTimes;
	}

	/**
	 * 属性BALANCETIMES的setter方法
	 */
	public void setBalanceTimes(Integer balanceTimes) {
		this.balanceTimes = balanceTimes;
	}

	/**
	 * 属性GROUPNO的getter方法
	 */

	@Column(name = "GROUPNO")
	public String getGroupNo() {
		return this.groupNo;
	}

	/**
	 * 属性GROUPNO的setter方法
	 */
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	/**
	 * 属性checkagentdescription的getter方法
	 */

	@Column(name = "CHECKAGENTDESCRIPTION")
	public String getCheckagentdescription() {
		return this.checkagentdescription;
	}

	/**
	 * 属性checkagentdescription的setter方法
	 */
	public void setCheckagentdescription(String checkagentdescription) {
		this.checkagentdescription = checkagentdescription;
	}

	/**
	 * 属性CONDITIONS的getter方法
	 */

	@Column(name = "CONDITIONS")
	public String getConditions() {
		return this.conditions;
	}

	/**
	 * 属性CONDITIONS的setter方法
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	/**
	 * 属性BATCHNO的getter方法
	 */

	@Column(name = "BATCHNO")
	public String getBatchNo() {
		return this.batchNo;
	}

	/**
	 * 属性BATCHNO的setter方法
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

}
