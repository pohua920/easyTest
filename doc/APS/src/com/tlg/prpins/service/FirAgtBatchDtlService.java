package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtBatchDtlService {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	public Result findAPS009Detail01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findAPS009Detail03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBatchDtlByParams(Map params) throws SystemException, Exception;
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
	public Result updateFirAgtBatchDtlByParams(Map<String,Object> params) throws SystemException, Exception;
	
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 start */
	public Result findAPS038Detail01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findAPS038Detail03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 end */
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	public Result findAPS041Dtl1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findAPS041Dtl2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */

	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start */
	public Result insertFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl) throws SystemException, Exception;
	
	public Result updateFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl) throws SystemException, Exception;
	
	public Result findFirAgtBatchDtlByUK(Map params) throws SystemException, Exception;
	
	public Result findForUbProposalEmail(Map params) throws SystemException, Exception;
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end */
	
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start */
	public Result findForUbBackFile(Map params) throws SystemException, Exception;
	
	public Result findForUbBackFileEmail(Map params) throws SystemException, Exception;
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end */
}
