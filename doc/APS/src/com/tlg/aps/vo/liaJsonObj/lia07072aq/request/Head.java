
package com.tlg.aps.vo.liaJsonObj.lia07072aq.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "checkno",
    "total",
    "datastatus"
})
public class Head implements Serializable{

	private String appCode;
    @JsonProperty("checkno")
    private String checkno;
    @JsonProperty("total")
    private String total;
    @JsonProperty("datastatus")
    private String datastatus;

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

    @JsonProperty("datastatus")
    public String getDatastatus() {
        return datastatus;
    }

    @JsonProperty("datastatus")
    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus;
    }

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}
