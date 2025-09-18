package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface FirRenewListDtlService {
	
	public Result findFirRenewListDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirRenewListDtlByParams(Map params) throws SystemException, Exception;
	
	public int countFirRenewListDtl(Map params) throws SystemException, Exception;
	
	public Result insertFirRenewListDtl(FirRenewListDtl firRenewListDtl) throws SystemException, Exception;
	
	public Result updateFirRenewListDtl(FirRenewListDtl firRenewListDtl) throws SystemException, Exception;
	
	public Result removeFirRenewListDtl(BigDecimal oid) throws SystemException, Exception;
	
	public Result findRenewListForPrem(Map params) throws SystemException, Exception;
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 start*/
	public Result findForAps044DetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public int countForAps044Detail(Map params) throws SystemException, Exception;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 end*/
	
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程
	public Result findRenewListForMail(Map params) throws SystemException, Exception;
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	public Result findRenewListForOtherFile(Map params) throws SystemException, Exception;
	
	public Result findRenewListForCoreFile(Map params) throws SystemException, Exception;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}
