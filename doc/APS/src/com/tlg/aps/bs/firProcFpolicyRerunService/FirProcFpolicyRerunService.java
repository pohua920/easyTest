package com.tlg.aps.bs.firProcFpolicyRerunService;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
public interface FirProcFpolicyRerunService {
	
	public Result rerunFpolicy(Date excuteTime,String underwriteenddate,String userId,String programId) throws Exception;
}
