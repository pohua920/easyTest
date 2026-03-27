package com.sinosoft.dmsdriver.model;

import java.math.BigDecimal;
import java.util.Date;


public class PrpDcustomerUnit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String customerCode;
	
	private String customerType;

	private String password;

	private String shortHandCode;

	private String customerCName;

	private String customerEName;

	private String addressCName;

	private String addressEName;

	private String possessNature;

	private String businessSource;

	private String businessSort;

	private String customerKind;

	private String customerFlag;

	private String organizeCode;

	private String creditLevel;

	private String leaderName;

	private String phoneNumber;

	private String faxNumber;

	private String mobile;

	private String netAddress;

	private String emailAddress;

	private String postAddress;

	private String postCode;

	private String linkerName;

	private String bank;

	private String account;

	private String industryCode;

	private String economyCode;

	private String measureCode;

	private String fatherCode;

	private String sponsorName;

	private String businessRange;

	private BigDecimal registFund;

	private String regionCode;

	private String blackState;

	private String newCustomerCode;

	private String validStatus;

	private String articleCode;

	private String flag;

	private String customerShortName;

	private String employSum;

	private String shareHolderFlag;

	private String revenueCode;

	private String wordRiskRank;

	private String lowerViewFlag;

	private String handlerCode;

	private String operatorCode;

	private Date inputDate;

	private String updaterCode;

	private Date updateDate;

	private String comCode;

	private String topLevelFlag;

	private String careerRiskGrade;

	private String taxIdentifyCode;
	
	// add by ZhaoXianyang
  	/** ?????????? */
      private String nationalityAddress;
  	
  	/** ???¥¬???*/
  	private String phoneAreaNumber;
  	
  	/** ???¥¬????*/
  	private String phoneExtNumber;
  	
  	/** ???????*/
  	private String localNo;
  	
  	/** ???????*/
  	private String localName;
  	
	
	private String principalName;
	
	private String principalIdentifyType;
	
	private String principalIdentifyNumber;
    
    private String principalIdentifyStartDate;
    
    private String principalIdentifyEndDate;
    
    private String verifyNumber;
    
    private String loanAccount;
    /** ⁄–≈±‡∫≈*/
    private String creditNumber;
/**µ£±£∆∑±‡∫≈*/
    private String collateralNumber;
/**ŸJ¥˙æéÃñ*/
    private String loansBehalfNumber;
/**∑≈øÓ≤øÈTÑe*/
    private String loansDepartment;
    /**ÂÆ¢Êà∂Á∑®Á¢º*/
    private String unitCode;
    private String minimumPreium;
    /**ÂÆ¢Êà∑Ë°åÂä®ÁîµËØù*/
    private String mobileTelephone;
    
    private PrpDcustomer prpDcustomer;

    
    public PrpDcustomer getPrpDcustomer() {
        return this.prpDcustomer;
    }

    public void setPrpDcustomer(PrpDcustomer prpDcustomer) {
        this.prpDcustomer = prpDcustomer;
    }
	public PrpDcustomerUnit() {
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShortHandCode() {
		return this.shortHandCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setShortHandCode(String shortHandCode) {
		this.shortHandCode = shortHandCode;
	}

	public String getCustomerCName() {
		return this.customerCName;
	}

	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	public String getCustomerEName() {
		return this.customerEName;
	}

	public void setCustomerEName(String customerEName) {
		this.customerEName = customerEName;
	}

	public String getAddressCName() {
		return this.addressCName;
	}

	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	public String getAddressEName() {
		return this.addressEName;
	}

	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	public String getPossessNature() {
		return this.possessNature;
	}

	public void setPossessNature(String possessNature) {
		this.possessNature = possessNature;
	}

	public String getBusinessSource() {
		return this.businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	public String getBusinessSort() {
		return this.businessSort;
	}

	public void setBusinessSort(String businessSort) {
		this.businessSort = businessSort;
	}

	public String getCustomerKind() {
		return this.customerKind;
	}

	public void setCustomerKind(String customerKind) {
		this.customerKind = customerKind;
	}

	public String getCustomerFlag() {
		return this.customerFlag;
	}
	
	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}

	public String getOrganizeCode() {
		return this.organizeCode;
	}

	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
	}

	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getLeaderName() {
		return this.leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNetAddress() {
		return this.netAddress;
	}
	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPostAddress() {
		return this.postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getLinkerName() {
		return this.linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIndustryCode() {
		return this.industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getEconomyCode() {
		return this.economyCode;
	}

	public void setEconomyCode(String economyCode) {
		this.economyCode = economyCode;
	}

	public String getMeasureCode() {
		return this.measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public String getFatherCode() {
		return this.fatherCode;
	}

	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

	public String getSponsorName() {
		return this.sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getBusinessRange() {
		return this.businessRange;
	}

	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	public BigDecimal getRegistFund() {
		return this.registFund;
	}

	public void setRegistFund(BigDecimal registFund) {
		this.registFund = registFund;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getBlackState() {
		return this.blackState;
	}

	public void setBlackState(String blackState) {
		this.blackState = blackState;
	}

	public String getNewCustomerCode() {
		return this.newCustomerCode;
	}

	public void setNewCustomerCode(String newCustomerCode) {
		this.newCustomerCode = newCustomerCode;
	}

	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getArticleCode() {
		return this.articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCustomerShortName() {
		return this.customerShortName;
	}

	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	public String getEmploySum() {
		return this.employSum;
	}

	public void setEmploySum(String employSum) {
		this.employSum = employSum;
	}

	public String getShareHolderFlag() {
		return this.shareHolderFlag;
	}

	public void setShareHolderFlag(String shareHolderFlag) {
		this.shareHolderFlag = shareHolderFlag;
	}

	public String getRevenueCode() {
		return this.revenueCode;
	}

	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}

	public String getWordRiskRank() {
		return this.wordRiskRank;
	}

	public void setWordRiskRank(String wordRiskRank) {
		this.wordRiskRank = wordRiskRank;
	}

	public String getLowerViewFlag() {
		return this.lowerViewFlag;
	}

	public void setLowerViewFlag(String lowerViewFlag) {
		this.lowerViewFlag = lowerViewFlag;
	}

	public String getHandlerCode() {
		return this.handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getOperatorCode() {
		return this.operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getUpdaterCode() {
		return this.updaterCode;
	}

	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getComCode() {
		return this.comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getTopLevelFlag() {
		return this.topLevelFlag;
	}

	public void setTopLevelFlag(String topLevelFlag) {
		this.topLevelFlag = topLevelFlag;
	}

	public String getCareerRiskGrade() {
		return this.careerRiskGrade;
	}

	public void setCareerRiskGrade(String careerRiskGrade) {
		this.careerRiskGrade = careerRiskGrade;
	}

	public String getTaxIdentifyCode() {
		return this.taxIdentifyCode;
	}

	public void setTaxIdentifyCode(String taxIdentifyCode) {
		this.taxIdentifyCode = taxIdentifyCode;
	}

	public String getNationalityAddress() {
		return nationalityAddress;
	}

	public void setNationalityAddress(String nationalityAddress) {
		this.nationalityAddress = nationalityAddress;
	}

	public String getPhoneAreaNumber() {
		return phoneAreaNumber;
	}

	public void setPhoneAreaNumber(String phoneAreaNumber) {
		this.phoneAreaNumber = phoneAreaNumber;
	}

	public String getPhoneExtNumber() {
		return phoneExtNumber;
	}

	public void setPhoneExtNumber(String phoneExtNumber) {
		this.phoneExtNumber = phoneExtNumber;
	}

	public String getLocalNo() {
		return localNo;
	}

	public void setLocalNo(String localNo) {
		this.localNo = localNo;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalIdentifyType() {
        return principalIdentifyType;
    }

    public void setPrincipalIdentifyType(String principalIdentifyType) {
        this.principalIdentifyType = principalIdentifyType;
    }

    public String getPrincipalIdentifyNumber() {
        return principalIdentifyNumber;
    }

    public void setPrincipalIdentifyNumber(String principalIdentifyNumber) {
        this.principalIdentifyNumber = principalIdentifyNumber;
    }

    public String getPrincipalIdentifyStartDate() {
        return principalIdentifyStartDate;
    }

    public void setPrincipalIdentifyStartDate(String principalIdentifyStartDate) {
        this.principalIdentifyStartDate = principalIdentifyStartDate;
    }

    public String getPrincipalIdentifyEndDate() {
        return principalIdentifyEndDate;
    }

    public void setPrincipalIdentifyEndDate(String principalIdentifyEndDate) {
        this.principalIdentifyEndDate = principalIdentifyEndDate;
    }

    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public String getCollateralNumber() {
        return collateralNumber;
    }

    public void setCollateralNumber(String collateralNumber) {
        this.collateralNumber = collateralNumber;
    }

    public String getLoansBehalfNumber() {
        return loansBehalfNumber;
    }

    public void setLoansBehalfNumber(String loansBehalfNumber) {
        this.loansBehalfNumber = loansBehalfNumber;
    }

    public String getLoansDepartment() {
        return loansDepartment;
    }

    public void setLoansDepartment(String loansDepartment) {
        this.loansDepartment = loansDepartment;
    }
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getMinimumPreium() {
		return minimumPreium;
	}

	public void setMinimumPreium(String minimumPreium) {
		this.minimumPreium = minimumPreium;
	}
	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}
}
