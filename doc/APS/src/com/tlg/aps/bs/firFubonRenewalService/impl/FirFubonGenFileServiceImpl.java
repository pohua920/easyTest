package com.tlg.aps.bs.firFubonRenewalService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firFubonRenewalService.FirFubonGenFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps034genFileVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchFb;
import com.tlg.prpins.entity.FirAgtrnTmpFb;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchFbService;
import com.tlg.prpins.service.FirAgtrnTmpFbService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.RptUtil;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirFubonGenFileServiceImpl implements FirFubonGenFileService {

	private static final Logger logger = Logger.getLogger(FirFubonGenFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirAgtrnTmpFbService firAgtrnTmpFbService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtrnBatchFbService firAgtrnBatchFbService;
	private RfrcodeService rfrcodeService;
	private Map<String, String> wallMap = new HashMap<>();

	//產生差異明細檔 EXCEL
	@SuppressWarnings("unchecked")
	@Override
	public Result genDiffFile(String batchNo, String userId, String filePwd) throws Exception{
		
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = "FBRN_DIFF_"+getDateFormat(new Date(),"yyyyMMddHHmmss")+".XLSX";
		String filepath = tempFolder + filename;
		File file = new File(filepath);
		FileOutputStream fos;
		
		//產險資料欄位標題
		String[] totalTitle = { "項次", "銀行住火保單號碼", "地震基本保單號碼", "被保險人姓名", "被保險人ID", "保險起日", "保險迄日", "保險標的物地址", "抵押權人",
				"建築物結構", "總樓層數", "主機坪數", "使用性質", "住火保額", "地震基本險保額", "擔保品編號+保險單建檔序號", "要保人ID", "要保人姓名", "保險公司代號",
				"作業中心", "業務員姓名", "登錄字號" };
		
		//作業中心欄位標題
		String[] pcTitle = { "項次", "銀行住火保單號碼", "地震基本保單號碼", "主機所有權人姓名", "主機所有權人ID", "保險起日", "保險迄日", "主機保險標的物地址",
				"抵押權人", "建築物結構", "總樓層數", "主機坪數", "使用性質", "主機住火保額", "主機地震基本險保額", "擔保品編號+保險單建檔序號", "借款人ID", "借款人姓名",
				"保險公司代號", "作業中心", "業務員姓名", "登錄字號", "差異項目(產險資料)", "備註" };
		
		String diffBno = batchNo + "_DIFF";
		try {
			// 建立workbook格式
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				// 先建立作業中心頁籤
				Map<String, String> params = new HashMap<>();
				params.put("batchNo", batchNo);
				
				List<Aps034genFileVo> diffFileList = new ArrayList<>();
				Result result = firAgtrnTmpFbService.findFbDiffFile(params);
				if(result.getResObject()!=null) {
					diffFileList = (List<Aps034genFileVo>) result.getResObject();
				}
				
				result = firAgtrnTmpFbService.findFbProcessCenter(params);
				List<XSSFSheet> pcSheets = new ArrayList<>();
				Map<String, Integer> sheetsName = new HashMap<>();
				Map<Integer, Integer> pcRows = new HashMap<>();

				if (result.getResObject() != null) {
					List<FirAgtrnTmpFb> pcList = (List<FirAgtrnTmpFb>) result.getResObject();
					//作業中心頁籤標題取第一筆資料到期日年月
					String title = getRocDate(diffFileList.get(0).getFbEnddate(),"/","") + "續保差異明細表";
					for (int i = 0; i < pcList.size(); i++) {
						XSSFSheet sheets = workbook.createSheet(pcList.get(i).getFbProcessCenter());

						sheets.addMergedRegion(new CellRangeAddress(0, 0, 0, pcTitle.length - 1));//合併儲存格-起始行號，終止行號， 起始列號，終止列號
						XSSFRow row0 = sheets.createRow(0);
						row0.createCell(0).setCellValue(title);

						pcSheets.add(sheets);
						sheetsName.put(pcList.get(i).getFbProcessCenter(), i);
						pcRows.put(i, 2);
						XSSFRow rowTitles = sheets.createRow(1);
						for (int j = 0; j < pcTitle.length; j++) {
							rowTitles.createCell(j).setCellValue(pcTitle[j]);
						}
					}
				}

				// 再建立產險中心頁籤
				XSSFSheet sheetT = workbook.createSheet("產險資料");

				sheetT.addMergedRegion(new CellRangeAddress(0, 0, 0, totalTitle.length - 1));// 起始行號，終止行號， 起始列號，終止列號
				XSSFRow row0 = sheetT.createRow(0);
				row0.createCell(0).setCellValue("產險資料");
				// 建立excel欄位
				XSSFRow rowTitle = sheetT.createRow(1);
				for (int i = 0; i < totalTitle.length; i++) {
					rowTitle.createCell(i).setCellValue(totalTitle[i]);
				}

				// 建立EXCEL內容 有資料才新增，無資料產生空檔
				int totalRowNum = 2;
				if (!diffFileList.isEmpty()) {
					for (int i = 0; i < diffFileList.size(); i++) {
						// 產險資料sheet內容
						Aps034genFileVo diffData = diffFileList.get(i);
						XSSFRow rowT = sheetT.createRow(totalRowNum); // 建立列 必須為每次迴圈筆數往上加
						rowT.createCell(0).setCellValue(totalRowNum - 1);
						rowT.createCell(1).setCellValue(diffData.getFbPolicynoF());
						rowT.createCell(2).setCellValue(diffData.getFbPolicynoQ());
						rowT.createCell(3).setCellValue(diffData.getNameI());
						rowT.createCell(4).setCellValue(diffData.getIdI());
						rowT.createCell(5).setCellValue(diffData.getStartdate() != null
										? getDateFormat(diffData.getStartdate(), "yyyyMMdd") : null);
						rowT.createCell(6).setCellValue(diffData.getEnddate() != null
										? getDateFormat(diffData.getEnddate(), "yyyyMMdd") : null);
						rowT.createCell(7).setCellValue(diffData.getAddressdetailinfo());
						rowT.createCell(8).setCellValue(diffData.getFbMortgagee());
						rowT.createCell(9).setCellValue(diffData.getCodecname());
						rowT.createCell(10).setCellValue(diffData.getSumfloors());
						rowT.createCell(11).setCellValue(diffData.getBuildarea());
						rowT.createCell(12).setCellValue("住宅");
						rowT.createCell(13).setCellValue(diffData.getAmountF());
						rowT.createCell(14).setCellValue(diffData.getAmountQ());
						rowT.createCell(15).setCellValue(diffData.getFbColInsurcNo());
						rowT.createCell(16).setCellValue(diffData.getIdA());
						rowT.createCell(17).setCellValue(diffData.getNameA());
						rowT.createCell(18).setCellValue(diffData.getFbInsCom());
						rowT.createCell(19).setCellValue(diffData.getFbProcessCenter());
						rowT.createCell(20).setCellValue(diffData.getFbSalesName());
						rowT.createCell(21).setCellValue(diffData.getFbSalesId());
						totalRowNum++;

						// 依個別作業中心新增內容

						String processCenter = diffData.getFbProcessCenter();
						int pcIndex = sheetsName.get(processCenter);
						XSSFRow rowPc = pcSheets.get(pcIndex).createRow(pcRows.get(pcIndex));
						rowPc.createCell(0).setCellValue(pcRows.get(pcIndex) - 1);
						rowPc.createCell(1).setCellValue(diffData.getFbPolicynoF());
						rowPc.createCell(2).setCellValue(diffData.getFbPolicynoQ());
						rowPc.createCell(3).setCellValue(diffData.getFbNameI());
						rowPc.createCell(4).setCellValue(diffData.getFbIdI());
						rowPc.createCell(5).setCellValue(diffData.getFbStartdate() != null
										? getDateFormat(diffData.getFbStartdate(), "yyyyMMdd") : null);
						rowPc.createCell(6).setCellValue(diffData.getFbEnddate() != null
										? getDateFormat(diffData.getFbEnddate(), "yyyyMMdd") : null);
						rowPc.createCell(7).setCellValue(diffData.getFbAddress());
						rowPc.createCell(8).setCellValue(diffData.getFbMortgagee());
						 rowPc.createCell(9).setCellValue(diffData.getFbWallno());
						rowPc.createCell(10).setCellValue(diffData.getFbSumfloors());
						rowPc.createCell(11).setCellValue(diffData.getFbBuildarea());
						rowPc.createCell(12).setCellValue(diffData.getFbUseNature());
						rowPc.createCell(13).setCellValue(diffData.getFbAmountF());
						rowPc.createCell(14).setCellValue(diffData.getFbAmountQ());
						rowPc.createCell(15).setCellValue(diffData.getFbColInsurcNo());
						rowPc.createCell(16).setCellValue(diffData.getFbIdA());
						rowPc.createCell(17).setCellValue(diffData.getFbNameA());
						rowPc.createCell(18).setCellValue(diffData.getFbInsCom());
						rowPc.createCell(19).setCellValue(diffData.getFbProcessCenter());
						rowPc.createCell(20).setCellValue(diffData.getFbSalesName());
						rowPc.createCell(21).setCellValue(diffData.getFbSalesId());
						rowPc.createCell(22).setCellValue(diffData.getDiffReason());
						pcRows.put(pcIndex, pcRows.get(pcIndex) + 1);
					}

					// 最後一筆放總數
					XSSFRow rowTEnd = sheetT.createRow(totalRowNum);
					rowTEnd.createCell(0).setCellValue("筆數:");
					rowTEnd.createCell(1).setCellValue(totalRowNum - 2);
					for (int pc = 0; pc < pcSheets.size(); pc++) {
						XSSFRow rowPcEnd = pcSheets.get(pc).createRow(pcRows.get(pc));
						rowPcEnd.createCell(0).setCellValue("筆數:");
						rowPcEnd.createCell(1).setCellValue(pcRows.get(pc) - 2);
					}
				}

				fos = new FileOutputStream(file);
				workbook.write(fos);
				fos.flush();
				fos.close();
				//產生後加密EXCEL
				encrypt(filePwd,filepath);
			}
		} catch (Exception e) {
			logger.error("genDiffFile error", e);
			updateFirAgtrnBatchFb(batchNo, "diff", userId, "產生EXCEL失敗"+e, diffBno);
			return getReturnResult("產生EXCEL失敗"+e);
		}
		
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(file, diffBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())){
				updateFirAgtrnBatchFb(batchNo, "diff", userId, "上傳檔案伺服器失敗"+fileUploadResponseVo.getMessage(), diffBno);
				return getReturnResult("上傳檔案伺服器失敗"+ fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genDiffFile uploadFileToftp error", e);
			updateFirAgtrnBatchFb(batchNo, "diff", userId, "上傳檔案伺服器失敗"+e, diffBno);
			return getReturnResult("上傳檔案伺服器失敗"+ e);
		}
		
		FileUtils.forceDelete(file);
		updateFirAgtrnBatchFb(batchNo, "diff", userId, "OK", diffBno);
		return getReturnResult("產生差異明細檔成功");
	}
	
	//產生剔退檔 TXT
	@SuppressWarnings("unchecked")
	@Override
	public Result genRejectFile(String batchNo, String userId, String filePwd) throws Exception {
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = "FBINSF_10.TXT";
		String content = "";
		StringBuilder sb = new StringBuilder();
		File file = null;

		String sfBno = batchNo + "_SF";
		try {
			Map<String, String> params = new HashMap<>();
			params.put("batchNo", batchNo);
			Result result = firAgtrnBatchDtlService.findForFbRejectFile(params);
			if(result.getResObject() != null) {
				List<Aps034genFileVo> rejectList =  (List<Aps034genFileVo>) result.getResObject();
				for(Aps034genFileVo rejectData : rejectList) {
					sb.append(transLength(rejectData.getFbPolicynoF(),16,"s")).append(",")
					.append(transLength(rejectData.getFbPolicynoQ(),16,"s")).append(",")
					.append(transLength(rejectData.getFbNameI(),90,"s")).append(",")
					.append(transLength(rejectData.getFbIdI(),11,"s")).append(",")
					.append(transLength(rejectData.getFbStartdate() != null ? getDateFormat(rejectData.getFbStartdate(), "yyyyMMdd"):"", 8,"s")).append(",")
					.append(transLength(rejectData.getFbEnddate() != null ? getDateFormat(rejectData.getFbEnddate(), "yyyyMMdd"):"", 8,"s")).append(",")
					.append(transLength(rejectData.getFbAddress(),180,"s")).append(",")
					.append(transLength(rejectData.getFbMortgagee(),42,"s")).append(",")
					.append(transLength(rejectData.getFbWallno(),16,"s")).append(",")
					.append(transLength(rejectData.getFbSumfloors(),3,"s")).append(",")
					.append(transLength(rejectData.getFbBuildarea(),9,"s")).append(",")
					.append(transLength(rejectData.getFbUseNature(),6,"s")).append(",")
					.append(transLength(rejectData.getFbAmountF(),12,"s")).append(",")
					.append(transLength(rejectData.getFbAmountQ(),12,"s")).append(",")
					.append(transLength(rejectData.getFbColInsurcNo(),16,"s")).append(",")
					.append(transLength(rejectData.getFbIdA(),11,"s")).append(",")
					.append(transLength(rejectData.getFbNameA(),12,"s")).append(",")
					.append(transLength(rejectData.getFbInsCom(),2,"s")).append(",")
					.append(transLength(rejectData.getFbProcessCenter(),5,"s")).append(",")
					.append(transLength(rejectData.getFbSalesName(),20,"s")).append(",")
					.append(transLength(rejectData.getFbSalesId(),12,"s")).append(",")
					.append(transLength(rejectData.getSfReason(),1000,"s"))
					.append("\r\n");
				}
				content = sb.toString();
			}
			file = genTxtFile(tempFolder, content, filename, "big5");
		}catch(Exception e) {
			//產生剔退檔失敗
			logger.error("genRejectFile error", e);
			updateFirAgtrnBatchFb(batchNo, "reject", userId, "產生TXT失敗"+e, sfBno);
			return getReturnResult("剔退檔產生TXT失敗"+e);
		}
		String zipFilepath = tempFolder + filename+".ZIP";
		
		writeZip(zipFilepath, file, filePwd);
		FileUtils.forceDelete(file);
		
		File zipFile = new File(zipFilepath);
		
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, sfBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				updateFirAgtrnBatchFb(batchNo, "reject", userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), sfBno);
				return getReturnResult("剔退檔上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genRejectFile uploadFileToftp error", e);
			updateFirAgtrnBatchFb(batchNo, "reject", userId, "上傳檔案伺服器失敗"+e, sfBno);
			return getReturnResult("剔退檔上傳檔案伺服器失敗" + e);
		}
		
		//上傳富邦sftp
		if("fail".equals(uploadFileToSftp(zipFilepath , "fbUploadPath1"))) {
			updateFirAgtrnBatchFb(batchNo, "reject", userId, "上傳SFTP失敗，請下載檔案後手動上傳", sfBno);
			FileUtils.forceDelete(zipFile);
			return getReturnResult("剔退檔上傳SFTP失敗，請下載檔案後手動上傳");
		}
		
		//上傳後再將temp資料夾檔案刪除
		FileUtils.forceDelete(zipFile);
		
		updateFirAgtrnBatchFb(batchNo, "reject", userId, "OK", sfBno);
		return getReturnResult("產生剔退檔成功。");
	}
	
	//產生大保單 TXT
	@Override
	public Result genBigPolicy(String batchNo, String userId, String filePwd) throws Exception {
		
		List<String> folderList = new ArrayList<>();
		List<File> folderPathList = new ArrayList<>();
		
		String utf8Folder = configUtil.getString("tempFolder") + "FUGGX4_10_UTF8" + File.separator;
		File utf8FolderPath = new File(utf8Folder);
		if (!utf8FolderPath.exists()) {
			utf8FolderPath.mkdirs();
		}
		
		String big5Folder = configUtil.getString("tempFolder") + "FUGGX4_10_big5" + File.separator;
		File big5FolderPath = new File(big5Folder);
		if (!big5FolderPath.exists()) {
			big5FolderPath.mkdirs();
		}
		folderList.add(utf8Folder);
		folderList.add(big5Folder);
		folderPathList.add(utf8FolderPath);
		folderPathList.add(big5FolderPath);
		
		String filename = "FUGGX4_10.TXT";
		String content = "";
		File utf8File = null;
		File big5File = null;
		
		List<Aps034genFileVo> bpDataList = findbPData(batchNo);
		if(bpDataList.isEmpty()) {
			return getReturnResult("查無資料，無法產生大保單。");
		}
		
		String bpBno = batchNo + "_BP";
		
		try {
			content = genBpContent(bpDataList);
			utf8File = genTxtFile(utf8Folder , content, filename, "UTF-8");
			big5File = genTxtFile(big5Folder , content, filename, "big5");
			
		} catch (Exception e) {
			logger.error("genbigPolicy error", e);
			updateFirAgtrnBatchFb(batchNo, "bigPolicy", userId, "產生TXT失敗"+e, bpBno);
			return getReturnResult("大保單產生TXT失敗"+e);
		}
		
		List<File> fileList = new ArrayList<>();
		fileList.add(utf8File);
		fileList.add(big5File);
		
		List<String> uploadPathList = new ArrayList<>();
		uploadPathList.add("fbUploadPath2");//utf8
		uploadPathList.add("fbUploadPath1");//big5
		String uploadBno = bpBno +"U";
		for(int i=0;i<fileList.size();i++) {
			//檔案壓縮zip加密
			String zipFilepath = folderList.get(i) + filename+".ZIP";
			
			writeZip(zipFilepath, fileList.get(i), filePwd);
			FileUtils.forceDelete(fileList.get(i));
			
			File zipFile = new File(zipFilepath);
			if(i==1) {
				uploadBno = bpBno + "B";
			}
			try {
				FileUploadResponseVo fileUploadResponseVo = uploadFile(zipFile, uploadBno, userId, "F");
				if ("N".equals(fileUploadResponseVo.getStatus())) {
					updateFirAgtrnBatchFb(batchNo, "bigPolicy", userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), uploadBno);
					return getReturnResult("大保單上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
				}
			} catch (Exception e) {
				logger.error("genbigPolicy uploadFileToftp error", e);
				updateFirAgtrnBatchFb(batchNo, "bigPolicy", userId, "上傳檔案伺服器失敗"+e.toString().substring(0,200), uploadBno);
				return getReturnResult("大保單上傳檔案伺服器失敗" + e);
			}
			
			//上傳富邦sftp
			//檔案產生時只先上傳big5檔案至A0001102資料夾，待次月20號再使用另個上傳功能上傳至A0001178資料夾
			if(i == 1 && "fail".equals(uploadFileToSftp(zipFilepath, uploadPathList.get(i)))) {
				updateFirAgtrnBatchFb(batchNo, "bigPolicy", userId, "上傳SFTP失敗，請下載檔案後手動上傳", uploadBno);
				FileUtils.forceDelete(zipFile);
				return getReturnResult("大保單上傳SFTP失敗，請下載檔案後手動上傳");
			}
			FileUtils.deleteDirectory(folderPathList.get(i));
		}
		
		updateFirAgtrnBatchFb(batchNo, "bigPolicy", userId, "OK", bpBno);
		return getReturnResult("產生大保單成功。");
	}
	
	private String genBpContent(List<Aps034genFileVo> bigPolicyList) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Aps034genFileVo bigPolicyData : bigPolicyList) {
			String amount = bigPolicyData.getAmount();
			String ePolicyNo = "";
			String fPolicyNo = "";
			if(StringUtil.isSpace(amount) || "0".equals(amount)) {
				ePolicyNo = bigPolicyData.getPolicyno();
			}else {
				fPolicyNo = bigPolicyData.getPolicyno();
			}
			
			String premium = bigPolicyData.getPremium()==null?"0":bigPolicyData.getPremium();
			String premQ = bigPolicyData.getPremQ()==null?"0":bigPolicyData.getPremQ();
			//合計保費
			
			String amtTot = String.valueOf(Integer.parseInt(premium)+Integer.parseInt(premQ)); 
			
			sb.append(transLength(bigPolicyData.getCollateralnumber(), 16, "s"))
			.append(transLength(fPolicyNo, 16, "s"))
			.append(transLength(ePolicyNo, 16, "s"))
			.append(transLength(bigPolicyData.getOldpolicyno(), 16, "s"))
			.append(transLength(transChar(bigPolicyData.getPiiInsuredname()), 48, "S"))
			.append(transLength(bigPolicyData.getPiiId(), 10, "s"))
			.append(transLength(transChar(bigPolicyData.getPiaInsuredname()), 48, "S"))
			.append(transLength(bigPolicyData.getPiaId(), 10, "s"))
			.append(transLength(bigPolicyData.getPiaPostcode(), 3, "s"))
			.append(transLength(transChar(bigPolicyData.getPostaddress()), 120, "S"))
			.append(transLength(bigPolicyData.getStartdate() != null ? getDateFormat(bigPolicyData.getStartdate(), "yyyyMMdd"):"", 8, "s"))
			.append(transLength(bigPolicyData.getEnddate() != null ? getDateFormat(bigPolicyData.getEnddate(), "yyyyMMdd"):"", 8, "s"))
			.append(transLength(bigPolicyData.getAddresscode(), 3, "s"))
			.append(transLength(transChar(bigPolicyData.getAddressdetailinfo()), 120, "S"))
			.append(transLength("台北富邦商業銀行股份有限公司", 28, "S"))
			.append(transLength(wallMap.get(bigPolicyData.getWallmaterial()) + bigPolicyData.getCodecname(), 16, "S"))
			.append(transLength(bigPolicyData.getStrSumfloors(), 3, "n"))
			.append(transLength(transStructure(bigPolicyData.getStructure()), 6, "S"))
			.append(transLength(transBuildarea(bigPolicyData.getNumBuildarea()), 8, "n"))
			.append(transLength("住宅", 4, "S"))
			.append(transLength(bigPolicyData.getAmount(), 12, "n"))
			.append(transLength(bigPolicyData.getAmtQ(), 12, "n"))
			.append(transLength("", 12, "n"))
			.append(transLength(premium, 10, "n"))
			.append(transLength(bigPolicyData.getPremQ(), 10, "n"))
			.append(transLength("", 10, "n"))
			.append(transLength("", 10, "n"))
			.append(transLength(amtTot, 12, "n"))
			.append(transLength(bigPolicyData.getExtracomcode(), 10, "s"))
			.append(transLength("", 10, "s"))
			.append(transLength("0".equals(bigPolicyData.getRenewinsuranceflag())?"Y":"N", 1, "s"))
			.append("\r\n");
		}
		return sb.toString();
	}
	
	/*上傳UTF_8大保單至SFTP*/
	public Result uploadBigPolicyFile(String bno, String batchNo, String fileType, String userId) throws Exception {
		InputStream fileInputStream = null;
		String downloadFilename;
		FileVo fileVo = this.getFtsFileOid(bno);
		String url = configUtil.getString("downloadFileUrl") + bno + "/" + fileVo.getOid();
		logger.info("lnkDownloadPDF url : " + url);
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);

			if (MediaType.APPLICATION_JSON.equals(contentType.getMimeType())) {
				String entityStr = EntityUtils.toString(entity, "UTF-8");
				System.out.println(entityStr);
				return getReturnResult("大保單上傳SFTP失敗，請下載檔案後手動上傳");
			}

			fileInputStream = httpResponse.getEntity().getContent();
			File fileFolder = new File(configUtil.getString("tempFolder"));
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			downloadFilename = fileVo.getName();
			File file = new File(configUtil.getString("tempFolder") + downloadFilename);
			if (file.exists()) {
				FileUtils.forceDelete(file);
			}
			file.createNewFile();
			
			//上傳至SFTP
			if("fail".equals(uploadFileToSftp(file.getPath(), "fbUploadPath2"))) {
				updateFirAgtrnBatchFb(batchNo, fileType, userId, "上傳SFTP失敗，請下載檔案後手動上傳", bno);
				fileInputStream = new DeleteAfterDownloadFileInputStream(file);
				return getReturnResult("大保單上傳SFTP失敗，請下載檔案後手動上傳");
			}
			
			fileInputStream = new DeleteAfterDownloadFileInputStream(file);
			updateFirAgtrnBatchFb(batchNo, fileType, userId, "OK", bno);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SystemException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return getReturnResult("大保單上傳SFTP成功!");
	}
	
	//取得上傳FTS的檔案清單OID
	private FileVo getFtsFileOid(String businessNo) {
		String riskCode = "F";
		String httpURL = configUtil.getString("ftsUrl");
		
		FtsUtil ftsUtil = new FtsUtil(httpURL);
		FileListResponseVo fileListResponseVo = ftsUtil.getFtsFileList(riskCode, businessNo);
        ArrayList<FileVo> list = fileListResponseVo.getFileList();
        FileVo fileVo = new FileVo();
		if (list != null && !list.isEmpty()) {
			fileVo = list.get(0);
		}
		return fileVo;
	}
	
	//產生續保明細檔 EXCEL
	@Override
	public Result genRenewFile(String batchNo, String userId, String filePwd) throws Exception {
		
		List<Aps034genFileVo> renewFileList = findbPData(batchNo);
		if(null == renewFileList) {
			return getReturnResult("查無資料，無法產生續保明細表。");
		}
		
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String rnlistBno = batchNo + "_RNLIST";
		String filename = "FB_RNLIST.XLSX";
		String filepath = tempFolder + filename;
		File file = new File(filepath);
		FileOutputStream fileOut;
		
		//產險資料欄位標題
		String[] title = { "項次", "住火保單號碼", "地震基本保單號碼", "舊保單號碼", "被保險人姓名", "被保險人ID", "要保人姓名", "要保人 ID", "要保人郵遞區號",
				"要保人通訊地址", "保險起日", "保險迄日", "保險標的物郵遞區號", "保險標的物地址", "抵押權人", "建築結構", "屋頂別", "總樓層數", "建物等級", "坪數", "使用性質",
				"住火保額", "地震基本險保額", "超額地震險保額", "住火保費", "地震基本險保費", "超額地震險保費", "家綜險專案保費", "合計保費", "產險經辨代號", "產險出單序號",
				"自動續約註記" };

		try {
			// 建立workbook格式
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				// 建立總表頁籤
				XSSFSheet sheets;
				//取第一筆資料起始日作為標題年月
				Date startDate = renewFileList.get(0).getStartdate();
				XSSFSheet sheetT = createSheetAndTitle(workbook, title, "總表", startDate);
				int rownumT = 2;
				
				Map<String, Map<String,Object>> sheetsInfo = new HashMap<>();
				List<String> sheetsNames = new ArrayList<>();
				
				int amtFT = 0;
				int amtQT = 0;
				int amtTotT = 0;
				
				NumberFormat numFormat = NumberFormat.getNumberInstance();
				for (int i = 0; i < renewFileList.size(); i++) {
					Aps034genFileVo renewData = renewFileList.get(i);
					
					//取保額、保費金額加總
					String amtF = renewData.getAmount() == null?"0":renewData.getAmount();
					String amtQ = renewData.getAmtQ() == null?"0":renewData.getAmtQ();
					String premQ = renewData.getPremQ() == null?"0":renewData.getPremQ(); 
					String premium = renewData.getPremium() == null?"0":renewData.getPremium();
					String amtTot = String.valueOf(Integer.parseInt(premQ)+Integer.parseInt(premium));
					renewData.setAmtTot(amtTot);
					
					amtFT = amtFT + Integer.parseInt(amtF);
					amtQT = amtQT + Integer.parseInt(amtQ);
					amtTotT = amtTotT + Integer.parseInt(amtTot);
					
					// 總表一定會新增每筆資料
					rownumT = createRenewCell(renewData, sheetT, rownumT);
					
					// 每筆資料會有一個作業中心頁籤資料
					//新增一筆作業中心頁籤，並記錄名稱及頁籤index、總行數 資料起始行數應從第3行開始 index為2 
					String extracomcode = renewData.getExtracomcode();
					if(StringUtil.isSpace(extracomcode)) {
						extracomcode = "無作業中心";
					}
					
					if(!sheetsInfo.containsKey(extracomcode)) {
						Map<String, Object> pcMap = new HashMap<>();
						pcMap.put("rownum",2);
						sheets = createSheetAndTitle(workbook, title, extracomcode.substring(extracomcode.length()-3,extracomcode.length()), startDate);
						pcMap.put("sheets",sheets);
						pcMap.put("amtFPc", 0);
						pcMap.put("amtQPc", 0);
						pcMap.put("amtTotPc", 0);
						sheetsInfo.put(extracomcode, pcMap);
						sheetsNames.add(extracomcode);
					}
					//取對應作業中心的sheet、現有行數新增資料
					int pcRowsNum = createRenewCell(renewData, (XSSFSheet) sheetsInfo.get(extracomcode).get("sheets"), (int) sheetsInfo.get(extracomcode).get("rownum"));
					sheetsInfo.get(extracomcode).put("rownum",pcRowsNum);
					sheetsInfo.get(extracomcode).put("amtFPc",(int)sheetsInfo.get(extracomcode).get("amtFPc") + Integer.parseInt(amtF));
					sheetsInfo.get(extracomcode).put("amtQPc",(int)sheetsInfo.get(extracomcode).get("amtQPc") + Integer.parseInt(amtQ));
					sheetsInfo.get(extracomcode).put("amtTotPc",(int)sheetsInfo.get(extracomcode).get("amtTotPc") + Integer.parseInt(amtTot));
				}

				// 最後一筆放總數
				sheetT.addMergedRegion(new CellRangeAddress(rownumT, rownumT, 0, title.length - 1));
				XSSFRow rowTEnd = sheetT.createRow(rownumT);
				rowTEnd.createCell(0).setCellValue("共"+(rownumT - 2)+"筆，火險保額 "+numFormat.format(amtFT)+
						"，地震保額 "+ numFormat.format(amtQT) +"，超額地震保額 0，家綜險專案保費 0， 總保費 "+ numFormat.format(amtTotT));
				
				for (int pc = 0; pc< sheetsNames.size(); pc++) {
					int pcRownum = (int) sheetsInfo.get(sheetsNames.get(pc)).get("rownum");
					XSSFSheet pcSheet = (XSSFSheet) sheetsInfo.get(sheetsNames.get(pc)).get("sheets");
					pcSheet.addMergedRegion(new CellRangeAddress(pcRownum, pcRownum, 0, title.length - 1));
					XSSFRow rowPcEnd = pcSheet.createRow(pcRownum);
					rowPcEnd.createCell(0).setCellValue("共"+(pcRownum - 2)+"筆，火險保額 "+numFormat.format(sheetsInfo.get(sheetsNames.get(pc)).get("amtFPc"))+
							"，地震保額 "+ numFormat.format(sheetsInfo.get(sheetsNames.get(pc)).get("amtQPc")) 
							+"，超額地震保額 0，家綜險專案保費 0， 總保費 "+ numFormat.format(sheetsInfo.get(sheetsNames.get(pc)).get("amtTotPc")));
				}

				fileOut = new FileOutputStream(file);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				
				encrypt(filePwd, filepath);
			}

		} catch (Exception e) {
			logger.error("genRenewListFile error", e);
			updateFirAgtrnBatchFb(batchNo, "renewal", userId, "產生EXCEL失敗"+e, rnlistBno);
			return getReturnResult("續保明細檔產生EXCEL失敗。"+e);
		}
		
		try {
			FileUploadResponseVo fileUploadResponseVo = uploadFile(file, rnlistBno, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())){
				updateFirAgtrnBatchFb(batchNo, "renewal", userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), rnlistBno);
				return getReturnResult("續保明細檔上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
			}
		}catch (Exception e) {
			logger.error("genRenewListFile uploadFileToftp error", e);
			updateFirAgtrnBatchFb(batchNo, "renewal", userId, "上傳檔案伺服器失敗" + e, rnlistBno);
			return getReturnResult("續保明細檔上傳檔案伺服器失敗" + e);
		}
		
		FileUtils.forceDelete(file);
		updateFirAgtrnBatchFb(batchNo, "renewal", userId, "OK", rnlistBno);
		return getReturnResult("產生續保明細檔成功。");
	}
	
	//產生續期扣款通知書
	@Override
	public Result genDebitNotice(String batchNo, String userId) throws Exception {
		
		String paynoBno = batchNo + "_PAYNO";
		
		String tempFolder = configUtil.getString("tempFolder") + File.separator;
		File fileFolder = new File(tempFolder);
		if(!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		RptUtil rptUtil = new RptUtil(configUtil.getString("rptUrl"));
		boolean check = rptUtil.genPdf(tempFolder, batchNo, paynoBno, "fir00115");
		if(!check) {
			updateFirAgtrnBatchFb(batchNo, "payno", userId, "產生扣款通知PDF失敗",paynoBno);
			return getReturnResult("產生扣款通知書PDF失敗");
		}
		File pdfFile = new File(tempFolder + paynoBno + ".pdf");
		if(pdfFile.exists()) {
			//將檔案上傳FTS
			try {
				FileUploadResponseVo fileUploadResponseVo = uploadFile(pdfFile, paynoBno, userId, "F");
				if ("N".equals(fileUploadResponseVo.getStatus())){
					updateFirAgtrnBatchFb(batchNo, "renewal", userId, "上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage(), paynoBno);
					return getReturnResult("上傳檔案伺服器失敗" + fileUploadResponseVo.getMessage());
				}
			}catch (Exception e) {
				logger.error("genRenewListFile uploadFileToftp error", e);
				updateFirAgtrnBatchFb(batchNo, "renewal", userId, "上傳檔案伺服器失敗" + e, paynoBno);
				return getReturnResult("扣款通知書上傳檔案伺服器失敗" + e);
			}finally{
				FileUtils.forceDelete(pdfFile);
			}
		}
		
		updateFirAgtrnBatchFb(batchNo, "payno", userId, "OK", paynoBno);
		return getReturnResult("產生續期扣款通知書成功。");
		
	}
	
	private Integer createRenewCell(Aps034genFileVo renewData, XSSFSheet sheet, int rownum) throws Exception {
		String amount = renewData.getAmount();
		String ePolicyNo = "";
		String fPolicyNo = "";
		if(StringUtil.isSpace(amount) || "0".equals(amount)) {
			ePolicyNo = renewData.getPolicyno();
		}else {
			fPolicyNo = renewData.getPolicyno();
		}
		
		//續保明細表內容
		XSSFRow row = sheet.createRow(rownum); //建立列
		row.createCell(0).setCellValue(rownum - 1);
		row.createCell(1).setCellValue(fPolicyNo);
		row.createCell(2).setCellValue(ePolicyNo);
		row.createCell(3).setCellValue(renewData.getOldpolicyno());
		row.createCell(4).setCellValue(renewData.getPiiInsuredname());
		row.createCell(5).setCellValue(renewData.getPiiId());
		row.createCell(6).setCellValue(renewData.getPiaInsuredname());
		row.createCell(7).setCellValue(renewData.getPiaId());
		row.createCell(8).setCellValue(renewData.getPiaPostcode());
		row.createCell(9).setCellValue(transChar(renewData.getPostaddress()));
		row.createCell(10).setCellValue(renewData.getStartdate() != null ? getDateFormat(renewData.getStartdate(), "yyyy/MM/dd") : null);
		row.createCell(11).setCellValue(renewData.getEnddate() != null ? getDateFormat(renewData.getEnddate(), "yyyy/MM/dd") : null);
		row.createCell(12).setCellValue(renewData.getAddresscode());
		row.createCell(13).setCellValue(transChar(renewData.getAddressdetailinfo()));
		row.createCell(14).setCellValue("台北富邦商業銀行股份有限公司");
		row.createCell(15).setCellValue(wallMap.get(renewData.getWallmaterial()));
		row.createCell(16).setCellValue(renewData.getCodecname());
		row.createCell(17).setCellValue(renewData.getStrSumfloors());
		row.createCell(18).setCellValue(transStructure(renewData.getStructure()));
		row.createCell(19).setCellValue(renewData.getNumBuildarea() != null ? renewData.getNumBuildarea().toString() : null);
		row.createCell(20).setCellValue("住宅");
		row.createCell(21).setCellValue(renewData.getAmount());
		row.createCell(22).setCellValue(renewData.getAmtQ());
		row.createCell(23).setCellValue("");
		row.createCell(24).setCellValue(renewData.getPremium());
		row.createCell(25).setCellValue(renewData.getPremQ());
		row.createCell(26).setCellValue("");
		row.createCell(27).setCellValue("");
		row.createCell(28).setCellValue(renewData.getAmtTot());
		row.createCell(29).setCellValue(renewData.getExtracomcode());
		row.createCell(30).setCellValue("");
		row.createCell(31).setCellValue("0".equals(renewData.getRenewinsuranceflag())?"Y":"N");
		rownum++;
		return rownum;
	}
	
	//查詢大保單及續保明細表資料
	@SuppressWarnings("unchecked")
	private List<Aps034genFileVo> findbPData(String batchNo) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("codetype","Wallno_FB");
		Result result = rfrcodeService.findRfrcodeByParams(params);
		List<Rfrcode> wallList = (List<Rfrcode>) result.getResObject();
		for(Rfrcode rfrcode: wallList) {
			wallMap.put(rfrcode.getMappedcode(), rfrcode.getCodename());
		}
		
		params.clear();
		params.put("batchNo", batchNo);
		result = firAgtrnBatchDtlService.findForFbRenewalData(params);
		List<Aps034genFileVo> dataList = new ArrayList<>();
		if(result.getResObject()!=null) {
			dataList = (List<Aps034genFileVo>) result.getResObject();
		}
		
		List<Aps034genFileVo> fKindList = null;
		result = firAgtrnBatchDtlService.findForFbRenewalFkind(params);
		if(result.getResObject()!=null) {
			fKindList = (List<Aps034genFileVo>) result.getResObject();
		}
		Map<String,Integer> policyNoMap = new HashMap<>();
		for(int i=0; i<fKindList.size(); i++) {
			policyNoMap.put(fKindList.get(i).getPolicyno(), i);
		}
		
		int index = 0;
		for(int i=0; i<dataList.size(); i++) {
			Aps034genFileVo genFileVo = dataList.get(i);
			if(policyNoMap.containsKey(genFileVo.getPolicyno())) {
				index = policyNoMap.get(genFileVo.getPolicyno());
				dataList.get(i).setAmount(fKindList.get(index).getAmount());
				dataList.get(i).setPremium(fKindList.get(index).getPremium());
			}
		}
		
		return dataList;
	}
	
	//更新批次主檔
	private void updateFirAgtrnBatchFb(String batchNo, String filetype, String userId, String memo, String bno) throws Exception {
		FirAgtrnBatchFb firAgtrnBatchFb = new FirAgtrnBatchFb();
		firAgtrnBatchFb.setBatchNo(batchNo);
		Date sysDate = new Date();
		firAgtrnBatchFb.setIupdate(userId);
		firAgtrnBatchFb.setDupdate(sysDate);
		if("diff".equals(filetype)) {
			firAgtrnBatchFb.setDiffUser(userId);
			firAgtrnBatchFb.setDiffTime(sysDate);
			firAgtrnBatchFb.setDiffMemo(memo);
			firAgtrnBatchFb.setDiffBno(bno);
		}
		if("reject".equals(filetype)) {
			firAgtrnBatchFb.setSfUser(userId);
			firAgtrnBatchFb.setSfTime(sysDate);
			firAgtrnBatchFb.setSfMemo(memo);
			firAgtrnBatchFb.setSfBno(bno);
		}
		if("bigPolicy".equals(filetype)) {
			firAgtrnBatchFb.setBpUser(userId);
			firAgtrnBatchFb.setBpTime(sysDate);
			firAgtrnBatchFb.setBpMemo(memo);
			firAgtrnBatchFb.setBpBno(bno);
		}
		if("renewal".equals(filetype)) {
			firAgtrnBatchFb.setRnlistUser(userId);
			firAgtrnBatchFb.setRnlistTime(sysDate);
			firAgtrnBatchFb.setRnlistMemo(memo);
			firAgtrnBatchFb.setRnlistBno(bno);
		}
		if("payno".equals(filetype)) {
			firAgtrnBatchFb.setPaynoUser(userId);
			firAgtrnBatchFb.setPaynoTime(sysDate);
			firAgtrnBatchFb.setPaynoMemo(memo);
			firAgtrnBatchFb.setPaynoBno(bno);
		}
		if("bpUpload".equals(filetype)) {
			firAgtrnBatchFb.setBpUploadUser(userId);
			firAgtrnBatchFb.setBpUploadTime(sysDate);
			firAgtrnBatchFb.setBpUploadMemo(memo);
		}
		firAgtrnBatchFbService.updateFirAgtrnBatchFb(firAgtrnBatchFb);
	}
	
	private XSSFSheet createSheetAndTitle(XSSFWorkbook workbook, String[] title, String sheetName, Date startDate) {
		XSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.length - 1));// 起始行號，終止行號， 起始列號，終止列號
		XSSFRow row0 = sheet.createRow(0);
		
		//標題年分取第一筆資料起日轉換為民國年月
		row0.createCell(0).setCellValue(getRocDate(startDate, "年", "月") + "續保明細表");
		// 建立excel欄位
		XSSFRow rowTitle = sheet.createRow(1);
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
	
	//將檔案上傳至富邦Sftp
	private String uploadFileToSftp(String filepath, String uploadPath){
		String sftpHost = configUtil.getString("fbSFTP");
		String sftpUser = configUtil.getString("fbSftpUserGet");
		String sftpPwd = configUtil.getString("fbSfptPwdGet");
		int sftpPort = Integer.parseInt(configUtil.getString("fbSftpPort"));
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		
		String remoteDir = configUtil.getString(uploadPath);
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
		//數字長度不足左邊補0
		if(type.equals("n")) {
			while(byteLength<length) {
				byteLength++;
				sb.append("0");
			}
			sb.append(str);
		}
		
		//英數字欄位長度不足右邊補半形空白
		if(type.equals("s")) {
			sb.append(str);
			while(byteLength<length) {
				byteLength++;
				sb.append(" ");
			}
		}
		
		//中文欄位長度不足右邊補全形空白
		if(type.equals("S")) {
			sb.append(str);
			int strLen = str.length();
			strLen = strLen*2;
			while(strLen<length) {
				strLen = strLen+2;
				sb.append("\u3000");
			}
		}
		return sb.toString();
	}
	
	private String transBuildarea(BigDecimal area) {
		String transArea = "";
		if(area != null) {
			DecimalFormat dFmt = new DecimalFormat();
			dFmt.setMinimumFractionDigits(2);
			dFmt.setMaximumFractionDigits(2);
			transArea = dFmt.format(area);
		}
		return transArea;
	}
	
	private String transStructure(String strutcure) {
		String structureText = "";
		if("1".equals(strutcure)) {
			structureText = "特一等";
		}
		if("2".equals(strutcure)) {
			structureText = "特二等";
		}
		if("3".equals(strutcure)) {
			structureText = "頭等";
		}
		if("5".equals(strutcure)) {
			structureText = "二等";
		}
		if("6".equals(strutcure)) {
			structureText = "三等";
		}
		
		return structureText;
	}

	private String getRocDate(Date date, String symbolY, String symbolM) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String adDate = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
		
		return (Integer.parseInt(adDate.substring(0,4))-1911) + symbolY + adDate.substring(4) + symbolM;
	}
	
	//半形轉全形
	private String transChar(String str) {
		if(null != str) {
			for (char c : str.toCharArray()) {
				str = str.replace(" ", "\u3000");
				if ((int) c >= 33 && (int) c <= 126) {
					str = str.replace(c, (char) (((int) c) + 65248));
				}
			}
		}
		return str;
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
	
	public FirAgtrnTmpFbService getFirAgtrnTmpFbService() {
		return firAgtrnTmpFbService;
	}

	public void setFirAgtrnTmpFbService(FirAgtrnTmpFbService firAgtrnTmpFbService) {
		this.firAgtrnTmpFbService = firAgtrnTmpFbService;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirAgtrnBatchFbService getFirAgtrnBatchFbService() {
		return firAgtrnBatchFbService;
	}

	public void setFirAgtrnBatchFbService(FirAgtrnBatchFbService firAgtrnBatchFbService) {
		this.firAgtrnBatchFbService = firAgtrnBatchFbService;
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
}
