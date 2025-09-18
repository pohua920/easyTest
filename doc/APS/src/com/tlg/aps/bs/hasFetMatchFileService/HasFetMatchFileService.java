package com.tlg.aps.bs.hasFetMatchFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
/**
 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 
 * @author dp0706
 *
 */
public interface HasFetMatchFileService {
	
	public Result runToGenerateFile(String fileType, Date excuteTime, String userId, Date dataDate) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String executeType) throws SystemException, Exception;
	
	public void deleteFile(String zipFilePath) ;
}
