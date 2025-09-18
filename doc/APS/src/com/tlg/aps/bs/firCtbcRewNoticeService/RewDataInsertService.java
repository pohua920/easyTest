package com.tlg.aps.bs.firCtbcRewNoticeService;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.prpins.entity.FirCtbcRewMatchname;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface RewDataInsertService {

	public Result policyDataImport(UserInfo userInfo) throws SystemException,Exception;
	
	public Result inputFirCtbcRewNoticeBatch(Date executeTime, UserInfo userInfo, FirCtbcRewNoticeBatchVo voObject) throws SystemException,Exception;
	
	public Result updateFirCtbcRewNoticeBatch(long executeTime, FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException,Exception;
	
	public Result updateFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException,Exception;
	
	public void inputRawData(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch, Date executeTime, File file, String fileName, String fileType, UserInfo userInfo) throws SystemException, Exception;
	
	public void insertAnalyzeRawData(BigDecimal batchOid, Date executeTime, UserInfo userInfo) throws SystemException, Exception;
	
	public void insertCompareData(FirCtbcRewMatchLog firCtbcRewMatchLog1, FirCtbcRewMatchLog firCtbcRewMatchLog2, FirCtbcRewMatchLog firCtbcRewMatchLog3, UserInfo userInfo) throws SystemException, Exception;
	
	public void insertCompareData(FirCtbcRewMatchname firCtbcRewMatchname1, FirCtbcRewMatchname firCtbcRewMatchname2, FirCtbcRewMatchname firCtbcRewMatchname3, UserInfo userInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180, UserInfo userInfo) throws SystemException,Exception;

	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 
	 * 依照matchType更新firCtbcRewFix180.matchType*/
	public void updateFirCtbcRewFix180MatchType(FirCtbcRewFix180 firCtbcRewFix180, String logMatchType, String nameMatchType) throws SystemException, Exception;
}
