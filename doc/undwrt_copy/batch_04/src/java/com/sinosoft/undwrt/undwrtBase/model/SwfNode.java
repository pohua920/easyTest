package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO类SwfNode.
 */
@Entity(name = "SWFNODE_UNDWRT")
@Table(name = "SWFNODE")
public class SwfNode implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性id. */
	private SwfNodeId id;

	/** 属性模板主表. */
	private SwfModelMain swfModelMain;

	/** 属性属性模板名称. */
	private String modelName;

	/** 属性当前節點名稱. */
	private String nodeName;

	/** 属性節點類型 regis報案 claim 立案. */
	private String nodeType;

	/** 属性處理時限. */
	private int timeLimit;

	/** 属性結束標誌. */
	private String endFlag;

	/** 属性處理要求. */
	private String criterion;

	/** 属性任務編號. */
	private int taskNo;

	/** 属性任務類型 M多任務 S單任務. */
	private String taskType;

	/** 属性辦理部門程式. */
	private String unitCode;

	/** 属性辦理部門名稱. */
	private String unitName;

	/** 属性辦理人員程式. */
	private String handlerCode;

	/** 属性辦理人員名稱. */
	private String handlerName;

	/** 属性節點X坐標. */
	private int posX;

	/** 属性節點Y坐標. */
	private int posY;

	/** 属性節點設定. */
	private String flag;

	/** 属性swfPathsForfkPathNode2. */
	private List<SwfPath> swfPathsForfkPathNode2 = new ArrayList<SwfPath>(0);

	/** 属性swfPathsForfkPathNode1. */
	private List<SwfPath> swfPathsForfkPathNode1 = new ArrayList<SwfPath>(0);

	/**
	 * 类SwfNode的默认构造方法.
	 */
	public SwfNode() {
	}

	/**
	 * 属性id的getter方法.
	 * 
	 * @return the 属性id
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")),
			@AttributeOverride(name = "nodeNo", column = @Column(name = "NODENO")) })
	public SwfNodeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法.
	 * 
	 * @param id
	 *            the new 属性id
	 */
	public void setId(SwfNodeId id) {
		this.id = id;
	}

	/**
	 * 属性模版主表的getter方法.
	 * 
	 * @return the 属性模版主表
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODELNO", nullable = false, insertable = false, updatable = false)
	public SwfModelMain getSwfModelMain() {
		return this.swfModelMain;
	}

	/**
	 * 属性模版主表的setter方法.
	 * 
	 * @param swfModelMain
	 *            the new 属性模版主表
	 */
	public void setSwfModelMain(SwfModelMain swfModelMain) {
		this.swfModelMain = swfModelMain;
	}

	/**
	 * 属性属性模板名称的getter方法.
	 * 
	 * @return the 属性属性模板名称
	 */

	@Column(name = "MODELNAME")
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 属性属性模板名称的setter方法.
	 * 
	 * @param modelName
	 *            the new 属性属性模板名称
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 属性当前节点名称的getter方法.
	 * 
	 * @return the 属性当前节点名称
	 */

	@Column(name = "NODENAME")
	public String getNodeName() {
		return this.nodeName;
	}

	/**
	 * 属性当前节点名称的setter方法.
	 * 
	 * @param nodeName
	 *            the new 属性当前节点名称
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 属性节点类型的getter方法.
	 * 
	 * @return the 属性节点类型
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点类型的setter方法.
	 * 
	 * @param nodeType
	 *            the new 属性节点类型
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 属性处理时限的getter方法.
	 * 
	 * @return the 属性处理时限
	 */

	@Column(name = "TIMELIMIT")
	public int getTimeLimit() {
		return this.timeLimit;
	}

	/**
	 * 属性处理时限的setter方法.
	 * 
	 * @param timeLimit
	 *            the new 属性处理时限
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * 属性结束标志的getter方法.
	 * 
	 * @return the 属性结束标志
	 */

	@Column(name = "ENDFLAG")
	public String getEndFlag() {
		return this.endFlag;
	}

	/**
	 * 属性结束标志的setter方法.
	 * 
	 * @param endFlag
	 *            the new 属性结束标志
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	/**
	 * 属性属性处理要求的getter方法.
	 * 
	 * @return the 属性属性处理要求
	 */

	@Column(name = "CRITERION")
	public String getCriterion() {
		return this.criterion;
	}

	/**
	 * 属性属性处理要求的setter方法.
	 * 
	 * @param criterion
	 *            the new 属性属性处理要求
	 */
	public void setCriterion(String criterion) {
		this.criterion = criterion;
	}

	/**
	 * 属性任务编号的getter方法.
	 * 
	 * @return the 属性任务编号
	 */

	@Column(name = "TASKNO")
	public int getTaskNo() {
		return this.taskNo;
	}

	/**
	 * 属性任务编号的setter方法.
	 * 
	 * @param taskNo
	 *            the new 属性任务编号
	 */
	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * 属性任务类型的getter方法.
	 * 
	 * @return the 属性任务类型
	 */

	@Column(name = "TASKTYPE")
	public String getTaskType() {
		return this.taskType;
	}

	/**
	 * 属性任务类型的setter方法.
	 * 
	 * @param taskType
	 *            the new 属性任务类型
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * 属性属性办理部门编码的getter方法.
	 * 
	 * @return the 属性属性办理部门编码
	 */

	@Column(name = "UNITCODE")
	public String getUnitCode() {
		return this.unitCode;
	}

	/**
	 * 属性属性办理部门编码的setter方法.
	 * 
	 * @param unitCode
	 *            the new 属性属性办理部门编码
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * 属性属性办理部门名称的getter方法.
	 * 
	 * @return the 属性属性办理部门名称
	 */

	@Column(name = "UNITNAME")
	public String getUnitName() {
		return this.unitName;
	}

	/**
	 * 属性属性办理部门名称的setter方法.
	 * 
	 * @param unitName
	 *            the new 属性属性办理部门名称
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 属性处理人员代码的getter方法.
	 * 
	 * @return the 属性处理人员代码
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法.
	 * 
	 * @param handlerCode
	 *            the new 属性处理人员代码
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性处理人员名称的getter方法.
	 * 
	 * @return the 属性处理人员名称
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性处理人员名称的setter方法.
	 * 
	 * @param handlerName
	 *            the new 属性处理人员名称
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性节点X坐标的getter方法.
	 * 
	 * @return the 属性节点X坐标
	 */

	@Column(name = "POSX")
	public int getPosX() {
		return this.posX;
	}

	/**
	 * 属性节点X坐标的setter方法.
	 * 
	 * @param posX
	 *            the new 属性节点X坐标
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * 属性节点Y坐标的getter方法.
	 * 
	 * @return the 属性节点Y坐标
	 */

	@Column(name = "POSY")
	public int getPosY() {
		return this.posY;
	}

	/**
	 * 属性节点Y坐标的setter方法.
	 * 
	 * @param posY
	 *            the new 属性节点Y坐标
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * 属性属性Flag位的getter方法.
	 * 
	 * @return the 属性属性Flag位
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性属性Flag位的setter方法.
	 * 
	 * @param flag
	 *            the new 属性属性Flag位
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性swfPathsForfkPathNode2的getter方法.
	 * 
	 * @return the 属性swfPathsForfkPathNode2
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "swfNodeByfkPathNode2")
	public List<SwfPath> getSwfPathsForfkPathNode2() {
		return this.swfPathsForfkPathNode2;
	}

	/**
	 * 属性swfPathsForfkPathNode2的setter方法.
	 * 
	 * @param swfPathsForfkPathNode2
	 *            the new 属性swfPathsForfkPathNode2
	 */
	public void setSwfPathsForfkPathNode2(List<SwfPath> swfPathsForfkPathNode2) {
		this.swfPathsForfkPathNode2 = swfPathsForfkPathNode2;
	}

	/**
	 * 属性swfPathsForfkPathNode1的getter方法.
	 * 
	 * @return the 属性swfPathsForfkPathNode1
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "swfNodeByfkPathNode1")
	public List<SwfPath> getSwfPathsForfkPathNode1() {
		return this.swfPathsForfkPathNode1;
	}

	/**
	 * 属性swfPathsForfkPathNode1的setter方法.
	 * 
	 * @param swfPathsForfkPathNode1
	 *            the new 属性swfPathsForfkPathNode1
	 */
	public void setSwfPathsForfkPathNode1(List<SwfPath> swfPathsForfkPathNode1) {
		this.swfPathsForfkPathNode1 = swfPathsForfkPathNode1;
	}

}
