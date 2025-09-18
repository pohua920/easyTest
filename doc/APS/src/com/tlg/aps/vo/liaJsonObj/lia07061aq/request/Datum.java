
package com.tlg.aps.vo.liaJsonObj.lia07061aq.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "keyidno",
    "keyaskidno",
    "keybirdate",
    "keyaskbirdate"
})
public class Datum implements Serializable
{

    @JsonProperty("keyidno")
    private String keyidno;
    @JsonProperty("keyaskidno")
    private String keyaskidno;
    @JsonProperty("keybirdate")
    private String keybirdate;
    @JsonProperty("keyaskbirdate")
    private String keyaskbirdate;

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

}
