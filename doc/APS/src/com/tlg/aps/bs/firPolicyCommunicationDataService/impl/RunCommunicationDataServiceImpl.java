package com.tlg.aps.bs.firPolicyCommunicationDataService.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPolicyCommunicationDataService.CommunicationDataService;
import com.tlg.aps.bs.firPolicyCommunicationDataService.RunCommunicationDataService;
import com.tlg.aps.vo.Aps031ExcelVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirBatchPins01DetailService;
import com.tlg.prpins.service.FirBatchPins01Service;
import com.tlg.prpins.service.FirBatchPins01SpDtlService;
import com.tlg.prpins.service.FirBatchPins01SpMainService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RunCommunicationDataServiceImpl implements RunCommunicationDataService {
	
	private static final Logger logger = Logger.getLogger(RunCommunicationDataServiceImpl.class);
	private CommunicationDataService communicationDataService;
	private FirBatchInfoService firBatchInfoService;
	private FirBatchPins01Service firBatchPins01Service;
	private FirBatchPins01DetailService firBatchPins01DetailService;
	private FirBatchPins01SpMainService firBatchPins01SpMainService;
	private FirBatchPins01SpDtlService firBatchPins01SpDtlService;
	private FirSpService firSpService;
	private ConfigUtil configUtil;
	
	private String fileName;
	private InputStream inputStream;
	
	private SimpleDateFormat spDateFormat = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat excelDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static final Integer DATACOUNT = 1000;

	@SuppressWarnings("unchecked")
	@Override
	/* 程式主流程 */
	public Result generatedata(String programId) throws Exception {
		Result result = new Result();
		// 新增執行記錄檔
		// 產生大批次號
		String batchNo = "FICB" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		FirBatchLog firBatchLog = new FirBatchLog();
		BigDecimal batchLogOid = null;
		try {
			result = this.insertFirBatchLog(batchNo, programId);
			if (result.getResObject() != null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				batchLogOid = firBatchLog.getOid();
			}

			Map<String, String> params = new HashMap<>();
			//檢核階段
			if (!checkExecutable(programId)) {
				this.updateFirBatchLog("S", " FIR_BATCH_INFO設定檔設定為排程暫停執行。", batchLogOid);
				return this.getReturnResult(" FIR_BATCH_INFO設定檔設定為排程暫停執行。");
			}
			/* 把符合條件的參數押入大批次號 */
			params.put("datastatus", "0");
			params.put("isvoid", "N");
			result = firBatchPins01Service.findFirBatchPins01ByParams(params);
			if (result.getResObject() != null) {
				List<FirBatchPins01> meetList = (List<FirBatchPins01>) result.getResObject();
				for (FirBatchPins01 firBatchPins01 : meetList) {
					firBatchPins01.setFirBatchLogBno(batchNo);
					communicationDataService.updateFirBatchPins01(firBatchPins01);
				}
			}
			params.clear();
			params.put("firBatchLogBno", batchNo);
			// 取出尚未執行過的批次號碼
			result = firBatchPins01Service.findFirBatchPins01ByParams(params);

			// 如果無符合條件的參數
			if (result.getResObject() == null) {
				this.updateFirBatchLog("S", "本日 FIR_BATCH_PINS01無參數設定，故不須執行。", batchLogOid);
				return this.getReturnResult("本日 FIR_BATCH_PINS01無參數設定，故不須執行。");
			}
			
			// 如果有符合條件的參數
			List<FirBatchPins01> bnoList = (List<FirBatchPins01>) result.getResObject();
			for (FirBatchPins01 firBatchPins01 : bnoList) {
				firBatchPins01SpMainService.truncateFirBatchPins01SpMain();
				firBatchPins01SpDtlService.truncateFirBatchPins01SpDtl();
				params.clear();
				String bno = firBatchPins01.getBno();
				params.put("bno", bno);
				// 依條件產生資料
				try {
					genDataByCondition(bno);
				} catch (Exception e) {
					logger.error("GeneratecommunicationData genDataByCondition error", e);
					firBatchPins01.setDatastatus("2");
					firBatchPins01.setDdataend(new Date());
					communicationDataService.updateFirBatchPins01(firBatchPins01);
					this.updateFirBatchLog("S", getErrMsg(e), batchLogOid);
				}

				// 依資料產生檔案
				try {
					genExcel(bno, programId);
				} catch (Exception e) {
					logger.error("GeneratecommunicationData genExcel error", e);
					firBatchPins01.setDatastatus("5");
					firBatchPins01.setDfileend(new Date());
					communicationDataService.updateFirBatchPins01(firBatchPins01);
					this.updateFirBatchLog("S", getErrMsg(e), batchLogOid);
				}
			}

			this.updateFirBatchLog("S", "", batchLogOid);
		} catch (Exception e) {
			logger.error("GeneratecommunicationData error", e);
			this.updateFirBatchLog("S", getErrMsg(e), batchLogOid);
		}
		return this.getReturnResult("執行完成");
	}
	
	/*檢核階段*/
	private boolean checkExecutable(String programId) throws Exception {
		/*判斷是否可執行批次 start*/
		Map<String,String> params = new HashMap<>();
		params.put("prgId",programId);
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()!=null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
			if(firBatchInfo.getMailTo().equals("N")){
				return false;
			}
		}
		/*判斷是否可執行批次 end*/
		/*若可執行，並為每月3日，自動寫入批次條件 start*/
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.DATE) == 3) {
			String bno = "FIC"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			FirBatchPins01 firBatchPins01 = new FirBatchPins01();
			firBatchPins01.setBno(bno);
			calendar.set(Calendar.HOUR_OF_DAY, 0);//將時分秒歸零
			calendar.set(Calendar.MINUTE, 0);  
			calendar.set(Calendar.SECOND, 0);  
			calendar.set(Calendar.DAY_OF_MONTH,0);//取上個月最後一日
			firBatchPins01.setParambasedate(calendar.getTime());
			firBatchPins01.setParamenddate(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH,1);//取上個月一日
			firBatchPins01.setParamstartdate(calendar.getTime());
			firBatchPins01.setParamriskcode("A01,B01,GA,PA,TA,HP,F02");
			firBatchPins01.setDatastatus("0");
			firBatchPins01.setDatacount(new BigDecimal(0));
			firBatchPins01.setIcreate("system");
			firBatchPins01.setDcreate(new Date());
			firBatchPins01.setIsvoid("N");
			communicationDataService.insertFirBatchPins01(firBatchPins01);
		}
		/*若可執行，並為每月3日，自動寫入批次條件 end*/
		return true;
	}
	
	/* 依條件產生資料階段 */
	private boolean genDataByCondition(String bno) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("bno", bno);
		params.put("datastatus", "0");
		params.put("isvoid", "N");
		Result result = firBatchPins01Service.findFirBatchPins01ByParams(params);
		// 查無資料表示已經產生過資料，將datastatus改為 3:產生資料成功
		if (result.getResObject() == null) {
			params.remove("datastatus");
			params.remove("isvoid");
			result = firBatchPins01Service.findFirBatchPins01ByUK(params);
			FirBatchPins01 firBatchPins01 = (FirBatchPins01) result.getResObject();
			firBatchPins01.setDatastatus("3");
			firBatchPins01.setDdataend(new Date());
			communicationDataService.updateFirBatchPins01(firBatchPins01);
			return false;
		}
		List<FirBatchPins01> resultList = (List<FirBatchPins01>) result.getResObject();
		FirBatchPins01 firBatchPins01 = resultList.get(0);
		String[] riskcodeArr = firBatchPins01.getParamriskcode().split(",");
		firBatchPins01.setDatastatus("1");
		firBatchPins01.setDdatastart(new Date());
		communicationDataService.updateFirBatchPins01(firBatchPins01);
		StringBuilder riskcodeSb = new StringBuilder();
		for (int i = 0; i < riskcodeArr.length; i++) {
			riskcodeSb.append(riskcodeArr[i]);
			firBatchPins01.setRiskcodelog(riskcodeSb.toString());
			communicationDataService.updateFirBatchPins01(firBatchPins01);
			riskcodeSb.append(callSp(firBatchPins01, riskcodeArr[i]));
			firBatchPins01.setRiskcodelog(riskcodeSb.toString());
			communicationDataService.updateFirBatchPins01(firBatchPins01);
		}
		riskcodeSb.append("done");
		firBatchPins01.setRiskcodelog(riskcodeSb.toString());
		communicationDataService.updateFirBatchPins01(firBatchPins01);
		int riskcodecount = firBatchPins01DetailService.countFirBatchPins01Detail(params);
		firBatchPins01.setDatacount(new BigDecimal(riskcodecount));
		firBatchPins01.setDatastatus("3");
		firBatchPins01.setDdataend(new Date());
		communicationDataService.updateFirBatchPins01(firBatchPins01);
		return true;
	}
	
	/*依資料產生檔案階段*/
	@SuppressWarnings("unchecked")
	private boolean genExcel(String bno, String programId) throws Exception {
		String tempFolder = configUtil.getString("tempFolder");
		File filePath = new File(tempFolder);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		Map params = new HashMap<>();
		params.put("bno", bno);
		params.put("isvoid", "N");
		Result result = firBatchPins01Service.findFirBatchPins01ByParams(params);
		if (result.getResObject() == null) {
			return false;
		}

		List<FirBatchPins01> resultList = (List<FirBatchPins01>) result.getResObject();
		FirBatchPins01 firBatchPins01 = resultList.get(0);
		firBatchPins01.setDatastatus("4");
		firBatchPins01.setDfilestart(new Date());
		communicationDataService.updateFirBatchPins01(firBatchPins01);
		
		//分次讀取資料寫入excel
		int count = firBatchPins01SpMainService.countForAps031Excel(params);
		
		int cycleTimes = count / DATACOUNT;
		if(count % DATACOUNT > 0) {
			cycleTimes = cycleTimes + 1;
		}
		int startRow = 1;
		int endRow = DATACOUNT;
		
		List<Aps031ExcelVo> excelList = new ArrayList<>();
		
		boolean status = false;
		String filename = tempFolder + bno + ".xlsx";
		File file = new File(filename);
		String[] sheetArr = { "通訊地址", "聯絡電話", "行動電話", "電子信箱" };
		List<Integer> sheetsRowList = new ArrayList<>();
		SXSSFSheet sheet0;
		try(SXSSFWorkbook workbook = new SXSSFWorkbook()) {
		//建立總表
			sheet0 = workbook.createSheet("總表");
			String[] titleArr = { "保單號碼", "險種", "險別", "簽單日期", "保單生效日", "保單到期日", "業務來源", "業務員姓名", "服務人員", "服務人員姓名",
					"關係人姓名", "關係人類型", "通訊地址", "行動電話", "聯絡電話", "電子郵件", "簽單單位", "簽單單位名稱", "業務來源名稱", "業務單位代號", "關係人身份證字號",
					"車牌" };
			SXSSFRow rowTitle = sheet0.createRow(0);
			//建立excel欄位
			for (int i = 0; i < titleArr.length; i++) {
				rowTitle.createCell(i).setCellValue(titleArr[i]);
			}
			
			//建立另外四個頁籤
			String[] titleArr2 = { "類型", "保單號碼", "險種", "險別", "簽單日期", "保單生效日", "保單到期日", "業務來源", "業務員姓名", "服務人員",
					"服務人員姓名", "關係人姓名", "關係人類型", "通訊地址", "行動電話", "聯絡電話", "電子郵件" };
			
			for (int i = 0; i < sheetArr.length; i++) {
				sheetsRowList.add(0);
				
				SXSSFSheet sheets = workbook.createSheet(sheetArr[i]);
				SXSSFRow rowTitles = sheets.createRow(0);
				for (int j = 0; j < titleArr2.length; j++) {
					rowTitles.createCell(j).setCellValue(titleArr2[j]);
				}
			}
			int totalRowNum = 0;
			for (int c = 0; c < cycleTimes; c++) {
					sheet0 = workbook.getSheetAt(0);
					params.put("startRow", startRow);
					params.put("endRow", endRow);
					result = firBatchPins01SpMainService.findForAps031ExcelByParams(params); // 一次只查詢一千筆
					if (result.getResObject() != null) {
						excelList = (List<Aps031ExcelVo>) result.getResObject();
						for (int i = 0; i < excelList.size(); i++) {
							totalRowNum++;
							SXSSFRow row = sheet0.createRow(totalRowNum); // 建立列 必須為每次迴圈筆數往上加
							row.createCell(0).setCellValue(excelList.get(i).getPolicyno());
							row.createCell(1).setCellValue(excelList.get(i).getClassname());
							row.createCell(2).setCellValue(excelList.get(i).getRiskcode());
							row.createCell(3).setCellValue(excelDateFormat.format(excelList.get(i).getUnderwriteenddate()));
							row.createCell(4).setCellValue(excelDateFormat.format(excelList.get(i).getStartdate()));
							row.createCell(5).setCellValue(excelDateFormat.format(excelList.get(i).getEnddate()));
							row.createCell(6).setCellValue(excelList.get(i).getBusinessnature());
							row.createCell(7).setCellValue(excelList.get(i).getHandlername());
							row.createCell(8).setCellValue(excelList.get(i).getHandler1code());
							row.createCell(9).setCellValue(excelList.get(i).getHandler1name());
							row.createCell(10).setCellValue(excelList.get(i).getInsuredname());
							row.createCell(11).setCellValue(excelList.get(i).getInsuredflag());
							row.createCell(12).setCellValue(excelList.get(i).getPostaddress());
							row.createCell(13).setCellValue(excelList.get(i).getMobile());
							row.createCell(14).setCellValue(excelList.get(i).getPhonenumber());
							row.createCell(15).setCellValue(excelList.get(i).getEmail());
							row.createCell(16).setCellValue(excelList.get(i).getComcode());
							row.createCell(17).setCellValue(excelList.get(i).getComcname());
							row.createCell(18).setCellValue(excelList.get(i).getCodecname());
							row.createCell(19).setCellValue(excelList.get(i).getExtracomcode());
							row.createCell(20).setCellValue(excelList.get(i).getIdentifynumber());
							row.createCell(21).setCellValue(excelList.get(i).getLicenseno());
						}
						status = true;
					}

					for (int i = 0; i < sheetArr.length; i++) {
						if (excelList != null) {
							String type = "";
							if (sheetArr[i].equals("通訊地址")) {
								type = "addr";
							} else if (sheetArr[i].equals("聯絡電話")) {
								type = "phone";
							} else if (sheetArr[i].equals("行動電話")) {
								type = "mobile";
							} else if (sheetArr[i].equals("電子信箱")) {
								type = "email";
							}
							for (int j = 0; j < excelList.size(); j++) {
								// 判斷若無四種type資料則不新增Excel
								if (!isCreateRow(type, excelList.get(j))) {
									continue;
								}
								SXSSFRow row = workbook.getSheetAt(i + 1).createRow(sheetsRowList.get(i) + 1); // 建立列
								row.createCell(0).setCellValue(type);
								row.createCell(1).setCellValue(excelList.get(j).getPolicyno());
								row.createCell(2).setCellValue(excelList.get(j).getClassname());
								row.createCell(3).setCellValue(excelList.get(j).getRiskcode());
								row.createCell(4).setCellValue(excelDateFormat.format(excelList.get(j).getUnderwriteenddate()));
								row.createCell(5).setCellValue(excelDateFormat.format(excelList.get(j).getStartdate()));
								row.createCell(6).setCellValue(excelDateFormat.format(excelList.get(j).getEnddate()));
								row.createCell(7).setCellValue(excelList.get(j).getBusinessnature());
								row.createCell(8).setCellValue(excelList.get(j).getHandlername());
								row.createCell(9).setCellValue(excelList.get(j).getHandler1code());
								row.createCell(10).setCellValue(excelList.get(j).getHandler1name());
								row.createCell(11).setCellValue(excelList.get(j).getInsuredname());
								row.createCell(12).setCellValue(excelList.get(j).getInsuredflag());
								if (type.equals("addr")) {
									row.createCell(13).setCellValue(excelList.get(j).getPostaddress());
								}
								if (type.equals("mobile")) {
									row.createCell(14).setCellValue(excelList.get(j).getMobile());
								}
								if (type.equals("phone")) {
									row.createCell(15).setCellValue(excelList.get(j).getPhonenumber());
								}
								if (type.equals("email")) {
									row.createCell(16).setCellValue(excelList.get(j).getEmail());
								}
								sheetsRowList.set(i, sheetsRowList.get(i) + 1);
							}
						}
					}
					startRow += DATACOUNT;
					endRow += DATACOUNT;
			}
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			workbook.dispose();
			
			fos.flush();
			fos.close();
		}

		FileUploadResponseVo fileUploadResponseVo = uploadFile(file, bno, programId);
		if (fileUploadResponseVo.getStatus().equals("Y")
				&& !StringUtil.isSpace(fileUploadResponseVo.getUploadOid())) {
			firBatchPins01.setUploadoid(fileUploadResponseVo.getUploadOid());
			communicationDataService.updateFirBatchPins01(firBatchPins01);
			status = true;
		} else if (!StringUtil.isSpace(fileUploadResponseVo.getMessage())) {
			firBatchPins01.setUploadoiderrmes(fileUploadResponseVo.getMessage().length() > 300
					? fileUploadResponseVo.getMessage().substring(300)
					: fileUploadResponseVo.getMessage());
			communicationDataService.updateFirBatchPins01(firBatchPins01);
			status = false;
		}
		// 不管有無資料都要更新批次狀態
		firBatchPins01.setDatastatus("6");
		firBatchPins01.setDfileend(new Date());
		communicationDataService.updateFirBatchPins01(firBatchPins01);
		
		return status;
	}
	
	private boolean isCreateRow(String type, Aps031ExcelVo data) throws Exception {
		boolean status = true;
		if(type.equals("addr") && StringUtil.isSpace(data.getPostaddress())) {
			status = false;
		}
		if(type.equals("phone") && StringUtil.isSpace(data.getPhonenumber())) {
			status = false;
		}
		if(type.equals("mobile") && StringUtil.isSpace(data.getMobile())) {
			status = false;
		}
		if(type.equals("email") && StringUtil.isSpace(data.getEmail())) {
			status = false;
		}
		return status;
	}
	
	private FileUploadResponseVo uploadFile(File file, String bno, String programId) {
		FileUploadResponseVo fileUploadResponseVo = null;
		try{
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			fileUploadResponseVo = ftsutil.uploadFile(file.getAbsolutePath(), programId, "0", bno);
		} catch (Exception e) {
			e.printStackTrace();
			return fileUploadResponseVo;
		}finally{
			try {
				Files.delete(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileUploadResponseVo;
	}
	
	private String callSp(FirBatchPins01 firBatchPins01, String riskcode) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("inBatchNo", firBatchPins01.getBno());
		params.put("inSdate", spDateFormat.format(firBatchPins01.getParamstartdate()));
		params.put("inEdate", spDateFormat.format(firBatchPins01.getParamenddate()));
		params.put("inBdate", spDateFormat.format(firBatchPins01.getParambasedate()));
		params.put("inRiskcode", riskcode);
		params.put("inUser", firBatchPins01.getIcreate());
		params.put("outResult", null);
		int returnValue = firSpService.runSpPins01GenCdata(params);				
		if(returnValue != 0) {
			return "失敗,";
		}
		return "成功,";
	}
	
	private Result insertFirBatchLog(String batchNo, String programId) throws Exception{
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus("1");
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setIcreate("system");
		firBatchLog.setDcreate(new Date());
		return communicationDataService.insertFirBatchLog(firBatchLog);
	}
	
	private void updateFirBatchLog(String status, String remark, BigDecimal oid) throws Exception{
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		firBatchLog.setIupdate("system");
		firBatchLog.setDupdate(new Date());
		firBatchLog.setOid(oid);
		communicationDataService.updateFirBatchLog(firBatchLog);
	}
	
	private String getErrMsg(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public CommunicationDataService getCommunicationDataService() {
		return communicationDataService;
	}

	public void setCommunicationDataService(CommunicationDataService communicationDataService) {
		this.communicationDataService = communicationDataService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public FirBatchPins01Service getFirBatchPins01Service() {
		return firBatchPins01Service;
	}

	public void setFirBatchPins01Service(FirBatchPins01Service firBatchPins01Service) {
		this.firBatchPins01Service = firBatchPins01Service;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirBatchPins01DetailService getFirBatchPins01DetailService() {
		return firBatchPins01DetailService;
	}

	public void setFirBatchPins01DetailService(FirBatchPins01DetailService firBatchPins01DetailService) {
		this.firBatchPins01DetailService = firBatchPins01DetailService;
	}

	public FirBatchPins01SpMainService getFirBatchPins01SpMainService() {
		return firBatchPins01SpMainService;
	}

	public void setFirBatchPins01SpMainService(FirBatchPins01SpMainService firBatchPins01SpMainService) {
		this.firBatchPins01SpMainService = firBatchPins01SpMainService;
	}

	public FirBatchPins01SpDtlService getFirBatchPins01SpDtlService() {
		return firBatchPins01SpDtlService;
	}

	public void setFirBatchPins01SpDtlService(FirBatchPins01SpDtlService firBatchPins01SpDtlService) {
		this.firBatchPins01SpDtlService = firBatchPins01SpDtlService;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
}
