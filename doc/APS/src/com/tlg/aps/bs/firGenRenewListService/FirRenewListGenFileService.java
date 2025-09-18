package com.tlg.aps.bs.firGenRenewListService;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
public interface FirRenewListGenFileService {
	
	public Result genCtbcFile(String batchNo, String userId, String fileType) throws Exception;
	
	public Result genNcoreFile(String batchNo, String userId, String fileType) throws Exception;
	
	public Result genNcoreaFile(String batchNo, String userId, String fileType) throws Exception;
	
	public Result genBopFile(String batchNo, String userId, String fileType) throws Exception;
	
	public Result genFbFile(String batchNo, String userId, String fileType) throws Exception;

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理
	public Result genPpFile(String batchNo, String userId, String rnYymm) throws Exception;

	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	public Map<String, String> changeHandler1code(String handler1code, String handle1name, String handleridentifynumber) throws SystemException, Exception;
}
