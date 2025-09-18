package com.tlg.aps.bs.cibPolicyDataImportService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface RunCibPolicyDataImportService {

	public Result policyDataImport(UserInfo userInfo) throws SystemException,Exception;
	public Result policyDataReturn(UserInfo userInfo) throws SystemException,Exception;
	public String uploadFile(String filePath, String orderSeq);
}
