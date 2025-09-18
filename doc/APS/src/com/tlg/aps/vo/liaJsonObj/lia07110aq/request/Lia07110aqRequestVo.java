
package com.tlg.aps.vo.liaJsonObj.lia07110aq.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "head"
})
public class Lia07110aqRequestVo implements Serializable{

    @JsonProperty("head")
    private Head head;
    

    @JsonProperty("head")
    public Head getHead() {
        return head;
    }

    @JsonProperty("head")
    public void setHead(Head head) {
        this.head = head;
    }
}
