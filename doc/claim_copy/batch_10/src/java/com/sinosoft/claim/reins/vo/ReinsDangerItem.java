package com.sinosoft.claim.reins.vo;

import java.io.Serializable;

/**
 * 再保危险标的信息
 * @author 中科软
 *
 */
public class ReinsDangerItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String kindCode;
	private String kindName;
	private String itemCode;
	private String itemDetailName;
	private String currency;
	private Double amount;
	private Double premium;
	private Boolean calculateFlag;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getCalculateFlag() {
		return calculateFlag;
	}

	public void setCalculateFlag(Boolean calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDetailName() {
		return itemDetailName;
	}

	public void setItemDetailName(String itemDetailName) {
		this.itemDetailName = itemDetailName;
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

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

}
