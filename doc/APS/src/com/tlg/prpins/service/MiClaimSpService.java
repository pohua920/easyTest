package com.tlg.prpins.service;

import java.util.Map;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
public interface MiClaimSpService {
	
	public Map<String, Object> runSpFetmispTonewclaim(Map<String, Object> params) throws Exception;

}
