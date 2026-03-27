package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
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
 * POJO类PrpLclaimStatus理赔节点状态表
 */
@Entity
@Table(name = "PRPLCLAIMSTATUS")
public class PrpLclaimStatus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLclaimStatusId id;

	/** 属性保单号 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性操作状态位 */
	private String status;

	/** 属性流程编号 */
	private String flowID;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性签单日期（制单日期） */
	private Date operateDate;

	/** 属性类型标志 */
	private String typeFlag;

	/** 属性状态字段 */
	private String flag;
	/** 属性显示列表 */
	private Collection<PrpLclaimStatus> claimList;
	/** 编辑类型 */
	private String editType = "";
	/** 属性类型标志 */
	private String typeFlagName = "";

	/** 操作员归属机构代码 */
	private String comCode = "";

	/**
	 * 类PrpLclaimStatus的默认构造方法
	 */
	public PrpLclaimStatus() {
		id = new PrpLclaimStatusId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "nodeType", column = @Column(name = "NODETYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLclaimStatusId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLclaimStatusId id) {
		this.id = id;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
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
	 * 属性操作状态位的getter方法
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性操作状态位的setter方法
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性流程编号的getter方法
	 */

	@Column(name = "FLOWID")
	public String getFlowID() {
		return this.flowID;
	}

	/**
	 * 属性流程编号的setter方法
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	/**
	 * 属性经办人代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性签单日期（制单日期）的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性签单日期（制单日期）的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性类型标志的getter方法
	 */

	@Column(name = "TYPEFLAG")
	public String getTypeFlag() {
		return this.typeFlag;
	}

	/**
	 * 属性类型标志的setter方法
	 */
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setClaimList(Collection<PrpLclaimStatus> claimList) {
		this.claimList = claimList;
	}

	public void setTypeFlagName(String typeFlagName) {
		this.typeFlagName = typeFlagName;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public Collection<PrpLclaimStatus> getClaimList() {
		return claimList;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	@Transient
	public String getTypeFlagName() {
		return typeFlagName;
	}

	@Transient
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

}
