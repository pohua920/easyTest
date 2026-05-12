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
 * POJO类PrpLcheckLoss查勘事故估损金额表
 */
@Entity
@Table(name = "PRPLCHECKLOSS")
public class PrpLcheckLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcheckLossId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性关联理赔车辆序号 */
	private Integer referSerialNo;

	/** 属性险别编码--** 车损险--** 三者险--** 其他附加险 */
	private String kindCode;

	/**
	 * 属性金额类型--** 车损部分：--** 1. 标的损失--** 2. 施救费--** 3. 吊车--** 4. 拖车--** 5. 其他--**
	 * 三者部分--** 1. 车辆--** 2. 人员--** 3. 财产
	 */
	private String lossFeeType;

	/** 属性损失金额 */
	private Double lossFee = 0d;

	/** 属性标志字段 */
	private String flag;
	/** 属性显示列表 */
	private Collection<PrpLcheckLoss> prpLcheckLossList;
	/** 属性险别名称 */
	private String kindName = "";

	/**
	 * 类PrpLcheckLoss的默认构造方法
	 */
	public PrpLcheckLoss() {
		this.id = new PrpLcheckLossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcheckLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcheckLossId id) {
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
	 * 属性关联理赔车辆序号的getter方法
	 */

	@Column(name = "REFERSERIALNO")
	public Integer getReferSerialNo() {
		return this.referSerialNo;
	}

	/**
	 * 属性关联理赔车辆序号的setter方法
	 */
	public void setReferSerialNo(Integer referSerialNo) {
		this.referSerialNo = referSerialNo;
	}

	/**
	 * 属性险别编码--** 车损险--** 三者险--** 其他附加险的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别编码--** 车损险--** 三者险--** 其他附加险的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性金额类型--** 车损部分：--** 1. 标的损失--** 2. 施救费--** 3. 吊车--** 4. 拖车--** 5. 其他--**
	 * 三者部分--** 1. 车辆--** 2. 人员--** 3. 财产的getter方法
	 */

	@Column(name = "LOSSFEETYPE")
	public String getLossFeeType() {
		return this.lossFeeType;
	}

	/**
	 * 属性金额类型--** 车损部分：--** 1. 标的损失--** 2. 施救费--** 3. 吊车--** 4. 拖车--** 5. 其他--**
	 * 三者部分--** 1. 车辆--** 2. 人员--** 3. 财产的setter方法
	 */
	public void setLossFeeType(String lossFeeType) {
		this.lossFeeType = lossFeeType;
	}

	/**
	 * 属性损失金额的getter方法
	 */

	@Column(name = "LOSSFEE")
	public Double getLossFee() {
		return this.lossFee;
	}

	/**
	 * 属性损失金额的setter方法
	 */
	public void setLossFee(Double lossFee) {
		this.lossFee = lossFee;
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
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setPrpLcheckLossList(Collection<PrpLcheckLoss> prpLcheckLossList) {
		this.prpLcheckLossList = prpLcheckLossList;
	}

	/**
	 * 设置属性险别名称
	 * @param kindName 险别名称
	 */

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public Collection<PrpLcheckLoss> getPrpLcheckLossList() {
		return prpLcheckLossList;
	}

	/**
	 * 得到险别名称
	 * @return 险别名称
	 */
	@Transient
	public String getKindName() {
		return kindName;
	}

}
