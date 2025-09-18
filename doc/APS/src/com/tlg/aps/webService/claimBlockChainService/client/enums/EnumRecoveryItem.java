package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets EnumRecoveryItem
 */
public enum EnumRecoveryItem {
	EMPTY(""), RI01("RI01"), RI02("RI02"), RI03("RI03"), RI04("RI04"), RI05(
			"RI05");

	private String value;

	EnumRecoveryItem(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumRecoveryItem fromValue(String text) {
		for (EnumRecoveryItem b : EnumRecoveryItem.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
