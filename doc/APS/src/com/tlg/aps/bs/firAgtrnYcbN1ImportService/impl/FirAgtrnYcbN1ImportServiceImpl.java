package com.tlg.aps.bs.firAgtrnYcbN1ImportService.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firAgtrnYcbN1ImportService.FirAgtrnYcbN1ImportService;
import com.tlg.aps.vo.Aps060MainDtlVo;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnYcbUpload;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnYcbUploadService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnYcbN1ImportServiceImpl  implements FirAgtrnYcbN1ImportService {

	private static final Logger logger = Logger.getLogger(FirAgtrnYcbN1ImportServiceImpl.class);
	
	private FirAgtrnYcbUploadService firAgtrnYcbUploadService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	
	@SuppressWarnings("unchecked")
	//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
	public Result importFirAgtrnYcbN1(String batchNo, File uploadFile, UserInfo userInfo, String[] handleridentifynumber) throws Exception{
		int rowCount = 0;
		FileInputStream fis = new FileInputStream(uploadFile);
		try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {
			Sheet sheet = workbook.getSheetAt(0);//只取第一個工作表
			int rownum = sheet.getPhysicalNumberOfRows();//取所有行數
			if(rownum <= 1) {//excel裡無資料
				if(fis!=null) {
					fis.close();
				}
				return getReturnResult("XLSX檔無資料");
			}
			
			//先查詢是否已存在相同批次號之資料，若已存在，將相同批次號資料刪除並重新轉入。
			Map params = new HashMap<>();
			params.put("batchNo", batchNo);
			Result result = firAgtrnYcbUploadService.findFirAgtrnYcbUploadByParams(params);
			List<FirAgtrnYcbUpload> oldFirAgtrnYcbUploads = (List<FirAgtrnYcbUpload>) result.getResObject();
			if(oldFirAgtrnYcbUploads != null &&  oldFirAgtrnYcbUploads.size() > 0) {
				firAgtrnYcbUploadService.removeFirAgtrnYcbUpload(oldFirAgtrnYcbUploads.get(0));//用第一筆batchNo刪除整批
			}
			
			//判斷excel是否有整行為空的資料
			List<Integer> blankRow = new ArrayList<>();
			for(int i=0;i<rownum;i++) {
				int cellNum = sheet.getRow(i).getPhysicalNumberOfCells();
				Row row1 = sheet.getRow(i);
				int blankNum = 0;
				for(int j=0;j<cellNum;j++) {
					if(row1.getCell(j,Row.RETURN_BLANK_AS_NULL) == null) {
						blankNum ++;
					}
				}
				if(blankNum == cellNum) {
					blankRow.add(i);
				}
			}
			//取得現在時間
			Date dateNow = new Date();
			//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位START
			int handlerCount = 0;
			String handler = "";
			//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位END
			for(int i=1; i<rownum; i++){
				rowCount ++ ;
				if(!blankRow.contains(i)) {//不是空行才往繼續以下動作
					FirAgtrnYcbUpload firAgtrnYcbUpload = new FirAgtrnYcbUpload();
					Row row = sheet.getRow(i);//獲取到每一行，但不包括第一行
					String oldPolicyNo = getCellFormatValue(row.getCell(3));//OLDPOLICYNO	續保保單號碼	Excel.D欄
					String collateralNumber = getCellFormatValue(row.getCell(19));//COLLATERALNUMBER	擔保品編號	Excel.T欄
					String isRenew = getCellFormatValue(row.getCell(17));//IS_RENEW	是否續保	Excel.R欄
					//若欄位值為“續保”，請轉換為“Y”;若欄位值為“不續保”，請轉換為“N”
					if("續約".equals(isRenew)) {
						isRenew = "Y";
						//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位START
						handler = handleridentifynumber[handlerCount % handleridentifynumber.length];
						firAgtrnYcbUpload.setHandleridentifynumber(StringUtils.trim(handler));
						handlerCount++;
						//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位END
					} else if("不續約".equals(isRenew)) {
						isRenew = "N";
					} else {
						isRenew = "";
					}
					
					firAgtrnYcbUpload.setBatchNo(batchNo);
					firAgtrnYcbUpload.setOldpolicyno(oldPolicyNo);
					firAgtrnYcbUpload.setCollateralnumber(collateralNumber);
					firAgtrnYcbUpload.setIsRenew(isRenew);
					firAgtrnYcbUpload.setIcreate(userInfo.getUserId().toUpperCase());
					firAgtrnYcbUpload.setDcreate(dateNow);
					firAgtrnYcbUploadService.insertFirAgtrnYcbUpload(firAgtrnYcbUpload);
				}
			}
		}catch(Exception e) {
			logger.error("元大續保資料處理作業-N+1轉檔作業錯誤", e);
			String errMsg = "上傳元大續保明細表失敗:"+e;
			if(rowCount != 0) {
				rowCount = rowCount+1;
				errMsg = errMsg+"，第"+rowCount+"筆，上傳元大續保明細表失敗:"+e;
			}
			return getReturnResult(errMsg);
		}
		return getReturnResult("S");
	}
	

	@SuppressWarnings("unchecked")
	public Result updateFirAgtrnTocoreMainAndDtl(String batchNo, UserInfo userInfo) throws Exception {
		try {
			Date now = new Date();
			String TMP_CHECK = "Y";
			String TMP_ERR_MSG = "";
			String TMP_FIX_USER = null;
			Date TMP_FIX_DATE = null;
			String TMP_SF_FLAG = "N";
			String TMP_SF_REASON = null; 
			String TMP_SF_USER = null; 
			Date TMP_SF_DATE = null; 
			String TMP_DATA_STATUS = "2";
			//查詢Dtl&Main
			Map params = new HashMap<>();
			params.put("batchNo", batchNo);
			Result result = firAgtrnYcbUploadService.selectAgtMainAndDtlForImport(params);
			List<Aps060MainDtlVo> aps060MainDtlVos = (List<Aps060MainDtlVo>) result.getResObject();
			for(Aps060MainDtlVo vo : aps060MainDtlVos) {
				TMP_CHECK = "Y";
				TMP_ERR_MSG = "";
				TMP_FIX_USER = null;
				TMP_FIX_DATE = null;
				TMP_SF_FLAG = "N";
				TMP_SF_REASON = null; 
				TMP_SF_USER = null; 
				TMP_SF_DATE = null; 
				TMP_DATA_STATUS = "2";
				//查詢FIR_AGTRN_YCB_UPLOAD
				params = new HashMap<>();
				params.put("oldpolicyNo", vo.getOldpolicyno());
				Result firAgtrnYcbUploadResult = firAgtrnYcbUploadService.findFirAgtrnYcbUploadByParams(params);
				List<FirAgtrnYcbUpload> firAgtrnYcbUploads = (List<FirAgtrnYcbUpload>) firAgtrnYcbUploadResult.getResObject();
				
				if(firAgtrnYcbUploads != null && firAgtrnYcbUploads.size() > 0) {
					FirAgtrnYcbUpload firAgtrnYcbUpload = firAgtrnYcbUploads.get(0);//SA說:只取第一筆
	
					if(firAgtrnYcbUpload.getIsRenew() == null) {//IF  查詢結果.IS_RENEW (是否續保)== NULL
						TMP_CHECK = "N";
						TMP_ERR_MSG += "比對續保明細表中無是否續保;";
						TMP_DATA_STATUS = "D";
					} else {
						if("Y".equals(firAgtrnYcbUpload.getIsRenew()) && vo.getCheckErrMsg() == null) {//IS_RENEW == ‘Y’&& 查詢結果. CHECK_ERR_MSG == NULL
							
							if(firAgtrnYcbUpload.getCollateralnumber() != null) {//IF 查詢結果.COLLATERALNUMBER(擔保品編號) <> NULL
								TMP_CHECK = "Y"; 
								TMP_FIX_USER = userInfo.getUserId().toUpperCase();
								TMP_FIX_DATE = now;
								//   更新FIR_AGT_TOCORE_MAIN
								FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
								firAgtTocoreMain.setOid(vo.getOid());
								firAgtTocoreMain.setCollateralnumber1(firAgtrnYcbUpload.getCollateralnumber());
								firAgtTocoreMain.setIupdate(userInfo.getUserId().toUpperCase());
								firAgtTocoreMain.setDupdate(now);
								firAgtTocoreMain.setHandleridentifynumber(firAgtrnYcbUpload.getHandleridentifynumber());//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
								firAgtTocoreMainService.updateFirAgtTocoreMainByOid(firAgtTocoreMain);
							} else {
								TMP_CHECK = "N";
								TMP_ERR_MSG += "比對續保明細表中無擔保品編號;";
								TMP_DATA_STATUS = "D";
							}
						
						} else if("N".equals(firAgtrnYcbUpload.getIsRenew()) && vo.getCheckErrMsg() == null) {//ELSE IF IS_RENEW == ‘N’&& 查詢結果. CHECK_ERR_MSG == NULL
							TMP_CHECK = "Y"; 
							TMP_SF_FLAG = "Y"; 
							TMP_SF_REASON = "比對續保明細不續保;";
							TMP_SF_USER = userInfo.getUserId().toUpperCase();
							TMP_SF_DATE = now;
							TMP_DATA_STATUS = "A";
						} else if(vo.getCheckErrMsg() != null) {
							TMP_CHECK = "N";
							TMP_ERR_MSG += "尚有異常訊息，比對續保明細表不轉入核心，需人工處理;";
							TMP_DATA_STATUS = "D";
						}
					}
					
					
					
					
					FirAgtrnBatchDtl dlt = new FirAgtrnBatchDtl();
					// 更新FIR_AGTRN_BATCH_DTL
					 if("Y".equals(TMP_CHECK)){
						 dlt.setBatchNo(vo.getBatchNo());
						 dlt.setBatchSeq(vo.getBatchseq().intValue());
						 dlt.setFixUser(TMP_FIX_USER);
						 dlt.setFixDate(TMP_FIX_DATE);
						 dlt.setSfFlag(TMP_SF_FLAG);
						 dlt.setSfReason(TMP_SF_REASON);
						 dlt.setSfUser(TMP_SF_USER);
						 dlt.setSfDate(TMP_SF_DATE);
						 dlt.setDataStatus(TMP_DATA_STATUS);
						 dlt.setIupdate(userInfo.getUserId().toUpperCase());
						 dlt.setDupdate(now);
	
					 } else {
						 dlt.setBatchNo(vo.getBatchNo());
						 dlt.setBatchSeq(vo.getBatchseq().intValue());
						 dlt.setDataStatus(TMP_DATA_STATUS);
						 dlt.setCheckErrMsg( vo.getCheckErrMsg()!= null ? vo.getCheckErrMsg() + TMP_ERR_MSG : TMP_ERR_MSG);//原始內容 +  TMP_ERR_MSG
						 dlt.setIupdate(userInfo.getUserId().toUpperCase());
						 dlt.setDupdate(now);
					 }
					 
					 firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(dlt);     
				}
			}
		}catch(Exception e) {
			logger.error("元大續保資料處理作業-N+1轉檔作業錯誤", e);
			e.printStackTrace();
			return getReturnResult(e.getMessage());
		}
		return getReturnResult("S");
	}
	
	private static String getCellFormatValue(Cell cell) {
		String cellvalue = null;
		if (cell instanceof XSSFCell) {
			//因Excel中欄位應只有文字格式，若非文字格式欄位表示資料有誤
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				cellvalue = xssfCell.getRichStringCellValue().getString().trim();
			}
		}
		return cellvalue;
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirAgtrnYcbUploadService getFirAgtrnYcbUploadService() {
		return firAgtrnYcbUploadService;
	}

	public void setFirAgtrnYcbUploadService(FirAgtrnYcbUploadService firAgtrnYcbUploadService) {
		this.firAgtrnYcbUploadService = firAgtrnYcbUploadService;
	}


	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}


	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}


	public FirAgtTocoreMainService getFirAgtTocoreMainService() {
		return firAgtTocoreMainService;
	}


	public void setFirAgtTocoreMainService(FirAgtTocoreMainService firAgtTocoreMainService) {
		this.firAgtTocoreMainService = firAgtTocoreMainService;
	}

}
