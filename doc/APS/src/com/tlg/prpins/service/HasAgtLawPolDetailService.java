package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLawPolDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface HasAgtLawPolDetailService {

	@SuppressWarnings("rawtypes")
	public Result findHasAgtLawPolDetailByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLawPolDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLawPolDetail(HasAgtLawPolDetail hasAgtLawPolDetail) throws SystemException, Exception;
	
	public Result updateHasAgtLawPolDetail(HasAgtLawPolDetail hasAgtLawPolDetail) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLawPolDetail(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLawPolDetailByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;
}
