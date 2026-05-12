package com.sinosoft.claim.print.vo;

import java.io.Serializable;

/***
 * 
 * @author 中科软
 */
public class ReplevySubPayInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 追回款支付象 */
	private String ownerName = "";
	/** 幣別 */
	private String currency = "";
	/** 追償金額 */
	private String payAmount = "";

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

}
