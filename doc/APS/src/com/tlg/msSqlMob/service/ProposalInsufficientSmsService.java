package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ProposalInsufficientSms;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ProposalInsufficientSmsService {

	public int countProposalInsufficientSms(Map params) throws SystemException, Exception;
	
	public Result findProposalInsufficientSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findProposalInsufficientSmsByParams(Map params) throws SystemException, Exception;

	public Result findProposalInsufficientSmsByUK(String transactionId) throws SystemException, Exception;

	public Result updateProposalInsufficientSms(ProposalInsufficientSms entity) throws SystemException, Exception;

	public Result insertProposalInsufficientSms(ProposalInsufficientSms entity) throws SystemException, Exception;

	public Result removeProposalInsufficientSms(String transactionId) throws SystemException, Exception;
	
}
