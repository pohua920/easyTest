package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnTmpFb;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
public interface FirAgtrnTmpFbService {
	public Result findFirAgtrnTmpFbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findFirAgtrnTmpFbByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnTmpFb(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnTmpFb(FirAgtrnTmpFb firAgtrnTmpFb) throws SystemException, Exception;
	
	public Result updateFirAgtrnTmpFb(FirAgtrnTmpFb firAgtrnTmpFb) throws SystemException, Exception;

	public Result findFbDiffFile(Map params) throws SystemException, Exception;
	
	public Result findFbProcessCenter(Map params) throws SystemException, Exception;
}
