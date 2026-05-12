/**
 * 
 */
package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义通用报表打印Dto对象
 * <p>
 * Title: ZBWEB
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class SubReportPrintDto implements Serializable {

	/** 序号*/
	private static final long serialVersionUID = 1L;

	/** 构造函数*/
	public SubReportPrintDto() {
		super();
	}
	/** 计算定损条款名称*/
	private String strKindName;
	/**计算定损每个条款对应的总保费 */
	private String dbSumFee = "0";
	/** 计算理算条款名称*/
	private String strKindName1;
	/**计算理算每个条款对应的总保费 */
	private String dbSumFee1 = "0";
	/**计算理算費用合計 */
	private String strChargeAmount = "0";
	/** 序号*/
	private String strSerialNo;
	/** 汽車保險報案記錄（承保理賠資訊）条款名称*/
	private String strKindName2;
	/**汽車保險報案記錄（承保理賠資訊）条款代码*/
	private String strKindCode2;
	/** 汽車保險報案記錄（承保理賠資訊）每个条款对应的总保费*/
	private String dbSumFee2 = "0";
	/** 理赔计算书保险种类*/
	private String strKindCode;
	/** 理赔计算书保险金额*/
	private String strSumFee = "0";
	/**标题 */
	private String strShortTitle;
	/**承保自负额 */
	private String strContractDeductible = "0" ;
	/**理赔计算书自负额 */
	private String strDeductible = "0";
	/** 理赔计算书理赔人数*/
	private String strClaimNumber = "0";
	/** 理赔计算书预估损失金额*/
	private String strEstimatedLossAmount = "0";
	/** 理赔计算书实际理赔金额*/
	private String strActualClaimAmount = "0";
	/** 理赔计算书理赔费用*/
	private String strClaimAmount = "0";
	/**数量 */
	private double quantity = 0d;
	/** 理赔计算书保险金额*/
	private double amount;
	/** 理赔计算书自负额 */
	private double sumDeductible;
	/** 理赔计算书理赔人数*/
	private double personNumber;
	/**理赔计算书预估损失金额 */
	private double claimLoss;
	/**理赔计算书实际理赔金额 */
	private double sumDutyPaid;
	/** 理赔计算书理赔费用*/
	private double sumNoDutyFee;
	/**受害人 */
	private String strPersonName;
	/** 身份證*/
	private String strID;
	/** 出生年月*/
	private String strBirthday;
	/** 傷害醫療給付*/
	private double strMedicalPaid;
	/** 失能給付*/
	private double strDisPaid;
	/**死亡給付 */
	private double strDiePaid;
	/** 每壹受害人賠款*/
	private double strPerPersonPay;
	/** 統壹編號*/
	private String strUniformNo;
	/** 名稱*/
	private String strName;
	/** 地區*/
	private String strAreaCode;
	/** 賠款金額*/
	private double sumRealPay;
	/**  银行名称*/
	private String customBankName;
	/** 总行代码*/
	private String bankCode;
	/** 分行代码*/
	private String customBankCode;
	/** 账号*/
	private String accountCode;
	/** 追偿金额 */
	private String strReplevyAmount = "0";
	/** 健保金額  */
	private double healthAmount = 0d;
	/** 健保點數  */
	private double healthPoints = 0d;
	/** 洗錢旗標 Y:已命中,N:未命中,M:手動*/
	private String strAmlFlag ;
	/** 洗錢檢測日期*/
	private String strAmlDate ;
	

	public String getStrKindName() {
		return strKindName;
	}

	public void setStrKindName(String strKindName) {
		this.strKindName = strKindName;
	}

	public String getDbSumFee() {
		return dbSumFee;
	}

	public void setDbSumFee(String dbSumFee) {
		this.dbSumFee = dbSumFee;
	}

	public String getStrKindName1() {
		return strKindName1;
	}

	public void setStrKindName1(String strKindName1) {
		this.strKindName1 = strKindName1;
	}

	public String getDbSumFee1() {
		return dbSumFee1;
	}

	public void setDbSumFee1(String dbSumFee1) {
		this.dbSumFee1 = dbSumFee1;
	}

	public String getStrKindName2() {
		return strKindName2;
	}

	public void setStrKindName2(String strKindName2) {
		this.strKindName2 = strKindName2;
	}

	public String getStrSerialNo() {
		return strSerialNo;
	}

	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
	}

	public String getDbSumFee2() {
		return dbSumFee2;
	}

	public void setDbSumFee2(String dbSumFee2) {
		this.dbSumFee2 = dbSumFee2;
	}

	public String getStrKindCode2() {
		return strKindCode2;
	}

	public void setStrKindCode2(String strKindCode2) {
		this.strKindCode2 = strKindCode2;
	}

	public String getStrKindCode() {
		return strKindCode;
	}

	public void setStrKindCode(String strKindCode) {
		this.strKindCode = strKindCode;
	}

	public String getStrSumFee() {
		return strSumFee;
	}

	public void setStrSumFee(String strSumFee) {
		this.strSumFee = strSumFee;
	}

	public String getStrContractDeductible() {
		return strContractDeductible;
	}

	public void setStrContractDeductible(String strContractDeductible) {
		this.strContractDeductible = strContractDeductible;
	}

	public String getStrDeductible() {
		return strDeductible;
	}

	public void setStrDeductible(String strDeductible) {
		this.strDeductible = strDeductible;
	}

	public String getStrClaimNumber() {
		return strClaimNumber;
	}

	public void setStrClaimNumber(String strClaimNumber) {
		this.strClaimNumber = strClaimNumber;
	}

	public String getStrEstimatedLossAmount() {
		return strEstimatedLossAmount;
	}

	public void setStrEstimatedLossAmount(String strEstimatedLossAmount) {
		this.strEstimatedLossAmount = strEstimatedLossAmount;
	}

	public String getStrActualClaimAmount() {
		return strActualClaimAmount;
	}

	public void setStrActualClaimAmount(String strActualClaimAmount) {
		this.strActualClaimAmount = strActualClaimAmount;
	}

	public String getStrClaimAmount() {
		return strClaimAmount;
	}

	public void setStrClaimAmount(String strClaimAmount) {
		this.strClaimAmount = strClaimAmount;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getSumDeductible() {
		return sumDeductible;
	}

	public void setSumDeductible(double sumDeductible) {
		this.sumDeductible = sumDeductible;
	}

	public double getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(double personNumber) {
		this.personNumber = personNumber;
	}

	public double getClaimLoss() {
		return claimLoss;
	}

	public void setClaimLoss(double claimLoss) {
		this.claimLoss = claimLoss;
	}

	public double getSumDutyPaid() {
		return sumDutyPaid;
	}

	public void setSumDutyPaid(double sumDutyPaid) {
		this.sumDutyPaid = sumDutyPaid;
	}

	public double getSumNoDutyFee() {
		return sumNoDutyFee;
	}

	public void setSumNoDutyFee(double sumNoDutyFee) {
		this.sumNoDutyFee = sumNoDutyFee;
	}

	public String getStrPersonName() {
		return strPersonName;
	}

	public void setStrPersonName(String strPersonName) {
		this.strPersonName = strPersonName;
	}

	public String getStrID() {
		return strID;
	}

	public void setStrID(String strID) {
		this.strID = strID;
	}

	public String getStrBirthday() {
		return strBirthday;
	}

	public void setStrBirthday(String strBirthday) {
		this.strBirthday = strBirthday;
	}

	public double getStrMedicalPaid() {
		return strMedicalPaid;
	}

	public void setStrMedicalPaid(double strMedicalPaid) {
		this.strMedicalPaid = strMedicalPaid;
	}

	public double getStrDisPaid() {
		return strDisPaid;
	}

	public void setStrDisPaid(double strDisPaid) {
		this.strDisPaid = strDisPaid;
	}

	public double getStrDiePaid() {
		return strDiePaid;
	}

	public void setStrDiePaid(double strDiePaid) {
		this.strDiePaid = strDiePaid;
	}

	public double getStrPerPersonPay() {
		return strPerPersonPay;
	}

	public void setStrPerPersonPay(double strPerPersonPay) {
		this.strPerPersonPay = strPerPersonPay;
	}

	public String getStrUniformNo() {
		return strUniformNo;
	}

	public void setStrUniformNo(String strUniformNo) {
		this.strUniformNo = strUniformNo;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = strAreaCode;
	}

	public double getSumRealPay() {
		return sumRealPay;
	}

	public void setSumRealPay(double sumRealPay) {
		this.sumRealPay = sumRealPay;
	}

	public String getStrShortTitle() {
		return strShortTitle;
	}

	public void setStrShortTitle(String strShortTitle) {
		this.strShortTitle = strShortTitle;
	}

	public String getCustomBankName() {
		return customBankName;
	}

	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCustomBankCode() {
		return customBankCode;
	}

	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getStrChargeAmount() {
		return strChargeAmount;
	}

	public void setStrChargeAmount(String strChargeAmount) {
		this.strChargeAmount = strChargeAmount;
	}

	public String getStrReplevyAmount() {
		return strReplevyAmount;
	}

	public void setStrReplevyAmount(String strReplevyAmount) {
		this.strReplevyAmount = strReplevyAmount;
	}

	public double getHealthAmount() {
		return healthAmount;
	}

	public void setHealthAmount(double healthAmount) {
		this.healthAmount = healthAmount;
	}

	public double getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(double healthPoints) {
		this.healthPoints = healthPoints;
	}

	public String getStrAmlFlag() {
		return strAmlFlag;
	}

	public void setStrAmlFlag(String strAmlFlag) {
		this.strAmlFlag = strAmlFlag;
	}

	public String getStrAmlDate() {
		return strAmlDate;
	}

	public void setStrAmlDate(String strAmlDate) {
		this.strAmlDate = strAmlDate;
	}

}
