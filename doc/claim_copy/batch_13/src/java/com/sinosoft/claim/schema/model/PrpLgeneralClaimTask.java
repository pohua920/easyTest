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
 * POJO类PrpLgeneralClaimTask
 */
@Entity
@Table(name = "PRPLGENERALCLAIMTASK")
public class PrpLgeneralClaimTask implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性序号 */
	private Long serialNo;

	/** 属性报案号码 */
	private String registNo;

	/** 属性立案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性当前节点 */
	private String currentNode;

	/** 属性委托机构代码 */
	private String giveComCode;

	/** 属性委托机构名称 */
	private String giveComName;

	/** 属性接收机构代码 */
	private String receiveComCode;

	/** 属性接收机构名称 */
	private String receiveComName;

	/** 属性委托人员代码 */
	private String giveOperatorCode;

	/** 属性委托人员名称 */
	private String giveOperatorName;

	/** 属性接收人员代码 */
	private String receiveOperatorCode;

	/** 属性接收人员名称 */
	private String receiveOperatorName;

	/** 属性委托日期 */
	private Date giveTime;

	/** 属性接收日期 */
	private Date receiveTime;

	/** 属性节点状态 */
	private String nodeStatus;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性归属机构名称 */
	private String comName;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性extendString1 */
	private String extendString1;

	/** 属性extendString2 */
	private String extendString2;

	/** 属性EXTENDSTRING3 */
	private String extendstring3;

	/**
	 * 类PrpLgeneralClaimTask的默认构造方法
	 */
	public PrpLgeneralClaimTask() {
	}

	/**
	 * 属性序号的getter方法
	 */
	@Id
	@Column(name = "SERIALNO")
	public Long getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性当前节点的getter方法
	 */

	@Column(name = "CURRENTNODE")
	public String getCurrentNode() {
		return this.currentNode;
	}

	/**
	 * 属性当前节点的setter方法
	 */
	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * 属性委托机构代码的getter方法
	 */

	@Column(name = "GIVECOMCODE")
	public String getGiveComCode() {
		return this.giveComCode;
	}

	/**
	 * 属性委托机构代码的setter方法
	 */
	public void setGiveComCode(String giveComCode) {
		this.giveComCode = giveComCode;
	}

	/**
	 * 属性委托机构名称的getter方法
	 */

	@Column(name = "GIVECOMNAME")
	public String getGiveComName() {
		return this.giveComName;
	}

	/**
	 * 属性委托机构名称的setter方法
	 */
	public void setGiveComName(String giveComName) {
		this.giveComName = giveComName;
	}

	/**
	 * 属性接收机构代码的getter方法
	 */

	@Column(name = "RECEIVECOMCODE")
	public String getReceiveComCode() {
		return this.receiveComCode;
	}

	/**
	 * 属性接收机构代码的setter方法
	 */
	public void setReceiveComCode(String receiveComCode) {
		this.receiveComCode = receiveComCode;
	}

	/**
	 * 属性接收机构名称的getter方法
	 */

	@Column(name = "RECEIVECOMNAME")
	public String getReceiveComName() {
		return this.receiveComName;
	}

	/**
	 * 属性接收机构名称的setter方法
	 */
	public void setReceiveComName(String receiveComName) {
		this.receiveComName = receiveComName;
	}

	/**
	 * 属性委托人员代码的getter方法
	 */

	@Column(name = "GIVEOPERATORCODE")
	public String getGiveOperatorCode() {
		return this.giveOperatorCode;
	}

	/**
	 * 属性委托人员代码的setter方法
	 */
	public void setGiveOperatorCode(String giveOperatorCode) {
		this.giveOperatorCode = giveOperatorCode;
	}

	/**
	 * 属性委托人员名称的getter方法
	 */

	@Column(name = "GIVEOPERATORNAME")
	public String getGiveOperatorName() {
		return this.giveOperatorName;
	}

	/**
	 * 属性委托人员名称的setter方法
	 */
	public void setGiveOperatorName(String giveOperatorName) {
		this.giveOperatorName = giveOperatorName;
	}

	/**
	 * 属性接收人员代码的getter方法
	 */

	@Column(name = "RECEIVEOPERATORCODE")
	public String getReceiveOperatorCode() {
		return this.receiveOperatorCode;
	}

	/**
	 * 属性接收人员代码的setter方法
	 */
	public void setReceiveOperatorCode(String receiveOperatorCode) {
		this.receiveOperatorCode = receiveOperatorCode;
	}

	/**
	 * 属性接收人员名称的getter方法
	 */

	@Column(name = "RECEIVEOPERATORNAME")
	public String getReceiveOperatorName() {
		return this.receiveOperatorName;
	}

	/**
	 * 属性接收人员名称的setter方法
	 */
	public void setReceiveOperatorName(String receiveOperatorName) {
		this.receiveOperatorName = receiveOperatorName;
	}

	/**
	 * 属性委托日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GIVETIME")
	public Date getGiveTime() {
		return this.giveTime;
	}

	/**
	 * 属性委托日期的setter方法
	 */
	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}

	/**
	 * 属性接收日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVETIME")
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	/**
	 * 属性接收日期的setter方法
	 */
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * 属性节点状态的getter方法
	 */

	@Column(name = "NODESTATUS")
	public String getNodeStatus() {
		return this.nodeStatus;
	}

	/**
	 * 属性节点状态的setter方法
	 */
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性归属机构名称的getter方法
	 */

	@Column(name = "COMNAME")
	public String getComName() {
		return this.comName;
	}

	/**
	 * 属性归属机构名称的setter方法
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 属性extendString1的getter方法
	 */

	@Column(name = "EXTENDSTRING1")
	public String getExtendString1() {
		return this.extendString1;
	}

	/**
	 * 属性extendString1的setter方法
	 */
	public void setExtendString1(String extendString1) {
		this.extendString1 = extendString1;
	}

	/**
	 * 属性extendString2的getter方法
	 */

	@Column(name = "EXTENDSTRING2")
	public String getExtendString2() {
		return this.extendString2;
	}

	/**
	 * 属性extendString2的setter方法
	 */
	public void setExtendString2(String extendString2) {
		this.extendString2 = extendString2;
	}

	/**
	 * 属性EXTENDSTRING3的getter方法
	 */

	@Column(name = "EXTENDSTRING3")
	public String getExtendstring3() {
		return this.extendstring3;
	}

	/**
	 * 属性EXTENDSTRING3的setter方法
	 */
	public void setExtendstring3(String extendstring3) {
		this.extendstring3 = extendstring3;
	}

}
