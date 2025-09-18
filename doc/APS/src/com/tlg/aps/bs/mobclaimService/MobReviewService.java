package com.tlg.aps.bs.mobclaimService;

import com.tlg.util.Result;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
public interface MobReviewService {
	
	public Result reviewClaimData(String userId, String wda00) throws Exception;
	
}
