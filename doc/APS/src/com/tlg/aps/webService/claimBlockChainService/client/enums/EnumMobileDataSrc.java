package com.tlg.aps.webService.claimBlockChainService.client.enums;

import java.util.HashMap;
import java.util.Map;
/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 */
public enum EnumMobileDataSrc {
	/* 遠傳保代 */
	FEPIA("FEPIA","FEPIA"),
	/* 遠傳電信estore */
	ESTORE("ESTORE","ESTORE"),
	/* 遠傳電信Friday */
	FRIDAY("Friday","Friday");
	
	private final String code;
	private final String value;
	private static final Map<String,EnumMobileDataSrc> CODE_MAP = new HashMap<String,EnumMobileDataSrc>();
	static {
		for(EnumMobileDataSrc dataSrc : EnumMobileDataSrc.values()) {
			CODE_MAP.put(dataSrc.getCode(), dataSrc);
		}
	}
	
	private EnumMobileDataSrc(String code, String value) {
		this.code = code;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	public static EnumMobileDataSrc fromCode(String code) {
		return CODE_MAP.get(code);
	}
}
