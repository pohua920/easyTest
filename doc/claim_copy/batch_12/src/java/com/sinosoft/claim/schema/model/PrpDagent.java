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
 * 代理人代码表的数据传输对象类PrpDagent
 */
@Entity
@Table(name = "PRPDAGENT")
public class PrpDagent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 属性AGENTENAME */
	private String agenteName;

	/** 属性PERMITFLAG */
	private String permitFlag;

	/** 属性PRINCIPALNAME */
	private String principalName;

	/** 属性MOBILE */
	private String mobile;

	/** 属性EMAIL */
	private String email;

	/** 属性NETADDRESS */
	private String netAddress;

	/** 属性BANK */
	private String bank;

	/** 属性ACCOUNT */
	private String account;

	/** 属性LOWERVIEWFLAG */
	private String lowerViewFlag;

	/** 属性AGENTSHORTNAME */
	private String agentShortName;

	/** 属性CREATERCODE */
	private String createRcode;

	/** 属性CREATETIME */
	private String createTime;

	/** 属性UPDATERCODE */
	private String updaterCode;

	/** 属性UPDATEDATE */
	private String updateDate;

	/** 属性SUBAGENTTYPE */
	private String subAgentType;

	/** 属性CHANNELTYPE */
	private String channelType;

	/** 属性SALESCHANNELCODE */
	private String salesChannelCode;

	/** 属性PERSTARTDATE */
	private Date perStartDate;

	/** 属性PERENDDATE */
	private Date perEndDate;

	/** 属性ACQNO */
	private String acqNo;

	/** 属性ACQSTARTDATE */
	private Date acqStartDate;

	/** 属性ACQENDDATE */
	private Date acqEndDate;

	/** 属性原币别编码 */
	private String currency;

	/** 属性DISTANCEFLAG */
	private String distanceFlag;

	/** 属性操作员编码 */
	private String operatorCode;

	/** 属性AGENTLEVEL */
	private String agentLevel;

	/** 属性BID */
	private String bid;

	/** 属性SALESFLAG */
	private String salesFlag;

	/** 属性手机号码 */
	private String mobileNo;

	/** 属性HANDLERCODE */
	private String handlerCode;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性代理人名称 */
	private String agentName;

	/** 属性代理人地址 */
	private String addressName;

	/** 属性邮政编码 */
	private String postCode;

	/** 属性代理人类型 */
	private String agentType;

	/** 属性许可证号 */
	private String permitNo;

	/** 属性联系人 */
	private String linkerName;

	/** 属性合同期 */
	private Date bargainDate;
	/** 属性电话 */
	private String phoneNumber;

	/** 属性传真 */
	private String faxNumber;

	/** 属性归属机构代码 */
	private String comCode;

	/** 属性上级代理人代码 */
	private String upperAgentCode;

	/** 属性新的代理人代码 */
	private String newAgentCode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性专项代码(对应会计科目) */
	private String articleCode;

	/** 属性标志字段 */
	private String flag;

	/** 属性标志字段 */
	private String agentNameSimple;

	/**
	 * 类PrpDagent的默认构造方法
	 */
	public PrpDagent() {
	}

	/**
	 * 属性AGENTCODE的getter方法
	 */
	@Id
	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性AGENTCODE的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性AGENTNAME的getter方法
	 */

	@Column(name = "AGENTNAME")
	public String getAgentName() {
		return this.agentName;
	}

	/**
	 * 属性AGENTNAME的setter方法
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * 属性ADDRESSNAME的getter方法
	 */

	@Column(name = "ADDRESSNAME")
	public String getAddressName() {
		return this.addressName;
	}

	/**
	 * 属性ADDRESSNAME的setter方法
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * 属性POSTCODE的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 属性POSTCODE的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 属性AGENTTYPE的getter方法
	 */

	@Column(name = "AGENTTYPE")
	public String getAgentType() {
		return this.agentType;
	}

	/**
	 * 属性AGENTTYPE的setter方法
	 */
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	/**
	 * 属性PERMITNO的getter方法
	 */

	@Column(name = "PERMITNO")
	public String getPermitNo() {
		return this.permitNo;
	}

	/**
	 * 属性PERMITNO的setter方法
	 */
	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	/**
	 * 属性LINKERNAME的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性LINKERNAME的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**
	 * 属性BARGAINDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BARGAINDATE")
	public Date getBargainDate() {
		return this.bargainDate;
	}

	/**
	 * 属性BARGAINDATE的setter方法
	 */
	public void setBargainDate(Date bargainDate) {
		this.bargainDate = bargainDate;
	}

	/**
	 * 属性PHONENUMBER的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性PHONENUMBER的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性FAXNUMBER的getter方法
	 */

	@Column(name = "FAXNUMBER")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**
	 * 属性FAXNUMBER的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * 属性COMCODE的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性COMCODE的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性HANDLERCODE的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性HANDLERCODE的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性UPPERAGENTCODE的getter方法
	 */

	@Column(name = "UPPERAGENTCODE")
	public String getUpperAgentCode() {
		return this.upperAgentCode;
	}

	/**
	 * 属性UPPERAGENTCODE的setter方法
	 */
	public void setUpperAgentCode(String upperAgentCode) {
		this.upperAgentCode = upperAgentCode;
	}

	/**
	 * 属性NEWAGENTCODE的getter方法
	 */

	@Column(name = "NEWAGENTCODE")
	public String getNewAgentCode() {
		return this.newAgentCode;
	}

	/**
	 * 属性NEWAGENTCODE的setter方法
	 */
	public void setNewAgentCode(String newAgentCode) {
		this.newAgentCode = newAgentCode;
	}

	/**
	 * 属性VALIDSTATUS的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性VALIDSTATUS的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性ARTICLECODE的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性ARTICLECODE的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性AGENTENAME的getter方法
	 */

	@Column(name = "AGENTENAME")
	public String getAgenteName() {
		return this.agenteName;
	}

	/**
	 * 属性AGENTENAME的setter方法
	 */
	public void setAgenteName(String agenteName) {
		this.agenteName = agenteName;
	}

	/**
	 * 属性PERMITFLAG的getter方法
	 */

	@Column(name = "PERMITFLAG")
	public String getPermitFlag() {
		return this.permitFlag;
	}

	/**
	 * 属性PERMITFLAG的setter方法
	 */
	public void setPermitFlag(String permitFlag) {
		this.permitFlag = permitFlag;
	}

	/**
	 * 属性PRINCIPALNAME的getter方法
	 */

	@Column(name = "PRINCIPALNAME")
	public String getPrincipalName() {
		return this.principalName;
	}

	/**
	 * 属性PRINCIPALNAME的setter方法
	 */
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	/**
	 * 属性MOBILE的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性MOBILE的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性EMAIL的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性EMAIL的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性NETADDRESS的getter方法
	 */

	@Column(name = "NETADDRESS")
	public String getNetAddress() {
		return this.netAddress;
	}

	/**
	 * 属性NETADDRESS的setter方法
	 */
	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}

	/**
	 * 属性BANK的getter方法
	 */

	@Column(name = "BANK")
	public String getBank() {
		return this.bank;
	}

	/**
	 * 属性BANK的setter方法
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 属性ACCOUNT的getter方法
	 */

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	/**
	 * 属性ACCOUNT的setter方法
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 属性LOWERVIEWFLAG的getter方法
	 */

	@Column(name = "LOWERVIEWFLAG")
	public String getLowerViewFlag() {
		return this.lowerViewFlag;
	}

	/**
	 * 属性LOWERVIEWFLAG的setter方法
	 */
	public void setLowerViewFlag(String lowerViewFlag) {
		this.lowerViewFlag = lowerViewFlag;
	}

	/**
	 * 属性AGENTSHORTNAME的getter方法
	 */

	@Column(name = "AGENTSHORTNAME")
	public String getAgentShortName() {
		return this.agentShortName;
	}

	/**
	 * 属性AGENTSHORTNAME的setter方法
	 */
	public void setAgentShortName(String agentShortName) {
		this.agentShortName = agentShortName;
	}

	/**
	 * 属性CREATERCODE的getter方法
	 */

	@Column(name = "CREATERCODE")
	public String getCreateRcode() {
		return this.createRcode;
	}

	/**
	 * 属性CREATERCODE的setter方法
	 */
	public void setCreateRcode(String createRcode) {
		this.createRcode = createRcode;
	}

	/**
	 * 属性CREATETIME的getter方法
	 */

	@Column(name = "CREATETIME")
	public String getCreateTime() {
		return this.createTime;
	}

	/**
	 * 属性CREATETIME的setter方法
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 属性UPDATERCODE的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性UPDATERCODE的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性UPDATEDATE的getter方法
	 */

	@Column(name = "UPDATEDATE")
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性UPDATEDATE的setter方法
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性SUBAGENTTYPE的getter方法
	 */

	@Column(name = "SUBAGENTTYPE")
	public String getSubAgentType() {
		return this.subAgentType;
	}

	/**
	 * 属性SUBAGENTTYPE的setter方法
	 */
	public void setSubAgentType(String subAgentType) {
		this.subAgentType = subAgentType;
	}

	/**
	 * 属性CHANNELTYPE的getter方法
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性CHANNELTYPE的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性SALESCHANNELCODE的getter方法
	 */

	@Column(name = "SALESCHANNELCODE")
	public String getSalesChannelCode() {
		return this.salesChannelCode;
	}

	/**
	 * 属性SALESCHANNELCODE的setter方法
	 */
	public void setSalesChannelCode(String salesChannelCode) {
		this.salesChannelCode = salesChannelCode;
	}

	/**
	 * 属性PERSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PERSTARTDATE")
	public Date getPerStartDate() {
		return this.perStartDate;
	}

	/**
	 * 属性PERSTARTDATE的setter方法
	 */
	public void setPerStartDate(Date perStartDate) {
		this.perStartDate = perStartDate;
	}

	/**
	 * 属性PERENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PERENDDATE")
	public Date getPerEndDate() {
		return this.perEndDate;
	}

	/**
	 * 属性PERENDDATE的setter方法
	 */
	public void setPerEndDate(Date perEndDate) {
		this.perEndDate = perEndDate;
	}

	/**
	 * 属性ACQNO的getter方法
	 */

	@Column(name = "ACQNO")
	public String getAcqNo() {
		return this.acqNo;
	}

	/**
	 * 属性ACQNO的setter方法
	 */
	public void setAcqNo(String acqNo) {
		this.acqNo = acqNo;
	}

	/**
	 * 属性ACQSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACQSTARTDATE")
	public Date getAcqStartDate() {
		return this.acqStartDate;
	}

	/**
	 * 属性ACQSTARTDATE的setter方法
	 */
	public void setAcqStartDate(Date acqStartDate) {
		this.acqStartDate = acqStartDate;
	}

	/**
	 * 属性ACQENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACQENDDATE")
	public Date getAcqEndDate() {
		return this.acqEndDate;
	}

	/**
	 * 属性ACQENDDATE的setter方法
	 */
	public void setAcqEndDate(Date acqEndDate) {
		this.acqEndDate = acqEndDate;
	}

	/**
	 * 属性原币别编码的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性原币别编码的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性DISTANCEFLAG的getter方法
	 */

	@Column(name = "DISTANCEFLAG")
	public String getDistanceFlag() {
		return this.distanceFlag;
	}

	/**
	 * 属性DISTANCEFLAG的setter方法
	 */
	public void setDistanceFlag(String distanceFlag) {
		this.distanceFlag = distanceFlag;
	}

	/**
	 * 属性操作员编码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员编码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性AGENTLEVEL的getter方法
	 */

	@Column(name = "AGENTLEVEL")
	public String getAgentLevel() {
		return this.agentLevel;
	}

	/**
	 * 属性AGENTLEVEL的setter方法
	 */
	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	/**
	 * 属性BID的getter方法
	 */

	@Column(name = "BID")
	public String getBid() {
		return this.bid;
	}

	/**
	 * 属性BID的setter方法
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}

	/**
	 * 属性SALESFLAG的getter方法
	 */

	@Column(name = "SALESFLAG")
	public String getSalesFlag() {
		return this.salesFlag;
	}

	/**
	 * 属性SALESFLAG的setter方法
	 */
	public void setSalesFlag(String salesFlag) {
		this.salesFlag = salesFlag;
	}

	/**
	 * 属性MOBILENO的getter方法
	 */

	@Column(name = "MOBILENO")
	public String getMobileNo() {
		return this.mobileNo;
	}

	/**
	 * 属性MOBILENO的setter方法
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * 属性AGENTNAMESIMPLE的getter方法
	 */

	@Column(name = "AGENTNAMESIMPLE")
	public String getAgentNameSimple() {
		return this.agentNameSimple;
	}

	/**
	 * 属性AGENTNAMESIMPLE的setter方法
	 */
	public void setAgentNameSimple(String agentNameSimple) {
		this.agentNameSimple = agentNameSimple;
	}

}
