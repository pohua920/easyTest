package com.tlg.db2.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**
 * mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
 */
public interface As400InrcfilService {

	public int countInrcfil(Map<String, Object> params) throws SystemException, Exception;
	
	public Result inrcfilRdmToAs400() throws Exception;


}
