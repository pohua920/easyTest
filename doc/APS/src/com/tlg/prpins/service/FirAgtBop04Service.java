package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBop04;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public interface FirAgtBop04Service {
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop04ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtBop04ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertFirAgtBop04(FirAgtBop04 firAgtBop04) throws SystemException, Exception;
	
	public Result updateFirAgtBop04(FirAgtBop04 firAgtBop04) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countFirAgtBop04(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop04ByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
