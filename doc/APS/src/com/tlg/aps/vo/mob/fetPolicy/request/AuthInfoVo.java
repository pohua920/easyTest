package com.tlg.aps.vo.mob.fetPolicy.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthInfoVo {
	
	@SerializedName("channelID")
	private String channelID;
	
	@SerializedName("userID")
	private String userID;
	
	@SerializedName("password")
	private String password;
	
	@Override
	public String toString() {
		return "AuthInfoVo [channelID=" + channelID + ", userID=" + userID + ", password=" + password + "]";
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
