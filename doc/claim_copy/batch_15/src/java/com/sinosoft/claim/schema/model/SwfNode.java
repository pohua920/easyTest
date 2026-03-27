package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类SwfNode工作流节点定义表
 */
@Entity
@Table(name = "SWFNODE")
public class SwfNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfNodeId id;

	/** 属性模板主表 */
	private SwfModelMain swfModelMain;

	/** 属性模板名称 */
	private String modelName;

	/** 属性当前节点名称 */
	private String nodeName;

	/** 属性节点类型 */
	private String nodeType;

	/** 属性处理时限 */
	private Integer timeLimit;

	/** 属性结束标志 */
	private String endFlag;

	/** 属性处理要求 */
	private String criterion;

	/** 属性任务编号 */
	private Integer taskNo;

	/** 属性任务类型 */
	private String taskType;

	/** 属性办理部门 */
	private String unitCode;

	/** 属性部门名称 */
	private String unitName;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性处理人员名称 */
	private String handlerName;

	/** 属性节点X坐标 */
	private Long posX;

	/** 属性节点Y坐标 */
	private Long posY;

	/** 属性标志字段 */
	private String flag;

	/*
	 * 数据库中没有的字段，在页面上展示用和处理逻辑使用
	 */
	/** 属性显示列表 */
	private List<SwfNode> nodeList = new ArrayList<SwfNode>(0);
	/** 属性调度号码 */
	private int scheduleID = 0;
	/** 属性标的序号 */
	private String lossItemCode = "";
	/** 属性车牌号码 */
	private String lossItemName = "";
	/** 属性是否为本保单车辆 */
	private String insureCarFlag = "";

	/** 属性类型标志 */
	private String typeFlag = "";

	/** 属性可操作/处理的级别划分 */
	private String handlerRange = "";
	/** 属性紧急程度 */
	private String exigenceGree = "";

	/** 属性处理部门 */
	private String handleDept = "";

	/** 属性处理部门名称 */
	private String deptName = "";

	/** 属性保单号码 */
	private String policyNo = "";

	/** 属性险种代码 */
	private String riskCode = "";

	/** 属性业务号 */
	private String businessNo = "";

	/** 属性任务接收载体键值 */
	private String keyIn = "";

	/**
	 * 类SwfNode的默认构造方法
	 */
	public SwfNode() {
		id = new SwfNodeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "nodeNo", column = @Column(name = "NODENO")) })
	public SwfNodeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfNodeId id) {
		this.id = id;
	}

	/**
	 * 属性模板主表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODELNO", nullable = false, insertable = false, updatable = false)
	public SwfModelMain getSwfModelMain() {
		return this.swfModelMain;
	}

	/**
	 * 属性模板主表的setter方法
	 */
	public void setSwfModelMain(SwfModelMain swfModelMain) {
		this.swfModelMain = swfModelMain;
	}

	/**
	 * 属性模板名称的getter方法
	 */

	@Column(name = "MODELNAME")
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 属性模板名称的setter方法
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
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
	 * 属性处理要求的getter方法
	 */

	@Column(name = "CRITERION")
	public String getCriterion() {
		return this.criterion;
	}

	/**
	 * 属性处理要求的setter方法
	 */
	public void setCriterion(String criterion) {
		this.criterion = criterion;
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
	 * 属性办理部门的getter方法
	 */

	@Column(name = "UNITCODE")
	public String getUnitCode() {
		return this.unitCode;
	}

	/**
	 * 属性办理部门的setter方法
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * 属性部门名称的getter方法
	 */

	@Column(name = "UNITNAME")
	public String getUnitName() {
		return this.unitName;
	}

	/**
	 * 属性部门名称的setter方法
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	 * 属性节点X坐标的getter方法
	 */

	@Column(name = "POSX")
	public Long getPosX() {
		return this.posX;
	}

	/**
	 * 属性节点X坐标的setter方法
	 */
	public void setPosX(Long posX) {
		this.posX = posX;
	}

	/**
	 * 属性节点Y坐标的getter方法
	 */

	@Column(name = "POSY")
	public Long getPosY() {
		return this.posY;
	}

	/**
	 * 属性节点Y坐标的setter方法
	 */
	public void setPosY(Long posY) {
		this.posY = posY;
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

	@Transient
	public List<SwfNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<SwfNode> nodeList) {
		this.nodeList = nodeList;
	}

	@Transient
	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	@Transient
	public String getLossItemCode() {
		return lossItemCode;
	}

	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	@Transient
	public String getLossItemName() {
		return lossItemName;
	}

	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	@Transient
	public String getInsureCarFlag() {
		return insureCarFlag;
	}

	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	@Transient
	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	@Transient
	public String getHandlerRange() {
		return handlerRange;
	}

	public void setHandlerRange(String handlerRange) {
		this.handlerRange = handlerRange;
	}

	@Transient
	public String getExigenceGree() {
		return exigenceGree;
	}

	public void setExigenceGree(String exigenceGree) {
		this.exigenceGree = exigenceGree;
	}

	@Transient
	public String getHandleDept() {
		return handleDept;
	}

	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Transient
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Transient
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Transient
	public String getKeyIn() {
		return keyIn;
	}

	public void setKeyIn(String keyIn) {
		this.keyIn = keyIn;
	}

}
