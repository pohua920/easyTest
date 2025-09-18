package com.tlg.aps.bs.firBotRenewalFileService;

import java.util.Map;

import com.tlg.aps.vo.Aps055BotDetailVo;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
public interface FirBotMaintainService {
	
	Map<String, Object> basicDataCheck(Aps055BotDetailVo botDetailVo, String userId, String checkType) throws Exception;
	
}
