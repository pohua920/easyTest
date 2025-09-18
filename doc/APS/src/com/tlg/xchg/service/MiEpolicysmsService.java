package com.tlg.xchg.service;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.MiEpolicysms;

public interface MiEpolicysmsService {
	
	public Result insertMiEpolicysms(MiEpolicysms miEpolicysms) throws SystemException, Exception;

}
