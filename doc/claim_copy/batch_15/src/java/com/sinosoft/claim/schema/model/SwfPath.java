package com.sinosoft.claim.schema.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类SwfPath工作流路径定义表
 */
@Entity
@Table(name = "SWFPATH")
public class SwfPath implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfPathId id;

	/** 属性模板名称 */
	private String modelName;

	/** 开始节点号 */
	private Integer startNodeNo;

	/** 属性起始节点名称 */
	private String startNodeName;

	/** 属性终止节点号 */
	private Integer endNodeNo;

	/** 属性终止节点名称 */
	private String endNodeName;

	/** 属性路径名称 */
	private String pathName;

	/** 属性优先级别 */
	private Integer priority;

	/** 属性是否缺省值 */
	private String defaultFlag;

	/** 属性是否存在流转条件 */
	private String conditionStatus;

	/** 属性正向流转所调用服务名 */
	private String forwardServices;

	/** 属性逆向流转所调用服务名 */
	private String backwardServices;

	/** 属性标志字段 */
	private String flag;

	/** 属性该流程的状态转储後是0 */
	private String flowStatus;

	/*
	 * 数据库中没有的字段，在页面上展示用和处理逻辑使用
	 */
	/** 属性swfConditions */
	private List<SwfCondition> swfConditions = new ArrayList<SwfCondition>(0);

	/** 属性显示列表 */
	private List<SwfPath> pathList = new ArrayList<SwfPath>(0);

	/** 属性下个节点s */
	private int nextNodeNo;

	/** 属性下个节点s */
	private String[] nextNodeNoList;

	/**
	 * 类SwfPath的默认构造方法
	 */
	public SwfPath() {
		id = new SwfPathId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "pathNo", column = @Column(name = "PATHNO")) })
	public SwfPathId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfPathId id) {
		this.id = id;
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
	 * 属性起始节点名称的getter方法
	 */

	@Column(name = "STARTNODENAME")
	public String getStartNodeName() {
		return this.startNodeName;
	}

	/**
	 * 属性起始节点名称的setter方法
	 */
	public void setStartNodeName(String startNodeName) {
		this.startNodeName = startNodeName;
	}

	/**
	 * 属性终止节点名称的getter方法
	 */

	@Column(name = "ENDNODENAME")
	public String getEndNodeName() {
		return this.endNodeName;
	}

	/**
	 * 属性终止节点名称的setter方法
	 */
	public void setEndNodeName(String endNodeName) {
		this.endNodeName = endNodeName;
	}

	/**
	 * 属性路径名称的getter方法
	 */

	@Column(name = "PATHNAME")
	public String getPathName() {
		return this.pathName;
	}

	/**
	 * 属性路径名称的setter方法
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * 属性优先级别的getter方法
	 */

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * 属性优先级别的setter方法
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * 属性是否缺省值的getter方法
	 */

	@Column(name = "DEFAULTFLAG")
	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	/**
	 * 属性是否缺省值的setter方法
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * 属性是否存在流转条件的getter方法
	 */

	@Column(name = "CONDITIONSTATUS")
	public String getConditionStatus() {
		return this.conditionStatus;
	}

	/**
	 * 属性是否存在流转条件的setter方法
	 */
	public void setConditionStatus(String conditionStatus) {
		this.conditionStatus = conditionStatus;
	}

	/**
	 * 属性正向流转所调用服务名的getter方法
	 */

	@Column(name = "FORWARDSERVICES")
	public String getForwardServices() {
		return this.forwardServices;
	}

	/**
	 * 属性正向流转所调用服务名的setter方法
	 */
	public void setForwardServices(String forwardServices) {
		this.forwardServices = forwardServices;
	}

	/**
	 * 属性逆向流转所调用服务名的getter方法
	 */

	@Column(name = "BACKWARDSERVICES")
	public String getBackwardServices() {
		return this.backwardServices;
	}

	/**
	 * 属性逆向流转所调用服务名的setter方法
	 */
	public void setBackwardServices(String backwardServices) {
		this.backwardServices = backwardServices;
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
	 * 属性swfConditions的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "swfPath")
	public List<SwfCondition> getSwfConditions() {
		return this.swfConditions;
	}

	/**
	 * 属性swfConditions的setter方法
	 */
	public void setSwfConditions(List<SwfCondition> swfConditions) {
		this.swfConditions = swfConditions;
	}

	@Transient
	public List<SwfPath> getPathList() {
		return pathList;
	}

	public void setPathList(List<SwfPath> pathList) {
		this.pathList = pathList;
	}

	@Transient
	public int getNextNodeNo() {
		return nextNodeNo;
	}

	public void setNextNodeNo(int nextNodeNo) {
		this.nextNodeNo = nextNodeNo;
	}

	@Transient
	public String[] getNextNodeNoList() {
		return nextNodeNoList;
	}

	public void setNextNodeNoList(String[] nextNodeNoList) {
		this.nextNodeNoList = nextNodeNoList;
	}

	@Column(name = "STARTNODENO")
	public Integer getStartNodeNo() {
		return startNodeNo;
	}

	public void setStartNodeNo(Integer startNodeNo) {
		this.startNodeNo = startNodeNo;
	}

	@Column(name = "ENDNODENO")
	public Integer getEndNodeNo() {
		return endNodeNo;
	}

	public void setEndNodeNo(Integer endNodeNo) {
		this.endNodeNo = endNodeNo;
	}

}
