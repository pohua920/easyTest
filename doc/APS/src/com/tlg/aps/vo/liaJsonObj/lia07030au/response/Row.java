
package com.tlg.aps.vo.liaJsonObj.lia07030au.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dataserno",
    "rtncode",
    "rtnmsg"
})
public class Row {

    @JsonProperty("dataserno")
    private String dataserno;
    @JsonProperty("rtncode")
    private String rtncode;
    @JsonProperty("rtnmsg")
    private String rtnmsg;

    @JsonProperty("dataserno")
    public String getDataserno() {
        return dataserno;
    }

    @JsonProperty("dataserno")
    public void setDataserno(String dataserno) {
        this.dataserno = dataserno;
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
