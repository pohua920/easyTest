package com.tlg.aps.vo.liaJsonObj.ipb905i.response;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code", "msg", "data" })
@Generated("jsonschema2pojo")
public class Ipb905iResponseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("code")
	private String code;
	@JsonProperty("msg")
	private String msg;
	@JsonProperty("data")
	private List<Datum> data = null;

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("msg")
	public String getMsg() {
		return msg;
	}

	@JsonProperty("msg")
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@JsonProperty("data")
	public List<Datum> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<Datum> data) {
		this.data = data;
	}

}
