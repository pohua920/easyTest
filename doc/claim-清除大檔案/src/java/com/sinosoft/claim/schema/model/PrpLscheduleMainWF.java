package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.dto.custom.TurnPageDto;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLscheduleMainWF调度任务/查勘任务主表
 */
@Entity
@Table(name = "PRPLSCHEDULEMAINWF")
public class PrpLscheduleMainWF implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLscheduleMainWFId id;

	/** 属性已查勘次数 */
	private Integer surveyNo;

	/** 属性理赔处理机构 */
	private String claimComCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号 */
	private String policyNo;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性计算机输单小时 */
	private Integer inputHour;

	/** 属性调度区域 */
	private int scheduleArea;

	/** 属性允许多级调度 */
	private String scheduleMoreFlag;

	/** 属性调度处理情况 */
	private String scheduleFlag;

	/** 属性调度对象代码 */
	private String scheduleObjectID;

	/** 属性调度对象名称 */
	private String scheduleObjectName;

	/** 属性调度处理标志 */
	private String scheduleType;

	/** 属性处理日期 */
	private Date checkInputDate;

	/** 属性查勘录入操作员代码 */
	private String checkOperatorCode;

	/** 属性查勘状态 */
	private String checkFlag;

	/** 属性处理信息 */
	private String checkInfo;

	/** 属性状态字段 */
	private String flag;

	/** 属性查勘地址 */
	private String checkSite;

	/** 属性下一个节点人的代码 */
	private String nextHandlerCode;

	/** 属性下一个节点人的名称 */
	private String nextHandlerName;

	/** 属性下一个节点的类型 */
	private String nextNodeNo;

	/** 属性分钟 */
	private BigDecimal inputMinute;

	/** 属性调度当前的状态 */
	private String scheduleStatus;

	/** 属性双代处理标志 */
	private String commiItemFlag;

	/** 属性部门名称 */
	private String claimComName = "";
	/** 属性操作员名称 */
	private String operatorName = "";
	/** 属性联系电话 */
	private String phoneNumber = "";
	/** 属性联系人 */
	private String linkerName = "";
	/** 出险详细情况，出险摘要 */
	private String registText = "";
	/** 节点状态信息 */
	private String status = "";
	/** 查勘操作员姓名 */
	private String checkOperatorName = "";
	/** 车牌号码 */
	private String licenseNo = "";
	/** 属性出险次数 */
	private int perilCount = 0;
	/** 新节点人的代码 */
	private String newHandlerCode = "";

	/** 保存的类别 cancel 表示是撤消的保存 getback 表示是取回的保存 其他的为正常保存 */
	private String saveType = "";

	/** 属性显示列表 */
	private Collection<PrpLscheduleMainWF> scheduleList;

	/** 属性案件双代标志 */
	private String commiFlag;

	/** 属性代理人代码 */
	private String agentCode = "";

	/** 属性代理人名称 */
	private String agentName = "";

	/** 属性估损金额 */
	private double estimateLoss = 0d;

	/** 属性估损费用 */
	private double estimateFee = 0d;

	TurnPageDto turnPageDto = null;

	/** 属性部门名称 */

	/**
	 * 类PrpLscheduleMainWF的默认构造方法
	 */
	public PrpLscheduleMainWF() {
		id = new PrpLscheduleMainWFId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "scheduleID", column = @Column(name = "SCHEDULEID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")) })
	public PrpLscheduleMainWFId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLscheduleMainWFId id) {
		this.id = id;
	}

	/**
	 * 属性已查勘次数的getter方法
	 */

	@Column(name = "SURVEYNO")
	public Integer getSurveyNo() {
		return this.surveyNo;
	}

	/**
	 * 属性已查勘次数的setter方法
	 */
	public void setSurveyNo(Integer surveyNo) {
		this.surveyNo = surveyNo;
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
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性计算机输单小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public Integer getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性计算机输单小时的setter方法
	 */
	public void setInputHour(Integer inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性调度区域的getter方法
	 */

	@Column(name = "SCHEDULEAREA")
	public int getScheduleArea() {
		return this.scheduleArea;
	}

	/**
	 * 属性调度区域的setter方法
	 */
	public void setScheduleArea(int scheduleArea) {
		this.scheduleArea = scheduleArea;
	}

	/**
	 * 属性允许多级调度的getter方法
	 */

	@Column(name = "SCHEDULEMOREFLAG")
	public String getScheduleMoreFlag() {
		return this.scheduleMoreFlag;
	}

	/**
	 * 属性允许多级调度的setter方法
	 */
	public void setScheduleMoreFlag(String scheduleMoreFlag) {
		this.scheduleMoreFlag = scheduleMoreFlag;
	}

	/**
	 * 属性调度处理情况的getter方法
	 */

	@Column(name = "SCHEDULEFLAG")
	public String getScheduleFlag() {
		return this.scheduleFlag;
	}

	/**
	 * 属性调度处理情况的setter方法
	 */
	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	/**
	 * 属性调度对象代码的getter方法
	 */

	@Column(name = "SCHEDULEOBJECTID")
	public String getScheduleObjectID() {
		return this.scheduleObjectID;
	}

	/**
	 * 属性调度对象代码的setter方法
	 */
	public void setScheduleObjectID(String scheduleObjectID) {
		this.scheduleObjectID = scheduleObjectID;
	}

	/**
	 * 属性调度对象名称的getter方法
	 */

	@Column(name = "SCHEDULEOBJECTNAME")
	public String getScheduleObjectName() {
		return this.scheduleObjectName;
	}

	/**
	 * 属性调度对象名称的setter方法
	 */
	public void setScheduleObjectName(String scheduleObjectName) {
		this.scheduleObjectName = scheduleObjectName;
	}

	/**
	 * 属性调度处理标志的getter方法
	 */

	@Column(name = "SCHEDULETYPE")
	public String getScheduleType() {
		return this.scheduleType;
	}

	/**
	 * 属性调度处理标志的setter方法
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	/**
	 * 属性处理日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECKINPUTDATE")
	public Date getCheckInputDate() {
		return this.checkInputDate;
	}

	/**
	 * 属性处理日期的setter方法
	 */
	public void setCheckInputDate(Date checkInputDate) {
		this.checkInputDate = checkInputDate;
	}

	/**
	 * 属性查勘录入操作员代码的getter方法
	 */

	@Column(name = "CHECKOPERATORCODE")
	public String getCheckOperatorCode() {
		return this.checkOperatorCode;
	}

	/**
	 * 属性查勘录入操作员代码的setter方法
	 */
	public void setCheckOperatorCode(String checkOperatorCode) {
		this.checkOperatorCode = checkOperatorCode;
	}

	/**
	 * 属性查勘状态的getter方法
	 */

	@Column(name = "CHECKFLAG")
	public String getCheckFlag() {
		return this.checkFlag;
	}

	/**
	 * 属性查勘状态的setter方法
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * 属性处理信息的getter方法
	 */

	@Column(name = "CHECKINFO")
	public String getCheckInfo() {
		return this.checkInfo;
	}

	/**
	 * 属性处理信息的setter方法
	 */
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性查勘地址的getter方法
	 */

	@Column(name = "CHECKSITE")
	public String getCheckSite() {
		return this.checkSite;
	}

	/**
	 * 属性查勘地址的setter方法
	 */
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}

	/**
	 * 属性下一个节点人的代码的getter方法
	 */

	@Column(name = "NEXTHANDLERCODE")
	public String getNextHandlerCode() {
		return this.nextHandlerCode;
	}

	/**
	 * 属性下一个节点人的代码的setter方法
	 */
	public void setNextHandlerCode(String nextHandlerCode) {
		this.nextHandlerCode = nextHandlerCode;
	}

	/**
	 * 属性下一个节点人的名称的getter方法
	 */

	@Column(name = "NEXTHANDLERNAME")
	public String getNextHandlerName() {
		return this.nextHandlerName;
	}

	/**
	 * 属性下一个节点人的名称的setter方法
	 */
	public void setNextHandlerName(String nextHandlerName) {
		this.nextHandlerName = nextHandlerName;
	}

	/**
	 * 属性下一个节点的类型的getter方法
	 */

	@Column(name = "NEXTNODENO")
	public String getNextNodeNo() {
		return this.nextNodeNo;
	}

	/**
	 * 属性下一个节点的类型的setter方法
	 */
	public void setNextNodeNo(String nextNodeNo) {
		this.nextNodeNo = nextNodeNo;
	}

	/**
	 * 属性分钟的getter方法
	 */

	@Column(name = "INPUTMINUTE")
	public BigDecimal getInputMinute() {
		return this.inputMinute;
	}

	/**
	 * 属性分钟的setter方法
	 */
	public void setInputMinute(BigDecimal inputMinute) {
		this.inputMinute = inputMinute;
	}

	/**
	 * 属性调度当前的状态的getter方法
	 */

	@Column(name = "SCHEDULESTATUS")
	public String getScheduleStatus() {
		return this.scheduleStatus;
	}

	/**
	 * 属性调度当前的状态的setter方法
	 */
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	/**
	 * 属性双代处理标志的getter方法
	 */

	@Column(name = "COMMIITEMFLAG")
	public String getCommiItemFlag() {
		return this.commiItemFlag;
	}

	/**
	 * 属性双代处理标志的setter方法
	 */
	public void setCommiItemFlag(String commiItemFlag) {
		this.commiItemFlag = commiItemFlag;
	}

	/**
	 * 设置属性估损费用
	 * @param estimateFee 待设置的属性估损费用
	 */
	public void setEstimateFee(double estimateFee) {
		this.estimateFee = estimateFee;
	}

	/**
	 * 获取属性估损费用
	 * @return 属性估损费用
	 */
	@Transient
	public double getEstimateFee() {
		return estimateFee;
	}

	/**
	 * @return Returns the agentCode.
	 */
	@Transient
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return Returns the agentName.
	 */
	@Transient
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * 设置属性部门
	 * @param comName 待设置的属性部门的值
	 */
	public void setClaimComName(String claimComName) {
		this.claimComName = StringUtils.rightTrim(claimComName);
	}

	/**
	 * 获取属性部门
	 * @return 属性部门的值
	 */
	@Transient
	public String getClaimComName() {
		return claimComName;
	}

	/**
	 * 设置属性操作员名称
	 * @param operatorName 待设置的属性操作员名称的值
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = StringUtils.rightTrim(operatorName);
	}

	/**
	 * 获取属性操作员名称
	 * @return 属性操作员名称的值
	 */
	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置属性联系电话
	 * @param phoneNumber 待设置的属性联系电话的值
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = StringUtils.rightTrim(phoneNumber);
	}

	/**
	 * 获取属性联系电话
	 * @return 属性联系电话的值
	 */
	@Transient
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 设置属性联系人
	 * @param linkerName 待设置的属性联系人的值
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = StringUtils.rightTrim(linkerName);
	}

	/**
	 * 获取属性联系人
	 * @return 属性联系人的值
	 */
	@Transient
	public String getLinkerName() {
		return linkerName;
	}

	/**
	 * 设置属性出险详细情况，出险摘要
	 * @param registText 待设置的属性出险详细情况，出险摘要的值
	 */
	public void setRegistText(String registText) {
		this.registText = StringUtils.rightTrim(registText);
	}

	/**
	 * 获取属性出险详细情况，出险摘要
	 * @return 属性出险详细情况，出险摘要的值
	 */
	@Transient
	public String getRegistText() {
		return registText;
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setScheduleList(Collection<PrpLscheduleMainWF> scheduleList) {
		this.scheduleList = scheduleList;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public Collection<PrpLscheduleMainWF> getScheduleList() {
		return scheduleList;
	}

	/**
	 * 获取保存类别操作状态
	 * @return 保存类别操作状态
	 */
	@Transient
	public String getSaveType() {
		return saveType;
	}

	/**
	 * 设置保存类别操作状态
	 * @param saveType 待设置的保存类别操作状态
	 */
	public void setSaveType(String saveType) {
		this.saveType = StringUtils.rightTrim(saveType);
	}

	/**
	 * 获取查勘操作员姓名
	 * @return 查勘操作员姓名
	 */
	@Transient
	public String getCheckOperatorName() {
		return checkOperatorName;
	}

	/**
	 * 设置查勘操作员姓名
	 * @param saveType 待设置的查勘操作员姓名
	 */
	public void setCheckOperatorName(String checkOperatorName) {
		this.checkOperatorName = StringUtils.rightTrim(checkOperatorName);
	}

	/**
	 * 获取车牌号码
	 * @return 车牌号码
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 设置车牌号码
	 * @param saveType 待设置的车牌号码
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = StringUtils.rightTrim(licenseNo);
	}

	/**
	 * 设置属性出险次数
	 * @param perilCount 待设置的属性出险次数的值
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	/**
	 * 获取属性出险次数 、* @return 属性出险次数的值
	 */
	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 设置属性新节点人的代码
	 * @param nextHandlerCode 待设置的属性新节点人的代码的值
	 */
	public void setNewHandlerCode(String newHandlerCode) {
		this.newHandlerCode = StringUtils.rightTrim(newHandlerCode);
	}

	/**
	 * 获取属性新节点人的代码
	 * @return 属性新节点人的代码的值
	 */
	@Transient
	public String getNewHandlerCode() {
		return newHandlerCode;
	}

	/**
	 * 设置属性估损金额
	 * @param estimateLoss 待设置的属性估损金额的值
	 */
	public void setEstimateLoss(double estimateLoss) {
		this.estimateLoss = estimateLoss;
	}

	/**
	 * 获取属性估损金额
	 * @return 属性估损金额的值
	 */
	@Transient
	public double getEstimateLoss() {
		return estimateLoss;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	/**
	 * 设置属性案件双代标识
	 * @param commiFlag 待设置的属性案件双代标识的值
	 */
	public void setCommiFlag(String commiFlag) {
		this.commiFlag = commiFlag;
	}

	/**
	 * 获取属性案件双代标识
	 * @return 属性案件双代标识的值
	 */
	@Transient
	public String getCommiFlag() {
		return commiFlag;
	}

}
