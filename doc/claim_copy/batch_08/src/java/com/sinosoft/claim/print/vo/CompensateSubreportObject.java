package com.sinosoft.claim.print.vo;

public class CompensateSubreportObject {
	/** 序号 */
	private String serialNo = "";
	/**殘餘物沖回險別代號  */
	private String  kindCode = "";
	/**殘餘物沖回險別名稱損失標的名稱 */
	private String kindName = "";
	/**幣別*/
	private String currency = "";
	/**殘餘物金額  餘殘物總金額 */
	private String  realPaid = "";
	/**領 款 人：賠付對象 ownerName*/
	private String ownerName = "";
	/**領 款 人：身分證字號/統一編碼 uniformNo*/
	private String uniformNo = "";
	/**銀  行：總行＋分行＋賬戶代號 */
	private String customBankFullName = "";
	/**銀行名稱*/
	private String customBankName = "";
	/**單位 ：收付端開票單位代碼*/
	private String billingUnit = "";
	/**賠付金額 payAmount*/
	private String payAmount = "";
	/**险别的保额 */
	private String amount = "";
	/** 自负额 */
	private String deductible = "";
	/** 自负额比率  */
	private String deductibleRate = "";
	/** 標的損失 代號  */
	private String itemCode = "";
	/** 標的損失 名稱  */
	private String itemName = "";

	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRealPaid() {
		return realPaid;
	}
	public void setRealPaid(String realPaid) {
		this.realPaid = realPaid;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getUniformNo() {
		return uniformNo;
	}
	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}
	public String getCustomBankFullName() {
		return customBankFullName;
	}
	public void setCustomBankFullName(String customBankFullName) {
		this.customBankFullName = customBankFullName;
	}
	public String getCustomBankName() {
		return customBankName;
	}
	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}
	
	public String getBillingUnit() {
		return billingUnit;
	}
	public void setBillingUnit(String billingUnit) {
		this.billingUnit = billingUnit;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDeductible() {
		return deductible;
	}
	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}
	public String getDeductibleRate() {
		return deductibleRate;
	}
	public void setDeductibleRate(String deductibleRate) {
		this.deductibleRate = deductibleRate;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
