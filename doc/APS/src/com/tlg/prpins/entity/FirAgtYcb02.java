package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
@SuppressWarnings("serial")
public class FirAgtYcb02 extends IBatisBaseEntity<BigDecimal>{

	private BigDecimal oid;
	private String batchNo;
	private String riskCode;
	private String policyNo;
	private String orderSeq;
	private String endorseNo;
	private String riskCodeYcb;
	private String kindCodeYcb;
	private Date dataDate;
	private Date startDate;
	private Date endDate;
	private Date underWriteEndDate;
	private String orderType;
	private String policyStatus;
	private String endorseText;
	private String isRenew;
	private String applyName;
	private String applyId;
	private String applyPostcode;
	private String applyAddr;
	private String insuredName;
	private String insuredId;
	private Date insuredBirthday;
	private String insuredAge;
	private String payType;
	private BigDecimal premium;
	private BigDecimal amount;
	private BigDecimal commRate;
	private BigDecimal commission;
	private BigDecimal serviceCharge;
	private String salesSource;
	private String salesBranch;
	private String salesId;
	private String salesNo;
	private String isAutoRenew;
	private String amountCheck;
	private String licensePlate;
	private String oldPolicyNo;
	private Date oldEndDate;
	private String projNo;
	private String projName;
	private String acceptWay;
	private String startTime;
	private String endTime;
	private String address;
	private String phoneNumber;
	private String deleteFlag;
	private String icreate;
	private Date dcreate;
	private String iupdate;
	private Date dupdate;

	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getRiskCodeYcb() {
		return riskCodeYcb;
	}
	public void setRiskCodeYcb(String riskCodeYcb) {
		this.riskCodeYcb = riskCodeYcb;
	}
	public String getKindCodeYcb() {
		return kindCodeYcb;
	}
	public void setKindCodeYcb(String kindCodeYcb) {
		this.kindCodeYcb = kindCodeYcb;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
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
	public Date getUnderWriteEndDate() {
		return underWriteEndDate;
	}
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getEndorseText() {
		return endorseText;
	}
	public void setEndorseText(String endorseText) {
		this.endorseText = endorseText;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getApplyPostcode() {
		return applyPostcode;
	}
	public void setApplyPostcode(String applyPostcode) {
		this.applyPostcode = applyPostcode;
	}
	public String getApplyAddr() {
		return applyAddr;
	}
	public void setApplyAddr(String applyAddr) {
		this.applyAddr = applyAddr;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredId() {
		return insuredId;
	}
	public void setInsuredId(String insuredId) {
		this.insuredId = insuredId;
	}
	public Date getInsuredBirthday() {
		return insuredBirthday;
	}
	public void setInsuredBirthday(Date insuredBirthday) {
		this.insuredBirthday = insuredBirthday;
	}
	public String getInsuredAge() {
		return insuredAge;
	}
	public void setInsuredAge(String insuredAge) {
		this.insuredAge = insuredAge;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getCommRate() {
		return commRate;
	}
	public void setCommRate(BigDecimal commRate) {
		this.commRate = commRate;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getSalesSource() {
		return salesSource;
	}
	public void setSalesSource(String salesSource) {
		this.salesSource = salesSource;
	}
	public String getSalesBranch() {
		return salesBranch;
	}
	public void setSalesBranch(String salesBranch) {
		this.salesBranch = salesBranch;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getSalesNo() {
		return salesNo;
	}
	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}
	public String getIsAutoRenew() {
		return isAutoRenew;
	}
	public void setIsAutoRenew(String isAutoRenew) {
		this.isAutoRenew = isAutoRenew;
	}
	public String getAmountCheck() {
		return amountCheck;
	}
	public void setAmountCheck(String amountCheck) {
		this.amountCheck = amountCheck;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getOldPolicyNo() {
		return oldPolicyNo;
	}
	public void setOldPolicyNo(String oldPolicyNo) {
		this.oldPolicyNo = oldPolicyNo;
	}
	public Date getOldEndDate() {
		return oldEndDate;
	}
	public void setOldEndDate(Date oldEndDate) {
		this.oldEndDate = oldEndDate;
	}
	public String getProjNo() {
		return projNo;
	}
	public void setProjNo(String projNo) {
		this.projNo = projNo;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getAcceptWay() {
		return acceptWay;
	}
	public void setAcceptWay(String acceptWay) {
		this.acceptWay = acceptWay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getIcreate() {
		return icreate;
	}
	public void setIcreate(String icreate) {
		this.icreate = icreate;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getIupdate() {
		return iupdate;
	}
	public void setIupdate(String iupdate) {
		this.iupdate = iupdate;
	}
	public Date getDupdate() {
		return dupdate;
	}
	public void setDupdate(Date dupdate) {
		this.dupdate = dupdate;
	}
}
