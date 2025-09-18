package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
* mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumApportionTableType {
	BYCHARGECOUNT("ByChargeCount"), BYRESPONSIBILITYRATE("ByResponsibilityRate"), BYRULE36(
			"ByRule36");

	private String value;

	EnumApportionTableType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumApportionTableType fromValue(String text) {
		for (EnumApportionTableType b : EnumApportionTableType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
