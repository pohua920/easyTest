package com.tlg.aps.bs.hasLionBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasLionBackFileService {
	
	public Result runToGenerateFile(String type, Date excuteTime, String userId, String programId, Date dataDate) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type) throws SystemException, Exception;
	
	public  void deleteFile(String txtFilePath, String zipFilePath) ;
}
