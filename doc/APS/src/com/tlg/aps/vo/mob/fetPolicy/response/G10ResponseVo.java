package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class G10ResponseVo {
	@SerializedName("RESULT")
	private String result;
	@SerializedName("RESULT_MSG")
	private String resultMsg;
	
	
	@Override
	public String toString() {
		return "G10ResponseVo [result=" + result + ", resultMsg=" + resultMsg + "]";
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
