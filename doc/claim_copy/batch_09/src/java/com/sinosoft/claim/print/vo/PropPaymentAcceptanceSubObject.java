package com.sinosoft.claim.print.vo;

/**
 * 火災保險賠款接受書 子報表  數據對象
 * @author 中科軟
 *
 */
public class PropPaymentAcceptanceSubObject {
	/** 保單號*/
	private String policyno;
	/** 保險標的*/
	private String itemKind;
	/** 保險金額，此處是標的的賠付金額*/
	private String amount;
	/** 標的物地址*/
	private String addressName;
	
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getItemKind() {
		return itemKind;
	}
	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
}
