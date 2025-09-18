package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLionCM;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionCMService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCMByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLionCMByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLionCM(HasAgtLionCM hasAgtLionCM) throws SystemException, Exception;
	
	public Result updateHasAgtLionCM(HasAgtLionCM hasAgtLionCM) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLionCM(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCMByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
