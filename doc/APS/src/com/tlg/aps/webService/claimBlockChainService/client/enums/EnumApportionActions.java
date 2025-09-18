package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumApportionActions {
	UPDATE("UPDATE"), NOTICE("NOTICE"), APPROVE("APPROVE"), DELETE("DELETE"), CANCEL(
			"CANCEL"), COMPLETE("COMPLETE"), REJECT("REJECT"), NEW_APPORTION(
			"NEW_APPORTION");

	private String value;

	EnumApportionActions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumApportionActions fromValue(String text) {
		for (EnumApportionActions b : EnumApportionActions.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
