package com.tlg.aps.webService.stakeHolderService.server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface StakeHolderService {

	
	/**
	 * 利關人webservice
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String stakeHolderQuery(@WebParam(name = "str")String str) throws Exception;
}
