package com.tlg.aps.webService.claimBlockChainService.client.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets ResponsibilityType
 */
public enum EnumResponsibilityType {
	RT1("RT1"), RT2("RT2"), RT3("RT3");

	private String value;

	EnumResponsibilityType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumResponsibilityType fromValue(String text) {
		for (EnumResponsibilityType b : EnumResponsibilityType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
