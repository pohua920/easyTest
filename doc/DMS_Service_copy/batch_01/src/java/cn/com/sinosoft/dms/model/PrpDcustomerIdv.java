package cn.com.sinosoft.dms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Parameter;

/**
 * POJO类PrpDcustomerIdv
 */
@Entity
@Table(name = "PRPDCUSTOMERIDV")
public class PrpDcustomerIdv implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性客户代码 */
	private String customerCode;

	/** 属性PRPDCUSTOMER */
	private PrpDcustomer prpDcustomer;

	/** 属性客户密码 */
	private String password;

	/** 属性速查索引码 */
	private String shortHandCode;

	/** 属性客户中文名称 */
	private String customerCName;

	/** 属性客户英文名称 */
	private String customerEName;

	/** 属性地址中文名称 */
	private String addressCName;

	/** 属性地址英文名称 */
	private String addressEName;

	/** 属性证件类型 */
	private String identifyType;

	/** 属性证件号码 (U) */
	private String identifyNumber;

	/** 属性资信等级 (I) A/B/C/D */
	private String creditLevel;

	/** 属性性别 */
	private String sex;

	/** 属性年龄 */
	private Long age;

	/** 属性健康状况 */
	private String health;

	/** 属性职业代码(I) */
	private String occupationCode;

	/** 属性学历代码(I) */
	private String educationCode;

	/** 属性工作单位 */
	private String unit;

	/** 属性单位地址 */
	private String unitAddress;

	/** 属性客户类型 */
	private String customerKind;

	/** 属性临时/正式标志(0:临时/1:正式 */
	private String customerFlag;

	/** 属性电话 */
	private String phoneNumber;

	/** 属性传真 */
	private String faxNumber;

	/** 属性手机(I) */
	private String mobile;

	/** 属性通信地址 */
	private String linkAddress;

	/** 属性邮编 */
	private String postCode;

	/** 属性呼机 */
	private String pager;

	/** 属性电子邮件 */
	private String email;

	/** 属性开户银行 */
	private String bank;

	/** 属性帐号 */
	private String account;

	/** 属性死亡时间 */
	private Date deathDate;

	/** 属性黑名单标志 [1]:0:正常 1：黑名单 */
	private String blackState;

	/** 属性新的客户代码 */
	private String newCustomerCode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性专项代码(对应会计科目 */
	private String articleCode;

	/** 属性标志字段 */
	private String flag;

	/** 属性网址 */
	private String netAddress;

	/** 属性下级机构是否允许查看 1/是 0/否 */
	private String lowerViewFlag;

	/** 属性归属业务员代码 */
	private String handlerCode;

	/** 属性备用3 */
	private String operatorCode;

	/** 属性备用4 */
	private Date inputDate;

	/** 属性最后一次修改人 */
	private String updaterCode;

	/** 属性修改日期 */
	private Date updateDate;

	/** 属性是否一级法人 1/是 0/否 */
	private String comCode;

	/** 属性归属机构代码 */
	private String topLevelFlag;

	/** 属性出生日期 */
	private Date birthDate;
	
	/** ?????????????? */
    private String roomPostCode;
    
    /** ?????????????? */
    private String roomAddress;
   
    /** ???????????????? */
    private String verifyNumber;
    
    /** ???????????? */
    private String loanAccount;

    /** ??????????????????? */
    private String linkerName;
    
    /**??????*/
    private String creditNumber;
/**??????? */
    private String collateralNumber;
/**??????*/
    private String loansBehalfNumber;
/**???????*/
    private String loansDepartment;

    // add by ZhaoXianyang
	/** ?????????? */
	private String occupationName;
	
	/** ?????? */
	private String roomPAreaNumber;
	
	/** ??????? */
	private String roomPExtNumber;
	
	/** ??????? */
	private String nationalityAddress;
	
	/** ??????*/
	private String phoneAreaNumber;
	
	/** ???????*/
	private String phoneExtNumber;
	
	/** ?????????*/
	private Date identifyStartDate;
	
	/** ?????????*/
	private Date identifyEndDate;
	
	/**瀹㈡埛琛屽姩鐢佃瘽*/
    private String mobileTelephone;
	/**
	 * 类PrpDcustomerIdv的默认构造方法
	 */
	public PrpDcustomerIdv() {
	}

	/**
	 * 属性客户代码的getter方法
	 */
	@Id
	@Column(name = "CUSTOMERCODE", nullable = false)
	public String getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * 属性客户代码的setter方法
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 属性PRPDCUSTOMER的getter方法
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERCODE")
	public PrpDcustomer getPrpDcustomer() {
		return this.prpDcustomer;
	}

	/**
	 * 属性PRPDCUSTOMER的setter方法
	 */
	public void setPrpDcustomer(PrpDcustomer prpDcustomer) {
		this.prpDcustomer = prpDcustomer;
	}

	/**
	 * 属性客户密码的getter方法
	 */

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	/**
	 * 属性客户密码的setter方法
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 属性速查索引码的getter方法
	 */

	@Column(name = "SHORTHANDCODE")
	public String getShortHandCode() {
		return this.shortHandCode;
	}

	/**
	 * 属性速查索引码的setter方法
	 */
	public void setShortHandCode(String shortHandCode) {
		this.shortHandCode = shortHandCode;
	}

	/**
	 * 属性客户中文名称的getter方法
	 */

	@Column(name = "CUSTOMERCNAME")
	public String getCustomerCName() {
		return this.customerCName;
	}

	/**
	 * 属性客户中文名称的setter方法
	 */
	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	/**
	 * 属性客户英文名称的getter方法
	 */

	@Column(name = "CUSTOMERENAME")
	public String getCustomerEName() {
		return this.customerEName;
	}

	/**
	 * 属性客户英文名称的setter方法
	 */
	public void setCustomerEName(String customerEName) {
		this.customerEName = customerEName;
	}

	/**
	 * 属性地址中文名称的getter方法
	 */

	@Column(name = "ADDRESSCNAME")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**
	 * 属性地址中文名称的setter方法
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**
	 * 属性地址英文名称的getter方法
	 */

	@Column(name = "ADDRESSENAME")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**
	 * 属性地址英文名称的setter方法
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**
	 * 属性证件类型的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性证件类型的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性证件号码 (U)的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性证件号码 (U)的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性资信等级 (I) A/B/C/D的getter方法
	 */

	@Column(name = "CREDITLEVEL")
	public String getCreditLevel() {
		return this.creditLevel;
	}

	/**
	 * 属性资信等级 (I) A/B/C/D的setter方法
	 */
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	/**
	 * 属性性别的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性性别的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性年龄的getter方法
	 */

	@Column(name = "AGE")
	public Long getAge() {
		return this.age;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setAge(Long age) {
		this.age = age;
	}

	/**
	 * 属性健康状况的getter方法
	 */

	@Column(name = "HEALTH")
	public String getHealth() {
		return this.health;
	}

	/**
	 * 属性健康状况的setter方法
	 */
	public void setHealth(String health) {
		this.health = health;
	}

	/**
	 * 属性职业代码(I)的getter方法
	 */

	@Column(name = "OCCUPATIONCODE")
	public String getOccupationCode() {
		return this.occupationCode;
	}

	/**
	 * 属性职业代码(I)的setter方法
	 */
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	/**
	 * 属性学历代码(I)的getter方法
	 */

	@Column(name = "EDUCATIONCODE")
	public String getEducationCode() {
		return this.educationCode;
	}

	/**
	 * 属性学历代码(I)的setter方法
	 */
	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	/**
	 * 属性工作单位的getter方法
	 */

	@Column(name = "UNIT")
	public String getUnit() {
		return this.unit;
	}

	/**
	 * 属性工作单位的setter方法
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 属性单位地址的getter方法
	 */

	@Column(name = "UNITADDRESS")
	public String getUnitAddress() {
		return this.unitAddress;
	}

	/**
	 * 属性单位地址的setter方法
	 */
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * 属性客户类型的getter方法
	 */

	@Column(name = "CUSTOMERKIND")
	public String getCustomerKind() {
		return this.customerKind;
	}

	/**
	 * 属性客户类型的setter方法
	 */
	public void setCustomerKind(String customerKind) {
		this.customerKind = customerKind;
	}

	/**
	 * 属性临时/正式标志(0:临时/1:正式的getter方法
	 */

	@Column(name = "CUSTOMERFLAG")
	public String getCustomerFlag() {
		return this.customerFlag;
	}

	/**
	 * 属性临时/正式标志(0:临时/1:正式的setter方法
	 */
	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}

	/**
	 * 属性电话的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性电话的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性传真的getter方法
	 */

	@Column(name = "FAXNUMBER")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**
	 * 属性传真的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * 属性手机(I)的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性手机(I)的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性通信地址的getter方法
	 */

	@Column(name = "LINKADDRESS")
	public String getLinkAddress() {
		return this.linkAddress;
	}

	/**
	 * 属性通信地址的setter方法
	 */
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	/**
	 * 属性邮编的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 属性邮编的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 属性呼机的getter方法
	 */

	@Column(name = "PAGER")
	public String getPager() {
		return this.pager;
	}

	/**
	 * 属性呼机的setter方法
	 */
	public void setPager(String pager) {
		this.pager = pager;
	}

	/**
	 * 属性电子邮件的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性电子邮件的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性开户银行的getter方法
	 */

	@Column(name = "BANK")
	public String getBank() {
		return this.bank;
	}

	/**
	 * 属性开户银行的setter方法
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 属性帐号的getter方法
	 */

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	/**
	 * 属性帐号的setter方法
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 属性死亡时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DEATHDATE")
	public Date getDeathDate() {
		return this.deathDate;
	}

	/**
	 * 属性死亡时间的setter方法
	 */
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * 属性黑名单标志 [1]:0:正常 1：黑名单的getter方法
	 */

	@Column(name = "BLACKSTATE")
	public String getBlackState() {
		return this.blackState;
	}

	/**
	 * 属性黑名单标志 [1]:0:正常 1：黑名单的setter方法
	 */
	public void setBlackState(String blackState) {
		this.blackState = blackState;
	}

	/**
	 * 属性新的客户代码的getter方法
	 */

	@Column(name = "NEWCUSTOMERCODE")
	public String getNewCustomerCode() {
		return this.newCustomerCode;
	}

	/**
	 * 属性新的客户代码的setter方法
	 */
	public void setNewCustomerCode(String newCustomerCode) {
		this.newCustomerCode = newCustomerCode;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性专项代码(对应会计科目的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性专项代码(对应会计科目的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
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
	 * 属性网址的getter方法
	 */

	@Column(name = "NETADDRESS")
	public String getNetAddress() {
		return this.netAddress;
	}

	/**
	 * 属性网址的setter方法
	 */
	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}

	/**
	 * 属性下级机构是否允许查看 1/是 0/否的getter方法
	 */

	@Column(name = "LOWERVIEWFLAG")
	public String getLowerViewFlag() {
		return this.lowerViewFlag;
	}

	/**
	 * 属性下级机构是否允许查看 1/是 0/否的setter方法
	 */
	public void setLowerViewFlag(String lowerViewFlag) {
		this.lowerViewFlag = lowerViewFlag;
	}

	/**
	 * 属性归属业务员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性归属业务员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性备用3的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性备用3的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性备用4的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性备用4的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性最后一次修改人的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性最后一次修改人的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性修改日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性修改日期的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性是否一级法人 1/是 0/否的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性是否一级法人 1/是 0/否的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性归属机构代码的getter方法
	 */

	@Column(name = "TOPLEVELFLAG")
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
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDATE")
	public Date getBirthDate() {
		return this.birthDate;
	}

	/**
	 * 属性出生日期的setter方法
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
     * ????????????????etter??????
     */
	@Column(name = "ROOMPOSTCODE")
    public String getRoomPostCode() {
        return roomPostCode;
    }

    public void setRoomPostCode(String roomPostCode) {
        this.roomPostCode = roomPostCode;
    }

    /**
     * ?????????????????getter??????
     */
    @Column(name = "ROOMADDRESS")
    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    /**
     * ????????????????etter??????
     */
    @Column(name = "VERIFYNUMBER")
    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    /**
     * ?????????????getter??????
     */
    @Column(name = "LOANACCOUNT")
    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    /**
     * ?????????????????????etter??????
     */
    @Column(name = "LINKERNAME")
    public String getLinkerName() {
        return linkerName;
    }

    public void setLinkerName(String linkerName) {
        this.linkerName = linkerName;
    }
	
    @Column(name = "CREDITNUMBER")
    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    @Column(name = "COLLATERALNUMBER")
    public String getCollateralNumber() {
        return collateralNumber;
    }

    public void setCollateralNumber(String collateralNumber) {
        this.collateralNumber = collateralNumber;
    }

    @Column(name = "LOANSBEHALFNUMBER")
    public String getLoansBehalfNumber() {
        return loansBehalfNumber;
    }

    public void setLoansBehalfNumber(String loansBehalfNumber) {
        this.loansBehalfNumber = loansBehalfNumber;
    }

    @Column(name = "LOANSDEPARTMENT")
    public String getLoansDepartment() {
        return loansDepartment;
    }

    public void setLoansDepartment(String loansDepartment) {
        this.loansDepartment = loansDepartment;
    }
    
	// add by ZhaoXianyang
 	@Column(name="OCCUPATIONNAME")
 	public String getOccupationName() {
 		return occupationName;
 	}

 	public void setOccupationName(String occupationName) {
 		this.occupationName = occupationName;
 	}


 	@Column(name="ROOMPAREANUMBER")
 	public String getRoomPAreaNumber() {
 		return roomPAreaNumber;
 	}


 	public void setRoomPAreaNumber(String roomPAreaNumber) {
 		this.roomPAreaNumber = roomPAreaNumber;
 	}

 	@Column(name="ROOMPEXTNUMBER")
 	public String getRoomPExtNumber() {
 		return roomPExtNumber;
 	}


 	public void setRoomPExtNumber(String roomPExtNumber) {
 		this.roomPExtNumber = roomPExtNumber;
 	}

 	@Column(name="NATIONALITYADDRESS")
 	public String getNationalityAddress() {
 		return nationalityAddress;
 	}

 	public void setNationalityAddress(String nationalityAddress) {
 		this.nationalityAddress = nationalityAddress;
 	}

 	@Column(name="PHONEAREANUMBER")
 	public String getPhoneAreaNumber() {
 		return phoneAreaNumber;
 	}

 	public void setPhoneAreaNumber(String phoneAreaNumber) {
 		this.phoneAreaNumber = phoneAreaNumber;
 	}

 	@Column(name="PHONEEXTNUMBER")
 	public String getPhoneExtNumber() {
 		return phoneExtNumber;
 	}

 	public void setPhoneExtNumber(String phoneExtNumber) {
 		this.phoneExtNumber = phoneExtNumber;
 	}
 	
 	@Column(name="IDENTIFYSTARTDATE")
	public Date getIdentifyStartDate() {
		return identifyStartDate;
	}

	public void setIdentifyStartDate(Date identifyStartDate) {
		this.identifyStartDate = identifyStartDate;
	}

	@Column(name="IDENTIFYENDDATE")
	public Date getIdentifyEndDate() {
		return identifyEndDate;
	}

	public void setIdentifyEndDate(Date identifyEndDate) {
		this.identifyEndDate = identifyEndDate;
	}
	@Column(name="MOBILETELEPHONE")
	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}
	
	
}
