package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtLawPol;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface HasAgtLawPolService {

	@SuppressWarnings("rawtypes")
	public Result findHasAgtLawPolByParams(Map params) throws SystemException, Exception;
	
	public Result findHasAgtLawPolByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertHasAgtLawPol(HasAgtLawPol hasAgtLawPol) throws SystemException, Exception;
	
	public Result updateHasAgtLawPol(HasAgtLawPol hasAgtLawPol) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countHasAgtLawPol(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasAgtLawPolByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;
}
