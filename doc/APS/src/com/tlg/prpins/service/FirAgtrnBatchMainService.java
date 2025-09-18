package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtrnBatchMainService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	public Result findFirAgtrnBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnBatchMainByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnBatchMain(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) throws SystemException, Exception;
	
	public Result updateFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) throws SystemException, Exception;

	// mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
	public Result findFbrnDownloadData(Map params) throws SystemException, Exception;
	
	public Result findFirAgtrnBatchMainByUk(Map params) throws SystemException, Exception;
	
	// mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	public Result findBatchMainForBoprnByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start */
	public Result findBotrnDownloadData(Map params) throws SystemException, Exception;
	
	public Result findBatchMainForBotrnByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end */
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	public Result findBatchMainForBotrnIntoCore() throws SystemException, Exception;
	
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	public Result findBatchMainForYcbrnByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	public Result findYcbrnDownloadData(Map params) throws SystemException, Exception;
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	
}
