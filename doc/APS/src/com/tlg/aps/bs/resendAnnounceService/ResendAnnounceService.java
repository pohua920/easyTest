package com.tlg.aps.bs.resendAnnounceService;

import java.util.List;

import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface ResendAnnounceService {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	public Result resendAnnounce(String announceCase,String UserId,CwpAnnounceVo cwpAnnounceVo) throws SystemException,Exception;
	
	
	public Result resendAnnounce(String announceCase,String userId, List<CwpAnnounceVo> cwpAnnounceVoList) throws SystemException,Exception;
}
