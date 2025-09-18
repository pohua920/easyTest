package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class FirAgtBop01 extends IBatisBaseEntity<BigDecimal>{
	/*mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
    private BigDecimal oid;
    private String batchNo;
    private String riskcode;
    private String orderseq;
    private String policyno;
    private String riskcodeBop;
    private String kindcodeBop;
    private Date dataDate;
    private Date applyDate;
    private Date startdate;
    private Date enddate;
    private String orderType;
    private String policyStatus;
    private String endorsetext;
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
    private String inquiryType;
    private BigDecimal commRate;
    private String salesSource;
    private String salesBranch;
    private String isAutoRenew;
    private String amountCheck;
    private String licensePlate;
    private String oldPolicyno;
    private Date oldEnddate;
    private String projNo;
    private String projName;
    private String salesId;
    private String salesName;
    private String salesNo;
    private String carBrand;
    private String carType;
    private String carYear;
    private String carCc;
    private String salesType;
    private String payWay;
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
    public String getRiskcode() {
        return riskcode;
    }
    public void setRiskcode(String riskcode) {
        this.riskcode = riskcode;
    }
    public String getOrderseq() {
        return orderseq;
    }
    public void setOrderseq(String orderseq) {
        this.orderseq = orderseq;
    }
    public String getPolicyno() {
        return policyno;
    }
    public void setPolicyno(String policyno) {
        this.policyno = policyno;
    }
    public String getRiskcodeBop() {
        return riskcodeBop;
    }
    public void setRiskcodeBop(String riskcodeBop) {
        this.riskcodeBop = riskcodeBop;
    }
    public String getKindcodeBop() {
        return kindcodeBop;
    }
    public void setKindcodeBop(String kindcodeBop) {
        this.kindcodeBop = kindcodeBop;
    }
    public Date getDataDate() {
        return dataDate;
    }
    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }
    public Date getApplyDate() {
        return applyDate;
    }
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    public Date getStartdate() {
        return startdate;
    }
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public Date getEnddate() {
        return enddate;
    }
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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
    public String getEndorsetext() {
        return endorsetext;
    }
    public void setEndorsetext(String endorsetext) {
        this.endorsetext = endorsetext;
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
    public String getInquiryType() {
        return inquiryType;
    }
    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }
    public BigDecimal getCommRate() {
        return commRate;
    }
    public void setCommRate(BigDecimal commRate) {
        this.commRate = commRate;
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
    public String getOldPolicyno() {
        return oldPolicyno;
    }
    public void setOldPolicyno(String oldPolicyno) {
        this.oldPolicyno = oldPolicyno;
    }
    public Date getOldEnddate() {
        return oldEnddate;
    }
    public void setOldEnddate(Date oldEnddate) {
        this.oldEnddate = oldEnddate;
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
    public String getSalesId() {
        return salesId;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }
    public String getSalesName() {
        return salesName;
    }
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
    public String getSalesNo() {
        return salesNo;
    }
    public void setSalesNo(String salesNo) {
        this.salesNo = salesNo;
    }
    public String getCarBrand() {
        return carBrand;
    }
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
    public String getCarType() {
        return carType;
    }
    public void setCarType(String carType) {
        this.carType = carType;
    }
    public String getCarYear() {
        return carYear;
    }
    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }
    public String getCarCc() {
        return carCc;
    }
    public void setCarCc(String carCc) {
        this.carCc = carCc;
    }
    public String getSalesType() {
        return salesType;
    }
    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }
    public String getPayWay() {
        return payWay;
    }
    public void setPayWay(String payWay) {
        this.payWay = payWay;
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