package com.tlg.aps.bs.firCtbcRewNoticeService;

import java.io.File;

import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface FirCtbcRewNoticeService {

	public Result policyDataImport(UserInfo userInfo, FirCtbcRewNoticeBatchVo voObject) throws SystemException,Exception;
	public File genCSV(String oid) throws SystemException,Exception;
	public File genExcel(String oid, String o180filename) throws SystemException,Exception;
	public File genExcelDiff(String oid) throws SystemException,Exception;
	public File genExcelDiff2(String oid) throws SystemException,Exception;
	public Result compareData(UserInfo userInfo, String oid) throws SystemException,Exception;
}
