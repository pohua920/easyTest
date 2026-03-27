package com.sinosoft.common.schema.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPQPE")
public class PrpQpe implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 属性id */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private PrpQpeId id;
	
	
	/** 属性险类代码 */
	private String riskCode;

	/** 寵物姓名 */
	private String name;

	/** 出生日期 */
	private Date birthday;

	/** 證明文件 */
	private String certifiedDocuments;
	
	/** 大頭照檔名 */
	private String picName;

	/** 晶片序號 */
	private String identifyNumber;

	/** 品種 */
	private String variety;

	/** 種類 */
	private String species;

	/** 年齡 */
	private Long age;

	/** 性別 1.male 公 2.female母 */
	private String sex;

	/** 體重 */
	private BigDecimal weight;

	/** 體重單位 */
	private String unit;

	/** 是否有投保其他公司寵物保險 */
	private String otherInsuredFlag;

	/** 投保其他保險公司數量 */
	private String otherInsuredAmount;

	/** 投保其他保險公司名稱 */
	private String otherInsuredCompany;
	
	/** FLAG*/
	 private String flag;
	 
	 /**
	  * PrpQmain
	  */
	 private PrpQmain prpQmain;
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposalNo", nullable = false, insertable = false, updatable = false)
	public PrpQmain getPrpQmain() {
		return this.prpQmain;
	}
	
	/**
	 * 属性PRPCPMAIN的setter方法
	 */
	public void setPrpQmain(PrpQmain prpQmain) {
		this.prpQmain = prpQmain;
	}
	
	/**
	 * 类PrpQpe的默认构造方法
	 */
	public PrpQpe() {
		
	}

	
	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpQpeId getId() {
		return this.id;
	}
	
	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpQpeId id) {
		this.id = id;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "CERTIFIEDDOCUMENTS")
	public String getCertifiedDocuments() {
		return certifiedDocuments;
	}

	public void setCertifiedDocuments(String certifiedDocuments) {
		this.certifiedDocuments = certifiedDocuments;
	}

	@Column(name = "PICNAME")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	@Column(name = "VARIETY")
	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	@Column(name = "SPECIES")
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	@Column(name = "AGE")
	public Long getAge() {
		return age;
	}
	
	public void setAge(Long age) {
		this.age = age;
	}

	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "WEIGHT")
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "OTHERINSUREDFLAG")
	public String getOtherInsuredFlag() {
		return otherInsuredFlag;
	}

	public void setOtherInsuredFlag(String otherInsuredFlag) {
		this.otherInsuredFlag = otherInsuredFlag;
	}

	@Column(name = "OTHERINSUREDAMOUNT")
	public String getOtherInsuredAmount() {
		return otherInsuredAmount;
	}

	public void setOtherInsuredAmount(String otherInsuredAmount) {
		this.otherInsuredAmount = otherInsuredAmount;
	}

	@Column(name = "OTHERINSUREDCOMPANY")
	public String getOtherInsuredCompany() {
		return otherInsuredCompany;
	}

	public void setOtherInsuredCompany(String otherInsuredCompany) {
		this.otherInsuredCompany = otherInsuredCompany;
	}

	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
