package com.tlg.aps.bs.miTerminationNoticeService.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.miTerminationNoticeService.MobileInsSmsNewTransService;
import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.MobileInsSms;
import com.tlg.xchg.service.MobileInsSmsService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class MobileInsSmsNewTransServiceImpl implements MobileInsSmsNewTransService{

	private MobileInsSmsService mobileInsSmsService;

	@Override
	public Result updateMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception {
		return this.mobileInsSmsService.updateMobileInsSms(mobileInsSms);
	}

	public MobileInsSmsService getMobileInsSmsService() {
		return mobileInsSmsService;
	}

	public void setMobileInsSmsService(MobileInsSmsService mobileInsSmsService) {
		this.mobileInsSmsService = mobileInsSmsService;
	}

}
