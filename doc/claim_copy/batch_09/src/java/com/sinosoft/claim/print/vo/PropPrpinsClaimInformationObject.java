package com.sinosoft.claim.print.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 火險承保理賠信息 數據對象
 * @author 中科軟
 *
 */
public class PropPrpinsClaimInformationObject {
	/**備案號碼*/
	private String registNo;
	/**保單號碼*/
	private String policyNo;
	/**被保險人*/
	private String insuranceName;
	/**保險標的物地址*/
	private String itemKindAddress;
	/**投保日期*/
	private String operateDate;
	/**輸入日期*/
	private String underWriteEndDate;
	/**簽單日期*/
	private String signDate;
	/**出單日期*/
	private String inputDate;
	/**保險期間*/
	private String insurancePeriod;
	/**是否共保*/
	private String isCoinsFlag;
	/**特別約定*/
	private String specialAgreement;
	/**經辦人*/
	private String handleName;
	/**列印日期*/
	private String printTime;
	/** 火險承保理賠信息 數據對象 子報表數據 （主險）*/
	List<PropPrpinsClaimInformationSubOneObject> propPrpinsClaimInformationSubOneObjectList = new ArrayList<PropPrpinsClaimInformationSubOneObject>();
	/**火險承保理賠信息 數據對象 子報表數據 （附加險）*/
	List<PropPrpinsClaimInformationSubTwoObject> propPrpinsClaimInformationSubTwoObjectList = new ArrayList<PropPrpinsClaimInformationSubTwoObject>();
	/**火險承保理賠信息 數據對象 子報表數據 （批改情況）*/
	List<PropPrpinsClaimInformationSubThreeObject> propPrpinsClaimInformationSubThreeObjectList = new ArrayList<PropPrpinsClaimInformationSubThreeObject>();
	/**火險承保理賠信息 數據對象 子報表數據 （收費情況）*/
	List<PropPrpinsClaimInformationSubFourObject> propPrpinsClaimInformationSubFourObjectList = new ArrayList<PropPrpinsClaimInformationSubFourObject>();
	/**火險承保理賠信息 數據對象 子報表數據 （賠付記錄）*/
	List<PropPrpinsClaimInformationSubFiveObject> propPrpinsClaimInformationSubFiveObjectList = new ArrayList<PropPrpinsClaimInformationSubFiveObject>();
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public String getItemKindAddress() {
		return itemKindAddress;
	}
	public void setItemKindAddress(String itemKindAddress) {
		this.itemKindAddress = itemKindAddress;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getUnderWriteEndDate() {
		return underWriteEndDate;
	}
	public void setUnderWriteEndDate(String underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getInsurancePeriod() {
		return insurancePeriod;
	}
	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	public String getIsCoinsFlag() {
		return isCoinsFlag;
	}
	public void setIsCoinsFlag(String isCoinsFlag) {
		this.isCoinsFlag = isCoinsFlag;
	}
	public String getSpecialAgreement() {
		return specialAgreement;
	}
	public void setSpecialAgreement(String specialAgreement) {
		this.specialAgreement = specialAgreement;
	}
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public List<PropPrpinsClaimInformationSubOneObject> getPropPrpinsClaimInformationSubOneObjectList() {
		return propPrpinsClaimInformationSubOneObjectList;
	}
	public void setPropPrpinsClaimInformationSubOneObjectList(List<PropPrpinsClaimInformationSubOneObject> propPrpinsClaimInformationSubOneObjectList) {
		this.propPrpinsClaimInformationSubOneObjectList = propPrpinsClaimInformationSubOneObjectList;
	}
	public List<PropPrpinsClaimInformationSubTwoObject> getPropPrpinsClaimInformationSubTwoObjectList() {
		return propPrpinsClaimInformationSubTwoObjectList;
	}
	public void setPropPrpinsClaimInformationSubTwoObjectList(List<PropPrpinsClaimInformationSubTwoObject> propPrpinsClaimInformationSubTwoObjectList) {
		this.propPrpinsClaimInformationSubTwoObjectList = propPrpinsClaimInformationSubTwoObjectList;
	}
	public List<PropPrpinsClaimInformationSubThreeObject> getPropPrpinsClaimInformationSubThreeObjectList() {
		return propPrpinsClaimInformationSubThreeObjectList;
	}
	public void setPropPrpinsClaimInformationSubThreeObjectList(List<PropPrpinsClaimInformationSubThreeObject> propPrpinsClaimInformationSubThreeObjectList) {
		this.propPrpinsClaimInformationSubThreeObjectList = propPrpinsClaimInformationSubThreeObjectList;
	}
	public List<PropPrpinsClaimInformationSubFourObject> getPropPrpinsClaimInformationSubFourObjectList() {
		return propPrpinsClaimInformationSubFourObjectList;
	}
	public void setPropPrpinsClaimInformationSubFourObjectList(List<PropPrpinsClaimInformationSubFourObject> propPrpinsClaimInformationSubFourObjectList) {
		this.propPrpinsClaimInformationSubFourObjectList = propPrpinsClaimInformationSubFourObjectList;
	}
	public List<PropPrpinsClaimInformationSubFiveObject> getPropPrpinsClaimInformationSubFiveObjectList() {
		return propPrpinsClaimInformationSubFiveObjectList;
	}
	public void setPropPrpinsClaimInformationSubFiveObjectList(List<PropPrpinsClaimInformationSubFiveObject> propPrpinsClaimInformationSubFiveObjectList) {
		this.propPrpinsClaimInformationSubFiveObjectList = propPrpinsClaimInformationSubFiveObjectList;
	}
	
}
