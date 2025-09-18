package com.tlg.aps.bs.firYcbRenewalFileService;

import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
public interface FirYcbGenFileService {
	
	
	public Result genRnFile(String batchNo, String userId) throws Exception;
	public Result genEnFile(String batchNo, String userId) throws SystemException, Exception;
	
	/**
	 * 傳入檔案類型  產生EXCEL檔案 
	 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	 * @param ycbFile 
	 * @param batchNo
	 * @param userId
	 * @return Result 
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result genXlsFile(EnumYCBFile ycbFile, String batchNo, String userId) throws SystemException, Exception;
	
}
