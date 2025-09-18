package com.tlg.aps.webService.creditCardBlackList.server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface CreditCardBlackListService {
	
	/**
	 * 信用卡黑名單查詢
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String creditCardBlackListQuery(@WebParam(name = "str")String str) throws Exception;

}
