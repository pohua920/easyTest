package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets LossReason
 */
public enum EnumLossReason {
	LR11("LR11"), LR12("LR12"), LR13("LR13"), LR21("LR21"), LR22("LR22"), LR31(
			"LR31"), LR32("LR32"), LR99("LR99");

	private String value;

	EnumLossReason(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumLossReason fromValue(String text) {
		for (EnumLossReason b : EnumLossReason.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
