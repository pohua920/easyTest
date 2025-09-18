package com.tlg.aps.bs.carBrokenPolicyDataServerce.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.carBrokenPolicyDataServerce.BrokenPolicyDataService;
import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.entity.TiiTvbcm;
import com.tlg.prpins.service.RenewalnoticeService;
import com.tlg.prpins.service.TiiTvbcmService;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class BrokenPolicyDataServiceImpl implements BrokenPolicyDataService {

	private TiiTvbcmService tiiTvbcmService;
	private RenewalnoticeService renewalnoticeService;

	@Override
	public void insertTiiTvbcm(TiiTvbcm tiiTvbcm) throws Exception {
		tiiTvbcmService.insertTiiTvbcm(tiiTvbcm);
	}

	@Override
	public void updateTiiTvbcm(TiiTvbcm tiiTvbcm) throws Exception {
		tiiTvbcmService.updateTiiTvbcm(tiiTvbcm);
	}
	
	@Override
	public void insertRenewalnotice(Renewalnotice renewalnotice) throws Exception {
		renewalnoticeService.insertRenewalnotice(renewalnotice);
	}

	public TiiTvbcmService getTiiTvbcmService() {
		return tiiTvbcmService;
	}

	public void setTiiTvbcmService(TiiTvbcmService tiiTvbcmService) {
		this.tiiTvbcmService = tiiTvbcmService;
	}

	public RenewalnoticeService getRenewalnoticeService() {
		return renewalnoticeService;
	}

	public void setRenewalnoticeService(RenewalnoticeService renewalnoticeService) {
		this.renewalnoticeService = renewalnoticeService;
	}
}
