package com.tlg.aps.bs.cibPolicyDataImportService.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.cibPolicyDataImportService.CibPolicyDataImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.entity.FirCtbcSig;
import com.tlg.prpins.entity.FirCtbcSnn;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.prpins.entity.FirCtbcTmpPbl;
import com.tlg.prpins.entity.FirCtbcTmpSnn;
import com.tlg.prpins.entity.FirCtbcTmpStl;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirCtbcB2b2cService;
import com.tlg.prpins.service.FirCtbcBatchDtlService;
import com.tlg.prpins.service.FirCtbcBatchMainService;
import com.tlg.prpins.service.FirCtbcRstService;
import com.tlg.prpins.service.FirCtbcSigService;
import com.tlg.prpins.service.FirCtbcSnnService;
import com.tlg.prpins.service.FirCtbcStlService;
import com.tlg.prpins.service.FirCtbcTmpPblService;
import com.tlg.prpins.service.FirCtbcTmpSnnService;
import com.tlg.prpins.service.FirCtbcTmpStlService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.util.ZipUtil;

@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class CibPolicyDataImportServiceImpl implements CibPolicyDataImportService {
	
	private static final Logger logger = Logger.getLogger(CibPolicyDataImportServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchLogService firBatchLogService;
	private FirCtbcBatchMainService firCtbcBatchMainService;
	private FirCtbcBatchDtlService firCtbcBatchDtlService;
	private FirCtbcTmpStlService firCtbcTmpStlService;
	private FirCtbcTmpSnnService firCtbcTmpSnnService;
	private FirCtbcTmpPblService firCtbcTmpPblService;
	private FirCtbcSigService firCtbcSigService;
	private FirCtbcStlService firCtbcStlService;
	private FirCtbcSnnService firCtbcSnnService;
	private FirCtbcRstService firCtbcRstService;
	private FirSpService firSpService;
	// mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投
	private FirCtbcB2b2cService firCtbcB2b2cService;

	@Override
	public Result policyDataImport(UserInfo userInfo) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result inputFirBatchLog(Date executeTime, UserInfo userInfo, String inType, String programId)
			throws SystemException, Exception {
		
		FirBatchLog firBatchLog = new FirBatchLog();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("inType", inType);
		params.put("outBatchno", null);
		String batchNo = firSpService.runSpFirGetBatchno(params);
		if(batchNo == null || batchNo.length() <= 0) {
			batchNo = "";
		}
		
		String status = "1";
		if(executeTime == null || userInfo == null || programId == null || programId.length() <= 0 
				|| batchNo.trim().length() <= 0) {
			status = "F";
		}
		
		String remark = "";
		if(executeTime == null) {
			remark += "轉檔時間無內容值。";
		}
		if(userInfo == null) {
			remark += "執行人員無內容值。";
		}
		if(programId == null || programId.length() <= 0) {
			remark += "程式代碼無內容值。";
		}
		if(batchNo.trim().length() <= 0) {
			remark += "批次號碼取號失敗。";
		}
		
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userInfo.getUserId());
		firBatchLog.setDcreate(executeTime);
		
		Result result = this.firBatchLogService.insertFirBatchLog(firBatchLog);
		
		return result;
	}
	
	@Override
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception {
		Result result = this.firBatchLogService.updateFirBatchLog(firBatchLog);
		return result;
	}
	
	@Override
	public Result updateFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException, Exception {
		Result result = this.firCtbcBatchMainService.updateFirCtbcBatchMain(firCtbcBatchMain);
		return result;
	}
	
	@Override
	public Result updateSingleDtlData(FirCtbcBatchDtl firCtbcBatchDtl, FirCtbcTmpStl firCtbcTmpStl) throws SystemException, Exception {
		Result result = null;
		if(firCtbcBatchDtl != null)
			result = this.firCtbcBatchDtlService.updateFirCtbcBatchDtl(firCtbcBatchDtl);
		
		if(firCtbcTmpStl != null)
			result = this.firCtbcTmpStlService.updateFirCtbcTmpStl(firCtbcTmpStl);
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result inputRawData(String batchNo, Date executeTime, UserInfo userInfo) throws SystemException, Exception {

		Result result = new Result();
		String[] returnValue = new String[2];
		String status = "Y";
		if(batchNo == null || batchNo.length() <= 0 
				|| executeTime == null 
				|| userInfo == null 
				|| batchNo.length() <= 0) {
			status = "N";
		}
		
		String remark = "";
		if(executeTime == null) {
			remark += "轉檔時間未輸入，";
		}
		if(userInfo == null) {
			remark += "執行人員未輸入，";
		}
		if(batchNo.length() <= 0) {
			remark += "批次號碼未輸入，";
		}
		
		if("N".equals(status)) {
			returnValue[0] = status;
			returnValue[1] = remark;
			result.setResObject(returnValue);
			return result;
		}
		
		/**
		 * 取得檔案
		 * */
		String sftpHost = "175.184.247.85";
		String sftpUser = "CX_TLG_GET";
		String sftpPwd = "db5yi2ry";
		int sftpPort = 22;
		String remoteDir = "/aptoap/insco/180/RECASPO";
		String workingDirPath = "";
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir);
		List<String> downloadFileList = new ArrayList<String>();
		int dataCount;
		Map params;
		if(fileList != null && fileList.size() > 0) {
			for(String fileName : fileList) {
				if(fileName.startsWith("FRIFIN")) {
					params = new HashMap();
					params.put("filenameZip", fileName);
					dataCount = firCtbcBatchMainService.countFirCtbcBatchMain(params);
					if(dataCount <= 0) {
						downloadFileList.add(fileName);
					}
				}
			}
		}
		
		if(downloadFileList.size() > 0) {
			String strResult = sftpUtil.getFileFromSftp(remoteDir, workingDirPath, downloadFileList);
			// TODO bj016:如果從sftp下載檔案失敗該怎麼辦?
			/**
			 * 新增中信新件批次主檔
			 * */
			FirCtbcBatchMain firCtbcBatchMain;
			int iSeq = 1;
			for(String fileName : downloadFileList) {
				firCtbcBatchMain = new FirCtbcBatchMain(); 
				firCtbcBatchMain.setBatchNo(batchNo);
				firCtbcBatchMain.setBatchSeq(String.format("%02d", iSeq));
				firCtbcBatchMain.setFilenameZip(fileName);
				firCtbcBatchMain.setTransStatus("N");
				firCtbcBatchMain.setDeleteFlag("N");
				firCtbcBatchMain.setIcreate(userInfo.getUserId());
				firCtbcBatchMain.setDcreate(executeTime);
				firCtbcBatchMainService.insertFirCtbcBatchMain(firCtbcBatchMain);
				iSeq += 1;
			}
			
		}else {
			status = "0";
			remark = "";
			returnValue[0] = status;
			returnValue[1] = remark;
			result.setResObject(returnValue);
			return result;
		}
		
		/**
		 * ZIP檔處理
		 * */
		ZipUtil zipUtil = new ZipUtil();
		String source;
		String destination;
		String pwd = "ctbc180A";
		File folder;
		for(String fileName : downloadFileList) {
			source = workingDirPath + fileName;
			destination = workingDirPath;
			zipUtil.unzip(source, destination, pwd);
			
			folder = new File(workingDirPath);
			for (File fileEntry : folder.listFiles()) {
		        if (fileEntry.isFile()) {
		            logger.info("fileName : " + fileEntry.getName());
		        } 
		    }
		}
		
//		List<String> downloadFileList = new ArrayList<String>();
//		downloadFileList.add("aa.zip");
//		downloadFileList.add("bb.zip");
//		downloadFileList.add("cc.zip");
//		
//		FirCtbcBatchMain firCtbcBatchMain;
//		int iSeq = 1;
//		for(String fileName : downloadFileList) {
//			firCtbcBatchMain = new FirCtbcBatchMain(); 
//			firCtbcBatchMain.setBatchNo(batchNo);
//			firCtbcBatchMain.setBatchSeq(String.format("%02d", iSeq));
//			firCtbcBatchMain.setFilenameZip(fileName);
//			firCtbcBatchMain.setTransStatus("N");
//			firCtbcBatchMain.setDeleteFlag("N");
//			firCtbcBatchMain.setIcreate(userInfo.getUserId());
//			firCtbcBatchMain.setDcreate(executeTime);
//			firCtbcBatchMainService.insertFirCtbcBatchMain(firCtbcBatchMain);
//			iSeq += 1;
//			if(iSeq == 2) {
//				throw new Exception("Test");
//			}
//		}
		
		
		returnValue[0] = "Y";
		returnValue[1] = "";
		result.setResObject(returnValue);
		return result;
	}
	
	@Override
	public Result insertFirCtbcBatchMainData(String batchNo, List<String> downloadFileList, UserInfo userInfo, Date executeTime) throws SystemException, Exception {
		FirCtbcBatchMain firCtbcBatchMain;
		int iSeq = 1;
		for(String fileName : downloadFileList) {
			firCtbcBatchMain = new FirCtbcBatchMain(); 
			firCtbcBatchMain.setBatchNo(batchNo);
			firCtbcBatchMain.setBatchSeq(String.format("%04d", iSeq));
			firCtbcBatchMain.setFilenameZip(fileName);
			firCtbcBatchMain.setTransStatus("N");
			firCtbcBatchMain.setDeleteFlag("N");
			firCtbcBatchMain.setIcreate(userInfo.getUserId());
			firCtbcBatchMain.setDcreate(executeTime);
			firCtbcBatchMainService.insertFirCtbcBatchMain(firCtbcBatchMain);
			iSeq += 1;
		}
		return null;
	}
	
	@Override
	public Result insertFirCtbcBatchDtlData(List<FirCtbcTmpStl> firCtbcTmpStlList, UserInfo userInfo) throws SystemException, Exception {
		FirCtbcBatchDtl firCtbcBatchDtl;
		for(FirCtbcTmpStl firCtbcTmpStl : firCtbcTmpStlList) {
			firCtbcBatchDtl = new FirCtbcBatchDtl();
			firCtbcBatchDtl.setBatchNo(firCtbcTmpStl.getBatchNo());
			firCtbcBatchDtl.setBatchSeq(firCtbcTmpStl.getBatchSeq());
			firCtbcBatchDtl.setFkOrderSeq(firCtbcTmpStl.getFkOrderSeq());
			firCtbcBatchDtl.setOrderSeqStatus("0");
			firCtbcBatchDtl.setDeleteFlag("N");
			firCtbcBatchDtl.setIcreate(userInfo.getUserId());
			firCtbcBatchDtl.setDcreate(new Date());
			firCtbcBatchDtlService.insertFirCtbcBatchDtl(firCtbcBatchDtl);
		}
		return null;
	}
	
	@Override
	public Result insertStlSnnRawData(String batchNo, String batchSeq, String stlFileName, String snnFileName, 
			List<String> stlRawDataList, List<String> snnRawDataList,
			UserInfo userInfo, Date executeTime) throws SystemException, Exception {
		
		String orderNo;
		if(stlRawDataList != null && stlRawDataList.size() > 0) {
			FirCtbcTmpStl firCtbcTmpStl;
			for(String rawData : stlRawDataList) {
				if(rawData.length() > 0) {
					firCtbcTmpStl = new FirCtbcTmpStl();
					orderNo = rawData.substring(0, rawData.indexOf("|"));
					
					firCtbcTmpStl.setBatchNo(batchNo);
					firCtbcTmpStl.setBatchSeq(batchSeq);
					firCtbcTmpStl.setFilename(stlFileName);
					firCtbcTmpStl.setFkOrderSeq(orderNo);
					firCtbcTmpStl.setRawdata(rawData);
					firCtbcTmpStl.setpStatus("N");
					firCtbcTmpStl.setIcreate(userInfo.getUserId());
					firCtbcTmpStl.setDcreate(executeTime);
					
					this.firCtbcTmpStlService.insertFirCtbcTmpStl(firCtbcTmpStl);
				}
			}
		}
		
		if(snnRawDataList != null && snnRawDataList.size() > 0) {
			FirCtbcTmpSnn firCtbcTmpSnn;
			for(String rawData : snnRawDataList) {
				if(rawData.length() > 0) {
					firCtbcTmpSnn = new FirCtbcTmpSnn();
					orderNo = rawData.substring(0, rawData.indexOf("|"));
					
					firCtbcTmpSnn.setBatchNo(batchNo);
					firCtbcTmpSnn.setBatchSeq(batchSeq);
					firCtbcTmpSnn.setFilename(snnFileName);
					firCtbcTmpSnn.setFkOrderSeq(orderNo);
					firCtbcTmpSnn.setRawdata(rawData);
					firCtbcTmpSnn.setIcreate(userInfo.getUserId());
					firCtbcTmpSnn.setDcreate(executeTime);
					
					this.firCtbcTmpSnnService.insertFirCtbcTmpSnn(firCtbcTmpSnn);
				}
			}
		}
		return null;
	}
	
	@Override
	public Result insertPblRawData(String batchNo, String batchSeq, String fileName, List<String> pblRawDataList,
			UserInfo userInfo, Date executeTime) throws SystemException, Exception {
		
		String orderNo;
		if(pblRawDataList != null && pblRawDataList.size() > 0) {
			FirCtbcTmpPbl firCtbcTmpPbl;
			for(String rawData : pblRawDataList) {
				if(rawData.length() > 0) {
					firCtbcTmpPbl = new FirCtbcTmpPbl();
					orderNo = rawData.substring(0, rawData.indexOf("|"));
					
					firCtbcTmpPbl.setBatchNo(batchNo);
					firCtbcTmpPbl.setBatchSeq(batchSeq);
					firCtbcTmpPbl.setFilename(fileName);
					firCtbcTmpPbl.setFkOrderSeq(orderNo);
					firCtbcTmpPbl.setRawdata(rawData);
					firCtbcTmpPbl.setIcreate(userInfo.getUserId());
					firCtbcTmpPbl.setDcreate(executeTime);
					
					this.firCtbcTmpPblService.insertFirCtbcTmpPbl(firCtbcTmpPbl);
				}
			}
		}
		return null;
	}
	
	@Override
	public Result insertSigTiffData(String batchNo, String batchSeq, List<File> sigTiffDataList, UserInfo userInfo,
			Date executeTime) throws SystemException, Exception {
		
		String fileName;
		String orderNo;
		if(sigTiffDataList != null && sigTiffDataList.size() > 0) {
			FirCtbcSig firCtbcSig;
			for(File file : sigTiffDataList) {
				fileName = file.getName();
				if(fileName.length() > 0) {
					firCtbcSig = new FirCtbcSig();
					orderNo = fileName.replaceAll("FRISIG", "").replaceAll(".tiff", "");
					
					firCtbcSig.setBatchNo(batchNo);
					firCtbcSig.setBatchSeq(batchSeq);
					firCtbcSig.setFilenameSig(fileName);
					firCtbcSig.setFkOrderSeq(orderNo);
					firCtbcSig.setIcreate(userInfo.getUserId());
					firCtbcSig.setDcreate(executeTime);
					
					this.firCtbcSigService.insertFirCtbcSig(firCtbcSig);
				}
			}
		}
		return null;
	}
	
	@Override
	public Result insertStlSnnData(FirCtbcStl firCtbcStl, List<FirCtbcSnn> firCtbcSnnList,
			UserInfo userInfo) throws SystemException, Exception {

		firCtbcStl.setIcreate(userInfo.getUserId());
		firCtbcStl.setDcreate(new Date());
		Result result = this.firCtbcStlService.insertFirCtbcStl(firCtbcStl);
		if(firCtbcSnnList != null && firCtbcSnnList.size() > 0) {
			for(FirCtbcSnn firCtbcSnn : firCtbcSnnList) {
				firCtbcSnn.setIcreate(userInfo.getUserId());
				firCtbcSnn.setDcreate(new Date());
				result = this.firCtbcSnnService.insertFirCtbcSnn(firCtbcSnn);
			}
		}
		return null;
	}
	
	@Override
	public Result insertRstData(String batchNo, List<FirCtbcRst> firCtbcRstList, UserInfo userInfo)
			throws SystemException, Exception {
		
		Result result = new Result();
		if(firCtbcRstList != null && firCtbcRstList.size() > 0) {
			for(FirCtbcRst firCtbcRst : firCtbcRstList) {
				firCtbcRst.setBatchNo(batchNo);
				firCtbcRst.setIcreate(userInfo.getUserId());
				firCtbcRst.setDcreate(new Date());
				this.firCtbcRstService.insertFirCtbcRst(firCtbcRst);
			}
		}
		result.setResObject(firCtbcRstList);
		return result;
	}
	
	@Override
	public Result updateListFirCtbcBatchDtlData(List<FirCtbcBatchDtl> firCtbcBatchDtlList, UserInfo userInfo,
			String orderSeqStatus, String fileName, Date fileCreateDate) throws SystemException, Exception {

		Result result = new Result();
		if(firCtbcBatchDtlList != null && firCtbcBatchDtlList.size() > 0) {
			for(FirCtbcBatchDtl firCtbcBatchDtl : firCtbcBatchDtlList) {
				firCtbcBatchDtl.setOrderSeqStatus(orderSeqStatus);
				if(fileName != null && fileName.length() > 0)
					firCtbcBatchDtl.setBkFilename(fileName);
				if(fileCreateDate != null)
					firCtbcBatchDtl.setBkTime(fileCreateDate);
				firCtbcBatchDtl.setIupdate(userInfo.getUserId());
				firCtbcBatchDtl.setDupdate(new Date());
				this.firCtbcBatchDtlService.updateFirCtbcBatchDtl(firCtbcBatchDtl);
			}
		}
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	 *新增「轉檔異常資料修正」功能*/
	@Override
	public Result updateUnusualData(FirCtbcStl firCtbcStl, FirCtbcBatchDtl firCtbcBatchDtl, UserInfo userInfo)
			throws SystemException, Exception {
		Result result = new Result();
		if(firCtbcStl != null) {
			this.firCtbcStlService.updateFirCtbcStl(firCtbcStl);
		}
		
		if(firCtbcBatchDtl != null) {
			this.firCtbcBatchDtlService.updateFirCtbcBatchDtl(firCtbcBatchDtl);
		}
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start*/
	@Override
	public Result updateSingleB2b2cData(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception {
		Result result = null;
		if(firCtbcB2b2c != null)
			result = this.firCtbcB2b2cService.updateFirCtbcB2b2c(firCtbcB2b2c);
		
		return result;
	}
	
	@Override
	public Result updateListB2b2cData(List<FirCtbcB2b2c> firCtbcB2b2cList, UserInfo userInfo,
			String orderSeqStatus, String fileName, Date fileCreateDate) throws SystemException, Exception {

		Result result = new Result();
		if(firCtbcB2b2cList != null && !firCtbcB2b2cList.isEmpty()) {
			for(FirCtbcB2b2c firCtbcB2b2c : firCtbcB2b2cList) {
				firCtbcB2b2c.setOrderSeqStatus(orderSeqStatus);
				if(!StringUtil.isSpace(fileName))
					firCtbcB2b2c.setBkFilename(fileName);
				if(fileCreateDate != null)
					firCtbcB2b2c.setBkTime(fileCreateDate);
				firCtbcB2b2c.setIupdate(userInfo.getUserId());
				firCtbcB2b2c.setDupdate(new Date());
				this.firCtbcB2b2cService.updateFirCtbcB2b2c(firCtbcB2b2c);
			}
		}
		result.setResObject(Boolean.TRUE);
		return result;
	}
	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end*/
	
	public static void main(String args[]) throws Exception{
		
	}
	
	private FirCtbcStl mappingFirCtbcStl(String batchNo, String batchSeq, String stlFileName, String[] arrData) throws Exception{
		FirCtbcStl firCtbcStl = new FirCtbcStl();
		firCtbcStl.setBatchNo(batchNo);
		firCtbcStl.setBatchSeq(batchSeq);
		firCtbcStl.setFilename(stlFileName);
		firCtbcStl.setFkOrderSeq(arrData[0]);
		firCtbcStl.setNumsNo(arrData[1]);
		firCtbcStl.setGuarantyNo(arrData[2]);
		firCtbcStl.setPolicyNo(arrData[3]);
		firCtbcStl.setStatus(arrData[4]);
		firCtbcStl.setSendType(arrData[5]);
		firCtbcStl.setProdCode(arrData[6]);
		firCtbcStl.setPlanCode(arrData[7]);
		firCtbcStl.setCellurPhoneNo(arrData[8]);
		firCtbcStl.setEmail(arrData[9]);
		firCtbcStl.setCommCenterCode(arrData[10]);
		firCtbcStl.setInscoCode(arrData[11]);
		firCtbcStl.setPoIssueDate(arrData[12]);
		firCtbcStl.setPoIssueEndDate(arrData[13]);
		firCtbcStl.setPeriod(arrData[14]);
		firCtbcStl.setAgentNo(arrData[15]);
		firCtbcStl.setReceivedBranch(arrData[16]);
		firCtbcStl.setBranchNo(arrData[17]);
		firCtbcStl.setInsEx(arrData[18]);
		firCtbcStl.setFireAmt(Long.parseLong(arrData[19]));
		firCtbcStl.setFirePrem(Long.parseLong(arrData[20]));
		firCtbcStl.setQuakeAmt(Long.parseLong(arrData[21]));
		firCtbcStl.setQuakePrem(Long.parseLong(arrData[22]));
		firCtbcStl.setTtlPremium(Long.parseLong(arrData[23]));
		firCtbcStl.setIsAutoRent(arrData[24]);
		firCtbcStl.setTargetZipCode(arrData[25]);
		firCtbcStl.setTargetCity(arrData[26]);
		firCtbcStl.setTargetDist(arrData[27]);
		firCtbcStl.setTargetAddress(arrData[28]);
		firCtbcStl.setTargetAreaCode(arrData[29]);
		firCtbcStl.setTargetSize(new BigDecimal(arrData[30]));
		firCtbcStl.setBuildYear(arrData[31]);
		firCtbcStl.setRoof(arrData[32]);
		firCtbcStl.setMaterialCode(arrData[33]);
		firCtbcStl.setFloorTotal(arrData[34]);
		firCtbcStl.setConstLevel(arrData[35]);
		firCtbcStl.setConstLevelText(arrData[36]);
		firCtbcStl.setMemo(arrData[37]);
		firCtbcStl.setSignStatus(arrData[38]);
		firCtbcStl.setSignatoryNo(arrData[39]);
		firCtbcStl.setSignatoryId(arrData[40]);
		firCtbcStl.setSignatoryName(arrData[41]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		firCtbcStl.setSignDatetime(sdf.parse(arrData[42]));
		if(arrData.length >= 44)
			firCtbcStl.setSignOtherProb(arrData[43]);
		
		return firCtbcStl;
	}
	
	private FirCtbcSnn mappingFirCtbcSnn(String batchNo, String batchSeq, String snnFileName, String[] arrData) throws Exception{
		FirCtbcSnn firCtbcSnn = new FirCtbcSnn();
		firCtbcSnn.setBatchNo(batchNo);
		firCtbcSnn.setBatchSeq(batchSeq);
		firCtbcSnn.setFilename(snnFileName);
		firCtbcSnn.setFkOrderSeq(arrData[0]);
		firCtbcSnn.setRelationAgent(arrData[1]);
		firCtbcSnn.setSortNo(Short.parseShort(arrData[2]));
		firCtbcSnn.setRelateId(arrData[3]);
		firCtbcSnn.setResidenceNo(arrData[4]);
		firCtbcSnn.setNames(arrData[5]);
		firCtbcSnn.setIsLegal(arrData[6]);
		firCtbcSnn.setRepresentative(arrData[7]);
		firCtbcSnn.setBirthday(arrData[8]);
		firCtbcSnn.setZipCode(arrData[9]);
		firCtbcSnn.setCity(arrData[10]);
		firCtbcSnn.setDist(arrData[11]);
		firCtbcSnn.setAddress(arrData[12]);
		firCtbcSnn.setPhone(arrData[13]);
		firCtbcSnn.setNationality(arrData[14]);
		firCtbcSnn.setRegistration(arrData[15]);
		firCtbcSnn.setOccupation(arrData[16]);
		firCtbcSnn.setListedCompany(arrData[17]);
		firCtbcSnn.setIssueShares(arrData[18]);
		firCtbcSnn.setAmlChkFlag(arrData[19]);
		firCtbcSnn.setDormantFlag(arrData[20]);
		firCtbcSnn.setLongNameInd(arrData[21]);
		firCtbcSnn.setLongName(arrData[22]);
		firCtbcSnn.setLongRomname(arrData[23]);

		return firCtbcSnn;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirCtbcBatchMainService getFirCtbcBatchMainService() {
		return firCtbcBatchMainService;
	}

	public void setFirCtbcBatchMainService(FirCtbcBatchMainService firCtbcBatchMainService) {
		this.firCtbcBatchMainService = firCtbcBatchMainService;
	}

	public FirCtbcTmpStlService getFirCtbcTmpStlService() {
		return firCtbcTmpStlService;
	}

	public void setFirCtbcTmpStlService(FirCtbcTmpStlService firCtbcTmpStlService) {
		this.firCtbcTmpStlService = firCtbcTmpStlService;
	}

	public FirCtbcTmpSnnService getFirCtbcTmpSnnService() {
		return firCtbcTmpSnnService;
	}

	public void setFirCtbcTmpSnnService(FirCtbcTmpSnnService firCtbcTmpSnnService) {
		this.firCtbcTmpSnnService = firCtbcTmpSnnService;
	}

	public FirCtbcTmpPblService getFirCtbcTmpPblService() {
		return firCtbcTmpPblService;
	}

	public void setFirCtbcTmpPblService(FirCtbcTmpPblService firCtbcTmpPblService) {
		this.firCtbcTmpPblService = firCtbcTmpPblService;
	}

	public FirCtbcSigService getFirCtbcSigService() {
		return firCtbcSigService;
	}

	public void setFirCtbcSigService(FirCtbcSigService firCtbcSigService) {
		this.firCtbcSigService = firCtbcSigService;
	}

	public FirCtbcBatchDtlService getFirCtbcBatchDtlService() {
		return firCtbcBatchDtlService;
	}

	public void setFirCtbcBatchDtlService(FirCtbcBatchDtlService firCtbcBatchDtlService) {
		this.firCtbcBatchDtlService = firCtbcBatchDtlService;
	}

	public FirCtbcStlService getFirCtbcStlService() {
		return firCtbcStlService;
	}

	public void setFirCtbcStlService(FirCtbcStlService firCtbcStlService) {
		this.firCtbcStlService = firCtbcStlService;
	}

	public FirCtbcSnnService getFirCtbcSnnService() {
		return firCtbcSnnService;
	}

	public void setFirCtbcSnnService(FirCtbcSnnService firCtbcSnnService) {
		this.firCtbcSnnService = firCtbcSnnService;
	}

	public FirCtbcRstService getFirCtbcRstService() {
		return firCtbcRstService;
	}

	public void setFirCtbcRstService(FirCtbcRstService firCtbcRstService) {
		this.firCtbcRstService = firCtbcRstService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start*/
	public FirCtbcB2b2cService getFirCtbcB2b2cService() {
		return firCtbcB2b2cService;
	}

	public void setFirCtbcB2b2cService(FirCtbcB2b2cService firCtbcB2b2cService) {
		this.firCtbcB2b2cService = firCtbcB2b2cService;
	}
	/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end*/

}
