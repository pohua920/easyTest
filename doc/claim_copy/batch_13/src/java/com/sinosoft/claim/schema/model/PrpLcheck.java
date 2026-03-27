package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLcheck
 */
@Entity
@Table(name = "PRPLCHECK")
public class PrpLcheck implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcheckId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性调查类型 */
	private String checkType;

	/** 属性调查方式 */
	private String checkNature;

	/** 属性调查起始日期 */
	private Date checkDate;

	/** 属性调查地点 */
	private String checkSite;

	/** 属性是否第一现场 */
	private String firstSiteFlag;

	/** 属性案件类型 */
	private String claimType;

	/** 属性事故原因代码 */
	private String damageCode;

	/** 属性事故原因说明 */
	private String damageName;

	/** 属性事故原因代码 */
	private String damageCodeBZ;

	/** 属性事故原因说明 */
	private String damageNameBZ;

	/** 属性事故类型代码 */
	private String damageTypeCode;

	/** 属性事故类型说明 */
	private String damageTypeName;

	/** 属性事故所涉及险种 */
	private String referKind;

	/** 属性出险区域代码 */
	private String damageAreaCode;

	/** 属性出险地点分类 */
	private String damageAddressType;

	/** 属性赔偿责任代码 */
	private String indemnityDuty;

	/** 属性是否属於保险责任 */
	private String claimFlag;

	/** 属性查勘/代查勘人1 */
	private String checker1;

	/** 属性查勘/代查勘人2 */
	private String checker2;

	/** 属性查勘/代查勘单位名称 */
	private String checkUnitName;

	/** 属性事故处理部门 */
	private String handleUnit;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性是否为本保单车辆 */
	private String insureCarFlag;

	/** 属性是否向别的保险公司投保(Y/N) */
	private String repeatInsureFlag;

	/** 属性事故处理部门代码 */
	private String handleUnitCode;

	/** 属性单位类型 */
	private String unitType;

	/** 属性未决赔款准备金 */
	private Double estimateFee;

	/** 属性估损金额 */
	private Double estimateLoss;

	/** 属性出险日期 */
	private Date damageStartDate;

	/** 属性性出险小时 */
	private String damageStartHour;

	/** 属性性出险地点 */
	private String damageAddress;

	/** 属性查勘参与人 */
	private String checkLinker;

	/** 属性事故地点代码 */
	private String acciAddressCode;

	/** 属性事故地点名称 */
	private String acciAddressName;

	/** 属性北分快速处理 */
	private String dealFastFlag;

	/** 属性被保险人联系电话 */
	private String insuredPhoneNumber;

	/** 属性事故處理類型 */
	private String manageType;

	/** 属性事故處理類型名稱 */
	private String manageTypeName;

	/** 属性被保險人代碼 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性報案人 */
	private String reportorName;

	/** 属性報案人電話 */
	private String reportorPhoneNumber;

	/** 属性駕駛人 */
	private String linkerName;

	/** 属性駕駛人電話 */
	private String phoneNumber;

	/** 属性駕駛人手機 */
	private String driverMobile;

	/** 集合 **/
	List<PrpLcheck> checkList;
	/** 编辑类型 */
	private String editType = "";
	/** 属性条款类别 */
	private String clauseType = "";
	/** 属性条款名称 */
	private String clauseName = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性出险日期止 */
	private Date damageEndDate = new Date();
	/** 属性出险终止小时 */
	private String damageEndHour = "";
	/** 属性出险终止分钟 */
	private String damageEndMinute = "";
	/** 属性出险区域 */
	private String damageAreaName = "";
	/** 属性事故处理部门 */
	private String handleUnitName = "";
	/** 属性此查勘案件的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** 属性此查勘案件的操作时间 */
	private Date operateDate = new Date();
	/** 属性标的序号 */
	private String lossItemCode = "";
	/** 属性车牌号码 */
	private String lossItemName = "";
	/** 是否更新立案的估损金额 */
	private boolean isUpdateSumClaim = false;
	/** 报案列表 */
	List<PrpLregist> registList;
	/** 属性出险次数 */
	private int perilCount = 0;
	// 原因：增加报损金额和报损费用
	/** 报损金额 */
	private double registEstimateLoss = 0D;
	/** 报损费用 */
	private double registEstimateFee = 0D;
	// 增加共保标志
	private String coinsFlag = "";
	private String currency = "";// 增加币别代码显示
	// 关联保单
	// 强三查询
	private Collection<String> relatepolicyNo = null;

	private String checkNo = "";

	private String payselfFlag = "";
	/** 被保險人手機 */
	private String insuredMobile = "";
	/** 警方單位 */
	private String policeUnit = "";
	/** 警員姓名 */
	private String policeName;
	/** 属性出险地邮编代码 */
	private String addressCode = "";
	/** 属性出险地名称 */
	private String addressName = "";
	/** 同险号码 */
	private String sameAddressNo = "";
	/**
	 * 保险金额
	 */
	private Double sumAmount = 0D;

	/**
	 * 类PrpLcheck的默认构造方法
	 */
	public PrpLcheck() {
		this.id = new PrpLcheckId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "referSerialNo", column = @Column(name = "REFERSERIALNO")) })
	public PrpLcheckId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcheckId id) {
		this.id = id;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	@Temporal(TemporalType.TIMESTAMP)
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
	 * 属性是否第一现场的getter方法
	 */

	@Column(name = "FIRSTSITEFLAG")
	public String getFirstSiteFlag() {
		return this.firstSiteFlag;
	}

	/**
	 * 属性是否第一现场的setter方法
	 */
	public void setFirstSiteFlag(String firstSiteFlag) {
		this.firstSiteFlag = firstSiteFlag;
	}

	/**
	 * 属性案件类型的getter方法
	 */

	@Column(name = "CLAIMTYPE")
	public String getClaimType() {
		return this.claimType;
	}

	/**
	 * 属性案件类型的setter方法
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
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

	@Column(name = "DAMAGECODEBZ")
	public String getDamageCodeBZ() {
		return damageCodeBZ;
	}

	public void setDamageCodeBZ(String damageCodeBZ) {
		this.damageCodeBZ = damageCodeBZ;
	}

	@Column(name = "DAMAGENAMEBZ")
	public String getDamageNameBZ() {
		return damageNameBZ;
	}

	public void setDamageNameBZ(String damageNameBZ) {
		this.damageNameBZ = damageNameBZ;
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
	 * 属性事故所涉及险种的getter方法
	 */

	@Column(name = "REFERKIND")
	public String getReferKind() {
		return this.referKind;
	}

	/**
	 * 属性事故所涉及险种的setter方法
	 */
	public void setReferKind(String referKind) {
		this.referKind = referKind;
	}

	/**
	 * 属性出险区域代码的getter方法
	 */

	@Column(name = "DAMAGEAREACODE")
	public String getDamageAreaCode() {
		return this.damageAreaCode;
	}

	/**
	 * 属性出险区域代码的setter方法
	 */
	public void setDamageAreaCode(String damageAreaCode) {
		this.damageAreaCode = damageAreaCode;
	}

	/**
	 * 属性出险地点分类的getter方法
	 */

	@Column(name = "DAMAGEADDRESSTYPE")
	public String getDamageAddressType() {
		return this.damageAddressType;
	}

	/**
	 * 属性出险地点分类的setter方法
	 */
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	/**
	 * 属性赔偿责任代码的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性赔偿责任代码的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性是否属於保险责任的getter方法
	 */

	@Column(name = "CLAIMFLAG")
	public String getClaimFlag() {
		return this.claimFlag;
	}

	/**
	 * 属性是否属於保险责任的setter方法
	 */
	public void setClaimFlag(String claimFlag) {
		this.claimFlag = claimFlag;
	}

	/**
	 * 属性查勘/代查勘人1的getter方法
	 */

	@Column(name = "CHECKER1")
	public String getChecker1() {
		return this.checker1;
	}

	/**
	 * 属性查勘/代查勘人1的setter方法
	 */
	public void setChecker1(String checker1) {
		this.checker1 = checker1;
	}

	/**
	 * 属性查勘/代查勘人2的getter方法
	 */

	@Column(name = "CHECKER2")
	public String getChecker2() {
		return this.checker2;
	}

	/**
	 * 属性查勘/代查勘人2的setter方法
	 */
	public void setChecker2(String checker2) {
		this.checker2 = checker2;
	}

	/**
	 * 属性查勘/代查勘单位名称的getter方法
	 */

	@Column(name = "CHECKUNITNAME")
	public String getCheckUnitName() {
		return this.checkUnitName;
	}

	/**
	 * 属性查勘/代查勘单位名称的setter方法
	 */
	public void setCheckUnitName(String checkUnitName) {
		this.checkUnitName = checkUnitName;
	}

	/**
	 * 属性事故处理部门的getter方法
	 */

	@Column(name = "HANDLEUNIT")
	public String getHandleUnit() {
		return this.handleUnit;
	}

	/**
	 * 属性事故处理部门的setter方法
	 */
	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
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
	 * 属性是否为本保单车辆的getter方法
	 */

	@Column(name = "INSURECARFLAG")
	public String getInsureCarFlag() {
		return this.insureCarFlag;
	}

	/**
	 * 属性是否为本保单车辆的setter方法
	 */
	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	/**
	 * 属性是否向别的保险公司投保(Y/N)的getter方法
	 */

	@Column(name = "REPEATINSUREFLAG")
	public String getRepeatInsureFlag() {
		return this.repeatInsureFlag;
	}

	/**
	 * 属性是否向别的保险公司投保(Y/N)的setter方法
	 */
	public void setRepeatInsureFlag(String repeatInsureFlag) {
		this.repeatInsureFlag = repeatInsureFlag;
	}

	/**
	 * 属性事故处理部门代码的getter方法
	 */

	@Column(name = "HANDLEUNITCODE")
	public String getHandleUnitCode() {
		return this.handleUnitCode;
	}

	/**
	 * 属性事故处理部门代码的setter方法
	 */
	public void setHandleUnitCode(String handleUnitCode) {
		this.handleUnitCode = handleUnitCode;
	}

	/**
	 * 属性单位类型的getter方法
	 */

	@Column(name = "UNITTYPE")
	public String getUnitType() {
		return this.unitType;
	}

	/**
	 * 属性单位类型的setter方法
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	/**
	 * 属性未决赔款准备金的getter方法
	 */

	@Column(name = "ESTIMATEFEE")
	public Double getEstimateFee() {
		return this.estimateFee;
	}

	/**
	 * 属性未决赔款准备金的setter方法
	 */
	public void setEstimateFee(Double estimateFee) {
		this.estimateFee = estimateFee;
	}

	/**
	 * 属性估损金额的getter方法
	 */

	@Column(name = "ESTIMATELOSS")
	public Double getEstimateLoss() {
		return this.estimateLoss;
	}

	/**
	 * 属性估损金额的setter方法
	 */
	public void setEstimateLoss(Double estimateLoss) {
		this.estimateLoss = estimateLoss;
	}

	/**
	 * 属性出险日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGESTARTDATE")
	public Date getDamageStartDate() {
		return this.damageStartDate;
	}

	/**
	 * 属性性出险小时的getter方法
	 */

	@Column(name = "DAMAGESTARTHOUR")
	public String getDamageStartHour() {
		return this.damageStartHour;
	}

	/**
	 * 属性性出险小时的setter方法
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 属性性出险地点的getter方法
	 */

	@Column(name = "DAMAGEADDRESS")
	public String getDamageAddress() {
		return this.damageAddress;
	}

	/**
	 * 属性性出险地点的setter方法
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 属性查勘参与人的getter方法
	 */

	@Column(name = "CHECKLINKER")
	public String getCheckLinker() {
		return this.checkLinker;
	}

	/**
	 * 属性查勘参与人的setter方法
	 */
	public void setCheckLinker(String checkLinker) {
		this.checkLinker = checkLinker;
	}

	/**
	 * 属性事故地点代码的getter方法
	 */

	@Column(name = "ACCIADDRESSCODE")
	public String getAcciAddressCode() {
		return this.acciAddressCode;
	}

	/**
	 * 属性事故地点代码的setter方法
	 */
	public void setAcciAddressCode(String acciAddressCode) {
		this.acciAddressCode = acciAddressCode;
	}

	/**
	 * 属性事故地点名称的getter方法
	 */

	@Column(name = "ACCIADDRESSNAME")
	public String getAcciAddressName() {
		return this.acciAddressName;
	}

	/**
	 * 属性事故地点名称的setter方法
	 */
	public void setAcciAddressName(String acciAddressName) {
		this.acciAddressName = acciAddressName;
	}

	/**
	 * 属性北分快速处理的getter方法
	 */

	@Column(name = "DEALFASTFLAG")
	public String getDealFastFlag() {
		return this.dealFastFlag;
	}

	/**
	 * 属性北分快速处理的setter方法
	 */
	public void setDealFastFlag(String dealFastFlag) {
		this.dealFastFlag = dealFastFlag;
	}

	/**
	 * 属性被保险人联系电话的getter方法
	 */

	@Column(name = "INSUREDPHONENUMBER")
	public String getInsuredPhoneNumber() {
		return this.insuredPhoneNumber;
	}

	/**
	 * 属性被保险人联系电话的setter方法
	 */
	public void setInsuredPhoneNumber(String insuredPhoneNumber) {
		this.insuredPhoneNumber = insuredPhoneNumber;
	}

	/**
	 * 属性事故處理類型的getter方法
	 */

	@Column(name = "MANAGETYPE")
	public String getManageType() {
		return this.manageType;
	}

	/**
	 * 属性事故處理類型的setter方法
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	/**
	 * 属性事故處理類型名稱的getter方法
	 */

	@Column(name = "MANAGETYPENAME")
	public String getManageTypeName() {
		return this.manageTypeName;
	}

	/**
	 * 属性事故處理類型名稱的setter方法
	 */
	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}

	/**
	 * 属性被保險人代碼的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保險人代碼的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性報案人的getter方法
	 */

	@Column(name = "REPORTORNAME")
	public String getReportorName() {
		return this.reportorName;
	}

	/**
	 * 属性報案人的setter方法
	 */
	public void setReportorName(String reportorName) {
		this.reportorName = reportorName;
	}

	/**
	 * 属性報案人電話的getter方法
	 */

	@Column(name = "REPORTORPHONENUMBER")
	public String getReportorPhoneNumber() {
		return this.reportorPhoneNumber;
	}

	/**
	 * 属性報案人電話的setter方法
	 */
	public void setReportorPhoneNumber(String reportorPhoneNumber) {
		this.reportorPhoneNumber = reportorPhoneNumber;
	}

	/**
	 * 属性駕駛人的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性駕駛人的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**
	 * 属性駕駛人電話的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性駕駛人電話的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Transient
	public Collection<String> getRelatepolicyNo() {
		return relatepolicyNo;
	}

	public void setRelatepolicyNo(Collection<String> relatepolicyNo) {
		this.relatepolicyNo = relatepolicyNo;
	}

	// add by zhouliu end at 2006-6-9

	/**
	 * 得到事故处理部门
	 * @return 事故处理部门
	 */
	@Transient
	public String getHandleUnitName() {
		return handleUnitName;
	}

	/**
	 * 得到出险日期止
	 * @return 出险日期止
	 */
	@Transient
	public Date getDamageEndDate() {
		return damageEndDate;
	}

	/**
	 * 得到条款名称
	 * @return 条款名称
	 */
	@Transient
	public String getClauseName() {
		return clauseName;
	}

	/**
	 * 得到查勘集合
	 * @return 查勘集合信息
	 */
	@Transient
	public List<PrpLcheck> getCheckList() {
		return checkList;
	}

	/**
	 * 设置查勘集合
	 * @param checkList 查勘集合
	 */
	public void setCheckList(List<PrpLcheck> checkList) {
		this.checkList = checkList;
	}

	/**
	 * 得到条款类别
	 * @return 条款类别
	 */
	@Transient
	public String getClauseType() {
		return clauseType;
	}

	/**
	 * 得到出险区域
	 * @return 出险区域
	 */
	@Transient
	public String getDamageAreaName() {
		return damageAreaName;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 设置事故处理部门
	 * @param handleUnitName 事故处理部门
	 */
	public void setHandleUnitName(String handleUnitName) {
		this.handleUnitName = handleUnitName;
	}

	/**
	 * 设置出险日期止
	 * @param damageEndDate 出险日期止
	 */
	public void setDamageEndDate(Date damageEndDate) {
		this.damageEndDate = damageEndDate;
	}

	/**
	 * 设置条款名称
	 * @param clauseName 条款名称
	 */
	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}

	/**
	 * 设置出险终止小时
	 * @param damageEndHour 出险终止小时
	 */
	public void setDamageEndHour(String damageEndHour) {
		this.damageEndHour = damageEndHour;
	}

	/**
	 * 得到出险终止小时
	 * @return 出险终止小时
	 */
	@Transient
	public String getDamageEndHour() {
		return damageEndHour;
	}

	/**
	 * 设置出险日期起
	 * @param damageStartDate 出险日期起
	 */
	public void setDamageStartDate(Date damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 设置条款类别
	 * @param clauseType 条款类别
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	/**
	 * 设置查勘报告
	 * @param prpLregistTextDtoList 查勘报告
	 */
	public void setDamageAreaName(String damageAreaName) {
		this.damageAreaName = damageAreaName;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态 Modify By Sunhao,2004-08-24
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置属性操作时间
	 * @param operateDate 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public void setDamageEndMinute(String damageEndMinute) {
		this.damageEndMinute = damageEndMinute;
	}

	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}

	/**
	 * 获取属性操作时间名称
	 * @param status 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public Date getOperateDate() {
		return operateDate;
	}

	@Transient
	public String getDamageEndMinute() {
		return damageEndMinute;
	}

	@Transient
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	/**
	 * 设置属性出险次数
	 * @param perilCount 待设置的属性出险次数的值
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	/**
	 * 获取属性出险次数
	 * @return 属性出险次数的值
	 */
	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public List<PrpLregist> getRegistList() {
		return registList;
	}

	/**
	 * 设置列表
	 * @param registList 待设置的列表
	 */
	public void setRegistList(List<PrpLregist> registList) {
		this.registList = registList;
	}

	/**
	 * 设置属性标的序号
	 * @param lossItemCode 待设置的属性标的序号的值
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = StringUtils.rightTrim(lossItemCode);
	}

	/**
	 * 获取属性标的序号
	 * @return 属性标的序号的值
	 */
	@Transient
	public String getLossItemCode() {
		return lossItemCode;
	}

	/**
	 * 设置属性车牌号码
	 * @param lossItemName 待设置的属性车牌号码的值
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = StringUtils.rightTrim(lossItemName);
	}

	/**
	 * 获取属性车牌号码
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getLossItemName() {
		return lossItemName;
	}

	public void setIsUpdateSumClaim(boolean isUpdateSumClaim) {
		this.isUpdateSumClaim = isUpdateSumClaim;
	}

	@Transient
	public boolean isIsUpdateSumClaim() {
		return isUpdateSumClaim;
	}

	// 原因：增加报损金额和报损费用
	/**
	 * 获取属性报损金额
	 * @return报损金额
	 */
	@Transient
	public double getRegistEstimateLoss() {
		return this.registEstimateLoss;
	}

	/**
	 * 设置属性报损金额
	 * @param registEstmateLoss 报损金额
	 */
	public void setRegistEstimateLoss(double registEstmateLoss) {
		this.registEstimateLoss = registEstmateLoss;
	}

	/**
	 * 获取属性报损费用
	 * @return 报损费用
	 */
	@Transient
	public double getRegistEstimateFee() {
		return this.registEstimateFee;
	}

	/**
	 * 设置属性报损费用
	 * @param registEstmateFee 报损费用
	 */
	public void setRegistEstimateFee(double registEstimateFee) {
		this.registEstimateFee = registEstimateFee;
	}

	/**
	 * 获取属性报损费用
	 * @return 报损费用
	 */
	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 设置属性报损费用
	 * @param registEstmateFee 报损费用
	 */
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * add by wuxiaodong 050905 begain 设置属性币别
	 * @return String
	 */
	@Transient
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 设置属性币别
	 * @param currency 属性币别
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	// add by wuxiaodong 050905 end

	/**
	 * @return Returns the insuredName.
	 */

	@Transient
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	@Column(name = "PAYSELFFLAG")
	public String getPayselfFlag() {
		return payselfFlag;
	}

	public void setPayselfFlag(String payselfFlag) {
		this.payselfFlag = payselfFlag;
	}

	@Column(name = "INSUREDMOBILE")
	public String getInsuredMobile() {
		return insuredMobile;
	}

	public void setInsuredMobile(String insuredMobile) {
		this.insuredMobile = insuredMobile;
	}

	@Column(name = "DRIVERMOBILE")
	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	@Column(name = "POLICEUNIT")
	public String getPoliceUnit() {
		return policeUnit;
	}

	public void setPoliceUnit(String policeUnit) {
		this.policeUnit = policeUnit;
	}

	@Column(name = "POLICENAME")
	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	@Column(name = "ADDRESSCODE")
	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	@Transient
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	@Transient
	public String getSameAddressNo() {
		return sameAddressNo;
	}

	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}
	@Transient
	public Double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

}
