
package com.tlg.aps.vo.liaJsonObj.lia07061aq.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "keyidno",
    "keyaskidno",
    "keybirdate",
    "keyaskbirdate",
    "rtncode",
    "rtnmsg",
    "row"
})
public class Datum  implements Serializable{

    @JsonProperty("keyidno")
    private String keyidno;
    @JsonProperty("keyaskidno")
    private String keyaskidno;
    @JsonProperty("keybirdate")
    private String keybirdate;
    @JsonProperty("keyaskbirdate")
    private String keyaskbirdate;
    @JsonProperty("rtncode")
    private String rtncode;
    @JsonProperty("rtnmsg")
    private String rtnmsg;
    @JsonProperty("row")
    private List<Row> row = null;

    @JsonProperty("keyidno")
    public String getKeyidno() {
        return keyidno;
    }

    @JsonProperty("keyidno")
    public void setKeyidno(String keyidno) {
        this.keyidno = keyidno;
    }

    @JsonProperty("keyaskidno")
    public String getKeyaskidno() {
        return keyaskidno;
    }

    @JsonProperty("keyaskidno")
    public void setKeyaskidno(String keyaskidno) {
        this.keyaskidno = keyaskidno;
    }

    @JsonProperty("keybirdate")
    public String getKeybirdate() {
        return keybirdate;
    }

    @JsonProperty("keybirdate")
    public void setKeybirdate(String keybirdate) {
        this.keybirdate = keybirdate;
    }

    @JsonProperty("keyaskbirdate")
    public String getKeyaskbirdate() {
        return keyaskbirdate;
    }

    @JsonProperty("keyaskbirdate")
    public void setKeyaskbirdate(String keyaskbirdate) {
        this.keyaskbirdate = keyaskbirdate;
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

    @JsonProperty("row")
    public List<Row> getRow() {
        return row;
    }

    @JsonProperty("row")
    public void setRow(List<Row> row) {
        this.row = row;
    }

}
