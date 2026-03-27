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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO类SwfPath.
 */
@Entity(name = "SWFPATH_UNDWRT")
@Table(name = "SWFPATH")
public class SwfPath implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性id. */
	private SwfPathId id;

	/** 属性swfNodeByfkPathNode1. */
	private SwfNode swfNodeByfkPathNode1;

	/** 属性swfNodeByfkPathNode2. */
	private SwfNode swfNodeByfkPathNode2;

	/** 属性模板名稱. */
	private String modelName;

	/** 属性起始節點名稱. */
	private String startNodeName;
	
	/** 屬性終止節點. */
	private String endNodeNo;
	
	/**
	 * 獲取屬性終止節點.
	 * 
	 * @return 屬性終止節點的值
	 */
	public String getEndNodeNo() {
		return endNodeNo;
	}

	/**
	 * 設置屬性終止節點.
	 * 
	 * @param endNodeNo
	 *            待設置的終止節點的值
	 */
	public void setEndNodeNo(String endNodeNo) {
		this.endNodeNo = endNodeNo;
	}

	/** 属性属性终止节点名称. */
	private String endNodeName;

	/** 属性属性路径名称. */
	private String pathName;

	/** 属性属性优先级. */
	private int priority;

	/** 属性属性缺省值. */
	private String defaultFlag;

	/** 属性属性是否存在流转条件. */
	private String conditionStatus;

	/** 属性属性正向流转所调用的业务处理服务名. */
	private String forwardServices;

	/** 属性属性逆向流转所调用的业务处理服务名. */
	private String backwardServices;

	/** 属性属性Flag位. */
	private String flag;

	/** 属性该流程的状态. */
	private String flowStatus;

	/** 属性swfConditions. */
	private List<SwfCondition> swfConditions = new ArrayList<SwfCondition>(0);

	/**
	 * 类SwfPath的默认构造方法.
	 */
	public SwfPath() {
	}

	/**
	 * 属性id的getter方法.
	 * 
	 * @return the 属性id
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")),
			@AttributeOverride(name = "pathNo", column = @Column(name = "PATHNO")) })
	public SwfPathId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法.
	 * 
	 * @param id
	 *            the new 属性id
	 */
	public void setId(SwfPathId id) {
		this.id = id;
	}

	/**
	 * 属性swfNodeByfkPathNode1的getter方法.
	 * 
	 * @return the 属性swfNodeByfkPathNode1
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "MODELNO", referencedColumnName = "MODELNO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "STARTNODENO", referencedColumnName = "NODENO", nullable = false, insertable = false, updatable = false) })
	public SwfNode getSwfNodeByfkPathNode1() {
		return this.swfNodeByfkPathNode1;
	}

	/**
	 * 属性swfNodeByfkPathNode1的setter方法.
	 * 
	 * @param swfNodeByfkPathNode1
	 *            the new 属性swfNodeByfkPathNode1
	 */
	public void setSwfNodeByfkPathNode1(SwfNode swfNodeByfkPathNode1) {
		this.swfNodeByfkPathNode1 = swfNodeByfkPathNode1;
	}

	/**
	 * 属性swfNodeByfkPathNode2的getter方法.
	 * 
	 * @return the 属性swfNodeByfkPathNode2
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "MODELNO", referencedColumnName = "MODELNO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "ENDNODENO", referencedColumnName = "NODENO", nullable = false, insertable = false, updatable = false) })
	public SwfNode getSwfNodeByfkPathNode2() {
		return this.swfNodeByfkPathNode2;
	}

	/**
	 * 属性swfNodeByfkPathNode2的setter方法.
	 * 
	 * @param swfNodeByfkPathNode2
	 *            the new 属性swfNodeByfkPathNode2
	 */
	public void setSwfNodeByfkPathNode2(SwfNode swfNodeByfkPathNode2) {
		this.swfNodeByfkPathNode2 = swfNodeByfkPathNode2;
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
	 * 属性属性起始节点名称的getter方法.
	 * 
	 * @return the 属性属性起始节点名称
	 */

	@Column(name = "STARTNODENAME")
	public String getStartNodeName() {
		return this.startNodeName;
	}

	/**
	 * 属性属性起始节点名称的setter方法.
	 * 
	 * @param startNodeName
	 *            the new 属性属性起始节点名称
	 */
	public void setStartNodeName(String startNodeName) {
		this.startNodeName = startNodeName;
	}

	/**
	 * 属性属性终止节点名称的getter方法.
	 * 
	 * @return the 属性属性终止节点名称
	 */

	@Column(name = "ENDNODENAME")
	public String getEndNodeName() {
		return this.endNodeName;
	}

	/**
	 * 属性属性终止节点名称的setter方法.
	 * 
	 * @param endNodeName
	 *            the new 属性属性终止节点名称
	 */
	public void setEndNodeName(String endNodeName) {
		this.endNodeName = endNodeName;
	}

	/**
	 * 属性属性路径名称的getter方法.
	 * 
	 * @return the 属性属性路径名称
	 */

	@Column(name = "PATHNAME")
	public String getPathName() {
		return this.pathName;
	}

	/**
	 * 属性属性路径名称的setter方法.
	 * 
	 * @param pathName
	 *            the new 属性属性路径名称
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * 属性属性优先级的getter方法.
	 * 
	 * @return the 属性属性优先级
	 */

	@Column(name = "PRIORITY")
	public int getPriority() {
		return this.priority;
	}

	/**
	 * 属性属性优先级的setter方法.
	 * 
	 * @param priority
	 *            the new 属性属性优先级
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * 属性属性缺省值的getter方法.
	 * 
	 * @return the 属性属性缺省值
	 */

	@Column(name = "DEFAULTFLAG")
	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	/**
	 * 属性属性缺省值的setter方法.
	 * 
	 * @param defaultFlag
	 *            the new 属性属性缺省值
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * 属性属性是否存在流转条件的getter方法.
	 * 
	 * @return the 属性属性是否存在流转条件
	 */

	@Column(name = "CONDITIONSTATUS")
	public String getConditionStatus() {
		return this.conditionStatus;
	}

	/**
	 * 属性属性是否存在流转条件的setter方法.
	 * 
	 * @param conditionStatus
	 *            the new 属性属性是否存在流转条件
	 */
	public void setConditionStatus(String conditionStatus) {
		this.conditionStatus = conditionStatus;
	}

	/**
	 * 属性属性正向流转所调用的业务处理服务名的getter方法.
	 * 
	 * @return the 属性属性正向流转所调用的业务处理服务名
	 */

	@Column(name = "FORWARDSERVICES")
	public String getForwardServices() {
		return this.forwardServices;
	}

	/**
	 * 属性属性正向流转所调用的业务处理服务名的setter方法.
	 * 
	 * @param forwardServices
	 *            the new 属性属性正向流转所调用的业务处理服务名
	 */
	public void setForwardServices(String forwardServices) {
		this.forwardServices = forwardServices;
	}

	/**
	 * 属性属性逆向流转所调用的业务处理服务名的getter方法.
	 * 
	 * @return the 属性属性逆向流转所调用的业务处理服务名
	 */

	@Column(name = "BACKWARDSERVICES")
	public String getBackwardServices() {
		return this.backwardServices;
	}

	/**
	 * 属性属性逆向流转所调用的业务处理服务名的setter方法.
	 * 
	 * @param backwardServices
	 *            the new 属性属性逆向流转所调用的业务处理服务名
	 */
	public void setBackwardServices(String backwardServices) {
		this.backwardServices = backwardServices;
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
	 * 属性该流程的状态的getter方法.
	 * 
	 * @return the 属性该流程的状态
	 */

	@Column(name = "FLOWSTATUS")
	public String getFlowStatus() {
		return this.flowStatus;
	}

	/**
	 * 属性该流程的状态的setter方法.
	 * 
	 * @param flowStatus
	 *            the new 属性该流程的状态
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * 属性工作流條件對象的getter方法.
	 * 
	 * @return the 属性工作流條件對象
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "swfPath")
	public List<SwfCondition> getSwfConditions() {
		return this.swfConditions;
	}

	/**
	 * 属性工作流條件對象的setter方法.
	 * 
	 * @param swfConditions
	 *            the new 属性工作流條件對象
	 */
	public void setSwfConditions(List<SwfCondition> swfConditions) {
		this.swfConditions = swfConditions;
	}

}
