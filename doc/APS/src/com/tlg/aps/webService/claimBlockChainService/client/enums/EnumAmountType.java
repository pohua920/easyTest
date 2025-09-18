package com.tlg.aps.webService.claimBlockChainService.client.enums;



/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets AmountType
 */
public enum EnumAmountType {
	MEDICAL("MEDICAL"), DISABILITY("DISABILITY"), DEATH("DEATH");

	private String value;

	EnumAmountType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumAmountType fromValue(String text) {
		for (EnumAmountType b : EnumAmountType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
