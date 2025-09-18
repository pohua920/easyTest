package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com051wc;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com051wcService {

	public int countCom051wc(Map params) throws SystemException, Exception;
	
	public Result findCom051wcByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom051wcByParams(Map params) throws SystemException, Exception;

	public Result updateCom051wc(Com051wc com051wc) throws SystemException, Exception;

	public Result insertCom051wc(Com051wc com051wc) throws SystemException, Exception;


}
