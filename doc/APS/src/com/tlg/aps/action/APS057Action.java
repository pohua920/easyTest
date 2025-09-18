package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 */
@SuppressWarnings("serial")
public class APS057Action extends BaseAction {
	private static final Logger logger = Logger.getLogger(APS057Action.class);
	private InputStream inputStream;
	private String downloadFileName;
	private String queryType;
	private String sortBy;
	private String sortType;
	private List<Aps057ResultVo> devResults1;
	
	private FirAgtBotFdService firAgtBotFdService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	
	private String token;
	private static final Integer DATACOUNT = 1000;
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			if("query1".equals(queryType)) {
				getPageInfo().getFilter().put("sortBy", "FD.DCREATE");
				getPageInfo().getFilter().put("sortType", "DESC");
				getPageInfo().setId("query1");
				mainQuery1();
				return "query1";
			}else if("query2".equals(queryType)) {
				getPageInfo().getFilter().put("sortBy", "DTL.BATCH_NO, DTL.OLDPOLICYNO");
				getPageInfo().getFilter().put("sortType", "DESC");
				getPageInfo().setId("query2");
				mainQuery2();
				return "query2";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex1() throws Exception {
		try {
			if("query1".equals(getPageInfo().getId())) {
				mainQuery1();
				return "query1";
			}else if("query2".equals(getPageInfo().getId())) {
				mainQuery2();
				return "query2";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged1() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			if("query1".equals(getPageInfo().getId())) {
				mainQuery1();
				return "query1";
			}else if("query2".equals(getPageInfo().getId())) {
				mainQuery2();
				return "query2";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void mainQuery1() throws Exception {
		parameterHandler();
		Result result = firAgtBotFdService.findAPS057Main1ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults1 = (List<Aps057ResultVo>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void mainQuery2() throws Exception {
		parameterHandler();
		Result result = firAgtrnBatchDtlService.findAPS057Main2ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults1 = (List<Aps057ResultVo>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String lnkSortQuery() throws Exception {
		try {
			if("query1".equals(queryType)) {
				mainQuery1();
				return "query1";
			}else if("query2".equals(queryType)) {
				mainQuery2();
				return "query2";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String goQuery1() throws Exception {
		try {
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().setFilter(new HashMap<>());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String goQuery2() throws Exception {
		try {
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().setFilter(new HashMap<>());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS057E1.jsp(明細)頁面按下「產生xls」按鈕，下載EXCEL檔案 */
	@SuppressWarnings("unchecked")
	public String btnDownloadFile() throws Exception {
		try {
			getPageInfo().getFilter().put("sortBy", "DTL.BATCH_NO, DTL.OLDPOLICYNO");
			// 分次讀取資料寫入excel
			int count = firAgtrnBatchDtlService.countForAps057Main2(getPageInfo().getFilter());

			int cycleTimes = count / DATACOUNT;
			if (count % DATACOUNT > 0) {
				cycleTimes = cycleTimes + 1;
			}
			int startRow = 1;
			int endRow = DATACOUNT;

			List<Aps057ResultVo> excelList = new ArrayList<>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
				// 建立總表
				SXSSFSheet sheet = workbook.createSheet();
				String[] titleArr = { "批次號", "受理編號", "續保單號", "資料狀態", "轉核心要保書號", "轉核心保單號", "類型", "簽署日期",
						"火險生效日", "地震生效日", "委扣帳號", "分行代號", "招攬員編", "是否出單", "是否約定續保", "其他險生效日",
						"火險保額", "地震險保額", "其他險保額", "放款帳號", "比對註記", "比對結果"};
				SXSSFRow rowTitle = sheet.createRow(0);
				// 建立excel欄位
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}

				int totalRowNum = 0;
				for (int c = 0; c < cycleTimes; c++) {
					getPageInfo().setStartRow(startRow);
					getPageInfo().setEndRow(endRow);
					getPageInfo().getFilter().put("doPage", false);
					Result result = firAgtrnBatchDtlService.findAPS057Main2ByPageInfo(getPageInfo()); // 一次只查詢一千筆
					if (result.getResObject() != null) {
						excelList = (List<Aps057ResultVo>) result.getResObject();
						for (Aps057ResultVo dtl : excelList) {
							totalRowNum++;
							SXSSFRow row = sheet.createRow(totalRowNum); // 建立列 必須為每次迴圈筆數往上加
							row.createCell(0).setCellValue(dtl.getBatchNo());
							row.createCell(1).setCellValue(dtl.getOrderseq());
							row.createCell(2).setCellValue(dtl.getOldpolicyno());
							row.createCell(3).setCellValue(getDataStatusName(dtl.getDataStatus()));
							row.createCell(4).setCellValue(dtl.getProposalno());
							row.createCell(5).setCellValue(dtl.getPolicyno());
							row.createCell(6).setCellValue(dtl.getFdType());
							row.createCell(7).setCellValue(dtl.getFdDate() == null ? "" : sdf.format(dtl.getFdDate()));
							row.createCell(8).setCellValue(dtl.getStartdateF() == null ? "" : sdf.format(dtl.getStartdateF()));
							row.createCell(9).setCellValue(dtl.getStartdateQ() == null ? "" : sdf.format(dtl.getStartdateQ()));
							row.createCell(10).setCellValue(dtl.getAccountNo());
							row.createCell(11).setCellValue(dtl.getBranchNo());
							row.createCell(12).setCellValue(dtl.getBankSales());
							row.createCell(13).setCellValue(dtl.getIsApproval());
							row.createCell(14).setCellValue(dtl.getIsAutoRenew());
							row.createCell(15).setCellValue(dtl.getStartdateA() == null ? "" : sdf.format(dtl.getStartdateA()));
							row.createCell(16).setCellValue(dtl.getAmountF() == null ? "" : dtl.getAmountF().toString());
							row.createCell(17).setCellValue(dtl.getAmountQ() == null ? "" : dtl.getAmountQ().toString());
							row.createCell(18).setCellValue(dtl.getAmountA() == null ? "" : dtl.getAmountA().toString());
							row.createCell(19).setCellValue(dtl.getLoanaccount());
							String compareFlag = StringUtil.nullToSpace(dtl.getCompareFlag());
							switch (compareFlag) {
							case "Y":
								compareFlag = "比對完成";
								break;
							case "N":
								compareFlag = "比對未完成";
								break;
							case "E":
								compareFlag = "比對、檢核異常";
								break;
							default : 
								compareFlag = "未定義";
								break;
							}
							row.createCell(20).setCellValue(compareFlag);
							row.createCell(21).setCellValue(dtl.getCompareResult());
						}
					}

					startRow += DATACOUNT;
					endRow += DATACOUNT;
				}

				downloadFileName = "BOTRN_FD_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".xlsx";
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte[] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);
			}
			//連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("btnDownloadFile",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) { 
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	private String getDataStatusName(String dataStatus) {
		switch (dataStatus) {
		case "0":
			dataStatus = "未處理";
			break;
		case "1":
			dataStatus = "APS暫存失敗";
			break;
		case "2":
			dataStatus = "APS暫存成功";
			break;
		case "3":
			dataStatus = "轉核心暫存成功";
			break;
		case "4":
			dataStatus = "轉核心暫存失敗";
			break;
		case "5":
			dataStatus = "轉核心要保成功";
			break;
		case "6":
			dataStatus = "轉核心要保失敗";
			break;
		case "7":
			dataStatus = "轉核心完成";
			break;
		case "8":
			dataStatus = "轉核心失敗-收付失敗";
			break;
		case "A":
			dataStatus = "人工判定不轉核心";
			break;
		case "B":
			dataStatus = "臺銀已鎖定尚未比對簽署檔";
			break;
		case "C":
			dataStatus = "臺銀比對簽署檔不一致";
			break;
		default:
			dataStatus = "未定義";
			break;
		}
		
		return dataStatus;
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endDate");
		}
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<Aps057ResultVo> getDevResults1() {
		return devResults1;
	}

	public void setDevResults1(List<Aps057ResultVo> devResults1) {
		this.devResults1 = devResults1;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public FirAgtBotFdService getFirAgtBotFdService() {
		return firAgtBotFdService;
	}

	public void setFirAgtBotFdService(FirAgtBotFdService firAgtBotFdService) {
		this.firAgtBotFdService = firAgtBotFdService;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
