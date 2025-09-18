
package com.tlg.aps.vo.liaJsonObj.lia07010au.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "keyidno",
    "row"
})
public class Datum implements Serializable
{

    @JsonProperty("keyidno")
    private String keyidno;
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

    @JsonProperty("row")
    public List<Row> getRow() {
        return row;
    }

    @JsonProperty("row")
    public void setRow(List<Row> row) {
        this.row = row;
    }

}
