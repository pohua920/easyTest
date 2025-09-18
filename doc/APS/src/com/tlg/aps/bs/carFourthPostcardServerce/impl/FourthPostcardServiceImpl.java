package com.tlg.aps.bs.carFourthPostcardServerce.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.carFourthPostcardServerce.FourthPostcardService;
import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.entity.TiiTvmcq;
import com.tlg.prpins.service.RenewalnoticeService;
import com.tlg.prpins.service.TiiTvmcqService;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FourthPostcardServiceImpl implements FourthPostcardService {

	private TiiTvmcqService tiiTvmcqService;
	private RenewalnoticeService renewalnoticeService;

	@Override
	public void insertTiiTvmcq(TiiTvmcq tiiTvmcq) throws Exception {
		tiiTvmcqService.insertTiiTvmcq(tiiTvmcq);
	}

	@Override
	public void updateTiiTvmcq(TiiTvmcq tiiTvmcq) throws Exception {
		tiiTvmcqService.updateTiiTvmcq(tiiTvmcq);
	}
	
	@Override
	public void insertRenewalnotice(Renewalnotice renewalnotice) throws Exception {
		renewalnoticeService.insertRenewalnotice(renewalnotice);
	}

	public TiiTvmcqService getTiiTvmcqService() {
		return tiiTvmcqService;
	}

	public void setTiiTvmcqService(TiiTvmcqService tiiTvmcqService) {
		this.tiiTvmcqService = tiiTvmcqService;
	}

	public RenewalnoticeService getRenewalnoticeService() {
		return renewalnoticeService;
	}

	public void setRenewalnoticeService(RenewalnoticeService renewalnoticeService) {
		this.renewalnoticeService = renewalnoticeService;
	}
}
