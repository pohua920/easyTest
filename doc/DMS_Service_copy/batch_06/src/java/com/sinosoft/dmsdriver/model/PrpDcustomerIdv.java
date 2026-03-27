package com.sinosoft.dmsdriver.model;

import java.math.BigDecimal;
import java.util.Date;

public class PrpDcustomerIdv implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String customerCode;
	
	private String customerType;

	private String password;

	private String shortHandCode;

	private String customerCName;

	private String customerEName;

	private String addressCName;

	private String addressEName;

	private String identifyTypeCName;
	
	private String identifyType;

	private String identifyNumber;

	private String creditLevel;

	private String sex;

	private Long age;

	private String health;

	private String occupationCode;

	private String educationCode;

	private String unit;

	private String unitAddress;

	private String customerKind;

	private String customerFlag;

	private String phoneNumber;

	private String faxNumber;

	private String mobile;

	private String linkAddress;

	private String postCode;

	private String pager;

	private String email;

	private String bank;

	private String account;

	private Date deathDate;

	private String blackState;

	private String newCustomerCode;

	private String validStatus;

	private String articleCode;

	private String flag;

	private String netAddress;

	private String lowerViewFlag;

	private String handlerCode;

	private String operatorCode;

	private Date inputDate;

	private String updaterCode;

	private Date updateDate;

	private String comCode;

	private String topLevelFlag;

	private Date birthDate;
	
	private String roomPostCode;
	
	private String roomAddress;
	
	private String verifyNumber;
	
	private String loanAccount;
	
	private String linkerName;
	
	/**????*/
    private String creditNumber;
/**?????*/
    private String collateralNumber;
/**????*/
    private String loansBehalfNumber;
/**?????*/
    private String loansDepartment;

	// add by ZhaoXianyang
    /** ???????????????? */
    private String occupationName;
    
    /** ????????? */
    private String roomPAreaNumber;
    
    /** ?????????? */
    private String roomPExtNumber;
    
   	/** ?????????? */
    private String nationalityAddress;
   	
   	/** ???????*/
   	private String phoneAreaNumber;
   	
   	/** ????????*/
   	private String phoneExtNumber;
   	
	/** ?????????'????*/
   	private Date identifyStartDate;
   	
   	/** ???????????????*/
   	private Date identifyEndDate;
   	
   	/**瀹㈡埛琛屽姩鐢佃瘽*/
    private String mobileTelephone;
    
	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}

	public PrpDcustomerIdv() {
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getIdentifyTypeCName() {
		return identifyTypeCName;
	}

	public void setIdentifyTypeCName(String identifyTypeCName) {
		this.identifyTypeCName = identifyTypeCName;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getIdentifyType() {
		return this.identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getOccupationCode() {
		return this.occupationCode;
	}

	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	public String getEducationCode() {
		return this.educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitAddress() {
		return this.unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
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

	public String getLinkAddress() {
		return this.linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPager() {
		return this.pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getDeathDate() {
		return this.deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
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

	public String getNetAddress() {
		return this.netAddress;
	}

	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
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

	/**
	 * 属性归属机构代码的getter方法
	 */

	public String getTopLevelFlag() {
		return this.topLevelFlag;
	}

	/**
	 * 属性归属机构代码的setter方法
	 */
	public void setTopLevelFlag(String topLevelFlag) {
		this.topLevelFlag = topLevelFlag;
	}

	/**
	 * 属性出生日期的getter方法
	 */
	public Date getBirthDate() {
		return this.birthDate;
	}

	/**
	 * 属性出生日期的setter方法
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getOccupationName() {
		return occupationName;
	}

	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}

	public String getRoomPAreaNumber() {
		return roomPAreaNumber;
	}

	public void setRoomPAreaNumber(String roomPAreaNumber) {
		this.roomPAreaNumber = roomPAreaNumber;
	}

	public String getRoomPExtNumber() {
		return roomPExtNumber;
	}

	public void setRoomPExtNumber(String roomPExtNumber) {
		this.roomPExtNumber = roomPExtNumber;
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

	/**??????????*/
    public String getRoomPostCode() {
        return roomPostCode;
    }

    public void setRoomPostCode(String roomPostCode) {
        this.roomPostCode = roomPostCode;
    }

    /**??????????*/
    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    /**??????????*/
    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    /**?????????*/
    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    /**?????????????*/
    public String getLinkerName() {
        return linkerName;
    }

    public void setLinkerName(String linkerName) {
        this.linkerName = linkerName;
    }

	public Date getIdentifyStartDate() {
		return identifyStartDate;
	}

	public void setIdentifyStartDate(Date identifyStartDate) {
		this.identifyStartDate = identifyStartDate;
	}

	public Date getIdentifyEndDate() {
		return identifyEndDate;
	}

	public void setIdentifyEndDate(Date identifyEndDate) {
		this.identifyEndDate = identifyEndDate;
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
	
	

}
