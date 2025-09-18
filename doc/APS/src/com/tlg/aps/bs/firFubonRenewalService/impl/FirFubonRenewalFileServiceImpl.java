package com.tlg.aps.bs.firFubonRenewalService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firFubonRenewalService.FirFubonRenewalFileService;
import com.tlg.aps.bs.firFubonRenewalService.FubonRenewalFileService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirHandler1codeVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirAgtrnTmpFb;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirAgtrnTmpFbService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.sales.entity.PrpdAgreement;
import com.tlg.sales.service.PrpdAgreementService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirFubonRenewalFileServiceImpl implements FirFubonRenewalFileService {

	private static final Logger logger = Logger.getLogger(FirFubonRenewalFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private PrpdAgreementService prpdAgreementService;
	private RfrcodeService rfrcodeService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;

	private static final String ROOTDIRECTORY = "D:" + File.separator + "FB_RNDATA" + File.separator;
	private static final String OUTMSG = "outMsg";
	private static final String OUTSTATUS = "outStatus";
	private static final String FILESTATUS = "fileStatus";

	private static FirAgtrnAs400DataService firAgtrnAs400DataService;
	private FubonRenewalFileService fubonRenewalFileService;
	private FirAgtrnTmpFbService firAgtrnTmpFbService;
	private FirVerifyDatasService firVerifyDatasService;
	
	@Override
	public Result runToReceiveData(String userId, Date excuteTime, String programId) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		String batchNo = programId.substring(8, 12) + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = fubonRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					fubonRenewalFileService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult("接收參數無值，結束排程");
		}
		String status = "1";
		String msg = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if (firBatchInfo.getMailTo().equals("N")) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}

		// 先檢查當月的AS400日盛資料是否已轉入APS
		params.clear();
		// 取系統日期+1個月(yyyyMM)
		String nextMonth = getNextMonth();
		params.put("rnYyyymm", nextMonth);
		params.put("businessnature", "I99060");

		if (firAgtrnAs400DataService.countFirAgtrnAs400Data(params) == 0) {
			msg = "日盛保單資料未匯入(" + nextMonth + ")，請先執行「AS400外銀續保匯入查詢作業」再通知資訊人員重新接收應續件資料。";
			status = "F";
		}

		result = fubonRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		if (status.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if (status.equals("F")) {
			return this.getReturnResult(msg);
		}

		if (result.getResObject() != null) {
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();
			Map<String, String> returnData = null;
			// 取得檔案、原始資料暫存
			returnData = temporaryDataStorage(batchNo, userId);

			if ("N".equals(returnData.get(OUTSTATUS))) {
				fubonRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, returnData.get(OUTSTATUS), returnData.get(OUTMSG), programId);
				return this.getReturnResult("資料暫存執行失敗");
			} else if ("0".equals(returnData.get(OUTSTATUS)) && "".equals(returnData.get(OUTMSG))) {
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				return this.getReturnResult(returnData.get(OUTMSG));
			} else if("0".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("N", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "N", returnData.get(OUTMSG), programId);
				return this.getReturnResult("本次沒有檔案要處理");
			}
//			 資料暫存執行成功
			fubonRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
			updateFirBatchLog("S", returnData.get(OUTMSG), userId, batchLogOid);
			returnData.clear();

			
			//資料檢核處理 
			try {
				returnData = temporaryArchiveDataReview(batchNo, userId);
			} catch (Exception e) {
				logger.error("FBRN temporaryArchiveDataReview error",e);
				sendEmail(batchNo, excuteTime, "F", e.toString(), programId);
				updateFirBatchLog("F", e.toString(), userId, batchLogOid);
				return this.getReturnResult("資料檢核執行失敗");
			}
			if ("Y".equals(returnData.get(OUTSTATUS))) {
				fubonRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				sendEmail(batchNo, excuteTime, "S", returnData.get(OUTMSG), programId);
				updateFirBatchLog("S", returnData.get(OUTMSG), userId, batchLogOid);
			} else {
				fubonRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
			}
		}

		moveFile();
		return this.getReturnResult("執行完成");
	}

	private Map<String, String> temporaryDataStorage(String batchNo, String userId) throws Exception {
		Map<String, String> returnData = new HashMap<>();
		if (StringUtil.isSpace(batchNo)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		}
		if (StringUtil.isSpace(userId)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}

		// 執行時先在根目錄找是否有檔案，若無檔案，才在SFTP上抓檔案放在根目錄上，執行成功後才把檔案移到對應月份資料夾中
		File rootPath = new File(ROOTDIRECTORY);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}
		String zipFilename = "FBINSS_10.TXT.ZIP";
		File file = new File(ROOTDIRECTORY + zipFilename);
		// 若根目錄中無檔案就連sftp抓檔案
		if (!file.exists()) {
			// 取sftp檔案
			String strResult = getFileFromSftp(zipFilename);
			// sftp無檔案
			if (StringUtil.isSpace(strResult)) {
				returnData.put(OUTSTATUS, "0");
				returnData.put(OUTMSG, "");
				return returnData;
			}
			if ("fail".equals(strResult)) {
				returnData.put(OUTSTATUS, "0");
				returnData.put(OUTMSG, "連線sftp異常");
				return returnData;
			}
		}
		try {
			// 取得檔案後新增 FIR_AGTRN_BATCH_MAIN、FIR_AGTRN_BATCH_FB
			fubonRenewalFileService.insertBatchMain(batchNo, zipFilename, userId);
		} catch (Exception e) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "新增住火保經代續保轉核心批次主檔失敗(AGTRN_BATCH_MAIN)");
			logger.error("FBRN 新增住火保經代續保轉核心批次主檔失敗", e);
			return returnData;
		}

		// 解壓縮檔案
		String txtfilename = unzipFile(ROOTDIRECTORY, zipFilename);

		// 處理TXT檔
		List<String> fileDataList = readFile(ROOTDIRECTORY + txtfilename);
		int countData = fileDataList.size();
		if (countData == 0) {
			// 檔案內無資料
			returnData.put("dataqtyT", "0");
			returnData.put(FILESTATUS, "Z");
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "檔案無資料");
			return returnData;
		}

		try {
			// 資料新增必須全部新增或全部失敗 全部commit 或 全部rollback
			fubonRenewalFileService.insertFirAgtrnTmpFbList(batchNo, zipFilename, userId, fileDataList);
			returnData.put(OUTSTATUS, "Y");
			returnData.put(OUTMSG, "");
			returnData.put(FILESTATUS, "S");
			returnData.put("dataqtyT", String.valueOf(countData));
		} catch (Exception e) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(FILESTATUS, "A");
			returnData.put(OUTMSG, "新增RAWDATA至FIR_AGTRN_TMP_FB失敗。");
			logger.error("RunFubonRenewalService insert FIR_AGTRN_TMP_FB error", e);
			return returnData;
		}
		return returnData;
	}

	// 將zip檔解壓縮，回傳解壓縮後的檔案名稱list
	private String unzipFile(String workDir, String zipFilename) throws Exception {
		ZipUtil zipUtil = new ZipUtil();
		if (zipFilename.contains(".zip") || zipFilename.contains(".ZIP")) {
			zipUtil.unzip(workDir + zipFilename, workDir, configUtil.getString("fbZipPwd"));
		}

		String filename = "";
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(workDir))) {
			for (Path file : stream) {
				if (!file.getFileName().toString().contains(".ZIP") && file.getFileName().toString().length() != 6) {
					filename = file.getFileName().toString();
				}
			}
		}
		return filename;
	}

	// 取得sftp檔案
	private String getFileFromSftp(String filename) {
		String strResult = "";
		String sftpHost = configUtil.getString("fbSFTP");
		String sftpUser = configUtil.getString("fbSftpUserGet");
		String sftpPwd = configUtil.getString("fbSfptPwdGet");
		int sftpPort = Integer.parseInt(configUtil.getString("fbSftpPort"));
		String remoteDir = configUtil.getString("fbDowloadRemotePath");
		try {
			SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
			List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir);
			if(null == fileList) {
				strResult = "fail";
				return strResult;
			}
			List<String> downloadFileList = new ArrayList<>();
			for (int i = 0; i < fileList.size(); i++) {
				if (filename.equals(fileList.get(i))) {
					downloadFileList.add(fileList.get(i));
				}
			}
			if (!downloadFileList.isEmpty()) {
				strResult = sftpUtil.getFileFromSftp(remoteDir, ROOTDIRECTORY, downloadFileList);
			}
		} catch (Exception e) {
			logger.error("FBRN getFileFromSftp Exception", e);
			strResult = "fail";
			return strResult;
		}
		return strResult;
	}

	//讀取檔案
	private List<String> readFile(String filepath) throws IOException {
		List<String> fileDataList = new ArrayList<>();
		File file = new File(filepath);
		if (file.length() == 0) {
			return new ArrayList<>();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "big5"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					fileDataList.add(line);
				}
			}
		}
		return fileDataList;
	}

	// 資料檢核處理 需檢核所有參數必填
	@SuppressWarnings("unchecked")
	private Map<String, String> temporaryArchiveDataReview(String batchNo, String userId) throws Exception {
		Map<String, String> returnData = new HashMap<>();
		if (StringUtil.isSpace(batchNo)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		} 
		if (StringUtil.isSpace(userId)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}

		String handler1code = "";
		String comcode = "";
		// 先查詢服務人員是否存在，查詢結果不影響之後程式執行
		Result result = firBatchInfoService.findHandler1code();
		if (result.getResObject() != null) {
			FirHandler1codeVo handler1codeVo = (FirHandler1codeVo) result.getResObject();
			handler1code = handler1codeVo.getHandler1code();
			comcode = handler1codeVo.getComcode();
		}
		Map<String, String> basicData = new HashMap<>();
		basicData.put("handler1code", handler1code);
		basicData.put("comcode", comcode);
		
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("pStatus", "N");
		params.put("orderByBatchSeq", "BATCH_SEQ");
		result = firAgtrnTmpFbService.findFirAgtrnTmpFbByParams(params);
		if (result.getResObject() != null) {
			List<FirAgtrnTmpFb> tmpResult = (List<FirAgtrnTmpFb>) result.getResObject();
			for (int i = 0; i < tmpResult.size(); i++) {
				try {
					fubonRenewalFileService.insertFirAgtrnBatchDtl(batchNo, tmpResult.get(i).getBatchSeq(), userId);
				} catch (Exception e) {
					logger.error("FBRN insertFirAgtrnBatchDtl fail",e);
					returnData.put(OUTSTATUS, "N");
					returnData.put("transStatus", "E");
					returnData.put(OUTMSG, "新增FIR_AGTRN_BATCH_DTL批次明細檔失敗。");
					return returnData;
				}
			}

			Pattern datePattern = Pattern.compile("^([0-9]{4}(0+[1-9]|1[012])(0+[1-9]|[12][0-9]|3[01]))$");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

			// 以每個欄位長度切割
			int dataqtyS = 0;
			int dataqtyF = 0;
			String dataStatus = "0";

			for (int i = 0; i < tmpResult.size(); i++) {
				Map tmpdatas = new HashMap<>();// 存暫存變數 後續帶入bean存入資料庫
				int batchSeq = i + 1;
				
				String[] rawData = tmpResult.get(i).getRawdata().split(",");
				for (int j = 0; j < rawData.length; j++) {
					rawData[j] = trimBlank(rawData[j]);
				}
				
				logger.info("rawData" + i + ":" + rawData);
				StringBuilder errMsg = new StringBuilder();
				StringBuilder warnMsg = new StringBuilder();
				String diffFlag = "N";
				StringBuilder diffReason = new StringBuilder();
				String as400 = "Y";// 是否有撈到AS400保單資料
				FirAgtrnAs400Data as400Data = new FirAgtrnAs400Data();

				boolean verifyStatus = true;

				// 01銀行住火保單號碼、02地震基本保單號 檢核 start

				String firPolicyno = rawData[0];
				String quakePolicyno = rawData[1];
				String oldpolicyno = "";
				boolean havePolicyno = true;

				if (!StringUtil.isSpace(firPolicyno) || !StringUtil.isSpace(quakePolicyno)) {
					if (StringUtil.isSpace(quakePolicyno)) {
						warnMsg.append("續保單號(地震)-未輸入；");
					} else if ("18".equals(quakePolicyno.substring(0, 2))) {
						oldpolicyno = quakePolicyno;
						tmpdatas.put("oldpolicyno", oldpolicyno);
					}

					if (StringUtil.isSpace(firPolicyno)) {
						warnMsg.append("續保單號(火險)-未輸入；");
					} else if ("18".equals(firPolicyno.substring(0, 2))) {
						oldpolicyno = firPolicyno;
						tmpdatas.put("oldpolicyno", oldpolicyno);
					}

					if (StringUtil.isSpace(oldpolicyno)) {
						as400 = "N";
						diffFlag = "Y";
						diffReason.append("保單號碼非中信產保單；");
						errMsg.append("續保單號非本公司保單號(");
						errMsg.append(StringUtil.isSpace(firPolicyno) ? quakePolicyno : firPolicyno);
						errMsg.append(");");
					} else {
						params.clear();
						params.put("businessnature", "I99060");
						params.put("oldpolicyno", oldpolicyno);
						params.put("sortBy", "RN_YYYYMM");
						params.put("sortType", "DESC");
						result = firAgtrnAs400DataService.findFirAgtrnAs400DataByParams(params);
						if (result.getResObject() == null) {
							warnMsg.append("查無AS400保單資料，無法帶入其他欄位資料；");
							as400 = "N";
							diffFlag = "Y";
							diffReason.append("查無前一年度保單資料，無法進行差異比對。;");
						} else {
							as400Data = ((List<FirAgtrnAs400Data>) result.getResObject()).get(0);
						}
					}
				} else {
					havePolicyno = false;
					diffFlag = "Y";
					diffReason.append("保單號碼無資料；");
					errMsg.append("續保單號-未輸入；");
				}
				tmpdatas.put("firPolicyno", firPolicyno);
				tmpdatas.put("quakePolicyno", quakePolicyno);
				// 01銀行住火保單號碼、02地震基本保單號 檢核 end

				// 03主機所有權人姓名、04主機所有權人ID 檢核 start
				String nameI = rawData[2];
				String idI = rawData[3];
				tmpdatas.put("fbIdI", idI);
				tmpdatas.put("fbNameI", nameI);

				if (StringUtil.isSpace(nameI)) {
					warnMsg.append("主機所有權人姓名-未輸入(被保險人)；");
				}
				if (StringUtil.isSpace(idI)) {
					diffFlag = "Y";
					diffReason.append("主機所有權人ID–未輸入；");
					warnMsg.append("主機所有權人ID –未輸入(被保險人)；");
				}
				if ("Y".equals(as400) && !StringUtil.isSpace(nameI) && !nameI.equals(as400Data.getNameI())) {
					nameI = as400Data.getNameI();
					warnMsg.append("主機所有權人姓名–與核心資料不一致(" + nameI + ")，已替換為我方資料；");
				}
				if ("Y".equals(as400) && !StringUtil.isSpace(idI) && !idI.equals(as400Data.getIdI())) {
					diffFlag = "Y";
					diffReason.append("主機所有權人ID–資料不一致(" + as400Data.getIdI() + ")；");
					warnMsg.append("主機所有權人ID–與核心資料不一致(" + as400Data.getIdI() + ")；");
				}

				tmpdatas.put("idI", idI);
				tmpdatas.put("nameI", nameI);

				String phoneI = "";
				String mobileI = "";
				String postcodeI = "";
				String addressI = "";
				
				if ("Y".equals(as400)) {
					phoneI = as400Data.getPhoneI();
					mobileI = as400Data.getMobileI();
					postcodeI = as400Data.getPostcodeI();
					addressI = as400Data.getAddressI();
				}
				tmpdatas.put("phoneI", phoneI);
				tmpdatas.put("mobileI", mobileI);
				tmpdatas.put("postcodeI", postcodeI);
				tmpdatas.put("addressI", addressI);

				// 若rowData ID有值才進行證號檢核
				// 預計呼叫共用Service 進行檢核 底下借款人也會用到相同檢核
				String idtype1 = "";
				String insuredNature1 = "";
				if (!StringUtil.isSpace(idI)) {
					try {
						Map<String, String> verifyMap = firVerifyDatasService.verifyID(idI);
						if (!StringUtil.isSpace(verifyMap.get("errMsg"))) {
							diffFlag = "Y";
							diffReason.append("主機所有權人ID–證號驗證失敗 " + verifyMap.get("errMsg") + "；");
							errMsg.append("主機所有權人ID-" + verifyMap.get("errMsg") + "；");
						}
						if (!StringUtil.isSpace(verifyMap.get("insuredNature"))) {
							insuredNature1 = verifyMap.get("insuredNature");
						}
						if (!StringUtil.isSpace(verifyMap.get("idType"))) {
							idtype1 = verifyMap.get("idType");
							if("04".equals(idtype1)) {
								errMsg.append("所有權人ID-證號類型可能為稅籍編號或異常，請再確認。；");
							}
						}
					}catch(Exception e) {
						logger.error("verifyID WS Exception", e);
						errMsg.append("主機所有權人ID-呼叫證號檢核WS異常:" + e + "；");
					}
				}
				tmpdatas.put("idtype1", idtype1);
				tmpdatas.put("insuredNature1", insuredNature1);

				// 03主機所有權人姓名、04主機所有權人ID 檢核 end

				boolean tmpStatus = true;
				// 05保險起日 檢核 start
				String startdate = rawData[4];
				tmpdatas.put("fbStartdate", startdate);
				if (StringUtil.isSpace(startdate) || !datePattern.matcher(startdate).matches()) {
					diffFlag = "Y";
					diffReason.append("保險起日-未輸入或格式異常；");
					warnMsg.append("保險起日-未輸入或格式異常；");
					verifyStatus = false;
					tmpStatus = false;
				} else {
					//來源資料起迄日都須加上一年
					startdate = (Integer.parseInt(startdate.substring(0,4))+1)+startdate.substring(4);
					if ("Y".equals(as400) && !startdate.equals(dateFormat.format(as400Data.getStartdate()))) {
						diffFlag = "Y";
						diffReason.append("保險起日–資料不一致(" + dateFormat.format(as400Data.getStartdate()) + ")；");
						warnMsg.append("保險起日–與核心資料不一致(" + dateFormat.format(as400Data.getStartdate()) + ")；");
					}
				}
				tmpdatas.put("startdate", startdate);
				// 05保險起日 檢核 end

				// 06保險迄日檢核 start
				String enddate = rawData[5];
				tmpdatas.put("fbEnddate", enddate);
				if (StringUtil.isSpace(enddate) || !datePattern.matcher(enddate).matches()) {
					diffFlag = "Y";
					diffReason.append("保險迄日-未輸入或格式異常；");
					warnMsg.append("保險迄日-未輸入或格式異常；");
					tmpStatus = false;
				} else {
					enddate = (Integer.parseInt(enddate.substring(0,4))+1)+enddate.substring(4);
					if ("Y".equals(as400) && !enddate.equals(dateFormat.format(as400Data.getEnddate()))) {
						diffFlag = "Y";
						diffReason.append("保險迄日–資料不一致(" + dateFormat.format(as400Data.getEnddate()) + ")；");
						warnMsg.append("保險迄日–與核心資料不一致(" + dateFormat.format(as400Data.getEnddate()) + ")；");
					}
				} 
				tmpdatas.put("enddate", enddate);

				if (tmpStatus) {// 起日、迄日有值且通過檢核才做判斷
					int startYear = Integer.parseInt(startdate.substring(0, 4)) + 1;
					int endYear = Integer.parseInt(enddate.substring(0, 4));
					if (startYear != endYear) {
						diffFlag = "Y";
						diffReason.append("保險期間不是一年；");
						errMsg.append("保險期間不是一年(" + startdate + "~" + enddate + ")；");
					}
				}
				// 06保險迄日檢核 end

				// 07主機保險標的物地址(同時處理郵遞區號、縣市行政區) start
				String addresscode = "";
				String addressname = "";
				String address = rawData[6];
				
				if (StringUtil.isSpace(address)) {
					diffFlag = "Y";
					diffReason.append("主機保險標的物地址-未輸入；");
					errMsg.append("主機保險標的物地址-未輸入；");
				} else if (!address.contains("號")) {
					diffFlag = "Y";
					diffReason.append("主機保險標的物地址-最少要有「號」這個字；");
					errMsg.append("保險標的物地址-最少要有「號」這個字；");
				}

				if ("Y".equals(as400) && !address.equals(as400Data.getAddressdetail())) {
					diffFlag = "Y";
					diffReason.append("主機保險標的物地址-資料不一致(" + as400Data.getAddressdetail() + ")；");
					warnMsg.append("標的物地址–與核心資料不一致(" + as400Data.getAddressdetail() + ")，請同時確認郵遞區號與縣市行政區是否正確；");
				}

				if ("Y".equals(as400)) {
					addresscode = as400Data.getAddresscode();
					addressname = as400Data.getAddressname();
				}
				tmpdatas.put("address", address);
				tmpdatas.put("addresscode", addresscode);
				tmpdatas.put("addressname", addressname);
				// 07主機保險標的物地址(同時處理郵遞區號、縣市行政區) end

				tmpdatas.put("fbMortgagee", rawData[7]);//08抵押權人
				
				// 09建築物結構(此處額外處理建築等級-取AS400資料) start
				String wallno = "";
				String roofno = "";

				String structure = "";
				String buildStructure = rawData[8];
				tmpdatas.put("fbWallno", buildStructure);
				if (StringUtil.isSpace(buildStructure)) {
					diffFlag = "Y";
					diffReason.append("建築物結構-未輸入；");
					errMsg.append("建築物結構-未輸入；");
					verifyStatus = false;
				} else {
					params.clear();
					params.put("codetype", "Wallno_FB");
					params.put("source", "FB");
					params.put("codecode", buildStructure);
					result = rfrcodeService.findRfrcodeByParams(params);
					if (result.getResObject() != null) {
						wallno = ((List<Rfrcode>) result.getResObject()).get(0).getMappedcode();
						if ("Y".equals(as400) && !wallno.equals(as400Data.getWallmaterial())) {
							diffFlag = "Y";
							diffReason.append("建築物結構-不一致(" + as400Data.getWallmaterial() + ")；");
							warnMsg.append("建築物結構–與核心資料不一致(" + as400Data.getWallmaterial() + ")；");
						}
						if ("Y".equals(as400) && !StringUtil.isSpace(as400Data.getRoofmaterial())) {
							roofno = as400Data.getRoofmaterial();
						}
						if ("Y".equals(as400) && !StringUtil.isSpace(as400Data.getStructure1())) {
							structure = as400Data.getStructure1();
						}
					} else {
						diffFlag = "Y";
						diffReason.append("建築物結構-無法對應中信產代碼；");
						errMsg.append("建築物結構(" + buildStructure + ")-轉換核心代碼失敗(RFRCODE- Wallno_FB)；");
						verifyStatus = false;
					}
				}
				tmpdatas.put("wallno", wallno);
				tmpdatas.put("roofno", roofno);
				tmpdatas.put("structure", structure);

				// 09建築物結構(此處額外處理建築等級-取AS400資料) end

				// 10總樓層數 start
				String sumfloors = rawData[9];
				BigDecimal highrisefee = null;

				if (StringUtil.isSpace(sumfloors) || !StringUtil.isNumeric(sumfloors)
						|| Integer.parseInt(sumfloors) == 0) {
					diffFlag = "Y";
					diffReason.append("總樓層數-未輸入或格式異常；");
					errMsg.append("總樓層數-未輸入或格式異常；");
					verifyStatus = false;
				} else {
					sumfloors = replaceLeftZero(sumfloors);
					if ("Y".equals(as400) && Integer.parseInt(sumfloors) != (as400Data.getSumfloors())) {
						diffFlag = "Y";
						diffReason.append("總樓層數-資料不一致(" + as400Data.getSumfloors() + ")；");
						warnMsg.append("總樓層數–與核心資料不一致(" + as400Data.getSumfloors() + ")；");
					}
					int floorNum = Integer.parseInt(sumfloors);
					if (floorNum >= 25) {
						highrisefee = new BigDecimal(15);
					} else if (floorNum >= 15) {
						highrisefee = new BigDecimal(10);
					} else {
						highrisefee = new BigDecimal(0);
					}
					tmpdatas.put("highrisefee", highrisefee);
					tmpdatas.put("sumfloors", sumfloors);
				}

				// 10總樓層數 end

				// 11主機坪數(此處額外處理建築年份-取AS400資料) start
				String area = rawData[10];

				if (!StringUtil.isSpace(area) && StringUtil.isNum(area) && checkArea(area)) {
					area = replaceLeftZero(area);
					tmpdatas.put("area",area);
					
					if("Y".equals(as400)) {
						BigDecimal buildarea = new BigDecimal(as400Data.getBuildarea());
						BigDecimal maxArea = new BigDecimal(area).add(BigDecimal.valueOf(0.5));
						BigDecimal minArea = new BigDecimal(area).subtract(BigDecimal.valueOf(0.5));
						if ("Y".equals(as400) && (buildarea.compareTo(maxArea) >= 0 || buildarea.compareTo(minArea) <= 0)) {
							diffFlag = "Y";
							diffReason.append("主機坪數-資料不一致(" + as400Data.getBuildarea() + ")；");
						}
					}
				} else {
					diffFlag = "Y";
					diffReason.append("坪數-未輸入或內容值異常；");
					errMsg.append("坪數-未輸入或內容值異常；");
					verifyStatus = false;
				}

				String buildyears = "";
				if ("Y".equals(as400) && !StringUtil.isSpace(as400Data.getBuildyears())) {
					buildyears = as400Data.getBuildyears();
				}
				tmpdatas.put("buildyears", buildyears);
				// 11主機坪數(此處額外處理建築年份-取AS400資料) end

				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  start*/
				//使用性質需為住宅
				if("Y".equals(as400) && !"住宅".equals(rawData[11])) {
					diffFlag = "Y";
					diffReason.append("使用性質-資料不一致(住宅);");
					warnMsg.append("使用性質非住宅;");
				}
				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  end*/
				tmpdatas.put("useNature", rawData[11]);//12使用性質
				
				// 13主機住火保額、14主機地震險保額 start
				String fAmt = rawData[12];
				//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 
				tmpdatas.put("amountFAgt", fAmt);
				String qAmt = rawData[13];
				String oidFirPremcalcTmp = "";
				String famtStatus = "";
				String qamtStatus = "";
				BigDecimal firAmt = new BigDecimal(0);
				BigDecimal quakeAmt = new BigDecimal(0);
				BigDecimal maxAmt = new BigDecimal(0);
				BigDecimal premiumT = new BigDecimal(0);//總保費
				BigDecimal premiumF = new BigDecimal(0);
				BigDecimal premiumQ = new BigDecimal(0);
				// 必須要有本公司住火保單號或本公司地震保單號
				if (havePolicyno) {
					//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
					if (!StringUtil.isSpace(firPolicyno) && !StringUtil.isSpace(fAmt) && StringUtil.isNumeric(fAmt)
							&& Integer.parseInt(replaceLeftZero(fAmt)) > 0) {
						fAmt = replaceLeftZero(fAmt);
						if ("Y".equals(as400) && Long.parseLong(fAmt) != as400Data.getAmountF()) {
							diffFlag = "Y";
							diffReason.append("主機住火保額-不一致(" + as400Data.getAmountF() + ")；");
							warnMsg.append("主機住火保額–與核心資料不一致(" + as400Data.getAmountF() + ")；");
						}
						
						/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start */
						//依保險生效日、火險保額取得重算後保額
						fAmt = firVerifyDatasService.recalAmount(as400Data.getAmountF().toString(), dateFormat.parse(startdate));
						/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end */
						
					/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
					} else if(!StringUtil.isSpace(firPolicyno)){
						diffFlag = "Y";
						diffReason.append("主機住火保額-未輸入或內容值異常；");
						errMsg.append("主機住火保額-未輸入或內容值異常；");
					} else if(StringUtil.isSpace(firPolicyno)) {
						String fAmtF = StringUtil.isSpace(fAmt)?"0":fAmt;
						if(!"0".equals(fAmtF)){
							diffFlag = "Y";
							diffReason.append("主機住火保額-無銀行住火保單號碼，住火保額應為0或空值;");
							errMsg.append("主機住火保額-無銀行住火保單號碼，住火保額應為0或空值;");
						}
						if("Y".equals(as400) && (!"0".equals(fAmtF) || as400Data.getAmountF()!=0) 
								&& Long.parseLong(fAmtF) != as400Data.getAmountF()) {
							diffFlag = "Y";
							diffReason.append("主機住火保額–銀行無住火保單，但產險有住火保額資料("+as400Data.getAmountF()+");");
							errMsg.append("主機住火保額–銀行無住火保單，但產險有住火保額資料 ("+as400Data.getAmountF()+");");
						}
					/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/
					}
					
					if (!StringUtil.isSpace(qAmt) && StringUtil.isNumeric(qAmt) && Integer.parseInt(replaceLeftZero(qAmt)) > 0) {
						qAmt = replaceLeftZero(qAmt);
						if ("Y".equals(as400) && Long.parseLong(qAmt) != as400Data.getAmountQ()) {
							diffFlag = "Y";
							diffReason.append("主機地震險保額-不一致(" + as400Data.getAmountQ() + ")；");
							warnMsg.append("主機地震險保額–與核心資料不一致(" + as400Data.getAmountQ() + ")；");
						}
					} else {
						diffFlag = "Y";
						diffReason.append("主機地震險保額-未輸入或內容值異常；");
						errMsg.append("主機地震險保額-未輸入或內容值異常；");
					}
					// 保額、保費比對處理
					
					if ((!StringUtil.isSpace(fAmt) || !StringUtil.isSpace(qAmt))
							&& (verifyStatus && !StringUtil.isSpace(startdate) && !StringUtil.isSpace(addresscode)
									&& !StringUtil.isSpace(wallno) && !StringUtil.isSpace(roofno)
									&& !StringUtil.isSpace(sumfloors) && !StringUtil.isSpace(area))) {
						FirPremcalcTmp firPremcalcTmp;
						// 呼叫webService保額計算
						FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
						firWsParamVo.setSourceType("FBRN");
						firWsParamVo.setSourceUser(userId);
						firWsParamVo.setCalcType("1");
						firWsParamVo.setCalcDate(startdate);
						firWsParamVo.setChannelType("20");
						firWsParamVo.setPostcode(addresscode);
						firWsParamVo.setWallno(wallno);
						firWsParamVo.setRoofno(roofno);
						firWsParamVo.setSumfloors(sumfloors);
						firWsParamVo.setBuildarea(area);
						firWsParamVo.setDecorFee("0");
						try {
							firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
							if ("Y".equals(firPremcalcTmp.getReturnType())) {
								firAmt = firPremcalcTmp.getFsAmt();
								maxAmt = firPremcalcTmp.getFsMaxAmt();

								// 判斷火險保額是否足額
								if (!StringUtil.isSpace(fAmt) && new BigDecimal(fAmt).compareTo(maxAmt) > 0) {
									famtStatus = "3";
									warnMsg.append("檢核-火險超額" + fAmt + "(上限保額:" + maxAmt + ")；");
								} else if (!StringUtil.isSpace(fAmt) && new BigDecimal(fAmt).compareTo(firAmt) < 0) {
									famtStatus = "2";
									warnMsg.append("火險不足額" + fAmt + "(建議保額：" + firAmt + ")；");
								} else {
									famtStatus = "1";// 足額
								}

								/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start */
								//依造價表計算地震險保額，不依富邦轉進保額當地震險保額，所以不再做地震險是否足額判斷
								quakeAmt = firPremcalcTmp.getEqAmt();
								qAmt = firPremcalcTmp.getEqAmt().toString();
								// 判斷地震保額是否足額
//								if (!StringUtil.isSpace(qAmt)) {
//									BigDecimal qAmtCarry = new BigDecimal(qAmt).setScale(-4, RoundingMode.UP);
//									if (qAmtCarry.compareTo(quakeAmt) > 0) {
//										qamtStatus = "3";
//										errMsg.append("檢核-地震超額" + qAmt + "(地震保額:" + quakeAmt + ")；");
//									} else if (qAmtCarry.compareTo(quakeAmt) < 0) {
//										qamtStatus = "2";
//										errMsg.append("檢核-地震不足額" + qAmt + "(地震保額:" + quakeAmt + ")；");
//									} else if (qAmtCarry.compareTo(quakeAmt) == 0) {
//										qamtStatus = "1";// 足額
//									}
//								}
								/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end */
							} else {
								errMsg.append("檢核-保額計算WS異常(" + firPremcalcTmp.getOid() + "):"
										+ firPremcalcTmp.getReturnMsg() + "；");
							}
							oidFirPremcalcTmp = firPremcalcTmp.getOid().toString();
							
						} catch (Exception e) {
							logger.error("保額計算失敗", e);
							warnMsg.append("檢核-保額計算WS無回應；");
						}

						// 呼叫webservice計算保費
						FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
						firPremWsParamVo.setSourceType("FBRN");
						firPremWsParamVo.setSourceUser(userId);
						firPremWsParamVo.setCalcType("2");
						firPremWsParamVo.setCalcDate(startdate);
						firPremWsParamVo.setChannelType("20");
						
						ArrayList<FirInsPremVo> firInsPremVoList = new  ArrayList<>();
						if(new BigDecimal(fAmt).compareTo(new BigDecimal(0))>0) {
							FirInsPremVo firInsPremVoF = new FirInsPremVo();
							firInsPremVoF.setRiskcode("F02");
							firInsPremVoF.setKindcode("FR3");
							firInsPremVoF.setParaType("1");
							firInsPremVoF.setPara01(fAmt);
							firInsPremVoF.setPara02(wallno);
							firInsPremVoF.setPara03(roofno);
							firInsPremVoF.setPara04(sumfloors);
							firInsPremVoF.setPara05("N");
							firInsPremVoList.add(firInsPremVoF);
						}
						
						if(new BigDecimal(qAmt).compareTo(new BigDecimal(0))>0) {
							FirInsPremVo firInsPremVoQ = new FirInsPremVo();
							firInsPremVoQ.setRiskcode("F02");
							firInsPremVoQ.setKindcode("FR2");
							firInsPremVoQ.setPara01(qAmt);
							firInsPremVoList.add(firInsPremVoQ);
						}
						firPremWsParamVo.setInsPremList(firInsPremVoList);

						try {
							firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);

							List<FirPremcalcTmpdtl> premList = firPremcalcTmp.getFirPremcalcTmpdtlList();
							FirPremcalcTmpdtl fr2Dtl = null;
							FirPremcalcTmpdtl fr3Dtl = null;
							for (int p = 0; p < premList.size(); p++) {
								if ("FR2".equals(premList.get(p).getKindcode())) {
									fr2Dtl = premList.get(p);
								} else if ("FR3".equals(premList.get(p).getKindcode())) {
									fr3Dtl = premList.get(p);
								}
							}
							if ("Y".equals(firPremcalcTmp.getReturnType())) {
								premiumT = firPremcalcTmp.getSumPremium();
								if (!StringUtil.isSpace(fAmt)) {
									premiumF = fr3Dtl.getPremium();
								}

								if (!StringUtil.isSpace(qAmt)) {
									premiumQ = fr2Dtl.getPremium();
								}

								if ("Y".equals(as400)
										&& new BigDecimal(as400Data.getPremiumF()).compareTo(premiumF) != 0) {
									warnMsg.append("檢核-WS住火保費(" + premiumF + ")與AS400住火保費(" + as400Data.getPremiumF()
											+ ")不同；");
								}
								if ("Y".equals(as400)
										&& new BigDecimal(as400Data.getPremiumQ()).compareTo(premiumQ) != 0) {
									warnMsg.append("檢核-WS地震保費(" + premiumQ + ")與AS400地震保費(" + as400Data.getPremiumQ()
											+ ")不同；");
								}

							} else {
								errMsg.append("檢核-保費計算WS計算發生錯誤(" + firPremcalcTmp.getOid() + ")"
										+ firPremcalcTmp.getReturnMsg() + "；");
							}

						} catch (Exception e) {
							logger.error("保費計算WS失敗",e);
							errMsg.append("檢核-保費計算WS無回應，未計算保費；");
						}

					} else {
						warnMsg.append("檢核-住火/地震建議保額因資料有缺漏未計算；");
					}

				}
				tmpdatas.put("amountF", fAmt);
				tmpdatas.put("amountQ", qAmt);
				tmpdatas.put("premiumF", premiumF);
				tmpdatas.put("premiumQ", premiumQ);
				tmpdatas.put("premiumT", premiumT);

				// 13主機住火保額、14主機地震險保額 end

				// 15擔保品編號+保險單建檔序號 start
				if (StringUtil.isSpace(rawData[14])) {
					errMsg.append("擔保品編號+保險單建檔序號-未輸入；");
				}
				tmpdatas.put("collateralnumber1", rawData[14]);
				// 15擔保品編號+保險單建檔序號 end

				// 16借款人ID、17借款人姓名 start
				String idA = rawData[15];
				String nameA = rawData[16];
				tmpdatas.put("nameA", nameA);
				tmpdatas.put("idA", idA);
				
				if (StringUtil.isSpace(idA)) {
					diffFlag = "Y";
					diffReason.append("借款人ID–未輸入；");
					warnMsg.append("借款人ID–未輸入(要保人)；");
				}

				if (StringUtil.isSpace(nameA)) {
					warnMsg.append("借款人姓名-未輸入(要保人)；");
				}

				if ("Y".equals(as400) && !StringUtil.isSpace(idA) && !idA.equals(as400Data.getIdA())) {
					diffFlag = "Y";
					diffReason.append("借款人ID–資料不一致(" + as400Data.getIdA() + ")；");
					warnMsg.append("借款人ID–與核心資料不一致(" + as400Data.getIdA() + ")；");
					nameA = as400Data.getNameA();
				}

				if ("Y".equals(as400) && !StringUtil.isSpace(nameA) && !nameA.equals(as400Data.getNameA())) {
					warnMsg.append("借款人姓名–與核心資料不一致(" + as400Data.getNameA() + ")；");
					nameA = rawData[16];
				}

				String phoneA = "";
				String mobileA = "";
				String postcodeA = "";
				String addressA = "";
				if ("Y".equals(as400)) {
					phoneA = as400Data.getPhoneA();
					mobileA = as400Data.getMobileA();
					postcodeA = as400Data.getPostcodeA();
					addressA = as400Data.getAddressA();
				}
				tmpdatas.put("phoneA", phoneA);
				tmpdatas.put("mobileA", mobileA);
				tmpdatas.put("postcodeA", postcodeA);
				tmpdatas.put("addressA", addressA);

				String idtype2 = "";
				String insuredNature2 = "";
				if (!StringUtil.isSpace(idA)) {
					try {
						Map<String, String> verifyMap = firVerifyDatasService.verifyID(idI);
						if (!StringUtil.isSpace(verifyMap.get("errMsg"))) {
							diffFlag = "Y";
							diffReason.append("借款人ID–證號驗證失敗 " + verifyMap.get("errMsg") + "；");
							errMsg.append("借款人ID-" + verifyMap.get("errMsg") + "；");
						}
						if (!StringUtil.isSpace(verifyMap.get("insuredNature"))) {
							insuredNature2 = verifyMap.get("insuredNature");
						}
						if (!StringUtil.isSpace(verifyMap.get("idType"))) {
							idtype2 = verifyMap.get("idType");
							if("04".equals(idtype2)) {
								errMsg.append("借款人ID-證號類型可能為稅籍編號或異常，請再確認。；");
							}
						}
					}catch(Exception e) {
						logger.error("verifyID WS Exception", e);
						errMsg.append("借款人ID-呼叫證號檢核WS異常:" + e + "；");
					}
				}
				tmpdatas.put("idtype2", idtype2);
				tmpdatas.put("insuredNature2", insuredNature2);
				// 16借款人ID、17借款人姓名 end

				// 18保險公司代號
				if (StringUtil.isSpace(rawData[17])) {
					errMsg.append("保險公司代號-未輸入；");
				}
				// 19作業中心
				String extracomcode = rawData[18];
				if (StringUtil.isSpace(extracomcode)) {
					errMsg.append("作業中心-未輸入；");
				}else {
					extracomcode = replaceLeftZero(extracomcode);
				}
				// 20業務員姓名
				if (StringUtil.isSpace(rawData[19])) {
					errMsg.append("業務員姓名-未輸入；");
				}
				// 21登錄字號
				if (StringUtil.isSpace(rawData[20])) {
					errMsg.append("登錄字號-未輸入；");
				}
				tmpdatas.put("insCom", rawData[17]);
				tmpdatas.put("extracomcode", extracomcode);
				tmpdatas.put("salesName", rawData[19]);
				tmpdatas.put("handleridentifynumber", rawData[20]);
				tmpdatas.put("diffFlag", diffFlag);
				tmpdatas.put("diffReason", diffReason.toString());

				/* 商業邏輯檢核及佣金率處理 start */

				// 前期保額/保費處理start
				Long amtFLast = null ;
				Long amtQLast = null ;
				Long premFLast = null ;
				Long premQLast = null ;
				if ("Y".equals(as400)) {
					amtFLast = as400Data.getAmountF();
					amtQLast = as400Data.getAmountQ();
					premFLast = as400Data.getPremiumF();
					premQLast = as400Data.getPremiumQ();
				}
				tmpdatas.put("amtFLast", amtFLast);
				tmpdatas.put("amtQLast", amtQLast);
				tmpdatas.put("premFLast", premFLast);
				tmpdatas.put("premQLast", premQLast);
				
				// 前期保額/保費處理end

				// 建築等級說明文字處理start
				String structureText = "";
				if (!StringUtil.isSpace(wallno) && !StringUtil.isSpace(roofno) && !StringUtil.isSpace(sumfloors)
						&& !StringUtil.isSpace(structure)) {

					String wallname = firVerifyDatasService.findPrpdNewCode("WallMaterial", wallno);
					if (StringUtil.isSpace(wallname)) {
						errMsg.append("建築等級說明-外牆(" + wallname + ")查無名稱；");
					}
					String roofname = firVerifyDatasService.findPrpdNewCode("RoofMaterial", roofno);
					if (StringUtil.isSpace(roofname)) {
						errMsg.append("建築等級說明-屋頂(" + roofname + ")查無名稱；");
					}
					String floors = sumfloors + "層樓";
					String stxt = getStructure(structure);
					structureText = wallname + roofname + floors + stxt;
					tmpdatas.put("structureText", structureText);
				}

				// 建築等級說明文字處理end

				// 取佣金率start
				String commrateF = "0";
				String commrateQ = "0";
				params.clear();
				params.put("businesssourcecode", "I00107");
				result = prpdAgreementService.findPrpdAgreementJoinDetail(params);
				if (result.getResObject() != null) {
					List<PrpdAgreement> resultList = (List<PrpdAgreement>) result.getResObject();
					// 還要加上判斷是否為本公司保單號檢查
					for (int j = 0; j < resultList.size(); j++) {
						if (resultList.get(j).getKindcode().equals("FR2")) {
							commrateQ = resultList.get(j).getTopCommission();
						} else if (resultList.get(j).getKindcode().equals("FR3")) {
							commrateF = resultList.get(j).getTopCommission();
						}
					}
				} else {
					errMsg.append("無法取得核心系統佣金率或佣金率設定異常；");
				}
				tmpdatas.put("commrateQ", commrateQ);
				tmpdatas.put("commrateF", commrateF);
				
				// 取佣金率end

				FirVerifyVo firVerifyVo = new FirVerifyVo();
				
				// 複保險檢核 start
				String dquakeStatus = "";
				String dquakeNo = "";
				if (!StringUtil.isSpace(startdate) && !StringUtil.isSpace(enddate) && !StringUtil.isSpace(addresscode)
						&& !StringUtil.isSpace(address)) {
					FirEqFundQueryVo firEqFundQueryVo = new FirEqFundQueryVo();

					firEqFundQueryVo.setStartDate(transferDateFormat(startdate, "yyyyMMdd", "yyyy/MM/dd"));
					firEqFundQueryVo.setEndDate(transferDateFormat(enddate, "yyyyMMdd", "yyyy/MM/dd"));
					firEqFundQueryVo.setPostcode(addresscode);
					firEqFundQueryVo.setAddress(address);
					firEqFundQueryVo.setSourceType("FBRN");
					firEqFundQueryVo.setSourceUserid(userId);
					firVerifyVo = firVerifyDatasService.queryDoubleInsVerify(firEqFundQueryVo);
					if(!"".equals(firVerifyVo.getWarnMsg())) {
						warnMsg.append("檢核-"+firVerifyVo.getWarnMsg());
					}
				} else {
					warnMsg.append("檢核-因資料不齊全未進行複保險檢核；");
				}
				tmpdatas.put("dquakeNo", dquakeNo);
				// 複保險檢核 end

				// 地址正確性檢核start
				String addrStatus = "";
				String addrDetail = "";
				if (!StringUtil.isSpace(addresscode) && !StringUtil.isSpace(address)
						&& !StringUtil.isSpace(structure) && !StringUtil.isSpace(sumfloors)&& !StringUtil.isSpace(buildyears)) {//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數
					FirAddressCheckVo firAddressCheckVo = new FirAddressCheckVo();
					firAddressCheckVo.setZip(addresscode);
					firAddressCheckVo.setAddress(address);
					firAddressCheckVo.setStructure(structure);
					firAddressCheckVo.setFloors(sumfloors);
					firAddressCheckVo.setBuildyears(buildyears);//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數
					firVerifyVo = firVerifyDatasService.addressVerify(firAddressCheckVo);
					addrStatus = firVerifyVo.getAddrStatus();
					addrDetail = firVerifyVo.getAddrDetail();
					if(!"".equals(firVerifyVo.getWarnMsg())) {
						warnMsg.append("檢核-"+firVerifyVo.getWarnMsg());
					}
				} else {
					warnMsg.append("檢核-因資料不齊全未進行地址正確性檢核；");
				}

				// 地址正確性檢核end

				// 颱風洪水檢核start
				// 還要加入判斷是否 有投保本公司火險
				if (!StringUtil.isSpace(structure) && !StringUtil.isSpace(addresscode)) {
					String[] areaArr = { "基隆市", "宜蘭縣", "花蓮縣", "台東縣", "屏東縣" };
					if (("3".equals(structure) || "5".equals(structure) || "6".equals(structure))) {
						params.clear();
						params.put("codetype ", "PostCode");
						params.put("codecode", addresscode);
						result = rfrcodeService.findRfrcodeByParams(params);
						if (result.getResObject() != null) {
							List<Rfrcode> rfrcodeList = (List<Rfrcode>) result.getResObject();
							if (Arrays.asList(areaArr).contains(rfrcodeList.get(0).getCodename().substring(1, 3))) {
								warnMsg.append("易淹水地區；");
							}
						}
					}
				} else {
					warnMsg.append("檢核-因資料不齊全未進行颱風洪水檢核；");
				}

				// 颱風洪水檢核end

				// 稽核議題檢核start
				if (!StringUtil.isSpace(addresscode) && !StringUtil.isSpace(address)
						&& !StringUtil.isSpace(structure) && !StringUtil.isSpace(sumfloors)
						&& !StringUtil.isSpace(wallno) && !StringUtil.isSpace(roofno)) {
					StringBuilder ruleMsg = new StringBuilder();
					FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
					firAddressRuleObj.setRiskcode("F02");
					firAddressRuleObj.setFuncType("P");
					firAddressRuleObj.setRulePrefix("FIR");
					firAddressRuleObj.setPostcode(addresscode);// 郵遞區號三碼
					firAddressRuleObj.setAddress(addressname);
					firAddressRuleObj.setAddrStructure(structure);
					firAddressRuleObj.setAddrSumfloors(sumfloors);
					firAddressRuleObj.setAddrWall(wallno);
					firAddressRuleObj.setAddrRoof(roofno);
					try {
						RuleReponseVo ruleReponseVo = firVerifyDatasService.firAddressRule(firAddressRuleObj);
						if ("0".equals(ruleReponseVo.getStatus())) {
							List<RuleReponseDetailVo> ruleList = ruleReponseVo.getDetailList();
							if (!ruleList.isEmpty()) {
								for (int j = 0; j < ruleList.size(); j++) {
									if ("0".equals(ruleList.get(j).getRuleResult())) {
										ruleMsg.append(ruleList.get(j).getRuleMsg());
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("webService呼叫火險稽核議題檢核異常", e);
					}
				} else {
					warnMsg.append("檢核-因資料不齊全未進行稽核議題檢核；");
				}
				// 稽核議題檢核end

				/* 商業邏輯檢核及佣金率處理 end */

				// 每一筆完成檢核後寫入APS 要保檔/關係人檔/差異檔

				try {
					fubonRenewalFileService.insertFirAgtTocore(batchNo, batchSeq,
							userId, tmpdatas, handler1code, comcode, as400Data);
					dataqtyS++;
					dataStatus = "2";
				} catch (Exception e) {
					logger.error("FirFbrn 新增APS暫存檔異常：",e);
					errMsg.append("新增APS暫存檔異常：" + e.toString() + "；");
					dataqtyF++;
					dataStatus = "1";
				}
				// 更新批次明細檔及暫存檔
				FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
				firAgtrnBatchDtl.setDataStatus(dataStatus);
				firAgtrnBatchDtl.setOldpolicyno(oldpolicyno);
				firAgtrnBatchDtl.setDquakeStatus(dquakeStatus);
				firAgtrnBatchDtl.setDquakeNo(dquakeNo);
				//空值""無法format number
				firAgtrnBatchDtl.setOidFirPremcalcTmp(!"".equals(oidFirPremcalcTmp)?Long.parseLong(oidFirPremcalcTmp):null);
				firAgtrnBatchDtl.setFamtStatus(famtStatus);
				firAgtrnBatchDtl.setWsFirAmt(Long.parseLong(firAmt.toString()));
				firAgtrnBatchDtl.setQamtStatus(qamtStatus);
				firAgtrnBatchDtl.setWsQuakeAmt(Integer.parseInt(quakeAmt.toString()));
				firAgtrnBatchDtl.setAddrStatus(addrStatus);
				firAgtrnBatchDtl.setAddrDetail(addrDetail);
				firAgtrnBatchDtl.setCheckErrMsg(errMsg.toString());
				firAgtrnBatchDtl.setCheckWarnMsg(warnMsg.toString());
				fubonRenewalFileService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl, batchNo, userId, batchSeq);
			}
			// 執行成功
			returnData.put(OUTSTATUS, "Y");
			returnData.put("dataqtyS", String.valueOf(dataqtyS));
			returnData.put("dataqtyF", String.valueOf(dataqtyF));
			returnData.put("transStatus", "Y");
		}
		return returnData;
	}

	private String getStructure(String structure) {
		String text = "";
		if ("1".equals(structure)) {
			text = " – 特一等建築";
		}
		if ("2".equals(structure)) {
			text = " – 特二等建築";
		}
		if ("3".equals(structure)) {
			text = " – 頭等";
		}
		if ("5".equals(structure)) {
			text = " – 二等";
		}
		if ("6".equals(structure)) {
			text = " – 三等";
		}
		if ("7".equals(structure)) {
			text = " – 露天";
		}
		return text;
	}

	private void moveFile() throws IOException {
		String zipFilename = "FBINSS_10.TXT.ZIP";
		String txtFilename = "FBINSS_10.TXT";
		File zipSource = new File(ROOTDIRECTORY + zipFilename);
		File txtSource = new File(ROOTDIRECTORY + txtFilename);
		String destDir = ROOTDIRECTORY + new SimpleDateFormat("yyyyMM").format(new Date());
		File destFilePath = new File(destDir);
		if (!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		File dest = new File(destDir + File.separator + zipFilename);
		if (dest.exists()) {
			FileUtils.forceDelete(dest);
		}
		Files.copy(zipSource.toPath(), dest.toPath());
		FileUtils.forceDelete(zipSource);
		FileUtils.forceDelete(txtSource);
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String status, String errMsg, String programId) {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("prgId", programId);
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() == null) {
				return "無法取得FIR_BATCH_INFO資料，無法寄送MAIL";
			}
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
			String mailTo = firBatchInfo.getMailTo();
			String mailCc = firBatchInfo.getMailCc();

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if (status.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				params.clear();
				params.put("batchNo", batchNo);
				Result mainResult = firAgtrnBatchMainService.findFirAgtrnBatchMainByParams(params);

				params.clear();
				params.put("batchNo", batchNo);
				params.put("sortBy", "BATCH_SEQ");
				Result dtlResult = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
				if (mainResult.getResObject() == null) {
					tmpMsg.append("FIR_AGTRN_BATCH_MAIN批次主檔查無資料，請洽系統人員。");
				} else if (dtlResult.getResObject() == null) {
					tmpMsg.append("FIR_AGTRN_BATCH_DTL批次明細檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>檔案名稱</td>");
					sb.append("<td>檔案狀態</td>");
					sb.append("<td>原始資料筆數</td>");
					sb.append("<td>成功筆數</td>");
					sb.append("<td>失敗筆數</td>");
					sb.append("</tr>");
					List<FirAgtrnBatchMain> firAgtrnBatchMainList = (List<FirAgtrnBatchMain>) mainResult.getResObject();
					for (FirAgtrnBatchMain firAgtrnBatchMain : firAgtrnBatchMainList) {
						sb.append("<tr>");
						sb.append("<td>" + firAgtrnBatchMain.getFilename() + "</td>");
						String fileStatus = "";
						if (firAgtrnBatchMain.getFileStatus().equals("S")) {
							fileStatus = "正常";
						} else if (firAgtrnBatchMain.getFileStatus().equals("A")) {
							fileStatus = "新增暫存錯誤";
						} else {
							fileStatus = "檔案無資料";
						}
						sb.append("<td>" + fileStatus + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyT() + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyS() + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyF() + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");

					sb.append("<p>本次處理明細如下：</p>");
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>序號</td>");
					sb.append("<td>續保保單號</td>");
					sb.append("<td>資料狀態</td>");
					sb.append("<td>資料檢核異常訊息</td>");
					sb.append("<td>資料檢核提示訊息</td>");
					sb.append("</tr>");
					List<FirAgtrnBatchDtl> firAgtrnBatchDtlList = (List<FirAgtrnBatchDtl>) dtlResult.getResObject();
					String dataStatus = "";
					for (FirAgtrnBatchDtl firAgtrnBatchDtl : firAgtrnBatchDtlList) {
						sb.append("<tr>");
						sb.append("<td>" + firAgtrnBatchDtl.getBatchSeq() + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getOldpolicyno()) + "</td>");
						if (firAgtrnBatchDtl.getDataStatus().equals("0")) {
							dataStatus = "未處理";
						} else if (firAgtrnBatchDtl.getDataStatus().equals("1")) {
							dataStatus = "資料驗證失敗";
						} else {
							dataStatus = "寫入APS暫存檔成功";
						}
						sb.append("<td>" + dataStatus + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckErrMsg()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckWarnMsg()) + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}
			} else if (status.equals("N")) {
				sb.append("<p>執行狀態：無檔案 </p>");
			} else {// status = "F"
				sb.append("<p>執行狀態：失敗</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("FBRN sendEmail Exception", e);
		}
		return tmpMsg.toString();
	}

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		fubonRenewalFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}

	// 取系統日期+1個月(yyyyMM)
	private String getNextMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		return new SimpleDateFormat("yyyyMM").format(calendar.getTime());
	}
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	// 去除左邊的0
	private static String replaceLeftZero(String str) {
		while (str.substring(0, 1).equals("0")) {
			if (str.equals("0"))
				break;
			str = str.substring(1, str.length());
		}
		return str;
	}

	private String trimBlank(String str){
		if(null != str && str.trim().length()>0) {
			return str.trim().replaceAll("^[\\u3000|\\s]*", "").replaceAll("[\\u3000|\\s]*$", "");
		}
		return "";
	}

	private String transferDateFormat(String sourceDate, String sourceDateFormat, String transferFormat) {
		String returnValue = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(sourceDateFormat);
			formatter.setLenient(false);
			Date newDate = formatter.parse(sourceDate);
			formatter = new SimpleDateFormat(transferFormat);
			returnValue = formatter.format(newDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public PrpdAgreementService getPrpdAgreementService() {
		return prpdAgreementService;
	}

	public void setPrpdAgreementService(PrpdAgreementService prpdAgreementService) {
		this.prpdAgreementService = prpdAgreementService;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public FirAgtrnBatchMainService getFirAgtrnBatchMainService() {
		return firAgtrnBatchMainService;
	}

	public void setFirAgtrnBatchMainService(FirAgtrnBatchMainService firAgtrnBatchMainService) {
		this.firAgtrnBatchMainService = firAgtrnBatchMainService;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirAgtrnAs400DataService getFirAgtrnAs400DataService() {
		return firAgtrnAs400DataService;
	}

	public void setFirAgtrnAs400DataService(FirAgtrnAs400DataService firAgtrnAs400DataService) {
		this.firAgtrnAs400DataService = firAgtrnAs400DataService;
	}

	public FubonRenewalFileService getFubonRenewalFileService() {
		return fubonRenewalFileService;
	}

	public void setFubonRenewalFileService(FubonRenewalFileService fubonRenewalFileService) {
		this.fubonRenewalFileService = fubonRenewalFileService;
	}

	public FirAgtrnTmpFbService getFirAgtrnTmpFbService() {
		return firAgtrnTmpFbService;
	}

	public void setFirAgtrnTmpFbService(FirAgtrnTmpFbService firAgtrnTmpFbService) {
		this.firAgtrnTmpFbService = firAgtrnTmpFbService;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}

	private boolean checkArea(String area) {
		boolean check = true;
		if(new BigDecimal(replaceLeftZero(area)).compareTo(new BigDecimal(0)) <=0) {
			check = false;
		}
		if(area.indexOf(".") != -1 && area.substring(area.indexOf(".") + 1).length() > 2) {
			check = false;
		}
		return check;
	}
}
