package com.tlg.aps.webService.claimBlockChainService.client.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumMspId {
	MSP00("MSP00"), MSP01("MSP01"), MSP02("MSP02"), MSP05("MSP05"), MSP06(
			"MSP06"), MSP07("MSP07"), MSP08("MSP08"), MSP09("MSP09"), MSP10(
			"MSP10"), MSP12("MSP12"), MSP13("MSP13"), MSP14("MSP14"), MSP15(
			"MSP15"), MSP17("MSP17"), MSP18("MSP18");

	private String value;

	EnumMspId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumMspId fromValue(String text) {
		for (EnumMspId b : EnumMspId.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
