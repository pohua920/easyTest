package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLgeneralClaimTaskLog
 */
@Entity
@Table(name = "PRPLGENERALCLAIMTASKLOG")
public class PrpLgeneralClaimTaskLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性主键 */
	private Long serialNo;

	/** 备案号码 */
	private String registNo;

	/** 立案号码 */
	private String claimNo;

	/** 保单号码 */
	private String policyNo;

	/** 险别 */
	private String riskCode;

	/** 节点名称 */
	private String currentNode;
	/** 节点名称 */
	private String currentNodeType;
	/** 操作人机构 */
	private String giveComCode;
	/** 操作人员机构名称 */
	private String giveComName;
	/** 操作人员代码 */
	private String giveOperatorCode;
	/** 操作人员名称 */
	private String giveOperatorName;

	/** 接收机构代码 */
	private String receiveComCode;

	/** 接收机构名称 */
	private String receiveComName;

	/** 接收人员代码 */
	private String receiveOperatorCode;

	/** 接收人员名称 */
	private String receiveOperatorName;

	/** 操作时间 */
	private Date giveTime;

	/** 接收时间 */
	private Date receiveTime;

	/** 节点状态 */
	private String nodeStatus;

	/** 机构名称 */
	private String comCode;

	/** 机构名称 */
	private String comName;

	/** 属性REMARK */
	private String remark;

	/** 属性FLAG */
	private String flag;

	/** 原因1 */
	private String extendString1;

	/** 原因2 */
	private String extendString2;

	/** 原因3 */
	private String extendstring3;
	private String actionType;
	@Id
	@Column(name="serialNo")
	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name="registNo")
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	@Column(name="claimNo")
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	@Column(name="policyNo")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	@Column(name="riskCode")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name="currentNode")
	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}
	@Column(name="currentNodeType")
	public String getCurrentNodeType() {
		return currentNodeType;
	}

	public void setCurrentNodeType(String currentNodeType) {
		this.currentNodeType = currentNodeType;
	}
	@Column(name="giveComCode")
	public String getGiveComCode() {
		return giveComCode;
	}

	public void setGiveComCode(String giveComCode) {
		this.giveComCode = giveComCode;
	}
	@Column(name="giveComName")
	public String getGiveComName() {
		return giveComName;
	}

	public void setGiveComName(String giveComName) {
		this.giveComName = giveComName;
	}
	@Column(name="giveOperatorCode")
	public String getGiveOperatorCode() {
		return giveOperatorCode;
	}

	public void setGiveOperatorCode(String giveOperatorCode) {
		this.giveOperatorCode = giveOperatorCode;
	}
	@Column(name="giveOperatorName")
	public String getGiveOperatorName() {
		return giveOperatorName;
	}

	public void setGiveOperatorName(String giveOperatorName) {
		this.giveOperatorName = giveOperatorName;
	}
	@Column(name="receiveComCode")
	public String getReceiveComCode() {
		return receiveComCode;
	}

	public void setReceiveComCode(String receiveComCode) {
		this.receiveComCode = receiveComCode;
	}
	@Column(name="receiveComName")
	public String getReceiveComName() {
		return receiveComName;
	}

	public void setReceiveComName(String receiveComName) {
		this.receiveComName = receiveComName;
	}
	@Column(name="receiveOperatorCode")
	public String getReceiveOperatorCode() {
		return receiveOperatorCode;
	}

	public void setReceiveOperatorCode(String receiveOperatorCode) {
		this.receiveOperatorCode = receiveOperatorCode;
	}
	@Column(name="receiveOperatorName")
	public String getReceiveOperatorName() {
		return receiveOperatorName;
	}

	public void setReceiveOperatorName(String receiveOperatorName) {
		this.receiveOperatorName = receiveOperatorName;
	}
	@Column(name="giveTime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}
	@Column(name="receiveTime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	@Column(name="nodeStatus")
	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	@Column(name="comCode")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	@Column(name="comName")
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="extendString1")
	public String getExtendString1() {
		return extendString1;
	}

	public void setExtendString1(String extendString1) {
		this.extendString1 = extendString1;
	}
	@Column(name="extendString2")
	public String getExtendString2() {
		return extendString2;
	}

	public void setExtendString2(String extendString2) {
		this.extendString2 = extendString2;
	}
	@Column(name="extendstring3")
	public String getExtendstring3() {
		return extendstring3;
	}

	public void setExtendstring3(String extendstring3) {
		this.extendstring3 = extendstring3;
	}
	@Column(name="actionType")
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
