package com.tlg.aps.bs.petFileDownloadService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface PetFileDownloadService {
	
	public Result petFileDownload(UserInfo userInfo) throws SystemException,Exception;
	
}
