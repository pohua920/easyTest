package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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
 * POJO类PrpLscheduleItem调度任务标的表
 */
@Entity
@Table(name = "PRPLSCHEDULEITEM")
public class PrpLscheduleItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLscheduleItemId id;

	/** 属性是否为本保单车辆(1.是2.否) */
	private String insureCarFlag;

	/** 属性理赔处理机构 */
	private String claimComCode;

	/** 属性是否选择发送 */
	private String selectSend;

	/** 属性查勘次数 */
	private Integer surveyTimes;

	/** 属性查勘地点类型(第一现场) */
	private String surveyType;

	/** 属性查勘地址 */
	private String checkSite;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性调度对象代码 */
	private String scheduleObjectID;

	/** 属性调度对象名称 */
	private String scheduleObjectName;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性调度信息 */
	private String resultInfo;

	/** 属性预约查勘(定损)状态 */
	private String bookFlag;

	/** 属性调度处理标志 */
	private String scheduleType;

	/** 属性状态字段 */
	private String flag;

	/** 属性下一个节点人的代码 */
	private String nextHandlerCode;

	/** 属性下一个节点人的名称 */
	private String nextHandlerName;

	/** 属性下一个节点的类型 */
	private String nextNodeNo;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性修理厂联系电话 */
	private String factoryPhone;

	/** 属性修理厂报损总金额 */
	private Double factoryEstimateLoss;

	/** 属性紧急程度 */
	private String exigenceGree;

	/** 属性推荐修理厂名称 */
	private String commendRepairFactoryName;

	/** 属性调度当前的状态 */
	private String scheduleStatus;

	/** 属性双代处理标志 */
	private String commiItemFlag;

	/** 属性推荐修理厂代码 */
	private String commendRepairFactoryCode;

	/** 属性显示列表 */
	private Collection<PrpLscheduleItem> scheduleItemList;
	/** 属性操作员名称 */
	private String operatorName = "";

	TurnPageDto turnPageDto = null;

	/**
	 * 类PrpLscheduleItem的默认构造方法
	 */
	public PrpLscheduleItem() {
		id = new PrpLscheduleItemId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "scheduleID", column = @Column(name = "SCHEDULEID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpLscheduleItemId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLscheduleItemId id) {
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

	@Column(name = "SELECTSEND")
	public String getSelectSend() {
		return this.selectSend;
	}

	/**
	 * 属性是否选择发送的setter方法
	 */
	public void setSelectSend(String selectSend) {
		this.selectSend = selectSend;
	}

	/**
	 * 属性查勘次数的getter方法
	 */

	@Column(name = "SURVEYTIMES")
	public Integer getSurveyTimes() {
		return this.surveyTimes;
	}

	/**
	 * 属性查勘次数的setter方法
	 */
	public void setSurveyTimes(Integer surveyTimes) {
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
	 * 属性车牌号的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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
	 * 属性修理厂联系电话的getter方法
	 */

	@Column(name = "FACTORYPHONE")
	public String getFactoryPhone() {
		return this.factoryPhone;
	}

	/**
	 * 属性修理厂联系电话的setter方法
	 */
	public void setFactoryPhone(String factoryPhone) {
		this.factoryPhone = factoryPhone;
	}

	/**
	 * 属性修理厂报损总金额的getter方法
	 */

	@Column(name = "FACTORYESTIMATELOSS")
	public Double getFactoryEstimateLoss() {
		return this.factoryEstimateLoss;
	}

	/**
	 * 属性修理厂报损总金额的setter方法
	 */
	public void setFactoryEstimateLoss(Double factoryEstimateLoss) {
		this.factoryEstimateLoss = factoryEstimateLoss;
	}

	/**
	 * 属性紧急程度的getter方法
	 */

	@Column(name = "EXIGENCEGREE")
	public String getExigenceGree() {
		return this.exigenceGree;
	}

	/**
	 * 属性紧急程度的setter方法
	 */
	public void setExigenceGree(String exigenceGree) {
		this.exigenceGree = exigenceGree;
	}

	/**
	 * 属性推荐修理厂名称的getter方法
	 */

	@Column(name = "COMMENDREPAIRFACTORYNAME")
	public String getCommendRepairFactoryName() {
		return this.commendRepairFactoryName;
	}

	/**
	 * 属性推荐修理厂名称的setter方法
	 */
	public void setCommendRepairFactoryName(String commendRepairFactoryName) {
		this.commendRepairFactoryName = commendRepairFactoryName;
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
	 * 属性推荐修理厂代码的getter方法
	 */

	@Column(name = "COMMENDREPAIRFACTORYCODE")
	public String getCommendRepairFactoryCode() {
		return this.commendRepairFactoryCode;
	}

	/**
	 * 属性推荐修理厂代码的setter方法
	 */
	public void setCommendRepairFactoryCode(String commendRepairFactoryCode) {
		this.commendRepairFactoryCode = commendRepairFactoryCode;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setScheduleItemList(Collection<PrpLscheduleItem> scheduleItemList) {
		this.scheduleItemList = scheduleItemList;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public Collection<PrpLscheduleItem> getScheduleItemList() {
		return scheduleItemList;
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

}
