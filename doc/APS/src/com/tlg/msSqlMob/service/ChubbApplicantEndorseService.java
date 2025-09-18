package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChubbApplicantEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public interface ChubbApplicantEndorseService {

	@SuppressWarnings("rawtypes")
	public int countChubbApplicantEndorse(Map params) throws SystemException, Exception;
	
	public Result findChubbApplicantEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findChubbApplicantEndorseByParams(Map params) throws SystemException, Exception;

	public Result findChubbApplicantEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateChubbApplicantEndorse(ChubbApplicantEndorse entity) throws SystemException, Exception;

	public Result insertChubbApplicantEndorse(ChubbApplicantEndorse entity) throws SystemException, Exception;

	public Result removeChubbApplicantEndorse(String transactionId) throws SystemException, Exception;
	
}
