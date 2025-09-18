package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnYcbUpload;
import com.tlg.util.Result;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案  **/
public interface FirAgtrnYcbUploadService {
	
	public Result findFirAgtrnYcbUploadByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnYcbUpload(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception;
	
	public Result updateFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception;
	
	public Result removeFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception;
	
	public Result selectAgtMainAndDtlForImport(Map params) throws SystemException, Exception;

}
