package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLionOP;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionOPService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionOPByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLionOPByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLionOP(HasAgtLionOP hasAgtLionOP) throws SystemException, Exception;
	
	public Result updateHasAgtLionOP(HasAgtLionOP hasAgtLionOP) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLionOP(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionOPByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
