package com.tlg.aps.bs.firBotRenewalFileService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.firBotRenewalFileService.FirBotGenFileService;
import com.tlg.aps.bs.firGenRenewListService.FirRenewListGenFileService;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.RptFir00103ResultVo;
import com.tlg.aps.vo.rpt.Fir00116RequestVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchBot;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirRenewPhone;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchBotService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirRenewPhoneService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBotGenFileServiceImpl implements FirBotGenFileService {

	private static final Logger logger = Logger.getLogger(FirBotGenFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private RfrcodeService rfrcodeService;
	private FirAgtrnBatchBotService firAgtrnBatchBotService;
	private FirRenewPhoneService firRenewPhoneService;
	private FirRenewListGenFileService firRenewListGenFileService;
	private FirSpService firSpService;
	private FirBatchInfoService firBatchInfoService;

	// mantis：FIR0706，處理人員：DP0714，APS_台銀N+2產生之RE檔-APS系統主動抛轉台銀SFTP調整 (全覆蓋) -- start
	//產生提供臺銀RE檔 TXT
	@SuppressWarnings("unchecked")
	@Override
	public Result genReFile(String batchNo, String userId) throws Exception {
		
		//取得臺銀對應外牆、屋頂對應代碼
		Map<String, String> params = new HashMap<>();
		params.put("codetype","WallNo_BOT");
		Result result = rfrcodeService.findRfrcodeByParams(params);
		List<Rfrcode> wallList = (List<Rfrcode>) result.getResObject();
		Map<String, String> wallMap = new HashMap<>();
		for(Rfrcode rfrcode: wallList) {
			wallMap.put(rfrcode.getCodecode(), rfrcode.getMappedcode());
		}
		
		params.clear();
		params.put("codetype","RoofNo_BOT");
		result = rfrcodeService.findRfrcodeByParams(params);
		List<Rfrcode> roofList = (List<Rfrcode>) result.getResObject();
		Map<String, String> roofMap = new HashMap<>();
		for(Rfrcode rfrcode: roofList) {
			roofMap.put(rfrcode.getCodecode(), rfrcode.getMappedcode());
		}
		
		//開始產生檔案
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = "18REX" + getDateFormat(new Date(),"yyyyMMdd") + "001.txt";
		String content = "";
		StringBuilder sb = new StringBuilder();
		File file = null;

		String reBno = batchNo + "_RE";
		try {
			params = new HashMap<>();
			params.put("batchNo", batchNo);
			result = firAgtTocoreMainService.findBotReFileDataByParams(params);
			if(result.getResObject() != null) {
				List<Aps055BotGenFileVo> reList =  (List<Aps055BotGenFileVo>) result.getResObject();
				for(Aps055BotGenFileVo reData : reList) {
					String sdate = transLength(reData.getStartdate() != null ? getDateFormat(reData.getStartdate(), "yyyyMMdd"):"", 8,"s");
					String edate = transLength(reData.getEnddate() != null ? getDateFormat(reData.getEnddate(), "yyyyMMdd"):"", 8,"s");
					//建築年份處理
					String buildyears = reData.getBuildyears() != null ? String.valueOf(Integer.valueOf(reData.getBuildyears())+1911): "";
					sb.append("18")//保險公司代號
					.append(transLength(reData.getTemp1(),2,"s"))//險種
					.append(transLength(reData.getOrderseq(),20,"s"))//受理編號
					.append(transLength(reData.getOldpolicyno(),20,"s"))//前保單號碼
					.append("000")//長火批加基本地震續保年期
					.append(transLength(reData.getIsAutoRenew(),1,"s"))//約定續保
					.append(sdate)//保單生效日(火、附)
					.append(edate)//保單到期日(火、附)
					.append(sdate)//保單生效日(地震)
					.append(edate)//保單到期日(地震)
					.append(transLength(reData.getIdA(),10,"s"))//要保人ID
					.append(transLength(reData.getNameA(),100,"s"))//要保人姓名
					.append(transLength(reData.getIdI(),10,"s"))//被保人ID
					.append(transLength(reData.getNameI(),100,"s"))//被保人姓名
					.append(transLength(reData.getPostcodeA(),5,"s"))//郵遞區號
					.append(transLength(reData.getPostaddressA(),120,"s"))//通訊地址
					.append(transLength(reData.getPhoneA(),20,"s"))//通訊電話
					.append(transLength("",22,"s"))//通路二階 不處理
					.append(transLength(reData.getExtracomcode(),22,"s")) //通路三階(臺銀分行)
					.append(transLength(reData.getTemp2(),22,"s")) //通路四階(台銀業務員)
					.append(transLength(reData.getExtracomcode(),7,"s"))//抵押行庫(金資代號)
					.append(transLength("",20,"s"))//放款帳號(不處理)
					.append(transLength("",20,"s"))//委扣帳號(不處理)
					.append(transLength(reData.getSumfloors(),3,"n"))//總樓層
					.append(transLength(wallMap.get(reData.getWallmaterial()), 2, "s"))//外牆
					.append(transLength(roofMap.get(reData.getRoofmaterial()), 2, "s"))//屋頂
					.append(transLength(reData.getBuildarea(),11,"n"))//建築面積
					.append(transLength(buildyears,4,"s"))//建築年分
					.append(transLength(reData.getAddresscode()+" "+reData.getAddressdetailinfo(),120,"s"))//建築地址
					.append(transLength(reData.getAmountF(),12,"n"))//火險保額
					.append(transLength(reData.getAmountQ(),12,"n"))//地震保額
					.append(transLength(reData.getPremiumF(),9,"n"))//火險保費
					.append(transLength("",9,"n"))//附加險保費(不處理)
					.append(transLength(reData.getPremiumQ(),9,"n"))//地震保費
					.append(transLength(sdate,6,"s"))//原保單到期月份
					.append(transLength("",9,"s"))//議價佣率(不處理)
					.append(transLength("",100,"s"))//議價原因(不處理)
					.append(transLength(reData.getBirthA() != null ? getDateFormat(reData.getBirthA(), "yyyyMMdd"):"", 8,"s"))//要保人生日
					.append(transLength(reData.getPhoneA(),20,"s"))//要保人電話
					.append(transLength(reData.getBirthI() != null ? getDateFormat(reData.getBirthI(), "yyyyMMdd"):"", 8,"s"))//被保人生日
					.append(transLength(reData.getPhoneI(),20,"s"))//被保人電話
					.append(transLength("",50,"s"))//電子信箱
					.append(transLength(reData.getPostaddressI(),90,"s"))//被保人地址
					.append("\r\n");
				}
				content = sb.toString();
			}
			file = genTxtFile(tempFolder, content, filename, "big5");
		}catch(Exception e) {
			//產生RE檔失敗
			logger.error("genReFile error", e);
			updateFirAgtrnBatchBot(batchNo, "RE", userId, "產生TXT失敗"+e, reBno);
			return getReturnResult("RE檔產生TXT失敗"+e);
		}

		//上傳檔案系統 FTS
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(file, reBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirAgtrnBatchBot(batchNo, "RE", userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), reBno);
				return getReturnResult("RE檔上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genReFile uploadFileTofts error", e);
			updateFirAgtrnBatchBot(batchNo, "RE", userId, "上傳檔案伺服器失敗"+e, reBno);
			FileUtils.forceDelete(file);
			return getReturnResult("RE檔上傳檔案伺服器失敗" + e);
		}
		
		//上傳臺銀 SFTP
		/*
		if("fail".equals(uploadFileToSftp(tempFolder + filename))) {
			updateFirAgtrnBatchBot(batchNo, "RE", userId, "上傳SFTP失敗，請下載檔案後手動上傳", reBno);
			FileUtils.forceDelete(file);
			return getReturnResult("RE檔上傳SFTP失敗，請下載檔案後手動上傳");
		}
		*/
		
		//上傳後再將temp資料夾檔案刪除
		FileUtils.forceDelete(file);
		
		updateFirAgtrnBatchBot(batchNo, "RE", userId, "OK", reBno);
		return getReturnResult("產生RE檔成功。");
	}
	// mantis：FIR0706，處理人員：DP0714，APS_台銀N+2產生之RE檔-APS系統主動抛轉台銀SFTP調整 (全覆蓋) -- end
	
	//產生臺銀供廠商套印到期通知TXT檔案
	public Result genEnFile(String batchNo, String userId) throws SystemException, Exception {
		
		//先取得加密密碼
		Map<String, String> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_BOTRN_XLSPWD");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()==null) {
			return getReturnResult("未設定資料交換檔案密碼，請通知資訊窗口處理。");
		}
		
		FirBatchInfo batchInfo = (FirBatchInfo) result.getResObject();
		String filePwd = batchInfo.getMailTo();
		
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String enBno = batchNo + "_EN";
		
		String filename = enBno +".txt";
		String content = "";
		File file = null;

		params = new HashMap<>();
		params.put("pType", "3");
		params.put("pNo", "AGT");
		result = firRenewPhoneService.findFirRenewPhoneByUk(params);
		if(result.getResObject() == null) {
			updateFirAgtrnBatchBot(batchNo, "EN", userId, "NG-未設定公司客服專線(FIR_RENEW_PHONE)", null);
			return this.getReturnResult("未設定公司客服專線(FIR_RENEW_PHONE)，無法產生檔案。");
		}
		
		FirRenewPhone firRenewPhone = (FirRenewPhone)result.getResObject();
		String pPhone = firRenewPhone.getpPhone();
		
		params.clear();
		params.put("batchNo", batchNo);
		result = firAgtTocoreMainService.findBotEnFileDataByParams(params);
		if(result.getResObject() == null) {
			updateFirAgtrnBatchBot(batchNo, "EN", userId, "查無資料，無法產生檔案。", enBno);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		File zipFile = null;
		
		try {
			List<Aps055BotGenFileVo> dataList = (List<Aps055BotGenFileVo>) result.getResObject();
			content = genTxtContent(dataList, pPhone);
			file = genTxtFile(tempFolder, content, filename, "UTF_16");
			String zipFilepath = tempFolder + filename + ".ZIP";

			ArrayList<File> fileList = new ArrayList<>();
			fileList.add(file);
			
			writeZip(zipFilepath, fileList, filePwd);
			FileUtils.forceDelete(file);

			zipFile = new File(zipFilepath);

			FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, enBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirAgtrnBatchBot(batchNo, "EN", userId, fileUploadResponseVo.getMessage(), null);
				throw new SystemException(fileUploadResponseVo.getMessage());
			}
			
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult("產生到期通知失敗:"+e.getMessage());
		}finally {
			FileUtils.forceDelete(zipFile);
		}
		
		updateFirAgtrnBatchBot(batchNo, "EN", userId, "OK", enBno);
		return getReturnResult("產生到期通知成功。");
	}
	
	private String genTxtContent(List<Aps055BotGenFileVo> dataList, String pPhone) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("保單號碼\t被保險人\t要保人\t要保人郵遞區號\t要保人通訊地址\t標的物郵遞區號\t標的物地址\t火險保額\t火險保費\t")
		.append("基本地震保額\t基本地震保費\t總保險費\t保單起年\t保單起月\t保單起日\t保單迄年\t保單迄月\t保單迄日\t")
		.append("服務人員代號\t服務人員名稱\t公司電話\t本年度火險保額\t本年度火險保費\t本年度基本地震保額\t本年度基本地震保費\t")
		.append("本年度總保險費\t抵押權人\t業務來源\t業務來源中文名稱\t續保約定\t")
		.append("\r\n");
		
		for (Aps055BotGenFileVo data : dataList) {
			Map<String, String> handleMap = firRenewListGenFileService.changeHandler1code(data.getHandler1code(),data.getUsername(),data.getHandleridentifynumber());
			//新核心續件依更換後的服務人員取得對應的公司電話
			sb.append(StringUtil.nullToSpace(data.getOldpolicyno())).append("\t")//保單號碼
			.append(StringUtil.nullToSpace(data.getNameI())).append("\t")//被保險人
			.append(StringUtil.nullToSpace(data.getNameA())).append("\t")//要保人
			.append(StringUtil.nullToSpace(data.getPostcodeA())).append("\t")//要保人郵遞區號
			.append(StringUtil.nullToSpace(data.getPostaddressA())).append("\t")//要保人通訊地址
			.append(StringUtil.nullToSpace(data.getAddresscode())).append("\t")//標的物郵遞區號
			.append(StringUtil.nullToSpace(data.getAddressdetailinfo())).append("\t")//標的物地址
			.append(StringUtil.nullToSpace(data.getAmountFLast())).append("\t")//火險保額
			.append(StringUtil.nullToSpace(data.getPremiumFLast())).append("\t")//火險保費
			.append(StringUtil.nullToSpace(data.getAmountQLast())).append("\t")//基本地震保額
			.append(StringUtil.nullToSpace(data.getPremiumQLast())).append("\t")//基本地震保費
			.append(StringUtil.nullToSpace(data.getPremiumTLast())).append("\t")//總保險費
			.append(StringUtil.nullToSpace(data.getOldY())).append("\t")//保單起年
			.append(StringUtil.nullToSpace(data.getOldM())).append("\t")//保單起月
			.append(StringUtil.nullToSpace(data.getOldD())).append("\t")//保單起日
			.append(StringUtil.nullToSpace(data.getNewY())).append("\t")//保單迄年
			.append(StringUtil.nullToSpace(data.getNewM())).append("\t")//保單迄月
			.append(StringUtil.nullToSpace(data.getNewD())).append("\t")//保單迄日
			.append(handleMap.get("handler1code")).append("\t")//服務人員代號
			.append(handleMap.get("handle1name")).append("\t")//服務人員名稱
			.append(pPhone).append("\t")//公司電話
			.append(StringUtil.nullToSpace(data.getAmountF())).append("\t")//本年度火險保額
			.append(StringUtil.nullToSpace(data.getPremiumF())).append("\t")//本年度火險保費
			.append(StringUtil.nullToSpace(data.getAmountQ())).append("\t")//本年度基本地震保額
			.append(StringUtil.nullToSpace(data.getPremiumQ())).append("\t")//本年度基本地震保費
			.append(StringUtil.nullToSpace(data.getPremiumT())).append("\t")//本年度總保險費
			.append("臺灣銀行股份有限公司").append("\t")//抵押權人
			.append(StringUtil.nullToSpace(data.getBusinessnature())).append("\t")//業務來源
			.append(StringUtil.nullToSpace(data.getBusinessename())).append("\t")//業務來源中文名稱
			.append(StringUtil.nullToSpace(data.getIsAutoRenew())).append("\t")//續保約定
			.append("\r\n");
		}
		return sb.toString();
	}
	
	@Override
	public Result genRnproFile(String batchNo, String userId, String rnYyyymm) throws Exception {
		//先取得加密密碼
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_BOTRN_XLSPWD");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()==null) {
			return getReturnResult("未設定資料交換檔案密碼，請通知資訊窗口處理。");
		}
		
		FirBatchInfo batchInfo = (FirBatchInfo) result.getResObject();
		String filePwd = batchInfo.getMailTo();
		
		int returnValue = 0;
		
		params.clear();
		params.put("batchNo", batchNo);
		params.put("temp1", "11");
		result = firAgtTocoreMainService.findDistinctExtracomcodeByParams(params);
		
		if(result.getResObject() == null) {
			return getReturnResult("查無資料，無法產生續保要保書相關檔案");
		}
		
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("outResult", null);

		returnValue = firSpService.runSpFirAgtrnBotTemp(params);

		//若呼叫SP失敗，更新狀態，並結束產檔作業
		if (returnValue != 0) {
			updateFirAgtrnBatchBot(batchNo, "RNPRO", userId, "執行暫存SP失敗。", null);
			return getReturnResult("執行暫存SP失敗，請聯繫資訊人員。");
		}
		
		String rnproBno = batchNo + "_RNPRO";
		List<FirAgtTocoreMain> extracomcodeList = (List<FirAgtTocoreMain>) result.getResObject();
	
		String tempFolder = configUtil.getString("tempFolder") + File.separator 
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "RNPRO_FILE" + File.separator;
		
		File fileFolder = new File(tempFolder);
		if(!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		
		ArrayList<File> fileList = new ArrayList<>();

		for(FirAgtTocoreMain extracomcodeInfo : extracomcodeList) {

			String filename = "" ;
			
			//依據是否有續保約定產生相關檔案
			//有續保約定，呼叫RPT產生續保要保書並產生PDF檔案。
			if("Y".equals(extracomcodeInfo.getIsAutoRenew())) {
				filename = rnYyyymm + "_" +extracomcodeInfo.getExtracomcode() + "_" + extracomcodeInfo.getExtracomname() + "_01.pdf";
			}
			
			//無續保約定，呼叫RPT產生續保要保書、書面分析報告、代理投保通知，並產生PDF檔案
			if("N".equals(extracomcodeInfo.getIsAutoRenew())) {
				filename = rnYyyymm + "_" +extracomcodeInfo.getExtracomcode() + "_" + extracomcodeInfo.getExtracomname() + "_02.pdf";
			}
			
			if(!generateReport(batchNo, extracomcodeInfo.getExtracomcode(),
					extracomcodeInfo.getIsAutoRenew(), tempFolder + filename)) {
				updateFirAgtrnBatchBot(batchNo, "RNPRO", userId, "產生續保要保書等PDF檔案失敗", rnproBno);
				return getReturnResult("產生續保要保書等PDF檔案失敗");
			}
			File pdfFile = new File(tempFolder + filename);
			fileList.add(pdfFile);
		}
		
		//當整個批次號報表產生完成，將檔案壓縮加密並上傳檔案伺服器FTS
		String zipFilepath = tempFolder + batchNo + "_RN.ZIP";

		writeZip(zipFilepath, fileList, filePwd);
		
		File zipFile = new File(zipFilepath);

		
		if(zipFile.exists()) {
			//將檔案上傳FTS
			try {
				FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, rnproBno, userId, "F");
				if ("N".equals(fileUploadResponseVo.getStatus())) {
					updateFirAgtrnBatchBot(batchNo, "RNPRO", userId, fileUploadResponseVo.getMessage(), rnproBno);
					throw new SystemException(fileUploadResponseVo.getMessage());
				}
			}catch (Exception e) {
				logger.error("genRenewListFile uploadFileToftp error", e);
				updateFirAgtrnBatchBot(batchNo, "RNPRO", userId, "上傳檔案伺服器失敗" + e, rnproBno);
				return getReturnResult("續保要保書等PDF檔案上傳檔案伺服器失敗" + e);
			}finally{
				FileUtils.forceDelete(new File(tempFolder));
			}
		}
		
		updateFirAgtrnBatchBot(batchNo, "RNPRO", userId, "OK", rnproBno);
		return getReturnResult("產生續保要保書相關檔案成功。");
		
	}
	
	private boolean generateReport(String batchNo, String extracomcode, String isAutoRenew, String filepath){
		try {
			@SuppressWarnings("resource")
			HttpClient httpClient = new DefaultHttpClient();
			String rcvurl = configUtil.getString("rptUrl") +"webService/pdf/fir00116/";
			
			Fir00116RequestVo vo  = new Fir00116RequestVo();
			vo.setBatchNo(batchNo);
			vo.setExtracomcode(extracomcode);
			vo.setIsAutoRenew(isAutoRenew);
			
			HttpPost httpPost = new HttpPost(rcvurl);  
			StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(vo), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
			httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			System.out.println( "httpResponse.getStatusLine() = " + httpResponse.getStatusLine());
			HttpEntity entity = httpResponse.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);
			
			if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
				String returnJsonStr = EntityUtils.toString(entity, "UTF-8");
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				RptFir00103ResultVo rptResultBaseVo = objectMapper.readValue(returnJsonStr, RptFir00103ResultVo.class);
				
				byte[] byteArray = Base64.decodeBase64(rptResultBaseVo.getRptStr());
				FileUtils.writeByteArrayToFile(new File(filepath), byteArray);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//更新批次主檔
	private void updateFirAgtrnBatchBot(String batchNo, String filetype, String userId, String memo, String bno) throws Exception {
		FirAgtrnBatchBot firAgtrnBatchBot = new FirAgtrnBatchBot();
		firAgtrnBatchBot.setBatchNo(batchNo);
		Date sysDate = new Date();
		firAgtrnBatchBot.setIupdate(userId);
		firAgtrnBatchBot.setDupdate(sysDate);
		if("RE".equals(filetype)) {
			firAgtrnBatchBot.setReUser(userId);
			firAgtrnBatchBot.setReTime(sysDate);
			firAgtrnBatchBot.setReMemo(memo);
			firAgtrnBatchBot.setReBno(bno);
		}
		if("EN".equals(filetype)) {
			firAgtrnBatchBot.setEnUser(userId);
			firAgtrnBatchBot.setEnTime(sysDate);
			firAgtrnBatchBot.setEnMemo(memo);
			firAgtrnBatchBot.setEnBno(bno);
		}
		if("RNPRO".equals(filetype)) {
			firAgtrnBatchBot.setRnproUser(userId);
			firAgtrnBatchBot.setRnproTime(sysDate);
			firAgtrnBatchBot.setRnproMemo(memo);
			firAgtrnBatchBot.setRnproBno(bno);
		}
		firAgtrnBatchBotService.updateFirAgtrnBatchBot(firAgtrnBatchBot);
	}
	
	//將檔案上傳至fts
	private FileUploadResponseVo uploadFile(File file, String businessNo, String userId, String riskcode) {
		FileUploadResponseVo fileUploadResponseVo = null;
		try{
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			fileUploadResponseVo = ftsutil.uploadFile(file.getAbsolutePath(), userId, riskcode, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			return fileUploadResponseVo;
		}
		return fileUploadResponseVo;
	}
	
	//將檔案上傳至Sftp
	private String uploadFileToSftp(String filepath){
		String sftpHost = configUtil.getString("botFTP");
		String sftpUser = configUtil.getString("botFtpUserPut");
		String sftpPwd = configUtil.getString("botFptPwdPut");
		int sftpPort = Integer.parseInt(configUtil.getString("botFtpPort"));
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		
		String remoteDir = configUtil.getString("botRemotePath");
		return sftpUtil.putFileToSftp2(remoteDir, filepath);
	}
	
	public File genTxtFile(String tempDir, String content, String filename, String encoding) {
		File file = new File(tempDir+filename);
		try(BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding))){
			bufWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	//壓縮檔案
	private static void writeZip(String filepath, ArrayList<File> filelist, String pwd) throws Exception {			
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(filepath);
		} catch (ZipException e) {
			e.printStackTrace();
		}		

		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if(!StringUtil.isSpace(pwd)){
			parameters.setEncryptFiles(true);
			parameters.setPassword(pwd);
		}
		try {
			zipFile.addFiles(filelist, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	public String transLength(String str, int length, String type) throws Exception {
		if(StringUtil.isSpace(str)) {
			str = "";
		}
		int byteLength = str.getBytes("big5").length;
		
		//先判斷長度是否超過
		if(byteLength > length) {
			byte[] strBytes = str.getBytes("big5");
			byte[] strBytes2 = new byte[length];
			System.arraycopy(strBytes, 0, strBytes2, 0, length);
			return new String(strBytes2, "big5");
		}
		
		StringBuilder sb = new StringBuilder();
		//數字長度不足左邊補半形空白
		if(type.equals("n")) {
			while(byteLength<length) {
				byteLength++;
				sb.append("0");
			}
			sb.append(str);
		}else {//無資料或非數字不足長度右邊補半形空白
			sb.append(str);
			while(byteLength<length) {
				byteLength++;
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private String getDateFormat(Date date, String type) {
		return new SimpleDateFormat(type).format(date);
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public FirAgtTocoreMainService getFirAgtTocoreMainService() {
		return firAgtTocoreMainService;
	}

	public void setFirAgtTocoreMainService(FirAgtTocoreMainService firAgtTocoreMainService) {
		this.firAgtTocoreMainService = firAgtTocoreMainService;
	}

	public FirAgtrnBatchBotService getFirAgtrnBatchBotService() {
		return firAgtrnBatchBotService;
	}

	public void setFirAgtrnBatchBotService(FirAgtrnBatchBotService firAgtrnBatchBotService) {
		this.firAgtrnBatchBotService = firAgtrnBatchBotService;
	}

	public FirRenewPhoneService getFirRenewPhoneService() {
		return firRenewPhoneService;
	}

	public void setFirRenewPhoneService(FirRenewPhoneService firRenewPhoneService) {
		this.firRenewPhoneService = firRenewPhoneService;
	}

	public FirRenewListGenFileService getFirRenewListGenFileService() {
		return firRenewListGenFileService;
	}

	public void setFirRenewListGenFileService(FirRenewListGenFileService firRenewListGenFileService) {
		this.firRenewListGenFileService = firRenewListGenFileService;
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
}
