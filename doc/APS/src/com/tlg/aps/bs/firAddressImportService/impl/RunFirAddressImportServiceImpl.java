package com.tlg.aps.bs.firAddressImportService.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firAddressImportService.FirAddressImportService;
import com.tlg.aps.bs.firAddressImportService.RunFirAddressImportService;
import com.tlg.aps.util.XlsToCsv;
import com.tlg.aps.vo.AddressFormatDataVo;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.prpins.entity.FirAddrImporterr;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAddrImportlistService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class RunFirAddressImportServiceImpl implements RunFirAddressImportService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	
	private FirAddrImportlistService firAddrImportlistService;
	private static FirAddressImportService firAddressImportService;
	private static final String FILEFOLDER = "D:\\APS005FileUpload\\";
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyyMMdd");

    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
	private static final Logger logger = Logger.getLogger(RunFirAddressImportServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public Result AddressDataImport(UserInfo userInfo,Date excuteTime,String programId) throws Exception {
		Result result = new Result();
		//新增執行記錄檔
		FirBatchLog firBatchLog = new FirBatchLog();
		BigDecimal batchLogOid = null;
		result = this.insertFirBatchLog(excuteTime,userInfo,programId);
		System.out.println("新增執行記錄檔");
		if(result.getResObject() != null) {
			firBatchLog = (FirBatchLog) result.getResObject();
			if(firBatchLog.getStatus().equals("F")) {
				return this.getReturnResult("接收參數空白，結束排程");
			}
			batchLogOid = firBatchLog.getOid();
		}

		//查詢要匯入的檔案資料
		Map<String,String> params = new HashMap<>();
		params.put("fileStatus", "N");
		result = firAddrImportlistService.findFirAddrImportlistByParams(params);
		if(result.getResObject() == null) {
			this.updateFirBatchLog("N","",userInfo,batchLogOid);//TMP_BATCH_STATUS = 'N'
			return this.getReturnResult("查無檔案資料");
		}
		
		//查詢欲處理資料 FILE_STATUS="N" 有資料
		FirAddrImportlist firAddrImportlist = new FirAddrImportlist();
		List<FirAddrImportlist> resultList = (List<FirAddrImportlist>) result.getResObject();
		//只對最新的excel做處理
		String fileStatus = "";
		String fileMsg = "";
		String filenameNew = resultList.get(0).getFilenameNew();
		/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
		String ultype = resultList.get(0).getUltype();
		
		//找出相對應檔案
		String filePath = FILEFOLDER + filenameNew;
		File file = new File(filePath);
		
		if (!file.exists()) {//無法取得檔案 -->更新XLSX檔處理結果、其餘XLSX檔處理
			fileStatus = "E";
			fileMsg = "無法取得最新檔案";
			this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,fileStatus,fileMsg);
			
			if(resultList.size()>1) {//多筆資料
				otherXlsxHandle(userInfo, firAddrImportlist, resultList);
			}
			return this.getReturnResult("無法取得最新檔案");
		}
		//讀取excel檔開始
		try {			
			OPCPackage p = OPCPackage.open(filePath, PackageAccess.READ);
			/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整  start*/
			//每一行的欄位數 = 10
			XlsToCsv xlsx2csv = new XlsToCsv(p, 10);
			/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整  end*/
	        ArrayList<ArrayList<String>> excelOutput = new ArrayList<ArrayList<String>>();
	        excelOutput = xlsx2csv.process();
	        p.close();
			
			if(excelOutput.size() <= 1) {//excel裡無資料 -->更新XLSX檔處理結果、其餘XLSX檔處理
				fileStatus = "E";
				fileMsg = "XLSX檔無資料";
				this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,fileStatus,fileMsg);
				if(resultList.size()>1) {//多筆資料 其餘XLSX檔處理
					for(int i=1;i<resultList.size();i++) {
						fileStatus = "X";
						fileMsg="";
						firAddrImportlist.setQtyAll(0);
						firAddrImportlist.setQtyNg(0);
						firAddrImportlist.setQtyOk(0);
						filenameNew = resultList.get(i).getFilenameNew();
						this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,fileStatus,fileMsg);
					}
				}
				return this.getReturnResult("XLSX檔無資料");
			}
			
			int tmpAll = excelOutput.size()-1; //資料總筆數
			int tmpOk = 0;		   //通過筆數
			int tmpNg = 0;		   //不通過筆數
			
			/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
			if("1".equals(ultype)) {
				firAddressImportService.truncateFirAddrCkdata();
			}
			/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/
			
			for(int i=1; i<excelOutput.size(); i++){
				FirAddrCkdata firAddrCkdata = new FirAddrCkdata();
				StringBuilder errMsg = new StringBuilder();
				/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
				String[] returnValue;
				String flag = "";
				for(ExcelHeader header: ExcelHeader.values()) {
					returnValue = header.checkCell(excelOutput.get(i), firAddrCkdata, ultype);
					errMsg.append(returnValue[0]);
					if(returnValue[1] != null && returnValue[1].length() > 0) {
						flag = returnValue[1];
					}
				}
				/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/
				if(errMsg.length()==0){
					/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整
					 *通過檢核的資料，才呼叫webService做地址正規化動作 */
					addressFormat(firAddrCkdata);
					
					/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
					if("1".equals(ultype) || ("2".equals(ultype) && "1".equals(flag))) {
						this.insertFirAddrCkdata(firAddrCkdata,userInfo);
						tmpOk++;
					}else if(("2".equals(ultype) && "2".equals(flag))){
						Result tmpResult = this.firAddressImportService.findFirAddrCkdataByPolicyno(firAddrCkdata.getPolicyno());
						if(tmpResult != null && tmpResult.getResObject() != null) {
							FirAddrCkdata dbFirAddrCkdata = (FirAddrCkdata)tmpResult.getResObject();
							dbFirAddrCkdata.setUnderwriteenddate(firAddrCkdata.getUnderwriteenddate());
							dbFirAddrCkdata.setOriAddress(firAddrCkdata.getOriAddress());
							dbFirAddrCkdata.setStdAddress(firAddrCkdata.getStdAddress());
							dbFirAddrCkdata.setAddrStructure(firAddrCkdata.getAddrStructure());
							dbFirAddrCkdata.setAddrSumfloors(firAddrCkdata.getAddrSumfloors());
							dbFirAddrCkdata.setIupdate(userInfo.getUserId().toUpperCase());
							dbFirAddrCkdata.setDupdate(new Date());
							/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 start*/
							dbFirAddrCkdata.setFormattedCode(firAddrCkdata.getFormattedCode());
							dbFirAddrCkdata.setFormattedMsg(firAddrCkdata.getFormattedMsg());
							dbFirAddrCkdata.setFormattedResult(firAddrCkdata.getFormattedResult());
							dbFirAddrCkdata.setFormattedAddr(firAddrCkdata.getFormattedAddr());
							dbFirAddrCkdata.setFormattedZip(firAddrCkdata.getFormattedZip());
							dbFirAddrCkdata.setFormattedCity(firAddrCkdata.getFormattedCity());
							dbFirAddrCkdata.setFormattedDistrict(firAddrCkdata.getFormattedDistrict());
							dbFirAddrCkdata.setFormattedNeighborhood(firAddrCkdata.getFormattedNeighborhood());
							dbFirAddrCkdata.setFormattedRoad(firAddrCkdata.getFormattedRoad());
							dbFirAddrCkdata.setFormattedLane(firAddrCkdata.getFormattedLane());
							dbFirAddrCkdata.setFormattedAlley1(firAddrCkdata.getFormattedAlley1());
							dbFirAddrCkdata.setFormattedAlley2(firAddrCkdata.getFormattedAlley2());
							dbFirAddrCkdata.setFormattedNo(firAddrCkdata.getFormattedNo());
							dbFirAddrCkdata.setFormattedFloor(firAddrCkdata.getFormattedFloor());
							dbFirAddrCkdata.setFormattedOther(firAddrCkdata.getFormattedOther());
							/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 end*/
							/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整  start*/
							dbFirAddrCkdata.setWallmaterial(firAddrCkdata.getWallmaterial());
							dbFirAddrCkdata.setRoofmaterial(firAddrCkdata.getRoofmaterial());
							dbFirAddrCkdata.setBuildyears(firAddrCkdata.getBuildyears());
							dbFirAddrCkdata.setRemark(firAddrCkdata.getRemark());
							/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整  end*/
							this.firAddressImportService.updateFirAddrCkdata(dbFirAddrCkdata);
							tmpOk++;
						}
					}
					/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/
				}else {
					this.insertFirAddrImporterr(userInfo,filenameNew,i+1,StringUtil.isSpace(errMsg.toString())?"保單號碼重複":errMsg.toString());
				}
			}
			tmpNg = tmpAll - tmpOk;
			if(resultList.size()>1) {//多筆資料 其餘XLSX檔處理
				otherXlsxHandle(userInfo, firAddrImportlist, resultList);
			}
			fileStatus="Y";
			fileMsg="";
			firAddrImportlist.setQtyAll(tmpAll);
			firAddrImportlist.setQtyNg(tmpNg);
			firAddrImportlist.setQtyOk(tmpOk);
			this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,fileStatus,fileMsg);
		}catch (Exception e){
		    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
			logger.error(e.getMessage(), e);
			// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
			this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,"E",e.getMessage());
			this.updateFirBatchLog("F","火險地址資料匯入錯誤",userInfo,batchLogOid);
			return this.getReturnResult("火險地址資料匯入錯誤");
		}
		//所有動作完成後，將資料夾內所有檔案刪除
		deleteFiles();
		//所有動作執行完成，更新執行記錄檔，STATUS="S"成功
		this.updateFirBatchLog("S","",userInfo,batchLogOid);
		
		return this.getReturnResult("執行完成");
	}

	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 start*/
	private void addressFormat(FirAddrCkdata firAddrCkdata) throws Exception {
		AddressFormatDataVo addressFormatDataVo = firAddressImportService.addressFormat(firAddrCkdata.getOriAddress());
		//地址正規化服務異常
		if(null == addressFormatDataVo) {
			firAddrCkdata.setFormattedResult("F");
		}else {
			String formattedResult = "N";
			if("S000".equals(addressFormatDataVo.getCode())) {
				formattedResult = "Y";
				firAddrCkdata.setFormattedAddr(addressFormatDataVo.getAddressFormatted());
				firAddrCkdata.setFormattedZip(addressFormatDataVo.getZip());
				firAddrCkdata.setFormattedCity(addressFormatDataVo.getCity());
				firAddrCkdata.setFormattedDistrict(addressFormatDataVo.getDistrict());
				firAddrCkdata.setFormattedNeighborhood(addressFormatDataVo.getNeighborhood());
				firAddrCkdata.setFormattedRoad(addressFormatDataVo.getRoad());
				firAddrCkdata.setFormattedLane(addressFormatDataVo.getLane());
				firAddrCkdata.setFormattedAlley1(addressFormatDataVo.getAlley1());
				firAddrCkdata.setFormattedAlley2(addressFormatDataVo.getAlley2());
				firAddrCkdata.setFormattedNo(addressFormatDataVo.getNo());
				firAddrCkdata.setFormattedFloor(addressFormatDataVo.getFloor());
				firAddrCkdata.setFormattedOther(addressFormatDataVo.getOther());
			}
			firAddrCkdata.setFormattedCode(addressFormatDataVo.getCode());
			//mantis：FIR0696，處理人員：CD094，需求單編號：FIR0696 住火_APS_火險地址匯入及查詢作業上傳檔案CALLWS檢核錯誤訊息截200長度 start
			firAddrCkdata.setFormattedMsg(addressFormatDataVo.getMsg().length()>66 ? addressFormatDataVo.getMsg().substring(0,66):addressFormatDataVo.getMsg());
			//mantis：FIR0696，處理人員：CD094，需求單編號：FIR0696 住火_APS_火險地址匯入及查詢作業上傳檔案CALLWS檢核錯誤訊息截200長度 end
			firAddrCkdata.setFormattedResult(formattedResult);
		}
	}
	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 end*/
	
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
	private enum ExcelHeader{
		DCREATE(0,""){
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype) {
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				try {
					String cellValue = row.get(index);
					if(checkString(cellValue)&&checkDateFormat(cellValue)) {
						firAddrCkdata.setDcreate(DATEFORMAT.parse(cellValue));
					}else {
						firAddrCkdata.setDcreate(new Date());
					}
				} catch (Exception e) {
				    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
					logger.error(e.getMessage(), e);
					firAddrCkdata.setDcreate(new Date());
				}
				return returnValue;
				
			}
		},UWDATE(1,"簽單日期異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype) {
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				try {
					String cellValue = row.get(index);
					if(checkString(cellValue)&&transformUwdate(cellValue) != null) {
						firAddrCkdata.setUnderwriteenddate(transformUwdate(cellValue));
                    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -- start
					} else {
						returnValue[0] = "簽單日期[" + cellValue + "]格式錯誤;";
					}
					// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -- end
				} catch (Exception e) {
				    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
					logger.error(e.getMessage(), e);
					returnValue[0] = msg;
				}
				return returnValue;
			}
		},POLICYNO(2,"保單號碼異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue) && "18".equals(cellValue.substring(0,2)) && cellValue.length() == 14) {
					
					if(policynoIsExist(cellValue)) {
						
						if("2".equals(ultype)) {
							returnValue[1] = "2";
						}else {
							returnValue[0] = "保單號碼重複";
						}
					}else {
						if("2".equals(ultype)) {
							returnValue[1] = "1";
						}
					}
					firAddrCkdata.setPolicyno(cellValue);
				}
				return returnValue;
			}
		},ORIADDRESS(3,"標的物地址異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue)) {
					firAddrCkdata.setOriAddress(cellValue);
				}else {
					returnValue[0] = msg;
				}
				return returnValue;
			}
		},ADDRSTRUCTURE(4,"建築等級異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue) && checkNum(cellValue)) {
					firAddrCkdata.setAddrStructure(cellValue);
				}else {
					returnValue[0] = msg;
				}
				return returnValue;
			}
		},ADDRSUMFLOORS(5,"樓層數異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue) && checkNum(cellValue)) {
					//mantis:FIR0183_APS_保單地址主檔轉入作業，樓層數存入正整數----START
					BigDecimal bd = new BigDecimal(cellValue);
					firAddrCkdata.setAddrSumfloors(Integer.toString(bd.intValue()));
					//mantis:FIR0183_APS_保單地址主檔轉入作業，樓層數存入正整數----END
				}else {
					returnValue[0] = msg;
				}
				return returnValue;
			}
		},STDADDRESS(6,"轉換後標的物地址異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue)) {
					firAddrCkdata.setStdAddress(cellValue);
				}else {
					returnValue[0] = msg;
				}
				return returnValue;
			}
		/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整 start*/
		},WALLMATERIAL(7,"") {//外牆
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue)) {
					firAddrCkdata.setWallmaterial(cellValue);
				}
				return returnValue;
			}
		},ROOFMATERIAL(8,"") {//屋頂
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue)) {
					firAddrCkdata.setRoofmaterial(cellValue);
				}
				return returnValue;
			}
		},BUILDYEARS(9,"建築年度異常;") {
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue) && checkNum(cellValue)) {
					BigDecimal bd = new BigDecimal(cellValue);
					firAddrCkdata.setBuildyears(Integer.toString(bd.intValue()));
				}else {
					returnValue[0] = msg;
				}
				return returnValue;
			}
		},REMARK(10,"") {//備註
			String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype){
				String[] returnValue = {"",""};//0放errorMsg,1放flag
				String cellValue = row.get(index);
				if(checkString(cellValue)) {
					firAddrCkdata.setRemark(cellValue);
				}
				return returnValue;
			}
		/*mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整 end*/
		};
		int index;
		String msg;
		ExcelHeader (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String[] checkCell(ArrayList<String> row,FirAddrCkdata firAddrCkdata, String ultype);
		private static boolean checkNum(String cellValue) {
			String regex ="^[0-9]*[1-9][0-9]*$";
			Pattern pattern = Pattern.compile(regex);
		    return pattern.matcher(cellValue).matches();
			
		}
		private static boolean checkString(String cellValue){
			try {
				return !StringUtil.isSpace(cellValue);
			} catch (Exception e) {
			    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		private static boolean policynoIsExist(String cellValue){
			Map<String,String> params = new HashMap<>();
			params.put("policyno",cellValue);
			try {
				if(firAddressImportService.countFirAddrCkdata(params)>0) {
					return true;
				}
			} catch (Exception e) {
			    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
				logger.error(e.getMessage(), e);
				return false;
			}
			return false;
		}
	}
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/

	//其餘XLSX檔處理
	private void otherXlsxHandle(UserInfo userInfo, FirAddrImportlist firAddrImportlist, List<FirAddrImportlist> resultList)
			throws Exception {
		String fileStatus;
		String fileMsg;
		String filenameNew;
		for(int i=1;i<resultList.size();i++) {
			fileStatus = "X";
			fileMsg="";
			firAddrImportlist.setQtyAll(0);
			firAddrImportlist.setQtyNg(0);
			firAddrImportlist.setQtyOk(0);
			filenameNew = resultList.get(i).getFilenameNew();
			this.updateFirAddrImportlist(firAddrImportlist,filenameNew,userInfo,fileStatus,fileMsg);
		}
	}

	private Result insertFirBatchLog(Date excuteTime, UserInfo userInfo, String programId) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();	
		String dateTime = sdf.format(date);

		FirBatchLog firBatchLog = new FirBatchLog();
		String status = "2";
		if(userInfo == null||StringUtil.isSpace(programId)||excuteTime == null) {
			status = "F";
		}
		String remark="";
		StringBuilder sb = new StringBuilder();
		if(excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if(StringUtil.isSpace(userInfo.getUserId())) {
			sb.append("執行人員無內容值。");
		}
		if(StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		remark = sb.toString();
		
		firBatchLog.setStatus(status);
		firBatchLog.setBatchNo("IMPORT"+dateTime);
		firBatchLog.setPrgId(programId);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userInfo.getUserId().toUpperCase());
		firBatchLog.setDcreate(date);
		Result result = firAddressImportService.insertFirBatchLog(firBatchLog);
		if(status.equals("F")) {
			result.setResObject(firBatchLog);
			return result;
		}
		return result;
	}
	
	private void insertFirAddrCkdata(FirAddrCkdata firAddrCkdata,UserInfo userInfo) throws Exception {
		firAddrCkdata.setAddrNo("1");
		firAddrCkdata.setValidFlag("1");
		firAddrCkdata.setIcreate(userInfo.getUserId().toUpperCase());
		firAddrCkdata.setIupdate(userInfo.getUserId().toUpperCase());
		firAddrCkdata.setDupdate(new Date());
		firAddressImportService.insertFirAddrCkdata(firAddrCkdata);
	}
	
	private void updateFirBatchLog(String status, String remark, UserInfo userInfo,BigDecimal oid) throws Exception{
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		firBatchLog.setIupdate(userInfo.getUserId().toUpperCase());
		firBatchLog.setDupdate(new Date());
		firBatchLog.setOid(oid);
		firAddressImportService.updateFirBatchLog(firBatchLog);
	}
	
	private void updateFirAddrImportlist(FirAddrImportlist firAddrImportlist,String filenameNew,
			UserInfo userInfo,String fileStatus,String fileMsg) throws Exception {
		firAddrImportlist.setFilenameNew(filenameNew);
		firAddrImportlist.setFileStatus(fileStatus);
		firAddrImportlist.setRemark(fileMsg);
		firAddrImportlist.setIupdate(userInfo.getUserId().toUpperCase());
		firAddrImportlist.setDupdate(new Date());
		firAddressImportService.updateFirAddrImportlist(firAddrImportlist);
	}
	
	private void insertFirAddrImporterr(UserInfo userInfo, String filenameNew, int excelRow,String errMsg) throws Exception {
		FirAddrImporterr firAddrImporterr = new FirAddrImporterr();
		firAddrImporterr.setFilenameNew(filenameNew);
		firAddrImporterr.setExcelRow(excelRow);
		firAddrImporterr.setErrmsg(errMsg.length()>500?errMsg.substring(0, 500):errMsg);
		firAddressImportService.insertFirAddrImporterr(firAddrImporterr,userInfo);
	}

	//檢查日期格式
	private static boolean checkDateFormat(String date) {
		try {
			DATEFORMAT.setLenient(false);
			//如果成功就是正確的日期，失敗就是有錯誤的日期。  
			DATEFORMAT.parse(date);  
		}catch(Exception e) {
		    // mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	//判斷簽單日期是否為民國年，並檢查格式是否正確
	private static Date transformUwdate(String uwdate) throws Exception {
		if(StringUtil.isSpace(uwdate)) {
			return null;
		}
		uwdate = uwdate.replace("/", "");
		if(uwdate.length()==7 || uwdate.length()==6) {//民國年長度 ex 1090810 || 980810
			int signDate = Integer.parseInt(uwdate)+19110000;
			uwdate = Integer.toString(signDate);
		}
		
		if(checkDateFormat(uwdate)) {//檢查日期格式是否正確，否則回傳null
			return DATEFORMAT.parse(uwdate);
		}
		return null;
	}

	//刪除資料夾內檔案
	private void deleteFiles() {
		File deleteFile = new File(FILEFOLDER);
		if(deleteFile.isDirectory()){
			File [] fs = deleteFile.listFiles();
			for (int i = 0; i < fs.length; i++) {
				File file = fs[i];
				file.delete();
			}
		}
	}

	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirAddrImportlistService getFirAddrImportlistService() {
		return firAddrImportlistService;
	}

	public void setFirAddrImportlistService(FirAddrImportlistService firAddrImportlistService) {
		this.firAddrImportlistService = firAddrImportlistService;
	}

	public FirAddressImportService getFirAddressImportService() {
		return firAddressImportService;
	}

	public void setFirAddressImportService(FirAddressImportService firAddressImportService) {
		this.firAddressImportService = firAddressImportService;
	}
}
