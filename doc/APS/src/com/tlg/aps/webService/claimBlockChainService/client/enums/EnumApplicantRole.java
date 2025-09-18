package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumApplicantRole {
	AR1("AR1"), AR2("AR2"), AR7("AR7"), AR8("AR8");

	private String value;

	EnumApplicantRole(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumApplicantRole fromValue(String text) {
		for (EnumApplicantRole b : EnumApplicantRole.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
