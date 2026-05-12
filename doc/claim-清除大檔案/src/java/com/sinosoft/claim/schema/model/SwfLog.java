package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import ins.framework.common.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类SwfLog工作流日志表
 */
@Entity
@Table(name = "SWFLOG")
public class SwfLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfLogId id;

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

	/** 属性节点状态（0：待处理，2：正在处理，3：回退处理，4：已处理，5：已回退，6：报案撤销）*/
	private String nodeStatus;

	/** 属性该流程的状态转储後是0，流状态（0：关闭，1：未被占用，2：被占用） */
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
	
	/** 属性部门名稱 */
	private String comName;

	/** 属性调度号码 */
	private Integer scheduleID = 0;

	/** 属性标的序号 */
	private String lossItemCode = "";

	/** 属性车牌号码 */
	private String lossItemName;

	/** 属性是否为本保单车辆 (1：是 0：否) */
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
	/*
	 * 数据库中没有的字段，在页面上展示用和处理逻辑使用
	 */
	/** 集合 * */
	List<SwfLog> swfLogList = new ArrayList<SwfLog>(0);

	/** 需要弹出消息的内容 */
	String alertMessage = "";
	String dfFlag = "";

	/** 需要是多任务处理的，並且是新生成子任务节点的配置，1是，非1 不是 */
	int newMTask = -1;
	/** 节点树所在的层 */
	int treeLayer = 0;

	/** 同一层节点所处的位置 */
	int nodePosLayer = 0;
	/** 起始节点 */
	int startNodeNo = 0;

	/** 终止节点 */
	int endNodeNo = 0;
	/** 每层的节点个数 */
	int countNode = 0;
	/** 是否是同层的相同节点 */
	boolean sameLayerSameNode = false;

	/** 需要创建流程，1是，非1 不是 */
	boolean createFlow = false;
	/** 是否是正在独自占用该节点 */
	boolean holdNode = false;

	/** 统计开始日期 */
	private String startDate;
	/** 统计结束日期 */
	private String endDate;

	public static String HANDLERCODE_NONE = "0";

	/** 属性节点种类名称 */
	private String nodeTypeName = "";

	/** 属性操作状态名称 */
	private String nodeStatusName = "";
	/** 属性险种名称 */
	private String riskCodeName = "";

	/** 定损类型的名称 */
	private String typeFlagName = "";
	/** 设置得到下一个节点的来源类型，设置为1，表示同时指定节点，否则从模板寻找 */
	private String nextNodeListType = "";
	/** 一页数据 */
	// private TurnPageDto turnPageDto = null;//这个是以前用来分页的对象，不在使用，这个对象不在使用
	// liudaoping
	// reason:计算流入和流出时间差
	/** 时间差 */
	private long stopTime = 0;
	/** 编辑类型 */
	private String editType = "";
	/** 拼的条件 */
	private String whereString = "";
	/** 时间差描述 */
	private String stopTimeDesc = "";

	/** 属性新处理部门 */
	private String newNewHandleDept = "";

	/** 属性新处理部门名称 */
	private String newNewDeptName = "";

	/** 节点的办理信息 */
	private List<SwfNotion> swfNotionList = new ArrayList<SwfNotion>(0);

	/** 未核赔通过的计算书数目 */
	private int compeCount;

	/** 理算任务状态 0表示未出计算书，1表示正在处理中，2表示已核赔通过 */
	private String compeFlag;
	/** 保单是否已被注销 */
	private String otherFlag;
	/** 一个流程的报案上对应的保单号 */
	private List<?> relatePolicyList = new ArrayList<Object>();// 存关联保单或者关联对象PrpLregistrpolicy
	/** 操作时间 */
	private DateTime operateDate;
	/** 下一个节点的属性业务号 */
	private String nextBusinessNo = "";

	/** 用来做条件wfcondition中的业务号限制条件 */
	private String conditionBusinessNo = "";

	private String riskType;
	/** 险类 */
	private String classCode;

	private String iFlowID = "";

	private int iModelNo = 0;

	private int iNodeNo = 0;

	private String iBusinessNo = "";

	private int iLogNo = 0;
	private String processId;
	private String actorId;
	private Long taskId;
	private String nextKeyIn;
	private String businessId;
	/** 立案剩余小时，未处理立案显示 */
	private long leftHour = 0;
	private Date damageDate ;
	private double sumPaid = 0;
	
	private boolean simpleFlag = false;
	
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能START
	private String dpLogInputStatus;//最後的紀錄狀態 INPUTSTATUS 狀態(-1:退回 /1:待審核/2:審核通過)
	private String dpLogId;
	private String inputUser;
	private String reviewUser;
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能END
	/**
	 * 类SwfLog的默认构造方法
	 */
	public SwfLog() {
		id = new SwfLogId();
	}

	public SwfLog(String flowID,Integer logNo) {
		id = new SwfLogId(flowID,logNo);
	}
	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowID", column = @Column(name = "FLOWID")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")) })
	public SwfLogId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfLogId id) {
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

	@Transient
	public List<SwfLog> getSwfLogList() {
		return swfLogList;
	}

	public void setSwfLogList(List<SwfLog> swfLogList) {
		this.swfLogList = swfLogList;
	}

	@Transient
	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	@Transient
	public String getDfFlag() {
		return dfFlag;
	}

	public void setDfFlag(String dfFlag) {
		this.dfFlag = dfFlag;
	}

	@Transient
	public int getNewMTask() {
		return newMTask;
	}

	public void setNewMTask(int newMTask) {
		this.newMTask = newMTask;
	}

	@Transient
	public int getTreeLayer() {
		return treeLayer;
	}

	public void setTreeLayer(int treeLayer) {
		this.treeLayer = treeLayer;
	}

	@Transient
	public int getNodePosLayer() {
		return nodePosLayer;
	}

	public void setNodePosLayer(int nodePosLayer) {
		this.nodePosLayer = nodePosLayer;
	}

	@Transient
	public int getStartNodeNo() {
		return startNodeNo;
	}

	public void setStartNodeNo(int startNodeNo) {
		this.startNodeNo = startNodeNo;
	}

	@Transient
	public int getEndNodeNo() {
		return endNodeNo;
	}

	public void setEndNodeNo(int endNodeNo) {
		this.endNodeNo = endNodeNo;
	}

	@Transient
	public int getCountNode() {
		return countNode;
	}

	public void setCountNode(int countNode) {
		this.countNode = countNode;
	}

	@Transient
	public boolean getSameLayerSameNode() {
		return sameLayerSameNode;
	}

	public void setSameLayerSameNode(boolean sameLayerSameNode) {
		this.sameLayerSameNode = sameLayerSameNode;
	}

	@Transient
	public boolean getCreateFlow() {
		return createFlow;
	}

	public void setCreateFlow(boolean createFlow) {
		this.createFlow = createFlow;
	}

	@Transient
	public boolean getHoldNode() {
		return holdNode;
	}

	public void setHoldNode(boolean holdNode) {
		this.holdNode = holdNode;
	}

	@Transient
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Transient
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getNodeTypeName() {
		return nodeTypeName;
	}

	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = nodeTypeName;
	}

	@Transient
	public String getNodeStatusName() {
		return nodeStatusName;
	}

	public void setNodeStatusName(String nodeStatusName) {
		this.nodeStatusName = nodeStatusName;
	}

	@Transient
	public String getRiskCodeName() {
		return riskCodeName;
	}

	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}

	@Transient
	public String getTypeFlagName() {
		return typeFlagName;
	}

	public void setTypeFlagName(String typeFlagName) {
		this.typeFlagName = typeFlagName;
	}

	@Transient
	public String getNextNodeListType() {
		return nextNodeListType;
	}

	public void setNextNodeListType(String nextNodeListType) {
		this.nextNodeListType = nextNodeListType;
	}

	@Transient
	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public String getWhereString() {
		return whereString;
	}

	public void setWhereString(String whereString) {
		this.whereString = whereString;
	}

	@Transient
	public String getStopTimeDesc() {
		return stopTimeDesc;
	}

	public void setStopTimeDesc(String stopTimeDesc) {
		this.stopTimeDesc = stopTimeDesc;
	}

	@Transient
	public String getNewNewHandleDept() {
		return newNewHandleDept;
	}

	public void setNewNewHandleDept(String newNewHandleDept) {
		this.newNewHandleDept = newNewHandleDept;
	}

	@Transient
	public String getNewNewDeptName() {
		return newNewDeptName;
	}

	public void setNewNewDeptName(String newNewDeptName) {
		this.newNewDeptName = newNewDeptName;
	}

	@Transient
	public List<SwfNotion> getSwfNotionList() {
		return swfNotionList;
	}

	public void setSwfNotionList(List<SwfNotion> swfNotionList) {
		this.swfNotionList = swfNotionList;
	}

	@Transient
	public int getCompeCount() {
		return compeCount;
	}

	public void setCompeCount(int compeCount) {
		this.compeCount = compeCount;
	}

	@Transient
	public String getCompeFlag() {
		return compeFlag;
	}

	public void setCompeFlag(String compeFlag) {
		this.compeFlag = compeFlag;
	}

	@Transient
	public String getOtherFlag() {
		return otherFlag;
	}

	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}

	@Transient
	public List getRelatePolicyList() {
		return relatePolicyList;
	}

	public void setRelatePolicyList(List relatePolicyList) {
		this.relatePolicyList = relatePolicyList;
	}

	@Transient
	public DateTime getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(DateTime operateDate) {
		this.operateDate = operateDate;
	}

	@Transient
	public String getNextBusinessNo() {
		return nextBusinessNo;
	}

	public void setNextBusinessNo(String nextBusinessNo) {
		this.nextBusinessNo = nextBusinessNo;
	}

	@Transient
	public String getConditionBusinessNo() {
		return conditionBusinessNo;
	}

	public void setConditionBusinessNo(String conditionBusinessNo) {
		this.conditionBusinessNo = conditionBusinessNo;
	}

	@Transient
	public String getHANDLERCODE_NONE() {
		return HANDLERCODE_NONE;
	}

	@Transient
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	@Transient
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Transient
	public String getiFlowID() {
		return iFlowID;
	}

	public void setiFlowID(String iFlowID) {
		this.iFlowID = iFlowID;
	}

	@Transient
	public int getiModelNo() {
		return iModelNo;
	}

	public void setiModelNo(int iModelNo) {
		this.iModelNo = iModelNo;
	}

	@Transient
	public int getiNodeNo() {
		return iNodeNo;
	}

	public void setiNodeNo(int iNodeNo) {
		this.iNodeNo = iNodeNo;
	}

	@Transient
	public String getiBusinessNo() {
		return iBusinessNo;
	}

	public void setiBusinessNo(String iBusinessNo) {
		this.iBusinessNo = iBusinessNo;
	}

	@Transient
	public int getiLogNo() {
		return iLogNo;
	}

	public void setiLogNo(int iLogNo) {
		this.iLogNo = iLogNo;
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

	@Transient
	public String getNextKeyIn() {
		return nextKeyIn;
	}

	public void setNextKeyIn(String nextKeyIn) {
		this.nextKeyIn = nextKeyIn;
	}

	@Column(name = "BUSINESSID")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	@Transient
	public long getLeftHour() {
		return leftHour;
	}

	public void setLeftHour(long leftHour) {
		this.leftHour = leftHour;
	}
	@Transient
	public Date getDamageDate() {
		return damageDate;
	}

	public void setDamageDate(Date damageDate) {
		this.damageDate = damageDate;
	}
	@Transient
	public double getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}
	@Transient
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
	@Transient
	public boolean getSimpleFlag() {
		return simpleFlag;
	}

	public void setSimpleFlag(boolean simpleFlag) {
		this.simpleFlag = simpleFlag;
	}

	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 START
	@Transient
	public String getDpLogInputStatus() {
		return dpLogInputStatus;
	}

	public void setDpLogInputStatus(String dpLogInputStatus) {
		this.dpLogInputStatus = dpLogInputStatus;
	}
	@Transient
	public String getDpLogId() {
		return dpLogId;
	}

	public void setDpLogId(String dpLogId) {
		this.dpLogId = dpLogId;
	}
	@Transient
	public String getInputUser() {
		return inputUser;
	}
	
	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}
	@Transient
	public String getReviewUser() {
		return reviewUser;
	}
	
	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 END

}
