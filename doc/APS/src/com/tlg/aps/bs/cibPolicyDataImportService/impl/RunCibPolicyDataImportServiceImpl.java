package com.tlg.aps.bs.cibPolicyDataImportService.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.cibPolicyDataImportService.CibPolicyDataImportService;
import com.tlg.aps.bs.cibPolicyDataImportService.RunCibPolicyDataImportService;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressCompareVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.RptFir00103ResultVo;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.aps.vo.VerifyIdVo;
import com.tlg.aps.webService.addressCheckService.client.AddressCheckService;
import com.tlg.aps.webService.firCalAmountService.client.FirAmountService;
import com.tlg.aps.webService.firDoubleInsService.client.FirDoubleInsService;
import com.tlg.aps.webService.firRuleService.client.RuleCheckService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.entity.FirCtbcDeptinfo;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.entity.FirCtbcSig;
import com.tlg.prpins.entity.FirCtbcSnn;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.prpins.entity.FirCtbcTmpSnn;
import com.tlg.prpins.entity.FirCtbcTmpStl;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirRptCtbcCtf;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirCtbcBatchDtlService;
import com.tlg.prpins.service.FirCtbcBatchMainService;
import com.tlg.prpins.service.FirCtbcDeptinfoService;
import com.tlg.prpins.service.FirCtbcRstService;
import com.tlg.prpins.service.FirCtbcSigService;
import com.tlg.prpins.service.FirCtbcTmpSnnService;
import com.tlg.prpins.service.FirCtbcTmpStlService;
import com.tlg.prpins.service.FirRptCtbcCtfService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.prpins.service.PrptmainService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.util.WebserviceObjConvert;
import com.tlg.util.ZipUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RunCibPolicyDataImportServiceImpl implements RunCibPolicyDataImportService {
	
	private static final Logger logger = Logger.getLogger(RunCibPolicyDataImportServiceImpl.class);
	private ConfigUtil configUtil;
	private CibPolicyDataImportService cibPolicyDataImportService;
	private FirCtbcBatchMainService firCtbcBatchMainService;
	private FirCtbcBatchDtlService firCtbcBatchDtlService;
	private FirCtbcTmpStlService firCtbcTmpStlService;
	private FirCtbcTmpSnnService firCtbcTmpSnnService;
	private FirCtbcSigService firCtbcSigService;
	private PrptmainService prptmainService;
	private RfrcodeService rfrcodeService;
	private FirDoubleInsService clientFirDoubleInsService;
	private FirAmountService clientFirAmountService;
	private FirSpService firSpService;
	private FirBatchInfoService firBatchInfoService;
	private FirCtbcRstService firCtbcRstService;
	private FirRptCtbcCtfService firRptCtbcCtfService;
	private FirCtbcDeptinfoService firCtbcDeptinfoService;
	private AddressCheckService clientAddressCheckService;
	//mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS
	private RuleCheckService clientRuleCheckService;
	
	private Map<String,String> rfrPostCodeMap = null;
	private List<String> rfrAreaCodeList = null;
	private Map<String,String> rfrBuildingLevelCodeMap = null;
	private Map<String,String> rfrRoofNoMap = null;
	private Map<String,String> rfrWallNoMap = null;
	private Map<String,String> rfrOccupationCodeMap = null;
	private Map<String,String> rfrStructureMap = null;
	private List<String> rfrCountryCodeList = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result policyDataImport(UserInfo userInfo) throws SystemException, Exception {

		Result result = new Result();
		
		//新增執行記錄檔-----start
		Date executeTime = new Date();
		result = cibPolicyDataImportService.inputFirBatchLog(executeTime, userInfo, "1", "FIR_CTBC_01");
		FirBatchLog firBatchLog = null;
		String batchNo = "";
		if(result != null && result.getResObject() != null) {
			firBatchLog = (FirBatchLog)result.getResObject();
			batchNo = firBatchLog.getBatchNo();
			if(batchNo == null || batchNo.trim().length() <= 0) {
				this.sendMail01("", "", executeTime, "批次號碼取號失敗。");
				return this.getReturnResult("批次號碼取號失敗");
			}
		}else {
			this.sendMail01("", "", executeTime, "批次號碼取號失敗。");
			return this.getReturnResult("新增FIR_BATCH_LOG批次程式執行記錄檔失敗");
		}
		//新增執行記錄檔-----end
		
		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----start
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("prgId", "FIR_CTBC_01_STATUS");
		result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		if(result != null && result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
			if("N".equals(firBatchInfo.getMailTo())) {
				updateFirBatchLog(firBatchLog, "S", "FIR_BATCH_INFO設定檔設定為排程暫停執行。", userInfo);
				return this.getReturnResult("FIR_BATCH_INFO設定檔設定為排程暫停執行。");
			}
		}
		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----end
		
		//取得檔案-----start
		List<String> downloadFileList = getZipFileFromSftp();
//		File tempFolder = new File(configUtil.getString("localFilePath"));
//		List<String> downloadFileList = new ArrayList<String>();
//		for (File fileEntry : tempFolder.listFiles()) {
//			if (fileEntry.isFile()) {
//				downloadFileList.add(fileEntry.getName());
//			}
//		}
		if(downloadFileList != null && downloadFileList.size() <= 0) {
			updateFirBatchLog(firBatchLog, "N", "", userInfo);
			this.sendMail01("N", batchNo, executeTime, "");
			return this.getReturnResult("本次無任何zip檔案");
		}else {
			//按照檔名作排序
			Collections.sort(downloadFileList);
		}
		//取得檔案-----end
		
		//新增FIR_CTBC_BATCH_MAIN火險中信新件批次主檔-----start
		try {
			result = cibPolicyDataImportService.insertFirCtbcBatchMainData(batchNo, downloadFileList, userInfo, executeTime);
		}catch(Exception e) {
			e.printStackTrace();
			String exceptionMsg = e.getMessage();

			updateFirBatchLog(firBatchLog, "F", exceptionMsg, userInfo);
			this.sendMail01("", batchNo, executeTime, exceptionMsg);
			return this.getReturnResult("新增FirCtbcBatchMain發生錯誤");
		}
		//新增FIR_CTBC_BATCH_MAIN火險中信新件批次主檔-----end
		
		//ZIP檔處理-----start
		ZipUtil zipUtil = new ZipUtil();
		String workingDirPath = configUtil.getString("localFilePath");
		String source;
		String destination;
		String pwd = configUtil.getString("ctbcZipPwd");
		File folder;
//		File moveTiffFile;
		String fileName;
		boolean checkFRISTL;
		boolean checkFRISNN;
		boolean checkFRISTLHasData;
		boolean checkFRISNNHasData;
		params = new HashMap();
		params.put("batchNo", batchNo);
		result = firCtbcBatchMainService.findFirCtbcBatchMainByParams(params);
		List<FirCtbcBatchMain> searchResult = null;
		if(result != null && result.getResObject() != null) {
			searchResult = (List<FirCtbcBatchMain>)result.getResObject();
		}else {
			return this.getReturnResult("查無FirCtbcBatchMain資料");
		}
		
		FirCtbcBatchMain firCtbcBatchMain;
		List<String> firstlRawDataList;
		List<String> firsnnRawDataList;
		List<String> firpblRawDataList;
		List<File> firsigTiffDataList;
		String firstlFileName;
		String firsnnFileName;
		String firPblFileName;
		int firstlFileDataCount;
		int firsnnFileDataCount;
		int firPblFileDataCount;
		int firsigTiffCount;
		String unzipFileFolder;
		for(String zipFileName : downloadFileList) {
			firCtbcBatchMain = this.getFirCtbcBatchMain(zipFileName, searchResult);
			if(firCtbcBatchMain == null) {
				//TODO bj016 資料庫中找不到zipFileName要怎麼辦?
				continue;
			}
			unzipFileFolder = workingDirPath+zipFileName.replaceAll(".zip", "");
			source = workingDirPath + zipFileName;
			destination = unzipFileFolder;
			zipUtil.unzip(source, destination, pwd);

			folder = new File(unzipFileFolder);
			//mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
			if(!folder.exists()) {
				//20210118檢查資料夾是否存在
				updateFirCtbcBatchMain(firCtbcBatchMain, "L", "找無解壓縮後的資料夾",  "Y", userInfo);//回寫FirCtbcBatchMain錯誤狀態L：缺檔
				continue;
			}
			checkFRISTL = false;
			checkFRISNN = false;
			checkFRISTLHasData = true;
			checkFRISNNHasData = true;
			firstlRawDataList = new ArrayList<String>();
			firsnnRawDataList = new ArrayList<String>();
			firpblRawDataList = new ArrayList<String>();
			firsigTiffDataList = new ArrayList<File>();
			firstlFileName = "";
			firsnnFileName = "";
			firPblFileName = "";
			firstlFileDataCount = 0; 
			firsnnFileDataCount = 0;
			firPblFileDataCount = 0;
			firsigTiffCount = 0;
			for (File fileEntry : folder.listFiles()) {
		        if (fileEntry.isFile()) {
		            logger.info("fileName : " + fileEntry.getName());
		            fileName = fileEntry.getName();
		            if(fileName.startsWith("FRISTL")) {
		            	checkFRISTL = true;
		            	firstlFileName = fileName;
		            	if(fileEntry.length() > 0) {
		            		checkFRISTLHasData = true;
		            		firstlRawDataList = this.readUsingFileReader(fileEntry);
		            		firstlFileDataCount = firstlRawDataList.size();
		            	}else {
		            		//無資料
		            		checkFRISTLHasData = false;
		            	}
		            } else if(fileName.startsWith("FRISNN")) {
		            	checkFRISNN = true;
		            	firsnnFileName = fileName;
		            	if(fileEntry.length() > 0) {
		            		checkFRISNNHasData = true;
		            		firsnnRawDataList = this.readUsingFileReader(fileEntry);
		            		firsnnFileDataCount = firsnnRawDataList.size();
		            	}else {
		            		//無資料
		            		checkFRISNNHasData = false;
		            	}
		            } else if(fileName.startsWith("FRIPBL")) {
		            	firPblFileName = fileName;
		            	if(fileEntry.length() > 0) {
		            		firpblRawDataList = this.readUsingFileReader(fileEntry);
		            		firPblFileDataCount = firpblRawDataList.size();
		            	}
		            } else if(fileName.startsWith("FRISIG")) {
		            	firsigTiffCount += 1;
		            	firsigTiffDataList.add(fileEntry);
		            }
		        } 
		    }
			
			if(!(checkFRISTL && checkFRISNN)) {
				updateFirCtbcBatchMain(firCtbcBatchMain, "L", "",  "Y", userInfo);//回寫FirCtbcBatchMain錯誤狀態L：缺檔
				continue;
			}else if(!(checkFRISTLHasData && checkFRISNNHasData)) {
				updateFirCtbcBatchMain(firCtbcBatchMain, "Z", "",  "Y", userInfo);//回寫FirCtbcBatchMain錯誤狀態Z：檔案無資料
				continue;
			} else {
				try {
					this.cibPolicyDataImportService.insertStlSnnRawData(batchNo, firCtbcBatchMain.getBatchSeq(), firstlFileName, firsnnFileName, firstlRawDataList, firsnnRawDataList, userInfo, executeTime);
				}catch(Exception ex) {
					ex.printStackTrace();
					updateFirCtbcBatchMain(firCtbcBatchMain, "A", "",  "Y", userInfo);//回寫FirCtbcBatchMain錯誤狀態A：新增錯誤
					continue;
				}
				
				//新增PBL中信新件暫存簽署問題檔，就算發生錯誤也要繼續執行，這是無關緊要的檔案
				try {
					this.cibPolicyDataImportService.insertPblRawData(batchNo, firCtbcBatchMain.getBatchSeq(), firPblFileName, firpblRawDataList, userInfo, executeTime);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				//新增	新增tiff簽署文件檔檔，就算發生錯誤也要繼續執行，這是無關緊要的檔案
				try {
					this.cibPolicyDataImportService.insertSigTiffData(batchNo, firCtbcBatchMain.getBatchSeq(),  firsigTiffDataList, userInfo, executeTime);
					
					//TODO bj016 Tiff檔搬檔到哪裡?
					if(firsigTiffDataList != null && firsigTiffDataList.size() > 0) {
//						String localTiffFileMovePath = configUtil.getString("localTiffFileMovePath");
						for(File originFile : firsigTiffDataList) {
//							moveTiffFile = new File(localTiffFileMovePath + originFile.getName());
//							if(moveTiffFile.exists()) {
//								System.out.println("Destination File exists!");
//							}else {
//								if(originFile.renameTo(moveTiffFile)){
//									System.out.println("File is moved successful!");
//									//上傳TIFF檔案----start
//									String filePath = localTiffFileMovePath + originFile.getName();
//									String businessNo =  originFile.getName().replaceAll("FRISIG", "").replaceAll(".tiff", "");
//							       
//							        String responseString = uploadFile(filePath, businessNo);
//							        System.out.println("responseString : " + responseString);
//							        FileUploadResponseVo vo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
//							        System.out.println("status:" + vo.getStatus());
//							        System.out.println("message:" + vo.getMessage());
//							        System.out.println("uploadOid:" + vo.getUploadOid());
//									//上傳ITFF檔案----end
//								}else{
//									System.out.println("File is failed to move!");
//								}
//							}
							
							//上傳TIFF檔案----start
							String filePath = originFile.getAbsolutePath();
							String businessNo =  originFile.getName().replaceAll("FRISIG", "").replaceAll(".tiff", "");
							businessNo = batchNo + firCtbcBatchMain.getBatchSeq() + businessNo;
					        String responseString = uploadFile(filePath, businessNo);
					        System.out.println("responseString : " + responseString);
					        FileUploadResponseVo vo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
					        System.out.println("status:" + vo.getStatus());
					        System.out.println("message:" + vo.getMessage());
					        System.out.println("uploadOid:" + vo.getUploadOid());
							//上傳ITFF檔案----end
						}
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				firCtbcBatchMain.setFilenameStl(firstlFileName);
				firCtbcBatchMain.setDataqtyStl(firstlFileDataCount);
				firCtbcBatchMain.setFilenameSnn(firsnnFileName);
				firCtbcBatchMain.setDataqtySnn(firsnnFileDataCount);
				firCtbcBatchMain.setFilenamePbl(firPblFileName);
				firCtbcBatchMain.setDataqtyPbl(firPblFileDataCount);
				firCtbcBatchMain.setFileqtySig(firsigTiffCount);
				updateFirCtbcBatchMain(firCtbcBatchMain, "S", "",  "", userInfo);//回寫FirCtbcBatchMain狀態S：正常
			}
		}
		//ZIP檔處理-----end
		
		//資料檢核-----start
		params = new HashMap();
		params.put("batchNo", batchNo);
		params.put("fileStatus", "S");
		params.put("transStatus", "N");
		params.put("deleteFlag", "N");
		params.put("sortBy", "BATCH_SEQ");
		params.put("sortType", "ASC");
		result = firCtbcBatchMainService.findFirCtbcBatchMainByParams(params);
		searchResult = null;
		List<FirCtbcTmpStl> firCtbcTmpStlList = null;
		List<FirCtbcTmpSnn> firCtbcTmpSnnList = null;
//		List<String[]> firCtbcSnnInsertList = null;
		if(result != null && result.getResObject() != null) {
			searchResult = (List<FirCtbcBatchMain>)result.getResObject();
		}else {
			updateFirBatchLog(firBatchLog, "F", "資料檢核異常-查無需處理的檔案。", userInfo);
			this.sendMail01("N", batchNo, executeTime, "");
			return this.getReturnResult("查無FirCtbcBatchMain資料");
		}
		if(searchResult != null && searchResult.size() > 0) {
			String tmpStlErr;
			String tmpStlWarn;
			String tmpIsTiff;
			String tmpFilenameTiff;
			String tmpDquakeNo;
			String tmpDquakeStatus;
			String tmpOidFirPremcalcTmp;
			String tmpWsFirAmt;
			String tmpWsQuakeAmt;
			String tmpFamtStatus;
			String tmpQamtStatus;
			String tmpAddrStatus;
			String tmpAddrDetail;
			String extraComname; 
			String handler1code; 
			String comcode; 
			String roofNo; 
			String wallNo; 
			String structure;
			String tmpProdcode;
			
			String rawData;
			String[] tempSplitData;
			String[] stlSplitData;
			String[] snnSplitData;
			FirCtbcBatchDtl firCtbcBatchDtl;
			String status;//下單狀態
			String sendType;//送件類別
			String signStatus;//簽署狀態
			String signDate;//簽署時間
			String orderSeq;//受理編號
			int count;
			String[] resultMsg = null;
			boolean checkInsured = false;//判斷有沒有被保人
			boolean checkApplicant = false;//判斷有沒有要保人
			boolean checkSnnRawData = true;//判斷snn資料檢核是否通過
			boolean checkSamePerson = false;//用身份證及要保被保人代碼來判斷資料是否重複
			String strSamePerson = "";
			
			FirEqFundQueryVo firEqFundQueryVo;
			FirAmountWsParamVo firWsParamVo;
			FirAddressCheckVo firAddressCheckVo;
			/* mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS start*/
			FirAddressRuleObj firAddressRuleObj;
			RuleReponseVo ruleReponseVo;
			/* mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS end*/
			FirPremcalcTmp firPremcalcTmp;
			String soapxml;
			String returnValue;
			byte[] decryptedByteArray;
			String tmp;
			String strTmpDate;
			BigDecimal stlRawDataFirAmt = null;
			BigDecimal stlRawDataEqAmt = null;
			String mappingRoofNo = "";
			String mappingWallNo = "";
			String mappingStructure = "";
			BigDecimal fireAmount = BigDecimal.ZERO;
			String mappingPostCode = "";
			FirCtbcStl firCtbcStl;
			FirCtbcSnn firCtbcSnn;
			List<FirCtbcSnn> firCtbcSnnList;
			List<String> orderSeqStatusList = Arrays.asList("2","3","4","6");
			
			for(FirCtbcBatchMain entity : searchResult) {
				params = new HashMap();
				params.put("batchNo", entity.getBatchNo());
				params.put("batchSeq", entity.getBatchSeq());
				params.put("pStatus", "N");
				params.put("sortBy", "FK_ORDER_SEQ");
				params.put("sortType", "ASC");
				result = this.firCtbcTmpStlService.findFirCtbcTmpStlByParams(params);
				if(result != null && result.getResObject() != null) {
					firCtbcTmpStlList = (List<FirCtbcTmpStl>)result.getResObject();
					try {
						this.cibPolicyDataImportService.insertFirCtbcBatchDtlData(firCtbcTmpStlList, userInfo);
					}catch(Exception e) {
						e.printStackTrace();
						updateFirCtbcBatchMain(entity, "E", "",  "E", userInfo);
						continue;
					}
					
					if(firCtbcTmpStlList != null && firCtbcTmpStlList.size() > 0) {
						for(FirCtbcTmpStl firCtbcTmpStl : firCtbcTmpStlList) {
							tmpStlErr = "";
							tmpStlWarn = "";
							tmpIsTiff = "";
							tmpFilenameTiff = "";
							extraComname = "";
							handler1code = "";
							comcode = "";
							roofNo = "";
							wallNo = "";
							structure = "";
							tmpProdcode = "";
							rawData = firCtbcTmpStl.getRawdata();
							tempSplitData = rawData.split("\\|");
							stlSplitData = Arrays.copyOf(tempSplitData, 45);
							firCtbcSnnList = new ArrayList<FirCtbcSnn>();
							if(stlSplitData.length >= 45) {
								firCtbcBatchDtl = new FirCtbcBatchDtl();
								firCtbcBatchDtl.setBatchNo(firCtbcTmpStl.getBatchNo());
								firCtbcBatchDtl.setBatchSeq(firCtbcTmpStl.getBatchSeq());
								firCtbcBatchDtl.setFkOrderSeq(firCtbcTmpStl.getFkOrderSeq());
								
								firCtbcTmpStl.setpStatus("Y");
								
								status = stlSplitData[4];
								sendType = stlSplitData[5];
								signDate = stlSplitData[42];
								firCtbcBatchDtl.setFkStatus(status);
								
								if(status.length() <= 0 || "01,02,09".indexOf(status) < 0) {
									if(status.length() <= 0) {
										tmpStlErr += "下單狀態不可空白;";
									}
									if("01,02,09".indexOf(status) < 0) {
										tmpStlErr += "下單狀態錯誤;";
									}
									
									firCtbcBatchDtl.setOrderSeqStatus("1");
									firCtbcBatchDtl.setFkStatus("XX");
									firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
									firCtbcBatchDtl.setCheckWarnMsg(tmpStlWarn);

									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
									continue;
								}

								if("02".equals(status)) {//中信暫存件
									firCtbcBatchDtl.setOrderSeqStatus("D");
									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
									continue;
								}else if("09".equals(status)) {//中信取消件
									/**
									 * 20200713
									 * BJ016
									 * 檢查這個受理編號之前有沒有進到新核心，
									 * 如果有進到核心要提示進行撤單或退保
									 * */
									params = new HashMap();
									orderSeq = stlSplitData[0];
									params.put("orderseq", orderSeq);
									count = this.prptmainService.countPrptmain(params);
									if(count > 0) {
										tmpStlWarn += "核心系統已有資料，請進行撤單或退保;";
										firCtbcBatchDtl.setCheckWarnMsg(tmpStlWarn);
									}
									
									firCtbcBatchDtl.setOrderSeqStatus("E");
									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
									continue;
								}else {
									if(sendType.length() <= 0 || "01,02,03".indexOf(sendType) < 0) {
										if(sendType.length() <= 0) {
											tmpStlErr += "送件類別不可空白;";
										}
										if("01,02,03".indexOf(sendType) < 0) {
											tmpStlErr += "送件類別錯誤;";
										}
										
										firCtbcBatchDtl.setOrderSeqStatus("1");
										firCtbcBatchDtl.setFkStatus("XX");
										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
										firCtbcBatchDtl.setCheckWarnMsg(tmpStlWarn);

										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
										continue;
									}
									
									signStatus = stlSplitData[38];
									if("01".equals(sendType)) {
										if(!"04".equals(signStatus)) {
											tmpStlErr += "下單完成且為正本送件時，簽署狀態應為04正本簽署;";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}else {
											signDate = "";
										}
									}
									
									if("02".equals(sendType)) {
										if(!"03".equals(signStatus)) {
											firCtbcBatchDtl.setOrderSeqStatus("F");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}else {
											//簽署時間-檢查-----start
											if(signDate == null || signDate .length() <= 0 || !this.checkDateFormat(signDate, "yyyy-MM-dd HH:mm:ss.SSS")) {
												tmpStlErr += "簽署時間異常;";
												firCtbcBatchDtl.setOrderSeqStatus("1");
												firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
												updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
												continue;
											}
										}
									}
									
									if("03".equals(sendType)) {
										if(!"05".equals(signStatus)) {
											tmpStlErr += "下單完成且為續保叫單時，簽署狀態應為05不需簽署;";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}else {
											signDate = "";
										}
									}
									
									//20200518-舊檢查方式-----start
//									if(signStatus.length() <= 0 || "01,02,03,04,05".indexOf(signStatus) < 0) {
//										tmpStlErr += "簽署狀態異常;";
//										firCtbcBatchDtl.setOrderSeqStatus("1");
//										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
//										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
//										continue;
//									}
//									
//									if("01".equals(signStatus) || "02".equals(signStatus)) {
//										firCtbcBatchDtl.setOrderSeqStatus("F");
//										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
//										continue;
//									}
									//20200518-舊檢查方式-----end
									
									params = new HashMap();
									orderSeq = stlSplitData[0];
									params.put("orderseq", orderSeq);
									count = this.prptmainService.countPrptmain(params);
									if(count > 0) {
										tmpStlErr += "核心系統已有資料，無法重複轉檔;";
										firCtbcBatchDtl.setOrderSeqStatus("1");
										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
										continue;
									}
									params = new HashMap();
									params.put("fkOrderSeq", orderSeq);
									params.put("deleteFlagNotEqual", "Y");
									params.put("orderSeqStatusList", orderSeqStatusList);
									count = this.firCtbcBatchDtlService.countFirCtbcBatchDtl(params);
									if(count > 0) {
										tmpStlErr += "此受理編號已轉入暫存但尚未轉入核心，無法重複轉檔;";
										firCtbcBatchDtl.setOrderSeqStatus("1");
										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
										continue;
									}
								}
								
								//從這邊開始作stlRawData的檢核
								resultMsg = this.stlRawDataCheck(stlSplitData, batchNo, firCtbcTmpStl.getBatchSeq());
								
								if(resultMsg[0] != null && resultMsg[0].length() > 0) {
									firCtbcBatchDtl.setOrderSeqStatus("1");
									firCtbcBatchDtl.setCheckErrMsg(resultMsg[0]);
									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
									continue;
								}else {
									if(resultMsg != null && resultMsg.length >= 10) {
										tmpStlWarn += resultMsg[1];
										tmpIsTiff = resultMsg[2];
										tmpFilenameTiff = resultMsg[3];
										extraComname = resultMsg[4];
										handler1code = resultMsg[5];
										comcode = resultMsg[6];
										roofNo = resultMsg[7];
										wallNo = resultMsg[8];
										structure = resultMsg[9];
										tmpProdcode = resultMsg[10];
									}
									params = new HashMap();
									params.put("batchNo", firCtbcTmpStl.getBatchNo());
									params.put("batchSeq", firCtbcTmpStl.getBatchSeq());
									params.put("fkOrderSeq", firCtbcTmpStl.getFkOrderSeq());
									params.put("pStatus", "N");
									result = this.firCtbcTmpSnnService.findFirCtbcTmpSnnByParams(params);
									if(result != null && result.getResObject() != null) {
										checkInsured = false;//判斷有沒有被保人
										checkApplicant = false;//判斷有沒有要保人
										checkSnnRawData = true;
										checkSamePerson = false;
										strSamePerson = "";
										firCtbcTmpSnnList = (List<FirCtbcTmpSnn>)result.getResObject();
										for(FirCtbcTmpSnn firCtbcTmpSnn : firCtbcTmpSnnList) {
											rawData = firCtbcTmpSnn.getRawdata();
											tempSplitData = rawData.split("\\|");
											snnSplitData = Arrays.copyOf(tempSplitData, 24);
											if(snnSplitData.length >= 24) {
												if("01".equals(snnSplitData[1])) {
													checkInsured = true;
												}else if("02".equals(snnSplitData[1])) {
													checkApplicant = true;
												}
												
												if(strSamePerson.indexOf(snnSplitData[1]+snnSplitData[3]) < 0) {
													strSamePerson += snnSplitData[1]+snnSplitData[3] + ",";
												}else {
													checkSamePerson = true;
												}
												
												resultMsg = this.snnRawDataCheck(snnSplitData, batchNo, firCtbcTmpStl.getBatchSeq());
												if(resultMsg[0] != null && resultMsg[0].length() > 0) {
													checkSnnRawData = false;
													firCtbcBatchDtl.setOrderSeqStatus("1");
													firCtbcBatchDtl.setCheckErrMsg(resultMsg[0]);
													updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
													break;
												} else {
													firCtbcSnn = this.mappingFirCtbcSnn(batchNo, firCtbcTmpStl.getBatchSeq(), entity.getFilenameSnn(), snnSplitData);
													/*mantis：FIR0438，處理人員：BJ085，需求單編號：FIR0438 中信新件_資料接收排程調整行職業別檢核 start*/
													if(resultMsg[1] != null && resultMsg[1].length() > 0) {
														tmpStlWarn += resultMsg[1];
													}
													/*mantis：FIR0438，處理人員：BJ085，需求單編號：FIR0438 中信新件_資料接收排程調整行職業別檢核 end*/
													if(resultMsg[2] != null && resultMsg[2].length() > 0) {
														firCtbcSnn.setCoreIsHighrisk(resultMsg[2]);
													}
													firCtbcSnnList.add(firCtbcSnn);
												}
											}
										}
										
										if(!checkSnnRawData) {
											continue;
										}
										
										if(!(checkInsured && checkApplicant)) {
											tmpStlErr += "要被保險人至少要各有一筆資料;";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}
										
										if(checkSamePerson) {
											tmpStlErr += "同一個身份別下要/被保險人身份證字號重複;";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}
									}else {
										tmpStlErr += "無對應要被保險人資料;";
										firCtbcBatchDtl.setOrderSeqStatus("1");
										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
										continue;
									}
								}
								
								
								//商業邏輯檢核-----start
								//複保險檢核-----start
								tmpDquakeNo = "";
								tmpDquakeStatus = "";
								soapxml = "";
								returnValue = "";
								mappingRoofNo = "";
								mappingWallNo = "";
								mappingStructure = "";
								fireAmount = BigDecimal.ZERO;
								mappingPostCode = "";
								
								firEqFundQueryVo = new FirEqFundQueryVo();
								firEqFundQueryVo.setSourceType("CTBC-R3");
								firEqFundQueryVo.setSourceCustno("");
								firEqFundQueryVo.setSourceUserid(userInfo.getUserId());
								//轉換保險起始日期
								strTmpDate = this.transferDateFormat(stlSplitData[12], "yyyyMMdd", "yyyy/MM/dd");
								firEqFundQueryVo.setStartDate(strTmpDate);
								//轉換保險結束日期
								strTmpDate = this.transferDateFormat(stlSplitData[13], "yyyyMMdd", "yyyy/MM/dd");
								firEqFundQueryVo.setEndDate(strTmpDate);
								firEqFundQueryVo.setPostcode(stlSplitData[25]);
								firEqFundQueryVo.setAddress(stlSplitData[26]+stlSplitData[27]+stlSplitData[28]);
								soapxml = WebserviceObjConvert.convertObjToBase64Str(FirEqFundQueryVo.class, firEqFundQueryVo);
//								soapxml = "CjxmaXJFcUZ1bmRRdWVyeVZvPgogICAgPGFkZHJlc3M+5Y+w5YyX5biC6Kix5piM6KGXMTfomZ8xOOaok+S5izE8L2FkZHJlc3M+CiAgICA8ZW5kRGF0ZT4yMDIwLzA2LzE5PC9lbmREYXRlPgogICAgPHBvc3Rjb2RlPjEwMDwvcG9zdGNvZGU+CiAgICA8c291cmNlVHlwZT5CMkI8L3NvdXJjZVR5cGU+CiAgICA8c291cmNlVXNlcmlkPkJJMDg2PC9zb3VyY2VVc2VyaWQ+CiAgICA8c3RhcnREYXRlPjIwMTkvMDYvMTk8L3N0YXJ0RGF0ZT4KPC9maXJFcUZ1bmRRdWVyeVZvPgo=";
								try {
									returnValue = clientFirDoubleInsService.queryDoubleIns(soapxml);
									decryptedByteArray = Base64.decodeBase64(returnValue);
									tmp = new String(decryptedByteArray, "UTF-8");
									logger.info("queryDoubleIns returnValue : " + tmp);
									System.out.println(tmp);
									firEqFundQueryVo = (FirEqFundQueryVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirEqFundQueryVo.class);
									if(firEqFundQueryVo != null) {
										tmpDquakeNo = firEqFundQueryVo.getRepeatPolicyno();
										if("0".equals(firEqFundQueryVo.getRepeatCode())) {
											tmpDquakeStatus = "S";
										}else if("1".equals(firEqFundQueryVo.getRepeatCode())) {
											tmpStlWarn += "檢核-複保險;";
//											firCtbcBatchDtl.setOrderSeqStatus("1");
//											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
//											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
//											continue;
											tmpDquakeStatus = "D";
										}else {
											tmpDquakeStatus = "E";
											tmpStlWarn += firEqFundQueryVo.getRepeatMsg() + ";";
										}
									}
								}catch(Exception e) {
									tmpDquakeStatus = "E";
									tmpStlWarn += "查詢複保險出現異常;";
								}
								
								//複保險檢核-----end
								//保額檢核-----start
								tmpOidFirPremcalcTmp = "";
								tmpWsFirAmt = "";
								tmpWsQuakeAmt = "";
								tmpFamtStatus = "1";
								tmpQamtStatus = "1";
								
								firWsParamVo = new FirAmountWsParamVo();
								firWsParamVo.setSourceType("CTBC-R3");
								firWsParamVo.setSourceUser(userInfo.getUserId());
								firWsParamVo.setCalcType("1");
								//轉換保險起始日期
//								strTmpDate = this.transferDateFormat(stlSplitData[12], "yyyyMMdd", "yyyy/MM/dd");
								firWsParamVo.setCalcDate(stlSplitData[12]);
								firWsParamVo.setChannelType("22");
								firWsParamVo.setPostcode(stlSplitData[25]);
								
								if(getRfrWallNoMap().containsKey(stlSplitData[33])) 
									mappingWallNo = getRfrWallNoMap().get(stlSplitData[33]);
								firWsParamVo.setWallno(mappingWallNo);
								
								if(getRfrRoofNoMap().containsKey(stlSplitData[32])) 
									mappingRoofNo = getRfrRoofNoMap().get(stlSplitData[32]);
								firWsParamVo.setRoofno(mappingRoofNo);

								firWsParamVo.setSumfloors(stlSplitData[34]);
								firWsParamVo.setBuildarea(stlSplitData[30]);
								firWsParamVo.setDecorFee("0");
								soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAmountWsParamVo.class, firWsParamVo);
								returnValue = clientFirAmountService.firAmountCal(soapxml);
								decryptedByteArray = Base64.decodeBase64(returnValue);
								tmp = new String(decryptedByteArray, "UTF-8");
								System.out.println(tmp);
								firPremcalcTmp = (FirPremcalcTmp)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirPremcalcTmp.class);
								if(firPremcalcTmp != null) {
									if(!"Y".equals(firPremcalcTmp.getReturnType())) {
										tmpStlErr += firPremcalcTmp.getReturnMsg() + ";";
										firCtbcBatchDtl.setOrderSeqStatus("1");
										firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
										updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
										continue;
									}
									tmpOidFirPremcalcTmp = firPremcalcTmp.getOid().toString();
									tmpWsFirAmt = firPremcalcTmp.getFsAmt().toString();
									tmpWsQuakeAmt = firPremcalcTmp.getEqAmt().toString();
									stlRawDataFirAmt = new BigDecimal(stlSplitData[19]);
									stlRawDataEqAmt = new BigDecimal(stlSplitData[21]);
									if("I650019".equals(tmpProdcode)) {
										if(stlRawDataFirAmt.compareTo(firPremcalcTmp.getFsMaxAmt()) > 0) {
											tmpFamtStatus = "3";
											tmpStlErr += "檢核-火險超額(保額上限："+firPremcalcTmp.getFsMaxAmt().toString() + ");";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}else if(stlRawDataFirAmt.compareTo(firPremcalcTmp.getFsAmt()) < 0){
											tmpFamtStatus = "2";
											tmpStlWarn += "檢核-火險不足額(建議保額："+firPremcalcTmp.getFsAmt().toString()+";";
										}
										
										if(stlRawDataEqAmt.compareTo(firPremcalcTmp.getEqAmt()) > 0) {
											tmpQamtStatus = "3";
											tmpStlErr += "檢核-地震險超額(建議保額："+firPremcalcTmp.getEqAmt().toString()+";";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}else if(stlRawDataEqAmt.compareTo(firPremcalcTmp.getEqAmt()) < 0){
											tmpQamtStatus = "2";
											tmpStlErr += "檢核-地震險不足額(建議保額："+firPremcalcTmp.getEqAmt().toString()+";";
											firCtbcBatchDtl.setOrderSeqStatus("1");
											firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
											updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
											continue;
										}
									}
								}
								//保額檢核-----end
								
								//地址正確性檢核-----start
								tmpAddrStatus = "";
								tmpAddrDetail = "";
								if(getRfrBuildingLevelCodeMap().containsKey(stlSplitData[35])) 
									mappingStructure = getRfrBuildingLevelCodeMap().get(stlSplitData[35]);
								
								firAddressCheckVo = new FirAddressCheckVo();
								firAddressCheckVo.setZip(stlSplitData[25]);
								firAddressCheckVo.setAddress(stlSplitData[26]+stlSplitData[27]+stlSplitData[28]);
								firAddressCheckVo.setStructure(mappingStructure);
								firAddressCheckVo.setFloors(stlSplitData[34]);
								firAddressCheckVo.setBuildyears(stlSplitData[31]);//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數

								soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressCheckVo.class, firAddressCheckVo);
								returnValue = clientAddressCheckService.addressCheck(soapxml);
								decryptedByteArray = Base64.decodeBase64(returnValue);
								tmp = new String(decryptedByteArray, "UTF-8");
								System.out.println(tmp);
								firAddressCheckVo = (FirAddressCheckVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirAddressCheckVo.class);
								if(firAddressCheckVo != null) {
									if("N".equals(firAddressCheckVo.getQueryResult())) {
										tmpStlWarn += "檢核-地址WS執行異常：" + firAddressCheckVo.getErrMsg() + ";";
										tmpAddrStatus = "E";
									}else {
										if("Y".equals(firAddressCheckVo.getCompareResult())) {
											tmpAddrStatus = "S";
											tmpAddrDetail = "";
										}else {
											tmpAddrStatus = "F";
											tmpAddrDetail = "";
											if(firAddressCheckVo.getFirAddressCompareVoList() != null && firAddressCheckVo.getFirAddressCompareVoList().size() > 0) {
												ArrayList<FirAddressCompareVo> facvList = firAddressCheckVo.getFirAddressCompareVoList();
												for(FirAddressCompareVo firAddressCompareVo : facvList) {
													tmpAddrDetail += firAddressCompareVo.getPolicyNo() + "/";
													if(getRfrStructureMap().containsKey(firAddressCompareVo.getStructure())) 
														tmpAddrDetail += getRfrStructureMap().get(firAddressCompareVo.getStructure()) + "/";
													else
														tmpAddrDetail += "查無對應建築等級/";
													//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數START
													tmpAddrDetail += firAddressCompareVo.getFloors() + "樓/";
													tmpAddrDetail += firAddressCompareVo.getBuildyears() + "年;";
													//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數END
												}
												facvList = null;
											}
										}
									}
								}
								//地址正確性檢核-----end
								
								//颱風洪水檢核-----start
								if(stlSplitData[19] != null && stlSplitData[19].length() > 0) {
									fireAmount = new BigDecimal(stlSplitData[19]);
								}
								
								if(fireAmount.compareTo(BigDecimal.ZERO) > 0 
										&& ("3".equals(mappingStructure) || "5".equals(mappingStructure) || "6".equals(mappingStructure))) {

									if(getRfrPostCodeMap().containsKey(stlSplitData[25])) {
										mappingPostCode = getRfrPostCodeMap().get(stlSplitData[25]);
									}
									if(mappingPostCode.length() >=3)
										mappingPostCode.subSequence(0, 3);
									
									if("基隆市".equals(mappingPostCode) || "宜蘭縣".equals(mappingPostCode) 
											|| "花蓮縣".equals(mappingPostCode) || "台東縣".equals(mappingPostCode) 
											|| "屏東縣".equals(mappingPostCode)) {
										tmpStlWarn += "易淹水地區;";
									}
								}
								//颱風洪水檢核-----end
								
								/**
								 * 20201007
								 * BJ016新增稽核議題檢核
								 * mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS
								 * */
								//TODO 稽核議題檢核
								//稽核議題檢核-----start
								firAddressRuleObj = new FirAddressRuleObj();
								firAddressRuleObj.setInsType("F02");
								firAddressRuleObj.setOperationType("P");
								firAddressRuleObj.setRulePrefix("FIR");
								firAddressRuleObj.setAddrRoof(mappingRoofNo);
								firAddressRuleObj.setAddrStructure(mappingStructure);
								firAddressRuleObj.setAddrSumfloors(stlSplitData[34]);
								firAddressRuleObj.setAddrWall(mappingWallNo);
								firAddressRuleObj.setAddress(stlSplitData[26]+stlSplitData[27]+stlSplitData[28]);
								firAddressRuleObj.setPostcode(stlSplitData[25]);
								soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressRuleObj.class, firAddressRuleObj);
								try {
									returnValue = clientRuleCheckService.ruleCheck(soapxml);
									decryptedByteArray = Base64.decodeBase64(returnValue);
									tmp = new String(decryptedByteArray, "UTF-8");
									System.out.println(tmp);
									ruleReponseVo = (RuleReponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, RuleReponseVo.class);
									if(ruleReponseVo != null) {
										if("0".equals(ruleReponseVo.getStatus())) {
											if(ruleReponseVo.getDetailList() != null && ruleReponseVo.getDetailList().size() > 0) {
												ArrayList<RuleReponseDetailVo> detailList = ruleReponseVo.getDetailList();
												for(RuleReponseDetailVo vo : detailList) {
													if("0".equals(vo.getRuleResult())) {
														tmpStlWarn += vo.getRuleMsg() + ";";
													}
												}
												detailList = null;
											}
										}
									}
								}catch(Exception e) {
									e.printStackTrace();
									tmpStlWarn += "檢核-稽核議題檢核WS無回應或發生異常: Exception;";
								}
								//稽核議題檢核-----end
								
								//商業邏輯檢核-----end
								
								//新增中信新件下單檔及中信新件關係人檔-----start
								try {
									firCtbcStl = this.mappingFirCtbcStl(batchNo, firCtbcTmpStl.getBatchSeq(), entity.getFilenameStl(), stlSplitData, extraComname, handler1code, comcode, roofNo, wallNo, structure);
									this.cibPolicyDataImportService.insertStlSnnData(firCtbcStl, firCtbcSnnList, userInfo);
									firCtbcBatchDtl.setOrderSeqStatus("2");
									firCtbcBatchDtl.setDquakeStatus(tmpDquakeStatus);
									firCtbcBatchDtl.setDquakeNo(tmpDquakeNo);
									firCtbcBatchDtl.setOidFirPremcalcTmp(Long.parseLong(tmpOidFirPremcalcTmp));
									firCtbcBatchDtl.setFamtStatus(tmpFamtStatus);
									firCtbcBatchDtl.setWsFirAmt(Long.parseLong(tmpWsFirAmt));
									firCtbcBatchDtl.setQamtStatus(tmpQamtStatus);
									firCtbcBatchDtl.setWsQuakeAmt(Integer.parseInt(tmpWsQuakeAmt));
									firCtbcBatchDtl.setAddrStatus(tmpAddrStatus);
									firCtbcBatchDtl.setAddrDetail(tmpAddrDetail);
									firCtbcBatchDtl.setIsTiff(tmpIsTiff);
									firCtbcBatchDtl.setFilenameTiff(tmpFilenameTiff);
									firCtbcBatchDtl.setFkStatus(stlSplitData[4]);
									firCtbcBatchDtl.setCheckWarnMsg(tmpStlWarn);
									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
								}catch(Exception e) {
									e.printStackTrace();
									tmpStlErr = "新增中信下單檔異常：" + e.getMessage();
									firCtbcBatchDtl.setOrderSeqStatus("G");
									firCtbcBatchDtl.setCheckErrMsg(tmpStlErr);
									updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl, userInfo);
								}
								//新增中信新件下單檔及中信新件關係人檔-----end
							}
						}
					}
				}
				
				firCtbcBatchMainProcessComplete(entity, userInfo);
			}
		}
		updateFirBatchLog(firBatchLog, "S", "", userInfo);
		sendMail01("S", batchNo, executeTime, "");
		//資料檢核-----end
		
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Result policyDataReturn(UserInfo userInfo) throws SystemException, Exception {
		
		Result result = new Result();
		String outStatus = "";
		String outMsg = "";
		
		//新增執行記錄檔-----start
		Date executeTime = new Date();
		result = cibPolicyDataImportService.inputFirBatchLog(executeTime, userInfo, "2", "FIR_CTBC_02");
		FirBatchLog firBatchLog = null;
		String batchNo = "";
		if(result != null && result.getResObject() != null) {
			firBatchLog = (FirBatchLog)result.getResObject();
			batchNo = firBatchLog.getBatchNo();
			if(batchNo == null || batchNo.trim().length() <= 0) {
				this.sendMail02("", "", executeTime, "批次號碼取號失敗。");
				return this.getReturnResult("批次號碼取號失敗");
			}
		}else {
			this.sendMail02("", "", executeTime, "批次號碼取號失敗。");
			return this.getReturnResult("新增FIR_BATCH_LOG批次程式執行記錄檔失敗");
		}
		//新增執行記錄檔-----end
		
		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----start
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("prgId", "FIR_CTBC_02_STATUS");
		result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		if(result != null && result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
			if("N".equals(firBatchInfo.getMailTo())) {
				updateFirBatchLog(firBatchLog, "S", "FIR_BATCH_INFO設定檔設定為排程暫停執行。", userInfo);
				return this.getReturnResult("FIR_BATCH_INFO設定檔設定為排程暫停執行。");
			}
		}
		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----end
		
		//中信新件回饋檔-回饋資料處理-----start
		//Call SP	產生投保證明-----start
		params = new HashMap<String,Object>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", "System");
		params.put("outResult", null);
		int iResult = this.firSpService.runSpRptCtbcCtf(params);
		if(iResult > 0) {
			outStatus = "N";
			outMsg = "投保證明SP執行失敗。";
			updateFirBatchLog(firBatchLog, "F", outMsg, userInfo);
			sendMail02("F", batchNo, executeTime, outMsg);
			return result;
		}
		
		params = new HashMap<String,Object>();
		params.put("rstBatchNo", batchNo);
		result = this.firRptCtbcCtfService.findFirRptCtbcCtfByParams(params);
		boolean checkGenPdf = false;
		String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		String filePath = configUtil.getString("localReturnFilePath") + (Integer.valueOf(date.substring(0, 6)) - 191100) + File.separator + date.substring(0, 8) + File.separator + "FRIRBK" + date + ".180" + File.separator;
		File fFilePath = new File(filePath);
		if(!fFilePath.exists()) {
			fFilePath.mkdirs();
		}
		if(result != null && result.getResObject() != null) {
			List<FirRptCtbcCtf> resultList = (List<FirRptCtbcCtf>)result.getResObject();
			FirCtbcBatchDtl firCtbcBatchDtl;
			Date now = null;
			for(FirRptCtbcCtf firRptCtbcCtf : resultList) {
				// 產生投保證明pdf
				checkGenPdf = this.genPdf(filePath, firRptCtbcCtf.getOid().toString(), firRptCtbcCtf.getOrderseq());
				
				/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start */
				//若來源別為中信保代網投
				if("2".equals(firRptCtbcCtf.getsType())) {
					now = new Date();
					FirCtbcB2b2c firCtbcB2b2c = new FirCtbcB2b2c();
					firCtbcB2b2c.setBatchNo(firRptCtbcCtf.getDtlBatchNo());
					firCtbcB2b2c.setBatchSeq(firRptCtbcCtf.getDtlBatchSeq());
					firCtbcB2b2c.setFkOrderSeq(firRptCtbcCtf.getOrderseq());
					if(checkGenPdf) {
						firCtbcB2b2c.setOrderSeqStatus("A");
					}else {
						firCtbcB2b2c.setOrderSeqStatus("I");
					}
					firCtbcB2b2c.setPrintCtfTime(now);
					firCtbcB2b2c.setIupdate(userInfo.getUserId());
					firCtbcB2b2c.setDupdate(now);
					cibPolicyDataImportService.updateSingleB2b2cData(firCtbcB2b2c);
				}else {
				/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end */
					firCtbcBatchDtl = new FirCtbcBatchDtl();
					firCtbcBatchDtl.setBatchNo(firRptCtbcCtf.getDtlBatchNo());
					firCtbcBatchDtl.setBatchSeq(firRptCtbcCtf.getDtlBatchSeq());
					firCtbcBatchDtl.setFkOrderSeq(firRptCtbcCtf.getOrderseq());
					now = new Date();
					if(checkGenPdf) {
						firCtbcBatchDtl.setOrderSeqStatus("A");
						firCtbcBatchDtl.setPrintCtfTime(now);
						firCtbcBatchDtl.setIupdate(userInfo.getUserId());
						firCtbcBatchDtl.setDupdate(now);
						
					}else {
						firCtbcBatchDtl.setOrderSeqStatus("I");
						firCtbcBatchDtl.setPrintCtfTime(now);
						firCtbcBatchDtl.setIupdate(userInfo.getUserId());
						firCtbcBatchDtl.setDupdate(now);
					}
					
					cibPolicyDataImportService.updateSingleDtlData(firCtbcBatchDtl, null);
				}
			}
		}
		//Call SP	產生投保證明-----end
		
		//Call SP	續保叫單之保單號碼回寫至FIR_CTBC_BATCH_DTL-----start
		params = new HashMap<String,Object>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", "System");
		params.put("outResult", null);
		iResult = this.firSpService.runSpFirCtbcR7(params);
		if(iResult > 0) {
			outStatus = "N";
			outMsg = "續保叫單保單號回寫批次明細檔失敗。";
			updateFirBatchLog(firBatchLog, "F", outMsg, userInfo);
			sendMail02("F", batchNo, executeTime, outMsg);
			return result;
		}
		//Call SP	續保叫單之保單號碼回寫至FIR_CTBC_BATCH_DTL-----end
		
		//產生回饋檔-----start
		result = this.firCtbcBatchDtlService.findFirCtbcBatchDtlForFeedback(batchNo);
		if(result != null && result.getResObject() != null) {
			List<FirCtbcRst> firCtbcRstList = (List<FirCtbcRst>)result.getResObject();
			try {
				result = null;
				result = cibPolicyDataImportService.insertRstData(batchNo, firCtbcRstList, userInfo);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(result != null && result.getResObject() != null) {
				firCtbcRstList = (List<FirCtbcRst>)result.getResObject();
				if(firCtbcRstList != null && firCtbcRstList.size() > 0) {
					StringBuffer sb = new StringBuffer();
					List<FirCtbcBatchDtl> firCtbcBatchDtlList = new ArrayList<FirCtbcBatchDtl>();//更新狀態用
					/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start*/
					List<FirCtbcB2b2c> firCtbcB2b2cList = new ArrayList<>();
					FirCtbcB2b2c firCtbcB2b2c;
					/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end*/
					FirCtbcBatchDtl firCtbcBatchDtl;
					for(FirCtbcRst firCtbcRst: firCtbcRstList) {
						sb.append(firCtbcRst.getFkOrderSeq()).append("|");
						sb.append(StringUtil.nullToSpace(firCtbcRst.getPolicyno())).append("|");
						sb.append(StringUtil.nullToSpace(firCtbcRst.getInscoStatus())).append("|");
						sb.append(StringUtil.nullToSpace(firCtbcRst.getInscoFeedback()).replaceAll("[\\r\\n ]", "")).append("\r\n");
						
						/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 start*/
						//增加判斷保代網投件
						if(!"5".equals(firCtbcRst.getRstType())) {
							firCtbcBatchDtl = new FirCtbcBatchDtl();
							firCtbcBatchDtl.setBatchNo(firCtbcRst.getoBatchNo());
							firCtbcBatchDtl.setBatchSeq(firCtbcRst.getoBatchSeq());
							firCtbcBatchDtl.setFkOrderSeq(firCtbcRst.getFkOrderSeq());
							firCtbcBatchDtl.setBkType(firCtbcRst.getInscoStatus());
							firCtbcBatchDtlList.add(firCtbcBatchDtl);
						}else {
							firCtbcB2b2c = new FirCtbcB2b2c();
							firCtbcB2b2c.setBatchNo(firCtbcRst.getoBatchNo());
							firCtbcB2b2c.setBatchSeq(firCtbcRst.getoBatchSeq());
							firCtbcB2b2c.setFkOrderSeq(firCtbcRst.getFkOrderSeq());
							firCtbcB2b2c.setBkType(firCtbcRst.getInscoStatus());
							firCtbcB2b2cList.add(firCtbcB2b2c);
						}
						/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 end*/
					}
					
					String fileName = "FRIRST" + date + ".180";
					String outputFile = filePath + fileName;
					boolean checkFileOutput = this.fileWrite(outputFile, sb.toString());
					Date dFileCreate = new Date();
					if(checkFileOutput) {
						cibPolicyDataImportService.updateListFirCtbcBatchDtlData(firCtbcBatchDtlList, userInfo, "B", fileName, dFileCreate);
						//mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投
						cibPolicyDataImportService.updateListB2b2cData(firCtbcB2b2cList, userInfo, "B", fileName, dFileCreate);
					}else {
						cibPolicyDataImportService.updateListFirCtbcBatchDtlData(firCtbcBatchDtlList, userInfo, "C", "", null);
						//mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投
						cibPolicyDataImportService.updateListB2b2cData(firCtbcB2b2cList, userInfo, "C", "", null);
					}
					
					//ZIP檔處理-----start
					ZipUtil zipUtil = new ZipUtil();
					ArrayList<File> filesToAdd = new ArrayList<File>();
					if(fFilePath != null && fFilePath.isDirectory()) {
						for (File fileEntry : fFilePath.listFiles()) {
							if (fileEntry.isFile()) {
								filesToAdd.add(fileEntry);
							}
						}
					}
					if(filesToAdd.size() > 0) {
						zipUtil.writeZip(filePath.substring(0, filePath.length()-1), filesToAdd);
						//上傳到中信sftp
						uploadZipFileToSftp(filePath.substring(0, filePath.length()-1) + ".zip");
					}
					//ZIP檔處理-----end
				}
			}
		}else {
			outStatus = "0";
			outMsg = "";
			updateFirBatchLog(firBatchLog, "N", outMsg, userInfo);
			sendMail02("N", batchNo, executeTime, outMsg);
			return result;
		}
		//產生回饋檔-----end
		
		updateFirBatchLog(firBatchLog, "S", "", userInfo);
		sendMail02("S", batchNo, executeTime, "");
		//中信新件回饋檔-回饋資料處理-----end
		
		/**
		 * 因為資料夾是先建出來的，
		 * 有可能因為沒有資料而不會在這個資料夾中產生回饋檔，
		 * 所以最後要檢查如果資料夾中都沒有資料的話，就把資料夾刪除
		 * */
		try {
			Path path = Paths.get(filePath);
			boolean hasFile = false;
			try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
				hasFile = dirStream.iterator().hasNext();
				if(!hasFile) {
					if(fFilePath != null && fFilePath.isDirectory()) {
						fFilePath.delete();
					}
				}
		    }
		}catch(Exception e) {
			/**
			 * Path path = Paths.get(filePath);
			 * 這段如果filePath是不存在的話，
			 * 會丟出exception
			 * */
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String args[]) throws Exception{
//		List<String> downloadFileList = new ArrayList<String>();
//		downloadFileList.add("FRIFIN202003261717.180.zip");
//		downloadFileList.add("FRIFIN202003261716.180.zip");
//		downloadFileList.add("FRIFIN202003161717.180.zip");
//		if(downloadFileList != null && downloadFileList.size() > 0) {
//			Collections.sort(downloadFileList);
//			for(String filename : downloadFileList) {
//				System.out.println(filename);
//			}
//		}
		
//		String test = "a|b||||||c";
//		String[] arrTest = test.split("\\|");
//		System.out.println("arrTest size : " + arrTest.length);
//		String[] arrTest3 = Arrays.copyOf(arrTest, 8);
//		System.out.println("arrTest3 size : " + arrTest3.length);
//		System.out.println("arrTest3 = " + Arrays.toString(arrTest3));
//		String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
//		date = "202004231328";
//		String filePath = "D:"+ File.separator + "ctbcFile" + File.separator + (Integer.valueOf(date.substring(0, 6)) - 191100) + File.separator + date.substring(0, 8) + File.separator + "FRIRBK" + date + ".180" + File.separator;
//		File fFilePath = new File(filePath);
//		if(!fFilePath.exists()) {
//			fFilePath.mkdirs();
//		}
//		//ZIP檔處理-----start
//		ZipUtil zipUtil = new ZipUtil();
//		ArrayList<File> filesToAdd = new ArrayList<File>();
//		if(fFilePath != null && fFilePath.isDirectory()) {
//			for (File fileEntry : fFilePath.listFiles()) {
//				if (fileEntry.isFile()) {
//					filesToAdd.add(fileEntry);
//				}
//			}
//		}
//		if(filesToAdd.size() > 0) {
//			zipUtil.writeZip(filePath.substring(0, filePath.length()-1), filesToAdd);
//		}
//		System.out.println("make zip file finished...");
//		//ZIP檔處理-----end
//		//全型轉半型-----start
//		String str = "０";
//		 System.out.println("before str : " + str);
//		for(char c:str.toCharArray()){
//		    str = str.replaceAll("　", " ");
//		    if((int)c >= 65281 && (int)c <= 65374){
//		      str = str.replace(c, (char)(((int)c)-65248));
//		    }
//		  }
//		  System.out.println("after str : " + str);
//		//全型轉半型-----end
//		//測試取得檔案list----start
//		String riskCode = "F";
//		String businessNo = "A1234567890";
//       
//        String responseString = downloadListService(riskCode, businessNo);
//        System.out.println("responseString : " + responseString);
//        Map classMap = new HashMap();
//        classMap.put("fileList", FileVo.class);
//        FileListResponseVo vo = (FileListResponseVo)JsonUtil.getDTO(responseString, FileListResponseVo.class, classMap);
//        ArrayList<FileVo> list = vo.getFileList();
//        for(FileVo fv : list) {
//        	System.out.println("----------");
//        	System.out.println("oid : " + fv.getOid());
//        	System.out.println("name : " + fv.getName());
//        	System.out.println("downloadPath : " + fv.getDownloadPath());
//        	System.out.println("----------");
//        }
//        
//		//測試取得檔案list----end
		//測試上傳檔案----start
//		String filePath = "D:\\aptoap\\insco\\180\\folderB\\FRISIGFK2020052200003.tiff";
//		String businessNo = "FK2020042200001";
//       
//        String responseString = uploadFile(filePath, businessNo);
//        System.out.println("responseString : " + responseString);
//        FileUploadResponseVo vo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
//        System.out.println("status:" + vo.getStatus());
//        System.out.println("message:" + vo.getMessage());
//        System.out.println("uploadOid:" + vo.getUploadOid());
		//測試上傳檔案----end
		
		//測試資料夾內是否沒有檔案，沒有檔案的話就刪除資料夾---start
		String folderPath = "D:\\aptoap\\insco\\180\\RECASPO\\FRIFIN202007101001.180";
		Path path = Paths.get(folderPath);
		boolean hasFile = false;
		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
			hasFile = dirStream.iterator().hasNext();
			System.out.println("hasFile:" + hasFile);
			if(!hasFile) {
				File folder = new File(folderPath);
				if(folder != null && folder.isDirectory()) {
					folder.delete();
					System.out.println("delete folder");
				}
			}else {
				System.out.println("folder has file,can not delete.");
			}
	    }
		//測試資料夾內是否沒有檔案，沒有檔案的話就刪除資料夾---end
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getZipFileFromSftp() throws Exception{
		String sftpHost = configUtil.getString("ctbcFTP");
		String sftpUser = configUtil.getString("ctbcFtpUserGet");
		String sftpPwd = configUtil.getString("ctbcFptPwdGet");
		int sftpPort = 22;
		String remoteDir = configUtil.getString("ctbcRemotePath");
		String workingDirPath = configUtil.getString("localFilePath");
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
		
		//下載檔案到指定目錄
		if(downloadFileList.size() > 0) {
			sftpUtil.getFileFromSftp(remoteDir, workingDirPath, downloadFileList);
		}
		return downloadFileList;
	}
	
	private boolean uploadZipFileToSftp(String filePath) throws Exception{
		boolean result = false;
		String sftpHost = configUtil.getString("ctbcFTP");
		String sftpUser = configUtil.getString("ctbcFtpUserPut");
		String sftpPwd = configUtil.getString("ctbcFptPwdPut");
		int sftpPort = 22;
		String remoteDir = configUtil.getString("ctbcRemotePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}
	
	private void updateFirBatchLog(FirBatchLog firBatchLog, String status, String remark, UserInfo userInfo) throws Exception{
		if(remark.length() > 300) {
			remark = remark.substring(0, 300);
		}
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark);
		firBatchLog.setIupdate(userInfo.getUserId());
		firBatchLog.setDupdate(new Date());
		cibPolicyDataImportService.updateFirBatchLog(firBatchLog);
	}
	
	private void updateFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain, String status, String remark, String transStatus, UserInfo userInfo) throws Exception{
		if(remark.length() > 300) {
			remark = remark.substring(0, 300);
		}
		firCtbcBatchMain.setFileStatus(status);
		firCtbcBatchMain.setRemark(remark);
		if(transStatus != null && transStatus.length() > 0) {
			firCtbcBatchMain.setTransStatus(transStatus);
		}
		firCtbcBatchMain.setIupdate(userInfo.getUserId());
		firCtbcBatchMain.setDupdate(new Date());
		cibPolicyDataImportService.updateFirCtbcBatchMain(firCtbcBatchMain);
	}
	
	private void firCtbcBatchMainProcessComplete(FirCtbcBatchMain firCtbcBatchMain, UserInfo userInfo) throws Exception{
		firCtbcBatchMain.setTransStatus("Y");
		firCtbcBatchMain.setIupdate(userInfo.getUserId());
		firCtbcBatchMain.setDupdate(new Date());
		cibPolicyDataImportService.updateFirCtbcBatchMain(firCtbcBatchMain);
	}
	
	private void updateSingleDtlData(FirCtbcBatchDtl firCtbcBatchDtl, FirCtbcTmpStl firCtbcTmpStl, UserInfo userInfo) throws Exception{
//		if(remark.length() > 300) {
//			remark = remark.substring(0, 300);
//		}
//		firCtbcBatchDtl.setOrderSeqStatus(status);
//		firCtbcBatchDtl.setFkStatus(fkStatus);
		Date now = new Date();
		firCtbcBatchDtl.setIupdate(userInfo.getUserId());
		firCtbcBatchDtl.setDupdate(now);
		
		firCtbcTmpStl.setIupdate(userInfo.getUserId());
		firCtbcTmpStl.setDupdate(now);
		cibPolicyDataImportService.updateSingleDtlData(firCtbcBatchDtl, firCtbcTmpStl);
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private FirCtbcBatchMain getFirCtbcBatchMain(String zipFileName, List<FirCtbcBatchMain> list){
		if(list != null && list.size() > 0) {
			for(FirCtbcBatchMain entity : list) {
				if(zipFileName.equals(entity.getFilenameZip()))
					return entity;
			}
		}
		return null;
	}
	
	private List<String> readUsingFileReader(File file) throws IOException {
		List<String> rawDataList = new ArrayList<String>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.println("Reading text file using FileReader");
        while((line = br.readLine()) != null){
            //process the line
        	if(line.trim().length() > 0) {
        		System.out.println(line);
                rawDataList.add(line);
        	}
        }
        br.close();
        isr.close();
        br = null;
        isr = null;
        return rawDataList;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String[] stlRawDataCheck(String[] rawData, String batchNo, String batchSeq) throws Exception{
		String[] returnMsg = new String[11];//0:TMP_STL_ERR; 1:TMP_STL_WARN; 2:tmpIsTiff; 3:tmpFilenameTiff
		returnMsg[0] = "";
		returnMsg[1] = "";
		returnMsg[2] = "";
		returnMsg[3] = "";
		returnMsg[4] = "";//TMP_EXTRA_COMCODE
		returnMsg[5] = "";//TMP_HANDLER1CODE
		returnMsg[6] = "";//TMP_COMCODE
		returnMsg[7] = "";//TMP_ROOFNO
		returnMsg[8] = "";//TMP_WALLNO
		returnMsg[9] = "";//TMP_STRUCTURE
		returnMsg[10] = "";//TMP_PRODCODE

		String orderSeq = rawData[0];
		String tmpIsTiff = "";
		String tmpFilenameTiff = "";
		Map params = new HashMap();
		int dataCount = 0;
		Result result = null;
		
		//送件類別-檢查-----start
		if("01,02,03".indexOf(rawData[5]) < 0) {
			returnMsg[0] += "送件類別異常;";
		}
		if("02".equals(rawData[5])) {
			tmpIsTiff = "Y";
			params.clear();
			params.put("batchNo", batchNo);
			params.put("batchSeq", batchSeq);
			params.put("fkOrderSeq", orderSeq);
			dataCount = this.firCtbcSigService.countFirCtbcSig(params);
			if(dataCount > 0) {
				result = this.firCtbcSigService.findFirCtbcSigByParams(params);
				if(result != null && result.getResObject() != null) {
					List<FirCtbcSig> resultList = (List<FirCtbcSig>)result.getResObject();
					FirCtbcSig firCtbcSig = resultList.get(0);
					tmpFilenameTiff = firCtbcSig.getFilenameSig();
					resultList = null;
					firCtbcSig = null;
				}else {
					tmpFilenameTiff = "";
					returnMsg[1] += "傳真送件但無對應簽署文件;";
				}
			}else {
				tmpFilenameTiff = "";
				returnMsg[1] += "傳真送件但無對應簽署文件;";
			}
			
			returnMsg[2] = tmpIsTiff;
			returnMsg[3] = tmpFilenameTiff;
		}
		//送件類別-檢查-----end
		
		//商品代號-檢查-----start
		if(rawData[6] == null || rawData[6] .length() <= 0) {
			returnMsg[0] += "商品代號不可空白;";
		}else {
			if(!"I650019".equals(rawData[6]) &&!"I650020".equals(rawData[6])) {
				returnMsg[0] += "商品代號應為I650019或I650020;";
			}else {
				returnMsg[10] = rawData[6];
			}
		}
		//商品代號-檢查-----end
		
		//險種代號-檢查-----start
		if(rawData[7] == null || rawData[7] .length() <= 0) {
			returnMsg[0] += "商品代號不可空白;";
		}else {
			if("I650019".equals(rawData[6]) && !"FH1".equals(rawData[7]) && !"FE02".equals(rawData[7])) {
				returnMsg[0] += "商品代號為I650019時，險種代號應為FH1或FE02;";
				returnMsg[10] = "";
			}
			if("I650020".equals(rawData[6]) && !"FE021".equals(rawData[7])) {
				returnMsg[0] += "商品代號為I650020時，險種代號應為FE021;";
				returnMsg[10] = "";
			}
		}
		//險種代號-檢查-----end
		
		//行動電話-檢查-----start
		if(rawData[8] != null && rawData[8] .length() > 0) {
			String mobileRegExp="^[0-9]{10}$";
			Pattern pattern = Pattern.compile(mobileRegExp);
			Matcher isNum = pattern.matcher(rawData[8]);
	        if( !isNum.matches() ){
	        	returnMsg[0] += "行動電話格式不正確;";
	        }  
		}
		//行動電話-檢查-----end
		
		//email-檢查-----start
		if(rawData[9] != null && rawData[9] .length() > 0) {
			String emailRegExp1="^([a-zA-Z0-9!#\\$%&'\\*\\+\\-\\/=\\?\\^_`\\{\\|\\}~；]+[\\.]?)+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
			String emailRegExp2="^(\"+[a-zA-Z0-9!#\\$%&'\\*\\+\\-\\/=\\?\\^_`\\{\\|\\}~；\\.]+\")+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
			Pattern pattern = Pattern.compile(emailRegExp1);
			Matcher isMatch = pattern.matcher(rawData[9]);
			Pattern pattern2 = Pattern.compile(emailRegExp2);
			Matcher isMatch2 = pattern2.matcher(rawData[9]);
	        if( !isMatch.matches() && !isMatch2.matches()){
	        	returnMsg[0] += "電子郵件格式不正確;";
	        }  
		}
		//email-檢查-----end
		
		//保險公司代碼-檢查-----start
		if(!"180".equals(rawData[11])) {
			returnMsg[0] += "保險公司代號應為180;";
		}
		//保險公司代碼-檢查-----end
		
		//保險開始日-檢查-----start
		if(rawData[12] == null || rawData[12] .length() <= 0) {
			returnMsg[0] += "保險開始日異常;";
		}else if(!this.checkDateFormat(rawData[12], "yyyyMMdd")){
			returnMsg[0] += "保險開始日異常;";
		/*mantis：FIR0387，處理人員：BJ085，需求單編號：FIR0387 住火-中信新件資料接收排程調整保期檢核 start*/
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date sysdate = sdf.parse(sdf.format(new Date()));
			Calendar cl = Calendar.getInstance();
			cl.setTime(sysdate);
			cl.add(Calendar.MONTH,6);//系統日期加六個月
			sysdate = cl.getTime();
			
			if(sdf.parse(rawData[12]).after(sysdate)) {
				returnMsg[0] += "保險開始日異常-保險開始日超過系統日+6個月;";
			}
		/*mantis：FIR0387，處理人員：BJ085，需求單編號：FIR0387 住火-中信新件資料接收排程調整保期檢核 end*/
		}
		//保險開始日-檢查-----end
		
		//保險到期日-檢查-----start
		if(rawData[13] == null || rawData[13] .length() <= 0) {
			returnMsg[0] += "保險到期日異常;";
		}else if(!this.checkDateFormat(rawData[13], "yyyyMMdd")){
			returnMsg[0] += "保險到期日異常;";
		}
		//保險到期日-檢查-----end
		
		//保險期間-檢查-----start
		if(rawData[14] == null || rawData[14] .length() <= 0) {
			returnMsg[0] += "保險期間不可空白;";
		}
		//保險期間-檢查-----end
		
		//洽訂文件歸屬行-檢查-----start
		boolean checkBank = true;
		if(rawData[16] == null || rawData[16] .length() <= 0) {
			returnMsg[0] += "洽訂文件歸屬行不可空白;";
			checkBank = false;
		}
		//洽訂文件歸屬行-檢查-----end
		
		//放款帳務行-檢查-----start
		if(rawData[17] == null || rawData[17] .length() <= 0) {
			returnMsg[0] += "放款帳務行不可空白;";
			checkBank = false;
		}
		//放款帳務行-檢查-----end
		
		//洽訂文件歸屬行及放款帳務行-檢查-----start
		if(checkBank) {
			params = new HashMap();
			//20200710舊查詢條件----start
//			params.put("branchNo", rawData[17]);
//			params.put("receivedBranch", rawData[16]);
//			result = this.firCtbcDeptinfoService.findFirCtbcDeptinfoByParams(params);
//			if(result != null && result.getResObject() != null) {
//				List<FirCtbcDeptinfo> resultList = (List<FirCtbcDeptinfo>)result.getResObject();
//				FirCtbcDeptinfo firCtbcDeptinfo = resultList.get(0);
//				returnMsg[4] = firCtbcDeptinfo.getBranchName() + firCtbcDeptinfo.getReceivedBranchName();
//				returnMsg[5] = firCtbcDeptinfo.getHandler1code();
//				returnMsg[6] =firCtbcDeptinfo.getComcode();
//			}else {
//				returnMsg[1] += "無法取得中信單位名稱及對應服務人員、歸屬單位(" + rawData[17] + "-" + rawData[16] + ");";
//			}
			//20200710舊查詢條件----end
			
			/**
			 * 20200722
			 * BJ016
			 * 新增代收機構檢查
			 * */
			String tempName = "";
			if(rawData[10] == null || rawData[10] .length() <= 0) {
				returnMsg[1] += "無法取得對應服務人員、歸屬單位(無代收區號);";
			}else {
				params.put("branchNo", "TLG");
				params.put("receivedBranch", rawData[10]);
				result = this.firCtbcDeptinfoService.findFirCtbcDeptinfoByParams(params);
				
				if(result != null && result.getResObject() != null) {
					List<FirCtbcDeptinfo> resultList = (List<FirCtbcDeptinfo>)result.getResObject();
					FirCtbcDeptinfo firCtbcDeptinfo = resultList.get(0);
					tempName = firCtbcDeptinfo.getReceivedBranchName();
					returnMsg[5] = firCtbcDeptinfo.getHandler1code();
					returnMsg[6] =firCtbcDeptinfo.getComcode();
				}else {
					returnMsg[1] += "無法取得對應服務人員、歸屬單位(" + rawData[10] + ");";
				}
			}

			params = new HashMap();
			params.put("branchNo", rawData[17]);
			params.put("receivedBranch","TLG");
			result = this.firCtbcDeptinfoService.findFirCtbcDeptinfoByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcDeptinfo> resultList = (List<FirCtbcDeptinfo>)result.getResObject();
				FirCtbcDeptinfo firCtbcDeptinfo = resultList.get(0);
				returnMsg[4] = firCtbcDeptinfo.getBranchName() + tempName;
			}else {
				returnMsg[1] += "無法取得中信單位名稱(" + rawData[17] + ");";
			}
		}
		//洽訂文件歸屬行及放款帳務行-檢查-----end
		
		//火險保額-檢查-----start
		BigDecimal bdNum;
		if("I650019".equals(rawData[6])) {
			if(rawData[19] == null || rawData[19].trim().length() <= 0) {
				returnMsg[0] += "商品代碼為I650019時，需有火險保額;";
			}else {
				if(this.isNumeric(rawData[19])) {
					bdNum = new BigDecimal(rawData[19]);
					if(bdNum.compareTo(BigDecimal.ZERO) <= 0) {
						returnMsg[0] += "商品代碼為I650019時，需有火險保額;";
					}
				}
			}
		}else if("I650020".equals(rawData[6])) {
			if(rawData[19] != null && rawData[19] .length() > 0) {
				if(this.isNumeric(rawData[19])) {
					bdNum = new BigDecimal(rawData[19]);
					if(bdNum.compareTo(BigDecimal.ZERO) != 0) {
						returnMsg[0] += "商品代碼為I650020時，不可以有火險保額;";
					}
				}else {
					returnMsg[0] += "商品代碼為I650020時，不可以有火險保額;";
				}
			}
		}
		//火險保額-檢查-----end
		
		//火險保費-檢查-----start
		if("I650019".equals(rawData[6])) {
			if(rawData[20] == null || rawData[20].trim().length() <= 0) {
				returnMsg[0] += "商品代碼為I650019時，需有火險保費;";
			}else {
				if(this.isNumeric(rawData[20])) {
					bdNum = new BigDecimal(rawData[20]);
					if(bdNum.compareTo(BigDecimal.ZERO) <= 0) {
						returnMsg[0] += "商品代碼為I650019時，需有火險保費;";
					}
				}
			}
		}else if("I650020".equals(rawData[6])) {
			if(rawData[20] != null && rawData[20] .length() > 0) {
				if(this.isNumeric(rawData[20])) {
					bdNum = new BigDecimal(rawData[20]);
					if(bdNum.compareTo(BigDecimal.ZERO) != 0) {
						returnMsg[0] += "商品代碼為I650020時，不可以有火險保費;";
					}
				}else {
					returnMsg[0] += "商品代碼為I650020時，不可以有火險保費;";
				}
			}
		}
		//火險保費-檢查-----end
		
		//地震險保額-檢查-----start
		boolean hasCoverage = true;
		if(rawData[21] != null && rawData[21] .length() > 0) {
			if(!this.isNumeric(rawData[21])) {
				returnMsg[0] += "地震險保額異常;";
			}else {
				if(Integer.parseInt(rawData[21]) <= 0) {
					returnMsg[0] += "地震險保額異常;";
				}
			}
		}else {
			returnMsg[0] += "地震險保額異常;";
			hasCoverage = false;
		}
		//地震險保額-檢查-----end
		
		//地震險保費-檢查-----start
		boolean hasPremium = true;
		if(rawData[22] != null && rawData[22] .length() > 0) {
			if(!this.isNumeric(rawData[22])) {
				returnMsg[0] += "地震險保費異常;";
			}else {
				if(Integer.parseInt(rawData[22]) <= 0) {
					returnMsg[0] += "地震險保費異常;";
				}
			}
		}else {
			returnMsg[0] += "地震險保費異常;";
			hasPremium = false;
		}
		//地震險保費-檢查-----end
		
		//地震險保額及地震險保費-檢查-----start
		if(hasCoverage && !hasPremium) {
			returnMsg[0] += "有地震險保額但沒有地震險保費;";
		}else if(!hasCoverage && hasPremium) {
			returnMsg[0] += "有地震險保費但沒有地震險保額;";
		}
		//地震險保額及地震險保費-檢查-----end
		
		//總保費-檢查-----start
		if(rawData[23] != null && rawData[23] .length() > 0) {
			if(!this.isNumeric(rawData[23])) {
				returnMsg[0] += "總保費異常;";
			}else {
				if(Integer.parseInt(rawData[23]) <= 0) {
					returnMsg[0] += "總保費異常;";
				}
			}
		}else {
			returnMsg[0] += "總保費異常;";
		}
		//總保費-檢查-----end
		
		//標的物郵遞區號-檢查-----start
		if(rawData[25] != null && rawData[25] .length() > 0) {
			if(!getRfrPostCodeMap().containsKey(rawData[25])) {
				returnMsg[0] += "標的物郵遞區號異常;";
			}
		}else {
			returnMsg[0] += "標的物郵遞區號異常;";
		}
		//標的物郵遞區號-檢查-----end
		
		//縣/市-檢查-----start
		if(rawData[26] == null || rawData[26] .length() <= 0) {
			returnMsg[0] += "縣/市不可空白;";
		}
		//縣/市-檢查-----end
		
		//鄉鎮市區-檢查-----start
		if(rawData[27] == null || rawData[27] .length() <= 0) {
			returnMsg[0] += "鄉鎮市區不可空白;";
		}
		//鄉鎮市區-檢查-----end
		
		//標的物地址-檢查-----start
		if(rawData[28] == null || rawData[28] .length() <= 0) {
			returnMsg[0] += "標的物地址不可空白;";
		}else {
			String address = rawData[28];
			address = address.replaceAll("\\s+","");//去除所有的空白或tab或\n
			int index = address.indexOf("號");
			if(index > 0) {
				address = address.substring(index-1, index);//取號的前一碼判斷是不是數字
				address = this.transferFullwidthToHalfwidth(address);//全形數字轉半形數字
				if(!this.isNumeric(address) && !this.isChineseNumeric(address)) {
					returnMsg[0] += "標的物地址需要有號;";
				}
			}else {
				returnMsg[0] += "標的物地址需要有號;";
			}
		}
		//標的物地址-檢查-----end
		
		//標的物地區別-檢查-----start
		if(rawData[29] != null && rawData[29] .length() > 0) {
			if(!getRfrAreaCodeList().contains(rawData[29])) {
				returnMsg[0] += "標的物地區別異常;";
			}
		}else {
			returnMsg[0] += "標的物地區別異常;";
		}
		//標的物地區別-檢查-----end
		
		//坪數-檢查-----start
		if(rawData[30] != null && rawData[30] .length() > 0) {
			if(!this.isNumeric(rawData[30])) {
				returnMsg[0] += "坪數異常;";
			}else {
				if(Double.parseDouble(rawData[30]) <= 0) {
					returnMsg[0] += "坪數異常;";
				}
			}
		}else {
			returnMsg[0] += "坪數異常;";
		}
		//坪數-檢查-----end
		
		//建築年期-檢查-----start
		if(rawData[31] != null && rawData[31] .length() > 0) {
			if(!this.isNumeric(rawData[31])) {
				returnMsg[0] += "建築年期異常;";
			}else {
				if(!(Integer.parseInt(rawData[31]) > 0 && Integer.parseInt(rawData[31]) <= 200) ) {
					returnMsg[0] += "建築年期異常;";
				}
			}
		}else {
			returnMsg[0] += "建築年期異常;";
		}
		//建築年期-檢查-----end
		
		//屋頂代碼-檢查-----start
		if(rawData[32] == null || rawData[32] .length() <= 0) {
			returnMsg[0] += "屋頂代碼異常;";
		}
		else {
			/* mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整 START */
			//if(!("01".equals(rawData[32]) || "02".equals(rawData[32]) || "03".equals(rawData[32])
			//		|| "04".equals(rawData[32]) || "99".equals(rawData[32]))) {
			//	returnMsg[0] += "屋頂代碼異常;";
			//}else {
			/* mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整 END */
				if(!getRfrRoofNoMap().containsKey(rawData[32])) {
					returnMsg[0] += "轉換核心屋頂代碼異常;";
				}else {
					if(getRfrRoofNoMap().get(rawData[32]).length() <= 0) {
						returnMsg[0] += "轉換核心屋頂代碼異常;";
					}else {
						returnMsg[7] = getRfrRoofNoMap().get(rawData[32]);
					}
				}
				if("99".equals(rawData[32])) {
					returnMsg[1] += "屋頂代碼為「其他屋頂」;";
				}
			//mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整
			//}
		}
		//屋頂代碼-檢查-----end
		
		//建材代碼-檢查-----start
		if(rawData[33] == null || rawData[33] .length() <= 0) {
			returnMsg[0] += "建材代碼異常;";
		}else {
			/* mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整 START */
			//if(!("01".equals(rawData[33]) || "02".equals(rawData[33]) || "03".equals(rawData[33])
			//		|| "04".equals(rawData[33]) || "05".equals(rawData[33]) || "06".equals(rawData[33]))) {
			//	returnMsg[0] += "建材代碼異常;";
			//}else {
			/* mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整 END */
				if(!getRfrWallNoMap().containsKey(rawData[33])) {
					returnMsg[0] += "轉換核心外牆代碼異常;";
				}else {
					if(getRfrWallNoMap().get(rawData[33]).length() <= 0) {
						returnMsg[0] += "轉換核心外牆代碼異常;";
					}else {
						returnMsg[8] = getRfrWallNoMap().get(rawData[33]);
					}
				}
			//mantis：FIR0371，處理人員：BJ085，需求單編號：FIR0371 中信新增建材代號-APS中信新件資料接收排程調整
			//}
		}
		//建材代碼-檢查-----end
		
		//總樓層數-檢查-----start
		if(rawData[34] != null && rawData[34] .length() > 0) {
			if(!this.isNumeric(rawData[34])) {
				returnMsg[0] += "總樓層數異常;";
			}else {
				if(Integer.parseInt(rawData[34]) <= 0) {
					returnMsg[0] += "總樓層數異常;";
				}
			}
		}else {
			returnMsg[0] += "總樓層數異常;";
		}
		//總樓層數-檢查-----end
		
		//建築等級代碼-檢查-----start
		if(rawData[35] != null && rawData[35] .length() > 0) {
			if(!getRfrBuildingLevelCodeMap().containsKey(rawData[35])) {
				returnMsg[0] += "建築等級代碼異常或無法轉換成核心代碼;";
			}else {
				if(getRfrBuildingLevelCodeMap().get(rawData[35]).length() <= 0) {
					returnMsg[0] += "建築等級代碼異常或無法轉換成核心代碼;";
				}else {
					returnMsg[9] = getRfrBuildingLevelCodeMap().get(rawData[35]);
				}
			}
		}else {
			returnMsg[0] += "建築等級代碼異常或無法轉換成核心代碼;";
		}
		//建築等級代碼-檢查-----end
		
		//建築等級說明-檢查-----start
		if(rawData[36] == null || rawData[36] .length() <= 0) {
			returnMsg[0] += "建築等級說明不可空白;";
		}
		//建築等級說明-檢查-----end
		
		//簽署時間-檢查-----start
		if(rawData[42] != null && rawData[42] .length() > 0) {
			if(!this.checkDateFormat(rawData[42], "yyyy-MM-dd HH:mm:ss.SSS")){
				returnMsg[0] += "簽署時間異常;";
			}
		}
		//簽署時間-檢查-----end
		
		//業務員登錄證字號-檢查-----start
		if(rawData[44] == null || rawData[44] .length() <= 0) {
			returnMsg[0] += "業務員登錄證字號不可空白;";
		}
		//業務員登錄證字號-檢查-----end
		
		return returnMsg;
	}
	
	private String[] snnRawDataCheck(String[] rawData, String batchNo, String batchSeq) throws Exception{
		String[] returnMsg = new String[3];//0:TMP_STL_ERR; 1:TMP_STL_WARN; 2:coreIsHighrisk
		returnMsg[0] = "";
		returnMsg[1] = "";
		returnMsg[2] = "";
		
		//身分別-檢查-----start
		if("01,02".indexOf(rawData[1]) < 0) {
			returnMsg[0] += "關係人-身分別異常;";
		}
		//身分別-檢查-----end
		
		//身分別順序-檢查-----start
		if(rawData[2] != null && rawData[2] .length() > 0) {
			if(!this.isNumeric(rawData[2])) {
				returnMsg[0] += "關係人-身分別順序異常;";
			}else {
				if(Integer.parseInt(rawData[2]) <= 0) {
					returnMsg[0] += "關係人-身分別順序異常;";
				}
			}
		}else {
			returnMsg[0] += "關係人-身分別順序異常;";
		}
		//身分別順序-檢查-----end
		
		//姓名-檢查-----start
		if(rawData[5] == null || rawData[5] .length() <= 0) {
			returnMsg[0] += "關係人-姓名不可空白;";
		}
		//姓名-檢查-----end
		
		//是否為法人-檢查-----start
		if("01,02,03".indexOf(rawData[6]) < 0) {
			returnMsg[0] += "關係人-是否為法人異常;";
		}
		if("01".equals(rawData[6])) {
			//id-檢查-----start
			if(rawData[3] == null || rawData[3].trim().length() <= 0) {
				returnMsg[0] += "關係人-ID異常(法人);";
			}else {
				if(!this.verifyID(rawData[3], "60")) {//檢查統編格式
					returnMsg[0] += "關係人-ID異常(法人);";
				}
			}
			//id-檢查-----end
			
			//關係人-代表人姓名-檢查-----start
			if(rawData[7] == null || rawData[7] .length() <= 0) {
				returnMsg[0] += "關係人-代表人姓名異常(法人);";
			}
			//關係人-代表人姓名-檢查-----end
			
			//關係人-註冊地-檢查-----start
			if(rawData[15] == null || rawData[15] .length() <= 0) {
				returnMsg[0] += "關係人-註冊地異常(法人);";
			}else {
				if(!getRfrCountryCodeList().contains(rawData[15])) {
					returnMsg[0] += "關係人-註冊地異常(法人);";
				}
			}
			//關係人-註冊地-檢查-----end
			
			//上市/櫃公司-檢查-----start
			if(rawData[17] == null || rawData[17] .length() <= 0) {
				returnMsg[0] += "關係人-上市/櫃公司異常(法人);";
			}else {
				if("01,02,03".indexOf(rawData[17]) < 0) {
					returnMsg[0] += "關係人-上市/櫃公司異常(法人);";
				}
			}
			//上市/櫃公司-檢查-----end
			
			//已發行無記名股票-檢查-----start
			if(rawData[18] == null || rawData[18] .length() <= 0) {
				returnMsg[0] += "關係人-已發行無記名股票異常(法人)";
			}else {
				if("01,02".indexOf(rawData[18]) < 0) {
					returnMsg[0] += "關係人-已發行無記名股票異常(法人);";
				}
			}
			//已發行無記名股票-檢查-----end
			
		}else if("02".equals(rawData[6])) {
			//id-檢查-----start
			if(rawData[3] == null || rawData[3].trim().length() <= 0) {
				returnMsg[0] += "關係人-ID異常(自然人);";
			}else {
				if(!this.verifyID(rawData[3], "01")) {//檢查身份證格式
					returnMsg[0] += "關係人-ID異常(自然人);";
				}
			}
			//id-檢查-----end
		}else if("03".equals(rawData[6])) {
			//id-檢查-----start
			if(rawData[4] == null || rawData[4].trim().length() <= 0) {
				returnMsg[0] += "關係人-ID異常(外籍人士);";
			}else {
				String foreignerId = rawData[4];
				if(foreignerId.length() > 10) {
					foreignerId = foreignerId.substring(0, 10);
				}
				if(!this.verifyID(foreignerId, "05")) {//檢查身份證格式
					returnMsg[0] += "關係人-ID異常(外籍人士);";
				}
			}
			//id-檢查-----end
		}
		//是否為法人-檢查-----end
		
		//生日/設立日-檢查-----start
		if(rawData[8] == null || rawData[8] .length() <= 0) {
			returnMsg[0] += "關係人-生日/設立日異常;";
		}else if(!this.checkDateFormat(rawData[8], "yyyyMMdd")){
			returnMsg[0] += "關係人-生日/設立日異常;";
		//mantis：FIR0554，處理人員：BJ085，需求單編號：FIR0554 住火-APS中信新件資料轉入排程-增加要被保險人生日提醒 start 
		}else if("19120101".equals(rawData[8])) {
			returnMsg[1] += "關係人-生日為民國1年1月1日;";
		}
		//mantis：FIR0554，處理人員：BJ085，需求單編號：FIR0554 住火-APS中信新件資料轉入排程-增加要被保險人生日提醒 end 
		//生日/設立日-檢查-----end
		
		//郵遞區號-檢查-----start
		if(rawData[9] != null && rawData[9] .length() > 0) {
			if(!getRfrPostCodeMap().containsKey(rawData[9])) {
				returnMsg[0] += "郵遞區號異常;";
			}
		}else {
			returnMsg[0] += "郵遞區號異常;";
		}
		//郵遞區號-檢查-----end
		
		//縣/市-檢查-----start
		if(rawData[10] == null || rawData[10] .length() <= 0) {
			returnMsg[0] += "關係人-縣/市不可空白;";
		}
		//縣/市-檢查-----end
		
		//鄉鎮市區-檢查-----start
		if(rawData[11] == null || rawData[11] .length() <= 0) {
			returnMsg[0] += "關係人-鄉鎮市區不可空白;";
		}
		//鄉鎮市區-檢查-----end
		
		//地址-檢查-----start
		if(rawData[12] == null || rawData[12] .length() <= 0) {
			returnMsg[0] += "關係人-地址不可空白;";
		}
		//地址-檢查-----end
		
		//聯絡電話-檢查-----start
		if(rawData[13] == null || rawData[13] .length() <= 0) {
			returnMsg[0] += "關係人-聯絡電話不可空白;";
		}
		//聯絡電話-檢查-----end
		
		//國籍-檢查-----start
		if(rawData[14] == null || rawData[14] .length() <= 0) {
			returnMsg[0] += "關係人-國籍異常;";
		}else {
			if(!getRfrCountryCodeList().contains(rawData[14])) {
				returnMsg[0] += "關係人-國籍異常;";
			}
		}
		//國籍-檢查-----end
		
		//行/職業別-檢查-----start
		if(rawData[16] != null && rawData[16] .length() > 0) {
			if(!(getRfrOccupationCodeMap().containsKey(rawData[16]))) {
				//mantis：FIR0438，處理人員：BJ085，需求單編號：FIR0438 中信新件_資料接收排程調整行職業別檢核
				returnMsg[1] += "關係人-行/職業別異常;";
			}else {
				returnMsg[2] = getRfrOccupationCodeMap().get(rawData[16]);
			}
		}
		//行/職業別-檢查-----end
		
		//AML審核-檢查-----start
		/**
		 * 20201214:BJ016:FIR0251:APS-中信新件轉入排程調整檢核(AML審核)
		 * 中信修改了他們的系統，他們的系統會傳空白資料
		 * */
//		if(rawData[19] != null && rawData[19] .length() > 0) {
//			if(!"Y".equalsIgnoreCase(rawData[19]) && !"N".equalsIgnoreCase(rawData[19])) {
//				returnMsg[0] += "關係人-AML審核異常;";
//			}
//		}
		//AML審核-檢查-----end
		
		//久未往來註記-檢查-----start
		if(rawData[20] != null && rawData[20] .length() > 0) {
			if(!"Y".equalsIgnoreCase(rawData[20]) && !"N".equalsIgnoreCase(rawData[20])) {
				returnMsg[0] += "關係人-久未往來註記異常;";
			}
		}
		//久未往來註記-檢查-----end
		
		//長戶名註記-檢查-----start
		if(rawData[21] != null && rawData[21].trim() .length() > 0) {
			if(!"Y".equalsIgnoreCase(rawData[21]) && !"N".equalsIgnoreCase(rawData[21])) {
				returnMsg[0] += "關係人-長戶名註記異常;";
			}
		}
		//長戶名註記-檢查-----end
		
		//長戶名中文-檢查-----start
		if("Y".equalsIgnoreCase(rawData[21])) {
			if((rawData[22] == null || rawData[22].trim() .length() <= 0) &&
					(rawData[23] == null || rawData[23].trim() .length() <= 0)) {
				returnMsg[0] += "關係人－長戶名註記為Y，長戶名中文或長戶名羅馬拼音不可同時空白;";
			}
		}
		//長戶名中文-檢查-----end

		return returnMsg;
	}
	
	private boolean checkDateFormat(String date, String pattern) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			 
			//如果成功就是正確的日期，失敗就是有錯誤的日期。  
			sdf.parse(date);  

			sdf = null;
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }  
        return true;
	}
	
	private boolean isChineseNumeric(String str){
		String [] chineseNumber = {"零","壹","貳","參","肆","伍","陸","柒","捌","玖"
				,"一","二","三","四","五","六","七","八","九","十"
				,"拾","佰","仟","百","千"};
		boolean returnValue = false;
        for(int i = 0; i < chineseNumber.length; i++) {
        	if(chineseNumber[i].equals(str)) {
        		returnValue = true;
        		break;
        	}
        }
        chineseNumber = null;
        return returnValue;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getCodeMap(String codetype){
		List<String> resultList = new ArrayList<String>();
		try {
			Map params = new HashMap();
			params.put("codetype", codetype);
	        Result result = this.rfrcodeService.findRfrcodeByParams(params);
	        if(result != null && result.getResObject() != null) {
	        	List<Rfrcode> list = (List<Rfrcode>) result.getResObject();
	        	for(Rfrcode rfrcode : list) {
	        		resultList.add(rfrcode.getCodecode());
	        	}
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
        return resultList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String,String> getCodeHashMap(String codetype, String source, String keyColumn, String valueColumn){
		Map<String,String> resultMap = new HashMap<String,String>(); 
		try {
			Map params = new HashMap();
			params.put("codetype", codetype);
			if(source != null && source.length() > 0) {
				params.put("source", source);
			}
	        Result result = this.rfrcodeService.findRfrcodeByParams(params);
	        if(result != null && result.getResObject() != null) {
	        	List<Rfrcode> list = (List<Rfrcode>) result.getResObject();
	        	Field keyField;
	        	Field valueField;
	        	for(Rfrcode rfrcode : list) {
	        		keyField = rfrcode.getClass().getDeclaredField(keyColumn);
	        		keyField.setAccessible(true);
	        		valueField = rfrcode.getClass().getDeclaredField(valueColumn);
	        		valueField.setAccessible(true);
//	        		if(!resultMap.containsKey(rfrcode.getCodecode()))
//	        			resultMap.put(rfrcode.getCodecode(),rfrcode.getMappedcode());
	        		if(!resultMap.containsKey(keyField.get(rfrcode)))
	        			resultMap.put(keyField.get(rfrcode).toString(),valueField.get(rfrcode).toString());
	        	}
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
        return resultMap;
	}
	
	private boolean checkCompanyNumber(String id){  
		try {
			int v[] = {1, 2, 1, 2, 1, 2, 4, 1};
		    int A1[] = new int[8];
		    int A2[] = new int[8];
		    int B = 0;
		    int B1 = 0;
		    for (int i = 0; i < v.length; i++) {  
		      A1[i] = Integer.parseInt(String.valueOf(id.charAt(i))) * v[i];
//		      System.out.println("A1[" + i +"]: " + A1[i]);
		    }
		      
		    for (int i = 0; i < A1.length; i++) {            
		      if (A1[i] < 10) {
		        A2[i] = A1[i];
		      } else {
		        A2[i] = Integer.parseInt(String.valueOf(A1[i]).substring(0, 1)) + Integer.parseInt(String.valueOf(A1[i]).substring(1, 2));
		      }  
//		      System.out.println("A2[" + i +"]: " + A2[i]);    
		    }
		    
		    for (int i = 0; i < A2.length; i++) {
		      B = B + A2[i];
		    }
		    
		    if ( B % 10 == 0) {
		      return true;  
		      
		    } else {
		      if (id.charAt(6) == 7) {
		        B  = A2[0] + A2[1] + A2[2] + A2[3] + A2[4] + A2[5] + 0 + A2[7];
		        B1 = A2[0] + A2[1] + A2[2] + A2[3] + A2[4] + A2[5] + 1 + A2[7];
		        if ((B % 10 == 0)||(B1 % 10 == 0)) {
		          return true;            
		        }            
		      }      
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
	    
	    return false;
	  }
	
	private boolean isValidIDorRCNumber(String str) {

	    if (str == null || "".equals(str)) {
	        return false;
	    }

	    final char[] pidCharArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	    // 原身分證英文字應轉換為10~33，這裡直接作個位數*9+10
	    final int[] pidIDInt = { 1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11, 20, 48, 29, 38, 47, 56, 65, 74, 83, 21, 3, 12, 30 };

	    // 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod 10]
	    final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3, 12, 10 };

	    // 原居留證第二碼英文字應轉換為10~33，並僅取個位數*8，這裡直接取[(個位數*8) mod 10]
	    final int[] pidResidentSecondInt = {0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8, 4};

	    str = str.toUpperCase();// 轉換大寫
	    final char[] strArr = str.toCharArray();// 字串轉成char陣列
	    int verifyNum = 0;

	    /* 檢查身分證字號 */
	    if (str.matches("[A-Z]{1}[1-2]{1}[0-9]{8}")) {
	        // 第一碼
	        verifyNum = verifyNum + pidIDInt[Arrays.binarySearch(pidCharArray, strArr[0])];
	        // 第二~九碼
	        for (int i = 1, j = 8; i < 9; i++, j--) {
	            verifyNum += Character.digit(strArr[i], 10) * j;
	        }
	        // 檢查碼
	        verifyNum = (10 - (verifyNum % 10)) % 10;

	        return verifyNum == Character.digit(strArr[9], 10);
	    }

	    /* 檢查統一證(居留證)編號 */
	    verifyNum = 0;
	    if (str.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
	        // 第一碼
	        verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
	        // 第二碼
	        verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, strArr[1])];
	        // 第三~八碼
	        for (int i = 2, j = 7; i < 9; i++, j--) {
	            verifyNum += Character.digit(strArr[i], 10) * j;
	        }
	        // 檢查碼
	        verifyNum = (10 - (verifyNum % 10)) % 10;

	        return verifyNum == Character.digit(strArr[9], 10);
	    }

	    return false;
	}
	
	private String transferDateFormat(String sourceDate, String sourceDateFormat, String transferFormat) {
		String returnValue = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(sourceDateFormat);
			formatter.setLenient(false);
			Date newDate= formatter.parse(sourceDate);
			formatter = new SimpleDateFormat(transferFormat);
			returnValue = formatter.format(newDate);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return returnValue;
	}
	
	private  boolean fileWrite(String fileName, String datas) {
		System.out.println("fileName :" + fileName);
		System.out.println("datas :" + datas);
		BufferedWriter bufWriter = null;
		File file = new File(fileName);
		try {
			bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
			bufWriter.write(datas);
			bufWriter.close();
//			ZipUtil zipUtil = new ZipUtil();
//			zipUtil.writeZip(fileName);
//			Files.deleteIfExists(file.toPath());// 只留zip檔即可 add by DP0705 20181228
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private  boolean sendMail01(String status, String batchNo, Date executeTime, String errorMessage) {
		Mailer mailer = new Mailer();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("prgId", "FIR_CTBC_01");
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result != null && result.getResObject() != null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱
				String smtpServer = "mail.ctbcins.com";
				String userName = "newims";
				String password = "2012newims";
				String contentType = "text/html; charset=utf-8";
				String auth = "smtp";
				String subject = firBatchInfo.getMailSubject() + " - " + sdf.format(new Date());
				//mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱
				String from = "newims@ctbcins.com";
				String to = firBatchInfo.getMailTo();
				String CC = firBatchInfo.getMailCc();
				String mailBody = "";
				StringBuffer sb = new StringBuffer();
				String fileStatus;
				String transStatus;
				String fkStatus;
				String orderSeqStatus;
				
				if("S".equals(status)) {
					sb.append("<P>批次號碼：" + batchNo + "</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：完成</P>");
					sb.append("<P>本次處理檔案清單如下：</P>");
					params = new HashMap<String,Object>();
					params.put("batchNo", batchNo);
					params.put("sortBy", "BATCH_SEQ,FK_ORDER_SEQ,FK_STATUS");
					result = this.firCtbcBatchMainService.findFirCtbcBatchMainByParams(params);
					if(result != null && result.getResObject() != null) {
						List<FirCtbcBatchMain> mainList = (List<FirCtbcBatchMain>)result.getResObject();
						if(mainList != null && mainList.size() > 0) {
							sb.append("<table border=1 style='border-collapse: collapse;'>");
							sb.append("<tr bgcolor='#70bbd9'>");
							sb.append("<td>批次序號</td>");
							sb.append("<td>ZIP檔案名稱</td>");
							sb.append("<td>檔案狀態</td>");
							sb.append("<td>STL資料筆數</td>");
							sb.append("<td>TIFF檔數量</td>");
							sb.append("<td>轉暫存檔狀態</td>");
							sb.append("</tr>");
							
							for(FirCtbcBatchMain firCtbcBatchMain : mainList) {
								sb.append("<tr>");
								sb.append("<td>" + firCtbcBatchMain.getBatchSeq() + "</td>");
								sb.append("<td>" + firCtbcBatchMain.getFilenameZip() + "</td>");
								
								if("S".equals(firCtbcBatchMain.getFileStatus())) {
									fileStatus = "正常";
								}else if("L".equals(firCtbcBatchMain.getFileStatus())) {
									fileStatus = "缺檔";
								}else if("E".equals(firCtbcBatchMain.getFileStatus())) {
									fileStatus = "檔案異常";
								}else if("Z".equals(firCtbcBatchMain.getFileStatus())) {
										fileStatus = "檔案無資料";
								}else {
									fileStatus = "狀態未定義";
								}
								sb.append("<td>" + fileStatus + "</td>");
								sb.append("<td>" + firCtbcBatchMain.getDataqtyStl() + "</td>");
								sb.append("<td>" + + firCtbcBatchMain.getFileqtySig() + "</td>");
								
								if("N".equals(firCtbcBatchMain.getTransStatus())) {
									transStatus = "未處理";
								}else if("Y".equals(firCtbcBatchMain.getTransStatus())) {
									transStatus = "已處理";
								}else if("E".equals(firCtbcBatchMain.getTransStatus())) {
									transStatus = "異常";
								}else {
									transStatus = "狀態未定義";
								}
								sb.append("<td>" + transStatus + "</td>");
								sb.append("</tr>");
							}
							sb.append("</table>");
						}
					}else {
						sb.append("<P>FIR_CTBC_BATCH_MAIN批次主檔查無資料，請洽系統人員。</P>");
					}
					
					sb.append("<P>本次處理受理編號如下：</P>");
//					params = new HashMap<String,Object>();
//					params.put("batchNo", batchNo);
//					params.put("sortBy", "BATCH_SEQ,FK_ORDER_SEQ,FK_STATUS");
					result = this.firCtbcBatchDtlService.findFirCtbcBatchDtlByParams(params);
					if(result != null && result.getResObject() != null) {
						List<FirCtbcBatchDtl> dtlList = (List<FirCtbcBatchDtl>)result.getResObject();
						if(dtlList != null && dtlList.size() > 0) {
							sb.append("<table border=1 style='border-collapse: collapse;'>");
							sb.append("<tr bgcolor='#70bbd9'>");
							sb.append("<td>批次序號</td>");
							sb.append("<td>受理編號</td>");
							sb.append("<td>下單狀態</td>");
							sb.append("<td>受理編號狀態</td>");
							sb.append("<td>資料檢核異常訊息</td>");
							sb.append("<td>資料檢核提示訊息</td>");
							sb.append("</tr>");
							for(FirCtbcBatchDtl firCtbcBatchDtl : dtlList) {
								sb.append("<tr>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcBatchDtl.getBatchSeq()) + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcBatchDtl.getFkOrderSeq()) + "</td>");
								
								if("01".equals(firCtbcBatchDtl.getFkStatus())) {
									fkStatus = "下單完成";
								}else if("02".equals(firCtbcBatchDtl.getFkStatus())) {
									fkStatus = "暫存";
								}else if("09".equals(firCtbcBatchDtl.getFkStatus())) {
									fkStatus = "取消";
								}else {
									fkStatus = "狀態未定義";
								}
								sb.append("<td>" + fkStatus + "</td>");
								
								if("0".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "未處理";
								}else if("1".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "資料驗證失敗";
								}else if("2".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "寫入中信下單檔成功";
								}else if("3".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "轉核心暫存檔成功";
								}else if("4".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "轉核心暫存檔失敗";
								}else if("D".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "中信暫存件不處理";
								}else if("E".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "中信取消件不處理";
								}else if("F".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "未簽署件不處理";
								}else if("G".equals(firCtbcBatchDtl.getOrderSeqStatus())) {
									orderSeqStatus = "內部程式異常";
								}else {
									orderSeqStatus = "狀態未定義";
								}
								sb.append("<td>" + orderSeqStatus + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcBatchDtl.getCheckErrMsg()) + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcBatchDtl.getCheckWarnMsg()) + "</td>");
								sb.append("</tr>");
							}
							sb.append("</table>");
						}
					}else {
						sb.append("<P>FIR_CTBC_BATCH_DTL批次明細檔查無資料，請洽系統人員。</P>");
					}
				}else if("N".equals(status)) {
					sb.append("<P>批次號碼：" + batchNo + "</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：無資料</P>");
				}else {
					sb.append("<P>批次號碼：</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：F</P>");
					sb.append("<P>異常訊息：" + errorMessage + "</P>");
				}
				
				mailBody = sb.toString();
				
				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", CC, "", "", "", mailBody, auth, userName, password);
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private  boolean sendMail02(String status, String batchNo, Date executeTime, String errorMessage) {
		Mailer mailer = new Mailer();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("prgId", "FIR_CTBC_02");
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result != null && result.getResObject() != null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱
				String smtpServer = "mail.ctbcins.com";
				String userName = "newims";
				String password = "2012newims";
				String contentType = "text/html; charset=utf-8";
				String auth = "smtp";
				String subject = firBatchInfo.getMailSubject() + " - " + sdf.format(new Date());
				//mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱
				String from = "newims@ctbcins.com";
				String to = firBatchInfo.getMailTo();
				String CC = firBatchInfo.getMailCc();
				String mailBody = "";
				StringBuffer sb = new StringBuffer();
				String rstType;
				String inscoStatus;
				
				if("S".equals(status)) {
					sb.append("<P>批次號碼：" + batchNo + "</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：完成</P>");

					params = new HashMap<String,Object>();
					params.put("batchNo", batchNo);
					params.put("sortBy", "RST_TYPE,FK_ORDER_SEQ,");
					result = this.firCtbcRstService.findFirCtbcRstByParams(params);
					if(result != null && result.getResObject() != null) {
						List<FirCtbcRst> returnList = (List<FirCtbcRst>)result.getResObject();
						if(returnList != null && returnList.size() > 0) {
							sb.append("<table border=1 style='border-collapse: collapse;'>");
							sb.append("<tr bgcolor='#70bbd9'>");
							sb.append("<td>回饋類型</td>");
							sb.append("<td>受理編號</td>");
							sb.append("<td>核保狀態</td>");
							sb.append("<td>失敗原因</td>");
							sb.append("<td>保單號碼</td>");
							sb.append("<td>投保證明檔名</td>");
							sb.append("</tr>");
							
							for(FirCtbcRst firCtbcRst : returnList) {
								sb.append("<tr>");
								
								if("1".equals(firCtbcRst.getRstType())) {
									rstType = "資料異常";
								}else if("2".equals(firCtbcRst.getRstType())) {
									rstType = "核心撤單";
								}else if("3".equals(firCtbcRst.getRstType())) {
									rstType = "核心出單";
								}else if("4".equals(firCtbcRst.getRstType())) {
									rstType = "續保叫單";
								//mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投
								}else if("5".equals(firCtbcRst.getRstType())) {
									rstType = "保代網投出單";
								}else {
									rstType = "回饋類型未定義";
								}
								sb.append("<td>" + rstType + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcRst.getFkOrderSeq()) + "</td>");
								
								if("01".equals(firCtbcRst.getInscoStatus())) {
									inscoStatus = "已核保";
								}else if("02".equals(firCtbcRst.getInscoStatus())) {
									inscoStatus = "不核保";
								}else {
									inscoStatus = "核保狀態未定義";
								}
								sb.append("<td>" + inscoStatus + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcRst.getInscoFeedback()) + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcRst.getPolicyno()) + "</td>");
								sb.append("<td>" + StringUtil.nullToSpace(firCtbcRst.getFilenameCtf()) + "</td>");
								sb.append("</tr>");
							}
							sb.append("</table>");
						}
					}
					
				}else if("N".equals(status)) {
					sb.append("<P>批次號碼：" + batchNo + "</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：無資料</P>");
				}else {
					sb.append("<P>批次號碼：</P>");
					sb.append("<P>轉檔時間：" + sdf.format(executeTime) + "</P>");
					sb.append("<P>執行狀態：F</P>");
					sb.append("<P>異常訊息：批次號碼取號失敗。</P>");
				}
				
				mailBody = sb.toString();
				
				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", CC, "", "", "", mailBody, auth, userName, password);
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private FirCtbcStl mappingFirCtbcStl(String batchNo, String batchSeq, String stlFileName, String[] arrData,
			String extraComname, String handler1code, String comcode, String roofNo, String wallNo, String structure) throws Exception{
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
		if(arrData[42] != null && arrData[42].trim().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			firCtbcStl.setSignDatetime(sdf.parse(arrData[42]));
		}
		firCtbcStl.setSignOtherProb(arrData[43]);
		firCtbcStl.setAgentRegisterNum(arrData[44]);
		firCtbcStl.setCoreExtraComname(extraComname);
		firCtbcStl.setCoreHandler1code(handler1code);
		firCtbcStl.setCoreComcode(comcode);
		firCtbcStl.setCoreRoofno(roofNo);
		firCtbcStl.setCoreWallno(wallNo);
		firCtbcStl.setCoreStructure(structure);
		
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
		if("03".equals(arrData[6])) {
			if(arrData[4] != null && arrData[4].length() > 10) {
				firCtbcSnn.setResidenceNo(arrData[4].substring(0, 10));
			}else {
				firCtbcSnn.setResidenceNo(arrData[4]);
			}
		}
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
		/* mantis：FIR0377，處理人員：BJ085，需求單編號：FIR0377 中信新件資料接收排程_調整長戶名檢核處理 start
		長戶名(中文、羅馬拼音)調整-去前後空白(全形、半形)*/
		firCtbcSnn.setLongName(trimBlank(arrData[22]));
		firCtbcSnn.setLongRomname(trimBlank(arrData[23]));
		/* mantis：FIR0377，處理人員：BJ085，需求單編號：FIR0377 中信新件資料接收排程_調整長戶名檢核處理 end*/

		return firCtbcSnn;
	}
	
	/* mantis：FIR0377，處理人員：BJ085，需求單編號：FIR0377 中信新件資料接收排程_調整長戶名檢核處理 start*/
	public String trimBlank(String str){
		if(null != str && str.trim().length()>0) {
			return str.replaceAll("^[\\u3000|\\s]*", "").replaceAll("[\\u3000|\\s]*$", "");
		}
		return "";
	}
	/* mantis：FIR0377，處理人員：BJ085，需求單編號：FIR0377 中信新件資料接收排程_調整長戶名檢核處理 end*/
	
	private boolean genPdf(String filePath, String oid, String fileName){
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			String httpURL = configUtil.getString("genPdfUrl") + oid;
	        HttpGet getRequest = new HttpGet(httpURL);
	        getRequest.addHeader("accept", "application/json");
	        HttpResponse response = httpClient.execute(getRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("statusCode = " + statusCode);
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        HttpEntity httpEntity = response.getEntity();
	        String jsonString = EntityUtils.toString(httpEntity);
	        RptFir00103ResultVo vo = (RptFir00103ResultVo)JsonUtil.getDTO(jsonString, RptFir00103ResultVo.class);
	        
	        System.out.println("vo.getMsg() = " + vo.getMsg());
	        byte[] byteArray = Base64.decodeBase64(vo.getRptStr());
			FileUtils.writeByteArrayToFile(new File(filePath + fileName + ".pdf"), byteArray);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean verifyID(String id, String identifyType){
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			/**
			 * 20200620
			 * BJ016
			 * 中信傳過來的資料會有中間空白的問題，所以這邊要對url作url encode，將特殊字作轉換，免得程式出exception
			 * */
			// 進行 URL 百分比編碼
			String encodedURLID = URLEncoder.encode(id, "UTF-8");
			String httpURL = configUtil.getString("verifyIDUrl") + encodedURLID;
	        HttpGet getRequest = new HttpGet(httpURL);
	        getRequest.addHeader("accept", "application/json");
	        HttpResponse response = httpClient.execute(getRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("statusCode = " + statusCode);
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        HttpEntity httpEntity = response.getEntity();
	        String jsonString = EntityUtils.toString(httpEntity);
	        VerifyIdVo vo = (VerifyIdVo)JsonUtil.getDTO(jsonString, VerifyIdVo.class);
	        if("S0000".equals(vo.getCode())) {
	        	if("05".equals(identifyType)) {
	        		/**
	        		 * bj016
	        		 * 外國人id這個欄位可能放得是居留證號或稅籍編號
	        		 * 所以當IdentifyType為05時表示為居留證號
	        		 *             IdentifyType為T1時表示為外國人稅籍編號
	        		 *             IdentifyType為T2時表示為中國人稅籍編號
	        		 * */
	        		if("05".equals(vo.getIdentifyType()) || "T1".equals(vo.getIdentifyType()) || "T2".equals(vo.getIdentifyType())) {
	        			return true;
	        		}
	        	}else if(identifyType != null && identifyType.equals(vo.getIdentifyType())) {
	        		return true;
	        	}
	        	return false;
	        }

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public String uploadFile(String filePath, String orderSeq) {
	    String httpURL = configUtil.getString("uploadFileUrl");
	    File file = new File(filePath);
	    StringBuilder stringBuilder = null;
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
	            HttpPost httpPost = new HttpPost(httpURL);
	            FileBody fileBody = new FileBody(file);
	            MultipartEntityBuilder  multipartEntityBuilder = MultipartEntityBuilder.create();
	            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	            multipartEntityBuilder.addTextBody("source", "中信新件流程", ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addTextBody("riskCode", "F", ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addTextBody("businessNo", orderSeq, ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addPart("file", fileBody);
	            HttpEntity httpEntity = multipartEntityBuilder.build();
	            httpPost.setEntity(httpEntity);
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            System.out.println("Response code/message: " + httpResponse.getStatusLine());
	            httpEntity = httpResponse.getEntity();

	            InputStream inputStream = httpEntity.getContent();
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	            stringBuilder = new StringBuilder();
	            String strReadLine = bufferedReader.readLine();
	            // iterate to get the data and append in StringBuilder
	            while (strReadLine != null) {
	                stringBuilder.append(strReadLine);
	                strReadLine = bufferedReader.readLine();
	                if (strReadLine != null) {
	                    stringBuilder.append("\n");
	                }
	            }
	        }catch (UnsupportedEncodingException usee) {
	            usee.printStackTrace();
	        }catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    return stringBuilder.toString();
	    }
	
	public String downloadListService(String riskCode, String businessNo) {
		String httpURL = configUtil.getString("downloadListServiceUrl");
		StringBuilder  stringBuilder = new StringBuilder();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        	FileUploadRequestVo vo = new FileUploadRequestVo();
        	vo.setRiskCode(riskCode);
        	vo.setBusinessNo(businessNo);
              
        	HttpPost httpPost = new HttpPost(httpURL);  
        	StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(vo), "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", "application/json");
        	httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();
            // get the response content
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            
            String strReadLine = bufferedReader.readLine();
 
            // iterate to get the data and append in StringBuilder
            while (strReadLine != null) {
                stringBuilder.append(strReadLine);
                strReadLine = bufferedReader.readLine();
                if (strReadLine != null) {
                    stringBuilder.append("\n");
                }
            }
        }
        catch (UnsupportedEncodingException usee) {
            usee.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
        return stringBuilder.toString();
    }


	/**
	 * 20200518：BJ016：全形數字轉半形數字
	 * */
	public String transferFullwidthToHalfwidth(String str){
		for(char c:str.toCharArray()){
			str = str.replaceAll("　", " ");
			if((int)c >= 65281 && (int)c <= 65374){
				str = str.replace(c, (char)(((int)c)-65248));
			}
		}
		return str;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public CibPolicyDataImportService getCibPolicyDataImportService() {
		return cibPolicyDataImportService;
	}

	public void setCibPolicyDataImportService(CibPolicyDataImportService cibPolicyDataImportService) {
		this.cibPolicyDataImportService = cibPolicyDataImportService;
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

	public PrptmainService getPrptmainService() {
		return prptmainService;
	}

	public void setPrptmainService(PrptmainService prptmainService) {
		this.prptmainService = prptmainService;
	}

	public FirCtbcSigService getFirCtbcSigService() {
		return firCtbcSigService;
	}

	public void setFirCtbcSigService(FirCtbcSigService firCtbcSigService) {
		this.firCtbcSigService = firCtbcSigService;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public FirCtbcTmpSnnService getFirCtbcTmpSnnService() {
		return firCtbcTmpSnnService;
	}

	public void setFirCtbcTmpSnnService(FirCtbcTmpSnnService firCtbcTmpSnnService) {
		this.firCtbcTmpSnnService = firCtbcTmpSnnService;
	}

	public FirDoubleInsService getClientFirDoubleInsService() {
		return clientFirDoubleInsService;
	}

	public void setClientFirDoubleInsService(FirDoubleInsService clientFirDoubleInsService) {
		this.clientFirDoubleInsService = clientFirDoubleInsService;
	}

	public FirAmountService getClientFirAmountService() {
		return clientFirAmountService;
	}

	public void setClientFirAmountService(FirAmountService clientFirAmountService) {
		this.clientFirAmountService = clientFirAmountService;
	}

	public FirCtbcBatchDtlService getFirCtbcBatchDtlService() {
		return firCtbcBatchDtlService;
	}

	public void setFirCtbcBatchDtlService(FirCtbcBatchDtlService firCtbcBatchDtlService) {
		this.firCtbcBatchDtlService = firCtbcBatchDtlService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public FirCtbcRstService getFirCtbcRstService() {
		return firCtbcRstService;
	}

	public void setFirCtbcRstService(FirCtbcRstService firCtbcRstService) {
		this.firCtbcRstService = firCtbcRstService;
	}

	public FirRptCtbcCtfService getFirRptCtbcCtfService() {
		return firRptCtbcCtfService;
	}

	public void setFirRptCtbcCtfService(FirRptCtbcCtfService firRptCtbcCtfService) {
		this.firRptCtbcCtfService = firRptCtbcCtfService;
	}

	public FirCtbcDeptinfoService getFirCtbcDeptinfoService() {
		return firCtbcDeptinfoService;
	}

	public void setFirCtbcDeptinfoService(FirCtbcDeptinfoService firCtbcDeptinfoService) {
		this.firCtbcDeptinfoService = firCtbcDeptinfoService;
	}

	public AddressCheckService getClientAddressCheckService() {
		return clientAddressCheckService;
	}

	public void setClientAddressCheckService(AddressCheckService clientAddressCheckService) {
		this.clientAddressCheckService = clientAddressCheckService;
	}

	public List<String> getRfrAreaCodeList() {
		if(rfrAreaCodeList == null || rfrAreaCodeList.size() <= 0) {
			rfrAreaCodeList = this.getCodeMap("AreaCode");
		}
		return rfrAreaCodeList;
	}

	public void setRfrAreaCodeList(List<String> rfrAreaCodeList) {
		this.rfrAreaCodeList = rfrAreaCodeList;
	}

	public Map<String, String> getRfrBuildingLevelCodeMap() {
		if(rfrBuildingLevelCodeMap == null || rfrBuildingLevelCodeMap.size() <= 0) {
			rfrBuildingLevelCodeMap = this.getCodeHashMap("Structure","", "mappedcode", "codecode");
		}
		return rfrBuildingLevelCodeMap;
	}

	public void setRfrBuildingLevelCodeMap(Map<String, String> rfrBuildingLevelCodeMap) {
		this.rfrBuildingLevelCodeMap = rfrBuildingLevelCodeMap;
	}

	public Map<String, String> getRfrRoofNoMap() {
		if(rfrRoofNoMap == null || rfrRoofNoMap.size() <= 0) {
			rfrRoofNoMap = this.getCodeHashMap("RoofNo","", "codecode", "mappedcode");
		}
		return rfrRoofNoMap;
	}

	public void setRfrRoofNoMap(Map<String, String> rfrRoofNoMap) {
		this.rfrRoofNoMap = rfrRoofNoMap;
	}

	public Map<String, String> getRfrWallNoMap() {
		if(this.rfrWallNoMap == null || rfrWallNoMap.size() <= 0) {
			this.rfrWallNoMap = this.getCodeHashMap("WallNo","", "codecode", "mappedcode");
		}
		return rfrWallNoMap;
	}

	public void setRfrWallNoMap(Map<String, String> rfrWallNoMap) {
		this.rfrWallNoMap = rfrWallNoMap;
	}

	public Map<String, String> getRfrOccupationCodeMap() {
		if(rfrOccupationCodeMap == null) {
			rfrOccupationCodeMap = this.getCodeHashMap("OccupationCode", "CTBC",  "codecode", "mappedcode");
		}
		return rfrOccupationCodeMap;
	}

	public void setRfrOccupationCodeMap(Map<String, String> rfrOccupationCodeMap) {
		this.rfrOccupationCodeMap = rfrOccupationCodeMap;
	}

	public Map<String, String> getRfrPostCodeMap() {
		if(rfrPostCodeMap == null || rfrPostCodeMap.size() <= 0) {
			rfrPostCodeMap = this.getCodeHashMap("PostCode", "", "codecode", "mappedcode");
		}
		return rfrPostCodeMap;
	}

	public void setRfrPostCodeMap(Map<String, String> rfrPostCodeMap) {
		this.rfrPostCodeMap = rfrPostCodeMap;
	}

	public List<String> getRfrCountryCodeList() {
		if(rfrCountryCodeList == null || rfrCountryCodeList.size() <= 0) {
			rfrCountryCodeList = this.getCodeMap("CountryCode");
		}
		return rfrCountryCodeList;
	}

	public void setRfrCountryCodeList(List<String> rfrCountryCodeList) {
		this.rfrCountryCodeList = rfrCountryCodeList;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public Map<String, String> getRfrStructureMap() {
		if(rfrStructureMap == null || rfrStructureMap.size() <= 0) {
			rfrStructureMap = this.getCodeHashMap("Structure", "", "codecode", "codename");
		}
		return rfrStructureMap;
	}

	public void setRfrStructureMap(Map<String, String> rfrStructureMap) {
		this.rfrStructureMap = rfrStructureMap;
	}

	/* mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS start*/
	public RuleCheckService getClientRuleCheckService() {
		return clientRuleCheckService;
	}

	public void setClientRuleCheckService(RuleCheckService clientRuleCheckService) {
		this.clientRuleCheckService = clientRuleCheckService;
	}
	/* mantis：FIR0212，中信新件資料接收排程-增加稽核議題WS end*/

}
