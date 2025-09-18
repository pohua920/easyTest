package com.tlg.aps.bs.firYcbBackFileService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firYcbBackFileService.FirYcbBackFileService;
import com.tlg.aps.bs.firYcbBackFileService.YcbBackFileService;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtYcb02;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtYcb02Service;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirYcbBackFileServiceImpl implements FirYcbBackFileService{
	
	private static final Logger logger = Logger.getLogger(FirYcbBackFileServiceImpl.class);

	private ConfigUtil configUtil;
	private FirSpService firSpService;
	private FirBatchInfoService firBatchInfoService;
	private YcbBackFileService ycbBackFileService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtYcb02Service firAgtYcb02Service;

	@Override
	public Result runToGenerateFile(Date excuteTime, String userId, String programId)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		String tmpStatus = "";
		String tmpMsg = "";
		String tmpBatchNo = "";
		String mailMsg = "";
		String fileQty = "0";
		String filePqty = "0";
		
		// 檢核傳入參數
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		
		// 判斷排程是否可以執行
		if (StringUtil.isSpace(sb.toString())) {
			tmpBatchNo = "YCB02_"+ new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
			Map<String, String> params = new HashMap<>();
			params.put("prgId", "FIR_AGT_YCB_FB_STATUS");
			Result result = firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() != null && ((FirBatchInfo) result.getResObject()).getMailTo().equals("N")) {
				tmpStatus = "S";
				tmpMsg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			} else {
				tmpStatus = "1";
			}
		} else {
			tmpBatchNo = "BOPER_"+ new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
			tmpStatus = "F";
			tmpMsg = "接收參數." + sb.toString();
		}
		
		logger.info("tmpBatchNo =" + tmpBatchNo);
		logger.info("tmpStatus =" + tmpStatus);
		logger.info("tmpMsg =" + tmpMsg);
		
		// 新增FIR_BATCH_LOG批次程式執行記錄檔
		Result result = ycbBackFileService.insertFirBatchLog(excuteTime, userId, programId, tmpStatus, tmpMsg, tmpBatchNo);
		FirBatchLog firBatchLog = null;
		if(result.getResObject()!=null) {
			firBatchLog = (FirBatchLog) result.getResObject();
		}
		
		if("1".equals(tmpStatus)) {
			Map<String,String> returnData = this.callSp(tmpBatchNo, userId, programId);
			tmpStatus = returnData.get("status");
			tmpMsg = returnData.get("msg");
			fileQty = returnData.get("fileQty");
			filePqty = returnData.get("filePqty");
			ycbBackFileService.updateFirBatchLog(tmpStatus, tmpMsg, userId, firBatchLog);
			mailMsg = sendEmail(tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpBatchNo, programId);
		} else if("F".equals(tmpStatus)) {
			mailMsg = sendEmail(tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpBatchNo, programId);
		}
		
		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			ycbBackFileService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
		}
		
		return this.getReturnResult(StringUtil.isSpace(tmpMsg)?"執行完成":tmpMsg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<>();
		Map<String,String> returnData = new HashMap<>();
		
		// 檢核傳入參數
		if (StringUtil.isSpace(batchNo)) {
			sb.append("批次號碼無內容值。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		if (StringUtil.isSpace(type)) {
			sb.append("執行類型無內容值。");
		}
		if (!StringUtil.isSpace(sb.toString())){
			returnData.put("status", "F");
			returnData.put("msg", "傳入參數異常:"+sb.toString());
			return returnData;
		}
		
		params.put("batchNo", batchNo);
		params.put("businessnature", "I00006");
		Result result = firAgtBatchMainService.findFirAgtBatchMainByParams(params);
		List<FirAgtBatchMain> list = new ArrayList<FirAgtBatchMain>();
		FirAgtBatchMain firAgtBatchMain = null;
		if(result.getResObject() != null) {
			list = (List<FirAgtBatchMain>)result.getResObject();
			if(list.size() > 0) {
				firAgtBatchMain = list.get(0);
				if(firAgtBatchMain.getFilePqty()==null || firAgtBatchMain.getFilePqty() == 0) {
					returnData.put("status", "0");
					returnData.put("msg", "");
					this.updateFirAgtBatch(firAgtBatchMain, "Z", null, null, userId);
					return returnData;
				}
			}
		}else {
			returnData.put("status", "F");
			returnData.put("msg", batchNo+ "批號查無對應資料。");
			return returnData;
		}
		
		//若接收參數.執行類型 = '2'重新執行，需將前次的執行結果寫入REMARK欄位，避免執行完成後資料覆蓋。
		if("2".equals(type)) {
			String remark=StringUtil.nullToSpace(firAgtBatchMain.getRemark()) + ";" +
					"上次執行資訊：" + firAgtBatchMain.getIupdate() + "-" + 
					DateUtils.format(firAgtBatchMain.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-檔名：" + firAgtBatchMain.getFileName();
			ycbBackFileService.updateFirAgtBatchMain(firAgtBatchMain, remark, userId);
		}
		
		String tmpBno = batchNo.substring(0, 5);
		String fileContent = "";
		String fileName = "";
		String source = "";
		File file = new File("");
		try {
			//產生txt資料
			fileName = this.getFileName(firAgtBatchMain.getFileName(), tmpBno);
			if("YCB02".equals(tmpBno)) {
				fileContent = genYcb02Data(batchNo);
				source = "元大回饋檔";
			} else {
				returnData.put("status", "F");
				returnData.put("msg", "批次號碼錯誤，需為YCB02開頭。");
				this.updateFirAgtBatch(firAgtBatchMain, "E", null, "批次號碼錯誤，需為YCB02開頭。", userId);
				return returnData;
			}
			
			//產生txt
			File filePath = new File(configUtil.getString("tempFolder"));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String outputFile = filePath + File.separator + fileName;
			file = new File(outputFile);
			String msg = this.genFile(fileContent, file);
			if(!StringUtil.isSpace(msg)) {
				throw new Exception(msg);
			}
			
			//壓縮檔案
			String pswd = "";
			params.clear();
			params.put("prgId", "FIR_AGT_YCB_FB_PSWD");
			result = firBatchInfoService.findFirBatchInfoByUK(params);
			if(result.getResObject() != null) {
				pswd = ((FirBatchInfo)result.getResObject()).getMailTo();
			}
			this.writeZip(file.getPath(), file, pswd);
		} catch (Exception e) {
            // mantis：FIR0694，處理人員：DP0714，住火_元大續保作業PhaseII
			logger.info(e.getMessage(), e);
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII
			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			returnData.put("status", "F");
			returnData.put("msg", "txt產生失敗:" + e.getMessage());
			this.updateFirAgtBatch(firAgtBatchMain, "E", null, "txt產生失敗:" + e.getMessage(), userId);
			return returnData;
		}

		//上傳檔案至FTS
		try {
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII
			FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(file.getPath() + ".zip", source, "F", batchNo);
			if(fileUploadResponseVo.getStatus().equals("N")) {
				throw new Exception(fileUploadResponseVo.getMessage());
			}
		} catch (Exception e) {
			logger.info(e.toString());
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII
			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			returnData.put("status", "F");
			returnData.put("msg", "上傳FTS失敗:" + e.getMessage());
			this.updateFirAgtBatch(firAgtBatchMain, "E", null, "上傳FTS失敗:" + e.getMessage(), userId);
			return returnData;
		}

		//上傳檔案至SFTP
		try {

            // mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII -- start
			// 元大要大寫副檔名
			File oldFile = new File(file.getPath() + ".zip");
			File newFile = new File(file.getPath() + ".ZIP");
			if (oldFile.exists()) {
	            oldFile.renameTo(newFile);
			}
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII -- end

			if(!uploadZipFileToSftp(file.getPath() + ".ZIP")) {
				throw new Exception("傳送SFTP失敗;");
			}

			returnData.put("status", "S");
			returnData.put("msg", "");
			this.deleteFile(file.getPath(), file.getPath() + ".ZIP");
			this.updateFirAgtBatch(firAgtBatchMain, "Y", fileName, null, userId);
			return returnData;

		} catch (Exception e) {
			logger.info(e.toString());
			this.deleteFile(file.getPath(), file.getPath() + ".ZIP");
			returnData.put("status", "F");
			returnData.put("msg", "傳送SFTP失敗;" + e.getMessage());
			this.updateFirAgtBatch(firAgtBatchMain, "E", fileName, "傳送SFTP失敗:" + e.getMessage(), userId);
			return returnData;
		}
	}
	
	private Map<String,String> callSp(String batchNo, String userId, String programId)
			throws SystemException, Exception {
		Map<String,Object> params = new HashMap<>();
		Map<String,String> returnData = new HashMap<>();
		int returnValue = 0;
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("outResult", null);

		returnValue = firSpService.runSpFirAgtYcb02(params);
		
		if(returnValue == 0) {
			params.clear();
			params.put("batchNo", batchNo);
			Result result = firAgtBatchMainService.findFirAgtBatchMainByUk(params);
			if(result.getResObject()!=null) {
				FirAgtBatchMain firAgtBatchMain = (FirAgtBatchMain) result.getResObject();
				if(firAgtBatchMain.getFileStatus().equals("Z")) {
					returnData.put("status", "N"); //檔案無資料
					returnData.put("msg", "檔案無資料");
				}else {
					returnData = this.generateFile(batchNo, userId, programId, "1");
					returnData.put("fileQty", firAgtBatchMain.getFileQty().toString());
					returnData.put("filePqty", firAgtBatchMain.getFilePqty().toString());
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", "執行SP失敗(SP_FIR_AGT_YCB_02)");
		}
		
		return returnData;
	}
	
	private String getFileName(String tmpMainFile, String tmpBno) throws Exception {
		String title = "";
		int number = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String today = sdf.format(new Date());
		if(!StringUtil.isSpace(tmpMainFile) && tmpMainFile.substring(7,15).equals(today.substring(0, 8))) {
			number = Integer.valueOf(tmpMainFile.substring(17, 20));
			number++;
		}
		if("YCB02".equals(tmpBno)) {
			title = "PNFPOLY";
		}
		return title + today + String.format("%03d", number) + ".118";
	}

	@SuppressWarnings("unchecked")
	private String genYcb02Data(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = firAgtYcb02Service.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<FirAgtYcb02> list = (List<FirAgtYcb02>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(FirAgtYcb02 m:list) {
                // mantis：FIR0694，處理人員：DP0714，住火_元大續保作業PhaseII (整段覆蓋) -- start
				sb.append(this.fillSpace("118", false, 10)).append("|"); //保險公司代號:固定值：118
				sb.append(this.fillSpace(m.getPolicyNo(), false, 25)).append("|"); //保單號碼
				sb.append(this.fillSpace(m.getOrderSeq(), false, 32)).append("|");	//受理編號
				sb.append(this.fillSpace(m.getEndorseNo(), false, 20)).append("|"); //批單號碼
				sb.append(this.fillSpace(m.getRiskCodeYcb(), false, 10)).append("|"); //險種代碼
				sb.append(this.fillSpace(m.getKindCodeYcb(), false, 10)).append("|"); //細險種代碼
				sb.append(this.fillSpace(sdf.format(new Date()), true, 9)).append("|"); //資料日
				sb.append(this.fillSpace(m.getStartDate()==null?"         ":sdf.format(m.getStartDate()), true, 9)).append("|"); //生效日
				sb.append(this.fillSpace(m.getEndDate()==null?"         ":sdf.format(m.getEndDate()), true, 9)).append("|"); //到期日
				sb.append(this.fillSpace(m.getUnderWriteEndDate()==null?"         ":sdf.format(m.getUnderWriteEndDate()), true, 9)).append("|"); //簽單日
				sb.append(StringUtil.nullToSpace(m.getOrderType())).append("|"); //受理狀況
				sb.append(StringUtil.nullToSpace(m.getPolicyStatus())).append("|"); //保單狀況
				sb.append(this.fillSpace(m.getEndorseText(), false, 200)).append("|"); //批改內容:不處理
				sb.append(StringUtil.nullToSpace(m.getIsRenew())).append("|"); //是否續保件
				sb.append(this.fillSpace(m.getApplyName(), false, 40)).append("|"); //要保人姓名
				sb.append(this.fillSpace(m.getApplyId(), false, 12)).append("|"); //要保人ID
				sb.append(this.fillSpace(m.getApplyPostcode(), false, 5)).append("|"); //要保人郵遞區號
				sb.append(this.fillSpace(m.getApplyAddr(), false, 80)).append("|"); //要保人地址
				sb.append(this.fillSpace(m.getInsuredName(), false, 40)).append("|"); //被保險人姓名
				sb.append(this.fillSpace(m.getInsuredId(), false, 12)).append("|"); //被保險人ID
				sb.append(m.getInsuredBirthday()==null?"        ":sdf.format(m.getInsuredBirthday())).append("|"); //被保險人生日
				sb.append(this.fillSpace(m.getInsuredAge(), true, 3)).append("|"); //被保險人投保年齡
				sb.append(StringUtil.nullToSpace(m.getPayType())).append("|"); //繳別
				String premium = m.getPremium().intValue() + ".00"; // 保費 -> 小數點2位補0，ex. 1350.00
				sb.append(this.fillSpace(premium, true, 14)).append("|");
				String amount = m.getAmount().intValue() + ".00"; // 保額 -> 小數點2位補0，ex. 15000000.00
				sb.append(this.fillSpace(amount, true, 16)).append("|");
				java.text.DecimalFormat df5 = new java.text.DecimalFormat("0.000000");
				String commRate = df5.format(m.getCommRate().doubleValue()).substring(1); // 佣金率 -> 去頭，小數點6位補0，ex. .060000
				sb.append(this.fillSpace(commRate, true, 9)).append("|");
				sb.append(this.fillSpace(m.getCommission().intValue()+ "", true, 19)).append("|"); //佣金
				sb.append(this.fillSpace(m.getServiceCharge().intValue()+ "", true, 19)).append("|"); //服務費
				sb.append(this.fillSpace(m.getSalesSource(), false, 3)).append("|"); //業績歸屬通路
				sb.append(this.fillSpace(m.getSalesBranch(), false, 4)).append("|"); //業績歸屬分行代號
				sb.append(this.fillSpace(m.getSalesId(), false, 10)).append("|"); //銷售行員代號
				sb.append(this.fillSpace(m.getSalesNo(), false, 10)).append("|"); //銷售行員登錄字號
				sb.append(StringUtil.nullToSpace(m.getIsAutoRenew())).append("|"); //是否自動續保
				sb.append(StringUtil.nullToSpace(m.getAmountCheck())).append("|"); //不足額/超額
				sb.append(this.fillSpace(m.getLicensePlate(), false, 30)).append("|"); //汽機車-車號(引擎號碼)
				sb.append(this.fillSpace(m.getOldPolicyNo(), false, 25)).append("|"); //前一保單號碼
				sb.append(m.getOldEndDate()==null?"        ":sdf.format(m.getOldEndDate())).append("|"); //前一保單到期日
				sb.append(this.fillSpace(m.getProjNo(), false, 10)).append("|"); //專案代碼
				sb.append(this.fillSpace(m.getProjName(), false, 50)).append("|"); //專案名稱
				sb.append(StringUtil.nullToSpace(m.getAcceptWay())).append("|"); //客戶受理途徑
				// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII
				//sb.append(StringUtil.nullToSpace(m.getStartTime())).append("|"); //生效時間
				sb.append(m.getStartTime()==null?"    ":StringUtil.nullToSpace(m.getStartTime())).append("|"); //生效時間
				sb.append(m.getEndTime()==null?"    ":StringUtil.nullToSpace(m.getEndTime())).append("|"); //到期時間
				sb.append(this.fillSpace(m.getAddress(), false, 80)).append("|"); //標的物地址/營業處所
				sb.append(this.fillSpace(m.getPhoneNumber(), false, 20)).append("|"); //聯絡電話
				sb.append("X").append("|"); //業務來源，固定值 X
				sb.append(StringUtil.nullToSpace(m.getDeleteFlag())); //刪除註記
				sb.append("\r\n");
				// mantis：FIR0694，處理人員：DP0714，住火_元大續保作業PhaseII (整段覆蓋) -- end
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo,String fileQty,String filePqty,Date excuteTime,
			String logStatus,String errMsg,String programId) throws Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
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
		if (logStatus.equals("S")) {
			sb.append("<p>執行狀態：完成</p>");
			sb.append("<p>預計筆數：" + fileQty + "</p>");
			sb.append("<p>處理筆數：" + filePqty + "</p>");
			params.clear();
			params.put("batchNo", batchNo);
			params.put("sortBy", "RISKCODE, ORDERSEQ");
			params.put("sortType", "asc");
			Result mainResult = firAgtBatchDtlService.findFirAgtBatchDtlByParams(params);

			if (mainResult.getResObject() == null) {
				sb.append("<p>OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。</p>");
				tmpMsg.append("OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。");
			} else {
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>受理編號</td>");
				sb.append("<td>狀態</td>");
				sb.append("<td>對應核心單號</td>");
				sb.append("<td>險別</td>");
				sb.append("<td>資料來源</td>");
				sb.append("</tr>");
				List<FirAgtBatchDtl> firAgtBatchDtlList = (List<FirAgtBatchDtl>) mainResult.getResObject();
				for (FirAgtBatchDtl main : firAgtBatchDtlList) {
					String dataSource = main.getDataSource().equals("1")?"新核心":"AS400";
					String status = "";
					switch (main.getOrderseqStatus()) {
					case "00":
						status = "未處理";
						break;
					case "01":
						status = "資料產生成功";
						break;
					case "02":
						status = "檔案產生成功";
						break;
					default:
						status = "未定義";
						break;
					}
					sb.append("<tr>");
					sb.append("<td>" + main.getOrderseq() + "</td>");
					sb.append("<td>" + status + "</td>");
					sb.append("<td>" + main.getCoreNo() + "</td>");
					sb.append("<td>" + main.getRiskcode() + "</td>");
					sb.append("<td>" + dataSource + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		} else if (logStatus.equals("N")) {
			sb.append("<p>執行狀態：無資料 </p>");
		} else {// status = "F"
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
		}
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
				mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		return tmpMsg.toString();
	}

	private void updateFirAgtBatch(FirAgtBatchMain oldMain, String fileStatus, String filename, String remark, String userId) throws Exception {
		// FirAgtBatchMain處理
		FirAgtBatchMain main = new FirAgtBatchMain();
		main.setBatchNo(oldMain.getBatchNo());
		if(filename != null)
			main.setFileName(filename);
		if(remark != null) {
			remark = StringUtil.isSpace(oldMain.getRemark())?remark:oldMain.getRemark()+";"+remark;
			if(remark != null && remark.length() > 300) {
				remark = remark.substring(remark.length()-300 <0?0:remark.length()-300, remark.length());
			}
			main.setRemark(remark);
		}
		main.setFileStatus(fileStatus);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		// FirAgtBatchDtl處理
		Map<String,Object> params = null;
		if("Y".equals(fileStatus)) {
			params = new HashMap<String, Object>();
			params.put("batchNo", main.getBatchNo());
			params.put("orderseqStatus", "02");
			params.put("iupdate", userId);
			params.put("dupdate", new Date());
		}
		ycbBackFileService.updateFirAgtBatch(main, params);
	}

	public String genFile(String fileContent, File file){
		FileOutputStream fileOutputStream = null;
		BufferedWriter bufWriter = null;
		try {
			fileOutputStream = new FileOutputStream(file, false);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_16));
			bufWriter.write(fileContent);
			bufWriter.close();
		} catch (Exception e) {
			logger.info(e.toString());
			return e.toString();
		} finally {
			try {
				bufWriter.close();				
				fileOutputStream.close();
			} catch (IOException e) {
				logger.info(e.toString());
			}
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void writeZip(String fileName,File file,String pswd) throws Exception {
		ZipFile zipFile = null;
		// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII
		zipFile = new ZipFile(fileName + ".zip");
		ArrayList filesToAdd = new ArrayList();
		filesToAdd.add(file);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if (!StringUtil.isSpace(pswd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setPassword(pswd);
		}
		zipFile.addFiles(filesToAdd, parameters);
	}

	private void deleteFile(String txtFilePath, String zipFilePath) {
		File txtFile = new File(txtFilePath);
		File zipFile = new File(zipFilePath);
		if(txtFile.exists())
			txtFile.delete();
		if(zipFile.exists())
			zipFile.delete();
	}

	private boolean uploadZipFileToSftp(String filePath) throws Exception{
		boolean result = false;
		String sftpHost = configUtil.getString("ycbBackFileFTP");
		String sftpUser = configUtil.getString("ycbBackFileFtpUserPut");
		String sftpPwd = configUtil.getString("ycbBackFileFtpPwdPut");
		String sftpPort = configUtil.getString("ycbBackFileFtpPort");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2("", filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public YcbBackFileService getYcbBackFileService() {
		return ycbBackFileService;
	}

	public void setYcbBackFileService(YcbBackFileService ycbBackFileService) {
		this.ycbBackFileService = ycbBackFileService;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}

	public FirAgtYcb02Service getFirAgtYcb02Service() {
		return firAgtYcb02Service;
	}

	public void setFirAgtYcb02Service(FirAgtYcb02Service firAgtYcb02Service) {
		this.firAgtYcb02Service = firAgtYcb02Service;
	}

	/**
	 * mantis：FIR0694，處理人員：DP0714，住火_元大續保作業PhaseII
	 */
	private String fillSpace(String str, boolean isNumber, int length) {
		if (org.apache.commons.lang3.StringUtils.isBlank(str) || str.equals("null")) {
			str = "";
		}
		if (isNumber) {
			return String.format("%" + length + "s", str); // 數字空白補左邊
		} else {
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII -- start
			String fillStr = String.format("%-" + length + "s", str); //預設補滿 文字空白補右邊  
			try {
				int str_length = str.getBytes("big5").length; // AS400 用 big5 編碼
				int fill_space_length = length - str_length ; // 計算以 big5 計算長度後，需取得 半型空白 補滿長度    
				char[] spaces = new char[fill_space_length];
				Arrays.fill(spaces, ' ');
				String fillSpaceStr = new String(spaces);
				fillStr =(StringUtils.isNotEmpty(fillStr)) ? str.concat(fillSpaceStr) : fillStr;  
			} catch (java.io.UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
			return fillStr;
			// mantis：FIR0689，處理人員：DP0714，住火_承保_元大續保作業_PhaseII -- end
		}
	}

	public static void main(String args[]) throws Exception{
		//測試取得檔案list----start
		String riskCode = "F";
		String businessNo = "BOP04_220818144349";
		FtsUtil ftsUtil = new FtsUtil("http://192.168.112.122:8880/FTS/rf/fileHandler/");
		FileListResponseVo vo = ftsUtil.getFtsFileList(riskCode, businessNo);
        ArrayList<FileVo> list = vo.getFileList();
        for(FileVo fv : list) {
        	System.out.println("----------");
        	System.out.println("oid : " + fv.getOid());
        	System.out.println("name : " + fv.getName());
        	System.out.println("downloadPath : " + fv.getDownloadPath());
        	System.out.println("----------");
        	System.out.println("start to download file : " + fv.getName());
        	ftsUtil.downloadFile(businessNo, fv.getOid(), "D:/測試檔案/aps/FIR0494/download/", fv.getName());
        	System.out.println("download file : " + fv.getName() + " finished");
        }
		//測試取得檔案list----end
	}
}
