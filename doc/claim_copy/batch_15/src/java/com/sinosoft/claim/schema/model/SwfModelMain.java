package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类SwfModelMain模板主表
 */
@Entity
@Table(name = "SWFMODELMAIN")
public class SwfModelMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性模板名称 */
	private String modelName;

	/** 属性创建人代码 */
	private String authorCode;

	/** 属性使用范围 */
	private String rightId;

	/** 属性创建日期 */
	private Date createDate;

	/** 属性最近一次修改日期 */
	private Date modifyDate;

	/** 属性模板类型 */
	private String modelType;

	/** 属性模板业务属性 */
	private String modelAttr;

	/** 属性模板状态 */
	private String modelStatus;

	/** 属性定义整个流程结束调用的服务 */
	private String closeService;

	/** 属性重新流转需调用的业务服务 */
	private String activeService;

	/*
	 * 数据库中没有的字段，在页面上展示用和处理逻辑使用
	 */
	/** 编辑类型 */
	private String editType = "";
	/** 列表 */
	List<SwfModelMain> modelMainList = new ArrayList<SwfModelMain>(0);

	/** 属性标志字段 */
	private String flag;

	/** 属性swfNodes */
	private List<SwfNode> swfNodes = new ArrayList<SwfNode>(0);

	/**
	 * 类SwfModelMain的默认构造方法
	 */
	public SwfModelMain() {
	}

	/**
	 * 属性模板编码的getter方法
	 */
	@Id
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
	 * 属性创建人代码的getter方法
	 */

	@Column(name = "AUTHORCODE")
	public String getAuthorCode() {
		return this.authorCode;
	}

	/**
	 * 属性创建人代码的setter方法
	 */
	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	/**
	 * 属性使用范围的getter方法
	 */

	@Column(name = "RIGHTID")
	public String getRightId() {
		return this.rightId;
	}

	/**
	 * 属性使用范围的setter方法
	 */
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	/**
	 * 属性创建日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 属性创建日期的setter方法
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 属性最近一次修改日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFYDATE")
	public Date getModifyDate() {
		return this.modifyDate;
	}

	/**
	 * 属性最近一次修改日期的setter方法
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 属性模板类型的getter方法
	 */

	@Column(name = "MODELTYPE")
	public String getModelType() {
		return this.modelType;
	}

	/**
	 * 属性模板类型的setter方法
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	/**
	 * 属性模板业务属性的getter方法
	 */

	@Column(name = "MODELATTR")
	public String getModelAttr() {
		return this.modelAttr;
	}

	/**
	 * 属性模板业务属性的setter方法
	 */
	public void setModelAttr(String modelAttr) {
		this.modelAttr = modelAttr;
	}

	/**
	 * 属性模板状态的getter方法
	 */

	@Column(name = "MODELSTATUS")
	public String getModelStatus() {
		return this.modelStatus;
	}

	/**
	 * 属性模板状态的setter方法
	 */
	public void setModelStatus(String modelStatus) {
		this.modelStatus = modelStatus;
	}

	/**
	 * 属性定义整个流程结束调用的服务的getter方法
	 */

	@Column(name = "CLOSESERVICE")
	public String getCloseService() {
		return this.closeService;
	}

	/**
	 * 属性定义整个流程结束调用的服务的setter方法
	 */
	public void setCloseService(String closeService) {
		this.closeService = closeService;
	}

	/**
	 * 属性重新流转需调用的业务服务的getter方法
	 */

	@Column(name = "ACTIVESERVICE")
	public String getActiveService() {
		return this.activeService;
	}

	/**
	 * 属性重新流转需调用的业务服务的setter方法
	 */
	public void setActiveService(String activeService) {
		this.activeService = activeService;
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
	 * 属性swfNodes的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "swfModelMain")
	public List<SwfNode> getSwfNodes() {
		return this.swfNodes;
	}

	/**
	 * 属性swfNodes的setter方法
	 */
	public void setSwfNodes(List<SwfNode> swfNodes) {
		this.swfNodes = swfNodes;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public List<SwfModelMain> getModelMainList() {
		return modelMainList;
	}

	public void setModelMainList(List<SwfModelMain> modelMainList) {
		this.modelMainList = modelMainList;
	}

}
