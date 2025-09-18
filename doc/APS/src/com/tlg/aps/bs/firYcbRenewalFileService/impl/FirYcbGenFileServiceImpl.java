package com.tlg.aps.bs.firYcbRenewalFileService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firGenRenewListService.FirRenewListGenFileService;
import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbGenFileService;
import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.aps.enums.EnumYCBField;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenPolicyFileVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchYcb;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirRenewPhone;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchYcbService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirRenewPhoneService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FieldAccessor;
import com.tlg.util.FtsUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.service.RfrcodeService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirYcbGenFileServiceImpl implements FirYcbGenFileService {

	private static final Logger logger = Logger.getLogger(FirYcbGenFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private RfrcodeService rfrcodeService;
	private FirAgtrnBatchYcbService firAgtrnBatchYcbService;
	private FirRenewPhoneService firRenewPhoneService;
	private FirRenewListGenFileService firRenewListGenFileService;
	private FirSpService firSpService;
	private FirBatchInfoService firBatchInfoService;

	//產生續保明細檔
	@SuppressWarnings("unchecked")
	@Override
	public Result genRnFile(String batchNo, String userId) throws Exception {
		
		//取得RN檔加密密碼
		Map<String, String> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_YCBRN_RNPWD");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()==null) {
			return getReturnResult("未設定資料交換檔案密碼，請通知資訊窗口處理。");
		}
		
		FirBatchInfo batchInfo = (FirBatchInfo) result.getResObject();
		String filePwd = batchInfo.getMailTo();
		
		
		
		//開始產生檔案
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		
		String reBno = batchNo + "_RN";
		String downloadFilename = reBno + ".xlsx";	//YCBRN_250611080000_RN.xlsx
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String filepath = configUtil.getString("tempFolder") + downloadFilename;
		
		try {
			params = new HashMap<>();
			params.put("batchNo", batchNo);
			result = firAgtTocoreMainService.findYcbRnFileDataByParams(params);
			try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
				// 建立總表
				SXSSFSheet sheet = workbook.createSheet();
				String[] titleArr = { "保險公司", "分行代號", "分行名稱", "保單號碼",
						"保單生效日", "保單到期日", "要保人姓名", "要保人ID", "被保險人姓名",
						"被保險人ID", "標的物地址", "原火險保額", "原地震險保額","總保費",
						"扣款帳號", "放款帳號", "行員員編", "險種", "是否自動續保","是否有其他附加險",
						"續約否", "火險是否為超額投保", "新火險保額", "新地震險保額","新保費"
						};
				SXSSFRow rowTitle = sheet.createRow(0);
				// 建立excel欄位
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				if (result.getResObject() != null) {
					List<Aps060YcbGenFileVo> excelList = (List<Aps060YcbGenFileVo>) result.getResObject();
					for (int i = 0; i < excelList.size(); i++) {
						SXSSFRow row = sheet.createRow(i + 1); // 建立儲存格
						Aps060YcbGenFileVo xlsData = excelList.get(i);
						row.createCell(0).setCellValue("中信產險");//保險公司	固定值:中信產險
						row.createCell(1).setCellValue(xlsData.getExtracomcode());//分行代號	查詢結果.EXTRACOMCODE
						row.createCell(2).setCellValue(xlsData.getExtracomname());//分行名稱	查詢結果.EXTRACOMNAME
						row.createCell(3).setCellValue(xlsData.getOldpolicyno());//保單號碼	查詢結果.OLDPOLICYNO
						row.createCell(4).setCellValue(sdf.format(xlsData.getStartdate()));//保單生效日	查詢結果.STARTDATE(日期格式YYYYMMDD)
						row.createCell(5).setCellValue(sdf.format(xlsData.getEnddate()));//保單到期日	查詢結果.ENDDATE(日期格式YYYYMMDD)
						row.createCell(6).setCellValue(xlsData.getNameA());//要保人姓名	查詢結果.NAME_A
						row.createCell(7).setCellValue(xlsData.getIdA());//要保人ID	查詢結果.ID_A
						row.createCell(8).setCellValue(xlsData.getNameI());//被保險人姓名	查詢結果.NAME_I
						row.createCell(9).setCellValue(xlsData.getIdI());//被保險人ID	查詢結果.ID_I
						row.createCell(10).setCellValue(xlsData.getAddressdetailinfo());//標的物地址	查詢結果.ADDRESSDETAILINFO
						row.createCell(11).setCellValue(xlsData.getAmountFLast());//原火險保額	查詢結果.AMOUNT_F_LAST
						row.createCell(12).setCellValue(xlsData.getAmountQLast());//原地震險保額	查詢結果.AMOUNT_Q_LAST
						row.createCell(13).setCellValue(xlsData.getPremiumT());//總保費	查詢結果. PREMIUM_Q_LAST+查詢結果.PREMIUM_F_LAST
						row.createCell(14).setCellValue("000000000000");//扣款帳號	固定值:000000000000
						row.createCell(15).setCellValue("000000000000");//放款帳號	固定值:000000000000
						row.createCell(16).setCellValue(xlsData.getHandleridentifynumber());//行員員編	查詢結果.HANDLERIDENTIFYNUMBER
						row.createCell(17).setCellValue("住火");//險種	固定值:住火
						row.createCell(18).setCellValue(xlsData.getIsAutoRenew());//是否自動續保	查詢結果.IS_AUTO_RENEW
						row.createCell(19).setCellValue("");//是否有其他附加險	不處理
						row.createCell(20).setCellValue("");//續約否	不處理
						row.createCell(21).setCellValue("");//火險是否為超額投保	不處理
						row.createCell(22).setCellValue("0");//新火險保額	固定值:0
						row.createCell(23).setCellValue("0");//新地震險保額	固定值:0
						row.createCell(24).setCellValue("0");//新保費	固定值:0

					}
				}
				
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close();
				
				// 檔案加密
				POIFSFileSystem fs = new POIFSFileSystem();
				EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
				Encryptor enc = info.getEncryptor();
				enc.confirmPassword(filePwd);
				OPCPackage opc = OPCPackage.open(new File(filepath), PackageAccess.READ_WRITE);
				OutputStream os = enc.getDataStream(fs);
				opc.save(os);
				opc.close();
				FileOutputStream fos = new FileOutputStream(filepath);
				fs.writeFilesystem(fos);
				fos.flush();
				fos.close();
				
			}
		}catch(Exception e) {
			//產生RE檔失敗
			logger.error("genRnFile error", e);
			updateFirAgtrnBatchYcb(EnumYCBFile.RENEW, batchNo, userId, "產生XLS失敗"+e, reBno);
			return getReturnResult("RN檔產生XLS失敗"+e);
		}
		
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(new File(filepath), reBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirAgtrnBatchYcb(EnumYCBFile.RENEW, batchNo, userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), reBno);
				return getReturnResult("RN檔上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genRNFile uploadFileTofts error", e);
			updateFirAgtrnBatchYcb(EnumYCBFile.RENEW, batchNo, userId, "上傳檔案伺服器失敗"+e, reBno);
			return getReturnResult("RN檔上傳檔案伺服器失敗" + e);
		}
		
		//上傳後再將temp資料夾檔案刪除
		FileUtils.forceDelete(new File(filepath));
		
		updateFirAgtrnBatchYcb(EnumYCBFile.RENEW, batchNo, userId, "OK", reBno);
		return getReturnResult("產生RN檔成功。");
	}
	
	//產生到期通知TXT檔案
	public Result genEnFile(String batchNo, String userId) throws SystemException, Exception {
		
		//先取得加密密碼
		Map<String, String> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_YCBRN_XLSPWD");
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
			updateFirAgtrnBatchYcb(EnumYCBFile.ENDNOTICE, batchNo, userId, "NG-未設定公司客服專線(FIR_RENEW_PHONE)", null);
			return this.getReturnResult("未設定公司客服專線(FIR_RENEW_PHONE)，無法產生檔案。");
		}
		
		FirRenewPhone firRenewPhone = (FirRenewPhone)result.getResObject();
		String pPhone = firRenewPhone.getpPhone();
		
		params.clear();
		params.put("batchNo", batchNo);
		result = firAgtTocoreMainService.findYcbEnFileDataByParams(params);
		if(result.getResObject() == null) {
			updateFirAgtrnBatchYcb(EnumYCBFile.ENDNOTICE , batchNo, userId, "查無資料，無法產生檔案。", enBno);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		File zipFile = null;
		
		try {
			List<Aps060YcbGenFileVo> dataList = (List<Aps060YcbGenFileVo>) result.getResObject();
			content = genTxtContent(dataList, pPhone);
			file = genTxtFile(tempFolder, content, filename, "UTF_16");
			String zipFilepath = tempFolder + enBno + ".ZIP";

			ArrayList<File> fileList = new ArrayList<>();
			fileList.add(file);
			
			writeZip(zipFilepath, fileList, filePwd);
			FileUtils.forceDelete(file);

			zipFile = new File(zipFilepath);

			FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, enBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirAgtrnBatchYcb(EnumYCBFile.ENDNOTICE, batchNo, userId, fileUploadResponseVo.getMessage(), null);
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
		
		updateFirAgtrnBatchYcb(EnumYCBFile.ENDNOTICE, batchNo, userId, "OK", enBno);
		return getReturnResult("產生到期通知成功。");
	}
	
	private String genTxtContent(List<Aps060YcbGenFileVo> dataList, String pPhone) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("保單號碼\t被保險人\t要保人\t要保人郵遞區號\t要保人通訊地址\t標的物郵遞區號\t標的物地址\t火險保額\t火險保費\t")
		.append("基本地震保額\t基本地震保費\t總保險費\t保單起年\t保單起月\t保單起日\t保單迄年\t保單迄月\t保單迄日\t")
		.append("服務人員代號\t服務人員名稱\t公司電話\t本年度火險保額\t本年度火險保費\t本年度基本地震保額\t本年度基本地震保費\t")
		.append("本年度總保險費\t抵押權人\t業務來源\t業務來源中文名稱\t續保約定\t")
		.append("\r\n");
		
		for (Aps060YcbGenFileVo data : dataList) {
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
			.append("元大商業銀行股份有限公司").append("\t")//抵押權人
			.append(StringUtil.nullToSpace(data.getBusinessnature())).append("\t")//業務來源
			.append(StringUtil.nullToSpace(data.getBusinessename())).append("\t")//業務來源中文名稱
			.append(StringUtil.nullToSpace(data.getIsAutoRenew())).append("\t")//續保約定
			.append("\r\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * 更新批次主檔
	 * @param ycbFile
	 * @param batchNo
	 * @param userId
	 * @param memo
	 * @param bno
	 * @throws Exception
	 */
	private void updateFirAgtrnBatchYcb(EnumYCBFile ycbFile, String batchNo, String userId, String memo, String bno) throws Exception {
		FirAgtrnBatchYcb firAgtrnBatchYcb = new FirAgtrnBatchYcb();
		firAgtrnBatchYcb.setBatchNo(batchNo);
		Date sysDate = new Date();
		firAgtrnBatchYcb.setIupdate(userId);
		firAgtrnBatchYcb.setDupdate(sysDate);	
		
		switch (ycbFile) {
		case RENEW:	//元大續保明細檔
			firAgtrnBatchYcb.setRnUser(userId);
			firAgtrnBatchYcb.setRnTime(sysDate);
			firAgtrnBatchYcb.setRnMemo(memo);
			firAgtrnBatchYcb.setRnBno(bno);
			break;
		case ENDNOTICE:	//元大到期通知
			firAgtrnBatchYcb.setEnUser(userId);
			firAgtrnBatchYcb.setEnTime(sysDate);
			firAgtrnBatchYcb.setEnMemo(memo);
			firAgtrnBatchYcb.setEnBno(bno);
			break;
		case POLICY:	//元大出單明細檔 excel
			firAgtrnBatchYcb.setPoUser(userId);
			firAgtrnBatchYcb.setPoTime(sysDate);
			firAgtrnBatchYcb.setPoMemo(memo);
			firAgtrnBatchYcb.setPoBno(bno);
			break;
		case POLICYCOPY:	//元大保單副本 EXCEL
			firAgtrnBatchYcb.setCoUser(userId);
			firAgtrnBatchYcb.setCoTime(sysDate);
			firAgtrnBatchYcb.setCoMemo(memo);
			firAgtrnBatchYcb.setCoBno(bno);
			break;
		default :
			//do nothing
			break;
		} 
		
		firAgtrnBatchYcbService.updateFirAgtrnBatchYcb(firAgtrnBatchYcb);
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

	public FirAgtrnBatchYcbService getFirAgtrnBatchYcbService() {
		return firAgtrnBatchYcbService;
	}

	public void setFirAgtrnBatchYcbService(FirAgtrnBatchYcbService firAgtrnBatchYcbService) {
		this.firAgtrnBatchYcbService = firAgtrnBatchYcbService;
	}
	

	/**
	 * 產生元大續保作業 n+1  excel 檔案
	 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	 * @param ycbFile
	 * @param batchNo
	 * @param userId
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Result genXlsFile(EnumYCBFile ycbFile, String batchNo, String userId) throws SystemException, Exception {
		
		//先取得加密密碼
		Map<String, String> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_YCBRN_XLSPWD");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()==null) {
			return getReturnResult("未設定資料交換檔案密碼，請通知資訊窗口處理。");
		}
			
		FirBatchInfo batchInfo = (FirBatchInfo) result.getResObject();
		String filePwd = batchInfo.getMailTo();
									
		//開始產生檔案
		String tmpFolder = configUtil.getString("tempFolder");		//d:/temp
		File tmpFolderPath = new File(tmpFolder);
		if (!tmpFolderPath.exists()) {
			tmpFolderPath.mkdirs();
		}
			
		//取得檔案名稱
		String apsBno = batchNo + "_" + ycbFile.getFileType();		//YCBRN_250611080000_RN
		String filename = apsBno +"." + ycbFile.getExtension();		//YCBRN_250611080000_RN.xlsx 
		String tmpFilePath = tmpFolder + filename;				//D:/temp/YCBRN_250611080000_RN.xlsx
					
		File uploadFile = null;
		 
		try {
			/*資料明細*/
			Map<String, Object> spParams = new HashMap<>();
			spParams.put("inFileType", ycbFile.getFileType());
			spParams.put("inBatchNo", batchNo);
			spParams.put("inUser", userId);
			spParams.put("outResult", null); 
			int returnValue = firSpService.runSpFirAgtYcbGenFile(spParams);
			
			/*執行失敗*/
			if (returnValue==1) {
				updateFirAgtrnBatchYcb(ycbFile , batchNo, userId, "查無資料，無法產生"+ ycbFile.getFileType() +"檔案。", apsBno);
				return this.getReturnResult("查無資料，無法產生"+ ycbFile.getFileType() +"檔案。");
			}
			params.clear();
			params.put("batchNo", batchNo);
			result = firAgtTocoreMainService.findYcbFileDataByParams(ycbFile, params);
			
			if(result.getResObject() == null) {
				updateFirAgtrnBatchYcb(ycbFile , batchNo, userId, "查無資料，無法產生"+ ycbFile.getFileType() +"檔案。", apsBno);
				return this.getReturnResult("查無資料，無法產生"+ ycbFile.getFileType() +"檔案。");
			}
			//產案檔案格式
			switch (ycbFile) {
			case POLICYCOPY://保單副本 excel    
			case POLICY:	//續件清單  excel   
				try {
					SXSSFWorkbook workbook = new SXSSFWorkbook();
					// Step1 建立總表
					SXSSFSheet sheet = workbook.createSheet();
					if (result.getResObject() != null) { /*step2 start*/
						// Step2 取得資料明細
						List<Aps060YcbGenPolicyFileVo> excelList = (List<Aps060YcbGenPolicyFileVo>) result.getResObject();
						
						// Step3  建立標題欄位
						SXSSFRow rowTitle = sheet.createRow(0);
						int idx=0;
						
						for (EnumYCBField field : EnumYCBField.values() ) {
							/*只列印檔案設定的欄案類型欄位*/
							if (!field.getFileType().equals(ycbFile.getFileType())){
								continue;
							} 
							rowTitle.createCell(idx).setCellValue(field.getTitle());
							idx++;
						}
						// Step4  建立明細欄位 
						for (int i = 0; i < excelList.size(); i++) {  
							idx = 0;		//reset

							Aps060YcbGenPolicyFileVo xlsData = excelList.get(i); 
							SXSSFRow row = sheet.createRow(i + 1); // 建立儲存格列
							
							for (EnumYCBField field : EnumYCBField.values() ) { 
								/*只列印檔案設定的欄案類型欄位*/
								if (!field.getFileType().equals(ycbFile.getFileType())){
									continue;
								} 
								
								String fieldValue = "";
								/*日期型態需轉換*/
								if (field.getFieldType()==java.util.Date.class){
									/*保單副本(CO) 日期格式 roc yyymmdd */
									if (ycbFile.equals(ycbFile.POLICYCOPY)){
										fieldValue =DateUtils.getRocYYYMMDD((java.util. Date)FieldAccessor.getFieldValue(xlsData, field.getName(), field.getFieldType()));
									}else{
										//預設為西元
										fieldValue =DateUtils.getADDateString((java.util. Date)FieldAccessor.getFieldValue(xlsData, field.getName(), field.getFieldType()));
									}
								}else{
									fieldValue = (String)FieldAccessor.getFieldValue(xlsData, field.getName(), field.getFieldType());
								} 
								
								row.createCell(idx).setCellValue(fieldValue);//"擔保品編號",
								idx++; 
								
							}   
						}
					}/*step2 end*/
 
					//step5 close file
					FileOutputStream fileOut = new FileOutputStream(tmpFilePath);
					workbook.write(fileOut);
					fileOut.close();
					
				}catch(Exception e) {
					//產生檔失敗
					String err= ycbFile.getFileType() + "檔產生" + ycbFile.getExtension() + "失敗";		//EN檔產生XLS失敗 + e.error
					updateFirAgtrnBatchYcb(ycbFile, batchNo, userId, err, apsBno);
					return getReturnResult(err+e);		//EN檔產生XLS失敗
				}
				
				break;
			default:	
				//donthing
				break;
			}
			//step6 upload file 
			uploadFile = new File(tmpFilePath);
			
			//step7 excel  檔案加密
			POIFSFileSystem fs = new POIFSFileSystem();
			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
			Encryptor enc = info.getEncryptor();
			enc.confirmPassword(filePwd);
			OPCPackage opc = OPCPackage.open(new File(tmpFilePath), PackageAccess.READ_WRITE);
			OutputStream os = enc.getDataStream(fs);
			opc.save(os);
			opc.close();
			FileOutputStream fos = new FileOutputStream(tmpFilePath);
			fs.writeFilesystem(fos);
			fos.flush();
			fos.close(); 
			
			/*step8  將檔案上傳至fts*/
			try {
				FileUploadResponseVo fileUploadResponseVo = uploadFile(uploadFile, apsBno, userId, "F");
				if ("N".equals(fileUploadResponseVo.getStatus())) {
					updateFirAgtrnBatchYcb(ycbFile, batchNo, userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), apsBno);
					return getReturnResult(ycbFile.getFileType() + "檔上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
				}
			}catch (Exception e) {
				updateFirAgtrnBatchYcb(ycbFile, batchNo, userId, "上傳檔案伺服器失敗"+e, apsBno);
				return getReturnResult( ycbFile.getFileType() + "檔上傳檔案伺服器失敗" + e);
			} 
				
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult("產生" + ycbFile.getFileType() + "失敗:"+e.getMessage());
		}finally {
			/*step9 remove local file */
			if (uploadFile!=null && uploadFile.exists()){
				//上傳後再將local temp資料夾檔案刪除
				FileUtils.forceDelete(uploadFile);					
				updateFirAgtrnBatchYcb(ycbFile, batchNo, userId, "OK", apsBno);
			} 
		}
			
		updateFirAgtrnBatchYcb(ycbFile, batchNo, userId, "OK", apsBno);
		return getReturnResult("產生" + ycbFile.getFileType() + "成功。");
	}	
}
