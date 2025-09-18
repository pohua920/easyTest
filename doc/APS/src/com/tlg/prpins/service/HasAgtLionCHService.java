package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLionCH;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionCHService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCHByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLionCHByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLionCH(HasAgtLionCH hasAgtLionCH) throws SystemException, Exception;
	
	public Result updateHasAgtLionCH(HasAgtLionCH hasAgtLionCH) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLionCH(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCHByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
