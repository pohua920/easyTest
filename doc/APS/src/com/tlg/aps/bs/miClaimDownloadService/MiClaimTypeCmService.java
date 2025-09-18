package com.tlg.aps.bs.miClaimDownloadService;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetclaimTypecm;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface MiClaimTypeCmService {

	/**
	 * 儲存資料
	 * 
	 * @param userInfo
	 * @param fetclaimTypecm
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result createData(UserInfo userInfo, FetclaimTypecm fetclaimTypecm) throws SystemException,Exception;
	
	/**
	 * 呼叫安達API
	 * 
	 * @param fetclaimTypecm
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result callChubbApi(FetclaimTypecm fetclaimTypecm) throws SystemException,Exception;
	
	/**
	 * 儲存 + 呼叫安達
	 * @param fetclaimTypecm
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result send(UserInfo userInfo, FetclaimTypecm fetclaimTypecm) throws SystemException, Exception ;
	
	/**
	 * 儲存 + 上傳至FTP
	 * @param fetclaimTypecm
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result upload(UserInfo userInfo, FetclaimTypecm fetclaimTypecm) throws SystemException, Exception ;
	
	public Result generateXmlToFtp(FetclaimTypecm fetclaimTypecm) throws SystemException, Exception ;
}
