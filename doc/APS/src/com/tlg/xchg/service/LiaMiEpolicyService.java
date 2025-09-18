package com.tlg.xchg.service;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiEpolicy;

public interface LiaMiEpolicyService {
	
	public Result insertLiaMiEpolicy(LiaMiEpolicy liaMiEpolicy) throws SystemException, Exception;

}
