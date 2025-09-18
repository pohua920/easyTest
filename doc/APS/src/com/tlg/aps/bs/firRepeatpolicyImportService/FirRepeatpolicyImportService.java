package com.tlg.aps.bs.firRepeatpolicyImportService;

import java.io.File;

import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public interface FirRepeatpolicyImportService {
	public Result dataUploadAndImport(String userId, File uploadFile, String filename, String rnYyyymm) throws Exception;
	
}
