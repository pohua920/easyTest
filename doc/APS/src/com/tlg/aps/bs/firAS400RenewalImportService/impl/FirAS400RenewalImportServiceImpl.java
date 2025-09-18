package com.tlg.aps.bs.firAS400RenewalImportService.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firAS400RenewalImportService.AS400RenewalDataService;
import com.tlg.aps.bs.firAS400RenewalImportService.FirAS400RenewalImportService;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.prpins.service.FirAgtrnAs400DataErrService;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.prpins.service.FirAgtrnAs400DataUplistService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class FirAS400RenewalImportServiceImpl implements FirAS400RenewalImportService {
	private static final Logger logger = Logger.getLogger(FirAS400RenewalImportServiceImpl.class);
	
	private FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService;
	private FirAgtrnAs400DataService firAgtrnAs400DataService;
	private FirAgtrnAs400DataErrService firAgtrnAs400DataErrService;
	private AS400RenewalDataService as400RenewalDataService;
	private static PrpdNewCodeService prpdNewCodeService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Result RenewalDataUploadAndImport(String userId, File uploadFile, String filename, String businessnature, String rnYyyymm) throws Exception {
		int tmpOk = 0;
		int tmpNg = 0;
		FileInputStream fis = new FileInputStream(uploadFile);
		try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {
			String remark = "";
			Sheet sheet = workbook.getSheetAt(0);//只取第一個工作表
			int rownum = sheet.getPhysicalNumberOfRows();//取所有行數
			if(rownum <= 1) {//excel裡無資料
				remark = "XLSX檔無資料";
				if(fis!=null) {
					fis.close();
				}
			}
			
			//判斷excel是否有整行為空的資料
			List<Integer> blankRow = new ArrayList<>();
			for(int i=0;i<rownum;i++) {
				int cellNum = sheet.getRow(i).getPhysicalNumberOfCells();
				Row row1 = sheet.getRow(i);
				int blankNum = 0;
				for(int j=0;j<cellNum;j++) {
					if("".equals(getCellFormatValue(row1.getCell(j)))) {
						blankNum ++;
					}
				}
				if(blankNum == cellNum) {
					blankRow.add(i);
				}
			}
			
			Map<String,String> params = new HashMap<>();
			params.put("businessnature", businessnature);
			params.put("rnYyyymm", rnYyyymm);
			
			int tmpAll = rownum-1-blankRow.size();
			
			Result result = firAgtrnAs400DataUplistService.findFirAgtrnAs400DataUplistByParams(params);
			//有資料更新，無資料新增
			if(result.getResObject() != null) {
				List<FirAgtrnAs400DataUplist> resultList = (List<FirAgtrnAs400DataUplist>) result.getResObject();
				FirAgtrnAs400DataUplist firAgtrnAs400DataUplist = resultList.get(0);
				firAgtrnAs400DataUplist.setFilename(filename);
				firAgtrnAs400DataUplist.setQtyAll(tmpAll);
				firAgtrnAs400DataUplist.setQtyOk(0);
				firAgtrnAs400DataUplist.setQtyNg(0);
				firAgtrnAs400DataUplist.setRemark(remark);
				firAgtrnAs400DataUplist.setIupdate(userId);
				firAgtrnAs400DataUplist.setDupdate(new Date());
				as400RenewalDataService.updateFirAgtrnAs400DataUplist(firAgtrnAs400DataUplist);
			}else {
				FirAgtrnAs400DataUplist firAgtrnAs400DataUplist = new FirAgtrnAs400DataUplist();
				firAgtrnAs400DataUplist.setBusinessnature(businessnature);
				firAgtrnAs400DataUplist.setFilename(filename);
				firAgtrnAs400DataUplist.setRnYyyymm(rnYyyymm);
				firAgtrnAs400DataUplist.setQtyAll(rownum-1);
				firAgtrnAs400DataUplist.setRemark(remark);
				firAgtrnAs400DataUplist.setIcreate(userId);
				firAgtrnAs400DataUplist.setDcreate(new Date());
				firAgtrnAs400DataUplist.setIupdate(userId);
				firAgtrnAs400DataUplist.setDupdate(new Date());
				as400RenewalDataService.insertFirAgtrnAs400DataUplist(firAgtrnAs400DataUplist);
			}
			
			result = firAgtrnAs400DataService.findFirAgtrnAs400DataByParams(params);
			if(result.getResObject()!=null) {
				List<FirAgtrnAs400Data> resultList = (List<FirAgtrnAs400Data>) result.getResObject();
				for(FirAgtrnAs400Data firAgtrnAs400Data : resultList) {
					this.removeFirAgtrnAs400Data(firAgtrnAs400Data.getOid());
				}
			}
			
			result = firAgtrnAs400DataErrService.findFirAgtrnAs400DataErrByParams(params);
			if(result.getResObject()!=null) {
				List<FirAgtrnAs400DataErr> resultList = (List<FirAgtrnAs400DataErr>) result.getResObject();
				for(FirAgtrnAs400DataErr firAgtrnAs400DataErr : resultList) {
					this.removeFirAgtrnAs400DataErr(firAgtrnAs400DataErr.getOid());
				}
			}
			
			
			if(tmpAll == 0) {
				updateFirAgtrnAs400DataUplist(businessnature, rnYyyymm, "檔案無資料", tmpNg, tmpOk);
				return this.getReturnResult("匯入完成，但檔案無資料。");
			}
			
			for(int i=1; i<rownum; i++){
				if(!blankRow.contains(i)) {//不是空行才往繼續以下動作
					Row row = sheet.getRow(i);//獲取到每一行，但不包括第一行
					FirAgtrnAs400Data firAgtrnAs400Data= new FirAgtrnAs400Data();
					StringBuilder errMsg = new StringBuilder();
					/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  Start */
					// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 (邏輯調整，整段覆蓋) -- start
					if (businessnature.equals("I99004")){
						// I99004臺銀 處理錯誤訊息用
						for(ExcelHeader2 header: ExcelHeader2.values()) {
							errMsg.append(header.checkCell(row, firAgtrnAs400Data));
						}
					} else if (businessnature.equals("I00006")){
						// I00006元大 處理錯誤訊息用
						for(ExcelHeader3 header: ExcelHeader3.values()) {
							errMsg.append(header.checkCell(row, firAgtrnAs400Data));
						}
					} else {
						// I99065板信、I99060日盛 處理錯誤訊息用
						for(ExcelHeader header: ExcelHeader.values()) {
							errMsg.append(header.checkCell(row, firAgtrnAs400Data));
						}
					}
					// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 (邏輯調整，整段覆蓋) -- end
					/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  End */
					if(errMsg.length()==0){//若檢核無錯誤訊息，則寫進住火保經代續保轉核心AS400補充資料
						firAgtrnAs400Data.setBusinessnature(businessnature);
						firAgtrnAs400Data.setRnYyyymm(rnYyyymm);
						firAgtrnAs400Data.setIcreate(userId);
						firAgtrnAs400Data.setDcreate(new Date());
						this.insertFirAgtrnAs400Data(firAgtrnAs400Data);	
						tmpOk++;
					}else {//寫進異常記錄表
						FirAgtrnAs400DataErr firAgtrnAs400DataErr = new FirAgtrnAs400DataErr();
						firAgtrnAs400DataErr.setBusinessnature(businessnature);
						firAgtrnAs400DataErr.setRnYyyymm(rnYyyymm);
						firAgtrnAs400DataErr.setExcelRow(i+1);
						firAgtrnAs400DataErr.setErrmsg(errMsg.toString());
						firAgtrnAs400DataErr.setIcreate(userId);
						firAgtrnAs400DataErr.setDcreate(new Date());
						this.insertFirAgtrnAs400DataErr(firAgtrnAs400DataErr);
						tmpNg++;
					}
				}
			}
			//執行完成
			updateFirAgtrnAs400DataUplist(businessnature, rnYyyymm, "", tmpNg, tmpOk);
			return this.getReturnResult("處理完成，請重新查詢確認執行結果。");	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			this.updateFirAgtrnAs400DataUplist(businessnature, rnYyyymm, e.toString(), tmpNg, tmpOk);
			return this.getReturnResult("上傳失敗。");
		}
	}
	
	private enum ExcelHeader{
		OLDPOLICYNO(0,""){//續保保單號 欄位A、B
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValueA = getCellFormatValue(row.getCell(index));
				String cellValueB = getCellFormatValue(row.getCell(index+1));
					String oldpolicyno = trimBlank(cellValueA+cellValueB);
					if(!checkString(oldpolicyno)){
						return "續保保單號空白;";
					}
					firAgtrnAs400Data.setOldpolicyno(oldpolicyno);
				return "";
			}
		},AMOUNT_F(6,"") {//總保險金額 欄位G
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountF(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},AMOUNT_Q(9,"") {//地震險金額 欄位J
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountQ(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},ADDRESS_I(10,"") {//住址/通訊處 1 欄位K
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setAddressI(trimBlank(cellValue));
				return msg;
			}
		},POSTCODE_I(11,"") {//郵遞區號 欄位L
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setPostcodeI(trimBlank(cellValue));
				return msg;
			}
		},PREMIUM_F(13,"") {//火險保費 欄位N
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumF(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},PREMIUM_Q(14,"") {//地震險保費 欄位O
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumQ(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},POSTCODE_A(21,"") {//郵遞區號 欄位V
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setPostcodeA(trimBlank(cellValue));
				return msg;
			}
		},ADDRESS_A(22,"") {//通訊地址1 欄位W
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setAddressA(trimBlank(cellValue));
				return msg;
			}
		},STARTDATE(24,"保險起期異常或空白;") {//生效日 欄位Y、Z、AA
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String year = trimBlank(getCellFormatValue(row.getCell(index)));
				String month = trimBlank(getCellFormatValue(row.getCell(index+1)));
				String date = trimBlank(getCellFormatValue(row.getCell(index+2)));
				try {
					if(StringUtil.isSpace(year)||StringUtil.isSpace(month)||StringUtil.isSpace(date)) {
						return msg;
					}
					if(month.length()==1) {
						month = "0"+month;
					}
					if(date.length()==1) {
						date = "0"+date;
					}
					Date startdate = transformDate(trimBlank(year+month+date));
					if(startdate!=null) {
						firAgtrnAs400Data.setStartdate(startdate);
						return "";
					}
					
				} catch (Exception e) {
					return msg;
				}
			return msg;
			}
		},BUILDAREA(34,"") {//坪數 欄位AI
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setBuildarea(trimBlank(cellValue));
				return msg;
			}
		},BUILDYEARS(35,"") {//建築年分 欄位AJ
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setBuildyears(trimBlank(cellValue));
				return msg;
			}
		},STRUCTURE_TEXT(41,"") {//建築構造說明 欄位AP
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				String oldpolicyno = getCellFormatValue(row.getCell(1));
				String wallno = null;
				String roofno = null;
				try {
					if(checkString(cellValue)){
						firAgtrnAs400Data.setStructureText(cellValue);
						//字軌為R0H表示為核心出單案件，非外銀續保件，欄位資料為四碼數字
						if(checkString(oldpolicyno) && oldpolicyno.contains("R0H") && Pattern.compile("^[0-9]{4}$").matcher(cellValue).matches()) {
							wallno = cellValue.trim().substring(0,2);
							roofno = cellValue.trim().substring(2,4);
						}else {
							String[] structureAr = cellValue.split("\\s+");
							if(structureAr.length >= 3) {
								wallno = getWallno(structureAr[0]);//外牆代號轉換
								roofno = getRoofno(structureAr[1]);
								if(structureAr[2].contains("層")) {
									firAgtrnAs400Data.setSumfloors(Integer.parseInt((replaceLeftZero(structureAr[2].substring(0, structureAr[2].lastIndexOf("層"))))));//樓層數
								}
							}
						}
						firAgtrnAs400Data.setWallmaterial(wallno);
						firAgtrnAs400Data.setRoofmaterial(roofno);
					}
				}catch(Exception e) {
					firAgtrnAs400Data.setSumfloors(0);
				}
				return msg;
			}
		//增加轉入日盛資料時需額外使用到的欄位 start
		},ADDRESSCODE(32,"") {//郵遞區號1 欄位AG
			@SuppressWarnings("unchecked")
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String addresscode = trimBlank(getCellFormatValue(row.getCell(index)));
				firAgtrnAs400Data.setAddresscode(addresscode);
				
				if(!"".equals(addresscode) && addresscode.length()>=3) {
					addresscode = addresscode.substring(0,3);
					firAgtrnAs400Data.setAddresscode(addresscode);
					
					try {
						Map<String,String> params = new HashMap<>();
						params.put("codetype", "PostAddress");
						params.put("codecode", addresscode);
						Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
						if(result.getResObject() != null) {
							List<PrpdNewCode> resultList =  (List<PrpdNewCode>) result.getResObject();
							firAgtrnAs400Data.setAddressname(resultList.get(0).getCodecname());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return msg;
			}
		},ADDRESSDETAIL(31,""){//標的物地址1 欄位AF
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setAddressdetail(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},STRUCTURE_1(57,""){//建築等級代號 欄位BF
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setStructure1(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},ID_A(20,""){//要保人ID 欄位U
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIdA(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},NAME_A(19,""){//要保人姓名 欄位T
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameA(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},PHONE_A(136,""){//要保人連絡電話 欄位EG
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String phone = trimBlank(getCellFormatValue(row.getCell(index)));
				if(phone.length() == 10 && "09".equals(phone.substring(0,2))) {
					firAgtrnAs400Data.setMobileA(phone);
				}else {
					firAgtrnAs400Data.setPhoneA(phone);
				}
				return msg;
			}
		},ID_I(8,""){//被保人身分證號 欄位I
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIdI(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},NAME_I(5,""){//被保險人姓名 欄位F
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameI(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},PHONE_I(135,""){// 聯絡電話 欄位EF
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String phone = trimBlank(getCellFormatValue(row.getCell(index)));
				if(phone.length() == 10 && "09".equals(phone.substring(0,2))) {
					firAgtrnAs400Data.setMobileI(phone);
				}else {
					firAgtrnAs400Data.setPhoneI(phone);
				}
				return msg;
			}
		},ENDDATE(27,"保險迄期異常或空白"){//到期年月日 欄位AB、AC、AD
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String year = trimBlank(getCellFormatValue(row.getCell(index)));
				String month = trimBlank(getCellFormatValue(row.getCell(index+1)));
				String date = trimBlank(getCellFormatValue(row.getCell(index+2)));
				try {
					if(StringUtil.isSpace(year)||StringUtil.isSpace(month)||StringUtil.isSpace(date)) {
						return msg;
					}
					if(month.length()==1) {
						month = "0"+month;
					}
					if(date.length()==1) {
						date = "0"+date;
					}
					Date enddate = transformDate(trimBlank(year+month+date));
					if(enddate!=null) {
						firAgtrnAs400Data.setEnddate(enddate);
						return "";
					}
				} catch (Exception e) {
					return msg;
				}
			return msg;
			}
		};
		//增加轉入日盛資料時需額外使用到的欄位 end
		int index;
		String msg;
		ExcelHeader (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data);
		
		private static boolean checkNum(String cellValue) {
			if(!checkString(cellValue)) {
				return false;
			}
			String regex ="^[0-9]*$";
			Pattern pattern = Pattern.compile(regex);
			return pattern.matcher(cellValue).matches();
		}
		
		private static boolean checkString(String cellValue){
			try {
				return !StringUtil.isSpace(cellValue);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		private static String trimBlank(String cellValue){
			if(null != cellValue && cellValue.trim().length()>0) {
				return cellValue.replaceAll("[\\u3000|\\s]*", "");
			}
			return "";
		}
		
		//去除左邊的0
		private static String replaceLeftZero(String str) throws Exception{
			if(StringUtil.isSpace(str)) {
				return "";
			}
			while(str.substring(0,1).equals("0")) {
				if(str.equals("0")) break;
				str = str.substring(1, str.length());
			}
			return str;
		}
	}
	
	/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  Start */
	private enum ExcelHeader2{
		OLDPOLICYNO(5,""){//續保保單號 欄位 F
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValue = getCellFormatValue(row.getCell(index));
				if(!checkString(cellValue)){
					return "續保保單號空白;";
				}
				firAgtrnAs400Data.setOldpolicyno(trimBlank(cellValue));
				return msg;
			}
		},STARTDATE(8,"保險起期異常或空白;"){//起期日 欄位 I
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)){
					return msg;
				}					
				try {
					Date startdate = transformDate(trimBlank(cellValue));
					if(startdate!=null) {
						firAgtrnAs400Data.setStartdate(startdate);
						return "";
					}
					
				} catch (Exception e) {
					return msg;
				}
				return "";
			}
		},WALLNO(12,"") {//主要建材 欄位 M
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setWallmaterial(trimBlank(cellValue));
				}
				return msg;
			}
		},ROOFNO(13,"") {//屋頂材料 欄位 N
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setRoofmaterial(trimBlank(cellValue));
				}
				return msg;
			}
		},SUMFLOORS(17,"") {//總樓層數 欄位 R
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setSumfloors(Integer.parseInt((trimBlank(cellValue))));
				return msg;
			}
		},BUILDAREA(15,"") {//坪數 欄位 P
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setBuildarea(trimBlank(cellValue));
				return msg;
			}
		},BUILDYEARS(16,"") {//建築完成年 欄位 Q
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setBuildyears(trimBlank(cellValue));
				}
				return msg;
			}
		},AMOUNT_F(22,"") {//火險保額 欄位 W
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountF(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},AMOUNT_Q(23,"") {//地震險保額 欄位 X
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountQ(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},PREMIUM_F(28,"") {//火險保費 欄位 AC
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumF(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},PREMIUM_Q(29,"") {//地震險保費 欄位 AD
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumQ(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},POSTCODE_A(18,"") {//要保人郵遞區號 欄位 S
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setPostcodeA(trimBlank(cellValue));
				return msg;
			}
		},ADDRESS_A(19,"") {//要保人地址 欄位 T
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setAddressA(trimBlank(cellValue));
				return msg;
			}
		},POSTCODE_I(18,"") {//要保人郵遞區號 欄位 S
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setPostcodeI(trimBlank(cellValue));
				return msg;
			}
		},ADDRESS_I(19,"") {//要保人地址 欄位 T
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setAddressI(trimBlank(cellValue));
				return msg;
			}
		},ADDRESSCODE(10,"") {//標的物郵遞區號 欄位 K
			@SuppressWarnings("unchecked")
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String addresscode = trimBlank(getCellFormatValue(row.getCell(index)));
				firAgtrnAs400Data.setAddresscode(addresscode);
				
				if(!"".equals(addresscode) && addresscode.length()>=3) {
					addresscode = addresscode.substring(0,3);
					firAgtrnAs400Data.setAddresscode(addresscode);
					
					try {
						Map<String,String> params = new HashMap<>();
						params.put("codetype", "PostAddress");
						params.put("codecode", addresscode);
						Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
						if(result.getResObject() != null) {
							List<PrpdNewCode> resultList =  (List<PrpdNewCode>) result.getResObject();
							firAgtrnAs400Data.setAddressname(resultList.get(0).getCodecname());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return msg;
			}
		},ADDRESSDETAIL(11,""){//標的物地址 欄位 L
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setAddressdetail(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},STRUCTURE_1(14,""){//建築等級代號 欄位 O
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setStructure1(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},ID_A(7,""){//借款人 ID 欄位 H
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIdA(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},NAME_A(6,""){//借款人 欄位 G
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameA(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},ID_I(21,""){//所有權人 ID 欄位 V
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIdI(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},NAME_I(20,""){//所有權人 欄位  U
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameI(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},ENDDATE(9,"保險迄期異常或空白;"){//到期年月日 欄位 J
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String date = trimBlank(getCellFormatValue(row.getCell(index)));
				try {
					if(StringUtil.isSpace(date)) {
						return msg;
					}
					Date enddate = transformDate(trimBlank(date));
					if(enddate!=null) {
						firAgtrnAs400Data.setEnddate(enddate);
						return "";
					}
				} catch (Exception e) {
					return msg;
				}
				return msg;
			}
		},EXTRACOMCODE(4,""){//分行別 欄位  E
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return "分行別空白;";
				}
				firAgtrnAs400Data.setExtracomcode(trimBlank(cellValue));
				return msg;
			}
		},ORGICODE(33,""){//業務員員編 欄位  AH
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return "業務員員編空白;";
				}
				firAgtrnAs400Data.setOrgicode(trimBlank(cellValue));
				return msg;
			}
		},HANDLERIDENTIFYNUMBER(34,""){//業務員登錄證號 欄位  AI
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return "業務員登錄證號空白;";
				}
				firAgtrnAs400Data.setHandleridentifynumber(trimBlank(cellValue));
				return msg;
			}
		},IS_AUTO_RENEW(31,""){//所有權人 欄位  AF
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIsAutoRenew(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		},ACCOUNTNO(3,""){//扣款帳戶 欄位  D
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setAccountno(trimBlank(getCellFormatValue(row.getCell(index))));
				return msg;
			}
		};
		int index;
		String msg;
		ExcelHeader2 (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data);
		
		private static boolean checkNum(String cellValue) {
			if(!checkString(cellValue)) {
				return false;
			}
			String regex ="^[0-9]*$";
			Pattern pattern = Pattern.compile(regex);
			return pattern.matcher(cellValue).matches();
		}
		
		private static boolean checkString(String cellValue){
			try {
				return !StringUtil.isSpace(cellValue);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		private static String trimBlank(String cellValue){
			if(null != cellValue && cellValue.trim().length()>0) {
				return cellValue.replaceAll("[\\u3000|\\s]*", "");
			}
			return "";
		}
	}
	/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  End */

	// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -- start
	private enum ExcelHeader3{
		EXTRACOMCODE(1,"分行別空白;"){//分行別
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return msg;
				}
				firAgtrnAs400Data.setExtracomcode(cellValue);
				return "";
			}
		},
		EXTRACOMNAME(2,"分行名稱空白;"){//分行名稱
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return msg;
				}
				firAgtrnAs400Data.setExtraComName(cellValue);
				return "";
			}
		},
		OLDPOLICYNO(3,"續保保單號空白;"){//續保保單號
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValue = getCellFormatValue(row.getCell(index));
				if(!checkString(cellValue)){
					return msg;
				}
				firAgtrnAs400Data.setOldpolicyno(cellValue);
				return "";
			}
		},
		STARTDATE(4,"保險起期異常或空白;"){//起期日
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)){
					return msg;
				}					
				Date startdate = toDate(cellValue);
				if(startdate!=null) {
					firAgtrnAs400Data.setStartdate(startdate);
					return "";
				} else {
					return msg;
				}
			}
		},
		ENDDATE(5,"保險迄期異常或空白;"){//到期年月日
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data) {
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)){
					return msg;
				}
				Date enddate = toDate(cellValue);
				if(enddate!=null) {
					firAgtrnAs400Data.setEnddate(enddate);
					return "";
				} else {
					return msg;
				}
			}
		},
		NAME_A(6,""){//要保人姓名
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameA(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		ID_A(7,"要保人ID空白;"){//要保人 ID
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)){
					return msg;
				}
				firAgtrnAs400Data.setIdA(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		NAME_I(8,""){//被保險人姓名
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setNameI(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		ID_I(9,"被保險人ID空白;"){//被保險人 ID
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)){
					return msg;
				}
				firAgtrnAs400Data.setIdI(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		ADDRESSCODE(10,"標的物郵遞區號空白;") {//標的物郵遞區號
			@SuppressWarnings("unchecked")
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String addresscode = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(addresscode)){
					return msg;
				}
				firAgtrnAs400Data.setAddresscode(addresscode);
				
				if(!"".equals(addresscode) && addresscode.length()>=3) {
					addresscode = addresscode.substring(0,3);
					firAgtrnAs400Data.setAddresscode(addresscode);
					
					try {
						Map<String,String> params = new HashMap<>();
						params.put("codetype", "PostAddress");
						params.put("codecode", addresscode);
						Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
						if(result.getResObject() != null) {
							List<PrpdNewCode> resultList =  (List<PrpdNewCode>) result.getResObject();
							firAgtrnAs400Data.setAddressname(resultList.get(0).getCodecname());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return "";
			}
		},
		ADDRESSDETAIL(11,"標的物地址空白;"){//標的物地址
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String addresscode = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(addresscode)){
					return msg;
				}
				firAgtrnAs400Data.setAddressdetail(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		STRUCTURE_1(12,""){//建築等級代號
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setStructure1(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		BUILDAREA(14,"") {//坪數
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setBuildarea(trimBlank(cellValue));
				return "";
			}
		},
		AMOUNT_F(15,"") {//火險保額
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountF(Long.parseLong(trimBlank(cellValue)));
				}
				return "";
			}
		},
		PREMIUM_F(16,"") {//火險保費
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumF(Long.parseLong(trimBlank(cellValue)));
				}
				return "";
			}
		},
		AMOUNT_Q(17,"") {//地震險保額
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setAmountQ(Long.parseLong(trimBlank(cellValue)));
				}
				return msg;
			}
		},
		PREMIUM_Q(18,"") {//地震險保費
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setPremiumQ(Long.parseLong(trimBlank(cellValue)));
				}
				return "";
			}
		},
		SUMFLOORS(23,"") {//總樓層數
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setSumfloors(Integer.parseInt((trimBlank(cellValue))));
				return "";
			}
		},
		BUILDYEARS(24,"") {//建築完成年
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setBuildyears(trimBlank(cellValue));
				}
				return msg;
			}
		},
		ADDRESS_A(25,"") {//要保人地址
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				firAgtrnAs400Data.setAddressA(trimBlank(cellValue));
				//mantis：FIR0666，處理人員：BJ085，住火_元大續保作業_AS400資料匯入新增元大格式 20250310-議題重啟
				firAgtrnAs400Data.setAddressI(trimBlank(cellValue));
				return msg;
			}
		},
		WALLNO(26,"") {//本體(外牆)
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setWallmaterial(trimBlank(cellValue));
				}
				return msg;
			}
		},
		ROOFNO(27,"") {//屋頂
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if(checkNum(cellValue)) {
					firAgtrnAs400Data.setRoofmaterial(trimBlank(cellValue));
				}
				return msg;
			}
		},
		HANDLERIDENTIFYNUMBER(28,"業務員登錄證號空白;"){//業務員登錄證號
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return msg;
				}
				firAgtrnAs400Data.setHandleridentifynumber(trimBlank(cellValue));
				return "";
			}
		},
		HANDLER1CODE(29,"輔專人員空白;"){//輔專人員
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = trimBlank(getCellFormatValue(row.getCell(index)));
				if(!checkString(cellValue)) {
					return msg;
				}
				firAgtrnAs400Data.setHandler1Code(cellValue);
				return "";
			}
		},
		IS_AUTO_RENEW(31,""){//是否自動續保
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				firAgtrnAs400Data.setIsAutoRenew(trimBlank(getCellFormatValue(row.getCell(index))));
				return "";
			}
		},
		REMARK(37,""){//保單備註
			String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data){
				String cellValue = getCellFormatValue(row.getCell(index));
				if (!"".equals(cellValue)) {
					cellValue = cellValue.replaceAll("/", "/r/n");
					firAgtrnAs400Data.setRemark(cellValue);
				}
				return "";
			}
		};
		int index;
		String msg;
		ExcelHeader3 (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String checkCell(Row row,FirAgtrnAs400Data firAgtrnAs400Data);
		
		private static boolean checkNum(String cellValue) {
			if(!checkString(cellValue)) {
				return false;
			}
			String regex ="^[0-9]*$";
			Pattern pattern = Pattern.compile(regex);
			return pattern.matcher(cellValue).matches();
		}
		
		private static boolean checkString(String cellValue){
			try {
				return !StringUtil.isSpace(cellValue);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		private static String trimBlank(String cellValue){
			if(null != cellValue && cellValue.trim().length()>0) {
				return cellValue.replaceAll("[\\u3000|\\s]*", "");
			}
			return "";
		}

		private static Date toDate(String yyyyMMdd) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = null;
			try {
				date = sdf.parse(yyyyMMdd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
	}
	// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -- end

	private static String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell instanceof XSSFCell) {
			// 判斷當前Cell的Type
			XSSFCell xssfCell = (XSSFCell)cell;
			switch (xssfCell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC:{
				cellvalue = String.valueOf(xssfCell.getNumericCellValue());
				BigDecimal bd = new BigDecimal(cellvalue);
				cellvalue = new DecimalFormat("###.###").format(bd);
				break;
			}
			case XSSFCell.CELL_TYPE_STRING:{//Type為string
				cellvalue = xssfCell.getRichStringCellValue().getString();
				break;				
			}
			default : cellvalue = "";
			}
		}
		return cellvalue;
	}
	
	private static String getWallno(String wallno) {
		Map<String,String> wallnoMap = new HashMap<>();
		wallnoMap.put("鋼骨水泥造","01");
		wallnoMap.put("鋼筋水泥造","02");
		wallnoMap.put("鋼筋混凝土造","03");
		wallnoMap.put("磚造","04" );
		wallnoMap.put("磚水泥造","05" );
		wallnoMap.put("加強磚造","06");
		wallnoMap.put("磚、鐵架造","07");
		wallnoMap.put("磚、鐵皮造","08");
		wallnoMap.put("磚、石棉板造","09");
		wallnoMap.put("磚、石造","10");
		wallnoMap.put("磚、木造","11");
		wallnoMap.put("木造","12");
		wallnoMap.put("鐵皮造","13");
		wallnoMap.put("鐵架造","14");
		wallnoMap.put("鐵架木造","15");
		wallnoMap.put("鐵架、石棉板造","16");
		wallnoMap.put("石棉板造","17");
		wallnoMap.put("塑膠造","18");
		wallnoMap.put("土塊造","19");
		wallnoMap.put("露天","20");
		wallnoMap.put("石棉瓦造","21");
		wallnoMap.put("金屬板造","22");
		wallnoMap.put("烤漆板造","23");
		wallnoMap.put("烤漆鋼板造","24");
		wallnoMap.put("磚、烤漆板造","25");
		wallnoMap.put("玻璃纖維造","26");
		wallnoMap.put("鋼骨混凝土造","27");
		wallnoMap.put("鋼骨鋼筋混凝土造","28");
		wallnoMap.put("石造","29");
		wallnoMap.put("鋼筋混凝土加強磚","30");
		wallnoMap.put("磚、石、木","31");
		wallnoMap.put("鋼骨造","32");
		wallnoMap.put("土、木造","33");
		wallnoMap.put("鋼鐵造","34");
		wallnoMap.put("預鑄混凝土造","35");
		wallnoMap.put("鋼筋混凝土加強空","36");
		wallnoMap.put("鋼筋混凝土加強空心磚造","36");//新核心
		wallnoMap.put("詳備註","37");
		wallnoMap.put("鋼架造","38");
		wallnoMap.put("鋼骨結構造","39");
		wallnoMap.put("混凝土造","40");
		wallnoMap.put("預力混凝土造","41");
		wallnoMap.put("壁式預鑄鋼筋混凝","42");
		wallnoMap.put("壁式預鑄鋼筋混凝土造","42");//新核心
		wallnoMap.put("鐵筋加強磚造","46");
		wallnoMap.put("鋼筋加強磚造","46");//新核心
		wallnoMap.put("鋼骨鋼筋加強磚","48");
		wallnoMap.put("鋼骨鋼筋加強磚造","48");//新核心
		wallnoMap.put("其他詳備註","49");
		wallnoMap.put("鋼造","AA");
		wallnoMap.put("土造","AB");
		wallnoMap.put("土石造","AC");
		wallnoMap.put("土磚石混合造","AD");
		wallnoMap.put("竹造","AE");
		wallnoMap.put("鋁架造","AF");
		
		if(wallnoMap.containsKey(wallno)) {
			return wallnoMap.get(wallno); 
		}
		return "";
	}
	
	private static String getRoofno(String roofno) {
		Map<String,String> roofnoMap = new HashMap<>();
		roofnoMap.put("平屋頂", "50");
		roofnoMap.put("瓦屋頂", "51");
		roofnoMap.put("石棉板屋頂", "52");
		roofnoMap.put("鐵皮屋頂", "53");
		roofnoMap.put("塑膠屋頂", "54");
		roofnoMap.put("油毛氈屋頂", "55");
		roofnoMap.put("平、瓦屋頂", "56");
		roofnoMap.put("石棉瓦屋頂", "57");
		roofnoMap.put("金屬板屋頂", "58");
		roofnoMap.put("金屬鐵皮屋頂", "58");//新核心
		roofnoMap.put("烤漆板屋頂", "59");
		roofnoMap.put("烤漆鋼板屋頂", "60");
		roofnoMap.put("玻璃纖維屋頂", "61");
		roofnoMap.put("木屋頂", "62");
		roofnoMap.put("水泥平屋頂", "70");
		roofnoMap.put("鋼筋水泥磚", "71");
		roofnoMap.put("鋼筋水泥磚屋頂", "71");//新核心
		roofnoMap.put("金屬造金屬屋頂", "96");
		roofnoMap.put("水泥斜屋頂", "97");
		roofnoMap.put("詳附圖", "98");
		roofnoMap.put("略", "99");
		
		if(roofnoMap.containsKey(roofno)) {
			return roofnoMap.get(roofno);
		}
		return "";
	}
	
	private void removeFirAgtrnAs400Data(BigDecimal oid) throws Exception {
		as400RenewalDataService.removeFirAgtrnAs400Data(oid);
	}
	
	private void removeFirAgtrnAs400DataErr(BigDecimal oid) throws Exception {
		as400RenewalDataService.removeFirAgtrnAs400DataErr(oid);
	}
	
	private void insertFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws Exception {
		as400RenewalDataService.insertFirAgtrnAs400Data(firAgtrnAs400Data);
	}
	
	private void insertFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws Exception {
		as400RenewalDataService.insertFirAgtrnAs400DataErr(firAgtrnAs400DataErr);
	}
	
	private void updateFirAgtrnAs400DataUplist(String businessnature, String rnYyyymm, String remark, int qtyNg, int qtyOk) throws Exception {
		FirAgtrnAs400DataUplist firAgtrnAs400DataUplist = new FirAgtrnAs400DataUplist();
		firAgtrnAs400DataUplist.setBusinessnature(businessnature);
		firAgtrnAs400DataUplist.setRnYyyymm(rnYyyymm);
		firAgtrnAs400DataUplist.setQtyNg(qtyNg);
		firAgtrnAs400DataUplist.setQtyOk(qtyOk);
		remark = "執行完成時間-"+new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date())+";" + remark;
		if(remark.length()>300) {
			remark = remark.substring(0,300); 
		}
		firAgtrnAs400DataUplist.setRemark(remark);
		as400RenewalDataService.updateFirAgtrnAs400DataUplist(firAgtrnAs400DataUplist);
	}

	//檢查日期格式
	private static boolean checkDateFormat(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.setLenient(false);
			//如果成功就是正確的日期，失敗就是有錯誤的日期。  
			sdf.parse(date);  
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	//轉換民國年為西元年並判斷日期格試是否正確
	private static Date transformDate(String date) throws Exception {
		if(StringUtil.isSpace(date)) {
			return null;
		}
		int signDate = Integer.parseInt(date)+19110000;
		date = Integer.toString(signDate);
		
		if(checkDateFormat(date)) {//檢查日期格式是否正確，否則回傳null
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		}
		return null;
	}

	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirAgtrnAs400DataUplistService getFirAgtrnAs400DataUplistService() {
		return firAgtrnAs400DataUplistService;
	}

	public void setFirAgtrnAs400DataUplistService(FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService) {
		this.firAgtrnAs400DataUplistService = firAgtrnAs400DataUplistService;
	}

	public AS400RenewalDataService getAs400RenewalDataService() {
		return as400RenewalDataService;
	}

	public void setAs400RenewalDataService(AS400RenewalDataService as400RenewalDataService) {
		this.as400RenewalDataService = as400RenewalDataService;
	}

	public FirAgtrnAs400DataService getFirAgtrnAs400DataService() {
		return firAgtrnAs400DataService;
	}

	public void setFirAgtrnAs400DataService(FirAgtrnAs400DataService firAgtrnAs400DataService) {
		this.firAgtrnAs400DataService = firAgtrnAs400DataService;
	}

	public FirAgtrnAs400DataErrService getFirAgtrnAs400DataErrService() {
		return firAgtrnAs400DataErrService;
	}

	public void setFirAgtrnAs400DataErrService(FirAgtrnAs400DataErrService firAgtrnAs400DataErrService) {
		this.firAgtrnAs400DataErrService = firAgtrnAs400DataErrService;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

}
