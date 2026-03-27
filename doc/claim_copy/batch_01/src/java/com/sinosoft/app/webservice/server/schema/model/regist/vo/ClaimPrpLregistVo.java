package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 *  回傳CWP
 */
@XmlRootElement
public class ClaimPrpLregistVo {
	//public String  FORMAT_JSON_SERIALIZE1   = new String("yyyy-MM-dd");
	//public String  FORMAT_JSON_SERIALIZE2   = new String("yyyy-MM-dd HH:mm:ss");
	
	/** 属性报案号 */
	private String registNo = "";

	/** 属性prpLthirdParties */
	private List<ClaimPrpLthirdPartyVo> prpLthirdParties = new ArrayList<ClaimPrpLthirdPartyVo>(0);

	/** 属性prpLdrivers */
	private List<ClaimPrpLdriverVo> prpLdrivers = new ArrayList<ClaimPrpLdriverVo>(0);
	
	/** 属性理赔类型 */
	private String lflag = "";

	/** 属性险类代码 */
	private String classCode = "";

	/** 属性险种代码 */
	private String riskCode = "";

	/** 属性保单号 */
	private String policyNo = "";

	/** 属性语种标志 */
	private String language = "";

	/** 属性被保险人代码 */
	private String insuredCode = "";

	/** 属性被保险人名称 */
	private String insuredName = "";

	/** 属性被保险人地址 */
	private String insuredAddress = "";

	/** 属性条款类别 */
	private String clauseType = "";

	/** 属性车牌号 */
	private String licenseNo = "";

	/** 属性车牌底色代码 */
	private String licenseColorCode = "";

	/** 属性车辆种类代码 */
	private String carKindCode = "";

	/** 属性车型代码 */
	private String modelCode = "";

	/** 属性厂牌型号 */
	private String brandName = "";

	/** 属性发动机号 */
	private String engineNo = "";

	/** 属性车架号 */
	private String frameNo = "";

	/** 属性车辆已行驶公里数 */
	private Double runDistance;

	/** 属性车辆实际使用年限 */
	private int useYears;

	/** 属性报案日期 */
	private String reportDate;

	/** 属性报案小时 */
	private String reportHour = "";

	/** 属性报案地点 */
	private String reportAddress = "";

	/** 属性报案人 */
	private String reportorName = "";

	/** 属性报案形式 */
	private String reportType = "";

	/** 属性报案人联系电话 */
	private String phoneNumber = "";

	/** 属性联系人 */
	private String linkerName = "";

	/** 属性出险日期起 */
	private String damageStartDate;

	/** 属性出险开始小时 */
	private String damageStartHour = "";

	/** 属性出险日期止 */
	private String damageEndDate;

	/** 属性出险终止小时 */
	private String damageEndHour = "";

	/** 属性任意险出险原因代码 */
	private String damageCode = "";

	/** 属性任意险出险原因名称 */
	private String damageName = "";

	/** 属性事故类型代码 */
	private String damageTypeCode = "";

	/** 属性事故类型说明 */
	private String damageTypeName = "";

	/** 属性是否第一现场 */
	private String firstSiteFlag = "";

	/** 属性出险区域代码 */
	private String damageAreaCode = "";

	/** 属性出险区域名称 */
	private String damageAreaName = "";

	/** 属性出险地点分类代码 */
	private String damageAddressType = "";

	/** 属性出险地代码 */
	private String addressCode = "";

	/** 属性出险地点 */
	private String damageAddress = "";

	/** 属性出险地点邮政编码 */
	private String damageAreaPostCode = "";

	/** 属性事故处理部门 */
	private String handleUnit = "";

	/** 属性受损标的 */
	private String lossName = "";

	/** 属性受损标的数量/出险分户数 */
	private Double lossQuantity;

	/** 属性数量单位 */
	private String unit = "";

	/** 属性估损币别 */
	private String estiCurrency = "";

	/** 属性估损金额 */
	private Double estimateLoss;

	/** 属性接案员姓名 */
	private String receiverName = "";

	/** 属性经办人代码 */
	private String handlerCode = "";

	/** 属性归属业务员代码 */
	private String handler1Code = "";

	/** 属性业务归属机构代码 */
	private String comCode = "";

	/** 属性计算机输单日期 */
	private String inputDate;

	/** 属性受理标志(Y/N) */
	private String acceptFlag = "";

	/** 属性是否向别的保险公司投保 */
	private String repeatInsureFlag = "";

	/** 属性赔案类别 */
	private String claimType = "";

	/** 属性注销/拒赔日期 */
	private String cancelDate;

	/** 属性注销/拒赔人代码 */
	private String dealerCode = "";

	/** 属性备注 */
	private String remark = "";

	/** 属性操作员代码 */
	private String operatorCode = "";

	/** 属性出单机构 */
	private String makeCom = "";

	/** 属性状态字段 */
	private String flag = "";

	/** 属性报案人电话 */
	private String reportorPhoneNumber = "";

	/** 属性联系人邮编 */
	private String linkerPostCode = "";

	/** 属性联系人通讯地址 */
	private String linkerAddress = "";

	/** 属性未决赔款准备金 */
	private Double estimateFee;

	/** 属性巨灾一级代码 */
	private String catastropheCode1 = "";

	/** 属性巨灾一级名称 */
	private String catastropheName1 = "";

	/** 属性巨灾二级代码 */
	private String catastropheCode2 = "";

	/** 属性巨灾二级名称 */
	private String catastropheName2 = "";

	/** 属性报案标志 */
	private String reportFlag = "";

	/** 属性赔偿责任代码 */
	private String indemnityDuty = "";

	/** 属性简易赔案标记 */
	private String claimTypeFlag = "";

	/** 属性事故处理类型代码 */
	private String manageType = "";

	/** 属性事故处理类型名称 */
	private String manageTypeName = "";

	/** 属性天气代码 */
	private String weather = "";

	/** 属性天气名称 */
	private String weatherName = "";

	/** 属性事故管制代码 */
	private String section = "";

	/** 属性事故管制名称 */
	private String sectionName = "";

	/** 属性报案人与被保险人关系代码 */
	private String relationType = "";

	/** 属性垫付赔案类型 */
	private String advanceType = "";

	/** 属性是否免导团单标志 */
	private String termFlag = "";

	/** 属性最新报案修改人名称 */
	private String alterName = "";

	/** 属性最新报案修改人联系电话 */
	private String alterPhoneNumber = "";

	/** 属性最新报案修改人与被保险人关系 */
	private String alterRelationType = "";

	/** 属性最新报案修改时间 */
	private String alterTime;

	/** 属性报案修改轨迹 */
	private String alterLocus = "";

	/** 属性报案修改方式 */
	private String alterType = "";

	/** 互碰自赔标志 0:非互碰自赔;1:是互碰自赔 */
	private String payselfFlag = "1";

	/** 属性三者车牌号 */
	private String thirdLicenseNo = "";

	/** 属性是否人伤 1:是 0：否 */
	private String personLossFlag = "0";

	/** 属性是否物损 1:是 0：否 */
	private String propLossFlag = "0";

	/** 属性报案类型 0：商业险单独报案 ，1：交强险单独报案，2：商业、交强险关联报案 */
	private String registType = "";

	/** 属性被保险人电话 */
	private String insuredPhoneNumber = "";

	/** 属性是否發簡訊 */
	private String sendMesFlag = "";

	/** 属性经办人名称 */
	private String handlerName = "";
	/** 属性经办人名称 */
	private String handler1Name = "";
	/** 属性部门名称 */
	private String comName = "";
	/** 属性起保日期 */
	private String startDate = "";
	/** 属性操作员名称 */
	private String operatorName = "";
	/** 属性理赔登记机构 */
	private String makeComName = "";
	/** 属性终保日期 */
	private String endDate = "";
	/** 属性条款名称 */
	private String clauseName = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性处理单位名称 */
	private String handleUnitName = "";
	/** 属性接案人代码 */
	private String receiverCode = "";
	/** 属性事故所涉及险种 */
	private String referKind = "";
	/** 编辑类型 */
	private String editType = "";
	/** 属性报案分钟 */
	private String reportMinute = "";
	/** 交费情况 */
	private String payFlag = "";
	/** 单号 */
	private String certiNo = "";
	/** 单号类型 */
	private String certiType = "";
	/** 第三者亡人数 */
	private long personDeathB = 0;
	/** 第三者伤人数 */
	private long personInjureB = 0;
	/** 车上人员亡人数 */
	private long personDeathD1 = 0;
	/** 车上人员伤人数 */
	private long personInjureD1 = 0;
	/** 车上人员伤人数 */
	private String lextValue1 = "";
	/** 车上人员伤人数 */
	private String lextValue2 = "";

	/** 属性此报案的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** 币别的名称 */
	private String estiCurrencyName = "";

	/** 被保险人类别 */
	private String customerType = "";

	/** 属性此报案的操作时间 */
	private String operateDate = "";

	/** 属性出险次数 */
	private int perilCount = 0;
	/** 属性最近N天出险次数 */
	private int recentCount = 0;
	/** 属性流程编号 */
	private String flowID = "";

	/** 排列记录的编号 */
	private int serialNo = 0;

	/** 调度标的的详细内容 */
	private String scheduleItemNote = "";

	/** 出险原因代码 */
	private String prpLregistDamageCode = "";

	/** 事故原因代码 */
	private String prpLregistDamageTypeCode = "";

	/** 属性标的序号 */
	private String lossItemCode = "";
	/** 列表 */
	private List<?> registList;
	/** 保险金额 */
	private Double sumAmount = 0D;

	/** 共保信息 */
	private String coinsFlag = "";

	/** 属性代理人代码 */
	private String agentCode = "";

	/** 属性代理人名称 */
	private String agentName = "";
	/** 流入系统时间 */
	private String flowInTime = "";
	private String signDate = "";
	private String underWriteEndDate = "";
	private String othFlag = "";
	/** 属性被保险人显示名称 */
	private String insuredNameShow = "";
	private int startHour = 0;
	private int endHour = 0;
//	private CompensateFeeDto compensateFeeDto;
	// 相应的工作流ID
	private String workFlowId;
//	// 增加字段关联保单
//	private Collection<String> relatepolicyNo = null;
	// 增加95519报案服务单号
	private String serviceNo = "";
	// 增加该报案是否允许修改标志，第1位 案件狀態，第2位可修改狀態
	// 00-已註銷，不可修改；01-未立案，可修改；10：已立案，不可修改；11-已立案，可修改；20-已結案，不可修改；21-已結案，可修改
	private String modifyFlag = "";
	// 保单里的被保险人电话
	private String policyInsuredPhoneNumber = "";

	/** 属性报案人手机 */
	private String reportorMobile = "";

	/** 出险车辆驾驶人手机 */
	private String driverMobile = "";

	/** 属性强制险出险原因代码 */
	private String damageCodeBZ = "";

	/** 属性强制险出险原因名称 */
	private String damageNameBZ = "";

	/** 被保险人手机 */
	private String policyInsuredMobile = "";

	/** 被保险人驾照 */
	private String policyInsuredLicenseNumber = "";

	/** 共摊标志 */
	private String sharingFlag = "0";

	/** 出险地点邮编名称 */
	private String addressName = "";
	/** 憲警單位 */
	private String authorityUnit = "";
	/** 船名 */
	private String shipCName = "";
	/** 機型 */
	private String shipModel = "";
	/** 理賠代號 */
	private String claimAgent = "";
	/** 地區別代號 */
	private String areaCode = "";
	/** 同险号码 */
	private String sameAddressNo = "";
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public List<ClaimPrpLthirdPartyVo> getPrpLthirdParties() {
		return prpLthirdParties;
	}
	public void setPrpLthirdParties(List<ClaimPrpLthirdPartyVo> prpLthirdParties) {
		this.prpLthirdParties = prpLthirdParties;
	}
	public List<ClaimPrpLdriverVo> getPrpLdrivers() {
		return prpLdrivers;
	}
	public void setPrpLdrivers(List<ClaimPrpLdriverVo> prpLdrivers) {
		this.prpLdrivers = prpLdrivers;
	}
	public String getLflag() {
		return lflag;
	}
	public void setLflag(String lflag) {
		this.lflag = lflag;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredAddress() {
		return insuredAddress;
	}
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	public String getClauseType() {
		return clauseType;
	}
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getLicenseColorCode() {
		return licenseColorCode;
	}
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}
	public String getCarKindCode() {
		return carKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}
	public Double getRunDistance() {
		return runDistance;
	}
	public void setRunDistance(Double runDistance) {
		this.runDistance = runDistance;
	}
	public int getUseYears() {
		return useYears;
	}
	public void setUseYears(int useYears) {
		this.useYears = useYears;
	}
	public String getReportHour() {
		return reportHour;
	}
	public void setReportHour(String reportHour) {
		this.reportHour = reportHour;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}
	public String getReportorName() {
		return reportorName;
	}
	public void setReportorName(String reportorName) {
		this.reportorName = reportorName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getDamageStartHour() {
		return damageStartHour;
	}
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}
	public String getDamageEndHour() {
		return damageEndHour;
	}
	public void setDamageEndHour(String damageEndHour) {
		this.damageEndHour = damageEndHour;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getDamageName() {
		return damageName;
	}
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}
	public String getDamageTypeCode() {
		return damageTypeCode;
	}
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}
	public String getDamageTypeName() {
		return damageTypeName;
	}
	public void setDamageTypeName(String damageTypeName) {
		this.damageTypeName = damageTypeName;
	}
	public String getFirstSiteFlag() {
		return firstSiteFlag;
	}
	public void setFirstSiteFlag(String firstSiteFlag) {
		this.firstSiteFlag = firstSiteFlag;
	}
	public String getDamageAreaCode() {
		return damageAreaCode;
	}
	public void setDamageAreaCode(String damageAreaCode) {
		this.damageAreaCode = damageAreaCode;
	}
	public String getDamageAreaName() {
		return damageAreaName;
	}
	public void setDamageAreaName(String damageAreaName) {
		this.damageAreaName = damageAreaName;
	}
	public String getDamageAddressType() {
		return damageAddressType;
	}
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageAreaPostCode() {
		return damageAreaPostCode;
	}
	public void setDamageAreaPostCode(String damageAreaPostCode) {
		this.damageAreaPostCode = damageAreaPostCode;
	}
	public String getHandleUnit() {
		return handleUnit;
	}
	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}
	public String getLossName() {
		return lossName;
	}
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}
	public Double getLossQuantity() {
		return lossQuantity;
	}
	public void setLossQuantity(Double lossQuantity) {
		this.lossQuantity = lossQuantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getEstiCurrency() {
		return estiCurrency;
	}
	public void setEstiCurrency(String estiCurrency) {
		this.estiCurrency = estiCurrency;
	}
	public Double getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(Double estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getHandlerCode() {
		return handlerCode;
	}
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	public String getHandler1Code() {
		return handler1Code;
	}
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getAcceptFlag() {
		return acceptFlag;
	}
	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}
	public String getRepeatInsureFlag() {
		return repeatInsureFlag;
	}
	public void setRepeatInsureFlag(String repeatInsureFlag) {
		this.repeatInsureFlag = repeatInsureFlag;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getMakeCom() {
		return makeCom;
	}
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getReportorPhoneNumber() {
		return reportorPhoneNumber;
	}
	public void setReportorPhoneNumber(String reportorPhoneNumber) {
		this.reportorPhoneNumber = reportorPhoneNumber;
	}
	public String getLinkerPostCode() {
		return linkerPostCode;
	}
	public void setLinkerPostCode(String linkerPostCode) {
		this.linkerPostCode = linkerPostCode;
	}
	public String getLinkerAddress() {
		return linkerAddress;
	}
	public void setLinkerAddress(String linkerAddress) {
		this.linkerAddress = linkerAddress;
	}
	public Double getEstimateFee() {
		return estimateFee;
	}
	public void setEstimateFee(Double estimateFee) {
		this.estimateFee = estimateFee;
	}
	public String getCatastropheCode1() {
		return catastropheCode1;
	}
	public void setCatastropheCode1(String catastropheCode1) {
		this.catastropheCode1 = catastropheCode1;
	}
	public String getCatastropheName1() {
		return catastropheName1;
	}
	public void setCatastropheName1(String catastropheName1) {
		this.catastropheName1 = catastropheName1;
	}
	public String getCatastropheCode2() {
		return catastropheCode2;
	}
	public void setCatastropheCode2(String catastropheCode2) {
		this.catastropheCode2 = catastropheCode2;
	}
	public String getCatastropheName2() {
		return catastropheName2;
	}
	public void setCatastropheName2(String catastropheName2) {
		this.catastropheName2 = catastropheName2;
	}
	public String getReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}
	public String getIndemnityDuty() {
		return indemnityDuty;
	}
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}
	public String getClaimTypeFlag() {
		return claimTypeFlag;
	}
	public void setClaimTypeFlag(String claimTypeFlag) {
		this.claimTypeFlag = claimTypeFlag;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	public String getManageTypeName() {
		return manageTypeName;
	}
	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeatherName() {
		return weatherName;
	}
	public void setWeatherName(String weatherName) {
		this.weatherName = weatherName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getAdvanceType() {
		return advanceType;
	}
	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}
	public String getTermFlag() {
		return termFlag;
	}
	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}
	public String getAlterName() {
		return alterName;
	}
	public void setAlterName(String alterName) {
		this.alterName = alterName;
	}
	public String getAlterPhoneNumber() {
		return alterPhoneNumber;
	}
	public void setAlterPhoneNumber(String alterPhoneNumber) {
		this.alterPhoneNumber = alterPhoneNumber;
	}
	public String getAlterRelationType() {
		return alterRelationType;
	}
	public void setAlterRelationType(String alterRelationType) {
		this.alterRelationType = alterRelationType;
	}
	public String getAlterLocus() {
		return alterLocus;
	}
	public void setAlterLocus(String alterLocus) {
		this.alterLocus = alterLocus;
	}
	public String getAlterType() {
		return alterType;
	}
	public void setAlterType(String alterType) {
		this.alterType = alterType;
	}
	public String getPayselfFlag() {
		return payselfFlag;
	}
	public void setPayselfFlag(String payselfFlag) {
		this.payselfFlag = payselfFlag;
	}
	public String getThirdLicenseNo() {
		return thirdLicenseNo;
	}
	public void setThirdLicenseNo(String thirdLicenseNo) {
		this.thirdLicenseNo = thirdLicenseNo;
	}
	public String getPersonLossFlag() {
		return personLossFlag;
	}
	public void setPersonLossFlag(String personLossFlag) {
		this.personLossFlag = personLossFlag;
	}
	public String getPropLossFlag() {
		return propLossFlag;
	}
	public void setPropLossFlag(String propLossFlag) {
		this.propLossFlag = propLossFlag;
	}
	public String getRegistType() {
		return registType;
	}
	public void setRegistType(String registType) {
		this.registType = registType;
	}
	public String getInsuredPhoneNumber() {
		return insuredPhoneNumber;
	}
	public void setInsuredPhoneNumber(String insuredPhoneNumber) {
		this.insuredPhoneNumber = insuredPhoneNumber;
	}
	public String getSendMesFlag() {
		return sendMesFlag;
	}
	public void setSendMesFlag(String sendMesFlag) {
		this.sendMesFlag = sendMesFlag;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public String getHandler1Name() {
		return handler1Name;
	}
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getMakeComName() {
		return makeComName;
	}
	public void setMakeComName(String makeComName) {
		this.makeComName = makeComName;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getClauseName() {
		return clauseName;
	}
	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}
	public String getDamageStartMinute() {
		return damageStartMinute;
	}
	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}
	public String getHandleUnitName() {
		return handleUnitName;
	}
	public void setHandleUnitName(String handleUnitName) {
		this.handleUnitName = handleUnitName;
	}
	public String getReceiverCode() {
		return receiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	public String getReferKind() {
		return referKind;
	}
	public void setReferKind(String referKind) {
		this.referKind = referKind;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getReportMinute() {
		return reportMinute;
	}
	public void setReportMinute(String reportMinute) {
		this.reportMinute = reportMinute;
	}
	public String getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	public String getCertiNo() {
		return certiNo;
	}
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}
	public String getCertiType() {
		return certiType;
	}
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}
	public long getPersonDeathB() {
		return personDeathB;
	}
	public void setPersonDeathB(long personDeathB) {
		this.personDeathB = personDeathB;
	}
	public long getPersonInjureB() {
		return personInjureB;
	}
	public void setPersonInjureB(long personInjureB) {
		this.personInjureB = personInjureB;
	}
	public long getPersonDeathD1() {
		return personDeathD1;
	}
	public void setPersonDeathD1(long personDeathD1) {
		this.personDeathD1 = personDeathD1;
	}
	public long getPersonInjureD1() {
		return personInjureD1;
	}
	public void setPersonInjureD1(long personInjureD1) {
		this.personInjureD1 = personInjureD1;
	}
	public String getLextValue1() {
		return lextValue1;
	}
	public void setLextValue1(String lextValue1) {
		this.lextValue1 = lextValue1;
	}
	public String getLextValue2() {
		return lextValue2;
	}
	public void setLextValue2(String lextValue2) {
		this.lextValue2 = lextValue2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEstiCurrencyName() {
		return estiCurrencyName;
	}
	public void setEstiCurrencyName(String estiCurrencyName) {
		this.estiCurrencyName = estiCurrencyName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public int getPerilCount() {
		return perilCount;
	}
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}
	public int getRecentCount() {
		return recentCount;
	}
	public void setRecentCount(int recentCount) {
		this.recentCount = recentCount;
	}
	public String getFlowID() {
		return flowID;
	}
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getScheduleItemNote() {
		return scheduleItemNote;
	}
	public void setScheduleItemNote(String scheduleItemNote) {
		this.scheduleItemNote = scheduleItemNote;
	}
	public String getPrpLregistDamageCode() {
		return prpLregistDamageCode;
	}
	public void setPrpLregistDamageCode(String prpLregistDamageCode) {
		this.prpLregistDamageCode = prpLregistDamageCode;
	}
	public String getPrpLregistDamageTypeCode() {
		return prpLregistDamageTypeCode;
	}
	public void setPrpLregistDamageTypeCode(String prpLregistDamageTypeCode) {
		this.prpLregistDamageTypeCode = prpLregistDamageTypeCode;
	}
	public String getLossItemCode() {
		return lossItemCode;
	}
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}
	public List<?> getRegistList() {
		return registList;
	}
	public void setRegistList(List<?> registList) {
		this.registList = registList;
	}
	public Double getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getCoinsFlag() {
		return coinsFlag;
	}
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getOthFlag() {
		return othFlag;
	}
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}
	public String getInsuredNameShow() {
		return insuredNameShow;
	}
	public void setInsuredNameShow(String insuredNameShow) {
		this.insuredNameShow = insuredNameShow;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public String getPolicyInsuredPhoneNumber() {
		return policyInsuredPhoneNumber;
	}
	public void setPolicyInsuredPhoneNumber(String policyInsuredPhoneNumber) {
		this.policyInsuredPhoneNumber = policyInsuredPhoneNumber;
	}
	public String getReportorMobile() {
		return reportorMobile;
	}
	public void setReportorMobile(String reportorMobile) {
		this.reportorMobile = reportorMobile;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getDamageCodeBZ() {
		return damageCodeBZ;
	}
	public void setDamageCodeBZ(String damageCodeBZ) {
		this.damageCodeBZ = damageCodeBZ;
	}
	public String getDamageNameBZ() {
		return damageNameBZ;
	}
	public void setDamageNameBZ(String damageNameBZ) {
		this.damageNameBZ = damageNameBZ;
	}
	public String getPolicyInsuredMobile() {
		return policyInsuredMobile;
	}
	public void setPolicyInsuredMobile(String policyInsuredMobile) {
		this.policyInsuredMobile = policyInsuredMobile;
	}
	public String getPolicyInsuredLicenseNumber() {
		return policyInsuredLicenseNumber;
	}
	public void setPolicyInsuredLicenseNumber(String policyInsuredLicenseNumber) {
		this.policyInsuredLicenseNumber = policyInsuredLicenseNumber;
	}
	public String getSharingFlag() {
		return sharingFlag;
	}
	public void setSharingFlag(String sharingFlag) {
		this.sharingFlag = sharingFlag;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getAuthorityUnit() {
		return authorityUnit;
	}
	public void setAuthorityUnit(String authorityUnit) {
		this.authorityUnit = authorityUnit;
	}
	public String getShipCName() {
		return shipCName;
	}
	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}
	public String getShipModel() {
		return shipModel;
	}
	public void setShipModel(String shipModel) {
		this.shipModel = shipModel;
	}
	public String getClaimAgent() {
		return claimAgent;
	}
	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getSameAddressNo() {
		return sameAddressNo;
	}
	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}
	//--date start
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getDamageEndDate() {
		return damageEndDate;
	}
	public void setDamageEndDate(String damageEndDate) {
		this.damageEndDate = damageEndDate;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getAlterTime() {
		return alterTime;
	}
	public void setAlterTime(String alterTime) {
		this.alterTime = alterTime;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getFlowInTime() {
		return flowInTime;
	}
	public void setFlowInTime(String flowInTime) {
		this.flowInTime = flowInTime;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getUnderWriteEndDate() {
		return underWriteEndDate;
	}
	public void setUnderWriteEndDate(String underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}
	
	
}
	