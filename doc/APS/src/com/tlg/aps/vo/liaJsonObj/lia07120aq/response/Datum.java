
package com.tlg.aps.vo.liaJsonObj.lia07120aq.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rtncode",
    "rtnmsg",
    "row"
})
public class Datum implements Serializable{

    @JsonProperty("rtncode")
    private String rtncode;
    @JsonProperty("rtnmsg")
    private String rtnmsg;
    @JsonProperty("row")
    private List<Row> row = null;


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

    @JsonProperty("row")
    public List<Row> getRow() {
        return row;
    }

    @JsonProperty("row")
    public void setRow(List<Row> row) {
        this.row = row;
    }

}
