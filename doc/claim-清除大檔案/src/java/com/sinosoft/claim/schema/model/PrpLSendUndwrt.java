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
import javax.persistence.Transient;

/**
 * POJO类PrpLSendUndwrt送审审核菜单
 */
@Entity
@Table(name = "PRPLSENDUNDWRT")
public class PrpLSendUndwrt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLSendUndwrtId id;

	/** 属性工作流ID */
	private String flowId;

	/** 属性节点种类 */
	private String nodeType;

	/** 属性审核者代码 */
	private String undwrtCode;

	/** 属性审核者名称 */
	private String undwrtName;

	/** 属性审核等级 */
	private Integer nodeNo;

	/** 属性审核标志位 */
	private String undwrtFlag;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorName;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性归属机构 */
	private String comCode;

	/** 属性理赔流转讨论留言表 */
	private PrpLmessage prpLmessage;

	/** 属性工作流对象 */
	private SwfLog swfLog = null;
	/** 属性工作流日志表转储对象 */
	private SwfLogStore swfLogStore = null;

	/** 送审时审核意见 */
	private String reCaseReason = "";

	/** 重开赔案原因 */
	private String appRecaseReason = "";

	/**
	 * 类PrpLSendUndwrt的默认构造方法
	 */
	public PrpLSendUndwrt() {
		this.id = new PrpLSendUndwrtId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLSendUndwrtId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLSendUndwrtId id) {
		this.id = id;
	}

	/**
	 * 属性工作流ID的getter方法
	 */

	@Column(name = "FLOWID")
	public String getFlowId() {
		return this.flowId;
	}

	/**
	 * 属性工作流ID的setter方法
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * 属性节点种类的getter方法
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点种类的setter方法
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 属性审核者代码的getter方法
	 */

	@Column(name = "UNDWRTCODE")
	public String getUndwrtCode() {
		return this.undwrtCode;
	}

	/**
	 * 属性审核者代码的setter方法
	 */
	public void setUndwrtCode(String undwrtCode) {
		this.undwrtCode = undwrtCode;
	}

	/**
	 * 属性审核者名称的getter方法
	 */

	@Column(name = "UNDWRTNAME")
	public String getUndwrtName() {
		return this.undwrtName;
	}

	/**
	 * 属性审核者名称的setter方法
	 */
	public void setUndwrtName(String undwrtName) {
		this.undwrtName = undwrtName;
	}

	/**
	 * 属性审核等级的getter方法
	 */

	@Column(name = "NODENO")
	public Integer getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性审核等级的setter方法
	 */
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性审核标志位的getter方法
	 */

	@Column(name = "UNDWRTFLAG")
	public String getUndwrtFlag() {
		return this.undwrtFlag;
	}

	/**
	 * 属性审核标志位的setter方法
	 */
	public void setUndwrtFlag(String undwrtFlag) {
		this.undwrtFlag = undwrtFlag;
	}

	/**
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Transient
	public PrpLmessage getPrpLmessage() {
		return prpLmessage;
	}

	public void setPrpLmessage(PrpLmessage prpLmessage) {
		this.prpLmessage = prpLmessage;
	}

	/**
	 * 设置属性工作流对象
	 * @param businessNo 待设置的属性工作流对象的值
	 */
	public void setSwfLog(SwfLog swfLog) {
		this.swfLog = swfLog;
	}

	/**
	 * 获取属性工作流对象
	 * @return 属性工作流对象的值
	 */
	@Transient
	public SwfLog getSwfLog() {
		return swfLog;
	}

	@Column(name = "RECASEREASON")
	public String getReCaseReason() {
		return reCaseReason;
	}

	public void setReCaseReason(String reCaseReason) {
		this.reCaseReason = reCaseReason;
	}

	@Column(name = "APPRECASEREASON")
	public String getAppRecaseReason() {
		return appRecaseReason;
	}

	public void setAppRecaseReason(String appRecaseReason) {
		this.appRecaseReason = appRecaseReason;
	}

	@Transient
	public SwfLogStore getSwfLogStore() {
		return swfLogStore;
	}

	public void setSwfLogStore(SwfLogStore swfLogStore) {
		this.swfLogStore = swfLogStore;
	}
}
