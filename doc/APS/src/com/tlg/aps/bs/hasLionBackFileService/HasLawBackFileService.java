package com.tlg.aps.bs.hasLionBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface HasLawBackFileService {
	
	public Result runToGenerateFile(Date excuteTime, String userId, Date dataDate) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String executeType) throws SystemException, Exception;
	
	public void deleteFile(String zipFilePath) ;
}
