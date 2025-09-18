package com.tlg.aps.bs.agentBranchBatchUploadService.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.agentBranchBatchUploadService.AgentBranchBatchUploadService;
import com.tlg.aps.bs.formatAddressService.FormatAddressCheckService;
import com.tlg.exception.SystemException;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.sales.service.PrpdagentsubService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AgentBranchBatchUploadServiceImpl implements AgentBranchBatchUploadService {
	
	private PrpdagentsubService prpdagentsubService;
	private static PrpdagentService prpdagentService;
	//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化
	private static FormatAddressCheckService formatAddressCheckService;
	
	@Override
	public Result uploadBatchData(String userId, File uploadFile) throws Exception {
		FileInputStream fis = new FileInputStream(uploadFile);
		try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {
			Sheet sheet = workbook.getSheetAt(0);//只取第一個工作表
			int rownum = sheet.getPhysicalNumberOfRows();//取所有行數
			if(rownum <= 1) {//excel裡無資料
				return this.getReturnResult("上傳失敗，檔案無資料。");
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
			
			if(rownum-blankRow.size() <=1) {
				return this.getReturnResult("上傳失敗，檔案無資料。");
			}
			
			for(int i=1; i<rownum; i++){
				if(!blankRow.contains(i)) {//不是空行才往繼續以下動作
					Row row = sheet.getRow(i);//獲取到每一行，但不包括第一行
					Prpdagentsub prpdagentsub = new Prpdagentsub();
					String errMsg = "";
					for(ExcelHeader header: ExcelHeader.values()) {
						errMsg = header.checkCell(row, prpdagentsub);
						if(errMsg == null) {
							throw new SystemException("※上傳失敗，保經代原有編號、保經代分支機構名稱、地址為必入欄位不可為空，請確認後重新上傳。");
						}
						if(errMsg.length()!=0){
							throw new SystemException("※上傳失敗，"+errMsg+"，請確認後重新上傳。");
						}
					}
					prpdagentsub.setAgentsubcode(String.valueOf(prpdagentsubService.countMaxAgentsubcode()+1));
					Date sysdate = new Date();
					prpdagentsub.setValidstatus("1");
					prpdagentsub.setIcreate(userId);
					prpdagentsub.setDcreate(sysdate);
					prpdagentsub.setIupdate(userId);
					prpdagentsub.setDupdate(sysdate);
					prpdagentsubService.insertPrpdagentsub(prpdagentsub);
				}
			}
			//執行完成
			return this.getReturnResult("處理完成，請重新查詢確認執行結果。");	
		} 
	}
	
	private enum ExcelHeader{
		ORGICODE(0,""){//保經代原有編號
			String checkCell(Row row,Prpdagentsub prpdagentsub) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return null;
				}
				try {
					msg = findAgent(cellValue,prpdagentsub);
					if(msg !="") {
						msg = msg+"("+cellValue+")";
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "查詢保經代原有編號錯誤。";
				}
				return msg;
			}
		},KIND(1,"") {//來源通路別
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					prpdagentsub.setKind(cellValue);
				}
				return msg;
			}
		},AGENTSUBNAME(2,"") {//保經代分支機構名稱
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String agentName = getCellFormatValue(row.getCell(index)).trim();
				String agentSubName = getCellFormatValue(row.getCell(index+1)).trim();
				if(!checkString(agentName) || !checkString(agentSubName)){
					return null;
				}
				prpdagentsub.setAgentsubname(agentName+"_"+agentSubName);
				return msg;
			}
		},POSTCODE(4,"") {//郵遞區號
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					prpdagentsub.setPostcode(cellValue);
				}
				return msg;
			}
		},ADDRESSNAME(5,"") {//地址
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return null;
				}
				// mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
				Map<String,Object> map = new LinkedHashMap<>();
				map = formatAddressCheckService.formatAddressCheck(cellValue);
				if(map.containsKey("errmsg") && !"".equals(map.get("errmsg"))){//地址正規化不成功
					return "地址正規化不成功";
				}
				// mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
				// mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
				prpdagentsub.setAddressname((String) map.get("addressFormatted"));
				prpdagentsub.setPostcode((String) map.get("postcode"));
				//mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
				return msg;
			}
		},PHONENUMBER(7,"") {//通訊電話
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					// mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
					// mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
					if(StringUtil.isNumeric(cellValue) && cellValue.startsWith("0")){//須為數字
						prpdagentsub.setPhonenumber(cellValue);
					}else{
						return "通訊電話格式不正確，不能包含特殊符號，僅能輸入數字且須輸入區域碼以0開頭。";
					}
					// mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
					// mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
				}
				return msg;
			}
		},FAXNUMBER(8,"") {//傳真號碼
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					prpdagentsub.setFaxnumber(cellValue);
				}
				return msg;
			}
		},EMAIL(6,"") {//電子郵箱
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					prpdagentsub.setEmail(cellValue);
				}
				return msg;
			}
		},REMARK(9,"") {//備註
			String checkCell(Row row,Prpdagentsub prpdagentsub){
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(checkString(cellValue)) {
					prpdagentsub.setRemark(cellValue);
				}
				return msg;
			}
		};
		int index;
		String msg;
		ExcelHeader (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String checkCell(Row row,Prpdagentsub prpdagentsub);
	}
	
	private static boolean checkString(String cellValue){
		try {
			return !StringUtil.isSpace(cellValue);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
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
	
	@SuppressWarnings("unchecked")
	private static String findAgent(String orgicode,Prpdagentsub prpdagentsub) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("orgicode", orgicode);
		//mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求-修正錯誤查詢條件
		String[] agenttypeList = {"2","3"};
		params.put("agenttypeList", agenttypeList);
		params.put("validstatus", "1");
		Result searchResult = prpdagentService.findPrpdagentByParams(params);
		
		if (null != searchResult.getResObject()) {
			List<Prpdagent> resultList =  (List<Prpdagent>) searchResult.getResObject();
			if(!resultList.isEmpty()) {
				Prpdagent prpdagent = resultList.get(0);
				prpdagentsub.setAgentcode(prpdagent.getAgentcode());
			}
		}
		if(null != searchResult.getMessage()) {
			return "查無此保經代原有編號。";
		}
		
		return "";
	}

	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public PrpdagentsubService getPrpdagentsubService() {
		return prpdagentsubService;
	}

	public void setPrpdagentsubService(PrpdagentsubService prpdagentsubService) {
		this.prpdagentsubService = prpdagentsubService;
	}

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化
	public FormatAddressCheckService getFormatAddressCheckService() {
		return formatAddressCheckService;
	}

	//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化
	public void setFormatAddressCheckService(FormatAddressCheckService formatAddressCheckService) {
		this.formatAddressCheckService = formatAddressCheckService;
	}
}
