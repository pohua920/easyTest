package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "prpchannelchecking")
public class PrpChannelChecking implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 序号 */
	private Integer serialNo;
	/** 保单号 */
	private String policyNo;
	/** 单证流水号 */
	private String printNo;
	/** 投保总份数 */
	private Integer sumQuantity;
	/** 总保额 */
	private Double sumAmount;
	/** 总保费 */
	private Double sumPremium;
	/** 归属机构 */
	private String comCode;
	/** 渠道代码 */
	private String channelCode;
	/** 渠道机构代码 */
	private String channelComCode;
	/** 渠道交易日期(YYYY-MM-DD HH:mm:ss) */
	private Date channelTradeDate;
	/** 保单状态 0-有效，1-已撤保 */
	private String policyType;
	/** 渠道细分类型 */
	private String sourceType;
	/** 对帳保单渠道代码 */
	private String chCode;
	/** 对帳保单渠道机构代码 */
	private String chComCode;
	/** 对帳保单渠道交易代码 */
	private String chTradeCode;
	/** 对帳保单渠道交易流水号 */
	private String chlTradeSerialNo;
	/** 对帳保单渠道交易日期(YYYY-MM-DD HH:mm:ss) */
	private Date chTradeDate;
	/** 对帳保险单号 */
	private String chPolicyNo;
	/** 对帳保单印刷号 */
	private String chPrintNo;
	/** 对帳保单投保份数 */
	private Integer chSumQuantity;
	/** 对帳保单保额 */
	private Double chSumAmount;
	/** 对帳保单保费 */
	private Double chSumPremium;
	/** 手工对帳员代码 */
	private String operatorCode;
	/** 对帳标志0-未对帳1-对帳成功2-对帳失败 */
	private String checkFlag;
	/** 对帳失败说明 */
	private String failureFlag;
	/** 转收付标志 */
	private String transferFlag;
	/** 备注 */
	private String remark;
	/** 志位 */
	private String flag;

	/**
	 * 序号
	 */
	@Id
	@Column(name = "serialno")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_prpChannelChecking_serialNo")
	@SequenceGenerator(name = "s_prpChannelChecking_serialNo", allocationSize = 1, initialValue = 1, sequenceName = "s_prpChannelChecking_serialNo")
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 保单号
	 */

	@Column(name = "policyno")
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 单证流水号
	 */
	@Column(name = "printno")
	public String getPrintNo() {
		return printNo;
	}

	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	/**
	 * 投保总份数
	 */

	@Column(name = "sumquantity")
	public Integer getSumQuantity() {
		return this.sumQuantity;
	}

	public void setSumQuantity(Integer sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	/**
	 * 总保额
	 */

	@Column(name = "sumamount")
	public Double getSumAmount() {
		return this.sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 总保费
	 */

	@Column(name = "sumpremium")
	public Double getSumPremium() {
		return this.sumPremium;
	}

	public void setSumPremium(Double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 归属机构
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Column(name = "channelcode")
	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channelcomcode")
	public String getChannelComCode() {
		return this.channelComCode;
	}

	public void setChannelComCode(String channelComCode) {
		this.channelComCode = channelComCode;
	}

	/**
	 * 渠道交易日期(YYYY-MM-DD HH:mm:ss)
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "channeltradedate")
	public Date getChannelTradeDate() {
		return this.channelTradeDate;
	}

	public void setChannelTradeDate(Date channelTradeDate) {
		this.channelTradeDate = channelTradeDate;
	}

	/**
	 * 保单状态
	 */

	@Column(name = "policytype")
	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	@Column(name = "chcode")
	public String getChCode() {
		return chCode;
	}

	public void setChCode(String chCode) {
		this.chCode = chCode;
	}

	@Column(name = "chcomcode")
	public String getChComCode() {
		return chComCode;
	}

	public void setChComCode(String chComCode) {
		this.chComCode = chComCode;
	}

	@Column(name = "chtradecode")
	public String getChTradeCode() {
		return chTradeCode;
	}

	public void setChTradeCode(String chTradeCode) {
		this.chTradeCode = chTradeCode;
	}

	@Column(name = "chltradeserialno")
	public String getChlTradeSerialNo() {
		return chlTradeSerialNo;
	}

	public void setChlTradeSerialNo(String chlTradeSerialNo) {
		this.chlTradeSerialNo = chlTradeSerialNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "chtradedate")
	public Date getChTradeDate() {
		return chTradeDate;
	}

	public void setChTradeDate(Date chTradeDate) {
		this.chTradeDate = chTradeDate;
	}

	@Column(name = "chpolicyno")
	public String getChPolicyNo() {
		return chPolicyNo;
	}

	public void setChPolicyNo(String chPolicyNo) {
		this.chPolicyNo = chPolicyNo;
	}

	@Column(name = "chprintno")
	public String getChPrintNo() {
		return chPrintNo;
	}

	public void setChPrintNo(String chPrintNo) {
		this.chPrintNo = chPrintNo;
	}

	@Column(name = "chsumquantity")
	public Integer getChSumQuantity() {
		return chSumQuantity;
	}

	public void setChSumQuantity(Integer chSumQuantity) {
		this.chSumQuantity = chSumQuantity;
	}

	@Column(name = "chsumamount")
	public Double getChSumAmount() {
		return chSumAmount;
	}

	public void setChSumAmount(Double chSumAmount) {
		this.chSumAmount = chSumAmount;
	}

	@Column(name = "chsumpremium")
	public Double getChSumPremium() {
		return chSumPremium;
	}

	public void setChSumPremium(Double chSumPremium) {
		this.chSumPremium = chSumPremium;
	}

	@Column(name = "checkflag")
	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	@Column(name = "failureflag")
	public String getFailureFlag() {
		return failureFlag;
	}

	public void setFailureFlag(String failureFlag) {
		this.failureFlag = failureFlag;
	}

	@Column(name = "transferflag")
	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "operatorcode")
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	@Column(name = "sourcetype")
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
