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
@Table(name = "PrpLsms")
public class PrpLsms implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 主鍵uuid*/
	private String id;
	/** 傳送序號*/
	private String serial;
	/** 手機號碼*/
	private String target;
	/** 客戶識別碼  */
	private String corp_Id;
	/**  預計傳送日期 */
	private String submit_Date;
	/** 註記*/
	private String dr_Flag;
	/** 實際傳送日期*/
	private String deliver_Date;
	/** 語言*/
	private String language;
	/** 簡訊內容*/
	private String message;
	/** 操作人员（当前系统用户）*/
	private String userCode;
	/** 保存时间*/
	private Date inputDate;
	/** 數據有效標示，1為有效*/
	private String validstatus;
	/** 备用字段*/
	private String flag;
	/** 备用字段 */
	private String remark;
	/** 是否寫入發送簡訊表,0 不寫入，1 寫入  */
	private String smsFlag;
	/**  模版  */
	private String modelId;
	/** 报案号码*/
	private String businessNo;
	/**节点名称 */
	private String nodeType;

	public PrpLsms() {
		super();
	}

	public PrpLsms(PrpLsmsTemplate prpLsmsTemplate) {
		super();
		this.corp_Id = prpLsmsTemplate.getCorp_Id();
		this.submit_Date = prpLsmsTemplate.getSubmit_Date();
		this.dr_Flag = prpLsmsTemplate.getDr_Flag();
		this.language = prpLsmsTemplate.getLanguage();
		this.inputDate = new Date();
		this.smsFlag = prpLsmsTemplate.getSmsFlag();
		this.nodeType = prpLsmsTemplate.getNodeType();
		this.modelId = prpLsmsTemplate.getModelId();
		this.businessNo = prpLsmsTemplate.getBusinessNo();
		this.validstatus = "1";
	}
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
	@Column(name="serial")
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	@Column(name="target")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	@Column(name="corp_Id")
	public String getCorp_Id() {
		return corp_Id;
	}

	public void setCorp_Id(String corpId) {
		corp_Id = corpId;
	}
	@Column(name="submit_Date")
	public String getSubmit_Date() {
		return submit_Date;
	}

	public void setSubmit_Date(String submitDate) {
		submit_Date = submitDate;
	}
	@Column(name="dr_Flag")
	public String getDr_Flag() {
		return dr_Flag;
	}

	public void setDr_Flag(String drFlag) {
		dr_Flag = drFlag;
	}
	@Column(name="deliver_Date")
	public String getDeliver_Date() {
		return deliver_Date;
	}

	public void setDeliver_Date(String deliverDate) {
		deliver_Date = deliverDate;
	}
	@Column(name="language")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	@Column(name="message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	@Column(name="smsFlag")
	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
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
	
}
