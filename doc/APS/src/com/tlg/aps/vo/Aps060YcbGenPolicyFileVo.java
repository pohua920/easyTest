package com.tlg.aps.vo;

import java.util.Date;

/**mantis：FIR0668，處理人員：CF048，需求單編號：FIR0668_住火_元大續保作業phaseII_續件資料處理作業  
 * 元大續保作業phaseII_續件資料處理作業  產生保單明細、副本*/
public class Aps060YcbGenPolicyFileVo {

	/*出單明細欄位*/
	private String batchNo;
	private String prpinsBatchNo;
	private String collateralNo;
	private String policyNo;
	private String ubn;
	private Date startDate;
	private Date endDate; 
	
	private String currency;
	private String sumAmount;
	private String amountF;
	private String amountQ;
	private String holderIDs;
	private String holderNames;
	private String insuredIDs;
	private String insuredNames;
	private String oldPolicyNo;
	private String sumPermium;
	
	private String lastPermium;
	private String lastAmountF;
	private String lastAmountQ;
	
	/*保單副本欄位*/
	private String premiumF;
	private String premiumQ;
	private String telphone;
	private String insuredID1;
	private String insuredName1;
	private String insuredName2;
	private String holderID1;
	private String holderName1;
	private String holderName2;
	private String postCode;
	private String postAddress;
	private String postCode2;
	private String postAddress2;
	private String addressCode;
	private String addressDetailInfo;
	private String addressCode2;
	private String addressDetailInfo2;
	private String mortgageeBank1;
	private String mortgageeBank2;

	private String buildStructure;
	private String buildDetailInfo;
	private String buildArea;
	private String buildYears;
	
	private String rate;
	private String possessNatureCode;
	private String structureLevel;
	private Date signDate;
	private String paymentArea;
	private String paymentUnit;
	private String paymentResp;
	private String handleUser;
	private String handle1Code;
	private String virtualCode;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getPrpinsBatchNo() {
		return prpinsBatchNo;
	}
	public void setPrpinsBatchNo(String prpinsBatchNo) {
		this.prpinsBatchNo = prpinsBatchNo;
	}
	public String getCollateralNo() {
		return collateralNo;
	}
	public void setCollateralNo(String collateralNo) {
		this.collateralNo = collateralNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getUbn() {
		return ubn;
	}
	public void setUbn(String ubn) {
		this.ubn = ubn;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getAmountF() {
		return amountF;
	}
	public void setAmountF(String amountF) {
		this.amountF = amountF;
	}
	public String getAmountQ() {
		return amountQ;
	}
	public void setAmountQ(String amountQ) {
		this.amountQ = amountQ;
	}
	public String getHolderIDs() {
		return holderIDs;
	}
	public void setHolderIDs(String holderIDs) {
		this.holderIDs = holderIDs;
	}
	public String getHolderNames() {
		return holderNames;
	}
	public void setHolderNames(String holderNames) {
		this.holderNames = holderNames;
	}
	public String getInsuredIDs() {
		return insuredIDs;
	}
	public void setInsuredIDs(String insuredIDs) {
		this.insuredIDs = insuredIDs;
	}
	public String getInsuredNames() {
		return insuredNames;
	}
	public void setInsuredNames(String insuredNames) {
		this.insuredNames = insuredNames;
	}
	public String getOldPolicyNo() {
		return oldPolicyNo;
	}
	public void setOldPolicyNo(String oldPolicyNo) {
		this.oldPolicyNo = oldPolicyNo;
	}
	public String getSumPermium() {
		return sumPermium;
	}
	public void setSumPermium(String sumPermium) {
		this.sumPermium = sumPermium;
	}
	public String getLastPermium() {
		return lastPermium;
	}
	public void setLastPermium(String lastPermium) {
		this.lastPermium = lastPermium;
	}
	public String getLastAmountF() {
		return lastAmountF;
	}
	public void setLastAmountF(String lastAmountF) {
		this.lastAmountF = lastAmountF;
	}
	public String getLastAmountQ() {
		return lastAmountQ;
	}
	public void setLastAmountQ(String lastAmountQ) {
		this.lastAmountQ = lastAmountQ;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getInsuredID1() {
		return insuredID1;
	}
	public void setInsuredID1(String insuredID1) {
		this.insuredID1 = insuredID1;
	}
	public String getInsuredName1() {
		return insuredName1;
	}
	public void setInsuredName1(String insuredName1) {
		this.insuredName1 = insuredName1;
	}
	public String getInsuredName2() {
		return insuredName2;
	}
	public void setInsuredName2(String insuredName2) {
		this.insuredName2 = insuredName2;
	}
	public String getHolderID1() {
		return holderID1;
	}
	public void setHolderID1(String holderID1) {
		this.holderID1 = holderID1;
	}
	public String getHolderName1() {
		return holderName1;
	}
	public void setHolderName1(String holderName1) {
		this.holderName1 = holderName1;
	}
	public String getHolderName2() {
		return holderName2;
	}
	public void setHolderName2(String holderName2) {
		this.holderName2 = holderName2;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	public String getPostCode2() {
		return postCode2;
	}
	public void setPostCode2(String postCode2) {
		this.postCode2 = postCode2;
	}
	public String getPostAddress2() {
		return postAddress2;
	}
	public void setPostAddress2(String postAddress2) {
		this.postAddress2 = postAddress2;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getAddressDetailInfo() {
		return addressDetailInfo;
	}
	public void setAddressDetailInfo(String addressDetailInfo) {
		this.addressDetailInfo = addressDetailInfo;
	}
	public String getAddressCode2() {
		return addressCode2;
	}
	public void setAddressCode2(String addressCode2) {
		this.addressCode2 = addressCode2;
	}
	public String getAddressDetailInfo2() {
		return addressDetailInfo2;
	}
	public void setAddressDetailInfo2(String addressDetailInfo2) {
		this.addressDetailInfo2 = addressDetailInfo2;
	}
	public String getMortgageeBank1() {
		return mortgageeBank1;
	}
	public void setMortgageeBank1(String mortgageeBank1) {
		this.mortgageeBank1 = mortgageeBank1;
	}
	public String getMortgageeBank2() {
		return mortgageeBank2;
	}
	public void setMortgageeBank2(String mortgageeBank2) {
		this.mortgageeBank2 = mortgageeBank2;
	}
	public String getBuildStructure() {
		return buildStructure;
	}
	public void setBuildStructure(String buildStructure) {
		this.buildStructure = buildStructure;
	}
	public String getBuildDetailInfo() {
		return buildDetailInfo;
	}
	public void setBuildDetailInfo(String buildDetailInfo) {
		this.buildDetailInfo = buildDetailInfo;
	}
	public String getBuildArea() {
		return buildArea;
	}
	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}
	public String getBuildYears() {
		return buildYears;
	}
	public void setBuildYears(String buildYears) {
		this.buildYears = buildYears;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPossessNatureCode() {
		return possessNatureCode;
	}
	public void setPossessNatureCode(String possessNatureCode) {
		this.possessNatureCode = possessNatureCode;
	}
	public String getStructureLevel() {
		return structureLevel;
	}
	public void setStructureLevel(String structureLevel) {
		this.structureLevel = structureLevel;
	}
	
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	public String getPaymentArea() {
		return paymentArea;
	}
	public void setPaymentArea(String paymentArea) {
		this.paymentArea = paymentArea;
	}
	public String getPaymentUnit() {
		return paymentUnit;
	}
	public void setPaymentUnit(String paymentUnit) {
		this.paymentUnit = paymentUnit;
	}
	public String getPaymentResp() {
		return paymentResp;
	}
	public void setPaymentResp(String paymentResp) {
		this.paymentResp = paymentResp;
	}
	public String getHandleUser() {
		return handleUser;
	}
	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}
	public String getHandle1Code() {
		return handle1Code;
	}
	public void setHandle1Code(String handle1Code) {
		this.handle1Code = handle1Code;
	}
	public String getVirtualCode() {
		return virtualCode;
	}
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPremiumF() {
		return premiumF;
	}
	public void setPremiumF(String premiumF) {
		this.premiumF = premiumF;
	}
	public String getPremiumQ() {
		return premiumQ;
	} 
	
	public void setPremiumQ(String premiumQ) {
		this.premiumQ = premiumQ;
	}
	
}
