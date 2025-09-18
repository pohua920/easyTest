
package com.tlg.aps.vo.liaJsonObj.lia07030aui.request;

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
    "type",
    "insno",
    "name",
    "idno",
    "birdate",
    "condate",
    "valdate",
    "ovrdate",
    "itema",
    "filldate",
    "instype",
    "kind"
})
public class Row implements Serializable
{

    @JsonProperty("dataserno")
    private String dataserno;
    @JsonProperty("cmptype")
    private String cmptype;
    @JsonProperty("cmpno")
    private String cmpno;
    @JsonProperty("type")
    private String type;
    @JsonProperty("insno")
    private String insno;
    @JsonProperty("name")
    private String name;
    @JsonProperty("idno")
    private String idno;
    @JsonProperty("birdate")
    private String birdate;
    @JsonProperty("condate")
    private String condate;
    @JsonProperty("valdate")
    private String valdate;
    @JsonProperty("ovrdate")
    private String ovrdate;
    @JsonProperty("itema")
    private String itema;
    @JsonProperty("filldate")
    private String filldate;
    @JsonProperty("instype")
    private String instype;
    @JsonProperty("kind")
    private String kind;

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

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("insno")
    public String getInsno() {
        return insno;
    }

    @JsonProperty("insno")
    public void setInsno(String insno) {
        this.insno = insno;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("idno")
    public String getIdno() {
        return idno;
    }

    @JsonProperty("idno")
    public void setIdno(String idno) {
        this.idno = idno;
    }

    @JsonProperty("birdate")
    public String getBirdate() {
        return birdate;
    }

    @JsonProperty("birdate")
    public void setBirdate(String birdate) {
        this.birdate = birdate;
    }

    @JsonProperty("condate")
    public String getCondate() {
        return condate;
    }

    @JsonProperty("condate")
    public void setCondate(String condate) {
        this.condate = condate;
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

    @JsonProperty("itema")
    public String getItema() {
        return itema;
    }

    @JsonProperty("itema")
    public void setItema(String itema) {
        this.itema = itema;
    }

    @JsonProperty("filldate")
    public String getFilldate() {
        return filldate;
    }

    @JsonProperty("filldate")
    public void setFilldate(String filldate) {
        this.filldate = filldate;
    }

    @JsonProperty("instype")
    public String getInstype() {
        return instype;
    }

    @JsonProperty("instype")
    public void setInstype(String instype) {
        this.instype = instype;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

}
