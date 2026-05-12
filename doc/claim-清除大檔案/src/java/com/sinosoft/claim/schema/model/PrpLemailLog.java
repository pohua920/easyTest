package com.sinosoft.claim.schema.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PRPLEMAILLOG")
public class PrpLemailLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 主鍵uuid*/
	private String id;
	/** 業務號碼*/
	private String businessNo;
	/** 发送类型：prplemailConfig的主键（模板号）*/
	private String sendType;
	/** 收件人 ，01再保，02承保*/
	private String receiver;
	/** 發送人地址*/
	private String sender;
	/** 收件人地址*/
	private String addressee;
	/** 郵件標題*/
	private String title;
	/** 發送時間*/
	private Date sendTime;
	/** 數據有效標示，1為有效*/
	private String validstatus;
	/** 郵件發送成功標示，0失敗，1成功*/
	private String flag = "1";
	/** 備註(异常记录)*/
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
	
	@Column(name = "SENDER")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	@Column(name = "ADDRESSEE")
	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "SENDTIME")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name = "VALIDSTATUS")
	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	@Column(name = "SENDTYPE")
	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	@Column(name = "RECEIVER")
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
}
