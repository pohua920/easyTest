package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ApplicantEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ApplicantEndorseService {

	public int countApplicantEndorse(Map params) throws SystemException, Exception;
	
	public Result findApplicantEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findApplicantEndorseByParams(Map params) throws SystemException, Exception;

	public Result findApplicantEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateApplicantEndorse(ApplicantEndorse applicantEndorse) throws SystemException, Exception;

	public Result insertApplicantEndorse(ApplicantEndorse applicantEndorse) throws SystemException, Exception;

	public Result removeApplicantEndorse(String transactionId) throws SystemException, Exception;
	
	public Result findEndorseForCheck() throws SystemException, Exception;
}
