package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBop03;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public interface FirAgtBop03Service {
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop03ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtBop03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertFirAgtBop03(FirAgtBop03 firAgtBop03) throws SystemException, Exception;
	
	public Result updateFirAgtBop03(FirAgtBop03 firAgtBop03) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countFirAgtBop03(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop03ByUK(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;

}
