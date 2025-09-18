package com.tlg.aps.bs.firFubonRenewalService;

import java.util.Map;

import com.tlg.aps.vo.Aps034FbDetailVo;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
public interface FirFubonMaintainService {
	
	Map<String, Object> basicDataCheck(Aps034FbDetailVo fbDetailVo, String userId, String checkType) throws Exception;
	
}
