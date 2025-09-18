package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.FirBopRenewalDataVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS023Action extends BaseAction {
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private List<FirBopRenewalDataVo> devResults;
	private ConfigUtil configUtil;
	private String fileName;
	private InputStream inputStream;

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
	
	/** APS023E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().getFilter().put("sortBy", "A.OLDPOLICYNO");
			getPageInfo().getFilter().put("sortType", "ASC");
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
		Result result = firAgtrnBatchDtlService.findRenewalDataByBatchNo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirBopRenewalDataVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS023E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS023E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start */
	/** APS023E0.jsp，按下產生檔按按鈕，產生excel檔案*/
	@SuppressWarnings("unchecked")
	public String btnGenExcel() throws Exception {
		String batchNo = getPageInfo().getFilter().get("batchNo").toString();
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firAgtrnBatchDtlService.findBopRnDataForExcelByBatchNo(params);
		if (result.getResObject() != null) {
			List<FirBopRenewalDataVo> dataList = (List<FirBopRenewalDataVo>) result.getResObject();
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				XSSFSheet sheet = workbook.createSheet();
				//mantis：FIR0608，處理人員：BJ085，需求單編號：FIR0608 外銀板信續件扣款前置檔產生作業_新增保單序號欄位
				String[] titleArr = { "新保單號碼", "受理編號", "分行代號", "板信機構代號", "他行庫機構代號", "保險公司代碼", "代扣繳帳號", "額度號碼", "授信帳號",
						"戶況", "放款產品子號", "系統別", "擔保品號碼", "保單序號" };
				XSSFRow rowTitle = sheet.createRow(0);
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				for (int i = 0; i < dataList.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(dataList.get(i).getPolicyno());
					row.createCell(1).setCellValue(dataList.get(i).getOrderseq());
					row.createCell(2).setCellValue(dataList.get(i).getExtracomcode());
					row.createCell(3).setCellValue(dataList.get(i).getTemp1());
					row.createCell(4).setCellValue(dataList.get(i).getTemp2());
					row.createCell(5).setCellValue(dataList.get(i).getTemp3());
					row.createCell(6).setCellValue(dataList.get(i).getLoanaccount1());
					row.createCell(7).setCellValue(dataList.get(i).getTemp4());
					row.createCell(8).setCellValue(dataList.get(i).getCreditnumber1());
					row.createCell(9).setCellValue(dataList.get(i).getTemp5());
					row.createCell(10).setCellValue(dataList.get(i).getTemp6());
					row.createCell(11).setCellValue(dataList.get(i).getTemp7());
					row.createCell(12).setCellValue(dataList.get(i).getCollateralnumber1());
					//mantis：FIR0608，處理人員：BJ085，需求單編號：FIR0608 外銀板信續件扣款前置檔產生作業_新增保單序號欄位
					row.createCell(13).setCellValue(dataList.get(i).getTemp8());
				}
				fileName = batchNo + ".xlsx";
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte[] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);

			} catch (SystemException se) {
				setMessage(se.getMessage());
			} catch (Exception e) {
				getRequest().setAttribute("exception", e);
				throw e;
			}
		}
		return Action.SUCCESS;
	}
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end */
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 
	 * APS023E0.jsp，按下批號資料下載按鈕，產生excel檔案*/
	@SuppressWarnings("unchecked")
	public String btnDownloadExcel() throws Exception {
		String forward = "input";
		String batchNo = getPageInfo().getFilter().get("batchNo").toString();
		Result result = firAgtrnBatchDtlService.findRenewalDataForExcelByBatchNo(batchNo);
		if (result.getResObject() == null) {
			setMessage(result.getMessage().toString());
			return forward;
		}
		List<FirBopRenewalDataVo> dataList = (List<FirBopRenewalDataVo>) result.getResObject();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet();
			String[] titleArr = { "續保單號", "受理編號", "轉核心要保時間", "要保書號", "保單號碼", "歸屬單位", "服務人員" };
			XSSFRow rowTitle = sheet.createRow(0);
			for (int i = 0; i < titleArr.length; i++) {
				rowTitle.createCell(i).setCellValue(titleArr[i]);
			}
			for (int i = 0; i < dataList.size(); i++) {
				FirBopRenewalDataVo firBopRenewalDataVo = dataList.get(i);
				XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
				row.createCell(0).setCellValue(firBopRenewalDataVo.getOldpolicyno());
				row.createCell(1).setCellValue(firBopRenewalDataVo.getOrderseq());
				row.createCell(2).setCellValue(firBopRenewalDataVo.getTransPpsTime() == null?"":new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(firBopRenewalDataVo.getTransPpsTime()));
				row.createCell(3).setCellValue(firBopRenewalDataVo.getProposalno());
				row.createCell(4).setCellValue(firBopRenewalDataVo.getPolicyno());
				String comInfo = (StringUtil.isSpace(firBopRenewalDataVo.getComcode())?"":firBopRenewalDataVo.getComcode()) + (StringUtil.isSpace(firBopRenewalDataVo.getComcname())?"":firBopRenewalDataVo.getComcname());
				row.createCell(5).setCellValue(comInfo);
				String handlerInfo = (StringUtil.isSpace(firBopRenewalDataVo.getHandler1code())?"":firBopRenewalDataVo.getHandler1code()) + (StringUtil.isSpace(firBopRenewalDataVo.getUsername())?"":firBopRenewalDataVo.getUsername());
				row.createCell(6).setCellValue(handlerInfo);
			}
			fileName = batchNo + ".xlsx";
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			byte[] bookByteAry = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bookByteAry);
			forward = Action.SUCCESS;

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
 
	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public List<FirBopRenewalDataVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirBopRenewalDataVo> devResults) {
		this.devResults = devResults;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
