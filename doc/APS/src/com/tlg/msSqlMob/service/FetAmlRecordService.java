package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetAmlRecord;
import com.tlg.util.Result;

/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 */
public interface FetAmlRecordService {
	/** 以合約編號查詢FET_AML_RECORD */
	public Result findFetAmlRecordByContractId(String contractId) throws SystemException, Exception;

	public int countFetAmlRecord(Map params) throws SystemException, Exception;

	public Result findFetAmlRecordByParams(Map params) throws SystemException, Exception;

	public Result findFetAmlRecordByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetAmlRecord(FetAmlRecord entity) throws SystemException, Exception;

	public Result insertFetAmlRecord(FetAmlRecord entity) throws SystemException, Exception;

	public Result removeFetAmlRecord(String transactionId) throws SystemException, Exception;

}
