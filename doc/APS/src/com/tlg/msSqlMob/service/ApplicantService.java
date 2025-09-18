package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Applicant;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ApplicantService {

	public int countApplicant(Map params) throws SystemException, Exception;
	
	public Result findApplicantByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findApplicantByParams(Map params) throws SystemException, Exception;

	public Result findApplicantByUK(String transactionId) throws SystemException, Exception;

	public Result updateApplicant(Applicant applicant) throws SystemException, Exception;

	public Result insertApplicant(Applicant applicant) throws SystemException, Exception;

	public Result removeApplicant(String transactionId) throws SystemException, Exception;
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
	public Result selectForFetPolicyCheck(Map params) throws SystemException, Exception;
	
}
