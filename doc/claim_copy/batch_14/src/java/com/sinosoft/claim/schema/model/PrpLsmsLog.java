package com.sinosoft.claim.schema.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PrpLsmsLog")
public class PrpLsmsLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 主鍵uuid*/
	private String id;
	/** 模板号码*/
	private String modelId;
	private String target;
	/** 报案号码*/
	private String businessNo;
	/**节点名称 */
	private String nodeType;
	/** 用户名称*/
	private String userCode;
	/** 发送成功标志，1成功，0失败*/
	private String success;
	/** 失败原因*/
	private String exception;
	/** 保存时间*/
	private Date inputDate;
	/** 數據有效標示，1為有效*/
	private String validstatus;
	/** 备用字段*/
	private String flag;
	/** 备用字段 */
	private String remark;
	

	@Id
	@Column(name = "ID")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="userCode")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="inputDate")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	@Column(name="modelId")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	@Column(name="businessNo")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name="nodeType")
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name="success")
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	@Column(name="exception")
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	@Column(name="validstatus")
	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="target")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
