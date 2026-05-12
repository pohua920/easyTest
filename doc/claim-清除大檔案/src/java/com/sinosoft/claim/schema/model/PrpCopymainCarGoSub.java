// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCopymainCarGoSub
 */
@Entity
@Table(name = "PRPCOPYMAINCARGOSUB")
public class PrpCopymainCarGoSub implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCopymainCarGoSubId id;
	
	/** 属性保单号 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性运输工具 */
	private String conveyance;// 水险CL借用 车种 

	/** 属性车牌号ss */
	private String licenseNo;

	/** 属性厂牌型号名称 */
	private String brandName;// 水险CL借用 车种名称 

	/** 属性吨位数 */
	private BigDecimal tonCount;

	/** 属性行驶里程(公里) */
	private BigDecimal runMiles;

	/** 属性火车车次 */
	private String trainNo;// 水险CL借用 车险保单号 

	/** 属性火车车号 */
	private String trainName;

	/** 属性航次 */
	private String voyageNo;

	/** 属性船名 */
	private String shipName;// 水险CL借用 引擎号

	/** 属性船龄 */
	private BigDecimal shipAge;

	/** 属性航班号 */
	private String flightNo;

	/** 属性飞机型号 */
	private String planeModel;

	/** 属性SITENAME */
	private String siteName;// 水险CL借用 运送地区

	/** 属性备注 */
	private String remark;

	/** 属性短信转存后是否删除标记 */
	private String flag;
	/** 港口代碼 */
	private String portCode;
	/** 港口名稱 */
	private String portName;
	/** 属性國家 */
	private String countries;

	/** 属性老船龄加费费率 */
	private BigDecimal shipRate;

	/** 原始船名 */
	private String baseSiteName;

	/** 原始航次 */
	private String baseVoyageNo;

	/** 原始备注 */
	private String baseRemark;

	/**
	 * 类PrpCopymainCarGoSub的默认构造方法
	 */
	public PrpCopymainCarGoSub() {
		this.id = new PrpCopymainCarGoSubId();
	}

	public PrpCopymainCarGoSub(String policyNo, Integer serialNo) {
		this.id = new PrpCopymainCarGoSubId(policyNo, serialNo);
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCopymainCarGoSubId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCopymainCarGoSubId id) {
		this.id = id;
	}
	
	/**
	 * 属性保单号的getter方法
	 */
	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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
	 * 属性运输工具的getter方法
	 */

	@Column(name = "CONVEYANCE")
	public String getConveyance() {
		return this.conveyance;
	}

	/**
	 * 属性运输工具的setter方法
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 属性车牌号ss的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号ss的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性厂牌型号名称的getter方法
	 */

	@Column(name = "BRANDNAME")
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 属性厂牌型号名称的setter方法
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 属性吨位数的getter方法
	 */

	@Column(name = "TONCOUNT")
	public BigDecimal getTonCount() {
		return this.tonCount;
	}

	/**
	 * 属性吨位数的setter方法
	 */
	public void setTonCount(BigDecimal tonCount) {
		this.tonCount = tonCount;
	}

	/**
	 * 属性行驶里程(公里)的getter方法
	 */

	@Column(name = "RUNMILES")
	public BigDecimal getRunMiles() {
		return this.runMiles;
	}

	/**
	 * 属性行驶里程(公里)的setter方法
	 */
	public void setRunMiles(BigDecimal runMiles) {
		this.runMiles = runMiles;
	}

	/**
	 * 属性火车车次的getter方法
	 */

	@Column(name = "TRAINNO")
	public String getTrainNo() {
		return this.trainNo;
	}

	/**
	 * 属性火车车次的setter方法
	 */
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	/**
	 * 属性火车车号的getter方法
	 */

	@Column(name = "TRAINNAME")
	public String getTrainName() {
		return this.trainName;
	}

	/**
	 * 属性火车车号的setter方法
	 */
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	/**
	 * 属性航次的getter方法
	 */

	@Column(name = "VOYAGENO")
	public String getVoyageNo() {
		return this.voyageNo;
	}

	/**
	 * 属性航次的setter方法
	 */
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	/**
	 * 属性船名的getter方法
	 */

	@Column(name = "SHIPNAME")
	public String getShipName() {
		return this.shipName;
	}

	/**
	 * 属性船名的setter方法
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	/**
	 * 属性船龄的getter方法
	 */

	@Column(name = "SHIPAGE")
	public BigDecimal getShipAge() {
		return this.shipAge;
	}

	/**
	 * 属性船龄的setter方法
	 */
	public void setShipAge(BigDecimal shipAge) {
		this.shipAge = shipAge;
	}

	/**
	 * 属性航班号的getter方法
	 */

	@Column(name = "FLIGHTNO")
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * 属性航班号的setter方法
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 属性飞机型号的getter方法
	 */

	@Column(name = "PLANEMODEL")
	public String getPlaneModel() {
		return this.planeModel;
	}

	/**
	 * 属性飞机型号的setter方法
	 */
	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}

	/**
	 * 属性SITENAME的getter方法
	 */

	@Column(name = "SITENAME")
	public String getSiteName() {
		return this.siteName;
	}

	/**
	 * 属性SITENAME的setter方法
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	 * 属性短信转存后是否删除标记的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性短信转存后是否删除标记的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性老船龄加费费率的getter方法
	 */

	@Column(name = "SHIPRATE")
	public BigDecimal getShipRate() {
		return this.shipRate;
	}

	/**
	 * 属性老船龄加费费率的setter方法
	 */
	public void setShipRate(BigDecimal shipRate) {
		this.shipRate = shipRate;
	}

	@Column(name = "PORTCODE")
	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	@Column(name = "PORTNAME")
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	@Column(name = "COUNTRIES")
	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	@Column(name = "BASESITENAME")
	public String getBaseSiteName() {
		return baseSiteName;
	}

	public void setBaseSiteName(String baseSiteName) {
		this.baseSiteName = baseSiteName;
	}

	@Column(name = "BASEVOYAGENO")
	public String getBaseVoyageNo() {
		return baseVoyageNo;
	}

	public void setBaseVoyageNo(String baseVoyageNo) {
		this.baseVoyageNo = baseVoyageNo;
	}

	@Column(name = "BASEREMARK")
	public String getBaseRemark() {
		return baseRemark;
	}

	public void setBaseRemark(String baseRemark) {
		this.baseRemark = baseRemark;
	}

}
