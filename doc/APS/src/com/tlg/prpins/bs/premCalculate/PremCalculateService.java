package com.tlg.prpins.bs.premCalculate;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.PbPremcalcTmp;

public interface PremCalculateService {
	
	/**
	 * 新增火險保費計算資料
	 * 
	 * @param firPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void insertFirCalData(FirPremcalcTmp firPremcalcTmp)throws SystemException,Exception;
	
	/**
	 * 修改火險保費計算資料
	 * 
	 * @param firPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void updateFirCalData(FirPremcalcTmp firPremcalcTmp)throws SystemException,Exception;
	
	/**
	 * 新增公共意外責任險保費計算資料
	 * 
	 * @param pbPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void insertPbCalData(PbPremcalcTmp pbPremcalcTmp)throws SystemException,Exception;
	
	/**
	 * 修改公共意外責任險保費計算資料
	 * 
	 * @param pbPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void updatePbCalData(PbPremcalcTmp pbPremcalcTmp)throws SystemException,Exception;
	
	/**
	 * 火險保費計算
	 * 
	 * @param firPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void firCalPrem(FirPremcalcTmp firPremcalcTmp)throws SystemException,Exception;
	
	/**
	 * 公共意外責任險保費計算
	 * 
	 * @param pbPremcalcTmp
	 * @throws SystemException
	 * @throws Exception
	 */
	public void pbCalPrem(PbPremcalcTmp pbPremcalcTmp)throws SystemException,Exception;
	


}
