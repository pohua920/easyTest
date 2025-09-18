package com.tlg.aps.vo.mob.fetPolicy.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionVo {
	
	@SerializedName("content")
	private String content;
	
	@Override
	public String toString() {
		return "ConditionVo [content=" + content + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
