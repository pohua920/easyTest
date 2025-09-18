package com.tlg.aps.bs.carAddrUploadAndImportService;

import java.io.File;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public interface CarAddrUploadAndImportService {

	public Result RenewalDataUploadAndImport(String userId, File uploadFile, String filename, String ultype) throws SystemException, Exception;
	
}
