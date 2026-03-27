package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfLogStore工作流日志表转储表
 */
@Entity
@Table(name = "SWFLOGSTORE")
public class SwfLogStore implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfLogStoreId id;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性当前节点号 */
	private Integer nodeNo = 0;

	/** 属性当前节点名称 */
	private String nodeName;

	/** 属性业务号 */
	private String businessNo;

	/** 属性处理部门 */
	private String handleDept;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性处理人员名称 */
	private String handlerName;

	/** 属性流入时间 */
	private String flowInTime;

	/** 属性处理时限 */
	private Integer timeLimit = 0;

	/** 属性处理时间 */
	private String handleTime;

	/** 属性提交时间 */
	private String submitTime;

	/** 属性节点状态 */
	private String nodeStatus;

	/** 属性该流程的状态转储後是0 */
	private String flowStatus;

	/** 属性明细信息包ID */
	private String packageID;

	/** 属性标志字段 */
	private String flag;

	/** 属性任务编号 */
	private Integer taskNo = 0;

	/** 属性任务类型 */
	private String taskType;

	/** 属性节点类型 */
	private String nodeType;

	/** 属性任务备注 */
	private String titleStr;

	/** 属性业务类型 */
	private String businessType;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性任务接收载体键值 */
	private String keyIn;

	/** 属性记录流出时的业务编码 */
	private String keyOut;

	/** 属性部门名称 */
	private String deptName;

	/** 属性主流程编号 */
	private String mainFlowID;

	/** 属性子流程编号 */
	private String subFlowID;

	/** 属性节点X坐标 */
	private Integer posX = 0;

	/** 属性节点Y坐标 */
	private Integer posY = 0;

	/** 属性结束标志 */
	private String endFlag;

	/** 属性上个处理人员代码 */
	private String beforeHandlerCode;

	/** 属性上个处理人员名称 */
	private String beforeHandlerName;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性类型标志 */
	private String typeFlag;

	/** 属性部门代码 */
	private String comCode;

	/** 属性调度号码 */
	private Integer scheduleID = 0;

	/** 属性标的序号 */
	private String lossItemCode;

	/** 属性车牌号码 */
	private String lossItemName;

	/** 属性是否为本保单车辆 */
	private String insureCarFlag;

	/** 属性可操作/处理的级别 */
	private String handlerRange;

	/** 属性紧急程度 */
	private String exigenceGree;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性报案号码 */
	private String registNo;

	/** 属性简易赔案标记 */
	private String claimTypeFlag;

	/** 属性PRIORTYPE */
	private String priorType;
	private String processId;
	private String actorId;
	private Long taskId;
	private String businessId;
	/**
	 * 类SwfLogStore的默认构造方法
	 */
	public SwfLogStore() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowId", column = @Column(name = "FLOWID")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")) })
	public SwfLogStoreId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfLogStoreId id) {
		this.id = id;
	}

	/**
	 * 属性模板编码的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模板编码的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性当前节点号的getter方法
	 */

	@Column(name = "NODENO")
	public Integer getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性当前节点号的setter方法
	 */
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性当前节点名称的getter方法
	 */

	@Column(name = "NODENAME")
	public String getNodeName() {
		return this.nodeName;
	}

	/**
	 * 属性当前节点名称的setter方法
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 属性业务号的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性处理部门的getter方法
	 */

	@Column(name = "HANDLEDEPT")
	public String getHandleDept() {
		return this.handleDept;
	}

	/**
	 * 属性处理部门的setter方法
	 */
	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性处理人员名称的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性处理人员名称的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性流入时间的getter方法
	 */

	@Column(name = "FLOWINTIME")
	public String getFlowInTime() {
		return this.flowInTime;
	}

	/**
	 * 属性流入时间的setter方法
	 */
	public void setFlowInTime(String flowInTime) {
		this.flowInTime = flowInTime;
	}

	/**
	 * 属性处理时限的getter方法
	 */

	@Column(name = "TIMELIMIT")
	public Integer getTimeLimit() {
		return this.timeLimit;
	}

	/**
	 * 属性处理时限的setter方法
	 */
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * 属性处理时间的getter方法
	 */

	@Column(name = "HANDLETIME")
	public String getHandleTime() {
		return this.handleTime;
	}

	/**
	 * 属性处理时间的setter方法
	 */
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	/**
	 * 属性提交时间的getter方法
	 */

	@Column(name = "SUBMITTIME")
	public String getSubmitTime() {
		return this.submitTime;
	}

	/**
	 * 属性提交时间的setter方法
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * 属性节点状态的getter方法
	 */

	@Column(name = "NODESTATUS")
	public String getNodeStatus() {
		return this.nodeStatus;
	}

	/**
	 * 属性节点状态的setter方法
	 */
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	/**
	 * 属性该流程的状态转储後是0的getter方法
	 */

	@Column(name = "FLOWSTATUS")
	public String getFlowStatus() {
		return this.flowStatus;
	}

	/**
	 * 属性该流程的状态转储後是0的setter方法
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * 属性明细信息包ID的getter方法
	 */

	@Column(name = "PACKAGEID")
	public String getPackageID() {
		return this.packageID;
	}

	/**
	 * 属性明细信息包ID的setter方法
	 */
	public void setPackageID(String packageID) {
		this.packageID = packageID;
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
	 * 属性任务编号的getter方法
	 */

	@Column(name = "TASKNO")
	public Integer getTaskNo() {
		return this.taskNo;
	}

	/**
	 * 属性任务编号的setter方法
	 */
	public void setTaskNo(Integer taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * 属性任务类型的getter方法
	 */

	@Column(name = "TASKTYPE")
	public String getTaskType() {
		return this.taskType;
	}

	/**
	 * 属性任务类型的setter方法
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * 属性节点类型的getter方法
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点类型的setter方法
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 属性任务备注的getter方法
	 */

	@Column(name = "TITLESTR")
	public String getTitleStr() {
		return this.titleStr;
	}

	/**
	 * 属性任务备注的setter方法
	 */
	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "BUSINESSTYPE")
	public String getBusinessType() {
		return this.businessType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
	 * 属性任务接收载体键值的getter方法
	 */

	@Column(name = "KEYIN")
	public String getKeyIn() {
		return this.keyIn;
	}

	/**
	 * 属性任务接收载体键值的setter方法
	 */
	public void setKeyIn(String keyIn) {
		this.keyIn = keyIn;
	}

	/**
	 * 属性记录流出时的业务编码的getter方法
	 */

	@Column(name = "KEYOUT")
	public String getKeyOut() {
		return this.keyOut;
	}

	/**
	 * 属性记录流出时的业务编码的setter方法
	 */
	public void setKeyOut(String keyOut) {
		this.keyOut = keyOut;
	}

	/**
	 * 属性部门名称的getter方法
	 */

	@Column(name = "DEPTNAME")
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 属性部门名称的setter方法
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 属性主流程编号的getter方法
	 */

	@Column(name = "MAINFLOWID")
	public String getMainFlowID() {
		return this.mainFlowID;
	}

	/**
	 * 属性主流程编号的setter方法
	 */
	public void setMainFlowID(String mainFlowID) {
		this.mainFlowID = mainFlowID;
	}

	/**
	 * 属性子流程编号的getter方法
	 */

	@Column(name = "SUBFLOWID")
	public String getSubFlowID() {
		return this.subFlowID;
	}

	/**
	 * 属性子流程编号的setter方法
	 */
	public void setSubFlowID(String subFlowID) {
		this.subFlowID = subFlowID;
	}

	/**
	 * 属性节点X坐标的getter方法
	 */

	@Column(name = "POSX")
	public Integer getPosX() {
		return this.posX;
	}

	/**
	 * 属性节点X坐标的setter方法
	 */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	/**
	 * 属性节点Y坐标的getter方法
	 */

	@Column(name = "POSY")
	public Integer getPosY() {
		return this.posY;
	}

	/**
	 * 属性节点Y坐标的setter方法
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	/**
	 * 属性结束标志的getter方法
	 */

	@Column(name = "ENDFLAG")
	public String getEndFlag() {
		return this.endFlag;
	}

	/**
	 * 属性结束标志的setter方法
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	/**
	 * 属性上个处理人员代码的getter方法
	 */

	@Column(name = "BEFOREHANDLERCODE")
	public String getBeforeHandlerCode() {
		return this.beforeHandlerCode;
	}

	/**
	 * 属性上个处理人员代码的setter方法
	 */
	public void setBeforeHandlerCode(String beforeHandlerCode) {
		this.beforeHandlerCode = beforeHandlerCode;
	}

	/**
	 * 属性上个处理人员名称的getter方法
	 */

	@Column(name = "BEFOREHANDLERNAME")
	public String getBeforeHandlerName() {
		return this.beforeHandlerName;
	}

	/**
	 * 属性上个处理人员名称的setter方法
	 */
	public void setBeforeHandlerName(String beforeHandlerName) {
		this.beforeHandlerName = beforeHandlerName;
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
	 * 属性类型标志的getter方法
	 */

	@Column(name = "TYPEFLAG")
	public String getTypeFlag() {
		return this.typeFlag;
	}

	/**
	 * 属性类型标志的setter方法
	 */
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	/**
	 * 属性部门代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性部门代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性调度号码的getter方法
	 */

	@Column(name = "SCHEDULEID")
	public Integer getScheduleID() {
		return this.scheduleID;
	}

	/**
	 * 属性调度号码的setter方法
	 */
	public void setScheduleID(Integer scheduleID) {
		this.scheduleID = scheduleID;
	}

	/**
	 * 属性标的序号的getter方法
	 */

	@Column(name = "LOSSITEMCODE")
	public String getLossItemCode() {
		return this.lossItemCode;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	/**
	 * 属性是否为本保单车辆的getter方法
	 */

	@Column(name = "INSURECARFLAG")
	public String getInsureCarFlag() {
		return this.insureCarFlag;
	}

	/**
	 * 属性是否为本保单车辆的setter方法
	 */
	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	/**
	 * 属性可操作/处理的级别的getter方法
	 */

	@Column(name = "HANDLERRANGE")
	public String getHandlerRange() {
		return this.handlerRange;
	}

	/**
	 * 属性可操作/处理的级别的setter方法
	 */
	public void setHandlerRange(String handlerRange) {
		this.handlerRange = handlerRange;
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
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性简易赔案标记的getter方法
	 */

	@Column(name = "CLAIMTYPEFLAG")
	public String getClaimTypeFlag() {
		return this.claimTypeFlag;
	}

	/**
	 * 属性简易赔案标记的setter方法
	 */
	public void setClaimTypeFlag(String claimTypeFlag) {
		this.claimTypeFlag = claimTypeFlag;
	}

	/**
	 * 属性PRIORTYPE的getter方法
	 */

	@Column(name = "PRIORTYPE")
	public String getPriorType() {
		return this.priorType;
	}

	/**
	 * 属性PRIORTYPE的setter方法
	 */
	public void setPriorType(String priorType) {
		this.priorType = priorType;
	}
	@Column(name = "PROCESSID")
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	@Column(name = "ACTORID")
	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	@Column(name = "TASKID")
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@Column(name = "BUSINESSID")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public SwfLog toSwfLog() {
		SwfLog swfLog = new SwfLog();
		swfLog.setId(new SwfLogId(this.id.getFlowID(), this.id.getLogNo()));
		swfLog.setModelNo(this.modelNo);
		swfLog.setNodeNo(this.nodeNo);
		swfLog.setNodeName(this.nodeName);
		swfLog.setBusinessNo(this.businessNo);
		swfLog.setHandleDept(this.handleDept);
		swfLog.setHandlerCode(this.handlerCode);
		swfLog.setHandlerName(this.handlerName);
		swfLog.setFlowInTime(this.flowInTime);
		swfLog.setTimeLimit(this.timeLimit);
		swfLog.setHandleTime(this.handleTime);
		swfLog.setSubmitTime(this.submitTime);
		swfLog.setNodeStatus(this.nodeStatus);
		swfLog.setFlowStatus(this.flowStatus);
		swfLog.setPackageID(this.packageID);
		swfLog.setFlag(this.flag);
		swfLog.setTaskNo(this.taskNo);
		swfLog.setTaskType(this.taskType);
		swfLog.setNodeType(this.nodeType);
		swfLog.setTitleStr(this.titleStr);
		swfLog.setBusinessType(this.businessType);
		swfLog.setRiskCode(this.riskCode);
		swfLog.setKeyIn(this.keyIn);
		swfLog.setKeyOut(this.keyOut);
		swfLog.setDeptName(this.deptName);
		swfLog.setMainFlowID(this.mainFlowID);
		swfLog.setSubFlowID(this.subFlowID);
		swfLog.setPosX(this.posX);
		swfLog.setPosY(this.posY);
		swfLog.setEndFlag(this.endFlag);
		swfLog.setBeforeHandlerCode(this.beforeHandlerCode);
		swfLog.setBeforeHandlerName(this.beforeHandlerName);
		swfLog.setPolicyNo(this.policyNo);
		swfLog.setTypeFlag(this.typeFlag);
		swfLog.setComCode(this.comCode);
		swfLog.setScheduleID(this.scheduleID);
		swfLog.setLossItemCode(this.lossItemCode);
		swfLog.setLossItemName(this.lossItemName);
		swfLog.setInsureCarFlag(this.insureCarFlag);
		swfLog.setHandlerRange(this.handlerRange);
		swfLog.setExigenceGree(this.exigenceGree);
		swfLog.setRegistNo(this.registNo);
		swfLog.setInsuredName(this.insuredName);
		swfLog.setClaimTypeFlag(this.claimTypeFlag);
		swfLog.setProcessId(this.processId);
		swfLog.setActorId(this.actorId);
		swfLog.setTaskId(this.taskId);
		swfLog.setBusinessId(this.businessId);
		return swfLog;
	}
}
