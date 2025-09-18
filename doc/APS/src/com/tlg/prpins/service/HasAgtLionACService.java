package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLionAC;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionACService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionACByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLionACByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLionAC(HasAgtLionAC hasAgtLionAC) throws SystemException, Exception;
	
	public Result updateHasAgtLionAC(HasAgtLionAC hasAgtLionAC) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLionAC(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionACByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
