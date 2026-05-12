package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.dto.custom.TurnPageDto;

/**
 * POJO类PrpLBackCheck 回勘表
 */
@Entity
@Table(name = "PRPLBACKCHECK")
public class PrpLBackCheck implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registno;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性事故号 */
	private String damageNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性复查类型 */
	private String backCheckType;

	/** 属性复查人代码 */
	private String backCheckCode;

	/** 属性复堪处理机构 */
	private String deptComCode;

	/** 属性复堪处理人代码 */
	private String deptCheckCode;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性处理时间 */
	private Date inputTime;

	/** 属性转入时间 */
	private Date startTime;

	/** 属性结束时间 */
	private Date endTime;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性标志 */
	private String flag;
	String registNoSign = "";
	String policyNoSign = "";
	String operateDateSign = "";
	TurnPageDto turnPageDto = null;
	ArrayList<PrpLBackCheck> prpLBackCheckDtoList = null;

	/**
	 * 类PrpLBackCheck的默认构造方法
	 */
	public PrpLBackCheck() {
	}

	/**
	 * 属性REGISTNO的getter方法
	 */
	@Id
	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
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
	 * 属性事故号的getter方法
	 */

	@Column(name = "DAMAGENO")
	public String getDamageNo() {
		return this.damageNo;
	}

	/**
	 * 属性事故号的setter方法
	 */
	public void setDamageNo(String damageNo) {
		this.damageNo = damageNo;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性复查类型的getter方法
	 */

	@Column(name = "BACKCHECKTYPE")
	public String getBackCheckType() {
		return this.backCheckType;
	}

	/**
	 * 属性复查类型的setter方法
	 */
	public void setBackCheckType(String backCheckType) {
		this.backCheckType = backCheckType;
	}

	/**
	 * 属性复查人代码的getter方法
	 */

	@Column(name = "BACKCHECKCODE")
	public String getBackCheckCode() {
		return this.backCheckCode;
	}

	/**
	 * 属性复查人代码的setter方法
	 */
	public void setBackCheckCode(String backCheckCode) {
		this.backCheckCode = backCheckCode;
	}

	/**
	 * 属性复堪处理机构的getter方法
	 */

	@Column(name = "DEPTCOMCODE")
	public String getDeptComCode() {
		return this.deptComCode;
	}

	/**
	 * 属性复堪处理机构的setter方法
	 */
	public void setDeptComCode(String deptComCode) {
		this.deptComCode = deptComCode;
	}

	/**
	 * 属性复堪处理人代码的getter方法
	 */

	@Column(name = "DEPTCHECKCODE")
	public String getDeptCheckCode() {
		return this.deptCheckCode;
	}

	/**
	 * 属性复堪处理人代码的setter方法
	 */
	public void setDeptCheckCode(String deptCheckCode) {
		this.deptCheckCode = deptCheckCode;
	}

	/**
	 * 属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性处理时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTTIME")
	public Date getInputTime() {
		return this.inputTime;
	}

	/**
	 * 属性处理时间的setter方法
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 属性转入时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTTIME")
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 属性转入时间的setter方法
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 属性结束时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDTIME")
	public Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 属性结束时间的setter方法
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Transient
	public String getOperateDateSign() {
		return operateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		this.operateDateSign = operateDateSign;
	}

	@Transient
	public String getPolicyNoSign() {
		return policyNoSign;
	}

	@Transient
	public ArrayList<PrpLBackCheck> getPrpLBackCheckDtoList() {
		return prpLBackCheckDtoList;
	}

	public void setPrpLBackCheckDtoList(ArrayList<PrpLBackCheck> prpLBackCheckDtoList) {
		this.prpLBackCheckDtoList = prpLBackCheckDtoList;
	}

	public void setPolicyNoSign(String policyNoSign) {
		this.policyNoSign = policyNoSign;
	}

	@Transient
	public String getRegistNoSign() {
		return registNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		this.registNoSign = registNoSign;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}
}
