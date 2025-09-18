package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpdcarmodelext;
import com.tlg.prpins.service.PrpdcarmodelextService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業 */
@SuppressWarnings("serial")
public class APS053Action extends BaseAction {
	private List<Prpdcarmodelext> devResults = new ArrayList<>();
	private String downloadFileName;
	private InputStream inputStream;
	private PrpdcarmodelextService prpdcarmodelextService;
	private Prpdcarmodelext prpdcarmodelext;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
			formLoad("execute");
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
		Result result = prpdcarmodelextService.findprpdcarmodelextByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Prpdcarmodelext>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}

	/** 負責處理update動作 */
	private void update() throws SystemException, Exception {
		prpdcarmodelext.setIupdate(getUserInfo().getUserId().toUpperCase());
		prpdcarmodelext.setDupdate(new Date());
		Result result = prpdcarmodelextService.updateFirCtbcDeptinfo(prpdcarmodelext);
		setMessage(result.getMessage().toString());
	}

	/** 新增 */
	private void create() throws SystemException, Exception {
		prpdcarmodelext.setIcreate(getUserInfo().getUserId().toUpperCase());
		prpdcarmodelext.setDcreate(new Date());
		Result result = prpdcarmodelextService.insertPrpdcarmodelext(prpdcarmodelext);
		setMessage(result.getMessage().toString());
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public String downloadFile() throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sortBy", "OID ASC");
			params.put("countrycode", getPageInfo().getFilter().get("countrycode"));
			params.put("carbrand", getPageInfo().getFilter().get("carbrand"));
			params.put("modelname", getPageInfo().getFilter().get("modelname"));
			params.put("carkind", getPageInfo().getFilter().get("carkind"));
			params.put("carkindext", getPageInfo().getFilter().get("carkindext"));
			params.put("application1", getPageInfo().getFilter().get("application1"));
			parameterHandler();
			params.put("validdate", getPageInfo().getFilter().get("validdate"));
			params.put("validdateEnd", getPageInfo().getFilter().get("validdateEnd"));
			params.put("makedateStart", getPageInfo().getFilter().get("makedateStart"));
			params.put("makedateEnd", getPageInfo().getFilter().get("makedateEnd"));
			Result result = prpdcarmodelextService.findPrpdcarmodelextByParams(params);
			if (result.getResObject() != null) {
				List<Prpdcarmodelext> list = (List<Prpdcarmodelext>) result.getResObject();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				String[] titleArr = { "序號", "廠牌", "廠牌別稱", "名稱", "國產/進口", "車種", "車種大類代號", "排氣量", "噸數", "用途", "乘載數量",
						"門數", "燃料", "排檔型式", "製造日期", "生效日期(起)", "生效日期(迄)", "備註" };
				XSSFRow rowTitle = sheet.createRow(0);
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				for (int i = 0; i < list.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(list.get(i).getOid().toString());
					row.createCell(1).setCellValue(list.get(i).getCarbrand());
					row.createCell(2).setCellValue(list.get(i).getCarbrandext());
					row.createCell(3).setCellValue(list.get(i).getModelname());
					row.createCell(4).setCellValue(list.get(i).getCountrycode().equals("1") ? "國產" : "進口");
					row.createCell(5).setCellValue(list.get(i).getCarkind());
					row.createCell(6).setCellValue(list.get(i).getCarkindext());
					row.createCell(7).setCellValue(list.get(i).getExhaustscale());
					row.createCell(8).setCellValue(list.get(i).getTcount());
					row.createCell(9).setCellValue(list.get(i).getApplication1());
					row.createCell(10).setCellValue(list.get(i).getSeatcount());
					row.createCell(11).setCellValue(list.get(i).getDoors());
					row.createCell(12).setCellValue(list.get(i).getFueltype());
					row.createCell(13).setCellValue(list.get(i).getGeartype());
					if (list.get(i).getMakedate() != null) {
						row.createCell(14).setCellValue(sdf.format(list.get(i).getMakedate()));
					}
					if (list.get(i).getValiddate() != null) {
						row.createCell(15).setCellValue(sdf.format(list.get(i).getValiddate()));
					}
					if (list.get(i).getInvaliddate() != null) {
						row.createCell(16).setCellValue(sdf.format(list.get(i).getInvaliddate()));
					}
					row.createCell(17).setCellValue(list.get(i).getMark());

				}
				sdf = new SimpleDateFormat("yyyyMMdd");
				String filename = "廠代外部資料檔" + sdf.format(new Date()) + ".xlsx";
				downloadFileName = URLEncoder.encode(filename, "UTF-8");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte[] bookByteAry = bos.toByteArray();
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

	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try {
			formLoad("query");
			getPageInfo().getFilter().put("sortBy", "oid");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			formLoad("create");
			create();
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("新增失敗\\n" + e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			formLoad("update");
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("儲存失敗\\n" + e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	/**
	 * 連結至查詢頁面
	 * 
	 * @throws Exception
	 */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
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

	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		try {
			formLoad("update");
			if (null == prpdcarmodelext.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
				return Action.INPUT;
			}
			Result result = prpdcarmodelextService.findPrpdcarmodelextByOid(prpdcarmodelext.getOid());
			if (null == result.getResObject()) {
				setMessage(result.getMessage().toString());
			} else {
				prpdcarmodelext = (Prpdcarmodelext) result.getResObject();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	// /** 連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		this.formLoad("create");
		return Action.SUCCESS;
	}

	/** APS053E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
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

	/** APS053E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	/** 參數處理 */
	private void parameterHandler() throws Exception {
		dateHandler((String) getPageInfo().getFilter().get("sDate"), "makedateStart", 0);
		dateHandler((String) getPageInfo().getFilter().get("eDate"), "makedateEnd", 1);
		dateHandler((String) getPageInfo().getFilter().get("sValiddate"), "validdate", 0);
		dateHandler((String) getPageInfo().getFilter().get("eValiddate"), "validdateEnd", 1);
	}

	// 0 = Start 1 = End
	@SuppressWarnings("unchecked")
	private void dateHandler(String strDate, String str, int state) throws Exception {
		if (!StringUtil.isSpace(strDate)) {
			if (state == 0) {
				strDate += " 00:00:00";
			} else {
				strDate += " 23:59:59";
			}
			getPageInfo().getFilter().put(str, strDate);
		} else {
			getPageInfo().getFilter().remove(str);
		}
	}

	public List<Prpdcarmodelext> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Prpdcarmodelext> devResults) {
		this.devResults = devResults;
	}

	public PrpdcarmodelextService getPrpdcarmodelextService() {
		return prpdcarmodelextService;
	}

	public void setPrpdcarmodelextService(PrpdcarmodelextService prpdcarmodelextService) {
		this.prpdcarmodelextService = prpdcarmodelextService;
	}

	public Prpdcarmodelext getPrpdcarmodelext() {
		return prpdcarmodelext;
	}

	public void setPrpdcarmodelext(Prpdcarmodelext prpdcarmodelext) {
		this.prpdcarmodelext = prpdcarmodelext;
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
}
