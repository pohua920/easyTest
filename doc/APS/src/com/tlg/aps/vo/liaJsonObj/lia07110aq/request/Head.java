
package com.tlg.aps.vo.liaJsonObj.lia07110aq.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "checkno",
    "procdatesta",
    "procdateend"
})
public class Head implements Serializable{

    @JsonProperty("checkno")
    private String checkno;
    @JsonProperty("procdatesta")
    private String procdatesta;
    @JsonProperty("procdateend")
    private String procdateend;
   
    @JsonProperty("checkno")
    public String getCheckno() {
        return checkno;
    }

    @JsonProperty("checkno")
    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    @JsonProperty("procdatesta")
    public String getProcdatesta() {
        return procdatesta;
    }

    @JsonProperty("procdatesta")
    public void setProcdatesta(String procdatesta) {
        this.procdatesta = procdatesta;
    }

    @JsonProperty("procdateend")
    public String getProcdateend() {
        return procdateend;
    }

    @JsonProperty("procdateend")
    public void setProcdateend(String procdateend) {
        this.procdateend = procdateend;
    }


}
