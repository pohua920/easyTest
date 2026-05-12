package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLProxyId
 */
@Embeddable
public class PrpLProxyId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性工作流ID */
	private String flowId;

	/** 属性工作流序号 */
	private BigDecimal logNo;

	/** 属性节点名称 */
	private String nodeName;

	/** 属性节点状态 */
	private String nodestatus;

	/** 属性工作流状态 */
	private String flowStatus;

	/** 属性原业务操作人代码 */
	private String fromUserCode;

	/** 属性原业务操作人名称 */
	private String fromUserName;

	/** 属性原业务操作人归属机构代码 */
	private String fromComCode;

	/** 属性原业务操作人归属机构名称 */
	private String fromComName;

	/** 属性新业务操作人代码 */
	private String toUserCode;

	/** 属性新业务操作人名称 */
	private String toUserName;

	/** 属性新业务操作人归属机构代码 */
	private String toComCode;

	/** 属性新业务操作人归属机构名称 */
	private String toComName;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性归属机构 */
	private String comCode;

	/** 属性调派操作时间 */
	private Date operateTime;

	/**
	 * 类PrpLProxyId的默认构造方法
	 */
	public PrpLProxyId() {
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
	 * 属性工作流序号的getter方法
	 */

	@Column(name = "LOGNO")
	public BigDecimal getLogNo() {
		return this.logNo;
	}

	/**
	 * 属性工作流序号的setter方法
	 */
	public void setLogNo(BigDecimal logNo) {
		this.logNo = logNo;
	}

	/**
	 * 属性节点名称的getter方法
	 */

	@Column(name = "NODENAME")
	public String getNodeName() {
		return this.nodeName;
	}

	/**
	 * 属性节点名称的setter方法
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 属性节点状态的getter方法
	 */

	@Column(name = "NODESTATUS")
	public String getNodestatus() {
		return this.nodestatus;
	}

	/**
	 * 属性节点状态的setter方法
	 */
	public void setNodestatus(String nodestatus) {
		this.nodestatus = nodestatus;
	}

	/**
	 * 属性工作流状态的getter方法
	 */

	@Column(name = "FLOWSTATUS")
	public String getFlowStatus() {
		return this.flowStatus;
	}

	/**
	 * 属性工作流状态的setter方法
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * 属性原业务操作人代码的getter方法
	 */

	@Column(name = "FROMUSERCODE")
	public String getFromUserCode() {
		return this.fromUserCode;
	}

	/**
	 * 属性原业务操作人代码的setter方法
	 */
	public void setFromUserCode(String fromUserCode) {
		this.fromUserCode = fromUserCode;
	}

	/**
	 * 属性原业务操作人名称的getter方法
	 */

	@Column(name = "FROMUSERNAME")
	public String getFromUserName() {
		return this.fromUserName;
	}

	/**
	 * 属性原业务操作人名称的setter方法
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * 属性原业务操作人归属机构代码的getter方法
	 */

	@Column(name = "FROMCOMCODE")
	public String getFromComCode() {
		return this.fromComCode;
	}

	/**
	 * 属性原业务操作人归属机构代码的setter方法
	 */
	public void setFromComCode(String fromComCode) {
		this.fromComCode = fromComCode;
	}

	/**
	 * 属性原业务操作人归属机构名称的getter方法
	 */

	@Column(name = "FROMCOMNAME")
	public String getFromComName() {
		return this.fromComName;
	}

	/**
	 * 属性原业务操作人归属机构名称的setter方法
	 */
	public void setFromComName(String fromComName) {
		this.fromComName = fromComName;
	}

	/**
	 * 属性新业务操作人代码的getter方法
	 */

	@Column(name = "TOUSERCODE")
	public String getToUserCode() {
		return this.toUserCode;
	}

	/**
	 * 属性新业务操作人代码的setter方法
	 */
	public void setToUserCode(String toUserCode) {
		this.toUserCode = toUserCode;
	}

	/**
	 * 属性新业务操作人名称的getter方法
	 */

	@Column(name = "TOUSERNAME")
	public String getToUserName() {
		return this.toUserName;
	}

	/**
	 * 属性新业务操作人名称的setter方法
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	/**
	 * 属性新业务操作人归属机构代码的getter方法
	 */

	@Column(name = "TOCOMCODE")
	public String getToComCode() {
		return this.toComCode;
	}

	/**
	 * 属性新业务操作人归属机构代码的setter方法
	 */
	public void setToComCode(String toComCode) {
		this.toComCode = toComCode;
	}

	/**
	 * 属性新业务操作人归属机构名称的getter方法
	 */

	@Column(name = "TOCOMNAME")
	public String getToComName() {
		return this.toComName;
	}

	/**
	 * 属性新业务操作人归属机构名称的setter方法
	 */
	public void setToComName(String toComName) {
		this.toComName = toComName;
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

	/**
	 * 属性调派操作时间的getter方法
	 */

	@Column(name = "OPERATETIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	/**
	 * 属性调派操作时间的setter方法
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLProxyId)) {
			return false;
		}
		PrpLProxyId castOther = (PrpLProxyId) other;

		return ((this.getFlowId() == castOther.getFlowId()) || (this.getFlowId() != null && castOther.getFlowId() != null && this.getFlowId().equals(castOther.getFlowId())))
				&& ((this.getLogNo() == castOther.getLogNo()) || (this.getLogNo() != null && castOther.getLogNo() != null && this.getLogNo().equals(castOther.getLogNo())))
				&& ((this.getNodeName() == castOther.getNodeName()) || (this.getNodeName() != null && castOther.getNodeName() != null && this.getNodeName().equals(castOther.getNodeName())))
				&& ((this.getNodestatus() == castOther.getNodestatus()) || (this.getNodestatus() != null && castOther.getNodestatus() != null && this.getNodestatus().equals(castOther.getNodestatus())))
				&& ((this.getFlowStatus() == castOther.getFlowStatus()) || (this.getFlowStatus() != null && castOther.getFlowStatus() != null && this.getFlowStatus().equals(castOther.getFlowStatus())))
				&& ((this.getFromUserCode() == castOther.getFromUserCode()) || (this.getFromUserCode() != null && castOther.getFromUserCode() != null && this.getFromUserCode().equals(castOther.getFromUserCode())))
				&& ((this.getFromUserName() == castOther.getFromUserName()) || (this.getFromUserName() != null && castOther.getFromUserName() != null && this.getFromUserName().equals(castOther.getFromUserName())))
				&& ((this.getFromComCode() == castOther.getFromComCode()) || (this.getFromComCode() != null && castOther.getFromComCode() != null && this.getFromComCode().equals(castOther.getFromComCode())))
				&& ((this.getFromComName() == castOther.getFromComName()) || (this.getFromComName() != null && castOther.getFromComName() != null && this.getFromComName().equals(castOther.getFromComName())))
				&& ((this.getToUserCode() == castOther.getToUserCode()) || (this.getToUserCode() != null && castOther.getToUserCode() != null && this.getToUserCode().equals(castOther.getToUserCode())))
				&& ((this.getToUserName() == castOther.getToUserName()) || (this.getToUserName() != null && castOther.getToUserName() != null && this.getToUserName().equals(castOther.getToUserName())))
				&& ((this.getToComCode() == castOther.getToComCode()) || (this.getToComCode() != null && castOther.getToComCode() != null && this.getToComCode().equals(castOther.getToComCode())))
				&& ((this.getToComName() == castOther.getToComName()) || (this.getToComName() != null && castOther.getToComName() != null && this.getToComName().equals(castOther.getToComName())))
				&& ((this.getOperatorCode() == castOther.getOperatorCode()) || (this.getOperatorCode() != null && castOther.getOperatorCode() != null && this.getOperatorCode().equals(castOther.getOperatorCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getOperateTime() == castOther.getOperateTime()) || (this.getOperateTime() != null && castOther.getOperateTime() != null && this.getOperateTime().equals(castOther.getOperateTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFlowId() == null ? 0 : this.getFlowId().hashCode());
		result = 37 * result + (getLogNo() == null ? 0 : this.getLogNo().hashCode());
		result = 37 * result + (getNodeName() == null ? 0 : this.getNodeName().hashCode());
		result = 37 * result + (getNodestatus() == null ? 0 : this.getNodestatus().hashCode());
		result = 37 * result + (getFlowStatus() == null ? 0 : this.getFlowStatus().hashCode());
		result = 37 * result + (getFromUserCode() == null ? 0 : this.getFromUserCode().hashCode());
		result = 37 * result + (getFromUserName() == null ? 0 : this.getFromUserName().hashCode());
		result = 37 * result + (getFromComCode() == null ? 0 : this.getFromComCode().hashCode());
		result = 37 * result + (getFromComName() == null ? 0 : this.getFromComName().hashCode());
		result = 37 * result + (getToUserCode() == null ? 0 : this.getToUserCode().hashCode());
		result = 37 * result + (getToUserName() == null ? 0 : this.getToUserName().hashCode());
		result = 37 * result + (getToComCode() == null ? 0 : this.getToComCode().hashCode());
		result = 37 * result + (getToComName() == null ? 0 : this.getToComName().hashCode());
		result = 37 * result + (getOperatorCode() == null ? 0 : this.getOperatorCode().hashCode());
		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getOperateTime() == null ? 0 : this.getOperateTime().hashCode());
		return result;
	}

}
