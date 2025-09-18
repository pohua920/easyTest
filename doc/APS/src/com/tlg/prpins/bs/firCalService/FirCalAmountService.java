package com.tlg.prpins.bs.firCalService;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;

public interface FirCalAmountService {
	
	/**
	 * 地震及住火保額計算
	 * 
	 * @param firPremcalcTmp 火險保額計算物件
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public void getFirAmountCal(FirPremcalcTmp firPremcalcTmp) throws SystemException, Exception;

}
