package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRPCFLOWINFOHIS")
public class PrpCFlowInfoHis implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 工作流号（主键） */
	private String flowID;

	/** 业务号 */
	private String businessNo;

	/** 业务类型（T-投保单，E-批单） */
	private String businessType;

	/** 功能类型 */
	private String taskType;

	/** 工作状态 */
	private String workStatues;

	/** 工作发起时间 */
	private Date flowinTime;

	/** 工作完成时间 */
	private Date submitTime;

	/** 操作员代码 */
	private String userCode;

	/** 机构代码 */
	private String comCode;

	/** 产品代码 */
	private String riskCode;

	/** 投保人名称 */
	private String insuredName;

	/** 总保额 */
	private String sumInsured;

	/** 标志字段 */
	private String flag;

	/** 单证号 */
	private String certiNo;

	/** 合同号 */
	private String contractNo;

	/** 录入时间 */
	private Date inputTime;

	/** 车牌号码 */
	private String licenseNo;

	public PrpCFlowInfoHis() {

	}

	public PrpCFlowInfoHis(String flowID) {
		super();
		this.flowID = flowID;
	}

	@Id
	@Column(name = "FLOWID")
	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name = "BUSINESSTYPE")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "TASKTYPE")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(name = "WORKSTATUES")
	public String getWorkStatues() {
		return workStatues;
	}

	public void setWorkStatues(String workStatues) {
		this.workStatues = workStatues;
	}

	@Column(name = "FLOWINTIME")
	public Date getFlowinTime() {
		return flowinTime;
	}

	public void setFlowinTime(Date flowinTime) {
		this.flowinTime = flowinTime;
	}

	@Column(name = "SUBMITTIME")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "USERCODE")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "COMCODE")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	@Column(name = "SUMINSURED")
	public String getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return certiNo;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "INPUTTIME")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

}
