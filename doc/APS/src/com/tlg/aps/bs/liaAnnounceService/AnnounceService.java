package com.tlg.aps.bs.liaAnnounceService;

import java.io.File;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface AnnounceService {

	
	/**
	 * 收件通報 - 排程呼叫CWP webservice to 公會web service
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void rcvAnnounceService() throws SystemException,Exception;
	
	/**
	 * 承保通報 - 排程呼叫CWP webservice to 公會web service
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void undwrtAnnounceService() throws SystemException,Exception;
	
	/**
	 * 400轉收件通報中繼
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void rcvComm051WBToAnnounceService() throws SystemException,Exception;
	
	/**
	 * 400轉承保通報中繼
	 * @throws SystemException
	 * @throws Exception
	 */
	public void undwrtComm051WAToAnnounceService() throws SystemException,Exception;
	
	
	/**
	 * 將ASSOC_RCV_ANCMT轉收件通報中繼
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void rcvAssocToAnnounceService() throws SystemException,Exception;
	
	/**
	 * 將ASSOC_ANN_ASSUW轉承保通報中繼
	 * @throws SystemException
	 * @throws Exception
	 */
	public void undwrtAssocToAnnounceService() throws SystemException,Exception;
	
	/**
	 * IPB902I 保險存摺 - 呼叫CWP webservice to 公會web service
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void ipb902iService() throws SystemException,Exception;
	
	/**
	 * IPB902I 保險存摺 - 新核心撈資料並呼叫CWP webservice to 公會web service
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void coreIpb902iService() throws SystemException,Exception;
	
	
	public Result mailUnsendRecord() throws SystemException, Exception;
	
	
	/**
	 * 提供上傳名單查詢收件通報及承保通報，並將檔案寄到指定的mail
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void queryRcvAndUndwrtByFileService(File file, String mail) throws SystemException,Exception;
	
}
