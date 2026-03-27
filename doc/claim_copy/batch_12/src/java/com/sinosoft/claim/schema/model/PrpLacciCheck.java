package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * POJO类PrpLacciCheck意健险调查主表
 */
@Entity
@Table(name = "PRPLACCICHECK", uniqueConstraints = @UniqueConstraint(columnNames = { "REGISTNO", "TIMES" }))
public class PrpLacciCheck implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调查号 */
	private String checkNo;

	/** 属性报案号码 */
	private String registNo;

	/** 属性调查次数 */
	private Integer times;

	/** 属性发起节点 */
	private String certiType;

	/** 属性发起节点的业务号码 */
	private String certiNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性调查类型 */
	private String checkType;

	/** 属性调查内容简要描述 */
	private String checkContext;

	/** 属性调查对象 */
	private String checkObject;

	/** 属性调查对象描述 */
	private String checkObjectDesc;

	/** 属性调查方式 */
	private String checkNature;

	/** 属性调查起始日期 */
	private Date checkDate;

	/** 属性调查起始时间 */
	private String checkHour;

	/** 属性调查结束日期 */
	private Date checkEndDate;

	/** 属性调查结束时间 */
	private String checkEndHour;

	/** 属性调查地点 */
	private String checkSite;

	/** 属性事故原因代码 */
	private String damageCode;

	/** 属性事故原因说明 */
	private String damageName;

	/** 属性事故类型代码 */
	private String damageTypeCode;

	/** 属性事故类型说明 */
	private String damageTypeName;

	/** 属性调查人代码 */
	private String checkerCode;

	/** 属性审核人代码 */
	private String approverCode;

	/** 属性审核日期 */
	private Date approverDate;

	/** 属性审核状态 */
	private String approverStatus;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性调查费用币别 */
	private String currency;

	/** 属性调查费用 */
	private Double checkFee;
	/** 属性调查人的歸屬機構 */
	private String handleDept;

	/** 属性CASETYPE */
	private String caseType;

	/** 属性prpLacciCheckTexts */
	private List<PrpLacciCheckText> prpLacciCheckTexts = new ArrayList<PrpLacciCheckText>(0);

	/** 属性prpLacciCheckCharges */
	private List<PrpLacciCheckCharge> prpLacciCheckCharges = new ArrayList<PrpLacciCheckCharge>(0);
	/** 属性出险日期起 */
	private DateTime damageStartDate = new DateTime();
	/** 属性出险开始小时 */
	private String damageStartHour = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性调查开始分钟 */
	private String damageStartMinute2 = "";
	/** 属性调查结束分钟 */
	private String damageStartMinute3 = "";
	/** 事故地点 */
	private String damageAddress = "";

	// add by liuyanmei 20051209 start
	private String claimNo = "";
	private String compensateNo = "";
	private String claimStatus = "";
	private String currencyName = "";

	// add by liuyanmei 20051209 end
	/** 备注信息 */

	/**
	 * 类PrpLacciCheck的默认构造方法
	 */
	public PrpLacciCheck() {
	}

	/**
	 * 属性调查号的getter方法
	 */
	@Id
	@Column(name = "CHECKNO")
	public String getCheckNo() {
		return this.checkNo;
	}

	/**
	 * 属性调查号的setter方法
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
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
	 * 属性调查次数的getter方法
	 */

	@Column(name = "TIMES")
	public Integer getTimes() {
		return this.times;
	}

	/**
	 * 属性调查次数的setter方法
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}

	/**
	 * 属性发起节点的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性发起节点的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性发起节点的业务号码的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性发起节点的业务号码的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
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
	 * 属性调查类型的getter方法
	 */

	@Column(name = "CHECKTYPE")
	public String getCheckType() {
		return this.checkType;
	}

	/**
	 * 属性调查类型的setter方法
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * 属性调查内容简要描述的getter方法
	 */

	@Column(name = "CHECKCONTEXT")
	public String getCheckContext() {
		return this.checkContext;
	}

	/**
	 * 属性调查内容简要描述的setter方法
	 */
	public void setCheckContext(String checkContext) {
		this.checkContext = checkContext;
	}

	/**
	 * 属性调查对象的getter方法
	 */

	@Column(name = "CHECKOBJECT")
	public String getCheckObject() {
		return this.checkObject;
	}

	/**
	 * 属性调查对象的setter方法
	 */
	public void setCheckObject(String checkObject) {
		this.checkObject = checkObject;
	}

	/**
	 * 属性调查对象描述的getter方法
	 */

	@Column(name = "CHECKOBJECTDESC")
	public String getCheckObjectDesc() {
		return this.checkObjectDesc;
	}

	/**
	 * 属性调查对象描述的setter方法
	 */
	public void setCheckObjectDesc(String checkObjectDesc) {
		this.checkObjectDesc = checkObjectDesc;
	}

	/**
	 * 属性调查方式的getter方法
	 */

	@Column(name = "CHECKNATURE")
	public String getCheckNature() {
		return this.checkNature;
	}

	/**
	 * 属性调查方式的setter方法
	 */
	public void setCheckNature(String checkNature) {
		this.checkNature = checkNature;
	}

	/**
	 * 属性调查起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECKDATE")
	public Date getCheckDate() {
		return this.checkDate;
	}

	/**
	 * 属性调查起始日期的setter方法
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 属性调查起始时间的getter方法
	 */

	@Column(name = "CHECKHOUR")
	public String getCheckHour() {
		return this.checkHour;
	}

	/**
	 * 属性调查起始时间的setter方法
	 */
	public void setCheckHour(String checkHour) {
		this.checkHour = checkHour;
	}

	/**
	 * 属性调查结束日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECKENDDATE")
	public Date getCheckEndDate() {
		return this.checkEndDate;
	}

	/**
	 * 属性调查结束日期的setter方法
	 */
	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	/**
	 * 属性调查结束时间的getter方法
	 */

	@Column(name = "CHECKENDHOUR")
	public String getCheckEndHour() {
		return this.checkEndHour;
	}

	/**
	 * 属性调查结束时间的setter方法
	 */
	public void setCheckEndHour(String checkEndHour) {
		this.checkEndHour = checkEndHour;
	}

	/**
	 * 属性调查地点的getter方法
	 */

	@Column(name = "CHECKSITE")
	public String getCheckSite() {
		return this.checkSite;
	}

	/**
	 * 属性调查地点的setter方法
	 */
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}

	/**
	 * 属性事故原因代码的getter方法
	 */

	@Column(name = "DAMAGECODE")
	public String getDamageCode() {
		return this.damageCode;
	}

	/**
	 * 属性事故原因代码的setter方法
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * 属性事故原因说明的getter方法
	 */

	@Column(name = "DAMAGENAME")
	public String getDamageName() {
		return this.damageName;
	}

	/**
	 * 属性事故原因说明的setter方法
	 */
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	/**
	 * 属性事故类型代码的getter方法
	 */

	@Column(name = "DAMAGETYPECODE")
	public String getDamageTypeCode() {
		return this.damageTypeCode;
	}

	/**
	 * 属性事故类型代码的setter方法
	 */
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}

	/**
	 * 属性事故类型说明的getter方法
	 */

	@Column(name = "DAMAGETYPENAME")
	public String getDamageTypeName() {
		return this.damageTypeName;
	}

	/**
	 * 属性事故类型说明的setter方法
	 */
	public void setDamageTypeName(String damageTypeName) {
		this.damageTypeName = damageTypeName;
	}

	/**
	 * 属性调查人代码的getter方法
	 */

	@Column(name = "CHECKERCODE")
	public String getCheckerCode() {
		return this.checkerCode;
	}

	/**
	 * 属性调查人代码的setter方法
	 */
	public void setCheckerCode(String checkerCode) {
		this.checkerCode = checkerCode;
	}

	/**
	 * 属性审核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性审核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性审核日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVERDATE")
	public Date getApproverDate() {
		return this.approverDate;
	}

	/**
	 * 属性审核日期的setter方法
	 */
	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	/**
	 * 属性审核状态的getter方法
	 */

	@Column(name = "APPROVERSTATUS")
	public String getApproverStatus() {
		return this.approverStatus;
	}

	/**
	 * 属性审核状态的setter方法
	 */
	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
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
	 * 属性调查费用币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性调查费用币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性调查费用的getter方法
	 */

	@Column(name = "CHECKFEE")
	public Double getCheckFee() {
		return this.checkFee;
	}

	/**
	 * 属性调查费用的setter方法
	 */
	public void setCheckFee(Double checkFee) {
		this.checkFee = checkFee;
	}

	/**
	 * 属性CASETYPE的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性CASETYPE的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * 属性prpLacciCheckTexts的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLacciCheck")
	public List<PrpLacciCheckText> getPrpLacciCheckTexts() {
		return this.prpLacciCheckTexts;
	}

	/**
	 * 属性prpLacciCheckTexts的setter方法
	 */
	public void setPrpLacciCheckTexts(List<PrpLacciCheckText> prpLacciCheckTexts) {
		this.prpLacciCheckTexts = prpLacciCheckTexts;
	}

	/**
	 * 属性prpLacciCheckCharges的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLacciCheck")
	public List<PrpLacciCheckCharge> getPrpLacciCheckCharges() {
		return this.prpLacciCheckCharges;
	}

	/**
	 * 属性prpLacciCheckCharges的setter方法
	 */
	public void setPrpLacciCheckCharges(List<PrpLacciCheckCharge> prpLacciCheckCharges) {
		this.prpLacciCheckCharges = prpLacciCheckCharges;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Transient
	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	@Transient
	public String getClaimNo() {
		return this.claimNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	@Transient
	public String getCompensateNo() {
		return this.compensateNo;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	@Transient
	public String getClaimStatus() {
		return this.claimStatus;
	}

	// add by liuyanmei 20051209 end

	/**
	 * 设置事故地点
	 * @param damageAddress事故地点
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 获得事故地点
	 * @return 获得事故地点
	 */
	@Transient
	public String getDamageAddress() {
		return this.damageAddress;
	}

	/**
	 * 设置出险日期起
	 * @param damageStartDate 出险日期起
	 */
	public void setDamageStartDate(DateTime damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 得到出险日期起
	 * @return 出险日期起
	 */
	@Transient
	public DateTime getDamageStartDate() {
		return damageStartDate;
	}

	/**
	 * 设置出险开始小时
	 * @param damageStartHour 出险开始小时
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 得到出险开始小时
	 * @return 出险开始小时
	 */
	@Transient
	public String getDamageStartHour() {
		return damageStartHour;
	}

	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}

	@Transient
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	public void setDamageStartMinute2(String damageStartMinute2) {
		this.damageStartMinute2 = damageStartMinute2;
	}

	@Transient
	public String getDamageStartMinute2() {
		return damageStartMinute2;
	}

	public void setDamageStartMinute3(String damageStartMinute3) {
		this.damageStartMinute3 = damageStartMinute3;
	}

	@Transient
	public String getDamageStartMinute3() {
		return damageStartMinute3;
	}

	@Column(name = "HANDLEDEPT")
	public String getHandleDept() {
		return handleDept;
	}

	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}

}
