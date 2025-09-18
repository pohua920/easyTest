package com.tlg.aps.bs.firYcbRenewalFileService;

import java.util.Map;

import com.tlg.aps.vo.Aps060YcbDetailVo;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
public interface FirYcbMaintainService {
	
	Map<String, Object> basicDataCheck(Aps060YcbDetailVo botDetailVo, String userId, String checkType) throws Exception;
	
}
