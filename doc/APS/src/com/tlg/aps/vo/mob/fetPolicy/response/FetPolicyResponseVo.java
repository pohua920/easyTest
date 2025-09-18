
package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetPolicyResponseVo {

    private ReturnHeader returnHeader;
    
    private MdiDailyReport mdiDailyReport;
    
	@Override
	public String toString() {
		return "FetPolicyResponseVo [returnHeader=" + returnHeader + ", mdiDailyReport=" + mdiDailyReport + "]";
	}

	public ReturnHeader getReturnHeader() {
		return returnHeader;
	}

	public void setReturnHeader(ReturnHeader returnHeader) {
		this.returnHeader = returnHeader;
	}

	public MdiDailyReport getMdiDailyReport() {
		return mdiDailyReport;
	}

	public void setMdiDailyReport(MdiDailyReport mdiDailyReport) {
		this.mdiDailyReport = mdiDailyReport;
	}

}
