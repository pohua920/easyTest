package com.tlg.aps.bs.firAgtrnYcbN1ImportService;

import java.io.File;

import com.tlg.util.Result;
import com.tlg.util.UserInfo;

/**
 * mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
 */
public interface FirAgtrnYcbN1ImportService {

	//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
	public Result importFirAgtrnYcbN1(String batchNo, File uploadFile, UserInfo userInfo, String[] handleridentifynumber) throws Exception;
	
	public Result updateFirAgtrnTocoreMainAndDtl(String batchNo, UserInfo userInfo) throws Exception;
}
