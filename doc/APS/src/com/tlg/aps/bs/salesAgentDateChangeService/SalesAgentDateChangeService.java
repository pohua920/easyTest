package com.tlg.aps.bs.salesAgentDateChangeService;

import java.util.List;

import com.tlg.aps.vo.SalesAgentDateReserveVo;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知 */
public interface SalesAgentDateChangeService {

	public Result excute() throws Exception;
	
	public List<SalesAgentDateReserveVo> sendMailToAgent() throws Exception;
}
