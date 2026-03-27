package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLcheckExt
 */
@Entity
@Table(name = "PRPLCHECKEXT")
public class PrpLcheckExt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcheckExtId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性查勘项目名称 */
	private String displayName;

	/** 属性扩充字段内容 */
	private String columnValue;

	/** 属性标志字段 */
	private String flag;

	/** 属性备注 */
	private String remark;
	/** 属性显示列表 */
	private Collection<PrpLcheckExt> prpLcheckExtList;

	/**
	 * 类PrpLcheckExt的默认构造方法
	 */
	public PrpLcheckExt() {
		this.id = new PrpLcheckExtId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "columnName", column = @Column(name = "COLUMNNAME")), @AttributeOverride(name = "referSerialNo", column = @Column(name = "REFERSERIALNO")) })
	public PrpLcheckExtId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcheckExtId id) {
		this.id = id;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性查勘项目名称的getter方法
	 */

	@Column(name = "DISPLAYNAME")
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * 属性查勘项目名称的setter方法
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 属性扩充字段内容的getter方法
	 */

	@Column(name = "COLUMNVALUE")
	public String getColumnValue() {
		return this.columnValue;
	}

	/**
	 * 属性扩充字段内容的setter方法
	 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
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

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setPrpLcheckExtList(Collection<PrpLcheckExt> prpLcheckExtList) {
		this.prpLcheckExtList = prpLcheckExtList;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public Collection<PrpLcheckExt> getPrpLcheckExtList() {
		return prpLcheckExtList;
	}

}
