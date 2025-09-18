package com.tlg.aps.bs.firPanhsinFeedbackFile;

import java.util.Map;

import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.exception.SystemException;

public interface RenewalDataProcessingService {
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	
	public Map<String,Object> basicDataCheck(Aps016DetailVo aps016DetailVo, String userId) throws SystemException, Exception;
	
	public String theFinalDataCheck(Aps016DetailVo aps016DetailVo) throws Exception;
}
