package com.tlg.aps.bs.firAS400RenewalImportService;

import java.io.File;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
public interface FirAS400RenewalImportService {

	public Result RenewalDataUploadAndImport(String userId, File uploadFile, String filename, String businessnature, String rnYyyymm) throws SystemException, Exception;
	
}
