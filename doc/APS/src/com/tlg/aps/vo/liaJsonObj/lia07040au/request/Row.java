
package com.tlg.aps.vo.liaJsonObj.lia07040au.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dataserno",
    "cmptype",
    "cmpno",
    "insno",
    "instype",
    "name",
    "inscnt",
    "valdate",
    "ovrdate"
})
public class Row implements Serializable
{

    @JsonProperty("dataserno")
    private String dataserno;
    @JsonProperty("cmptype")
    private String cmptype;
    @JsonProperty("cmpno")
    private String cmpno;
    @JsonProperty("insno")
    private String insno;
    @JsonProperty("instype")
    private String instype;
    @JsonProperty("name")
    private String name;
    @JsonProperty("inscnt")
    private String inscnt;
    @JsonProperty("valdate")
    private String valdate;
    @JsonProperty("ovrdate")
    private String ovrdate;

    @JsonProperty("dataserno")
    public String getDataserno() {
        return dataserno;
    }

    @JsonProperty("dataserno")
    public void setDataserno(String dataserno) {
        this.dataserno = dataserno;
    }

    @JsonProperty("cmptype")
    public String getCmptype() {
        return cmptype;
    }

    @JsonProperty("cmptype")
    public void setCmptype(String cmptype) {
        this.cmptype = cmptype;
    }

    @JsonProperty("cmpno")
    public String getCmpno() {
        return cmpno;
    }

    @JsonProperty("cmpno")
    public void setCmpno(String cmpno) {
        this.cmpno = cmpno;
    }

    @JsonProperty("insno")
    public String getInsno() {
        return insno;
    }

    @JsonProperty("insno")
    public void setInsno(String insno) {
        this.insno = insno;
    }

    @JsonProperty("instype")
    public String getInstype() {
        return instype;
    }

    @JsonProperty("instype")
    public void setInstype(String instype) {
        this.instype = instype;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("inscnt")
    public String getInscnt() {
        return inscnt;
    }

    @JsonProperty("inscnt")
    public void setInscnt(String inscnt) {
        this.inscnt = inscnt;
    }

    @JsonProperty("valdate")
    public String getValdate() {
        return valdate;
    }

    @JsonProperty("valdate")
    public void setValdate(String valdate) {
        this.valdate = valdate;
    }

    @JsonProperty("ovrdate")
    public String getOvrdate() {
        return ovrdate;
    }

    @JsonProperty("ovrdate")
    public void setOvrdate(String ovrdate) {
        this.ovrdate = ovrdate;
    }

}
