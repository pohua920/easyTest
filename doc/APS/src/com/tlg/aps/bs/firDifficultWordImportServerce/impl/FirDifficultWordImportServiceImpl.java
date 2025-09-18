package com.tlg.aps.bs.firDifficultWordImportServerce.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firDifficultWordImportServerce.FirDifficultWordImportService;
import com.tlg.aps.bs.firDifficultWordImportServerce.RewNoshowwordDataService;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入*/
@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirDifficultWordImportServiceImpl implements FirDifficultWordImportService {
	
	private static final Logger logger = Logger.getLogger(FirDifficultWordImportServiceImpl.class);

	private FirCtbcRewNoshowwordService firCtbcRewNoshowwordService;
	private RewNoshowwordDataService rewNoshowwordDataService;
	
	@SuppressWarnings("unchecked")
	public Result importDifficultWord(File uploadFile, UserInfo userInfo) throws Exception{
		int dataCount = 0;
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
			Map<String,String> params = new HashMap<>();
			for(int i=1; i<rownum; i++){
				rowCount ++ ;
				if(!blankRow.contains(i)) {//不是空行才往繼續以下動作
					Row row = sheet.getRow(i);//獲取到每一行，但不包括第一行
					String ownerid = getCellFormatValue(row.getCell(0));
					String thename = getCellFormatValue(row.getCell(1));
					//若欄位格式不為文字或為空值，則匯入失敗，但跳過空行
					if(StringUtil.isSpace(ownerid) || StringUtil.isSpace(thename)) {
						return getReturnResult("匯入失敗，第"+(i+1)+"筆資料錯誤");
					}
					params.put("ownerid", ownerid);
					params.put("datatype", "1");
					Result result = firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByParams(params);
					
					if(result.getResObject()!=null) {
						List<FirCtbcRewNoshowword> resultList = (List<FirCtbcRewNoshowword>)result.getResObject();
						for(FirCtbcRewNoshowword firCtbcRewNoshowword : resultList) {
							rewNoshowwordDataService.removeFirCtbcRewNoshowword(firCtbcRewNoshowword.getOid());
						}
					}
					
					FirCtbcRewNoshowword firCtbcRewNoshowword = new FirCtbcRewNoshowword();
					firCtbcRewNoshowword.setDatatype("1");
					firCtbcRewNoshowword.setOwnerid(ownerid);
					firCtbcRewNoshowword.setThename(thename);
					firCtbcRewNoshowword.setIcreate(userInfo.getUserId().toUpperCase());
					firCtbcRewNoshowword.setDcreate(dateNow);
					firCtbcRewNoshowword.setIupdate(userInfo.getUserId().toUpperCase());
					firCtbcRewNoshowword.setDupdate(new Date());
					rewNoshowwordDataService.insertFirCtbcRewNoshowword(firCtbcRewNoshowword);
					dataCount ++;
				}
			}
		}catch(Exception e) {
			logger.error("中信代理投保難字匯入錯誤", e);
			String errMsg = "匯入失敗";
			if(rowCount != 0) {
				rowCount = rowCount+1;
				errMsg = errMsg+"，第"+rowCount+"筆資料錯誤";
			}
			return getReturnResult(errMsg);
		}
		return getReturnResult("匯入完成，本次共匯入"+dataCount+"筆資料");
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

	public FirCtbcRewNoshowwordService getFirCtbcRewNoshowwordService() {
		return firCtbcRewNoshowwordService;
	}

	public void setFirCtbcRewNoshowwordService(FirCtbcRewNoshowwordService firCtbcRewNoshowwordService) {
		this.firCtbcRewNoshowwordService = firCtbcRewNoshowwordService;
	}

	public RewNoshowwordDataService getRewNoshowwordDataService() {
		return rewNoshowwordDataService;
	}

	public void setRewNoshowwordDataService(RewNoshowwordDataService rewNoshowwordDataService) {
		this.rewNoshowwordDataService = rewNoshowwordDataService;
	}

}
