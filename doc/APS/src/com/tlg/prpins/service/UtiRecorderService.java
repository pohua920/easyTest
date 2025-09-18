package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.UtiRecorder;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface UtiRecorderService {

	public int countUtiRecorder(Map params) throws SystemException, Exception;

	public Result findUtiRecorderByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findUtiRecorderByParams(Map params) throws SystemException, Exception;
	
	public Result updateUtiRecorder(UtiRecorder utiRecorder) throws SystemException, Exception;

	public Result insertUtiRecorder(UtiRecorder utiRecorder) throws SystemException, Exception;


}
