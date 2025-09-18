
package com.tlg.aps.vo.liaJsonObj.lia07110aq.response;

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
    "name",
    "idno",
    "birdate",
    "sex",
    "con",
    "condate",
    "adddate"
})
public class Row implements Serializable{

    @JsonProperty("dataserno")
    private String dataserno;
    @JsonProperty("cmptype")
    private String cmptype;
    @JsonProperty("cmpno")
    private String cmpno;
    @JsonProperty("name")
    private String name;
    @JsonProperty("idno")
    private String idno;
    @JsonProperty("birdate")
    private String birdate;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("con")
    private String con;
    @JsonProperty("condate")
    private String condate;
    @JsonProperty("adddate")
    private String adddate;


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

    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonProperty("con")
    public String getCon() {
        return con;
    }

    @JsonProperty("con")
    public void setCon(String con) {
        this.con = con;
    }

    @JsonProperty("condate")
    public String getCondate() {
        return condate;
    }

    @JsonProperty("condate")
    public void setCondate(String condate) {
        this.condate = condate;
    }

    @JsonProperty("adddate")
    public String getAdddate() {
        return adddate;
    }

    @JsonProperty("adddate")
    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

 }
