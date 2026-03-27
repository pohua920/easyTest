package com.sinosoft.claim.print.vo;

import java.io.Serializable;
/***
 * 理算险种讯息
 * @author 中科软
 */
public class CompensateKindInfoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 險別代號 */
	private String kindCode = "";
	/** 險別名稱 */
	private String kindName = "";
	/** 損失標的代號 */
	private String itemCode = "";
	/** 損失標的名稱 */
	private String itemName = "";
	/** 保險金額 */
	private String amount = "";

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
