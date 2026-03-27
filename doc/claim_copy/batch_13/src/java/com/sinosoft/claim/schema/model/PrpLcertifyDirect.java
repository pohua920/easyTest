package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLcertifyDirect
 */
@Entity
@Table(name = "PRPLCERTIFYDIRECT")
public class PrpLcertifyDirect implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcertifyDirectId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性单证类型代码 */
	private String typeCode;

	/** 属性单证类型名称 */
	private String typeName;

	/** 属性扩充字段内容 */
	private String columnValue;

	/** 属性标志字段 */
	private String flag;

	/** 属性强制保险收集标志 */
	private String compelFlag;

	/** 属性商业保险收集标志 */
	private String businessFlag;

	/** 页面展示时候使用,不予数据库关联 */
	private List<PrpLcertifyDirect> certifyDirectList = new ArrayList<PrpLcertifyDirect>(0);

	/**
	 * 类PrpLcertifyDirect的默认构造方法
	 */
	public PrpLcertifyDirect() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLcertifyDirectId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcertifyDirectId id) {
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
	 * 属性单证类型代码的getter方法
	 */

	@Column(name = "TYPECODE")
	public String getTypeCode() {
		return this.typeCode;
	}

	/**
	 * 属性单证类型代码的setter方法
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 属性单证类型名称的getter方法
	 */

	@Column(name = "TYPENAME")
	public String getTypeName() {
		return this.typeName;
	}

	/**
	 * 属性单证类型名称的setter方法
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	/**
	 * 属性强制保险收集标志的getter方法
	 */

	@Column(name = "COMPELFLAG")
	public String getCompelFlag() {
		return this.compelFlag;
	}

	/**
	 * 属性强制保险收集标志的setter方法
	 */
	public void setCompelFlag(String compelFlag) {
		this.compelFlag = compelFlag;
	}

	/**
	 * 属性商业保险收集标志的getter方法
	 */

	@Column(name = "BUSINESSFLAG")
	public String getBusinessFlag() {
		return this.businessFlag;
	}

	/**
	 * 属性商业保险收集标志的setter方法
	 */
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}

	@Transient
	public List<PrpLcertifyDirect> getCertifyDirectList() {
		return certifyDirectList;
	}

	public void setCertifyDirectList(List<PrpLcertifyDirect> certifyDirectList) {
		this.certifyDirectList = certifyDirectList;
	}

}
