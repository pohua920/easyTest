package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "PrpDriskRate")
public class PrpDriskRate implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private PrpDriskRateId id;
	private String ratecName;
	/** 通路别的值 10---11-12-13
		20---2开头的
		30---3开头的
		40是11-12-13并勾选直接业务
	 */
	private String tcol1;
	/**
	 * 職業類別
	 * */
	private String tcol2;
	private String tcol3;
	private String tcol4;
	private String tcol5;
	private String tcol6;
	private String tcol7;
	private String tcol8;
	private String tcol9;
	private String tcol10;
	private Double basepRemium;
	private String rateUnit;
	private Double rate;
	private Date validDate;
	private Date invalidDate;
	private String areamapPingc;
	private String areaLevel;
	private String areaCode;
	private String areaName;
	private String remark;
	private String flag;
	private String tcol11;
	private String tcol12;
	private String tcol13;
	private String tcol14;
	private String tcol15;
	private String tcol16;
	private String tcol17;
	private String tcol18;
	private String tcol19;
	private String tcol20;
	private Double officialPremium;
	private Double specialfundrate;
	private String specialfundrateunit;
	private Double stablefundrate;
	private String stablefundrateunit;
	private Double businessCost;
	private Double improveCost;
	private Double totalCost;
	private Double rateperiod;
	private Double purePremium;
	private Double freedepreCoeff;
	private String ratevalidDate;
	private Double addCostrateunit;
	private Double addCostrate;
	private Double highcoverage;
	private Double multiple;
	private String purerateUnit;
	private Double purerate;
	private Double conversionCoeff;
	
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
		@AttributeOverride(name = "clauseCode", column = @Column(name = "clauseCode")),
		@AttributeOverride(name = "kindCode", column = @Column(name = "kindCode")),
		@AttributeOverride(name = "rateCode", column = @Column(name = "rateCode")),
		@AttributeOverride(name = "serialNo", column = @Column(name = "serialNo"))})
	public PrpDriskRateId getId() {
		return id;
	}
	public void setId(PrpDriskRateId id) {
		this.id = id;
	}
	@Column(name = "ratecName")
	public String getRatecName() {
		return ratecName;
	}
	public void setRatecName(String ratecName) {
		this.ratecName = ratecName;
	}
	@Column(name = "tcol1")
	public String getTcol1() {
		return tcol1;
	}
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name = "tcol2")
	public String getTcol2() {
		return tcol2;
	}
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	@Column(name = "tcol3")
	public String getTcol3() {
		return tcol3;
	}
	
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}
	@Column(name = "tcol4")
	public String getTcol4() {
		return tcol4;
	}
	public void setTcol4(String tcol4) {
		this.tcol4 = tcol4;
	}
	@Column(name = "tcol5")
	public String getTcol5() {
		return tcol5;
	}
	public void setTcol5(String tcol5) {
		this.tcol5 = tcol5;
	}
	@Column(name = "tcol6")
	public String getTcol6() {
		return tcol6;
	}
	public void setTcol6(String tcol6) {
		this.tcol6 = tcol6;
	}
	@Column(name = "tcol7")
	public String getTcol7() {
		return tcol7;
	}
	public void setTcol7(String tcol7) {
		this.tcol7 = tcol7;
	}
	@Column(name = "tcol8")
	public String getTcol8() {
		return tcol8;
	}
	public void setTcol8(String tcol8) {
		this.tcol8 = tcol8;
	}
	@Column(name = "tcol9")
	public String getTcol9() {
		return tcol9;
	}
	public void setTcol9(String tcol9) {
		this.tcol9 = tcol9;
	}
	@Column(name = "tcol10")
	public String getTcol10() {
		return tcol10;
	}
	public void setTcol10(String tcol10) {
		this.tcol10 = tcol10;
	}
	@Column(name = "basepRemium")
	public Double getBasepRemium() {
		return basepRemium;
	}
	public void setBasepRemium(Double basepRemium) {
		this.basepRemium = basepRemium;
	}
	@Column(name = "rateUnit")
	public String getRateUnit() {
		return rateUnit;
	}
	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}
	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@Column(name = "validDate")
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	@Column(name = "invalidDate")
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	@Column(name = "areamapPingc")
	public String getAreamapPingc() {
		return areamapPingc;
	}
	public void setAreamapPingc(String areamapPingc) {
		this.areamapPingc = areamapPingc;
	}
	@Column(name = "areaLevel")
	public String getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}
	@Column(name = "areaCode")
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	@Column(name = "areaName")
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "tcol11")
	public String getTcol11() {
		return tcol11;
	}
	public void setTcol11(String tcol11) {
		this.tcol11 = tcol11;
	}
	@Column(name = "tcol12")
	public String getTcol12() {
		return tcol12;
	}
	public void setTcol12(String tcol12) {
		this.tcol12 = tcol12;
	}
	@Column(name = "tcol13")
	public String getTcol13() {
		return tcol13;
	}
	public void setTcol13(String tcol13) {
		this.tcol13 = tcol13;
	}
	@Column(name = "tcol14")
	public String getTcol14() {
		return tcol14;
	}
	public void setTcol14(String tcol14) {
		this.tcol14 = tcol14;
	}
	@Column(name = "tcol15")
	public String getTcol15() {
		return tcol15;
	}
	public void setTcol15(String tcol15) {
		this.tcol15 = tcol15;
	}
	@Column(name = "tcol16")
	public String getTcol16() {
		return tcol16;
	}
	public void setTcol16(String tcol16) {
		this.tcol16 = tcol16;
	}
	@Column(name = "tcol17")
	public String getTcol17() {
		return tcol17;
	}
	public void setTcol17(String tcol17) {
		this.tcol17 = tcol17;
	}
	@Column(name = "tcol18")
	public String getTcol18() {
		return tcol18;
	}
	public void setTcol18(String tcol18) {
		this.tcol18 = tcol18;
	}
	@Column(name = "tcol19")
	public String getTcol19() {
		return tcol19;
	}
	public void setTcol19(String tcol19) {
		this.tcol19 = tcol19;
	}
	@Column(name = "tcol20")
	public String getTcol20() {
		return tcol20;
	}
	public void setTcol20(String tcol20) {
		this.tcol20 = tcol20;
	}
	@Column(name = "officialPremium")
	public Double getOfficialPremium() {
		return officialPremium;
	}
	public void setOfficialPremium(Double officialPremium) {
		this.officialPremium = officialPremium;
	}
	@Column(name = "specialfundrate")
	public Double getSpecialfundrate() {
		return specialfundrate;
	}
	public void setSpecialfundrate(Double specialfundrate) {
		this.specialfundrate = specialfundrate;
	}
	@Column(name = "specialfundrateunit")
	public String getSpecialfundrateunit() {
		return specialfundrateunit;
	}
	public void setSpecialfundrateunit(String specialfundrateunit) {
		this.specialfundrateunit = specialfundrateunit;
	}
	@Column(name = "stablefundrate")
	public Double getStablefundrate() {
		return stablefundrate;
	}
	public void setStablefundrate(Double stablefundrate) {
		this.stablefundrate = stablefundrate;
	}
	@Column(name = "stablefundrateunit")
	public String getStablefundrateunit() {
		return stablefundrateunit;
	}
	public void setStablefundrateunit(String stablefundrateunit) {
		this.stablefundrateunit = stablefundrateunit;
	}
	@Column(name = "businessCost")
	public Double getBusinessCost() {
		return businessCost;
	}
	public void setBusinessCost(Double businessCost) {
		this.businessCost = businessCost;
	}
	@Column(name = "improveCost")
	public Double getImproveCost() {
		return improveCost;
	}
	public void setImproveCost(Double improveCost) {
		this.improveCost = improveCost;
	}
	@Column(name = "totalCost")
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	@Column(name = "rateperiod")
	public Double getRateperiod() {
		return rateperiod;
	}
	public void setRateperiod(Double rateperiod) {
		this.rateperiod = rateperiod;
	}
	@Column(name = "purePremium")
	public Double getPurePremium() {
		return purePremium;
	}
	public void setPurePremium(Double purePremium) {
		this.purePremium = purePremium;
	}
	@Column(name = "freedepreCoeff")
	public Double getFreedepreCoeff() {
		return freedepreCoeff;
	}
	public void setFreedepreCoeff(Double freedepreCoeff) {
		this.freedepreCoeff = freedepreCoeff;
	}
	@Column(name = "ratevalidDate")
	public String getRatevalidDate() {
		return ratevalidDate;
	}
	public void setRatevalidDate(String ratevalidDate) {
		this.ratevalidDate = ratevalidDate;
	}
	@Column(name = "addCostrateunit")
	public Double getAddCostrateunit() {
		return addCostrateunit;
	}
	public void setAddCostrateunit(Double addCostrateunit) {
		this.addCostrateunit = addCostrateunit;
	}
	@Column(name = "addCostrate")
	public Double getAddCostrate() {
		return addCostrate;
	}
	public void setAddCostrate(Double addCostrate) {
		this.addCostrate = addCostrate;
	}
	@Column(name = "highcoverage")
	public Double getHighcoverage() {
		return highcoverage;
	}
	public void setHighcoverage(Double highcoverage) {
		this.highcoverage = highcoverage;
	}
	@Column(name = "multiple")
	public Double getMultiple() {
		return multiple;
	}
	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}
	@Column(name = "purerateUnit")
	public String getPurerateUnit() {
		return purerateUnit;
	}
	public void setPurerateUnit(String purerateUnit) {
		this.purerateUnit = purerateUnit;
	}
	@Column(name = "purerate")
	public Double getPurerate() {
		return purerate;
	}
	public void setPurerate(Double purerate) {
		this.purerate = purerate;
	}
	@Column(name = "conversionCoeff")
	public Double getConversionCoeff() {
		return conversionCoeff;
	}
	public void setConversionCoeff(Double conversionCoeff) {
		this.conversionCoeff = conversionCoeff;
	}

}
