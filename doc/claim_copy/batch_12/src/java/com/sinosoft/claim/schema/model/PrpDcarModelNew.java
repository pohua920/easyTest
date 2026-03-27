package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpDcarModelNew
 */
@Entity
@Table(name = "PRPDCARMODELNEW")
public class PrpDcarModelNew implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性shorthandcode */
	private String shorthandcode;

	/** 属性车型名称 */
	private String modelName;

	/** 属性别名查询码 */
	private String spellab;

	/** 属性标准型号编码 */
	private String modelId;

	/** 属性THEFTCLASS */
	private String theftClass;

	/** 属性DAMAGEDCLASS */
	private String damagedClass;

	/** 属性THEFTCLASSNEW */
	private String theftClassNew;

	/** 属性DAMAGEDCLASSNEW */
	private String damagedClassNew;

	/** 属性车型品牌 */
	private String carBrand;

	/** 属性车系名称 */
	private String carSeriesName;

	/** 属性CARKIND */
	private String carKind;

	/** 属性CARSTYLE */
	private String carStyle;

	/** 属性VEHAREA */
	private String vehArea;

	/** 属性ABSFLAG */
	private String absFlag;

	/** 属性THEFTPROOF */
	private String theftProof;

	/** 属性AIRBAGCOUNT */
	private Short airBagCount;

	/** 属性EXHAUSTSCALE */
	private Double exhaustscale;

	/** 属性TONCOUNT */
	private Double tonCount;

	/** 属性SEATMIN */
	private Long seatMin;

	/** 属性SEATMAX */
	private Long seatMax;

	/** 属性核定载客 */
	private Long seatCount;

	/** 属性新车购置价(广信提供) */
	private Double purChasePrice;

	/** 属性TRANSMISSIONTYPE */
	private String tranSmissionType;

	/** 属性PRODUCTSTATUS */
	private String productStatus;

	/** 属性FACTORY */
	private String factory;

	/** 属性CARYEAR */
	private String carYear;

	/** 属性RISKSCOPE */
	private String riskScope;

	/** 属性COEFFICIENT1 */
	private String coefficient1;

	/** 属性COEFFICIENT2 */
	private String coefficient2;

	/** 属性COUNTRYCODE */
	private String countryCode;

	/** 属性VALIDDATE */
	private Date validDate;

	/** 属性状态标志位（1有效、0无效） */
	private String validStatus;

	/** 属性REMARK */
	private String remark;

	/** 属性RISKTYPE */
	private String riskType;

	/** 属性DBRISKTYPE */
	private String dbRiskType;

	/** 属性STANDARDEQUIPMENT */
	private String standardequipment;

	/** 属性OPTIONALEQUIPMENT */
	private String optionalequipment;

	/** 属性FLAG */
	private String flag;

	/** 属性CURBWEIGHTMIN */
	private Double curbWeightMin;

	/** 属性CURBWEIGHTMAX */
	private Double curbWeightMax;

	/** 属性BRAND_ID */
	private String brandid;

	/** 属性SERIES_ID */
	private String seriesid;

	/** 属性XH_PRICE */
	private Double xhprice;

	/** 属性LB_PRICE */
	private Double lbprice;

	/** 属性HASRATIO */
	private String hasratio;

	/** 属性XH_PRETAX_PRICE */
	private Double xhpretaxprice;

	/** 属性LB_PRETAX_PRICE */
	private Double lbpretaxprice;

	/**
	 * 类PrpDcarModelNew的默认构造方法
	 */
	public PrpDcarModelNew() {
	}

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
	 * 属性shorthandcode的getter方法
	 */

	@Column(name = "SHORTHANDCODE")
	public String getShorthandcode() {
		return this.shorthandcode;
	}

	/**
	 * 属性shorthandcode的setter方法
	 */
	public void setShorthandcode(String shorthandcode) {
		this.shorthandcode = shorthandcode;
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
	 * 属性别名查询码的getter方法
	 */

	@Column(name = "SPELLAB")
	public String getSpellab() {
		return this.spellab;
	}

	/**
	 * 属性别名查询码的setter方法
	 */
	public void setSpellab(String spellab) {
		this.spellab = spellab;
	}

	/**
	 * 属性标准型号编码的getter方法
	 */

	@Column(name = "MODELID")
	public String getModelId() {
		return this.modelId;
	}

	/**
	 * 属性标准型号编码的setter方法
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * 属性THEFTCLASS的getter方法
	 */

	@Column(name = "THEFTCLASS")
	public String getTheftClass() {
		return this.theftClass;
	}

	/**
	 * 属性THEFTCLASS的setter方法
	 */
	public void setTheftClass(String theftClass) {
		this.theftClass = theftClass;
	}

	/**
	 * 属性DAMAGEDCLASS的getter方法
	 */

	@Column(name = "DAMAGEDCLASS")
	public String getDamagedClass() {
		return this.damagedClass;
	}

	/**
	 * 属性DAMAGEDCLASS的setter方法
	 */
	public void setDamagedClass(String damagedClass) {
		this.damagedClass = damagedClass;
	}

	/**
	 * 属性THEFTCLASSNEW的getter方法
	 */

	@Column(name = "THEFTCLASSNEW")
	public String getTheftClassNew() {
		return this.theftClassNew;
	}

	/**
	 * 属性THEFTCLASSNEW的setter方法
	 */
	public void setTheftClassNew(String theftClassNew) {
		this.theftClassNew = theftClassNew;
	}

	/**
	 * 属性DAMAGEDCLASSNEW的getter方法
	 */

	@Column(name = "DAMAGEDCLASSNEW")
	public String getDamagedClassNew() {
		return this.damagedClassNew;
	}

	/**
	 * 属性DAMAGEDCLASSNEW的setter方法
	 */
	public void setDamagedClassNew(String damagedClassNew) {
		this.damagedClassNew = damagedClassNew;
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
	 * 属性VEHAREA的getter方法
	 */

	@Column(name = "VEHAREA")
	public String getVehArea() {
		return this.vehArea;
	}

	/**
	 * 属性VEHAREA的setter方法
	 */
	public void setVehArea(String vehArea) {
		this.vehArea = vehArea;
	}

	/**
	 * 属性ABSFLAG的getter方法
	 */

	@Column(name = "ABSFLAG")
	public String getAbsFlag() {
		return this.absFlag;
	}

	/**
	 * 属性ABSFLAG的setter方法
	 */
	public void setAbsFlag(String absFlag) {
		this.absFlag = absFlag;
	}

	/**
	 * 属性THEFTPROOF的getter方法
	 */

	@Column(name = "THEFTPROOF")
	public String getTheftProof() {
		return this.theftProof;
	}

	/**
	 * 属性THEFTPROOF的setter方法
	 */
	public void setTheftProof(String theftProof) {
		this.theftProof = theftProof;
	}

	/**
	 * 属性AIRBAGCOUNT的getter方法
	 */

	@Column(name = "AIRBAGCOUNT")
	public Short getAirBagCount() {
		return this.airBagCount;
	}

	/**
	 * 属性AIRBAGCOUNT的setter方法
	 */
	public void setAirBagCount(Short airBagCount) {
		this.airBagCount = airBagCount;
	}

	/**
	 * 属性EXHAUSTSCALE的getter方法
	 */

	@Column(name = "EXHAUSTSCALE")
	public Double getExhaustscale() {
		return this.exhaustscale;
	}

	/**
	 * 属性EXHAUSTSCALE的setter方法
	 */
	public void setExhaustscale(Double exhaustscale) {
		this.exhaustscale = exhaustscale;
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
	 * 属性SEATMIN的getter方法
	 */

	@Column(name = "SEATMIN")
	public Long getSeatMin() {
		return this.seatMin;
	}

	/**
	 * 属性SEATMIN的setter方法
	 */
	public void setSeatMin(Long seatMin) {
		this.seatMin = seatMin;
	}

	/**
	 * 属性SEATMAX的getter方法
	 */

	@Column(name = "SEATMAX")
	public Long getSeatMax() {
		return this.seatMax;
	}

	/**
	 * 属性SEATMAX的setter方法
	 */
	public void setSeatMax(Long seatMax) {
		this.seatMax = seatMax;
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
		this.seatCount = seatCount;
	}

	/**
	 * 属性新车购置价(广信提供)的getter方法
	 */

	@Column(name = "PURCHASEPRICE")
	public Double getPurChasePrice() {
		return this.purChasePrice;
	}

	/**
	 * 属性新车购置价(广信提供)的setter方法
	 */
	public void setPurChasePrice(Double purChasePrice) {
		this.purChasePrice = purChasePrice;
	}

	/**
	 * 属性TRANSMISSIONTYPE的getter方法
	 */

	@Column(name = "TRANSMISSIONTYPE")
	public String getTranSmissionType() {
		return this.tranSmissionType;
	}

	/**
	 * 属性TRANSMISSIONTYPE的setter方法
	 */
	public void setTranSmissionType(String tranSmissionType) {
		this.tranSmissionType = tranSmissionType;
	}

	/**
	 * 属性PRODUCTSTATUS的getter方法
	 */

	@Column(name = "PRODUCTSTATUS")
	public String getProductStatus() {
		return this.productStatus;
	}

	/**
	 * 属性PRODUCTSTATUS的setter方法
	 */
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
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
	 * 属性COEFFICIENT1的getter方法
	 */

	@Column(name = "COEFFICIENT1")
	public String getCoefficient1() {
		return this.coefficient1;
	}

	/**
	 * 属性COEFFICIENT1的setter方法
	 */
	public void setCoefficient1(String coefficient1) {
		this.coefficient1 = coefficient1;
	}

	/**
	 * 属性COEFFICIENT2的getter方法
	 */

	@Column(name = "COEFFICIENT2")
	public String getCoefficient2() {
		return this.coefficient2;
	}

	/**
	 * 属性COEFFICIENT2的setter方法
	 */
	public void setCoefficient2(String coefficient2) {
		this.coefficient2 = coefficient2;
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
	 * 属性RISKTYPE的getter方法
	 */

	@Column(name = "RISKTYPE")
	public String getRiskType() {
		return this.riskType;
	}

	/**
	 * 属性RISKTYPE的setter方法
	 */
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	/**
	 * 属性DBRISKTYPE的getter方法
	 */

	@Column(name = "DBRISKTYPE")
	public String getDbRiskType() {
		return this.dbRiskType;
	}

	/**
	 * 属性DBRISKTYPE的setter方法
	 */
	public void setDbRiskType(String dbRiskType) {
		this.dbRiskType = dbRiskType;
	}

	/**
	 * 属性STANDARDEQUIPMENT的getter方法
	 */

	@Column(name = "STANDARDEQUIPMENT")
	public String getStandardequipment() {
		return this.standardequipment;
	}

	/**
	 * 属性STANDARDEQUIPMENT的setter方法
	 */
	public void setStandardequipment(String standardequipment) {
		this.standardequipment = standardequipment;
	}

	/**
	 * 属性OPTIONALEQUIPMENT的getter方法
	 */

	@Column(name = "OPTIONALEQUIPMENT")
	public String getOptionalequipment() {
		return this.optionalequipment;
	}

	/**
	 * 属性OPTIONALEQUIPMENT的setter方法
	 */
	public void setOptionalequipment(String optionalequipment) {
		this.optionalequipment = optionalequipment;
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
	 * 属性CURBWEIGHTMIN的getter方法
	 */

	@Column(name = "CURBWEIGHTMIN")
	public Double getCurbWeightMin() {
		return this.curbWeightMin;
	}

	/**
	 * 属性CURBWEIGHTMIN的setter方法
	 */
	public void setCurbWeightMin(Double curbWeightMin) {
		this.curbWeightMin = curbWeightMin;
	}

	/**
	 * 属性CURBWEIGHTMAX的getter方法
	 */

	@Column(name = "CURBWEIGHTMAX")
	public Double getCurbWeightMax() {
		return this.curbWeightMax;
	}

	/**
	 * 属性CURBWEIGHTMAX的setter方法
	 */
	public void setCurbWeightMax(Double curbWeightMax) {
		this.curbWeightMax = curbWeightMax;
	}

	/**
	 * 属性BRAND_ID的getter方法
	 */

	@Column(name = "BRAND_ID")
	public String getBrandid() {
		return this.brandid;
	}

	/**
	 * 属性BRAND_ID的setter方法
	 */
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	/**
	 * 属性SERIES_ID的getter方法
	 */

	@Column(name = "SERIES_ID")
	public String getSeriesid() {
		return this.seriesid;
	}

	/**
	 * 属性SERIES_ID的setter方法
	 */
	public void setSeriesid(String seriesid) {
		this.seriesid = seriesid;
	}

	/**
	 * 属性XH_PRICE的getter方法
	 */

	@Column(name = "XH_PRICE")
	public Double getXhprice() {
		return this.xhprice;
	}

	/**
	 * 属性XH_PRICE的setter方法
	 */
	public void setXhprice(Double xhprice) {
		this.xhprice = xhprice;
	}

	/**
	 * 属性LB_PRICE的getter方法
	 */

	@Column(name = "LB_PRICE")
	public Double getLbprice() {
		return this.lbprice;
	}

	/**
	 * 属性LB_PRICE的setter方法
	 */
	public void setLbprice(Double lbprice) {
		this.lbprice = lbprice;
	}

	/**
	 * 属性HASRATIO的getter方法
	 */

	@Column(name = "HASRATIO")
	public String getHasratio() {
		return this.hasratio;
	}

	/**
	 * 属性HASRATIO的setter方法
	 */
	public void setHasratio(String hasratio) {
		this.hasratio = hasratio;
	}

	/**
	 * 属性XH_PRETAX_PRICE的getter方法
	 */

	@Column(name = "XH_PRETAX_PRICE")
	public Double getXhpretaxprice() {
		return this.xhpretaxprice;
	}

	/**
	 * 属性XH_PRETAX_PRICE的setter方法
	 */
	public void setXhpretaxprice(Double xhpretaxprice) {
		this.xhpretaxprice = xhpretaxprice;
	}

	/**
	 * 属性LB_PRETAX_PRICE的getter方法
	 */

	@Column(name = "LB_PRETAX_PRICE")
	public Double getLbpretaxprice() {
		return this.lbpretaxprice;
	}

	/**
	 * 属性LB_PRETAX_PRICE的setter方法
	 */
	public void setLbpretaxprice(Double lbpretaxprice) {
		this.lbpretaxprice = lbpretaxprice;
	}

}
