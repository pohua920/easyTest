package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@XmlRootElement
public class RiskCC{
	private String insuredName;	//AS400-賠付對象
	private String accidentName;//AS400-被保險人
	private String accidentId;//AS400-統編
	private String accidentAreaCode;//AS400-賠付地址郵遞區號
	private String accidentAddress;//AS400-賠付地址
	private String cardNo;//AS400-卡號
	private String cardName;//AS400-卡別
	private Long estimatedLoss;//AS400-預估損失
	private Long netLoss;//AS400-淨損金
	private Long adjustedAmount;//AS400-理算金
	private Long totalLoss;//AS400-賠款總計
	private Long paidAmount;//AS400-已付
	private Long payAmount;//AS400-賠付金額
	private Long actualPaidAmount;//AS400-實際賠款金額
	private String contextNo;//AS400-賠款內容說明
	private String bankCode;//AS400-帳號
	private String custBankCode;//AS400-分行代碼
	private String accountCode;//AS400-匯款帳號
	
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getAccidentName() {
		return accidentName;
	}
	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
	}
	public String getAccidentId() {
		return accidentId;
	}
	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}
	public String getAccidentAreaCode() {
		return accidentAreaCode;
	}
	public void setAccidentAreaCode(String accidentAreaCode) {
		this.accidentAreaCode = accidentAreaCode;
	}
	public String getAccidentAddress() {
		return accidentAddress;
	}
	public void setAccidentAddress(String accidentAddress) {
		this.accidentAddress = accidentAddress;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Long getEstimatedLoss() {
		return estimatedLoss;
	}
	public void setEstimatedLoss(Long estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}
	public Long getNetLoss() {
		return netLoss;
	}
	public void setNetLoss(Long netLoss) {
		this.netLoss = netLoss;
	}
	public Long getAdjustedAmount() {
		return adjustedAmount;
	}
	public void setAdjustedAmount(Long adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	public Long getTotalLoss() {
		return totalLoss;
	}
	public void setTotalLoss(Long totalLoss) {
		this.totalLoss = totalLoss;
	}
	public Long getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public Long getActualPaidAmount() {
		return actualPaidAmount;
	}
	public void setActualPaidAmount(Long actualPaidAmount) {
		this.actualPaidAmount = actualPaidAmount;
	}
	public String getContextNo() {
		return contextNo;
	}
	public void setContextNo(String contextNo) {
		this.contextNo = contextNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCustBankCode() {
		return custBankCode;
	}
	public void setCustBankCode(String custBankCode) {
		this.custBankCode = custBankCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
}
