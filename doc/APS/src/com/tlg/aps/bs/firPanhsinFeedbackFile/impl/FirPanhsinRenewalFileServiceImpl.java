package com.tlg.aps.bs.firPanhsinFeedbackFile.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPanhsinFeedbackFile.FirPanhsinRenewalFileService;
import com.tlg.aps.bs.firPanhsinFeedbackFile.ProcessPanhsinFileService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.aps.vo.VerifyIdVo;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirAgtrnTmpBop;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.FirAgtSalesMappingService;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirAgtrnTmpBopService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.PrpcinsuredService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.prpins.service.PrpyddagentService;
import com.tlg.sales.entity.PrpdAgreement;
import com.tlg.sales.service.PrpdAgreementService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirPanhsinRenewalFileServiceImpl implements FirPanhsinRenewalFileService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */

	private static final Logger logger = Logger.getLogger(FirPanhsinRenewalFileServiceImpl.class);
	private ConfigUtil configUtil;
	private ProcessPanhsinFileService processPanhsinFileService;
	private FirBatchInfoService firBatchInfoService;
	private FirAgtrnTmpBopService firAgtrnTmpBopService;
	private FirAgtSalesMappingService firAgtSalesMappingService;
	private PrpdNewCodeService prpdNewCodeService;
	private PrpdAgreementService prpdAgreementService;
	private RfrcodeService rfrcodeService;
	private PrpcmainService prpcmainService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
	private FirVerifyDatasService firVerifyDatasService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	
	private Map<Integer,String> fieldMap = new HashMap<>();
	private static final String ROOTDIRECTORY = "D:"+File.separator+"BOP_RNDATA"+File.separator;
	private static final String OUTMSG = "outMsg";
	private static final String OUTSTATUS = "outStatus";
	private static final String FILESTATUS = "fileStatus";
	
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start */
	private PrpcinsuredService prpcinsuredService;
	private PrpyddagentService prpyddagentService;
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end */
	
	@Override
	public Result runToReceiveData(String userId, Date excuteTime, String programId) throws Exception{
		//新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if(StringUtil.isSpace(userId)){
			sb.append("執行人員無內容值。");
			}
		if(excuteTime==null) {
			sb.append("轉檔時間無內容值。");
		}
		if(StringUtil.isSpace(programId)){
			sb.append("程式代碼無內容值。");
		}
		String batchNo = programId.substring(8,13)+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if(!StringUtil.isSpace(sb.toString())) {
			//tmp_status = "F"
			Result result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId,"F", sb.toString(),batchNo);
			if(result.getResObject()!=null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime,"F", sb.toString(), programId);
				if(!StringUtil.isSpace(mailMsg)){
					processPanhsinFileService.updateFirBatchLog("F",mailMsg, userId, firBatchLog);
				}
			} return this.getReturnResult("接收參數無值，結束排程");
			//結束排程
		}
		String status = "1";
		String msg="";
		Map<String,String> params = new HashMap<>();
		params.put("prgId",programId+"_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()!=null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
			if(firBatchInfo.getMailTo().equals("N")){
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
				}
			}
		result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId,status, msg, batchNo);
		if(status.equals("S")){
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if(result.getResObject()!=null){
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();
		    //取得檔案、原始資料暫存
			Map<String,String> returnData = new HashMap<>();
			returnData = temporaryDataStorage(batchNo, userId);				
			if("N".equals(returnData.get(OUTSTATUS))) {
				processPanhsinFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				updateFirBatchLog("F",returnData.get(OUTMSG),userId,batchLogOid);
				sendEmail(batchNo, excuteTime, returnData.get(OUTSTATUS), returnData.get(OUTMSG), programId);
				return this.getReturnResult("資料暫存執行失敗");
			}else if("0".equals(returnData.get(OUTSTATUS))){
				updateFirBatchLog("N",returnData.get(OUTMSG),userId,batchLogOid);
				sendEmail(batchNo, excuteTime, returnData.get(OUTSTATUS), returnData.get(OUTMSG), programId);
				return this.getReturnResult("本次沒有檔案要處理");
			}
			//資料暫存執行成功
			processPanhsinFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
			updateFirBatchLog("S",returnData.get(OUTMSG),userId,batchLogOid);
			returnData.clear();
			
			//資料檢核處理
			try {
				returnData = temporaryArchiveDataReview(batchNo, userId);
			}catch (Exception e) {
				logger.error("資料檢核失敗",e);
				/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 */
				e.printStackTrace();
				sendEmail(batchNo, excuteTime, "F", e.toString(), programId);
				updateFirBatchLog("F",e.toString(),userId,batchLogOid);
				returnData.put("transStatus", "E");
				returnData.put("remark", e.toString());
				processPanhsinFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				throw new SystemException("執行資料檢核失敗");
			}
			if("Y".equals(returnData.get(OUTSTATUS))){
				processPanhsinFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				sendEmail(batchNo, excuteTime, "S", returnData.get(OUTMSG), programId);
				updateFirBatchLog("S",returnData.get(OUTMSG),userId,batchLogOid);
				moveFile();
			}else {
				processPanhsinFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				updateFirBatchLog("F",returnData.get(OUTMSG),userId,batchLogOid);
			}
		}
		return this.getReturnResult("執行完成");
	}

	private Map<String,String> temporaryDataStorage(String batchNo, String userId) throws Exception {
		Map<String,String> returnData = new HashMap<>();
		if(StringUtil.isSpace(batchNo) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		}
		if(StringUtil.isSpace(userId) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}
		
		//執行時先在根目錄找是否有檔案，若無檔案，才在SFTP上抓檔案放在根目錄上，執行成功後才把檔案移到對應月份資料夾中
		File rootPath = new File(ROOTDIRECTORY);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}
		String filename = "台壽保火險續保檔.TXT";
		File file = new File(ROOTDIRECTORY+filename);
		//若根目錄中無檔案就連sftp抓檔案
		if(!file.exists()) {
			//取sftp檔案
			String strResult = getFileFromSftp(filename);
			//sftp無檔案
			if(StringUtil.isSpace(strResult)) {
				returnData.put(OUTSTATUS, "0");
				returnData.put(OUTMSG, "");
				return returnData;
			}
			if("fail".equals(strResult)) {
				returnData.put(OUTSTATUS, "0");
				returnData.put(OUTMSG, "連線sftp異常");
				return returnData;
			}			
		}
		try {
			// 取得檔案後新增FIR_AGTRN_BATCH_MAIN住火保經代續保轉核心批次主檔
			processPanhsinFileService.insertFirAgtrnBatchMain(batchNo, userId, filename);
		} catch (Exception e) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "FIR_AGTRN_BATCH_MAIN住火保經代續保轉核心批次主檔失敗");
			logger.error("住火保經代續保轉核心批次主檔失敗:",e);
			return returnData;
		}
		// 新增成功 處理TXT檔
		List<String> fileDataList = readFile(ROOTDIRECTORY+filename);
		int countData = fileDataList.size();
		if(countData == 0) {
			//檔案內無資料
			returnData.put("dataqtyT", "0");
			returnData.put(FILESTATUS, "Z");
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "檔案無資料");
			return returnData;
		}
		try {
			processPanhsinFileService.insertFirAgtrnTmpBopList(batchNo, "TMP_FILENAME", userId, fileDataList);
			returnData.put(OUTSTATUS, "Y");
			returnData.put(OUTMSG, "");
			returnData.put(FILESTATUS, "S");
			returnData.put("dataqtyT", String.valueOf(countData));
		} catch (Exception e) {
			//新增暫存錯誤 
			returnData.put(FILESTATUS, "A");
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "新增RAWDATA至FIR_AGTRN_TMP_BOP失敗。");
			logger.error("新增RAWDATA至FIR_AGTRN_TMP_BOP失敗。",e);
			return returnData;
		}
		return returnData;
	}
	
	//取得sftp檔案
	private String getFileFromSftp(String filename) {
		String strResult = "";
		String sftpHost = configUtil.getString("panhsinFTP");
		String sftpUser = configUtil.getString("panhsinFtpUserGet");
		String sftpPwd = configUtil.getString("panhsinFptPwdGet");
		int sftpPort = 22;
		String remoteDir = configUtil.getString("panhsinRemotePath");
		try {
			SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
			List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir);
			List<String> downloadFileList = new ArrayList<>();
			for(int i=0;i<fileList.size();i++) {
				if(fileList.get(i).equals(filename)) {
					downloadFileList.add(fileList.get(i));
				}
			}
			if(!downloadFileList.isEmpty()) {
				strResult = sftpUtil.getFileFromSftp(remoteDir, ROOTDIRECTORY, downloadFileList);
			}
		}catch (Exception e) {
			logger.error("PanhsinRenewalFile connect sftp fail:",e);
			strResult = "fail";
			return strResult;			
		}
		return strResult;
	}
	
	private List<String> readFile(String filepath) throws IOException {
		List<String> fileDataList = new ArrayList<>();
		File file = new File(filepath);
		if(file.length()==0) {
			return new ArrayList<>();
		}
		BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(filepath),"big5"));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.trim().length() > 0) {
				fileDataList.add(line);
			}
		}
		br.close();
		return fileDataList;
	}
	
	//資料檢核處理 需檢核所有參數必填 
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	@SuppressWarnings("unchecked")
	private Map<String,String> temporaryArchiveDataReview(String batchNo,String userId) throws SystemException, Exception {
		Map<String,String> returnData = new HashMap<>();
		if(StringUtil.isSpace(batchNo) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		}
		if(StringUtil.isSpace(userId) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}
		
		Map<String,String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("pStatus", "N");
		params.put("orderByBatchSeq","Y");
		Result result = firAgtrnTmpBopService.findFirAgtrnTmpBopByParams(params);
		if(result.getResObject()!=null) {
			List<FirAgtrnTmpBop> tmpResult = (List<FirAgtrnTmpBop>) result.getResObject();
			for(int i=0;i<tmpResult.size();i++) {
				try {
					insertFirAgtrnBatchDtl(batchNo, tmpResult.get(i).getBatchSeq(), userId);					
				}catch (Exception e){
					returnData.put(OUTSTATUS, "N");
					returnData.put("transStatus", "E");
					returnData.put(OUTMSG, "新增FIR_AGTRN_BATCH_DTL批次明細檔失敗。");
					logger.error("新增FIR_AGTRN_BATCH_DTL批次明細檔失敗。", e);
					return returnData;
				}
			}
			
			Pattern datePattern = Pattern.compile("^([0-9]\\d{3}(0+[1-9]|1[012])(0+[1-9]|[12][0-9]|3[01]))$");
			
			//以每個欄位長度切割
			String cutNos = "|7|11|18|25|36|96|107|167|169|189|197|198|206|209|212|213|217|229|241|255|258|"
					+ "318|378|394|408|410|414|429|439|442|458|462|477|498|";
			int dataqtyS=0;
			int dataqtyF=0;
			String dataStatus = "0";
			for(int i=0;i<tmpResult.size();i++) {
				FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
				FirAgtTocoreInsured firAgtTocoreInsured = new FirAgtTocoreInsured();
				Map<String,String> tmpdatas = new HashMap<>();
				int batchSeq = i+1;
				
				List<String> rawDataList = getRawDataTexts(tmpResult.get(i).getRawdata(),cutNos);
				StringBuilder errMsg = new StringBuilder();
				StringBuilder warnMsg = new StringBuilder();
				List<Integer> unfilledList = new ArrayList<>();
				for(int j=0;j<rawDataList.size();j++) {
					//必填檢核
					if((j==1||j==2||j==4||j==5||j==6||j==7||j==10||j==20||j==21||j==22||j==32)
							&& StringUtil.isSpace(rawDataList.get(j).trim())) {
						errMsg.append(getFieldMap().get(j)+"-未輸入;");
						unfilledList.add(j);
					}
				}
				
				boolean iscore = false;//是否存在新核心
				/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 */
				FirPahsinRenewalVo eCore = new FirPahsinRenewalVo();
				
				//保單號碼檢核
				String policyNo = rawDataList.get(9).trim();
				String isAutoRenew = "N";
				String clauseSendtype = "2";
				if(unfilledList.indexOf(9)==-1) {
					policyNo = policyNo.replaceAll("\\s|[\\pP\\p{Punct}]|[\\u4e00-\\u9fa5]", "");
					if(policyNo.length()<12 || policyNo.length()>14) {
						errMsg.append("保單號碼-長度異常；");
					}else if(policyNo.length()==12){
						policyNo = "18" + policyNo;
					}else if(policyNo.length()==13) {
						policyNo = "1" + policyNo;
					}
					
					if(policyNo.length()==14) {
						params.clear();
						params.put("policyno", policyNo);
						result = prpcmainService.findForPanhsinRenewalCoreData(params);
						if(result.getResObject() != null) {
							eCore = (FirPahsinRenewalVo) result.getResObject();
							iscore = true;
							String othflag = eCore.getOthflag();
							if(othflag.substring(2,3).equals("1")||othflag.substring(3,4).equals("1")||othflag.substring(15,16).equals("1")) {
								errMsg.append("續保單號碼-已退保或註銷；");
							}
							
							//取第二位抵押權人
							String mortgageepcode2 = eCore.getMortgageepcode2();
							Integer serialno2 = eCore.getSerialno2();
							if(!StringUtil.isSpace(mortgageepcode2) && null != serialno2) {
								firAgtTocoreMain.setSerialno2(eCore.getSerialno2());
								firAgtTocoreMain.setMortgageepcode2(eCore.getMortgageepcode2());
								warnMsg.append("前一年度保單資料有多筆抵押權人，請於資料轉入核心時處理；");
							}
							//取得業務員
							params.clear();
							params.put("businesssource", "I99065");
							params.put("verifyremark", "1");
							params.put("agentid", eCore.getHandleridentifynumber());
							result = prpyddagentService.findPrpyddagentByParams(params);
							if(result.getResObject() == null) {
								warnMsg.append("業務員登錄證號不存在或無效，請與通路部聯繫確認；");
							}else {
								firAgtTocoreMain.setHandleridentifynumber(eCore.getHandleridentifynumber());
							}
							
							//是否可續保
							isAutoRenew = StringUtil.isSpace(eCore.getIsAutoRenew())?"N":eCore.getIsAutoRenew();
							
							//若為自動續約才帶入前一年度是否為電子保單、條款交付方式
							if("Y".equals(isAutoRenew)) {
								//是否為電子保單
								firAgtTocoreMain.setEpolicy(eCore.getEpolicy());
								//條款交付方式
								clauseSendtype = eCore.getClauseSendtype();
							}
							
							//是否有批改
							if("Y".equals(eCore.getIsEndorse())) {
								warnMsg.append("前一年度保單有批改紀錄；");
							}
							
							//取前期火險、地震保額保費
							firAgtTocoreMain.setAmountFLast(eCore.getAmtF() == null ? null:(Long.parseLong(eCore.getAmtF())));
							firAgtTocoreMain.setAmountQLast(eCore.getAmtQ() == null ? null:(Long.parseLong(eCore.getAmtQ())));
							firAgtTocoreMain.setPremiumFLast(eCore.getPremF() == null ? null:(Long.parseLong(eCore.getPremF())));
							firAgtTocoreMain.setPremiumQLast(eCore.getPremQ() == null ? null:(Long.parseLong(eCore.getPremQ())));
						}else {
							errMsg.append("續保單號碼-不存在於新核心系統；");
						}
					}
				}
				firAgtTocoreMain.setOldpolicyno(policyNo);
				firAgtTocoreMain.setIsAutoRenew(isAutoRenew);
				firAgtTocoreMain.setClauseSendtype(clauseSendtype);
				//保單號碼檢核 end
				
				//日期檢核
				if(StringUtil.isSpace(rawDataList.get(0).trim()) 
						&& !datePattern.matcher(rawDataList.get(0).trim()).matches()) {
						errMsg.append("資料日期-日期格式錯誤；");
				}
				
				//分行代號檢核
				String extracomcode = rawDataList.get(1).trim();
				if(unfilledList.indexOf(1)==-1) {
					params.clear();
					params.put("branchNo", extracomcode);
					result = firAgtSalesMappingService.findFirAgtSalesMappingByBranchNo(params);
					if(result.getResObject()!=null) {
						FirAgtSalesMapping firAgtSalesMapping = (FirAgtSalesMapping) result.getResObject();
						if(!firAgtSalesMapping.getValidstatus().equals("1")) {
							errMsg.append("分行代號-對應服務人員已失效；");
						}else {
							firAgtTocoreMain.setHandler1code(firAgtSalesMapping.getHandler1code());
							firAgtTocoreMain.setComcode(firAgtSalesMapping.getComcode());
							firAgtTocoreMain.setExtracomname(firAgtSalesMapping.getBranchName());
						}
					}else {
						errMsg.append("分行代號-查無分行對應服務人員；");
					}
					firAgtTocoreMain.setExtracomcode(extracomcode);
				}
				//分行代號檢核 end
				
				//板信機構代號檢核
				String bopNo = rawDataList.get(2).trim();
				if(unfilledList.indexOf(2)==-1 
					&& (bopNo.length()!=7||!StringUtil.isNum(bopNo)||!bopNo.substring(0,3).equals("118"))) {
					warnMsg.append("板信機構代號-銀行或分行代號可能異常；");
				}
				//板信機構代號檢核end
				
				//借款人ID檢核
				String borrowerId = rawDataList.get(4).trim();
				if(unfilledList.indexOf(4)==-1) {
					//呼叫webService檢核
					Map<String,String> verifyMap = verifyID(borrowerId);
					if(!StringUtil.isSpace(verifyMap.get("errMsg"))) {
						errMsg.append("借款人ID-"+verifyMap.get("errMsg"));
					}
					if(!StringUtil.isSpace(verifyMap.get("insuredNature"))) {
						tmpdatas.put("insuredNature2", verifyMap.get("insuredNature"));
					}
					if(!StringUtil.isSpace(verifyMap.get("idType"))) {
						tmpdatas.put("idType2",verifyMap.get("idType"));
					}
					tmpdatas.put("borrowerId", borrowerId);
					if(iscore) {
						params.clear();
						params.put("policyno", policyNo);
						params.put("insuredflag", "2");
						result = prpcinsuredService.findForPanhsinCoreInsured(params);
						if(result.getResObject()!=null) {
							List<FirPahsinRenewalVo> applyList = (List<FirPahsinRenewalVo>) result.getResObject();
							if(applyList.size()>1) {
								warnMsg.append("前一年度保單資料有多筆要保人，請於資料轉入核心時處理；");
							}
							//比對核心要保人資料
							boolean isEqual = false;
							for(FirPahsinRenewalVo applyData : applyList) {
								if(borrowerId.equals(applyData.getIdentifynumber())) {
									tmpdatas.put("birthday2", formatDate(applyData.getBirthday(),"yyyy/MM/dd"));
									tmpdatas.put("postcode2", applyData.getPostcode().length()>3?applyData.getPostcode().substring(0,3):applyData.getPostcode());
									isEqual = true;
									//若比對到ID相同，比對姓名是否一致
									if(!StringUtil.isSpace(applyData.getInsuredname()) &&
											!applyData.getInsuredname().equals(rawDataList.get(5).trim())) {
										warnMsg.append("板信轉入借款人(要保人)姓名("+applyData.getInsuredname()+")與核心資料不一致；");
									}
								}
							}
							if(!isEqual) warnMsg.append("板信轉入借款人ID(要保人)與核心資料不一致；");
						}
					}
				}
				//借款人ID檢核 end
				//借款人姓名
				if(unfilledList.indexOf(5)==-1) {
					tmpdatas.put("borrowerName", rawDataList.get(5).trim());
				}
				//提供人ID檢核
				String providerId = rawDataList.get(6).trim();
				if(unfilledList.indexOf(6)==-1) {
					//呼叫webService檢核
					Map<String,String> verifyMap =  verifyID(providerId);
					if(!StringUtil.isSpace(verifyMap.get("errMsg"))) {
						errMsg.append("提供人ID-"+verifyMap.get("errMsg"));
					}
					if(!StringUtil.isSpace(verifyMap.get("insuredNature"))) {
						tmpdatas.put("insuredNature1", verifyMap.get("insuredNature"));
					}
					if(!StringUtil.isSpace(verifyMap.get("idType"))) {
						tmpdatas.put("idType1",verifyMap.get("idType"));
					}
					tmpdatas.put("providerId", providerId);
					if(iscore) {
						params.clear();
						params.put("policyno", policyNo);
						params.put("insuredflag", "1");
						result = prpcinsuredService.findForPanhsinCoreInsured(params);
						if(result.getResObject()!=null) {
							List<FirPahsinRenewalVo> insuredList = (List<FirPahsinRenewalVo>) result.getResObject();
							if(insuredList.size()>1) {
								warnMsg.append("前一年度保單資料有多筆被保險人，請於資料轉入核心時處理；");
							}
							//比對核心被保險人資料
							boolean isEqual = false;
							for(FirPahsinRenewalVo insuredData : insuredList) {
								if(providerId.equals(insuredData.getIdentifynumber())) {
									tmpdatas.put("birthday1", formatDate(insuredData.getBirthday(),"yyyy/MM/dd"));
									tmpdatas.put("postcode1", insuredData.getPostcode().length()>3?insuredData.getPostcode().substring(0,3):insuredData.getPostcode());
									isEqual = true;
									//若比對到ID相同，比對姓名是否一致
									if(!StringUtil.isSpace(insuredData.getInsuredname()) &&
											!insuredData.getInsuredname().equals(rawDataList.get(7).trim())) {
										warnMsg.append("板信轉入提供人(被保險人)姓名("+insuredData.getInsuredname()+")與核心資料不一致；");
									}
								}
							}
							if(!isEqual) warnMsg.append("板信轉入提供人ID(被保險人)與核心資料不一致；");
						}
					}
				}
				//提供人ID檢核 end
				
				//提供人姓名
				if(unfilledList.indexOf(7)==-1) {
					tmpdatas.put("providerName", rawDataList.get(7).trim());
				}
				
				//保單到期日
				String startDate = rawDataList.get(10).trim();
				//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 
				SimpleDateFormat startdateFormat = new SimpleDateFormat("yyyyMMdd");
				if (StringUtil.isSpace(startDate) && !datePattern.matcher(startDate).matches()) {
					errMsg.append("保單到期日-日期格式錯誤；");
				} else {
					firAgtTocoreMain.setStartdate(startdateFormat.parse(startDate));
					if (iscore) {
						String coreEnddate = startdateFormat.format(eCore.getEnddate());
						if (!startDate.equals(coreEnddate)) {
							warnMsg.append("板信轉入保單到期日與核心(" + coreEnddate + ")資料不一致；");
						}
					}
				}
				
				//建築結構代號檢核
				String struct = rawDataList.get(11).trim();
				String[] structArr = {"1","2","3","5","6","7"};
				if(StringUtil.isSpace(struct) || !Arrays.asList(structArr).contains(struct)) {
					errMsg.append("建築結構代號-未輸入或內容值異常；");
				}else if(iscore && !struct.equals(eCore.getStructure())) {
					warnMsg.append("板信轉入建築結構代號與核心(" + eCore.getStructure() + ")資料不一致；");
				}
				//建築結構代號檢核 end 
				
				//坪數檢核
				String buildarea = rawDataList.get(12).trim();
				if(!StringUtil.isSpace(buildarea)) {
					buildarea = replaceLeftZero(buildarea);
					if(!StringUtil.isNum(buildarea) || countDecimal(buildarea)>2 
							||new BigDecimal(buildarea).compareTo(BigDecimal.ZERO) < 0) {
						errMsg.append("坪數-未輸入或內容值異常；");
					}else if(iscore && !buildarea.equals(StringUtil.nullToSpace(eCore.getBuildarea()))) {
						warnMsg.append("板信轉入坪數與核心(" + eCore.getBuildarea() + ")資料不一致；");
					}
				}else if(iscore && null != eCore.getBuildarea()) {
					buildarea = eCore.getBuildarea();
					warnMsg.append("板信未提供坪數資料，使用前一年保單資料;");
				}
				firAgtTocoreMain.setBuildarea(buildarea);
				//坪數檢核 end
				
				//建築物年份檢核
				String buildyears = rawDataList.get(13).trim();
				if(StringUtil.isSpace(buildyears) || !StringUtil.isNum(buildyears) 
						|| Integer.parseInt(buildyears)<1 || Integer.parseInt(buildyears)>200) {
					errMsg.append("建築物年份-未輸入或內容值異常(應介於1~200年)；");
				}else if(iscore && !buildyears.equals(eCore.getBuildyears() == null?"" : eCore.getBuildyears().toString())) {
					warnMsg.append("板信轉入建築物年份與核心(" + eCore.getBuildyears() + ")資料不一致；");
				}
				if(StringUtil.isSpace(buildyears) && iscore) {
					buildyears = eCore.getBuildyears();
					warnMsg.append("板信未提供建築物年份資料，使用前一年保單資料；");
				}
				firAgtTocoreMain.setBuildyears(buildyears);
				//建築物年份檢核 end
				
				//總樓層數檢核
				String sumfloors = rawDataList.get(14).trim();
				if(!StringUtil.isSpace(sumfloors)) {
					sumfloors = replaceLeftZero(sumfloors);
					if(!StringUtil.isNumeric(sumfloors) || Integer.parseInt(sumfloors)==0) {
						errMsg.append("總樓層數-未輸入或內容值異常；");
					}else if(iscore && !sumfloors.equals(eCore.getSumfloors())){
						warnMsg.append("板信轉入總樓層數與核心(" + eCore.getSumfloors() + ")資料不一致；");
					}
				}else {
					errMsg.append("總樓層數-未輸入或內容值異常；");
					if(iscore) {
						sumfloors = eCore.getSumfloors();
						warnMsg.append("板信未提供總樓層數資料，使用前一年保單資料；");
					}
				}
				firAgtTocoreMain.setSumfloors(sumfloors);
				//總樓層數檢核end
				
				//保險金額檢核
				String amountF = rawDataList.get(17).trim();
				if(!StringUtil.isSpace(amountF)) {
					amountF = replaceLeftZero(amountF);
					if(Integer.parseInt(amountF)<0) {
						errMsg.append("保險金額-未輸入或內容值異常；");
					}else if(iscore && !amountF.equals(eCore.getAmtF())) {
						warnMsg.append("板信轉入保險金額與核心(" + eCore.getAmtF() + ")資料不一致；");
					}
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  start*/
					firAgtTocoreMain.setAmountFAgt(amountF);
					//依保險生效日、火險保額取得重算後保額
					amountF = firVerifyDatasService.recalAmount(null == eCore.getAmtF()?null:eCore.getAmtF(), startdateFormat.parse(startDate));
					firAgtTocoreMain.setAmountF(amountF);
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  end*/
				}else {
					errMsg.append("保險金額-未輸入或內容值異常；");
				}
				//保險金額檢核end
				
				//總保險費檢核
				String premT = rawDataList.get(18).trim();
				if(!StringUtil.isSpace(premT)) {
					premT = replaceLeftZero(premT);
					if(Integer.parseInt(premT)<0) {
						errMsg.append("總保險費-未輸入或內容值異常；");
					}
					firAgtTocoreMain.setPremiumT(premT);
				}else {
					errMsg.append("總保險費-未輸入或內容值異常；");
				}
				//總保險費檢核end
				
				//郵遞區號檢核
				String addressname = "";
				String addresscode = rawDataList.get(20).trim();
				if(unfilledList.indexOf(20)==-1) {
					params.clear();
					params.put("codetype", "PostAddress");
					params.put("codecode", addresscode);
					result = prpdNewCodeService.findPrpdNewCodeByParams(params);
					if(result.getResObject()!=null) {
						List<PrpdNewCode> prpdNewCode = (List<PrpdNewCode>) result.getResObject();
						addressname = prpdNewCode.get(0).getCodecname();
						firAgtTocoreMain.setAddressname(addressname);
						
						if(iscore && !addresscode.equals(eCore.getAddresscode())) {
							warnMsg.append("板信轉入標的物郵遞區號與核心("+ eCore.getAddresscode() +")不一致；");
						}
						
					}else {
						errMsg.append("標的物郵遞區號-不存在核心設定檔內；");
					}
					firAgtTocoreMain.setAddresscode(addresscode);
				}
				//郵遞區號檢核 end
				
				//標的物地址檢核
				String addressdetailinfo = rawDataList.get(21).trim();
				if(unfilledList.indexOf(21)==-1 && !addressdetailinfo.contains("號")) {
					errMsg.append("標的物地址-最少要有「號」這個字；");
				}else if(iscore && !addressdetailinfo.equals(eCore.getAddressdetailinfo())) {
					warnMsg.append("板信轉入標的物地址與核心("+ eCore.getAddressdetailinfo() +")不一致；");
				}
				firAgtTocoreMain.setAddressdetailinfo(addressdetailinfo);
				//標的物地址檢核 end
				
				//通訊地址
				if(unfilledList.indexOf(22)==-1) {
					firAgtTocoreInsured.setPostaddress(rawDataList.get(22).trim());//T_BOP.RAWDATA.23通訊地址
				}
				
				//手機號碼檢核
				String mobile = rawDataList.get(28).trim();
				if(!StringUtil.isSpace(mobile) && mobile.length()!=10) {
					errMsg.append("行動電話-格式不正確；");
				}
				if(!StringUtil.isSpace(mobile) && !mobile.substring(0,2).equals("09")) {
					errMsg.append("行動電話-非09開頭；");
				}
				firAgtTocoreInsured.setMobile(mobile);
				//手機號碼檢核 end
				
				//受理編號檢核
				String orderseq = rawDataList.get(32).trim();
				if(unfilledList.indexOf(32)==-1 && 
						(orderseq.length()!=15 || !orderseq.substring(0,3).equals("118"))) {
					errMsg.append("受理編號-長度需為15碼或前3碼公司碼需為118；");
				}else {
					firAgtTocoreMain.setOrderseq(orderseq);
				}
				//受理編號檢核end
				
				unfilledList.clear();
				
				//商業邏輯檢核、佣金率處理
				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
				params.clear();
				params.put("businesssourcecode", "I99065");
				result = prpdAgreementService.findPrpdAgreementJoinDetail(params);
				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/
				if(result.getResObject()!=null) {
					List<PrpdAgreement> resultList = (List<PrpdAgreement>) result.getResObject();
					if(resultList.size()==2) {
						for(int j=0;j<resultList.size();j++) {
							if(resultList.get(j).getKindcode().equals("FR2")) {
								String commrateQ = resultList.get(j).getTopCommission();
								firAgtTocoreMain.setCommrateQ(new BigDecimal(commrateQ));
							}else {
								String commrateF = resultList.get(j).getTopCommission();
								firAgtTocoreMain.setCommrateF(new BigDecimal(commrateF));
							}
						}
					}else {
						errMsg.append("無法取得核心系統佣金率或佣金率設定異常；");
					}
				}else {
					errMsg.append("無法取得核心系統佣金率或佣金率設定異常；");
				}
				
				firAgtTocoreMain.setCreditnumber1(rawDataList.get(24).trim());//T_BOP.RAWDATA.25授信帳號
				firAgtTocoreMain.setCollateralnumber1(rawDataList.get(30).trim());//T_BOP.RAWDATA.31擔保品號碼
				firAgtTocoreMain.setLoanaccount1(rawDataList.get(19).trim());//T_BOP.RAWDATA.20代扣繳帳號
				firAgtTocoreMain.setTemp1(bopNo);//03板信機構代號
				firAgtTocoreMain.setTemp2(rawDataList.get(3).trim());//04他行庫機構代號
				firAgtTocoreMain.setTemp3(rawDataList.get(8).trim());//09保險公司代碼
				firAgtTocoreMain.setTemp4(rawDataList.get(23).trim());//24額度號碼
				firAgtTocoreMain.setTemp5(rawDataList.get(25).trim());//26戶況
				firAgtTocoreMain.setTemp6(rawDataList.get(26).trim());//27放款產品子號
				firAgtTocoreMain.setTemp7(rawDataList.get(29).trim());//30系統別
				firAgtTocoreMain.setTemp8(rawDataList.get(31).trim());//32保單序號
				firAgtTocoreInsured.setPhonenumber(rawDataList.get(27).trim());//T_BOP.RAWDATA.28聯絡電話
				
				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
				//複保險查詢檢核
				FirEqFundQueryVo firEqFundQueryVo = new FirEqFundQueryVo();
				String endDate = "";
				String transStartDate = "";
				if(!StringUtil.isSpace(startDate) 
						&& datePattern.matcher(startDate).matches()) {
					transStartDate = transferDateFormat(startDate,"yyyyMMdd", "yyyy/MM/dd");
					endDate = String.valueOf(Integer.parseInt(transStartDate.substring(0, 4))+1)+transStartDate.substring(4,10);
				}
				firEqFundQueryVo.setStartDate(transStartDate);
				firEqFundQueryVo.setEndDate(endDate);
 				firEqFundQueryVo.setPostcode(addresscode);
				firEqFundQueryVo.setAddress(addressdetailinfo);
				firEqFundQueryVo.setSourceType("BOP_RN");
				firEqFundQueryVo.setSourceUserid(userId);
				FirVerifyVo firVerifyVo = firVerifyDatasService.queryDoubleInsVerify(firEqFundQueryVo);
				String dquakeStatus = firVerifyVo.getDquakeStatus();
				String dquakeNo = firVerifyVo.getDquakeNo();
				if(!"".equals(firVerifyVo.getWarnMsg())) {
					warnMsg.append("檢核-"+firVerifyVo.getWarnMsg());
				}
				//複保險查詢檢核 end
				
				//若存在核心資料，則取得核心資料做相關商業邏輯檢核，包括保額、保費、建築等級及稽核議題檢核等
				String oidFirPremcalcTmp = "";
				String famtStatus = "";
				if(iscore) {
					BigDecimal eAmtQ = new BigDecimal(eCore.getAmtQ());
					BigDecimal ePremF = new BigDecimal(eCore.getPremF());
					BigDecimal ePremQ = new BigDecimal(eCore.getPremQ());
					String wallmaterial = eCore.getWallmaterial();
					String roofmaterial = eCore.getRoofmaterial();
					firAgtTocoreMain.setWallmaterial(wallmaterial);
					firAgtTocoreMain.setRoofmaterial(roofmaterial);
					
					//保額、保費處理start
					BigDecimal firAmt = new BigDecimal(0);
					BigDecimal quakeAmt = new BigDecimal(0);
					BigDecimal maxAmt = new BigDecimal(0);
					BigDecimal premiumT = new BigDecimal(0);//WS計算總保費
					BigDecimal premiumF = new BigDecimal(0);//WS計算火險保費
					BigDecimal premiumQ = new BigDecimal(0);//WS計算地震險保費
					
					eAmtQ = eAmtQ.multiply(BigDecimal.valueOf(0.0001)).setScale(0, RoundingMode.CEILING).multiply(new BigDecimal(10000));
					FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
					firWsParamVo.setSourceType("BOPRN");
					firWsParamVo.setSourceUser(userId);
					firWsParamVo.setCalcType("1");
					firWsParamVo.setCalcDate(startDate);
					firWsParamVo.setChannelType("20");
					firWsParamVo.setPostcode(addresscode);
					firWsParamVo.setWallno(wallmaterial);
					firWsParamVo.setRoofno(roofmaterial);
					firWsParamVo.setSumfloors(sumfloors);
					firWsParamVo.setBuildarea(buildarea);
					firWsParamVo.setDecorFee("0");
					try {
						FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
						if ("Y".equals(firPremcalcTmp.getReturnType())) {
							firAmt = firPremcalcTmp.getFsAmt();//火險建議保額
							maxAmt = firPremcalcTmp.getFsMaxAmt();//火險上限保額
							quakeAmt = firPremcalcTmp.getEqAmt();//地震險建議保額
							firAgtTocoreMain.setAmountQ(quakeAmt.toString());

							// 判斷火險保額是否足額
							if (!StringUtil.isSpace(amountF) && new BigDecimal(amountF).compareTo(maxAmt) > 0) {
								famtStatus = "3";
								warnMsg.append("檢核-火險超額" + amountF + "(上限保額:" + maxAmt + ")；");
							} else if (!StringUtil.isSpace(amountF) && new BigDecimal(amountF).compareTo(firAmt) < 0) {
								famtStatus = "2";
								warnMsg.append("火險不足額" + amountF + "(建議保額：" + firAmt + ")；");
							} else {
								famtStatus = "1";// 足額
							}
						} else {
							errMsg.append("檢核-保額計算WS異常(" + firPremcalcTmp.getOid() + "):"
									+ firPremcalcTmp.getReturnMsg() + "；");
						}
						oidFirPremcalcTmp = firPremcalcTmp.getOid().toString();
						
					} catch (Exception e) {
						logger.error("保額計算失敗", e);
						warnMsg.append("檢核-保額計算WS無回應；");
					}
					//保額檢核end
					
					//計算保費 start
					FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
					firPremWsParamVo.setSourceType("BOPRN");
					firPremWsParamVo.setSourceUser(userId);
					firPremWsParamVo.setCalcType("2");
					firPremWsParamVo.setCalcDate(startDate);
					firPremWsParamVo.setChannelType("20");

					ArrayList<FirInsPremVo> firInsPremVoList = new ArrayList<>();
					if (new BigDecimal(amountF).compareTo(new BigDecimal(0)) > 0) {
						FirInsPremVo firInsPremVoF = new FirInsPremVo();
						firInsPremVoF.setRiskcode("F02");
						firInsPremVoF.setKindcode("FR3");
						firInsPremVoF.setParaType("1");
						firInsPremVoF.setPara01(amountF);
						firInsPremVoF.setPara02(wallmaterial);
						firInsPremVoF.setPara03(roofmaterial);
						firInsPremVoF.setPara04(sumfloors);
						firInsPremVoF.setPara05("N");
						firInsPremVoList.add(firInsPremVoF);
					}

					if (quakeAmt.compareTo(new BigDecimal(0)) > 0) {
						FirInsPremVo firInsPremVoQ = new FirInsPremVo();
						firInsPremVoQ.setRiskcode("F02");
						firInsPremVoQ.setKindcode("FR2");
						firInsPremVoQ.setPara01(quakeAmt.toString());
						firInsPremVoList.add(firInsPremVoQ);
					}
					firPremWsParamVo.setInsPremList(firInsPremVoList);

					try {
						FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);

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
							premiumF = fr3Dtl.getPremium();
							premiumQ = fr2Dtl.getPremium();
							firAgtTocoreMain.setPremiumF(premiumF.toString());
							firAgtTocoreMain.setPremiumQ(premiumQ.toString());

							//比對核心保費、轉檔保費是否一致
							if (premiumT.compareTo(new BigDecimal(premT)) != 0) {
								warnMsg.append("WS計算總保費與板信轉檔總保費不符(" + premT + ")；");
							}

							if (premiumF.compareTo(ePremF) != 0) {
								warnMsg.append("火險保費計算結果與核心(" + ePremF + ")資料不一致；");
							}

							if (premiumQ.compareTo(ePremQ) != 0) {
								warnMsg.append("地震險保費計算結果與核心(" + ePremF + ")資料不一致；");
							}
							
							//取得高樓加費及建築等級，並與板信、核心資料比對
							if(!firPremcalcTmp.getFireStructure().equals(struct)) {
								warnMsg.append("WS計算建築等級結果與板信("+ struct + ")資料不一致 ；");
							}
							if(!firPremcalcTmp.getFireStructure().equals(eCore.getStructure())) {
								warnMsg.append("WS計算建築等級結果與核心("+ eCore.getStructure() + ")資料不一致 ；");
							}
							//取WS回傳的建築等級做後續檢核處理
							struct = firPremcalcTmp.getFireStructure();
							firAgtTocoreMain.setHighrisefee(firPremcalcTmp.getFireHigh()); 
						} else {
							errMsg.append("檢核-保費計算WS計算發生錯誤(" + firPremcalcTmp.getOid() + firPremcalcTmp.getReturnMsg() + "；");
						}

					} catch (Exception e) {
						logger.error("保費計算WS失敗", e);
						errMsg.append("檢核-保費計算WS無回應，未計算保費；");
					}
					//保額、保費處理 end
					
					//建築等級處理 start
					if(!StringUtil.isSpace(wallmaterial) && !StringUtil.isSpace(roofmaterial) 
							&& !StringUtil.isSpace(startDate) && !StringUtil.isSpace(sumfloors)) {
						Map<String,String> structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial, startDate, Integer.parseInt(sumfloors));
						if(!structMap.isEmpty()) {
							String wallname = firVerifyDatasService.findPrpdNewCode("WallMaterial", wallmaterial);
							if(StringUtil.isSpace(wallname)) {
								errMsg.append("建築等級說明-外牆("+wallmaterial+")查無名稱；");
							}
							String roofname = firVerifyDatasService.findPrpdNewCode("RoofMaterial", roofmaterial);
							if(StringUtil.isSpace(roofname)) {
								errMsg.append("建築等級說明-屋頂("+roofmaterial+")查無名稱；");
							}
							firAgtTocoreMain.setStructureText(wallname + roofname + sumfloors + "層樓"+ structMap.get("structureText"));
						}
					}else {
						warnMsg.append("其他-因資料不齊全未進行建築等級說明文字處理；");
					}
					//建築等級處理 end
					
					//稽核議題檢核 start
					if (!StringUtil.isSpace(addresscode) && !StringUtil.isSpace(addressdetailinfo)
							&& !StringUtil.isSpace(struct) && !StringUtil.isSpace(sumfloors)
							&& !StringUtil.isSpace(wallmaterial) && !StringUtil.isSpace(roofmaterial)) {
						StringBuilder ruleMsg = new StringBuilder();
						FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
						firAddressRuleObj.setRiskcode("F02");
						firAddressRuleObj.setFuncType("P");
						firAddressRuleObj.setRulePrefix("FIR");
						firAddressRuleObj.setPostcode(addresscode);// 郵遞區號三碼
						firAddressRuleObj.setAddress(addressdetailinfo);
						firAddressRuleObj.setAddrStructure(struct);
						firAddressRuleObj.setAddrSumfloors(sumfloors);
						firAddressRuleObj.setAddrWall(wallmaterial);
						firAddressRuleObj.setAddrRoof(roofmaterial);
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
								warnMsg.append(ruleMsg.toString());
							}
						} catch (Exception e) {
							logger.error("webService呼叫火險稽核議題檢核異常", e);
							warnMsg.append("檢核-稽核議題WS無回應或異常" + e.toString() +"；");
						}
					} else {
						warnMsg.append("檢核-因資料不齊全未進行稽核議題檢核；");
					}
					
					//機核議題檢核 end
				}
				firAgtTocoreMain.setStructure(struct);
				
				//地址正確性檢核
				String addrStatus = "";
				String addrDetail = "";
				FirAddressCheckVo firAddressCheckVo = new FirAddressCheckVo();
				firAddressCheckVo.setZip(addresscode);
				firAddressCheckVo.setAddress(addressdetailinfo);
				firAddressCheckVo.setStructure(struct);
				firAddressCheckVo.setFloors(sumfloors);
				firAddressCheckVo.setBuildyears(buildyears);//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數
				firVerifyVo = firVerifyDatasService.addressVerify(firAddressCheckVo);
				addrStatus = firVerifyVo.getAddrStatus();
				addrDetail = firVerifyVo.getAddrDetail();
				if(!"".equals(firVerifyVo.getWarnMsg())) {
					warnMsg.append("檢核-"+firVerifyVo.getWarnMsg());
				}
				//地址正確性檢核 end
				/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/
				
				//颱風洪水檢核
				String[] areaArr = {"基隆市","宜蘭縣","花蓮縣","台東縣","屏東縣"};
				//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額
				if(!StringUtil.isSpace(amountF) && Integer.parseInt(amountF) > 0 && ("3".equals(struct)||"5".equals(struct)||"6".equals(struct))) {
					params.clear();
					params.put("codetype ", "PostCode");
					params.put("codecode", addresscode);
					result = rfrcodeService.findRfrcodeByParams(params);
					if(result.getResObject()!=null) {
						List<Rfrcode> rfrcodeList = (List<Rfrcode>) result.getResObject();
						if(Arrays.asList(areaArr).contains(rfrcodeList.get(0).getCodename().substring(1, 3))) {
							warnMsg.append("易淹水地區；");
						}
					}
				}
				//颱風洪水檢核 end
				
				try {
					insertFirAgtTocore(firAgtTocoreMain, firAgtTocoreInsured, batchNo, batchSeq, userId, tmpdatas);
					dataqtyS ++;
					dataStatus = "2";
				}catch(Exception e) {
					e.printStackTrace();
					errMsg.append("新增APS暫存檔異常："+e.toString()+";");
					dataqtyF ++;
					dataStatus = "1";
				}
				//更新批次明細檔及暫存檔
				FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
				firAgtrnBatchDtl.setOldpolicyno(policyNo);
				firAgtrnBatchDtl.setOrderseq(orderseq);
				firAgtrnBatchDtl.setDquakeStatus(dquakeStatus);
				firAgtrnBatchDtl.setDquakeNo(dquakeNo);
				firAgtrnBatchDtl.setDataStatus(dataStatus);
				firAgtrnBatchDtl.setAddrStatus(addrStatus);
				firAgtrnBatchDtl.setOidFirPremcalcTmp(!"".equals(oidFirPremcalcTmp)?Long.parseLong(oidFirPremcalcTmp):null);
				firAgtrnBatchDtl.setFamtStatus(famtStatus);
				
				//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
				firAgtrnBatchDtl.setAddrDetail(addrDetail);
				firAgtrnBatchDtl.setCheckErrMsg(errMsg.toString());
				firAgtrnBatchDtl.setCheckWarnMsg(warnMsg.toString());
				updateFirAgtrnBatchDtlAndTmpBop(firAgtrnBatchDtl, batchNo, userId, batchSeq);
			}
			//執行成功
			returnData.put(OUTSTATUS, "Y");
			returnData.put("dataqtyS", String.valueOf(dataqtyS));
			returnData.put("dataqtyF", String.valueOf(dataqtyF));
			returnData.put("transStatus", "Y");
		}
		return returnData;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
	
	//切割每筆資料並存入list回傳
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	private List<String> getRawDataTexts(String data,String cutNos) throws UnsupportedEncodingException{
		List<String> texts = new LinkedList<>();
		int byteIndex = 0;
		int unitBytes = 0;
  		
  		data = data.replace("?", "？");//若有亂碼則將讀取到的?資料由全形？取代
  		char[] charArr = data.toCharArray();
  		StringBuilder textSb = new StringBuilder();
  		for(int i=0; i<charArr.length; i++) {
  			
  			String unitText = String.valueOf(charArr[i]);
  			textSb.append(unitText);
  			
  			unitBytes = unitText.getBytes(StandardCharsets.UTF_8).length > 1 ? 2 : 1;
  			//中文或全形會有兩個byte，若最後一個字元為2個byte，以被保人姓名為例，最後一個字起始index = 95，加上2個byte後index為97，會導致程式無法切割字串，所以新增以下判斷
  			if(unitBytes ==2 && (byteIndex == 95 || byteIndex == 166 || byteIndex == 317 || byteIndex == 377)) {
  				byteIndex = byteIndex +1;
  				unitBytes = 1;
  			}
  			if(cutNos.indexOf("|"+byteIndex+"|")!=-1) {
  				texts.add(textSb.toString().trim());
  				if(("|"+byteIndex+"|").equals("|498|")) {
  					texts.add(data.substring(i+1,data.length()));
  					break;
  				}
  				textSb = new StringBuilder();
  			}
  			byteIndex = byteIndex + unitBytes;
  		}
		return texts;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
	
	//驗證身份證字號
	private Map<String,String> verifyID(String id) {
		Map<String,String> resultMap = new HashMap<>();
		try {
			String url = configUtil.getString("verifyIDUrl")+id;
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse;
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			VerifyIdVo verifyIdVo = (VerifyIdVo) JsonUtil.getDTO(entityStr, VerifyIdVo.class);
			
			if(!verifyIdVo.getCode().equals("S0000")) {
				resultMap.put("errMsg",verifyIdVo.getCode()+verifyIdVo.getMsg()+";");
				return resultMap;
			}
			//檢核為法人或自然人
			if(verifyIdVo.getInsuredType().equals("1")) {
				resultMap.put("insuredNature", "3");
			}else {
				resultMap.put("insuredNature", "4");
			}
			String identifyType = verifyIdVo.getIdentifyType();
			if(identifyType.equals("01")||identifyType.equals("05")||identifyType.equals("60")) {
				resultMap.put("idType", verifyIdVo.getIdentifyType());
			}else {
				resultMap.put("idType", "04");
				resultMap.put("errMsg","證號類型可能為稅籍編號或異常，請再確認;");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return resultMap;
	}
	
	private void moveFile() throws IOException {
		String filename = "台壽保火險續保檔.TXT";
		File source = new File(ROOTDIRECTORY + filename);
		String destDir = ROOTDIRECTORY + new SimpleDateFormat("yyyyMM").format(new Date());
		File destFilePath = new File(destDir);
		if(!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		File dest = new File(destDir + File.separator + filename);
		if(dest.exists()) {
			dest.delete(); 
		}
		Files.copy(source.toPath(), dest.toPath());
		source.delete();
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo,Date excuteTime,String status,String errMsg,String programId) {
	 Mailer mailer = new Mailer();
	 StringBuilder tmpMsg = new StringBuilder();
	 try {
		 Map<String,Object> params = new HashMap<>();
		 params.put("prgId", programId);
		 Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		 if(result.getResObject() == null) {
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
		 if(status.equals("S")) {
			 sb.append("<p>執行狀態：完成</p>");
			 params.clear();
			 params.put("batchNo", batchNo);
			 Result mainResult = firAgtrnBatchMainService.findFirAgtrnBatchMainByParams(params);
			 
			 params.clear();
			 params.put("batchNo", batchNo);
			 params.put("sortBy", "BATCH_SEQ");
			 Result dtlResult = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
			 if(mainResult.getResObject()==null) {
				 tmpMsg.append("FIR_AGTRN_BATCH_MAIN批次主檔查無資料，請洽系統人員。");
			 }else if(dtlResult.getResObject()==null) {
				 tmpMsg.append("FIR_AGTRN_BATCH_DTL批次明細檔查無資料，請洽系統人員。");
			 }else {
				 sb.append("<table border=1 style='border-collapse: collapse;'>");
				 sb.append("<tr bgcolor='#70bbd9'>");
				 sb.append("<td>檔案名稱</td>");
				 sb.append("<td>檔案狀態</td>");
				 sb.append("<td>原始資料筆數</td>");
				 sb.append("<td>成功筆數</td>");
				 sb.append("<td>失敗筆數</td>");
				 sb.append("</tr>");
				 List<FirAgtrnBatchMain> firAgtrnBatchMainList = (List<FirAgtrnBatchMain>) mainResult.getResObject();
				 for(FirAgtrnBatchMain firAgtrnBatchMain:firAgtrnBatchMainList) {
					 sb.append("<tr>");
					 sb.append("<td>" + firAgtrnBatchMain.getFilename() + "</td>");
					 String fileStatus = "";
					 if(firAgtrnBatchMain.getFileStatus().equals("S")) {
						 fileStatus = "正常";
					 }else if(firAgtrnBatchMain.getFileStatus().equals("A")) {
						 fileStatus = "新增暫存錯誤";
					 }else {
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
				 sb.append("<td>受理編號</td>");
				 sb.append("<td>續保保單號</td>");
				 sb.append("<td>資料狀態</td>");
				 sb.append("<td>資料檢核異常訊息</td>");
				 sb.append("<td>資料檢核提示訊息</td>");
				 sb.append("</tr>");
				 List<FirAgtrnBatchDtl> firAgtrnBatchDtlList = (List<FirAgtrnBatchDtl>) dtlResult.getResObject();
				 String dataStatus = "";
				 for(FirAgtrnBatchDtl firAgtrnBatchDtl : firAgtrnBatchDtlList) {
					 sb.append("<tr>");
					 sb.append("<td>" + firAgtrnBatchDtl.getBatchSeq() + "</td>");
					 sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getOrderseq()) + "</td>");
					 sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getOldpolicyno()) + "</td>");
					 if(firAgtrnBatchDtl.getDataStatus().equals("0")) {
						 dataStatus = "未處理";
					 }else if(firAgtrnBatchDtl.getDataStatus().equals("1")) {
						 dataStatus = "資料驗證失敗";
					 }else {
						 dataStatus = "寫入APS暫存檔成功";
					 }
					 sb.append("<td>" + dataStatus + "</td>");
					 sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckErrMsg()) + "</td>");
					 sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckWarnMsg()) + "</td>");
					 sb.append("</tr>");
				 }
				 sb.append("</table>");
			 }
		 }else if (status.equals("N")) {
			 sb.append("<p>執行狀態：無檔案 </p>");
		 }else {//status = "F"
			 sb.append("<p>執行狀態：失敗</p>");
			 sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
		 }
		 mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", 
				 "", mailTo, "", mailCc, "", "", "", sb.toString(), "smtp","newims", "2012newims");
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	 	return tmpMsg.toString();
	 }
	 
	 private void updateFirBatchLog(String status, String outMsg, String userId,BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		processPanhsinFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	 }

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	//去除左邊的0
	private String replaceLeftZero(String str){
		while(str.substring(0,1).equals("0")) {
			if(str.equals("0")) break;
			str = str.substring(1, str.length());
		}
		return str;
	}
	
	//取小數後有幾位
	private int countDecimal(String str){
		int count = 0;
		if(str.contains(".")) {
			str = str.substring(str.indexOf(".")+1);
			count = str.length();
		}
		return count;
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
	
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	private String formatDate(Date date, String transferFormat) {
		String returnValue = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(transferFormat);
			returnValue = formatter.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		return returnValue;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public ProcessPanhsinFileService getProcessPanhsinFileService() {
		return processPanhsinFileService;
	}

	public void setProcessPanhsinFileService(ProcessPanhsinFileService processPanhsinFileService) {
		this.processPanhsinFileService = processPanhsinFileService;
	}

	public FirAgtrnTmpBopService getFirAgtrnTmpBopService() {
		return firAgtrnTmpBopService;
	}

	public void setFirAgtrnTmpBopService(FirAgtrnTmpBopService firAgtrnTmpBopService) {
		this.firAgtrnTmpBopService = firAgtrnTmpBopService;
	}

	public FirAgtSalesMappingService getFirAgtSalesMappingService() {
		return firAgtSalesMappingService;
	}

	public void setFirAgtSalesMappingService(FirAgtSalesMappingService firAgtSalesMappingService) {
		this.firAgtSalesMappingService = firAgtSalesMappingService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public PrpdAgreementService getPrpdAgreementService() {
		return prpdAgreementService;
	}

	public void setPrpdAgreementService(PrpdAgreementService prpdAgreementService) {
		this.prpdAgreementService = prpdAgreementService;
	}

	/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}
	/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/
	
	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
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
	
	public Map<Integer, String> getFieldMap() {
		fieldMap.put(1, "分行代號");
		fieldMap.put(2, "板信機構代號");
		fieldMap.put(4, "借款人ID");
		fieldMap.put(5, "借款人姓名");
		fieldMap.put(6, "提供人ID");
		fieldMap.put(7, "提供人姓名");
		fieldMap.put(10, "續保單號碼");
		fieldMap.put(20, "郵遞區號");
		fieldMap.put(21, "標的物地址");
		fieldMap.put(22, "通訊地址");
		fieldMap.put(32, "受理編號");
		return fieldMap;
	}

	public void setFieldMap(Map<Integer, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public void insertFirAgtrnBatchDtl(String batchNo, Integer batchSeq, String userId) throws SystemException, Exception {
		FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
		firAgtrnBatchDtl.setBatchNo(batchNo);
		firAgtrnBatchDtl.setBatchSeq(batchSeq);
		firAgtrnBatchDtl.setDataStatus("0");
		firAgtrnBatchDtl.setDeleteFlag("N");
		firAgtrnBatchDtl.setIcreate(userId);
		firAgtrnBatchDtl.setDcreate(new Date());
		//mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程
		firAgtrnBatchDtl.setSfFlag("N");
		firAgtrnBatchDtlService.insertFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}
	
	public void insertFirAgtTocore(FirAgtTocoreMain firAgtTocoreMain, FirAgtTocoreInsured firAgtTocoreInsured,
			String batchNo, int batchSeq, String userId,Map<String,String> tmpdatas) throws SystemException, Exception {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		firAgtTocoreMain.setBatchNo(batchNo);//IN_BATCH_NO
		firAgtTocoreMain.setBatchSeq(batchSeq);//T_BOP.BATCH_SEQ
		firAgtTocoreMain.setIsNew("N");
		firAgtTocoreMain.setRationcode("D");
		firAgtTocoreMain.setAgentcode("349018");
		firAgtTocoreMain.setBusinessnature("I99065");
		firAgtTocoreMain.setChanneltype("22");
		firAgtTocoreMain.setMaildate(new SimpleDateFormat("yyyyMMdd").parse(date));
		firAgtTocoreMain.setUpdatedate(new SimpleDateFormat("yyyyMMdd").parse(date));
		firAgtTocoreMain.setSerialno1(1);
		firAgtTocoreMain.setMortgageepcode1("1180000");
		firAgtTocoreMain.setAddressno(1);
		firAgtTocoreMain.setPossessnaturecode("A0001A8");
		firAgtTocoreMain.setBuildingno(1);
		firAgtTocoreMain.setPropAddressno(1);
		firAgtTocoreMain.setKindcodeF("FR3");
		firAgtTocoreMain.setKindnameF("住宅火災保險");
		firAgtTocoreMain.setItemcodeF("11");
		firAgtTocoreMain.setItemnameF("建築物");
		firAgtTocoreMain.setItemnatureF("不動產");
		firAgtTocoreMain.setBuildingnoF(1);
		firAgtTocoreMain.setKindcodeQ("FR2");
		firAgtTocoreMain.setKindnameQ("政策性地震險");
		firAgtTocoreMain.setItemcodeQ("11");
		firAgtTocoreMain.setItemnameQ("建築物");
		firAgtTocoreMain.setItemnatureQ("不動產");
		firAgtTocoreMain.setBuildingnoQ(1);
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
//		firAgtTocoreMain.setIsAutoRenew("N");//第一年固定值N
//		firAgtTocoreMain.setClauseSendtype("1");//固定值1 QRCode條款
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
		firAgtTocoreMain.setIcreate(userId);
		firAgtTocoreMain.setDcreate(new Date());
		firAgtTocoreMainService.insertFirAgtTocoreMain(firAgtTocoreMain);
		
		firAgtTocoreInsured.setBatchNo(batchNo);
		firAgtTocoreInsured.setBatchSeq(batchSeq);
		firAgtTocoreInsured.setInsuredSeq(1);
		firAgtTocoreInsured.setDomicile("TW");
		firAgtTocoreInsured.setCountryename("TW");
		firAgtTocoreInsured.setIcreate(userId);
		firAgtTocoreInsured.setDcreate(new Date());
		firAgtTocoreInsured.setIshighdengeroccupation("0");//高危職業 固定給0 高風險

		firAgtTocoreInsured.setInsuredflag("2");
		firAgtTocoreInsured.setInsurednature(tmpdatas.get("insuredNature2"));//TMP_INSUREDNATURE_2
		firAgtTocoreInsured.setInsuredname(tmpdatas.get("borrowerName"));//第一次：T_BOP.RAWDATA.06借款人姓名
		firAgtTocoreInsured.setIdentifytype(tmpdatas.get("idType2"));//第一次：TMP_IDTYPE_2
		firAgtTocoreInsured.setIdentifynumber(tmpdatas.get("borrowerId"));//第一次：T_BOP.RAWDATA.05借款人ID
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
		firAgtTocoreInsured.setBirthday(StringUtil.isSpace(tmpdatas.get("birthday2"))?null:new SimpleDateFormat("yyyy/MM/dd").parse(tmpdatas.get("birthday2")));
		firAgtTocoreInsured.setPostcode(tmpdatas.get("postcode2"));
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
		firAgtTocoreInsuredService.insertFirAgtTocoreInsured(firAgtTocoreInsured);//第一次
		
		firAgtTocoreInsured.setInsuredflag("1");
		firAgtTocoreInsured.setInsurednature(tmpdatas.get("insuredNature1"));//第二次：TMP_INSUREDNATURE_1
		firAgtTocoreInsured.setInsuredname(tmpdatas.get("providerName"));//第二次：T_BOP.RAWDATA.08提供人姓名
		firAgtTocoreInsured.setIdentifytype(tmpdatas.get("idType1"));//第二次：TMP_IDTYPE_1
		firAgtTocoreInsured.setIdentifynumber(tmpdatas.get("providerId"));//第二次：T_BOP.RAWDATA.07提供人ID
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
		firAgtTocoreInsured.setBirthday(StringUtil.isSpace(tmpdatas.get("birthday1"))?null:new SimpleDateFormat("yyyy/MM/dd").parse(tmpdatas.get("birthday1")));
		firAgtTocoreInsured.setPostcode(tmpdatas.get("postcode1"));
		/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
		firAgtTocoreInsuredService.insertFirAgtTocoreInsured(firAgtTocoreInsured);//第二次
	}

	public void updateFirAgtrnBatchDtlAndTmpBop(FirAgtrnBatchDtl firAgtrnBatchDtl, String batchNo, String userId, int batchSeq) throws SystemException, Exception {
		firAgtrnBatchDtl.setBatchNo(batchNo);
		firAgtrnBatchDtl.setBatchSeq(batchSeq);
		firAgtrnBatchDtl.setIupdate(userId);
		firAgtrnBatchDtl.setDupdate(new Date());
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
		FirAgtrnTmpBop firAgtrnTmpBop = new FirAgtrnTmpBop();
		firAgtrnTmpBop.setBatchNo(batchNo);
		firAgtrnTmpBop.setBatchSeq(batchSeq);
		firAgtrnTmpBop.setpStatus("Y");
		firAgtrnTmpBop.setIupdate(userId);
		firAgtrnTmpBop.setDupdate(new Date());
		firAgtrnTmpBopService.updateFirAgtrnTmpBop(firAgtrnTmpBop);
	}
	
	public FirAgtTocoreMainService getFirAgtTocoreMainService() {
		return firAgtTocoreMainService;
	}

	public void setFirAgtTocoreMainService(FirAgtTocoreMainService firAgtTocoreMainService) {
		this.firAgtTocoreMainService = firAgtTocoreMainService;
	}

	public FirAgtTocoreInsuredService getFirAgtTocoreInsuredService() {
		return firAgtTocoreInsuredService;
	}

	public void setFirAgtTocoreInsuredService(FirAgtTocoreInsuredService firAgtTocoreInsuredService) {
		this.firAgtTocoreInsuredService = firAgtTocoreInsuredService;
	}

	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	public PrpcinsuredService getPrpcinsuredService() {
		return prpcinsuredService;
	}

	public void setPrpcinsuredService(PrpcinsuredService prpcinsuredService) {
		this.prpcinsuredService = prpcinsuredService;
	}

	public PrpyddagentService getPrpyddagentService() {
		return prpyddagentService;
	}

	public void setPrpyddagentService(PrpyddagentService prpyddagentService) {
		this.prpyddagentService = prpyddagentService;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
}
