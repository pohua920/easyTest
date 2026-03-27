package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLinvestigate
 */
@Entity
@Table(name = "PRPLINVESTIGATE")
public class PrpLinvestigate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLinvestigateId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性被调查人姓名 */
	private String informantName;

	/** 属性被调查人身份 */
	private String informantStatus;

	/** 属性调查对象名称 */
	private String objectName;

	/** 属性调查对象单位 */
	private String objectUnit;

	/** 属性调查对象地址 */
	private String objectAddress;

	/** 属性调查对象电话 */
	private String objectPhone;

	/** 属性调查对象年收入 */
	private BigDecimal yearIncome;

	/** 属性交/领款人证件号码 */
	private String identifyNumber;

	/** 属性调查对象营业执照号码 */
	private String businessCode;

	/** 属性贷款金额 */
	private BigDecimal loanAmount;

	/** 属性已还款金额 */
	private BigDecimal sumRePaid;

	/** 属性尚欠金额 */
	private BigDecimal arrearageCorpus;

	/** 属性最近一次还款日期 */
	private Date lastRepaidDate;

	/** 属性逾期时间 */
	private Date arrearageDate;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性厂牌型号 */
	private String brandName;

	/** 属性车辆价格 */
	private BigDecimal purchasePrice;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性车架号 */
	private String frameNo;

	/** 属性车辆用途 */
	private String useNature;

	/** 属性购车日期 */
	private Date purchaseDate;

	/** 属性初次登记日期 */
	private Date enrollDate;

	/** 属性抵押登记单位 */
	private String inpawnEnrollDept;

	/** 属性抵押登记日期 */
	private Date inpawnEnrollDate;

	/** 属性抵押物名称 */
	private String guarantyName;

	/** 属性抵押物是否收回(Y/N) */
	private String guarantyRetractFlg;

	/** 属性抵押物估价 */
	private BigDecimal guarantyAssessment;

	/** 属性售车款收取方式 */
	private String gatheringWay;

	/** 属性欠款原因代码 */
	private String arrearReasonCode;

	/** 属性欠款原因 */
	private String arrearReasonName;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性调查结论 */
	private String conclution;

	/** 属性调查者 */
	private String investigator;

	/** 属性调查时间 */
	private Date investigateDate;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLinvestigate的默认构造方法
	 */
	public PrpLinvestigate() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "objectType", column = @Column(name = "OBJECTTYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLinvestigateId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLinvestigateId id) {
		this.id = id;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性被调查人姓名的getter方法
	 */

	@Column(name = "INFORMANTNAME")
	public String getInformantName() {
		return this.informantName;
	}

	/**
	 * 属性被调查人姓名的setter方法
	 */
	public void setInformantName(String informantName) {
		this.informantName = informantName;
	}

	/**
	 * 属性被调查人身份的getter方法
	 */

	@Column(name = "INFORMANTSTATUS")
	public String getInformantStatus() {
		return this.informantStatus;
	}

	/**
	 * 属性被调查人身份的setter方法
	 */
	public void setInformantStatus(String informantStatus) {
		this.informantStatus = informantStatus;
	}

	/**
	 * 属性调查对象名称的getter方法
	 */

	@Column(name = "OBJECTNAME")
	public String getObjectName() {
		return this.objectName;
	}

	/**
	 * 属性调查对象名称的setter方法
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * 属性调查对象单位的getter方法
	 */

	@Column(name = "OBJECTUNIT")
	public String getObjectUnit() {
		return this.objectUnit;
	}

	/**
	 * 属性调查对象单位的setter方法
	 */
	public void setObjectUnit(String objectUnit) {
		this.objectUnit = objectUnit;
	}

	/**
	 * 属性调查对象地址的getter方法
	 */

	@Column(name = "OBJECTADDRESS")
	public String getObjectAddress() {
		return this.objectAddress;
	}

	/**
	 * 属性调查对象地址的setter方法
	 */
	public void setObjectAddress(String objectAddress) {
		this.objectAddress = objectAddress;
	}

	/**
	 * 属性调查对象电话的getter方法
	 */

	@Column(name = "OBJECTPHONE")
	public String getObjectPhone() {
		return this.objectPhone;
	}

	/**
	 * 属性调查对象电话的setter方法
	 */
	public void setObjectPhone(String objectPhone) {
		this.objectPhone = objectPhone;
	}

	/**
	 * 属性调查对象年收入的getter方法
	 */

	@Column(name = "YEARINCOME")
	public BigDecimal getYearIncome() {
		return this.yearIncome;
	}

	/**
	 * 属性调查对象年收入的setter方法
	 */
	public void setYearIncome(BigDecimal yearIncome) {
		this.yearIncome = yearIncome;
	}

	/**
	 * 属性交/领款人证件号码的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性交/领款人证件号码的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性调查对象营业执照号码的getter方法
	 */

	@Column(name = "BUSINESSCODE")
	public String getBusinessCode() {
		return this.businessCode;
	}

	/**
	 * 属性调查对象营业执照号码的setter方法
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * 属性贷款金额的getter方法
	 */

	@Column(name = "LOANAMOUNT")
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	/**
	 * 属性贷款金额的setter方法
	 */
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * 属性已还款金额的getter方法
	 */

	@Column(name = "SUMREPAID")
	public BigDecimal getSumRePaid() {
		return this.sumRePaid;
	}

	/**
	 * 属性已还款金额的setter方法
	 */
	public void setSumRePaid(BigDecimal sumRePaid) {
		this.sumRePaid = sumRePaid;
	}

	/**
	 * 属性尚欠金额的getter方法
	 */

	@Column(name = "ARREARAGECORPUS")
	public BigDecimal getArrearageCorpus() {
		return this.arrearageCorpus;
	}

	/**
	 * 属性尚欠金额的setter方法
	 */
	public void setArrearageCorpus(BigDecimal arrearageCorpus) {
		this.arrearageCorpus = arrearageCorpus;
	}

	/**
	 * 属性最近一次还款日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LASTREPAIDDATE")
	public Date getLastRepaidDate() {
		return this.lastRepaidDate;
	}

	/**
	 * 属性最近一次还款日期的setter方法
	 */
	public void setLastRepaidDate(Date lastRepaidDate) {
		this.lastRepaidDate = lastRepaidDate;
	}

	/**
	 * 属性逾期时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ARREARAGEDATE")
	public Date getArrearageDate() {
		return this.arrearageDate;
	}

	/**
	 * 属性逾期时间的setter方法
	 */
	public void setArrearageDate(Date arrearageDate) {
		this.arrearageDate = arrearageDate;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性厂牌型号的getter方法
	 */

	@Column(name = "BRANDNAME")
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 属性厂牌型号的setter方法
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 属性车辆价格的getter方法
	 */

	@Column(name = "PURCHASEPRICE")
	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * 属性车辆价格的setter方法
	 */
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 属性发动机号的getter方法
	 */

	@Column(name = "ENGINENO")
	public String getEngineNo() {
		return this.engineNo;
	}

	/**
	 * 属性发动机号的setter方法
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * 属性车架号的getter方法
	 */

	@Column(name = "FRAMENO")
	public String getFrameNo() {
		return this.frameNo;
	}

	/**
	 * 属性车架号的setter方法
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * 属性车辆用途的getter方法
	 */

	@Column(name = "USENATURE")
	public String getUseNature() {
		return this.useNature;
	}

	/**
	 * 属性车辆用途的setter方法
	 */
	public void setUseNature(String useNature) {
		this.useNature = useNature;
	}

	/**
	 * 属性购车日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PURCHASEDATE")
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * 属性购车日期的setter方法
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * 属性初次登记日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENROLLDATE")
	public Date getEnrollDate() {
		return this.enrollDate;
	}

	/**
	 * 属性初次登记日期的setter方法
	 */
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * 属性抵押登记单位的getter方法
	 */

	@Column(name = "INPAWNENROLLDEPT")
	public String getInpawnEnrollDept() {
		return this.inpawnEnrollDept;
	}

	/**
	 * 属性抵押登记单位的setter方法
	 */
	public void setInpawnEnrollDept(String inpawnEnrollDept) {
		this.inpawnEnrollDept = inpawnEnrollDept;
	}

	/**
	 * 属性抵押登记日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPAWNENROLLDATE")
	public Date getInpawnEnrollDate() {
		return this.inpawnEnrollDate;
	}

	/**
	 * 属性抵押登记日期的setter方法
	 */
	public void setInpawnEnrollDate(Date inpawnEnrollDate) {
		this.inpawnEnrollDate = inpawnEnrollDate;
	}

	/**
	 * 属性抵押物名称的getter方法
	 */

	@Column(name = "GUARANTYNAME")
	public String getGuarantyName() {
		return this.guarantyName;
	}

	/**
	 * 属性抵押物名称的setter方法
	 */
	public void setGuarantyName(String guarantyName) {
		this.guarantyName = guarantyName;
	}

	/**
	 * 属性抵押物是否收回(Y/N)的getter方法
	 */

	@Column(name = "GUARANTYRETRACTFLG")
	public String getGuarantyRetractFlg() {
		return this.guarantyRetractFlg;
	}

	/**
	 * 属性抵押物是否收回(Y/N)的setter方法
	 */
	public void setGuarantyRetractFlg(String guarantyRetractFlg) {
		this.guarantyRetractFlg = guarantyRetractFlg;
	}

	/**
	 * 属性抵押物估价的getter方法
	 */

	@Column(name = "GUARANTYASSESSMENT")
	public BigDecimal getGuarantyAssessment() {
		return this.guarantyAssessment;
	}

	/**
	 * 属性抵押物估价的setter方法
	 */
	public void setGuarantyAssessment(BigDecimal guarantyAssessment) {
		this.guarantyAssessment = guarantyAssessment;
	}

	/**
	 * 属性售车款收取方式的getter方法
	 */

	@Column(name = "GATHERINGWAY")
	public String getGatheringWay() {
		return this.gatheringWay;
	}

	/**
	 * 属性售车款收取方式的setter方法
	 */
	public void setGatheringWay(String gatheringWay) {
		this.gatheringWay = gatheringWay;
	}

	/**
	 * 属性欠款原因代码的getter方法
	 */

	@Column(name = "ARREARREASONCODE")
	public String getArrearReasonCode() {
		return this.arrearReasonCode;
	}

	/**
	 * 属性欠款原因代码的setter方法
	 */
	public void setArrearReasonCode(String arrearReasonCode) {
		this.arrearReasonCode = arrearReasonCode;
	}

	/**
	 * 属性欠款原因的getter方法
	 */

	@Column(name = "ARREARREASONNAME")
	public String getArrearReasonName() {
		return this.arrearReasonName;
	}

	/**
	 * 属性欠款原因的setter方法
	 */
	public void setArrearReasonName(String arrearReasonName) {
		this.arrearReasonName = arrearReasonName;
	}

	/**
	 * 属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性调查结论的getter方法
	 */

	@Column(name = "CONCLUTION")
	public String getConclution() {
		return this.conclution;
	}

	/**
	 * 属性调查结论的setter方法
	 */
	public void setConclution(String conclution) {
		this.conclution = conclution;
	}

	/**
	 * 属性调查者的getter方法
	 */

	@Column(name = "INVESTIGATOR")
	public String getInvestigator() {
		return this.investigator;
	}

	/**
	 * 属性调查者的setter方法
	 */
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	/**
	 * 属性调查时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INVESTIGATEDATE")
	public Date getInvestigateDate() {
		return this.investigateDate;
	}

	/**
	 * 属性调查时间的setter方法
	 */
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
