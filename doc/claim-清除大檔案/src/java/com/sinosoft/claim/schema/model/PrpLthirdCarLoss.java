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
 * POJO类PrpLthirdCarLoss三者车辆损失部位表
 */
@Entity
@Table(name = "PRPLTHIRDCARLOSS")
public class PrpLthirdCarLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLthirdCarLossId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性零件(项目)代码 */
	private String compCode;

	/** 属性零件(项目)名称 */
	private String compName;

	/** 属性损失程度级别 */
	private String lossGrade;

	/** 属性损失程度描述 */
	private String lossDesc;

	/** 属性状态字段 */
	private String flag;

	/** 属性损失部件代码 */
	private String partCode;

	/** 属性损失部件名称 */
	private String partName;

	/** 属性险别代码 */
	private String kindCode;
	
	
	/** 属性显示列表*/
	private Collection<PrpLthirdCarLoss> thirdCarLossList;

	/**
	 * 类PrpLthirdCarLoss的默认构造方法
	 */
	public PrpLthirdCarLoss() {
		id = new PrpLthirdCarLossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpLthirdCarLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLthirdCarLossId id) {
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
	 * 属性车牌号的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性零件(项目)代码的getter方法
	 */

	@Column(name = "COMPCODE")
	public String getCompCode() {
		return this.compCode;
	}

	/**
	 * 属性零件(项目)代码的setter方法
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	/**
	 * 属性零件(项目)名称的getter方法
	 */

	@Column(name = "COMPNAME")
	public String getCompName() {
		return this.compName;
	}

	/**
	 * 属性零件(项目)名称的setter方法
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * 属性损失程度级别的getter方法
	 */

	@Column(name = "LOSSGRADE")
	public String getLossGrade() {
		return this.lossGrade;
	}

	/**
	 * 属性损失程度级别的setter方法
	 */
	public void setLossGrade(String lossGrade) {
		this.lossGrade = lossGrade;
	}

	/**
	 * 属性损失程度描述的getter方法
	 */

	@Column(name = "LOSSDESC")
	public String getLossDesc() {
		return this.lossDesc;
	}

	/**
	 * 属性损失程度描述的setter方法
	 */
	public void setLossDesc(String lossDesc) {
		this.lossDesc = lossDesc;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性损失部件代码的getter方法
	 */

	@Column(name = "PARTCODE")
	public String getPartCode() {
		return this.partCode;
	}

	/**
	 * 属性损失部件代码的setter方法
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	/**
	 * 属性损失部件名称的getter方法
	 */

	@Column(name = "PARTNAME")
	public String getPartName() {
		return this.partName;
	}

	/**
	 * 属性损失部件名称的setter方法
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * 属性险别代码的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别代码的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	
	public void setThirdCarLossList(Collection<PrpLthirdCarLoss> thirdCarLossList) {
	    this.thirdCarLossList = thirdCarLossList;
	}
    @Transient
	public Collection<PrpLthirdCarLoss> getThirdCarLossList() {
	    return thirdCarLossList;
	}

}
