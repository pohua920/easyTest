package com.tlg.aps.bs.claimBlockchainService.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.claimBlockchainService.ClaimCompulsoryJobNTService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryJob;
import com.tlg.prpins.service.ClaimCompulsoryJobService;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class ClaimCompulsoryJobNTServiceImpl implements ClaimCompulsoryJobNTService{

	private ClaimCompulsoryJobService claimCompulsoryJobService;
	

	@Override
	public Result updateClaimCompulsoryJob(ClaimCompulsoryJob claimCompulsoryJob) throws SystemException, Exception {
		return claimCompulsoryJobService.updateClaimCompulsoryJob(claimCompulsoryJob);
	}

	@Override
	public Result insertClaimCompulsoryJob(ClaimCompulsoryJob claimCompulsoryJob) throws SystemException, Exception {
		return claimCompulsoryJobService.insertClaimCompulsoryJob(claimCompulsoryJob);
	}

	@Override
	public Result removeClaimCompulsoryJob(String oid) throws SystemException, Exception {
		return claimCompulsoryJobService.removeClaimCompulsoryJob(oid);
	}

	public ClaimCompulsoryJobService getClaimCompulsoryJobService() {
		return claimCompulsoryJobService;
	}

	public void setClaimCompulsoryJobService(
			ClaimCompulsoryJobService claimCompulsoryJobService) {
		this.claimCompulsoryJobService = claimCompulsoryJobService;
	}
}
