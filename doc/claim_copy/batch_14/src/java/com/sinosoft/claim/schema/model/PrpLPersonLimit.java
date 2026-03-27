package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLPersonLimit
 */
@Entity
@Table(name = "PRPLPERSONLIMIT")
public class PrpLPersonLimit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLPersonLimitId id;

	/** 属性标志 */
	private String flag;
	/** 属性人员代码 */
	private String userCode = "";
	/** 属性险种代码 */
	private String riskCode = "";

	/**
	 * 类PrpLPersonLimit的默认构造方法
	 */
	public PrpLPersonLimit() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")) })
	public PrpLPersonLimitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLPersonLimitId id) {
		this.id = id;
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
	 * 设置属性人员代码
	 * @param userCode 待设置的属性人员代码
	 */
	public void setUserCode(String userCode) {
		this.userCode = StringUtils.rightTrim(userCode);
	}

	/**
	 * 获取属性人员代码
	 * @return 属性人员代码
	 */
	@Transient
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 设置属性险种代码
	 * @param riskCode 待设置的属性险种代码的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = StringUtils.rightTrim(riskCode);
	}

	/**
	 * 获取属性险种代码
	 * @return 属性险种代码的值
	 */
	@Transient
	public String getRiskCode() {
		return riskCode;
	}

}
