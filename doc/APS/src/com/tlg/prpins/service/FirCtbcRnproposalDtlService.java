package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public interface FirCtbcRnproposalDtlService {
	
	public Result findFirCtbcRnproposalDtlByPageInfoJoinPc(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertFirCtbcRnproposalDtl(FirCtbcRnproposalDtl firCtbcRnproposalDtl) throws SystemException, Exception;
	
	public Result findFirCtbcRnproposalDtlByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRnproposalDtl (FirCtbcRnproposalDtl firCtbcRnproposalDtl) throws SystemException, Exception;
}
