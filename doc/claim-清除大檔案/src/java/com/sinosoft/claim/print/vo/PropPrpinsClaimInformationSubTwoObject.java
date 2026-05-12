package com.sinosoft.claim.print.vo;

/**
 * 火險承保理賠信息 數據對象 子報表數據 （附加險）
 * @author 中科軟
 *
 */
public class PropPrpinsClaimInformationSubTwoObject {
	/**險別*/
	private String kindName;
	/**標的項目*/
	private String itemCode;
	/**標的名稱*/
	private String itemDetailName;
	/**保額*/
	private String sumAmount;
	
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
	public String getItemDetailName() {
		return itemDetailName;
	}
	public void setItemDetailName(String itemDetailName) {
		this.itemDetailName = itemDetailName;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	
}
