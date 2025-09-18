package com.tlg.aps.bs.othBatchPassbookServerce;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public interface PassbookCalSpService {

	public Result excuteAndCallSp(String userId, String programId, String riskcode, 
			String spName, String policyno, String type, Date undate) throws Exception;
}
