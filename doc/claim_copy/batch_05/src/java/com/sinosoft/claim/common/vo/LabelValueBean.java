package com.sinosoft.claim.common.vo;

/***
 * 标签替换类，用於替换原有Apach类
 * @author 中科软
 */
public class LabelValueBean {
	/** 键信息 */
	private String key = "";
	/** 值信息 */
	private String value = "";

	public LabelValueBean(String value, String key) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
