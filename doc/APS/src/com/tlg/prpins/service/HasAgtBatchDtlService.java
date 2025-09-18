package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtBatchDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtBatchDtlService {

	@SuppressWarnings("rawtypes")
	public Result findHasAgtBatchDtlByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtBatchDtlByUK(Map params) throws SystemException, Exception;
	
	public Result updateHasAgtBatchDtlByParams(Map<String,Object> params) throws SystemException, Exception;

	public Result insertHasAgtBatchDtl(HasAgtBatchDtl hasAgtBatchDtl) throws SystemException, Exception;
	
	public Result updateHasAgtBatchDtl(HasAgtBatchDtl hasAgtBatchDtl) throws SystemException, Exception;
	
	

}
