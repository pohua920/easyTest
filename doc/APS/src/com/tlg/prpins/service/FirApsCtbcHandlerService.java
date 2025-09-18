package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
 * 調整角色權限控管功能，後USER決定取消此功能*/
public interface FirApsCtbcHandlerService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirApsCtbcHandlerByParams(Map params) throws SystemException, Exception;
	
	public String findCoreComcodeByUpperComcode(String upperComcode) throws SystemException, Exception;

}
