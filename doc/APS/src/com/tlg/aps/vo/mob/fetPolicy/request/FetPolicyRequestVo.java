package com.tlg.aps.vo.mob.fetPolicy.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetPolicyRequestVo {
	
	@SerializedName("authInfo")
    private AuthInfoVo authInfo;
	
	@SerializedName("condition")
    private ConditionVo condition;
	
	@Override
	public String toString() {
		return "FetPolicyRequestVo [authInfo=" + authInfo + ", condition=" + condition + "]";
	}

	public AuthInfoVo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfoVo authInfo) {
		this.authInfo = authInfo;
	}

	public ConditionVo getCondition() {
		return condition;
	}

	public void setCondition(ConditionVo condition) {
		this.condition = condition;
	}
	
}
