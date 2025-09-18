package com.tlg.aps.bs.miClaimDownloadService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface MiClaimDownloadService {

	public Result download(String executDate) throws SystemException, Exception ;
}
