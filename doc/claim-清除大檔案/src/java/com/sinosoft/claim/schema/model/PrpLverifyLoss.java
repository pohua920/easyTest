package com.sinosoft.claim.schema.model;

import java.util.ArrayList;
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

@Entity
@Table(name = "PRPLVERIFYLOSS")
public class PrpLverifyLoss implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpLverifyLossId id;
	/** 属性立案号码 */
	private String claimNo;
	/** 属性险种代码 */
	private String riskCode;
	/** 属性保单号码 */
	private String policyNo;
	/** 属性车牌号码 */
	private String lossItemName;
	/** 属性是否为本单位车辆 */
	private String insureCarFlag;
	/** 属性被保险人 */
	private String insuredName;
	/** 属性号牌号码 */
	private String licenseNo;
	/** 属性号牌底色代码 */
	private String licenseColorCode;
	/** 属性号牌种类代码 */
	private String carKindCode;
	/** 属性货币代码 */
	private String currency;
	/** 属性定损总金额 */
	private Double sumPreDefLoss;
	/** 属性核损总金额 */
	private Double sumDefLoss;
	/** 属性出单机构 */
	private String makeCom;
	/** 属性业务归属机构代码 */
	private String comCode;
	/** 属性定损人代码 */
	private String handlerCode;
	/** 属性定损人名称 */
	private String handlerName;
	/** 属性定损结束日期 */
	private Date defLossDate;
	/** 属性核损人代码 */
	private String underWriteCode;
	/** 属性核损人名称 */
	private String underWriteName;
	/** 属性最终核损完成日期 */
	private Date underWriteEndDate;
	/** 属性是否经过合损标志 */
	private String underWriteFlag;
	/** 属性备注 */
	private String remark;
	/** 属性备注（合损） */
	private String verifyRemark;
	/** 属性标志字段 */
	private String flag;
	/** 属性回勘意见 */
	private String backCheckRemark;
	/** 属性人伤核损回退的原因 */
	private String veriwReturnReason;
	/** 属性核损意见 */
	private String verifyOpinion;
	/** 属性初次定损金额 */
	private Double firstDefLoss;
	/** 属性偏差定损金额 */
	private Double warpDefLoss;
	/** 属性核价人代码 */
	private String verpApproverCode;
	/** 属性核价时间 */
	private Date verpDate;
	/** 属性核价意见 */
	private String verpOpinion;
	/** 属性备注核价 */
	private String verpRemark;
	/** 属性理算退回标记 */
	private String compensateFlag;
	/** 属性理算退回原因 */
	private String compensateOpinion;
	/** 属性理算退回时间 */
	private Date compensateBackDate;
	/** 属性理算退回操作人 */
	private String compensateApproverCode;
	/** 属性修理厂代码 */
	private String repairFactoryCode;
	/** 属性修理厂名称 */
	private String repairFactoryName;
	/** 修理厂类型 */
	private String repairFactoryType;
	/** 此立案的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** 案件的操作时间 */
	private Date operateDate = new Date();
	/** 强三查询 */
	private Collection<String> relatepolicyNo = null;
	/** 号牌底色名称 */
	private String licenseColor = "";
	/** 车辆种类 */
	private String carKind = "";
	/** 属性条款类别 */
	private String clauseType = "";
	/** 属性条款名称 */
	private String clauseName = "";

	private List<PrpLverifyLoss> verifyLossList = new ArrayList<PrpLverifyLoss>();
	/** 编辑类型 */
	private String editType = "";
	/** 币别名称 */
	private String currencyName = "";
	/** 属性出险次数 */
	private int perilCount = 0;
	/** 是否更新立案的估损金额 */
	private boolean isUpdateSumClaim = false;
	/** 向外询价信息 */
	private String verifPriceOuterMsg = "";
	/** 属性理算退回的操作人 */
	private String compensateApproverName = "";
	/** 属性流程编号 */
	private String flowID = "";

	/**
	 * 属性FlowID的getter方法
	 */
	@Transient
	public String getFlowID() {
		return flowID;
	}

	/**
	 * 属性FlowID的setter方法
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	/**
	 * 属性CompensateApproverName的getter方法
	 */
	@Transient
	public String getCompensateApproverName() {
		return compensateApproverName;
	}

	/**
	 * 属性CompensateApproverName的setter方法
	 */
	public void setCompensateApproverName(String compensateApproverName) {
		this.compensateApproverName = compensateApproverName;
	}

	/**
	 * 属性VerifyPriceOuterMsg的getter方法
	 */
	@Transient
	public String getVerifPriceOuterMsg() {
		return verifPriceOuterMsg;
	}

	/**
	 * 属性VerifyPriceOuterMsg的setter方法
	 */
	public void setVerifPriceOuterMsg(String verifPriceOuterMsg) {
		this.verifPriceOuterMsg = verifPriceOuterMsg;
	}

	/**
	 * 属性updateSumClaim的getter方法
	 */
	@Transient
	public boolean isUpdateSumClaim() {
		return isUpdateSumClaim;
	}

	/**
	 * 属性UpdateSumClaim的setter方法
	 */
	public void setUpdateSumClaim(boolean isUpdateSumClaim) {
		this.isUpdateSumClaim = isUpdateSumClaim;
	}

	/**
	 * 属性perilCount的getter方法
	 */
	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 属性perilCount的setter方法
	 * @param perilCount
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	/**
	 * 属性CurrencyName的getter方法
	 */
	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * 属性Currency的setter方法
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 类PrpLverifyLoss的默认构造方法
	 */
	public PrpLverifyLoss() {
		id = new PrpLverifyLossId();
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), 
		@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")),
		@AttributeOverride(name = "nodeType", column = @Column(name = "nodeType"))})
	public PrpLverifyLossId getId() {
		return id;
	}

	public void setId(PrpLverifyLossId id) {
		this.id = id;
	}

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return lossItemName;
	}

	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	@Column(name = "INSURECARFLAG")
	public String getInsureCarFlag() {
		return insureCarFlag;
	}

	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	@Column(name = "CARKINDCODE")
	public String getCarKindCode() {
		return carKindCode;
	}

	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "SUMPREDEFLOSS")
	public Double getSumPreDefLoss() {
		return sumPreDefLoss;
	}

	public void setSumPreDefLoss(Double sumPreDefLoss) {
		this.sumPreDefLoss = sumPreDefLoss;
	}

	@Column(name = "SUMDEFLOSS")
	public Double getSumDefLoss() {
		return sumDefLoss;
	}

	public void setSumDefLoss(Double sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
	}

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	@Column(name = "COMCODE")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEFLOSSDATE")
	public Date getDefLossDate() {
		return defLossDate;
	}

	public void setDefLossDate(Date defLossDate) {
		this.defLossDate = defLossDate;
	}

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return underWriteCode;
	}

	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return underWriteName;
	}

	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return underWriteEndDate;
	}

	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return underWriteFlag;
	}

	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "VERIFYREMARK")
	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "BACKCHECKREMARK")
	public String getBackCheckRemark() {
		return backCheckRemark;
	}

	public void setBackCheckRemark(String backCheckRemark) {
		this.backCheckRemark = backCheckRemark;
	}

	@Column(name = "VERIWRETURNREASON")
	public String getVeriwReturnReason() {
		return veriwReturnReason;
	}

	public void setVeriwReturnReason(String veriwReturnReason) {
		this.veriwReturnReason = veriwReturnReason;
	}

	@Column(name = "VERIFYOPINION")
	public String getVerifyOpinion() {
		return verifyOpinion;
	}

	public void setVerifyOpinion(String verifyOpinion) {
		this.verifyOpinion = verifyOpinion;
	}

	@Column(name = "FIRSTDEFLOSS")
	public Double getFirstDefLoss() {
		return firstDefLoss;
	}

	public void setFirstDefLoss(Double firstDefLoss) {
		this.firstDefLoss = firstDefLoss;
	}

	@Column(name = "WARPDEFLOSS")
	public Double getWarpDefLoss() {
		return warpDefLoss;
	}

	public void setWarpDefLoss(Double warpDefLoss) {
		this.warpDefLoss = warpDefLoss;
	}

	@Column(name = "VERPAPPROVERCODE")
	public String getVerpApproverCode() {
		return verpApproverCode;
	}

	public void setVerpApproverCode(String verpApproverCode) {
		this.verpApproverCode = verpApproverCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VERPDATE")
	public Date getVerpDate() {
		return verpDate;
	}

	public void setVerpDate(Date verpDate) {
		this.verpDate = verpDate;
	}

	@Column(name = "VERPOPINION")
	public String getVerpOpinion() {
		return verpOpinion;
	}

	public void setVerpOpinion(String verpOpinion) {
		this.verpOpinion = verpOpinion;
	}

	@Column(name = "VERPREMARK")
	public String getVerpRemark() {
		return verpRemark;
	}

	public void setVerpRemark(String verpRemark) {
		this.verpRemark = verpRemark;
	}

	@Column(name = "COMPENSATEFLAG")
	public String getCompensateFlag() {
		return compensateFlag;
	}

	public void setCompensateFlag(String compensateFlag) {
		this.compensateFlag = compensateFlag;
	}

	@Column(name = "COMPENSATEOPINION")
	public String getCompensateOpinion() {
		return compensateOpinion;
	}

	public void setCompensateOpinion(String compensateOpinion) {
		this.compensateOpinion = compensateOpinion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COMPENSATEBACKDATE")
	public Date getCompensateBackDate() {
		return compensateBackDate;
	}

	public void setCompensateBackDate(Date compensateBackDate) {
		this.compensateBackDate = compensateBackDate;
	}

	@Column(name = "COMPENSATEAPPROVERCODE")
	public String getCompensateApproverCode() {
		return compensateApproverCode;
	}

	public void setCompensateApproverCode(String compensateApproverCode) {
		this.compensateApproverCode = compensateApproverCode;
	}

	@Column(name = "REPAIRFACTORYCODE")
	public String getRepairFactoryCode() {
		return repairFactoryCode;
	}

	public void setRepairFactoryCode(String repairFactoryCode) {
		this.repairFactoryCode = repairFactoryCode;
	}

	@Column(name = "REPAIRFACTORYNAME")
	public String getRepairFactoryName() {
		return repairFactoryName;
	}

	public void setRepairFactoryName(String repairFactoryName) {
		this.repairFactoryName = repairFactoryName;
	}

	@Column(name = "REPAIRFACTORYTYPE")
	public String getRepairFactoryType() {
		return repairFactoryType;
	}

	public void setRepairFactoryType(String repairFactoryType) {
		this.repairFactoryType = repairFactoryType;
	}

	@Transient
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@Transient
	public Collection<String> getRelatepolicyNo() {
		return relatepolicyNo;
	}

	public void setRelatepolicyNo(Collection<String> relatepolicyNo) {
		this.relatepolicyNo = relatepolicyNo;
	}

	@Transient
	public String getClauseType() {
		return clauseType;
	}

	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	@Transient
	public String getClauseName() {
		return clauseName;
	}

	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}

	@Transient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public List<PrpLverifyLoss> getVerifyLossList() {
		return verifyLossList;
	}

	public void setVerifyLossList(List<PrpLverifyLoss> verifyLossList) {
		this.verifyLossList = verifyLossList;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public String getLicenseColor() {
		return licenseColor;
	}

	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}

	@Transient
	public String getCarKind() {
		return carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}
}
