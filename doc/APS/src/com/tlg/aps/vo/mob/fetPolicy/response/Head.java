package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Head {
	
	@SerializedName("count")
	private String count;
	
	@SerializedName("logId")
	private String logId;

	@SerializedName("rptBatchNo")
	private String rptBatchNo;
	
	@SerializedName("rptId")
	private String rptId;
	
	@SerializedName("totalCount")
	private String totalCount;
	
	@SerializedName("totalRecord")
	private String totalRecord;
	
	@Override
	public String toString() {
		return "Head [count=" + count + ", logId=" + logId + ", rptBatchNo=" + rptBatchNo + ", rptId=" + rptId
				+ ", totalCount=" + totalCount + ", totalRecord=" + totalRecord + "]";
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
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

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
