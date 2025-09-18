package com.tlg.aps.webService.claimBlockChainService.client.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumIdNumberType {
	ID_NUMBER("ID_NUMBER"), ARC_NUMBER("ARC_NUMBER"), PASSPORT_NUMBER(
			"PASSPORT_NUMBER");

	private String value;

	EnumIdNumberType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumIdNumberType fromValue(String text) {
		for (EnumIdNumberType b : EnumIdNumberType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
