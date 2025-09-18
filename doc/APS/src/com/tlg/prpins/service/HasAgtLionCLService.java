package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLionCL;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtLionCLService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCLByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLionCLByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLionCL(HasAgtLionCL hasAgtLionCL) throws SystemException, Exception;
	
	public Result updateHasAgtLionCL(HasAgtLionCL hasAgtLionCL) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLionCL(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLionCLByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
