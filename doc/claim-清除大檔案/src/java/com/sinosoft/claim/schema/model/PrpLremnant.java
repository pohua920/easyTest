package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinosoft.claim.common.ConstantCodes;

/**
 * POJO类PrpLremnant残余物任务登记表
 */
@Entity
@Table(name = "PRPLREMNANT")
public class PrpLremnant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 属性Id */
	private PrpLremnantId id;
	/** 保单号码 */
	private String policyNo = "";
	/** 险类 */
	private String classCode = "";
	/** 险别代码 */
	private String kindCode = "";
	/** 险别名称 */
	private String kindName = "";
	/** 理賠確認日 */
	private Date remnantDate;
	/** 標的號碼 */
	private String remnantCode = "";
	/** 理賠單位 */
	private String comCode = "";
	/** 处理人员 */
	private String handleCode = "";
	/** 赔付对象讯息 */
	private String payObjectSerialNo = "";
	/** 处理名称 */
	private String handleName = "";
	/** 备注 */
	private String remark = "";
	/** 标志位 */
	private String flag = "";
	/** 属性赔案号码 */
	private String claimNo = "";
	/** 属性出险险种 */
	private String riskCode = "";
	/** 属性放置地点 */
	private String address = "";
	/** 属性产生日期 */
	private Date generateDate;
	/** 属性预估金额 */
	private Double estimateAmount = 0d;
	/** 属性拍卖日期 */
	private Date auctionDate;
	/** 属性拍卖金额 */
	private Double auctionAmount = 0d;
	/** 属性处理费用 */
	private Double handleCost = 0d;
	/** 实缴金额 */
	private Double realPay = 0d;
	/** 摊回日期 */
	private Date shareDate;
	/** 失窃车返还额 */
	private Double backAmount = 0d;
	/** 确认人代码 */
	private String confirmorCode = "";
	/** 确认人名称 */
	private String confirmorName = "";
	/** 确认日期 */
	private Date confirmDate;
	/** 殘餘物任務是否結束,0否，1是，默认'是'*/
	private String remnants = "1";
	
	/** 汇率 （赔付币别对本位币的汇率） */
	private Double exchRate = 1d;
	/** 属性币别 */
	private String currency = ConstantCodes.LOCAL_CURRENCY;

	public PrpLremnant() {
		this.id = new PrpLremnantId();
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLremnantId getId() {
		return id;
	}

	public void setId(PrpLremnantId id) {
		this.id = id;
	}

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

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GENERATEDATE")
	public Date getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}

	@Column(name = "ESTIMATEAMOUNT")
	public Double getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUCTIONDATE")
	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	@Column(name = "AUCTIONAMOUNT")
	public Double getAuctionAmount() {
		return auctionAmount;
	}

	public void setAuctionAmount(Double auctionAmount) {
		this.auctionAmount = auctionAmount;
	}

	@Column(name = "HANDLECOST")
	public Double getHandleCost() {
		return handleCost;
	}

	public void setHandleCost(Double handleCost) {
		this.handleCost = handleCost;
	}

	@Column(name = "REALPAY")
	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SHAREDATE")
	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}

	@Column(name = "BACKAMOUNT")
	public Double getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(Double backAmount) {
		this.backAmount = backAmount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONFIRMDATE")
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "KINDNAME")
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REMNANTDATE")
	public Date getRemnantDate() {
		return remnantDate;
	}

	public void setRemnantDate(Date remnantDate) {
		this.remnantDate = remnantDate;
	}

	@Column(name = "REMNANTCODE")
	public String getRemnantCode() {
		return remnantCode;
	}

	public void setRemnantCode(String remnantCode) {
		this.remnantCode = remnantCode;
	}

	@Column(name = "COMCODE")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Column(name = "HANDLECODE")
	public String getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	@Column(name = "PAYOBJECTSERIALNO")
	public String getPayObjectSerialNo() {
		return payObjectSerialNo;
	}

	public void setPayObjectSerialNo(String payObjectSerialNo) {
		this.payObjectSerialNo = payObjectSerialNo;
	}

	@Column(name = "HANDLENAME")
	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CONFIRMORCODE")
	public String getConfirmorCode() {
		return confirmorCode;
	}

	public void setConfirmorCode(String confirmorCode) {
		this.confirmorCode = confirmorCode;
	}

	@Column(name = "CONFIRMORNAME")
	public String getConfirmorName() {
		return confirmorName;
	}

	public void setConfirmorName(String confirmorName) {
		this.confirmorName = confirmorName;
	}

	@Column(name = "REMNANTS")
	public String getRemnants() {
		return remnants;
	}

	public void setRemnants(String remnants) {
		this.remnants = remnants;
	}
	@Column(name = "exchRate")
	public Double getExchRate() {
		return exchRate;
	}

	public void setExchRate(Double exchRate) {
		this.exchRate = exchRate;
	}
	@Column(name = "currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
