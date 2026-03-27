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
 * POJO类PrpCmainCargo
 */
@Entity
@Table(name = "PRPCMAINCARGO")
public class PrpCmainCargo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性风险类别 */
	private String riskKind;

	/** 属性CONVEYDATEDESC */
	private String conveyDateDesc;

	/** 属性价格条件 */
	private String priceCondition;

	/** 属性提单号 */
	private String ladingNo;

	/** 属性发票号 */
	private String invoiceNo;

	/** 属性发票金额币别 */
	private String invoiceCurrency;

	/** 属性发票金额 */
	private Double invoiceAmount;

	/** 属性加成比例 */
	private Double plusRate;

	/** 属性信用证号 */
	private String creditNo;

	/** 属性起运通知书编号 */
	private String shipNoteNo;

	/** 属性合同号 */
	private String bargainNo;

	/** 属性装载运输工具 */
	private String conveyance;

	/** 属性运具名称 */
	private String blName;

	/** 属性货票运单号 */
	private String carryBillNo;

	/** 属性转运工具 */
	private String transferConveyance;

	/** 属性运具牌号 */
	private String blNo;

	/** 属性航次 */
	private String voyageNo;

	/** 属性预留信息 */
	private String preserveInfo;

	/** 属性吨位数 */
	private Double tonCount;

	/** 属性起始地编码 */
	private String startSiteCode;

	/** 属性起始地名称 */
	private String startSiteName;

	/** 属性中转地编码 */
	private String viaSiteCode;

	/** 属性中转地名称 */
	private String viaSiteName;

	/** 属性转载地名称 */
	private String reshipSiteName;

	/** 属性终止地编码 */
	private String endSiteCode;

	/** 属性终止地名称 */
	private String endSiteName;

	/** 属性具体终止地名称 */
	private String endDetailName;

	/** 属性国外检验代理人代码 */
	private String checkAgentCode;

	/** 属性赔款偿付地点 */
	private String claimSite;

	/** 属性过户银行 */
	private String transferBank;

	/** 属性保单正本份数 */
	private Integer originalCount;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性DEPARTDATE */
	private Date departDate;

	/**
	 * 类PrpCmainCargo的默认构造方法
	 */
	public PrpCmainCargo() {
	}

	/**
	 * 属性保单号码的getter方法
	 */
	@Id
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
	 * 属性风险类别的getter方法
	 */

	@Column(name = "RISKKIND")
	public String getRiskKind() {
		return this.riskKind;
	}

	/**
	 * 属性风险类别的setter方法
	 */
	public void setRiskKind(String riskKind) {
		this.riskKind = riskKind;
	}

	/**
	 * 属性CONVEYDATEDESC的getter方法
	 */

	@Column(name = "CONVEYDATEDESC")
	public String getConveyDateDesc() {
		return this.conveyDateDesc;
	}

	/**
	 * 属性CONVEYDATEDESC的setter方法
	 */
	public void setConveyDateDesc(String conveyDateDesc) {
		this.conveyDateDesc = conveyDateDesc;
	}

	/**
	 * 属性价格条件的getter方法
	 */

	@Column(name = "PRICECONDITION")
	public String getPriceCondition() {
		return this.priceCondition;
	}

	/**
	 * 属性价格条件的setter方法
	 */
	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}

	/**
	 * 属性提单号的getter方法
	 */

	@Column(name = "LADINGNO")
	public String getLadingNo() {
		return this.ladingNo;
	}

	/**
	 * 属性提单号的setter方法
	 */
	public void setLadingNo(String ladingNo) {
		this.ladingNo = ladingNo;
	}

	/**
	 * 属性发票号的getter方法
	 */

	@Column(name = "INVOICENO")
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	/**
	 * 属性发票号的setter方法
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * 属性发票金额币别的getter方法
	 */

	@Column(name = "INVOICECURRENCY")
	public String getInvoiceCurrency() {
		return this.invoiceCurrency;
	}

	/**
	 * 属性发票金额币别的setter方法
	 */
	public void setInvoiceCurrency(String invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}

	/**
	 * 属性发票金额的getter方法
	 */

	@Column(name = "INVOICEAMOUNT")
	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	/**
	 * 属性发票金额的setter方法
	 */
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	/**
	 * 属性加成比例的getter方法
	 */

	@Column(name = "PLUSRATE")
	public Double getPlusRate() {
		return this.plusRate;
	}

	/**
	 * 属性加成比例的setter方法
	 */
	public void setPlusRate(Double plusRate) {
		this.plusRate = plusRate;
	}

	/**
	 * 属性信用证号的getter方法
	 */

	@Column(name = "CREDITNO")
	public String getCreditNo() {
		return this.creditNo;
	}

	/**
	 * 属性信用证号的setter方法
	 */
	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	/**
	 * 属性起运通知书编号的getter方法
	 */

	@Column(name = "SHIPNOTENO")
	public String getShipNoteNo() {
		return this.shipNoteNo;
	}

	/**
	 * 属性起运通知书编号的setter方法
	 */
	public void setShipNoteNo(String shipNoteNo) {
		this.shipNoteNo = shipNoteNo;
	}

	/**
	 * 属性合同号的getter方法
	 */

	@Column(name = "BARGAINNO")
	public String getBargainNo() {
		return this.bargainNo;
	}

	/**
	 * 属性合同号的setter方法
	 */
	public void setBargainNo(String bargainNo) {
		this.bargainNo = bargainNo;
	}

	/**
	 * 属性装载运输工具的getter方法
	 */

	@Column(name = "CONVEYANCE")
	public String getConveyance() {
		return this.conveyance;
	}

	/**
	 * 属性装载运输工具的setter方法
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 属性运具名称的getter方法
	 */

	@Column(name = "BLNAME")
	public String getBlName() {
		return this.blName;
	}

	/**
	 * 属性运具名称的setter方法
	 */
	public void setBlName(String blName) {
		this.blName = blName;
	}

	/**
	 * 属性货票运单号的getter方法
	 */

	@Column(name = "CARRYBILLNO")
	public String getCarryBillNo() {
		return this.carryBillNo;
	}

	/**
	 * 属性货票运单号的setter方法
	 */
	public void setCarryBillNo(String carryBillNo) {
		this.carryBillNo = carryBillNo;
	}

	/**
	 * 属性转运工具的getter方法
	 */

	@Column(name = "TRANSFERCONVEYANCE")
	public String getTransferConveyance() {
		return this.transferConveyance;
	}

	/**
	 * 属性转运工具的setter方法
	 */
	public void setTransferConveyance(String transferConveyance) {
		this.transferConveyance = transferConveyance;
	}

	/**
	 * 属性运具牌号的getter方法
	 */

	@Column(name = "BLNO")
	public String getBlNo() {
		return this.blNo;
	}

	/**
	 * 属性运具牌号的setter方法
	 */
	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	/**
	 * 属性航次的getter方法
	 */

	@Column(name = "VOYAGENO")
	public String getVoyageNo() {
		return this.voyageNo;
	}

	/**
	 * 属性航次的setter方法
	 */
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	/**
	 * 属性预留信息的getter方法
	 */

	@Column(name = "PRESERVEINFO")
	public String getPreserveInfo() {
		return this.preserveInfo;
	}

	/**
	 * 属性预留信息的setter方法
	 */
	public void setPreserveInfo(String preserveInfo) {
		this.preserveInfo = preserveInfo;
	}

	/**
	 * 属性吨位数的getter方法
	 */

	@Column(name = "TONCOUNT")
	public Double getTonCount() {
		return this.tonCount;
	}

	/**
	 * 属性吨位数的setter方法
	 */
	public void setTonCount(Double tonCount) {
		this.tonCount = tonCount;
	}

	/**
	 * 属性起始地编码的getter方法
	 */

	@Column(name = "STARTSITECODE")
	public String getStartSiteCode() {
		return this.startSiteCode;
	}

	/**
	 * 属性起始地编码的setter方法
	 */
	public void setStartSiteCode(String startSiteCode) {
		this.startSiteCode = startSiteCode;
	}

	/**
	 * 属性起始地名称的getter方法
	 */

	@Column(name = "STARTSITENAME")
	public String getStartSiteName() {
		return this.startSiteName;
	}

	/**
	 * 属性起始地名称的setter方法
	 */
	public void setStartSiteName(String startSiteName) {
		this.startSiteName = startSiteName;
	}

	/**
	 * 属性中转地编码的getter方法
	 */

	@Column(name = "VIASITECODE")
	public String getViaSiteCode() {
		return this.viaSiteCode;
	}

	/**
	 * 属性中转地编码的setter方法
	 */
	public void setViaSiteCode(String viaSiteCode) {
		this.viaSiteCode = viaSiteCode;
	}

	/**
	 * 属性中转地名称的getter方法
	 */

	@Column(name = "VIASITENAME")
	public String getViaSiteName() {
		return this.viaSiteName;
	}

	/**
	 * 属性中转地名称的setter方法
	 */
	public void setViaSiteName(String viaSiteName) {
		this.viaSiteName = viaSiteName;
	}

	/**
	 * 属性转载地名称的getter方法
	 */

	@Column(name = "RESHIPSITENAME")
	public String getReshipSiteName() {
		return this.reshipSiteName;
	}

	/**
	 * 属性转载地名称的setter方法
	 */
	public void setReshipSiteName(String reshipSiteName) {
		this.reshipSiteName = reshipSiteName;
	}

	/**
	 * 属性终止地编码的getter方法
	 */

	@Column(name = "ENDSITECODE")
	public String getEndSiteCode() {
		return this.endSiteCode;
	}

	/**
	 * 属性终止地编码的setter方法
	 */
	public void setEndSiteCode(String endSiteCode) {
		this.endSiteCode = endSiteCode;
	}

	/**
	 * 属性终止地名称的getter方法
	 */

	@Column(name = "ENDSITENAME")
	public String getEndSiteName() {
		return this.endSiteName;
	}

	/**
	 * 属性终止地名称的setter方法
	 */
	public void setEndSiteName(String endSiteName) {
		this.endSiteName = endSiteName;
	}

	/**
	 * 属性具体终止地名称的getter方法
	 */

	@Column(name = "ENDDETAILNAME")
	public String getEndDetailName() {
		return this.endDetailName;
	}

	/**
	 * 属性具体终止地名称的setter方法
	 */
	public void setEndDetailName(String endDetailName) {
		this.endDetailName = endDetailName;
	}

	/**
	 * 属性国外检验代理人代码的getter方法
	 */

	@Column(name = "CHECKAGENTCODE")
	public String getCheckAgentCode() {
		return this.checkAgentCode;
	}

	/**
	 * 属性国外检验代理人代码的setter方法
	 */
	public void setCheckAgentCode(String checkAgentCode) {
		this.checkAgentCode = checkAgentCode;
	}

	/**
	 * 属性赔款偿付地点的getter方法
	 */

	@Column(name = "CLAIMSITE")
	public String getClaimSite() {
		return this.claimSite;
	}

	/**
	 * 属性赔款偿付地点的setter方法
	 */
	public void setClaimSite(String claimSite) {
		this.claimSite = claimSite;
	}

	/**
	 * 属性过户银行的getter方法
	 */

	@Column(name = "TRANSFERBANK")
	public String getTransferBank() {
		return this.transferBank;
	}

	/**
	 * 属性过户银行的setter方法
	 */
	public void setTransferBank(String transferBank) {
		this.transferBank = transferBank;
	}

	/**
	 * 属性保单正本份数的getter方法
	 */

	@Column(name = "ORIGINALCOUNT")
	public Integer getOriginalCount() {
		return this.originalCount;
	}

	/**
	 * 属性保单正本份数的setter方法
	 */
	public void setOriginalCount(Integer originalCount) {
		this.originalCount = originalCount;
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
	 * 属性DEPARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DEPARTDATE")
	public Date getDepartDate() {
		return this.departDate;
	}

	/**
	 * 属性DEPARTDATE的setter方法
	 */
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

}
