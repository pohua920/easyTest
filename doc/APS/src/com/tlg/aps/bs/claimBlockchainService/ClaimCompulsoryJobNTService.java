package com.tlg.aps.bs.claimBlockchainService;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryJob;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public interface ClaimCompulsoryJobNTService {

	public Result updateClaimCompulsoryJob(ClaimCompulsoryJob claimCompulsoryJob) throws SystemException, Exception;

	public Result insertClaimCompulsoryJob(ClaimCompulsoryJob claimCompulsoryJob) throws SystemException, Exception;

	public Result removeClaimCompulsoryJob(String oid) throws SystemException, Exception;
	
}
