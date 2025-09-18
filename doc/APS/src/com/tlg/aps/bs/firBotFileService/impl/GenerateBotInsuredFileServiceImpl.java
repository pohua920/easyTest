package com.tlg.aps.bs.firBotFileService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBotFileService.GenerateBotInsuredFileService;
import com.tlg.aps.bs.firPanhsinFeedbackFile.ProcessPanhsinFileService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtBotAp;
import com.tlg.prpins.entity.FirAgtBotFh;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtBotApService;
import com.tlg.prpins.service.FirAgtBotFhService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class GenerateBotInsuredFileServiceImpl implements GenerateBotInsuredFileService {
	
	private ProcessPanhsinFileService processPanhsinFileService;
	private FirBatchInfoService firBatchInfoService;
	private FirSpService firSpService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtBotApService firAgtBotApService;
	private FirAgtBotFhService firAgtBotFhService;
	
	private ConfigUtil configUtil;
	
	@Override
	public Result RunToGenerateFiles(String userId,Date excuteTime,String programId) throws Exception {
		Result result = new Result();
		FirBatchLog firBatchLog = null;
		try {
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
			String type = programId.substring(12,14);
			String batchNo = "BOT_"+type+"_" + new SimpleDateFormat("yyMMddHHmm").format(new Date());
			if(!StringUtil.isSpace(sb.toString())) {
				result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),batchNo);
				if(result.getResObject()!=null) {
					firBatchLog = (FirBatchLog) result.getResObject();
					if(!sendEmail(firBatchLog.getBatchNo(),excuteTime,"F",sb.toString(),null,programId)) {
						processPanhsinFileService.updateFirBatchLog("F", "無法取得FIR_BATCH_INFO資料，無法寄送MAIL", userId, firBatchLog);
					}
				}
				return this.getReturnResult("接收參數無值，結束排程");
				//結束排程
			}
			String tmpStatus = "1";
			String msg="";
			Map<String,String> params = new HashMap<>();
			params.put("prgId", programId+"_STATUS");
			result = firBatchInfoService.findFirBatchInfoByUK(params);
			
			if(result.getResObject()!=null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
				if(firBatchInfo.getMailTo().equals("N")) {
					tmpStatus = "S";
					msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
				}
			}
			result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId, tmpStatus, msg, batchNo);
			if(tmpStatus.equals("S")) {
				return this.getReturnResult("查詢狀態為N，不執行排程");
			}
			
			if(result.getResObject()!=null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				//執行SP
				FirAgtBatchMain firAgtBatchMain = callSp(firBatchLog.getBatchNo(),userId,programId);
				tmpStatus = firAgtBatchMain.getTempStatus();
				msg = firAgtBatchMain.getTempMsg();
				//產生檔案及上傳FTP SP執行完有資料才產生檔案及上傳   無資料:tmpStatus="N" 失敗:tmpStatus="F" 成功:tmpStatus="S"
				if("S".equals(tmpStatus)) {
					Map<String,String> resultMap = generateFileAndUpload(batchNo,userId,programId);
					if("F".equals(resultMap.get("outStatus"))) {
						tmpStatus = "F";
						msg = "產生"+type+"檔或上傳FTP失敗:"+resultMap.get("errMsg");
					}
				}
				
				processPanhsinFileService.updateFirBatchLog(tmpStatus, msg, userId, firBatchLog);
				if(!sendEmail(firBatchLog.getBatchNo(), excuteTime, tmpStatus, msg,firAgtBatchMain,programId)) {
					processPanhsinFileService.updateFirBatchLog(tmpStatus, "無法取得FIR_BATCH_INFO資料，無法寄送MAIL", userId, firBatchLog);
				}	
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(firBatchLog!=null) {
				processPanhsinFileService.updateFirBatchLog("F", e.toString(), userId, firBatchLog);
				sendEmail(firBatchLog.getBatchNo(),excuteTime,"F",e.toString(),null,programId);
			}
			return this.getReturnResult("執行失敗!" + e.toString());
		}
		return this.getReturnResult("執行完成!");
	}
	
	private FirAgtBatchMain callSp(String batchNo, String userId, String programId) {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("inBatchNo", batchNo);
			params.put("inUser", userId);
			params.put("outResult", null);
			int returnValue = 0;
			if(programId.equals("FIR_AGT_BOT_AP")) {
				returnValue = firSpService.runSpFirAgtBotAp(params);				
			}else if(programId.equals("FIR_AGT_BOT_FH")) {
				returnValue = firSpService.runSpFirAgtBotFh(params);	
			}
			if(returnValue != 0) {
				firAgtBatchMain.setTempStatus("F");
				firAgtBatchMain.setTempMsg("執行SP失敗(SP_"+programId+")");
				return firAgtBatchMain;
			}
			params.clear();
			params.put("batchNo", batchNo);
			Result result = firAgtBatchMainService.findFirAgtBatchMainByUk(params);
			if(result.getResObject()!=null) {
				firAgtBatchMain = (FirAgtBatchMain) result.getResObject();
				if(firAgtBatchMain.getFileStatus().equals("Z")) {
					firAgtBatchMain.setTempStatus("N");//檔案無資料
				}else {
					firAgtBatchMain.setTempStatus("S");
				}
				firAgtBatchMain.setTempMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			firAgtBatchMain.setTempStatus("F");
			firAgtBatchMain.setTempMsg("執行SP失敗(SP_"+programId+")");
		}
		return firAgtBatchMain;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,String> generateFileAndUpload(String batchNo,String userId,String programId) throws Exception{
		String filetype = programId.substring(12,14);
		Map params = new HashMap();
		String fileContent = "";
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setBusinessnature("I99004");
		firAgtBatchMain.setIupdate(userId);
		firAgtBatchMain.setDupdate(new Date());
		firAgtBatchMain.setFileStatus("E");
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("outStatus", "S");
		File file = null;
		try {
			if(filetype.equals("AP")) {
				params.put("batchType", "01");
				fileContent = genBotApFile(batchNo);
			}else {
				params.put("batchType", "03");
				fileContent = genBotFhFile(batchNo);
			}
			String filename = getFileName(params, filetype, userId, batchNo);
			
			File filePath = new File(configUtil.getString("tempFolder"));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			
			String outputFile = filePath + File.separator + filename;
			file = new File(outputFile);
			String msg = genFile(batchNo,fileContent,userId, file);
			
			//檔案產生失敗
			if(!StringUtil.isSpace(msg)) {
				msg = "產生檔案失敗-" + msg;
				firAgtBatchMain.setRemark(msg);
				firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
				resultMap.put("outStatus", "F");
				resultMap.put("errMsg", msg);
				//檔案上傳失敗不繼續上傳sftp
				return resultMap;
			}
			
			//上傳sftp 
//			if(uploadFileToSftp(outputFile)) {
//				resultMap.put("outStatus", "F");
//				resultMap.put("errMsg", "傳送FTP失敗");
//				firAgtBatchMain.setRemark("傳送FTP失敗");
//				firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
//				return resultMap;
//			}
			
			//將temp檔案刪除
			if(file.exists()){
				file.delete();
			}
			
			//檔案產生成功、上傳sftp成功，再更新main檔
			firAgtBatchMain.setFileStatus("Y");
			firAgtBatchMain.setFileName(filename);
			firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "產生檔案失敗-" + e.toString();
			firAgtBatchMain.setRemark(msg);
			firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
			resultMap.put("outStatus", "F");
			resultMap.put("errMsg", msg);
			return resultMap;
		}
		return resultMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getFileName(Map params, String filetype, String userId ,String batchNo) throws SystemException, Exception {
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date dcreate = new Date();
		params.put("businessnature", "I99004");
		if(!userId.equals("SYSTEM")) {
			params.put("batchNo", batchNo);
			result = firAgtBatchMainService.findFirAgtBatchMainByParams(params);
			List<FirAgtBatchMain> resultList = (List<FirAgtBatchMain>)result.getResObject();
			dcreate = resultList.get(0).getDcreate();
			params.remove("batchNo");
		}
		String filename = "18"+filetype+"X"+new SimpleDateFormat("MMdd").format(dcreate)+"001.txt";
		
		params.put("dcreate", sdf.parse(sdf.format(dcreate)));
		result = firAgtBatchMainService.findBotMaxFilenameByParams(params);
		
		if(result !=null && result.getResObject()!=null) {
			String maxfilename = (String) result.getResObject();
			String serialNum = String.valueOf(Integer.valueOf(maxfilename.substring(9,12))+1);
			while(serialNum.length()<3) {
				serialNum = "0" + serialNum;
			}
			filename = maxfilename.substring(0,9)+serialNum+".txt";
		}
		return filename;
	}
	
	public String genFile(String batchNo, String fileContent,String userId, File file){
		FileOutputStream fileOutputStream = null;
		BufferedWriter bufWriter = null;
		try {
			fileOutputStream = new FileOutputStream(file, false);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "big5"));
			bufWriter.write(fileContent);
			bufWriter.close();
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(file.getAbsolutePath(), userId, "F", batchNo);
			if(fileUploadResponseVo.getStatus().equals("N")) {
				return fileUploadResponseVo.getMessage();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}finally {
			try {
				bufWriter.close();				
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	private boolean uploadFileToSftp(String filePath){
		boolean result = false;
		String sftpHost = configUtil.getString("botFTP");
		String sftpUser = configUtil.getString("botFtpUserPut");
		String sftpPwd = configUtil.getString("botFptPwdPut");
		int sftpPort = Integer.parseInt(configUtil.getString("botFtpPort"));
		String remoteDir = configUtil.getString("botRemotePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private boolean sendEmail(String batchNo,Date excuteTime,String status,String errMsg,FirAgtBatchMain firAgtBatchMain,String programId) {
		Mailer mailer = new Mailer();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("prgId", programId);
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result.getResObject() == null) {
				return false;
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
				sb.append("<p>預計筆數：" + firAgtBatchMain.getFileQty() + "</p>");
				sb.append("<p>處理筆數：" + firAgtBatchMain.getFilePqty() + "</p>");
				params.clear();
				params.put("batchNo", batchNo);
				
				result = firAgtBatchDtlService.findFirAgtBatchDtlByParams(params);
				if(result.getResObject()!=null) {
					List<FirAgtBatchDtl> firAgtBatchDtlList = (List<FirAgtBatchDtl>) result.getResObject();
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>受理編號</td>");
					sb.append("<td>狀態</td>");
					sb.append("<td>對應核心單號</td>");
					sb.append("<td>險別</td>");
					sb.append("<td>資料來源</td>");
					sb.append("</tr>");
					for(FirAgtBatchDtl firAgtBatchDtl:firAgtBatchDtlList) {
						sb.append("<tr>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getOrderseq()) + "</td>");
						String orderseqStatus = "";
						if(firAgtBatchDtl.getOrderseqStatus().equals("00")) {
							orderseqStatus = "未處理";
						}else if(firAgtBatchDtl.getOrderseqStatus().equals("01")) {
							orderseqStatus = "資料產生成功";
						}else {
							orderseqStatus = "未定義";
						}
						sb.append("<td>" + orderseqStatus + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getCoreNo()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getRiskcode()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getDataSource().equals("1")?"新核心":"AS400") + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}else {
					sb.append("<p> FIR_AGT_BATCH_DTL批次明細檔查無資料，請洽系統人員。</p>");
				}
				
			}else if (status.equals("N")) {
				sb.append("<p>執行狀態：無資料 </p>");
			}else {//status = "F"
				sb.append("<p>執行狀態：" +status+ "</p>");
				sb.append("<p>異常訊息：" +errMsg+ "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, 
					"newims@ctbcins.com", "",mailTo, "", mailCc, "", "", "", sb.toString(),
					"smtp","newims", "2012newims");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public String genBotApFile(String batchNo) throws Exception{
		Map<String,String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firAgtBotApService.findFirAgtBotApByParams(params);
		StringBuilder sb = new StringBuilder();
		if(result!=null && result.getResObject()!=null) {
			List<FirAgtBotAp> dataList = (List<FirAgtBotAp>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(FirAgtBotAp firAgtBotAp : dataList) {
				sb.append(firAgtBotAp.getCompanyno())//1保險公司代號
				.append(firAgtBotAp.getRiskcodeBot())//2商品(險種)
				.append(transLength(firAgtBotAp.getOrderseq(),"s",20))//3 受理編號
				.append(transLength("","s",20))//4 保單號(不處理)
				.append(transLength(firAgtBotAp.getStartdateF()==null?"":sdf.format(firAgtBotAp.getStartdateF()),"s",8))//5 保單生效日_火附
				.append(transLength(firAgtBotAp.getEnddateF()==null?"":sdf.format(firAgtBotAp.getEnddateF()),"s",8))//6 保單到期日_火附
				.append(transLength(firAgtBotAp.getStartdateQ()==null?"":sdf.format(firAgtBotAp.getStartdateQ()),"s",8))//7 保單生效日_地震
				.append(transLength(firAgtBotAp.getEnddateQ()==null?"":sdf.format(firAgtBotAp.getEnddateQ()),"s",8))//8 保單到期日_地震
				.append(transLength(firAgtBotAp.getAppliId(),"s",10))//9 要保人ID 
				.append(transLength(StringUtil.nullToSpace(firAgtBotAp.getAppliname()),"s",100))//10 要保人姓名
				.append(transLength(firAgtBotAp.getInsuredId(),"s",10))//11 被保險人ID
				.append(transLength(StringUtil.nullToSpace(firAgtBotAp.getInsuredname()),"s",100))//12 被保險人姓名
				.append(transLength(firAgtBotAp.getAppliPostcode(),"s",5))//13 要保人郵遞區號
				.append(transLength(firAgtBotAp.getAppliAddress(),"s",120))//14 要保人通訊地址
				.append(transLength(firAgtBotAp.getExtracomcode(),"s",22))//15 通路三階(台銀分行)
				.append(transLength(firAgtBotAp.getOrgicode(),"s",22))//16 通路四階(台銀業務員)
				.append(transLength(firAgtBotAp.getBankno(),"s",7))//17 抵押行庫(金資代號)
				.append(transLength(firAgtBotAp.getSumfloors(),"n",3))//18 總樓層
				.append(transLength(firAgtBotAp.getWallno(),"s",2))//19 建物構造(主要建材)
				.append(transLength(firAgtBotAp.getRoofno(),"s",2))//20 建物屋頂(屋頂材料)
				.append(transLength(firAgtBotAp.getBuildarea()==null?"":firAgtBotAp.getBuildarea().toString(),"n",11))//21 建築面積(坪)
				.append(transLength(StringUtil.nullToSpace(firAgtBotAp.getBuildyears()),"s",4))//22 建築年份(西元年)
				.append(transLength(StringUtil.nullToSpace(firAgtBotAp.getPropAddress()),"s",120))//23 建築地址
				.append(transLength(firAgtBotAp.getAmountF()==null?"":firAgtBotAp.getAmountF().toString(),"n",12))//24 火險保額
				.append(transLength(firAgtBotAp.getAmountQ()==null?"":firAgtBotAp.getAmountQ().toString(),"n",12))//25 地震險保額
				.append(transLength(firAgtBotAp.getPremiumF()==null?"":firAgtBotAp.getPremiumF().toString(),"n",9))//26 火險保費
				.append(transLength(firAgtBotAp.getPremiumA()==null?"":firAgtBotAp.getPremiumA().toString(),"n",9))//27 附加險保費
				.append(transLength(firAgtBotAp.getPremiumQ()==null?"":firAgtBotAp.getPremiumQ().toString(),"n",9))//28 地震險保費
				.append(transLength(firAgtBotAp.getCommrateBargain()==null?"":firAgtBotAp.getCommrateBargain().toString(),"n",9))//29 議價佣率
				.append(transLength(StringUtil.nullToSpace(firAgtBotAp.getReasonBargain()),"s",100))//30 議價原因
				.append(firAgtBotAp.getAutoRenew())//31 約定續保
				.append(transLength(firAgtBotAp.getEndorDate()==null?"":sdf.format(firAgtBotAp.getEndorDate()),"s",8))//32 批單申請日
				.append(transLength(firAgtBotAp.getAppliBirthday()==null?"":sdf.format(firAgtBotAp.getAppliBirthday()),"s",8))//33 要保人生日
				.append(transLength(firAgtBotAp.getAppliPhone(),"s",20))//34 要保人電話
				.append(transLength(firAgtBotAp.getInsuredBirthday()==null?"":sdf.format(firAgtBotAp.getInsuredBirthday()),"s",8))//35 被保險人生日
				.append(transLength(firAgtBotAp.getInsuredPhone(),"s",20))//36 被保險人電話
				.append(transLength(firAgtBotAp.getInsuredMail(),"s",50))//37 電子信箱
				.append(transLength(firAgtBotAp.getInsuredAddress(),"s",90))//38 被保險人地址
				.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String genBotFhFile(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firAgtBotFhService.findFirAgtBotFhByParams(params);
		StringBuilder sb = new StringBuilder();
		if(result!=null && result.getResObject()!=null) {
			List<FirAgtBotFh> dataList = (List<FirAgtBotFh>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(FirAgtBotFh firAgtBotFh : dataList) {
				sb.append(firAgtBotFh.getCompanyno())//1保險公司代號
				.append(firAgtBotFh.getRiskcodeBot())//2商品(險種)
				.append(firAgtBotFh.getDataType())//3 識別碼
				.append(transLength(firAgtBotFh.getOrderseq(),"s",20))//4 受理編號
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getOldpolicyno()),"s",20))//5 前保單號碼
				.append(transLength(firAgtBotFh.getLtFireYear()==null?"":firAgtBotFh.getLtFireYear().toString(),"n",3))//6 長火批加基本地震年期
				.append(transLength(firAgtBotFh.getPolicyno(),"s",20))//7 保單號碼
				.append(transLength(firAgtBotFh.getEndorseOrder()==null?"":firAgtBotFh.getEndorseOrder().toString(),"n",3))//8 批單順序
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getEndorseno()),"s",20))//9 批單號碼
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getAutoRenew()),"s",1))//10 約定續保
				.append(transLength(firAgtBotFh.getUnderwriteenddate()==null?"":sdf.format(firAgtBotFh.getUnderwriteenddate()),"s",8))//11 簽單日期
				.append(transLength(firAgtBotFh.getStartdateF()==null?"":sdf.format(firAgtBotFh.getStartdateF()),"s",8))//12 保單生效日_火附
				.append(transLength(firAgtBotFh.getEnddateF()==null?"":sdf.format(firAgtBotFh.getEnddateF()),"s",8))//13 保單到期日_火附
				.append(transLength(firAgtBotFh.getStartdateQ()==null?"":sdf.format(firAgtBotFh.getStartdateQ()),"s",8))//14 保單生效日_地震
				.append(transLength(firAgtBotFh.getEnddateQ()==null?"":sdf.format(firAgtBotFh.getEnddateQ()),"s",8))//15 保單到期日_地震
				.append(transLength(firAgtBotFh.getAppliId(),"s",10))//16 要保人ID 
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getAppliname()),"s",100))//17 要保人姓名
				.append(transLength(firAgtBotFh.getInsuredId(),"s",10))//18 被保險人ID
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getInsuredname()),"s",100))//19 被保險人姓名
				.append(transLength(firAgtBotFh.getAppliPostcode(),"s",5))//20  要保人郵遞區號
				.append(transLength(firAgtBotFh.getAppliAddress(),"s",120))//21 要保人通訊地址
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getAppliPhone1()),"s",20))//22 要保人通訊電話
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getExtra2()),"s",22))//23 通路二階
				.append(transLength(firAgtBotFh.getExtracomcode(),"s",22))//24 通路三階(台銀分行)
				.append(transLength(firAgtBotFh.getOrgicode(),"s",22))//25 通路四階(台銀業務員)
				.append(transLength(firAgtBotFh.getBankno(),"s",7))//26 抵押行庫(金資代號)
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getAccount1()),"s",20))//27 放款帳號
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getAccount2()),"s",20))//28 委扣帳號
				.append(transLength(firAgtBotFh.getSumfloors(),"n",3))//29 總樓層
				.append(transLength(firAgtBotFh.getWallno(),"s",2))//30 建物構造(主要建材)
				.append(transLength(firAgtBotFh.getRoofno(),"s",2))//31 建物屋頂(屋頂材料)
				.append(transLength(firAgtBotFh.getBuildarea()==null?"":firAgtBotFh.getBuildarea().toString(),"n",11))//32 建築面積(坪)
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getBuildyears()),"s",4))//33 建築年份(西元年)
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getPropAddress()),"s",120))//34 建築地址
				.append(transLength(firAgtBotFh.getAmountF()==null?"":firAgtBotFh.getAmountF().toString(),"n",12))//35 火險保額
				.append(transLength(firAgtBotFh.getAmountQ()==null?"":firAgtBotFh.getAmountQ().toString(),"n",12))//36 地震險保額
				.append(transLength(firAgtBotFh.getPremiumF()==null?"":firAgtBotFh.getPremiumF().toString(),"n",9))//37 火險保費
				.append(transLength(firAgtBotFh.getPremiumA()==null?"":firAgtBotFh.getPremiumA().toString(),"n",9))//38 附加險保費
				.append(transLength(firAgtBotFh.getPremiumQ()==null?"":firAgtBotFh.getPremiumQ().toString(),"n",9))//39 地震險保費
				.append(transLength(firAgtBotFh.getCommF()==null?"":firAgtBotFh.getCommF().toString(),"n",9))//40 火險佣金
				.append(transLength(firAgtBotFh.getCommA()==null?"":firAgtBotFh.getCommA().toString(),"n",9))//41 附加險佣金
				.append(transLength(firAgtBotFh.getCommQ()==null?"":firAgtBotFh.getCommQ().toString(),"n",9))//42 地震險佣金
				.append(transLength(firAgtBotFh.getServiceFee()==null?"":firAgtBotFh.getServiceFee().toString(),"n",9))//43 服務費用
				.append(transLength(StringUtil.nullToSpace(firAgtBotFh.getPaymentNo()),"s",20))//44 銷帳編號
				.append(transLength(firAgtBotFh.getPremiumTotal()==null?"":firAgtBotFh.getPremiumTotal().toString(),"n",9))//45 應繳總金額
				.append(transLength(firAgtBotFh.getAppliBirthday()==null?"":sdf.format(firAgtBotFh.getAppliBirthday()),"s",8))//46 要保人生日
				.append(transLength(firAgtBotFh.getAppliPhone2(),"s",20))//47 要保人電話
				.append(transLength(firAgtBotFh.getInsuredBirthday()==null?"":sdf.format(firAgtBotFh.getInsuredBirthday()),"s",8))//48 被保險人生日
				.append(transLength(firAgtBotFh.getInsuredPhone(),"s",20))//49 被保險人電話
				.append(transLength(firAgtBotFh.getInsuredMail(),"s",50))//50 電子信箱
				.append(transLength(firAgtBotFh.getInsuredAddress(),"s",90))//51 被保險人地址
				.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	public String transLength(String value, String type, int length) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
			int byteLength = value.getBytes("big5").length;
			//文字長度不足右邊補空白
			if(type.equals("s")) {
				sb.append(value);
				while(byteLength<length) {
					byteLength++;
					sb.append(" ");
				}
			}
			//數字長度不足左邊補空白
			if(type.equals("n")) {
				while(byteLength<length) {
					byteLength++;
					sb.append(" ");
				}
				sb.append(value);
			}
		return sb.toString();
	}

	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
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

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ProcessPanhsinFileService getProcessPanhsinFileService() {
		return processPanhsinFileService;
	}

	public void setProcessPanhsinFileService(ProcessPanhsinFileService processPanhsinFileService) {
		this.processPanhsinFileService = processPanhsinFileService;
	}

	public FirAgtBotApService getFirAgtBotApService() {
		return firAgtBotApService;
	}

	public void setFirAgtBotApService(FirAgtBotApService firAgtBotApService) {
		this.firAgtBotApService = firAgtBotApService;
	}

	public FirAgtBotFhService getFirAgtBotFhService() {
		return firAgtBotFhService;
	}

	public void setFirAgtBotFhService(FirAgtBotFhService firAgtBotFhService) {
		this.firAgtBotFhService = firAgtBotFhService;
	}
}
