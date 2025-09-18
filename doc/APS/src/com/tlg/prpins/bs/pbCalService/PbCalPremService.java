package com.tlg.prpins.bs.pbCalService;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.PbPremcalcTmp;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;

public interface PbCalPremService {
	
	/**
	 * PB -公共意外責任險-處所計算
	 * 
	 * @param pbPremcalcTmp
	 * @param pbPremcalcTmpdtl
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getPbPlacePrem(PbPremcalcTmp pbPremcalcTmp, PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception;
	

}
