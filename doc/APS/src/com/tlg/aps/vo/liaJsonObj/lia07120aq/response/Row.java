
package com.tlg.aps.vo.liaJsonObj.lia07120aq.response;

import java.io.Serializable;
import java.util.HashMap;
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
    "dataserno",
    "id",
    "codtype",
    "codcode",
    "codname",
    "statcode",
    "codename"
})
public class Row implements Serializable{

    @JsonProperty("dataserno")
    private String dataserno;
    @JsonProperty("id")
    private String id;
    @JsonProperty("codtype")
    private String codtype;
    @JsonProperty("codcode")
    private String codcode;
    @JsonProperty("codname")
    private String codname;
    @JsonProperty("statcode")
    private String statcode;
    @JsonProperty("codename")
    private String codename;

    @JsonProperty("dataserno")
    public String getDataserno() {
        return dataserno;
    }

    @JsonProperty("dataserno")
    public void setDataserno(String dataserno) {
        this.dataserno = dataserno;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("codtype")
    public String getCodtype() {
        return codtype;
    }

    @JsonProperty("codtype")
    public void setCodtype(String codtype) {
        this.codtype = codtype;
    }

    @JsonProperty("codcode")
    public String getCodcode() {
        return codcode;
    }

    @JsonProperty("codcode")
    public void setCodcode(String codcode) {
        this.codcode = codcode;
    }

    @JsonProperty("codname")
    public String getCodname() {
        return codname;
    }

    @JsonProperty("codname")
    public void setCodname(String codname) {
        this.codname = codname;
    }

    @JsonProperty("statcode")
    public String getStatcode() {
        return statcode;
    }

    @JsonProperty("statcode")
    public void setStatcode(String statcode) {
        this.statcode = statcode;
    }

    @JsonProperty("codename")
    public String getCodename() {
        return codename;
    }

    @JsonProperty("codename")
    public void setCodename(String codename) {
        this.codename = codename;
    }

}
