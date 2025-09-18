package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets CompulsoryCaseType
 */
public enum EnumCompulsoryCaseType {
	RECEIVE("RECEIVE"), PAY("PAY"), BOTH("BOTH"), VOLUNTARY_RECEIVE(
			"VOLUNTARY_RECEIVE"), VOLUNTARY_PAY("VOLUNTARY_PAY");

	private String value;

	EnumCompulsoryCaseType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumCompulsoryCaseType fromValue(String text) {
		for (EnumCompulsoryCaseType b : EnumCompulsoryCaseType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
