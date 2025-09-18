package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public interface FirCtbcRnproposalMainService {
	
	public Result findFirCtbcRnproposalMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
	@SuppressWarnings("rawtypes")
	public int countFirCtbcRnproposalMain(Map params) throws SystemException, Exception;
	
	public Result insertFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRnproposalMainByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain) throws SystemException, Exception;

}
