package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 */
public interface FirCtbcB2b2cService {

	public Result findFirCtbcB2b2cByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public int countFirCtbcB2b2c(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findFirCtbcB2b2cByParams(Map params) throws SystemException, Exception;

	public Result findFirCtbcB2b2cByUk(Map params) throws SystemException, Exception;

	//mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業
	public Result findAPS039DetailByParams(Map params) throws SystemException, Exception;
	
	public Result insertFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception;

	public Result updateFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception;

	public Result removeFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception;

}
