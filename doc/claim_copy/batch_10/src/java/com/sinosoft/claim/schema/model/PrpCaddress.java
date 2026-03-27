package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCaddress
 */
@Entity
@Table(name = "PRPCADDRESS")
public class PrpCaddress implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCaddressId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性地址编码 */
	private String addressCode;

	/** 属性地址 */
	private String addressName;

	/** 属性标志字段 */
	private String flag;

	/** 属性PROJECTNAME */
	private String projectName;

	/** 属性PROVINCECODE */
	private String provinceCode;

	/** 属性PROVINCENAME */
	private String provinceName;

	/** 属性PROVINCEFLAG */
	private String provinceFlag;

	/** 属性CITYCODE */
	private String cityCode;

	/** 属性DISTRICTFLAG */
	private String districtFlag;

	/** 属性REMARK */
	private String reMark;

	/** 属性DISTRICTCODE */
	private String districtCode;

	/** 属性DISTRICTNAME */
	private String districtName;

	/** 属性CITYFLAG */
	private String cityFlag;

	/** 属性CITYNAME */
	private String cityName;
	/** 同险代号 */
	private String sameAddressNo;
	/**
	 * 保险地址
	 */
	private String addressDetailInfo;

	/**
	 * 类PrpCaddress的默认构造方法
	 */
	public PrpCaddress() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "addressNo", column = @Column(name = "ADDRESSNO")) })
	public PrpCaddressId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCaddressId id) {
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
	 * 属性地址编码的getter方法
	 */

	@Column(name = "ADDRESSCODE")
	public String getAddressCode() {
		return this.addressCode;
	}

	/**
	 * 属性地址编码的setter方法
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	/**
	 * 属性地址的getter方法
	 */

	@Column(name = "ADDRESSNAME")
	public String getAddressName() {
		return this.addressName;
	}

	/**
	 * 属性地址的setter方法
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
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
	 * 属性PROJECTNAME的getter方法
	 */

	@Column(name = "PROJECTNAME")
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 属性PROJECTNAME的setter方法
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 属性PROVINCECODE的getter方法
	 */

	@Column(name = "PROVINCECODE")
	public String getProvinceCode() {
		return this.provinceCode;
	}

	/**
	 * 属性PROVINCECODE的setter方法
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * 属性PROVINCENAME的getter方法
	 */

	@Column(name = "PROVINCENAME")
	public String getProvinceName() {
		return this.provinceName;
	}

	/**
	 * 属性PROVINCENAME的setter方法
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * 属性PROVINCEFLAG的getter方法
	 */

	@Column(name = "PROVINCEFLAG")
	public String getProvinceFlag() {
		return this.provinceFlag;
	}

	/**
	 * 属性PROVINCEFLAG的setter方法
	 */
	public void setProvinceFlag(String provinceFlag) {
		this.provinceFlag = provinceFlag;
	}

	/**
	 * 属性CITYCODE的getter方法
	 */

	@Column(name = "CITYCODE")
	public String getCityCode() {
		return this.cityCode;
	}

	/**
	 * 属性CITYCODE的setter方法
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 属性DISTRICTFLAG的getter方法
	 */

	@Column(name = "DISTRICTFLAG")
	public String getDistrictFlag() {
		return this.districtFlag;
	}

	/**
	 * 属性DISTRICTFLAG的setter方法
	 */
	public void setDistrictFlag(String districtFlag) {
		this.districtFlag = districtFlag;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getReMark() {
		return this.reMark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	/**
	 * 属性DISTRICTCODE的getter方法
	 */

	@Column(name = "DISTRICTCODE")
	public String getDistrictCode() {
		return this.districtCode;
	}

	/**
	 * 属性DISTRICTCODE的setter方法
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * 属性DISTRICTNAME的getter方法
	 */

	@Column(name = "DISTRICTNAME")
	public String getDistrictName() {
		return this.districtName;
	}

	/**
	 * 属性DISTRICTNAME的setter方法
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * 属性CITYFLAG的getter方法
	 */

	@Column(name = "CITYFLAG")
	public String getCityFlag() {
		return this.cityFlag;
	}

	/**
	 * 属性CITYFLAG的setter方法
	 */
	public void setCityFlag(String cityFlag) {
		this.cityFlag = cityFlag;
	}

	/**
	 * 属性CITYNAME的getter方法
	 */

	@Column(name = "CITYNAME")
	public String getCityName() {
		return this.cityName;
	}

	/**
	 * 属性CITYNAME的setter方法
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(name = "sameAddressNo")
	public String getSameAddressNo() {
		return sameAddressNo;
	}

	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}
	@Column(name = "addressDetailInfo")
	public String getAddressDetailInfo() {
		return addressDetailInfo;
	}

	public void setAddressDetailInfo(String addressDetailInfo) {
		this.addressDetailInfo = addressDetailInfo;
	}

}
