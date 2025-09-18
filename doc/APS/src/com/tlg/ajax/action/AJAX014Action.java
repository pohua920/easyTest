package com.tlg.ajax.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Aps024ExportVo;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAddrCkdataService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Result;

//mantis：FIR0521，處理人員：DP0713，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能
import com.tlg.util.StringUtil;

/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 */
@SuppressWarnings("serial")
public class AJAX014Action extends BaseAction implements Serializable {
	
	private static final Logger logger = Logger.getLogger(AJAX014Action.class);
	private ConfigUtil configUtil;
	private FirAddrCkdataService firAddrCkdataService;
	private FirBatchLogService firBatchLogService;
	private FirBatchInfoService firBatchInfoService;
	
	private static final Integer DATACOUNT = 1000;
	private SimpleDateFormat excelDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exportFirAddrCkdata() {
		String remark = "";
		try {
			remark = genExcel(getParams());
		} catch (Exception e) {
			remark = "F";
			e.printStackTrace();
		}
		try {
			Map params = new HashMap();
			params.put("prgId", "FIR_ADDR_EXPORT");
			params.put("batchNo", getRequest().getParameter("batchNo"));
			Result result = firBatchLogService.findFirBatchLogByParams(params);
			if (result.getResObject() != null) {
				List<FirBatchLog> list = (List<FirBatchLog>) result.getResObject();
				FirBatchLog firBatchLog = list.get(0);
				firBatchLog.setStatus("S");
				firBatchLog.setRemark(remark);
				firBatchLog.setIupdate("SYS");
				firBatchLog.setDupdate(new Date());
				firBatchLogService.updateFirBatchLog(firBatchLog);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String genExcel(Map params) throws Exception {
		String tempFolder = configUtil.getString("aps024ExportPath");
		File filePath = new File(tempFolder);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		
		//mantis：FIR0521，處理人員：DP0713，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 START
		String startDate = (String) params.get("sDate");
		if(!StringUtil.isSpace(startDate)) {
			String s_put ="";
			if(startDate.length()==8){
				s_put = startDate.substring(0, 4)+"/"+startDate.substring(4, 6)+"/"+startDate.substring(6);
			}else{
				s_put = startDate;
			}
			s_put += " 00:00:00";
			params.put("startCreateDate", s_put);
		}
		
		String endDate = (String) params.get("eDate");
		if(!StringUtil.isSpace(endDate)) {
			String e_put ="";
			if(endDate.length()==8){
				e_put = endDate.substring(0, 4)+"/"+endDate.substring(4, 6)+"/"+endDate.substring(6);
			}else{
				e_put = endDate;
			}
			e_put += " 00:00:00";
			params.put("endCreateDate", e_put);
		}
		//mantis：FIR0521，處理人員：DP0713，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 END
		
		int count = firAddrCkdataService.findAps024ExportDataCount(params);
		if(count <=0) {
			return "N";
		}
		
		String password = "27938888";
		params.put("prgId", "FIR_ADDR_EXPORT");
		Result res = firBatchInfoService.findFirBatchInfoByUK(params);
		if(res.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) res.getResObject();
			password = firBatchInfo.getMailTo();
		}
		
		int cycleTimes = count / DATACOUNT;
		if(count % DATACOUNT > 0) {
			cycleTimes = cycleTimes + 1;
		}
		int startRow = 1;
		int endRow = DATACOUNT;
		
		List<Aps024ExportVo> excelList = new ArrayList<>();
		String filename = getRequest().getParameter("batchNo")+".xlsx";
		File file = new File(tempFolder +filename);
		
		SXSSFSheet sheet0;
		try(SXSSFWorkbook workbook = new SXSSFWorkbook()) {
			//建立總表
			sheet0 = workbook.createSheet("總表");
			//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
			String[] titleArr = { "保單號碼", "批單號碼", "簽單日期", "生效日", "到期日", "保單是否有效", "地址序號", "郵遞區號", "原始地址", "轉換後地址",
					"建物等級", "總樓層數", "生效註記", "建檔人員", "建檔日期", "最後異動人員", "最後異動日期", "備註", "正規化_狀態碼", "正規化_訊息", "正規化_結果",
					"正規化_地址","正規化_郵遞區號","正規化_縣市","正規化_鄉鎮區","正規化_村里鄰","正規化_街路段","正規化_巷","正規化_弄","正規化_衖",
					"正規化_號","正規化_樓","正規化_其他","外牆","屋頂","建築年度"};
			SXSSFRow rowTitle = sheet0.createRow(0);
			//建立excel欄位
			for (int i = 0; i < titleArr.length; i++) {
				rowTitle.createCell(i).setCellValue(titleArr[i]);
			}
			
			int totalRowNum = 0;
			for (int c = 0; c < cycleTimes; c++) {
				sheet0 = workbook.getSheetAt(0);
				params.put("startRow", startRow);
				params.put("endRow", endRow);
				Result result = firAddrCkdataService.findAps024ExportData(params); // 一次只查詢一千筆
				if (result.getResObject() != null) {
					excelList = (List<Aps024ExportVo>) result.getResObject();
					for (int i = 0; i < excelList.size(); i++) {
						totalRowNum++;
						SXSSFRow row = sheet0.createRow(totalRowNum); // 建立列 必須為每次迴圈筆數往上加
						row.createCell(0).setCellValue(StringUtils.isBlank(excelList.get(i).getPolicyno())?"":excelList.get(i).getPolicyno() );
						row.createCell(1).setCellValue(StringUtils.isBlank(excelList.get(i).getEndorseno())?"":excelList.get(i).getEndorseno());
						row.createCell(2).setCellValue(excelList.get(i).getUnderwriteenddate()==null?"":excelDateFormat.format(excelList.get(i).getUnderwriteenddate()));
						row.createCell(3).setCellValue(excelList.get(i).getStartdate()==null?"":excelDateFormat.format(excelList.get(i).getStartdate()));
						row.createCell(4).setCellValue(excelList.get(i).getEnddate()==null?"":excelDateFormat.format(excelList.get(i).getEnddate()));
						if(excelList.get(i).getP1().equals("Y") || excelList.get(i).getP2().equals("Y") 
								|| excelList.get(i).getP3().equals("Y")) {
							
							row.createCell(5).setCellValue("N");
						}else {
							row.createCell(5).setCellValue("Y");
						}
						row.createCell(6).setCellValue(StringUtils.isBlank(excelList.get(i).getAddrNo())?"":excelList.get(i).getAddrNo());
						row.createCell(7).setCellValue(StringUtils.isBlank(excelList.get(i).getAddrCode())?"":excelList.get(i).getAddrCode());
						row.createCell(8).setCellValue(StringUtils.isBlank(excelList.get(i).getOriAddress())?"":excelList.get(i).getOriAddress());
						row.createCell(9).setCellValue(StringUtils.isBlank(excelList.get(i).getStdAddress())?"":excelList.get(i).getStdAddress());
						row.createCell(10).setCellValue(StringUtils.isBlank(excelList.get(i).getAddrStructure())?"":excelList.get(i).getAddrStructure());
						row.createCell(11).setCellValue(StringUtils.isBlank(excelList.get(i).getAddrSumfloors())?"":excelList.get(i).getAddrSumfloors());
						row.createCell(12).setCellValue(StringUtils.isBlank(excelList.get(i).getValidFlag())?"":excelList.get(i).getValidFlag());
						row.createCell(13).setCellValue(StringUtils.isBlank(excelList.get(i).getIcreate())?"":excelList.get(i).getIcreate());
						row.createCell(14).setCellValue(excelList.get(i).getDcreate()==null?"":excelDateFormat.format(excelList.get(i).getDcreate()));
						row.createCell(15).setCellValue(StringUtils.isBlank(excelList.get(i).getAddrNo())?"":excelList.get(i).getIupdate());
						row.createCell(16).setCellValue(excelList.get(i).getDupdate()==null?"":excelDateFormat.format(excelList.get(i).getDupdate()));
						row.createCell(17).setCellValue(StringUtils.isBlank(excelList.get(i).getRemark())?"":excelList.get(i).getRemark());
						row.createCell(18).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedCode())?"":excelList.get(i).getFormattedCode());
						row.createCell(19).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedMsg())?"":excelList.get(i).getFormattedMsg());
						row.createCell(20).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedResult())?"":excelList.get(i).getFormattedResult());
						row.createCell(21).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedAddr())?"":excelList.get(i).getFormattedAddr());
						row.createCell(22).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedZip())?"":excelList.get(i).getFormattedZip());
						row.createCell(23).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedCity())?"":excelList.get(i).getFormattedCity());
						row.createCell(24).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedDistrict())?"":excelList.get(i).getFormattedDistrict());
						row.createCell(25).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedNeighborhood())?"":excelList.get(i).getFormattedNeighborhood());
						row.createCell(26).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedRoad())?"":excelList.get(i).getFormattedRoad());
						row.createCell(27).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedLane())?"":excelList.get(i).getFormattedLane());
						row.createCell(28).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedAlley1())?"":excelList.get(i).getFormattedAlley1());
						row.createCell(29).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedAlley2())?"":excelList.get(i).getFormattedAlley2());
						row.createCell(30).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedNo())?"":excelList.get(i).getFormattedNo());
						row.createCell(31).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedFloor())?"":excelList.get(i).getFormattedFloor());
						row.createCell(32).setCellValue(StringUtils.isBlank(excelList.get(i).getFormattedOther())?"":excelList.get(i).getFormattedOther());
						/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
						row.createCell(33).setCellValue(StringUtils.isBlank(excelList.get(i).getWallmaterial())?"":excelList.get(i).getWallmaterial());
						row.createCell(34).setCellValue(StringUtils.isBlank(excelList.get(i).getRoofmaterial())?"":excelList.get(i).getRoofmaterial());
						row.createCell(35).setCellValue(StringUtils.isBlank(excelList.get(i).getBuildyears())?"":excelList.get(i).getBuildyears());
						/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/
					}
				}
				startRow += DATACOUNT;
				endRow += DATACOUNT;
			}
			try(POIFSFileSystem fileSystem = new POIFSFileSystem();) {
				 EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
		         Encryptor enc = info.getEncryptor();
		         enc.confirmPassword(password);
		         OutputStream encryptedDS = enc.getDataStream(fileSystem);
		         workbook.write(encryptedDS);
		         
		         File[] listFiles = filePath.listFiles();
		         for (File file2 : listFiles) {
		 			 file2.delete();
		 		 }
		         
		         FileOutputStream fos = new FileOutputStream(file);
		         fileSystem.writeFilesystem(fos);
		         workbook.dispose();
		         fos.flush();
		         fos.close(); 
	        } catch (Exception e) {
	        	throw new Exception(e);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		return "S";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getParams() throws Exception {
		Map params = new HashMap();
		if(StringUtils.isNotBlank(getRequest().getParameter("policyno"))) {
			params.put("policyno", getRequest().getParameter("policyno"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("sDate"))) {
			params.put("sDate", getRequest().getParameter("sDate"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("eDate"))) {
			params.put("eDate", getRequest().getParameter("eDate"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("oriAddressLike"))) {
			params.put("oriAddressLike", getRequest().getParameter("oriAddressLike"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("validFlag"))) {
			params.put("validFlag", getRequest().getParameter("validFlag"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("formattedResult"))) {
			params.put("formattedResult", getRequest().getParameter("formattedResult"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("exportFlag")) 
				&& "1".equals(getRequest().getParameter("exportFlag"))) {
			params.put("exportFlag", "1");
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("formattedCity"))) {
			params.put("formattedCity", getRequest().getParameter("formattedCity"));
		}
		if(StringUtils.isNotBlank(getRequest().getParameter("formattedDistrict"))) {
			params.put("formattedDistrict", getRequest().getParameter("formattedDistrict"));
		}
		return params;
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirAddrCkdataService getFirAddrCkdataService() {
		return firAddrCkdataService;
	}

	public void setFirAddrCkdataService(FirAddrCkdataService firAddrCkdataService) {
		this.firAddrCkdataService = firAddrCkdataService;
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}
	
	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}
}