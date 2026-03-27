package cn.com.sinosoft.dms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
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
 * POJO类PrpDcustomerUnit
 */
@Entity
@Table(name = "PRPDCUSTOMERUNIT")
public class PrpDcustomerUnit implements java.io.Serializable {
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

	/** 属性占用性质代码 */
	private String possessNature;

	/** 属性行业代码 */
	private String businessSource;

	/** 属性所有制代码（单位性质） */
	private String businessSort;

	/** 属性客户类型 */
	private String customerKind;

	/** 属性临时/正式标志(0:临时/1:正式 */
	private String customerFlag;

	/** 属性法人组织机构代码 个人身份证号码 */
	private String organizeCode;

	/** 属性资信等级 (I) A/B/C/D */
	private String creditLevel;

	/** 属性法人代表 */
	private String leaderName;

	/** 属性电话 */
	private String phoneNumber;

	/** 属性传真 */
	private String faxNumber;

	/** 属性手机(I) */
	private String mobile;

	/** 属性网址 */
	private String netAddress;

	/** 属性电子信箱(U) */
	private String emailAddress;

	/** 属性通信地址(I) */
	private String postAddress;

	/** 属性邮编 */
	private String postCode;

	/** 属性联系人(U) */
	private String linkerName;

	/** 属性开户银行 */
	private String bank;

	/** 属性帐号 */
	private String account;

	/** 属性工商局码 */
	private String industryCode;

	/** 属性经贸委码 */
	private String economyCode;

	/** 属性标准计量码 */
	private String measureCode;

	/** 属性上级客户代码 */
	private String fatherCode;

	/** 属性主管人名称 */
	private String sponsorName;

	/** 属性经营范围 */
	private String businessRange;

	/** 属性注册资金 */
	private BigDecimal registFund;

	/** 属性行政区划编码 */
	private String regionCode;

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

	/** 属性客户简称 */
	private String customerShortName;

	/** 属性在册员工人数 */
	private String employSum;

	/** 属性是否股东 1/是 0/否 */
	private String shareHolderFlag;

	/** 属性企业税务代码 */
	private String revenueCode;

	/** 属性职业风险等级 */
	private String wordRiskRank;

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

	/** 属性备注1 */
	private String careerRiskGrade;

	/** 属性备注2 */
	private String taxIdentifyCode;
	
	// add by ZhaoXianyang
  	/** ?????????? */
      private String nationalityAddress;
  	
  	/** ???????*/
  	private String phoneAreaNumber;
  	
  	/** ???????*/
  	private String phoneExtNumber;
  	
  	/** ???????*/
  	private String localNo;
  	
  	/** ???????*/
  	private String localName;
  	
	/**??????????*/
    private String principalName;
    
    /**?????????????*/
    private String principalIdentifyType;
    
    /**?????????????*/
    private String principalIdentifyNumber;
    
    /**??????????????'????*/
    private String principalIdentifyStartDate;
    
    /**????????????????????*/
    private String principalIdentifyEndDate;  
    
    /**??????????*/
    private String verifyNumber;
    
    /**?????????*/
    private String loanAccount;

    /**????*/
    private String creditNumber;
/**?????*/
    private String collateralNumber;
/**????*/
    private String loansBehalfNumber;
/**?????*/
    private String loansDepartment;
    /**瀹㈡埗绶ㄧ⒓*/
    private String unitCode;
    /**鏈€浣庝繚璐?*/
    private String minimumPreium;
    /**瀹㈡埛琛屽姩鐢佃瘽*/
    private String mobileTelephone;
	

	/**
	 * 类PrpDcustomerUnit的默认构造方法
	 */
	public PrpDcustomerUnit() {
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
	 * 属性占用性质代码的getter方法
	 */

	@Column(name = "POSSESSNATURE")
	public String getPossessNature() {
		return this.possessNature;
	}

	/**
	 * 属性占用性质代码的setter方法
	 */
	public void setPossessNature(String possessNature) {
		this.possessNature = possessNature;
	}

	/**
	 * 属性行业代码的getter方法
	 */

	@Column(name = "BUSINESSSOURCE")
	public String getBusinessSource() {
		return this.businessSource;
	}

	/**
	 * 属性行业代码的setter方法
	 */
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	/**
	 * 属性所有制代码（单位性质）的getter方法
	 */

	@Column(name = "BUSINESSSORT")
	public String getBusinessSort() {
		return this.businessSort;
	}

	/**
	 * 属性所有制代码（单位性质）的setter方法
	 */
	public void setBusinessSort(String businessSort) {
		this.businessSort = businessSort;
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
	 * 属性法人组织机构代码 个人身份证号码的getter方法
	 */

	@Column(name = "ORGANIZECODE")
	public String getOrganizeCode() {
		return this.organizeCode;
	}

	/**
	 * 属性法人组织机构代码 个人身份证号码的setter方法
	 */
	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
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
	 * 属性法人代表的getter方法
	 */

	@Column(name = "LEADERNAME")
	public String getLeaderName() {
		return this.leaderName;
	}

	/**
	 * 属性法人代表的setter方法
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
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
	 * 属性电子信箱(U)的getter方法
	 */

	@Column(name = "EMAILADDRESS")
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * 属性电子信箱(U)的setter方法
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * 属性通信地址(I)的getter方法
	 */

	@Column(name = "POSTADDRESS")
	public String getPostAddress() {
		return this.postAddress;
	}

	/**
	 * 属性通信地址(I)的setter方法
	 */
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
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
	 * 属性联系人(U)的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性联系人(U)的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
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
	 * 属性工商局码的getter方法
	 */

	@Column(name = "INDUSTRYCODE")
	public String getIndustryCode() {
		return this.industryCode;
	}

	/**
	 * 属性工商局码的setter方法
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * 属性经贸委码的getter方法
	 */

	@Column(name = "ECONOMYCODE")
	public String getEconomyCode() {
		return this.economyCode;
	}

	/**
	 * 属性经贸委码的setter方法
	 */
	public void setEconomyCode(String economyCode) {
		this.economyCode = economyCode;
	}

	/**
	 * 属性标准计量码的getter方法
	 */

	@Column(name = "MEASURECODE")
	public String getMeasureCode() {
		return this.measureCode;
	}

	/**
	 * 属性标准计量码的setter方法
	 */
	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	/**
	 * 属性上级客户代码的getter方法
	 */

	@Column(name = "FATHERCODE")
	public String getFatherCode() {
		return this.fatherCode;
	}

	/**
	 * 属性上级客户代码的setter方法
	 */
	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

	/**
	 * 属性主管人名称的getter方法
	 */

	@Column(name = "SPONSORNAME")
	public String getSponsorName() {
		return this.sponsorName;
	}

	/**
	 * 属性主管人名称的setter方法
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * 属性经营范围的getter方法
	 */

	@Column(name = "BUSINESSRANGE")
	public String getBusinessRange() {
		return this.businessRange;
	}

	/**
	 * 属性经营范围的setter方法
	 */
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	/**
	 * 属性注册资金的getter方法
	 */

	@Column(name = "REGISTFUND")
	public BigDecimal getRegistFund() {
		return this.registFund;
	}

	/**
	 * 属性注册资金的setter方法
	 */
	public void setRegistFund(BigDecimal registFund) {
		this.registFund = registFund;
	}

	/**
	 * 属性行政区划编码的getter方法
	 */

	@Column(name = "REGIONCODE")
	public String getRegionCode() {
		return this.regionCode;
	}

	/**
	 * 属性行政区划编码的setter方法
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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
	 * 属性客户简称的getter方法
	 */

	@Column(name = "CUSTOMERSHORTNAME")
	public String getCustomerShortName() {
		return this.customerShortName;
	}

	/**
	 * 属性客户简称的setter方法
	 */
	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	/**
	 * 属性在册员工人数的getter方法
	 */

	@Column(name = "EMPLOYSUM")
	public String getEmploySum() {
		return this.employSum;
	}

	/**
	 * 属性在册员工人数的setter方法
	 */
	public void setEmploySum(String employSum) {
		this.employSum = employSum;
	}

	/**
	 * 属性是否股东 1/是 0/否的getter方法
	 */

	@Column(name = "SHAREHOLDERFLAG")
	public String getShareHolderFlag() {
		return this.shareHolderFlag;
	}

	/**
	 * 属性是否股东 1/是 0/否的setter方法
	 */
	public void setShareHolderFlag(String shareHolderFlag) {
		this.shareHolderFlag = shareHolderFlag;
	}

	/**
	 * 属性企业税务代码的getter方法
	 */

	@Column(name = "REVENUECODE")
	public String getRevenueCode() {
		return this.revenueCode;
	}

	/**
	 * 属性企业税务代码的setter方法
	 */
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}

	/**
	 * 属性职业风险等级的getter方法
	 */

	@Column(name = "WORDRISKRANK")
	public String getWordRiskRank() {
		return this.wordRiskRank;
	}

	/**
	 * 属性职业风险等级的setter方法
	 */
	public void setWordRiskRank(String wordRiskRank) {
		this.wordRiskRank = wordRiskRank;
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
	 * 属性备注1的getter方法
	 */

	@Column(name = "CAREERRISKGRADE")
	public String getCareerRiskGrade() {
		return this.careerRiskGrade;
	}

	/**
	 * 属性备注1的setter方法
	 */
	public void setCareerRiskGrade(String careerRiskGrade) {
		this.careerRiskGrade = careerRiskGrade;
	}

	/**
	 * 属性备注2的getter方法
	 */

	@Column(name = "TAXIDENTIFYCODE")
	public String getTaxIdentifyCode() {
		return this.taxIdentifyCode;
	}

	/**
	 * 属性备注2的setter方法
	 */
	public void setTaxIdentifyCode(String taxIdentifyCode) {
		this.taxIdentifyCode = taxIdentifyCode;
	}
	
	 // add by ZhaoXianYang 
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

 	@Column(name="LOCALNO")
 	public String getLocalNo() {
 		return localNo;
 	}

 	public void setLocalNo(String localNo) {
 		this.localNo = localNo;
 	}

 	@Column(name="LOCALNAME")
 	public String getLocalName() {
 		return localName;
 	}

 	public void setLocalName(String localName) {
 		this.localName = localName;
 	}

	/**??????????*/
	@Column(name = "PRINCIPALNAME")
    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**?????????????*/
    @Column(name = "PRINCIPALIDENTIFYTYPE")
    public String getPrincipalIdentifyType() {
        return principalIdentifyType;
    }

    public void setPrincipalIdentifyType(String principalIdentifyType) {
        this.principalIdentifyType = principalIdentifyType;
    }

    /**?????????????*/
    @Column(name = "PRINCIPALIDENTIFYNUMBER")
    public String getPrincipalIdentifyNumber() {
        return principalIdentifyNumber;
    }

    public void setPrincipalIdentifyNumber(String principalIdentifyNumber) {
        this.principalIdentifyNumber = principalIdentifyNumber;
    }

    /**??????????????'????*/
    @Column(name = "PRINCIPALIDENTIFYSTARTDATE")
    public String getPrincipalIdentifyStartDate() {
        return principalIdentifyStartDate;
    }

    public void setPrincipalIdentifyStartDate(String principalIdentifyStartDate) {
        this.principalIdentifyStartDate = principalIdentifyStartDate;
    }

    /**????????????????????*/
    @Column(name = "PRINCIPALIDENTIFYENDDATE")
    public String getPrincipalIdentifyEndDate() {
        return principalIdentifyEndDate;
    }

    public void setPrincipalIdentifyEndDate(String principalIdentifyEndDate) {
        this.principalIdentifyEndDate = principalIdentifyEndDate;
    }

    /**??????????*/
    @Column(name = "VERIFYNUMBER")
    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    /**?????????*/
    @Column(name = "LOANACCOUNT")
    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
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
    
    @Column(name = "UNITCODE")
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	 @Column(name = "MINIMUMPREIUM")
	public String getMinimumPreium() {
		return minimumPreium;
	}

	public void setMinimumPreium(String minimumPreium) {
		this.minimumPreium = minimumPreium;
	}
	@Column(name="MOBILETELEPHONE")
	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}

}
