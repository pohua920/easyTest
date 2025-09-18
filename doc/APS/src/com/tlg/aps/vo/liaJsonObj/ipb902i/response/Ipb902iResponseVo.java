
package com.tlg.aps.vo.liaJsonObj.ipb902i.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "success",
    "fail",
    "datas"
})
public class Ipb902iResponseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("total")
    private Integer total;
    @JsonProperty("success")
    private Integer success;
    @JsonProperty("fail")
    private Integer fail;
    @JsonProperty("datas")
    private List<Data> datas = null;
    
    /**
     * 自定義變數，非規格書所有
     */
    @JsonProperty("code")
    private String code;
    /**
     * 自定義變數，非規格書所有
     */
    @JsonProperty("msg")
    private String msg;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("success")
    public Integer getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Integer success) {
        this.success = success;
    }

    @JsonProperty("fail")
    public Integer getFail() {
        return fail;
    }

    @JsonProperty("fail")
    public void setFail(Integer fail) {
        this.fail = fail;
    }

    @JsonProperty("datas")
    public List<Data> getDatas() {
        return datas;
    }

    @JsonProperty("datas")
    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
