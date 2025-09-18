package com.tlg.aps.bs.cibPolicyDataImportService;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.entity.FirCtbcSnn;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.prpins.entity.FirCtbcTmpStl;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface CibPolicyDataImportService {

	public Result policyDataImport(UserInfo userInfo) throws SystemException,Exception;
	
	public Result inputFirBatchLog(Date executeTime, UserInfo userInfo, String inType, String programId) throws SystemException,Exception;
	
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException,Exception;
	
	public Result updateFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException,Exception;
	
	public Result updateSingleDtlData(FirCtbcBatchDtl firCtbcBatchDtl, FirCtbcTmpStl firCtbcTmpStl) throws SystemException,Exception;
	
	public Result inputRawData(String batchNo, Date executeTime, UserInfo userInfo) throws SystemException,Exception;
	
	public Result insertFirCtbcBatchMainData(String batchNo, List<String> downloadFileList, UserInfo userInfo, Date executeTime) throws SystemException,Exception;
	
	public Result insertFirCtbcBatchDtlData(List<FirCtbcTmpStl> firCtbcTmpStlList, UserInfo userInfo) throws SystemException,Exception;
	
	public Result insertStlSnnRawData(String batchNo, String batchSeq, String stlFileName, String snnFileName, 
			List<String> stlRawDataList, List<String> snnRawDataList, UserInfo userInfo, Date executeTime) throws SystemException,Exception;
	
	public Result insertPblRawData(String batchNo, String batchSeq, String fileName, 
			List<String> pblRawDataList, UserInfo userInfo, Date executeTime) throws SystemException,Exception;
	
	public Result insertSigTiffData(String batchNo, String batchSeq, 
			List<File> sigTiffDataList, UserInfo userInfo, Date executeTime) throws SystemException,Exception;
	
	public Result insertStlSnnData(FirCtbcStl firCtbcStl, List<FirCtbcSnn> firCtbcSnnList, UserInfo userInfo) throws SystemException,Exception;
	
	public Result insertRstData(String batchNo, List<FirCtbcRst> firCtbcRstList, UserInfo userInfo) throws SystemException,Exception;
	
	public Result updateListFirCtbcBatchDtlData(List<FirCtbcBatchDtl> firCtbcBatchDtlList, UserInfo userInfo, String orderSeqStatus, String fileName, Date fileCreateDate) throws SystemException,Exception;
	
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	 *新增「轉檔異常資料修正」功能*/
	public Result updateUnusualData(FirCtbcStl firCtbcStl, FirCtbcBatchDtl firCtbcBatchDtl, UserInfo userInfo) throws SystemException,Exception;
	
	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start*/
	public Result updateSingleB2b2cData(FirCtbcB2b2c firCtbcB2b2c) throws SystemException,Exception;

	public Result updateListB2b2cData(List<FirCtbcB2b2c> firCtbcB2b2cList, UserInfo userInfo, 
			String orderSeqStatus, String fileName, Date fileCreateDate) throws SystemException, Exception;
	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end*/
}
