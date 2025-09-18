package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.Newepolicymain;

public interface NewepolicymainService {
	
	@SuppressWarnings("rawtypes")
	public Result selectForFirBatchSendmail(Map params) throws SystemException, Exception;
	
	public Result insertNewepolicymain(Newepolicymain newepolicymain) throws SystemException, Exception;

}
