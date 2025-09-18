package com.tlg.aps.bs.firRepeatpolicyImportService;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public interface RepeatpolicyImportService {
	public Result insertFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain firRepeatpolicyBatchMain) throws Exception;
	
	public Result updateFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException, Exception;
	
	public Result removeFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException,Exception;
	
	public Result removeFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException,Exception;
	
}
