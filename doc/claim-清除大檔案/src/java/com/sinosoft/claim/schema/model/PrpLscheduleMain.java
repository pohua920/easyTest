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
 * POJO类PrpLscheduleMain调度任务主表
 */
@Entity
@Table(name = "PRPLSCHEDULEMAIN")
public class PrpLscheduleMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLscheduleMainId id;

	/** 属性是否为本保单车辆(1.是2.否) */
	private String insureCarFlag;

	/** 属性理赔处理机构 */
	private String claimComCode;

	/** 属性是否选择发送 */
	private String selectTSend;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性调度单位代码,可以为修理厂,定损点 */
	private String scheduleDeptCode;

	/** 属性被调度单位名称 (新增) */
	private String scheduleDeptName;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorname;

	/** 属性查勘次数 */
	private BigDecimal surveyTimes;

	/** 属性查勘地点类型(第一现场) */
	private String surveyType;

	/** 属性查勘地址 */
	private String surveyAddress;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性小时 */
	private BigDecimal inputHour;

	/** 属性调度信息 */
	private String resultInfo;

	/** 属性处理案件标志 */
	private String transFlag;

	/** 属性预约查勘(定损)状态 */
	private String bookFlag;

	/** 属性调度处理标志 */
	private String scheduleFlag;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLscheduleMain的默认构造方法
	 */
	public PrpLscheduleMain() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "scheduleID", column = @Column(name = "SCHEDULEID")), @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLscheduleMainId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLscheduleMainId id) {
		this.id = id;
	}

	/**
	 * 属性是否为本保单车辆(1.是2.否)的getter方法
	 */

	@Column(name = "INSURECARFLAG")
	public String getInsureCarFlag() {
		return this.insureCarFlag;
	}

	/**
	 * 属性是否为本保单车辆(1.是2.否)的setter方法
	 */
	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	/**
	 * 属性理赔处理机构的getter方法
	 */

	@Column(name = "CLAIMCOMCODE")
	public String getClaimComCode() {
		return this.claimComCode;
	}

	/**
	 * 属性理赔处理机构的setter方法
	 */
	public void setClaimComCode(String claimComCode) {
		this.claimComCode = claimComCode;
	}

	/**
	 * 属性是否选择发送的getter方法
	 */

	@Column(name = "SELECTTSEND")
	public String getSelectTSend() {
		return this.selectTSend;
	}

	/**
	 * 属性是否选择发送的setter方法
	 */
	public void setSelectTSend(String selectTSend) {
		this.selectTSend = selectTSend;
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
	 * 属性调度单位代码,可以为修理厂,定损点的getter方法
	 */

	@Column(name = "SCHEDULEDEPTCODE")
	public String getScheduleDeptCode() {
		return this.scheduleDeptCode;
	}

	/**
	 * 属性调度单位代码,可以为修理厂,定损点的setter方法
	 */
	public void setScheduleDeptCode(String scheduleDeptCode) {
		this.scheduleDeptCode = scheduleDeptCode;
	}

	/**
	 * 属性被调度单位名称 (新增)的getter方法
	 */

	@Column(name = "SCHEDULEDEPTNAME")
	public String getScheduleDeptName() {
		return this.scheduleDeptName;
	}

	/**
	 * 属性被调度单位名称 (新增)的setter方法
	 */
	public void setScheduleDeptName(String scheduleDeptName) {
		this.scheduleDeptName = scheduleDeptName;
	}

	/**
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorname() {
		return this.operatorname;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	/**
	 * 属性查勘次数的getter方法
	 */

	@Column(name = "SURVEYTIMES")
	public BigDecimal getSurveyTimes() {
		return this.surveyTimes;
	}

	/**
	 * 属性查勘次数的setter方法
	 */
	public void setSurveyTimes(BigDecimal surveyTimes) {
		this.surveyTimes = surveyTimes;
	}

	/**
	 * 属性查勘地点类型(第一现场)的getter方法
	 */

	@Column(name = "SURVEYTYPE")
	public String getSurveyType() {
		return this.surveyType;
	}

	/**
	 * 属性查勘地点类型(第一现场)的setter方法
	 */
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}

	/**
	 * 属性查勘地址的getter方法
	 */

	@Column(name = "SURVEYADDRESS")
	public String getSurveyAddress() {
		return this.surveyAddress;
	}

	/**
	 * 属性查勘地址的setter方法
	 */
	public void setSurveyAddress(String surveyAddress) {
		this.surveyAddress = surveyAddress;
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
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public BigDecimal getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性小时的setter方法
	 */
	public void setInputHour(BigDecimal inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性调度信息的getter方法
	 */

	@Column(name = "RESULTINFO")
	public String getResultInfo() {
		return this.resultInfo;
	}

	/**
	 * 属性调度信息的setter方法
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * 属性处理案件标志的getter方法
	 */

	@Column(name = "TRANSFLAG")
	public String getTransFlag() {
		return this.transFlag;
	}

	/**
	 * 属性处理案件标志的setter方法
	 */
	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	/**
	 * 属性预约查勘(定损)状态的getter方法
	 */

	@Column(name = "BOOKFLAG")
	public String getBookFlag() {
		return this.bookFlag;
	}

	/**
	 * 属性预约查勘(定损)状态的setter方法
	 */
	public void setBookFlag(String bookFlag) {
		this.bookFlag = bookFlag;
	}

	/**
	 * 属性调度处理标志的getter方法
	 */

	@Column(name = "SCHEDULEFLAG")
	public String getScheduleFlag() {
		return this.scheduleFlag;
	}

	/**
	 * 属性调度处理标志的setter方法
	 */
	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
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
