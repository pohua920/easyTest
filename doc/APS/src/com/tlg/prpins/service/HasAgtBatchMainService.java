package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtBatchMainService {
	@SuppressWarnings("rawtypes")
	public Result findHasAgtBatchMainByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findHasAgtBatchMainByUk(Map params) throws SystemException, Exception;
	
	public Result updateHasAgtBatchMain(HasAgtBatchMain hasAgtBatchMain) throws SystemException, Exception;

	public Result insertHasAgtBatchMain(HasAgtBatchMain hasAgtBatchMain) throws SystemException, Exception;
	
	public int countByParams(Map params) throws SystemException, Exception;
}
