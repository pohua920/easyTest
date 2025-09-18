package com.tlg.aps.bs.firCtbcRewNoticeService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firCtbcRewNoticeService.RewDataInsertService;
import com.tlg.aps.service.PremiumCalService;
import com.tlg.aps.util.BuildingGradeMappingUtil;
import com.tlg.aps.util.CountryMappingUtil;
import com.tlg.aps.util.CwpUtil;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.aps.vo.VerifyIdVo;
import com.tlg.aps.webService.firRuleService.client.RuleCheckService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewDontnotice;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.prpins.entity.FirCtbcRewMatchname;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.prpins.entity.FirCtbcRewOriginal180;
import com.tlg.prpins.entity.FirCtbcRewSnn;
import com.tlg.prpins.entity.Prpdnewcode;
import com.tlg.prpins.service.FirCtbcRewDontnoticeService;
import com.tlg.prpins.service.FirCtbcRewFix180Service;
import com.tlg.prpins.service.FirCtbcRewMatchLogService;
import com.tlg.prpins.service.FirCtbcRewMatchnameService;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.prpins.service.FirCtbcRewNoticeBatchService;
import com.tlg.prpins.service.FirCtbcRewOriginal180Service;
import com.tlg.prpins.service.FirCtbcRewSnnService;
import com.tlg.prpins.service.PrpdnewcodeService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.util.WebserviceObjConvert;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class RewDataInsertServiceImpl implements RewDataInsertService {
	
	private static final Logger logger = Logger.getLogger(RewDataInsertServiceImpl.class);
	private ConfigUtil configUtil;
	private FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService;
	private FirCtbcRewOriginal180Service firCtbcRewOriginal180Service;
	private FirCtbcRewDontnoticeService firCtbcRewDontnoticeService;
	private FirCtbcRewFix180Service firCtbcRewFix180Service;
	private FirCtbcRewNoshowwordService firCtbcRewNoshowwordService;
	private FirCtbcRewMatchLogService firCtbcRewMatchLogService;
	private PremiumCalService premiumCalService;
	private RfrcodeService rfrcodeService;
	private RuleCheckService clientRuleCheckService;
	private FirCtbcRewMatchnameService firCtbcRewMatchnameService;
	private FirCtbcRewSnnService firCtbcRewSnnService;
	private PrpdnewcodeService prpdnewcodeService;

	@Override
	public Result policyDataImport(UserInfo userInfo) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result inputFirCtbcRewNoticeBatch(Date executeTime, UserInfo userInfo, FirCtbcRewNoticeBatchVo voObject)
			throws SystemException, Exception {
		
		FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = new FirCtbcRewNoticeBatch();
		
		firCtbcRewNoticeBatch.setCtbcno(voObject.getCtbcno());
		firCtbcRewNoticeBatch.setP180filename(voObject.getP180FileName());
		firCtbcRewNoticeBatch.setC180filename(voObject.getC180FileName());
		firCtbcRewNoticeBatch.setDontnoticefilename(voObject.getDontnoticeFileName());
		firCtbcRewNoticeBatch.setTransrs("開始轉檔...");
		firCtbcRewNoticeBatch.setTransing("Y");
		firCtbcRewNoticeBatch.setIcreate(userInfo.getUserId());
		firCtbcRewNoticeBatch.setDcreate(new Date());
		
		Result result = this.firCtbcRewNoticeBatchService.insertFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
		
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewNoticeBatch(long transtime, FirCtbcRewNoticeBatch firCtbcRewNoticeBatch)
			throws SystemException, Exception {

		String transrs = firCtbcRewNoticeBatch.getTransrs();
		transrs += "FIR_CTBC_REW_FIX180轉檔完成(共用了: " + transtime +"秒)";
		firCtbcRewNoticeBatch.setTransrs(transrs);
		firCtbcRewNoticeBatch.setTransing("N");
		firCtbcRewNoticeBatch.setInvalidflag("N");

		Result result = this.firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
		
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException, Exception {
		Result result = this.firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
		return result;
	}
	
	@SuppressWarnings({"resource" })
	@Override
	public void inputRawData(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch, Date executeTime, File file, String fileName, String fileType, UserInfo userInfo) throws SystemException, Exception {

		long linenum = 0;
		String transrs = firCtbcRewNoticeBatch.getTransrs();
		try {

			if("P180".equals(fileType) || "C180".equals(fileType)) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS950"));
				FirCtbcRewOriginal180 firCtbcRewOriginal180;
				String str;
				while ((str = reader.readLine()) != null) {
					linenum += 1;
					firCtbcRewOriginal180 = new FirCtbcRewOriginal180();
					firCtbcRewOriginal180.setBatchOid(firCtbcRewNoticeBatch.getOid());
					firCtbcRewOriginal180.setO180filename(fileName);
					firCtbcRewOriginal180.setLinenum(linenum);
					firCtbcRewOriginal180.setRowdata(str);
					firCtbcRewOriginal180.setIcreate(userInfo.getUserId());
					firCtbcRewOriginal180.setDcreate(new Date());
					firCtbcRewOriginal180Service.insertFirCtbcRewOriginal180(firCtbcRewOriginal180);
				}
			}else {
				try {
					FirCtbcRewDontnotice firCtbcRewDontnotice;
					FileInputStream nfile = new FileInputStream(new File(file.getPath()));
					// Get the workbook instance for XLS file
					XSSFWorkbook workbook = new XSSFWorkbook(nfile);
					// Get first sheet from the workbook
					XSSFSheet sheet = workbook.getSheetAt(0);
					// Iterate through each rows from first sheet
					Iterator<Row> rowIterator = sheet.iterator();
					boolean hasData = false;
					while (rowIterator.hasNext()) {
						hasData = false;
						firCtbcRewDontnotice = new FirCtbcRewDontnotice();
						firCtbcRewDontnotice.setBatchOid(firCtbcRewNoticeBatch.getOid());
						firCtbcRewDontnotice.setIcreate(userInfo.getUserId());
						firCtbcRewDontnotice.setDcreate(new Date());
						Row row = rowIterator.next();
						// For each row, iterate through each columns
						Iterator<Cell> cellIterator = row.cellIterator();
						
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							if (cell.getColumnIndex() == 0
									&& cell.getCellType() == Cell.CELL_TYPE_STRING
									&& cell.getStringCellValue() != null) {
								firCtbcRewDontnotice.setOwner(cell.getStringCellValue().trim());
								hasData = true;
							}
							if (cell.getColumnIndex() == 1
									&& cell.getCellType() == Cell.CELL_TYPE_STRING
									&& cell.getStringCellValue() != null) {
								firCtbcRewDontnotice.setOwnerid(cell.getStringCellValue().trim());
								hasData = true;
							}
						}
						
						if(hasData) {
							linenum += 1;
							firCtbcRewDontnotice.setLinenum(linenum);
							firCtbcRewDontnoticeService.insertFirCtbcRewDontnotice(firCtbcRewDontnotice);
						}

					}
					nfile.close();

				} catch (FileNotFoundException e) {
					transrs += "找不到不通知續保件檔案...";
					e.printStackTrace();
					throw e;
				} catch (IOException e) {
					transrs += "不通知續保件檔案IOException...";
					e.printStackTrace();
					throw e;
				}
			}
			
			if("P180".equals(fileType)) {
				firCtbcRewNoticeBatch.setP180datarow(linenum);
				transrs += "個金檔案落地完成...";
			}else if("C180".equals(fileType)) {
				firCtbcRewNoticeBatch.setC180datarow(linenum);
				transrs += "法金檔案落地完成...";
			}else {
				firCtbcRewNoticeBatch.setDontnoticedatarow(linenum);
				transrs += "不通知續保件檔案落地完成...";
			}
			
		}catch(Exception e) {
			if("P180".equals(fileType)) {
				transrs += "個金檔案Exception...";
			}else if("C180".equals(fileType)) {
				transrs += "法金檔案Exception...";
			}else {
				transrs += "不通知續保件檔案Exception...";
			}
			e.printStackTrace();
		}finally {
			firCtbcRewNoticeBatch.setTransrs(transrs);
			firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insertAnalyzeRawData(BigDecimal batchOid, Date executeTime, UserInfo userInfo) throws SystemException, Exception {
		Result result;
		try {
			Map params = new HashMap();
			params.put("batchOid", batchOid);
			result = firCtbcRewOriginal180Service.findFirCtbcRewOriginal180ByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewOriginal180> searchResult = (List<FirCtbcRewOriginal180>)result.getResObject();
				FirCtbcRewFix180 firCtbcRewFix180;
				String rawData;
				
				params = new HashMap();
				result = rfrcodeService.findRfrcodeByParams(params);
				Map<String,Rfrcode> mapRfrCode = new HashMap<String,Rfrcode>();
				if(result != null && result.getResObject() != null) {
					List<Rfrcode> searchResultRfrcode = (List<Rfrcode>)result.getResObject();
					for(Rfrcode rfrcode : searchResultRfrcode) {
						mapRfrCode.put(rfrcode.getCodetype(), rfrcode);
						mapRfrCode.put(rfrcode.getCodetype() + "," + rfrcode.getCodecode(), rfrcode);
						mapRfrCode.put(rfrcode.getCodetype() + "," + rfrcode.getCodecode()+","+rfrcode.getSource(), rfrcode);
					}
				}
				for(FirCtbcRewOriginal180 firCtbcRewOriginal180 : searchResult) {
					firCtbcRewFix180 = new FirCtbcRewFix180();
					firCtbcRewFix180.setBatchOid(batchOid);
					firCtbcRewFix180.setO180filename(firCtbcRewOriginal180.getO180filename());
					firCtbcRewFix180.setLinenum(firCtbcRewOriginal180.getLinenum());
					firCtbcRewFix180.setIcreate(userInfo.getUserId());
					firCtbcRewFix180.setDcreate(new Date());
					
					rawData = "";
					if(firCtbcRewOriginal180.getRowdata() != null)
						rawData = firCtbcRewOriginal180.getRowdata();
					
					firCtbcRewFix180 = this.mappingFirCtbcRewFix180(firCtbcRewFix180, rawData, mapRfrCode);
					this.mappingAndInsertFirCtbcRewSnn(firCtbcRewFix180);//新增FirCtbcRewSnn
					firCtbcRewFix180Service.insertFirCtbcRewFix180(firCtbcRewFix180);
				}
				
				/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start
				 * 比對ID相同姓名不同或姓名相同ID不同保單號*/
				params = new HashMap<>();
				params.put("batchOid", batchOid);
				result = firCtbcRewSnnService.findCtbcRewSnnForSameId(params);
				if(result.getResObject()!=null) {
					List<FirCtbcRewSnn> resultList = (List<FirCtbcRewSnn>) result.getResObject();
					for(int i=0;i<resultList.size();i++) {
						params.put("o180filename", resultList.get(i).getO180filename());
						params.put("linenum", resultList.get(i).getLinenum());
						result = firCtbcRewFix180Service.findFirCtbcRewFix180ByPK(params);
						if(result.getResObject()!=null) {
							firCtbcRewFix180 = (FirCtbcRewFix180)result.getResObject();
							firCtbcRewFix180.setErrormes((firCtbcRewFix180.getErrormes()==null?"":firCtbcRewFix180.getErrormes())+"ID相同但姓名不同("+firCtbcRewFix180.getPolicynumber()+"),");
							firCtbcRewFix180Service.updateFirCtbcRewFix180(firCtbcRewFix180);
						}
					}
				}
				
				result = firCtbcRewSnnService.findCtbcRewSnnForSameName(params);
				if(result.getResObject()!=null) {
					List<FirCtbcRewSnn> resultList = (List<FirCtbcRewSnn>) result.getResObject();
					for(int i=0;i<resultList.size();i++) {
						params.put("o180filename", resultList.get(i).getO180filename());
						params.put("linenum", resultList.get(i).getLinenum());
						result = firCtbcRewFix180Service.findFirCtbcRewFix180ByPK(params);
						if(result.getResObject()!=null) {
							firCtbcRewFix180 = (FirCtbcRewFix180)result.getResObject();
							firCtbcRewFix180.setErrormes((firCtbcRewFix180.getErrormes()==null?"":firCtbcRewFix180.getErrormes())+"姓名相同但ID不同("+firCtbcRewFix180.getPolicynumber()+"),");
							firCtbcRewFix180Service.updateFirCtbcRewFix180(firCtbcRewFix180);
						}
					}
				}
				/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end */
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public void insertCompareData(FirCtbcRewMatchLog firCtbcRewMatchLog1, FirCtbcRewMatchLog firCtbcRewMatchLog2,
			FirCtbcRewMatchLog firCtbcRewMatchLog3, UserInfo userInfo) throws SystemException, Exception {
		
		this.firCtbcRewMatchLogService.insertFirCtbcRewMatchLog(firCtbcRewMatchLog1);
		this.firCtbcRewMatchLogService.insertFirCtbcRewMatchLog(firCtbcRewMatchLog2);
		this.firCtbcRewMatchLogService.insertFirCtbcRewMatchLog(firCtbcRewMatchLog3);
		
	}
	
	@Override
	public void insertCompareData(FirCtbcRewMatchname firCtbcRewMatchname1, FirCtbcRewMatchname firCtbcRewMatchname2,
			FirCtbcRewMatchname firCtbcRewMatchname3, UserInfo userInfo) throws SystemException, Exception {
		
		this.firCtbcRewMatchnameService.insertFirCtbcRewMatchname(firCtbcRewMatchname1);
		this.firCtbcRewMatchnameService.insertFirCtbcRewMatchname(firCtbcRewMatchname2);
		this.firCtbcRewMatchnameService.insertFirCtbcRewMatchname(firCtbcRewMatchname3);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result updateFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180, UserInfo userInfo)
			throws SystemException, Exception {
		Result result = new Result();
		try {
			Map params = new HashMap();
			Result resultTemp = rfrcodeService.findRfrcodeByParams(params);
			Map<String,Rfrcode> mapRfrCode = new HashMap<String,Rfrcode>();
			if(resultTemp != null && resultTemp.getResObject() != null) {
				List<Rfrcode> searchResultRfrcode = (List<Rfrcode>)resultTemp.getResObject();
				for(Rfrcode rfrcode : searchResultRfrcode) {
					mapRfrCode.put(rfrcode.getCodetype(), rfrcode);
					mapRfrCode.put(rfrcode.getCodetype() + "," + rfrcode.getCodecode(), rfrcode);
					mapRfrCode.put(rfrcode.getCodetype() + "," + rfrcode.getCodecode()+","+rfrcode.getSource(), rfrcode);
				}
			}
			
			//取得建議保額----START
			BigDecimal[] arrSugAmt = this.calSugAmt(firCtbcRewFix180, mapRfrCode);
			
			BigDecimal sugAmt = arrSugAmt[0];
			BigDecimal sugAmt1 = arrSugAmt[1];

			//BJ016:20191202將住火險建議保額存到物件中，出excel的時候需要用到
			firCtbcRewFix180.setFiresuggestamount(sugAmt.longValue());
			//取得建議保額----END

			StringBuffer sbErrorMsg = this.dataCheck(firCtbcRewFix180, sugAmt, sugAmt1);
			
			if(sbErrorMsg.length() > 0){
				firCtbcRewFix180.setErrormes(sbErrorMsg.toString());
			}
			
			result = this.firCtbcRewFix180Service.updateFirCtbcRewFix180(firCtbcRewFix180);
		}catch(Exception e) {
			e.printStackTrace();
			result.setMessage(Message.getMessage(e.getMessage()));
			result.setResObject(firCtbcRewFix180);
		}
		return result;
	}
	
	public static void main(String args[]) throws Exception{
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FirCtbcRewFix180 mappingFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180, String rawData, Map<String,Rfrcode> mapRfrCode) throws Exception{
		if(rawData == null) return null;
		String[] strCibArray = rawData.split(",", 168); //設定欄位數量
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		firCtbcRewFix180.setStartdate(trimBlank(strCibArray[0])); // 8
		
		long longNum = StringUtil.stringToLong(trimBlank(strCibArray[1]));
		firCtbcRewFix180.setTotalpremiums(longNum);
		
		firCtbcRewFix180.setPersonid(trimBlank(strCibArray[2]));
		firCtbcRewFix180.setProcessdata(trimBlank(strCibArray[3]));
		firCtbcRewFix180.setFilingdate(trimBlank(strCibArray[4]));
		firCtbcRewFix180.setPolicynumber(trimBlank(strCibArray[5]));
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[15]));
		firCtbcRewFix180.setCol6(longNum);
		
		BigDecimal bdNum = StringUtil.stringToBigDecimal(trimBlank(strCibArray[7]));
		firCtbcRewFix180.setRates(bdNum);
		
		firCtbcRewFix180.setEnddate(trimBlank(strCibArray[9]));
		firCtbcRewFix180.setBranchcode(trimBlank(strCibArray[10]));
		firCtbcRewFix180.setBuildinglevelcode(trimBlank(strCibArray[11]));
		firCtbcRewFix180.setBuildinglevel(trimBlank(strCibArray[12]));
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[13]));
		firCtbcRewFix180.setPremium(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[14]));
		firCtbcRewFix180.setPremium1(longNum);
		
		bdNum = StringUtil.stringToBigDecimal(trimBlank(strCibArray[15]));
		firCtbcRewFix180.setAmount(bdNum);
		
		bdNum = StringUtil.stringToBigDecimal(trimBlank(strCibArray[16]));
		firCtbcRewFix180.setAmount1(bdNum);
		
		bdNum = StringUtil.stringToBigDecimal(trimBlank(strCibArray[17]));
		firCtbcRewFix180.setShortfactors(bdNum);
		
		firCtbcRewFix180.setPropertycode(trimBlank(strCibArray[18]));
		
		//20211227:BJ016:作舊五都轉換成新五都的動作----START
		firCtbcRewFix180.setMailingaddress(this.mappingOldCityToNewCity(trimBlank(strCibArray[19])));
		firCtbcRewFix180.setAddress(this.mappingOldCityToNewCity(trimBlank(strCibArray[20])));
		//20211227:BJ016:作舊五都轉換成新五都的動作----END
		
		firCtbcRewFix180.setBuildingsarea(trimBlank(strCibArray[21]));
		firCtbcRewFix180.setEarrates(trimBlank(strCibArray[22]));
		firCtbcRewFix180.setPeriod(trimBlank(strCibArray[23]));
		firCtbcRewFix180.setSumfloors(trimBlank(strCibArray[24]));
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[25]));
		firCtbcRewFix180.setLoanamount(longNum);
		
		firCtbcRewFix180.setAreacode(trimBlank(strCibArray[26]));
		firCtbcRewFix180.setHandlertype(trimBlank(strCibArray[27]));
		firCtbcRewFix180.setEmpcode(trimBlank(strCibArray[28]));
		// 20180709 bh061 將字串前的0去除 start
		String buildYearsStr = trimBlank(strCibArray[29]);
		int buildYearsInt = Integer.parseInt(buildYearsStr);//轉成數字(去除0)
		firCtbcRewFix180.setBuildyears(String.valueOf(buildYearsInt));
		// 20180709 bh061 將字串前的0去除 end
		firCtbcRewFix180.setRemark(trimBlank(strCibArray[30]));
		firCtbcRewFix180.setHolderid(trimBlank(strCibArray[31]));
		firCtbcRewFix180.setOwnername(trimBlank(strCibArray[32]));
		firCtbcRewFix180.setHolderid2(trimBlank(strCibArray[33]));
		firCtbcRewFix180.setOwnername2(trimBlank(strCibArray[34]));
		firCtbcRewFix180.setHolderid3(trimBlank(strCibArray[35]));
		firCtbcRewFix180.setOwnername3(trimBlank(strCibArray[36]));
		firCtbcRewFix180.setHolderid4(trimBlank(strCibArray[37]));
		firCtbcRewFix180.setOwnername4(trimBlank(strCibArray[38]));
		firCtbcRewFix180.setHolderid5(trimBlank(strCibArray[39]));
		firCtbcRewFix180.setOwnername5(trimBlank(strCibArray[40]));
		firCtbcRewFix180.setHolderid6(trimBlank(strCibArray[41]));
		firCtbcRewFix180.setOwnername6(trimBlank(strCibArray[42]));
		firCtbcRewFix180.setIdentifynumber(trimBlank(strCibArray[43]));
		firCtbcRewFix180.setInsuredname(trimBlank(strCibArray[44]));
		firCtbcRewFix180.setIdentifynumber2(trimBlank(strCibArray[45]));
		firCtbcRewFix180.setInsuredname2(trimBlank(strCibArray[46]));
		firCtbcRewFix180.setIdentifynumber3(trimBlank(strCibArray[47]));
		firCtbcRewFix180.setInsuredname3(trimBlank(strCibArray[48]));
		firCtbcRewFix180.setIdentifynumber4(trimBlank(strCibArray[49]));
		firCtbcRewFix180.setInsuredname4(trimBlank(strCibArray[50]));
		firCtbcRewFix180.setIdentifynumber5(trimBlank(strCibArray[51]));
		firCtbcRewFix180.setInsuredname5(trimBlank(strCibArray[52]));
		firCtbcRewFix180.setIdentifynumber6(trimBlank(strCibArray[53]));
		firCtbcRewFix180.setInsuredname6(trimBlank(strCibArray[54]));
		
		// bh061 1712130037 AML 新增欄位 start
		// 被保人1
		firCtbcRewFix180.setOwnerbirthday(trimBlank(strCibArray[57]));
		firCtbcRewFix180.setOwnernationality(trimBlank(strCibArray[58]));
		firCtbcRewFix180.setOwneroccupationcode(trimBlank(strCibArray[59]));
		firCtbcRewFix180.setOwneroccupationname(trimBlank(strCibArray[60]));
		firCtbcRewFix180.setOwnerislistedcompany(trimBlank(strCibArray[61]));
		firCtbcRewFix180.setOwnerisissuedshare(trimBlank(strCibArray[62]));
		firCtbcRewFix180.setOwnerphonenumber(trimBlank(strCibArray[63]));

		// 被保人2
		firCtbcRewFix180.setOwnerbirthday2(trimBlank(strCibArray[64]));
		firCtbcRewFix180.setOwnernationality2(trimBlank(strCibArray[65]));
		firCtbcRewFix180.setOwneroccupationcode2(trimBlank(strCibArray[66]));
		firCtbcRewFix180.setOwneroccupationname2(trimBlank(strCibArray[67]));
		firCtbcRewFix180.setOwnerislistedcompany2(trimBlank(strCibArray[68]));
		firCtbcRewFix180.setOwnerisissuedshare2(trimBlank(strCibArray[69]));
		firCtbcRewFix180.setOwnerphonenumber2(trimBlank(strCibArray[70]));

		// 被保人3
		firCtbcRewFix180.setOwnerbirthday3(trimBlank(strCibArray[71]));
		firCtbcRewFix180.setOwnernationality3(trimBlank(strCibArray[72]));
		firCtbcRewFix180.setOwneroccupationcode3(trimBlank(strCibArray[73]));
		firCtbcRewFix180.setOwneroccupationname3(trimBlank(strCibArray[74]));
		firCtbcRewFix180.setOwnerislistedcompany3(trimBlank(strCibArray[75]));
		firCtbcRewFix180.setOwnerisissuedshare3(trimBlank(strCibArray[76]));
		firCtbcRewFix180.setOwnerphonenumber3(trimBlank(strCibArray[77]));

		// 被保人4
		firCtbcRewFix180.setOwnerbirthday4(trimBlank(strCibArray[78]));
		firCtbcRewFix180.setOwnernationality4(trimBlank(strCibArray[79]));
		firCtbcRewFix180.setOwneroccupationcode4(trimBlank(strCibArray[80]));
		firCtbcRewFix180.setOwneroccupationname4(trimBlank(strCibArray[81]));
		firCtbcRewFix180.setOwnerislistedcompany4(trimBlank(strCibArray[82]));
		firCtbcRewFix180.setOwnerisissuedshare4(trimBlank(strCibArray[83]));
		firCtbcRewFix180.setOwnerphonenumber4(trimBlank(strCibArray[84]));

		// 被保人5
		firCtbcRewFix180.setOwnerbirthday5(trimBlank(strCibArray[85]));
		firCtbcRewFix180.setOwnernationality5(trimBlank(strCibArray[86]));
		firCtbcRewFix180.setOwneroccupationcode5(trimBlank(strCibArray[87]));
		firCtbcRewFix180.setOwneroccupationname5(trimBlank(strCibArray[88]));
		firCtbcRewFix180.setOwnerislistedcompany5(trimBlank(strCibArray[89]));
		firCtbcRewFix180.setOwnerisissuedshare5(trimBlank(strCibArray[90]));
		firCtbcRewFix180.setOwnerphonenumber5(trimBlank(strCibArray[91]));

		// 被保人6
		firCtbcRewFix180.setOwnerbirthday6(trimBlank(strCibArray[92]));
		firCtbcRewFix180.setOwnernationality6(trimBlank(strCibArray[93]));
		firCtbcRewFix180.setOwneroccupationcode6(trimBlank(strCibArray[94]));
		firCtbcRewFix180.setOwneroccupationname6(trimBlank(strCibArray[95]));
		firCtbcRewFix180.setOwnerislistedcompany6(trimBlank(strCibArray[96]));
		firCtbcRewFix180.setOwnerisissuedshare6(trimBlank(strCibArray[97]));
		firCtbcRewFix180.setOwnerphonenumber6(trimBlank(strCibArray[98]));

		// 要保人1
		firCtbcRewFix180.setBirthday(trimBlank(strCibArray[99]));
		firCtbcRewFix180.setNationality(trimBlank(strCibArray[100]));
		firCtbcRewFix180.setOccupationcode(trimBlank(strCibArray[101]));
		firCtbcRewFix180.setOccupationname(trimBlank(strCibArray[102]));
		firCtbcRewFix180.setIslistedcompany(trimBlank(strCibArray[103]));
		firCtbcRewFix180.setIsissuedshare(trimBlank(strCibArray[104]));
		firCtbcRewFix180.setPhonenumber(trimBlank(strCibArray[105]));

		// 要保人2
		firCtbcRewFix180.setBirthday2(trimBlank(strCibArray[106]));
		firCtbcRewFix180.setNationality2(trimBlank(strCibArray[107]));
		firCtbcRewFix180.setOccupationcode2(trimBlank(strCibArray[108]));
		firCtbcRewFix180.setOccupationname2(trimBlank(strCibArray[109]));
		firCtbcRewFix180.setIslistedcompany2(trimBlank(strCibArray[110]));
		firCtbcRewFix180.setIsissuedshare2(trimBlank(strCibArray[111]));
		firCtbcRewFix180.setPhonenumber2(trimBlank(strCibArray[112]));

		// 要保人3
		firCtbcRewFix180.setBirthday3(trimBlank(strCibArray[113]));
		firCtbcRewFix180.setNationality3(trimBlank(strCibArray[114]));
		firCtbcRewFix180.setOccupationcode3(trimBlank(strCibArray[115]));
		firCtbcRewFix180.setOccupationname3(trimBlank(strCibArray[116]));
		firCtbcRewFix180.setIslistedcompany3(trimBlank(strCibArray[117]));
		firCtbcRewFix180.setIsissuedshare3(trimBlank(strCibArray[118]));
		firCtbcRewFix180.setPhonenumber3(trimBlank(strCibArray[119]));

		// 要保人4
		firCtbcRewFix180.setBirthday4(trimBlank(strCibArray[120]));
		firCtbcRewFix180.setNationality4(trimBlank(strCibArray[121]));
		firCtbcRewFix180.setOccupationcode4(trimBlank(strCibArray[122]));
		firCtbcRewFix180.setOccupationname4(trimBlank(strCibArray[123]));
		firCtbcRewFix180.setIslistedcompany4(trimBlank(strCibArray[124]));
		firCtbcRewFix180.setIsissuedshare4(trimBlank(strCibArray[125]));
		firCtbcRewFix180.setPhonenumber4(trimBlank(strCibArray[126]));

		// 要保人5
		firCtbcRewFix180.setBirthday5(trimBlank(strCibArray[127]));
		firCtbcRewFix180.setNationality5(trimBlank(strCibArray[128]));
		firCtbcRewFix180.setOccupationcode5(trimBlank(strCibArray[129]));
		firCtbcRewFix180.setOccupationname5(trimBlank(strCibArray[130]));
		firCtbcRewFix180.setIslistedcompany5(trimBlank(strCibArray[131]));
		firCtbcRewFix180.setIsissuedshare5(trimBlank(strCibArray[132]));
		firCtbcRewFix180.setPhonenumber5(trimBlank(strCibArray[133]));

		// 要保人6
		firCtbcRewFix180.setBirthday6(trimBlank(strCibArray[134]));
		firCtbcRewFix180.setNationality6(trimBlank(strCibArray[135]));
		firCtbcRewFix180.setOccupationcode6(trimBlank(strCibArray[136]));
		firCtbcRewFix180.setOccupationname6(trimBlank(strCibArray[137]));
		firCtbcRewFix180.setIslistedcompany6(trimBlank(strCibArray[138]));
		firCtbcRewFix180.setIsissuedshare6(trimBlank(strCibArray[139]));
		firCtbcRewFix180.setPhonenumber6(trimBlank(strCibArray[140]));
		
		/**
		 * 20190819
		 * BJ016 新增欄位 start
		 * */
		// 被保人7
		firCtbcRewFix180.setHolderid7(trimBlank(strCibArray[141]));
		firCtbcRewFix180.setOwnername7(trimBlank(strCibArray[142]));
		firCtbcRewFix180.setOwnerbirthday7(trimBlank(strCibArray[143]));
		firCtbcRewFix180.setOwnernationality7(trimBlank(strCibArray[144]));
		firCtbcRewFix180.setOwneroccupationcode7(trimBlank(strCibArray[145]));
		firCtbcRewFix180.setOwneroccupationname7(trimBlank(strCibArray[146]));
		firCtbcRewFix180.setOwnerislistedcompany7(trimBlank(strCibArray[147]));
		firCtbcRewFix180.setOwnerisissuedshare7(trimBlank(strCibArray[148]));
		firCtbcRewFix180.setOwnerphonenumber7(trimBlank(strCibArray[149]));
		
		// 要保人7
		firCtbcRewFix180.setIdentifynumber7(trimBlank(strCibArray[150]));
		firCtbcRewFix180.setInsuredname7(trimBlank(strCibArray[151]));
		firCtbcRewFix180.setBirthday7(trimBlank(strCibArray[152]));
		firCtbcRewFix180.setNationality7(trimBlank(strCibArray[153]));
		firCtbcRewFix180.setOccupationcode7(trimBlank(strCibArray[154]));
		firCtbcRewFix180.setOccupationname7(trimBlank(strCibArray[155]));
		firCtbcRewFix180.setIslistedcompany7(trimBlank(strCibArray[156]));
		firCtbcRewFix180.setIsissuedshare7(trimBlank(strCibArray[157]));
		firCtbcRewFix180.setPhonenumber7(trimBlank(strCibArray[158]));
		
		//保額/通知類別
		firCtbcRewFix180.setNoticetype(trimBlank(strCibArray[159]));
		/**
		 * 20190819
		 * BJ016 新增欄位 end
		 * */
		
		/**
		 * 20191226
		 * 中信只有續保通知轉檔才有加這以下8個欄位
		 * */
		/**
		 * 20191128
		 * BJ016 新增8欄位 start
		 * */
		firCtbcRewFix180.setInscocode(trimBlank(strCibArray[160]));
		firCtbcRewFix180.setOldpolicyno(trimBlank(strCibArray[161]));
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[162]));
		firCtbcRewFix180.setFireamt(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[163]));
		firCtbcRewFix180.setQuakeamt(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[164]));
		firCtbcRewFix180.setFirequakeamt(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[165]));
		firCtbcRewFix180.setFireprem(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[166]));
		firCtbcRewFix180.setQuakeprem(longNum);
		
		longNum = StringUtil.stringToLong(trimBlank(strCibArray[167]));
		firCtbcRewFix180.setTtlpremium(longNum);
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		/**
		 * 20191128
		 * BJ016 新增8欄位 end
		 * */
		StringBuffer sbErrorMsg = new StringBuffer();
		//新件有的檢查，我這邊也帶進來，如果用不到再刪除-----START
//		if (null != Handler1MappingUtil.getHandler1Code(strCibArray[57].substring(0, 10))) {
//			//服務人員
//			String handler1Code = Handler1MappingUtil.getHandler1Code(strCibArray[57].substring(0, 10)).getHandler1Code();
//			firCtbcRewFix180.setHandler1Code(handler1Code);
//			if (handler1Code == null || handler1Code.equals("")) {
//				sbErrorMsg.append("服務人員比對:無此服務人員代碼,");
//			}
//			//歸屬單位
//			String comCode = Handler1MappingUtil.getHandler1Code(strCibArray[57].substring(0, 10)).getComCode();
//			firCtbcRewFix180.setComCode(comCode);
//			if (comCode == null || comCode.equals("")) {
//				sbErrorMsg.append("服務人員比對:歸屬單位比對錯誤,");
//			}
//		} else {
//			sbErrorMsg.append("服務人員比對:無此服務人員id,");
//		}
//
//		//業務員證號
//		if (firCtbcRewFix180.getHandlerCode() == null || firCtbcRewFix180.getHandlerCode().equals("")) {
//			sbErrorMsg.append("業務員檢核:業務員證號空白,");
//		}
		//新件有的檢查，我這邊也帶進來，如果用不到再刪除-----END
		
		// 國家代碼-被保人
		String countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality());
		firCtbcRewFix180.setOwnernationality(countryCodeOwner);

		if (firCtbcRewFix180.getHolderid2() != null && !firCtbcRewFix180.getHolderid2().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality2());
			firCtbcRewFix180.setOwnernationality2(countryCodeOwner);
		}
		if (firCtbcRewFix180.getHolderid3() != null && !firCtbcRewFix180.getHolderid3().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality3());
			firCtbcRewFix180.setOwnernationality3(countryCodeOwner);
		}
		if (firCtbcRewFix180.getHolderid4() != null && !firCtbcRewFix180.getHolderid4().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality4());
			firCtbcRewFix180.setOwnernationality4(countryCodeOwner);
		}
		if (firCtbcRewFix180.getHolderid5() != null && !firCtbcRewFix180.getHolderid5().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality5());
			firCtbcRewFix180.setOwnernationality5(countryCodeOwner);
		}
		if (firCtbcRewFix180.getHolderid6() != null && !firCtbcRewFix180.getHolderid6().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality6());
			firCtbcRewFix180.setOwnernationality6(countryCodeOwner);
		}
		if (firCtbcRewFix180.getHolderid7() != null && !firCtbcRewFix180.getHolderid7().equals("")) {
			countryCodeOwner = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getOwnernationality7());
			firCtbcRewFix180.setOwnernationality7(countryCodeOwner);
		}
		// 國家代碼-要保人
		String countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality());
		firCtbcRewFix180.setNationality(countryCodeInsured);
		
		if (firCtbcRewFix180.getIdentifynumber2() != null && !firCtbcRewFix180.getIdentifynumber2().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality2());
			firCtbcRewFix180.setNationality2(countryCodeInsured);
		}
		if (firCtbcRewFix180.getIdentifynumber3() != null && !firCtbcRewFix180.getIdentifynumber3().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality3());
			firCtbcRewFix180.setNationality3(countryCodeInsured);
		}
		if (firCtbcRewFix180.getIdentifynumber4() != null && !firCtbcRewFix180.getIdentifynumber4().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality4());
			firCtbcRewFix180.setNationality4(countryCodeInsured);
		}
		if (firCtbcRewFix180.getIdentifynumber5() != null && !firCtbcRewFix180.getIdentifynumber5().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality5());
			firCtbcRewFix180.setNationality5(countryCodeInsured);
		}
		if (firCtbcRewFix180.getIdentifynumber6() != null && !firCtbcRewFix180.getIdentifynumber6().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality6());
			firCtbcRewFix180.setNationality6(countryCodeInsured);
		}
		if (firCtbcRewFix180.getIdentifynumber7() != null && !firCtbcRewFix180.getIdentifynumber7().equals("")) {
			countryCodeInsured = CountryMappingUtil.getCountryCode(firCtbcRewFix180.getNationality7());
			firCtbcRewFix180.setNationality7(countryCodeInsured);
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		String buildMaterial = trimBlank(strCibArray[12]);
		firCtbcRewFix180.setRoof(getRoof(buildMaterial)); // 建築物屋頂
		firCtbcRewFix180.setMaterial(getMaterial(buildMaterial)); // 建築物建材

		if (strCibArray.length > 55) {
			//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
			firCtbcRewFix180.setAutorenew(trimBlank(strCibArray[56]));// 自動續保註記
		}

		//去除地址中的全形空白 (例如：台中市南　區)
		int spaceInt1 = firCtbcRewFix180.getAddress().indexOf("　");
		if (spaceInt1 > 0) {
			firCtbcRewFix180.setAddress(firCtbcRewFix180.getAddress().substring(0, spaceInt1) + firCtbcRewFix180.getAddress().substring(spaceInt1 + 1));
		}
		int spaceInt2 = firCtbcRewFix180.getMailingaddress().indexOf("　");
		if (spaceInt2 > 0) {
			firCtbcRewFix180.setMailingaddress(firCtbcRewFix180.getMailingaddress().substring(0, spaceInt2) + firCtbcRewFix180.getMailingaddress().substring(spaceInt2 + 1));
		}
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		firCtbcRewFix180.setAreacode(trimBlank(strCibArray[26]));

		//取得建議保額----START
		BigDecimal[] arrSugAmt = this.calSugAmt(firCtbcRewFix180, mapRfrCode);
		
		BigDecimal sugAmt = arrSugAmt[0];
		BigDecimal sugAmt1 = arrSugAmt[1];

		//BJ016:20191202將住火險建議保額存到物件中，出excel的時候需要用到
		firCtbcRewFix180.setFiresuggestamount(sugAmt.longValue());
		//取得建議保額----END

		sbErrorMsg = this.dataCheck(firCtbcRewFix180, sugAmt, sugAmt1);
		
		if(sbErrorMsg.length() > 0){
			firCtbcRewFix180.setErrormes(sbErrorMsg.toString());
		}

		return firCtbcRewFix180;
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	//去除前後所有空白，包括全行、半形
	private String trimBlank(String str){
		if(null != str && str.trim().length()>0) {
			return str.replaceAll("^[\\u3000|\\s]*", "").replaceAll("[\\u3000|\\s]*$", "");
		}
		return "";
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
	
	private String getRoof(String roofName) {
		if (roofName.contains("平屋頂")) {
			return "50";
		} else if (roofName.contains("瓦屋頂")) {
			return "51";
		} else if (roofName.contains("石棉板屋頂")) {
			return "52";
		//mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整
		} else if (roofName.contains("鐵皮屋頂")) {
			return "53";
		} else if (roofName.contains("塑膠屋頂")) {
			return "54";
		} else if (roofName.contains("油毛氈屋頂")) {
			return "55";
		} else if (roofName.contains("平、瓦屋頂")) {
			return "56";
		} else if (roofName.contains("石棉瓦屋頂")) {
			return "57";
		} else if (roofName.contains("金屬鐵皮屋頂")) {
			return "58";
		} else if (roofName.contains("烤漆板屋頂")) {
			return "59";
		} else if (roofName.contains("烤漆鋼板屋頂")) {
			return "60";
		} else if (roofName.contains("玻璃纖維屋頂")) {
			return "61";
		} else if (roofName.contains("木屋頂")) {
			return "62";
		} else if (roofName.contains("詳附圖")) {
			return "98";
		} else if (roofName.contains("略")) {
			return "99";
		} else {
			return "X";
		}
	}
	
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 start*/
	private String getMaterial(String roofName) {
		if (roofName.contains("鋼筋混凝土造") && !roofName.contains("壁式預鑄鋼筋混凝土造") && !roofName.contains("鋼骨鋼筋混凝土造")) {
			return "03";
		} else if (roofName.contains("磚造") && !roofName.contains("加強磚造") && !roofName.contains("鋼筋混凝土加強磚造")) {
			return "04";
		} else if ((roofName.contains("加強磚造") || roofName.contains("加強磚")) && !roofName.contains("鋼筋混凝土加強磚造")) {
			return "06";
		} else if (roofName.contains("木造") && !roofName.contains("土木造")) {
			return "12";
		} else if (roofName.contains("鐵造")) {
			return "13";
		} else if (roofName.contains("鋼骨鋼筋混凝土造") || roofName.contains("鋼骨鋼筋混凝土") ) {
			return "28";
		} else if (roofName.contains("石造") && !roofName.contains("土石造")) {
			return "29";
		} else if (roofName.contains("鋼筋混凝土加強磚造")) {
			return "30";
		} else if (roofName.contains("土木造")) {
			return "33";
		} else if (roofName.contains("鋼筋混凝土加強空心磚造")) {
			return "36";
		} else if (roofName.contains("鋼骨結構") || roofName.contains("鋼骨混凝土造")) {
			return "39";
		} else if (roofName.contains("混擬土造") && !roofName.contains("壁式預鑄鋼筋混凝土造") 
				&& !roofName.contains("預力混擬土造") && !roofName.contains("鋼筋混凝土造")) {
			return "40";
		} else if (roofName.contains("預力混擬土造")) {
			return "41";
		} else if (roofName.contains("壁式預鑄鋼筋混凝土造")) {
			return "42";
		} else if (roofName.contains("土造") && !roofName.contains("混擬土造") && !roofName.contains("壁式預鑄鋼筋混凝土造") 
				&& !roofName.contains("鋼筋混凝土造")&& !roofName.contains("預力混擬土造") && !roofName.contains("鋼骨混凝土造")) {
			return "AB";
		} else if (roofName.contains("土石造")) {
			return "AC";
		} else if (roofName.contains("土磚石混合造")) {
			return "AD";
		} else if (roofName.contains("竹造")) {
			return "AE";
		}else if (roofName.contains("鋁架造")) {
			return "AF";
		} else {
			return "X";
		}
	}
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 end*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private StringBuffer dataCheck(FirCtbcRewFix180 firCtbcRewFix180, BigDecimal sugAmt, BigDecimal sugAmt1) throws Exception{
		StringBuffer sbErrorMsg = new StringBuffer();

		if (firCtbcRewFix180.getOwnernationality() == null || "".equals(firCtbcRewFix180.getOwnernationality())) {
			sbErrorMsg.append("被保人1國籍比對:國家代碼比對錯誤,");
		}
		
		/**
		 * 20200817：BJ016
		 * issue:FIR0199
		 * 新增檢核ID是否正確
		 * 及如果ID不為空姓名也不得為空
		 * ----START
		 * */
		String verifyID_host = configUtil.getString("verifyIDUrl");
		CwpUtil cwpUtil = new CwpUtil(verifyID_host);
		List<String> idList = new ArrayList<String>();//難字查詢用
		
		if(firCtbcRewFix180.getHolderid() == null || firCtbcRewFix180.getHolderid().trim().length() <= 0){
			sbErrorMsg.append("被保險人1的ID不得為空,");
		}else{
			idList.add(firCtbcRewFix180.getHolderid());
			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid())){
				sbErrorMsg.append("被保險人1的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername() == null || firCtbcRewFix180.getOwnername().trim().length() <= 0){
				sbErrorMsg.append("被保險人1姓名不得為空,");
			}
		}
		if (firCtbcRewFix180.getHolderid2() != null && !firCtbcRewFix180.getHolderid2().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid2());
			
			if (firCtbcRewFix180.getOwnernationality2() == null || "".equals(firCtbcRewFix180.getOwnernationality2())) {
				sbErrorMsg.append("被保人2國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid2())){
				sbErrorMsg.append("被保險人2的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername2() == null || firCtbcRewFix180.getOwnername2().trim().length() <= 0){
				sbErrorMsg.append("被保險人2姓名不得為空,");
			}
		}
		if (firCtbcRewFix180.getHolderid3() != null && !firCtbcRewFix180.getHolderid3().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid3());
			
			if (firCtbcRewFix180.getOwnernationality3() == null || "".equals(firCtbcRewFix180.getOwnernationality3())) {
				sbErrorMsg.append("被保人3國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid3())){
				sbErrorMsg.append("被保險人3的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername3() == null || firCtbcRewFix180.getOwnername3().trim().length() <= 0){
				sbErrorMsg.append("被保險人3姓名不得為空,");
			}
		}
		if (firCtbcRewFix180.getHolderid4() != null && !firCtbcRewFix180.getHolderid4().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid4());

			if (firCtbcRewFix180.getOwnernationality4() == null || "".equals(firCtbcRewFix180.getOwnernationality4())) {
				sbErrorMsg.append("被保人4國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid4())){
				sbErrorMsg.append("被保險人4的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername4() == null || firCtbcRewFix180.getOwnername4().trim().length() <= 0){
				sbErrorMsg.append("被保險人4姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getHolderid5() != null && !firCtbcRewFix180.getHolderid5().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid5());

			if (firCtbcRewFix180.getOwnernationality5() == null || "".equals(firCtbcRewFix180.getOwnernationality5())) {
				sbErrorMsg.append("被保人5國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid5())){
				sbErrorMsg.append("被保險人5的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername5() == null || firCtbcRewFix180.getOwnername5().trim().length() <= 0){
				sbErrorMsg.append("被保險人5姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getHolderid6() != null && !firCtbcRewFix180.getHolderid6().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid6());

			if (firCtbcRewFix180.getOwnernationality6() == null || "".equals(firCtbcRewFix180.getOwnernationality6())) {
				sbErrorMsg.append("被保人6國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid6())){
				sbErrorMsg.append("被保險人6的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername6() == null || firCtbcRewFix180.getOwnername6().trim().length() <= 0){
				sbErrorMsg.append("被保險人6姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getHolderid7() != null && !firCtbcRewFix180.getHolderid7().equals("")) {
			idList.add(firCtbcRewFix180.getHolderid7());

			if (firCtbcRewFix180.getOwnernationality7() == null || "".equals(firCtbcRewFix180.getOwnernationality7())) {
				sbErrorMsg.append("被保人7國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getHolderid7())){
				sbErrorMsg.append("被保險人7的ID異常,");
			}
			
			if(firCtbcRewFix180.getOwnername7() == null || firCtbcRewFix180.getOwnername7().trim().length() <= 0){
				sbErrorMsg.append("被保險人7姓名不得為空,");
			}

		}
		// 國家代碼-要保人
		if(firCtbcRewFix180.getNationality() == null || "".equals(firCtbcRewFix180.getNationality())) {
			sbErrorMsg.append("要保人1國籍比對:國家代碼比對錯誤,");
		}
		
		if(firCtbcRewFix180.getIdentifynumber() == null || firCtbcRewFix180.getIdentifynumber().trim().length() <= 0){
			sbErrorMsg.append("要保人1的ID不得為空,");
		}else{
			idList.add(firCtbcRewFix180.getIdentifynumber());
			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber())){
				sbErrorMsg.append("要保人1的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname() == null || firCtbcRewFix180.getInsuredname().trim().length() <= 0){
				sbErrorMsg.append("要保人1姓名不得為空,");
			}
		}
		if (firCtbcRewFix180.getIdentifynumber2() != null && !firCtbcRewFix180.getIdentifynumber2().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber2());

			if(firCtbcRewFix180.getNationality2() == null || "".equals(firCtbcRewFix180.getNationality2())) {
				sbErrorMsg.append("要保人2國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber2())){
				sbErrorMsg.append("要保人2的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname2() == null || firCtbcRewFix180.getInsuredname2().trim().length() <= 0){
				sbErrorMsg.append("要保人2姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getIdentifynumber3() != null && !firCtbcRewFix180.getIdentifynumber3().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber3());

			if(firCtbcRewFix180.getNationality3() == null || "".equals(firCtbcRewFix180.getNationality3())) {
				sbErrorMsg.append("要保人3國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber3())){
				sbErrorMsg.append("要保人3的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname3() == null || firCtbcRewFix180.getInsuredname3().trim().length() <= 0){
				sbErrorMsg.append("要保人3姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getIdentifynumber4() != null && !firCtbcRewFix180.getIdentifynumber4().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber4());

			if(firCtbcRewFix180.getNationality4() == null || "".equals(firCtbcRewFix180.getNationality4())) {
				sbErrorMsg.append("要保人4國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber4())){
				sbErrorMsg.append("要保人4的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname4() == null || firCtbcRewFix180.getInsuredname4().trim().length() <= 0){
				sbErrorMsg.append("要保人4姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getIdentifynumber5() != null && !firCtbcRewFix180.getIdentifynumber5().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber5());

			if(firCtbcRewFix180.getNationality5() == null || "".equals(firCtbcRewFix180.getNationality5())) {
				sbErrorMsg.append("要保人5國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber5())){
				sbErrorMsg.append("要保人5的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname5() == null || firCtbcRewFix180.getInsuredname5().trim().length() <= 0){
				sbErrorMsg.append("要保人5姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getIdentifynumber6() != null && !firCtbcRewFix180.getIdentifynumber6().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber6());

			if(firCtbcRewFix180.getNationality6() == null || "".equals(firCtbcRewFix180.getNationality6())) {
				sbErrorMsg.append("要保人6國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber6())){
				sbErrorMsg.append("要保人6的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname6() == null || firCtbcRewFix180.getInsuredname6().trim().length() <= 0){
				sbErrorMsg.append("要保人6姓名不得為空,");
			}

		}
		if (firCtbcRewFix180.getIdentifynumber7() != null && !firCtbcRewFix180.getIdentifynumber7().equals("")) {
			idList.add(firCtbcRewFix180.getIdentifynumber7());

			if(firCtbcRewFix180.getNationality7() == null || "".equals(firCtbcRewFix180.getNationality7())) {
				sbErrorMsg.append("要保人7國籍比對:國家代碼比對錯誤,");
			}

			if(!cwpUtil.verifyID(firCtbcRewFix180.getIdentifynumber7())){
				sbErrorMsg.append("要保人7的ID異常,");
			}
			
			if(firCtbcRewFix180.getInsuredname7() == null || firCtbcRewFix180.getInsuredname7().trim().length() <= 0){
				sbErrorMsg.append("要保人7姓名不得為空,");
			}

		}
		/**
		 * 20200817：BJ016
		 * issue:FIR0199
		 * 新增檢核ID是否正確
		 * 及如果ID不為空姓名也不得為空
		 * ----END
		 * */
		
		if (firCtbcRewFix180.getRoof().equals("X") || firCtbcRewFix180.getMaterial().equals("X")) {
			sbErrorMsg.append("建物構造屋頂:比對錯誤,");
		}
		
		if ("X".equals(firCtbcRewFix180.getRoof())) {
			sbErrorMsg.append("建物構造屋頂:比對錯誤，無法計算保額,");
		} else {

			logger.debug("sugAmt="+sugAmt);
			logger.debug("sugAmt1="+sugAmt1);
			sugAmt = sugAmt.setScale(-4, RoundingMode.UP);//建議保額無條件進位至萬元
			sugAmt1 = sugAmt1.setScale(-4, RoundingMode.UP);//建議保額無條件進位至萬元
			logger.debug("sugAmt="+sugAmt.intValue());
			logger.debug("sugAmt1="+sugAmt1.intValue());
			
			//住火保額檢核
			int i = sugAmt.compareTo(firCtbcRewFix180.getAmount());
			if (i == -1) {
				//超額
				BigDecimal sugPremium = sugAmt.multiply(firCtbcRewFix180.getRates());
				sugPremium = sugPremium.setScale(0, RoundingMode.HALF_UP);
				sbErrorMsg.append("住火超額:建議保額為" + sugAmt.intValue() + "元，保費為" + sugPremium.intValue() + "元" + ",");
			} else if (i == 1) {
				//不足額
				BigDecimal sugPremium = sugAmt.multiply(firCtbcRewFix180.getRates());
				sugPremium = sugPremium.setScale(0, RoundingMode.HALF_UP);
				sbErrorMsg.append("住火不足額:建議保額為" + sugAmt.intValue() + "元，保費為" + sugPremium.intValue() + "元" + ",");
			} else {
				//足額
			}
			
			//地震險保額檢核
			int j = sugAmt1.compareTo(firCtbcRewFix180.getAmount1());
			if (j == -1) {
				//超額
				BigDecimal sugPremium1 = sugAmt1.multiply(new BigDecimal("0.0009"));//earRate=900，1,500,000*0.0009=1350
				sugPremium1 = sugPremium1.setScale(0, RoundingMode.HALF_UP);
				sbErrorMsg.append("地震險超額:建議保額為" + sugAmt1.intValue() + "元，保費為" + sugPremium1.intValue() + "元" + ",");
			} else if (j == 1) {
				//不足額
				BigDecimal sugPremium1 = sugAmt1.multiply(new BigDecimal("0.0009"));//earRate=900，1,500,000*0.0009=1350
				sugPremium1 = sugPremium1.setScale(0, RoundingMode.HALF_UP);
				sbErrorMsg.append("地震險不足額:建議保額為" + sugAmt1.intValue() + "元，保費為" + sugPremium1.intValue() + "元" + ",");
			} else {
				//足額
			}
		}
		
		if(Double.valueOf(firCtbcRewFix180.getBuildingsarea())>300){
			sbErrorMsg.append("坪數超過300坪:坪數為" +firCtbcRewFix180.getBuildingsarea()+",");
		}
		
		/**
		 * 20200310
		 * BJ016
		 * 增加颱洪檢核
		 * 1.標的物地址位於：基隆市、宜蘭縣、花蓮縣、台(臺)東縣、屏東縣
		 * 2.建築等級為：3、5、6(頭等、二等、三等)
		 * 3.考量單保地震險，若火險保額為0無須檢核
		 * 4.訊息內容：本件為易淹水地區，請先查詢國家災害防救中心災害淺勢地圖。
		 * 
		 * 20200318
		 * BJ016
		 * 芳安及尚儒測試的資料中，有出現花蓮市的地址前面沒有加花蓮縣，導致沒有被檢核出來，
		 * 因此多加宜蘭市、花蓮市、台東市、屏東市的判斷。
		 * */
		if(BigDecimal.ZERO.compareTo(firCtbcRewFix180.getAmount()) != 0){
			if(firCtbcRewFix180.getAddress().contains("基隆市") || firCtbcRewFix180.getAddress().contains("宜蘭縣") || firCtbcRewFix180.getAddress().contains("宜蘭市") || firCtbcRewFix180.getAddress().contains("花蓮縣") || firCtbcRewFix180.getAddress().contains("花蓮市")
					 || firCtbcRewFix180.getAddress().contains("台東縣") || firCtbcRewFix180.getAddress().contains("台東市") || firCtbcRewFix180.getAddress().contains("臺東縣") || firCtbcRewFix180.getAddress().contains("臺東市") || firCtbcRewFix180.getAddress().contains("屏東縣") || firCtbcRewFix180.getAddress().contains("屏東市")){
				if("B1".equals(firCtbcRewFix180.getBuildinglevelcode()) || "B2".equals(firCtbcRewFix180.getBuildinglevelcode()) || "B3".equals(firCtbcRewFix180.getBuildinglevelcode())){
					sbErrorMsg.append("本件為易淹水地區，請先查詢國家災害防救中心災害淺勢地圖,");
				}
			}
		}
		
		/**
		 * 20201014
		 * BJ016
		 * FIR0213新增稽核議題檢核
		 * */
		try{//重新計算地震險保額
			FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
			firAddressRuleObj.setInsType("F02");
			firAddressRuleObj.setOperationType("P");
			firAddressRuleObj.setRulePrefix("FIR");
			firAddressRuleObj.setAddrRoof(firCtbcRewFix180.getRoof());
			firAddressRuleObj.setAddrStructure(BuildingGradeMappingUtil.getlv(firCtbcRewFix180.getBuildinglevelcode()));
			firAddressRuleObj.setAddrSumfloors(firCtbcRewFix180.getSumfloors());
			firAddressRuleObj.setAddrWall(firCtbcRewFix180.getMaterial());
			firAddressRuleObj.setAddress(firCtbcRewFix180.getAddress());
			firAddressRuleObj.setPostcode(firCtbcRewFix180.getAreacode());
			
			String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressRuleObj.class, firAddressRuleObj);
			
			String returnValue = clientRuleCheckService.ruleCheck(soapxml);
			byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
			String tmp = new String(decryptedByteArray, "UTF-8");
			System.out.println(tmp);
			RuleReponseVo ruleReponseVo = (RuleReponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, RuleReponseVo.class);
			if(ruleReponseVo != null) {
				if("0".equals(ruleReponseVo.getStatus())) {
					if(ruleReponseVo.getDetailList() != null && ruleReponseVo.getDetailList().size() > 0) {
						ArrayList<RuleReponseDetailVo> detailList = ruleReponseVo.getDetailList();
						for(RuleReponseDetailVo vo : detailList) {
							if("0".equals(vo.getRuleResult())) {
								sbErrorMsg.append(vo.getRuleMsg() + ";");
							}
						}
						detailList = null;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			sbErrorMsg.append("檢核-稽核議題檢核WS無回應或發生異常: Exception;");
		}
		
		//難字案件檢核-----START
		try{
			Map params = new HashMap();
			params.put("datatype", "1");
			params.put("owneridList", idList);
			int resultCount = this.firCtbcRewNoshowwordService.countByParams(params);
			if(resultCount > 0) {
				sbErrorMsg.append("姓名有難字;");
			}
			
			params = new HashMap();
			params.put("datatype", "2");
			params.put("ownerid", firCtbcRewFix180.getPolicynumber());
			resultCount = this.firCtbcRewNoshowwordService.countByParams(params);
			if(resultCount > 0) {
				sbErrorMsg.append("地址有難字;");
			}
		}catch(Exception e) {
			e.printStackTrace();
			sbErrorMsg.append("難字案件檢核發生異常: Exception;");
		}
		//難字案件檢核-----END
		
		if(sbErrorMsg.length() > 0) {
			return sbErrorMsg;
		}
		return sbErrorMsg;
	}
	
	private BigDecimal[] calSugAmt(FirCtbcRewFix180 firCtbcRewFix180, Map<String,Rfrcode> mapRfrCode) throws Exception {
		//取得建議保額----START
		BigDecimal[] returnValue = new BigDecimal[2];
		BigDecimal sugAmt;
		BigDecimal sugAmt1;
		String startDate = firCtbcRewFix180.getStartdate().replace("/", "");
		startDate = String.valueOf(Integer.valueOf(startDate)+19110000);//民國年月日轉成西元年月日
		logger.debug("startDate"+startDate);
		Map<String, BigDecimal> sugAmtMap = premiumCalService.calSugAmt(firCtbcRewFix180.getSumfloors(), firCtbcRewFix180.getAddress().substring(0, 3),
				firCtbcRewFix180.getBuildinglevelcode(), firCtbcRewFix180.getMaterial(), firCtbcRewFix180.getBuildingsarea(), startDate,mapRfrCode);
		sugAmt = sugAmtMap.get("suggestAmount");//住火險建議保額
		sugAmt1 = sugAmtMap.get("suggestAmount1");//地震險建議保額
		logger.debug("sugAmt="+sugAmt);
		logger.debug("sugAmt1="+sugAmt1);
		sugAmt = sugAmt.setScale(-4, RoundingMode.UP);//建議保額無條件進位至萬元
		
		//BJ016:20191202將住火險建議保額存到物件中，出excel的時候需要用到
		firCtbcRewFix180.setFiresuggestamount(sugAmt.longValue());
		
		sugAmt1 = sugAmt1.setScale(-4, RoundingMode.UP);//建議保額無條件進位至萬元
		logger.debug("sugAmt="+sugAmt.intValue());
		logger.debug("sugAmt1="+sugAmt1.intValue());
		
		returnValue[0] = sugAmt;
		returnValue[1] = sugAmt1;
		//取得建議保額----END
		return returnValue;
	}
	
	private void mappingAndInsertFirCtbcRewSnn(FirCtbcRewFix180 firCtbcRewFix180) {
		try {
			if(firCtbcRewFix180 != null) {
				Date now = new Date();
				for(int i = 1; i<=7; i++) {

					if(i == 1) {
						if(firCtbcRewFix180.getHolderid() != null && firCtbcRewFix180.getHolderid().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid(), firCtbcRewFix180.getOwnername(), firCtbcRewFix180.getOwnerbirthday(), 
									firCtbcRewFix180.getOwnerphonenumber(), firCtbcRewFix180.getOwnernationality(), firCtbcRewFix180.getOwneroccupationcode(), 
									firCtbcRewFix180.getOwneroccupationname(), firCtbcRewFix180.getOwnerislistedcompany(), firCtbcRewFix180.getOwnerisissuedshare(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber() != null && firCtbcRewFix180.getIdentifynumber().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber(), firCtbcRewFix180.getInsuredname(), firCtbcRewFix180.getBirthday(), 
									firCtbcRewFix180.getPhonenumber(), firCtbcRewFix180.getNationality(), firCtbcRewFix180.getOccupationcode(), 
									firCtbcRewFix180.getOccupationname(), firCtbcRewFix180.getIslistedcompany(), firCtbcRewFix180.getIsissuedshare(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 2) {
						if(firCtbcRewFix180.getHolderid2() != null && firCtbcRewFix180.getHolderid2().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid2(), firCtbcRewFix180.getOwnername2(), firCtbcRewFix180.getOwnerbirthday2(), 
									firCtbcRewFix180.getOwnerphonenumber2(), firCtbcRewFix180.getOwnernationality2(), firCtbcRewFix180.getOwneroccupationcode2(), 
									firCtbcRewFix180.getOwneroccupationname2(), firCtbcRewFix180.getOwnerislistedcompany2(), firCtbcRewFix180.getOwnerisissuedshare2(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber2() != null && firCtbcRewFix180.getIdentifynumber2().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber2(), firCtbcRewFix180.getInsuredname2(), firCtbcRewFix180.getBirthday2(), 
									firCtbcRewFix180.getPhonenumber2(), firCtbcRewFix180.getNationality2(), firCtbcRewFix180.getOccupationcode2(), 
									firCtbcRewFix180.getOccupationname2(), firCtbcRewFix180.getIslistedcompany2(), firCtbcRewFix180.getIsissuedshare2(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 3) {
						if(firCtbcRewFix180.getHolderid3() != null && firCtbcRewFix180.getHolderid3().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid3(), firCtbcRewFix180.getOwnername3(), firCtbcRewFix180.getOwnerbirthday3(), 
									firCtbcRewFix180.getOwnerphonenumber3(), firCtbcRewFix180.getOwnernationality3(), firCtbcRewFix180.getOwneroccupationcode3(), 
									firCtbcRewFix180.getOwneroccupationname3(), firCtbcRewFix180.getOwnerislistedcompany3(), firCtbcRewFix180.getOwnerisissuedshare3(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber3() != null && firCtbcRewFix180.getIdentifynumber3().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber3(), firCtbcRewFix180.getInsuredname3(), firCtbcRewFix180.getBirthday3(), 
									firCtbcRewFix180.getPhonenumber3(), firCtbcRewFix180.getNationality3(), firCtbcRewFix180.getOccupationcode3(), 
									firCtbcRewFix180.getOccupationname3(), firCtbcRewFix180.getIslistedcompany3(), firCtbcRewFix180.getIsissuedshare3(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 4) {
						if(firCtbcRewFix180.getHolderid4() != null && firCtbcRewFix180.getHolderid4().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid4(), firCtbcRewFix180.getOwnername4(), firCtbcRewFix180.getOwnerbirthday4(), 
									firCtbcRewFix180.getOwnerphonenumber4(), firCtbcRewFix180.getOwnernationality4(), firCtbcRewFix180.getOwneroccupationcode4(), 
									firCtbcRewFix180.getOwneroccupationname4(), firCtbcRewFix180.getOwnerislistedcompany4(), firCtbcRewFix180.getOwnerisissuedshare4(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber4() != null && firCtbcRewFix180.getIdentifynumber4().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber4(), firCtbcRewFix180.getInsuredname4(), firCtbcRewFix180.getBirthday4(), 
									firCtbcRewFix180.getPhonenumber4(), firCtbcRewFix180.getNationality4(), firCtbcRewFix180.getOccupationcode4(), 
									firCtbcRewFix180.getOccupationname4(), firCtbcRewFix180.getIslistedcompany4(), firCtbcRewFix180.getIsissuedshare4(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 5) {
						if(firCtbcRewFix180.getHolderid5() != null && firCtbcRewFix180.getHolderid5().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid5(), firCtbcRewFix180.getOwnername5(), firCtbcRewFix180.getOwnerbirthday5(), 
									firCtbcRewFix180.getOwnerphonenumber5(), firCtbcRewFix180.getOwnernationality5(), firCtbcRewFix180.getOwneroccupationcode5(), 
									firCtbcRewFix180.getOwneroccupationname5(), firCtbcRewFix180.getOwnerislistedcompany5(), firCtbcRewFix180.getOwnerisissuedshare5(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber5() != null && firCtbcRewFix180.getIdentifynumber5().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber5(), firCtbcRewFix180.getInsuredname5(), firCtbcRewFix180.getBirthday5(), 
									firCtbcRewFix180.getPhonenumber5(), firCtbcRewFix180.getNationality5(), firCtbcRewFix180.getOccupationcode5(), 
									firCtbcRewFix180.getOccupationname5(), firCtbcRewFix180.getIslistedcompany5(), firCtbcRewFix180.getIsissuedshare5(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 6) {
						if(firCtbcRewFix180.getHolderid6() != null && firCtbcRewFix180.getHolderid6().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid6(), firCtbcRewFix180.getOwnername6(), firCtbcRewFix180.getOwnerbirthday6(), 
									firCtbcRewFix180.getOwnerphonenumber6(), firCtbcRewFix180.getOwnernationality6(), firCtbcRewFix180.getOwneroccupationcode6(), 
									firCtbcRewFix180.getOwneroccupationname6(), firCtbcRewFix180.getOwnerislistedcompany6(), firCtbcRewFix180.getOwnerisissuedshare6(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber6() != null && firCtbcRewFix180.getIdentifynumber6().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber6(), firCtbcRewFix180.getInsuredname6(), firCtbcRewFix180.getBirthday6(), 
									firCtbcRewFix180.getPhonenumber6(), firCtbcRewFix180.getNationality6(), firCtbcRewFix180.getOccupationcode6(), 
									firCtbcRewFix180.getOccupationname6(), firCtbcRewFix180.getIslistedcompany6(), firCtbcRewFix180.getIsissuedshare6(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}else if(i == 7) {
						if(firCtbcRewFix180.getHolderid7() != null && firCtbcRewFix180.getHolderid7().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"01", i, firCtbcRewFix180.getHolderid7(), firCtbcRewFix180.getOwnername7(), firCtbcRewFix180.getOwnerbirthday7(), 
									firCtbcRewFix180.getOwnerphonenumber7(), firCtbcRewFix180.getOwnernationality7(), firCtbcRewFix180.getOwneroccupationcode7(), 
									firCtbcRewFix180.getOwneroccupationname7(), firCtbcRewFix180.getOwnerislistedcompany7(), firCtbcRewFix180.getOwnerisissuedshare7(), 
									firCtbcRewFix180.getIcreate(), now);
						}
						if(firCtbcRewFix180.getIdentifynumber7() != null && firCtbcRewFix180.getIdentifynumber7().trim().length() > 0){
							this.InsertFirCtbcRewSnn(firCtbcRewFix180.getBatchOid(), firCtbcRewFix180.getO180filename(), firCtbcRewFix180.getLinenum(), 
									"02", i, firCtbcRewFix180.getIdentifynumber7(), firCtbcRewFix180.getInsuredname7(), firCtbcRewFix180.getBirthday7(), 
									firCtbcRewFix180.getPhonenumber7(), firCtbcRewFix180.getNationality7(), firCtbcRewFix180.getOccupationcode7(), 
									firCtbcRewFix180.getOccupationname7(), firCtbcRewFix180.getIslistedcompany7(), firCtbcRewFix180.getIsissuedshare7(), 
									firCtbcRewFix180.getIcreate(), now);
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void InsertFirCtbcRewSnn(BigDecimal batchOid, String o180filename, long linenum, String relationAgent, int sortNo, String relateId, String names, 
			String birthday, String phone, String nationality, String occupation, String occupationname, String listedCompany, String issueShares, String icreate, Date dcreate) {
		try {
			String verifyID_host = configUtil.getString("verifyIDUrl");
			CwpUtil cwpUtil = new CwpUtil(verifyID_host);
			FirCtbcRewSnn firCtbcRewSnn = new FirCtbcRewSnn();
			firCtbcRewSnn.setBatchOid(batchOid);
			firCtbcRewSnn.setO180filename(o180filename);
			firCtbcRewSnn.setLinenum(linenum);
			firCtbcRewSnn.setRelationAgent(relationAgent);
			firCtbcRewSnn.setSortNo(String.valueOf(sortNo));
			firCtbcRewSnn.setRelateId(relateId);
			firCtbcRewSnn.setNames(names);
			VerifyIdVo verifyIdVo = cwpUtil.getIDInfo(relateId);
			String isLegal = "";
			if(verifyIdVo != null) {
				if("60".equals(verifyIdVo.getIdentifyType())) {//統一編號
					isLegal = "01";//法人
				}else if("01".equals(verifyIdVo.getIdentifyType())) {//身分證
					isLegal = "02";//自然人
				}else if("05".equals(verifyIdVo.getIdentifyType())) {//居留證
					isLegal = "03";//外國人
				}
			}
			firCtbcRewSnn.setIsLegal(isLegal);
			firCtbcRewSnn.setBirthday(birthday);
			firCtbcRewSnn.setPhone(phone);
			firCtbcRewSnn.setNationality(nationality);
			firCtbcRewSnn.setOccupation(occupation);
			firCtbcRewSnn.setOccupationname(occupationname);
			firCtbcRewSnn.setListedCompany(listedCompany);
			firCtbcRewSnn.setIssueShares(issueShares);
			firCtbcRewSnn.setIcreate(icreate);
			firCtbcRewSnn.setDcreate(dcreate);
			this.firCtbcRewSnnService.insertFirCtbcRewSnn(firCtbcRewSnn);

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String mappingOldCityToNewCity(String oldAddress) throws Exception{
		String newAddress = "";
		try {
			String codecode = "***";
			if(oldAddress != null && oldAddress.length() >= 3) {
				codecode = oldAddress.substring(0,3);
			}
			Map params = new HashMap();
			params.put("codetype", "PostAddress");
			params.put("codecode", codecode);
			Result result = prpdnewcodeService.findPrpdnewcodeByParams(params);
			String codename = "";
			if(result != null && result.getResObject() != null) {
				List<Prpdnewcode> searchResult = (List<Prpdnewcode>)result.getResObject();
				Prpdnewcode entity = searchResult.get(0);
				codename = entity.getCodecname();
				if(oldAddress.length() >= codename.length() + 5) {
					newAddress = oldAddress.substring(0, 5) + codename + oldAddress.substring(5 + codename.length());
				}else {
					return oldAddress;
				}
			}else {
				return oldAddress;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return oldAddress;
		}
		return newAddress;
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start
	 * 依照matchType更新firCtbcRewFix180.matchType*/
	@Override
	public void updateFirCtbcRewFix180MatchType(FirCtbcRewFix180 firCtbcRewFix180, String logMatchType, String nameMatchType) throws SystemException, Exception {
		if(nameMatchType.equals("1") && logMatchType.equals("1")) {
			firCtbcRewFix180.setMatchType("1");
		}else if((nameMatchType.equals("2") && logMatchType.equals("1"))
				|| (nameMatchType.equals("1") && logMatchType.equals("2"))
				|| (nameMatchType.equals("2") && logMatchType.equals("2"))) {
			firCtbcRewFix180.setMatchType("2");
		}else if(nameMatchType.equals("3") && logMatchType.equals("3")) {
			firCtbcRewFix180.setMatchType("3");
		}else if(nameMatchType.equals("4") && logMatchType.equals("4")) {
			firCtbcRewFix180.setMatchType("4");
		}
		firCtbcRewFix180Service.updateFirCtbcRewFix180(firCtbcRewFix180);
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end */

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirCtbcRewNoticeBatchService getFirCtbcRewNoticeBatchService() {
		return firCtbcRewNoticeBatchService;
	}

	public void setFirCtbcRewNoticeBatchService(FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService) {
		this.firCtbcRewNoticeBatchService = firCtbcRewNoticeBatchService;
	}

	public FirCtbcRewOriginal180Service getFirCtbcRewOriginal180Service() {
		return firCtbcRewOriginal180Service;
	}

	public void setFirCtbcRewOriginal180Service(FirCtbcRewOriginal180Service firCtbcRewOriginal180Service) {
		this.firCtbcRewOriginal180Service = firCtbcRewOriginal180Service;
	}

	public FirCtbcRewDontnoticeService getFirCtbcRewDontnoticeService() {
		return firCtbcRewDontnoticeService;
	}

	public void setFirCtbcRewDontnoticeService(FirCtbcRewDontnoticeService firCtbcRewDontnoticeService) {
		this.firCtbcRewDontnoticeService = firCtbcRewDontnoticeService;
	}

	public PremiumCalService getPremiumCalService() {
		return premiumCalService;
	}

	public void setPremiumCalService(PremiumCalService premiumCalService) {
		this.premiumCalService = premiumCalService;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public RuleCheckService getClientRuleCheckService() {
		return clientRuleCheckService;
	}

	public void setClientRuleCheckService(RuleCheckService clientRuleCheckService) {
		this.clientRuleCheckService = clientRuleCheckService;
	}

	public FirCtbcRewFix180Service getFirCtbcRewFix180Service() {
		return firCtbcRewFix180Service;
	}

	public void setFirCtbcRewFix180Service(FirCtbcRewFix180Service firCtbcRewFix180Service) {
		this.firCtbcRewFix180Service = firCtbcRewFix180Service;
	}

	public FirCtbcRewNoshowwordService getFirCtbcRewNoshowwordService() {
		return firCtbcRewNoshowwordService;
	}

	public void setFirCtbcRewNoshowwordService(FirCtbcRewNoshowwordService firCtbcRewNoshowwordService) {
		this.firCtbcRewNoshowwordService = firCtbcRewNoshowwordService;
	}

	public FirCtbcRewMatchLogService getFirCtbcRewMatchLogService() {
		return firCtbcRewMatchLogService;
	}

	public void setFirCtbcRewMatchLogService(FirCtbcRewMatchLogService firCtbcRewMatchLogService) {
		this.firCtbcRewMatchLogService = firCtbcRewMatchLogService;
	}

	public FirCtbcRewMatchnameService getFirCtbcRewMatchnameService() {
		return firCtbcRewMatchnameService;
	}

	public void setFirCtbcRewMatchnameService(FirCtbcRewMatchnameService firCtbcRewMatchnameService) {
		this.firCtbcRewMatchnameService = firCtbcRewMatchnameService;
	}

	public FirCtbcRewSnnService getFirCtbcRewSnnService() {
		return firCtbcRewSnnService;
	}

	public void setFirCtbcRewSnnService(FirCtbcRewSnnService firCtbcRewSnnService) {
		this.firCtbcRewSnnService = firCtbcRewSnnService;
	}

	public PrpdnewcodeService getPrpdnewcodeService() {
		return prpdnewcodeService;
	}

	public void setPrpdnewcodeService(PrpdnewcodeService prpdnewcodeService) {
		this.prpdnewcodeService = prpdnewcodeService;
	}

}
