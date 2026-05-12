package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PrpDcarModel")
public class PrpDcarModel implements java.io.Serializable {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 属性车型代码 */
	private String modelCode = "";
	/** 属性速查索引码 */
	private String shortHandCode = "";
	/** 属性车辆种类 */
	private String carKind = "";
	/** 属性车辆类型 */
	private String carStyle = "";
	/** 属性车型名称 */
	private String modelName = "";
	/** 属性车系名称 */
	private String carSeriesName = "";
	/** 属性座位数 */
	private Long seatCount = 0L;
	/** 属性吨位数 */
	private Double tonCount = 0D;
	/** 属性排气量 */
	private Double exhaustScale = 0D;
	/** 属性生产厂家 */
	private String factory = "";
	/** 属性国家代码 */
	private String countryCode = "";
	/** 属性新车购置价 */
	private Double purchasePrice = 0D;
	/** 属性险种范围 */
	private String riskScope = "";
	/** 属性有效日期 */
	private Date validDate = new Date();
	/** 属性是否有效 */
	private String validStatus = "";
	/** 属性备注 */
	private String remark = "";
	/** 属性标志 */
	private String flag = "";
	/** 属性车损车型系数 */
	private Double coefficient1 = 0D;
	/** 属性盗抢车型系数 */
	private Double coefficient2 = 0D;
	/** 属性品牌 */
	private String carBrand = "";
	/** 属性年款 */
	private String carYear = "";

	/**
	 * 属性车型代码的getter方法
	 */
	@Id
	@Column(name = "MODELCODE")
	public String getModelCode() {
		return this.modelCode;
	}

	/**
	 * 属性车型代码的setter方法
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 属性shortHandCode的getter方法
	 */

	@Column(name = "ShortHandCode")
	public String getShortHandCode() {
		return this.shortHandCode;
	}

	/**
	 * 属性shortHandCode的setter方法
	 */
	public void setShortHandCode(String shortHandCode) {
		this.shortHandCode = shortHandCode;
	}

	/**
	 * 属性车型名称的getter方法
	 */

	@Column(name = "MODELNAME")
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 属性车型名称的setter方法
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 属性车型品牌的getter方法
	 */

	@Column(name = "CARBRAND")
	public String getCarBrand() {
		return this.carBrand;
	}

	/**
	 * 属性车型品牌的setter方法
	 */
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	/**
	 * 属性车系名称的getter方法
	 */

	@Column(name = "CARSERIESNAME")
	public String getCarSeriesName() {
		return this.carSeriesName;
	}

	/**
	 * 属性车系名称的setter方法
	 */
	public void setCarSeriesName(String carSeriesName) {
		this.carSeriesName = carSeriesName;
	}

	/**
	 * 属性CARKIND的getter方法
	 */

	@Column(name = "CARKIND")
	public String getCarKind() {
		return this.carKind;
	}

	/**
	 * 属性CARKIND的setter方法
	 */
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	/**
	 * 属性CARSTYLE的getter方法
	 */

	@Column(name = "CARSTYLE")
	public String getCarStyle() {
		return this.carStyle;
	}

	/**
	 * 属性CARSTYLE的setter方法
	 */
	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	/**
	 * 属性ExhaustScale的getter方法
	 */

	@Column(name = "ExhaustScale")
	public Double getExhaustScale() {
		return this.exhaustScale;
	}

	/**
	 * 属性ExhaustScale的setter方法
	 */
	public void setExhaustScale(Double exhaustScale) {
		this.exhaustScale = exhaustScale;
	}

	/**
	 * 属性TONCOUNT的getter方法
	 */

	@Column(name = "TONCOUNT")
	public Double getTonCount() {
		return this.tonCount;
	}

	/**
	 * 属性TONCOUNT的setter方法
	 */
	public void setTonCount(Double tonCount) {
		this.tonCount = tonCount;
	}

	/**
	 * 属性核定载客的getter方法
	 */

	@Column(name = "SEATCOUNT")
	public Long getSeatCount() {
		return this.seatCount;
	}

	/**
	 * 属性核定载客的setter方法
	 */
	public void setSeatCount(Long seatCount) {
		if(seatCount!=null){
			this.seatCount = seatCount;
		}
	}

	/**
	 * 属性新车购置价(广信提供)的getter方法
	 */

	@Column(name = "PurchasePrice")
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * 属性新车购置价(广信提供)的setter方法
	 */
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 属性FACTORY的getter方法
	 */

	@Column(name = "FACTORY")
	public String getFactory() {
		return this.factory;
	}

	/**
	 * 属性FACTORY的setter方法
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	/**
	 * 属性CARYEAR的getter方法
	 */

	@Column(name = "CARYEAR")
	public String getCarYear() {
		return this.carYear;
	}

	/**
	 * 属性CARYEAR的setter方法
	 */
	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	/**
	 * 属性RISKSCOPE的getter方法
	 */

	@Column(name = "RISKSCOPE")
	public String getRiskScope() {
		return this.riskScope;
	}

	/**
	 * 属性RISKSCOPE的setter方法
	 */
	public void setRiskScope(String riskScope) {
		this.riskScope = riskScope;
	}

	/**
	 * 属性COUNTRYCODE的getter方法
	 */

	@Column(name = "COUNTRYCODE")
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * 属性COUNTRYCODE的setter方法
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 属性VALIDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性VALIDDATE的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 属性状态标志位（1有效、0无效）的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性状态标志位（1有效、0无效）的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性COEFFICIENT1的getter方法
	 */

	@Column(name = "COEFFICIENT1")
	public Double getCoefficient1() {
		return this.coefficient1;
	}

	/**
	 * 属性COEFFICIENT1的setter方法
	 */
	public void setCoefficient1(Double coefficient1) {
		this.coefficient1 = coefficient1;
	}

	/**
	 * 属性COEFFICIENT2的getter方法
	 */

	@Column(name = "COEFFICIENT2")
	public Double getCoefficient2() {
		return this.coefficient2;
	}

	/**
	 * 属性COEFFICIENT2的setter方法
	 */
	public void setCoefficient2(Double coefficient2) {
		this.coefficient2 = coefficient2;
	}
}
