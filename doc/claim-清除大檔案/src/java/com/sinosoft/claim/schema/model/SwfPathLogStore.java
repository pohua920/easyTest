package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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
 * POJO类SwfPathLogStore 工作流日志表转储表
 */
@Entity
@Table(name = "SWFPATHLOGSTORE")
public class SwfPathLogStore implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfPathLogStoreId id;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性路径名称 */
	private String pathName;

	/** 属性起始节点 */
	private Integer startNodeNo = 0;

	/** 属性起始节点名称 */
	private String startNodeName;

	/** 属性终止节点 */
	private Integer endNodeNo = 0;

	/** 属性终止节点名称 */
	private String endNodeName;

	/** 属性流入时间 */
	private Date flowInTime;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类SwfPathLogStore的默认构造方法
	 */
	public SwfPathLogStore() {
		id = new SwfPathLogStoreId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowId", column = @Column(name = "FLOWID")), @AttributeOverride(name = "pathNo", column = @Column(name = "PATHNO")) })
	public SwfPathLogStoreId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfPathLogStoreId id) {
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
	 * 属性起始节点的getter方法
	 */

	@Column(name = "STARTNODENO")
	public Integer getStartNodeNo() {
		return this.startNodeNo;
	}

	/**
	 * 属性起始节点的setter方法
	 */
	public void setStartNodeNo(Integer startNodeNo) {
		this.startNodeNo = startNodeNo;
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
	 * 属性终止节点的getter方法
	 */

	@Column(name = "ENDNODENO")
	public Integer getEndNodeNo() {
		return this.endNodeNo;
	}

	/**
	 * 属性终止节点的setter方法
	 */
	public void setEndNodeNo(Integer endNodeNo) {
		this.endNodeNo = endNodeNo;
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
	 * 属性流入时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FLOWINTIME")
	public Date getFlowInTime() {
		return this.flowInTime;
	}

	/**
	 * 属性流入时间的setter方法
	 */
	public void setFlowInTime(Date flowInTime) {
		this.flowInTime = flowInTime;
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

	public SwfPathLog toSwfPathLog() {
		SwfPathLog swfPathLog = new SwfPathLog();
		swfPathLog.setId(new SwfPathLogId(this.id.getFlowId(), this.id.getPathNo()));
		swfPathLog.setModelNo(this.modelNo);
		swfPathLog.setPathName(this.pathName);
		swfPathLog.setStartNodeNo(this.startNodeNo);
		swfPathLog.setStartNodeName(this.startNodeName);
		swfPathLog.setEndNodeNo(this.endNodeNo);
		swfPathLog.setEndNodeName(this.endNodeName);
		swfPathLog.setFlowInTime(this.flowInTime);
		swfPathLog.setFlag(this.flag);
		return swfPathLog;
	}

}
