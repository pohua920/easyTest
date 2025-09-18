package com.tlg.aps.vo.mob.fetPolicy.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonSerialize
public class ReqContentVo {

	@SerializedName("RPT_BATCH_NO")
    private String rptBatchNo;
	
	@SerializedName("RPT_ID")
	private String rptId;
	
	@SerializedName("COUNT")
	private String count;
	
	@SerializedName("USER_ID")
	private String userId;
	
	@Override
	public String toString() {
		return "ReqContentVo [rptBatchNo=" + rptBatchNo + ", rptId=" + rptId + ", count=" + count + ", userId=" + userId
				+ "]";
	}

	public String getRptBatchNo() {
		return rptBatchNo;
	}

	public void setRptBatchNo(String rptBatchNo) {
		this.rptBatchNo = rptBatchNo;
	}

	public String getRptId() {
		return rptId;
	}

	public void setRptId(String rptId) {
		this.rptId = rptId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
