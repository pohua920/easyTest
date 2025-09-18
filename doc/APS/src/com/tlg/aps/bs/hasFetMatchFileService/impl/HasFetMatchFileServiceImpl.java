package com.tlg.aps.bs.hasFetMatchFileService.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.hasFetMatchFileService.HasFetMatchFileService;
import com.tlg.aps.bs.hasLionBackFileService.LionBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.entity.HasBatchInfo;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.prpins.entity.HasBatchMatchFet;
import com.tlg.prpins.service.HasAgtBatchMainService;
import com.tlg.prpins.service.HasBatchInfoService;
import com.tlg.prpins.service.HasBatchMatchFetService;
import com.tlg.prpins.service.HasSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 
 * @author dp0706
 *
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasFetMatchFileServiceImpl implements HasFetMatchFileService{

	private static final Logger logger = Logger.getLogger(HasFetMatchFileServiceImpl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private ConfigUtil configUtil;
	private HasSpService hasSpService;
	private HasBatchInfoService hasBatchInfoService;
	private LionBackFileService lionBackFileService;
	private HasAgtBatchMainService hasAgtBatchMainService;
	private HasBatchMatchFetService hasBatchMatchFetService;
	
	/**
	 * 遠傳產生作業-資料產生
	 * 1.新增執行記錄檔
	 * 2.產生遠傳比對資料(callSp)
	 * 3.發送郵件
	 * 4.回寫執行記錄檔
	 */
	@Override
	public Result runToGenerateFile(String fileType, Date excuteTime, String userId, Date dataDate) throws SystemException, Exception {
		String tmpStatus = "";
		String tmpMsg = "";
		String tmpBatchNo = "";
		String mailMsg = "";
		String fileQty = "0";
		String filePqty = "0";
		String programId = "HAS_BATCH_MATCH_FET";

		StringBuilder sb2 = new StringBuilder();
		sb2.append("FET");
		sb2.append(fileType);//1=每日檢核,2=每月總筆數
		if("1".equals(fileType)) {//1=每日檢核
			//因應每月統計以BATCH_NO取資料，因每月一號執行的是前一個月最後一天的資料，故改成資料"日期"+執行時間
			String tmpBatchDate = new SimpleDateFormat("yyMMdd").format(dataDate);
			String tmpBatchTime = new SimpleDateFormat("HHmmss").format(excuteTime);
			sb2.append("_").append(tmpBatchDate).append(tmpBatchTime);
		} else {//每月
			//執行時間
			String tmpBatchTime = new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
			sb2.append("_" + tmpBatchTime);
		}
		
		tmpBatchNo = sb2.toString();

		Map<String, String> params = new HashMap<>();
		params.put("prgId", "HAS_BATCH_MATCH_FET_STATUS");
		Result result0 = hasBatchInfoService.findHasBatchInfoByUK(params);
		if (result0.getResObject() != null && "N".equals(((HasBatchInfo) result0.getResObject()).getMailTo())) {//查詢結果有資料 AND 查詢結果.MAIL_TO欄位 = ‘N’
			tmpStatus = "S";
			tmpMsg = "HAS_BATCH_INFO 設定檔設定為排程暫停執行。";
		} else {//查無資料或查有資料但MAIL_TO不是N時
			tmpStatus = "1"; // 可執行
		}
		logger.info("tmpBatchNo =" + tmpBatchNo);
		logger.info("tmpStatus =" + tmpStatus);
		logger.info("tmpMsg =" + tmpMsg);

		// 新增Has_BATCH_LOG批次程式執行記錄檔
		Result result = lionBackFileService.insertHasBatchLog(excuteTime, userId, programId, tmpStatus, tmpMsg,	tmpBatchNo);

		HasBatchLog hasBatchLog = null;
		if (result.getResObject() != null) {
			hasBatchLog = (HasBatchLog) result.getResObject();
		}
		Map<String, String> returnData = new HashMap<String, String>();
		if ("1".equals(tmpStatus)) {// 可執行
			if("1".equals(fileType)) {//1=每日檢核
				returnData = this.callSp(tmpBatchNo, userId, dataDate);
				tmpStatus = returnData.get("status");
				tmpMsg = returnData.get("msg");
				fileQty = returnData.get("fileQty");
				filePqty = returnData.get("filePqty");
				lionBackFileService.updateHasBatchLog(tmpStatus, tmpMsg, userId, hasBatchLog);
				
				if ("S".equals(tmpStatus)) {
					if(returnData.get("outputFile") != null) {
						File file = new File(returnData.get("outputFile"));
						if (file.exists()) {
							String filePath = file.getPath();
							String fileName = file.getName();
							mailMsg = sendEmail(fileType, tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, "HAS_BATCH_MATCH_FET_EMAIL",
									filePath, fileName,"");
							this.deleteFile(returnData.get("outputFile"));
						}
					}
				} else {
					mailMsg = sendEmail(fileType, tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, "HAS_BATCH_MATCH_FET_EMAIL",null,null,"");
				}
			} else {//2=每月統計
//				資料表：HAS_AGT_BATCH_MAIN 旅平險保經代資料交換批次主檔
//				過濾條件：BATCH_NO = 前一個月份的IN_BATCH_NO 
//				AND　 BUSINESSNATURE='MATCH_FET'　
//						  AND  FILE_STATUS in (‘Y’,’Z’)
//				  取得欄位：COUNT(FILE_QTY),COUNT(FILE_PQTY)
				String monthBathNo = new SimpleDateFormat("yyMM").format(dataDate);
				params.clear();
				params.put("batchNoLike", "FET1_"+monthBathNo+"%");
				params.put("businessnature", "MATCH_FET");
				params.put("fileStatusInYz", "fileStatusInYz");
				Result monthResult = hasAgtBatchMainService.findHasAgtBatchMainByParams(params);
				if (monthResult.getResObject() != null) {
					List<HasAgtBatchMain> hasAgtBatchMainList = (List<HasAgtBatchMain>) monthResult.getResObject();
					if (hasAgtBatchMainList != null || hasAgtBatchMainList.size() > 0) {
						int fileQtyInt = 0;
						int filePqtyInt = 0;
						for(HasAgtBatchMain hasAgtBatchMain : hasAgtBatchMainList) {
							if(hasAgtBatchMain.getFileQty() != null) {
								fileQtyInt += hasAgtBatchMain.getFileQty();
							}
							if(hasAgtBatchMain.getFilePqty() != null) {
								filePqtyInt += hasAgtBatchMain.getFilePqty();
							}
						}
						tmpStatus = "S";
						mailMsg = sendEmail(fileType, tmpBatchNo, String.valueOf(fileQtyInt), String.valueOf(filePqtyInt), excuteTime,
								tmpStatus, tmpMsg, "HAS_BATCH_MATCH_FET_EMAIL",null,null, monthBathNo);
						
					} else {
						returnData.put("status", "N"); // 檔案無資料
						returnData.put("msg", "檔案無資料");
						tmpStatus = "N";
						mailMsg = sendEmail(fileType, tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, "HAS_BATCH_MATCH_FET_EMAIL",null,null,"");
					}
				}

			}
			
		} else if ("F".equals(tmpStatus)) {
			mailMsg = sendEmail(fileType, tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, "HAS_BATCH_MATCH_FET_EMAIL",null,null,"");
		}

		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			lionBackFileService.updateHasBatchLog("F", mailMsg, userId, hasBatchLog);
		}

		return this.getReturnResult(StringUtil.isSpace(mailMsg) ? "執行完成" : mailMsg);
	}

	/**
	 * 產生遠傳比對資料
	 * 1. call SP_HAS_BATCH_MATCH_FET
	 * 2. 實體檔案產生(generateFile)
	 * @param batchNo
	 * @param userId
	 * @param dataDate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	private Map<String, String> callSp(String batchNo, String userId, Date dataDate)
			throws SystemException, Exception {

		Map<String, String> returnData = new HashMap<>();

		Map<String, Object> params = new HashMap<>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("inDate", dataDate);
		params.put("outResult", null);
		int returnValue = hasSpService.runSpHasBatchMatchFet(params);
		logger.info(">>> returnValue: " + returnValue);

		if (returnValue == 0) {//SP執行成功
			params.clear();
			params.put("batchNo", batchNo);
			Result result = hasAgtBatchMainService.findHasAgtBatchMainByUk(params);
			if (result.getResObject() != null) {
				HasAgtBatchMain hasAgtBatchMain = (HasAgtBatchMain) result.getResObject();
				if ("Z".equals(hasAgtBatchMain.getFileStatus())) {
					returnData.put("status", "N"); // 檔案無資料
					returnData.put("msg", "檔案無資料");
				} else {
					//遠傳比對產生作業-實體檔案產生
					returnData = this.generateFile(batchNo, userId, "1");
					returnData.put("fileQty", hasAgtBatchMain.getFileQty().toString());
					returnData.put("filePqty", hasAgtBatchMain.getFilePqty().toString());
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", "執行SP失敗(SP_HAS_BATCH_MATCH_FET)");
		}

		return returnData;
	}
	
	/**
	 * 需求項目2：遠傳比對產生作業-實體檔案產生
	 * batchNo 排程:系統產生批次號碼	手動執行:畫面值.批次號碼
	 * userId 執行人員
	 * executeType 執行類型 排程:1(正常執行)  手動執行:2(重新執行)
	 */
	@Override
	public Map<String, String> generateFile(String batchNo, String userId, String executeType)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		Map<String, String> returnData = new HashMap<>();

		// 檢核傳入參數
		if (StringUtil.isSpace(batchNo)) {
			sb.append("批次號碼無內容值。");
		}
		if (batchNo.indexOf("FET") < 0) {
			sb.append("批次號碼錯誤。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (!StringUtil.isSpace(sb.toString())) {
			returnData.put("status", "F");
			returnData.put("msg", "傳入參數異常:" + sb.toString());
			return returnData;
		}
		
		//更新批次主檔，記錄本次執行前之資訊
		params.put("batchNo", batchNo);
		params.put("businessnature", "MATCH_FET");
		Result result = hasAgtBatchMainService.findHasAgtBatchMainByParams(params);
		List<HasAgtBatchMain> list = new ArrayList<HasAgtBatchMain>();
		HasAgtBatchMain hasAgtBatchMain = null;
		if (result.getResObject() != null) {
			list = (List<HasAgtBatchMain>) result.getResObject();
			if (list.size() > 0) {
				hasAgtBatchMain = list.get(0);
				//若查詢結果有資料，但是查詢結果.FILE_PQTY = 0 或 NULL
				if (hasAgtBatchMain.getFilePqty() == null || hasAgtBatchMain.getFilePqty() == 0) {
					returnData.put("status", "N");
					returnData.put("msg", "無資料");
					this.updateHasAgtBatch(hasAgtBatchMain, "Z", null, null, userId);
					return returnData;
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", batchNo + "批號查無對應資料。");
			return returnData;
		}

		//若接收參數.執行類型 = '2'重新執行，需將前次的執行結果寫入REMARK欄位，避免執行完成後資料覆蓋。
		if("2".equals(executeType)) {
			String remark=StringUtil.nullToSpace(hasAgtBatchMain.getRemark()) + ";" +
					"上次執行資訊：" + hasAgtBatchMain.getIupdate() + "-" + 
					DateUtils.format(hasAgtBatchMain.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-檔名：" + hasAgtBatchMain.getFileName();
			lionBackFileService.updateHasAgtBatchMain(hasAgtBatchMain, remark, userId);
		}

		String tempFolder = configUtil.getString("tempFolder");
		try {
			Result resultHasBatchMatchFet = hasBatchMatchFetService.findHasBatchMatchFetByParams(params);
			if(resultHasBatchMatchFet.getResObject()!=null) {
				try(XSSFWorkbook workbook = new XSSFWorkbook()){
					XSSFSheet sheet = workbook.createSheet();
					// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
					String[] titleArr = {"批次號", "保單號碼", "年度(投保日期)", "要保人ID", "要保人姓名", "要保人生日", "要保人地址", "要保人行動電話", 
							"要保人Email", "被保人ID", "被保人姓名", "被保人生日", "被保人地址", "保額", "保費", "保險開始日", "保險結束日", "收件日", "簽單日"};
					XSSFRow rowTitle = sheet.createRow(0);
					for(int i=0;i<titleArr.length;i++) {
						rowTitle.createCell(i).setCellValue(titleArr[i]);
					}
					
					List<HasBatchMatchFet> dataList = (List<HasBatchMatchFet>) resultHasBatchMatchFet.getResObject();
					for(int i = 0; i < dataList.size(); i++) {
						XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
						row.createCell(0).setCellValue(dataList.get(i).getBatchNo());//批次號
						row.createCell(1).setCellValue(dataList.get(i).getPolicyno());//保單號碼
						row.createCell(2).setCellValue(dataList.get(i).getInputdate());//年度(投保日期)
						row.createCell(3).setCellValue(dataList.get(i).getOwnerid());//要保人ID
						row.createCell(4).setCellValue(dataList.get(i).getOwnername());//要保人姓名
						row.createCell(5).setCellValue(dataList.get(i).getOwnerbirth());//要保人生日
						row.createCell(6).setCellValue(dataList.get(i).getOwneraddr());//要保人地址
						row.createCell(7).setCellValue(dataList.get(i).getOwnermobile());//要保人行動電話
						row.createCell(8).setCellValue(dataList.get(i).getOwneremail());//要保人Email
						row.createCell(9).setCellValue(dataList.get(i).getInsid());//被保人ID
						row.createCell(10).setCellValue(dataList.get(i).getInsname());//被保人姓名
						row.createCell(11).setCellValue(dataList.get(i).getInsbirth());//被保人生日
						row.createCell(12).setCellValue(dataList.get(i).getInsaddr());//被保人地址
						row.createCell(13).setCellValue(dataList.get(i).getSumamount());//保額
						row.createCell(14).setCellValue(dataList.get(i).getSumpremium());//保費
						row.createCell(15).setCellValue(dataList.get(i).getStartdate());//保險開始日
						row.createCell(16).setCellValue(dataList.get(i).getEnddate());//保險結束日
						row.createCell(17).setCellValue(dataList.get(i).getMaildate());//收件日
						row.createCell(18).setCellValue(dataList.get(i).getUnderwriteenddate());//簽單日
					}
					
					String tempDir = configUtil.getString("tempFolder");
					
					String filename = "FET_MATCH_"+sdf.format(new Date())+".xlsx";
					FileOutputStream fos = new FileOutputStream(new File(tempDir+filename));
					workbook.write(fos);
					String filePath = tempFolder + filename;
					params.clear();
					returnData.put("outputFile", filePath);
					returnData.put("status", "S");
					returnData.put("msg", "");
					
					// 執行產檔完後更新批次主檔
					this.updateHasAgtBatch(hasAgtBatchMain, "Y", filename, null, userId);
					return returnData;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnData.put("status", "F");
			returnData.put("msg", "產生失敗:" + e.getMessage());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "產生失敗:" + e.getMessage(), userId);
			return returnData;
		}
		return returnData;
	}
	
	/**
	 * 執行產檔完後更新批次主檔
	 * @param oldMain
	 * @param fileStatus
	 * @param filename
	 * @param remark
	 * @param userId
	 * @throws Exception
	 */
	private void updateHasAgtBatch(HasAgtBatchMain oldMain, String fileStatus, String filename,
			String remark,	String userId) throws Exception {
		// HasAgtBatchMain處理
		HasAgtBatchMain main = new HasAgtBatchMain();
		main.setBatchNo(oldMain.getBatchNo());
		if (filename != null)
			main.setFileName(filename);
		if (remark != null) {
			remark = StringUtil.isSpace(oldMain.getRemark()) ? remark : oldMain.getRemark() + ";" + remark;
			if (remark != null && remark.length() > 300) {
				remark = remark.substring(remark.length() - 300 < 0 ? 0 : remark.length() - 300, remark.length());
			}
			main.setRemark(remark);
		}
		main.setFileStatus(fileStatus);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		// HasAgtBatchDtl處理
		Map<String, Object> params = null;
		if ("Y".equals(fileStatus)) {
			params = new HashMap<String, Object>();
			params.put("batchNo", main.getBatchNo());
			params.put("orderseqStatus", "02");
			params.put("iupdate", userId);
			params.put("dupdate", new Date());
		}
		lionBackFileService.updateHasAgtBatch(main, params);
	}
	
	/**
	 * 郵件寄發
	 * 區分每日/每月
	 * @param fileType
	 * @param batchNo
	 * @param type
	 * @param fileQty
	 * @param filePqty
	 * @param excuteTime
	 * @param logStatus
	 * @param errMsg
	 * @param programId
	 * @param filepath
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	private String sendEmail(String fileType, String batchNo, String fileQty,String filePqty,Date excuteTime,
			String logStatus,String errMsg,String programId,String filepath,String filename, String monthBatchYm) throws Exception {
		//mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 
		if ("N".equals(logStatus)) {
			return "";//250630 改成無資料就不寄信
		}
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", programId);           
		Result result = this.hasBatchInfoService.findHasBatchInfoByUK(params);
		if (result.getResObject() == null) {
			return "無法取得HAS_BATCH_INFO資料，無法寄送MAIL";
		}
		HasBatchInfo hasBatchInfo = (HasBatchInfo) result.getResObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//主旨
		String subject;
		if("1".equals(fileType)) {//每日檢核
			//TMP_SUBJECT =查詢結果.MAIL_SUBJECT + ‘每日檢核‘ + 系統時間(YYYY/M/DD HH24:MI:SS)
			subject = hasBatchInfo.getMailSubject() + "-每日檢核-" + sdf.format(new Date());

		} else if("2".equals(fileType)) {//每月統計
			// TMP_SUBJECT =查詢結果.MAIL_SUBJECT + ‘每月統計‘ + 系統時間(YYYY/M/DD HH24:MI:SS)
			subject = hasBatchInfo.getMailSubject() + "-每月統計-" + sdf.format(new Date());
		} else {
			//TMP_SUBJECT =查詢結果.MAIL_SUBJECT + ‘-‘ + 系統時間(YYYY/M/DD HH24:MI:SS)
			subject = hasBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
		}
		
		//收件者&CC
		String mailTo = hasBatchInfo.getMailTo();
//		mailTo = "dp0706@ctbcins.com";
		String mailCc = hasBatchInfo.getMailCc();

		//內容
		StringBuilder sb = new StringBuilder();
		sb.append("<p>批次號碼：" + batchNo + "</p>");
		sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
		if ("S".equals(logStatus)) {//mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 
			sb.append("<p>執行狀態：完成</p>");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			if("1".equals(fileType)) {//每日檢核
				//表頭
				sb.append("<tr>");
				sb.append("<th>批次號</th>");
				sb.append("<th>命中筆數</th>");
				sb.append("<th>當日保單數</th>");
				sb.append("<th>檔案名稱</th>");
				sb.append("</tr>");
				//表格內容
				sb.append("<tr>");
				sb.append("<td>").append(batchNo).append("</td>");
				sb.append("<td>").append(fileQty).append("</td>");
				sb.append("<td>").append(filePqty).append("</td>");
				sb.append("<td>").append(filename).append("</td>");
				sb.append("</tr>");
			} else {//每月統計
				//表頭
				sb.append("<tr>");
				sb.append("<th>批次號</th>");
				sb.append("<th>比對年月</th>");
				sb.append("<th>總比對筆數</th>");
				sb.append("<th>資料不一致筆數</th>");
				sb.append("</tr>");
				//表格內容
				sb.append("<tr>");
				sb.append("<td>").append(batchNo).append("</td>");
				sb.append("<td>").append(monthBatchYm).append("</td>");
				sb.append("<td>").append(fileQty).append("</td>");
				sb.append("<td>").append(filePqty).append("</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
		} else if ("N".equals(logStatus)) {//mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 
			sb.append("<p>執行狀態：無資料 </p>");
			tmpMsg.append("無資料");
		} else {// status = "F"
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr>");
			sb.append("<th>比對時間</th>");
			sb.append("<th>比對狀態</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>").append(sdf.format(excuteTime)).append("</td>");
			sb.append("<td>失敗</td>");
			sb.append("</tr>");
			sb.append("</table>");
			tmpMsg.append(errMsg);
		}
		try{
			if(filepath != null && filename != null) {
				mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
						mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims",
						new String[] { filepath },new String[] { filename });
			} else {
				mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
						mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
			}
		}
		catch(Exception e ){
			return "寄送信件發生異常";
		}
		return tmpMsg.toString();
	 }
	

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public void deleteFile(String file) {
		File delfile = new File(file);
		try {
	        FileUtils.forceDeleteOnExit(delfile);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public HasSpService getHasSpService() {
		return hasSpService;
	}

	public void setHasSpService(HasSpService hasSpService) {
		this.hasSpService = hasSpService;
	}

	public HasBatchInfoService getHasBatchInfoService() {
		return hasBatchInfoService;
	}

	public void setHasBatchInfoService(HasBatchInfoService hasBatchInfoService) {
		this.hasBatchInfoService = hasBatchInfoService;
	}

	public LionBackFileService getLionBackFileService() {
		return lionBackFileService;
	}

	public void setLionBackFileService(LionBackFileService lionBackFileService) {
		this.lionBackFileService = lionBackFileService;
	}

	public HasAgtBatchMainService getHasAgtBatchMainService() {
		return hasAgtBatchMainService;
	}

	public void setHasAgtBatchMainService(HasAgtBatchMainService hasAgtBatchMainService) {
		this.hasAgtBatchMainService = hasAgtBatchMainService;
	}

	public HasBatchMatchFetService getHasBatchMatchFetService() {
		return hasBatchMatchFetService;
	}

	public void setHasBatchMatchFetService(HasBatchMatchFetService hasBatchMatchFetService) {
		this.hasBatchMatchFetService = hasBatchMatchFetService;
	}

}
