package com.sinosoft.dmsdriver.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpDcustomerFXQ
 */
@Entity
@Table(name = "PRPDCUSTOMERFXQ")
public class PrpDcustomerFXQ implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性CUSTOMERCODE */
	private String customerCode;

	/** 属性CUSTOMERTYPE */
	private String customerType;

	/** 属性SEX */
	private String sex;

	/** 属性OCCUPATIONNAME */
	private String occupationName;

	/** 属性OCCUPATIONCODE */
	private String occupationCode;

	/** 属性IDENTIFYNAME */
	private String identifyName;

	/** 属性IDENTIFYTYPE */
	private String identifyType;

	/** 属性IDENTIFYNUMBER */
	private String identifyNumber;

	/** 属性IDENTIFYSTARTDATE */
	private Date identifyStartDate;

	/** 属性IDENTIFYENDDATE */
	private Date identifyEndDate;

	/** 属性PHONENUMBER */
	private String phoneNumber;

	/** 属性BUSINESSRANGE */
	private String businessRange;

	/** 属性BUSINESSSOURCENAME */
	private String businessSourceName;

	/** 属性BUSINESSSOURCECODE */
	private String businessSourceCode;

	/** 属性SHAREHOLDERNAME */
	private String shareHolderName;

	/** 属性SHAREHOLDERIDENTIFYNAME */
	private String shareHolderIdentifyName;

	/** 属性SHAREHOLDERIDENTIFYTYPE */
	private String shareHolderIdentifyType;

	/** 属性SHAREHOLDERIDENTIFYNUMBER */
	private String shareHolderIdentifyNumber;

	/** 属性SHAREHOLDERIDENTIFYSTARTDATE */
	private Date shareHolderIdentifyStartDate;

	/** 属性SHAREHOLDERIDENTIFYENDDATE */
	private Date shareHolderIdentifyEndDate;

	/** 属性LEADERNAME */
	private String leaderName;

	/** 属性LEADERIDENTIFYNAME */
	private String leaderIdentifyName;

	/** 属性LEADERIDENTIFYTYPE */
	private String leaderIdentifyType;

	/** 属性LEADERIDENTIFYNUMBER */
	private String leaderIdentifyNumber;

	/** 属性LEADERIDENTIFYSTARTDATE */
	private Date leaderIdentifyStartDate;

	/** 属性LEADERIDENTIFYENDDATE */
	private Date leaderIdentifyEndDate;

	/** 属性PRINCIPALNAME */
	private String principalName;

	/** 属性PRINCIPALIDENTIFYNAME */
	private String principalIdentifyName;

	/** 属性PRINCIPALIDENTIFYTYPE */
	private String principalIdentifyType;

	/** 属性PRINCIPALIDENTIFYNUMBER */
	private String principalIdentifyNumber;

	/** 属性PRINCIPALIDENTIFYSTARTDATE */
	private Date principalIdentifyStartDate;

	/** 属性PRINCIPALIDENTIFYENDDATE */
	private Date principalIdentifyEndDate;

	/** 属性TAXREGISTERNUMBER */
	private String taxRegisterNumber;

	/** 属性FLAG */
	private String flag;

	/** 属性FLAG1 */
	private String flag1;

	/** 属性FLAG2 */
	private String flag2;

	/**
	 * 类PrpDcustomerFXQ的默认构造方法
	 */
	public PrpDcustomerFXQ() {
	}

	/**
	 * 属性CUSTOMERCODE的getter方法
	 */
	@Id
	@Column(name = "CUSTOMERCODE")
	public String getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * 属性CUSTOMERCODE的setter方法
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 属性CUSTOMERTYPE的getter方法
	 */

	@Column(name = "CUSTOMERTYPE")
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * 属性CUSTOMERTYPE的setter方法
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * 属性SEX的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性SEX的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性OCCUPATIONNAME的getter方法
	 */

	@Column(name = "OCCUPATIONNAME")
	public String getOccupationName() {
		return this.occupationName;
	}

	/**
	 * 属性OCCUPATIONNAME的setter方法
	 */
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}

	/**
	 * 属性OCCUPATIONCODE的getter方法
	 */

	@Column(name = "OCCUPATIONCODE")
	public String getOccupationCode() {
		return this.occupationCode;
	}

	/**
	 * 属性OCCUPATIONCODE的setter方法
	 */
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	/**
	 * 属性IDENTIFYNAME的getter方法
	 */

	@Column(name = "IDENTIFYNAME")
	public String getIdentifyName() {
		return this.identifyName;
	}

	/**
	 * 属性IDENTIFYNAME的setter方法
	 */
	public void setIdentifyName(String identifyName) {
		this.identifyName = identifyName;
	}

	/**
	 * 属性IDENTIFYTYPE的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性IDENTIFYTYPE的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性IDENTIFYNUMBER的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性IDENTIFYNUMBER的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性IDENTIFYSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "IDENTIFYSTARTDATE")
	public Date getIdentifyStartDate() {
		return this.identifyStartDate;
	}

	/**
	 * 属性IDENTIFYSTARTDATE的setter方法
	 */
	public void setIdentifyStartDate(Date identifyStartDate) {
		this.identifyStartDate = identifyStartDate;
	}

	/**
	 * 属性IDENTIFYENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "IDENTIFYENDDATE")
	public Date getIdentifyEndDate() {
		return this.identifyEndDate;
	}

	/**
	 * 属性IDENTIFYENDDATE的setter方法
	 */
	public void setIdentifyEndDate(Date identifyEndDate) {
		this.identifyEndDate = identifyEndDate;
	}

	/**
	 * 属性PHONENUMBER的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性PHONENUMBER的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性BUSINESSRANGE的getter方法
	 */

	@Column(name = "BUSINESSRANGE")
	public String getBusinessRange() {
		return this.businessRange;
	}

	/**
	 * 属性BUSINESSRANGE的setter方法
	 */
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	/**
	 * 属性BUSINESSSOURCENAME的getter方法
	 */

	@Column(name = "BUSINESSSOURCENAME")
	public String getBusinessSourceName() {
		return this.businessSourceName;
	}

	/**
	 * 属性BUSINESSSOURCENAME的setter方法
	 */
	public void setBusinessSourceName(String businessSourceName) {
		this.businessSourceName = businessSourceName;
	}

	/**
	 * 属性BUSINESSSOURCECODE的getter方法
	 */

	@Column(name = "BUSINESSSOURCECODE")
	public String getBusinessSourceCode() {
		return this.businessSourceCode;
	}

	/**
	 * 属性BUSINESSSOURCECODE的setter方法
	 */
	public void setBusinessSourceCode(String businessSourceCode) {
		this.businessSourceCode = businessSourceCode;
	}

	/**
	 * 属性SHAREHOLDERNAME的getter方法
	 */

	@Column(name = "SHAREHOLDERNAME")
	public String getShareHolderName() {
		return this.shareHolderName;
	}

	/**
	 * 属性SHAREHOLDERNAME的setter方法
	 */
	public void setShareHolderName(String shareHolderName) {
		this.shareHolderName = shareHolderName;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYNAME的getter方法
	 */

	@Column(name = "SHAREHOLDERIDENTIFYNAME")
	public String getShareHolderIdentifyName() {
		return this.shareHolderIdentifyName;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYNAME的setter方法
	 */
	public void setShareHolderIdentifyName(String shareHolderIdentifyName) {
		this.shareHolderIdentifyName = shareHolderIdentifyName;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYTYPE的getter方法
	 */

	@Column(name = "SHAREHOLDERIDENTIFYTYPE")
	public String getShareHolderIdentifyType() {
		return this.shareHolderIdentifyType;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYTYPE的setter方法
	 */
	public void setShareHolderIdentifyType(String shareHolderIdentifyType) {
		this.shareHolderIdentifyType = shareHolderIdentifyType;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYNUMBER的getter方法
	 */

	@Column(name = "SHAREHOLDERIDENTIFYNUMBER")
	public String getShareHolderIdentifyNumber() {
		return this.shareHolderIdentifyNumber;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYNUMBER的setter方法
	 */
	public void setShareHolderIdentifyNumber(String shareHolderIdentifyNumber) {
		this.shareHolderIdentifyNumber = shareHolderIdentifyNumber;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SHAREHOLDERIDENTIFYSTARTDATE")
	public Date getShareHolderIdentifyStartDate() {
		return this.shareHolderIdentifyStartDate;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYSTARTDATE的setter方法
	 */
	public void setShareHolderIdentifyStartDate(
			Date shareHolderIdentifyStartDate) {
		this.shareHolderIdentifyStartDate = shareHolderIdentifyStartDate;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SHAREHOLDERIDENTIFYENDDATE")
	public Date getShareHolderIdentifyEndDate() {
		return this.shareHolderIdentifyEndDate;
	}

	/**
	 * 属性SHAREHOLDERIDENTIFYENDDATE的setter方法
	 */
	public void setShareHolderIdentifyEndDate(Date shareHolderIdentifyEndDate) {
		this.shareHolderIdentifyEndDate = shareHolderIdentifyEndDate;
	}

	/**
	 * 属性LEADERNAME的getter方法
	 */

	@Column(name = "LEADERNAME")
	public String getLeaderName() {
		return this.leaderName;
	}

	/**
	 * 属性LEADERNAME的setter方法
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * 属性LEADERIDENTIFYNAME的getter方法
	 */

	@Column(name = "LEADERIDENTIFYNAME")
	public String getLeaderIdentifyName() {
		return this.leaderIdentifyName;
	}

	/**
	 * 属性LEADERIDENTIFYNAME的setter方法
	 */
	public void setLeaderIdentifyName(String leaderIdentifyName) {
		this.leaderIdentifyName = leaderIdentifyName;
	}

	/**
	 * 属性LEADERIDENTIFYTYPE的getter方法
	 */

	@Column(name = "LEADERIDENTIFYTYPE")
	public String getLeaderIdentifyType() {
		return this.leaderIdentifyType;
	}

	/**
	 * 属性LEADERIDENTIFYTYPE的setter方法
	 */
	public void setLeaderIdentifyType(String leaderIdentifyType) {
		this.leaderIdentifyType = leaderIdentifyType;
	}

	/**
	 * 属性LEADERIDENTIFYNUMBER的getter方法
	 */

	@Column(name = "LEADERIDENTIFYNUMBER")
	public String getLeaderIdentifyNumber() {
		return this.leaderIdentifyNumber;
	}

	/**
	 * 属性LEADERIDENTIFYNUMBER的setter方法
	 */
	public void setLeaderIdentifyNumber(String leaderIdentifyNumber) {
		this.leaderIdentifyNumber = leaderIdentifyNumber;
	}

	/**
	 * 属性LEADERIDENTIFYSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LEADERIDENTIFYSTARTDATE")
	public Date getLeaderIdentifyStartDate() {
		return this.leaderIdentifyStartDate;
	}

	/**
	 * 属性LEADERIDENTIFYSTARTDATE的setter方法
	 */
	public void setLeaderIdentifyStartDate(Date leaderIdentifyStartDate) {
		this.leaderIdentifyStartDate = leaderIdentifyStartDate;
	}

	/**
	 * 属性LEADERIDENTIFYENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LEADERIDENTIFYENDDATE")
	public Date getLeaderIdentifyEndDate() {
		return this.leaderIdentifyEndDate;
	}

	/**
	 * 属性LEADERIDENTIFYENDDATE的setter方法
	 */
	public void setLeaderIdentifyEndDate(Date leaderIdentifyEndDate) {
		this.leaderIdentifyEndDate = leaderIdentifyEndDate;
	}

	/**
	 * 属性PRINCIPALNAME的getter方法
	 */

	@Column(name = "PRINCIPALNAME")
	public String getPrincipalName() {
		return this.principalName;
	}

	/**
	 * 属性PRINCIPALNAME的setter方法
	 */
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	/**
	 * 属性PRINCIPALIDENTIFYNAME的getter方法
	 */

	@Column(name = "PRINCIPALIDENTIFYNAME")
	public String getPrincipalIdentifyName() {
		return this.principalIdentifyName;
	}

	/**
	 * 属性PRINCIPALIDENTIFYNAME的setter方法
	 */
	public void setPrincipalIdentifyName(String principalIdentifyName) {
		this.principalIdentifyName = principalIdentifyName;
	}

	/**
	 * 属性PRINCIPALIDENTIFYTYPE的getter方法
	 */

	@Column(name = "PRINCIPALIDENTIFYTYPE")
	public String getPrincipalIdentifyType() {
		return this.principalIdentifyType;
	}

	/**
	 * 属性PRINCIPALIDENTIFYTYPE的setter方法
	 */
	public void setPrincipalIdentifyType(String principalIdentifyType) {
		this.principalIdentifyType = principalIdentifyType;
	}

	/**
	 * 属性PRINCIPALIDENTIFYNUMBER的getter方法
	 */

	@Column(name = "PRINCIPALIDENTIFYNUMBER")
	public String getPrincipalIdentifyNumber() {
		return this.principalIdentifyNumber;
	}

	/**
	 * 属性PRINCIPALIDENTIFYNUMBER的setter方法
	 */
	public void setPrincipalIdentifyNumber(String principalIdentifyNumber) {
		this.principalIdentifyNumber = principalIdentifyNumber;
	}

	/**
	 * 属性PRINCIPALIDENTIFYSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRINCIPALIDENTIFYSTARTDATE")
	public Date getPrincipalIdentifyStartDate() {
		return this.principalIdentifyStartDate;
	}

	/**
	 * 属性PRINCIPALIDENTIFYSTARTDATE的setter方法
	 */
	public void setPrincipalIdentifyStartDate(Date principalIdentifyStartDate) {
		this.principalIdentifyStartDate = principalIdentifyStartDate;
	}

	/**
	 * 属性PRINCIPALIDENTIFYENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRINCIPALIDENTIFYENDDATE")
	public Date getPrincipalIdentifyEndDate() {
		return this.principalIdentifyEndDate;
	}

	/**
	 * 属性PRINCIPALIDENTIFYENDDATE的setter方法
	 */
	public void setPrincipalIdentifyEndDate(Date principalIdentifyEndDate) {
		this.principalIdentifyEndDate = principalIdentifyEndDate;
	}

	/**
	 * 属性TAXREGISTERNUMBER的getter方法
	 */

	@Column(name = "TAXREGISTERNUMBER")
	public String getTaxRegisterNumber() {
		return this.taxRegisterNumber;
	}

	/**
	 * 属性TAXREGISTERNUMBER的setter方法
	 */
	public void setTaxRegisterNumber(String taxRegisterNumber) {
		this.taxRegisterNumber = taxRegisterNumber;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性FLAG1的getter方法
	 */

	@Column(name = "FLAG1")
	public String getFlag1() {
		return this.flag1;
	}

	/**
	 * 属性FLAG1的setter方法
	 */
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	/**
	 * 属性FLAG2的getter方法
	 */

	@Column(name = "FLAG2")
	public String getFlag2() {
		return this.flag2;
	}

	/**
	 * 属性FLAG2的setter方法
	 */
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

}
