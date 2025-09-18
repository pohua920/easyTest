package com.tlg.aps.bs.firRepeatpolicyImportService.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firRepeatpolicyImportService.FirRepeatpolicyImportService;
import com.tlg.aps.bs.firRepeatpolicyImportService.RepeatpolicyImportService;
import com.tlg.aps.vo.Aps042ImportVo;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.prpins.service.FirRepeatpolicyBatchDtlService;
import com.tlg.prpins.service.FirRepeatpolicyBatchMainService;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRepeatpolicyImportServiceImpl implements FirRepeatpolicyImportService {
	private static final Logger logger = Logger.getLogger(FirRepeatpolicyImportServiceImpl.class);
	
	private FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService;
	private FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService;
	private RepeatpolicyImportService repeatpolicyImportService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Result dataUploadAndImport(String userId, File uploadFile, String filename, String rnYyyymm) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		Result result = new Result();
		FirRepeatpolicyBatchMain main = new FirRepeatpolicyBatchMain();
		
		//查詢主檔明細檔是否有資料，若已存在相同年月資料，將相同年月資料刪除。
		params.put("rpYyyymm", rnYyyymm);
		Result mainResult = firRepeatpolicyBatchMainService.findByParams(params);
		if(mainResult.getResObject() != null) {
			List<FirRepeatpolicyBatchMain> mainList = (List<FirRepeatpolicyBatchMain>) mainResult.getResObject();
			for(FirRepeatpolicyBatchMain m:mainList) {
				repeatpolicyImportService.removeFirRepeatpolicyBatchMain(m);
				
				params.put("batchNo", m.getBatchNo());
				Result dtlRresult = firRepeatpolicyBatchDtlService.findByParams(params);
				if(dtlRresult.getResObject() != null) {
					List<FirRepeatpolicyBatchDtl> dtlList = (List<FirRepeatpolicyBatchDtl>) dtlRresult.getResObject();
					for(FirRepeatpolicyBatchDtl d:dtlList) {
						repeatpolicyImportService.removeFirRepeatpolicyBatchDtl(d);
					}
				}
			}
		}
		
		String batchNo = "REPO"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//新增主檔完成直接commit
		try {
			main.setBatchNo(batchNo);
			main.setRpYyyymm(rnYyyymm);
			main.setFilename(filename);
			main.setFileStatus("N");
			main.setIcreate(userId);
			main.setDcreate(new Date());
			result = repeatpolicyImportService.insertFirRepeatpolicyBatchMain(main);
			if(result.getResObject() == null) {
				throw new Exception(result.getMessage().toString());
			}
		} catch (Exception e) {
			return this.getReturnResult("新增複保險通知轉檔主檔失敗:"+e.getMessage());
		}
		
		//讀取xlsx檔案，loop每筆資料，依資料中保單號查詢核心資料，並將查詢結果寫入資料庫。
		FileInputStream fis = new FileInputStream(uploadFile);
		String remark = "";
		int tmpAll = 0;
		int index = 0;
		try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {
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
			
			tmpAll = rownum-1-blankRow.size();
			if(tmpAll == 0) {
				updateFirRepeatpolicyBatchMain(main, "Z", "檔案無資料", tmpAll, userId);
				return this.getReturnResult("檔案無資料。");
			}
			
			StringBuffer msg = new StringBuffer();
			for(int i=1; i<rownum; i++){
				Boolean check = false;
				index = i-1;
				if(!blankRow.contains(i)) {//不是空行才往繼續以下動作
					Row row = sheet.getRow(i);//獲取到每一行，但不包括第一行
					String policyno = getCellFormatValue(row.getCell(0)).trim();
					if(StringUtils.isBlank(policyno)) {
						msg.append("第"+ (index+1) +"筆資料新增轉檔明細失敗:保單號碼不能是空值;");
						continue;
					}
					String repeatCompany = getCellFormatValue(row.getCell(14)).trim();
					String repeatPolicyno = getCellFormatValue(row.getCell(15)).trim();
					String repeatSdate = getCellFormatValue(row.getCell(16)).trim();
					String repeatEdate = getCellFormatValue(row.getCell(17)).trim();
					if(StringUtils.isNotBlank(repeatSdate)) {
						if(!checkDateFormat(repeatSdate)) {
							msg.append("第"+ (index+1) +"筆資料新增轉檔明細失敗:保險起日格式錯誤;");
							check = true;
						}
					}
					if(StringUtils.isNotBlank(repeatEdate)) {
						if(!checkDateFormat(repeatEdate)) {
							msg.append("第"+ (index+1) +"筆資料新增轉檔明細失敗:保險迄日格式錯誤;");
							check = true;
						}
					}
					if(check) {
						continue;
					}
					
					FirRepeatpolicyBatchDtl dtl = new FirRepeatpolicyBatchDtl();
					params.clear();
					params.put("policyno", policyno);
					result = firRepeatpolicyBatchDtlService.selectForAps042Import(params);
					Aps042ImportVo vo = new Aps042ImportVo();
					if(result.getResObject() != null) {
						List<Aps042ImportVo> list = (List<Aps042ImportVo>) result.getResObject();
						vo = list.get(0);
						BeanUtils.copyProperties(vo, dtl);
					}
					dtl.setBatchNo(batchNo);
					dtl.setPolicyno(policyno);
					dtl.setRepeatPolicyno(repeatPolicyno);
					dtl.setRepeatCompany(repeatCompany);
					dtl.setRepeatSdate(DateUtils.getDateObj(repeatSdate));
					dtl.setRepeatEdate(DateUtils.getDateObj(repeatEdate));
					if(StringUtils.isBlank(dtl.getContactNumber())) {
						dtl.setContactNumber("0800-075777");
					}
					dtl.setIcreate(userId);
					dtl.setDcreate(new Date());
					try {
						result = firRepeatpolicyBatchDtlService.insertFirRepeatpolicyBatchDtl(dtl);
						if(result.getResObject() == null) {
							throw new Exception(result.getMessage().toString());
						}
					} catch (Exception e) {
						msg.append("第"+ (index+1) +"筆資料新增轉檔明細失敗:"+e.getCause().getCause().getMessage().replaceAll("\n", "")+";");
					}
				}
			}
			
			if(StringUtils.isNotBlank(msg.toString())) {
				throw new Exception(msg.toString());
			}
			
			this.updateFirRepeatpolicyBatchMain(main, "S", remark, tmpAll, userId);
			return this.getReturnResult("處理完成，請重新查詢確認執行結果。");
		} catch (Exception e) {
			logger.error(e);
			main.setFileStatus("E");
			main.setRemark(e.getMessage());
			main.setDataQty(tmpAll);
			main.setIupdate(userId);
			main.setDupdate(new Date());
			repeatpolicyImportService.updateFirRepeatpolicyBatchMain(main);
			throw new Exception("轉檔失敗:"+e.getMessage());
		}
	}
	
	private void updateFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain main,String fileStatus, String remark, int dataQty, String userId) throws Exception {
		main.setDataQty(dataQty);
		main.setFileStatus(fileStatus);
		main.setRemark(remark);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		firRepeatpolicyBatchMainService.updateFirRepeatpolicyBatchMain(main);
	}
	
	private static String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell instanceof XSSFCell) {
			// 判斷當前Cell的Type
			XSSFCell xssfCell = (XSSFCell)cell;
			switch (xssfCell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC:{
				Date value = xssfCell.getDateCellValue();
				cellvalue = DateUtils.format(value, "yyyy/MM/dd");
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
	
	//檢查日期格式
	private static boolean checkDateFormat(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			sdf.setLenient(false);
			//如果成功就是正確的日期，失敗就是有錯誤的日期。  
			sdf.parse(date);  
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	public FirRepeatpolicyBatchMainService getFirRepeatpolicyBatchMainService() {
		return firRepeatpolicyBatchMainService;
	}
	public void setFirRepeatpolicyBatchMainService(FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService) {
		this.firRepeatpolicyBatchMainService = firRepeatpolicyBatchMainService;
	}
	public FirRepeatpolicyBatchDtlService getFirRepeatpolicyBatchDtlService() {
		return firRepeatpolicyBatchDtlService;
	}
	public void setFirRepeatpolicyBatchDtlService(FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService) {
		this.firRepeatpolicyBatchDtlService = firRepeatpolicyBatchDtlService;
	}
	public RepeatpolicyImportService getRepeatpolicyImportService() {
		return repeatpolicyImportService;
	}
	public void setRepeatpolicyImportService(RepeatpolicyImportService repeatpolicyImportService) {
		this.repeatpolicyImportService = repeatpolicyImportService;
	}
}
