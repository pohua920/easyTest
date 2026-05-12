package com.sinosoft.claim.schema.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfConfig
 */
@Entity
@Table(name = "SWFCONFIG")
public class SwfConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 属性id */
	private SwfConfigId id;
	/** 属性映射业务节点序号 */
	private int nodeNo;
	/** 属性映射业务节点类型 */
	private String nodeType;
	/** 属性映射业务节点名称 */
	private String nodeName;
	/** 属性映射险种代码*/
	private String riskCode;
	/** 属性映射 多任务后续节点actorId*/
	private String taskId;
	/** 属性映射多任務執行的下級節點序號*/
	private Integer taskNo;
	/** 属性映射 任务类型 M多任务，S单任务*/
	private String taskType;
	/** 是否结束节点 1：结束；0：非 */
	private String endFlag;

	public SwfConfig(){
		this.id = new SwfConfigId();
	}
	
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "processId", column = @Column(name = "PROCESSID")),
	@AttributeOverride(name = "actorId", column = @Column(name = "ACTORID")) })
	public SwfConfigId getId() {
		return id;
	}
	public void setId(SwfConfigId id) {
		this.id = id;
	}
	@Column(name = "NODENO")
	public int getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}
	@Column(name = "NODETYPE")
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	@Column(name = "NODENAME")
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name = "TASKID")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(name = "TASKTYPE")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	@Column(name = "TASKNO")
	public Integer getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(Integer taskNo) {
		this.taskNo = taskNo;
	}
	@Column(name = "ENDFLAG")
	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

}
