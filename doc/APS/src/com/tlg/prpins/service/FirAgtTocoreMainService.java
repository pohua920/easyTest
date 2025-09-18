package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtTocoreMainService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	public Result findFirAgtTocoreMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtTocoreMainByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtTocoreMain(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtTocoreMain(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception;
	
	public Result updateFirAgtTocoreMain(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception;
	
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start */
	public Result findBotReFileDataByParams(Map params) throws SystemException, Exception;
	
	public Result findBotEnFileDataByParams(Map params) throws SystemException, Exception;
	
	public Result findDistinctExtracomcodeByParams(Map params) throws SystemException, Exception;
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end */
	
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	public Result findYcbRnFileDataByParams(Map params) throws SystemException, Exception;
	
	public Result findYcbEnFileDataByParams(Map params) throws SystemException, Exception;
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	
	/**
	 * 查詢元大指定檔案的資料明細
	 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	 * @param ycbFile
	 * @param params
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result findYcbFileDataByParams(EnumYCBFile ycbFile, Map params) throws SystemException, Exception; 
	
	//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	public Result updateFirAgtTocoreMainByOid(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception;
}
