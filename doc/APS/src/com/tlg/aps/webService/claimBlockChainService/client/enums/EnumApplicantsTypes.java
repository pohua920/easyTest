package com.tlg.aps.webService.claimBlockChainService.client.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets EnumApplicantsTypes
 */
public enum EnumApplicantsTypes {
	DRIVER("DRIVER"), PASSENGER("PASSENGER"), PEDESTRIAN("PEDESTRIAN"), THIS_CAR_PASSENGER(
			"THIS_CAR_PASSENGER");

	private String value;

	EnumApplicantsTypes(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumApplicantsTypes fromValue(String text) {
		for (EnumApplicantsTypes b : EnumApplicantsTypes.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
