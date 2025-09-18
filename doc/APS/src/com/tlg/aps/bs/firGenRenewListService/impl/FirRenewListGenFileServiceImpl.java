package com.tlg.aps.bs.firGenRenewListService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firGenRenewListService.FirRenewListGenFileService;
import com.tlg.aps.bs.firGenRenewListService.GenRenewListService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FirRenewListForFileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.entity.FirRenewPhone;
import com.tlg.prpins.service.FirRenewListDtlService;
import com.tlg.prpins.service.FirRenewListService;
import com.tlg.prpins.service.FirRenewPhoneService;
import com.tlg.sales.entity.CommdataCmemfil;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.service.CommdataCmemfilService;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRenewListGenFileServiceImpl implements FirRenewListGenFileService {

	private static final Logger logger = Logger.getLogger(FirRenewListGenFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirRenewPhoneService firRenewPhoneService;
	private FirRenewListService firRenewListService;
	private FirRenewListDtlService firRenewListDtlService;
	private GenRenewListService genRenewListService;
	private PrpdagentService prpdagentService;
	private CommdataCmemfilService commdataCmemfilService;
	private static final String MEMO_OK = "OK";
	private static final String MEMO_N = "OK-無資料";
	private static final String MEMO_E = "產生檔案或上傳內部伺服器失敗";
	private static final String FILEPWD = "27938888";

	//產生中信件到期通知TXT
	@Override
	public Result genCtbcFile(String batchNo, String userId, String fileType) throws Exception {
		String ctbcBno = batchNo + "_I99050";
		Map<String, String> params = new HashMap<>();
		params.put("pType", "1");
		params.put("pNo", "I99050");
		Result result = firRenewPhoneService.findFirRenewPhoneByUk(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, "NG-未設定公司客服專線(FIR_RENEW_PHONE)", null);
			return this.getReturnResult("未設定公司客服專線(FIR_RENEW_PHONE)，無法產生檔案。");
		}
		
		FirRenewPhone firRenewPhone = (FirRenewPhone)result.getResObject();
		String pPhone = firRenewPhone.getpPhone();
		
		params.clear();
		params.put("batchNo", batchNo);
		params.put("rnType", "2");
		result = firRenewListDtlService.findRenewListForOtherFile(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, MEMO_N, null);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		int dataCount = 0;
		try {
			dataCount = genOtherFile(batchNo, ctbcBno, pPhone, fileType, userId, result);
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult(MEMO_E+e.getMessage());
		}
		return getReturnResult("OK-" + dataCount + "筆");
	}
	
	/*
	 * 產生核心續件到期通知-無附加險TXT
	 * mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式
	 */
	@Override
	public Result genNcoreFile(String batchNo, String userId, String fileType) throws Exception {
		String ncoreBno = batchNo + "_NCORE";
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("isAddIns", "N");
		
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式   start
		if (fileType.equals("tsca") ){ 
			ncoreBno = batchNo + "_I40503"; 
			params.put("businessNature", "I40503");
		}
		if (fileType.equals("ncore") ){ // [產生核心續件到期通知-無附加險] 要排除 [I40503勤業]
			params.put("excludeBusinessNature", "I40503");
		}
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式   end
		Result result = firRenewListDtlService.findRenewListForCoreFile(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, MEMO_N, null);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		int dataCount = 0;
		try {
			dataCount = genOtherFile(batchNo, ncoreBno, "", fileType, userId, result);
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult(MEMO_E+e.getMessage());
		}
		return getReturnResult("OK-" + dataCount + "筆");
	}
	
	//產生板信件到期通知
	@Override
	public Result genBopFile(String batchNo, String userId, String fileType) throws SystemException, Exception {
		String bopBno = batchNo + "_I99065";
		Map<String, String> params = new HashMap<>();
		params.put("pType", "3");
		params.put("pNo", "AGT");
		Result result = firRenewPhoneService.findFirRenewPhoneByUk(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, "NG-未設定公司客服專線(FIR_RENEW_PHONE)", null);
			return this.getReturnResult("未設定公司客服專線(FIR_RENEW_PHONE)，無法產生檔案。");
		}
		
		FirRenewPhone firRenewPhone = (FirRenewPhone)result.getResObject();
		String pPhone = firRenewPhone.getpPhone();
		
		params.clear();
		params.put("batchNo", batchNo);
		params.put("rnType", "3");
		params.put("businessnature", "I99065");
		result = firRenewListDtlService.findRenewListForOtherFile(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, MEMO_N, null);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		int dataCount = 0;
		try {
			dataCount = genOtherFile(batchNo, bopBno, pPhone, fileType, userId, result);
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult(MEMO_E+e.getMessage());
		}
		return getReturnResult("OK-" + dataCount + "筆");
	}

	//產生富邦件到期通知
	@Override
	public Result genFbFile(String batchNo, String userId, String fileType) throws Exception {
		String fbBno = batchNo + "_I00107";
		Map<String, String> params = new HashMap<>();
		params.put("pType", "3");
		params.put("pNo", "AGT");
		Result result = firRenewPhoneService.findFirRenewPhoneByUk(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, "NG-未設定公司客服專線(FIR_RENEW_PHONE)", null);
			return this.getReturnResult("未設定公司客服專線(FIR_RENEW_PHONE)，無法產生檔案。");
		}
		
		FirRenewPhone firRenewPhone = (FirRenewPhone)result.getResObject();
		String pPhone = firRenewPhone.getpPhone();
		
		params.clear();
		params.put("batchNo", batchNo);
		params.put("rnType", "3");
		params.put("businessnature", "I00107");
		result = firRenewListDtlService.findRenewListForOtherFile(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, MEMO_N, null);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		int dataCount = 0;
		try {
			dataCount = genOtherFile(batchNo, fbBno, pPhone, fileType, userId, result);
		} catch (SystemException se) {
			return getReturnResult(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return getReturnResult(MEMO_E+e.getMessage());
		}
		return getReturnResult("OK-" + dataCount + "筆");
	}

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@Override
	public Result genPpFile(String batchNo, String userId, String rnYymm) throws Exception {

		logger.info(">>> batchNo: " + batchNo);

		File txtFile1 = null;
		File txtFile2 = null;
		File txtFile3 = null;
		File txtFile4 = null;
		File txtFile5 = null;
		File txtFile6 = null;

		String bno = "FRNPRO_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

		try {

			String tempFolder = configUtil.getString("tempFolder");
			File folder = new File(tempFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			ArrayList<File> toZipFiles = new ArrayList<File>(); // 要一起打包壓縮的檔案

			// generate txt files
			txtFile1 = new File(tempFolder + File.separator + rnYymm + "_MAIN.txt");
			String content1 = this.genPpFileMainData(batchNo);
			FileUtils.write(txtFile1, content1, "UTF-16");
			toZipFiles.add(txtFile1);

			txtFile2 = new File(tempFolder + File.separator + rnYymm + "_INSURED.txt");
			String content2 = this.genPpFileInsuredData(batchNo);
			FileUtils.write(txtFile2, content2, "UTF-16");
			toZipFiles.add(txtFile2);

			txtFile3 = new File(tempFolder + File.separator + rnYymm + "_ADDRESS.txt");
			String content3 = this.genPpFileAddressData(batchNo);
			FileUtils.write(txtFile3, content3, "UTF-16");
			toZipFiles.add(txtFile3);

			txtFile4 = new File(tempFolder + File.separator + rnYymm + "_PROP.txt");
			String content4 = this.genPpFilePropData(batchNo);
			FileUtils.write(txtFile4, content4, "UTF-16");
			toZipFiles.add(txtFile4);

			txtFile5 = new File(tempFolder + File.separator + rnYymm + "_MORTGAGEE.txt");
			String content5 = this.genPpFileMortgageeData(batchNo);
			FileUtils.write(txtFile5, content5, "UTF-16");
			toZipFiles.add(txtFile5);

			txtFile6 = new File(tempFolder + File.separator + rnYymm + "_ITEMKIND.txt");
			String content6 = this.genPpFileItemkindData(batchNo);
			FileUtils.write(txtFile6, content6, "UTF-16");
			toZipFiles.add(txtFile6);

			// zip files
			String zipFilepath = tempFolder + bno + ".zip";
			String pswd = FILEPWD;
			try {
				this.zipFiles(zipFilepath, toZipFiles, pswd);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new Exception("zip file failed, error: " + e.getMessage());
			}

			// upload FTS
			File zipFile = new File(zipFilepath);
			FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, bno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				throw new Exception("upload FTS failed, status: " + fileUploadResponseVo.getMessage());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			updateFirRenewList(batchNo, "frnpro", userId, MEMO_E + e.getMessage(), null);
			return getReturnResult(MEMO_E + e.getMessage());
		} finally {
			if (txtFile1!=null && txtFile1.exists()) {
				FileUtils.forceDelete(txtFile1);
			}
			if (txtFile2!=null && txtFile2.exists()) {
				FileUtils.forceDelete(txtFile2);
			}
			if (txtFile3!=null && txtFile3.exists()) {
				FileUtils.forceDelete(txtFile3);
			}
			if (txtFile4!=null && txtFile4.exists()) {
				FileUtils.forceDelete(txtFile4);
			}
			if (txtFile5!=null && txtFile5.exists()) {
				FileUtils.forceDelete(txtFile5);
			}
			if (txtFile6!=null && txtFile6.exists()) {
				FileUtils.forceDelete(txtFile6);
			}
		}
		updateFirRenewList(batchNo, "frnpro", userId, MEMO_OK, bno);
		return getReturnResult("OK");
	}
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

	@SuppressWarnings("unchecked")
	private int genOtherFile(String batchNo, String bno, String pPhone, String fileType, String userId, Result result) throws Exception {
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = bno + ".TXT";
		String content = "";
		File file = null;
		File zipFile = null;
		int dataCount = 0;
		
		try {
			List<FirRenewListForFileVo> dataList = (List<FirRenewListForFileVo>) result.getResObject();
			dataCount = dataList.size();
			content = genTxtContent(dataList, pPhone, fileType);
			file = genTxtFile(tempFolder, content, filename, StandardCharsets.UTF_16);
			String zipFilepath = tempFolder + filename + ".ZIP";

			writeZip(zipFilepath, file, FILEPWD);
			FileUtils.forceDelete(file);

			zipFile = new File(zipFilepath);

			FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, bno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirRenewList(batchNo, fileType, userId, MEMO_E + fileUploadResponseVo.getMessage(), null);
				throw new SystemException(fileUploadResponseVo.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("genRejectFile uploadFileToftp error", e);
			updateFirRenewList(batchNo, fileType, userId, MEMO_E + e.toString(), null);
			throw new SystemException(MEMO_E + e.toString());
		}finally {
			FileUtils.forceDelete(zipFile);
		}
		
		updateFirRenewList(batchNo, fileType, userId, MEMO_OK, bno);
		return dataCount;
	}
	
	private String genTxtContent(List<FirRenewListForFileVo> dataList, String pPhone, String filetype) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("保單號碼\t被保險人\t要保人\t要保人郵遞區號\t要保人通訊地址\t標的物郵遞區號\t標的物地址\t火險保額\t火險保費\t")
		.append("基本地震保額\t基本地震保費\t總保險費\t保單起年\t保單起月\t保單起日\t保單迄年\t保單迄月\t保單迄日\t")
		.append("服務人員代號\t服務人員名稱\t公司電話\t本年度火險保額\t本年度火險保費\t本年度基本地震保額\t本年度基本地震保費\t")
		.append("本年度總保險費\t抵押權人\t業務來源\t業務來源中文名稱\t續保約定\t");
		if("ncore".equals(filetype)) {
			sb.append("單位\t登錄證字號\t業務員姓名\t繳費類型\t信用卡\t有效年月\t");
		}
		sb.append("\r\n");
		
		for (FirRenewListForFileVo data : dataList) {
			Map<String, String> handleMap = changeHandler1code(data.getHandler1code(),data.getHandler1name(),data.getHandleridentifynumber());
			//新核心續件依更換後的服務人員取得對應的公司電話
			if("ncore".equals(filetype)) {
				pPhone = coreFindPphone(handleMap.get("handler1code"));
			}
			sb.append(StringUtil.nullToSpace(data.getOldPolicyno())).append("\t")//保單號碼
			.append(StringUtil.nullToSpace(data.getInsuredname())).append("\t")//被保險人
			.append(StringUtil.nullToSpace(data.getAppliname())).append("\t")//要保人
			.append(StringUtil.nullToSpace(data.getPostcode())).append("\t")//要保人郵遞區號
			.append(StringUtil.nullToSpace(data.getPostaddress())).append("\t")//要保人通訊地址
			.append(StringUtil.nullToSpace(data.getAddresscode())).append("\t")//標的物郵遞區號
			.append(StringUtil.nullToSpace(data.getAddressdetailinfo())).append("\t")//標的物地址
			.append(numNullToSpace(data.getOldAmtF())).append("\t")//火險保額
			.append(numNullToSpace(data.getOldPremF())).append("\t")//火險保費
			.append(numNullToSpace(data.getOldAmtQ())).append("\t")//基本地震保額
			.append(numNullToSpace(data.getOldPremQ())).append("\t")//基本地震保費
			.append(numNullToSpace(data.getOldPrem())).append("\t")//總保險費
			.append(StringUtil.nullToSpace(data.getOldY())).append("\t")//保單起年
			.append(StringUtil.nullToSpace(data.getOldM())).append("\t")//保單起月
			.append(StringUtil.nullToSpace(data.getOldD())).append("\t")//保單起日
			.append(StringUtil.nullToSpace(data.getNewY())).append("\t")//保單迄年
			.append(StringUtil.nullToSpace(data.getNewM())).append("\t")//保單迄月
			.append(StringUtil.nullToSpace(data.getNewD())).append("\t")//保單迄日
			.append(handleMap.get("handler1code")).append("\t")//服務人員代號
			.append(handleMap.get("handle1name")).append("\t")//服務人員名稱
			.append(pPhone).append("\t")//公司電話
			.append(numNullToSpace(data.getAmtF())).append("\t")//本年度火險保額
			.append(numNullToSpace(data.getPremF())).append("\t")//本年度火險保費
			.append(numNullToSpace(data.getAmtQ())).append("\t")//本年度基本地震保額
			.append(numNullToSpace(data.getPremQ())).append("\t")//本年度基本地震保費
			.append(numNullToSpace(data.getPrem())).append("\t")//本年度總保險費
			.append(StringUtil.nullToSpace(data.getMortgagee())).append("\t")//抵押權人
			.append(StringUtil.nullToSpace(data.getBusinessnature())).append("\t")//業務來源
			.append(StringUtil.nullToSpace(data.getBusinessename())).append("\t")//業務來源中文名稱
			.append(StringUtil.nullToSpace(data.getIsAutoRenew())).append("\t");//續保約定
			if("ncore".equals(filetype)) {
				sb.append(StringUtil.nullToSpace(data.getExtracomname())).append("\t")//單位
				.append(StringUtil.nullToSpace(data.getHandleridentifynumber())).append("\t")//登錄證字號
				.append(StringUtil.nullToSpace(data.getHandlername())).append("\t")//業務員姓名
				.append(StringUtil.nullToSpace(data.getRnPayway())).append("\t")//續期繳費類型
				.append(StringUtil.nullToSpace(data.getRnCreditno())).append("\t")//續期信用卡
				.append(StringUtil.nullToSpace(data.getRnCreditdate())).append("\t");//有效年月
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@SuppressWarnings("unchecked")
	private String genPpFileMainData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectMainData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("續保單號\t");
		sb.append("要保書號\t");
		sb.append("電子保單\t");
		sb.append("紙本保單-條款交付方式\t");
		sb.append("住火保額\t");
		sb.append("住火保費\t");
		sb.append("地震保額\t");
		sb.append("地震保費\t");
		sb.append("附加險保費\t");
		sb.append("總保險費\t");
		sb.append("保險起日-年\t");
		sb.append("保險起日-月\t");
		sb.append("保險起日-日\t");
		sb.append("保險迄日-年\t");
		sb.append("保險迄日-月\t");
		sb.append("保險迄日-日\t");
		sb.append("同棟樓是否有營業\t");
		sb.append("營業性質\t");
		sb.append("是否有貸款戶\t");
		sb.append("續保約定條款\t");
		sb.append("服務人員代號\t");
		sb.append("服務人員姓名\t");
		sb.append("單位代號\t");
		sb.append("單位名稱\t");
		sb.append("登錄證字號\t");
		sb.append("業務員姓名\t");
		sb.append("業務來源代碼\t");
		sb.append("業務來源名稱\t");
		sb.append("保險經紀(代理)人\t");
		sb.append("是否有批改\t");
		sb.append("是否有理賠\t");
		sb.append("前期保額\t");
		sb.append("前期保費\t");
		sb.append("保單備註\t");
		sb.append("繳費方式\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("OLD_POLICYNO"))).append("\t"); // 續保單號
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(StringUtil.nullToSpace((String) map.get("EPOLICY"))).append("\t"); // 電子保單
				sb.append(StringUtil.nullToSpace((String) map.get("CLAUSE_SENDTYPE"))).append("\t"); // 紙本保單-條款交付方式
				sb.append(this.parseBigDecimal((BigDecimal) map.get("AMT_F"))).append("\t"); // 住火保額
				sb.append(this.parseBigDecimal((BigDecimal) map.get("PREM_F"))).append("\t"); // 住火保費
				sb.append(this.parseBigDecimal((BigDecimal) map.get("AMT_Q"))).append("\t"); // 地震保額
				sb.append(this.parseBigDecimal((BigDecimal) map.get("PREM_Q"))).append("\t"); // 地震保費
				sb.append(this.parseBigDecimal((BigDecimal) map.get("PREM_A"))).append("\t"); // 附加險保費
				sb.append(this.parseBigDecimal((BigDecimal) map.get("PREM"))).append("\t"); // 總保險費
				sb.append(StringUtil.nullToSpace((String) map.get("OLD_Y"))).append("\t"); // 保險起日-年
				sb.append(StringUtil.nullToSpace((String) map.get("OLD_M"))).append("\t"); // 保險起日-月
				sb.append(StringUtil.nullToSpace((String) map.get("OLD_D"))).append("\t"); // 保險起日-日
				sb.append(this.parseBigDecimal((BigDecimal) map.get("NEW_Y"))).append("\t"); // 保險迄日-年
				sb.append(StringUtil.nullToSpace((String) map.get("NEW_M"))).append("\t"); // 保險迄日-月
				sb.append(StringUtil.nullToSpace((String) map.get("NEW_D"))).append("\t"); // 保險迄日-日
				sb.append(StringUtil.nullToSpace((String) map.get("BUSINESS"))).append("\t"); // 同棟樓是否有營業
				sb.append(StringUtil.nullToSpace((String) map.get("BUSINESS_TYPE"))).append("\t"); // 營業性質
				sb.append(StringUtil.nullToSpace((String) map.get("ISLOANS"))).append("\t"); // 是否有貸款戶
				sb.append(StringUtil.nullToSpace((String) map.get("IS_AUTO_RENEW"))).append("\t"); // 續保約定條款
				sb.append(StringUtil.nullToSpace((String) map.get("HANDLER1CODE"))).append("\t"); // 服務人員代號
				sb.append(StringUtil.nullToSpace((String) map.get("HANDLER1NAME"))).append("\t"); // 服務人員姓名
				sb.append(StringUtil.nullToSpace((String) map.get("EXTRACOMCODE"))).append("\t"); // 單位代號
				sb.append(StringUtil.nullToSpace((String) map.get("EXTRACOMNAME"))).append("\t"); // 單位名稱
				sb.append(StringUtil.nullToSpace((String) map.get("HANDLERIDENTIFYNUMBER"))).append("\t"); // 登錄證字號
				sb.append(StringUtil.nullToSpace((String) map.get("HANDLERNAME"))).append("\t"); // 業務員姓名
				sb.append(StringUtil.nullToSpace((String) map.get("BUSINESSNATURE"))).append("\t"); // 業務來源代碼
				sb.append(StringUtil.nullToSpace((String) map.get("BUSINESSENAME"))).append("\t"); // 業務來源名稱
				sb.append(StringUtil.nullToSpace((String) map.get("AGENTCODE"))).append("\t"); // 保險經紀(代理)人
				sb.append(StringUtil.nullToSpace((String) map.get("HAS_ENDORSE"))).append("\t"); // 是否有批改
				sb.append(StringUtil.nullToSpace((String) map.get("HAS_CLAIM"))).append("\t"); // 是否有理賠
				sb.append(this.parseBigDecimal((BigDecimal) map.get("OLD_AMT"))).append("\t"); // 前期保額
				sb.append(this.parseBigDecimal((BigDecimal) map.get("OLD_PREM"))).append("\t"); // 前期保費
				sb.append(StringUtil.nullToSpace((String) map.get("NOTE"))).append("\t"); // 保單備註
				sb.append(StringUtil.nullToSpace((String) map.get("PAYWAY"))).append("\t"); // 繳費方式
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	@SuppressWarnings("unchecked")
	private String genPpFileInsuredData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectInsuredData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("要保書號\t");
		sb.append("序號\t");
		sb.append("關係人標誌\t");
		sb.append("關係人類型\t");
		sb.append("姓名\t");
		sb.append("身分證/統編\t");
		sb.append("生日/註冊日\t");
		sb.append("通訊處郵遞區號\t");
		sb.append("通訊處地址\t");
		sb.append("市話\t");
		sb.append("行動電話\t");
		sb.append("電子信箱\t");
		sb.append("國籍\t");
		sb.append("居住地/註冊地\t");
		sb.append("行/職業別\t");
		sb.append("是否為高風險職業\t");
		sb.append("代表人名稱\t");
		sb.append("上市櫃公司\t");
		sb.append("發行無記名股票\t");
		sb.append("與被保人關係\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(this.parseBigDecimal((BigDecimal) map.get("SERIALNO"))).append("\t"); // 序號
				sb.append(StringUtil.nullToSpace((String) map.get("INSUREDFLAG"))).append("\t"); // 關係人標誌
				sb.append(StringUtil.nullToSpace((String) map.get("INSUREDNATURE"))).append("\t"); // 關係人類型
				sb.append(StringUtil.nullToSpace((String) map.get("INSUREDNAME"))).append("\t"); // 姓名
				sb.append(StringUtil.nullToSpace((String) map.get("IDENTIFYNUMBER"))).append("\t"); // 身分證/統編
				sb.append(StringUtil.nullToSpace((String) map.get("BIRTHDAY"))).append("\t"); // 生日/註冊日
				sb.append(StringUtil.nullToSpace((String) map.get("POSTCODE"))).append("\t"); // 通訊處郵遞區號
				sb.append(StringUtil.nullToSpace((String) map.get("POSTADDRESS"))).append("\t"); // 通訊處地址
				sb.append(StringUtil.nullToSpace((String) map.get("PHONENUMBER"))).append("\t"); // 市話
				sb.append(StringUtil.nullToSpace((String) map.get("MOBILE"))).append("\t"); // 行動電話
				sb.append(StringUtil.nullToSpace((String) map.get("EMAIL"))).append("\t"); // 電子信箱
				sb.append(StringUtil.nullToSpace((String) map.get("COUNTRYCNAME"))).append("\t"); // 國籍
				sb.append(StringUtil.nullToSpace((String) map.get("DOMICILECNAME"))).append("\t"); // 居住地/註冊地
				sb.append(StringUtil.nullToSpace((String) map.get("OCCUPATIONCODE"))).append("\t"); // 行/職業別
				sb.append(StringUtil.nullToSpace((String) map.get("ISHIGHDENGEROCCUPATION"))).append("\t"); // 是否為高風險職業
				sb.append(StringUtil.nullToSpace((String) map.get("HEADNAME"))).append("\t"); // 代表人名稱
				sb.append(StringUtil.nullToSpace((String) map.get("LISTEDCABINETCOMPANY"))).append("\t"); // 上市櫃公司
				sb.append(StringUtil.nullToSpace((String) map.get("BEARER"))).append("\t"); // 發行無記名股票
				sb.append(StringUtil.nullToSpace((String) map.get("INSUREDIDENTITY"))).append("\t"); // 與被保人關係
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	@SuppressWarnings("unchecked")
	private String genPpFileAddressData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectAddressData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("要保書號\t");
		sb.append("地址序號\t");
		sb.append("標的物郵遞區號\t");
		sb.append("標的物地址\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(this.parseBigDecimal((BigDecimal) map.get("ADDRESSNO"))).append("\t"); // 地址序號
				sb.append(StringUtil.nullToSpace((String) map.get("ADDRESSCODE"))).append("\t"); // 標的物郵遞區號
				sb.append(StringUtil.nullToSpace((String) map.get("ADDRESSDETAILINFO"))).append("\t"); // 標的物地址
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	@SuppressWarnings("unchecked")
	private String genPpFilePropData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectPropData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("要保書號\t");
		sb.append("地址序號\t");
		sb.append("建築物序號\t");
		sb.append("外牆\t");
		sb.append("屋頂\t");
		sb.append("坪數\t");
		sb.append("年分\t");
		sb.append("總樓層\t");
		sb.append("建築等級\t");
		sb.append("建築等級說明\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(StringUtil.nullToSpace((String) map.get("ADDRESSNO"))).append("\t"); // 地址序號
				sb.append(StringUtil.nullToSpace((String) map.get("BUILDINGNO"))).append("\t"); // 建築物序號
				sb.append(StringUtil.nullToSpace((String) map.get("WALLMATERIAL"))).append("\t"); // 外牆
				sb.append(StringUtil.nullToSpace((String) map.get("ROOFMATERIAL"))).append("\t"); // 屋頂
				sb.append(this.parseBigDecimal((BigDecimal) map.get("BUILDAREA"))).append("\t"); // 坪數
				sb.append(StringUtil.nullToSpace((String) map.get("BUILDYEARS"))).append("\t"); // 年分
				sb.append(StringUtil.nullToSpace((String) map.get("SUMFLOORS"))).append("\t"); // 總樓層
				sb.append(StringUtil.nullToSpace((String) map.get("STRUCTURE"))).append("\t"); // 建築等級
				sb.append(StringUtil.nullToSpace((String) map.get("BUILDDETAILINFO"))).append("\t"); // 建築等級說明
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	@SuppressWarnings("unchecked")
	private String genPpFileMortgageeData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectMortgageeData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("要保書號\t");
		sb.append("抵押權人順位\t");
		sb.append("抵押權人代號\t");
		sb.append("抵押權人名稱\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(this.parseBigDecimal((BigDecimal) map.get("SEQUENCENO"))).append("\t"); // 抵押權人順位
				sb.append(StringUtil.nullToSpace((String) map.get("MORTGAGEEPCODE"))).append("\t"); // 抵押權人代號
				sb.append(StringUtil.nullToSpace((String) map.get("MORTGAGEEPEOPLE"))).append("\t"); // 抵押權人名稱
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	@SuppressWarnings("unchecked")
	private String genPpFileItemkindData(String batchNo) throws Exception {

		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firRenewListService.selectItemkindData(params);

		StringBuilder sb = new StringBuilder();
		// 建立表頭列，以tab分隔
		sb.append("要保書號\t");
		sb.append("序號\t");
		sb.append("險種代號\t");
		sb.append("險種名稱\t");
		sb.append("保險標的物\t");
		sb.append("標的物性質\t");
		sb.append("保險金額\t");
		sb.append("費率\t");
		sb.append("短期係數\t");
		sb.append("保費\t");
		sb.append("使用性質代號\t");
		sb.append("使用性質名稱\t");
		sb.append("建築物序號\t");
		sb.append("地址序號\t");
		sb.append("附加險等級\t");
		sb.append("建築等級\t");
		sb.append("\r\n");

		if (result.getResObject() != null) {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getResObject();
			for (Map<String, Object> map : dataList) {
				sb.append(StringUtil.nullToSpace((String) map.get("PROPOSALNO"))).append("\t"); // 要保書號
				sb.append(this.parseBigDecimal((BigDecimal) map.get("ITEMKINDNO"))).append("\t"); // 序號
				sb.append(StringUtil.nullToSpace((String) map.get("KINDCODE"))).append("\t"); // 險種代號
				sb.append(StringUtil.nullToSpace((String) map.get("KINDNAME"))).append("\t"); // 險種名稱
				sb.append(StringUtil.nullToSpace((String) map.get("ITEMCODE"))).append("\t"); // 保險標的物
				sb.append(StringUtil.nullToSpace((String) map.get("ITEMNATURE"))).append("\t"); // 標的物性質
				sb.append(this.parseBigDecimal((BigDecimal) map.get("AMOUNT"))).append("\t"); // 保險金額
				sb.append(this.parseBigDecimal((BigDecimal) map.get("RATE"))).append("\t"); // 費率
				sb.append(this.parseBigDecimal((BigDecimal) map.get("SHORTRATE"))).append("\t"); // 短期係數
				sb.append(this.parseBigDecimal((BigDecimal) map.get("PREMIUM"))).append("\t"); // 保費
				sb.append(StringUtil.nullToSpace((String) map.get("USINGNATURE"))).append("\t"); // 使用性質代號
				sb.append(StringUtil.nullToSpace((String) map.get("USINGNATURENAME"))).append("\t"); // 使用性質名稱
				sb.append(StringUtil.nullToSpace((String) map.get("BUILDINGNO"))).append("\t"); // 建築物序號
				sb.append(this.parseBigDecimal((BigDecimal) map.get("ADDRESSNO"))).append("\t"); // 地址序號
				sb.append(StringUtil.nullToSpace((String) map.get("SUBGRADE"))).append("\t"); // 附加險等級
				sb.append(StringUtil.nullToSpace((String) map.get("STRUCTURE"))).append("\t"); // 建築等級
				sb.append("\r\n");
			}
		}
		String content = sb.toString();
		logger.debug(">>> content: " + content);

		return content;
	}

	private String parseBigDecimal(BigDecimal b) {
		if (b!=null) {
			return b.toString();
		} else {
			return "";
		}
	}
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

	//產生核心續件到期通知-有附加險 EXCEL
	@SuppressWarnings("unchecked")
	@Override
	public Result genNcoreaFile(String batchNo, String userId, String fileType) throws Exception {
		String ncoreaBno = batchNo + "_NCOREA";
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("isAddIns", "Y");
		Result result = firRenewListDtlService.findRenewListForCoreFile(params);
		if(result.getResObject() == null) {
			updateFirRenewList(batchNo, fileType, userId, MEMO_N, null);
			return this.getReturnResult("查無資料，無法產生檔案。");
		}
		
		List<FirRenewListForFileVo> ncoreaList = (List<FirRenewListForFileVo>) result.getResObject();
		
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = ncoreaBno + ".XLSX";
		String filepath = tempFolder + filename;
		File file = new File(filepath);
		FileOutputStream fileOut;
		
		//標題列
		String[] title = { "保單號碼", "被保險人", "要保人", "要保人郵遞區號", "要保人通訊地址", "標的物郵遞區號", "標的物地址", "火險保額", "火險保費",
				"基本地震保額", "基本地震保費", "附加險保費", "總保險費", "保單起年", "保單起月", "保單起日", "保單迄年", "保單迄月", "保單迄日",
				"服務人員代號", "服務人員名稱", "公司電話", "本年度火險保額", "本年度火險保費", "本年度基本地震保額", "本年度基本地震保費", "本年度附加險保費",
				"本年度總保險費","抵押權人","業務來源","業務來源中文名稱","單位","登錄證字號","業務員姓名","續保約定","繳費類型","信用卡","有效年月"};
		try {
			// 建立workbook格式
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				
				XSSFSheet sheet = createSheetAndTitle(workbook, title);
				int rownum = 1;
				for (int i = 0; i < ncoreaList.size(); i++) {
					FirRenewListForFileVo ncoreaData = ncoreaList.get(i);
					rownum = createNcoreaCell(ncoreaData, sheet, rownum);
				}

				fileOut = new FileOutputStream(file);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				
				encrypt(FILEPWD, filepath);
			}

		} catch (Exception e) {
			logger.error("genNcoreaFile error", e);
			updateFirRenewList(batchNo, fileType, userId, MEMO_E, null);
			return getReturnResult(MEMO_E + e);
		}
		
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(file, ncoreaBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())){
				updateFirRenewList(batchNo, fileType, userId, MEMO_E + fileUploadResponseVo.getMessage(), null);
				return getReturnResult(MEMO_E + fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genNcoreaFile uploadFileTofts error", e);
			updateFirRenewList(batchNo, fileType, userId, MEMO_E + e.toString(), null);
			return getReturnResult(MEMO_E + e.toString());
		}
		
		FileUtils.forceDelete(file);
		updateFirRenewList(batchNo, fileType, userId, MEMO_OK, ncoreaBno);
		return getReturnResult("OK-" + ncoreaList.size() + "筆");
	}
	
	private Integer createNcoreaCell(FirRenewListForFileVo data, XSSFSheet sheet, int rownum) throws Exception {
		XSSFRow row = sheet.createRow(rownum); //建立列
		row.createCell(0).setCellValue(StringUtil.nullToSpace(data.getOldPolicyno()));
		row.createCell(1).setCellValue(StringUtil.nullToSpace(data.getInsuredname()));
		row.createCell(2).setCellValue(StringUtil.nullToSpace(data.getAppliname()));
		row.createCell(3).setCellValue(StringUtil.nullToSpace(data.getPostcode()));
		row.createCell(4).setCellValue(StringUtil.nullToSpace(data.getPostaddress()));
		row.createCell(5).setCellValue(StringUtil.nullToSpace(data.getAddresscode()));
		row.createCell(6).setCellValue(StringUtil.nullToSpace(data.getAddressdetailinfo()));
		row.createCell(7).setCellValue(numNullToSpace(data.getOldAmtF()));
		row.createCell(8).setCellValue(numNullToSpace(data.getOldPremF()));
		row.createCell(9).setCellValue(numNullToSpace(data.getOldAmtQ()));
		row.createCell(10).setCellValue(numNullToSpace(data.getOldPremQ()));
		row.createCell(11).setCellValue(numNullToSpace(data.getOldPremA()));
		row.createCell(12).setCellValue(numNullToSpace(data.getOldPrem()));
		row.createCell(13).setCellValue(StringUtil.nullToSpace(data.getOldY()));
		row.createCell(14).setCellValue(StringUtil.nullToSpace(data.getOldM()));
		row.createCell(15).setCellValue(StringUtil.nullToSpace(data.getOldD()));
		row.createCell(16).setCellValue(StringUtil.nullToSpace(data.getNewY()));
		row.createCell(17).setCellValue(StringUtil.nullToSpace(data.getNewM()));
		row.createCell(18).setCellValue(StringUtil.nullToSpace(data.getNewD()));
		Map<String, String> handleMap = changeHandler1code(data.getHandler1code(),data.getHandler1name(),data.getHandleridentifynumber());
		row.createCell(19).setCellValue(handleMap.get("handler1code"));
		row.createCell(20).setCellValue(handleMap.get("handle1name"));
		row.createCell(21).setCellValue(coreFindPphone(handleMap.get("handler1code")));
		row.createCell(22).setCellValue(numNullToSpace(data.getAmtF()));
		row.createCell(23).setCellValue(numNullToSpace(data.getPremF()));
		row.createCell(24).setCellValue(numNullToSpace(data.getAmtQ()));
		row.createCell(25).setCellValue(numNullToSpace(data.getPremQ()));
		row.createCell(26).setCellValue(numNullToSpace(data.getPremA()));
		row.createCell(27).setCellValue(numNullToSpace(data.getPrem()));
		row.createCell(28).setCellValue(StringUtil.nullToSpace(data.getMortgagee()));
		row.createCell(29).setCellValue(StringUtil.nullToSpace(data.getBusinessnature()));
		row.createCell(30).setCellValue(StringUtil.nullToSpace(data.getBusinessename()));
		row.createCell(31).setCellValue(StringUtil.nullToSpace(data.getExtracomname()));
		row.createCell(32).setCellValue(StringUtil.nullToSpace(data.getHandleridentifynumber()));
		row.createCell(33).setCellValue(StringUtil.nullToSpace(data.getHandlername()));
		row.createCell(34).setCellValue(StringUtil.nullToSpace(data.getIsAutoRenew()));
		row.createCell(35).setCellValue(StringUtil.nullToSpace(data.getRnPayway()));
		row.createCell(36).setCellValue(StringUtil.nullToSpace(data.getRnCreditno()));
		row.createCell(37).setCellValue(StringUtil.nullToSpace(data.getRnCreditdate()));
		rownum++;
		return rownum;
	}
	
	private String numNullToSpace(String str) throws Exception {
		return "0".equals(str)?"":StringUtil.nullToSpace(str);
	}
	
	private String coreFindPphone(String handler1code) throws SystemException, Exception{
		Map<String,String> params = new HashMap<>();
		params.put("usercode", handler1code);
		params.put("pType", "2");
		Result result;
			result = firRenewPhoneService.FindPhoneByHandler1code(params);
			if(result.getResObject()!=null) {
				FirRenewPhone firRenewPhone = (FirRenewPhone) result.getResObject();
				return StringUtil.nullToSpace(firRenewPhone.getpPhone());
			}
		return "";
	}
	
	private void updateFirRenewList(String batchNo, String filetype, String userId, String memo, String bno) throws Exception {
		FirRenewList firRenewList = new FirRenewList();
		firRenewList.setBatchNo(batchNo);
		Date sysDate = new Date();
		firRenewList.setIupdate(userId);
		firRenewList.setDupdate(sysDate);
		if("ctbc".equals(filetype)) {
			firRenewList.setI99050User(userId);
			firRenewList.setI99050Time(sysDate);
			firRenewList.setI99050Memo(memo);
			firRenewList.setI99050Bno(bno);
		}
		if("ncore".equals(filetype)) {
			firRenewList.setNcoreUser(userId);
			firRenewList.setNcoreTime(sysDate);
			firRenewList.setNcoreMemo(memo);
			firRenewList.setNcoreBno(bno);
		}
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 start
		if("tsca".equals(filetype)) {
			firRenewList.setI40503User(userId);
			firRenewList.setI40503Time(sysDate);
			firRenewList.setI40503Memo(memo);
			firRenewList.setI40503Bno(bno);
		}		
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 end 
		if("ncorea".equals(filetype)) {
			firRenewList.setNcoreaUser(userId);
			firRenewList.setNcoreaTime(sysDate);
			firRenewList.setNcoreaMemo(memo);
			firRenewList.setNcoreaBno(bno);
		}
		if("bop".equals(filetype)) {
			firRenewList.setI99065User(userId);
			firRenewList.setI99065Time(sysDate);
			firRenewList.setI99065Memo(memo);
			firRenewList.setI99065Bno(bno);
		}
		if("fb".equals(filetype)) {
			firRenewList.setI00107User(userId);
			firRenewList.setI00107Time(sysDate);
			firRenewList.setI00107Memo(memo);
			firRenewList.setI00107Bno(bno);
		}
		// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
		if("frnpro".equals(filetype)) {
			firRenewList.setFrnproUser(userId);
			firRenewList.setFrnproTime(sysDate);
			firRenewList.setFrnproMemo(memo);
			firRenewList.setFrnproBno(bno);
			firRenewList.setIupdate(userId);
			firRenewList.setDupdate(new Date());
		}
		// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
		genRenewListService.updateFirRenewList(firRenewList);
	}
	
	//服務人員替換處理，只供住火產生到期通知判斷是否為失效服務人員使用，非完善解決服務人員替換之方法
	@SuppressWarnings("unchecked")
	public Map<String, String> changeHandler1code(String handler1code, String handle1name, String handleridentifynumber) throws SystemException, Exception {
		Map<String,String> handleMap = new HashMap<>();
		Map<String,String> params = new HashMap<>();
		
		//查詢SALES.PRPDAGENT表，查詢是否為有效的台壽業務員，並取得所屬服務人員
		params.put("logincode", handleridentifynumber);
		Result result =  prpdagentService.FindForFirChangeHandler1code(params);
		//若查詢結果有資料，先判斷業務員是否失效，若為有效業務員，則取得對應服務人員，若為失效業務員則帶原保單服務人員
		if (result.getResObject() != null) {
			List<Prpdagent> hadlecodeList = (List<Prpdagent>) result.getResObject();
			boolean isValid = false;
			for (Prpdagent hadleData : hadlecodeList) {
				// 若有效業務員且對應服務人員不為空，則取得服務人員
				if ("1".equals(hadleData.getValidstatus())) {
					isValid = true;
					if (!StringUtil.isSpace(hadleData.getHandlercode())) {
						handler1code = hadleData.getHandlercode();
					}
				}
			}
			//若為無效台壽業務員，則帶原始服務人員，業務員代碼前加上*
			if(!isValid) {
				handleridentifynumber = "*" + handleridentifynumber;
			}
		}
		handleMap.put("handleridentifynumber", handleridentifynumber);
		
		//查詢SALES.COMMDATA_CMEMFIL 取得對應服務人員
		params.clear();
		params.put("cmem00", handler1code);
		result = commdataCmemfilService.FindForFirChangeHandler1code(params);
		
		if(result.getResObject() != null) {
			List<CommdataCmemfil> hadlecodeList = (List<CommdataCmemfil>)result.getResObject();
			CommdataCmemfil hadleData = hadlecodeList.get(0);
			
			//若取得的服務人員已失效卻無設定對應服務人員，則將原始服務人員代碼前加上*號
			if("Y".equals(hadleData.getCmem18()) && handler1code.equals(hadleData.getCmem00())) {
				handleMap.put("handler1code", "*" + handler1code);
				handleMap.put("handle1name", handle1name);
				return handleMap;
			}else {
				handleMap.put("handler1code", hadleData.getCmem00());
				handleMap.put("handle1name", hadleData.getCmem01());
				return handleMap;
			}
		}
		
		//若查無接替的服務人員資料，或查詢到的服務人員為原始服務人員，則將服務人員代碼前加上*號
		handleMap.put("handler1code", "*" + handler1code);
		handleMap.put("handle1name", handle1name);
		return handleMap;
	}
	
	private XSSFSheet createSheetAndTitle(XSSFWorkbook workbook, String[] title) {
		XSSFSheet sheet = workbook.createSheet();
		// 建立excel欄位
		XSSFRow rowTitle = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			rowTitle.createCell(i).setCellValue(title[i]);
		}
		return sheet;
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
	
	public File genTxtFile(String tempDir, String content, String filename, Charset charset) {
		File file = new File(tempDir+filename);
		try(BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset))){
			bufWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	//壓縮檔案
	private static void writeZip(String filepath, File file, String pwd) throws Exception {			
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
			zipFile.addFile(file, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	private void zipFiles(String filePath, ArrayList<File> files, String pswd) throws Exception {
		ZipFile zipFile = new ZipFile(filePath);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if (!StringUtil.isSpace(pswd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setPassword(pswd);
		}
		zipFile.addFiles(files, parameters);
	}
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

	//加密EXCEL
	private static void encrypt(String filePwd, String filepath) throws InvalidFormatException, IOException, GeneralSecurityException {
		OPCPackage opc = null;
		try(POIFSFileSystem fs = new POIFSFileSystem()){
			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
			Encryptor enc = info.getEncryptor();
			enc.confirmPassword(filePwd);
			opc = OPCPackage.open(new File(filepath), PackageAccess.READ_WRITE);
			OutputStream os = enc.getDataStream(fs);
			opc.save(os);
			opc.close();
			FileOutputStream fos = new FileOutputStream(filepath);
			fs.writeFilesystem(fos);
			fos.flush();
			fos.close();
		}
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

	public FirRenewPhoneService getFirRenewPhoneService() {
		return firRenewPhoneService;
	}

	public void setFirRenewPhoneService(FirRenewPhoneService firRenewPhoneService) {
		this.firRenewPhoneService = firRenewPhoneService;
	}

	public FirRenewListService getFirRenewListService() {
		return firRenewListService;
	}

	public void setFirRenewListService(FirRenewListService firRenewListService) {
		this.firRenewListService = firRenewListService;
	}

	public FirRenewListDtlService getFirRenewListDtlService() {
		return firRenewListDtlService;
	}

	public void setFirRenewListDtlService(FirRenewListDtlService firRenewListDtlService) {
		this.firRenewListDtlService = firRenewListDtlService;
	}

	public GenRenewListService getGenRenewListService() {
		return genRenewListService;
	}

	public void setGenRenewListService(GenRenewListService genRenewListService) {
		this.genRenewListService = genRenewListService;
	}

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public CommdataCmemfilService getCommdataCmemfilService() {
		return commdataCmemfilService;
	}

	public void setCommdataCmemfilService(CommdataCmemfilService commdataCmemfilService) {
		this.commdataCmemfilService = commdataCmemfilService;
	}
}
