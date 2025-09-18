package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 	SOURCE_TYPE 來源，必填。 
 * 	SOURCE_USER 使用者，必填。
 *  CALC_TYPE 試算類型，必填。
 *  CALC_DATE 費率基準日，必填。 YYYYMMDD 
 *  CHANNEL_TYPE 通路別，必填。
 * 
 * @author bi086
 * 
 */
@XmlRootElement
public class PbPremWsParamVo {

	private String sourceType;
	private String sourceUser;
	private String calcType;
	private String calcDate;
	private String businessCategory;
	private String buildarea;
	private String premisesQuantity;
	private String daysActivity;
	private String deductible;
	private String businessPremises;
	private String peraccidentdeaths;
	private String peraccidentinjury;
	private String peraccidentdamage;
	private String periodmaxamount;

	private ArrayList<PbInsPremVo> insPremList;
	private ArrayList<PbCkListVo> pbCkList;
	private ArrayList<ClauseVo> clauseList;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public String getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(String calcDate) {
		this.calcDate = calcDate;
	}

	public ArrayList<PbCkListVo> getPbCkList() {
		return pbCkList;
	}

	public void setPbCkList(ArrayList<PbCkListVo> pbCkList) {
		this.pbCkList = pbCkList;
	}

	public ArrayList<ClauseVo> getClauseList() {
		return clauseList;
	}

	public void setClauseList(ArrayList<ClauseVo> clauseList) {
		this.clauseList = clauseList;
	}

	public ArrayList<PbInsPremVo> getInsPremList() {
		return insPremList;
	}

	public void setInsPremList(ArrayList<PbInsPremVo> insPremList) {
		this.insPremList = insPremList;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBuildarea() {
		return buildarea;
	}

	public void setBuildarea(String buildarea) {
		this.buildarea = buildarea;
	}

	public String getPremisesQuantity() {
		return premisesQuantity;
	}

	public void setPremisesQuantity(String premisesQuantity) {
		this.premisesQuantity = premisesQuantity;
	}

	public String getDeductible() {
		return deductible;
	}

	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

	public String getBusinessPremises() {
		return businessPremises;
	}

	public void setBusinessPremises(String businessPremises) {
		this.businessPremises = businessPremises;
	}

	public String getPeraccidentdeaths() {
		return peraccidentdeaths;
	}

	public void setPeraccidentdeaths(String peraccidentdeaths) {
		this.peraccidentdeaths = peraccidentdeaths;
	}

	public String getPeraccidentinjury() {
		return peraccidentinjury;
	}

	public void setPeraccidentinjury(String peraccidentinjury) {
		this.peraccidentinjury = peraccidentinjury;
	}

	public String getPeraccidentdamage() {
		return peraccidentdamage;
	}

	public void setPeraccidentdamage(String peraccidentdamage) {
		this.peraccidentdamage = peraccidentdamage;
	}

	public String getPeriodmaxamount() {
		return periodmaxamount;
	}

	public void setPeriodmaxamount(String periodmaxamount) {
		this.periodmaxamount = periodmaxamount;
	}

	public String getDaysActivity() {
		return daysActivity;
	}

	public void setDaysActivity(String daysActivity) {
		this.daysActivity = daysActivity;
	}

}
