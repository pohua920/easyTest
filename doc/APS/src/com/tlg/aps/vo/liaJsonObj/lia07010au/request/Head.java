
package com.tlg.aps.vo.liaJsonObj.lia07010au.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "checkno",
    "total"
})
public class Head implements Serializable
{
	@JsonProperty("appCode")
    private String appCode;
    @JsonProperty("checkno")
    private String checkno;
    @JsonProperty("total")
    private String total;

    @JsonProperty("checkno")
    public String getCheckno() {
        return checkno;
    }

    @JsonProperty("checkno")
    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
    }

    @JsonProperty("appCode")
	public String getAppCode() {
		return appCode;
	}

    @JsonProperty("appCode")
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}
