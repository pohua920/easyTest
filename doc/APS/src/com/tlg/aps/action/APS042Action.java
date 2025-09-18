package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firRepeatpolicyImportService.FirRepeatpolicyImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.prpins.service.FirRepeatpolicyBatchDtlService;
import com.tlg.prpins.service.FirRepeatpolicyBatchMainService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
@SuppressWarnings("serial")
public class APS042Action extends BaseAction {
	private static final Logger logger = Logger.getLogger(APS042Action.class);
	private File upload = null;//上傳的檔案
	private String uploadFileName;//上傳檔案名稱
	private String downloadFileName;//下載SAMPLE檔案名稱
	private InputStream inputStream;
	private String batchNo;
	private String rpYyyymm;
	private FirRepeatpolicyImportService firRepeatpolicyImportService;
	private FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService;
	private FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService;
	private List<FirRepeatpolicyBatchMain> devResults;
	private ConfigUtil configUtil;
	
	//轉入作業確定轉入按鈕，做資料匯入動作
	public String btnDataImport() throws Exception {
		try {
			if (upload != null) {
				Result result = firRepeatpolicyImportService.dataUploadAndImport(getUserInfo().getUserId().toUpperCase(), upload, uploadFileName, rpYyyymm);
				setMessage(result.getMessage().toString());
			} else {
				throw new SystemException("檔案讀取失敗");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			setMessage(e.getMessage().replaceAll("\"", ""));
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "BATCH_NO");
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
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
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
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	private void query() throws Exception {
		parameterHandler();
		Result result = firRepeatpolicyBatchMainService.findFirRepeatpolicyBatchMainByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirRepeatpolicyBatchMain>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String lnkGoQuery() throws Exception {
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
	
	/** 連結至檔案上傳頁面 */
	public String lnkGoUpload(){
		return Action.SUCCESS;
	}
	
	/** 參數處理 */
	private void parameterHandler() throws Exception {
		String batchNo = (String) getPageInfo().getFilter().get("batchNo");
		String rpYyyymm = (String) getPageInfo().getFilter().get("rpYyyymm");
		if(StringUtils.isBlank(batchNo)) {
			getPageInfo().getFilter().remove("batchNo");
		}
		if(StringUtils.isBlank(rpYyyymm)) {
			getPageInfo().getFilter().remove("rpYyyymm");
		}
	}
	
	/*下載sample檔案*/
	public String btnDowloadSample() throws Exception {
		try {
			downloadFileName = "SAMPLE.xlsx";
			inputStream = APS029Action.class.getClassLoader().getResourceAsStream("xlsx/FirRepeatpolicyBatchSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	public String downloadFile() throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("batchNo", batchNo);
			params.put("sortBy", "DCREATE ASC");
			Result result = firRepeatpolicyBatchDtlService.findByParams(params);
			if(result.getResObject() != null) {
				List<FirRepeatpolicyBatchDtl> list = (List<FirRepeatpolicyBatchDtl>) result.getResObject();
				
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				String[] titleArr = {"保單號碼","是否批改","要保人姓名","被保險人姓名","被保險人ID","要保人郵遞區號","要保人通訊地址",
						"標的物郵遞區號","標的物地址","抵押權人","放款部門別","保險起日","保險迄日","重複投保保險公司","重複投保保單號碼",
						"重複投保保險起日","重複投保保險迄日","聯絡電話"};
				XSSFRow rowTitle = sheet.createRow(0);
				for(int i=0;i<titleArr.length;i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				for (int i = 0; i < list.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(list.get(i).getPolicyno());
					row.createCell(1).setCellValue(list.get(i).getIsEndor());
					row.createCell(2).setCellValue(list.get(i).getApplyName());
					row.createCell(3).setCellValue(list.get(i).getInsuredName());
					row.createCell(4).setCellValue(list.get(i).getInsuredId());
					row.createCell(5).setCellValue(list.get(i).getApplyPostcode());
					row.createCell(6).setCellValue(list.get(i).getApplyAddress());
					row.createCell(7).setCellValue(list.get(i).getAddressCode());
					row.createCell(8).setCellValue(list.get(i).getAddressdetailinfo());
					row.createCell(9).setCellValue(list.get(i).getMortgageepeople());
					row.createCell(10).setCellValue(list.get(i).getLoansdepartment());
					if(list.get(i).getStartdate() != null) {
						row.createCell(11).setCellValue(sdf.format(list.get(i).getStartdate()));
					}
					if(list.get(i).getEnddate() != null) {
						row.createCell(12).setCellValue(sdf.format(list.get(i).getEnddate()));
					}
					row.createCell(13).setCellValue(list.get(i).getRepeatCompany());
					row.createCell(14).setCellValue(list.get(i).getRepeatPolicyno());
					if(list.get(i).getRepeatSdate() != null) {
						row.createCell(15).setCellValue(sdf.format(list.get(i).getRepeatSdate()));
					}
					if(list.get(i).getRepeatEdate() != null) {
						row.createCell(16).setCellValue(sdf.format(list.get(i).getRepeatEdate()));
					}
					row.createCell(17).setCellValue(list.get(i).getContactNumber());
					
				}
				downloadFileName = URLEncoder.encode(batchNo+".xlsx", "UTF-8");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte [] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);
				
			} else {
				setMessage("查無資料，無法產生XLSX檔案。");
				return Action.INPUT;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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

	public String getRpYyyymm() {
		return rpYyyymm;
	}

	public void setRpYyyymm(String rpYyyymm) {
		this.rpYyyymm = rpYyyymm;
	}

	public FirRepeatpolicyImportService getFirRepeatpolicyImportService() {
		return firRepeatpolicyImportService;
	}

	public void setFirRepeatpolicyImportService(FirRepeatpolicyImportService firRepeatpolicyImportService) {
		this.firRepeatpolicyImportService = firRepeatpolicyImportService;
	}

	public FirRepeatpolicyBatchMainService getFirRepeatpolicyBatchMainService() {
		return firRepeatpolicyBatchMainService;
	}

	public void setFirRepeatpolicyBatchMainService(FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService) {
		this.firRepeatpolicyBatchMainService = firRepeatpolicyBatchMainService;
	}

	public List<FirRepeatpolicyBatchMain> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirRepeatpolicyBatchMain> devResults) {
		this.devResults = devResults;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public static Logger getLogger() {
		return logger;
	}

	public FirRepeatpolicyBatchDtlService getFirRepeatpolicyBatchDtlService() {
		return firRepeatpolicyBatchDtlService;
	}

	public void setFirRepeatpolicyBatchDtlService(FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService) {
		this.firRepeatpolicyBatchDtlService = firRepeatpolicyBatchDtlService;
	}
}
