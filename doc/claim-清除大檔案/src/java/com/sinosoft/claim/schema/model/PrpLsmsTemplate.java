package com.sinosoft.claim.schema.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "PrpLsmsTemplate")
public class PrpLsmsTemplate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 模板号码，表主键*/
	private String modelId;
	/** 节点号*/
	private String nodeType;
	/** 发送手机号码，支持固定值（13800000000）或者程序指定值$!{targets}，多个使用，号分隔*/
	private String target;
	/** 固定值為01900200017102700023或者程序指定$!{corp_Id} */
	private String corp_Id;
	/**  預計傳送日期，不设置默认为系统当前时间，$!{submit_Date} */
	private String submit_Date;
	/** 固定為1,或者程序指定$!{dr_Flag} */
	private String dr_Flag;
	/** 語言*/
	private String language;
	/** 台壽保產險通知：您的客戶$!{insuredName}於$!{damageStartDatee}發生保險事故本公司承辦人
	 * $!{handlerCode}電話$!{phoneNumber}已受理申請請關心
	 * */
	private String message;
	/** 同步或者异步发送，1同步发送，0异步发送，默认是异步发送简讯，支持程序指定$!{sny}*/
	private String syn;
	/** 是否寫入發送簡訊表,0 不寫入，1 寫入  */
	private String smsFlag = "0";
	/** 數據有效標示，1為有效*/
	private String validstatus;
	/** 备用字段*/
	private String flag;
	/** 备用字段 */
	private String remark;
	/** 业务号码 */
	private String businessNo = "${businessNo}";
	@Id
	@Column(name="modelId")
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	@Column(name="nodeType")
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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
	@Column(name="syn")
	public String getSyn() {
		return syn;
	}
	public void setSyn(String syn) {
		this.syn = syn;
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
	@Transient
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
}
