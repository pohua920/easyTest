package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MdiDailyReport {
	
	@SerializedName("channelID")
	private String channelID;
	
	@SerializedName("content")
	private String content;
	
	@SerializedName("head")
	private Head head;
	
	@Override
	public String toString() {
		return "MdiDailyReport [channelID=" + channelID + ", content=" + content + ", head=" + head
				+ ", getChannelID()=" + getChannelID() + ", getContent()=" + getContent() + ", getHead()=" + getHead()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}
	
}
