package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets EnumVehicleType
 */
public enum EnumVehicleType {
	VT01("VT01"), VT02("VT02"), VT03("VT03"), VT04("VT04"), VT05("VT05"), VT06(
			"VT06"), VT07("VT07"), VT08("VT08"), VT09("VT09"), VT10("VT10"), VT11(
			"VT11"), VT12("VT12"), VT13("VT13"), VT14("VT14"), VT15("VT15"), VT16(
			"VT16"), VT17("VT17"), VT18("VT18"), VT19("VT19"), VT20("VT20"), VT21(
			"VT21"), VT22("VT22"), VT23("VT23"), VT24("VT24"), VT25("VT25"), VT26(
			"VT26"), VT27("VT27"), VT28("VT28"), VT29("VT29"), VT30("VT30"), VT31(
			"VT31"), VT32("VT32"), VT33("VT33"), VT34("VT34"), VT35("VT35");

	private String value;

	EnumVehicleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumVehicleType fromValue(String text) {
		for (EnumVehicleType b : EnumVehicleType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
