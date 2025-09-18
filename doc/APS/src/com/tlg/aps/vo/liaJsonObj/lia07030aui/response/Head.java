
package com.tlg.aps.vo.liaJsonObj.lia07030aui.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "checkno",
    "total",
    "success",
    "fail",
    "starttime",
    "endtime",
    "rtncode",
    "rtnmsg"
})
public class Head implements Serializable
{

    @JsonProperty("checkno")
    private String checkno;
    @JsonProperty("total")
    private String total;
    @JsonProperty("success")
    private String success;
    @JsonProperty("fail")
    private String fail;
    @JsonProperty("starttime")
    private String starttime;
    @JsonProperty("endtime")
    private String endtime;
    @JsonProperty("rtncode")
    private String rtncode;
    @JsonProperty("rtnmsg")
    private String rtnmsg;

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

    @JsonProperty("success")
    public String getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(String success) {
        this.success = success;
    }

    @JsonProperty("fail")
    public String getFail() {
        return fail;
    }

    @JsonProperty("fail")
    public void setFail(String fail) {
        this.fail = fail;
    }

    @JsonProperty("starttime")
    public String getStarttime() {
        return starttime;
    }

    @JsonProperty("starttime")
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    @JsonProperty("endtime")
    public String getEndtime() {
        return endtime;
    }

    @JsonProperty("endtime")
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    @JsonProperty("rtncode")
    public String getRtncode() {
        return rtncode;
    }

    @JsonProperty("rtncode")
    public void setRtncode(String rtncode) {
        this.rtncode = rtncode;
    }

    @JsonProperty("rtnmsg")
    public String getRtnmsg() {
        return rtnmsg;
    }

    @JsonProperty("rtnmsg")
    public void setRtnmsg(String rtnmsg) {
        this.rtnmsg = rtnmsg;
    }

}
