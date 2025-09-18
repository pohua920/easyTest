package com.tlg.aps.webService.claimBlockChainService.client.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumStateCode {
	A01("A01"), A021("A021"), A022("A022"), A023("A023"), A024("A024"), A025(
			"A025"), A026("A026"), A0291("A0291"), A0292("A0292"), A0293(
			"A0293"), A0294("A0294"), A03("A03"), A04("A04"), B00("B00"), C01(
			"C01"), C02("C02"), C03("C03"), C04("C04"), C05("C05"), C06("C06"), C07(
			"C07"), C08("C08"), C09("C09"), C10("C10"), C11("C11"), C12("C12"), C13(
			"C13"), C14("C14"), C15("C15");

	private String value;

	EnumStateCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumStateCode fromValue(String text) {
		for (EnumStateCode b : EnumStateCode.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
