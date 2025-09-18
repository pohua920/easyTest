package com.tlg.aps.bs.salesAgentDateAlertService;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.util.Result;

/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
public interface SalesAgentDateAlertService {

	public Result excute() throws Exception;
	
	public List<SalesAgentDateAlertVo> sendMailToAgent(Date sysdate) throws Exception;
	
	public File genTxtFile(Date sysdate) throws Exception;
	
	public String sendEmail(File file,List<SalesAgentDateAlertVo> mailVoList) throws Exception;
}
