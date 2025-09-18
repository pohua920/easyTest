package com.tlg.aps.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 START */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.tlg.util.StringUtil;
import com.tlg.sales.service.HandlercodeTmpService;
import javax.servlet.http.Cookie;
/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 END */

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
@SuppressWarnings("serial")
public class APS027Action extends BaseAction {
	private PrpdagentService prpdagentService;
	private List<Prpdagent> devResults;
	private Prpdagent prpdagent;
	
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 START */
	private HandlercodeTmpService handlercodeTmpService;
	private File upload = null;
	private String downloadFileName;
	private InputStream inputStream;
	private String token;
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 END */

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS027E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "AGENTCODE");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		parameterHandler();
		getPageInfo().getFilter().put("agenttype", "1");
		getPageInfo().getFilter().put("validstatus", "1");
		Result result = prpdagentService.findPrpdagentByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Prpdagent>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS027E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS027E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS027E0.jsp， 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS027E0.jsp 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if (null == prpdagent.getAgentcode()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,String> params = new HashMap<>();
				params.put("agentcode", prpdagent.getAgentcode());
				Result result = prpdagentService.findPrpdagentByUk(params);
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					prpdagent = (Prpdagent) result.getResObject();
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("修改失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			query();
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		Result result = prpdagentService.updatePrpdagent(prpdagent);
		if(result.getResObject()!=null) {
			setMessage("修改完成");
		}else {
			setMessage("修改失敗");
		}
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler(){
		String handlerStatus = (String) getPageInfo().getFilter().get("handlerStatus");
		if("N".equals(handlerStatus)) {
			getPageInfo().getFilter().put("handlerStatus","N");
		}else if("Y".equals(handlerStatus)) {
			getPageInfo().getFilter().put("handlerStatus","Y");
		}else if("ALL".equals(handlerStatus)) {
			getPageInfo().getFilter().remove("handlerStatus");
		}
	}
	
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 START */
	public String lnkGobatchUploadAndDownload() throws Exception {
		try {
			logger.info("lnkGobatchUploadAndDownload.....");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/*下載檔案*/
	@SuppressWarnings({ "unchecked", "resource" })
	public String btnDownloadFile() throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String[] agenttypeList = {"1"};
			params.put("agenttypeList", agenttypeList);
			params.put("validstatus", "1");
			Result searchResult = prpdagentService.findPrpdagentByParams(params);
			if (null != searchResult.getResObject()) {
				List<Prpdagent> dataList =  (List<Prpdagent>) searchResult.getResObject();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				String[] titleArr = {"業務來源","業務員代碼","業務員姓名","登錄證號","歸屬部門","所屬服務人員"};
				XSSFRow rowTitle = sheet.createRow(0);
				for(int i=0;i<titleArr.length;i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				for (int i = 0; i < dataList.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(dataList.get(i).getBusinesssource());
					row.createCell(1).setCellValue(dataList.get(i).getAgentcode());
					row.createCell(2).setCellValue(dataList.get(i).getAgentname());
					row.createCell(3).setCellValue(dataList.get(i).getLogincode());
					row.createCell(4).setCellValue(dataList.get(i).getUnitcode());
					row.createCell(5).setCellValue(dataList.get(i).getHandlercode());
				}
				downloadFileName = URLEncoder.encode("所有業務人員資料.xlsx", "UTF-8");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte [] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);
			}
			//連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("aps027Download",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			
		}
		return Action.SUCCESS;
	}
	
	/*上傳檔案*/
	@SuppressWarnings("unchecked")
	public String btnBatchUpload() throws Exception {
		String forward = Action.INPUT;
		if(upload!=null) {
			FileInputStream fis = new FileInputStream(upload);
			try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);//只取第一個工作表
				int rownum = sheet.getPhysicalNumberOfRows();//取所有行數
				if(rownum <= 1) {//excel裡無資料
					if(fis!=null) {
						fis.close();
					}
					setMessage("上傳失敗:XLSX檔無資料");
					return forward;
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
					setMessage("上傳失敗:檔案無資料。");
					return forward;
				}
				
				//檢核欄位資料
				List<Prpdagent> Datalist = new ArrayList<Prpdagent>();
				StringBuffer errMsg = new StringBuffer();
				for(int i=1; i<rownum; i++){
					if(!blankRow.contains(i)) {	//不是空行才往繼續以下動作
						Row row = sheet.getRow(i);	//獲取到每一行，但不包括第一行
						Prpdagent prpdagent = new Prpdagent();
						for(ExcelHeader header: ExcelHeader.values()) {
							String msg = header.checkCell(row, prpdagent);
							if(msg.length()!=0){
								if(errMsg.length() > 0)
									errMsg.append(",").append("\\n");
								errMsg.append(msg);
							}
						}
						Datalist.add(prpdagent);
					}
				}
				if(errMsg.length() > 0) {
					errMsg.append(",").append("\\n").append("請整批重新上傳。");
					setMessage("上傳失敗:\\n"+errMsg.toString());
					return forward;
				}
				
				//檢查資料是否存在
				int index = 1;
				for(Prpdagent prpdagent:Datalist) {
					String msg = this.findAgent(prpdagent);
					if(msg.length()!=0){
						if(errMsg.length() > 0)
							errMsg.append("\\n");
						errMsg.append("第"+index+"筆，登錄證號 "+prpdagent.getLogincode()+","+msg);
					}
					index++;
				}
				if(errMsg.length() > 0) {
					setMessage("上傳失敗:\\n"+errMsg.toString());
					return forward;
				}
				
				//檢查所屬服務人員是否有效，服務人員有效才能存檔
				index = 1;
				for(Prpdagent prpdagent:Datalist) {
					Map<String,String> params = new HashMap<>();
					if(StringUtil.isSpace(prpdagent.getUnitcode()))
						params.put("orgicodeIsNull", "true");
					else
						params.put("orgicode", prpdagent.getUnitcode());
					params.put("cmem00", prpdagent.getHandlercode());
					params.put("validstatus", "1");
					Result searchResult = handlercodeTmpService.findHandlercodeIsValidByParams(params);
					Map<String,Object> map = new HashMap<String, Object>();
					//查詢結果cmem18為空有效，有值無效
					if (null != searchResult.getResObject()) {
						Map<String,Object> resultMap = (Map<String,Object>) searchResult.getResObject();
						if(!StringUtil.isSpace((String) resultMap.get("cmem18"))) {
							errMsg.append("第"+index+"筆，登錄證號 "+prpdagent.getLogincode()+",所屬服務人員無效或不存在，請重新確認。");
							errMsg.append("\\n");
						}
					} else {
						errMsg.append("第"+index+"筆，登錄證號 "+prpdagent.getLogincode()+",所屬服務人員無效或不存在，請重新確認。");
						errMsg.append("\\n");
					}
					index++;
				}
				if(errMsg.length() > 0) {
					setMessage("上傳失敗:\\n"+errMsg.toString());
					return forward;
				}
				
				//更新資料
				for(Prpdagent oldPrpdagent:Datalist) {
					Prpdagent prpdagent = new Prpdagent();
					prpdagent.setAgentcode(oldPrpdagent.getAgentcode());
					prpdagent.setHandlercode(oldPrpdagent.getHandlercode());
					Result updateResult = prpdagentService.updatePrpdagent(prpdagent);
					if(updateResult.getResObject() == null) {
						setMessage("上傳失敗:"+updateResult.getMessage());
						return forward;
					}
				}
				setMessage("整批更新完成，請重新查詢確認資料是否更新正確。");
				return Action.SUCCESS;
				
			} catch (Exception e) {
				logger.error(e.toString());
				setMessage("上傳失敗:"+e.getMessage());
				return forward;
			}
		} else {
			setMessage("上傳失敗:請選擇檔案。");
			return forward;
		}
	}
	
	private enum ExcelHeader{
		BUSINESSSOURCE(0,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return "第"+row.getRowNum()+"筆業務來源空白";
				}
				prpdagent.setBusinesssource(cellValue);
				return msg;
			}
		},AGENTCODE(1,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return "第"+row.getRowNum()+"筆業務員代碼空白";
				}
				prpdagent.setAgentcode(cellValue);
				return msg;
			}
		},AGENTNAME(2,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return "第"+row.getRowNum()+"筆業務員姓名空白";
				}
				prpdagent.setAgentname(cellValue);
				return msg;
			}
		},LOGINCODE(3,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return "第"+row.getRowNum()+"筆登錄證號空白";
				}
				prpdagent.setLogincode(cellValue);
				return msg;
			}
		},UNITCODE(4,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				prpdagent.setUnitcode(cellValue);
				return msg;
			}
		},HANDLERCODE(5,""){
			String checkCell(Row row,Prpdagent prpdagent) {
				String cellValue = getCellFormatValue(row.getCell(index)).trim();
				if(!checkString(cellValue)){
					return "第"+row.getRowNum()+"筆所屬服務人員空白";
				}
				prpdagent.setHandlercode(cellValue);
				return msg;
			}
		};
		int index;
		String msg;
		ExcelHeader (int index,String msg){
			this.index = index;
			this.msg = msg;
		}
		abstract String checkCell(Row row,Prpdagent prpdagent);
	}
	
	private String findAgent(Prpdagent prpdagent) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("agentcode", prpdagent.getAgentcode());
		params.put("logincode", prpdagent.getLogincode());
		params.put("businesssource", prpdagent.getBusinesssource());
		if(StringUtil.isSpace(prpdagent.getUnitcode()))
			params.put("unitcodeIsNull", true);
		else
			params.put("unitcode", prpdagent.getUnitcode());
		String[] agenttypeList = {"1"};
		params.put("agenttypeList", agenttypeList);
		params.put("validstatus", "1");
		Result searchResult = prpdagentService.findPrpdagentByParams(params);
		
		if(null != searchResult.getMessage()) {
			return "資料對應不存在，請重新確認。";
		}
		return "";
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
	
	/*下載sample檔案*/
	public String btnDowloadSample() throws Exception {
		try {
			downloadFileName = "SAMPLE.xlsx";
			inputStream = APS015Action.class.getClassLoader().getResourceAsStream("xlsx/APS027DownloadSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 END */

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public List<Prpdagent> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Prpdagent> devResults) {
		this.devResults = devResults;
	}

	public Prpdagent getPrpdagent() {
		return prpdagent;
	}

	public void setPrpdagent(Prpdagent prpdagent) {
		this.prpdagent = prpdagent;
	}
	
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 START */
	public HandlercodeTmpService getHandlercodeTmpService() {
		return handlercodeTmpService;
	}
	
	public void setHandlercodeTmpService(HandlercodeTmpService handlercodeTmpService) {
		this.handlercodeTmpService = handlercodeTmpService;
	}
	
	public File getUpload() {
		return upload;
	}
	
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public String getDownloadFileName() {
		return downloadFileName;
	}
	
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	/** mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 END */
}
