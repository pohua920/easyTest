package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtrnBatchDtlService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 */
	public Result findFirAgtrnBatchDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnBatchDtlByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnBatchDtl(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl) throws SystemException, Exception;
	
	public Result updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl) throws SystemException, Exception;
	
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	public Result findInsuredDataJoinTocoreMain(Map params) throws SystemException, Exception;
	
	public Result findFirAgtrnBatchDtlForExcel(String batchNo) throws SystemException, Exception;
	
	public Result findFirAgtrnBatchDtlForDetail(PageInfo pageInfo) throws SystemException, Exception;
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  end */
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start*/
	public Result findRenewalDataByBatchNo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findPolicyCountByBatchNo(Map params) throws SystemException, Exception;
	
	public Result findBopRnDataForExcelByBatchNo(Map params) throws SystemException, Exception;
	
	public Result findRenewalDataForExcelByBatchNo(String batchNo) throws SystemException, Exception;
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end*/
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	public Result findForFbrnDetail(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findFbrnInsuredData(Map params) throws SystemException, Exception;
	
	public Result findForFbRejectFile(Map params) throws SystemException, Exception;
	
	public Result findForFbRenewalData(Map params) throws SystemException, Exception;
	
	public int countCoreInsured(Map params) throws SystemException, Exception;
	
	public Result findCoreNotInsuredData(Map params) throws SystemException, Exception;
	
	public Result findForFbRenewalFkind(Map params) throws SystemException, Exception;
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
	
	/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start*/
	public Result findForBotrnDetail(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findBotRnDataForXlsByBatchNo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findBotrnInsuredData(Map params) throws SystemException, Exception;
	/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end*/
	
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 start*/
	public Result findAPS057Main2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public int countForAps057Main2(Map params) throws SystemException, Exception;
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 end*/
	
	/*mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start*/
	public Result findForYcbrnDetail(PageInfo pageInfo) throws SystemException, Exception;

	public Result findYcbRnDataForXlsByBatchNo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findYcbrnInsuredData(Map params) throws SystemException, Exception;
	/*mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end*/
}
