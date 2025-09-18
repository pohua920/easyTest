package com.tlg.msSqlSh.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSh.entity.Rs000Ap;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Rs000ApService {

	public int countRs000Ap(Map params) throws SystemException, Exception;
	
	public Result findRs000ApByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findRs000ApByParams(Map params) throws SystemException, Exception;

	public Result findRs000ApByUK(String ap01, String ap00) throws SystemException, Exception;

	public Result updateRs000Ap(Rs000Ap rs000Ap) throws SystemException, Exception;

	public Result insertRs000Ap(Rs000Ap rs000Ap) throws SystemException, Exception;

	public Result removeRs000Ap(String ap00, String ap01) throws SystemException, Exception;
	
}
