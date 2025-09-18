package com.tlg.aps.webService.claimBlockChainService.client.enums;

import java.util.HashMap;
import java.util.Map;

/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 */
/** 行動裝置險-通路別*/
public enum EnumMobileChannelType {
	EBU("E","EBU"),
	ARCOA("A","全虹"),
	FRANCHISE("F","加盟"),
	RETAIL("R","直營");
	private final String code;
	private final String value;
	private static final Map<String,EnumMobileChannelType> CODE_MAP = new HashMap<String,EnumMobileChannelType>();
	static {
		for(EnumMobileChannelType channel : EnumMobileChannelType.values()) {
			CODE_MAP.put(channel.getCode(), channel);
		}
	}
	private EnumMobileChannelType(String code, String value) {
		this.code = code;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	public static EnumMobileChannelType fromCode(String code) {
		return CODE_MAP.get(code);
	}
	/** 行動裝置險 通路別: E:EBU*/
//	public static final String MOB_CHANNEL_EBU = "E";
//	/** 行動裝置險 通路別: A:全虹*/
//	public static final String MOB_CHANNEL_ARCOA = "A";
//	/** 行動裝置險 通路別: F:加盟*/
//	public static final String MOB_CHANNEL_FRANCHISE = "F";
//	/** 行動裝置險 通路別: R:直營*/
//	public static final String MOB_CHANNEL_RETAIL = "R";
}
