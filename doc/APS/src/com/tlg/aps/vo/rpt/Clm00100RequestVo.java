package com.tlg.aps.vo.rpt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clm00100RequestVo implements Serializable {

	@JsonProperty("encodeStr")
	private String encodeStr;

	@JsonProperty("encodeStr")
	public String getEncodeStr() {
		return encodeStr;
	}

	@JsonProperty("encodeStr")
	public void setEncodeStr(String encodeStr) {
		this.encodeStr = encodeStr;
	}
}
