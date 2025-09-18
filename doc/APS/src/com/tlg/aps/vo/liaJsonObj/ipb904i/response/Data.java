
package com.tlg.aps.vo.liaJsonObj.ipb904i.response;

import java.io.Serializable;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "moudle",
    "count"
})
@Generated("jsonschema2pojo")
public class Data implements Serializable{


	private static final long serialVersionUID = 1L;
	@JsonProperty("moudle")
    private String moudle;
    @JsonProperty("count")
    private String count;

    @JsonProperty("moudle")
    public String getMoudle() {
        return moudle;
    }

    @JsonProperty("moudle")
    public void setMoudle(String moudle) {
        this.moudle = moudle;
    }

    @JsonProperty("count")
    public String getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(String count) {
        this.count = count;
    }

}
