package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets LossCity
 */
public enum EnumLossCity {
	LC01("LC01"), LC06("LC06"), LC11("LC11"), LC12("LC12"), LC13("LC13"), LC14(
			"LC14"), LC15("LC15"), LC41("LC41"), LC43("LC43"), LC44("LC44"), LC45(
			"LC45"), LC61("LC61"), LC62("LC62"), LC64("LC64"), LC66("LC66"), LC67(
			"LC67"), LC81("LC81"), LC82("LC82"), LC90("LC90"), LC91("LC91");

	private String value;

	EnumLossCity(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumLossCity fromValue(String text) {
		for (EnumLossCity b : EnumLossCity.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
