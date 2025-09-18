package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChubbInsuredEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public interface ChubbInsuredEndorseService {

	@SuppressWarnings("rawtypes")
	public int countChubbInsuredEndorse(Map params) throws SystemException, Exception;
	
	public Result findChubbInsuredEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findChubbInsuredEndorseByParams(Map params) throws SystemException, Exception;

	public Result findChubbInsuredEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateChubbInsuredEndorse(ChubbInsuredEndorse entity) throws SystemException, Exception;

	public Result insertChubbInsuredEndorse(ChubbInsuredEndorse entity) throws SystemException, Exception;

	public Result removeChubbInsuredEndorse(String transactionId) throws SystemException, Exception;
	
}
